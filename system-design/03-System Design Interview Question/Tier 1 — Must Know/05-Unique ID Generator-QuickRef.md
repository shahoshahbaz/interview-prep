# Unique ID Generator — Mock Interview Drill Sheet
*Use this to self-quiz or run a mock interview. Prompts on the left, key points you must hit on the right. Cover the right side, answer out loud, then check.*

---

## Table of Contents
1. [Opening / Requirements Gathering](#1-opening--requirements-gathering)
2. [Estimation](#2-estimation)
3. [High-Level Design](#3-high-level-design)
4. [Deep-Dive Probes](#4-deep-dive-probes)
5. [API Design](#5-api-design)
6. [Data Model](#6-data-model)
7. [Walk Me Through a Request](#7-walk-me-through-a-request)
8. [Failure Scenarios (interviewer will push here)](#8-failure-scenarios-interviewer-will-push-here)
9. [Trade-off Questions](#9-trade-off-questions)
10. [Curveballs](#10-curveballs)

---

## 1. Opening / Requirements Gathering

**Prompt:** *"Design a unique ID generator for a distributed system."*

**You should ask/state:**
- Scale? (throughput, number of nodes/datacenters)
- Does ID need to be time-sortable, or just unique?
- ID format constraint — numeric (64-bit) or string acceptable?
- Multi-datacenter?

**You should land on:**
- Uniqueness + rough time-ordering + high availability + low latency + no hot-path coordination + fits in 64 bits

---

## 2. Estimation

**Prompt:** *"What scale are we talking, and does 64 bits suffice?"*

**You should be able to derive live:**
- 2^64 ≈ 1.8×10^19 — essentially unlimited, not the real constraint
- Given 50K IDs/sec peak, show total IDs over N years is negligible vs. total space
- State the bit split from memory: **1 sign + 41 timestamp + 10 machine_id + 12 sequence**
- Be ready to derive each piece:
   - 2^41 ms ÷ 1000 ÷ (seconds/year) ≈ 69 years
   - 2^10 = 1,024 nodes
   - 2^12 = 4,096 IDs/ms/node → ×1000 = ~4.1M IDs/sec/node

**Trap:** don't just recite the split — be ready to justify *why* those specific bit counts (tied to your stated scale requirements).

---

## 3. High-Level Design

**Prompt:** *"Sketch the architecture."*

**Must draw/say:**
- Client → LB → pool of stateless, active-active ID generator nodes
- Each node generates independently — no inter-node calls on the hot path
- Coordinator (Zookeeper/etcd) exists only for machine_id assignment, at startup only

**Say this line explicitly:** *"The key design decision is keeping all coordination off the hot path — machine_id is assigned once at startup and cached in memory, so per-request generation never talks to another service."*

---

## 4. Deep-Dive Probes

**Q: How do you assign machine_id?**
- Zookeeper/etcd ephemeral sequential znode, or simpler: Kubernetes StatefulSet pod ordinal
- Ephemeral znode auto-deletes on crash → machine_id reclaimed

**Q: How do you handle clock issues?**
- Local clock per node (no central time service)
- If `current_ms < last_timestamp` → reject (503) or busy-wait — never generate
- NTP should slew, not step, to minimize backward jumps

**Q: How does the sequence counter work?**
- In-memory, resets to 0 each new ms
- Must be atomic (thread-safe) if node is multi-threaded
- Overflow (>4096/ms) → busy-wait for next tick

**Q: Show me the bit-packing.**
```
id = (timestamp << 22) | (machine_id << 12) | sequence
```

---

## 5. API Design

**Prompt:** *"What's the API surface?"*

- `GET /v1/id/generate` — no body, no auth needed (not a mutating op)
- Optional `?count=N` for batch generation, capped (e.g. 1000)
- 503 on clock violation, 400 on invalid batch size
- **Be ready to justify:** why no idempotency key needed (every call is naturally safe/stateless — nothing to replay)

---

## 6. Data Model

**Prompt:** *"What's your schema?"*

**The trap:** there isn't one — say so confidently, don't invent a fake table.
- No DB. State lives only in-memory per node: `machine_id`, `sequence`, `last_timestamp`
- Only "data" anywhere in the system is the ephemeral znode registry in the coordinator (if used) — and that has no payload, just existence = claimed

---

## 7. Walk Me Through a Request

**Prompt:** *"Trace a single request end to end."*

Practice saying this out loud in under 60 seconds:
1. Client hits LB → routed to any node
2. Node reads local clock
3. Compares to last_timestamp — if backward, reject
4. Same ms as last request? increment sequence : reset sequence to 0
5. Bit-pack (timestamp, machine_id, sequence) → return
6. Update last_timestamp

Then also practice the **same-ms burst case**: multiple requests in one ms on the same node → same timestamp, incrementing sequence.

---

## 8. Failure Scenarios (interviewer will push here)

Drill each of these as a rapid-fire Q&A — cover the answer column and answer out loud:

| Interviewer asks | Your answer |
|---|---|
| "What if a node crashes?" | LB health check routes around it; stateless, so no data loss; machine_id reclaimed via znode expiry |
| "What if Zookeeper/etcd goes down?" | Running nodes unaffected — machine_id already cached in memory. Only blocks *new* node startup/scale-up |
| "What if the clock jumps backward?" | Node detects `current_ms < last_timestamp`, refuses to generate (503 or busy-wait) — prevents duplicate IDs |
| "What if a node generates >4096 IDs in one ms?" | Busy-wait until next ms tick; rare given 4.1M/sec capacity vs. target load |
| "What if two datacenters both assign machine_id=5?" | Partition the machine_id space by region up front so this can't happen independently |
| "What happens to in-flight requests when a node dies?" | LB retries against a healthy node; request is naturally idempotent/stateless, safe to retry anywhere |

---

## 9. Trade-off Questions

**Q: Why not just use UUID v4?**
- No coordination needed, but: 128 bits (2x storage), not time-sortable, bad B-tree index locality from random inserts

**Q: Why not a DB auto-increment counter?**
- Simple and strictly ordered, but: single point of failure, doesn't scale horizontally, becomes a write bottleneck

**Q: Why not a range-handout scheme (DB gives each node a block of 1000 IDs)?**
- Fewer DB calls than pure auto-increment, but: still has a DB dependency, no cross-node time ordering, gaps on node crash mid-block

**Q: What do you give up with this design?**
- Strict global ordering (only ordered within a node)
- Predictability/unguessability (structure is reverse-engineerable)
- Correctness depends on clock behavior — real operational surface area

---

## 10. Curveballs

Be ready for these — they test whether you understand the design or just memorized it:

- *"How would you get strict global ordering instead of rough ordering?"* → route through a single leader/partition; name the throughput/availability trade-off explicitly (PACELC-style reasoning)
- *"Client wants string IDs, not longs — does anything change?"* → no, it's just a formatting/serialization choice at the API boundary, the underlying generation logic is unchanged
- *"How do you migrate from 1,024 nodes to more?"* → machine_id is only 10 bits; you'd need to either shrink another field (fewer years of timestamp range, or lower per-ms throughput) or accept a breaking ID-format change
- *"Can IDs be exposed to end users (e.g., in a URL)?"* → flag the predictability trade-off from Section 9; if that's disqualifying, note UUID or a hashed/obfuscated wrapper as an alternative
- *"What if you need this to work offline / at the edge with unreliable connectivity to the coordinator?"* → reinforce that this is already true by design — coordinator is startup-only, nodes are fully autonomous once running