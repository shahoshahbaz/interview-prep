# Design P2P Payment Engine (e-Transfer)

## Table of Contents
1. [Problem Statement](#1-problem-statement)
2. [Clarifications & Assumptions](#2-clarifications--assumptions)
3. [Requirements & Goals](#3-requirements--goals)
4. [BOE / Capacity Estimation](#4-boe--capacity-estimation)
5. [High-Level Architecture](#5-high-level-architecture)
6. [Component Summary](#6-component-summary)
7. [System APIs](#7-system-apis)
8. [Data Model](#8-data-model)
9. [Component Design - Transfer Flow](#9-component-design---transfer-flow)
10. [State Machine](#10-state-machine)
11. [Failure Handling](#11-failure-handling)
12. [Consistency & Correctness Guarantees](#12-consistency--correctness-guarantees)
13. [Scalability & Sharding](#13-scalability--sharding)
14. [Security](#14-security)
15. [Trade-offs & Alternatives](#15-trade-offs--alternatives)
16. [Wrap-Up Cheat Sheet](#16-wrap-up-cheat-sheet)

---

## 1. Problem Statement

Design a P2P Payment Engine (e-Transfer) that:

1. Allows a sender to initiate a money transfer using a recipient's email or phone number
2. Routes the transfer through Interac's network, acting as a trusted intermediary hub between sender's and recipient's financial institutions
3. Supports two fulfillment flows: Autodeposit (straight-through) and Standard e-Transfer (security Q&A accept flow)
4. Guarantees exactly-once money movement — debited from sender once, credited to recipient once
5. Handles transfer expiry, cancellation, and unclaimed fund reconciliation
6. Notifies all parties at each stage of the transfer lifecycle
7. Maintains a complete immutable audit log for regulatory compliance
8. Ensures high availability — a network or bank outage must not result in lost or duplicated funds

---

## 2. Clarifications & Assumptions

| Question | Assumption |
|---|---|
| Scale | ~1B transfers/year |
| Transfer limits | Enforced by sending FI, not Interac |
| Currency | CAD only |
| Fund holding | Interac is a messaging/routing hub — funds stay at FIs |
| Fraud detection | Out of scope — handled by FIs |
| Regulatory | FINTRAC reporting in scope (audit log must support it) |

---

## 3. Requirements & Goals

### Functional Requirements
1. Initiate transfer via recipient email/phone
2. Route via Autodeposit (straight-through) or Standard Q&A flow
3. Accept, cancel, or expire a transfer
4. Notify sender and recipient at each lifecycle event
5. Idempotent transfer initiation (no duplicate money movement)
6. Query transfer status
7. Immutable audit log per transfer

### Non-Functional Requirements
1. **Availability:** 99.99% (~4.3 min/month downtime)
2. **Latency:** Initiation < 500ms; Autodeposit end-to-end < 30s
3. **Exactly-once:** No double debit or double credit under any failure
4. **Durability:** Zero data loss on committed transfers
5. **Scalability:** ~1B transfers/year
6. **Security:** Encrypt Q&A at rest; mTLS between services
7. **Auditability:** FINTRAC-compliant immutable event log

---

## 4. BOE / Capacity Estimation

### Assumptions

| Metric | Assumption |
|---|---|
| Transfers/year | 1B |
| Peak multiplier | 3x (holidays, end of month) |
| Avg transfer record size | 2 KB |
| Audit log entry size | 500 B |
| Avg state transitions/transfer | 5 |
| Retention | 7 years (regulatory) |

### Traffic Estimation
- Transfers/day = 1B / 365 = ~2.7M/day
- Avg Write QPS = 2.7M / 86,400 = ~31 QPS
- Peak Write QPS = 31 × 3 = ~93 QPS
- Read QPS (status checks, ~3x writes) = ~93 QPS avg, ~280 QPS peak

### Storage Estimation
- Daily transfer records = 2.7M × 2KB = ~5.4 GB/day
- Daily audit log = 2.7M × 5 events × 500B = ~6.75 GB/day
- Total daily = ~12 GB/day
- Yearly = ~4.4 TB/year
- 7-year retention = ~30 TB

---

## 5. High-Level Architecture

```
Sender FI ──────────────────────────────────── Recipient FI
    │                                                │
    └──────────────► API Gateway ◄──────────────────┘
                          │
                   Transfer Service
                  /       │        \
      Idempotency    Directory    Transfer DB
         Store        Service
                          │
                   Fulfillment Engine
                  /       │        \
          Notify      Settlement   Audit Log
          Service      Service
                          │
                   Reconciliation Service
```

**Key insight:** Interac is a messaging and routing hub — it never holds money. Funds stay in the Sender FI suspense account until settlement is confirmed.

---

## 6. Component Summary

### Layer 1 — FI Layer
- **Sender FI / Recipient FI** — Authenticate users, debit/credit accounts, hold funds in suspense. Interac never touches the money.

### Layer 2 — API Gateway
- **API Gateway** — Entry point for all FI calls. Handles mTLS auth, rate limiting, and routes to Transfer Service.

### Layer 3 — Orchestration
- **Transfer Service** — Brain of the system. Owns full transfer lifecycle, enforces state transitions, coordinates all downstream components.
- **Fulfillment Engine** — Decides routing: Autodeposit (straight-through) or Standard Q&A (wait for recipient accept).

### Layer 4 — Supporting Services
- **Directory Service** — Resolves recipient email/phone to a registered FI. Flags if Autodeposit is enabled.
- **Idempotency Store** — Deduplicates transfer requests using idempotency key from Sender FI.
- **Transfer DB** — Source of truth for all transfer records and state transitions.

### Layer 5 — Output
- **Notification Service** — Sends email/SMS at each lifecycle event (async via Kafka).
- **Settlement Service** — Triggers actual fund movement between FIs (synchronous, exactly-once).
- **Audit Log** — Immutable append-only record of every state change (FINTRAC compliance).

### Layer 6 — Safety Net
- **Reconciliation Service** — Scheduled jobs to catch expired transfers, trigger refunds, fix mismatches between Transfer DB and FI settlement records.

---

## 7. System APIs

### 1. Initiate Transfer
```
POST /v1/transfers
Headers: Idempotency-Key, Authorization
Body: sender_account_id, recipient_handle, amount, currency, security_question, security_answer_hash, message
Response 201: transfer_id, status, expires_at
```

### 2. Accept Transfer (Q&A flow)
```
POST /v1/transfers/{transfer_id}/accept
Body: security_answer
Response 200: transfer_id, status
```

### 3. Cancel Transfer
```
POST /v1/transfers/{transfer_id}/cancel
Response 200: transfer_id, status
```

### 4. Get Transfer Status
```
GET /v1/transfers/{transfer_id}
Response 200: transfer_id, status, amount, currency, initiated_at, deposited_at
```

### 5. Register Autodeposit
```
POST /v1/autodeposit/register
Body: handle, account_id, fi_id
Response 201: autodeposit_id, status
```

---

## 8. Data Model

### Transfer
```
transfer(transfer_id PK, sender_fi_id, recipient_handle, amount, currency, status, idempotency_key, security_question, security_answer_hash, expires_at, created_at, updated_at)
```

### Transfer Event (audit log)
```
transfer_event(event_id PK, transfer_id FK, event_type, previous_status, new_status, actor, created_at)
```

### Directory (Autodeposit registry)
```
directory(directory_id PK, handle, fi_id, account_id, autodeposit_enabled, created_at, updated_at)
```

### Idempotency Store
```
idempotency(idempotency_key PK, transfer_id, response_payload, status, expires_at, created_at)
```

### Settlement Record
```
settlement(settlement_id PK, transfer_id FK, sender_fi_id, recipient_fi_id, amount, currency, status, settled_at, created_at)
```

**Key notes:**
- `security_answer_hash` — Interac stores only a hash, never plaintext
- `transfer_event` is append-only — no updates, no deletes
- `idempotency` TTL-based — expires after 24 hours
- `handle` in directory is indexed — high-frequency lookup on every transfer

---

## 9. Component Design - Transfer Flow

### Happy Path: Autodeposit
```
Sender FI
  → POST /v1/transfers (with idempotency key)
  → API Gateway (auth + rate limit)
  → Transfer Service
      → Check idempotency store (new request)
      → Lookup Directory (autodeposit enabled)
      → Write transfer record (status: INITIATED)
      → Fulfillment Engine
          → Notify sender (transfer sent)
          → Notify recipient (funds incoming)
          → Trigger Settlement [SYNC]
          → Update status: DEPOSITED
          → Write to Audit Log [ASYNC]
```

### Happy Path: Standard Q&A
```
Sender FI
  → POST /v1/transfers
  → Transfer Service
      → Lookup Directory (no autodeposit)
      → Write transfer record (status: PENDING)
      → Notify recipient (accept required) [ASYNC]

Recipient
  → POST /v1/transfers/{id}/accept (with answer)
  → Transfer Service
      → Validate security answer
      → Update status: ACCEPTED
      → Trigger Settlement [SYNC]
      → Update status: DEPOSITED
      → Notify both parties [ASYNC]
      → Write to Audit Log [ASYNC]
```

### Expiry Path
```
Scheduler (runs hourly)
  → Find transfers where status=PENDING and expires_at < now
  → Update status: EXPIRED
  → Notify sender [ASYNC]
  → Trigger refund to Sender FI suspense account
  → Write to Audit Log [ASYNC]
```

### Sync vs Async Summary

| Operation | Sync/Async | Reason |
|---|---|---|
| Idempotency check | Sync | Must block before any processing |
| Directory lookup | Sync | Need result to decide routing |
| Transfer DB write | Sync | Must persist before proceeding |
| Security answer validation | Sync | Must confirm before accepting |
| Settlement | Sync | Money movement, exactly-once critical |
| Notification | Async (Kafka) | Delay acceptable, must not block transfer |
| Audit log write | Async (Kafka) | Append-only, eventual is fine |
| Reconciliation | Async (scheduled job) | Background, not in critical path |

---

## 10. State Machine

```
                    ┌─────────────┐
                    │  INITIATED  │
                    └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │   PENDING   │ ← Q&A flow only
                    └──────┬──────┘
          ┌────────────────┼────────────────┐
          │                │                │
   ┌──────▼──────┐         │        ┌───────▼──────┐
   │  CANCELLED  │         │        │    EXPIRED   │
   └─────────────┘         │        └──────────────┘
                    ┌──────▼──────┐
                    │  ACCEPTED   │
                    └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │  DEPOSITED  │
                    └─────────────┘
```

### Transition Rules

| From | To | Trigger |
|---|---|---|
| INITIATED | PENDING | Q&A flow — waiting for recipient |
| INITIATED | ACCEPTED | Autodeposit — straight through |
| PENDING | ACCEPTED | Recipient answers correctly |
| PENDING | CANCELLED | Sender cancels |
| PENDING | EXPIRED | 30 days elapsed, scheduler fires |
| ACCEPTED | DEPOSITED | Settlement confirmed by Recipient FI |

**Key rules:**
- DEPOSITED, CANCELLED, EXPIRED are terminal states — no further transitions
- Only PENDING transfers can expire or be cancelled
- Autodeposit skips PENDING entirely

---

## 11. Failure Handling

### Main Failure Scenarios

**1. Duplicate request (client retry)**
```
→ Idempotency key already in store
→ Return cached response immediately, no reprocessing
```

**2. Transfer Service crashes after DB write, before Settlement**
```
→ Transfer stuck in ACCEPTED state
→ Reconciliation detects stale ACCEPTED transfers
→ Retries Settlement
```

**3. Settlement times out**
```
→ Transfer stays in ACCEPTED
→ Retry with exponential backoff (1s → 2s → 4s → max 30s)
→ After max retries → mark FAILED, trigger refund
```

**4. Recipient FI unreachable (Autodeposit)**
```
→ Fulfillment Engine retries with backoff
→ After max retries → fall back to Q&A flow
→ Notify recipient to accept manually
```

**5. Expiry job missed (scheduler failure)**
```
→ Reconciliation catches transfers where expires_at < now and status = PENDING
→ Forces EXPIRED transition → triggers refund
```

### Retry Strategy

| Scenario | Strategy |
|---|---|
| Settlement timeout | Exponential backoff, max 3 retries |
| Notification failure | Async retry via Kafka, non-blocking |
| Directory lookup failure | Fail fast, return error to Sender FI |
| Reconciliation | Scheduled job every 1 hour |

**Golden rule:** Always write state before calling downstream. On recovery, resume from last known state — never restart from scratch.

---

## 12. Consistency & Correctness Guarantees

### Strong vs Eventual Consistency

| What | Consistency | Why |
|---|---|---|
| Transfer state (DB write) | Strong | Can never have two conflicting states |
| Idempotency store | Strong | Must block duplicates immediately |
| Settlement | Strong | Money movement must be exactly once |
| Notification delivery | Eventual | Slight delay acceptable |
| Audit log | Eventual | Append-only, order matters but delay ok |

### Exactly-Once Money Movement

Three guarantees working together:
1. **Idempotency key** — prevents duplicate transfer creation
2. **Write-ahead state** — transfer persisted before any money moves
3. **Optimistic locking** — only one process transitions a transfer at a time

### Concurrency Control

```sql
UPDATE transfer
SET status = 'ACCEPTED', version = version + 1
WHERE transfer_id = ? AND version = ? AND status = 'PENDING'
```
If zero rows updated → another process already transitioned → discard.

---

## 13. Scalability & Sharding

### Bottlenecks & Solutions

| Component | Bottleneck | Solution |
|---|---|---|
| Transfer DB | Write volume at peak | Shard by transfer_id |
| Directory Service | High read volume (every transfer) | Cache in Redis, TTL 1 hour |
| Idempotency Store | High read/write, low latency needed | Redis with persistence |
| Notification Service | Spike at peak hours | Async queue (Kafka) |
| Settlement Service | FI rate limits | Queue + throttle per FI |

### Sharding Strategy
- **Transfer DB** — shard by `transfer_id`: random distribution, even load, all queries by transfer_id
- **Directory DB** — shard by `handle`: all lookups by handle, low write volume

### How the System Handles Millions of Requests
1. **Horizontal scaling** — Transfer Service is stateless → auto scale behind load balancer
2. **Caching** — Directory lookups hit Redis 95%+ of the time
3. **Async offload** — Notifications and Audit Log go to Kafka, off the critical path
4. **DB sharding** — writes distributed across shards
5. **Connection pooling** — avoid connection exhaustion under spike

> Auto scaling handles compute spikes. Caching handles read spikes. Sharding handles write spikes. Kafka handles burst offload. You need all four.

---

## 14. Security

| Concern | Solution |
|---|---|
| Transport | mTLS on all FI ↔ Interac communication |
| Security answer | Sender FI hashes before sending; Interac stores hash only |
| FI Authentication | Client certificates at API Gateway |
| Authorization | Each FI can only initiate transfers from their own accounts |
| Data at rest | Security answer hash encrypted at rest |
| Rate limiting | Per FI at API Gateway |
| Audit & Compliance | Every state transition logged with actor, timestamp, IP |
| Retention | 7 years (FINTRAC) |

**One-liner:** Interac is a trusted hub — it never sees credentials, never stores plaintext answers, and never holds funds.

---

## 15. Trade-offs & Alternatives

### 1. Synchronous Settlement vs Async
| | Chosen (Sync) | Alternative (Async) |
|---|---|---|
| Pro | Exactly-once guaranteed, simpler state | Higher throughput |
| Con | Slower if FI is slow | Risk of double settlement, complex recovery |
| **Verdict** | Money movement — sync wins every time | |

### 2. Optimistic vs Pessimistic Locking
| | Chosen (Optimistic) | Alternative (Pessimistic) |
|---|---|---|
| Pro | No lock contention, scales well | Simpler logic |
| Con | Needs retry on conflict | Deadlock risk, poor throughput |
| **Verdict** | Low conflict rate — optimistic fits | |

### 3. Kafka vs Direct Call for Notifications
| | Chosen (Kafka) | Alternative (Direct call) |
|---|---|---|
| Pro | Decoupled, survives notification outage | Simpler |
| Con | Eventual delivery | Notification failure blocks transfer |
| **Verdict** | Non-critical path — Kafka wins | |

### 4. Redis vs DB for Idempotency Store
| | Chosen (Redis) | Alternative (DB) |
|---|---|---|
| Pro | Sub-millisecond lookup, TTL built-in | Durable by default |
| Con | Needs persistence config (AOF/RDB) | Slower, adds DB load |
| **Verdict** | On every request — Redis wins | |

### 5. Shard by transfer_id vs sender_id
| | Chosen (transfer_id) | Alternative (sender_id) |
|---|---|---|
| Pro | Even distribution, no hot shards | Co-locates sender history |
| Con | Can't query all sender transfers on one shard | Power users = hot shards |
| **Verdict** | Uniform distribution more important at scale | |

---

## 16. Wrap-Up Cheat Sheet

### What Interac Is
> A trusted messaging and routing hub between financial institutions. It never holds money, never sees credentials, never stores plaintext answers.

### Key Numbers
| Metric | Value |
|---|---|
| Availability target | 99.99% (~4.3 min/month) |
| Initiation latency | < 500ms |
| Autodeposit end-to-end | < 30s |
| Transfers/year | ~1B |
| Avg write QPS | ~31 QPS |
| Peak write QPS | ~93 QPS |
| Storage (7 years) | ~30 TB |

### Core Design Decisions
| Decision | Why |
|---|---|
| Idempotency key per transfer | Prevents duplicate money movement on retry |
| Write state before calling downstream | Recovery always resumes from last known state |
| Settlement is synchronous | Money movement must be exactly-once |
| Notifications async via Kafka | Must not block transfer on notification failure |
| Optimistic locking on state transition | Prevents race conditions without deadlocks |
| Shard Transfer DB by transfer_id | Even distribution, no hot shards |
| Redis for idempotency + directory cache | Sub-ms latency on every request |
| Suspense account at Sender FI | No money lost on Interac outage |

### State Machine (quick reference)
```
INITIATED → PENDING (Q&A) or ACCEPTED (Autodeposit)
PENDING → ACCEPTED / CANCELLED / EXPIRED
ACCEPTED → DEPOSITED
```
Terminal states: DEPOSITED, CANCELLED, EXPIRED

### Failure Recovery (quick reference)
- Crash after DB write → reconciliation retries from last state
- Settlement timeout → exponential backoff, then refund
- Duplicate request → idempotency store returns cached response
- Expiry missed → hourly reconciliation job catches it