# Designing Fraud Detection System

## Table of Contents
1. [Problem Statement](#1-problem-statement)
2. [Requirements & Goals](#2-requirements--goals)
3. [Assumptions + BOE Estimation](#3-assumptions--boe-estimation)
4. [High-Level Design](#4-high-level-design)
5. [Core Components](#5-core-components)
6. [API Design](#6-api-design)
7. [Data Model](#7-data-model)
8. [Fraud Rules vs ML Model](#8-fraud-rules-vs-ml-model)
9. [Real-Time Detection Flow](#9-real-time-detection-flow)
10. [Async Review / Investigation Flow](#10-async-review--investigation-flow)
11. [Decision Engine](#11-decision-engine)
12. [Feature Store](#12-feature-store)
13. [Event Streaming / Kafka Pipeline](#13-event-streaming--kafka-pipeline)
14. [State Machine](#14-state-machine)
15. [False Positives / False Negatives](#15-false-positives--false-negatives)
16. [Scalability](#16-scalability)
17. [Caching](#17-caching)
18. [Consistency & Reliability](#18-consistency--reliability)
19. [Security](#19-security)
20. [Observability & Monitoring](#20-observability--monitoring)
21. [Trade-offs & Alternatives](#21-trade-offs--alternatives)
22. [Wrap-Up Cheat Sheet](#22-wrap-up-cheat-sheet)

---

## 1. Problem Statement

Design a real-time Fraud Detection System that:

### Core Goals
- Analyze incoming transactions/events in real-time
- Make instant decisions to protect against fraud
- Minimize fraud loss while reducing customer friction

### Decision Categories
- ✅ **Approve** → Allow transaction to proceed
- ❌ **Block** → Reject transaction immediately
- ⚠️ **Flag for review** → Manual review by risk team

### Key Requirements
- Handle high throughput with ms-level latency
- Support multiple channels:
  - Card payments
  - Wallet transfers
  - Online transactions
- Use rules + ML models for detection
- Be scalable, reliable, and fault-tolerant

---

## 2. Requirements & Goals

### ✅ Functional Requirements
- **Ingest** transaction events in real-time
- **Evaluate** each transaction for fraud risk
- **Return decision:**
  - APPROVE
  - BLOCK
  - REVIEW
- **Support rule-based checks** for fraud patterns
- **Support ML-based risk scoring** for complex patterns
- **Store fraud decisions** and reasons for audit
- **Allow manual review** by risk team
- **Update fraud rules/models** over time dynamically
- **Emit events** for downstream systems:
  - Notification service
  - Audit logs
  - Analytics pipeline
  - Reconciliation service

### ⚙️ Non-Functional Requirements
- **Low latency:** Decision in milliseconds (< 100ms)
- **High availability:** Fraud service should not impact payment flow
- **Scalable:** Handle high transaction volume (1000s QPS)
- **Reliable:** No lost fraud events or decisions
- **Auditable:** Keep complete decision history and reasons
- **Consistent enough:** Same transaction should not get conflicting decisions
- **Secure:** Protect sensitive user/payment data (PCI)
- **Explainable:** Provide reason codes for block/review decisions


---

## 3. Assumptions + BOE Estimation

### Assumptions
- **Users:** 10M users
- **Transactions/day:** 1M transactions/day
- **Avg transaction event size:** 2 KB
- **Fraud decision latency:** Must complete in < 100 ms
- **Transaction characteristics:**
  - Most transactions are normal (benign)
  - Small percentage flagged for manual review
- **Read/write pattern:**
  - Write-heavy for transaction events
  - Read-heavy for fraud review dashboards

### Traffic Estimation
- **Average QPS:** 
  - 1M transactions/day ÷ 86,400 seconds ≈ 12 transactions/sec
- **Peak QPS:**
  - Assume 10x peak ≈ 120 transactions/sec

### Storage Estimation
- **Per day:** 1M transactions/day × 2 KB = 2 GB/day
- **Per year:** 2 GB/day × 365 ≈ 730 GB/year
- **With decision logs, features, metadata, audit (5x multiplier):** ≈ 1–2 TB/year

---

## 4. High-Level Design

### Flow

```
    Client / Payment System
            ↓
    API Gateway
            ↓
    Transaction Service
            ↓
    Fraud Detection Service
            ↓
    Decision Engine
    ┌───────────────┬───────────────┐
    ↓               ↓               ↓
    Rule Engine     ML Scoring     Feature Store
    |_______________________________| 
            ↓               
        Fraud Decision
            ↓
    Transaction Service
            ↓
    Approve / Block / Review
```

### Request Flow Steps
1. **Client sends transaction** to Payment System
2. **API Gateway** routes request to Transaction Service
3. **Transaction Service** calls Fraud Detection Service with transaction details
4. **Fraud Service:**
   - Fetches features from Feature Store
   - Runs rule-based checks (Rule Engine)
   - Runs ML-based risk scoring (ML Scoring)
   - Combines signals for final decision
5. **Decision Engine** makes final decision
6. **Result returned** to Transaction Service:
   - ✅ **Approve** → Transaction proceeds
   - ❌ **Block** → Transaction rejected immediately
   - ⚠️ **Review** → Sent to manual review queue

### Key Components
- **Rule Engine:** Fast rule-based pattern matching
- **ML Scoring:** ML model for risk scoring
- **Feature Store:** Real-time feature lookup
- **Decision Engine:** Combines rule + ML signals

---

## 5. Core Components

### 1️⃣ Transaction Service
- **Responsibility:** Starts the payment/transfer flow
- **Key actions:**
  - Receives transaction request
  - Calls Fraud Service for fraud check
  - Proceeds with approval only after fraud check passes

### 2️⃣ Fraud Detection Service
- **Responsibility:** Main entry point for fraud checks
- **Key actions:**
  - Orchestrates feature lookup from Feature Store
  - Runs rule-based checks via Rule Engine
  - Calls ML Scoring Service for risk score
  - Combines signals to make final decision

### 3️⃣ Feature Store
- **Responsibility:** Stores user/device/merchant/history signals
- **Examples of signals:**
  - Failed login attempts
  - Recent transaction velocity
  - Device risk history
  - Merchant risk profile
  - Geographic anomalies
  - Device fingerprint

### 4️⃣ Rule Engine
- **Responsibility:** Runs hard-coded business rules
- **Example rules:**
  - "Block if velocity > threshold"
  - "Block if new device + high amount"
  - "Flag if merchant in high-risk region"
- **Speed:** Millisecond-level decision making

### 5️⃣ ML Scoring Service
- **Responsibility:** Returns fraud risk score
- **Score range:**
  - 0.0 = Low risk (benign)
  - 1.0 = High risk (fraudulent)
- **Uses:** ML model trained on historical fraud data

### 6️⃣ Decision Engine
- **Responsibility:** Combines rules + ML score
- **Decision logic:**
  - Evaluates rule outputs
  - Considers ML risk score
  - Applies thresholds
- **Returns:** APPROVE, BLOCK, or REVIEW

### 7️⃣ Review Service
- **Responsibility:** Handles suspicious transactions manually
- **Key actions:**
  - Queues transactions flagged as REVIEW
  - Presents to fraud analysts with context
  - Records manual decisions for audit

### 8️⃣ Event Stream / Kafka Pipeline
- **Responsibility:** Publishes fraud events
- **Downstream consumers:**
  - Analytics pipeline
  - Audit logs
  - Model training data
  - Alerting systems

### 9️⃣ Audit / Decision Store
- **Responsibility:** Stores fraud decisions and metadata
- **Stores:**
  - Final decision (APPROVE/BLOCK/REVIEW)
  - Reason codes
  - ML model version used
  - Rule engine version used
  - Feature values used
  - Timestamp

---

## 6. API Design
- **POST /fraud/check** → evaluate a transaction and return decision
  - use Idempotency-Key header to avoid duplicate fraud decisions
- GET /fraud/checks/{transaction_id} → read fraud decision
  - returns decision, score, reason codes, rule/model version
- POST /fraud/reviews → create/update fraud rule
  - admin/risk team only
  - use Idempotency-Key header to avoid duplicate fraud decisions
- GET /fraud/checks/{transaction_id} → read fraud decision
  - returns decision, score, reason codes, rule/model version
- POST /fraud/reviews → create manual review case
  - used when decision is REVIEW
- POST /fraud/reviews/{review_id}/decision → analyst approves or blocks
  - final manual decision
- GET /fraud/users/{user_id}/risk-profile → read user risk signals
  - recent velocity, device history, blocked attempts, risk level
- POST /fraud/rules → create/update fraud rule
  - admin/risk team only


---

## 7. Data Model
- **fraud_checks**: Stores each fraud evaluation result ```id, transaction_id, user_id, decision, risk_score, status, created_at```
    - status: 
- **fraud_reasons**: Stores why a transaction was blocked/reviewed ```id, fraud_check_id, reason_code, description```
- **user_risk_profiles**: Stores user-level risk info ```user_id, risk_level, failed_attempts, recent_velocity, updated_at```
- **device_profiles**: Tracks device/IP behavior ```device_id, user_id, first_seen_at, last_seen_at, risk_score```
- **fraud_rules**: Stores configurable rules ```id, rule_name, condition, action, status, version```
- **review_cases**: Stores manual review workflow ```id, transaction_id, fraud_check_id, status, analyst_id, final_decision```
- **fraud_events**: Immutable event/audit log ```id, transaction_id, event_type, payload, created_at```


---

## 8. Fraud Rules vs ML Model
###  Rule Engine
- Uses clear business rules
- Easy to explain and audit
- Fast to run
- Good for known patterns

- **Example:**

- if amount > 5000 AND new_device = true → REVIEW
- if failed_attempts > 5 → BLOCK
###  ML Model
- Predicts fraud risk score
- Good for hidden/complex patterns
- Learns from historical data
- Harder to explain than rules

- **Example:**

- risk_score = 0.87

### Best approach

**Use hybrid:**
- Rules catch obvious fraud
- ML catches complex suspicious behavior
- Decision Engine combines both for final decision


---

## 9. Real-Time Detection Flow
### Diagram
                    Client / Payment App
                             ↓
                        API Gateway
                            ↓
                    Transaction Service
                                ↓
             ───────────Fraud Service  ────────────┐
            │                                      │
            │                                      ↓      
          Feature Store                          Rule Engine
         (user/device/history)                  (hard rules)
            │                                      ↓      
            └──────────────► ML Scoring ◄──────────┘
                                ↓
                        Decision Engine
                                ↓
            ┌──────────────────────────────────────────┐
            ↓                   ↓                      ↓
          APPROVE              BLOCK                  REVIEW
            ↓                    ↓                      ↓ 
            Transaction       Reject tx      Create Review Case
            Continues                          (manual review)
             ↓
            Event Stream / Kafka (async)
             ↓
            Audit Logs / Analytics / Model Training

### 🔄 **flow**
1. Transaction is created
2. Transaction Service calls ```/fraud/check```
3. Fraud Service:
   - fetches features (user, device, history)
   - runs rules
   - gets ML risk score
4. Decision Engine evaluates:
   - rules result + risk score
5. Return decision:
   - ✅ **APPROVE** → continue transaction
   - ❌ **BLOCK** → reject transaction
   - ⚠️ **REVIEW** → hold + create review case
6. Store:
   - decision + reason codes + model/rule version
7. Publish event to stream (analytics, training, audit)

---

## 10. Async Review / Investigation Flow
### When this happens
- Transaction is suspicious but not obviously fraud
- Real-time decision returns REVIEW
- Transaction is usually:
  - held
  - delayed
  - or limited until review finishes

### flow

    Fraud Decision = REVIEW
          ↓
    Create Review Case
          ↓
    Publish Review Event
          ↓
    Risk Analyst Dashboard
          ↓
    Analyst checks user/device/transaction history
          ↓
    Analyst decision:
        APPROVE or BLOCK
          ↓
    Update transaction status
          ↓
    Notify user / downstream systems
### Interview line

>“For uncertain cases, I don’t block immediately. I create a review case asynchronously, hold the transaction, and let a risk analyst make the final approve/block decision.”
---

## 11. Decision Engine

### 🎯 Role
Combines outputs from:
- Rule Engine
- ML Model
- Feature signals

**Produces final decision:**
- APPROVE
- BLOCK
- REVIEW

### ⚙️ How it Works (Simple Logic)

    IF rule = BLOCK → BLOCK
    ELSE IF risk_score > 0.9 → BLOCK
    ELSE IF risk_score between 0.6–0.9 → REVIEW
    ELSE → APPROVE


### 🧠 What to Highlight in Interview
- **Deterministic logic** (easy to reason about)
- **Configurable thresholds** (risk team can tune)
- **Priority:** Rules override ML (for safety)
- **Supports:** A/B testing of thresholds, model versioning

### 🎯 Interview Line
"The decision engine applies deterministic logic on top of rules and ML scores, with configurable thresholds to balance fraud detection and customer experience."

---

## 12. Feature Store
what is a feature store and why is it important in fraud detection?
it is a specialized data storage system that provides ;
### 🎯 what is a feature store?
- A centralized repository for storing and serving features used in fraud detection
- Provides real-time + historical features to Fraud Service
- Must be fast (low latency) and consistent

### 🧠 What it Stores (Examples)
- **User features:**
  - transactions_last_1h
  - failed_attempts_10min
- **Device features:**
  - is_new_device
  - device_risk_score
- **Transaction patterns:**
  - avg_amount_7d
  - merchant_frequency

### ⚙️ Two Types (VERY IMPORTANT)

#### 🔹 1. Online Feature Store (Real-Time)
- **Used during:** Fraud check
- **Latency requirement:** Must be < 10ms
- **Backed by:** Redis / low-latency KV store
- **Example:** `user_123 → {failed_attempts_10min: 3, is_new_device: true}`

#### 🔹 2. Offline Feature Store
- **Used for:**
  - ML model training
  - Analytics
- **Backed by:** Data warehouse (S3, BigQuery, etc.)
- **Latency:** Not time-sensitive (minutes+)

### 🔄 How Data Flows

**For ONLINE Feature Store:**
```
Transactions → Kafka → Stream Processing → Feature Store (Redis)
```
- Why? Fraud detection needs real-time updates
- Example: User makes 3 failed attempts → next transaction sees it immediately

**For OFFLINE Feature Store:**
```
Transactions → Kafka → Batch/Stream → Data Lake (S3/BigQuery)
```
- Used for training and analytics
- Not latency sensitive

### ⚡ Clean Mental Model

| Type    | Purpose              | Storage      | Latency  |
| ------- | -------------------- | ------------ | -------- |
| Online  | Real-time decisions  | Redis / KV   | ms       |
| Offline | Training / analytics | Data Lake    | minutes+ |


---

## 13. Event Streaming / Kafka Pipeline

### 🎯 Goal
- Capture every fraud-related event
- Update real-time features
- Support audit, analytics, model training
- Decouple real-time decision from slower systems

### 🔄 Main Flow
```
    Transaction Service
          ↓
    Kafka Topic: transaction_events
          ↓
    Stream Processor
    ┌─────────────────────┬──────────────┐
    ↓                     ↓              ↓
    Online Feature   Offline Store   Analytics / Audit
    Store            Data Lake       Dashboard
```

### 🧠 What Kafka is Used For
- **Update online feature store** → Real-time feature updates for next decisions
- **Store events for offline training** → Historical data for ML model training
- **Send events to audit/logging** → Immutable audit trail
- **Trigger review workflow** → Create review cases for flagged transactions
- **Feed monitoring dashboards** → Real-time fraud metrics and alerts

### 🎯 Interview Line
"Kafka is not in the critical fraud-decision path; it is used asynchronously to update features, audit decisions, trigger review workflows, and feed model training."

---

## 14. State Machine

### 🎯 Goal
Track the fraud decision lifecycle clearly, especially when a transaction goes to manual review.

### 📊 Fraud Decision States

```
             PENDING_CHECK
                   ↓
               CHECKED
      ┌───────────┼─────────────────┐
      ↓           ↓                 ↓
      APPROVED  BLOCKED    REVIEW_REQUIRED
                                   ↓
                            UNDER_REVIEW
                            ┌──────┴──────┐
                            ↓             ↓
                    REVIEW_APPROVED  REVIEW_BLOCKED
```

### 🧠 State Explanations
- **PENDING_CHECK** → Fraud evaluation started
- **CHECKED** → Rules/ML completed
- **APPROVED** → Transaction can continue
- **BLOCKED** → Transaction rejected
- **REVIEW_REQUIRED** → Needs analyst review
- **UNDER_REVIEW** → Analyst is investigating
- **REVIEW_APPROVED** → Analyst allows it
- **REVIEW_BLOCKED** → Analyst confirms block

### 📋 Which Tables Get Updated

**fraud_checks:**
- Updated with decision and status changes
- Flow: `PENDING_CHECK → CHECKED → APPROVED/BLOCKED/REVIEW_REQUIRED`

**review_cases:**
- Created/updated when REVIEW_REQUIRED
- Flow: `REVIEW_REQUIRED → UNDER_REVIEW → REVIEW_APPROVED / REVIEW_BLOCKED`

**fraud_events:**
- Emits events at each state change for audit and monitoring
- Flow: `PENDING_CHECK → CHECKED → APPROVED/BLOCKED/REVIEW_REQUIRED → UNDER_REVIEW → REVIEW_APPROVED / REVIEW_BLOCKED`

### ✅ Best Way to Explain in Interview
- **fraud_checks** → Current state (mutable)
- **fraud_events** → History (immutable)
- **review_cases** → Review workflow (mutable)

### 🎯 Interview Line
"I keep the current fraud state in fraud_checks, manage review lifecycle in review_cases, and store every transition in an immutable fraud_events table for audit and debugging."

---

## 15. False Positives / False Negatives

### 🎯 Goal
Fraud detection is not only about blocking fraud—it's about balancing fraud loss vs customer friction.

### 🔴 False Negatives
**What:** Fraud transaction goes through (System misses fraud)
- Fraud = YES
- System says = APPROVE

**Impact:**
- Money loss
- Chargebacks
- Customer trust damage

### 🟡 False Positives
**What:** Real customer gets blocked (System is too strict)
- Fraud = NO
- System says = BLOCK / REVIEW

**Impact:**
- Bad user experience
- Lost revenue
- Customer support cost

### ✅ How to Reduce Both
- Use REVIEW state for uncertain cases
- Tune ML thresholds carefully
- Add risk-based rules without over-blocking
- Monitor fraud rate and block rate continuously
- Use feedback from manual reviews
- Retrain model using confirmed fraud data

---

## 16. Scalability

### 🎯 Goal
Handle growing transaction volume without increasing latency.

### 🔹 Key Strategies

1. **Horizontal Scaling**
   - Scale Fraud Service statelessly
   - Add more instances behind load balancer

2. **Feature Store Scaling**
   - Use Redis cluster / sharding
   - Partition by: user_id, device_id

3. **Rule Engine Scaling**
   - Keep rules in-memory
   - Use rule cache
   - Avoid DB calls in hot path

4. **ML Scaling**
   - Preload model in memory
   - Use lightweight model or model service with autoscaling

5. **Kafka Scaling**
   - Partition topics by: user_id or transaction_id
   - Enables parallel consumers

6. **Avoid Bottlenecks (VERY IMPORTANT)**
   - Critical path must be: `Fraud Service → Feature Store → Rules + ML → Decision`
   - ❌ No synchronous DB writes
   - ❌ No external blocking calls
   - ❌ No heavy computation

### ⚠️ Hotspot Handling
- **Problem:** High-risk users/merchants → heavy traffic
- **Solutions:**
  - Caching
  - Rate limiting
  - Load-aware routing

---

## 17. Caching

### 🎯 Goal
Reduce latency in the critical fraud path and avoid repeated expensive lookups.

### 🔹 What to Cache
- **User risk profile:** Recent velocity, failed attempts
- **Device profile:** Known/new device, risk score
- **Fraud rules:** Keep in memory (very important)
- **Model artifacts:** Loaded in memory (not fetched per request)

### ⚙️ Where to Cache
- In-memory cache (service level)
- Redis (distributed cache)

### 🧠 Cache Pattern
**Read-through cache:**
```
Fraud Service → Cache → DB (fallback)
```

### 🔄 Cache Invalidation
- **On new transaction:** Update feature store → update cache
- **On rule change:** Refresh rule cache
- **TTL for safety:** Avoid stale data (balance between freshness and perf)

### ⚠️ Trade-off
- Slightly stale data vs low latency
- ✅ Acceptable in fraud detection (streaming updates help)

### 🎯 Interview Line
"I cache user and device features and keep rules in memory to minimize latency, using read-through caching with TTL and updates from streaming pipelines."

---

## 18. Consistency & Reliability

### 🎯 Goal
Make sure fraud decisions are safe, repeatable, and auditable.

### 🔑 Key Points
- **Idempotency:** Same transaction_id should not create multiple fraud decisions
- **Decision consistency:** Once BLOCKED, don't later approve it accidentally
- **Immutable audit log:** Store every decision/change in fraud_events
- **Retry-safe design:** Retries return existing decision, not recalculating randomly
- **Graceful degradation:**
  - If ML is down → fall back to rules
  - If feature store is down → use conservative default or REVIEW
- **At-least-once events:** Kafka events may repeat, so consumers must be idempotent

### 🎯 Interview Line
"I make fraud checks idempotent, store immutable decision events, and design retries so the same transaction always gets a consistent decision."

---

## 19. Security

### 🎯 Goal
Protect sensitive transaction/user data and prevent attackers from abusing the fraud system.

### 🔑 Key Points

- **PII Protection:**
  - Encrypt sensitive fields
  - Mask logs
  - Limit access

- **AuthN/AuthZ:**
  - Only trusted services call `/fraud/check`
  - Only risk team can update rules/review cases

- **Data Minimization:**
  - Store only what fraud needs

- **Audit Logging:**
  - Track who changed rules or review decisions

- **Abuse Protection:**
  - Rate limit fraud APIs
  - Detect suspicious repeated checks

- **Model/Rule Protection:**
  - Don't expose exact fraud rules to users

### 🎯 Interview Line
"Security is critical because fraud systems handle sensitive data and attackers may try to reverse-engineer rules, so I protect PII, lock down access, and audit all rule/review changes."

---

## 20. Observability & Monitoring

### 🎯 Goal
Know how well the fraud system is working and catch issues fast.

### 📊 Business Metrics (VERY IMPORTANT)
- **Fraud rate:** Fraud / total transactions
- **False positive rate:** Real tx blocked / total tx
- **False negative rate:** Fraud slipped through / fraud tx
- **% of transactions:**
  - Approved
  - Blocked
  - Sent to review
- **Review queue size & SLA**

### ⚙️ System Metrics
- **API latency:** /fraud/check response time
- **QPS:** Throughput
- **Error rate:** Failures
- **Feature store latency:** Speed of feature lookups
- **ML model response time:** Scoring latency

### 🔍 Logs & Tracing
- **Decision logs:** With reason codes
- **Distributed tracing:** Transaction → Fraud → Feature → ML

### 🚨 Alerts
- Sudden spike in:
  - BLOCK rate
  - APPROVE rate (fraud leak!)
  - Latency increase
  - Feature store failures
  - ML service downtime

---

## 21. Trade-offs & Alternatives

### 🎯 Goal
Show you can reason, not just design.

### 🔹 1. Real-time vs Async Detection

| Option      | Pros                   | Cons                          |
| ----------- | ---------------------- | ----------------------------- |
| Real-time   | Immediate protection   | Adds latency                  |
| Async only  | No impact on latency   | Fraud may go through ❌        |

**👉 Choice: Hybrid**
- Real-time for critical checks
- Async for deep analysis

### 🔹 2. Rules vs ML

| Option | Pros           | Cons                  |
| ------ | -------------- | --------------------- |
| Rules  | Simple, explainable | Misses complex patterns |
| ML     | Detects patterns | Harder to explain |

**👉 Choice: Hybrid (best practice)**

### 🔹 3. Strict vs Lenient Blocking

| Strategy | Pros         | Cons                     |
| -------- | ------------ | ------------------------ |
| Strict   | Less fraud   | More false positives ❌  |
| Lenient  | Better UX    | More fraud ❌            |

**👉 Choice: Use REVIEW as middle ground**

### 🔹 4. Fresh Data vs Low Latency

| Option       | Pros        | Cons              |
| ------------ | ----------- | ----------------- |
| Always fresh | Accurate    | Slow ❌           |
| Cached       | Fast        | Slightly stale data |

**👉 Choice: Cached + streaming updates**

### 🔹 5. Centralized vs Distributed Features

| Option       | Pros      | Cons        |
| ------------ | --------- | ----------- |
| Centralized  | Simple    | Bottleneck ❌ |
| Distributed  | Scalable  | Complex     |

**👉 Choice: Distributed (Redis + streaming)**

### 🎯 Interview Line
"Most decisions are trade-offs between latency, accuracy, and user experience, so I use hybrid approaches like rules + ML and real-time + async processing."

---

## 22. Wrap-Up Cheat Sheet

### 🧠 One-Line Story
"A real-time fraud system that enriches transactions with features, evaluates rules and ML scoring, makes a decision, and uses streaming pipelines for learning and audit."

### 🔄 Core Flow
```
Transaction → Fraud Service → Features + Rules + ML → Decision → Response
    ↓
  Kafka
    ↓
Audit / Training / Analytics
```

### 🏗️ Key Components
- Fraud Service (orchestrator)
- Feature Store (online + offline)
- Rule Engine
- ML Scoring
- Decision Engine
- Kafka (async)
- Review System
- Audit Store

### ⚡ Must Say in Interview
- ✅ Idempotent fraud check
- ✅ Low latency (<100ms)
- ✅ Hybrid rules + ML
- ✅ Feature store (online/offline)
- ✅ Async pipeline (Kafka)
- ✅ REVIEW flow (reduce false positives)
- ✅ Immutable audit log
- ✅ Handle timeouts gracefully

### 🚀 Final Interview Line
"I designed a low-latency fraud detection system that balances accuracy and user experience using a hybrid decision engine, real-time feature enrichment, and asynchronous pipelines for scalability and learning."
