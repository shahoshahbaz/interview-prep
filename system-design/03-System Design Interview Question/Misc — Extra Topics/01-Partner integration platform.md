# System Design — Partner Integration Platform

* [Problem Statement & Requirements](#1-problem-statement--requirements)
* [Estimation](#2-estimation)
* [High-Level Design](#3-high-level-design)
* [Components Deep-Dive](#4-components-deep-dive)
* [APIs](#5-apis)
* [Data Model](#6-data-model)
* [Flows](#7-flows)
* [Failure Handling](#8-failure-handling)
* [Trade-offs](#9-trade-offs)

---

## 1. Problem Statement & Requirements

### Problem Statement
Vantage needs to onboard and manage integrations with multiple retail partners (e.g., The Home Depot, and others as the business grows). Each partner exposes their own REST API with different authentication schemes, rate limits, and data formats. Vantage needs to pull data from partners (product catalog, inventory, ad placement inventory, campaign performance metrics), push data to partners (campaign configs, creative assets, bid updates), and do this reliably at scale — without each new partner requiring a one-off, bespoke solution.

### Functional Requirements
- **FR1:** Pull data from partners — catalog, inventory, ad placement inventory, campaign performance metrics
- **FR2:** Push data to partners — campaign configurations, creative assets, bid updates
- **FR3:** Support both sync modes per partner — scheduled polling *and* partner-initiated webhooks
- **FR4:** Onboard a new partner via configuration, not a code rewrite (auth type, rate limits, data format all vary per partner)
- **FR5:** Provide per-partner observability — sync status, failure count, data staleness

### Non-Functional Requirements
- **NFR1 — Reliability:** One partner's failure must not cascade to others (isolation)
- **NFR2 — Extensibility:** New partner = config change, not core logic change
- **NFR3 — Observability:** Sync health visible per partner at all times
- **NFR4 — Rate-limit compliance:** Never exceed a partner's own limits (avoid throttling/bans)
- **NFR5 — Scalability:** Tens of partners today → hundreds; data volume per partner ranges from MB/day to GB/day (highly variable, not uniform)

### Explicitly Out of Scope (per problem framing)
- Real-time/synchronous user-facing calls — this is background/async data sync, not in the request path of a user action

### Clarifying Questions (asked, with answers given)
| Question | Answer |
|---|---|
| Polling or webhook? | Both — some partners push webhooks, others require polling |
| Sync or async? | Mostly async/background |
| Scale? | Tens → hundreds of partners; MB–GB/day per partner, highly variable |

### Terminology: Pull vs. Push vs. Poll vs. Webhook
```
PULL (direction: partner → Vantage)
 ├── via POLLING  → Vantage initiates, on a schedule ("any updates?")
 └── via WEBHOOK  → Partner initiates, on their own trigger (partner calls Vantage's endpoint)

PUSH (direction: Vantage → partner)
 └── via OUTBOUND CALL → Vantage initiates, whenever it has data ready
```
- **Pull vs. push** = which way the data moves.
- **Poll vs. webhook** = a sub-choice that only applies *within* pull, describing who kicks off each transfer.
- **Push has no poll/webhook sub-choice** — it's just Vantage making a normal outbound API call to the partner, same mechanism as polling, just sending data instead of fetching it. No webhook involved on either side.

---

## 2. Estimation

**Assumptions:**
- 200 partners at steady state
- Each partner polled every 15 min
- 195 "normal" partners send ~500MB/day; 5 "large" partners send ~10GB/day
- Each poll job takes ~5 seconds (partner API call + processing)

### Poll jobs per minute
- 1 partner: 60 min ÷ 15 min = **4 polls/hour**
- 200 partners: 200 × 4 = **800 polls/hour**
- Per minute: 800 ÷ 60 = **~13 polls/min**

### Total daily data ingest
*(= how much data flows into Vantage from all partners, added up, per day)*
- Normal partners: 195 × 500MB = **~97.5GB/day**
- Large partners: 5 × 10GB = **50GB/day**
- Total: 97.5 + 50 = **~150GB/day**

### Monthly volume
- 150GB/day × 30 days = **~4.5TB/month**

### Worker count needed
- 1 worker throughput: 60 sec ÷ 5 sec/job = **12 jobs/min**
- Workers needed for 13 jobs/min: 13 ÷ 12 = 1.1 → round up = **2 workers minimum**
- With headroom for retries/spikes/slow partners (2–3x): **~5–10 workers**

### Takeaway
- ~13 jobs/min, ~150GB/day, ~4.5TB/month → **moderate scale, not high-QPS**
- Long tail (5 partners = 1/3 of total volume) → per-partner throughput must be independently tunable, not a global constant
- Bottleneck is **per-partner rate limits**, not total worker capacity — a partner capped at 1 req/sec queues regardless of how many workers exist

---

## 3. High-Level Design

### Design principle
Separate **what a partner is** (config) from **how sync happens** (engine).

### Diagram

![High-Level Design Diagram](./images/03-high-level-design-diagram.png)

**Note:** Circuit Breaker conceptually *wraps* the outbound call (checks state before calling, records outcome after) rather than being a strictly sequential step — the visual draws it as a step for clarity, but it should be described as a guard around the call, not a stage after it.

<details>
<summary>ASCII fallback (if image doesn't render)</summary>

```
PULL PATH — POLLING (Vantage-initiated)
────────────────────────────────────────
[Scheduler] ── fires on cron ── creates INSTRUCTION ("fetch Home Depot inventory")
                                          │
                                          ▼
                              [Queue: partitioned by partner_id]
                                          │
                                          ▼
                              [Worker Pool] picks up instruction
                                          │
                                          ▼
                              [Rate Limiter] — am I allowed to call this partner now?
                                          │
                                          ▼
                              [Adapter: attach Auth (OAuth2/API key/HMAC)]
                                          │
                                          ▼
                              [Outbound HTTP call] ──► Partner's REST API
                                          │
                                          ▼ (response comes back)
                              [Adapter: parse Format (JSON/XML/CSV → canonical)]
                                          │
                                          ▼
                              [Canonical Data Store]


PULL PATH — WEBHOOK (Partner-initiated)
────────────────────────────────────────
[Partner] ── POSTs data directly ──► [Vantage Webhook Ingestion API]
                                          │
                                          ▼
                              [Validate signature] (using webhook_secret from registry)
                                          │
                                          ▼
                              [Queue: partitioned by partner_id] — job carries the DATA itself
                                          │
                                          ▼
                              [Worker Pool] picks up job
                                          │
                                          ▼
                              [Adapter: parse Format (JSON/XML/CSV → canonical)]
                                    (no rate limiter, no outbound call — data already arrived)
                                          │
                                          ▼
                              [Canonical Data Store]


PUSH PATH (Vantage-initiated, Vantage → partner)
────────────────────────────────────────
[Internal Event] (bid changed, creative uploaded)
                                          │
                                          ▼
                              [Queue: partitioned by partner_id]
                                          │
                                          ▼
                              [Worker Pool] picks up job
                                          │
                                          ▼
                              [Rate Limiter] — same gate as polling, same partner limit shared
                                          │
                                          ▼
                              [Adapter: attach Auth + format payload]
                                          │
                                          ▼
                              [Outbound HTTP call] ──► Partner's REST API


CROSS-CUTTING (applies across all three paths)
────────────────────────────────────────
[Partner Registry] ── feeds config into: Scheduler cron, Rate Limiter limits, Adapter auth/format rules
[Circuit Breaker] ── wraps every OUTBOUND call (polling + push only — webhook has no outbound call to protect)
[Observability Layer] ── every worker reports status here (success/fail/timestamp) regardless of path
```

</details>

### Key structural points
- **Polling and push share the same rate-limit budget per partner** — if Home Depot allows 5 req/sec total, that's shared across both polling calls and push calls to them, not 5+5.
- **Webhook path has no rate limiter, no outbound call, no circuit breaker** — the partner already pushed the data; there's nothing to protect against.
- **Queue partitioned by `partner_id`** — same key always lands in the same partition, and a partition is owned by one consumer at a time (within a consumer group), which is what gives per-partner ordering and isolation without needing hundreds of separate queues/topics.
- **Registry sits outside the hot path** — read by the components that need config (scheduler, rate limiter, adapter), not itself in the per-request flow.

---

## 4. Components Deep-Dive

### 4.1 Partner Registry
- **Responsibility:** single source of truth for everything partner-specific
- **Fields:** `partner_id`, `auth_type` (OAUTH2/API_KEY/HMAC), `auth_config` (encrypted), `base_url`, `endpoints` map, `data_format` (JSON/XML/CSV), `rate_limit` (req/sec, daily quota, burst), `sync_mode` (POLL/WEBHOOK/BOTH), `poll_schedule` (cron), `webhook_secret`
- **Storage — two layers, not either/or:**
    - **Postgres = source of truth.** Durable, structured (partner → auth_type → rate limits are relational), low write volume (not registering partners thousands of times/sec) — no reason to avoid a normal DB for the actual data.
    - **Redis (or in-memory per worker) = cache in front of it.** Hot-path reads (every job, every worker needs this partner's config) shouldn't hit Postgres directly — cache as `partner_id → config blob`, refreshed on write-through or periodic re-pull (config changes rarely).
- **Talking point:** *"Postgres is the registry, Redis is a cache in front of it — DB is truth, cache is speed, same pattern you'd use anywhere else. This is what turns 'new partner' from a code change into a config change."*

### 4.2 Scheduler
- **Responsibility:** on cron per partner (per data type — catalog hourly, inventory every 15 min, etc.), creates an **instruction** and enqueues it — never calls the partner directly
- **Design decision:** must itself be distributed/HA (e.g., leader-election or a distributed cron like Kubernetes CronJobs / Quartz with DB-backed locking) — a single scheduler instance is a SPOF
- **Talking point:** *"The scheduler's only job is deciding WHEN — the actual call happens downstream at the worker, after a rate-limit check."*

### 4.3 Webhook Ingestion API
- **Responsibility:** lightweight public endpoint per partner (`/webhooks/{partner_id}/...`) that receives partner-initiated pushes
- **Design decision:** validate signature immediately (using `webhook_secret` from registry) before doing anything else — reject unsigned/invalid requests fast
- **Design decision:** endpoint should do minimal work — validate, then enqueue — and return 200 quickly, so the partner's webhook sender doesn't time out or retry unnecessarily
- **Talking point:** *"Webhook jobs carry the data itself, not an instruction — that's why this path skips rate limiter and outbound call entirely."*

### 4.4 Queue (partitioned by `partner_id`)
- **Responsibility:** decouple job creation from job execution; provide per-partner isolation and ordering
- **Design decision (open trade-off — mention if it comes up):** could be a single topic partitioned by `partner_id`, or two tiered topics (high-volume vs. standard) each partitioned the same way — not literally one queue/topic per partner (operational overhead at hundreds of partners)
- **Design decision:** over-provision partition count ahead of partner growth, since repartitioning doesn't cleanly preserve key→partition mapping
- **Talking point:** *"Same key always lands in the same partition, and a partition is owned by one consumer at a time — that combination gives per-partner isolation and ordering without one queue per partner."*

### 4.5 Worker Pool
- **Responsibility:** consume jobs, orchestrate the rate-limit check → adapter → outbound call → store sequence
- **Design decision:** I/O-bound work (mostly waiting on partner API responses) → favor concurrency within a pod (multiple threads/goroutines) over just adding more pods
- **Design decision:** scale via HPA on queue depth (consumer lag), not just CPU — lag is the real signal that you're falling behind
- **Talking point:** *"Worker count solves parallelism across partners. It does nothing for a single partner capped at 1 req/sec — that's the rate limiter's job."*

### 4.6 Rate Limiter
- **Responsibility:** enforce each partner's own outbound call limit before the call happens
- **Design decision:** token bucket per partner (allows short bursts up to a cap, refills over time) — matches how most partner APIs actually define limits
- **Design decision:** shared state across all worker pods (Redis-backed counters/tokens) — a per-pod in-memory limiter would undercount if multiple pods call the same partner
- **Design decision:** shared budget across polling AND push for the same partner (they hit the same partner API, same limit)
- **Talking point:** *"This is the component that keeps Vantage from getting throttled or banned — it's the only thing standing between the worker and the partner's actual limit."*

### 4.7 Adapter Layer (Auth + Format)
- **Responsibility:** the extensibility seam — translates between Vantage's generic engine and each partner's specific auth/format requirements
- **Design decision:** two independent strategy interfaces — `AuthStrategy` (OAuth2Handler / ApiKeyHandler / HmacSigner) and `FormatParser` (JSON/XML/CSV ↔ canonical) — selected per partner from registry config, composed independently (a partner's auth type and data format are orthogonal choices)
- **Design decision:** OAuth2 tokens need refresh-before-expiry logic — adapter (or a shared token cache) handles refresh, not each individual call
- **Talking point:** *"New partner = new registry row, plus a new adapter implementation ONLY if their auth/format combo doesn't already exist among the strategies. Core sync logic never changes."*

### 4.8 Circuit Breaker
- **Responsibility:** stop calling a partner that's clearly failing, so workers aren't wasted retrying a dead endpoint
- **Design decision:** per-partner circuit breaker (not global) — one partner's failures shouldn't trip the breaker for others
- **States:** Closed (normal) → Open (failure threshold hit, calls skipped/fast-failed) → Half-Open (after cooldown, allow a trial call to test recovery)
- **Talking point:** *"This wraps the outbound call — before calling, it checks its own state; after calling, it records the outcome. It's a guard, not a sequential step."*

### 4.9 Canonical Data Store
- **Responsibility:** hold normalized data, so downstream consumers never touch partner-specific formats
- **Design decision:** schema per data type (catalog, inventory, campaign metrics), not per partner — that's the whole point of normalization
- **Design decision:** hot data (90 days) in the primary store, older archived to cold storage — bounds cost as partner count grows
- **Talking point:** *"Once data lands here, the campaign engine and reporting never know or care whether it originally came in as Home Depot's XML or another partner's CSV."*

### 4.10 Observability Layer
- **Responsibility:** per-partner sync health — last successful sync timestamp, failure count, staleness, circuit breaker state
- **Design decision:** every worker emits a status event regardless of path (poll/webhook/push) — one unified pipeline for metrics, not per-path reporting
- **Talking point:** *"This is what directly answers the problem statement's question: 'is partner X's sync failing, how stale is partner Y's data.'"*

---

## 5. APIs

### 5.1 Partner Registry — Admin API
Lets ops onboard/manage partners: create, read, update, pause a partner's config (auth type, rate limit, format, schedule). This is the "onboarding = config action" mechanism in practice.

### 5.2 Webhook Ingestion API
Receives partner-pushed data. Validates the signature first, then enqueues — responds fast, doesn't wait for processing.

### 5.3 Internal Push Trigger
Not partner-facing — internal events (bid updated, creative uploaded, campaign config changed) that kick off the push path. Triggered by other parts of Vantage, not by an external caller.

### 5.4 Observability API
Read-only, used by dashboards. Returns per-partner health: last successful sync, failure count, staleness, circuit breaker state. This directly answers "is partner X failing / how stale is partner Y."

### 5.5 Adapter Contract
Not a real API — the internal interface every partner adapter implements: `attach auth to a request`, `parse partner format → canonical`, `serialize canonical → partner format`. This is the actual extensibility mechanism (FR4).

**Talking point:** *"Onboarding only touches the admin API. Everything else — webhook, push, observability — is generic infrastructure that already exists before partner #1 shows up."*

---

## 6. Data Model

**`partners`** (partner_id, name, status, auth_type, auth_config, base_url, data_format, rate_limit, webhook_secret, created_at, updated_at)
One row per partner. Facts shared across everything Vantage does with that partner.

**`partner_schedules`** (id, partner_id, data_type, sync_mode, cron_expression, endpoint_path, is_active)
Many rows per partner — one per data type. Holds what varies by data type (schedule, endpoint), separate from partner-level facts.

**`sync_jobs`** — append-only event log (job_id, partner_id, data_type, event_type [QUEUED/STARTED/SUCCESS/FAILED], timestamp, error_message)
One new row per state change, never updated. "Current status" = latest row per job_id. Source of truth for observability and reconciliation — no SUCCESS event for an expected job = a gap to investigate.

**Canonical Data Store** — one schema per data_type (partner_id, entity_id, ...normalized fields..., last_updated_by_partner, ingested_at)
Holds the actual fetched data, normalized to one shape regardless of which partner it came from. Downstream consumers only ever read this shape.

**Rate limiter state** — Redis: `ratelimit:{partner_id} → {tokens_remaining, last_refill_ts}`
Not a table — shared, fast-access state checked before every outbound call.

**Talking point:** *"`partners` vs. `partner_schedules` follows the same normalization principle used throughout — separate what's true of the partner as a whole from what varies per data type. `sync_jobs` being append-only gives observability and reconciliation for free, rather than needing a separate audit mechanism."*

---

## 7. Flows

### 7.1 Poll Flow
Scheduler cron matches → append `sync_jobs: QUEUED` → Kafka message (partner_id, data_type) → worker consumes, appends `STARTED` → look up `partners` for auth/rate_limit → rate limiter check → circuit breaker check → adapter attaches auth → outbound call → success: parse response, write Canonical Store, append `SUCCESS` / failure: retry w/ backoff, append `FAILED`.

### 7.2 Webhook Flow
Partner POSTs payload + signature → validate against `webhook_secret` (401 if invalid) → append `QUEUED` → Kafka message **carries the data itself** → return 200 fast → worker consumes, appends `STARTED` → adapter parses format only (**no rate limiter, no outbound call, no circuit breaker**) → write Canonical Store → append `SUCCESS`.

### 7.3 Push Flow
Internal event (e.g. `bid.updated`) → append `QUEUED` → Kafka message (partner_id, action, payload) → worker consumes, appends `STARTED` → look up `partners` (**same shared rate budget as poll**) → rate limiter → circuit breaker → adapter attaches auth + serializes payload → outbound call → append `SUCCESS`/`FAILED`.

### 7.4 Reconciliation Flow
Periodic job (e.g. every 30 min) → per `partner_schedules` row, compute **expected** SUCCESS count from its cron (e.g. every 15 min → 4/hour) → query `sync_jobs` for **actual** SUCCESS count in that window → expected > actual → flag gap → surfaces in Observability API + alert → human investigates (silent crash, dead scheduler, unreachable partner) and resolves manually. Reconciliation detects gaps; it doesn't auto-fix them.

**Talking point:** *"Poll and push share the rate-limiter/adapter/circuit-breaker chain; webhook skips it because data already arrived. Reconciliation catches the case normal failure handling can't — a job that silently never ran at all, not one that ran and failed."*

---

## 8. Failure Handling

**Retries:** exponential backoff, capped attempts (e.g. 3), only on transient errors (timeouts, 5xx) — not on 4xx.

**Circuit breaker:** per-partner. Repeated failures → opens → skip calls for a cooldown (avoid hammering a down partner) → half-open to test → closes if healthy again.

**Rate limit hit:** don't fail the job — requeue/delay until budget refills.

**Partial failures:** batch partially parses → store what succeeded, flag only the failed records, not the whole job.

**Dead letter queue:** after max retries, job goes to a DLQ instead of vanishing — allows manual inspection and replay.

---

## 9. Trade-offs

**Poll vs. webhook:** poll is simple, works for any partner, but wastes calls and adds latency. Webhook is instant and efficient but needs partner support plus signature-validation complexity. → Support both, prefer webhook when available.

**Shared Kafka topic (partitioned by partner) vs. queue per partner:** shared topic is operationally simple but a noisy partner can lag others on that partition. Dedicated queues give true isolation but are heavy to manage at hundreds of partners. → Shared, partitioned topic as the standard.

**Config-driven adapters vs. custom code per partner:** config + generic adapter scales onboarding without deploys, but can't handle a truly nonstandard API. Custom code handles anything but doesn't scale operationally. → Default to config-driven; custom adapter code as an escape hatch for outliers.

**Synchronous rate limiting vs. optimistic calls + retry:** checking budget first avoids wasted calls and partner-side throttling penalties, costs a bit of latency. Optimistic calls are faster when under budget but risk throttling/bans. → Check first.

**Strong vs. eventual consistency in Canonical Store:** immediate consistency is simpler to reason about but ties ingestion speed to write latency. Eventual, backed by reconciliation as a safety net, scales better and fits this domain — a few minutes of staleness is acceptable. → Eventual + reconciliation.

---
