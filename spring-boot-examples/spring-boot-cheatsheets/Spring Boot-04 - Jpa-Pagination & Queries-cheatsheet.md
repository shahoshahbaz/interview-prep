# Spring Data JPA — Pagination & Queries Cheat Sheet

---

## 1. What is Pagination?

Pagination means **returning data in chunks (pages)** instead of all at once.
Without pagination, returning 10,000 products in one response would be slow and wasteful.

```
Total 18 products, page size 5:
Page 0 → products 1-5
Page 1 → products 6-10
Page 2 → products 11-15
Page 3 → products 16-18  ← isLast = true
```

**Note:** Spring pages are **zero-indexed** — first page is `0`, not `1`.

---

## 2. Key Pagination Classes

### Pageable
An interface that carries **page number, page size, and sort info**.
You never create it directly — use `PageRequest` to build one.

```java
Pageable pageable = PageRequest.of(0, 5);                    // page 0, size 5
Pageable pageable = PageRequest.of(0, 5, Sort.by("name"));  // with sort
```

### PageRequest
The concrete implementation of `Pageable` — this is what you create.

```java
// page, size
PageRequest.of(0, 5)

// page, size, sort direction, field
PageRequest.of(0, 5, Sort.by("price").descending())
PageRequest.of(0, 5, Sort.by("name").ascending())

// dynamic sort direction
Sort sort = sortDir.equalsIgnoreCase("desc")
        ? Sort.by(sortBy).descending()
        : Sort.by(sortBy).ascending();
PageRequest.of(page, size, sort);
```

### Page\<T\>
What the repository returns — contains the **data AND metadata**.

```java
Page<Product> page = productRepository.findAll(pageable);

page.getContent()        // List<Product> — the actual data
page.getNumber()         // current page number (0-indexed)
page.getSize()           // page size
page.getTotalElements()  // total number of records in DB
page.getTotalPages()     // total number of pages
page.isLast()            // is this the last page?
page.isFirst()           // is this the first page?
page.hasNext()           // is there a next page?
page.hasPrevious()       // is there a previous page?
```

### Sort
Controls ordering of results.

```java
Sort.by("name")                          // ascending by default
Sort.by("price").ascending()
Sort.by("price").descending()
Sort.by("category").and(Sort.by("price")) // sort by multiple fields
```

---

## 3. PageResponse DTO

Never return `Page<T>` directly to the client — map it to your own DTO:

```java
@Data
public class PageResponse {
    private List<ProductResponse> content;  // the actual data
    private int pageNumber;                 // current page (0-indexed)
    private int pageSize;                   // items per page
    private long totalElements;             // total records in DB
    private int totalPages;                 // total pages
    private boolean isLast;                 // is this the last page
}
```

**Why not return Page\<T\> directly?**
- `Page<T>` has many internal Spring fields the client doesn't need
- Your DTO gives you control over exactly what the client sees
- Easier to change later without breaking the API contract

---

## 4. Repository Pagination Methods

```java
// built into JpaRepository
Page<Product> findAll(Pageable pageable);

// derived methods with pagination
Page<Product> findByCategory(String category, Pageable pageable);
Page<Product> findByPriceBetween(Double min, Double max, Pageable pageable);
Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
Page<Product> findByCategoryAndBrand(String category, String brand, Pageable pageable);
Page<Product> findByStockGreaterThan(Integer stock, Pageable pageable);
```

---

## 5. Derived Query Methods

Spring generates SQL automatically from the method name.

### Naming keywords

| Keyword | Example | SQL equivalent |
|---|---|---|
| `findBy` | `findByName` | `WHERE name = ?` |
| `And` | `findByCategoryAndBrand` | `WHERE category = ? AND brand = ?` |
| `Or` | `findByCategoryOrBrand` | `WHERE category = ? OR brand = ?` |
| `Between` | `findByPriceBetween` | `WHERE price BETWEEN ? AND ?` |
| `LessThan` | `findByPriceLessThan` | `WHERE price < ?` |
| `GreaterThan` | `findByPriceGreaterThan` | `WHERE price > ?` |
| `Containing` | `findByNameContaining` | `WHERE name LIKE %?%` |
| `StartingWith` | `findByNameStartingWith` | `WHERE name LIKE ?%` |
| `EndingWith` | `findByNameEndingWith` | `WHERE name LIKE %?` |
| `IgnoreCase` | `findByNameIgnoreCase` | `WHERE UPPER(name) = UPPER(?)` |
| `OrderBy` | `findByGenreOrderByPrice` | `ORDER BY price` |
| `IsNull` | `findByBrandIsNull` | `WHERE brand IS NULL` |
| `IsNotNull` | `findByBrandIsNotNull` | `WHERE brand IS NOT NULL` |
| `In` | `findByCategoryIn` | `WHERE category IN (?)` |

---

## 6. @Query — JPQL

JPQL uses **entity names and field names** — not table/column names.
DB-independent — works with any database.

```java
// basic JPQL
@Query("SELECT p FROM Product p WHERE p.price > :minPrice")
List<Product> findExpensive(@Param("minPrice") Double minPrice);

// with pagination
@Query("SELECT p FROM Product p WHERE p.price > :minPrice AND p.category = :category")
Page<Product> findExpensiveByCategory(
        @Param("minPrice") Double minPrice,
        @Param("category") String category,
        Pageable pageable);

// distinct values
@Query("SELECT DISTINCT p.category FROM Product p")
List<String> findAllCategories();

// JOIN FETCH — fixes N+1
@Query("SELECT a FROM Author a JOIN FETCH a.books")
List<Author> findAllWithBooks();
```

### @Param
Binds a method parameter to a named parameter in the query.

```java
@Query("SELECT p FROM Product p WHERE p.price > :minPrice")
//                                               ↑ named param in query
List<Product> findExpensive(@Param("minPrice") Double minPrice);
//                           ↑ binds method param to named param
```

---

## 7. @Query — Native SQL

Uses **actual table and column names**. DB-specific — tied to your database engine.

```java
@Query(value = "SELECT * FROM products WHERE price < :maxPrice ORDER BY price ASC",
       nativeQuery = true)
List<Product> findBudgetProducts(@Param("maxPrice") Double maxPrice);
```

### JPQL vs Native SQL

| | JPQL | Native SQL |
|---|---|---|
| Uses | Entity/field names | Table/column names |
| DB independent | ✅ Yes | ❌ No |
| Supports pagination | ✅ Yes | ⚠️ Needs countQuery |
| Performance | Standard | Can use DB-specific optimizations |
| Use when | Most cases | Complex DB-specific queries |

---

## 8. Controller — Pagination Parameters

```java
@GetMapping
public ResponseEntity<PageResponse> getAllProducts(
        @RequestParam(defaultValue = "0") int page,      // page number, starts at 0
        @RequestParam(defaultValue = "5") int size,      // items per page
        @RequestParam(defaultValue = "name") String sortBy,   // field to sort by
        @RequestParam(defaultValue = "asc") String sortDir) { // asc or desc
    return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, sortDir));
}
```

**Client calls:**
```
GET /api/v1/products                              → page 0, size 5, sort by name asc
GET /api/v1/products?page=1&size=10              → page 1, size 10
GET /api/v1/products?sortBy=price&sortDir=desc   → sorted by price descending
```

---

## 9. Service — Full Pagination Flow

```java
public PageResponse getAllProducts(int page, int size, String sortBy, String sortDir) {

    // 1. build sort
    Sort sort = sortDir.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    // 2. build pageable
    Pageable pageable = PageRequest.of(page, size, sort);

    // 3. query DB
    Page<Product> productPage = productRepository.findAll(pageable);

    // 4. map to response DTO
    return toPageResponse(productPage);
}

private PageResponse toPageResponse(Page<Product> page) {
    PageResponse response = new PageResponse();
    response.setContent(page.getContent().stream()
            .map(this::toResponse).collect(Collectors.toList()));
    response.setPageNumber(page.getNumber());
    response.setPageSize(page.getSize());
    response.setTotalElements(page.getTotalElements());
    response.setTotalPages(page.getTotalPages());
    response.setLast(page.isLast());
    return response;
}
```

---

## 10. Sample PageResponse JSON

```json
{
  "content": [
    { "id": 1, "name": "iPhone 15", "category": "Electronics", "price": 999.99 },
    { "id": 2, "name": "MacBook Pro", "category": "Electronics", "price": 2499.99 }
  ],
  "pageNumber": 0,
  "pageSize": 5,
  "totalElements": 18,
  "totalPages": 4,
  "last": false
}
```

---

## 11. Common Interview Questions

**Q: What is the difference between Page and List in Spring Data JPA?**
> `List` returns all matching records. `Page` returns a subset with metadata — total elements, total pages, current page, whether it's the last page.

**Q: What is Pageable?**
> An interface that carries pagination info — page number, page size, and sort. You create it using `PageRequest.of()`.

**Q: Why is the first page 0 in Spring pagination?**
> Spring follows zero-based indexing for pages — page 0 is the first page, page 1 is the second.

**Q: What is the difference between @Query JPQL and native SQL?**
> JPQL uses entity and field names and is DB-independent. Native SQL uses table and column names and is tied to a specific database.

**Q: What is @Param used for?**
> It binds a method parameter to a named parameter in a `@Query` — `:paramName` in the query maps to `@Param("paramName")` on the method parameter.

**Q: Why not return Page\<T\> directly from the controller?**
> `Page<T>` contains Spring internal fields the client doesn't need. A custom `PageResponse` DTO gives you full control over the API contract.

**Q: How do you sort dynamically in Spring Data JPA?**
> Build a `Sort` object based on user input, pass it into `PageRequest.of(page, size, sort)`, and Spring handles the rest.

---

## 12. What We Built — Pagination Module Summary

```
Endpoints:
GET /api/v1/products                          → all products, paginated + sorted
GET /api/v1/products/category/{category}      → by category, paginated
GET /api/v1/products/search?name=X            → search by name, paginated
GET /api/v1/products/price-range?min=X&max=Y  → price range, paginated
GET /api/v1/products/expensive?min=X&cat=Y    → @Query JPQL, paginated
GET /api/v1/products/out-of-stock             → @Query JPQL, list
GET /api/v1/products/categories               → @Query JPQL, distinct values
GET /api/v1/products/budget?maxPrice=X        → native @Query, list
POST /api/v1/products                         → create product
```

---

*Part of Spring Boot interview prep series — pagination practice module*