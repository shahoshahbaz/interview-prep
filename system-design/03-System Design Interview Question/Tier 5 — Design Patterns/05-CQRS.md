# CQRS (Command Query Responsibility Segregation)

> Status: Placeholder — concept-level notes.

## What it is
Separates the write model (commands that change state) from the read model (queries). Writes go through a command handler that validates/applies business rules; reads are served from one or more denormalized, query-optimized projections, often updated asynchronously.

## Benefits
- Independently scale reads vs writes.
- Read models can be tailored/denormalized per use case (fast queries).
- Pairs naturally with Event Sourcing (events drive projection updates).

## Challenges
- Eventual consistency between write and read models.
- Added complexity: more moving parts, need to keep projections in sync.

## When to use
- Read and write workloads have very different scaling/shape requirements.

## Related
- Event Sourcing, Transactional Outbox (to publish events reliably for projections).
