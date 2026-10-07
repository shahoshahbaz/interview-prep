# Event Sourcing

> Status: Placeholder — concept-level notes.

## What it is
Instead of storing current state, store the full sequence of state-changing events. Current state is derived by replaying events (or from a materialized snapshot/projection).

## Benefits
- Full audit trail / history "for free".
- Can rebuild state at any point in time, or rebuild new projections later.
- Natural fit with event-driven architectures.

## Challenges
- Querying current state requires projections (often paired with CQRS).
- Schema evolution of events over time (versioning).
- Snapshotting needed to avoid replaying huge event logs.

## When to use
- Domains needing strong audit/history requirements (e.g., ledgers, financial transactions).

## Related
- CQRS (commonly paired together), Transaction Ledger design.
