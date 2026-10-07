# Design a Messaging System (Kafka)

> Status: Placeholder — needs full write-up. (Notes: draw on work experience with Kafka.)

## 1. Requirements
- Durable, ordered, high-throughput pub/sub messaging.
- Multiple consumer groups reading independently.
- At-least-once (or exactly-once) delivery semantics.
- Horizontal scalability via partitioning.

## 2. Capacity Estimation
- TBD: messages/sec, retention period, partition count, replication factor.

## 3. High-Level Design
- Producers → Topic (partitioned, replicated log) → Brokers (leader/follower per partition) → Consumer groups (offset tracking) → Downstream consumers.
- Zookeeper/KRaft for metadata & leader election.

## 4. Deep Dives
- Partitioning strategy (key-based) and ordering guarantees per partition.
- Replication (ISR - in-sync replicas), leader election, failover.
- Consumer offset management and rebalancing.
- Exactly-once semantics (idempotent producer + transactions).
- Log compaction vs time/size-based retention.

## 5. Trade-offs
- Throughput vs latency (batching, acks=all vs acks=1).
- Partition count vs rebalancing cost and parallelism.
