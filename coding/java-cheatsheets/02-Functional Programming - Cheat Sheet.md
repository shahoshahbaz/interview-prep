# Java Functional Programming — Interview Cheat Sheet

---

## 1. What is Functional Programming?

A style of writing code where you **treat behavior as data** — you can pass functions around just like you pass variables.

### Imperative vs Functional Style

```java
// Imperative — tell the computer HOW to do it
List<Integer> result = new ArrayList<>();
for (int n : numbers) {
    if (n % 2 == 0) {
        result.add(n);
    }
}

// Functional — tell the computer WHAT you want
List<Integer> result = numbers.stream()
        .filter(n -> n % 2 == 0)
        .collect(Collectors.toList());
```

### Three Core Ideas

| Idea | Meaning |
|---|---|
| **Functions as values** | Pass a function to a method like you pass a variable |
| **No side effects** | A function only uses its inputs, doesn't change external state |
| **Immutability** | Don't modify existing data — create new data instead |

**Interview one-liner:**
> *"Functional programming treats functions as first-class citizens — you can pass them around, store them, and chain them to describe what you want rather than how to do it."*

---

## 2. Lambda

A lambda is a **function without a name** — defined inline and passed around.

### Syntax

```java
(parameters) -> expression
```

### Evolution — Same Thing, 3 Ways

```java
// 1. Old way — anonymous class (before Java 8)
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("Running...");
    }
};

// 2. Lambda — Java 8
Runnable r = () -> System.out.println("Running...");

// 3. Method reference — even shorter
Runnable r = System.out::println;
```

### Lambda Syntax Variations

```java
// no parameters
() -> System.out.println("Hello")

// one parameter — parentheses optional
name -> System.out.println("Hello " + name)

// two parameters
(a, b) -> a + b

// multiple lines — need curly braces and return
(a, b) -> {
    int result = a + b;
    return result;
}
```

### Key Point
A lambda needs a **functional interface** to live in — it can't float on its own.

```java
// ✅ Runnable is a functional interface
Runnable r = () -> System.out.println("Running...");

// ❌ lambda has no type on its own
var x = () -> System.out.println("Running...");  // compile error
```

---

## 3. Functional Interface

An interface with **exactly one abstract method** — gives a lambda its type and purpose.

```java
@FunctionalInterface
public interface Greeting {
    void greet(String name);  // one abstract method — valid

    // void bye(String name); // ❌ compile error — two abstract methods
}

// lambda implements it
Greeting g = name -> System.out.println("Hello " + name);
g.greet("Shaho");  // Hello Shaho
```

### @FunctionalInterface
Marks the interface as functional — Java gives a compile error if you add a second abstract method.

### Default Methods Don't Count
```java
@FunctionalInterface
public interface Greeting {
    void greet(String name);  // abstract — counts

    default void greetLoud(String name) {  // default — does NOT count
        greet(name.toUpperCase());
    }
}
```

**Interview one-liner:**
> *"A functional interface has exactly one abstract method — that's what allows a lambda to implement it. `@FunctionalInterface` enforces this at compile time."*

---

## 4. Built-in Functional Interfaces

Java provides 4 core functional interfaces in `java.util.function`.

### Function\<T, R\> — takes something, returns something

```java
Function<String, Integer> strLength = str -> str.length();
strLength.apply("Shaho");  // 5
```

### Predicate\<T\> — takes something, returns boolean

```java
Predicate<Integer> isEven = n -> n % 2 == 0;
isEven.test(4);  // true
isEven.test(3);  // false
```

### Consumer\<T\> — takes something, returns nothing

```java
Consumer<String> print = name -> System.out.println("Hello " + name);
print.accept("Shaho");  // Hello Shaho
```

### Supplier\<T\> — takes nothing, returns something

```java
Supplier<String> greeting = () -> "Hello World";
greeting.get();  // Hello World
```

### Quick Reference

| Interface | Input | Output | Method | Use case |
|---|---|---|---|---|
| `Function<T,R>` | ✅ T | ✅ R | `.apply()` | Transform a value |
| `Predicate<T>` | ✅ T | ✅ boolean | `.test()` | Filter / check condition |
| `Consumer<T>` | ✅ T | ❌ void | `.accept()` | Do something with a value |
| `Supplier<T>` | ❌ none | ✅ T | `.get()` | Produce a value |

### Where You See These in Real Code

```java
List<String> names = List.of("Shaho", "Alice", "Bob");

names.stream()
     .filter(name -> name.length() > 4)   // Predicate
     .forEach(name -> System.out.println(name));  // Consumer
```

**Interview one-liner:**
> *"Java provides 4 core functional interfaces — Function transforms, Predicate tests, Consumer consumes, Supplier produces. They are the building blocks of lambdas and streams."*

---

## 5. Method References

A shorter way to write a lambda when it **only calls one existing method**.

### Syntax

```java
ClassName::methodName
```

### 4 Types

```java
// 1. Static method reference
Function<String, Integer> parse = Integer::parseInt;
// same as: str -> Integer.parseInt(str)

// 2. Instance method on a specific object
String prefix = "Hello ";
Function<String, String> greet = prefix::concat;
// same as: name -> prefix.concat(name)

// 3. Instance method on a parameter
Function<String, String> upper = String::toUpperCase;
// same as: str -> str.toUpperCase()

// 4. Constructor reference
Supplier<ArrayList> listMaker = ArrayList::new;
// same as: () -> new ArrayList()
```

### Quick Reference

| Type | Syntax | Lambda equivalent |
|---|---|---|
| Static method | `Integer::parseInt` | `str -> Integer.parseInt(str)` |
| Instance on object | `prefix::concat` | `name -> prefix.concat(name)` |
| Instance on parameter | `String::toUpperCase` | `str -> str.toUpperCase()` |
| Constructor | `ArrayList::new` | `() -> new ArrayList()` |

---

## 6. Lambda vs Method Reference — When to Use Which

### The Simple Rule

```
Lambda body has ONE method call, no extra logic → method reference
Lambda body has ANYTHING else                  → keep lambda
```

### Side by Side

| Scenario | Use | Example |
|---|---|---|
| Just print | ✅ method reference | `System.out::println` |
| Print with text | ❌ keep lambda | `name -> System.out.println("Hi " + name)` |
| Just parse int | ✅ method reference | `Integer::parseInt` |
| Parse then multiply | ❌ keep lambda | `str -> Integer.parseInt(str) * 2` |
| Just uppercase | ✅ method reference | `String::toUpperCase` |
| Uppercase + trim | ❌ keep lambda | `str -> str.trim().toUpperCase()` |
| Just create object | ✅ method reference | `ArrayList::new` |
| Create with args | ❌ keep lambda | `() -> new ArrayList(10)` |

**Interview one-liner:**
> *"Method references are shorthand for lambdas that just call an existing method — they make code cleaner using the `::` operator."*

---

## 7. Common Interview Questions

**Q: What is a lambda in Java?**
> A lambda is an anonymous function — a shorter way to implement a functional interface inline without creating an anonymous class.

**Q: What is a functional interface?**
> An interface with exactly one abstract method. `@FunctionalInterface` enforces this at compile time. Examples: `Runnable`, `Comparator`, `Predicate`, `Function`.

**Q: What is the difference between Function, Predicate, Consumer, and Supplier?**
> Function takes input and returns output. Predicate takes input and returns boolean. Consumer takes input and returns nothing. Supplier takes nothing and returns output.

**Q: When would you use a method reference over a lambda?**
> When the lambda body only calls one existing method with no extra logic — method reference is cleaner and more readable.

**Q: Can a functional interface have default methods?**
> Yes. Default methods don't count toward the one abstract method rule — only abstract methods count.

**Q: What is the difference between imperative and functional style?**
> Imperative tells the computer how to do something step by step. Functional tells the computer what you want — behavior is expressed as data passed through functions.

---

*Part of Java interview prep series — java-concepts module*