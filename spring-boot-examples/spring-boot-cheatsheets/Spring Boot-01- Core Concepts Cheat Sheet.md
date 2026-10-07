# Spring Boot Core Concepts — Interview Cheat Sheet

---

## 1. What is Spring Boot?

Spring Boot = **Spring Framework + Auto-configuration + Embedded Server (Tomcat)**

- You don't configure everything manually — Spring Boot guesses sensible defaults from your classpath
- No need to deploy a WAR file — it runs as a standalone JAR with an embedded Tomcat

**Interview one-liner:**
> *"Spring Boot removes boilerplate configuration from Spring Framework — you get a production-ready app with minimal setup."*

---

## 2. What is a Bean?

A bean is a **Java object whose lifecycle is managed by Spring** instead of by you.

```java
// Without Spring — you manage it
BookService service = new BookService();

// With Spring — Spring manages it
@Service
public class BookService { ... }  // Spring creates, stores, and injects this
```

Spring stores all beans in a container called the **Application Context**.

**What makes a class a bean?**

| Annotation | Layer |
|---|---|
| `@Component` | Generic |
| `@Service` | Business logic |
| `@Repository` | Data access |
| `@Controller` | MVC web |
| `@RestController` | REST API |

**Interview one-liner:**
> *"A bean is a Java object created and managed by the Spring container — I don't call `new`, Spring does."*

---

## 3. What is Dependency Injection (DI)?

Dependency Injection means **Spring automatically passes the objects a class needs** instead of the class creating them itself.

```java
// Without DI — tightly coupled, hard to test
public class BookController {
    private BookService bookService = new BookService(); // bad
}

// With DI — loosely coupled, easy to test
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService; // Spring passes it in
    }
}
```

### 3 types of injection
1. Constructor injection ✅ preferred

``` java
   public BookController(BookService bookService) {
   this.bookService = bookService;
   }
```
2. Field injection ❌ avoid
```java
   @Autowired
   private BookService bookService;
```
3.  Setter injection ⚠️ optional deps only

```java
@Autowired
   public void setBookService(BookService bookService) {
   this.bookService = bookService;
   }
```
**Interview one-liner:**
> *"DI means a class doesn't create its own dependencies — Spring creates and injects them, making code loosely coupled and easily testable."*

**Why constructor injection is preferred**

| | Constructor | Field |
| :--- | :--- | :--- |
| **Testable** | ✅ pass mock in constructor | ❌ needs reflection |
| **Immutable** | ✅ field can be `final` | ❌ can't be `final` |
| **Dependencies explicit** | ✅ visible in constructor | ❌ hidden in class |
| **Works without Spring** | ✅ yes | ❌ no |


---

## 4. What is @Autowired?

`@Autowired` tells Spring to inject a bean automatically.

```java
// Field injection (avoid)
@Autowired
private BookService bookService;

// Constructor injection (preferred — @Autowired optional since Spring 4.3)
public BookController(BookService bookService) {
    this.bookService = bookService;
}
```

> Since **Spring 4.3**, if a class has only one constructor, `@Autowired` is not needed — Spring injects automatically.

---

## 5. What is the Application Context?

The Application Context is **Spring's container that holds all beans**.

- At startup, Spring scans your packages, finds all annotated classes, creates beans, and stores them
- When a bean is needed, Spring retrieves it from the context and injects it

**Interview one-liner:**
> *"The Application Context is Spring's central registry of all beans — it creates them, wires them together, and manages their lifecycle."*

---

## 6. What is @ComponentScan?

Tells Spring **which packages to scan** for beans.

```java
@SpringBootApplication  // includes @ComponentScan automatically
public class Application { ... }
```

Spring scans the package of the main class and **all sub-packages** automatically.
This is why your beans must live under `com.springboot.practice` — not outside it.

---

## 7. What is Bean Scope?

Scope defines **how many instances** of a bean Spring creates.

| Scope | Instances | Default? |
|---|---|---|
| `singleton` | One instance for the whole app | ✅ Yes |
| `prototype` | New instance every time it's requested | No |
| `request` | One per HTTP request (web only) | No |
| `session` | One per HTTP session (web only) | No |

```java
@Service
@Scope("prototype")
public class BookService { ... }
```

**Interview one-liner:**
> *"By default all Spring beans are singletons — one shared instance for the entire application lifetime."*

---

## 8. What is @SpringBootApplication?

It is a combination of 3 annotations:

```java
@SpringBootApplication
// is the same as:
@Configuration         // this class is a source of bean definitions
@EnableAutoConfiguration  // auto-configure beans based on classpath
@ComponentScan         // scan this package and sub-packages for beans
```

---

## 9. Inversion of Control (IoC)

IoC is the **principle behind DI** — instead of your code controlling object creation, you invert the control and let Spring do it.

- **Without IoC**: your class creates its dependencies → tightly coupled
- **With IoC**: Spring creates and injects dependencies → loosely coupled

**Interview one-liner:**
> *"IoC means the control of object creation is inverted from the developer to the Spring container."*

---

## 10. Tight Coupling vs Loose Coupling

```java
// Tight coupling — BookController is locked to BookService
public class BookController {
    private BookService bookService = new BookService();
}

// Loose coupling — BookController only depends on an interface
public class BookController {
    private final BookService bookService; // injected by Spring

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
}
```

Loose coupling means you can swap `BookService` for a mock in tests without changing `BookController`.

---

## 11. Primitive vs Wrapper Types in Entities

| Type | Can be null? | Use in entity? |
|---|---|---|
| `long`, `int`, `double` | ❌ No — defaults to 0 | ❌ Avoid |
| `Long`, `Integer`, `Double` | ✅ Yes | ✅ Yes |

> JPA needs `null` to represent a missing DB value. Primitives always have a default (0, false) so they can't represent NULL.
> `@Id` must always be `Long` not `long` — before saving, id is `null`.

---

## 12. Common Interview Questions & Answers

**Q: What is the difference between @Component, @Service, @Repository?**
> Functionally identical — all register a bean. The difference is semantic clarity and that `@Repository` also translates SQL exceptions into Spring's `DataAccessException`.

**Q: What is the difference between @Controller and @RestController?**
> `@Controller` returns views (HTML). `@RestController` = `@Controller` + `@ResponseBody` — returns JSON/XML directly.

**Q: Can you have two beans of the same type?**
> Yes. Use `@Primary` to set a default, or `@Qualifier("name")` to specify which one to inject.

**Q: What happens if Spring can't find a bean to inject?**
> It throws `NoSuchBeanDefinitionException` at startup.

**Q: What is a circular dependency?**
> Bean A needs Bean B and Bean B needs Bean A. Spring throws an error. Fix by redesigning or using `@Lazy` on one of them.

**Q: What is the difference between Spring and Spring Boot?**
> Spring is the framework. Spring Boot adds auto-configuration and an embedded server so you can build production-ready apps with minimal configuration.

**Q: What is @Bean vs @Component?**
> `@Component` is on a class — Spring auto-detects it. `@Bean` is on a method inside a `@Configuration` class — you manually define how the bean is created, useful for third-party classes you can't annotate.

```java
@Configuration
public class AppConfig {
    @Bean
    public SomeThirdPartyClass myBean() {
        return new SomeThirdPartyClass("custom config");
    }
}
```

---

*Part of Spring Boot interview prep series — bookstore practice project*