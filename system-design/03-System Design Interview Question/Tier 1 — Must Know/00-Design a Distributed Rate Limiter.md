# Design a Distributed Rate Limiter

## Table of Contents
1. [Problem Definition and Scope](#1-problem-definition-and-scope)
2. [Clarify Functional Requirements](#2-clarify-functional-requirements)
3. [Clarify Non-Functional Requirements](#3-clarify-non-functional-requirements)
4. [Back of the Envelope Estimates](#4-back-of-the-envelope-estimates)
5. [API Design](#5-api-design)
6. [High-Level Architecture](#6-high-level-architecture)
7. [Data Model](#7-data-model)
8. [Core Flows End to End](#8-core-flows-end-to-end)
   - [Flow 1: The "Check-and-Act" Cycle (Happy Path)](#flow-1-the-check-and-act-cycle-happy-path)
   - [Flow 2: Deep Dive into the "Weighted Average" Calculation](#flow-2-deep-dive-into-the-weighted-average-calculation)
   - [Flow 3: Handling Rejection (The "Blocked" Path)](#flow-3-handling-rejection-the-blocked-path)
   - [Flow 4: Failure Handling (Fail-Open Strategy)](#flow-4-failure-handling-fail-open-strategy)
9. [Caching and Read Performance](#9-caching-and-read-performance)
10. [Storage, Indexing and Media](#10-storage-indexing-and-media)
11. [Scaling Strategies](#11-scaling-strategies)
12. [Reliability, Failure Handling and Backpressure](#12-reliability-failure-handling-and-backpressure)
13. [Security, Privacy and Abuse](#13-security-privacy-and-abuse)
14. [Bottlenecks and Next Steps](#14-bottlenecks-and-next-steps)
15. [Summary](#15-summary)

---

## 1. Problem Definition and Scope
Design a Distributed Rate Limiter API that controls how many requests a client can make within a time window.

What the system does

It answers this question:

“Should this request be allowed right now, or should we block it because the caller exceeded the limit?”
Protect backend systems from:

traffic spikes
abusive users
buggy clients
accidental retry storms
expensive endpoint overload


## 2. Clarify Functional Requirements
this system should support:

| Requirement                 | Meaning                                                         |
| --------------------------- | --------------------------------------------------------------- |
| Check request               | Decide `ALLOW` or `BLOCK` before backend processes request      |
| Multiple limit keys         | Limit by `user_id`, `api_key`, `IP`, `endpoint`, or combination |
| Configurable rules          | Example: `100 requests / minute`, `10 login attempts / hour`    |
| Return metadata             | Tell client remaining quota and retry time                      |
| Support distributed traffic | Requests may hit different API gateway nodes                    |
| Update limits               | Admin/service can change rules                                  |
| Audit/metrics               | Track allowed, blocked, latency, hot keys                       |



## 3. Clarify Non-Functional Requirements
| Requirement         | Target                                                             |
| ------------------- | ------------------------------------------------------------------ |
| Low latency         | Rate limit check should be very fast, around 1–5 ms if cache/local |
| High availability   | Rate limiter should not take down APIs                             |
| Scalable            | Handle millions of checks/sec                                      |
| Reasonably accurate | Small approximation is okay for some APIs                          |
| Consistent enough   | Avoid allowing huge bursts across nodes                            |
| Fault tolerant      | Decide fail-open or fail-closed                                    |
| Multi-region ready  | Support global APIs if needed                                      |




## 4. Back of the Envelope Estimates
Assumption:
- total traffic is 100 million request perday
- Peak traffic is 5x average
- Reate limit check: one per request

Average QPS  = 100 Million / 100000 = 1000 QPS 
Peak QPS = 5 * 1000 = 5000 QPS

for Bigger sacle:
 10B requests per day 
Average QPS  = 10 Billion / 100000 = 100,000 QPS
Peak QPS = 5 * 100,000 = 500,000 QPS
so we need to design a system that can handle 500,000 QPS at peak.

so We need:

| Conclusion                  | Why                                                                         |
| --------------------------- | --------------------------------------------------------------------------- |
| **In-memory counter store** | DB cannot handle per-request counter writes at 500K QPS cheaply/fast enough |
| **Horizontal scaling**      | One rate limiter node cannot handle all checks                              |
| **Shard by key**            | Counters must be distributed across Redis nodes                             |
| **Local config cache**      | Reading rules from DB per request is too slow                               |
| **Fallback strategy**       | If Redis/rate limiter fails, API should not fully collapse                  |



## 5. API Design

for this desing we have two main APIs:
1. Runtime API:
   - This is only API used in the request flow
     - check Rate Limit:
       - ```POST /api/v1/rate-limit/check```
       - Request body:
         ```json
         {
         "key": "user:123:endpoint:/payments",
         "rule_id": "payments_per_minute",
         "cost": 1
         }
         ```
       - | Field     | Meaning                              |
         | --------- | ------------------------------------ |
         | `key`     | Who/what we are limiting             |
         | `rule_id` | Which limit rule to apply            |
         | `cost`    | How many units this request consumes |
       - Response body -Allowed:
         ```json
         {
          "allowed": true,
          "limit": 100, 
          "remaining": 72,
          "rest_at": "2024-07-01T12:00:00Z",
          "retry_after_seconds": 0
         }
         ```
       - Response body -Blocked:
         ```json
         {
          "allowed": false,
          "limit": 100, 
          "remaining": 0,
          "rest_at": "2024-07-01T12:00:00Z",
          "retry_after_seconds": 60
         }
         ```
2. Admin API:
 - These are used to manage rules, not per request, and used by Internal tools / config service / admins
  
   ```
     POST   /api/v1/rate-limit/rules
     GET    /api/v1/rate-limit/rules/{id}
     PATCH  /api/v1/rate-limit/rules/{id}
    ``` 
   
### 🎯 What you say in interview

>“There is one main runtime API /check that the gateway calls for every request. In addition, I expose admin APIs to create and manage rate limiting rules.”



## 6. High-Level Architecture

- for this design , rate limiter sits before the back end service.
- ### Main components:
   | Component                 | Responsibility                        |
   | ------------------------- | ------------------------------------- |
   | **Client**                | Sends API request                     |
   | **API Gateway**           | Auth, routing, calls rate limiter     |
   | **Rate Limiter Service**  | Decides `ALLOW` or `BLOCK`            |
   | **Rule Store**            | Stores limit rules like `100 req/min` |
   | **Rule Cache**            | Keeps rules in memory for fast access |
   | **Redis Counter Store**   | Stores live request counts            |
   | **Backend Service**       | Processes request only if allowed     |
   | **Metrics/Logs Pipeline** | Tracks allowed/blocked traffic        |
   
```

   Client
     ↓
   API Gateway
     ↓
   Rate Limiter Service
       ├── Rule Cache (in-memory, inside service)
       ├── Redis (counters)
       └── Rule Store (DB / Config Service)
     ↓
   Backend Service
```
### How it actually works (step-by-step)

Everything happens inside Rate Limiter Service
1. Gateway calls Rate Limiter
2. Rate Limiter checks Rule Cache (memory)
3. If not found → fetch from Rule Store (DB)
4. Then check counter in Redis
5. Make decision (allow/block)

###  🎯Interview Line (clean)

“The Rate Limiter is a stateless service that keeps rules in an in-memory cache for fast access, fetches them from a persistent store on cache miss, and uses Redis to track request counts. All these components are used internally within the Rate Limiter during request processing.”
## 7. Data Model
 For rate limiter, we have two types of data:

| Data Type        | Purpose           | Storage         |
| ---------------- | ----------------- | --------------- |
| **Rule data**    | Defines the limit | DB / Rule Store |
| **Counter data** | Tracks live usage | Redis           |

1. **Rule Table:** This is stored in Rule Store DB.
    
    | Column           | Example                  | Notes                      |
    | ---------------- | ------------------------ | -------------------------- |
    | `rule_id`        | `payments_per_minute`    | Primary key                |
    | `scope`          | `user_id:endpoint`       | What dimension we limit by |
    | `limit_count`    | `100`                    | Max allowed requests       |
    | `window_seconds` | `60`                     | Time window                |
    | `algorithm`      | `sliding_window_counter` | Rate limiting strategy     |
    | `fail_mode`      | `fail_open`              | What to do if Redis fails  |
    | `status`         | `ACTIVE`                 | Active/disabled            |
    | `created_at`     | timestamp                | Audit                      |
    | `updated_at`     | timestamp                | Cache refresh              |
    
    **Example:**
   
    | rule_id               | scope              | limit_count | window_seconds | algorithm                |
    | --------------------- | ------------------ | ----------: | -------------: | ------------------------ |
    | `payments_per_minute` | `user_id:endpoint` |         100 |             60 | `sliding_window_counter` |


2. **Redis Counter Data:**

    This is runtime usage, stored temporarily.
    
    Key:
    
    ```rl:{rule_id}:{user_id}:{endpoint}:{window_start}```
    
    Example:
    
    ``rl:payments_per_minute:user123:/payments:1714672800``
    
    Value:
    ``` json
    {
    "count": 29
    }
    ```
    **TTL:**
    
    ```window_seconds * 2```
    
    **Why TTL?**
    ```
    After the time window passes, old counters are useless.
    Redis deletes them automatically.
    ```
3. **Optional Rule Assignment Table**

     Use this if different customers have different limits.
    
    | Column      | Example                       |
    | ----------- | ----------------------------- |
    | `tenant_id` | `merchant_123`                |
    | `endpoint`  | `/payments`                   |
    | `rule_id`   | `payments_per_minute_premium` |
    | `status`    | `ACTIVE`                      |

    Example:

   | tenant_id          | endpoint    | rule_id            |
   | ------------------ | ----------- | ------------------ |
   | `merchant_basic`   | `/payments` | `payments_basic`   |
   | `merchant_premium` | `/payments` | `payments_premium` |

4. **Metrics / Audit Events**

    Do not store every request in SQL.

    Instead aggregate:

   | Metric          | Example               |
   | --------------- | --------------------- |
   | `rule_id`       | `payments_per_minute` |
   | `allowed_count` | 95000                 |
   | `blocked_count` | 1200                  |
   | `minute_bucket` | `2026-05-02T17:00`    |

    ### Clean mental Model

    ```
    Rule Store:
    "What is the limit?"
    Redis:
    "How much has been used?"
    Metrics:
    "What happened overall?"
    ```

#### Interview Line

>“For the data model, I separate persistent rule configuration from temporary runtime counters. Rules are stored in a durable rule store and cached in memory, while counters are stored in Redis with TTL because they expire after the rate-limit window.”


## 8. Core Flows End to End
**Focus:** how a single request moves through the system.

### 🔄 Main Flow (End-to-End)
```    
    Client
        ↓
    API Gateway
        ↓
    Rate Limiter Service
        ├─ read rule (Rule Cache → DB if miss)
        ├─ check/update counter (Redis)
        └─ return decision
        ↓
    Backend Service (only if allowed)
```
#### 📜 Story (clean, interview style)

> “When a request arrives at the API Gateway, the gateway extracts the identity of the caller and the endpoint. It then calls the Rate Limiter service. The Rate Limiter loads the rule from its local cache, checks the current usage in Redis, and decides whether the request can proceed. If allowed, the gateway forwards the request to the backend; otherwise, it returns a 429 response.”

🧠 **_Step-by-Step Breakdown_**
1. Request arrives: ```User 123 calls /payments```
   
2. Gateway builds key:
    ```
       key = user:123:endpoint:/payments
       rule_id = payments_per_minute
    ```
3. Gateway calls Rate Limiter :```   POST /rate-limit/check```
4. Rate Limiter loads rule: 

    ```
       Check Rule Cache
        ↓
       If miss → fetch from Rule Store (DB)
    ```
    Example rule: ```limit = 100 requests / 60 seconds```
5. Rate Limiter checks Redis
    ```
        Key: rl:payments_per_minute:user123:/payments:window123
        Value:  count = 28
    ```
6. Decision:```28 < 100 → ALLOW```
7. Update counter:```   count = 29  (atomic update in Redis)```
8. Response back to Gateway:
    ```json
       {
       "allowed": true,
       "remaining": 71
       }
    ```
9. Gateway action:
    ```
   allowed → forward to backend
   blocked → return 429
    ```
   
### 🔥 Important Observations
1. **Everything happens before backend**
   Rate limiter protects backend
2. **Rule vs Counter (again, but now in flow)**

   | Step   | Uses            |
   | ------ | --------------- |
   | Step 4 | Rule Cache / DB |
   | Step 5 | Redis counter   |
   | Step 6 | Decision logic  |

3. **Atomicity (very important)**
    - Check + increment must be atomic
    - Otherwise:
    ````
       Two requests read 99
       Both allow → limit broken ❌
    ````
    - Solution:
   ```
    Redis Lua script OR atomic INCR logic
   ```
#### 🎯 Interview Line

>“In the core flow, the gateway calls the Rate Limiter before forwarding traffic. The Rate Limiter reads the rule from cache, checks the current usage in Redis, performs an atomic check-and-increment, and returns whether the request is allowed. This ensures consistent enforcement under high concurrency.”

### Flow 1: The "Check-and-Act" Cycle (Happy Path):

```Gateway → Rate Limiter → Redis → Allow → Backend```

**What really happens:**

1. Gateway builds key:``` → user:123:/payments```
2. Rate Limiter loads rule ```→ from cache (DB only if miss)```
3. Redis lookup: ``` → current usage (e.g., 28)```
4. Atomic logic: ```  - → if 28 < 100 → increment → 29```
5. Return:````   { "allowed": true }````

**👉 Why important:**
  - This is the core decision path executed on every request.
  - Atomicity is critical (no race condition)
### Flow 2: Deep Dive into the "Weighted Average" Calculation
```Use previous + current window```

Instead of hard cutoff: ```estimated = prev * weight + current```

**👉 Meaning:**
- We don’t reset instantly at minute boundary
- We smooth traffic

**👉 Prevents:**

```100 req at 12:00:59 + 100 at 12:01:00 ❌```



### Flow 3: Handling Rejection (The "Blocked" Path)
```Gateway → Rate Limiter → BLOCK → 429```

**Steps:**

1. Redis shows usage exceeded
2. Rate Limiter returns: ```{ "allowed": false }```
3. Gateway stops request
4.Returns: ```429 Too Many Requests```

👉 Critical: **Backend is never called**



### Flow 4: Failure Handling (Fail-Open Strategy)

### 🎯 What is the problem we are solving?

👉 What if Rate Limiter is DOWN? ```Gateway → Rate Limiter ❌ (fails)```

Now Gateway must decide: ```Allow request OR Block request?```
#### 🔴 Option 1: Fail-Closed (STRICT)

```If limiter fails → BLOCK everything```

**Example**
```
User calls /payments
Limiter is down
→ Gateway blocks request ❌
```
👉 Result:
- No abuse ✅
- But system becomes unusable ❌

#### 🟢 Option 2: Fail-Open (RELAXED)
    If limiter fails → ALLOW everything
**Example:**

        User calls /payments
        Limiter is down
        → Gateway allows request ✅

👉 Result:
   - System keeps working ✅
   - But risk: ```Users can spam → backend overload ❌```

####  ⚖️ The Problem
| Strategy    | Problem          |
| ----------- | ---------------- |
| Fail-Closed | System outage    |
| Fail-Open   | Abuse / overload |

#### ✅ Best Practical Solution
**👉 Fail-Open + Small Local Limit**

        Limiter fails
        ↓
        Gateway uses LOCAL memory counter
        ↓
        Allow limited traffic only
#### 🧠 Real Example
    Rule = 100 req/min (normally via Redis)

Now Redis is down.

**What we do:**

        Fallback = 20 req/min (local memory)
**Flow**
        
        1. Gateway calls Rate Limiter
        2. Rate Limiter cannot access Redis
        3. Rule says fail_mode = fail_open
        4. Gateway allows request
        5. Optional: apply local fallback limit
        6. Emit alert/metric
   **After limit**

           count = 21 → BLOCK ❌
#### 🎯 Why this works
| Benefit           | Explanation             |
| ----------------- | ----------------------- |
| Avoid full outage | not blocking everything |
| Avoid abuse       | still limits traffic    |
| Protect backend   | prevents flood          |

🧠 Mental Model

    Normal → Redis controls
    Failure → Gateway controls (smaller limit)
#### Interview Lines — All Flows

**Flow 0: End-to-End**

>“At a high level, every request first reaches the API Gateway. The gateway calls the Rate Limiter before forwarding traffic. The limiter checks the rule, checks usage counters, and returns either allow or block.”

**Flow 1: Check-and-Act**

>“In the happy path, the Rate Limiter loads the rule from cache, checks the current usage in Redis, performs an atomic check-and-increment, and returns allow if the request is still under the limit.”

**Flow 2: Sliding Window / Weighted Average**

>“To avoid burst traffic at fixed-window boundaries, I use a sliding window counter. It combines the previous window count and current window count using a time-based weight, so recent past traffic still partially counts.”

**Flow 3: Blocked Path**

>“If the estimated usage exceeds the limit, the Rate Limiter returns blocked, and the API Gateway immediately returns HTTP 429 with retry information. The backend is not called.”

**Flow 4: Failure Handling**

>“If Redis or the Rate Limiter is unavailable, the fallback behavior depends on the rule. For normal APIs I may fail open with a small local emergency limit, while for sensitive APIs like login or payments I may fail closed.”

**One combined line**

> “The gateway calls the Rate Limiter on every request. The limiter loads rules from cache, checks Redis counters atomically, uses sliding window logic to smooth traffic, blocks over-limit users with 429, and applies fail-open or fail-closed behavior during failures.”
## 9. Caching and Read Performance


Every request hits the rate limiter, so the system must avoid slow operations like database reads and minimize network calls.

**🔹 How the fast path works**

        Gateway → Rate Limiter
        → Rule Cache (memory)
        → Redis (counter)
        → Decision

**👉 No DB in request path**

**🔹 What we cache**
- Rules (limit, window) → stored in memory (Rule Cache)
- Counters (usage) → stored in Redis

**👉 Why?**

    Rules change rarely → cache them
    Counters change constantly → keep in Redis
**🔹 Key optimization**

👉 Do check + increment in one Redis call

    Avoid:
    GET → check → INCR  ❌
    
    Use:
    Atomic operation (Lua script) ✅
**🔹 Cache update strategy**
- Load rules:
  - on startup OR first request
- Keep fresh:
  - TTL OR push updates
**🔹 Important rules**
  
          1. No DB per request
          2. Use in-memory cache for rules
          3. Use Redis for shared counters
          4. Keep Redis calls minimal (1 per request)
 #### 🎯 Interview line

> “To keep latency low, I cache rate-limit rules in memory and store counters in Redis. I ensure the check-and-increment operation is atomic and done in a single Redis call, and I avoid any database access in the request path.”

## 10. Storage, Indexing and Media
We store rules permanently, but usage is temporary.

**🔹 What goes where**

    Rules     → DB (source of truth)
    Counters  → Redis (fast + temporary)
    Metrics   → Aggregated (not per request)
**🔹 Why this split?**
- **Rules** → rarely change → safe in DB
- **Counters** → change every request → must be fast → Redis
- **Metrics** → too large → aggregate only

**🔹 Redis Key (important)**

        rl:{rule_id}:{key}:{window}

**👉 Example:** ```rl:payments:user123:12:00```
- TTL = auto delete old data
- No manual cleanup needed

**🔹 Indexing (simple)**
- rule_id → primary key
- nothing complex (low traffic)

**🔹 What we DO NOT do**

    ❌ store every request in DB
    ❌ store counters permanently
##### 🎯 Interview line

>“I store rate-limit rules in a database as the source of truth, keep counters in Redis with TTL since they are short-lived, and only store aggregated metrics to avoid high write volume.”

## 11. Scaling Strategies
- Horizontal scaling: stateless rate limiter nodes
- Redis sharding: partition by hash(key)
- Redis Cluster: automatic sharding + replication
- Local rule cache: avoid DB lookup
- Edge deployment: push to CDN for global APIs

## 12. Reliability, Failure Handling and Backpressure
- Redis replication: master-replica setup
- Fail-open with local limit (already covered in Flow 4)
- Circuit breaker: fail fast if Redis slow
- Monitoring: latency, block rate, Redis health
- Backpressure: gateway queue + reject early

## 13. Security, Privacy and Abuse
- Rate limit by IP at edge (DDoS)
- Authenticate Redis connections (TLS)
- Audit log: rule changes
- Multi-layer limits: IP + user + endpoint
- Key obfuscation: don't expose internal IDs

## 14. Bottlenecks and Next Steps
Bottlenecks:
- Redis capacity at extreme scale
- Network latency to Redis
- Rule propagation delays

Next:
- In-memory local rate limiting
- Geo-distributed Redis
- Adaptive ML-based limits
- Token bucket hardware acceleration

## 15. Summary
Key decisions:
- Sliding window counter for smooth traffic
- Redis for shared state, local cache for rules
- Fail-open with fallback limits
- Atomic check-and-increment in Redis
- Horizontal scaling + Redis sharding