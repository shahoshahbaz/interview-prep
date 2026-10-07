# Partner Integration Platform — Interview Cheat Sheet (45-min version)

## Problem (say this)
"Vantage integrates with retail partners like Home Depot to pull data (catalog, inventory, ad inventory, metrics) and push data (bids, creatives, campaign configs), at scale, without bespoke code per partner."

## Requirements (30 seconds)
- Pull + push, both poll AND webhook supported
- Onboard via config, not code
- Per-partner observability
- Isolation: one partner's failure doesn't cascade
- Scale: tens → hundreds of partners, MB-GB/day, highly variable

## BOE Numbers (memorize these)
- 200 partners, poll every 15 min → **~13 poll jobs/min**
- 195 normal (500MB/day) + 5 large (10GB/day) → **~150GB/day, ~4.5TB/month**
- **~5-10 workers** (with headroom)
- **Bottleneck = per-partner rate limits, not worker capacity or QPS**

## High-Level Flow (the core story — say this fast)
Three paths, one shared backbone:

**POLL:** Scheduler (cron) → Queue (partitioned by partner_id) → Worker → Rate Limiter → Circuit Breaker → Adapter (auth) → Outbound call → Adapter (parse) → Canonical Store

**WEBHOOK:** Partner POSTs → validate signature → Queue (carries data itself) → Worker → Adapter (parse only, NO rate limiter/circuit breaker/outbound call) → Canonical Store

**PUSH:** Internal event → Queue → Worker → Rate Limiter (SAME budget as poll) → Circuit Breaker → Adapter (auth+serialize) → Outbound call

**Cross-cutting:** Partner Registry feeds config to all three (not in hot path). Circuit breaker wraps outbound calls only. Observability layer gets status from every worker.

## Key Talking Points (say these verbatim if stuck)
1. "Poll and push share the same rate limiter, adapter, and circuit breaker — webhook skips all of it because data already arrived."
2. "Queue is a single Kafka topic partitioned by partner_id — not one topic per partner. Same key always lands on the same partition, giving per-partner ordering/isolation without hundreds of topics."
3. "Registry is config, not code — onboarding a new partner means adding a row, not a deploy."
4. "Rate limiting is checked before the call, not optimistic — avoids wasted calls and partner-side throttling penalties."
5. "Canonical Data Store is one schema per data_type, not per partner — every partner's inventory data lands in the same shape regardless of source format."

## Data Model (just the tables + one-liner each)
| Table | Purpose |
|---|---|
| `partners` | one row per partner — auth, base_url, rate_limit, webhook_secret |
| `partner_schedules` | one row per (partner, data_type) — cron, sync_mode, endpoint |
| `sync_jobs` | **append-only** event log (QUEUED/STARTED/SUCCESS/FAILED) — powers observability + reconciliation |
| Canonical Data Store | one schema per data_type — normalized, partner-agnostic |
| Rate limiter state | Redis only — `ratelimit:{partner_id} → tokens_remaining` |

## Failure Handling (short version)
- **Retries:** exponential backoff, capped attempts, transient errors only
- **Circuit breaker:** per-partner, opens on repeated failure, half-open to test, closes when healthy
- **Rate limit hit:** requeue/delay, don't fail
- **Partial failure:** store what succeeded, flag only failed records
- **DLQ:** after max retries, park for manual replay instead of losing it

## Reconciliation (the one people forget)
Periodic job compares **expected** SUCCESS count (from cron) vs **actual** SUCCESS count (from sync_jobs) per window. Gap → flag + alert → human investigates. This is the safety net for silent failures that never even generate a FAILED event (e.g. scheduler crash).

## Trade-offs (pick 2-3 to mention if asked)
- **Poll vs webhook** → support both, prefer webhook when available
- **Shared partitioned topic vs queue-per-partner** → shared topic, pragmatic at hundreds of partners
- **Config-driven adapter vs custom code** → config-driven default, custom code as escape hatch
- **Sync rate-limit check vs optimistic+retry** → check first, avoid throttling penalties
- **Eventual consistency + reconciliation vs strong consistency** → eventual, a few minutes of staleness is fine here

## If you only remember 5 things
1. Config-driven onboarding (registry = source of truth)
2. Queue partitioned by partner_id (isolation without per-partner infra)
3. Poll/push share rate limiter + circuit breaker; webhook doesn't
4. Canonical store = one schema per data_type, not per partner
5. Reconciliation catches silent gaps that normal failure handling can't
