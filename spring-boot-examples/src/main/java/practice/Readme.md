# Spring Boot Practice

A hands-on Spring Boot practice project organized by topic.

---

## Modules

### 📦 bookstore
#### **Topic**: REST Controller fundamentals
#### **Covers**:
- @RestController
- @GetMapping
- @PostMapping
- @PutMapping
- @PatchMapping
- @DeleteMapping
- @PathVariable
- @RequestParam
- @RequestBody
- @Valid
- @RestControllerAdvice
- ResponseEntity, H2, JPA

**Base URL**: /api/v1/books

---

### 📦 ordering
**Topic**: @Transactional — transactions, rollback, propagation
**Covers**: @Transactional, readOnly, rollback, propagation,
multi-step DB operations, stock management, invoice generation

**Base URL**: /api/v1/orders

---

## How to Run
1. Run `SpringbootPracticeApplication.java`
2. H2 Console: http://localhost:8080/h2-console
    - JDBC URL: jdbc:h2:mem:bookstore
    - Username: sa
    - Password: (empty)
3. Run tests: `./test.sh`