# Designing Credit Card Authorization
## Table of Contents
1. [Problem Statement](#1-problem-statement)
2. [Requirements & Goals](#2-requirements--goals)
3. [Capacity Estimation (BOE)](#3-capacity-estimation-boe)
4. [High-Level Architecture](#4-high-level-architecture)
5. [Core Components](#5-core-components)
6. [Data Model](#6-data-model)
7. [Authorization Flow](#7-authorization-flow)
8. [State Machine](#8-state-machine)
9. [Idempotency](#9-idempotency)
10. [Failure Handling & Retries](#10-failure-handling--retries)
11. [Consistency & Guarantees](#11-consistency--guarantees)
12. [Performance & Latency Optimization](#12-performance--latency-optimization)
13. [Scalability](#13-scalability)
14. [Caching](#14-caching)
15. [Security & Fraud Prevention](#15-security--fraud-prevention)
16. [Observability & Monitoring](#16-observability--monitoring)
17. [Trade-offs & Alternatives](#17-trade-offs--alternatives)
18. [Wrap-Up Cheat Sheet (summary)](#18-wrap-up-cheat-sheetsummary)

---

## 1. Problem Statement
Design a system to authorize credit card transactions in real time that:
- Validate incoming payment requests (card, amount, merchant, etc.)
- Communicate with card networks and issuing banks
- Return approve / decline decision within milliseconds
- Ensure low latency and high availability
- Prevent duplicate or inconsistent authorizations (idempotency)
- Handle retries, timeouts, and partial failures safely
---

## 2. Requirements & Goals
### Functional Requirements:
- Authorize transaction (approve / decline)
- Validate request
- Call network & issuer
- Handle retries (idempotency)
### Non-Functional Requirements:
- Low latency
- High availability
- Strong correctness
- Scalable & reliable
- Secure (PCI)


---

## 3. Capacity Estimation (BOE)
Assumptions:

| Metric              | Assumption             |
| ------------------- | ---------------------- |
| Active users/cards  | 50M cards              |
| Transactions/day    | 100M auth requests/day |
| Avg txn size        | ~1 KB                  |
| Read (status check) | 200M reads/day         |
| Peak multiplier     | 5–10×                  |
| Retention           | 1 year (auth logs)     |

- Write QPS: 100 M / 86400 = 1,157 QPS (avg)
- Peak QPS: 1,157 × 10 = 11,570 QPS
- Read QPS: 200 M / 86400 = 2,314 QPS (avg)
- Peak Read QPS: 2,314 × 10 = 23,140 QPS
- Storage: 100 M auth/day × 365 days × 1 KB = 36.5 TB/year
- Bandwidth(write)  = 1,157 QPS × 1 KB = 1.157 GB/s (avg)
- Bandwdith(read)  = 2,314 QPS × 1 KB =
- Latency requirement: < 300 ms end-to-end
  - Breakdown:
    - Api gateway: < 20 ms
    - Auth service processing: < 50 ms
    - Network: < 100-150 ms
    - issuer response: < 50- 100 ms
---

## 4. High-Level Architecture

    
    Client (POS / App)
        ↓
    API Gateway
        ↓
    Authorization Service
        ↓
    Card Network (Visa / Mastercard)
        ↓
    Issuer Bank
        ↓
    Authorization DB  ← store result
        ↓
    Outbox / Queue
        ↓
    Consumers
    - Notification Service
      - Reconciliation Service
      - Analytics / Fraud Detection

**- Components:**
- Client → sends auth request
- API Gateway → auth, rate limit, routing
- Authorization Service → orchestrates flow
- Card Network → routes to issuer
- Issuer Bank → approve / decline
- Auth DB → store transaction
- Outbox/Queue → async processing
---

## 5. Core Components
- API Gateway
- Authorization Service (orchestrator)
- Card Network Integration (e.g., Visa / Mastercard)
- Issuer Bank Service
- Authorization Database
- Idempotency Store
- Cache (card / risk data)
- Message Queue / Event Bus





---

## 6. Data Model
1️⃣ **authorizations:** stores the main auth result (approve/decline + details):

        id (PK), card_id,  merchant_id, amount, currency, status (INITIATED / APPROVED / DECLINED), auth_code, created_at
2️⃣ **idempotency_keys:** prevents duplicate processing on retries

       key (PK), request_hash, response, status, created_at
3️⃣ **auth_events: ** stores event history for each auth (for auditing, troubleshooting, analytics)

      id (PK), auth_id (FK), event_type, payload, created_at



---

## 7. Authorization Flow
1. Receive request (card, amount, merchant, idempotency key)
2. Check idempotency
3. if exists → return stored result
4. Validate request (basic checks)
5. Run risk/fraud checks
6. Send to card network (e.g., Visa / Mastercard)
7. Issuer bank decision (approve / decline)
8. Store result in DB
9. Return response to client
10. Publish event (async: notification, reconciliation)



---

## 8. State Machine:
- States:
  - INITIATED
  - APPROVED
  - DECLINED
  - EXPIRED
  - CAPTURED (later phase)
- Transitions
  
          INITIATED → APPROVED → CAPTURED
          INITIATED → DECLINED
          INITIATED → EXPIRED



---

## 9. Idempotency

 - Use Idempotency-Key per request
 - Store key + request + response
 - On retry:
   - if key exists → return same result
   - else → process normally

---

## 10. Failure Handling & Retries
- **🧠Goal:** 
  - **Never double authorize**
  - **Never lose a transaction**
  - **Always know final state(eventual consistency)**

- **⚠️ What Causes Failures?**
  - **Network issues** (timeout, disconnect):
    - timeout to visa/issuer
    - packet loss
  - **External system problems:**
    - Issuer bank down
    - card network delay
  - **Client Retries:**
    - Duplicate requests (e.g., user double clicks)
    - POS retries automatically
  - **Internal errors:**
    - DB write failure
    - Service crash

- **🛠️ How We Deal With Them:**
  1. **Idempotency (MOST IMPORTANT)**:
     - same request → same result
     - prevents double authorization
  2. **Retries (Controlled)**
      - retry only on safe failures (timeout, 5xx)
      - use backoff + limit
  3. **UNKNOWN / PENDING State**
     - if no response → don’t guess
     - mark as PENDING
  4. **Reconciliation (FINAL SAFETY NET)**
     - later check with network/issuer
     - fix:
       - missing records
       - wrong states
  5. **Atomic Writes**
     - DB transaction ensures:
       - no partial state 

---

## 11. Consistency & Guarantees

- **What We Guarantee**
  - No duplicate authorization
  - No partial state (atomic write)
  - Eventually correct final state
- **⚖️ Exactly-once vs At-least-once**
  - **Exactly-once:**  ❌ not realistic (network retries, failures)
  - **At-least-once (what we use)**
    - retries may happen
    - ✅ handled via idempotency
- **🧠 Practical Approach**
Use at-least-once + idempotency = effectively exactly-once



---

## 12. Performance & Latency Optimization
- **🎯 Goal:**
  - Keep end-to-end latency ≤ 300 ms
  - Ensure fast response even under peak load
  - Avoid blocking on non-critical work
  - **🛠️ How We Achieve It**
  
  1. ✅ **Parallel Work:**
  
      - ❌ Sequential flow — slower:```   Idempotency → Validate card → Fraud check → Merchant check → Call network```
      - ✅ Parallel flow — faster:
           ````
                        ┌─ Card validation
            Request  ── |─Idempotency
                        ├─ Merchant validation
                        ├─ Fraud / velocity check
                        └─ Risk rule lookup
            After all required checks pass → Call card network
           ````
      - **What Can Run in Parallel?**
        - Card validation: expiry, card status, token lookup
        - Merchant validation:     merchant active, allowed currency, MCC rules
        - Fraud / risk check:     velocity check, suspicious pattern check
        - BIN lookup:          card prefix → issuer/network info
        - Limit checks:         per-merchant / per-card limits
  2. ✅ **Keep Critical Path Small**:
     - Only do what’s required before calling network:
       - **Critical path:**
         - validate
         - idempotency check
         - call network
       - **Everything else:**
         - logging
         - analytics
         - notifications
         - 👉 move to async (queue)
  3. ✅ **3. Caching (Remove Work)**
        - Cache card data (BIN info, card status)
  4. **Fast Network Calls:**
     - reuse TCP connections
     - avoid reconnecting each request
  5. **Timeout Control:**
        - Don’t wait forever:
          - network timeout: ~150 ms
          - if no response → mark PENDING
   
---

## 13. Scalability
- **🎯 Goal**
  - Handle **normal + peak authorization traffic**
  - Avoid **one DB/service becoming bottleneck**
  - Scale without breaking correctness

- **🛠️ How We Scale**
  - **Stateless Authorization Service**
    - run many instances behind load balancer
  - **Partition DB**
    - shard by merchant_id or card_id
  - **Use Read Replicas**
    - for status/history reads
  - **Queue Async Work**
    - notifications, analytics, reconciliation
  - **Cache Hot Data**
    - card/BIN metadata, risk rules


---

## 14. Caching

- **🎯 Goal**
  - Reduce latency
  - Reduce repeated DB/network lookups
  - Protect backend services during peak traffic
- **What to Cache**
  - **BIN/card metadata:** card prefix → network / issuer info
  - **Merchant config:** merchant status, limits, allowed currencies
  - **Risk rules:** fraud thresholds, velocity rules
  - **Idempotency result:** short TTL for retry protection
- **Cache Pattern**
  - Use cache-aside
  - Check cache first
  - If miss → read DB → update cache
- **TTL / Invalidation**
  - BIN metadata → long TTL
  - Risk rules → medium TTL
  - Merchant config → short TTL or event-based invalidation
  - Idempotency → TTL based on retry window


---

## 15. Security & Fraud Prevention

- **🎯 Goal**
    - Protect cardholder data
    - Prevent unauthorized/fraudulent transactions
    - Stay compliant with PCI requirements
- **Security**
  - Encrypt sensitive data
  - Tokenize card number / PAN
  - Never store CVV
  - Use TLS for all network calls
  - Strict access control and audit logs
- **Fraud Prevention**
  - Velocity checks
  - Merchant risk rules
  - Device/IP/location signals
  - Amount threshold checks 
  - Suspicious pattern detection


---

## **16. Observability & Monitoring**

### 🎯 Goal
- Detect issues fast
- Track system health & performance
- Enable debugging & audits

### 📈 Metrics
- Latency (p50 / p95 / p99)
- TPS (throughput)
- Approval / decline rate
- Error rate
- Timeout rate (network/issuer)

### 🧾 Logging
- Request / response logs (masked)
- Authorization state transitions
- Failure reasons

### 🚨 Alerts
- High latency
- Spike in failures/declines
- Network/issuer timeout increase


---

## **17. Trade-offs & Alternatives**

### 🎯 Goal
- Show awareness of design decisions + their impact

### 🔄 Sync vs Async Authorization
- **Sync (default)** → real-time decision
- **Async** → lower latency but risk of inconsistency
- **👉 Choice:** Select sync for correctness

### ⚖️ Speed vs Fraud Accuracy
- **More fraud checks** → safer but slower
- **Fewer checks** → faster but riskier
- **👉 Choice:** Balance based on risk tolerance

### 🧠 Cache vs Fresh Data
- **Cache** → fast
- **DB** → accurate
- **👉 Choice:** Cache only stable data (not auth decisions)

### 🔁 Retry vs Duplicate Risk
- **Retry** improves reliability
- **But** risks duplicate processing
- **👉 Choice:** Solved via idempotency

### 🗄️ Strong Consistency vs Scalability
- **Strong consistency** → safer (financial systems)
- **Eventual consistency** → more scalable but risky
- **👉 Choice:** Choose strong consistency for writes



---

## **18. Wrap-Up Cheat Sheet (Summary)**

### 🎯 Goal
- Real-time approve / decline
- < 300 ms latency
- No duplicates, no money inconsistency

### 🏗️ Core Flow
- Client → Gateway → Auth Service → Network → Issuer → Response
- ↓ DB + Queue

### 🧠 Key Concepts
- **Idempotency** → no double auth
- **State machine** → INITIATED → APPROVED / DECLINED
- **At-least-once + idempotency** = effectively exactly-once
- **PENDING + reconciliation** for unknown cases

### ⚠️ Failure Strategy
- **Timeout** → PENDING
- **Retry** → safe via idempotency
- **Final fix** → reconciliation job

### ⚡ Performance
- Parallel checks (fraud + validation)
- Minimize critical path
- Cache metadata
- Fast network calls

### 📈 Scalability
- Stateless services
- DB sharding (card_id / merchant_id)
- Read replicas
- Async processing (queue)

### 🧊 Caching
**Cache:**
- BIN / card metadata
- Merchant config
- Risk rules

**Don't cache:**
- Final auth decision

### 🔐 Security & Fraud
- Tokenization (no raw PAN)
- No CVV storage
- TLS everywhere
- Fraud checks (velocity, rules, patterns)

### 📊 Observability
- **Metrics:** latency, TPS, error rate
- **Logs:** request + state changes
- **Alerts:** failures, timeouts spike

### ⚖️ Trade-offs
- **Sync > Async** (correctness)
- **Strong consistency for writes**
- **Speed vs fraud accuracy**
- **Retry vs duplicate** → solved via idempotency
