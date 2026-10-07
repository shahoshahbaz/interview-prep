# Key-Value Store (Medium) — Mock Interview Quick Ref

*One-glance reference for the single-node + naive scaling design only. Full detail lives in `02-Key-Value Store.md`.*

---

## Problem Statement (say this first)
`put(key, value)` / `get(key)` / `delete(key)` — single node → naive horizontal scaling (static sharding + primary-replica). No consistent hashing, no gossip, no vector clocks.

---

## Requirements — say these out loud fast
**Functional:** put / get / delete, opaque value blob, no queries
**Non-functional:** durable, strongly consistent per shard (single writer), simple scale-out via static sharding, basic failover via replica promotion

---

## BOE Estimation — quick shortcuts
- Storage = keys × avg value size × RF (RF=2 typical: primary + 1 replica)
- Check: does it fit on one beefy node? If yes → single node may genuinely suffice
- State the trigger point out loud: "at this scale a single node/shard suffices until X (storage or QPS ceiling)"

---

## High-Level Design — 3 stages
1. **Single Node** — one process handles everything
2. **Primary + Replica** — primary takes writes, replica for reads/failover
3. **Static Sharding** — `hash(key) % num_shards`, each shard = its own primary+replica pair

---

## Component Diagram
`
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

---

## Core Components — rapid recall
| Concept | One-liner |
|---|---|
| WAL | Append-first log; replayed on crash to rebuild memtable |
| Memtable | In-memory sorted structure; every write lands here first |
| SSTable | Immutable disk file from a flushed memtable; multiple accumulate, compaction merges them |
| Newest-wins resolution | Every entry carries a timestamp; reads scan newest→oldest SSTable and return first match; compaction keeps the higher timestamp on conflict |
| Primary-Replica | Single writer (primary); sync=durable/slower, async=faster/small loss window |
| Failover Monitor | Heartbeat on primary; promotes replica after timeout |
| Static Sharding | `hash % num_shards`; resharding = full migration + cutover, not incremental |

---

## API
```
PUT /keys/{key}    { value }   → 200 OK
GET /keys/{key}    → value | 404
DELETE /keys/{key} → 200 OK (tombstone write) | 404
```
No consistency-level param — routing is implicit (primary for writes, primary/replica for reads).

---

## Key Flows — say the sequence, don't over-explain
- **Write:** WAL → memtable → replicate to replica → ack per sync/async mode
- **Read:** memtable → SSTables newest→oldest → first match wins
- **Delete:** tombstone write, same path as put, physically removed at compaction
- **Failover:** heartbeat timeout → promote replica → update router/LB
- **Resharding:** manual — provision new shards → migrate data → update shard map → brief write-freeze/dual-write during cutover

---

## Failure Handling — name these without prompting
- Primary dies → replica promoted; async mode has a small data-loss window
- Replica dies → writes unaffected, reads lose capacity until it resyncs
- Both down → full outage for that shard (accepted risk at RF=2)
- Partition (sync mode) → writes block until connectivity restored (favors consistency over availability)
- Split-brain → fencing token / generation number on promotion
- SPOFs to flag: failover monitor (single instance), router/config service (Stage 3)

---

## Trade-offs — the sentences interviewers want to hear
- "Sync replication trades latency for durability."
- "Static sharding trades rebalance-cost for operational simplicity — fine when resharding is rare and plannable."
- "LSM-trees trade read amplification for write throughput."
- "Single primary means conflict resolution is trivial — timestamp/sequence wins, no vector clocks needed."
- "This design accepts a SPOF at the failover-monitor and router layer — a known, named limitation, not an oversight."

---

## Common Follow-Ups — have an answer ready
- *"What if two writes conflict?"* → Single writer per shard = no concurrent-write ambiguity; newest timestamp wins during compaction/read.
- *"Why not consistent hashing here?"* → Overkill at this scale; static sharding is simpler, and resharding is rare enough to do as a planned migration.
- *"What happens if the primary crashes mid-write?"* → If WAL append didn't complete, write was never acked — client retries safely (PUT is idempotent). If WAL succeeded, replay on restart recovers it.
- *"How do you scale reads?"* → Serve reads from replica(s) too, not just primary.
- *"What's the biggest weakness of this design?"* → Resharding requires a full data migration and cutover window — no incremental rebalance like consistent hashing gives you.