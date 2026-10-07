# Retry Pattern

> Status: Placeholder — concept-level notes.

## What it is
Automatically retries a failed operation (typically a transient failure) instead of immediately surfacing the error, improving resilience for operations calling unreliable networks/services.

## Key techniques
- **Exponential backoff**: increase delay between retries exponentially to avoid overwhelming the dependency.
- **Jitter**: add randomness to backoff to avoid thundering-herd/synchronized retries across clients.
- **Max retry limit**: cap retries to avoid infinite loops; fall back to failure or dead-letter handling.
- **Idempotency**: retried operations must be safe to repeat (idempotent) to avoid duplicate side effects.

## When to use
- Transient failures: network blips, temporary unavailability, rate limiting (429/503 responses).
- NOT for permanent errors (e.g., 400 Bad Request) — retrying won't help.

## Related
- Circuit Breaker (often combined: stop retrying once circuit opens), Idempotent API design.
