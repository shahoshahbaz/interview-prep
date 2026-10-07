# @Transactional & ResponseEntity — Interview Cheat Sheet

---

## 1. What is @Transactional?

A transaction means **all or nothing**. If you're doing multiple DB operations and one fails,
everything rolls back — no partial data saved.

```java
// Without @Transactional — if step 2 fails, step 1 is already saved. Corrupted data!
public void placeOrder(OrderRequest request) {
    bookRepository.save(jpaBook);       // step 1 — saved
    orderRepository.save(order);     // step 2 — fails. Inconsistent data!
}

// With @Transactional — if step 2 fails, step 1 rolls back automatically
@Transactional
public void placeOrder(OrderRequest request) {
    bookRepository.save(jpaBook);       // step 1
    orderRepository.save(order);     // step 2 — fails → step 1 rolled back
}
```

**Interview one-liner:**
> *"@Transactional wraps all DB operations in a single transaction — if anything fails,
> everything rolls back automatically, preventing partial or corrupted data."*

---

## 2. Where to put @Transactional?

Always on the **service layer** — never on the controller or repository.

```java
@Service                            // ✅ correct
@Transactional(readOnly = true)
public class OrderService { ... }

@RestController                     // ❌ wrong layer
@Transactional
public class OrderController { ... }
```

The service owns the business logic and knows which operations belong together in one transaction.

---

## 3. readOnly = true

Tells Hibernate this transaction will **only read data, not write**.

```java
@Service
@Transactional(readOnly = true)  // default for all methods — reads only
public class OrderService {

    // inherits readOnly = true — correct, only reads DB
    public OrderResponse getOrder(Long id) { ... }

    // overrides class level — writes to DB
    @Transactional
    public OrderResponse placeOrder(OrderRequest request) { ... }
}
```

**What readOnly = true does:**
- Tells Hibernate to skip **dirty checking** — no snapshot comparison at end of transaction
- Allows DB-level read optimizations
- Faster performance for read-only operations

**Best practice:** set `readOnly = true` at class level, override with `@Transactional` on write methods.

**Interview one-liner:**
> *"`readOnly = true` tells Hibernate to skip dirty checking since no writes will happen
> — it's a performance optimization for read-only operations."*

---

## 4. Dirty Checking

Hibernate normally keeps a **snapshot** of every object you load. At the end of the transaction
it compares the snapshot to the current state to see if anything changed and needs saving.

```java
@Transactional
public void updateTitle(Long id, String newTitle) {
    Book jpaBook = bookRepository.findById(id).orElseThrow();
    jpaBook.setTitle(newTitle);
    // no explicit save() needed — Hibernate detects the change and saves automatically
}
```

With `readOnly = true` — Hibernate skips this entirely. Faster for reads.

---

## 5. Rollback Rules

| Scenario | Rolls back? |
|---|---|
| `RuntimeException` thrown | ✅ Yes — default |
| `Error` thrown | ✅ Yes — default |
| Checked `Exception` thrown | ❌ No — default |
| `rollbackFor = Exception.class` | ✅ Yes — explicit |

```java
// Roll back on checked exceptions too
@Transactional(rollbackFor = Exception.class)
public void placeOrder(OrderRequest request) throws Exception { ... }

// Never roll back on a specific exception
@Transactional(noRollbackFor = InsufficientStockException.class)
public void placeOrder(OrderRequest request) { ... }
```

**Interview one-liner:**
> *"By default @Transactional only rolls back on RuntimeException and Error —
> not on checked exceptions. Use rollbackFor to change this."*

---

## 6. Propagation

Propagation defines **what happens when a @Transactional method calls another @Transactional method**.

### REQUIRED (default)
Join the existing transaction if one exists, otherwise create a new one.
They share the same transaction — fail together, succeed together.

```
placeOrder()              ← transaction starts
    │
    ├── bookRepository.save()      ← inside placeOrder's transaction
    ├── orderRepository.save()     ← inside placeOrder's transaction
    └── createInvoice()            ← JOINS the same transaction (REQUIRED)
            │
            └── invoiceRepository.save()  ← still inside placeOrder's transaction

If createInvoice() fails → everything rolls back including bookRepository.save()
```

### REQUIRES_NEW
Always create a new transaction, suspend the existing one.
Independent — fails alone, doesn't affect the caller's transaction.

```
placeOrder()              ← transaction 1 starts
    │
    └── logAction()       ← transaction 1 PAUSED, transaction 2 starts (REQUIRES_NEW)
            │
            └── if logAction() fails — only transaction 2 rolls back
                placeOrder's transaction 1 continues unaffected
```

### Propagation levels quick reference

| Propagation | Behaviour | Use case |
|---|---|---|
| `REQUIRED` | Join existing or create new (default) | Most service methods |
| `REQUIRES_NEW` | Always new, suspend existing | Audit logging, independent operations |
| `NESTED` | Nested within existing transaction | Partial rollback within a transaction |
| `SUPPORTS` | Join if exists, non-transactional if not | Optional transaction |
| `NOT_SUPPORTED` | Always non-transactional | Reporting, no transaction needed |
| `NEVER` | Throw if transaction exists | Must run outside transaction |
| `MANDATORY` | Throw if no transaction exists | Must run inside transaction |

```java
@Transactional(propagation = Propagation.REQUIRED)    // default
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Transactional(propagation = Propagation.NESTED)
```

---

## 7. Self-Invocation Trap ⚠️

**Most common interview trap about @Transactional.**

```java
@Service
public class OrderService {

    public void methodA() {
        methodB(); // ← @Transactional on methodB is IGNORED!
    }

    @Transactional
    public void methodB() { ... }
}
```

**Why?** Spring uses a **proxy** to intercept calls and apply the transaction.
Self-calls bypass the proxy entirely — the transaction never starts.

**Fix:** inject the service into itself or restructure into separate services.

**Interview one-liner:**
> *"Self-invocation bypasses Spring's proxy, so @Transactional is ignored on internal method calls
> — this is one of the most common Spring transaction bugs."*

---

## 8. Common Interview Questions

**Q: What does @Transactional do?**
> Wraps the method in a DB transaction — if any exception occurs, all DB operations roll back.

**Q: On which layer should @Transactional be placed?**
> The service layer — it owns the business logic and defines transaction boundaries.

**Q: Does @Transactional roll back on checked exceptions?**
> No — only on RuntimeException and Error by default. Use `rollbackFor = Exception.class` to include checked exceptions.

**Q: What is the difference between REQUIRED and REQUIRES_NEW?**
> REQUIRED joins the existing transaction — they share the same fate.
> REQUIRES_NEW starts a fresh independent transaction — it can fail without affecting the caller.

**Q: What happens if you call a @Transactional method from within the same class?**
> The transaction is bypassed — Spring's proxy is not involved in self-calls,
> so the @Transactional annotation has no effect.

**Q: What is readOnly = true?**
> A performance hint that tells Hibernate to skip dirty checking — no writes will happen in this transaction.

---

## 9. ResponseEntity

`ResponseEntity` gives full control over the HTTP response — status code, headers, and body.

```java
// 200 OK — shortcut
ResponseEntity.ok(body)

// 201 Created — resource was created
ResponseEntity.status(HttpStatus.CREATED).body(body)

// 204 No Content — success but nothing to return (DELETE)
ResponseEntity.noContent().build()

// 404 Not Found
ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found")

// 400 Bad Request
ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)

// 409 Conflict — request valid but conflicts with current state
ResponseEntity.status(HttpStatus.CONFLICT).body("Insufficient stock")
```

### When to use which status code

| Status | Code | When to use |
|---|---|---|
| OK | 200 | Successful GET, PUT, PATCH |
| Created | 201 | Successful POST — resource created |
| No Content | 204 | Successful DELETE — nothing to return |
| Bad Request | 400 | Validation failed, malformed request |
| Unauthorized | 401 | Not authenticated |
| Forbidden | 403 | Authenticated but not authorized |
| Not Found | 404 | Resource does not exist |
| Conflict | 409 | Valid request but conflicts with current state |
| Internal Server Error | 500 | Unexpected server error |

**Interview one-liner:**
> *"`ResponseEntity` gives full control over the HTTP response — status code, headers,
> and body — rather than just returning data and defaulting to 200."*

---

## 10. Ordering Module — What We Built

A multi-step transaction scenario: place an order, deduct stock, create invoice.
If any step fails — everything rolls back.

```
POST /api/v1/orders
    │
    ├── 1. Find jpaBook or throw BookNotFoundException (404)
    ├── 2. Check stock or throw InsufficientStockException (409)
    ├── 3. Deduct stock → bookRepository.save()
    ├── 4. Create order → orderRepository.save()
    └── 5. Create invoice → invoiceRepository.save()
              │
              └── if this fails → steps 3 and 4 roll back automatically
```

**Test rollback:**
```json
POST /api/v1/orders
{ "bookId": 2, "quantity": 1, "simulateInvoiceFailure": true }
```

---

*Part of Spring Boot interview prep series — ordering practice module*