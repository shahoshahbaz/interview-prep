# Unique ID Generator — Cheat Sheet

---

## Table of Contents
1. [Problem Statement & Requirements](#1-problem-statement--requirements)
2. [Estimation](#2-estimation)
3. [High-Level Design](#3-high-level-design)
4. [Components Deep-Dive](#4-components-deep-dive)
5. [APIs](#5-apis)
6. [Data Model](#6-data-model)
7. [Flows](#7-flows)
8. [Failure Handling](#8-failure-handling)
9. [Trade-offs](#9-trade-offs)

---

## 1. Problem Statement & Requirements

**Problem Statement**
Design a service that generates unique IDs for distributed systems — e.g., for database rows, orders, messages — at scale, across multiple nodes, without coordination bottlenecks.

**Functional Requirements**
- Generate a unique ID on request
- IDs must be unique across the entire system (multi-node, multi-datacenter)
- IDs should be roughly sortable by time of generation (useful for indexing, pagination, debugging)

**Non-Functional Requirements**
- High availability — ID generation can't be a single point of failure
- Low latency (single-digit ms, ideally in-process/no network call)
- High throughput (tens of thousands of IDs/sec, scalable horizontally)
- No central coordination/lock on the hot path (would kill throughput and availability)
- IDs fit in 64 bits (common constraint — fits a `long`, indexes well, no string overhead)

**Out of scope (for now)**
- Guaranteed strict global ordering across nodes (only rough time-ordering)
- Security/unguessability of IDs (not a goal unless stated)

---

## 2. Estimation

**Scale assumptions**
- 10,000 IDs/sec sustained, bursting to 50,000 IDs/sec at peak
- Multi-datacenter deployment (say 3 regions, multiple nodes per region)

**ID space math**
- 64-bit ID → 2^64 ≈ 1.8 × 10^19 possible values — effectively unlimited space, the constraint is *structure*, not exhaustion
- At 50,000 IDs/sec sustained over 10 years:
   - Seconds in 10 years ≈ 10 × 365.25 × 24 × 60 × 60 ≈ 3.15 × 10^8 seconds
   - Total IDs = 50,000 × 3.15 × 10^8 ≈ **1.6 × 10^13** IDs
   - Compared to the full space (1.8 × 10^19), that's a negligible fraction — confirms 64 bits is comfortably sufficient

**Throughput per node**
- If IDs are generated in-memory (no DB/network round trip), a single node can easily do >1M IDs/sec
- The real bottleneck isn't raw generation speed — it's avoiding **collisions across nodes** without coordination

**Bit budget (this drives the design in HLD)**
- 64 bits total split across: timestamp (time-ordering + most of the range), machine/node ID (uniqueness across nodes), sequence number (uniqueness within a node in the same millisecond)
- Example split (Twitter Snowflake-style): 1 unused sign bit + 41 bits timestamp + 10 bits machine ID + 12 bits sequence

   - **41 bits timestamp → ~69 years**
      - 2^41 = 2,199,023,255,552 (~2.2 × 10^12) distinct millisecond values
      - 2.2 × 10^12 ms ÷ 1000 = 2.2 × 10^9 seconds
      - 2.2 × 10^9 sec ÷ (60×60×24×365.25 sec/year) ≈ **69.7 years** of range from a custom epoch (not Unix epoch — avoids wasting range on years before the system existed)

   - **10 bits machine ID → 1,024 nodes**
      - 2^10 = **1,024** distinct node IDs (0–1023) that can generate concurrently without collision

   - **12 bits sequence → 4,096 IDs/ms/node**
      - 2^12 = **4,096** distinct sequence values (0–4095), reset every millisecond
      - 4,096 × 1,000 ms/sec = **4,096,000 IDs/sec per node** — ~82x the 50K/sec peak target, comfortable headroom

   - **Bit check:** 1 + 41 + 10 + 12 = 64 bits ✓ (leading bit kept 0 to stay a positive signed long)

   - **(timestamp, sequence) — why it matters:** uniqueness *within one node* comes from this pair, not the timestamp alone. Same ms → sequence increments (0, 1, 2...). Clock ticks to next ms → sequence resets to 0. If a node exceeds 4,096 IDs in one ms, it busy-waits for the next tick (rarely hit given the 4.096M/sec headroom).

**Storage**
- The generator itself is stateless — it doesn't persist the IDs it issues, so no storage estimation applies to this service (unlike the downstream systems that consume the IDs)

---

## 3. High-Level Design

```
Client
   |
   v
Load Balancer
   |
   v
┌─────────────────────────────┐
│  ID Generator Node 1  (machine_id=0)  │
│  ID Generator Node 2  (machine_id=1)  │  ← stateless, active-active
│  ID Generator Node 3  (machine_id=2)  │
└─────────────────────────────┘
   |
   v
Zookeeper / etcd (used ONLY at startup, not on hot path)
   — assigns each node a unique machine_id
   — detects node crashes so machine_id can be reclaimed
```

**Flow**
1. Client sends `GET /generate` to the load balancer
2. LB routes to any available ID Generator node (no session affinity needed — nodes are stateless and interchangeable)
3. Node reads its local clock, combines with its own `machine_id` (assigned once, cached in memory) and its in-memory sequence counter
4. Node returns the 64-bit ID directly — no DB write, no network call, no coordination with other nodes
5. Node increments/resets its sequence counter locally for the next request

**Why this shape works**
- Each node generates IDs **independently** — the (timestamp, machine_id, sequence) combination guarantees global uniqueness without nodes ever talking to each other on the hot path
- Horizontal scaling = just add more nodes (up to 1,024, per the 10-bit machine ID budget from Section 2)
- No single point of failure — any node can go down and the LB routes around it; no shared state to lose

**Where coordination *is* needed (and why it's off the hot path)**
- Machine ID assignment: needs a coordinator (Zookeeper/etcd) so two nodes never get the same ID
- This only happens once per node at startup/restart — not per request — so it doesn't affect the throughput or availability numbers from Section 2

---

## 4. Components Deep-Dive

**4.1 Machine ID Assignment**

Two common approaches:

| Approach | How it works | Trade-off |
|---|---|---|
| **Zookeeper/etcd ephemeral nodes** | On startup, node creates an ephemeral sequential znode under `/id-generator/nodes/`; its sequence number becomes the `machine_id`. If node crashes, znode auto-deletes → ID freed for reuse | Requires running/operating Zookeeper — extra infra |
| **Config-based / manual assignment** | `machine_id` set via environment variable or config file at deploy time (e.g., derived from Kubernetes pod ordinal in a StatefulSet) | Simpler, no extra infra — but you must guarantee no duplicate config across deploys |

- In practice, **Kubernetes StatefulSet pod ordinal** (pod-0, pod-1, pod-2...) is a clean way to get a stable, unique `machine_id` without Zookeeper — leans on infra you likely already have
- Either way: this happens **once at startup**, cached in memory, never touched again during request handling

**4.2 Clock Handling (the trickiest part)**

- Each node uses its **local system clock** to generate the timestamp component — no calls to a central time service (that would reintroduce a bottleneck/SPOF)
- **Clock drift risk:** if a node's clock is out of sync with others, IDs are only *roughly* time-ordered across nodes — this is accepted per the Section 1 requirement (rough ordering, not strict global ordering)
- **Clock moving backwards (NTP correction, VM pause/resume) — the critical failure case:**
   - If `current_ms < last_recorded_ms` on that node, the node **refuses to generate an ID** and either:
      - Throws an error / returns 503 until the clock catches up, or
      - Waits (`sleep`) until `current_ms > last_recorded_ms` again
   - This is essential — without this check, a backwards clock jump could produce a duplicate `(timestamp, sequence)` pair
- Mitigation: run NTP with small correction steps (not large jumps), and monitor clock skew across nodes as an operational metric

**4.3 Sequence Counter**

- In-memory counter, local to each node, **not shared or persisted**
- Reset to 0 on every new millisecond (from Section 2)
- Thread-safety: if a node handles concurrent requests, the counter needs an atomic increment (e.g., `AtomicInteger` in Java) — a naive read-modify-write across threads would produce duplicate sequence values within the same node

**4.4 ID Encoding**

- Final ID assembled via bit-shifting:
  ```
  id = (timestamp << 22) | (machine_id << 12) | sequence
  ```
  (22 = 10 bits machine_id + 12 bits sequence, positions timestamp in the high bits)
- Returned as a `long` (or stringified if the client expects a string) — no encoding/decoding service needed, it's pure bitwise math

---

## 5. APIs

**Single endpoint — this service does one thing**

```
GET /v1/id/generate
```

**Request**
- No body, no required params
- Optional: `count` (int, default 1) — batch-generate multiple IDs in one call to reduce round-trips for high-throughput clients

**Response — single ID**
```
200 OK
{
  id: long        — the generated 64-bit ID
}
```

**Response — batch (`?count=N`)**
```
200 OK
{
  ids: long[]      — array of N generated IDs
}
```

**Error responses**
| Status | Meaning |
|---|---|
| 503 Service Unavailable | Node detected backward clock drift, refusing to generate until clock catches up (Section 4.2) |
| 400 Bad Request | `count` exceeds max batch size (e.g., cap at 1000 per call to avoid one client hogging a node's sequence space) |

**Why so minimal**
- No auth, no request body, no idempotency key needed — this isn't a mutating/stateful operation from the client's perspective, every call is safe to retry
- No `DELETE` or `GET /id/{id}` — the service doesn't track or look up IDs it issued, it's pure generation

---

## 6. Data Model

**There isn't one — and that's the point**

Unlike most system designs, this service has **no persistent data model**:

- No database, no table schema, no rows to define
- Every ID is computed from three ephemeral inputs (timestamp, machine_id, sequence) and returned immediately — nothing is written to disk or retained after the response

**What *is* held in memory (not "data" in the persistence sense)**

| Item | Scope | Lifetime |
|---|---|---|
| `machine_id` | Per node | Assigned once at startup, held for the process lifetime |
| `sequence` counter | Per node | Resets every millisecond, lives only in RAM |
| `last_timestamp` | Per node | Used for the backward-clock check (Section 4.2), overwritten every request |

**The only place a "data model" shows up: the coordination layer**

If using Zookeeper/etcd for machine ID assignment (Section 4.1):
```
/id-generator/nodes/node-0000000001  → ephemeral znode, no payload needed
/id-generator/nodes/node-0000000002
```
This isn't really a data model for IDs — it's just a registry of currently-active machine IDs, and it's outside this service's core request path.

**Contrast with downstream consumers**
- The services that *use* these IDs (orders table, ledger entries, etc.) have their own data models where the ID is just a primary key column — but that's out of scope for the ID generator itself

---

## 7. Flows

**7.1 Happy Path — Single ID Request**

```
Client → LB → Node 5 (machine_id=5)
                 |
                 v
         Read local clock: ts = 1,742,001,337,201
                 |
                 v
         Compare to last_timestamp on this node
         (1,742,001,337,201 > 1,742,001,337,190) → OK, clock moved forward
                 |
                 v
         New ms? → yes → sequence = 0
         (if same ms as last request → sequence++ instead)
                 |
                 v
         id = (ts << 22) | (5 << 12) | 0
                 |
                 v
         Update last_timestamp = 1,742,001,337,201
                 |
                 v
         Return { id } → Client
```
Total time: microseconds — everything is in-memory, no I/O.

**7.2 Same-Millisecond Burst**

```
Requests A, B, C all land on Node 5 within the same millisecond (ts = 1000)
  A: sequence 0 → id_A = (1000, 5, 0)
  B: sequence 1 → id_B = (1000, 5, 1)
  C: sequence 2 → id_C = (1000, 5, 2)
  ts stays the same, sequence is what differentiates them
```

**7.3 Failure Scenario — Clock Moves Backward**

```
Node's last_timestamp = 1000
NTP correction fires → local clock now reads 998
                 |
                 v
         current_ms (998) < last_timestamp (1000) → VIOLATION
                 |
                 v
    Option A: return 503 immediately, let client retry (possibly against a different node via LB)
    Option B: sleep until clock catches up past 1000, then proceed
                 |
                 v
         Once clock recovers → resume normal generation
```
Either option prevents a duplicate `(timestamp, sequence)` — the core invariant the whole design depends on.

**7.4 Failure Scenario — Node Crash**

```
Node 5 crashes
                 |
                 v
LB health check fails → LB stops routing traffic to Node 5
                 |
                 v
If using Zookeeper for machine_id: ephemeral znode for machine_id=5 auto-deletes
                 |
                 v
machine_id=5 becomes available for a future node to claim on restart
                 |
                 v
No data loss — the node held no persistent state (Section 6), so nothing needs recovery
```

**7.5 Failure Scenario — Sequence Overflow (>4096 IDs in 1ms)**

```
Node 5 issues sequence 0..4095 within ts=1000, a 4097th request arrives in the same ms
                 |
                 v
         sequence would overflow past 12-bit max
                 |
                 v
         Node busy-waits (spins) until clock ticks to ts=1001
                 |
                 v
         sequence resets to 0, request proceeds normally
```
Rare in practice — per Section 2, capacity is 4.096M/sec vs. a 50K/sec peak target.

---

## 8. Failure Handling

**8.1 Failure modes summary table**

| Failure | Detection | Response | Data at risk? |
|---|---|---|---|
| Single node crashes | LB health check fails | LB routes around it; machine_id reclaimed via ephemeral znode expiry | None — stateless |
| Clock moves backward (NTP correction) | `current_ms < last_timestamp` check | Reject (503) or busy-wait until clock recovers | None, but prevents a duplicate ID |
| Sequence overflow (>4096/ms on one node) | Sequence counter hits max | Busy-wait for next ms tick | None — just added latency |
| Coordinator (Zookeeper/etcd) goes down | Node can't get new `machine_id` at startup | **Existing nodes keep serving** — they already have their `machine_id` cached in memory; only *new* node startup is blocked | None for running traffic |
| Entire datacenter/region goes down | LB/DNS health checks | Traffic fails over to nodes in other regions | None — other regions have disjoint machine_id ranges, no collision risk |
| Load balancer itself fails | Depends on LB tier (DNS failover, multi-LB active-active) | Standard LB redundancy patterns — outside this service's specific design | N/A |

**8.2 Why the coordinator being down is not scary**

This is a common interviewer probe: *"What happens if Zookeeper goes down?"*

- The coordinator is only in the **critical path at startup**, not per-request
- A running node has already cached its `machine_id` in memory — it keeps generating IDs completely independently of Zookeeper's availability
- Impact of Zookeeper being down: you can't **scale up** (new nodes can't get assigned an ID) and can't **restart crashed nodes** cleanly — but existing capacity is unaffected
- This is the payoff of the design choice in Section 3: pushing coordination off the hot path

**8.3 Region assignment to prevent cross-DC collisions**

- If deploying across 3 regions, split the 1,024 machine_id space by region (e.g., region A: 0–341, region B: 342–682, region C: 683–1023) or reserve high bits of machine_id for region
- Prevents two nodes in different regions from ever being independently assigned the same `machine_id` by their respective (potentially disconnected) coordinators

**8.4 Monitoring / alerting worth calling out**
- Clock skew per node (catch NTP drift before it causes backward-clock rejections)
- Sequence-overflow rate per node (signals a node is near its per-ms capacity ceiling)
- machine_id pool utilization (are you approaching the 1,024-node ceiling?)

---

## 9. Trade-offs

**9.1 Snowflake-style (timestamp + machine_id + sequence) vs. alternatives**

| Approach | Pros | Cons |
|---|---|---|
| **Snowflake-style (chosen)** | No coordination on hot path, roughly time-sortable, compact 64-bit int | Requires machine_id management; vulnerable to clock issues |
| **UUID (v4, random)** | Zero coordination, zero state, trivially simple | 128 bits (2x storage/index cost), not sortable by time, poor DB index locality (random inserts fragment B-tree indexes) |
| **Centralized DB counter (auto-increment)** | Simple, strictly ordered | Single point of failure, doesn't scale horizontally, becomes a bottleneck under high write throughput |
| **Range-handout (DB hands each node a block of IDs, e.g. 1000 at a time)** | Reduces DB calls vs. per-ID auto-increment | Still has a DB dependency; IDs not time-sortable across nodes; gaps if a node crashes mid-block |

**9.2 Why Snowflake-style wins for this use case**
- The NFRs from Section 1 (low latency, no hot-path coordination, high availability) rule out anything requiring a network call per ID
- Rough time-ordering was a stated requirement — rules out pure UUID v4
- 64-bit fits standard `long`/`bigint` columns cleanly — rules out 128-bit UUID on storage/index-efficiency grounds

**9.3 What you give up**
- **Strict global ordering** — two IDs generated in the same millisecond across *different* nodes aren't ordered relative to each other (only within the same node). If a use case truly needs strict cross-node ordering, this design doesn't provide it — you'd need a different approach (e.g., a single ordered log like Kafka, at the cost of throughput/availability)
- **Predictability** — IDs are not cryptographically random; the structure (timestamp + machine_id) is guessable/reverse-engineerable. Not a stated requirement here, but worth flagging if the interviewer pushes on "can IDs be public-facing?"
- **Clock dependency** — correctness leans on each node's local clock behaving reasonably (Section 8.1); this is a real operational surface area, not free

**9.4 If asked "how would you extend this for stricter ordering?"**
- Route all writes needing strict order through a single partition/leader (sacrifices the "no coordination" property, trades throughput for ordering guarantees) — good answer to show you understand the CAP/PACELC-style trade-off explicitly rather than presenting Snowflake as a free lunch

---