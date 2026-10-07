# Comparable vs Comparator in Java

This document summarizes the key differences between the `Comparable` and `Comparator` interfaces in Java.

## Summary of Differences

| **Feature**         | **Comparable**                         | **Comparator**                                  |
|---------------------|----------------------------------------|-------------------------------------------------|
| **Interface**       | `compareTo(T o)`                       | `compare(T o1, T o2)`                           |
| **Package**         | `java.lang`                            | `java.util`                                     |
| **Sorting Logic**   | Defines **natural order**              | Defines **custom order**                        |
| **Implementation**  | Must be implemented by the class       | Separate class or lambda expression             |
| **Flexibility**     | Can have only one natural order        | Can define multiple comparators                 |
| **Modification**    | Requires changing the class itself     | No need to modify the class                     |
| **Usage Example**   | `Collections.sort()` on a class that implements `Comparable` | `Collections.sort()` with a `Comparator` as an argument |

## When to Use
- Use **Comparable** when your class has a natural, default ordering (e.g., sorting by `ID`, `name`, `age`).
- Use **Comparator** when you need to define multiple ways to compare objects or want more flexibility in sorting without modifying the class itself.

