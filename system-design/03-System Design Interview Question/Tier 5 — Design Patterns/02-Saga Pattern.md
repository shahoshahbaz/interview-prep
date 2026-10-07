# Saga Pattern

> Status: Placeholder — concept-level notes.

## What it is
A pattern for managing data consistency across microservices in distributed transactions, without a 2PC-style global lock. A saga is a sequence of local transactions; each publishes an event/triggers the next step, and each step has a compensating action to undo it on failure.

## Variants
- **Choreography**: each service publishes events; others react (no central coordinator). Simple but harder to trace.
- **Orchestration**: a central orchestrator tells each service what to do next. Easier to monitor, adds a coordinator component.

## When to use
- Long-running business transactions spanning multiple services (e.g., order → payment → inventory → shipping).

## Key considerations
- Compensating transactions must be idempotent and ideally commutative.
- Eventual consistency, not atomicity.
- Need to handle partial failures and retries carefully.

## Related
- Transactional Outbox (reliable event publishing), Event Sourcing.
