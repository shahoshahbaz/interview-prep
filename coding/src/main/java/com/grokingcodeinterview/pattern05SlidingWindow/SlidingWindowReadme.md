# 🧭 The Thumb Rules for Sliding Window Pattern

Sliding window problems are all about managing a range within an array or string using two pointers: `windowStart` and `windowEnd`.

---

## Types of Sliding Window

### 🧱 1. Fixed-size window

**Used when the problem says “subarray/substring of size K”**

**Typical pattern:**
```java
for (int windowEnd = 0; windowEnd < arr.length; windowEnd++) {
    currentSum += arr[windowEnd]; // or count logic

    if (windowEnd >= k - 1) {
        // process the window (e.g., update maxSum)
        result = Math.max(result, currentSum);

        // slide the window
        currentSum -= arr[windowStart];
        windowStart++;
    }
}
```

**Thumb rule:**
- When the window size is fixed, expand until the window is size k,
- Then record results and slide forward by removing the leftmost element.

---

## 🔄 2. Dynamic-size (variable) window

There are two dynamic sliding window patterns:

#### 2-1. MAX Window Pattern: 
Used when the problem asks for:
- longest, maximum, at most, no more than
You expand until invalid → then shrink until valid.

#### 2-2. MIN Window Pattern
Used when the problem asks for:

- smallest, minimum, at least, shortest
You expand until valid → then shrink as much as possible.

**Both follow the same loop, but the shrink logic is completely opposite.**

### 🔶 2. Side-by-Side Comparison
| Feature          | MAX Window Problems                       | MIN Window Problems                      |
| ---------------- | ----------------------------------------- | ---------------------------------------- |
| Goal             | Longest valid window                      | Smallest valid window                    |
| Shrink Condition | **Shrink when condition becomes INVALID** | **Shrink while condition REMAINS VALID** |
| Typical Keywords | longest, max length, at most K            | smallest, minimum, at least S            |
| Update Answer    | after window becomes valid again          | inside the shrink loop                   |
| Window Behavior  | keep it big                               | compress it aggressively                 |

### 🔶 3. Shrink Rules (Critical Part)
#### **MAX Window Shrink Rule**
```
Shrink only when the window violates the condition.
Stop shrinking when the window becomes valid again.
```
####  **MIN Window Shrink Rule**
```
Window becomes valid → now shrink while it’s still valid.
Stop shrinking when it becomes invalid.
```
These two opposite rules are what cause most confusion.

### 🔶 4. Java-Style Pseudo-Code Templates
####  1️⃣ MAX Window Template (Longest / At Most / Maximum)
```java
int windowStart = 0;
int result = 0;

for (int windowEnd = 0; windowEnd < arr.length; windowEnd++) {

    // expand window by adding right element
    add(arr[windowEnd]);

    // shrink when the window becomes invalid
    while (conditionIsViolated()) {
        remove(arr[windowStart]);
        windowStart++;
    }

    // window is valid here → update longest window size
    result = Math.max(result, windowEnd - windowStart + 1);
}
```

#### 2️⃣ MIN Window Template (Smallest / At Least / Minimum)
```java
int windowStart = 0;
int result = Integer.MAX_VALUE;

for (int windowEnd = 0; windowEnd < arr.length; windowEnd++) {

    // expand window by adding right element
    add(arr[windowEnd]);

    // shrink while window is still valid
    while (conditionIsSatisfied()) {

        // window is valid → update smallest window size
        result = Math.min(result, windowEnd - windowStart + 1);

        // shrink from the left
        remove(arr[windowStart]);
        windowStart++;
    }
}
```

## 🔶 5. Thumb-Rules (Put this on your cheat-sheet)

**✔ MAX Window Problems**
- Look for: longest, maximum, at most K, no more than
- Shrink only when the window becomes invalid
- Goal: Keep the window as large as possible while valid

**✔ MIN Window Problems**
- Look for: smallest, minimum, at least S, shortest
- Shrink while the window is valid
- Goal: Make the window as small as possible

## 🔶 6. One-Sentence Summary

MAX window → shrink on INVALID.
MIN window → shrink while VALID.


## 💣 Top 5 Common Mistakes Developers Make in Sliding Window

| # | Mistake | What Happens | How to Avoid |
|---|---------|--------------|--------------|
| 1 | Using wrong window length formula (`windowStart - windowStart + 1`) | Always gives wrong size (often 1) | Use `windowEnd - windowStart + 1` |
| 2 | Forgetting to shrink the window | Window grows forever, incorrect results | Always shrink while violating condition |
| 3 | Updating result before fixing window | Counts invalid windows | Only update result after shrinking (valid state) |
| 4 | Wrong base/guard cases (`k > len`, `s > len`, empty input) | Returns wrong or crashes | Always guard `if (arr == null)` |
| 5 | Modifying wrong variable in while-loop | Infinite loops or off-by-one | Always increment `windowStart`, not `windowEnd` inside shrink loop |

---



