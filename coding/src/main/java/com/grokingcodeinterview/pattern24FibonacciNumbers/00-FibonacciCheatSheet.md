# Fibonacci Numbers Pattern

---

## 📚 Table of Contents

1. [Definition](#1-definition)
2. [When to Use This Pattern (Recognition Signals)](#2-when-to-use-this-pattern-recognition-signals)
3. [Core Idea (Mental Model)](#3-core-idea-mental-model)
4. [Standard Code Template (Skeleton)](#4-standard-code-template-skeleton)
5. [Key Decisions to Make (Interview Gold)](#5-key-decisions-to-make-interview-gold)
6. [Common Traps & Mistakes](#6-common-traps--mistakes)
7. [Time & Space Complexity](#7-time--space-complexity)
8. [Canonical Problems (Must-Know)](#8-canonical-problems-must-know)
9. [My Personal Notes (Critical Section)](#9-my-personal-notes-critical-section)
10. [Memoization vs Tabulation (Critical Concepts)](#10-memoization-vs-tabulation-critical-concepts)
11. [Quick Checklist Before Coding](#-quick-checklist-before-coding)
12. [Quick Reference: Problem Patterns](#-quick-reference-problem-patterns)
13. [Interview Tips](#-interview-tips)
14. [Final Wisdom](#-final-wisdom)

---

## 1️. Definition:


A pattern for solving problems that involve **overlapping subproblems** with **optimal substructure**, where the solution depends on solutions to smaller instances of the same problem.

---

## 2️. When to Use This Pattern (Recognition Signals)

- ✅ Problems with **recursive structure** (problem breaks into similar subproblems)
- ✅ Problems asking for **counting ways**, **minimum steps**, **maximum sum**, or **nth number**
- ✅ You notice the **same calculation repeating** multiple times
- ✅ Problem involves **climbing stairs, jumping, number sequences, or dynamic choices**
- ✅ Examples: Fibonacci, Staircase, House Robber, Coin Change, etc.

### Complexity Hint:
- **Brute Force:** O(2^n) – exponential, very slow
- **Memoization (Top-Down):** O(n) – much faster
- **Tabulation (Bottom-Up):** O(n) – cleaner and faster
- **Space Optimized:** O(1) – best solution when possible

### One-Line Trigger Thought:
> "Can I break this problem into smaller versions of itself AND reuse those answers?"

---

## 3️. Core Idea (Mental Model)

**The Problem:** You're solving the same subproblems over and over again, wasting time.

**The Solution:** **Store the answers** so you never solve the same problem twice.

**Three Levels (from worst to best):**

1. **Brute Force (Recursion):** Just call the function recursively → O(2^n) ❌
2. **Memoization (Top-Down DP):** Recursion + cache answers → O(n) ✅
3. **Tabulation (Bottom-Up DP):** Build answer from bottom using loops → O(n) ✅✅
4. **Space Optimized:** Only keep what you need → O(1) space ✅✅✅

**Rule:** If you can't explain it simply, you don't understand it yet.

---

## 4️. Standard Code Template (Skeleton)

### **Template 1: Brute Force (Worst Case)**
```java
public static int fibonacci_BF(int n) {
    if (n < 2) return n;  // Base case
    return fibonacci_BF(n - 1) + fibonacci_BF(n - 2);  // Recursion
}
// Time: O(2^n) - VERY SLOW for large n
```

### **Template 2: Memoization (Top-Down DP)**
```java
public static int fibonacci_topDown(int n) {
    int[] dp = new int[n + 1];
    return helper(dp, n);
}

private static int helper(int[] dp, int n) {
    if (n < 2) return n;  // Base case
    
    if (dp[n] == 0) {  // If not computed yet
        dp[n] = helper(dp, n - 1) + helper(dp, n - 2);  // Compute and store
    }
    return dp[n];
}
// Time: O(n) - Each number calculated once
// Space: O(n) - Recursion stack + DP array
```

### **Template 3: Tabulation (Bottom-Up DP) ⭐ RECOMMENDED**
```java
public static int fibonacci_bottomUp(int n) {
    if (n < 2) return n;
    
    int[] dp = new int[n + 1];
    dp[0] = 0;
    dp[1] = 1;
    
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];  // Build from bottom-up
    }
    return dp[n];
}
// Time: O(n) - Single loop
// Space: O(n) - DP array
```

### **Template 4: Space-Optimized Bottom-Up DP ⭐⭐ BEST**
```java
public static int fibonacci_bottomUp_Optimized(int n) {
    if (n < 2) return n;
    
    int n1 = 0;  // fib(0)
    int n2 = 1;  // fib(1)
    int temp;
    
    for (int i = 2; i <= n; i++) {
        temp = n1 + n2;  // fib(i)
        n1 = n2;        // Shift left
        n2 = temp;      // Shift left
    }
    return n2;
}
// Time: O(n) - Single loop
// Space: O(1) - Only 3 variables!
```

⚠️ **Important:** Keep this structure. Only fill in the logic.

---

## 5️. Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently.**

1. **What is the BASE CASE?**
   - When do I stop recursing?
   - For Fibonacci: n < 2 returns n

2. **What are the RECURRENCE RELATIONS?**
   - How do I express the problem in terms of smaller subproblems?
   - For Fibonacci: fib(n) = fib(n-1) + fib(n-2)

3. **Do I have OVERLAPPING SUBPROBLEMS?**
   - Will I calculate the same values multiple times?
   - For Fibonacci: YES! fib(5) needs fib(4) and fib(3), but fib(4) also needs fib(3)

4. **What approach should I use?**
   - Brute Force (only for small n)
   - Memoization (if interview-friendly recursion needed)
   - Tabulation (safer, cleaner, no stack overflow)
   - Space Optimized (if you know only last 2 values matter)

5. **Are there SPACE CONSTRAINTS?**
   - Do I need to optimize space?
   - Can I use O(n) space or must I use O(1)?

---

## 6️. Common Traps & Mistakes

👉 **Tip:** This section gets more valuable over time as you add your personal pitfalls.

### ❌ **Trap 1: Forgetting Base Case**
```java
// WRONG - Stack overflow!
public static int fib(int n) {
    return fib(n - 1) + fib(n - 2);  // No base case!
}

// RIGHT
public static int fib(int n) {
    if (n < 2) return n;  // Base case first!
    return fib(n - 1) + fib(n - 2);
}
```

### ❌ **Trap 2: Not Initializing DP Array Correctly**
```java
// WRONG - dp[2] would be 0 + 0 = 0 (incorrect)
int[] dp = new int[n + 1];
for (int i = 2; i <= n; i++) {
    dp[i] = dp[i - 1] + dp[i - 2];
}

// RIGHT
int[] dp = new int[n + 1];
dp[0] = 0;
dp[1] = 1;  // Initialize base cases!
for (int i = 2; i <= n; i++) {
    dp[i] = dp[i - 1] + dp[i - 2];
}
```

### ❌ **Trap 3: Comparing with == 0 in Memoization**
```java
// WRONG - If fib(n) = 0, this breaks!
if (dp[n] == 0) {
    dp[n] = calculate(n);
}
// For Fibonacci this works (fib(0) is valid), but use Boolean/Optional for others

// SAFER for other problems
if (dp[n] == null) {
    dp[n] = calculate(n);
}
```

### ❌ **Trap 4: Stack Overflow with Large n**
```java
// WRONG - recursion depth too large
fibonacci_topDown(1000);  // Stack overflow!

// RIGHT - use bottom-up
fibonacci_bottomUp_Optimized(1000);  // No problem
```

### ❌ **Trap 5: Forgetting to Check Edge Cases**
- n = 0, n = 1, negative n
- Integer overflow for large n (use long if needed)

---

## 7️. Time & Space Complexity

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| **Brute Force** | O(2^n) | O(n) | Recursion stack only. NEVER use in interview! |
| **Memoization** | O(n) | O(n) | n + recursion stack |
| **Tabulation** | O(n) | O(n) | Single loop, cleaner |
| **Space Optimized** | O(n) | O(1) | BEST for production! |

**Explain why in one line:**
> Each Fibonacci number is calculated only once and reused, reducing from exponential to linear time. Space optimization works because we only need the previous two values to compute the next.

---

## 8️. Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

**Problem 1: Fibonacci Numbers**
- Name: Calculate the nth Fibonacci number
- Key insight: Overlapping subproblems - fib(5) needs fib(3) which is needed by fib(4)
- Pattern: Simple addition-based recurrence

**Problem 2: Climbing Stairs**
- Name: Count ways to climb n stairs (1, 2, or 3 steps at a time)
- Key insight: Same structure as Fibonacci but different recurrence (3 previous values instead of 2)
- Pattern: countWays(n) = countWays(n-1) + countWays(n-2) + countWays(n-3)

**Problem 3: House Robber**
- Name: Max money you can rob without robbing adjacent houses
- Key insight: Choice problem - rob current or skip it
- Pattern: dp[i] = max(dp[i-1], dp[i-2] + house[i])

**Problem 4: Coin Change**
- Name: Minimum coins needed to make amount or count ways to make amount
- Key insight: Multiple previous states, knapsack-like
- Pattern: Similar DP structure but with multiple recurrence options

---

## 9. My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> "If I see the same calculation happening multiple times, it's a DP problem. Cache the answer!"

### Interview Phrasing:
> "I notice this problem has overlapping subproblems. Let me use dynamic programming to avoid recalculating the same values. I'll start with a recursive solution, then optimize it with memoization or tabulation."

### My Favorite Variation:
> **Space-Optimized Bottom-Up:** Why keep all n values when you only need the last 2? This shows you understand the problem deeply and care about efficiency.

### What I Always Forget:
> **Initialize base cases in the DP array before looping!** dp[0] and dp[1] must be set correctly, or everything downstream is wrong.

### Key Pattern Recognition:
> - If problem asks for "number of ways" → likely DP
> - If you can express answer as f(n) = f(n-1) + f(n-2) + ... → DP
> - If brute force has repeated calculations → DP

### When to Use Each Approach:
1. **Memoization:** When problem naturally suggests recursion (thinking top-down)
2. **Tabulation:** When you want to avoid recursion stack issues (safer for interviews)
3. **Space Optimized:** When you understand the pattern AND need to show optimization skills

---

## 10. Memoization vs Tabulation (Critical Concepts)

### **Memoization (Top-Down)**
```
Think: "Start from the goal, work backwards"
Implementation: Recursion + Cache (HashMap/Array)
Approach: Solve smaller problems when you need them
```

**Pros:**
- Natural problem decomposition
- Only compute what's needed
- Familiar recursive thinking

**Cons:**
- Recursion stack can overflow
- Harder to reason about complexity
- Function call overhead

### **Tabulation (Bottom-Up)**
```
Think: "Start from base case, build upwards"
Implementation: Iteration + DP Array
Approach: Solve all smaller problems first, then combine
```

**Pros:**
- No recursion stack issues
- Clear iteration logic
- Better for space optimization
- Faster in practice (no function calls)

**Cons:**
- Need to understand problem structure upfront
- Must identify correct order to solve subproblems
- Might solve unnecessary subproblems

### **When to Pick:**
- **Pick Memoization:** Problem naturally recursive OR interviewer asks "how would you optimize this?"
- **Pick Tabulation:** You want clean code OR handling large n OR showing mastery

---

## 📋 Quick Checklist Before Coding

```
✅ Do I understand the base case(s)?
✅ Can I write the recurrence relation?
✅ Will there be overlapping subproblems?
✅ Should I use memoization or tabulation?
✅ Do I need to handle edge cases (n=0, n=1)?
✅ Should I optimize space?
✅ Will there be integer overflow?
✅ Have I traced through a small example?
```

---

## 📚 Quick Reference: Problem Patterns

### **Sum-Based Problems:**
```java
dp[i] = dp[i-1] + dp[i-2]  // Fibonacci, Staircase
dp[i] = dp[i-1] + dp[i-2] + dp[i-3]  // 3-step staircase
```

### **Choice-Based Problems:**
```java
dp[i] = max(dp[i-1] + value[i], dp[i-1])  // House Robber
dp[i] = min(dp[i] + coins[j] for all j)  // Coin Change
```

### **Path-Based Problems:**
```java
dp[i][j] = dp[i-1][j] + dp[i][j-1]  // Unique Paths
```

---

## 🎯 Interview Tips

1. **Always start with brute force** - shows you understand the problem
2. **Point out overlapping subproblems** - "I see we calculate fib(3) multiple times"
3. **Ask about constraints** - "Should I optimize for space or time?"
4. **Use bottom-up by default** - safer and shows confidence
5. **Trace through a small example** - prevents silly mistakes
6. **Mention time/space complexity** - before and after optimization

---

## ✨ Final Wisdom

> The Fibonacci pattern is your **gateway drug to dynamic programming**. Master it, and suddenly 50+ other problems become solvable. The key insight is: **"Don't recalculate. Cache."**


