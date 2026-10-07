# Transaction Ledger - Double Entry Ledger



## Table of Contents
1. [Problem Statement](#1-problem-statement)
2. [Requirements (Functional + Non-Functional)](#2-requirements-functional--non-functional)
3. [Ledger vs Payment System](#3-ledger-vs-payment-system)
4. [Back Of Envelope Calculations](#4-back-of-envelope-calculations)
5. [High-Level Architecture](#5-high-level-architecture)
6. [Core Components](#6-core-components)
7. [Double-Entry Rules](#7-double-entry-rules)
8. [API Design](#8-api-design)
9. [Data Model](#9-data-model)
10. [Write Flow](#10-write-flow)
11. [Balance Calculation](#11-balance-calculation)
12. [Consistency & Correctness](#12-consistency--correctness)
13. [Concurrency Control](#13-concurrency-control)
14. [Immutability & Reversal](#14-immutability--reversal)
15. [Failure Handling, Reconciliation & Scaling](#15-failure-handling-reconciliation--scaling)
16. [trade-offs](#16-trade-offs)
17. [Wrap-Up Cheat Sheet](#17-wrap-up-cheat-sheet)

---

## 1. Problem Statement

- Design a transaction ledger system that:
  - Record all financial transactions
  - Enforce double-entry (sum = 0)
  - sure strong consistency (ACID)
  - Maintain immutable audit log
  - Provide accurate balances & history

---
## 2. Requirements (Functional + Non-Functional)
### ✅ Functional requirements
- Create transaction (multi-entry)
- Enforce double-entry (debit = credit)
- Get account balance
- Get transaction history
- Reverse transaction (no delete/update)
- Idempotent API (prevent duplicates)
### ⚙️ Non-Functional
- Strong consistency (ACID)
- High durability (no data loss)
- High reliability
- Scalable (high write volume)
- Low-latency reads (balance)
- Full auditability (immutable log)
### 🔥 One-liner (say this in interview)

> "The system must reliably record balanced transactions, provide accurate balances and history, and guarantee strong consistency, durability, and auditability at scale."

---
## 3. Ledger vs Payment System
### **🧾 Ledger**
- Source of truth
- Double-entry (debit = credit)
- Immutable
- Strong consistency
- No external calls

### **💳 Payment System**:
- Moves money
- Talks to banks/networks
- Handles retries/failures
- Stateful (pending, success, failed)
### 🔥 One-liner

>Payment moves money, Ledger records money.


---
## 4. Back Of Envelope calculations
 
###  **Assumptions:**
  - 10M accounts
  - Transactions/day: 10M
  - Ledger entries per transaction:3
  - Avg entry size: ~300 bytes
  - peak load: 10x average
  - Reads: 100M balance reads/day (10x transactions)
#### calculations:

  - Write QPS ```10M transactions/day ÷ 86,400 ≈ 116 transactions/sec```
  - Peak  write QPS: ```116 × 10 = 1,160 transactions/sec```
  - Ledger Entry QPS: ```10M transactions/day × 3 entries/transaction ÷ 86,400 ≈ 348 ledger entries/sec```
  - Ledger Entry QPS per day:  ```10M × 3 = 30M ledger entries/day```
  - Peak Ledger entry : ``348 × 10 = 3,480 ledger entries/sec```
    - Balance Read QPSL ```100M reads/day ÷ 86,400 ≈ 1,160 reads/sec```
    - Peak 10x: ``~12K reads/sec``
    - Storage per day : ```30M entries/day × 300 bytes = 9GB/day```
    - For 7 years: ```9GB × 365 × 7 ≈ 23TB```
---
## 5. High-Level Architecture:

### Flow:
    Client / Internal Service
        ↓
    API Gateway
        ↓
    Ledger Service
        ↓
    Ledger DB  ← source of truth
        ↓
    Outbox Table
        ↓
    Event Bus / Queue
        ↓
    Consumers
      - Notification Service
      - Reconciliation Service
      - Analytics / Reporting
### **What each part does**

| Component                                           | Responsibility                                            |
|-----------------------------------------------------| --------------------------------------------------------- |
| **Client / Internal Service**                       | Sends ledger transaction request                          |
| **API Gateway**                                     | Auth, rate limit, routing                                 |
| **Ledger Service**                                  | Validates transaction, enforces double-entry, idempotency |
| **Ledger DB**                                       | Stores accounts, transactions, ledger entries             |
| [**Outbox Table**](../../../../Gloassay.md#outbox-pattern) | Guarantees event is published after DB commit             |
| **Event Bus / Queue**                               | Async downstream processing                               |
| **Consumers**                                       | Notifications, reconciliation, analytics                  |
---
## **6. Core Components**

### 1️⃣ Ledger Service
- Core business logic
- Validates transactions
- Enforces double-entry
- Handles idempotency

### 2️⃣ Ledger Database
- Stores:
  - Accounts
  - Transactions
  - Ledger entries
- Source of truth

### 3️⃣ Balance Store (Derived)
- account_balances
- Fast balance reads
- Updated with ledger writes

### 4️⃣ Idempotency Store
- Prevent duplicate transactions
- Ensures exactly-once effect

### 5️⃣ Outbox + Event Publisher
- Stores events reliably
- Publishes to queue (async)

### 6️⃣ Queue / Event Bus
- Distributes events
- Decouples systems

### 7️⃣ Downstream Consumers
- Notification service
- Reconciliation service
- Analytics/reporting
---
## 7. Double-Entry Rules
1. Transaction must be balanced
2. Transaction must have at least 2 entries
3. write must be **ATOMIC** (all or nothing)
4. Ledger entries are _**immutable**_ (no update/delete)
---
## 8. API Design
 - **POST /ledger/transactions** → create balanced transaction
   - use Idempotency-Key header to prevent duplicates
- **GET /accounts/{id}/balance** → read balance
- **GET /accounts/{id}/ledger-entries** → history
- **POST /ledger/transactions/{id}/reversal** → fix mistake safely



---
## 9. Data Model
### Tables:
-  **accounts:** ```(id(pk), user_id, currency, status, created_at)```
-  **transactions:** ```(id(pk), idempotency_key(unique), status, created_at, posted_at, reversal_of_transaction_id (nullable fk to transactions.id))```
-  **ledger_entries:** ```(id(pk), transaction_id(fk), account_id(fk), amount, created_at)```
-  **Idempotency_keys:** ```(id(pk), key(unique), status, response_data, created_at)```
-  **account_balances:** ```(account_id(pk/Fk), balance,  currency,  version, updated_at)```
-  **outbox_events:** ```(id(pk), transaction_id(fk), event_type, payload, published_at, status)```
Notes
### Mutability: 
1️⃣ **accounts**: Defines each money bucket (wallet/account
- Mutable?: ✅ Updatable
- Why: status can change (ACTIVE → FROZEN)

2️⃣ **transactions**: Represents a business event (groups ledger entries)
   - Mutable?: ⚠️ Partially updatable
   - Why: status / posted_at can change (PENDING → POSTED), but core data should not

3️⃣ **ledger_entries**: Actual debit/credit records (source of truth)
- Mutable?: ❌ Immutable
- Why: financial audit trail (never update/delete)

4️⃣ **idempotency_keys**:: Prevents duplicate transaction processing
- Mutable?: ⚠️ Partially updatable
- Why: status changes (IN_PROGRESS → SUCCESS), response_data set once

5️⃣ **account_balances**: Cached balance for fast reads
- Mutable?: ✅ Updatable
- Why: balance changes on every transaction

6️⃣ **outbox_events** Stores events to be published reliably
- **Questions?** why we need this table?
  > **Answer**:To ensure that events are only published if the corresponding transaction is successfully committed.
  > By writing the event to the outbox table within the same DB transaction as the ledger entries,
  > we guarantee that either both the ledger update and event write succeed, or neither does. This prevents scenarios where a transaction is recorded but the event is lost (e.g., due to a crash after commit but before event publish), ensuring reliable downstream processing. 
- Mutable?: ⚠️ Partially updatable
- Why: status (PENDING → PUBLISHED), published_at updated

 
---

## 10. Write Flow

       1. Payment / Wallet Service calls Ledger API
                            ↓
       2. API Gateway authenticates + routes request
                        ↓
       3. Ledger Service receives request
                        ↓
       -----------------DB Transaction Boundary-----------------
       |4. Ledger Service starts DB transaction                 |
       |           ↓                                            |
       |5. Check idempotency table                              |
       |           ↓                                            |
       |6. Validate accounts + currency + amounts               |
       |           ↓                                            |
       |7. Validate double-entry rule: SUM(amount) = 0          |
       |           ↓                                            |
       |8. Insert transaction row                               |   
       |           ↓                                            |
       |9. Insert ledger_entries rows                           |
       |           ↓                                            |
       |10. Update account_balances                             |
       |           ↓                                            |
       |11. Insert outbox event                                 |    
       |           ↓                                            |
       |12. Commit DB transaction                               |    
       |           ↓                                            |
        ---------------------------------------------------------
       13. Outbox worker publishes event to queue
                   ↓
       14. Consumers process event


 ### write flow steps:

1. Receive request:includes Idempotency-Key in header + transaction details in body (accounts, amounts, currency)
2. **Start DB transaction (ACID boundary)**
3. Check idempotency key:
    - if already processed → return stored response
    - if new → create idempotency record as `IN_PROGRESS`
4. Validate request:
   - accounts exist
   - accounts are active
   - currency matches
   - amounts are valid
5. Validate double-entry rule:
   - SUM(amount) = 0
   - at least 2 entries
6. Insert transaction row:
   - status = POSTED or PENDING
   - PENDING if we need to wait for external confirmation (e.g., payment provider), POSTED if we can finalize immediately
7. Insert ledger entries
   - all entries linked to transaction_id

8. Insert outbox event:
    -inside same DB transaction
9. **Commit DB transaction(all or nothing)**
10. Async worker publishes event
   notification / reconciliation / reporting
--
## 11. Balance Calculation
There are 2 main approaches:
1. **On-the-fly calculation** - sum all ledger entries for account
   - Pros: always accurate, no stale data
   - Cons: can be slow if many entries, high read load
2. **store Balance in `account_balances` table and update on each transaction**
    - account_balance table is used as a cache for fast balance reads, updated in the same DB transaction as ledger entries to ensure consistency
    - **Pros**: fast reads, low latency
    - **cons**: risk of stale data, must handle concurrency carefully
3. **Best approach: hybrid**
- store balance for fast reads
- periodically reconcile with on-the-fly calculation to ensure accuracy

**so during  write:**

       BEGIN DB TRANSACTION
         Insert transactions row
         Insert ledger_entries rows
         Update account_balances
         Insert outbox_events row
       COMMIT



---
## 12. Consistency & Correctness
###  **🧠 Goal:**
Ensure ledger is always correct and consistent
### **Key guarantees:**
- Atomicity (ACID): All writes succeed or fail together
- Double-entry rule: SUM(amount) = 0 for each transaction
- Immutability: No updates/deletes to ledger entries
- Isolation: concurrent transactions don't interfere
- Durability: once committed, data is never lost
### **how to achieve this:**
- Use a relational DB with ACID transactions (e.g., PostgreSQL)
- Enforce double-entry in application logic within DB transaction
- Use unique constraint on idempotency key to prevent duplicates
- Store all writes (transactions, ledger entries, outbox) in same DB transaction
- Use optimistic locking or serializable isolation level to handle concurrency
---
## 13. Concurrency Control
### **🧠 Problem:**
Multiple transactions may hit the same account at the same time:

- Example:

        Tx1: deduct $100
        Tx2: deduct $50

👉 Without control:

- both read balance = 100
- both succeed → balance = -50 ❌

### **🛠️ Solutions**
  - 1️⃣ **Row-Level Locking (Pessimistic):**
      - Lock account row during transaction
      - Pros: simple, prevents conflicts
      - Cons: can cause contention, reduces concurrency
  - 2️⃣ **Optimistic Locking**
      - Read balance + version number
      - On update, check version matches
      - If version mismatch → retry transaction
      - **Pros**: better concurrency, less locking
      - **Cons**: requires retry logic, can lead to more failed transactions under high contention
  - 3️⃣ **Hybrid Approach**
      - Use optimistic locking for most transactions
      - If retry count exceeds threshold → fallback to pessimistic locking for that transaction

---
## 14. Immutability & Reversal
### **🧠 Goal**: 
Ledger entries are immutable (no update/delete)

### **❌what Not allowed:**
  - Update a ledger entry❌
  - Delete a ledger entry❌
### **✅How to fix mistakes:**
  - Create a reversal transaction with opposite entries:
  - Example:
    - Original Tx: Debit $100 from A, Credit $100 to B
    - Reversal Tx: Credit $100 to A, Debit $100 from B
    - Both transactions remain in the ledger, preserving audit trail and immutability
    - Reversal transactions should reference the original transaction for traceability
        so in our design we will add a new column to transactions table: `reversal_of_transaction_id` (nullable FK to transactions.id) to link reversal transactions to the original transaction.
    - This way, we maintain a complete and auditable history of all transactions, including corrections, without ever modifying or deleting existing ledger entries.





---
## 15. Failure Handling, Reconciliation & Scaling
### 🔥 Failure Handling

 - **🧠Core idea:** Protect ledger from bad writes
 - **Guarantees:**
   - Idempotency key → no duplicate transactions
   - DB transaction (ACID) → no partial writes
   - Outbox pattern → no lost events
   - Safe retries → same result, no double effect


### 🔍 Reconciliation

- **🧠Core idea:**  Detect mismatches after the fact
- Checks
  - Missing transactions
  - Duplicate transactions
  - Amount / currency mismatch
  - Stuck pending transactions
- How:
  - Scheduled jobs
  - Compare with payment/provider systems


### ⚡ Scaling

- **🧠Core idea:** Scale reads, keep writes strict
- **Approach:**
  - Shard by account_id / tenant_id
  - Use account_balances for fast reads
  - Read replicas for history
  - Archive old ledger data
- **How:**
  - Scale reads aggressively (caching, read replicas)
  - Keep ledger writes strongly consistent (single shard, ACID DB)
---
## 16. Trade-offs

### 🎯 Trade-off 1: Strong Consistency vs Scalability

| Aspect | Strong Consistency | Eventual Consistency |
|--------|-------------------|----------------------|
| **Write Model** | Synchronous, ACID transactions | Async, event-based |
| **Balance Accuracy** | Always correct, no stale reads | May be temporarily stale |
| **Latency** | Higher (DB transaction overhead) | Lower (faster writes) |
| **Complexity** | Simpler application logic | Complex reconciliation logic |
| **Auditability** | Perfect audit trail | Requires additional reconciliation |

**✅ Chosen: Strong Consistency**
- **Why:** Financial systems require correctness over speed; a stale balance is unacceptable; audit trail must be perfect
- **Interview line:** "We prioritize correctness and auditability over raw throughput because money is at stake."

---

### 🎯 Trade-off 2: Stored Balance vs On-the-fly Calculation

| Aspect | Stored Balance | On-the-fly Calculation |
|--------|----------------|------------------------|
| **Read Speed** | O(1) lookup, ~1ms | O(n) sum of entries, can be slow |
| **Storage Overhead** | Extra table, ~8 bytes per account | No extra storage |
| **Consistency Risk** | Must update atomically with ledger | Always correct by definition |
| **Scalability** | Very fast for balance reads | Slow under high read load |
| **Maintenance** | Reconciliation jobs needed | No maintenance needed |

**✅ Chosen: Hybrid Approach (Stored + Periodic Reconciliation)**
- **Why:** Fast reads (stored balance), correctness (reconciliation), best of both worlds
- **How:** Update `account_balances` in same DB transaction as ledger entries; periodically recalculate from ledger to detect drift
- **Interview line:** "We store balance for fast reads but reconcile periodically to ensure ledger entries are the source of truth."

---

### 🎯 Trade-off 3: Pessimistic vs Optimistic Locking

| Aspect | Pessimistic (Row Lock) | Optimistic (Version Check) |
|--------|------------------------|---------------------------|
| **Conflict Handling** | Prevents conflicts (blocking) | Detects conflicts (retry) |
| **Concurrency** | Low (locks reduce parallel execution) | High (more parallelism) |
| **Latency** | Variable (depends on lock contention) | Consistent (but may need retries) |
| **Throughput** | Lower under high concurrency | Higher if conflicts rare |
| **Deadlock Risk** | Yes, requires careful ordering | No deadlocks |
| **Complexity** | Simple lock acquisition | Retry logic required |

**✅ Chosen: Optimistic Locking with Fallback**
- **Why:** Better throughput and concurrency; retries only happen on actual conflicts (rare in practice)
- **Fallback:** If retry count exceeds threshold, switch to pessimistic locking for that transaction
- **Interview line:** "Optimistic locking scales better; pessimistic locking is only used as fallback for hot accounts."

---

### 🎯 Trade-off 4: Immutability (Reversal) vs Update/Delete

| Aspect | Immutability + Reversal | Update/Delete |
|--------|-------------------------|----------------|
| **Audit Trail** | Complete history; all changes auditable | Difficult to trace who changed what |
| **Compliance** | Meets financial regulations | May fail audit/compliance |
| **Storage** | Extra storage for reversal records | Smaller footprint |
| **Complexity** | Simple (append-only) | Complex (must track deletes) |
| **Data Recovery** | Easy (full history) | Difficult (history lost) |
| **Correctness** | Guaranteed (no data loss) | Risk of accidental deletion |

**✅ Chosen: Immutability + Reversal Transactions**
- **Why:** Financial audit trail requires complete history; immutability prevents accidental corruption
- **How:** Fix errors via reversal transaction (opposite entries); link via `reversal_of_transaction_id`
- **Interview line:** "We never update or delete ledger entries—we fix errors by creating reversal transactions, maintaining a complete audit trail."

---

### 🎯 Trade-off 5: Synchronous Writes + Asynchronous Events vs Fully Asynchronous

| Aspect | Sync Writes + Async Events | Fully Asynchronous |
|--------|----------------------------|-------------------|
| **Consistency** | Strong (write committed before response) | Eventual (write acked before commit) |
| **Write Latency** | Higher (DB round-trip) | Lower (ack immediately) |
| **Event Reliability** | Outbox pattern (no lost events) | Events may be lost on crash |
| **Application Complexity** | Simpler (write = done) | Complex (must handle failures) |
| **Correctness Risk** | None (ACID boundary includes events) | High (lost event = lost business logic) |

**✅ Chosen: Synchronous Writes + Asynchronous Events (Outbox Pattern)**
- **Why:** Guarantees ledger and events are always in sync; prevents lost events
- **How:** Write transaction + outbox event in same DB transaction; separate worker publishes asynchronously
- **Interview line:** "We commit writes synchronously (guaranteeing correctness) and publish events asynchronously (decoupling consumers), using the outbox pattern to ensure no events are lost."

---

### 🎯 Trade-off 6: Sharding Strategy (Distributed Ledger vs Centralized)

| Aspect | Distributed (by account) | Centralized |
|--------|--------------------------|-------------|
| **Write Scalability** | High (multiple shards for writes) | Limited (single DB bottleneck) |
| **Consistency** | Easy within shard, hard cross-shard | Easy (single transaction) |
| **Cross-Account Transactions** | Distributed transactions (complex) | Simple (single transaction) |
| **Rebalancing** | Expensive (data movement) | Not needed |
| **Operational Complexity** | High (multiple DB instances) | Low (single DB) |

**✅ Chosen: Hybrid (Centralized for Writes, Distributed for Reads)**
- **Why:** Ledger writes are single-leader (strong consistency); read replicas distributed for scale
- **How:** All writes to primary DB (single shard); balance/history reads from read replicas; shard read replicas by account_id
- **Interview line:** "Ledger writes remain centralized for ACID guarantees; reads are replicated and sharded for scalability."

---

### 📊 Trade-off Summary Table

| Design Decision | Option A | Option B | ✅ Chosen | Rationale |
|-----------------|----------|----------|----------|-----------|
| **Consistency Model** | Strong ✅ | Eventual | Strong | Correctness > Speed in fintech |
| **Balance Storage** | Stored + Reconcile ✅ | On-the-fly | Hybrid | Best performance + correctness |
| **Concurrency Control** | Optimistic ✅ | Pessimistic | Optimistic | Better throughput/concurrency |
| **Ledger Updates** | Reversal ✅ | Update/Delete | Reversal | Audit trail immutability |
| **Event Reliability** | Outbox ✅ | Async only | Outbox | No lost events |
| **Write Scalability** | Centralized ✅ | Distributed | Centralized | ACID > raw throughput |

---

## **17. Wrap-Up Cheat Sheet**

### 🎯 1. Core Idea (Start with this)
- "I design the ledger as the single source of truth where every financial transaction is recorded using double-entry, ensuring that no money is created or lost, and the system remains strongly consistent and auditable."

### 🧠 2. Mental Model
- **Account** → where money lives
- **Transaction** → business event
- **Ledger Entry** → actual money movement
- One transaction → multiple ledger entries → across accounts
- Sum must always be 0

### 🧱 3. Core Components
- Ledger Service → business logic + validation
- Ledger DB → source of truth
- Balance Store → fast reads
- Idempotency → prevent duplicates
- Outbox → reliable event publishing
- Queue → async processing
- Consumers → notification, reconciliation, analytics

### ⚖️ 4. Double-Entry Rules (Critical)
- Sum(entries) = 0
- Minimum 2 entries
- Atomic write
- Immutable entries

### 🗄️ 5. Data Model (Key Tables)
- accounts → money buckets
- transactions → event grouping
- ledger_entries → source of truth
- idempotency_keys → exactly-once
- account_balances → fast reads
- outbox_events → reliable events

### 🔄 6. Write Flow (Most Important Section)
- Start DB Transaction
- Check idempotency
- Validate request
- Validate SUM = 0
- Insert transaction
- Insert ledger entries
- Update balance
- Insert outbox event
- Commit
- Async publish
- All DB steps are atomic

### 💰 7. Balance Strategy
- Source of truth → ledger_entries
- Fast reads → account_balances
- Hybrid approach: write = update both, periodic reconciliation

### 🛡️ 8. Consistency Guarantees
- ACID transaction
- No partial writes
- Idempotency → no duplicates
- Ledger always derivable

### ⚔️ 9. Concurrency Control
- Optimistic locking (version)
- Optional row locking
- Prevent overdraft

### 🔁 10. Immutability & Reversal
- No update/delete ❌
- Fix using reversal transaction ✅
- Link via reversal_of_transaction_id

### 🚨 11. Failure Handling
- Idempotency → safe retry
- DB transaction → atomicity
- Outbox → no lost events

### 🔍 12. Reconciliation
- Detect: missing transactions, duplicates, mismatches
- Scheduled jobs compare systems

### ⚡ 13. Scaling
- Shard by account_id
- Read replicas for history
- Cache / balance table for reads
- Archive old data

### 🧠 14. Key Trade-offs (Say this if pushed)
- Strong consistency vs scalability
- Real-time balance vs cached balance
- Locking vs throughput
- Simplicity vs performance

### 🚀 Final Interview Closing
- "The key design decision is to keep the ledger write path strongly consistent, atomic, and immutable, while using idempotency, outbox, and reconciliation to ensure reliability, and balance snapshots and sharding to scale reads efficiently."

### 💡 If you only remember 5 things
- Double-entry (SUM = 0)
- Immutable ledger
- Atomic DB transaction
- Idempotency
- Outbox pattern

