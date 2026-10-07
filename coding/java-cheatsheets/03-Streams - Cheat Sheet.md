# Java Streams — Interview Cheat Sheet

---

## 1. What is a Stream?

A stream is a **pipeline that processes a collection** — you chain operations together to filter, transform, and collect data.

```java
List<String> names = List.of("Shaho", "Alice", "Bob", "Charlie");

List<String> result = names.stream()        // 1. create stream
        .filter(name -> name.length() > 3)  // 2. intermediate operation
        .map(String::toUpperCase)           // 3. intermediate operation
        .collect(Collectors.toList());      // 4. terminal operation

// result: ["SHAHO", "ALICE", "CHARLIE"]
```

Three steps every time:
```
Source → Intermediate Operations → Terminal Operation
```

**Interview one-liner:**
> *"A stream is a pipeline — you chain intermediate operations like filter and map, and a terminal operation like collect triggers the whole thing."*

---

## 2. Two Types of Operations

### Intermediate — lazy, returns a stream, can chain many

```java
.filter()    // keep elements that match
.map()       // transform each element
.sorted()    // sort elements
.distinct()  // remove duplicates
.limit()     // take first N elements
.skip()      // skip first N elements
```

### Terminal — triggers execution, ends the pipeline

```java
.collect()    // gather into a list, set, map
.forEach()    // do something with each element
.count()      // count elements
.findFirst()  // get first element
.anyMatch()   // does any element match?
.allMatch()   // do all elements match?
.reduce()     // combine all elements into one
```

---

## 3. Lazy vs Eager

### Lazy — Streams
Intermediate operations **don't run** until a terminal operation is called.

```java
// nothing runs — just building the pipeline
names.stream()
     .filter(name -> name.length() > 3)
     .map(String::toUpperCase);

// NOW it runs — terminal operation triggered it
names.stream()
     .filter(name -> name.length() > 3)
     .map(String::toUpperCase)
     .collect(Collectors.toList());  // ← this triggers everything
```

**Why lazy is good** — stops as soon as the job is done:

```java
// 1 million names — stops at first match, doesn't process the rest
names.stream()
     .filter(name -> name.startsWith("A"))
     .findFirst();
```

### Eager — for loops
Regular for loops execute immediately, no waiting:

```java
List<String> result = new ArrayList<>();
for (String name : names) {
    if (name.length() > 3) {
        result.add(name);  // runs immediately
    }
}
```

### Summary

```
Lazy  → build the plan first, execute when needed  → Streams
Eager → execute immediately, no waiting            → for loops, collections
```

---

## 4. Most Common Operations

### filter — keep elements that match

```java
List<Integer> evens = numbers.stream()
        .filter(n -> n % 2 == 0)
        .collect(Collectors.toList());
// [2, 4, 6]
```

### map — transform each element

```java
List<String> upper = names.stream()
        .map(String::toUpperCase)
        .collect(Collectors.toList());
// ["SHAHO", "ALICE", "BOB"]
```

### sorted — sort elements

```java
// natural order
List<String> sorted = names.stream()
        .sorted()
        .collect(Collectors.toList());

// custom order — by length
List<String> sortedByLength = names.stream()
        .sorted((a, b) -> a.length() - b.length())
        .collect(Collectors.toList());
```

### distinct — remove duplicates

```java
List<String> unique = names.stream()
        .distinct()
        .collect(Collectors.toList());
```

### limit / skip

```java
// take first 3
names.stream().limit(3).collect(Collectors.toList());

// skip first 2
names.stream().skip(2).collect(Collectors.toList());
```

### collect — gather into a collection

```java
// to List
.collect(Collectors.toList())

// to Set — removes duplicates
.collect(Collectors.toSet())

// to Map
.collect(Collectors.toMap(
        name -> name,           // key
        name -> name.length()   // value
));
// result: {"Shaho"=5, "Alice"=5, "Bob"=3}
```

### count — count elements

```java
long count = names.stream()
        .filter(name -> name.length() > 3)
        .count();  // 3
```

### findFirst — get first match

```java
Optional<String> first = names.stream()
        .filter(name -> name.startsWith("A"))
        .findFirst();  // Optional["Alice"]
```

### anyMatch / allMatch / noneMatch

```java
boolean anyLong  = names.stream().anyMatch(name -> name.length() > 4);   // true
boolean allLong  = names.stream().allMatch(name -> name.length() > 4);   // false
boolean noneLong = names.stream().noneMatch(name -> name.length() > 10); // true
```

### reduce — combine all into one

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5);

int sum = numbers.stream()
        .reduce(0, (a, b) -> a + b);  // 15

// with method reference
int sum = numbers.stream()
        .reduce(0, Integer::sum);  // 15
```

---

## 5. Stream Method → Functional Interface Map

| Stream method | Expects | Because |
|---|---|---|
| `.filter()` | `Predicate` | needs true/false to decide keep or drop |
| `.forEach()` | `Consumer` | just does something, returns nothing |
| `.map()` | `Function` | transforms input to output |
| `.sorted()` | `Comparator` | needs to compare two elements |

---

## 6. Chaining — Real World Example

```java
List<String> names = List.of("Shaho", "Alice", "Bob", "Charlie", "Shaho");

List<String> result = names.stream()
        .filter(name -> name.length() > 3)   // keep names longer than 3
        .map(String::toUpperCase)            // uppercase
        .distinct()                          // remove duplicates
        .sorted()                            // sort alphabetically
        .collect(Collectors.toList());

// result: ["ALICE", "CHARLIE", "SHAHO"]
```

---

## 7. Common Interview Questions

**Q: What is a Stream in Java?**
> A stream is a pipeline for processing collections — you chain intermediate operations like filter and map, and a terminal operation like collect triggers execution. Streams don't store data — they process it.

**Q: What is the difference between intermediate and terminal operations?**
> Intermediate operations are lazy — they return a stream and don't execute until a terminal operation is called. Terminal operations trigger the pipeline and produce a result.

**Q: What does lazy mean in streams?**
> Intermediate operations don't run until a terminal operation is called. This is efficient — for example, findFirst stops processing as soon as the first match is found, even if there are millions of elements.

**Q: What is the difference between map and filter?**
> filter keeps or drops elements based on a Predicate — it doesn't change the elements. map transforms each element using a Function — it changes the shape of the data but doesn't remove anything.

**Q: What is the difference between findFirst and anyMatch?**
> findFirst returns the first matching element wrapped in Optional. anyMatch returns a boolean — true if any element matches the condition.

**Q: What is the difference between a Stream and a Collection?**
> A Collection stores data. A Stream processes data — it doesn't store anything. You create a stream from a collection, process it, and collect the result back.

**Q: Can you reuse a stream?**
> No. Once a terminal operation is called, the stream is consumed and cannot be reused. You must create a new stream.

**Q: What is reduce used for?**
> reduce combines all elements into a single value — for example, summing a list of numbers or concatenating strings.

---

*Part of Java interview prep series — java-concepts module*