# Desing Payment Processing System

## Table of Contents
1. [Problem Statement](#1-problem-statement)
2. [Requirements & Goals](#2-requirements--goals)
3. [BOE / Capacity Estimation](#3-boe--capacity-estimation)
4. [High-Level Design](#4-high-level-design)
5. [System APIs Design](#5-system-apis-design)
6. [Data Model](#6-data-model)
7. [Component Design - Payment Flow](#7-component-design--payment-flow)
8. [Retry and Failure Handling + State Machine](#8-retry-and-failure-handling--state-machine)
9. [Idempotency Deep Dive](#9-idempotency-deep-dive)
10. [Reconciliation](#10-reconciliation)
11. [Consistency & Correctness Guarantees](#11-consistency--correctness-guarantees)
12. [Exactly-Once vs At-Least-Once](#12-exactly-once-vs-at-least-once)
13. [Queue / Async Processing Design](#13-queue--async-processing-design)
14. [Sharding/Scaling DB](#14-shardingscaling-db)
15. [Security (PCI Compliance, Tokenization, Encryption)](#15-security-pci-compliance-tokenization-encryption)
16. [Trade-offs and Alternatives](#16-trade-offs-and-alternatives)
17. [Wrap Up Cheat Sheet](#16-wrap-up-cheat-sheet)


---

## 1. Problem Statement
We need to design a payment processing system that: 
1. Accept payment requests, 
2. Prevent duplicate transactions,
3. Interact with external payment provider
4. Maintain accurate ledger of all transactions for auditing and reconciliation purposes.
5. Support high throughput and low latency for real-time processing.

---

## 2. Requirements & Goals

### Functional Requirements:
1. Accept payment requests (amount, payer, payee, etc.)
2. Prevent duplicate transactions (idempotency)
3. Interact with external payment providers (APIs)
4. Maintain accurate ledger of all transactions

### Non-Functional Requirements:
1. High throughput (handle many transactions per second)
2. Low latency (real-time processing)
3. Scalability (handle growth in users and transactions)
4. Reliability (ensure transactions are processed correctly)
5. Security (protect sensitive data, prevent fraud)

---

## 3. BOE / Capacity Estimation
- **Assumption:**
    - 10 Million user daily
    - 2 payments per user per day
    - Average transaction size =  2KB
- Dail transaction = 10 M * 2 = 20 M transactions per day
- write QPS = 20 M/(24*3600) = 231 transactions per second
- Peak QPS = 231 * 10 = 2310 transactions per second
- Storage estimation = 20 M * 2K B = 40 M KB = 40 GB per day / 40 GB * 365 = 14.6 TB per year





---

## 4. High-Level Design
        
        Client / Merchant
        ↓
        API Gateway
        ↓
        Payment Service
        ↓
        Idempotency Store
        ↓
        Transaction DB
        ↓
        External Payment Provider
        ↓
        Ledger Service
        ↓
        Queue
        ↓
        Notification / Reconciliation

what each Component does:
1. **Client / Merchant**: Initiates payment requests.
2. **API Gateway**: Entry point for all payment requests, handles routing, authentication,
3. **Payment Service**: Core service that processes payment requests, 
4. **Idempotency Store**: Stores idempotency keys to prevent duplicate transactions.
5. **Transaction DB**: Database to store transaction details and status.
6. **External Payment Provider**: Third-party service that processes payments.
7. **Ledger Service**: Maintains an accurate ledger of all transactions for auditing and reconciliation purposes.
8. **Queue**: Asynchronous processing of tasks such as notifications and reconciliation.
9. **Notification / Reconciliation**: Handles sending notifications to users and performing reconciliation of transactions

---

## 5. System APIs Design
    Payments API
    ├── POST /payments : 
    ├── POST /payments/{payment_id}/capture: 
    ├── POST /payments/{payment_id}/refund
    └── GET  /payments/{payment_id}

how each endpoint works:
1. **POST /payments**: Accepts a payment request with details such as amount, payer, payee, and idempotency key. Returns a payment ID and status.
2. **POST /payments/{payment_id}/capture**: Captures a previously authorized payment. 
3. **POST /payments/{payment_id}/refund**: Initiates a refund for a completed payment.
4. **GET /payments/{payment_id}**: Retrieves the status and details of a specific payment using its payment ID.


---

## 6. Data Model
Core tables:
1. **Payments Table**: Stores payment details ```(payment_id, amount, payer_id, payee_id, status, timestamp, etc.)```.
2. **Payment events Table**: Stores events related to payments for tracking the payment lifecycle ```(event_id, payment_id, event_type, timestamp, details)```."
3. **Idempotency Table**: Stores idempotency keys and their associated payment IDs to prevent duplicate transactions. ```(idempotency_key, merchant_id, request_hash, payment_id, response_body, status, expires_at)```.
4. **Ledger Table**: Maintains a record of all transactions for auditing and reconciliation purposes ```(ledger_id, payment_id, amount, timestamp, status). ```



---

## 7. Component Design - Payment Flow
        Client
            ↓
        API Gateway
            ↓
        Payment Service
            ↓
        Idempotency Check
            ↓
        Create payment = INITIATED
            ↓
        Call Provider
            ↓
        Update status
            ↓
        Write Ledger
            ↓
        Publish Event
Main Steps:

| Step | Action                                                                                                         |
| ---- |----------------------------------------------------------------------------------------------------------------|
| 1    | Client sends `POST /payments` with `Idempotency-Key`                                                           |
| 2    | Payment Service checks idempotency  store for existing key and handles accordingly (return existing response, or create new record) |
| 3    | Create payment row as `INITIATED`                                                                              |
| 4    | Call external provider for authorization/capture (sync or async)                                               |
| 5    | Update status: `AUTHORIZED`, `CAPTURED`, `FAILED`, or `PENDING`  in  payments table based on provider response |
| 6    | If captured, write ledger entries                                                                              |
| 7    | Publish event to queue for notification/reconciliation                                                         |




---

## 8. Retry and Failure Handling + State Machine
- **State machine for payment lifecycle:**
  
      INITIATED → AUTHORIZED → CAPTURED → SETTLED
  
         ↓           ↓            ↓
      FAILED      FAILED      REFUNDED
      
      PENDING / UNKNOWN = provider result unclear

- Failure rules:

  | Failure                         | Action                               |
  | ------------------------------- | ------------------------------------ |
  | Client retry                    | Return same result using idempotency |
  | Provider declined               | Mark `FAILED`                        |
  | Provider timeout                | Mark `PENDING / UNKNOWN`             |
  | DB failure before provider call | Safe to retry                        |
  | DB failure after provider call  | Reconciliation required              |
  | Queue failure                   | Retry / DLQ                          |

---

## 9. Idempotency Deep Dive

- **Goal:** Ensure that multiple identical requests result in only one transaction being processed.
  - **where it happens:**

        Payment Service
            ↓
        Idempotency Store

- Lookup key:
  - merchant_id + idempotency_key (or client_id + idempotency_key)
- cases:

    | Case                         | Action                         |
    | ---------------------------- | ------------------------------ |
    | Key not found                | Create `IN_PROGRESS`, continue |
    | Same key + same request      | Return saved response          |
    | Same key + different request | Return `409 Conflict`          |
    | Key in progress/pending      | Return `PENDING`               |


---

## 10. Reconciliation
- **Goal:** fix Payment stuck in `PENDING` or `UNKNOWN` state 
- **when needed:** provider timeout - we don't know if payment succeeded or failed
 - **flow:**

        Reconciliation Job
            ↓
        Query payments with PENDING/UNKNOWN status
            ↓
        Call provider API for each payment to get latest status
            ↓
        Update payment status and ledger accordingly

- **Decision table:**

| Provider result | Action                                   |
| --------------- | ---------------------------------------- |
| `SUCCESS`       | Mark `CAPTURED`, write ledger if missing |
| `FAILED`        | Mark `FAILED`                            |
| `NOT_FOUND`     | Safe to retry                            |
| `PENDING`       | Check again later                        |




---

## 11. Consistency & Correctness Guarantees

- **Goal:** Don't double charge. Don't lose money. Always explain what happened.
- **core rules:**

  | Rule                        | How                             |
  | --------------------------- | ------------------------------- |
  | No duplicate charge         | Idempotency key                 |
  | Money must balance          | Double-entry ledger             |
  | Unknown failures            | Mark `PENDING`, reconcile later |
  | No duplicate ledger entries | Unique constraints              |

- **Strong consistency vs Eventual consistency:**

  | Strong consistency | Eventual consistency   |
  | ------------------ | ---------------------- |
  | Payment state      | Notifications          |
  | Ledger writes      | Analytics              |
  | Idempotency record | Reconciliation results |




---

## 12. Exactly-Once vs At-Least-Once
- **Meaning:** 
  - **Exactly-once:** Each payment is processed once and only once, no duplicates, no missing transactions.(ideal, but hard to achieve in distributed systems)
  - **At-least-once:** Each payment is processed at least once, (real world)
- **Reality:** Systems are at-least-once -> duplicate can happen
- **Solution:** Idempotency → returns same payment → no double charg




---

## 13. Queue / Async Processing Design

- **Goal:** Decouple non-critical work and make system more resilient and scalable.
- **where it fits:**

          After Payment  + ledger write
              ↓
            Queue
              ↓
          consumer(notification/reconciliation, analytics, etc)



---


## 14. Sharding/Scaling DB
Goal: handle write throughput and bottele necks as system grows.
- Sharding strategy:
  - Horizontal sharding by 
    - `payment_id`
      - pros: simple, good for write scaling, avoid hostspots
    - `merchant_id` or `payer_id`
      - pros: group merchant data together, good for queries by merchant/payer
      - cons: potential hotspots if some merchants/payers are much more active
    better solution:
      - share by `payment_id` for main payments table (write-heavy)
      - handle hot merchant using caching and read replicas
      
  


---

## 15. Security (PCI Compliance, Tokenization, Encryption)

- Goal: Protect sensitive data, and reduce compliance risks.
- Core rules

| Area           | Rule                                |
| -------------- | ----------------------------------- |
| Card data      | Never store raw card number / CVV   |
| Payment method | Store tokenized `payment_method_id` |
| Data security  | Encrypt in transit + at rest        |
| Access         | Strict auth, RBAC, audit logs       |
| Compliance     | Use PCI-compliant provider/vault    |



---
## 16. Trade-offs and Alternatives

### **Sync vs Async Processing**

   | Option                         | Pros                             | Cons                                     |
   | ------------------------------ | -------------------------------- | ---------------------------------------- |
   | **Sync provider call**         | Immediate response, simpler flow | Higher latency, provider can slow system |
   | **Async processing via queue** | Better scalability/resilience    | More complexity, eventual consistency    |

 - **👉 Common approach:**
    - critical payment flow = sync
    - notifications/reconciliation = async
---
- **Strong vs Eventual Consistency**
  
  | Option                   | Pros                           | Cons                               |
  | ------------------------ | ------------------------------ | ---------------------------------- |
  | **Strong consistency**   | Correct balances/states        | Higher latency, lower availability |
  | **Eventual consistency** | Better scalability/performance | Temporary stale state              |

 - **👉 Use:**
   - strong consistency for payment + ledger
   - eventual for notifications/analytics
---
### **Exactly-once vs At-least-once**

  | Option                          | Pros                  | Cons                         |
  | ------------------------------- | --------------------- | ---------------------------- |
  | **Exactly-once**                | No duplicates ideally | Very hard/expensive          |
  | **At-least-once + idempotency** | Practical, resilient  | Requires deduplication logic |

  - **👉 Real systems use:**
    - at-least-once delivery
    - idempotent processing 
### Authorization + Capture vs Immediate Capture

| Option                | Pros                      | Cons                   |
| --------------------- | ------------------------- | ---------------------- |
| **Auth + Capture**    | Flexibility, fraud checks | More states/complexity |
| **Immediate capture** | Simpler flow              | Less flexibility       |

- **👉 Use auth/capture for:**
  - hotels
  - Uber
  - e-commerce
---
### Sharding by payment_id vs merchant_id

| Option          | Pros                       | Cons                    |
| --------------- | -------------------------- | ----------------------- |
| **payment_id**  | Uniform write distribution | Merchant queries harder |
| **merchant_id** | Better merchant locality   | Hot merchants problem   |

- **👉 Common choice:**
  - shard by payment_id
  - mitigate hot merchants with cache/replicas
---
### Ledger Inline vs Async

| Option                     | Pros               | Cons                           |
| -------------------------- | ------------------ | ------------------------------ |
| **Inline ledger write**    | Strong consistency | Higher latency/coupling        |
| **Async ledger via queue** | Better throughput  | More reconciliation complexity |

- **👉 Financial systems usually prefer:**
  - inline or transactional ledger writes
---

---


## 17. Wrap Up Cheat Sheet
Problem
→ Safe, reliable payment processing with auditability

Requirements
→ create, authorize, capture, refund, status + idempotency
→ reliability, consistency, availability, security

BOE
→ ~20M/day → ~200 QPS avg → ~1K peak
→ ~40GB/day → ~15TB/year
→ correctness > scale

High-Level
→ Gateway → Payment Service → Idempotency → DB → Provider → Ledger → Queue

APIs
→ POST /payments (+ Idempotency-Key)
→ capture / refund / status

Data Model
→ payments (state)
→ payment_events (history)
→ ledger_entries (money)
→ idempotency_keys (retry safety)

Flow
→ check idempotency → create INITIATED → call provider → update → ledger → event

Failure
→ timeout ≠ failure → PENDING
→ retry safe via idempotency

Idempotency
→ same key = same result
→ prevents double charge

Reconciliation
→ fix PENDING by checking provider
→ eventual correctness

Consistency
→ strong: payment + ledger
→ eventual: notification, analytics

Exactly-once
→ not possible → use at-least-once + idempotency

Queue
→ async, retry, idempotent consumers, DLQ

Scaling
→ shard by payment_id

Security
→ tokenization, no raw card data, encryption


