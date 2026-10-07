# JPA & Hibernate — Interview Cheat Sheet

---

## 1. What is JPA & Hibernate?

- **JPA** (Java Persistence API) — a specification that defines how Java objects map to DB tables
- **Hibernate** — the most popular **implementation** of JPA
- **Spring Data JPA** — Spring's layer on top of JPA that makes repositories even easier

```
Your Code → Spring Data JPA → JPA (spec) → Hibernate (impl) → Database
```

**Interview one-liner:**
> *"JPA is the specification, Hibernate is the implementation — Spring Data JPA adds repository abstractions on top to reduce boilerplate."*

---

## 2. Core Entity Annotations

| Annotation | What it does |
|---|---|
| `@Entity` | Marks class as a JPA managed table |
| `@Entity(name = "X")` | Sets unique JPA entity name — required when two classes share the same name |
| `@Table(name = "X")` | Sets the actual DB table name |
| `@Id` | Marks the primary key |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)` | Auto-increment primary key |
| `@Column(nullable = false, length = 200)` | Customizes column constraints |
| `@Enumerated(EnumType.STRING)` | Stores enum as String not integer |
| `@Transient` | Excludes field from being persisted to DB |

### @GeneratedValue strategies

| Strategy | Behaviour |
|---|---|
| `IDENTITY` | DB auto-increment — most common, best for H2/MySQL |
| `SEQUENCE` | DB sequence object — best for PostgreSQL |
| `AUTO` | JPA picks strategy — avoid, can cause issues |
| `UUID` | Generates UUID string key |

---

## 3. Relationships

### @ManyToOne — many books belong to one author
```java
// Book.java — owns the relationship, has the foreign key column
@ManyToOne
@JoinColumn(name = "author_id")  // actual FK column in DB
private Author author;
```

### @OneToMany — one author has many books
```java
// Author.java — mirrors the relationship
@OneToMany(mappedBy = "author")  // "author" = field name in Book.java
private List<Book> books;
```

### @OneToOne — one order has one invoice
```java
// Invoice.java
@OneToOne
@JoinColumn(name = "order_id")
private Order order;
```

### @ManyToMany — students and courses
```java
// Student.java
@ManyToMany
@JoinTable(
    name = "student_courses",
    joinColumns = @JoinColumn(name = "student_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id")
)
private List<Course> courses;
```

### Who owns the relationship?
- The **owner** is always the side with `@JoinColumn` — it has the FK column in DB
- The **non-owner** uses `mappedBy` — it just mirrors the relationship
- Always `@ManyToOne` side is the owner

---

## 4. Fetch Types — Lazy vs Eager

### Default fetch types

| Relationship | Default |
|---|---|
| `@OneToMany` | `LAZY` |
| `@ManyToMany` | `LAZY` |
| `@ManyToOne` | `EAGER` |
| `@OneToOne` | `EAGER` |

### Lazy Loading
Load related data **only when accessed** — default for collections.

```java
@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
private List<Book> books;

// Usage
Author author = authorRepository.findById(1L); // SQL: SELECT * FROM authors only
author.getName();    // no extra query
author.getBooks();   // NOW it queries: SELECT * FROM books WHERE author_id = 1
```

### Eager Loading
Load related data **immediately** with the parent.

```java
@OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
private List<Book> books;

// Usage
Author author = authorRepository.findById(1L);
// SQL: SELECT * FROM authors JOIN jpa_books — loads everything immediately
```

**Interview one-liner:**
> *"Lazy loading defers DB queries until the data is actually accessed — eager loading fetches everything upfront. Lazy is the default for collections and preferred for performance."*

---

## 5. N+1 Problem

The most common JPA interview question.

### What is it?
Loading a list of entities and then accessing their relationships triggers **one extra query per entity**.

```java
// Load 3 authors — 1 query
List<Author> authors = authorRepository.findAll();

// Access books for each author — 1 query PER author
for (Author author : authors) {
    author.getBooks(); // triggers separate SQL query each time!
}
```

**Result:**
```sql
SELECT * FROM jpa_authors                          -- 1 query
SELECT * FROM jpa_books WHERE author_id = 1        -- query for author 1
SELECT * FROM jpa_books WHERE author_id = 2        -- query for author 2
SELECT * FROM jpa_books WHERE author_id = 3        -- query for author 3
-- Total: 1 + 3 = 4 queries (N+1)
```

With 1000 authors → **1001 queries**. DB gets hammered.

### Fix 1 — JOIN FETCH in @Query
```java
@Query("SELECT a FROM Author a JOIN FETCH a.books")
List<Author> findAllWithBooks();
```
Loads authors AND books in **one single query**.

### Fix 2 — @EntityGraph
```java
@EntityGraph(attributePaths = "books")
List<Author> findAll();
```
Same result — one query, cleaner syntax.

**Result after fix:**
```sql
SELECT a.*, b.* FROM jpa_authors a
JOIN jpa_books b ON b.author_id = a.id   -- 1 query total
```

**Interview one-liner:**
> *"The N+1 problem occurs when loading N entities triggers N additional queries for their relationships. Fix it with JOIN FETCH or @EntityGraph to load everything in one query."*

---

## 6. CascadeType

Cascade defines **what happens to child entities when you perform an operation on the parent**.

| Type | What it does |
|---|---|
| `ALL` | Applies ALL operations to children |
| `PERSIST` | Saving parent also saves children |
| `MERGE` | Updating parent also updates children |
| `REMOVE` | Deleting parent also deletes children |
| `REFRESH` | Refreshing parent also refreshes children |
| `DETACH` | Detaching parent also detaches children |

```java
// deleting an author also deletes all their books
@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Book> books;
```

### orphanRemoval = true
If a book is **removed from the author's list**, it gets deleted from DB automatically.

```java
author.getBooks().remove(book); // book gets deleted from DB
authorRepository.save(author);
```

### CascadeType.ALL vs orphanRemoval

| | `CascadeType.REMOVE` | `orphanRemoval = true` |
|---|---|---|
| Delete parent | ✅ deletes children | ✅ deletes children |
| Remove child from collection | ❌ child stays in DB | ✅ child deleted from DB |

**Interview one-liner:**
> *"CascadeType propagates operations from parent to children — REMOVE deletes children when parent is deleted. orphanRemoval goes further and deletes children removed from the collection."*

---

## 7. Spring Data JPA Repository Methods

### Auto-generated from method name
```java
List<Author> findByNameIgnoreCase(String name);
List<Book> findByGenre(String genre);
List<Book> findByPriceGreaterThan(Double price);
List<Book> findByGenreAndPriceLessThan(String genre, Double price);
Optional<Book> findFirstByGenreOrderByPriceDesc(String genre);
```

### Custom JPQL with @Query
```java
// JPQL — uses entity/field names not table/column names
@Query("SELECT b FROM Book b WHERE b.price > :minPrice")
List<Book> findExpensiveBooks(@Param("minPrice") Double minPrice);

// Native SQL — uses actual table/column names
@Query(value = "SELECT * FROM jpa_books WHERE price > :minPrice", nativeQuery = true)
List<Book> findExpensiveBooksNative(@Param("minPrice") Double minPrice);

// JOIN FETCH — fixes N+1
@Query("SELECT a FROM Author a JOIN FETCH a.books")
List<Author> findAllWithBooks();
```

### JPQL vs SQL
| | JPQL | Native SQL |
|---|---|---|
| Uses | Entity names | Table names |
| Uses | Field names | Column names |
| DB independent | ✅ Yes | ❌ No |

---

## 8. Dirty Checking

Hibernate keeps a **snapshot** of every loaded entity. At the end of a transaction it compares snapshots to current state and saves any changes automatically.

```java
@Transactional
public void updateTitle(Long id, String newTitle) {
    Book book = bookRepository.findById(id).orElseThrow();
    book.setTitle(newTitle);
    // no explicit save() needed — Hibernate detects the change automatically
}
```

---

## 9. Common Interview Questions

**Q: What is the difference between JPA and Hibernate?**
> JPA is the specification (interface), Hibernate is the most popular implementation. Spring Data JPA adds repository abstractions on top.

**Q: What is the N+1 problem and how do you fix it?**
> Loading N entities and accessing their lazy relationships triggers N additional queries. Fix with JOIN FETCH or @EntityGraph to load everything in one query.

**Q: What is the difference between LAZY and EAGER loading?**
> LAZY defers loading until the data is accessed. EAGER loads related data immediately with the parent. LAZY is default for collections and preferred for performance.

**Q: What is the difference between CascadeType.REMOVE and orphanRemoval?**
> CascadeType.REMOVE deletes children when the parent is deleted. orphanRemoval also deletes children when they are removed from the parent's collection.

**Q: What is dirty checking?**
> Hibernate snapshots entities when loaded and detects changes at transaction end — saving changes automatically without explicit save() calls.

**Q: What is mappedBy?**
> mappedBy marks the non-owning side of a relationship. It points to the field in the owning entity that holds the @JoinColumn. The owning side always has the FK column in DB.

**Q: What is the difference between @Query JPQL and native SQL?**
> JPQL uses entity and field names and is DB-independent. Native SQL uses table and column names and is tied to a specific DB.

**Q: What happens if you delete a parent with children and no cascade?**
> The DB throws a foreign key constraint violation — you must either cascade the delete or manually delete children first.

---

## 10. What We Built — JPA Module Summary

```
Author (one) ←→ Book (many)

Author.java
- @OneToMany(mappedBy = "author", fetch = LAZY, cascade = ALL, orphanRemoval = true)

Book.java
- @ManyToOne
- @JoinColumn(name = "author_id")

AuthorRepository
- findByNameIgnoreCase(String name)

BookRepository
- findByGenre(String genre)
- @Query JOIN FETCH → findAllWithAuthor() — fixes N+1

Endpoints:
GET  /api/v1/authors         → getAllAuthors()      — N+1 present
GET  /api/v1/authors/fixed   → getAllAuthorsFixed()  — N+1 fixed
GET  /api/v1/authors/{id}    → getAuthorById()
POST /api/v1/authors         → createAuthor()
DELETE /api/v1/authors/{id}  → deleteAuthor() — cascades to books
```

---

*Part of Spring Boot interview prep series — JPA practice module*