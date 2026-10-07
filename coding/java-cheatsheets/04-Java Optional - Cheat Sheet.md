# Java Optional — Interview Cheat Sheet

---

## 1. What is Optional?

A container that **may or may not hold a value** — Java's way of handling null safely without null checks everywhere.

---

## 2. The Problem Without Optional

```java
// returns null if not found — caller might forget to check
public String findName(int id) {
    return null;  // user not found
}

// caller forgets null check → NullPointerException
String name = findName(1);
System.out.println(name.toUpperCase());  // 💥 NullPointerException
```

---

## 3. With Optional

```java
// returns Optional — forces caller to handle the empty case
public Optional<String> findName(int id) {
    return Optional.empty();  // user not found
}

// caller must deal with it
Optional<String> name = findName(1);
name.ifPresent(n -> System.out.println(n.toUpperCase()));  // safe — won't crash
```

**Interview one-liner:**
> *"Optional is a container that forces callers to handle the case where a value might not exist — it replaces null returns and prevents NullPointerException."*

---

## 4. Creating an Optional

```java
// empty — no value
Optional<String> empty = Optional.empty();

// with a value — throws NullPointerException if value is null
Optional<String> name = Optional.of("Shaho");

// with a value that might be null — safe even if null
Optional<String> maybe = Optional.ofNullable(getValue());
```

### When to use which

```
Optional.of()          → use when value is never null
Optional.ofNullable()  → use when value might be null
Optional.empty()       → use when there is no value
```

---

## 5. Using an Optional

```java
Optional<String> name = Optional.of("Shaho");

// check if value exists
name.isPresent();   // true
name.isEmpty();     // false (Java 11+)

// get the value — risky, throws NoSuchElementException if empty
name.get();         // "Shaho" — avoid unless you checked isPresent() first

// get or default if empty
name.orElse("Unknown");                          // "Shaho"
Optional.empty().orElse("Unknown");              // "Unknown"

// get or throw if empty
name.orElseThrow(() -> new RuntimeException("Not found"));

// do something if present
name.ifPresent(n -> System.out.println(n));      // prints "Shaho"

// transform if present
name.map(String::toUpperCase);                   // Optional["SHAHO"]
```

---

## 6. Quick Reference

| Method | What it does | Use when |
|---|---|---|
| `Optional.of(value)` | Creates Optional with value | Value is never null |
| `Optional.ofNullable(value)` | Creates Optional, handles null | Value might be null |
| `Optional.empty()` | Creates empty Optional | No value to return |
| `.isPresent()` | Returns true if value exists | Checking before get() |
| `.isEmpty()` | Returns true if no value | Java 11+ |
| `.get()` | Returns value or throws | Avoid — use orElse instead |
| `.orElse(default)` | Returns value or default | Need a fallback value |
| `.orElseThrow()` | Returns value or throws | Most common in Spring Boot |
| `.ifPresent(action)` | Runs action if value exists | Do something if present |
| `.map(fn)` | Transforms value if present | Chain operations safely |

---

## 7. Where You See Optional in Real Code

```java
// Spring Data JPA — findById always returns Optional
Optional<Book> book = bookRepository.findById(1L);

// most common pattern in Spring Boot service layer
Book found = book.orElseThrow(() -> new BookNotFoundException("Book not found"));

// or with ifPresent
book.ifPresent(b -> System.out.println(b.getTitle()));
```

---

## 8. Common Interview Questions

**Q: What is Optional in Java?**
> Optional is a container object that may or may not hold a value. It forces the caller to explicitly handle the case where a value might be absent, preventing NullPointerException.

**Q: What is the difference between Optional.of() and Optional.ofNullable()?**
> Optional.of() throws NullPointerException if the value is null. Optional.ofNullable() safely wraps null as an empty Optional — use it when the value might be null.

**Q: Why should you avoid Optional.get()?**
> get() throws NoSuchElementException if the Optional is empty. Use orElse() or orElseThrow() instead — they handle the empty case explicitly.

**Q: What is the difference between orElse() and orElseThrow()?**
> orElse() returns a default value if the Optional is empty. orElseThrow() throws an exception if the Optional is empty — most common in Spring Boot service layers when a resource is not found.

**Q: Where do you commonly see Optional in Spring Boot?**
> In Spring Data JPA — findById() returns Optional\<T\>. The service layer then calls orElseThrow() to return the entity or throw a NotFoundException.

**Q: Can Optional be used as a field in an entity?**
> No — Optional is not serializable and not meant for fields. It is designed for return types only.

---

*Part of Java interview prep series — java-concepts module*