# Spring Boot Annotations Cheat Sheet

---

## 1. Core Stereotype Annotations: 
Stereotype annotations are markers that register a class as a Spring bean and communicate its role in the application architecture — they all work the same way but add semantic clarity.

| Annotation | Used On | What it does |
|---|---|---|
| `@Component` | Class | Generic Spring-managed bean |
| `@Service` | Class | Business logic layer — same as `@Component` but semantically meaningful |
| `@Repository` | Class/Interface | Data access layer — also translates SQL exceptions into Spring exceptions |
| `@Controller` | Class | Web MVC controller — returns views (HTML) |
| `@RestController` | Class | `@Controller` + `@ResponseBody` — returns JSON/XML, not views |

They all do the same thing under the hood — register the class as a Spring bean. The difference is just semantic — they communicate the purpose of the class.
````
@Component   →  "I am a Spring managed object"
@Service     →  "I am a Spring managed object that handles business logic"
@Repository  →  "I am a Spring managed object that talks to the DB"
@Controller  →  "I am a Spring managed object that handles HTTP requests"
````
---

## 2. REST Controller Annotations

| Annotation | Used On | What it does |
|---|---|---|
| `@RequestMapping("/path")` | Class / Method | Base URL prefix for all endpoints in the controller |
| `@GetMapping` | Method | Maps HTTP GET requests |
| `@PostMapping` | Method | Maps HTTP POST requests |
| `@PutMapping` | Method | Maps HTTP PUT requests (full update) |
| `@PatchMapping` | Method | Maps HTTP PATCH requests (partial update) |
| `@DeleteMapping` | Method | Maps HTTP DELETE requests |
| `@PathVariable` | Parameter | Extracts `{id}` from the URL path → `/books/{id}` |
| `@RequestParam` | Parameter | Extracts `?genre=Tech` from the query string |
| `@RequestBody` | Parameter | Deserializes JSON request body into a Java object |
| `@ResponseBody` | Method / Class | Serializes return value to JSON (included in `@RestController`) |
| `@ResponseStatus` | Method / Class | Sets the default HTTP status code for a response |

### @RequestParam options
```java
@RequestParam(required = false) String genre        // optional param
@RequestParam(defaultValue = "0") int page          // default value if not provided
@RequestParam(name = "q") String query              // map ?q= to variable named differently
```

---

## 3. Dependency Injection:
Dependency Injection means Spring automatically provides the objects a class needs instead of the class
creating itself.

**withoug DI- you manage everything**

```java

import com.springboot.practice.bookstore.service.BookService;

public class bookController {
    // you are creating the depedency yourself-tightly copuled
    private BookService bookService = new BookService();
}
```

**Problem:**
- BookController is locked to BookService — hard to swap
- Hard to test — you can't replace BookService with a mock
- If BookService needs dependencies too, you have to create those manually as well

## 3. Dependency Injection Annotations

| Annotation | Used On | What it does |
|---|---|---|
| `@Autowired` | Field / Constructor / Setter | Injects a Spring bean automatically |
| `@Qualifier("name")` | Parameter / Field | Specifies which bean to inject when multiple exist |
| `@Primary` | Class | Marks a bean as the default when multiple candidates exist |
| `@Bean` | Method (in `@Configuration`) | Declares a Spring bean manually |
| `@Configuration` | Class | Marks a class as a source of bean definitions |

> **Best practice**: prefer constructor injection over `@Autowired` on fields — easier to test and dependencies are explicit.

```java
// Field injection (avoid)
@Autowired
private BookService bookService;

// Constructor injection (preferred)
public BookController(BookService bookService) {
    this.bookService = bookService;
}
```

---

## 4. Validation Annotations (`jakarta.validation.constraints`)

| Annotation | Used On | What it does |
|---|---|---|
| `@Valid` | Method parameter | Triggers validation on the annotated object |
| `@NotNull` | Field | Rejects `null` only |
| `@NotBlank` | String field | Rejects `null`, `""`, and `"   "` (whitespace) |
| `@NotEmpty` | String / Collection | Rejects `null` and empty, but allows whitespace |
| `@Size(min, max)` | String / Collection | Validates length/size range |
| `@Min(value)` | Integer / Long | Minimum numeric value |
| `@Max(value)` | Integer / Long | Maximum numeric value |
| `@DecimalMin(value)` | Double / BigDecimal | Minimum decimal value |
| `@DecimalMax(value)` | Double / BigDecimal | Maximum decimal value |
| `@Email` | String | Validates email format |
| `@Pattern(regexp)` | String | Validates against a regex pattern |
| `@Positive` | Number | Must be > 0 |
| `@PositiveOrZero` | Number | Must be >= 0 |

> `@NotBlank` fires when `@Valid` is used on the `@RequestBody` parameter in the controller.

---

## 5. JPA / Entity Annotations (`jakarta.persistence`)

| Annotation | Used On | What it does |
|---|---|---|
| `@Entity` | Class | Marks class as a JPA-managed database table |
| `@Table(name="...")` | Class | Customizes the table name |
| `@Id` | Field | Marks the primary key |
| `@GeneratedValue` | Field | Auto-generates the primary key value |
| `@Column` | Field | Customizes column name, nullable, length etc. |
| `@Enumerated(EnumType.STRING)` | Enum field | Stores enum as String (`"DRAFT"`) not integer |
| `@Transient` | Field | Excludes field from being persisted to the DB |
| `@OneToMany` | Field | One-to-many relationship |
| `@ManyToOne` | Field | Many-to-one relationship |
| `@ManyToMany` | Field | Many-to-many relationship |
| `@JoinColumn` | Field | Specifies the foreign key column |

### @GeneratedValue strategies
```java
@GeneratedValue(strategy = GenerationType.IDENTITY)  // DB auto-increment (most common)
@GeneratedValue(strategy = GenerationType.SEQUENCE)  // DB sequence
@GeneratedValue(strategy = GenerationType.UUID)      // UUID string key
```

### @Column options
```java
@Column(nullable = false, length = 200, name = "book_title", unique = true)
```

---

## 6. Exception Handling Annotations

| Annotation | Used On | What it does |
|---|---|---|
| `@RestControllerAdvice` | Class | Global exception handler for ALL controllers |
| `@ControllerAdvice` | Class | Same but for MVC (non-REST) controllers |
| `@ExceptionHandler(X.class)` | Method | Catches a specific exception type and handles it |

> Spring matches the **most specific** exception first, then falls back to the generic `Exception` catch-all.

---

## 7. Spring Boot Auto-Configuration Annotations

| Annotation | Used On | What it does |
|---|---|---|
| `@SpringBootApplication` | Main class | Combines `@Configuration` + `@EnableAutoConfiguration` + `@ComponentScan` |
| `@EnableAutoConfiguration` | Class | Tells Spring Boot to auto-configure beans based on classpath |
| `@ComponentScan` | Class | Tells Spring where to scan for beans |

---

## 8. application.properties Key Properties

```properties
# Server
server.port=8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:bookstore
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop   # create-drop / update / validate / none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

---

## 9. HTTP Status Codes Quick Reference

| Status | Code | When to use |
|---|---|---|
| OK | 200 | Successful GET, PUT, PATCH |
| Created | 201 | Successful POST (resource created) |
| No Content | 204 | Successful DELETE (no body returned) |
| Bad Request | 400 | Validation failed / malformed request |
| Not Found | 404 | Resource does not exist |
| Internal Server Error | 500 | Unexpected server error |

```java
ResponseEntity.ok(body)                              // 200
ResponseEntity.status(HttpStatus.CREATED).body(x)   // 201
ResponseEntity.noContent().build()                   // 204
ResponseEntity.status(HttpStatus.NOT_FOUND).body(x) // 404
```

---

## 10. Why Wrapper Types in Entities?

| Type | Use case |
|---|---|
| `Long`, `Integer`, `Double` | Entity fields — can be `null` (maps to DB NULL) |
| `long`, `int`, `double` | Local variables — cannot be `null`, defaults to `0` |

> Primitives cannot represent `null`. JPA needs `null` to distinguish "not set" from "set to zero".
> `@Id` must always be a wrapper (`Long`, not `long`) because before saving, id is `null`.

---

*Generated during Spring Boot REST + JPA practice — bookstore example*