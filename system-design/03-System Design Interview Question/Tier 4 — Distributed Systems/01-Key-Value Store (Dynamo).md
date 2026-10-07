# Designing a Key-Value Store (Medium — Single-Node + Naive Scaling)

## Table of Contents
1. [Problem Statement](#1-problem-statement)
2. [Requirements & Goals](#2-requirements--goals)
3. [Assumptions + BOE Estimation](#3-assumptions--boe-estimation)
4. [High-Level Design](#4-high-level-design)
5. [Core Components](#5-core-components)
6. [API Design](#6-api-design)
7. [Data Model](#7-data-model)
8. [Key Flows](#8-key-flows)
9. [Failure Handling](#9-failure-handling)
10. [Trade-offs & Alternatives](#10-trade-offs--alternatives)
11. [Glossary](#11-glossary)

---

## 1. Problem Statement

Design a key-value store supporting `put(key, value)` and `get(key)`, starting as a **single node**, then extended via **naive horizontal scaling** (basic sharding + primary-replica replication) — no consistent hashing, no gossip, no vector clocks. This is the baseline version before the distributed/Dynamo-style design.

---

## 2. Requirements & Goals

**Functional**
- `put(key, value)` — insert/update
- `get(key)` — retrieve
- `delete(key)` — remove
- Keys/values are simple strings or blobs, no schema

**Non-Functional**
- **Durable** — writes survive a crash (single node must not lose acknowledged data)
- **Available for the given scale** — acceptable to have a defined single point of failure initially (single node), then improved via replication
- **Simple horizontal scale-out** — data can be split across multiple nodes via **static/naive sharding** (e.g., `hash(key) % num_shards`) when one node isn't enough
- **Fault tolerance via replication** — each shard has a **primary + replica(s)**; replica takes over if primary fails (not automatic multi-master, just basic failover)
- **Low latency** — fast in-memory-first reads/writes, similar performance goals to the distributed version but without the coordination overhead
- **Consistency model:** strong consistency within a shard (single writer = primary) — simpler to reason about since there's no quorum/vector-clock complexity

---

## 3. Assumptions + BOE Estimation

**Assumptions**
- 10M keys, avg value size 1 KB
- Read:Write ratio = 9:1
- Peak QPS: 9,000 reads/sec, 1,000 writes/sec (total 10,000 QPS)

**Storage**
- Raw data: 10M × 1 KB = 10 GB
- With 1 replica per shard (RF=2): 10 GB × 2 = 20 GB total

**Single-node capacity check**
- A single beefy node (large RAM + SSD) can typically handle 10s of thousands of QPS for simple KV ops → one node alone could plausibly handle 10,000 QPS at this scale
- Storage-wise, 20 GB easily fits on one machine too
- **Conclusion:** at this scale, a single node might genuinely suffice — sharding becomes necessary only past a threshold (e.g., data doesn't fit in memory/disk, or QPS exceeds single-node capacity)

**When naive scaling kicks in (example trigger)**
- If dataset grows to, say, 500M keys × 1 KB = 500 GB → too big for one node's practical working set → split into N static shards (e.g., 8 shards via `hash(key) % 8`), each an independent primary-replica pair

This section's main point for the interview: show you know when NOT to over-engineer — single-node is a valid, correct answer until a concrete bottleneck (storage or QPS) forces sharding.

---

## 4. High-Level Design

**Core idea:** Start with **one node** handling all reads/writes. Add a **primary-replica** pair for durability/failover. Scale out via **static sharding** only when a concrete bottleneck (storage or QPS) is hit.

### Stage 1 — Single Node
```
Client → Server (in-memory hashmap + disk persistence) → Response
```
- One process, one machine, handles everything
- Simple, no coordination overhead — the correct answer until you outgrow it

### Stage 2 — Add Replication (Primary-Replica)
```
Client → Load Balancer → Primary Node (handles all writes)
                              │
                              ▼ (async or sync replication)
                          Replica Node (serves reads, standby for failover)
```
- Primary handles all writes, replica(s) get a copy of every write
- Reads can be served from replica(s) too → offloads read traffic from primary
- If primary dies, a replica is promoted (manual or via simple health-check + failover mechanism) — no gossip, just a basic heartbeat/monitor

### Stage 3 — Naive Sharding (once single primary can't handle the data/load)
```
Client → Router/Proxy → hash(key) % num_shards → correct Shard
                                                       │
                                          Shard = its own Primary + Replica pair
```
- Data split across N **fixed** shards using a simple modulo or range-based scheme
- Each shard is independently a Stage-2 setup (primary + replica)
- **Static** — unlike consistent hashing, adding/removing a shard means recomputing `% num_shards` for *all* keys (a full reshuffle) — this is the explicit trade-off vs. the distributed design

### Why this shape (ties to requirements)
- Matches "don't over-engineer" — each stage only gets added when justified by the BOE numbers
- Strong consistency is easy here — single primary per shard = single source of truth, no conflict resolution needed
- The known weakness (full reshuffle on resharding) is the natural segue into *why* consistent hashing exists — good talking point to bridge into the Hard version

---

## 5. Core Components

### Component Diagram

```
                              ┌──────────────┐
                              │    Client    │
                              └──────┬───────┘
                                     │
                     ┌───────────────────────────────┐
                     │   Router / Sharding Layer     │
                     │    hash(key) % num_shards     │ (Stage 3 only — skip for Stage 1-2)
                     └───────────────┬───────────────┘
                                     │
                                     ▼
                         ┌────────────────────────┐
                         │      Load Balancer     │
                         └───────┬───────┬────────┘
                       (writes)  │       │ (reads, optional)
                                 ▼       ▼
                    ┌────────────────┐    ┌─────────────────┐
                    │  PRIMARY node  │    │  REPLICA node   │
                    │                │    │                 │
                    │ ┌────────────┐ │    │ ┌─────────────┐ │
                    │ │ WAL        │ │    │ │ WAL         │ │
                    │ ├────────────┤ │    │ ├─────────────┤ │
                    │ │ Memtable   │ │──▶ │ │ Memtable    │ │  (replication
                    │ ├────────────┤ │repl│ ├─────────────┤ │   stream)
                    │ │ SSTables   │ │    │ │ SSTables    │ │
                    │ └────────────┘ │    │ └─────────────┘ │
                    └───────┬────────┘    └────────┬────────┘
                            │                      │
                            └──────────┬───────────┘
                                       ▼
                          ┌─────────────────────────┐
                          │  Failover Monitor       │
                          │  (heartbeat / health    │
                          │   check on Primary)     │
                          └─────────────────────────┘
                     on Primary failure → promotes Replica
```

**How they connect:**
- Client → Router (only if sharded) → Load Balancer → Primary (writes) / Primary or Replica (reads)
- Primary → Replica: continuous replication stream (sync or async) of every write
- Failover Monitor watches Primary independently, triggers promotion if it goes dark
- Each node internally runs its own storage engine (WAL → Memtable → SSTable), completely independent of what the other node's storage engine is doing — replication just re-applies the same writes on the replica's own engine

### Component 1: Storage Engine (per node)

Each node — primary or replica — runs its own independent storage engine, using the **LSM-tree** approach:

1. **Write-ahead log (WAL):** every `put` is first appended to a log file on disk (fast sequential write). Only after this succeeds is the write considered durable.
2. **Memtable:** the write is then inserted into an in-memory sorted structure (e.g., skip list). Reads check this first — it holds the newest data. Has a size cap (e.g., 64 MB).
3. **SSTable:** once the memtable fills up, its sorted contents are flushed to disk as one immutable file. A fresh memtable takes over. Over time, multiple SSTables accumulate; a background **compaction** process merges them, dropping outdated/deleted entries.
4. **Crash recovery:** if the node crashes before a memtable flush, the WAL is replayed on restart to rebuild the memtable — no data lost.

**Why multiple SSTables accumulate:** SSTables are immutable — once written, never modified. So every memtable flush must write a **brand-new** file, it can't update an existing one.
- T1: memtable fills → flush → SSTable_1 (new file)
- New writes come in → fresh memtable fills again → T2: flush → SSTable_2 (another new file)
- Repeat → SSTable_3, SSTable_4, SSTable_5... — one more file per flush, forever, until compaction reduces the count

**How conflicting versions across SSTables are resolved:** every write carries a timestamp/sequence number, so an entry is really `key → (value, timestamp)`, not just `key → value`.
- **Read path (`get`):** check memtable first (always newest, not yet flushed). If not found, check SSTables **newest → oldest** by flush order. Return the **first match found** — since scanning is newest-first, the first hit is automatically the latest version; older SSTables aren't even checked once a match is found.
- **Compaction path (merge):** when compaction merges SSTables that both contain the same key with different values, it compares their timestamps and **keeps the higher (newer) one**, discarding the older — the merged SSTable ends up with only the winning value.
- **Why this is safe here (unlike the distributed design):** a single node processes its own writes sequentially, so it has one unambiguous ordering — "higher timestamp wins" is sufficient. No vector clocks needed, because there's no concurrent-writes-across-different-nodes problem within one storage engine.

**No coordinator/replica-set logic needed** (unlike the distributed design) — there's exactly one primary, so the write path is simply WAL → memtable → SSTable, full stop.

**Replication reuses the WAL:** many real single-primary systems (PostgreSQL, MySQL) ship the WAL stream itself to the replica, which replays those entries into its own memtable/SSTable pipeline. Primary and replica end up with independently-built but logically-identical data — not copied files, but reconstructed the same way.

**Compaction is per-node:** both primary and replica independently accumulate and compact their own SSTables — not synchronized between them.

**Failure recovery is simpler than the distributed case:** if the primary is unrecoverable, the replica already has a full independent copy (kept continuously in sync via WAL streaming) — it just gets promoted. No anti-entropy/repair process needed, since it wasn't periodically reconciled, it was continuously fed.

**Summary — Concrete Sequences**

*SSTable creation sequence:*
- Write "key1" → appended to WAL → inserted into memtable
- Write "key2" → appended to WAL → inserted into memtable
- ... writes keep landing in WAL + memtable ...
- T1: memtable fills → flush → SSTable_1 written (a new file)
- New writes keep coming → each still WAL-appended first → fresh memtable fills again
- T2: flush → SSTable_2 (another new file)
- Repeat → SSTable_3, SSTable_4, SSTable_5...

*Read path (`get("user123")`) sequence:*
- Step 1: check memtable → not found
- Step 2: check SSTable_5 (newest) → not found
- Step 3: check SSTable_4 → found, entry = `(value="B", timestamp=105)` → **return "B"**
- Step 4: SSTable_3, SSTable_2, SSTable_1 → never checked, search stops at first match

*Compaction (merge) sequence:*
- Compaction picks SSTable_1 and SSTable_2 to merge
- SSTable_1 has `key="user123" → (value="A", timestamp=100)`
- SSTable_2 has `key="user123" → (value="B", timestamp=105)`
- Compare timestamps: 105 > 100 → **keep "B", discard "A"**
- Write merged SSTable_new with `key="user123" → (value="B", timestamp=105)`
- Delete SSTable_1 and SSTable_2 (superseded by SSTable_new)

### Component 2: Primary-Replica Replication
- **Synchronous replication:** primary waits for replica ack before confirming write to client → stronger durability, higher write latency
- **Asynchronous replication:** primary confirms write immediately, replica catches up shortly after → lower latency, small risk of data loss if primary dies before replica catches up
- Trade-off is the classic consistency vs. latency choice — state explicitly which one you're picking and why

### Component 3: Failover Mechanism
- A simple health check/heartbeat monitors the primary
- If primary is unresponsive past a threshold → replica is promoted to primary
- Naive version: manual failover or a single monitor process (potential SPOF in the monitor itself)
- Slightly better version: a small quorum of monitors (e.g., 3) vote on promoting a replica

### Component 4: Router / Sharding Layer (Stage 3 only)
- A thin proxy or client-side library computes `hash(key) % num_shards` to route each request
- Needs to know current shard count and each shard's primary address — kept in a small config service or static config
- Resharding is manual/offline: changing `num_shards` requires a data migration step, not a live rebalance

### Component 5: Client / Load Balancer
- Stage 1–2: simple load balancer routes reads to primary or replicas, writes always to primary
- Stage 3: the router (Component 4) sits in front of, or alongside, this

---

## 6. API Design

**Core Operations**

```
PUT /keys/{key}
Body: { "value": <blob> }
Response: 200 OK { "status": "success" }
```

```
GET /keys/{key}
Response: 200 OK { "value": <blob> }
         404 Not Found (key doesn't exist)
```

```
DELETE /keys/{key}
Response: 200 OK { "status": "deleted" }
         404 Not Found
```

**Design notes for this scope (Medium):**

- **No consistency-level parameter** — every request just talks to the primary (writes) or primary/replica (reads) — consistency is implicit, not client-controlled
- **Routing is transparent to the client** — if sharded (Stage 3), the client hits a router/proxy that internally resolves `hash(key) % num_shards`; the client API surface doesn't change, only what's behind it
- **Idempotency:** `PUT` is naturally idempotent (same key+value → same end state). `DELETE` should return success even if the key is already gone, to stay idempotent for retries
- **Optional: conditional write** — `PUT /keys/{key}?if_not_exists=true` for simple "create only if absent" semantics — easy to support since there's a single primary (no quorum ambiguity)
- **Optional: batch endpoints** — `POST /keys/batch_get` / `batch_put` — worth mentioning as an optimization if the interviewer probes on reducing round-trips, not core scope

---

## 7. Data Model

**Logical Model (client-facing)**

Simple key-value pairs — no schema enforcement:

```
key:   string (e.g., UTF-8, max length ~256 bytes — practical limit)
value: opaque blob (bytes) — up to some max size, e.g., 1 MB
```

**Internal Storage Record (per entry, on disk/in memtable)**

```
{
  key:        string
  value:      bytes
  timestamp:  int64          // sequence number or wall-clock time — used for
                              // newest-wins resolution across SSTables (Section 5)
  deleted:    bool           // tombstone flag — true means "this key was deleted",
                              // kept until compaction removes it permanently
}
```

- **Tombstones:** a `delete(key)` doesn't remove the entry immediately — it writes a new record with `deleted=true`. This is necessary because SSTables are immutable; you can't reach into an old file and erase a key. The tombstone itself gets compacted away (dropped entirely) once compaction confirms no older SSTable still holds a version of that key that needs shadowing.

**Sharding Metadata (Stage 3 only)**

A small config record, held by the router:

```
{
  num_shards:  int
  shard_map: [
    { shard_id: 0, primary_addr: "10.0.1.1:6379", replica_addr: "10.0.1.2:6379" },
    { shard_id: 1, primary_addr: "10.0.2.1:6379", replica_addr: "10.0.2.2:6379" },
    ...
  ]
}
```
- Static — this map only changes during a manual resharding event, not dynamically
- `shard_id = hash(key) % num_shards` determines routing

**Replication Metadata (per node)**

```
{
  role:            "primary" | "replica"
  replication_offset: int64   // how far the replica has replayed the WAL stream —
                               // used to detect lag / confirm sync status
}
```

---

## 8. Key Flows

**Flow 1 — `put(key, value)` (Stage 1-2: single primary + replica)**

1. Client → Load Balancer → routed to **Primary**
2. Primary appends write to its **WAL** (durability point)
3. Primary inserts into its **memtable**
4. Primary streams the write (via WAL replication) to **Replica**
5. **Sync mode:** Primary waits for Replica's ack before responding → Client gets `200 OK`
   **Async mode:** Primary responds `200 OK` immediately, Replica catches up shortly after
6. Replica independently appends to its own WAL + memtable upon receiving the stream

**Flow 2 — `get(key)` (read path)**

1. Client → Load Balancer → routed to **Primary or Replica** (either can serve reads)
2. Node checks its own **memtable** first → if found, return value
3. If not in memtable, check **SSTables newest → oldest**, return first match
4. If no match anywhere (and no tombstone found) → `404 Not Found`

**Flow 3 — `delete(key)`**

1. Client → Load Balancer → routed to **Primary**
2. Primary writes a **tombstone record** (`deleted=true`) — same WAL → memtable path as a normal write
3. Replicated to Replica same as any write
4. Tombstone is physically removed later, during **compaction**, once safe to do so

**Flow 4 — Failover (Primary dies)**

1. Failover Monitor's heartbeat to Primary times out past threshold
2. Monitor (or quorum of monitors) declares Primary down
3. Replica is **promoted** to new Primary
4. Load Balancer / Router config updated to point writes at the new Primary
5. Old Primary, if it recovers, rejoins as a Replica (must catch up via WAL replay/resync, not assumed current)

**Flow 5 — Resharding (Stage 3, manual/offline — naive scaling's known weakness)**

1. Operator decides to change `num_shards` (e.g., 4 → 8) due to growth
2. New shard-primary/replica pairs are provisioned
3. **Data migration job** runs: recomputes `hash(key) % new_num_shards` for every existing key, moves data to its new shard
4. Router's shard map is updated **only after** migration completes
5. Brief write-freeze or dual-write window typically needed during cutover to avoid losing writes mid-migration — worth calling out explicitly as the operational cost naive sharding carries (vs. consistent hashing's incremental rebalance)

---

## 9. Failure Handling

**Primary node crashes**
- Detected via Failover Monitor heartbeat timeout (see Flow 4)
- Replica promoted to new Primary — writes resume there
- Old node, on recovery, rejoins as a replica and must resync (WAL replay from where it left off, or full copy if too far behind)
- **Gap risk (async replication only):** if Primary dies right after acking a write to the client but before that write reached the Replica, that write is lost — this is the explicit durability trade-off of async mode

**Replica node crashes**
- Primary keeps serving writes normally — no impact on write availability
- Read capacity drops (one less node to serve reads from) until replica recovers
- On recovery, replica resyncs via WAL replay/catch-up from the Primary
- If down long enough that required WAL segments were already truncated on the Primary → replica needs a full re-copy of current data, not just a replay

**Both primary and replica down simultaneously**
- Full outage for that shard — no automatic recovery path, this is the accepted risk at this scope (vs. distributed design's N=3 tolerance)
- Mitigation if this SLA isn't acceptable: add a second replica (RF=3), same trade-off curve as the distributed version, just without quorum tuning

**Node crash mid-write (before WAL flush to disk)**
- If the WAL append itself didn't complete/sync to disk, that specific write was never acknowledged to the client → safe, client should retry (this is why `PUT` is designed idempotent)
- If WAL append succeeded but crash happened before memtable update → WAL replay on restart handles it, no loss

**Network partition between Primary and Replica**
- Sync mode: Primary can't get replica ack → writes block or fail until connectivity restored (favors consistency over availability — opposite trade-off from the distributed AP design)
- Async mode: Primary keeps accepting writes, Replica falls behind → risk window widens the longer the partition lasts

**Router/config service down (Stage 3 only)**
- New shard lookups can't resolve → this is a SPOF at this scope, worth flagging explicitly
- Mitigation: cache the shard map client-side with a TTL, or run the config service itself as a small replicated store (acknowledging this becomes a smaller instance of the same problem)

**Split-brain risk (two nodes both think they're Primary)**
- Can happen if failover fires while the "dead" Primary was actually just slow/partitioned, not truly down
- Naive mitigation: fencing token or generation number — each promotion increments a version, old Primary rejects writes once it sees a higher generation number elsewhere
- Worth explicitly naming as a known naive-design gap vs. more robust consensus-based approaches (e.g., Raft-based leader election)

---

## 10. Trade-offs & Alternatives

**Sync vs. Async Replication**
- Sync → stronger durability, higher latency, availability drops if replica is slow
- Async → lower latency, higher availability, small data-loss window on primary failure

**1 Replica vs. Multiple Replicas**
- RF=2: simpler/cheaper, but simultaneous primary+replica failure = outage
- RF=3+: better fault tolerance, but more replication streams and a harder promotion decision

**Static Sharding vs. Consistent Hashing**
- Static (`hash % num_shards`): simple, but resharding = full data migration + cutover window
- Consistent hashing: incremental rebalance, but brings in vnodes/ring/gossip — meaningfully more complexity
- **Biggest scope line vs. the Hard version:** at this scale, static sharding's simplicity outweighs its rebalance cost — resharding is rare and plannable, not constant

**Failover Monitor: single vs. quorum**
- Single monitor: simple, but itself a SPOF for failover decisions
- Quorum of monitors: fewer false-positive failovers, but a mini-consensus problem — a preview of why real systems use Raft/Paxos/ZooKeeper

**LSM-tree vs. hashmap + snapshot**
- LSM-tree: durable, handles data > RAM, production-standard
- Hashmap + snapshot: simpler, but loses everything since the last snapshot on crash — not acceptable given the "durable" requirement

**When Medium stops being enough**
- Resharding becomes frequent, not rare
- Need per-request tunable consistency
- Need to survive failures without manual intervention
- → handoff point into the Hard/Distributed design

---

## 11. Glossary

*(pending)*