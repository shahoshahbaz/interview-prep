# URL Shortener — Mock Interview Quick Ref

## 1. Requirements
**Func:** shorten, redirect, custom alias, expiration, analytics
**NFR:** 100M/day writes | 99.9% avail | redirect <100ms | 5yr retention | 100:1 read:write

## 2. Capacity (memorize these numbers)
- Write QPS: **200/s** avg → **~600/s** peak (3x)
- Read QPS: **20,000/s** avg → **~60,000/s** peak (3x)
- Total objects (5yr): **30B**
- Storage: 15TB raw → **30-60TB** w/ overhead
- Cache: 20% hit ratio → **~3TB** (6TB w/ replication)
- Short code length: **6 chars Base62** (62⁶ ≈ 56.8B > 30B)

## 3. HLD (draw this)
```
Client → CDN → LB/Gateway → App Layer
                              ├─ Create URL Svc → ID Gen (KGS) ─┐
                              ├─ Redirect Svc → Redis Cache ────┼→ URL Mapping DB
                              └─ Admin Svc → Cache/CDN Invalid ─┘
                                       ↓ (all 3, parallel — NOT serial)
                              Async Queue (Kafka) → Analytics/Abuse
```
**Trigger:** Redirect Svc emits click event to Kafka directly — don't route through DB.

## 4. API + Idempotency
- `POST /urls` (Idempotency-Key header → Redis 24h TTL) → 201
- `GET /{short_url}` → 302
- Admin: PATCH/DELETE (soft delete)

## 5. DB Choice
**NoSQL KV (DynamoDB/Cassandra)** — reason: simple KV lookup, read-heavy, easy shard by short_url

## 6. Create Flow
Validate/normalize (SSRF block, private IP block) → custom alias? → conditional write (IF NOT EXISTS) → 409 on conflict → else KGS assigns code → store → cache

## 7. Short Code Generation — 3 strategies
| Strategy | Verdict | Why |
|---|---|---|
| Hash-based | ✗ | collisions, normalization issues |
| Counter+Base62 | ✗ | predictable/guessable, hotspots |
| **KGS** | ✓ preferred | pre-generated pool, O(1), no runtime collision |
**KGS optimization:** app servers prefetch batches (1000 keys) to cut round-trips

## 8. Redirect: 302 not 301
- 302 = no browser cache by default → analytics survive, links stay mutable
- 301 = cached forever by browser → kills analytics, breaks mutability
- CDN caching done **explicitly** via `Cache-Control: max-age=60`, not implicit 301 caching
- Trade-off: short TTL = some origin traffic still gets through = analytics OK, but deleted link may serve stale for TTL window

## 9. Cache
LRU eviction | replicated | cache-aside (miss → DB → populate → propagate to replicas)

## 10. Partitioning
Hash-based (uniform) → **consistent hashing** (minimize remap on scale) → replicate each partition (leader/follower)

## 11. Load Balancing
Load-aware > round robin: least connections / weighted / EWMA latency

## 12. Ops
- **Purge:** lazy (on access) + batch job by TTL index
- **Analytics:** click → Kafka → Flink/Spark → OLAP (ClickHouse/Druid) — never write counters straight to primary KV (hot-row risk)
- **Security:** visibility enum (public/private/restricted), rate limit + anomaly detection on key enumeration

---
### If stuck, ask yourself:
1. Read or write path? → which tier
2. Why 302? → analytics + mutability
3. Why KGS over hash/counter? → no runtime collision
4. Why async queue? → decouple redirect latency from analytics write, avoid hot-row
5. Why consistent hashing? → minimize remap on partition add/remove