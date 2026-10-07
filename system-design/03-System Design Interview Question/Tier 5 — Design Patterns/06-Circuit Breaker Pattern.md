# Circuit Breaker Pattern

> Status: Placeholder — concept-level notes.

## What it is
Prevents a service from repeatedly calling a downstream dependency that is failing, by "tripping" after a failure threshold and short-circuiting further calls (failing fast) for a cooldown period, then allowing limited trial calls to test recovery.

## States
- **Closed**: calls flow normally; failures are tracked.
- **Open**: calls fail immediately without hitting the dependency, after failure threshold exceeded.
- **Half-Open**: after a timeout, allow a few trial calls; if they succeed, close the circuit, else reopen.

## Benefits
- Prevents cascading failures and resource exhaustion (thread/connection pool saturation).
- Fails fast, improving overall system responsiveness during partial outages.

## When to use
- Calling unreliable external/downstream services (common with microservices, 3rd-party APIs).

## Related
- Retry Pattern (often combined with circuit breaker + exponential backoff), Bulkhead pattern.
