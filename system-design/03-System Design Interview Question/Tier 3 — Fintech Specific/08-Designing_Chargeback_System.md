# Designing Card Chargeback System

## Table of Contents
1. [Problem Statement](#1-problem-statement)
2. [Requirements & Goals](#2-requirements--goals)
3. [Assumptions + BOE Estimation](#3-assumptions--boe-estimation)
4. [High-Level Design](#4-high-level-design)
5. [Core Components](#5-core-components)
6. [API Design](#6-api-design)
7. [Data Model](#7-data-model)
8. [Idempotency & Concurrency Control](#8-idempotency--concurrency-control)
9. [Client Submission Flow](#9-client-submission-flow)
10. [Batch Processing Flow](#10-batch-processing-flow)
11. [Outbox Pattern & Event Streaming](#11-outbox-pattern--event-streaming)
12. [State Machine](#12-state-machine)
13. [Scalability](#13-scalability)
14. [Caching](#14-caching)
15. [Consistency & Reliability](#15-consistency--reliability)
16. [Security](#16-security)
17. [Observability & Monitoring](#17-observability--monitoring)
18. [Trade-offs & Alternatives](#18-trade-offs--alternatives)
19. [Wrap-Up Cheat Sheet](#19-wrap-up-cheat-sheet)

---

## 1. Problem Statement

Design a Card Chargeback System that:

### Core Goals
- Let a client dispute/report a transaction from the app (chargeback)
- Reliably deliver every chargeback to Mastercard as a batched CSV file
- Never lose, duplicate, or double-batch a chargeback

### Key Requirements
- Mastercard receives a CSV batch file, one line per chargeback
- Every chargeback needs a unique identifier
- A chargeback must be unique within a single batch file
- Mastercard accepts exactly 4 batch files/day (no size limit per file)
- Client-facing write path must not be affected by batch processing

### 🚧 Follow-ups to explore (add notes here as they come up)
- [ ] What happens if Mastercard rejects a batch file (partial or full failure)?
- [ ] How does the client see chargeback status updates after batching?
- [ ] What if fewer than 4 batch windows have pending chargebacks — do we still send an empty file?
- [ ] Retry/backoff strategy for FTP push failure to Mastercard?
- [ ] How far back can a client dispute a transaction (time window)?
- [ ] Multi-region / multi-issuer (Visa vs Mastercard) routing — out of scope here or in scope?

---

## 2. Requirements & Goals

### ✅ Functional Requirements
- **Submit** chargeback via `POST /cardChargeback`
- **Assign** a unique id to every chargeback
- **Deduplicate** retries via idempotency key
- **Batch** pending chargebacks into a CSV file, 4x/day
- **Guarantee** each chargeback appears in exactly one batch
- **Deliver** batch file to Mastercard via FTP
- **Track** chargeback status (OPEN → BATCHED → SENT)
- **Store** full event history for audit

### ⚙️ Non-Functional Requirements
- **Consistency:** no duplicate/lost/double-batched chargebacks
- **Correctness:** idempotency guaranteed under retry/failure
- **Availability:** client write path unaffected by batch/file-processing issues
- **Scalability:** client path and batch path scale independently
- **Isolation:** client-facing DB and batch DB are never shared
- **Reliability:** no SPOF; on-prem datacenter carries no business logic
- **Auditable:** full event history of every chargeback

---

## 3. Assumptions + BOE Estimation

### Assumptions
- **Users:** ~10M cardholders
- **Chargebacks/day:** assume ~50K/day (small fraction of total transactions)
- **Avg chargeback record size:** ~1 KB
- **Batch windows:** fixed, 4x/day (~every 6 hours)
- **Read/write pattern:** write-light on ingestion, bursty read on batch cutoff

### Traffic Estimation
- **Average QPS:** 50,000 / 86,400 ≈ 0.6 chargebacks/sec
- **Peak QPS:** assume 10x peak ≈ 6 chargebacks/sec (very low volume relative to a payments system — this is not a throughput-bound problem, it's a **correctness**-bound one)

### Storage Estimation
- **Per day:** 50K × 1 KB ≈ 50 MB/day
- **Per year:** 50 MB × 365 ≈ ~18 GB/year
- **With events, idempotency records, batch metadata (3–5x):** ≈ 50–90 GB/year

### 🎯 Interview Line
"This system is low-volume but high-stakes on correctness — the hard part isn't scale, it's guaranteeing exactly-once batching and no data loss across service boundaries."

---

## 4. High-Level Design

### Flow
```
Client App
    ↓
API Gateway
    ↓
Chargeback Service ──→ Chargeback DB (Postgres)
    ↓ (same txn)
Outbox table
    ↓
Outbox Relay ──→ Kafka
    ↓
Batch Consumer Service ──→ Batch DB (separate Postgres)
    ↓
Scheduler (cron, 4x/day)
    ↓
Atomic Batch Claim
    ↓
CSV Generator ──→ Blob storage
    ↓
FTP push
    ↓
Datacenter (thin relay, no logic)
    ↓
Mastercard Machine
```

### Request Flow Steps
1. **Client submits** chargeback → API Gateway → Chargeback Service
2. **Chargeback Service** checks idempotency, writes chargeback + event + outbox row in one transaction
3. **Outbox Relay** publishes the event to Kafka asynchronously
4. **Batch Consumer Service** consumes the event into its own staging table
5. **Scheduler** fires 4x/day, triggers an atomic claim of all pending rows
6. **CSV Generator** builds the file from claimed rows
7. **File is pushed** via FTP to the datacenter, which relays it to Mastercard

### Key Components
- **Chargeback Service:** client-facing write path, owns idempotency
- **Outbox Relay:** bridges DB write to Kafka reliably
- **Batch Consumer Service:** owns batching, isolated from client DB
- **Scheduler:** triggers batch cutoff, holds no data
- **Datacenter:** pure FTP relay, no business logic

---

## 5. Core Components

### 1️⃣ Chargeback Service
- **Responsibility:** entry point for client chargeback submissions
- **Key actions:**
  - Validates request
  - Enforces idempotency `(client_id, idempotency_key)`
  - Writes chargeback + event + outbox row in a single DB transaction

### 2️⃣ Outbox Relay
- **Responsibility:** reliably deliver DB-committed events to Kafka
- **Key actions:**
  - Polls outbox table for `published = false` rows
  - Publishes to Kafka topic
  - Marks row published on success; retries on failure

### 3️⃣ Batch Consumer Service
- **Responsibility:** owns the entire batching lifecycle
- **Key actions:**
  - Consumes `ChargebackCreated` events from Kafka
  - Writes into its own `batch_staging` table (separate DB)
  - Executes the atomic claim on scheduler trigger
  - Hands claimed rows to CSV Generator

### 4️⃣ Scheduler
- **Responsibility:** triggers batch cutoff on a fixed cadence
- **Key actions:**
  - Fires 4x/day
  - Sends a trigger only — holds no data, no logic beyond timing

### 5️⃣ CSV Generator
- **Responsibility:** turns a claimed batch into a Mastercard-ready file
- **Key actions:**
  - Reads rows for a given `batch_id`
  - Writes CSV, stores to blob storage
  - Marks batch `generated`

### 6️⃣ Datacenter (thin relay)
- **Responsibility:** forward files to Mastercard, nothing else
- **Key actions:**
  - Receives file over FTP from Batch Consumer Service
  - Forwards to Mastercard Machine
  - No orchestration, no state, no business logic

---

## 6. API Design

- **POST /cardChargeback** → submit a chargeback
  - `Idempotency-Key` header required
  - not found → process → 201
  - found + same request → 200 (saved response)
  - found + different request → 409 Conflict
- **GET /cardChargeback/{id}** → read chargeback status
  - returns status, amount, created_at, batch_id (if batched)
- **GET /cardChargeback/{id}/events** → read full event history (audit)

---

## 7. Data Model

- **chargeback**: core record — `id, client_id, amount, reason, status, version, created_at`
- **chargeback_event**: immutable audit trail — `id, chargeback_id, old_status, new_status, created_at`
- **idempotency_key**: dedup guard — `key(client_id + idempotency_key), request_hash, saved_response, status, expires_at`
- **outbox**: transactional bridge to Kafka — `id, aggregate_id, event_type, payload, published, created_at`
- **batch_staging** *(separate DB)*: batch working set — `chargeback_id, client_id, amount, batch_id (nullable), consumed_at`

**No ledger table** — dropped from scope. This service isn't the system of record for fund movement, so double-entry accounting adds complexity without solving a requirement here.

---

## 8. Idempotency & Concurrency Control

### Idempotency
- Key = `(client_id, idempotency_key)`, unique constraint in Postgres
- **Cases:**
  - not found → process normally
  - found + same request hash → return saved response, don't reprocess
  - found + different request hash → 409 Conflict
- Enforced inside the same DB transaction as the chargeback write — this is what makes it correct, not the table alone

### Concurrency
- **Optimistic locking** (`version` column) on chargeback status updates
- **Why optimistic over pessimistic:** low contention per row (a single chargeback is rarely updated concurrently by two actors) — optimistic avoids lock overhead and scales better
- Conflict → retry with latest version

### 🎯 Interview Line
"Idempotency is enforced transactionally at write time, not as an afterthought — the chargeback, its event, and the outbox row either all commit together or none do."

---

## 9. Client Submission Flow

### Diagram
```
Client App
    ↓
API Gateway
    ↓
Chargeback Service
    ↓
Idempotency check ──┬── not found → continue
                     ├── same request → return saved response
                     └── different request → 409
    ↓
BEGIN TX
  INSERT chargeback (status=OPEN)
  INSERT chargeback_event
  INSERT outbox (ChargebackCreated, published=false)
COMMIT TX
    ↓
201 Created → Client
    ↓ (async, after response already sent)
Outbox Relay → Kafka
```

### Flow Steps
1. Client sends `POST /cardChargeback` with `Idempotency-Key`
2. Chargeback Service checks idempotency table
3. If new: single transaction writes chargeback + event + outbox row
4. Client gets `201` immediately — Kafka publish happens after, asynchronously
5. Outbox Relay picks up the row and publishes to Kafka

### 🎯 Interview Line
"The client never waits on Kafka — the transaction that matters to the client is entirely local to one database, and event delivery is decoupled and retried independently."

---

## 10. Batch Processing Flow

### Diagram
```
Kafka (ChargebackCreated)
    ↓
Batch Consumer Service
    ↓
batch_staging (batch_id = NULL)
    ↓
Scheduler fires (4x/day) ──→ trigger only
    ↓
ATOMIC CLAIM:
UPDATE batch_staging
SET batch_id = 'batch-xyz'
WHERE batch_id IS NULL
    ↓
CSV Generator (reads claimed rows)
    ↓
Blob storage
    ↓
FTP push → Datacenter → Mastercard
```

### Flow Steps
1. Batch Consumer Service writes each incoming event to `batch_staging`, unclaimed
2. Scheduler fires on a fixed cadence and triggers a claim — it holds no data itself
3. **Atomic claim:** one `UPDATE ... WHERE batch_id IS NULL` — guarantees no row lands in two batches and none are skipped, regardless of exact arrival timing
4. CSV Generator reads only rows matching the new `batch_id`, writes the file
5. File is pushed via FTP; Datacenter relays it untouched to Mastercard

### 🎯 Interview Line
"The scheduler decides *when* to cut a batch; the atomic UPDATE decides *what* gets included — that separation is what actually guarantees batch integrity, not the timing alone."

---

## 11. Outbox Pattern & Event Streaming

### 🎯 Why Outbox
Writing to a DB and publishing to Kafka are two separate systems — without a shared transaction, a crash between the two either loses the event or publishes one for data that was never committed (the dual-write problem).

### 🔄 How It Works
```
1. INSERT chargeback         ┐
2. INSERT outbox row         ┘  same DB transaction — atomic
3. Relay reads outbox → Kafka   (retried independently, at-least-once)
```

### 🧠 What Kafka Is Used For
- Deliver `ChargebackCreated` events to the Batch Consumer Service
- Decouple client-facing write path from batch-processing load
- Feed reconciliation/status updates back from batch flow to client flow

### 🎯 Interview Line
"Outbox turns a distributed dual-write into two easy problems: an atomic local DB write, and a retryable at-least-once delivery — with consumers made idempotent to handle Kafka's at-least-once guarantee."

---

## 12. State Machine

### 📊 Chargeback States
```
        OPEN
          ↓
     BATCHED (claimed into a batch)
          ↓
       SENT (FTP delivered to Mastercard)
```

### 🧠 State Explanations
- **OPEN** → chargeback created, sitting in `batch_staging` unclaimed
- **BATCHED** → claimed by the atomic UPDATE, included in a generated CSV
- **SENT** → file successfully delivered via FTP

### 📋 Which Tables Get Updated
- **chargeback** (client DB) → current status, updated via events flowing back from batch side
- **chargeback_event** → immutable history of every transition
- **batch_staging** (batch DB) → `batch_id` set at claim time, `consumed_at` set once CSV generated

### 🎯 Interview Line
"Current status lives in `chargeback`, full history lives in `chargeback_event` — same pattern as keeping mutable state separate from an immutable audit trail."

---

## 13. Scalability

### 🎯 Goal
This isn't a throughput problem (very low QPS) — it's a decoupling problem: client writes must never be blocked or slowed by batch processing.

### 🔹 Key Strategies
1. **Isolate client and batch paths** — separate services, separate databases, connected only by Kafka
2. **Shard Chargeback DB** by `client_id` if the client base grows
3. **Scale Outbox Relay and Batch Consumer independently** — both are stateless consumers, can run multiple instances
4. **Datacenter carries zero logic** — can't become a bottleneck for anything else in the system

### ⚠️ Avoid
- ❌ Any synchronous call from client write path into batch processing
- ❌ Sharing a database between client and batch flow (the original anti-pattern)
- ❌ Heavy orchestration logic in the on-prem datacenter

---

## 14. Caching

### 🎯 Goal
Minor role here given low QPS, but still relevant for the idempotency check.

### 🔹 What to Cache
- Recent idempotency-key lookups (short TTL) to shave DB round-trips on retries
- Chargeback status for `GET /cardChargeback/{id}` if read traffic ever grows

### ⚠️ Trade-off
- Given the low volume (~1 QPS avg), caching is a minor optimization, not a requirement — correctness matters far more than shaving milliseconds here

---

## 15. Consistency & Reliability

### 🔑 Key Points
- **Idempotency:** same `(client_id, idempotency_key)` never creates two chargebacks
- **Exactly-once batching:** atomic claim guarantees a chargeback lands in exactly one batch
- **Immutable audit log:** every status transition recorded in `chargeback_event`
- **At-least-once Kafka delivery:** Batch Consumer Service must be idempotent on `chargeback_id` (dedup on insert into `batch_staging`)
- **Retry-safe outbox relay:** unpublished rows retried until confirmed; no data loss on relay crash
- **Isolation:** client DB and batch DB never share a connection or schema — batch flow only sees data that already crossed through Kafka

### 🎯 Interview Line
"Every crash point in this system — DB commit, relay publish, batch claim, FTP push — is either atomic or safely retryable, so a failure anywhere causes a delay, never a duplicate or a loss."

---

## 16. Security

### 🔑 Key Points
- **AuthN/AuthZ:** only the authenticated client (owner of `client_id`) can submit/view their own chargebacks
- **PII/financial data:** encrypt sensitive fields at rest, mask in logs
- **FTP transfer:** encrypted channel (SFTP/FTPS) to the datacenter, not plain FTP
- **Datacenter access:** locked down, since it's a boundary to an external network (Mastercard)
- **Audit logging:** every chargeback event is already immutable and timestamped — supports dispute/compliance review

---

## 17. Observability & Monitoring

### 📊 Business Metrics
- Chargebacks submitted per day
- % successfully batched within the expected window
- Batch file delivery success rate to Mastercard

### ⚙️ System Metrics
- API latency on `POST /cardChargeback`
- Outbox relay lag (time between commit and Kafka publish)
- Batch claim duration
- FTP push success/failure rate

### 🚨 Alerts
- Outbox rows stuck unpublished beyond threshold
- Scheduler fires but claim returns 0 rows unexpectedly (possible upstream failure)
- FTP push failures
- Any chargeback stuck in OPEN past a full batch cycle (should have been claimed)

---

## 18. Trade-offs & Alternatives

### 🔹 1. Outbox + Kafka vs Direct Publish
| Option | Pros | Cons |
|---|---|---|
| Outbox + Kafka | No dual-write problem, atomic + retryable | Added latency, one more moving part (relay) |
| Direct publish | Simpler | Risk of lost/phantom events on crash ❌ |

**👉 Choice: Outbox + Kafka**

### 🔹 2. Separate Batch DB vs Shared DB
| Option | Pros | Cons |
|---|---|---|
| Separate DB | Client path isolated, independent scaling/failure | Data duplication, eventual consistency |
| Shared DB | Simpler, no duplication | Client writes coupled to batch load ❌ (flagged anti-pattern) |

**👉 Choice: Separate DB**

### 🔹 3. Atomic Claim vs Fixed Time-Window Query
| Option | Pros | Cons |
|---|---|---|
| Atomic UPDATE claim | Guarantees exactly-once batching | None significant — cheap indexed write |
| Time-window SELECT | Simple to write | Race conditions — row can leak into two batches or be skipped ❌ |

**👉 Choice: Atomic claim**

### 🔹 4. SQL vs NoSQL
| Option | Pros | Cons |
|---|---|---|
| SQL (Postgres) | ACID needed for idempotency + outbox atomicity, unique constraints cheap | Harder to scale writes horizontally |
| NoSQL | Easier horizontal write scaling | No native multi-row transactional guarantee — breaks the outbox pattern ❌ |

**👉 Choice: SQL — correctness requirements dominate here, volume is low**

### 🔹 5. Optimistic vs Pessimistic Locking
| Option | Pros | Cons |
|---|---|---|
| Optimistic | No lock contention, simple | Requires retry on conflict |
| Pessimistic | Simpler mental model | Unnecessary overhead — low contention per row ❌ |

**👉 Choice: Optimistic locking**

### 🎯 Interview Line
"Most of these trade-offs favor correctness and isolation over raw performance, because this system is low-throughput but the cost of a duplicated or lost chargeback is high."

---

## 19. Wrap-Up Cheat Sheet

### 🧠 One-Line Story
"A chargeback system that writes idempotently to its own DB, hands off events reliably via outbox + Kafka, and batches them for Mastercard using an atomic claim so nothing is ever lost, duplicated, or double-batched."

### 🔄 Core Flow
```
Client → Chargeback Service → Chargeback DB (+ outbox, same txn)
    ↓
Outbox Relay → Kafka → Batch Consumer Service → Batch DB
    ↓
Scheduler → Atomic Claim → CSV Generator → FTP → Datacenter → Mastercard
```

### 🏗️ Key Components
- Chargeback Service (client-facing, idempotent writes)
- Outbox Relay (reliable async delivery)
- Batch Consumer Service (owns batching, isolated DB)
- Scheduler (trigger only)
- CSV Generator
- Datacenter (thin FTP relay, no logic)

### ⚡ Must Say in Interview
- ✅ Idempotent chargeback creation (transactional, not just a lookup table)
- ✅ Outbox pattern solves the dual-write problem
- ✅ Atomic claim guarantees exactly-once batching, not fixed time windows alone
- ✅ Client DB and batch DB are isolated — no shared database
- ✅ Datacenter has zero business logic — pure relay
- ✅ No unnecessary ledger table — scope discipline
- ✅ Immutable event log for audit

### 🚀 Final Interview Line
"I designed a low-throughput, correctness-critical chargeback pipeline that isolates the client write path from batch processing, using the outbox pattern for reliable event delivery and an atomic claim to guarantee each chargeback is batched exactly once."
