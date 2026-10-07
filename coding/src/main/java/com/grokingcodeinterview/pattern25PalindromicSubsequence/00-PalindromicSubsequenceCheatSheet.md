#  Palindromic Subsequence pattern

---

## 📚 Table of Contents

1. [Definition](#1️-definition)
2. [When to Use This Pattern (Recognition Signals)](#2️--when-to-use-this-pattern-recognition-signals)
3. [Core Idea (Mental Model)](#3️-core-idea-mental-model)
4. [Visualization](#4-visualization)
5. [Standard Code Template (Skeleton)](#5standard-code-template-skeleton )
6. [Key Decisions to Make (Interview Gold)](#6key-decisions-to-make-interview-gold)
7. [Common Traps & Mistakes](#7-common-traps--mistakes)
8. [Time & Space Complexity](#8-time--space-complexity)
9. [Canonical Problems (Must-Know)](#9-canonical-problems-must-know)
10. [My Personal Notes (Critical Section)](#10-my-personal-notes-critical-section)
11. [Quick Checklist Before Coding](#-quick-checklist-before-coding)
12. [Quick Reference: Pattern Recognition](#-quick-reference-pattern-recognition)

---

## 1️. Definition:



A pattern for solving problems involving finding, counting, or manipulating palindromic subsequences within strings using Dynamic Programming.

---

## 2️.  When to Use This Pattern (Recognition Signals)

- ✅ Problem asks for **longest palindromic subsequence**
- ✅ Need to **count palindromic subsequences**
- ✅ Find **minimum cuts to make palindromes**
- ✅ Problem mentions **non-contiguous characters forming palindrome**
- ✅ Need to **print all palindromic subsequences**
- ✅ Problem requires **minimum deletion/insertion for palindrome**

### Complexity Hint:
- Brute force: O(2^n) - check all subsequences
- DP 1D: O(n²) - compare characters
- DP 2D: O(n²) space and time

### One-Line Trigger Thought:
> "Can I match characters from outside-in to find palindromes?"

---

## 3️. Core Idea (Mental Model)

**Key Insight:** Use a **2D DP table** where `dp[i][j]` represents a property (longest length, count, etc.) of palindromic subsequence from index `i` to `j`.

**The Magic:** Palindromes are built symmetrically. If `s[i] == s[j]`, you can extend an inner palindrome. If they don't match, try shrinking from either side.

**Expansion Pattern:**
```
For length = 2 to n:
  For each window of that length:
    If characters at boundaries match → dp[i][j] = inner_result + 2
    Else → dp[i][j] = max(left_result, right_result)
```

---
 ## 4. Visualization

### Dependency View

Before computing `dp[si][ei]`, these cells must already be computed:

| Cell | Location | Reference |
|------|----------|-----------|
| `dp[si+1][ei-1]` | Diagonal down-left | **D** |
| `dp[si+1][ei]` | Below | **B** |
| `dp[si][ei-1]` | Left | **L** |

### Table Layout

```
       ei-1   ei
si      L      X
si+1    D      B
```

### Cell References

| Symbol | Definition | Meaning |
|--------|------------|---------|
| **X** | `dp[si][ei]` | Current cell (what we're computing) |
| **L** | `dp[si][ei-1]` | Same row, left |
| **B** | `dp[si+1][ei]` | Below, same column |
| **D** | `dp[si+1][ei-1]` | Diagonal down-left (inner substring) |

**Abbreviation:** `si` = startIndex, `ei` = endIndex

### Dependency Pattern

```
┌─────────────────────────────┐
│     [L]        [X]          │
│                             │
│     [D]        [B]          │
└─────────────────────────────┘
```

**Current cell `dp[si][ei]` depends on:**
- `dp[si][ei-1]` (left)
- `dp[si+1][ei]` (below)
- `dp[si+1][ei-1]` (inner)

## 5️.Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

```java
public class PalindromicSubsequenceSolver {
    
    // Template: 2D DP for palindromic problems
    public int solvePalindromic(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        
        // Base case: single characters are palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;  // OR: 0 if measuring cuts, etc.
        }
        
        // Fill table by increasing length
        // see previous section for loop structure
        for (int startIndex = n-1; startIndex >= 0; startIndex--) { // start index
            for (int endIndex = startIndex + 1; endIndex < n; endIndex++) { // end index
                if (s.charAt(startIndex) == s.charAt(endIndex)) {
                    // Characters match: extend inner palindrome
                    dp[startIndex][endIndex] = dp[startIndex + 1][endIndex - 1] + 2;  // Logic varies by problem
                } else {
                    // Characters don't match: try both sides
                    dp[startIndex][endIndex] = Math.max(dp[startIndex + 1][endIndex], dp[startIndex][endIndex - 1]);
                }
            }
        }
               
        return dp[0][n - 1];
    }
}
```

⚠️ **Important:** Keep this structure. Only fill in the logic (what to do when characters match/don't match).

---

## 6.Key Decisions to Make (Interview Gold)

| Decision | Impact | Examples |
|----------|--------|----------|
| **What does dp[i][j] represent?** | Determines all calculations | Length? Count? Min cuts? |
| **How to handle matching chars?** | Changes recurrence relation | `dp[i+1][j-1] + 2` or `+ 1` or `* 2`? |
| **How to handle non-matching?** | Defines transition | `max(left, right)` or `left + right`? |
| **Bottom-up vs Top-down?** | Memory/speed tradeoff | Usually bottom-up for this pattern |
| **Do you need reconstruction?** | Adds complexity | Track parent pointers if needed |

✅ **If you can answer these, you can code confidently.**

---

## 7. Common Traps & Mistakes

🚨 **Trap 1: Wrong Base Case**
- ❌ Forgetting to initialize single characters
- ✅ Always set `dp[i][i]` first

🚨 **Trap 2: Wrong Loop Order**
- ❌ starting with `startIndex` from 0 to n-1 and `endIndex` from i+1 to n-1 (won't have inner results ready)
- ✅ Loop by starting with the end for `startIndex` and moving backwards, and `endIndex` moving forwards

🚨 **Trap 3: Off-by-one in Length Calculation**
- ❌ using `startIndex + length` for end index
- ✅ Use `endIndex = startIndex + length - 1`

🚨 **Trap 4: Confusion with Substring vs Subsequence**
- ❌ Palindromic Substring = contiguous (use different DP)
- ✅ Palindromic Subsequence = non-contiguous (this pattern)

🚨 **Trap 5: Not Considering Both Removal Options**
- ❌ Only removing from left: `dp[i+1][j]`
- ✅ Must try both: `Math.max(dp[i+1][j], dp[i][j-1])`

👉 **Tip:** This section gets more valuable over time as you add your personal pitfalls.

---

## 8. Time & Space Complexity

**Time:** O(n²)
- Two nested loops through all substrings

**Space:** O(n²)
- 2D DP table of size n×n

**Explain why in one line:**
> We fill an n×n table, and each cell takes O(1) to compute.

---

## 9. Canonical Problems (Must-Know)

### Problem 1: Longest Palindromic Subsequence
- **Name:** LeetCode 516
- **Key insight:** `dp[i][j] = max length of palindrome from i to j`
- When `s[i] == s[j]`: `dp[i][j] = dp[i+1][j-1] + 2`
- When `s[i] != s[j]`: `dp[i][j] = max(dp[i+1][j], dp[i][j-1])`

### Problem 2: Count Different Palindromic Subsequences
- **Name:** LeetCode 730
- **Key insight:** `dp[i][j] = count of palindromes from i to j`
- Must handle duplicates carefully
- When `s[i] == s[j]`: `dp[i][j] = dp[i+1][j] + dp[i][j-1] + 1` (but avoid double-counting)

### Problem 3: Minimum Cuts for Palindromes
- **Name:** LeetCode 132
- **Key insight:** Combine palindrome detection with DP on cuts
- First build `isPalin[i][j]` table, then `dp[i] = min cuts for 0 to i`

---

## 10. My Personal Notes (Critical Section)

### Mental Shortcut:
> "Expand outward from every pair of matching characters. If they match, add 2 to the inner result."

### Interview Phrasing:
> "I'll use a 2D DP table where dp[i][j] represents [the property] of the palindromic subsequence from index i to j. I'll fill it by increasing substring length, and when characters match at boundaries, I'll extend the inner palindrome."

### My Favorite Variation:
> **Reconstruction:** To print the actual palindrome, maintain parent pointers. When `s[i] == s[j]`, point both to inner solution. Trace back to reconstruct.

### What I Always Forget:
> Must initialize base cases (single characters) BEFORE filling longer substrings, and must always check BOTH directions when characters don't match.

### Connection to Other Patterns:
> - **Similar to:** Edit Distance (both use 2D DP table)
> - **Opposite of:** Palindromic Substrings (contiguous vs non-contiguous)
> - **Often combined with:** Greedy algorithms for minimum cuts/insertions
> - substring → must be continuous → boolean DP
> -  subsequence → can skip → length DP

---

## 📋 Quick Checklist Before Coding

```
[ ] Do I understand if it's subsequence (non-contiguous) or substring (contiguous)?
[ ] Have I defined what dp[i][j] represents clearly?
[ ] Did I initialize all base cases (single characters)?
[ ] Am I filling the table by INCREASING LENGTH?
[ ] Do I handle s[i] == s[j] correctly for this specific problem?
[ ] Do I handle s[i] != s[j] correctly for this specific problem?
[ ] Did I test with edge cases: empty string, single char, all same chars, all different?
[ ] Is my loop order correct? (length → i → j)?
[ ] Did I avoid the off-by-one error with `j = i + len - 1`?
```

---

## 🎯 Quick Reference: Pattern Recognition

| Problem Type | Key DP State | Base Case |
|--------------|--------------|-----------|
| Longest Palindromic Subsequence | Length | `dp[i][i] = 1` |
| Count Palindromes | Count | `dp[i][i] = 1` |
| Min Cuts to Palindromes | Cuts needed | Combine with palindrome table |
| Min Insertions/Deletions | Operations | Related to longest |


