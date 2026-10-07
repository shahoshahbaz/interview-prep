# Matrix Traversal Patterns - Visual Reference

## 📊 Visual Examples (5x5 Matrix)

### UPPER TRIANGLE (i < j)

#### 1️⃣ Top-to-Bottom, Left-to-Right
```
Iteration Order:
(0,1)→(0,2)→(0,3)→(0,4)→
(1,2)→(1,3)→(1,4)→
(2,3)→(2,4)→
(3,4)

Numbered Matrix:
      j=0 j=1 j=2 j=3 j=4 
i=0    0   1   2   3   4  
i=1    0   0   5   6   7  
i=2    0   0   0   8   9  
i=3    0   0   0   0  10  
i=4    0   0   0   0   0  

Use Case: Simple problems, natural reading order
✅ MOST COMMON PATTERN
```

---

#### 2️⃣ Bottom-to-Top, Left-to-Right
```
Iteration Order:
(4,5) → ERROR! j must be < n
(3,4)→(3,5) → ERROR!
(2,3)→(2,4)→(2,5) → ERROR!
(1,2)→(1,3)→(1,4)→
(0,1)→(0,2)→(0,3)→(0,4)

Numbered Matrix:
      j=0 j=1 j=2 j=3 j=4 
i=0    0   7   8   9  10  
i=1    0   0   4   5   6  
i=2    0   0   0   2   3  
i=3    0   0   0   0   1  
i=4    0   0   0   0   0  

Use Case: When dp[i+1][j] needs to be ready first
✓ BOTTOM-UP APPROACH
```

---

#### 3️⃣ By Interval Length (BEST FOR INTERVAL DP) ⭐⭐⭐
```
Iteration Order:
Length 2: (0,1)→(1,2)→(2,3)→(3,4)
Length 3: (0,2)→(1,3)→(2,4)
Length 4: (0,3)→(1,4)
Length 5: (0,4)

Numbered Matrix:
      j=0 j=1 j=2 j=3 j=4 
i=0    0   1   5   8  10  
i=1    0   0   2   6   9  
i=2    0   0   0   3   7  
i=3    0   0   0   0   4  
i=4    0   0   0   0   0  

Use Case: Matrix Chain Multiplication, Burst Balloons, etc.
✓ BEST FOR INTERVAL DP
🎯 GUARANTEES ALL DEPENDENCIES READY!
```

---

## LOWER TRIANGLE (i > j)

#### 1️⃣ Top-to-Bottom, Left-to-Right
```
Iteration Order:
(1,0)→
(2,0)→(2,1)→
(3,0)→(3,1)→(3,2)→
(4,0)→(4,1)→(4,2)→(4,3)

Numbered Matrix:
      j=0 j=1 j=2 j=3 j=4 
i=0    0   0   0   0   0  
i=1    1   0   0   0   0  
i=2    2   3   0   0   0  
i=3    4   5   6   0   0  
i=4    7   8   9  10   0  

Use Case: Standard left-to-right filling
✓ MOST COMMON FOR LOWER TRIANGLE
```

---

#### 2️⃣ Top-to-Bottom, Right-to-Left
```
Iteration Order:
(1,0)→
(2,1)→(2,0)→
(3,2)→(3,1)→(3,0)→
(4,3)→(4,2)→(4,1)→(4,0)

Numbered Matrix:
      j=0 j=1 j=2 j=3 j=4 
i=0    0   0   0   0   0  
i=1    1   0   0   0   0  
i=2    3   2   0   0   0  
i=3    6   5   4   0   0  
i=4   10   9   8   7   0  

Use Case: When right-to-left dependency matters
✓ REVERSE COLUMN ORDER
```

---

#### 3️⃣ Bottom-to-Top
```
Iteration Order:
(4,0)→(4,1)→(4,2)→(4,3)→
(3,0)→(3,1)→(3,2)→
(2,0)→(2,1)→
(1,0)→

Numbered Matrix:
      j=0 j=1 j=2 j=3 j=4 
i=0    0   0   0   0   0  
i=1   10   0   0   0   0  
i=2    8   9   0   0   0  
i=3    5   6   7   0   0  
i=4    1   2   3   4   0  

Use Case: When dp[i-1][*] needs to be ready first
✓ REVERSE ROW ORDER
```

---

## 📈 Pattern Comparison Chart

```
┌─────────────────────────────────────────────────────────────┐
│           UPPER TRIANGLE (i < j)                            │
├─────────────────────────────────────────────────────────────┤
│ Pattern              │ Dependency        │ When to Use       │
├─────────────────────────────────────────────────────────────┤
│ Top-Down LTR         │ Previous rows     │ ✓ Simple problems │
│ Bottom-Top LTR       │ Next row (i+1)    │ ✓ Bottom-up DP    │
│ By Interval Length   │ ALL GUARANTEED    │ ⭐⭐⭐ Interval DP │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│           LOWER TRIANGLE (i > j)                            │
├─────────────────────────────────────────────────────────────┤
│ Pattern              │ Column Order      │ When to Use       │
├─────────────────────────────────────────────────────────────┤
│ Top-Down LTR         │ Left to Right     │ ✓ Standard        │
│ Top-Down RTL         │ Right to Left     │ ✓ R→L dependency  │
│ Bottom-Top           │ Left to Right     │ ✓ Previous rows   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎯 Problem-to-Pattern Mapping

```
INTERVAL DP PROBLEMS (Use Upper Triangle By Interval Length):
┌──────────────────────────────────────────────────────────┐
│ • Matrix Chain Multiplication                            │
│ • Burst Balloons                                         │
│ • Palindrome Partition                                   │
│ • Regular Expression Matching                            │
│ • Distinct Subsequences                                  │
│ • Optimal Binary Search Tree                            │
└──────────────────────────────────────────────────────────┘

SIMPLE DP PROBLEMS (Use Upper Triangle Top-Down LTR):
┌──────────────────────────────────────────────────────────┐
│ • Basic interval problems                               │
│ • No specific dependency order                          │
│ • Linear DP transitions                                 │
└──────────────────────────────────────────────────────────┘

GRAPH/ADJACENCY PROBLEMS (Use Lower Triangle):
┌──────────────────────────────────────────────────────────┐
│ • Graph connectivity matrices                           │
│ • Triangular matrix operations                          │
│ • Game theory DP problems                               │
└──────────────────────────────────────────────────────────┘
```

---

## 🔄 Dependency Visualization

### Pattern: By Interval Length
```
When solving dp[i][j]:

Length 2:
dp[0][1], dp[1][2], dp[2][3], dp[3][4]

Length 3:
dp[0][2] → depends on dp[0][1], dp[1][2] ✓ READY!
dp[1][3] → depends on dp[1][2], dp[2][3] ✓ READY!
...

Length 4:
dp[0][3] → depends on dp[0][2], dp[1][3] ✓ READY!
...

✓ All smaller intervals computed before larger ones!
✓ All dependencies guaranteed to be ready!
```

---

## 💡 Decision Tree

```
START: How to traverse matrix for DP?
  │
  ├─→ Is it an INTERVAL DP problem?
  │   │
  │   ├─→ YES → Use "By Interval Length" ⭐
  │   │   (Matrix Chain, Burst Balloons, etc.)
  │   │
  │   └─→ NO → Continue...
  │
  ├─→ Do I need dp[i+1][j] ready first?
  │   │
  │   ├─→ YES (UPPER) → Use "Bottom-Top" ✓
  │   │
  │   └─→ NO (UPPER) → Use "Top-Down LTR" ✓
  │
  └─→ For LOWER TRIANGLE:
      │
      ├─→ Need specific column order?
      │   ├─→ Right-to-Left? → Use "Top-Down RTL"
      │   └─→ Left-to-Right? → Use "Top-Down LTR"
      │
      └─→ Need dp[i-1][*] ready first?
          ├─→ YES → Use "Bottom-Top"
          └─→ NO → Use "Top-Down LTR"
```

---

## ✨ Quick Reference: Which Pattern?

### Most Common: 🥇 By Interval Length
```java
for (int len = 2; len <= n; len++) {
    for (int i = 0; i + len - 1 < n; i++) {
        int j = i + len - 1;
        // Solve dp[i][j]
        // All dependencies are GUARANTEED ready!
    }
}
```

### Simple/Standard: 🥈 Top-to-Bottom LTR
```java
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
        // Solve dp[i][j] (upper) or
        // Solve dp[j][i] (lower - reversed i,j)
    }
}
```

### Specialized: 🥉 Bottom-to-Top
```java
for (int i = n - 1; i >= 0; i--) {
    for (int j = i + 1; j < n; j++) {
        // Solve dp[i][j] - ensures dp[i+1][j] ready
    }
}
```

---

## 📌 Important Reminders

✅ **DO:**
- Use "By Interval Length" for interval DP
- Visualize the traversal order
- Verify dependencies are ready
- Test with small examples first

❌ **DON'T:**
- Mix traversal orders in same table
- Ignore dependency chains
- Assume order doesn't matter
- Skip verification of dependencies

---

**Visual Reference Complete** ✅  
**Use this as a quick lookup guide**

