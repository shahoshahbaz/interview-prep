## Data Partitioning - Cheat Sheet

## What Is Data Partitioning?

Dividing a large dataset into smaller independent pieces (partitions), each assigned to a separate node.

## 3 Partitioning Methods

| Method | Split By | Key Idea |
|---|---|---|
| Horizontal | Rows | Different servers hold different records (this is sharding) |
| Vertical | Columns | Same rows, fewer columns per partition |
| Hybrid | Both | Rows and columns split across nodes |

## Sharding = Horizontal Partitioning (6 Techniques)

### 1. Range-Based

- **How:** Key falls in a range and is assigned to a shard (for example, ID `1-25` -> Shard 1)
- **Pro:** Range queries are fast
  - Query like `WHERE date BETWEEN Jan AND Mar` can target only relevant shard(s), not all shards
- **Con:** Hotspots if data is skewed
  - If a few high-traffic users land on one range, that shard gets overloaded

### 2. Hash-Based

- **How:** `hash(key) % N` -> shard number
- **Pro:** Even distribution
- **Con:** Range queries break, and resharding is costly

### 3. Directory-Based

- **How:** Lookup table maps each key to a shard explicitly
- **Pro:** Easy resharding, most flexible
- **Con:** Lookup overhead; directory can be a single point of failure (SPOF)

### 4. Geographical

- **How:** Shard location matches user region
- **Pro:** Low latency, compliance-friendly
- **Con:** Complex when users move across regions

### 5. Dynamic

- **How:** Shards split/merge automatically based on load
- **Pro:** Auto-balances
- **Con:** Operationally complex

### 6. Hybrid Sharding

- **How:** Layer multiple strategies (for example, range -> directory)
- **Pro:** Best of each technique
- **Con:** Hardest to reason about

## Common Problems

| Problem | What It Means | Fix |
|---|---|---|
| Data skew | Bad key causes one shard to become a hotspot | Pick high-cardinality keys |
| Cross-partition queries | Fan out to all shards, then aggregate | Design key to avoid cross-shard scans |
| Data migration | Changing scheme requires moving data | Plan the key before launch |
| Key selection | Wrong key causes most other problems | Match query patterns and distribution goals |
| Backup complexity | Coordinated snapshots needed across shards | Use a consistent snapshot strategy |
| Operational overhead | Monitoring and ops effort grows with shard count | Invest in tooling |

## Interview Quick Picks

| If You Need... | Use |
|---|---|
| Range queries | Range-based |
| Even distribution | Hash-based |
| Easy resharding | Directory-based |
| Global users + low latency | Geographical |
| Reduce scanned columns | Vertical partitioning |
| Auto-scaling workload | Dynamic |

## One-Liner to Remember

Sharding is horizontal partitioning. The six techniques are different answers to the same question: "How do I decide which shard a row belongs to?"
