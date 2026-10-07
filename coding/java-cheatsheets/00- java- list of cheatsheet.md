# Java Core Fundamentals — Interview Cheatsheet

> Living document. Topics are added with explanations, code examples, and interview one-liners as we review them together. Checkboxes track review status.

---

## Table of Contents

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

## Review Log

| Date | Topic | Notes |
|------|-------|-------|
| | | |