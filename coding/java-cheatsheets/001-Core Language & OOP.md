# Java Core Fundamentals — Interview Cheatsheet

> Living document. Topics are added with explanations, code examples, and interview one-liners as we review them together. Checkboxes track review status.

---

## Table of Contents

### 0. Widening vs. Autoboxing vs. Varargs
- [x] Widening (primitive widening conversion)
- [x] Autoboxing / unboxing (incl. Integer caching gotcha)
- [x] Varargs

### 1. Core Language & OOP
- [ ] Overriding vs. overloading — rules, pitfalls
- [x] Abstract class vs. interface — when to use each, default methods
- [ ] `equals()` and `hashCode()` contract — why they must be overridden together
- [ ] `String` immutability — why, String pool vs. `new String()`, `StringBuilder`/`StringBuffer`
- [ ] `final`, `finally`, `finalize()` — differences (and why `finalize()` is deprecated)
- [ ] Checked vs. unchecked exceptions — design philosophy, custom exceptions
- [ ] Static vs. instance members, static initialization order
- [ ] Constructors — overloading, chaining (`this()`/`super()`), no-arg constructor rules
- [ ] `this` vs `super`
- [ ] Access modifiers — public/protected/default/private, package-private nuances
- [ ] Composition vs. inheritance — favor composition, "is-a" vs "has-a"
- [ ] Polymorphism — compile-time vs runtime, dynamic method dispatch internals
- [ ] Encapsulation — practical violations (returning mutable fields, etc.)
- [ ] Object class methods — `toString()`, `clone()` (shallow vs deep copy), `getClass()`

### 2. Java Memory Model & JVM
- [ ] Stack vs. heap
- [ ] Method Area / Metaspace (post-Java 8)
- [ ] Garbage collection — generations (young/old), GC algorithms overview (G1, ZGC)
- [ ] `==` vs `.equals()` for objects vs primitives
- [ ] Autoboxing/unboxing — pitfalls (Integer caching -128 to 127)
- [ ] Pass-by-value in Java (why "pass-by-reference" is a misconception)
- [ ] Class loading — ClassLoader hierarchy, when classes get loaded
- [ ] Memory leaks in Java despite GC (static references, unclosed resources, listener leaks)

### 3. Collections Framework
- [ ] `List` vs `Set` vs `Map` — implementations and trade-offs
- [ ] `HashMap` internals — hashing, buckets, treeification (Java 8+), load factor, resize
- [ ] `HashMap` vs `Hashtable` vs `ConcurrentHashMap`
- [ ] `Comparable` vs `Comparator`
- [ ] `Iterator` vs `ListIterator`, fail-fast vs fail-safe, `ConcurrentModificationException`
- [ ] `equals`/`hashCode` contract in hash-based collections
- [ ] `Arrays.asList()` gotchas, `List.of()` immutability

### 4. Generics
- [ ] Type erasure — what it means, why
- [ ] Bounded types (`<T extends X>`)
- [ ] Wildcards — `? extends` vs `? super` (PECS principle)
- [ ] Why you can't create generic arrays

### 5. Functional Programming / Java 8+
- [ ] Lambdas, functional interfaces (`Function`, `Supplier`, `Consumer`, `Predicate`, `@FunctionalInterface`)
- [ ] Method references — 4 types
- [ ] Streams — intermediate vs terminal ops, laziness, `map`/`filter`/`reduce`/`collect`
- [ ] `Optional` — proper usage, anti-patterns
- [ ] Default/static methods in interfaces — diamond problem resolution

### 6. Concurrency & Multithreading
- [ ] Thread creation — `Thread` vs `Runnable` vs `Callable`
- [ ] `synchronized` — method vs block, monitor locks
- [ ] `volatile` — visibility vs atomicity, happens-before
- [ ] `wait()`/`notify()`/`notifyAll()` vs `Lock`/`Condition`
- [ ] Deadlock, livelock, starvation — causes and prevention
- [ ] `ExecutorService`, thread pools, `Future`, `CompletableFuture`
- [ ] `ConcurrentHashMap` internals, `CopyOnWriteArrayList`
- [ ] Atomic classes (`AtomicInteger`, etc.) — CAS operations
- [ ] Double-checked locking, singleton thread-safety patterns
- [ ] `ThreadLocal`

### 7. Exception Handling
- [ ] Exception hierarchy (`Throwable` → `Error`/`Exception`)
- [ ] try-with-resources, `AutoCloseable`
- [ ] Custom exceptions — checked vs unchecked design decisions
- [ ] Multi-catch, exception chaining

### 8. Java I/O & Serialization
- [ ] `Serializable` — `serialVersionUID`, transient fields
- [ ] NIO vs IO basics

### 9. Design & Architecture (Java-specific)
- [ ] SOLID principles — practical Java examples
- [ ] Common design patterns — Singleton, Factory, Builder, Strategy, Observer
- [ ] Immutability — how to design immutable classes

### 10. JVM Internals / Performance
- [ ] JIT compilation basics
- [ ] Common performance pitfalls (string concatenation in loops, boxing in hot paths)

---

---

## 0. Widening vs. Autoboxing vs. Varargs

Three different conversions the compiler can apply to make an argument fit a parameter — used constantly in overload resolution.

| Conversion | What changes | Example |
|---|---|---|
| Widening | primitive → bigger primitive | `int → long` |
| Autoboxing | primitive → wrapper object | `int → Integer` |
| Varargs | argument(s) → array | `5 → new int[]{5}` |

**Overload resolution order:** compiler tries **widening only** first (Phase 1), then **autoboxing** (Phase 2), then **varargs** (Phase 3) — stops at the first phase that finds a match. Not a preference, it's search order (cheapest/safest conversion tried first).

```
void call(long x)    { }
void call(Integer x) { }
void call(int... x)  { }

call(5); // -> "long" wins: int widens to long in Phase 1, search stops there

```

---

### 0.1 Widening (Primitive Widening Conversion)

Converting a **smaller primitive to a bigger primitive**, automatically, no cast, no object involved — stays primitive the whole time.

```
byte → short → int → long → float → double
              char → int
```

```java
int i = 100;
long l = i;     // widening, automatic
```

**Why automatic:** the bigger type can always represent every value of the smaller type — no data loss possible. (Reverse direction = *narrowing*, requires explicit cast, can lose data.)

---

### 0.2 Autoboxing / Unboxing

**Why "autoboxing":** wrapping a primitive in its object wrapper is called *boxing* (picture the object as a box holding the primitive). Pre-Java 5 you boxed manually (`Integer.valueOf(5)`); the compiler now does it *automatically* — hence *auto*-boxing.

- **Autoboxing** = primitive → wrapper object (`int → Integer`)
- **Unboxing** = wrapper object → primitive (`Integer → int`)

```java
Integer wrapped = 5;        // autoboxing: compiler inserts Integer.valueOf(5)
int back = wrapped;         // unboxing: compiler inserts wrapped.intValue()
```

**Integer Caching Gotcha:** `Integer.valueOf()` caches objects for **-128 to 127**. Values in that range return the *same* cached object; outside it, a *new* object is created each time.

```
Integer a = 100, b = 100;
System.out.println(a == b); // true  — both hit the cache, same object

Integer c = 200, d = 200;
System.out.println(c == d);// false — outside cache, two different objects

```

**Rule:** `==` on wrapper objects compares references, not values — use `.equals()` for wrapper comparisons, always.

**One-liner:** *"Integer caches -128 to 127 via `valueOf()`, so `==` 'accidentally' works in that range and breaks outside it — never rely on it, always use `.equals()`."*

---

### 0.3 Varargs (Variable Arguments)

Lets a method accept **zero or more arguments**, packed into an array by the compiler.

```
void printAll(String... names) { ... }

printAll("Alice", "Bob");
// compiler effectively does: printAll(new String[]{"Alice", "Bob"});
```

**Rules:**
- Only **one** varargs parameter per method, and it must be **last**.
- Passing an array directly skips the packing step — used as-is.
- "Loosest" match of the three — requires the most work (array creation), which is why it's tried last in overload resolution.

---

## Review Log

| Date | Topic | Notes |
|------|-------|-------|
| | | |