# Transactional Outbox Pattern

> Status: Placeholder — concept-level notes.

## What it is
Ensures reliable event publishing when updating a database and sending a message must be atomic. Instead of writing to DB and publishing to a message broker in two separate steps (risking inconsistency), the service writes the event to an "outbox" table in the same local DB transaction as the business change. A separate relay process (polling or CDC like Debezium) reads the outbox table and publishes events to the message broker, then marks them as sent.

## When to use
- Need to atomically update state and emit an event (avoid dual-write problem).

## Key considerations
- At-least-once delivery; consumers must be idempotent.
- CDC-based relay (e.g., Debezium + Kafka Connect) avoids polling overhead.
- Outbox table cleanup/archival strategy.

## Related
- Saga Pattern (uses outbox for reliable step events), Event Sourcing, CQRS.
