# 🔢 Prefix Sum Pattern

## 📋 Table of Contents

1. [Definition](#1-definition)
2. [When to Use This Pattern](#2-when-to-use-this-pattern-recognition-signals)
3. [Core Idea (Mental Model)](#3-core-idea-mental-model)
4. [Standard Code Template](#4-standard-code-template-skeleton)
5. [Key Decisions to Make](#5-key-decisions-to-make-interview-gold)
6. [Common Traps & Mistakes](#6-common-traps--mistakes)
7. [Time & Space Complexity](#7-time--space-complexity)
8. [Canonical Problems](#8-canonical-problems-must-know)
9. [My Personal Notes](#9-my-personal-notes-critical-section)
10. [Quick Checklist](#-quick-checklist-before-coding)

---

## 1. Definition:

**Prefix Sum** is a technique where you precompute cumulative sums from the start of an array up to each index. This allows you to answer range sum queries in **O(1) time** instead of **O(n)**.

**Core Concept:**
- `prefix[i]` = sum of all elements from index 0 to i
- `prefix[0]` = `arr[0]`
- `prefix[i]` = `prefix[i-1] + arr[i]`

**Use the prefix array to find sum of any range [L, R]:**
- Sum from L to R = `prefix[R] - prefix[L-1]` (if L > 0)
- Sum from 0 to R = `prefix[R]`

---

## 2. When to Use This Pattern (Recognition Signals)

✅ **Use Prefix Sum when you see:**
- "Find sum of elements between indices..."
- "Subarray with sum equal to target..."
- "Maximum/minimum subarray sum..."
- "Number of subarrays with sum equal to k..."
- "Range query problems" (especially multiple queries)
- "Contiguous elements" that need aggregation
- Problems asking for "cumulative" or "running total"

### Complexity Hint:
- **Without prefix sum:** Multiple range queries → O(n*q) where q = number of queries
- **With prefix sum:** Precompute O(n), answer queries in O(1) per query → O(n + q)

### One-Line Trigger Thought:
> **"If I need to sum ranges repeatedly, build a prefix array first."**

---

## 3. Core Idea (Mental Model)

Think of prefix sum like a **"running scoreboard"** in a game:
- You keep a cumulative total as you move through the array
- At any point, you know the total score from start to current position
- To find score in a range, you just subtract two positions

**Why it works:**
```
Array:  [3, 1, 4, 1, 5]
Prefix: [3, 4, 8, 9, 14]

To find sum(1, 3) = arr[1] + arr[2] + arr[3] = 1 + 4 + 1 = 6
= prefix[3] - prefix[0] = 9 - 3 = 6 ✓
```

**Mental Model:**
> "Precompute once, query forever."

---

## 4. Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

### Basic Prefix Sum Array:
```java
// Build prefix sum
int[] arr = {3, 1, 4, 1, 5};
int[] prefix = new int[arr.length];

prefix[0] = arr[0];
for (int i = 1; i < arr.length; i++) {
    prefix[i] = prefix[i - 1] + arr[i];
}

// Query range sum O(1)
int left = 1, right = 3;
int rangeSum = left == 0 ? prefix[right] : prefix[right] - prefix[left - 1];
```

### Using HashMap for Prefix Sum (Subarrays with Target Sum):
```java
int target = 5;
Map<Integer, Integer> prefixCount = new HashMap<>();
prefixCount.put(0, 1); // important

int currentSum = 0;
int count = 0;

for (int num : arr) {

currentSum += num;

// CHECK: how many previous sums can form target
count += prefixCount.getOrDefault(currentSum - target, 0);

// STORE: current sum frequency
    prefixCount.put(currentSum, prefixCount.getOrDefault(currentSum, 0) + 1);
        }
```

⚠️ **Important:** Keep this structure. Only fill in the logic.

---

## 5. Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently:**

1. **Should I build the entire prefix array or use on-the-fly computation?**
   - Build array if: Multiple queries needed OR need to optimize memory access
   - On-the-fly if: Single pass OR space is critical

2. **Do I need HashMap or just array?**
   - Array: Fixed range queries
   - HashMap: Finding subarrays with target sum

3. **How do I handle negative numbers?**
   - Works the same! Prefix sum handles negatives naturally
   - Important for subarray sum problems

4. **What's my base case?**
   - `prefix[0] = arr[0]` OR
   - `prefix[0] = 0` (useful for many problems)
   - Always use `prefixCount.put(0, 1)` in HashMap approach

5. **Am I computing prefix sum on 1D array or 2D matrix?**
   - 1D: Simple linear approach
   - 2D: Need 2D prefix sum with formula: `prefix[i][j] = arr[i][j] + prefix[i-1][j] + prefix[i][j-1] - prefix[i-1][j-1]`

---

## 6. Common Traps & Mistakes

👉 **Tip:** This section gets more valuable over time as you add your personal pitfalls.

### ❌ Mistake 1: Off-by-One Errors in Range Queries
**Wrong:**
```java
int rangeSum = prefix[right] - prefix[left]; // WRONG!
```
**Correct:**
```java
int rangeSum = left == 0 ? prefix[right] : prefix[right] - prefix[left - 1];
```

### ❌ Mistake 2: Forgetting to Initialize HashMap
**Wrong:**
```java
Map<Integer, Integer> map = new HashMap<>();
// Forgot to put(0, 1)
```
**Correct:**
```java
Map<Integer, Integer> map = new HashMap<>();
map.put(0, 1); // Essential for finding subarrays starting at index 0
```

### ❌ Mistake 3: Confusing Current vs Previous in Iteration
**Wrong:**
```java
for (int i = 0; i < arr.length; i++) {
    prefix[i] = prefix[i] + arr[i]; // WRONG! prefix[i] is not set yet
}
```
**Correct:**
```java
for (int i = 1; i < arr.length; i++) {
    prefix[i] = prefix[i - 1] + arr[i];
}
```

### ❌ Mistake 4: Not Handling Edge Cases
- Empty array
- Single element
- All negative numbers
- Duplicate prefix sums

### ❌ Mistake 5: 2D Prefix Sum Formula Error
**Wrong:**
```java
prefix[i][j] = arr[i][j] + prefix[i-1][j] + prefix[i][j-1];
```
**Correct (don't double-count):**
```java
prefix[i][j] = arr[i][j] + prefix[i-1][j] + prefix[i][j-1] - prefix[i-1][j-1];
```

---

## 7. Time & Space Complexity

**Time:** 
- **Build Prefix Array:** O(n)
- **Single Range Query:** O(1)
- **Total for q queries:** O(n + q)

**Space:** 
- **With Prefix Array:** O(n)
- **With HashMap:** O(n) in worst case

**Explain why in one line:**
> We trade O(n) space upfront to make every query O(1) instead of O(n), which is worth it when we have multiple queries.

---

## 8. Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

**Problem 1: Range Sum Query**
- **Name:** LeetCode 303 - Range Sum Query - Immutable
- **Key insight:** Classic prefix sum application. Build array once, answer O(1) queries.
- **Trick:** Initialize prefix[0] properly for clean range query formula

**Problem 2: Subarray Sum Equals K**
- **Name:** LeetCode 560 - Subarray Sum Equals K
- **Key insight:** Use HashMap to count subarrays. At each position, check if `(currentSum - target)` exists.
- **Trick:** Remember `prefixCount.put(0, 1)` to handle subarrays starting at index 0

**Problem 3: Contiguous Array**
- **Name:** LeetCode 525 - Contiguous Array
- **Key insight:** Convert 0s to -1s, then find subarrays with sum = 0 (length = max subarray of 0s and 1s)
- **Trick:** Treat as "subarray sum equals 0" problem

**Problem 4: 2D Region Sum**
- **Name:** LeetCode 304 - Range Sum Query 2D - Immutable
- **Key insight:** 2D prefix sum with inclusion-exclusion formula
- **Trick:** `prefix[i][j] = arr[i][j] + prefix[i-1][j] + prefix[i][j-1] - prefix[i-1][j-1]`

**Problem 5: Maximum Subarray Sum**
- **Name:** LeetCode 53 - Maximum Subarray (Kadane's is simpler, but prefix sum also works)
- **Key insight:** For each right index, find minimum prefix sum before it
- **Trick:** Use a variable to track minimum prefix seen so far

---

## 9. My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> **"At each position, ask: 'what subarrays ending here match my target?'"**
> 
> Instead of checking all subarrays, use the map to look up instantly.

### Interview Phrasing:
> "I'm building a prefix sum array/map as I iterate. At each element, I check if a matching subarray already exists in my history. This lets me count subarrays in a single pass."

### My Favorite Variation:
> **Prefix Sum + HashMap** is the power combo:
> - Solves "how many subarrays sum to X" elegantly
> - Single pass, O(n) time
> - Often beats Kadane's algorithm in clarity

### What I Always Forget:
> 1. **The `prefixCount.put(0, 1)`** — this catches subarrays from index 0
> 2. **Order matters:** Add to count BEFORE updating the map, or you'll count the subarray with itself
> 3. **2D Prefix:** The inclusion-exclusion formula `- prefix[i-1][j-1]` prevents double-counting the overlap
> 4. **Negative numbers:** They don't break anything. Prefix sum naturally handles them.

### Common Interview Question:
> **Interviewer:** "Can you optimize this from O(n²)?"
> **You:** "Yes! I'll precompute prefix sums so each query is O(1) instead of O(n)."
> **Interviewer:** "Great. What about space?"
> **You:** "O(n) extra space for the prefix array, but that's worth it for q queries since we save O(n*q) time."

### Pattern Recognition in Code:
Watch for code that does this:
```java
for (int i = 0; i < n; i++) {
    for (int j = i; j < n; j++) {
        sum += arr[j];  // ❌ This is O(n²). Refactor with prefix sum!
    }
}
```
Replace with:
```java
// Use prefix array, now it's O(n²) → O(n) ✓
```

---

## 📋 Quick Checklist Before Coding

- [ ] Did I identify this as a "range sum" or "subarray sum" problem?
- [ ] Do I need array-based or HashMap-based prefix sum?
- [ ] Did I initialize `prefix[0]` correctly?
- [ ] Did I add `prefixCount.put(0, 1)` for HashMap approach?
- [ ] Is my range query formula correct? (`prefix[right] - prefix[left-1]` or `prefix[right] - prefix[left]`?)
- [ ] Did I handle the 2D case with inclusion-exclusion if needed?
- [ ] Did I test with edge cases: empty, single element, all negatives?
- [ ] Did I verify the time complexity is O(n + q), not O(n*q)?
- [ ] Can I explain WHY this optimization works in 1 sentence?

---

## 📚 Additional Resources

### Key Terms:
- **Cumulative Sum:** Same as prefix sum
- **Range Query:** Finding sum of elements in a range [L, R]
- **Inclusion-Exclusion Principle:** Used for 2D prefix sums
- **HashMap Frequency Map:** Tracks prefix sums seen so far

### Related Patterns:
- Two Pointers (sometimes combined with prefix sum)
- Sliding Window (can use prefix sum for ranges)
- Dynamic Programming (often uses prefix sums)

---

**Last Updated:** April 2026  
**Interview Readiness:** ⭐⭐⭐⭐⭐

