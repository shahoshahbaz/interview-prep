# Design Digital Wallet System

## Table of Contents
1. [Problem Statement](#1-problem-statement)
2. [Requirements & Goals](#2-requirements--goals)
3. [Back-of-the-Envelope Estimation (BOE)](#3-back-of-the-envelope-estimation-boe)
4. [High-Level Architecture](#4-high-level-architecture)
5. [System APIs](#5-system-apis)
6. [Data Model](#6data-model) 
7. [Core Components](#7-core-components)
8. [Transaction Flows](#8-transaction-flows)
9. [Balance Management](#9-balance-management)
10. [Consistency & Correctness](#10-consistency--correctness)
11. [Idempotency](#11-idempotency)
12. [Failure Handling & Reconciliation and state machine](#12-failure-handling--reconciliation)
13. [Concurrency Control](#13-concurrency-control) 
14. [Scalability](#14-scalability)
15. [Caching](#15-caching)
16. [Security & Fraud Prevention](#16-security--fraud-prevention)
17. [Observability & Monitoring](#17-observability--monitoring)
18. [Trade-offs & Alternatives](#18-trade-offs--alternatives)
19. [Wrap Up Cheat Sheet (Summary)](#19-wrap-up-cheat-sheet-summary)

---

## 1. Problem Statement
Design a digital wallet that:
- Allows users to store money digitally and make payments.
- Supports features like:
  - adding money:
  - sending money to other users,
  - paying merchants,
  - transaction history,
  - balance management
  - Ensures security, reliability, and scalability.

---

## 2. Requirements & Goals
### Functional Requirements
- add money to wallet
- send money to other users
- pay merchants using balance in wallet
- view transaction history
- check wallet balance
- prevent duplicate transactions
- maintain accurate transaction records(ledger)
### Non-Functional Requirements
- Strong consistency (no incorrect balances)
- High reliability (no money loss or duplication)
- Scalability (handle growth in users and transactions)
- Low latency for balance check and payments
- High availability (wallet should always be accessible)
- Security (protect user data and prevent fraud)


---

## 3.  Back-of-the-Envelope Estimation (BOE)
 ### Assumptions:
  - 10 million users
  - Each user performs:
    - 1 balance check per day
    - 2 transactions (send money, pay merchant) per day
    - Average transaction size = 2 KB
    - Peak mulitplier: 10X

- Traffic Estimation:
  - Daily write transactions = 10 M  * 2 tx  = 20 M tx/day
  - Daily read transactions (balance checks) = 10 M users * 1 balance check= 10 M balance checks/day
  - Write QPS = 20 M * (100,000) ≈ 200 QPS
  - peak write QPS (transactions) = 200 * 10 = 2000 QPS
  - Read QPS = 10M* (100,000) ≈ 100 QPS
  - Peak QPS (balance checks) = 100 * 10 = 1000 QPS
  - 
- storage Estimation:
   - Daily data = 20 M * 2KB = 40 GB a day
   - yearly data = 40 GB/day * 365 ≈ 14.6 TB/year
   - storage for 7 years = 14.6 TB/year *7 = 102.2 TB



---

## 4.  High-Level Architecture

### Flow:

    Client / Mobile App
        ↓
    API Gateway
        ↓
    Wallet Service
        ↓
    Idempotency Store
        ↓
    Wallet DB / Transaction DB
        ↓
    Ledger Service
        ↓
    Queue
        ↓
    Notification / Reconciliation

## 5. System APIs

    Wallet API
    ├── POST /wallets/top-up
    ├── POST /wallets/transfer
    ├── POST /wallets/pay
    ├── POST /wallets/withdraw
    ├── GET  /wallets/{wallet_id}/balance
    └── GET  /wallets/{wallet_id}/transactions

How each endpoint works:
1. POST /wallets/top-up: User adds money to wallet from bank account or card
2. POST /wallets/transfer: User sends money to another user's wallet
3. POST /wallets/pay: User pays a merchant using wallet balance
4. POST /wallets/withdraw: User withdraws money from wallet to bank account or card
5. GET /wallets/{wallet_id}/balance: User checks current wallet balance
6. GET /wallets/{wallet_id}/transactions: User views transaction history for wallet

---

## 6.Data Model

Core tables:

- **Wallets:** Stores current wallet state and balance.```(wallet_id, user_id, currency, available_balance, status, version, created_at, updated_at)```
- **Wallet Transactions**:Stores user-facing transactions.```(transaction_id, wallet_id, transaction_type, amount, currency, status, created_at)```
- **Ledger Entries**:Source of truth for money movement.```(ledger_entry_id, transaction_id, account_id, direction, amount, currency, created_at)```
- **Idempotency**:Prevents duplicate operations.```(idempotency_key, user_id, request_hash, transaction_id, status, response_body, created_at)```
- **Wallet Events**: Stores lifecycle/history events.```(event_id, transaction_id, event_type, old_status, new_status, metadata, created_at)```

---

## 7. Core Components

- **Client / Mobile App**: Starts wallet actions like top-up, transfer, pay, withdraw, balance check.
- **API Gateway**: Handles authentication, routing, rate limiting, and request validation.
- **Wallet Service**: Main orchestrator for wallet operations.
- **Ledger Service**: Source of truth for all money movement using double-entry accounting.
- **Transaction Service**: Tracks transaction lifecycle: INITIATED, PROCESSING, COMPLETED, FAILED, PENDING.
- **Payment Provider / Bank Integration Service**: Handles top-up and withdrawal with external banks/cards/payment networks.
- **Idempotency Store**: Prevents duplicate top-ups, transfers, payments, or withdrawals.
- **Fraud / Risk Service**: Checks suspicious activity before allowing risky transactions.
- **Queue / Event Bus**: Sends async events for notifications, analytics, reconciliation, audit.
- **Notification Service**: Sends push/email/SMS transaction alerts.
- **Reconciliation Service**: Fixes transactions stuck in PENDING or UNKNOWN.
- **Wallet DB / Ledger DB**: Stores wallets, transactions, ledger entries, events, and idempotency records.

---

## 8. Transaction Flows
- Wallet Flow(Transaction Flow):

          Client
            ↓
          API Gateway
            ↓
          Wallet Service
            ↓
          Idempotency Check
            ↓
          Validate Wallet + Balance
            ↓
          Create Transaction = INITIATED
            ↓
          Write Ledger Entries
            ↓
          Update Wallet Balance
            ↓
          Publish Event

- Main Steps:
- Cline send request to API Gateway with Idempotency key
- API Gateway authenticates and routes to Wallet Service
- Wallet Service checks Idempotency Store for duplicate request
- If not duplicate, Wallet Service validates wallet and balance
- Wallet Service creates transaction with status INITIATED
- Wallet Service writes ledger entries(double-entry) for transaction
- Wallet Service updates wallet balance based on ledger entries
- Wallet Service publishes event for transaction lifecycle




---

## 9. Balance Management

### Core Idea:
  - Ledger = source of truth
  - Wallet balance = fast-access view
  
### Type of balance:
  - Available Balance = Money user can spend immediately
  - Pending Balance = Money in-flight(e.g. withdrawal not settled yet)
    - How Balance is Updated:

           Transaction request
              ↓
           Write ledger entries (debit/credit)
              ↓
           Update wallet.available_balance
### Rules:
  - **Double-Entry rule:** Every Transaction must have equal debit and credit ledger entries
  - **prevent Double Spending:** Ensure available balance is sufficient before allowing spending
    - use optimistic locking (version column)
    - use pessimistic locking (row-level locks)
  - **Consistency Rule**: wallet balance update + ledger write must be
    - atomic (transactional) to prevent incorrect balances
    - idempotent to prevent duplicates
  - **Rebuild strategy:**
    - if balance is corrupted: ```Recalculate balance = SUM(ledger entries)``` and fix wallet balance
    > 👉 Ledger always wins
      




---

## 10. Consistency & Correctness
### Correctness:
 - Don't lose the money
 - Don't create money
 - Always explain what happened.

### **key Guarantees:**

  | Rule                       | How                                |
  | -------------------------- | ---------------------------------- |
  | No double spending         | Balance check + locking/versioning |
  | No duplicate transactions  | Idempotency key                    |
  | Money must balance         | Double-entry ledger                |
  | No missing transactions    | Durable writes + retries           |
  | Recover from unknown state | Reconciliation                     |


### **Strong vs Eventual Consistency:**

| Strong Consistency  | Eventual Consistency |
| ------------------- | -------------------- |
| Wallet balance      | Notifications        |
| Ledger entries      | Analytics            |
| Transaction status  | Reporting            |
| Idempotency records | Fraud signals        |

### **Atomicity (Critical):**
    - **Ledger write + balance** update must be:
      - Atomic (same DB transaction) OR
      - Recoverable (idempotent + retry + reconciliation)


---

## 11. Idempotency 
### Goal:  Ensure that retries do not create duplicate transactions (no double charge / no double transfer
### where it happens:
    
           Client
            ↓
           API Gateway
            ↓
           Wallet Service
            ↓
           Idempotency Store
### How it works:
  - Client generates unique idempotency key for each request (e.g. UUID)
  - System uses idempotency key + user_id to check if request has been processed before
  - Question: why we use request_hash in idempotency store?
    - Answer:
      - smaller storage: hash is fixed size, while request payload can be large
      - Faster comparison: comparing hash is faster than comparing full request payload
      - Security / compliance: may contains sensitive data
  
        - 
          - hash of request payload (amount, recipient, etc.)
          - used to detect if same key is reused with different request (potential bug or attack)
  - 
  - Cases:
    
    | Case                         | Action                                  |
    | ---------------------------- | --------------------------------------- |
    | Key not found                | Create record (`IN_PROGRESS`), continue |
    | Same key + same request      | Return saved response                   |
    | Same key + different request | Return `409 Conflict`                   |
    | Key in progress              | Return `PENDING`                        |
  ### Data Stored:
  - **Idempotency Store record**:```(idempotency_key, user_id, request_hash, transaction_id, status, response_body, created_at)```
  - **Flow**:

            Request comes in
            ↓
            Check idempotency store
            ↓
            If exists → return saved response
            If not → process transaction
            ↓
            Save result in idempotency store
  ### Why Idempotency is Critical:
  - Without Idempotency:      ```Retry → double debit → money loss ❌```
  - With Idempotency:       ```Retry → no double debit → money safe ✅```


## 12. Failure Handling & Reconciliation and state machine

### Goal: Handle failures safely and ensure eventual correctness when system state is uncertain.
### State Machine: there are state for transaction, 

      
    INITIATED → PROCESSING →COMPLETED
      ↓             ↓            
    FAILED       FAILED        
    
    PROCESSING  →  PENDING/ UNKNOWN → RECONCILIATION → COMPLETED/FAILED  
- what each state Means:

| State             | Meaning                                    |
| ----------------- | ------------------------------------------ |
| INITIATED         | Transaction created                        |
| PROCESSING        | In progress (ledger / external call)       |
| COMPLETED         | Successfully applied                       |
| FAILED            | Final failure                              |
| PENDING / UNKNOWN | Result unclear (timeout / partial failure) |

### Failure cases:

| Scenario                        | What happens                               |
| ------------------------------- | ------------------------------------------ |
| Client retries                  | Same request may hit system multiple times |
| Service crash before completion | Partial work done                          |
| DB write fails                  | Ledger/balance inconsistency risk          |
| External provider timeout       | Don’t know success/failure                 |
| Queue/event failure             | Async tasks not executed                   |

- How we handle failures
    - Idempotency:
        - Prevent duplicate transactions on retry
    - State machine control:
      - only allow valid transitions
      - prevent invalid states(e.g., COMPLETED → PROCESSING ❌)
    - Mark Unknow:
      - Provider timeout → mark PENDING / UNKNOWN 
### Reconciliation (Recovery Mechanism):

### Flow:
    Reconciliation Job
          ↓
    Scan transactions with PENDING / UNKNOWN
          ↓
    Check:
      - Ledger entries
      - External provider (if applicable)
          ↓
        Determine final outcome
          ↓
        Update transaction state

### Decision Table:

  | Condition                      | Action                          |
  | ------------------------------ | ------------------------------- |
  | Ledger exists (money moved)    | Mark COMPLETED                  |
  | No ledger + provider failed    | Mark FAILED                     |
  | Provider success but no ledger | Write missing ledger + COMPLETE |
  | Still unclear                  | Retry later                     |

---

## 13. Concurrency Control
### **Goal:**
Prevent race conditions and double-spending when multiple requests hit the same wallet at the same time.
### Solutions:
  - **Optimistic Locking(preferred)**
    - Add version column to wallet table
    - and update the query
  - **Pessimistic Locking:**
    - Lock rwo during transaction
    - Safe but can reduce throughput
  - **Atomic DB update:**
    - Ensures:
      - no negative balance
      - Atomic check + update


---

## 14. Scalability

### **Goal:**
  - Handle Growth in:
    - users
    - wallets
    - transactions
    - balance reads
    - transaction history read
    - async background work
### Scaling Strategy:
  | Layer                | Scaling Approach                            |
  | -------------------- | ------------------------------------------- |
  | API Gateway          | Run multiple instances behind load balancer |
  | Wallet Service       | Stateless service, scale horizontally       |
  | Ledger Service       | Stateless service, scale horizontally       |
  | Wallet Table         | Shard by `wallet_id`                        |
  | Transaction Table    | Shard by `transaction_id` or `wallet_id`    |
  | Ledger Entries Table | Shard by `wallet_id` or `account_id`        |
  | Balance Reads        | Cache + read replicas                       |
  | Async Work           | Queue + multiple consumers                  |

- **Queue/Async Processing:** Use queue for non-critical work:

      Wallet Service
         ↓
      Queue / Event Bus
        ↓
      Notification / Analytics / Reconciliation / Fraud Consumers

- Async task:
  - notifications
  - analytics
  - audit events
  - fraud signals
  - reconciliation jobs
- Critical path stays sync:
  - idempotency check
  - balance validation
  - ledger write
  - wallet balance update
  - transaction status update


---

## 15. Caching
### Goal:
  - Reduce read latency
  - Offload DB for frequent reads
### What to Cache:
  - Wallet metadata ✅
  - Transaction history (recent) ✅
  - Balance ⚠️ (only for display, short TTL)
### Cache Pattern:
      
      Wallet Service
      ↓
      Cache (Redis)
      ↓ miss
      DB
### Cache Invalidation:

      On transaction success:
      → Update DB
      → Invalidate / update cache



---

## 16. Security & Fraud Prevention
### **Goal:**
  - Protect user funds and data
  - Detect and prevent fraudulent transactions
## **Security Measures:**
  - Authentication & Authorization (JWT, OAuth, RBAC)
  - Encryption (in transit + at rest)
  - Tokenization (never store raw card/bank details)
  - Audit logs (track all actions)
## **Fraud Prevention:**
  - Rate limiting (prevent abuse)
  - Transaction limits (daily / per txn caps)
  - Anomaly detection (unusual amount, location, velocity)
  - Risk scoring (flag / block suspicious transactions)



---

## 17. Observability & Monitoring

### **Goal:**
  - Detect issues early
  - Debug failures quickly
  - Ensure system health and correctness
### **What to Monitor:**
  - **Metrics:**
    - QPS (read/write)
    - latency (p95/p99)
    - error rate
    - success vs failed transactions
### **Business Metrics:**
  - total transaction volume
  - failed / pending transactions
  - reconciliation backlog
### **Logging**:
    - Log every transaction with:
      - transaction_id
      - wallet_id
      - status changes
    - Include audit logs for money movement
### **Tracing**
    - Trace request across: ``` API Gateway → Wallet Service → Ledger → DB```
    - Helps debug latency and failures
### - **Alerts:**
  - High error rate
  - Spike in FAILED / PENDING
  - Balance mismatch / ledger inconsistency
  - Queue lag



---

## 18. Trade-offs & Alternatives**
- Goal:
Explain design decisions and why you chose them.
- Key Trade-offs
- 
| Decision       | Option A     | Option B    | Chosen        | Why                       |
| -------------- | ------------ | ----------- | ------------- | ------------------------- |
| Balance source | Wallet table | Ledger only | Hybrid        | Fast reads + correctness  |
| Consistency    | Strong       | Eventual    | Strong (core) | Money correctness         |
| Locking        | Pessimistic  | Optimistic  | Optimistic    | Better scalability        |
| Processing     | Sync         | Async       | Hybrid        | Critical sync, rest async |
| Idempotency    | Optional     | Required    | Required      | Prevent duplicates        |
| DB scaling     | Vertical     | Sharding    | Sharding      | Scale writes              |
 ## 19. Wrap Up Cheat Sheet (Summary)
Problem

→ Digital wallet to store balance and support top-up, transfer, payment, withdraw
→ Must prevent double-spend and ensure correctness

Core Design

→ API Gateway → Wallet Service → Idempotency → DB → Ledger → Queue

Data Model

→ wallets (balance)
→ transactions (state)
→ ledger_entries (money movement)
→ idempotency_keys (retry safety)

Flow

→ check idempotency → validate balance → create transaction
→ write ledger → update balance → publish event

Balance

→ Ledger = source of truth
→ Wallet balance = cached view

Correctness

→ idempotency (no duplicates)
→ double-entry ledger (no money loss/creation)
→ reconciliation (fix unknown states)

Concurrency

→ atomic update (balance ≥ amount)
→ optimistic locking (version)

Scaling

→ stateless services
→ shard by wallet_id / transaction_id
→ async processing via queue

Caching

→ cache metadata + recent transactions
→ balance cache only for display

Security

→ auth + encryption + tokenization
→ fraud checks (limits, anomaly detection)

Key Insight

correctness > performance