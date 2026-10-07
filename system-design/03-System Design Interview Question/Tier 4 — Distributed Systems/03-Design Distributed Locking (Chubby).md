# Design Distributed Locking (Chubby)

> Status: Placeholder — needs full write-up.

## 1. Requirements
- Provide mutual exclusion across distributed processes/nodes.
- Locks must be fault-tolerant (survive node failures) and consistent.
- Support lock leases/TTL to avoid deadlocks from crashed holders.

## 2. Capacity Estimation
- TBD: lock acquisition QPS, number of distinct lockable resources, lease duration.

## 3. High-Level Design
- Consensus-based lock service (Paxos/Raft, e.g., Chubby/Zookeeper/etcd).
- Clients acquire leases/sessions; locks tied to session with heartbeats/keep-alives.
- Lock release on session expiry (client crash) or explicit unlock.

## 4. Deep Dives
- Consensus protocol basics (Paxos/Raft) for replicated lock state.
- Session/lease management and handling network partitions (split-brain avoidance).
- Fencing tokens to prevent stale-lock-holder issues.
- Comparison: Chubby vs Zookeeper vs etcd vs Redis (Redlock) locking approaches.

## 5. Trade-offs
- Strong consistency (consensus-based) vs simpler but weaker guarantees (Redis-based locks).
- Lease duration: too short causes false expiry, too long delays recovery.
