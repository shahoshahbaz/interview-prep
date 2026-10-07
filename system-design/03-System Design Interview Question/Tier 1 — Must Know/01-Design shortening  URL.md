# Designing a URL Shortening Service like TinyURL


## Overview

A URL shortening service like TinyURL converts long URLs into shorter, more manageable links. This system design covers the key components, algorithms, and infrastructure needed to build a scalable URL shortening service that can handle millions of requests per day.

## Table of Contents

- [Overview](#overview)
- [1. Problem Statement](#1-problem-statement)
- [2. System Requirements](#2-system-requirements)
- [3. Capacity Estimation and Constraints](#3-capacity-estimation-and-constraints)
- [4. High-Level Architecture](#4-high-level-architecture)
- [5. API Design](#5-api-design)
- [6. Database Design](#6-database-design)
- [7. URL Short Code Generation](#7-url-short-code-generation)
- [8. Redirect Flow](#8-redirect-flow)
- [9. Cache](#9-cache)
- [10. Data Partitioning and Replication](#10-data-partitioning-and-replication)
- [11. Load Balancer](#11-load-balancer)
- [12. Operational and Advanced Considerations](#12-operational-and-advanced-considerations)

## 1. Problem Statement

Design a URL shortening service that:
- Shortens long URLs to short URLs
- Redirects short URLs to original URLs
- Supports custom aliases
- Handles expiration of URLs
- Provides analytics on URL usage

---
## 2. System Requirements

### Functional Requirements
- **URL Shortening**: Convert long URLs to short URLs
- **URL Redirection**: Redirect short URLs to original URLs
- **Custom Aliases**: Allow users to create custom short URLs
- **Expiration**: URLs can have expiration dates
- **Analytics**: Track click statistics

### Non-Functional Requirements
- **Scale**: Handle 100M URLs shortened per day
- **Availability**: 99.9% uptime
- **Latency**: Redirection < 100ms
- **Storage**: Store URLs for 5+ years
- **Read Heavy**: 100:1 read to write ratio

---

## 3. Capacity Estimation and Constraints

### Assumptions
- 500 million new URL shortenings per month
- Ratio between read and write is 100:1
- Retention period is 5 years
- Each URL is 500 bytes on average
- Cache hit ratio is 20%

### Traffic Estimation

**Write QPS:**
- 500 million / (30 * 10^5) = 166.7 new URLs per second → round up to **200 new URLs/s**

**Read QPS:**
- 100 * write QPS = 100 * 200 = **20,000 redirects/s**

**Peak QPS (provision for this, not the average):**
- Real traffic isn't flat — apply a 2-3x peak factor for launches, viral links, and daily/regional traffic curves.
- Peak write QPS: 200 * 3 = **~600 writes/s**
- Peak read QPS: 20,000 * 3 = **~60,000 redirects/s**
- Interviewer expects capacity numbers (cache, DB connections, app server count) sized against peak, not average — always state which one you're using.

**Bandwidth:**
- Inbound (write) = 200 * 500 = 100KB/s = **0.8 Mbps**
- Outbound (read) = 20,000 * 500 = 10MB/s = **80 Mbps**

### Storage Estimation

**Monthly Storage:**
- 500 million * 500 bytes = **250 GB**

**Yearly Storage:**
- 250 GB * 12 = **3 TB**

**5-Year Storage:**
- 3 TB * 5 = **15 TB**
- With overhead (2-4x): **30-60 TB**

### Memory Estimation

**Total Objects for 5 Years:**
- 500 million * 12 months * 5 years = **30 Billion URLs**

**Cache Size:**
- (30B * 0.2 cache hit ratio) * 500 bytes = **3TB cache** (approximately 6TB with replication)

---

## 4. High-Level Architecture


                                    Client / Browser
                                          ↓
                                         CDN
                                          ↓
                                    Load Balancer / API Gateway
                                          ↓
                                    Application Layer
            ┌────────────────────┬────────────────────┬────────────────────┐
            │ Create URL Service │ Redirect Service   │ Admin Service      │
            └────────────────────┴────────────────────┴────────────────────┘
                     ↓                   ↓                     ↓
                ID Generator         Redis Cache           Cache/CDN Invalidation
                     ↓                  ↓                      ↓
                     └─────── URL Mapping Database ─────────────┘
                                        ↓
                            Async Queue / Event Stream
                                       ↓
                            Analytics / Abuse Detection

Flow:
1. Client sends request to shorten URL → Load Balancer
2. CDN: cache static content and reduce latency
3. Load balancer send request to API Gateway
4. if request is for shortening URL → Create URL Service → ID Generator → URL Mapping Database → Redis Cache
5. if request is for redirection → Redirect Service → Redis Cache → URL Mapping Database (if cache miss) → 302 Redirect to original URL
6. if request is for admin operations → Admin Service → URL Mapping Database + Cache Invalidation
7. Admin operations (e.g., update, delete) → Admin Service → URL Mapping Database + Cache Invalidation
8. Analytics events (e.g., clicks) → Async Queue → Analytics Service


---

## 5. API Design
### Core APIs
- **POST /api/v1/urls** → Create URL shortening
- **GET /api/v1/urls/{short_url}** → Redirect to original URL

### Idempotency (POST /api/v1/urls)
- Client sends an `Idempotency-Key` header (client-generated UUID) with the create request.
- Server checks a fast-lookup store (Redis, TTL'd ~24h) for that key before creating anything:
    - **Key seen, request completed** → return the original `201` response (same short_url), no new mapping created.
    - **Key seen, request in-flight** → return `409 Conflict` / `429` to avoid duplicate concurrent processing.
    - **Key not seen** → proceed to create, then store `key → response` in the idempotency store.
- Without this, a client-side retry on a network timeout (common at 20K+ QPS scale) silently creates duplicate short codes for the same long URL — same Redis + Postgres/DynamoDB pairing used for idempotency in payment-write paths applies here.

### Admin APIs
- **GET /api/v1/urls/{short_url}** → Get URL details
- **PATCH /api/v1/urls/{short_url}** → Update URL details (e.g., expiration, custom alias)
- **DELETE /api/v1/urls/{short_url}** → Delete URL (soft delete)

### Analytics APIs
- **GET /api/v1/urls/{short_url}/stats** → Get statistics for a shortened URL
- **GET /api/v1/users/{userId}/urls** → Get all URLs created by a user

### Internal APIs / Interfaces
- **NextId()** → Generate next unique short code
- **GetMapping(code)** → Retrieve original URL and metadata
- **Invalidate(code)** → Invalidate cache entry for code

---
## 6. Database Design

### Data Entities

**URL_Mapping Table:**
- Stores the mapping between short code and original URL with metadata
- Fields: `(short_url, original_url, user_id, created_at, expiration_at, click_count, status)`

**User Table:**
- Stores user information and quota management
- Fields: `(user_id, email, plan_tier, created_at, quota_limits)`

### Database Technology Choice

**URL_Mapping:**
- **Type:** NoSQL KV / Wide-column DB (Cassandra / DynamoDB)
- **Reasoning:** Massive scale, read-heavy workload, simple key-value lookups, easy horizontal sharding by short_url

**User:**
- **Type:** NoSQL (or SQL if user/account logic becomes complex)
- **Reasoning:** Flexible schema, scalability

---

## 7. URL Short Code Generation

### 7.1 Create URL Flow

```
Client Request: POST /urls {long_url, custom_alias?, expiration?}
                   ↓
        Validate & Normalize URL (§7.2)
                   ↓
        Custom alias provided?
           YES → Check availability (§7.3)
                   → taken     → 409 Conflict
                   → available → reserve alias, store mapping
           NO  → Generate short code (§7.4) → store mapping
                   ↓
        Populate cache (write-through, optional)
                   ↓
        Return 201 Created + short_url
```

### 7.2 URL Validation & Normalization
- **Validation:** reject malformed URLs, enforce a max length, block known malicious/phishing domains (blocklist or third-party threat-intel API), and reject internal/private IP ranges and localhost (SSRF protection — a shortener that happily redirects to `169.254.169.254` or an internal hostname is a real attack vector, not just a nice-to-have).
- **Normalization:** lowercase scheme/host, strip default ports, apply a consistent policy for `http` vs `https` and `www` vs non-`www`. This matters even beyond correctness — it's what keeps hash-based dedup (§7.4) from fragmenting into multiple short codes for what's really the same destination.

### 7.3 Custom Alias Handling
- If `custom_alias` is provided, check availability with a **conditional write** (`INSERT ... IF NOT EXISTS`, or a DynamoDB `PutItem` with a condition expression) rather than read-then-write — a plain check-then-insert has a race window where two users can claim the same alias concurrently.
- On conflict → **409 Conflict**; client retries with a different alias.
- Enforce a minimum length (e.g., 4+ characters) on custom aliases to keep their collision surface separate from the auto-generated KGS pool (§7.4).

### 7.4 Short Code Generation Strategies

#### 1. Hash-Based Generation

- How it works:
    - Generate short code by:     hash(url) → encode → short code

- Problems:
- collisions: different URLs can produce the same hash → need collision resolution
- same URL → same url: same URL will always generate the same short url, which may not be desirable
- normalization complexity: different URLs that point to the same resource (e.g., with/without www, http vs https) may generate different url → need URL normalization logic
---
#### 2. Counter + Base62 Encoding
- How it works:
    - Generate unique increasing counter:  Maintain a global counter that increments for each request    - Convert counter to Base 62 String
    -  Store mapping in database: short_url → original_url
    - Benefits:
        - simple
        - fast
        - no collisions (counter guarantees uniqueness)
    - drawbacks:
        - predictability: short codes are sequential and can be easily guessed → security concern for sensitive URLs
        - hot keys: popular URLs may generate many short codes in a short time, leading to hotspots in the database
    - Maintain a global counter that increments for each new URL
    - Encode the counter value in Base62 (0-9, a-z, A-Z) to create short code

#### 3. Key Generation Service (KGS) ⭐ **PREFERRED**
- How:
    - Pre-generate unique short codes offline and distribute them to app servers.
- Benefits:
    - fast
    - collision-free
    - scalable

- How It Works:

1. **KGS** generates billions of short keys (e.g., using sequential Base62 encoding)
2. **Stores keys** in two states:
    - `unused_keys`: Available for assignment
    - `used_keys`: Already assigned to URLs
3. **On URL shortening request:**
    - App server fetches an available key from the pool
    - Moves key from `unused_keys` → `used_keys`
    - Maps shorten url to original url in database
4. **Avoids collision checks** at request time (pre-generated uniqueness guaranteed)

- Benefits:
    - Fast key assignment (O(1) lookup)
    - No collision retry logic needed
    - Simple app servers (stateless, no complex hashing or collision handling)
    - No runtime collisions (all keys pre-validated)
    - Horizontal scalability (app servers just consume from key pool)


- drawbacks:
    - operational complexity (managing KGS, ensuring high availability, handling key exhaustion)
    - Single point of failure (if KGS goes down, no new keys can be issued)
    - Wasted keys on crash (keys in-flight or cached by crashed servers are lost;
    - Synchronization overhead (requires locking/coordination between KGS and app servers; distributed systems complexity)


- Optimization: Distributed Key Caching
    - **Problem:** Requesting keys one-by-one creates bottleneck at KGS.

    - **Solution:**
        - **KGS caches keys in memory** for rapid distribution
        - **App servers prefetch batches** of keys (e.g., 1000 keys at a time)
        - **Each app server maintains local pool** to reduce KGS round-trips
        - **On depletion**, refetch next batch from KGS

    - **Benefits:**
        - Reduces KGS load significantly
        - Minimizes latency on key assignment
        - Handles short-term traffic spikes locally

### 7.5 Short Code Length Sizing
- From Capacity Estimation (§3): ~30 billion total short codes needed to cover the 5-year retention window.
- Base62 alphabet (`0-9`, `a-z`, `A-Z`) → 62 symbols per character.
- 62⁵ ≈ 916 million → **too small**.
- 62⁶ ≈ **56.8 billion** → covers 30B with headroom. **Decision: 6-character short codes.**
- Follow-up an interviewer may ask: *"what happens as you approach exhaustion?"* → extend new generation to 7 characters (62⁷ ≈ 3.5 trillion); existing 6-character codes stay valid since length is a generation-policy choice, not a fixed schema constraint.

---

## 8. Redirect Flow

### 8.1 Flow

```
        Client Request: GET /{short_url}
                   ↓
        lookup short_url in Cache
                   ↓
        Lookup short_url in Database (if cache miss)
                   ↓
        Found? → YES → Redirect to original_url
                   ↓
       NO → 404 Not Found (expired, deleted, or invalid)
```

### 8.2 301 vs 302 — Redirect Type Decision
- **302 (Found / temporary) — chosen.** Not cached by browsers by default, so every click round-trips through Redirect Service. This preserves accurate click analytics and lets links stay mutable (update destination, expire, delete) without stale browser caches getting in the way.
- **301 (Moved Permanently) — rejected for this use case.** Browsers cache it aggressively and indefinitely, even with no `Cache-Control` header. Great for reducing origin load, but you lose click visibility entirely once a browser has it cached, and a changed/deleted link stays "alive" on any client that already cached the 301.
- **Explicit CDN caching instead of implicit 301 caching:** set `Cache-Control: public, max-age=60` (or similar short TTL) on the 302 response. This lets CDN/edge absorb repeated hits on hot/viral links without fully sacrificing analytics — origin still sees most traffic, just not literally every click.
- **Trade-off to say out loud:** short-TTL caching means a deleted/abusive link can still serve from edge cache for up to the TTL window after deletion — purge-on-write (§Cache/CDN Invalidation) narrows this but doesn't eliminate it, since edge purge propagation isn't instant.

---

## 9. Cache

- **Purpose** :Store frequently accessed shortURL → longURL mappings in memory. Application servers check cache before hitting the database.
- **How It Works:**``` Client → App Server → Cache → (miss) → Database```
- **Eviction Policy:** Use **LRU (Least Recently Used)**.
- **Cache Replication**
    - Use multiple cache replicas to distribute load.
    - Improves scalability and availability.

- **Cache Update Strategy**
    - On cache miss:
        1. Read URL from database.
        2. Store it in cache.
        3. Propagate the new entry to other cache replicas.
        4. If a replica already has the entry → ignore.

## 10. Data Partitioning and Replication

- **Goal:** Scale storage to billions of URL mappings by distributing data predictably and evenly across multiple database servers.

### Partitioning Strategies
- **Range-Based (by leading character):** Store keys starting with A/a in one partition, B/b in another, etc.
    - Pros: Simple, human-predictable placement.
    - Cons: Skewed load (letter frequency uneven), manual rebalancing when hotspots emerge (e.g., many keys starting with 'e').
- **Hash-Based (hash(short_key) → partition id):** Apply a hash that maps each key to a bucket (e.g., 1..256).
    - Pros: More uniform distribution, constant-time routing.
    - Cons: Still possible hotspots (very popular keys) and expensive re-sharding when node count changes (many keys remapped).

- Mitigating Rebalancing Pain by Using **Consistent Hashing**:
    - So adding/removing partitions only remaps a small slice of keys instead of the entire dataset, smoothing horizontal scaling.

-  **Replication (Implied Consideration)**
- each partition would typically be replicated (e.g., leader + followers or quorum-based) for high availability and fast recovery.

- **Summary**
- Start with hash-based partitioning for even spread.
- Plan for growth with consistent hashing to minimize data movement.
- Layer replication for durability and availability.

## 11. Load Balancer

- **Purpose:** Distribute traffic and improve availability across all tiers in the system.

-  **Where to Place Load Balancers:**
- **Client → App Servers**: Route client requests evenly across app servers
- **App Servers → Cache**: Distribute cache requests across cache replicas
- **App Servers → Database**: Distribute database requests across database replicas

- Basic Strategy: Round Robin
    - **How it works**: Distribute requests evenly in a circular fashion across all servers
    - **Advantage**: Simple to implement; automatically removes dead nodes
    - **Limitation**: Doesn't consider per-server load or latency

-  Better Option: Load-Aware Load Balancing
- **How it works**: Routes traffic based on server health and actual load metrics
    - **Strategies**:
        - Weighted Round Robin: Assign different weights to servers based on capacity
        - Least Connections: Route to the server with the fewest active connections
        - EWMA Latency: Use exponential weighted moving average of response times
    - **Advantage**: More efficient utilization of server resources
    - **Benefit**: Slower or overloaded nodes receive less traffic

## 12. Operational and Advanced Considerations

### 12.1 Purging / DB Cleanup
Reclaim storage and enforce expiration without overloading DB.
- Expiration Handling: Lazy deletion—on access, if expired → delete + return error (404/410);
- Batch Cleanup: Lightweight periodic job scanning by TTL index/bucket to remove expired + evict from cache.
- Default TTL: e.g., 2 years; keys optionally recyclable back into key pool after purge (if uniqueness window allows).
- Cold Data: Generally retained indefinitely (storage cheap) unless explicit retention policy; avoid scanning full dataset continuously.

### 12.2 Telemetry / Analytics
Track usage without hot-row contention.
- Metrics: Total clicks, unique users/IPs, country, referrer, user-agent/device, timestamp buckets.
- Write Path Pattern: Append-only event (log/stream) → aggregation pipeline (Kafka → Flink/Spark → OLAP store) to avoid per-click counter hotspots.
- Serving Layer: Pre-aggregated counters cached; heavy analytics served from OLAP (e.g., ClickHouse / Druid / BigQuery) not primary KV store.
- Sampling / Rate Control: Optional for extremely hot links to cap write amplification.

### 12.3 Security & Permissions
Support private or restricted URLs.
- Model: visibility enum (public | private | restricted) stored with mapping; restricted list of authorized user IDs.
- Access Check: On redirect, fetch metadata (possibly from cache) and enforce authN/authZ (401 unauthorized or 403 forbidden on failure).
- Storage Pattern: For wide-column NoSQL: partition by short_key; secondary table or collection mapping short_key → authorized_user_ids (bounded list) or store as set attribute if size small.
- Abuse Mitigation: Rate limits (IP + user), anomaly detection for brute-force key enumeration, optional HMAC-signed custom aliases.

### 12.4 Summary
- **Validation & Aliases**: Normalize + validate (SSRF/blocklist checks) before generation; custom aliases claimed via conditional write to avoid race conditions
- **Idempotency**: `Idempotency-Key` header on create, backed by Redis, to survive client retries without duplicate mappings
- **Key Generation**: KGS with pre-generated key pool; batch distribution to app servers; no runtime collisions; 6-character Base62 codes sized against 30B 5-year capacity
- **Redirects**: 302 (not 301) to preserve analytics and mutability; short-TTL `Cache-Control` lets CDN absorb hot links without fully losing origin visibility
- **Caching**: LRU cache for hot short_url → original_url; distributed replicas; batch updates
- **Partitioning**: Hash-based with consistent hashing for smooth scaling; replication for durability
- **Load Balancing**: Load-aware strategy (e.g., least connections) across all tiers
- **Purging**: Lazy + batch cleanup; default TTL; optional key recycling
- **Analytics**: Append-only event logging → batch aggregation; pre-aggregated counters for hot links
- **Security**: Visibility model + auth checks; secondary access control data structure; abuse mitigation via rate limits and anomaly detection.