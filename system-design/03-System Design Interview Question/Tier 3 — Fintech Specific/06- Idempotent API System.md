# Design Idempotent API System

## Table of Contents

1. [Problem Statement](#1-problem-statement)
2. [Requirements](#2-requirements)
3. [Assumptions & Capacity Estimation (BOE)](#3-assumptions--capacity-estimation-boe)
4. [High-Level Architecture](#4-high-level-architecture)
5. [API Design](#5-api-design)
6. [Data Model / Database Schema](#6-data-model--database-schema)
7. [Idempotency Key Design](#7-idempotency-key-design)
8. [Request Flow (Happy Path)](#8-request-flow-happy-path)
9. [Retry Flow / Duplicate Request Handling](#9-retry-flow--duplicate-request-handling)
10. [Idempotency Store Design](#10-idempotency-store-design)
11. [State Machine (PROCESSING / SUCCESS / FAILED / EXPIRED)](#11-state-machine-processing--success--failed--expired)
12. [Concurrency & Race Conditions](#12-concurrency--race-conditions)
13. [Atomicity & Transaction Boundaries](#13-atomicity--transaction-boundaries)
14. [Consistency Guarantees](#14-consistency-guarantees)
15. [Exactly-once vs At-least-once Behavior](#15-exactly-once-vs-at-least-once-behavior)
16. [Distributed Locking (if needed)](#16-distributed-locking-if-needed)
17. [Cache Usage](#17-cache-usage)
18. [Expiration / TTL Strategy](#18-expiration--ttl-strategy)
19. [Failure Handling](#19-failure-handling)
20. [Reconciliation / Recovery Jobs](#20-reconciliation--recovery-jobs)
21. [Scalability](#21-scalability)
22. [Database Sharding Strategy](#22-database-sharding-strategy)
23. [Security Considerations](#23-security-considerations)
24. [Monitoring & Observability](#24-monitoring--observability)
25. [Trade-offs & Alternatives](#25-trade-offs--alternatives)
26. [Edge Cases](#26-edge-cases)
27. [Interview Wrap-up / Summary Story](#27-interview-wrap-up--summary-story)

---

## 1. Problem Statement

-  Design an API system that:
  -  Prevent duplicate processing of client requests
  - Handle retries caused by:
    - network timeout
    - client retry
    - server timeout
    - unknown response state
## 2. Requirements

### Functional Requirements:
- Accept idempotency key in request headers
- Detect and deduplicate requests with same idempotency key
- Return consistent response for duplicate requests
- Support multiple API endpoints (payments, transfers, etc.)



### ⚙️Non-Functional Requirements
 
- **Availability:** 99.99% uptime
- **Latency:** Response time < 200ms (including idempotency check)
- **Throughput:** Handle 10,000 requests/sec
- **Storage:** Store idempotency records for 24 hours
- **Consistency:** Guarantee exactly-once semantics

---
## 3. Assumptions & Capacity Estimation (BOE)

Assumptions:
  - 10M API requests/day
  - 20% are idempotent write requests
  - Average request body = 2 KB
  - Average stored response = 4 KB
  - Idempotency records kept for 24 hours
  - Peak traffic = 5× average traffic
### Calculations:
- Write Requests:  2M idempotent requests/day
- Ave QPS = 2M / 86,400 sec ≈ 23 req/sec
- Peak QPS = 5 × 23 ≈ 115 req/sec
- daily storage = 2M × (2 KB + 4 KB + 1 KB(extra metadata) ) = 14 GB/day
  
---
## 4. High-Level Architecture

          Client
            |
            ↓ POST /payments,  Idempotency-Key: abc-123
            ↓
         API Gateway
            ↓
      ┌─────────────────────┐
      │ Idempotency Service │
      │ (Cache/DB Lookup)   │
      └─────────────────────┘
                ↓
      Found in Idempotency Store?
      ├─ YES → Return Cached Response (304)
      └─ NO  → Process Request
            ↓ 
          Business Service
            ↓
          Database
            ↓
          Response stored in Idempotency Store
- Flow: Main Components
  - Client:   Sends request with Idempotency-Key
  - API Gateway:   Auth, rate limit, routing
  - Idempotency Service :
    -  Checks if request was already processed
    -  Blocks duplicate execution
    - Returns cached/stored response
  - Business Service:   Executes actual operation once
  - Primary Database
  Stores real business data
---
## 5. API Design

Core APIs:
- POST **/payments:** Create payment safely using idempotency key
- POST **/transfers:** Create money transfer without duplicates
- POST **/orders:** Create order exactly once
- POST **/refunds:** Prevent duplicate refunds

---
## 6. Data Model / Database Schema

- **Idempotency Store Table**: this table store the idempotency records.
  ```(id(PK),  merchant_id,  idempotency_key,  endpoint,  request_hash,  request_body,  response_body,  status,  http_status_code,  error_message,  created_at,  updated_at, expires_at)```
- Important Design Choice:
    - UNIQUE(merchant_id, idempotency_key)
    - why? prevent duplicate insert
    - Index(merchant_id, idempotency_key) for fast lookup
---
## 7. Idempotency Key Design
### what is the key: a unique value sent by the client for one logical operation
### Key Rules:
- Same operation → same key
- New operation → new key
- Key should be unique per user/merchant
- Key should expire after TTL, usually 24 hours
- Key should be stored with **request hash**
- Client MUST generate and send Idempotency-Key header
- Server validates format and length; rejects if invalid
- length: 32-128 characters (enough for UUID or hash)
- Uniqueness scope: Per user + endpoint (e.g., user_id + endpoint + key)
- Generation: Client-side (recommended) or server-side fallback (if client doesn't provide key, server can generate one, but this limits idempotency benefits)
- Use distributed hash (Redis/Memcache) for fast lookup

---
## 8. Request Flow (Happy Path)

### why is called Happy Path?
because this is the flow when everything goes well, no retries, no failures, no duplicates.

      Client
        |
        | POST /payments
        | Idempotency-Key: abc-123
        v
      API Gateway
        |
        v
       Idempotency Service
        |
        |-- Check Redis
        |-- Check SQL if cache miss
        |
        |-- Key not found
        v
      Create idempotency record (status = PROCESSING)
        |
        v
       Business Service
        |
        v
      Payment DB updated
        |
        v
      Store final response (status = SUCCESS)
        |
        v
        Return response to client

###  Flow:
  1. Client sends request with Idempotency-Key header
  2. server receives request
  3. Server checks Idempotency Store (first cache, then DB)
  4. If key not found → proceed with business logic
     - Create idempotency record with status = PROCESSING
  5. Execute operation (e.g., debit account, credit account)
  6. Store request + response in Idempotency Store with status = SUCCESS
  7. Return response to client
## 9. Retry Flow / Duplicate Request Handling

          Client retries
            |
            | POST /payments
            | Idempotency-Key: abc-123
            v
          API Gateway
            |
            v
          Idempotency Service
            |
            |-- Check Redis
            |-- Check SQL if cache miss
            |
            |-- Key found
            v
           Check request_hash(why? to detect same/different payload)
            |
            |-- Same payload?
            |      |
            |      v
            |   Return stored response
            |
            |-- Different payload?
            |
            v
          Return 409 Conflict
            


-Flow:
1. Client retries with same Idempotency-Key
2. Server checks Idempotency Store
3. Key found → check if request body hash matches stored hash
4. If hash matches → return cached response (status = SUCCESS or FAILED)
5. If hash doesn't match → return 409 Conflict (indicates client error: same key used for different request)
6. Client can generate new key and retry with correct payload

---
## 10. Idempotency Store Design
### **Important Design Choice**:
  - Use a scoped unique key: ```merchant_id + idempotency_key```
  - Not just: idempotency_key
  - Why?
    - Different merchants may accidentally generate the same key
    - Scope prevents collision across users/tenants
    - Makes sharding easier
### **Multi-Layer Storage Strategy:**
  - **Layer 1: In-Memory Cache (Redis)**
    - Fast lookup (<1ms)
    - TTL: 1 hour
  - Eviction policy: LRU
  - Stores: `merchant_id + idempotency_key → response`

- **Layer 2: Primary Database (SQL)**
  - Persistent storage
  - ACID transactions for atomicity
  - TTL: 24 hours
  - Stores: full idempotency record with request details



---
## 11. State Machine (PROCESSING / SUCCESS / FAILED / EXPIRED)

Track the lifecycle of each idempotency record using a state machine:

```
       ON REQUEST
          ↓
      PROCESSING → SUCCESS → EXPIRED
          ↓
        FAILED ────→ EXPIRED or UNKNOWN
```

**State Details:**

| State | Meaning | Action |
|-------|---------|--------|
| **PROCESSING** | Operation in-flight | Return pending response; retry safe |
| **SUCCESS** | Operation completed successfully | Return cached response; skip execution |
| **FAILED** | Operation failed | Return cached error; skip execution |
| **EXPIRED** | Record > 24 hours old | Can be purged; treat as new request |
| **UNKNOWN** | Operation state unknown (e.g., timeout) | Return 202 Accepted; client can retry |

---
## 12. Concurrency & Race Conditions

### **Critical Race Condition:**

**Scenario:**
```
Request A (Idempotency Key: abc123) arrives
Request B (Same Key) arrives simultaneously
Both check Idempotency Store = KEY NOT FOUND
Both proceed to execute operation
Result: Duplicate execution! ❌
```

### **Solution: Distributed Lock**
- Use database unique constraint or Redis SET NX (SET if Not eXists)
- First request acquires lock; second waits or gets queued
- Lock timeout: 30 seconds (operation max latency)

---

## 13. Atomicity & Transaction Boundaries

### **Ensuring Atomicity:**

**Atomic Block (Single Transaction):**
1. Check if idempotency_key exists
2. If NOT exists:
   - Insert record with status = PROCESSING
   - Lock acquired
3. Perform business operation
4. Update idempotency record with response + status = SUCCESS
5. Commit transaction

**Key Principle:** Idempotency check + data insertion must be atomic.

---

## 14. Consistency Guarantees

### **Consistency Model:**

- **Idempotency Store + Business Data:** Both updated in same transaction
- **Read Consistency:** Monotonic reads (client always sees same/newer state)
- **Write Consistency:** Causal consistency (operation → idempotency record)
- **Isolation Level:** READ_COMMITTED or REPEATABLE_READ

---

## 15. Exactly-once vs At-least-once Behavior

📊 **Comparison:**

| Aspect | Exactly-Once | At-Least-Once |
|--------|--------------|---------------|
| **Guarantee** | Operation executes ≤ 1 time | Operation executes ≥ 1 time |
| **Implementation** | Idempotency key + deduplication | Retry with side effects |
| **Complexity** | Higher (requires state tracking) | Lower (simpler logic) |
| **Use Case** | Payments, transfers, critical ops | Non-critical, read-only operations |

**For Idempotent APIs:** We implement **Exactly-Once Semantics**

---

## 16. Distributed Locking (if needed)

### **Locking Strategy:**

**Option 1: Database Unique Constraint**
```sql
-- Insert fails if key already exists
INSERT INTO idempotency_records (idempotency_key, status, ...)
VALUES ('abc123', 'PROCESSING', ...)
-- First insert succeeds; subsequent retries fail with UNIQUE constraint error
```

**Option 2: Redis Distributed Lock**
```
SET idempotency:abc123 processing NX EX 30
-- Returns OK if lock acquired, nil if already held
```

**Lock Timeout:** ~2x expected operation latency (e.g., 30 seconds for payment)

---

## 17. Cache Usage

⚡ **Cache Strategy:**

**What to Cache:**
- Successful responses (status = SUCCESS)
- Failed responses with error details
- Amount: ~20% of daily requests

**Cache Pattern:**
```
key = idempotency_key
value = { response_body, http_code, status, timestamp }
ttl = min(24 hours, operation_specific_ttl)
```

**Invalidation:**
- Lazy expiration (TTL)
- Optional explicit cleanup on user logout
- Optional on account closure

---

## 18. Expiration / TTL Strategy

⏰ **TTL Design:**

**Idempotency Records Lifetime:**
- **Default:** 24 hours
- **Payment systems:** Up to 7 days (regulatory requirement)
- **Short-lived operations:** 1 hour

**TTL Management:**
- Set `expires_at` timestamp on record creation
- Batch cleanup job runs nightly
- Soft delete: mark as EXPIRED instead of hard delete

---

## 19. Failure Handling

❌ **Failure Scenarios:**

**Scenario 1: Network failure mid-operation**
- Operation starts but response lost
- Status in store = PROCESSING
- Client retries → receives pending response

**Scenario 2: Database failure**
- Fallback to secondary DB replica
- In-memory cache serves stale responses (acceptable)
- Circuit breaker to reject requests if all replicas down

**Scenario 3: Distributed lock timeout**
- Lock held but operation stuck
- After timeout (30s), lock released
- Retry by another request → execute operation again

---

## 20. Reconciliation / Recovery Jobs

🔧 **Cleanup & Recovery:**

**Job 1: Expired Record Cleanup**
- Delete records where `expires_at < NOW()`
- Run frequency: Daily (off-peak hours)
- Impact: Frees storage; allows re-use of idempotency keys

**Job 2: Stale PROCESSING State Recovery**
- Find records status = PROCESSING for > 30 min
- Mark as FAILED with "timeout" error message
- Alert: Investigate why operation hung

**Job 3: Consistency Audit**
- Verify idempotency_key in store matches transaction table
- Flag inconsistencies for manual investigation

---

## 21. Scalability

📈 **Scaling Strategies:**

**Horizontal Scaling:**
- Partition idempotency store by user_id
- Route requests to partition based on user_id hash
- Independent stores can scale independently

**Read Scaling:**
- Replicate idempotency store across regions
- Use read replicas for non-critical checks

**Write Scaling:**
- Write-through cache (cache + DB always in sync)
- Batch writes if permissible (not for payments)

---

## 22. Database Sharding Strategy

🗂️ **Sharding Key:** `user_id`

**Schema:**
```
Shard 0: user_ids 0-999
Shard 1: user_ids 1000-1999
...
Shard N: user_ids N*1000-(N+1)*1000-1
```

**Routing Logic:**
```
shard_id = hash(user_id) % num_shards
route request to shard_id
```

**Benefits:**
- Distribute load evenly
- Independent shard failures
- Can add/remove shards with consistent hashing

---

## 23. Security Considerations

🔐 **Security Measures:**

✅ **Idempotency Key Security:**
- Client-generated keys should be cryptographically random
- Validate key format (length, characters)
- Don't log full idempotency keys (only hash)

✅ **Access Control:**
- Users can only view their own idempotency records
- Admin access limited to authorized users
- Audit log all idempotency store accesses

✅ **Data Protection:**
- Encrypt sensitive data at rest (PII, amounts)
- Encrypt in transit (HTTPS/TLS)
- Implement rate limiting per user + IP

---

## 24. Monitoring & Observability

📊 **Metrics to Track:**

**Business Metrics:**
- Idempotency hit ratio (% of duplicate requests)
- Average response time (first vs. cached)
- Error rate by endpoint

**System Metrics:**
- Cache hit ratio (% cache vs. DB lookups)
- Idempotency store query latency
- Lock contention rate (failed lock acquisitions)
- Record insertion latency

**Logs & Alerts:**
- PROCESSING state duration > 30 seconds: **WARNING**
- Idempotency store query > 500ms: **ALERT**
- Cache hit ratio < 50%: **INVESTIGATE**

---

## 25. Trade-offs & Alternatives

⚖️ **Key Trade-offs:**

| Decision | Option A | Option B | Trade-off |
|----------|----------|----------|-----------|
| **Storage** | Inline in idempotency record | Separate blob table | Storage size vs. query complexity |
| **Lock Type** | DB constraint | Redis lock | Simplicity vs. performance |
| **Cache TTL** | 1 hour | 24 hours | Performance vs. memory usage |
| **Cleanup** | Hard delete | Soft delete (archived) | Storage vs. auditability |
| **Retry Behavior** | Queue retry | Return PROCESSING | Latency vs. complexity |

---

## 26. Edge Cases

🎪 **Edge Cases to Handle:**

1. **Same idempotency key, different request body**
   - Treat as error or accept only first body?
   - Recommendation: Log warning; use first body

2. **Idempotency key collision (hash collision)**
   - UUID v4: ~1 in 5 billion; acceptable risk
   - Add user_id to key scope to reduce collision risk

3. **Expired idempotency record + retry**
   - Key doesn't exist (expired and purged)
   - Treat as new request; execute operation again

4. **Clock skew across servers**
   - Use NTP to sync servers
   - Add buffer (TTL = expires_at + 5 min grace period)

---

## 27. Interview Wrap-up / Summary Story

🎯 **One-Liner:**
"Build an API system where duplicate requests are safely handled by storing and replaying responses, using idempotency keys, distributed locks, and state management."

**Core Flow:**
1. Client → Request with Idempotency-Key
2. Server → Check Idempotency Store (Cache → DB)
3. Found? → Return cached response (Exactly-Once)
4. Not found? → Execute operation → Store result
5. Retry with same key? → Return cached result instantly

**Key Components:**
- ✅ Idempotency Store (Redis + DB)
- ✅ Distributed Lock (prevent race conditions)
- ✅ State Machine (PROCESSING → SUCCESS/FAILED)
- ✅ TTL/Expiration (cleanup old records)

**Must-Say Points in Interview:**
- "Exactly-once semantics are critical for payments"
- "Race conditions require distributed locking"
- "Multi-layer caching (Redis + DB) balances latency and persistence"
- "TTL strategy prevents unbounded storage growth"

**Final Line:**
"This design scales to millions of requests/day while guaranteeing no duplicate side effects—ideal for payment systems, transfers, and other critical operations."
