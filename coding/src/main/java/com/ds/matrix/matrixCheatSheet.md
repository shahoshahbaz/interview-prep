# Matrix Traversal Patterns - Cheatsheet

## Table of Contents
1. [Overview](#overview)
2. [Upper Triangle Patterns](#upper-triangle-patterns)
   - [top-to-bottom, left-to-right](#1-top-to-bottom-left-to-right-most-common)
   - [bottom-to-top, left-to-right](#2-bottom-to-top-left-to-right)
   - [by interval length](#3-by-interval-length-best-for-interval-dp-)
3. [Lower Triangle Patterns](#lower-triangle-patterns)
4. [Quick Reference](#quick-reference)
5. [Use Cases & Problem Examples](#use-cases--problem-examples)
6. [Code Examples](#code-examples)

---

## Overview

This cheatsheet covers **2D matrix traversal patterns** for **dynamic programming** problems, particularly those involving **intervals** and **subarrays**.

### Key Concepts
- **Upper Triangle**: All cells where `i < j`
- **Lower Triangle**: All cells where `i > j`
- **Diagonal**: Cells where `i == j`

### Why Traversal Order Matters
The order in which we process cells in a DP table affects:
- **Dependencies**: Which cells have already been computed
- **Optimization**: Cache efficiency
- **Correctness**: Ensuring all required dependencies are available

---

## Upper Triangle Patterns

Upper Triangle contains all cells where `i < j`.

### 1️⃣ Top-to-Bottom, Left-to-Right (MOST COMMON)

- Iterate row from top (r =0) to bottom (r = n-1)
- for each row, iterate column from left(c= r+1) to right (c = n-1)
- for each row, we process all pairs (r,c) where   r<c
- for each row,  we skip all pairs (r,c) where r>=c
- thi give us upper triangle traversal order
- 
- ✅ When to use:
  - Simple problems
  - When no specific dependency order needed
  - Natural left-to-right reading order
``` java
for (int r = 0; r < n; r++) {
    for (int c = r + 1; c < n; c++) {
        // process matrix[r][c]
    }
}
```
- Visualization:
```
Order (n=5):
(0,1) → (0,2) → (0,3) → (0,4) →
(1,2) → (1,3) → (1,4) →
(2,3) → (2,4) →
(3,4)

show what has been scked and what is being processed
 X ,  (0,1) , (0,2) , (0,3) , (0,4) 
 X ,    X     (1,2) , (1,3) , (1,4)
 X ,    X ,     X ,   (2,3) , (2,4)
 X ,    X ,     X ,     X   , (3,4)
```

### 2️⃣ Bottom-to-Top, Left-to-Right

- Iterate row from bottom(r = n-1 ) to top (r = 0)
- for each row, iterate column from left(c = r+1) to right(c = n-1)
- for each row, we process all pairs (r, c) where r<c
- for each row, we skip all pairs (r,c) where r>=c
- ✅ When to use:
  - When dp[i+1][j] needs to be computed first
  - Bottom-up DP approach
  - Filling from bottom row upward

```java
 for(int r = n-1; r>= 0; r--){
    for (int c = r+1; c<n; c++){
        // process matrix [r][c]
    }
        }
```
- visualization:     
```
Order (n=5):
(3,4) →
(2,3) → (2,4) →
(1,2) → (1,3) → (1,4) →
(0,1) → (0,2) → (0,3) → (0,4)

shows what has been scked and what is being processed
(0,1) , (0,2) , (0,3) , (0,4) 
  X ,   (1,2) , (1,3) , (1,4)
  X ,     X ,   (2,3) , (2,4) 
  X ,     X ,     X   , (3,4)


```
n
### 3️⃣ By Interval Length (BEST FOR INTERVAL DP) ⭐

- Process all intervals of length 2, then 3, then 4, etc.
- For each length, iterate all valid starting positions
- i + len - 1 gives the ending position j
- Guarantees all dependencies (like dp[i+1][j], dp[i][j-1]) are computed before dp[i][j]
- ✅ When to use:
  - Matrix Chain Multiplication
  - Burst Balloons
  - Palindrome Partition
  - Any problem where solution depends on dp[i+1][j], dp[i][j-1], etc.
  - this is also upper triangle traversal pattern but we are processing by interval length
  - this traverse from top to bottom and left to right but we are processing by interval length

  🎯 **KEY ADVANTAGE:** Guarantees all dependencies are solved before use
````java
 for (int len = 2; len <= n; len++) {      
    for (int i = 0; i + len - 1 < n; i++) {
        int j = i + len - 1;
        // process dp[i][j]
    }
}
````
- visualization:
````
Pattern: 

Order (n=5):
Length 2: (0,1) → (1,2) → (2,3) → (3,4)
Length 3: (0,2) → (1,3) → (2,4)
Length 4: (0,3) → (1,4)
Length 5: (0,4)

````

---

## Lower Triangle Patterns

Lower Triangle contains all cells where `i > j`.

### 1️⃣ Top-to-Bottom, Left-to-Right
```
Pattern: Iterate rows from top to bottom
         For each row i, iterate columns from 0 to i-1

Order (n=5):
(1,0) →
(2,0) → (2,1) →
(3,0) → (3,1) → (3,2) →
(4,0) → (4,1) → (4,2) → (4,3)

Code:
for (int i = 0; i < n; i++) {
    for (int j = 0; j < i; j++) {
        // process dp[i][j]
    }
}

✅ When to use:
- Standard left-to-right filling
```

### 2️⃣ Top-to-Bottom, Right-to-Left
```
Pattern: Iterate rows from top to bottom
         For each row i, iterate columns from i-1 down to 0

Code:
for (int i = 0; i < n; i++) {
    for (int j = i - 1; j >= 0; j--) {
        // process dp[i][j]
    }
}

✅ When to use:
- When right-to-left dependency matters
```

### 3️⃣ Bottom-to-Top
```
Pattern: Iterate rows from bottom to top
         For each row, iterate columns left to right

Code:
for (int i = n - 1; i >= 0; i--) {
    for (int j = 0; j < i; j++) {
        // process dp[i][j]
    }
}

✅ When to use:
- When you need dp[i-1][*] ready first
```

---

## Quick Reference

| Pattern | Code | Best For |
|---------|------|----------|
| **Upper: Top-Down LTR** | `for i=0..n; j=i+1..n` | Simple, most common |
| **Upper: Bottom-Top LTR** | `for i=n-1..0; j=i+1..n` | Depends on i+1 |
| **Upper: By Length** ⭐ | `for len=2..n; i=0..n-len` | Interval DP problems |
| **Lower: Top-Down LTR** | `for i=0..n; j=0..i-1` | Standard filling |
| **Lower: Top-Down RTL** | `for i=0..n; j=i-1..0` | Right-to-left dependency |
| **Lower: Bottom-Top** | `for i=n-1..0; j=0..i` | Depends on i-1 |

---

## Use Cases & Problem Examples

### Problems Using Upper Triangle (i < j)

#### 🔴 Matrix Chain Multiplication
- **Pattern**: By Interval Length
- **Dependency**: `dp[i][k]` and `dp[k+1][j]` must be computed first

#### 🔴 Burst Balloons
- **Pattern**: By Interval Length
- **Dependency**: All subproblems of length k before length k+1

#### 🔴 Palindromic Subsequence / Partition
- **Pattern**: By Interval Length
- **Dependency**: Smaller intervals must be solved first

### Problems Using Lower Triangle (i > j)
- Graph algorithms with adjacency matrix
- Triangular matrix operations
- Some game theory DP problems

---

## Code Examples

### Using Matrix.java

```java
// Import
import com.ds.matrix.Matrix;
import java.util.List;

// Upper Triangle - Top to Bottom
List<int[]> traversal = Matrix.upperTriangleTopToBottom(5);
Matrix.printTraversalOrder(traversal, 5);

// Upper Triangle - By Interval Length (RECOMMENDED)
List<int[]> traversal = Matrix.upperTriangleByIntervalLength(5);
Matrix.printTraversalOrder(traversal, 5);

// Lower Triangle Patterns
List<int[]> lower1 = Matrix.lowerTriangleTopToBottomLeftToRight(5);
List<int[]> lower2 = Matrix.lowerTriangleTopToBottomRightToLeft(5);
List<int[]> lower3 = Matrix.lowerTriangleBottomToTop(5);
```

### Example DP Solution: Matrix Chain Multiplication

```java
int n = matrices.length;
int[][] dp = new int[n][n];  // min cost

// By interval length - GUARANTEES all dependencies ready
for (int len = 2; len <= n; len++) {
    for (int i = 0; i + len - 1 < n; i++) {
        int j = i + len - 1;
        dp[i][j] = Integer.MAX_VALUE;
        
        for (int k = i; k < j; k++) {
            int cost = dp[i][k] + dp[k+1][j];
            if (cost < dp[i][j]) {
                dp[i][j] = cost;
            }
        }
    }
}
```

---

## Tips & Best Practices

✅ **DO**
- Use "By Interval Length" for complex interval problems
- Verify dependencies before choosing traversal order
- Test with small examples (n=3, n=4)

❌ **DON'T**
- Mix traversal orders in same DP table
- Ignore dependency chains
- Assume order doesn't matter

---

**Last Updated**: March 2024
**Source**: Matrix traversal patterns for interval DP problems

