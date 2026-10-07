# Java Lambda Expression Cheat Sheet

## 🧩 Lambda Expression

### Purpose
Lambda expressions provide a short, concise way to represent anonymous functions. They enable you to write functional-style code in Java, making code more readable and reducing boilerplate.

### Core Idea
A lambda expression is a block of code that takes parameters and returns a value. It implements a functional interface (interface with a single abstract method).

**Syntax:** `(parameters) -> expression` or `(parameters) -> { statements; }`

### Key APIs
- `@FunctionalInterface` - Annotation to mark interfaces with single abstract method
- `java.util.function.Consumer<T>` - Takes an input, returns nothing
- `java.util.function.Function<T, R>` - Takes T, returns R
- `java.util.function.Predicate<T>` - Tests a condition, returns boolean
- `java.util.function.BiFunction<T, U, R>` - Takes two inputs, returns result
- `java.util.function.Supplier<T>` - Takes no input, returns T

### Syntax / Example

```java
// Basic syntax
(parameters) -> expression

// With Consumer (no return)
Consumer<String> greeter = name -> System.out.println("Hello, " + name);
greeter.accept("Shaho");

// With type declaration
Consumer<String> greeter2 = (String name) -> System.out.println("Hello, " + name);
greeter2.accept("World");

// Multiple parameters (BiFunction)
BiFunction<Integer, Integer, Integer> addition = (a, b) -> a + b;
System.out.println(addition.apply(5, 3));  // Output: 8

// Multiple statements - requires curly braces and return
BiFunction<Integer, Integer, Integer> addWithLog = (a, b) -> {
    System.out.println("Adding " + a + " and " + b);
    return a + b;
};

// Predicate (returns boolean)
Predicate<String> isShort = s -> s.length() < 4;
System.out.println(isShort.test("abc"));  // true
```

### When to Use

✅ When you need to pass behavior as a parameter
✅ When implementing functional interfaces with single abstract method
✅ When working with Streams API (filter, map, forEach)
✅ When you want concise, readable code instead of anonymous inner classes
✅ With Collection methods like `forEach()`, `removeIf()`, `sort()`

### When NOT to Use

❌ For complex logic (use regular methods instead)
❌ When the interface has multiple abstract methods
❌ If you need multiple code paths or deep nesting
❌ When you need better performance (tiny overhead)
❌ For code that needs to be reused in many places (extract to a method)

### Gotchas

⚠️ **Parentheses Rules:**
  - Single parameter WITHOUT type: Parentheses optional → `name -> ...`
  - Single parameter WITH type: Parentheses required → `(String name) -> ...`
  - Multiple parameters: Always use parentheses → `(a, b) -> ...`
  - No parameters: Empty parentheses required → `() -> ...`

⚠️ **Curly Braces Rules:**
  - Single expression: Omit braces → `(a, b) -> a + b`
  - Multiple statements: Braces required → `(a, b) -> { statements; }`
  - With braces, explicit `return` keyword needed

⚠️ **Scope & Variables:**
  - Can only access `final` or effectively final variables from enclosing scope
  - Cannot modify captured variables
  - `this` refers to enclosing class, not the lambda

⚠️ **Exception Handling:**
  - Checked exceptions must be declared in functional interface
  - Regular try-catch works fine

### Interview One-Liner

Lambda expressions are syntactic sugar for functional interfaces, enabling concise implementation of single-method interfaces while reducing boilerplate code.

---

## 🔥 Example Snapshots

### Parentheses Rules

| Scenario | Example | Valid |
|----------|---------|-------|
| Single param, no type | `name -> println(name)` | ✅ |
| Single param, with type | `(String name) -> println(name)` | ✅ |
| Single param with type + parens omitted | `String name -> ...` | ❌ |
| Multiple params | `(a, b) -> a + b` | ✅ |
| No params | `() -> value` | ✅ |

### Curly Braces Rules

```java
// Single expression - no braces needed
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;

// Multiple statements - braces required
BiFunction<Integer, Integer, Integer> addWithLogging = (a, b) -> {
    System.out.println("Adding " + a + " and " + b);
    return a + b;  // Explicit return needed
};

// Custom functional interface
@FunctionalInterface
interface Joiner {
    String join(String text1, String text2);
}

Joiner joiner = (t1, t2) -> t1 + t2;  // Single expression, no braces
System.out.println(joiner.join("Hello ", "World"));  // Output: Hello World
```

### Common Functional Interfaces

```java
// Consumer - takes input, returns nothing
Consumer<String> printer = s -> System.out.println(s);
printer.accept("Hello");

// Function - takes input, returns output
Function<Integer, String> toString = num -> "Number: " + num;
System.out.println(toString.apply(42));  // Output: Number: 42

// Predicate - tests a condition
Predicate<Integer> isEven = n -> n % 2 == 0;
System.out.println(isEven.test(4));  // true

// BiFunction - two inputs, one output
BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
System.out.println(multiply.apply(3, 4));  // Output: 12

// Supplier - no input, returns output
Supplier<Double> random = () -> Math.random();
System.out.println(random.get());
```

### Real-World Usage Patterns

```java
// With Collections
List<String> list = Arrays.asList("One", "Two", "Three");
list.forEach(s -> System.out.println(s));

// With Streams
list.stream()
    .filter(s -> s.length() > 3)
    .forEach(System.out::println);

// Sorting with Comparator
List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5);
Collections.sort(numbers, (a, b) -> b - a);  // Descending order

// ArrayList removeIf
ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(3, 5, 7, 2, 9));
nums.removeIf(n -> n < 6);  // Removes: 3, 5, 2
System.out.println(nums);  // Output: [7, 9]
```

### Creating Your Own Functional Interface

```java
@FunctionalInterface
interface Calculator {
    int calculate(int x, int y);
}

// Usage
Calculator add = (x, y) -> x + y;
Calculator subtract = (x, y) -> x - y;

System.out.println(add.calculate(10, 5));      // Output: 15
System.out.println(subtract.calculate(10, 5)); // Output: 5
```

### Variable Capture Rules

```java
// ✅ Accessing final/effectively final variables
int multiplier = 10;  // Effectively final
Function<Integer, Integer> multiply = n -> n * multiplier;
System.out.println(multiply.apply(5));  // Output: 50

// ❌ Cannot modify captured variable
int counter = 0;
Consumer<String> printer = s -> {
    // counter++;  // ERROR: counter is not effectively final
    System.out.println(s);
};

// ✅ Using 'this' in lambdas (refers to enclosing class)
class MyClass {
    private int value = 42;
    
    void demo() {
        Supplier<Integer> supplier = () -> this.value;
        System.out.println(supplier.get());  // Output: 42
    }
}
```

---

## 📝 Key Takeaways

1. **Lambda = Functional Interface Implementation** - Syntactic sugar for anonymous inner classes
2. **Parentheses:** Optional for single untyped param, required otherwise
3. **Curly Braces:** Optional for single expression, required for multiple statements
4. **Variable Capture:** Can only access final/effectively final variables
5. **Built-in Interfaces:** Use Consumer, Function, Predicate, BiFunction, Supplier
6. **Common Use Cases:** forEach, filter, map, sort, removeIf, custom implementations

---

## 🎯 Quick Reference

```
Syntax Pattern:  (params) -> body

Lambda          Equivalent Anonymous Class
─────────────────────────────────────────────
s -> s.length() | (String s) -> { return s.length(); }
(a, b) -> a+b   | (Integer a, Integer b) -> { return a + b; }
() -> 42        | () -> { return 42; }
x -> {          | (Integer x) -> {
  print(x);     |   System.out.println(x);
  return x*2;   |   return x * 2;
}               | }
```
