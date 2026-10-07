# 0/1 Knapsack Cheat Sheet

## 📑 Table of Contents

1. [When to Use This Pattern (Recognition Signals)](#when-to-use-this-pattern-recognition-signals)
2. [Core Idea (Mental Model)](#core-idea-mental-model)
3. [Tabulation vs Memoization (Two DP Approaches)](#tabulation-vs-memoization-two-dp-approaches)
    - [What Are They?](#-what-are-they)
    - [Tabulation (Bottom-Up DP)](#-tabulation-bottom-up-dp---recommended-for-knapsack)
    - [Memoization (Top-Down DP)](#-memoization-top-down-dp---alternative-approach)
    - [Comparison: Tabulation vs Memoization](#-comparison-tabulation-vs-memoization)
    - [When to Use Which?](#-when-to-use-which)
4. [0/1 Knapsack Standard Code Template (Skeleton)](#01-knapsack-standard-code-template-skeleton)
5. [Key Decisions to Make (Interview Gold)](#key-decisions-to-make-interview-gold)
6. [Common Traps & Mistakes](#common-traps--mistakes)
7. [Time & Space Complexity](#time--space-complexity)
8. [Canonical Problems (Must-Know)](#canonical-problems-must-know)
9. [My Personal Notes (Critical Section)](#my-personal-notes-critical-section)
10. [Quick Checklist Before Coding](#-quick-checklist-before-coding)

---

## When to Use This Pattern (Recognition Signals)

- You have a **capacity constraint** (weight limit, budget, time limit, etc.)
- You need to **maximize value** (profit, points, score) OR **minimize cost**
- Each item can be **taken at most once** (0/1 = either take it or don't)
- **No sequences or ordering matters** – it's purely a selection problem
- The problem has **overlapping subproblems** and **optimal substructure**

**Keywords to spot:**
- "maximize profit with limited budget"
- "select items to fit in knapsack"
- "best combination without repetition"
- "capacity constraint"

### Complexity Hint:
**O(n × W)** where n = number of items, W = capacity

### One-Line Trigger Thought:
> "Can I pick and choose items to maximize value within a constraint? → Use DP table with capacity dimension."

---

## Core Idea (Mental Model)

**The Mental Picture:**

Imagine a knapsack with limited capacity W. You have n items, each with `weight[i]` and `value[i]`. For **EACH** item, you make a decision: **INCLUDE** or **EXCLUDE**?

Your goal: maximize total value while staying within capacity W.

**Key insight:** `dp[i][w]` = max value using first `i` items with capacity `w`. For each item, you compare:
- **DON'T take it:** value of `i-1` items with the same capacity `w` → `dp[i][w] = dp[i-1][w]`
- **TAKE it:** value of `i-1` items with remaining capacity, plus the current item's value → `dp[i][w] = dp[i-1][w - weight[i]] + value[i]`
- Pick the **maximum** of the two options.

**Rule:** If you can't explain it simply, you don't understand it yet.
> "0/1 Knapsack is building up solutions: at each step, decide whether the current item helps us maximize value."

---

## Tabulation vs Memoization (Two DP Approaches)

**Both solve the same problem, but with different perspectives:**

**Problem Statement (used by both code examples below):**

> Given `n` items, each with a `weight[i]` and a `value[i]`, and a knapsack with capacity `W`, determine the maximum total value you can carry without exceeding the capacity. Each item can be taken **at most once**.
>
> Example: `weights = [2, 3]`, `values = [6, 7]`, `capacity = 5` → answer: `13` (take both items; total weight `2+3=5` fits exactly, total value `6+7=13`).

### 🔄 What Are They?

**Dynamic Programming has TWO implementation styles:**
1. **Tabulation** (Bottom-Up) ← What we use in 0/1 Knapsack
2. **Memoization** (Top-Down) ← Alternative approach using recursion

---

### 📊 Tabulation (Bottom-Up DP) - RECOMMENDED FOR KNAPSACK

**Core Idea:**
- Start from **small subproblems** and build up to the final answer
- Use an explicit **DP table/array** to store results
- Fill the table **iteratively** (loops)
- No recursion involved

**Mental Model:**

What is bottom-up logic? It's building up solutions from smaller subproblems to larger ones. For knapsack, we start with no items and no capacity, and iteratively add items and capacities. We build a dp table where `dp[i][w]` represents the maximum value achievable using the **first `i` items** with capacity `w` — matching the exact state definition from Core Idea above. We initialize `dp[0][w] = 0` for every `w` (base case: zero items considered, so no value possible regardless of capacity), then build row `dp[1]` from row `dp[0]`, row `dp[2]` from row `dp[1]`, and so on, until we reach the last item and the full capacity — starting with the smallest subproblem (no items) and building up to the full problem (all items, full capacity).

**Pseudocode (2D — matches the `dp[i][w]` state from Core Idea):**
```
n: number of items
W: max capacity
dp[i][w]: max value using the first i items with capacity w
dp = array of size (n+1) x (W+1)

dp[0][w] = 0 for all w   // base case: zero items considered

For each item i (1 to n):
   currentWeight = weight[i-1];
   currentValue = value[i-1];
    For each capacity w (0 to W):
         includeItem = 0
         excludeItem = dp[i-1][w]   // Option 1: don't take item i
        If curr <= w:
            includeItem = currentValue + dp[i-1][w - currentWeight]  // Option 2: take item i
        dp[i][w] = max(includeItem, excludeItem)
    return dp[n][W]  // Max value using all n items, full capacity
```

> "I'll solve all small problems first, then use those results to solve bigger problems. By the time I need the final answer, it's already computed in my table."

**Code Example - Tabulation (2D, matches the state above):**

```java
// Tabulation - Bottom-Up DP (2D table, matches dp[i][w] from Core Idea)
public class KnapsackTabulation2D {

    public int knapsackTabulation(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];  // dp[i][w] = max value using first i items, capacity w

        for (int i = 1; i <= n; i++) {
            int currentWeight = weights[i - 1];
            int currentValue = values[i - 1];
            for (int w = 0; w <= capacity; w++) {
                int excludeItem = dp[i - 1][w];  // Option 1: skip item i
                int includeItem = 0;

                if (weights[i - 1] <= w) {  // Option 2: take item i, if it fits
                    includeItem = currentValue + dp[i - 1][w - currentWeight]; 
                }
                dp[i][w] = Math.max(excludeItem, includeItem);  // Choose the better option
            }
        }

        return dp[n][capacity];  // Max value using all n items, full capacity
    }
}
```

**Once the 2D version makes sense, it collapses to 1D:** notice `dp[i][w]` only ever reads from row `i-1` — never row `i` itself, and never any earlier row. That means you don't need to keep every row around; a single 1D array, updated in place, works — **as long as you update it backward** (so you don't overwrite a value from row `i-1` before you've read it for this same item). That's where the `dp[w]` version below comes from.

**Pseudocode (1D — space-optimized):**
```
W: max capacity
dp[w]: max value achievable with capacity w
dp[w] = 0 for all w initially

For each item (0 to n):
    For each capacity (w down to 0):
        If item fits in w:
            dp[w] = max(dp[w], dp[w - weight[i]] + value[i])
```


**Code Example - Tabulation (1D, space-optimized):**

```java
// Tabulation - Bottom-Up DP (1D Array Optimization)
public class KnapsackTabulation {

    public int knapsackTabulation(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[] dp = new int[capacity + 1];  // dp[w] = max value with capacity w

        // For each item
        for (int i = 0; i < n; i++) {
            // Iterate capacity BACKWARDS so each item is only used once
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(
                    dp[w],                                   // Option 1: Skip this item
                    dp[w - weights[i]] + values[i]           // Option 2: Take this item
                );
            }
        }

        return dp[capacity];  // Max value with full capacity
    }
}
```

**Example** with `weights=[2,3]`, `values=[6,7]`, `capacity=5`:

**Initial state:** `dp = [0, 0, 0, 0, 0, 0]` (indices 0 through 5)

**Item 0** (weight=2, value=6) — inner loop `w` from `5` down to `2`:
```
w=5: dp[5] = max(dp[5], dp[5-2]+6) = max(0, dp[3]+6) = max(0, 0+6) = 6
w=4: dp[4] = max(dp[4], dp[4-2]+6) = max(0, dp[2]+6) = max(0, 0+6) = 6
w=3: dp[3] = max(dp[3], dp[3-2]+6) = max(0, dp[1]+6) = max(0, 0+6) = 6
w=2: dp[2] = max(dp[2], dp[2-2]+6) = max(0, dp[0]+6) = max(0, 0+6) = 6
```
State after Item 0: `dp = [0, 0, 6, 6, 6, 6]`

**Item 1** (weight=3, value=7) — inner loop `w` from `5` down to `3`:
```
w=5: dp[5] = max(dp[5], dp[5-3]+7) = max(6, dp[2]+7) = max(6, 6+7) = max(6, 13) = 13
w=4: dp[4] = max(dp[4], dp[4-3]+7) = max(6, dp[1]+7) = max(6, 0+7) = max(6, 7)  = 7
w=3: dp[3] = max(dp[3], dp[3-3]+7) = max(6, dp[0]+7) = max(6, 0+7) = max(6, 7)  = 7
```
State after Item 1: `dp = [0, 0, 6, 7, 7, 13]`

**Final table:**
```
Capacity: 0  1  2  3  4  5
Item 0:   0  0  6  6  6  6    (weight=2, value=6)
Item 1:   0  0  6  7  7  13   (weight=3, value=7)

Answer: dp[5] = 13 (take both items)
```

**The one cell worth staring at: `dp[5]` during Item 1.** `dp[5-3] = dp[2] = 6` — that `6` came entirely from Item 0's pass (a single water bottle). Adding Item 1's value `7` on top gives `13` — meaning the final answer combines **both** items, each counted exactly once, which is only possible because the backward loop guaranteed `dp[2]` still reflected "before Item 1 existed" at the moment it was read.
- You're **filling a table** row by row, column by column
- Each cell depends on previously computed cells
- No surprises—linear, predictable computation

**Advantages:**
- ✅ Space-efficient (can optimize to 1D)
- ✅ Fast (no recursion overhead)
- ✅ Predictable memory access (cache-friendly)
- ✅ Easier to optimize further
- ✅ **Better for interviews** (cleaner, faster)

**Disadvantages:**
- ❌ Computes ALL states (even ones you don't need)
- ❌ Less intuitive for some people

---

### 🎯 Memoization (Top-Down DP) - ALTERNATIVE APPROACH

What is memoization? It's a technique where you solve the problem recursively, but you store the results of subproblems so you don't compute them again. It's like having a "memory" of what you've already solved.

**Core Idea:**
- Start from **the final problem** and recursively solve subproblems
- Store results of subproblems to avoid recomputation
- Use a **HashMap/array** to cache results
- Recursion with "memory" (hence "memo-ization")

**Mental Model:**
```
"I'll try to solve the big problem recursively.
 Whenever I solve a subproblem, I'll remember the answer.
 If I see the same subproblem again, I'll use my memory instead 
 of solving it again."

Example: Can I fit items with capacity=5?
  → Try item 0 (weight=2, value=6):
     → Recursively solve for capacity=3
     → Try item 1 (weight=3, value=7):
        → Recursively solve for capacity=0
        → Base case: return 0
     → (memoize result for (item=1, capacity=3))
  → Compare options and memoize result
```

**Code Example - Memoization:**

```java
// Memoization - Top-Down DP (Recursion + Caching)
public class KnapsackMemo {
    private Map<String, Integer> memo = new HashMap<>();
    
    public int knapsackMemo(int[] weights, int[] values, int capacity) {
        return solve(weights, values, 0, capacity);
    }
    
    private int solve(int[] weights, int[] values, int itemIdx, int capacity) {
        // Base case: no items left or no capacity
        if (itemIdx == weights.length || capacity == 0) {
            return 0;
        }
        
        // Check if already solved
        String key = itemIdx + "," + capacity;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        // Option 1: Skip this item
        int skip = solve(weights, values, itemIdx + 1, capacity);
        
        // Option 2: Take this item (if it fits)
        int take = 0;
        if (weights[itemIdx] <= capacity) {
            take = values[itemIdx] + solve(weights, values, itemIdx + 1, 
                   capacity - weights[itemIdx]);
        }
        
        // Store and return the best option
        int result = Math.max(skip, take);
        memo.put(key, result);
        return result;
    }
}
```

**Advantages:**
- ✅ More intuitive (follows problem logic naturally)
- ✅ Only computes needed subproblems
- ✅ Easier to understand the recursion tree

**Disadvantages:**
- ❌ Recursion overhead (function calls)
- ❌ Risk of stack overflow for large inputs
- ❌ HashMap/cache lookup is slower than array access
- ❌ More memory overhead (call stack + memo)

---

### 📈 Comparison: Tabulation vs Memoization

| Aspect | Tabulation (Bottom-Up) | Memoization (Top-Down) |
|--------|----------------------|----------------------|
| **Approach** | Iterative (loops) | Recursive (function calls) |
| **Data Structure** | DP array/table | HashMap or 2D array |
| **Computation Order** | Fill from base cases forward | Solve from goal backward |
| **Used States** | Computes ALL | Only needed ones |
| **Speed** | ⚡ Faster (no recursion) | Slower (recursion overhead) |
| **Memory** | 💾 Better (1D optimization) | More (call stack + memo) |
| **Intuition** | Build up from simple to complex | Recursively break down problem |
| **Interview Choice** | ✅ PREFERRED | Used when memoization is clearer |

---

### 🎓 When to Use Which?

**Use Tabulation (Bottom-Up) if:**
- ✅ You understand the problem bottom-up naturally
- ✅ You want maximum performance
- ✅ You're optimizing space (1D array)
- ✅ You need predictable runtime

**Use Memoization (Top-Down) if:**
- ✅ The recursive definition is more natural
- ✅ You only need some states (sparse subproblems)
- ✅ You want to match your recursive thinking
- ✅ The problem is easier to think about recursively

**For 0/1 Knapsack in interviews:**
> **Use Tabulation (bottom-up 2D or 1D array)**
> - It's the "standard" solution
> - Faster and cleaner
> - Easier to explain the DP state and recurrence

---

## 0/1 Knapsack Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

### Tabulation (Bottom-Up)

```java
// 0/1 Knapsack - Bottom-Up DP (1D Array Optimization)
public int knapsack(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[] dp = new int[capacity + 1];  // dp[w] = max value with capacity w
    
    // For each item
    for (int i = 0; i < n; i++) {
        // IMPORTANT: Iterate capacity BACKWARDS to avoid using same item twice
        for (int w = capacity; w >= weights[i]; w--) {
            // Recurrence: take it or leave it
            dp[w] = Math.max(
                dp[w],                                    // Option 1: Skip this item
                dp[w - weights[i]] + values[i]            // Option 2: Take this item
            );
        }
    }
    
    return dp[capacity];  // Max value with full capacity
}
```

**2D Version (Easier to understand, uses more space):**

```java
public int knapsack2D(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[][] dp = new int[n + 1][capacity + 1];
    
    for (int i = 1; i <= n; i++) {
        for (int w = 0; w <= capacity; w++) {
            // Skip current item
            dp[i][w] = dp[i-1][w];
            
            // Take current item if it fits
            if (weights[i-1] <= w) {
                dp[i][w] = Math.max(dp[i][w], 
                    dp[i-1][w - weights[i-1]] + values[i-1]);
            }
        }
    }
    
    return dp[n][capacity];
}
```

⚠️ **Important:**
- **1D version**: Iterate capacity BACKWARDS (critical!)
- **2D version**: Iterate normally (each row depends only on previous row)
- Both give same answer; 1D is space-optimized

### Memoization (Top-Down)

```java
// 0/1 Knapsack - Top-Down DP (Recursion + Caching)
public int knapsackMemo(int[] weights, int[] values, int capacity) {
    Map<String, Integer> memo = new HashMap<>();
    return solve(weights, values, 0, capacity, memo);
}

private int solve(int[] weights, int[] values, int itemIdx, int capacity, Map<String, Integer> memo) {
    // Base case: no items left or no capacity
    if (itemIdx == weights.length || capacity == 0) {
        return 0;
    }

    String key = itemIdx + "," + capacity;
    if (memo.containsKey(key)) {
        return memo.get(key);
    }

    // Option 1: Skip this item
    int skip = solve(weights, values, itemIdx + 1, capacity, memo);

    // Option 2: Take this item (if it fits)
    int take = 0;
    if (weights[itemIdx] <= capacity) {
        take = values[itemIdx] + solve(weights, values, itemIdx + 1,
               capacity - weights[itemIdx], memo);
    }

    int result = Math.max(skip, take);
    memo.put(key, result);
    return result;
}
```

⚠️ **Important:** unlike the tabulation versions, there's no "iterate backward" rule here — the 0/1 constraint (each item used once) comes from `itemIdx + 1` always moving forward in the recursive call, never revisiting the same item twice.

---

## Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently.**

1. **What exactly am I maximizing/minimizing?**
    - Value? Profit? Count? Make sure you understand the goal

2. **Can items be taken multiple times?**
    - 0/1 Knapsack: NO (at most once)
    - Unbounded Knapsack: YES (iterate forward in 1D DP)

3. **Do I need the actual items selected OR just the max value?**
    - Max value only? → Return dp[capacity]
    - Need actual items? → Backtrack from dp[n][capacity]

4. **What's the constraint dimension?**
    - Single constraint (weight)? → Use 2D DP
    - Multiple constraints? → Expand DP to 3D or more

5. **Should I use 1D or 2D DP?**
    - 1D is faster; 2D is clearer for interviews
    - **Choose 2D first if unsure** – clarity > cleverness

---

## Common Traps & Mistakes

👉 **Trap 1: Forward iteration in 1D version**
```java
// ❌ WRONG: Iterates forward
for (int w = weights[i]; w <= capacity; w++) { ... }
// Uses updated dp[w - weight[i]] (uses same item twice!)

// ✅ CORRECT: Iterate backward
for (int w = capacity; w >= weights[i]; w--) { ... }
// Uses old dp[w - weight[i]] (from previous item)
```

👉 **Trap 2: Forgetting to initialize DP**
- All zeros is correct for maximization
- Use Integer.MAX_VALUE carefully for minimization

👉 **Trap 3: Off-by-one errors with array indices**
- 0-indexed items but 1-indexed DP rows? → Watch boundaries!

👉 **Trap 4: Confusing with "Coin Change" or "Combination Sum"**
- Knapsack: Each item at most once, return max value
- Coin Change: Coins unlimited, return min count
- Different problems, different DP recurrence!

👉 **Trap 5: Not handling impossible cases**
```java
// If you can't fill the knapsack, what to return?
// Usually: 0 (can always take nothing)
// But sometimes: -1 or special handling needed
```

---

## Time & Space Complexity

**Time:** O(n × W)
- n items, W capacity
- Each state computed once

**Space:** O(W) for 1D DP, O(n × W) for 2D DP

**Explain why in one line:**
> We fill a table with n items and W+1 capacities; each cell depends on previous cells in constant time.

---

## Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

**Problem 1: Classic 0/1 Knapsack**
- Name: 0/1 Knapsack (LeetCode 416 - Partition Equal Subset Sum)
- Key insight: Convert to "Can we achieve exactly capacity W/2?"
- Input: weights, values, capacity
- Output: Maximum value

**Problem 2: Unbounded Knapsack (Variation)**
- Name: Coin Change (LeetCode 322)
- Key insight: Items can be reused → Iterate capacity forward in 1D
- Problem: Find minimum coins to make amount
- Different recurrence but same DP idea

**Problem 3: Multiple Constraints**
- Name: Target Sum (LeetCode 494)
- Key insight: Similar structure but extra constraint (sum target)
- Variation: 3D or 2D DP depending on constraints

---

## My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> "0/1 = each item at most once → backward iteration in 1D, or use 2D naturally. Unbounded = forward iteration."

### Interview Phrasing:
> "We use dynamic programming with a 1D array where `dp[w]` represents the maximum value achievable with capacity `w`. For each item, we decide whether to include it or exclude it, processing in reverse capacity order to avoid using the same item twice."

### My Favorite Variation:
> **Partition Equal Subset Sum**: Check if array can be split into two equal-sum subsets. Convert to knapsack: Can we select items with sum = total/2? Yes = partition possible.

### What I Always Forget:
> **Backward iteration in 1D!** I often write forward iteration and spend 10 minutes debugging why items get used twice. Always reverse when optimizing to 1D.

---

## 📋 Quick Checklist Before Coding

- [ ] Did I identify this is a 0/1 knapsack problem (each item ≤ 1 time)?
- [ ] Did I define what I'm maximizing/minimizing?
- [ ] Did I choose 1D (backward) or 2D (clear) DP?
- [ ] If 1D: Am I iterating capacity **backward**?
- [ ] If 2D: Am I handling 1-indexed items correctly?
- [ ] Did I handle edge case where item doesn't fit?
- [ ] Did I verify with a small example (2-3 items)?
- [ ] Can I trace backward to find which items were selected (if needed)?
- [ ] Did I consider if items can be used multiple times (unbounded)?