# Saga Pattern — Cheat Sheet
*Distributed transaction management across microservices*

---

## What is the Saga Pattern?
A way to manage **distributed transactions** where a single business operation spans multiple services, each with its own database.
Instead of one ACID transaction, you break it into a **sequence of local transactions**, each triggering the next via an event or command.

> "Saga compensates. 2PC blocks. In fintech at scale, you can't afford to hold locks across service boundaries."

---

## Why Not 2PC (Two-Phase Commit)?
| | 2PC | Saga |
|---|---|---|
| Locking | Holds locks across all participants until confirmed | No distributed locks |
| Availability | Low — one participant down blocks all | High — compensate and move on |
| Latency | High | Low |
| Consistency | Strong (ACID) | Eventual |
| Fintech fit | ❌ Too fragile at scale | ✅ Right tradeoff |

---

## Two Types of Saga

### 1. Choreography (event-driven)
- Each service listens for events and reacts independently
- No central coordinator
- Simple to implement, hard to track the overall flow
- Best for: **short, simple flows**

### 2. Orchestration (command-driven)
- A central **Saga Orchestrator** tells each service what to do step by step
- Orchestrator persists state to DB after every step — resumable on crash
- Easier to monitor and debug
- Best for: **complex, multi-step flows (fintech default)**

---

## Compensating Transactions
If any step fails, **roll back** by running compensating transactions in reverse order on already-completed steps.

**Example — Money Transfer:**
```
1. Debit sender           ✅
2. Credit receiver        ❌ fails
→ Compensation: Refund sender
```

**Key rule:** Every forward step must have a defined compensating step designed upfront.

---

## Request Flow (Orchestrated Saga)

```
Client
  ↓
Saga Orchestrator  ←→  Saga State DB (persisted after each step)
  ↓
Step 1: Debit Service     → success → persist state → next step
  ↓
Step 2: Credit Service    → fail    → trigger compensation
  ↓
Compensation: Refund Service → retry until success
```

---

## Idempotency is Non-Negotiable
- Kafka delivers **at-least-once** — messages can be replayed
- Each saga step must be **idempotent**: same message processed twice = same result, no double-charge
- Use **idempotency keys** (composite key: saga_id + step_id) on every service
- Store processed keys in DB; check before processing

---

## Outbox Pattern + Kafka (Always Pair With Saga)
**The risk:** service completes DB write → crashes before publishing event → event lost, saga stalls.

**The fix:**
```
Within same local transaction:
  1. Write business data to DB
  2. Write event to outbox table in same DB

Separate poller:
  3. Reads outbox table
  4. Publishes to Kafka
  5. Marks outbox record as published
```
Guarantees **at-least-once delivery** without distributed locks.

---

## Failure Modes & Responses

| Failure | Response |
|---|---|
| Step fails midway | Trigger compensating transactions in reverse order |
| Compensation itself fails | Retry with exponential backoff; alert for manual intervention |
| Duplicate message (Kafka replay) | Idempotency key prevents double-processing |
| Orchestrator crashes | Read saga state from DB, resume from last known step |
| Compensation not possible | Escalate to manual review queue (last resort) |

---

## Saga State Persistence (Orchestration)
- Orchestrator writes current saga state to DB **after every step**
- State includes: saga_id, current_step, status (IN_PROGRESS / COMPENSATING / DONE / FAILED), timestamps
- On crash/restart: read state → resume from last completed step
- This is what makes orchestration resilient for long-running flows

---

## Choreography vs Orchestration

| | Choreography | Orchestration |
|---|---|---|
| Best for | Simple, short flows | Complex, multi-step flows |
| Coordinator | None — services react to events | Central orchestrator |
| Observability | Hard — flow is implicit across services | Easy — orchestrator owns the state |
| Failure handling | Each service handles its own compensation | Orchestrator drives compensation |
| Fintech preference | Rarely | Almost always |

---

## Fintech Example: Payment Flow (Orchestrated)

```
Saga Orchestrator
  → Step 1: Validate & Reserve Funds (Payment Service)
  → Step 2: Debit Account (Account Service)
  → Step 3: Credit Receiver (Account Service)
  → Step 4: Record Ledger Entry (Ledger Service)
  → Step 5: Send Notification (Notification Service)

On failure at Step 3:
  ← Compensate Step 2: Reverse Debit
  ← Compensate Step 1: Release Reserved Funds
```

---

## Key Interview Phrases

- *"In a payment flow, I'd use an orchestrated saga with the outbox pattern — the orchestrator persists state after each step so it's resumable, and idempotency keys on each service prevent double-processing on replay."*
- *"2PC blocks. Saga compensates. In fintech at scale, you can't afford to hold locks across service boundaries."*
- *"Every forward step has a compensating step — I design them together upfront, never as an afterthought."*
- *"Choreography is simpler but loses observability. For a complex payment flow, I want an orchestrator so I can see exactly where the saga is at any point."*
- *"I always pair Saga with the Outbox pattern — otherwise a crash between the DB write and the Kafka publish silently breaks the flow."*

---

## What Can Go Wrong

| Risk | Mitigation |
|---|---|
| Lost event (crash between write and publish) | Outbox pattern |
| Double-processing (Kafka replay) | Idempotency keys |
| Orchestrator is a SPOF | Run multiple instances; state is in DB not memory |
| Compensation fails | Retry with backoff; dead-letter queue; manual review |
| Saga runs forever | Timeout per step + saga-level timeout with forced compensation |



---

## Real-World Example: Interac e-Transfer (Canada)

Interac e-Transfer involves three parties — sender's bank, Interac's network,
and receiver's bank — making it a textbook Saga use case. A transfer left
half-completed is a regulatory and trust problem, not just a bug.

### Flow (Orchestrated Saga)
Saga Orchestrator (Interac Network Layer)
- **Step 1:**  Validate sender & check funds (Sender's Bank)
- **Step 2:** Place hold on sender's account (Sender's Bank)
- **Step 3:** Register transfer on Interac network
- **Step 4:** Notify receiver & await acceptance
- **Step 5:** Debit sender's account (Sender's Bank)
- **Step 6:** Credit receiver's account (Receiver's Bank)
- **Step 7:** Record ledger entry + audit log
- **Step 8:** Send confirmation to both parties
### Compensating Transactions

| Step Failed | Compensation |
|---|---|
| Step 3 (Interac registration fails) | Release hold on sender's account |
| Step 6 (Credit receiver fails) | Reverse debit, release hold, notify sender |
| Step 4 (Receiver never accepts) | Timeout → release hold → notify sender |

### Why Orchestration (Not Choreography)
- Multiple external parties (banks) — you need a central coordinator
- Regulatory requirement to know exactly where every transfer stands at any point
- Long-running flow (receiver may take hours to accept) — orchestrator
  persists state and resumes

### Key NFRs
- **Consistency**: Money must never be debited without being credited —
  compensations are mandatory, not optional
- **Idempotency**: Bank APIs must be idempotent — network retries must not
  double-debit or double-credit
- **Auditability**: Every saga step logged with timestamp for regulatory compliance
- **Timeout handling**: If receiver doesn't accept within 30 days,
  saga auto-compensates and returns funds

### Interview Talking Point
*"Interac e-Transfer is a great example — you have three parties, so you
can't use a single ACID transaction. I'd use an orchestrated Saga with the
Outbox pattern for guaranteed delivery between steps. The orchestrator persists
state after each step so if it crashes mid-transfer, it resumes exactly where
it left off. And every bank API call carries an idempotency key so retries
never double-charge."*