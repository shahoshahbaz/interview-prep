# Java Functional Interface Cheat Sheet

## 🧩 Functional Interface

### Purpose
A Functional Interface is a Java interface with exactly one abstract method. It serves as the foundation for lambda expressions and functional programming in Java, enabling you to pass behavior as data.

### Core Idea
Functional interfaces bridge object-oriented and functional programming paradigms. They allow you to treat methods as first-class citizens—you can pass them as arguments, return them from methods, and assign them to variables. The `@FunctionalInterface` annotation marks interfaces with a single abstract method, enabling the use of lambda expressions.

### Key APIs
- `@FunctionalInterface` - Annotation to declare functional interfaces
- `java.util.function.Function<T, R>` - Takes T, returns R
- `java.util.function.Predicate<T>` - Tests condition, returns boolean
- `java.util.function.Consumer<T>` - Takes input, returns nothing
- `java.util.function.Supplier<T>` - Takes nothing, returns T
- `java.util.function.BiFunction<T, U, R>` - Takes two inputs, returns R
- `java.util.function.BiConsumer<T, U>` - Takes two inputs, returns nothing

### Syntax / Example

```java
// Defining a Functional Interface
@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}

// Using lambda expression to implement the interface
MathOperation add = (x, y) -> x + y;
MathOperation subtract = (x, y) -> x - y;

// Calling the lambda
System.out.println(add.operate(10, 5));      // Output: 15
System.out.println(subtract.operate(10, 5)); // Output: 5

// Using as method parameter
public static int calculate(int a, int b, MathOperation operation) {
    return operation.operate(a, b);
}

System.out.println(calculate(10, 5, (x, y) -> x * y)); // Output: 50
```

### When to Use

✅ When you want to pass behavior/logic as a method parameter
✅ When you need to implement a single-method interface with lambda expressions
✅ When designing APIs that accept custom behavior (callbacks, event handlers)
✅ When working with functional-style operations (filtering, mapping, transforming data)
✅ To reduce boilerplate code compared to anonymous inner classes
✅ When you want to enable functional programming in your Java code

### When NOT to Use

❌ When the interface has multiple abstract methods
❌ When you need to store state or maintain complex object behavior
❌ If the interface needs static or default methods as primary functionality
❌ When the logic is complex and difficult to read in a single expression
❌ For one-time use cases where a regular method would be clearer
❌ When you need better IDE debugging support (lambdas are harder to debug)

### Gotchas

⚠️ **Only ONE Abstract Method Allowed:**
  - If you add multiple abstract methods, it's no longer a functional interface
  - Default and static methods don't count—they can coexist with one abstract method

⚠️ **@FunctionalInterface is Optional:**
  - The annotation is not required for a functional interface to work
  - However, it's highly recommended for documentation and compile-time checking
  - The compiler will verify the interface has exactly one abstract method

⚠️ **Inherited Abstract Methods Count:**
  - If a functional interface extends another interface with abstract methods
  - The total count must still be exactly one abstract method

⚠️ **Variable Scope Issues:**
  - Lambda expressions can only access `final` or effectively final variables
  - Cannot capture and modify external variables

⚠️ **Type Inference:**
  - Java's type system will infer the functional interface type based on context
  - Sometimes you need explicit casting: `(MathOperation) (x, y) -> x + y`

### Interview One-Liner

A functional interface is a single-method interface that acts as a target type for lambda expressions and method references, enabling functional programming patterns in Java.

---

## 🔥 Example Snapshots

### Valid vs Invalid Functional Interfaces

```java
// ✅ VALID - Single abstract method
@FunctionalInterface
interface Valid1 {
    void execute();
}

// ✅ VALID - Can have default methods
@FunctionalInterface
interface Valid2 {
    void execute();
    default void helper() { }
}

// ✅ VALID - Can have static methods
@FunctionalInterface
interface Valid3 {
    void execute();
    static void utility() { }
}

// ❌ INVALID - Multiple abstract methods
@FunctionalInterface  // Compile error!
interface Invalid1 {
    void execute();
    void run();
}

// ❌ INVALID - No abstract methods
@FunctionalInterface  // Compile error!
interface Invalid2 {
    default void execute() { }
}
```

### Custom Functional Interface Examples

```java
// Simple single-parameter interface
@FunctionalInterface
interface StringProcessor {
    String process(String input);
}

StringProcessor toUpperCase = s -> s.toUpperCase();
StringProcessor reverse = s -> new StringBuilder(s).reverse().toString();

System.out.println(toUpperCase.process("hello"));    // Output: HELLO
System.out.println(reverse.process("hello"));        // Output: olleh

// Multi-parameter interface
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

Calculator add = (x, y) -> x + y;
Calculator multiply = (x, y) -> x * y;
Calculator power = (x, y) -> {
    int result = 1;
    for (int i = 0; i < y; i++) {
        result *= x;
    }
    return result;
};

System.out.println(add.calculate(10, 5));        // Output: 15
System.out.println(multiply.calculate(10, 5));   // Output: 50
System.out.println(power.calculate(2, 3));       // Output: 8

// No-parameter interface
@FunctionalInterface
interface RandomGenerator {
    int generate();
}

RandomGenerator random = () -> (int) (Math.random() * 100);
System.out.println(random.generate());  // Random number 0-99
```

### Functional Interface as Method Parameter

```java
@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}

// Method that accepts a functional interface as parameter
public static int calculate(int a, int b, MathOperation operation) {
    return operation.operate(a, b);
}

// Usage with different implementations
System.out.println("Addition: " + calculate(10, 5, (x, y) -> x + y));          // 15
System.out.println("Subtraction: " + calculate(10, 5, (x, y) -> x - y));       // 5
System.out.println("Multiplication: " + calculate(10, 5, (x, y) -> x * y));    // 50
System.out.println("Division: " + calculate(10, 5, (x, y) -> x / y));          // 2
System.out.println("Modulo: " + calculate(10, 5, (x, y) -> x % y));            // 0
System.out.println("Power: " + calculate(2, 3, (x, y) -> {
    int result = 1;
    for (int i = 0; i < y; i++) result *= x;
    return result;
}));  // 8
```

### Built-in Functional Interfaces

```java
// Predicate<T> - Returns boolean
Predicate<Integer> isEven = n -> n % 2 == 0;
Predicate<String> isLongString = s -> s.length() > 5;

System.out.println(isEven.test(4));          // true
System.out.println(isLongString.test("hi")); // false

// Consumer<T> - Takes input, no return
Consumer<String> printer = s -> System.out.println(s);
Consumer<Integer> doubler = n -> System.out.println(n * 2);

printer.accept("Hello");  // Output: Hello
doubler.accept(5);        // Output: 10

// Function<T, R> - Input to Output transformation
Function<String, Integer> stringLength = s -> s.length();
Function<Integer, String> intToString = n -> "Number: " + n;

System.out.println(stringLength.apply("Hello"));    // 5
System.out.println(intToString.apply(42));          // Number: 42

// Supplier<T> - Returns value without input
Supplier<Double> randomDouble = () -> Math.random();
Supplier<Integer> constantValue = () -> 42;

System.out.println(randomDouble.get());  // Random 0.0-1.0
System.out.println(constantValue.get()); // 42

// BiFunction<T, U, R> - Two inputs to one output
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
BiFunction<String, String, String> concat = (s1, s2) -> s1 + s2;

System.out.println(add.apply(10, 5));           // 15
System.out.println(concat.apply("Hello", " World")); // Hello World
```

### Filtering with Predicate (Real-World Example)

```java
// Helper method using Predicate
public static List<Integer> filterNumbers(List<Integer> numbers, Predicate<Integer> predicate) {
    List<Integer> result = new ArrayList<>();
    for (Integer number : numbers) {
        if (predicate.test(number)) {
            result.add(number);
        }
    }
    return result;
}

List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Filter even numbers
List<Integer> evenNumbers = filterNumbers(numbers, n -> n % 2 == 0);
System.out.println("Even: " + evenNumbers);  // [2, 4, 6, 8, 10]

// Filter odd numbers
List<Integer> oddNumbers = filterNumbers(numbers, n -> n % 2 != 0);
System.out.println("Odd: " + oddNumbers);    // [1, 3, 5, 7, 9]

// Filter numbers greater than 5
List<Integer> greaterThan5 = filterNumbers(numbers, n -> n > 5);
System.out.println("Greater than 5: " + greaterThan5);  // [6, 7, 8, 9, 10]

// Filter numbers divisible by 3
List<Integer> divisibleBy3 = filterNumbers(numbers, n -> n % 3 == 0);
System.out.println("Divisible by 3: " + divisibleBy3);  // [3, 6, 9]
```

### Functional Programming Characteristics

```java
// 1. FIRST-CLASS FUNCTIONS - Functions as values
@FunctionalInterface
interface Operation {
    int execute(int x, int y);
}

Operation add = (a, b) -> a + b;
Operation sub = (a, b) -> a - b;

// Store in variable, pass as parameter, return from method
List<Operation> operations = Arrays.asList(add, sub);

// 2. PURE FUNCTIONS - No side effects, deterministic output
// ✅ Pure function
Function<Integer, Integer> double_num = n -> n * 2;
int result1 = double_num.apply(5);  // Always returns 10
int result2 = double_num.apply(5);  // Always returns 10

// ❌ Not pure (side effect - printing)
Consumer<Integer> impure = n -> {
    System.out.println(n);  // Side effect
    System.out.println(n * 2);
};

// 3. IMMUTABILITY - Don't modify input
@FunctionalInterface
interface Transform {
    String transform(String input);
}

Transform uppercase = s -> s.toUpperCase();  // Creates new string
// Original string remains unchanged

// 4. HIGHER-ORDER FUNCTIONS - Functions that operate on functions
public static Operation compose(Operation op1, Operation op2) {
    return (x, y) -> op1.execute(op2.execute(x, y), y);
}

Operation result = compose(add, sub);
System.out.println(result.execute(10, 5));  // (10-5) + 5 = 10
```

### Comparison: Anonymous Class vs Lambda vs Method Reference

```java
// 1. ANONYMOUS INNER CLASS (Pre-Java 8)
MathOperation add1 = new MathOperation() {
    @Override
    public int operate(int a, int b) {
        return a + b;
    }
};

// 2. LAMBDA EXPRESSION (Java 8+)
MathOperation add2 = (a, b) -> a + b;

// 3. METHOD REFERENCE (Java 8+)
public static int addNumbers(int a, int b) {
    return a + b;
}
MathOperation add3 = FunctionalInterfaceExample::addNumbers;

// All three work identically
System.out.println(add1.operate(10, 5));  // 15
System.out.println(add2.operate(10, 5));  // 15
System.out.println(add3.operate(10, 5));  // 15
```

---

## 📝 Key Takeaways

1. **One Abstract Method Required** - Defines what the lambda will implement
2. **@FunctionalInterface Annotation** - Optional but highly recommended for documentation
3. **Lambda Expressions** - Provide clean syntax to implement functional interfaces
4. **Method Parameters** - Functional interfaces enable passing behavior to methods
5. **Built-in Interfaces** - Java provides common ones (Predicate, Consumer, Function, etc.)
6. **Functional Programming** - Enables immutable, side-effect-free code patterns
7. **Higher-Order Functions** - Functions can work with other functions

---

## 🎯 Quick Reference

### Creating Functional Interfaces

```
Rule: Exactly ONE abstract method

@FunctionalInterface
interface InterfaceName {
    ReturnType methodName(ParameterTypes);
}
```

### Common Functional Interface Patterns

| Interface | Method | Use Case |
|-----------|--------|----------|
| `Predicate<T>` | `boolean test(T)` | Testing conditions |
| `Consumer<T>` | `void accept(T)` | Processing values |
| `Function<T,R>` | `R apply(T)` | Transforming values |
| `Supplier<T>` | `T get()` | Providing values |
| `BiFunction<T,U,R>` | `R apply(T, U)` | Two-input operations |
| `BiConsumer<T,U>` | `void accept(T, U)` | Two-input processing |

### Lambda Implementation Formula

```
(parameters) -> { logic }

Examples:
(a, b) -> a + b                    // Addition
(x) -> x * 2                       // Doubling
(s) -> s.toUpperCase()             // Transform to uppercase
(n) -> n % 2 == 0                  // Check if even
() -> Math.random()                // Generate random
```

### When to Choose What

```
Choose Functional Interface if:
├─ You need a single method to implement
├─ You want to pass behavior as parameter
├─ You want clean lambda syntax
└─ You're writing functional-style code

Choose Regular Interface if:
├─ You need multiple abstract methods
├─ You need significant state/behavior
├─ You're implementing complex contracts
└─ Multiple implementations with shared logic
```

