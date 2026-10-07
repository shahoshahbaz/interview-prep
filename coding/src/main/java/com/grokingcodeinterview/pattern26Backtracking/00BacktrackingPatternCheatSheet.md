#  Backtracking cheat Sheet

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
10. [Quick Checklist Before Coding](#-quick-checklist-before-coding)
11. [Quick Reference - Problem Types](#-quick-reference---problem-types)
12. [Pinterest Senior SWE Priority Tracker](#12-pinterest-senior-swe-priority-tracker)

---

## 1. Definition

**Backtracking** - A systematic exploration technique that tries all possible solutions and abandons paths that don't lead to a valid solution.

---

## 2. When to Use This Pattern (Recognition Signals)

- ✅ Need to **find ALL combinations, permutations, or subsets**
- ✅ Problem asks for **all valid solutions** (not just one)
- ✅ Constraints eliminate branches early (puzzles, sudoku, N-Queens)
- ✅ Problems with **"generate", "find all", "all possible" keywords**
- ✅ **Decision tree problems** where you make a choice, explore, then undo the choice

### Complexity Hint:
Depends on the problem shape — see the corrected table in [Section 7](#7-time--space-complexity). Don't assume it's always `N!`.

### One-Line Trigger Thought:
> "Can I build solutions incrementally by choosing one element at a time, and backtrack if I hit a dead end?"

---

## 3. Core Idea (Mental Model)

**Backtracking = Depth-First Search (DFS) with Pruning**

Think of it like exploring a maze:
1. **Choose** - Pick a path forward
2. **Explore** - Recursively try to solve from this point
3. **Unchoose** - Undo the choice (backtrack) if it doesn't work
4. **Prune** - Skip branches that violate constraints

The key insight: **You build ONE solution path at a time**, add to results when complete, then undo and try another.

---

## 4. Standard Code Template (Skeleton)

**Six shapes. Pick by how the choices are made, not by the problem's name.**

- **Base — array/index** → Loop forward through one shared array by index, collecting completed paths into a result list. *(Subsets, Combinations, Combination Sum)*
- **[4a — Permutations](#4a-permutations-variant-start-from-0-track-used)** → Loop restarts from `0` on every call with a `used[]` flag, because order matters and every unused element is a candidate at every position. *(Permutations)*
- **[4b — Grid DFS](#4b-grid-dfs-skeleton-word-search-n-queens-style)** → Recurse in directions or row-by-row across a 2D board, marking cells visited and restoring them on the way out; usually returns `boolean` to stop at the first valid solution. *(Word Search, N-Queens, Sudoku, Unique Paths III)*
- **[4c — Fixed binary-choice counting](#4c-fixed-binary-choice--counting-skeleton-target-sum-style)** → Exactly two choices per index, and you count valid leaves instead of collecting paths — so there's no path list and no unchoose step. *(Target Sum)*
- **[4d — Per-position candidate set](#4d-per-position-candidate-set--cartesian-product-skeleton-letter-combinations-style)** → The candidate pool is different at every index rather than shrinking from one shared array. *(Letter Combinations)*
- **[4e — Branch-and-bound](#4e-branch-and-bound--minimize-skeleton-find-minimum-time-to-finish-all-jobs-style)** → Track a running best and prune any branch that already can't beat it, instead of collecting or counting every path. *(Optimal Account Balancing, Find Minimum Time to Finish All Jobs, Matchsticks to Square)*

### Base template — array/index shape

The default. Candidates come from one array, the loop starts at `index` and moves forward so earlier elements are never revisited, and each completed path is snapshotted into `result`.

```java
public List<List<Integer>> solve(int[] arr) {
    List<List<Integer>> res = new ArrayList<>();
    backtrack(arr, 0, new ArrayList<>(), res);
    return res;
}

private void backtrack(int[] arr, int start,
                       List<Integer> path, List<List<Integer>> res) {

    if (isComplete(path)) {                   // BASE CASE
        res.add(new ArrayList<>(path));       // snapshot, never the live list
        return;
    }

    for (int i = start; i < arr.length; i++) {
        if (!isValid(path, arr[i])) continue; // PRUNE
        path.add(arr[i]);                     // CHOOSE
        backtrack(arr, i + 1, path, res);     // EXPLORE
        path.remove(path.size() - 1);         // UNCHOOSE
    }
}
```

`isComplete` and `isValid` are the two things you define per problem — usually `path.size() == k`, `sum == target`, or a constraint check. Everything else stays as-is.

**The four lines that matter** — memorize this block, it's the whole pattern:

```java
path.add(arr[i]);                 // choose
backtrack(arr, i + 1, path, res); // explore
path.remove(path.size() - 1);     // unchoose
```

⚠️ **Trap:** every recursive call must pass every parameter the method declares — dropping `res` won't compile, and dropping/mistyping an index silently gives wrong answers.

### 4a. Permutations variant (start from 0, track `used[]`)

Permutations don't move forward by index — every position considers every unused element, so the loop restarts from `0` each call and a `boolean[] used` (or a `Set`) tracks what's already in the path.

```java
private void permute(int[] arr, boolean[] used, List<Integer> path, List<List<Integer>> result) {
    if (path.size() == arr.length) {
        result.add(new ArrayList<>(path));
        return;
    }
    for (int i = 0; i < arr.length; i++) {
        if (used[i]) continue;
        used[i] = true;
        path.add(arr[i]);
        permute(arr, used, path, result);
        path.remove(path.size() - 1);
        used[i] = false;
    }
}
```

### 4b. Grid DFS skeleton (Word Search, N-Queens style)

This is a genuinely different shape from 4/4a: no flat array to loop over with an index. Instead you recurse in directions (grid) or row-by-row (N-Queens), and often return `boolean` to stop at the first valid solution instead of collecting into a `result` list.

```java
private static final int[][] DIRS = {{1,0},{-1,0},{0,1},{0,-1}};

private boolean dfs(char[][] grid, String word, int r, int c, int idx) {
    if (idx == word.length()) return true;
    if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length
        || grid[r][c] != word.charAt(idx)) return false;

    char temp = grid[r][c];
    grid[r][c] = '#';                   // mark visited

    boolean found = false;
    for (int[] d : DIRS) {
        if (dfs(grid, word, r + d[0], c + d[1], idx + 1)) {
            found = true;
            break;                      // stop at first success
        }
    }

    grid[r][c] = temp;                  // unchoose / restore
    return found;
}
```

### 4c. Fixed binary-choice / counting skeleton (Target Sum style)

A third shape: not a loop over candidates (4/4a), not a grid (4b) — a **fixed 2-way decision per index** (assign `+` or `-`, or include/exclude), where you're **counting** valid leaves instead of collecting paths. No `currentPath` list and no explicit unchoose, because nothing mutates shared state — each call just returns an `int` and the caller sums the branches.

```java
private int backtrack(int[] nums, int index, int currentSum, int target) {
    // BASE CASE: used every number — count it only if sum hit target *here*
    if (index == nums.length) {
        return currentSum == target ? 1 : 0;
    }

    int addWays = backtrack(nums, index + 1, currentSum + nums[index], target);      // choose '+'
    int subtractWays = backtrack(nums, index + 1, currentSum - nums[index], target); // choose '-'

    return addWays + subtractWays; // combine both branches' counts
}
```

⚠️ **Trap specific to this shape:** the base case needs `index == nums.length` **AND** `currentSum == target` together — checking the sum alone (without confirming every number got a sign) overcounts, since the running sum can pass through the target value before all numbers are used.

### 4d. Per-position candidate set / Cartesian-product skeleton (Letter Combinations style)

Templates 4/4a assume you're always choosing from the *same* pool (`arr[i..n]`, minus used elements). Some problems give you a **different candidate set at every index** — e.g. digit `2` maps to `"abc"`, digit `3` maps to `"def"`. The shape is still choose → explore → unchoose, but the inner loop iterates over `candidatesFor(index)` instead of `arr`.

```java
private static final String[] MAPPING = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

public List<String> letterCombinations(String digits) {
    List<String> result = new ArrayList<>();
    if (digits.isEmpty()) return result;
    backtrack(digits, 0, new StringBuilder(), result);
    return result;
}

private void backtrack(String digits, int index, StringBuilder current, List<String> result) {
    // BASE CASE: built a letter for every digit
    if (index == digits.length()) {
        result.add(current.toString());
        return;
    }

    String letters = MAPPING[digits.charAt(index) - '0']; // candidate pool CHANGES per index
    for (char c : letters.toCharArray()) {
        current.append(c);                              // choose
        backtrack(digits, index + 1, current, result);   // explore
        current.deleteCharAt(current.length() - 1);      // unchoose
    }
}
```

⚠️ **What to watch for:** always move `index + 1` (no reuse, no "start from same spot") — the candidate set itself already changes each level, so there's no need for an `i+1`-style loop bound like Combinations uses.

### 4e. Branch-and-bound / minimize skeleton (Find Minimum Time to Finish All Jobs style)

Templates 4/4a/4b/4c all either collect every valid path or count every valid leaf. Some problems instead want the **best** (min/max) outcome across all paths — here you track a running "best so far" and prune any branch that already can't beat it, instead of pruning only on hard validity.

```java
private int best = Integer.MAX_VALUE;

public int minimumTimeRequired(int[] jobs, int k) {
    int[] workerLoads = new int[k];
    backtrack(jobs, 0, workerLoads);
    return best;
}

private void backtrack(int[] jobs, int jobIndex, int[] workerLoads) {
    // BASE CASE: every job assigned — update best if this assignment is better
    if (jobIndex == jobs.length) {
        best = Math.min(best, maxLoad(workerLoads));
        return;
    }

    for (int w = 0; w < workerLoads.length; w++) {
        // PRUNE (bound): this branch already can't beat the current best — skip it
        if (workerLoads[w] + jobs[jobIndex] >= best) continue;

        workerLoads[w] += jobs[jobIndex];             // choose
        backtrack(jobs, jobIndex + 1, workerLoads);    // explore
        workerLoads[w] -= jobs[jobIndex];              // unchoose

        // PRUNE (symmetry): worker w was empty and we just tried it — trying another empty worker is redundant
        if (workerLoads[w] == 0) break;
    }
}

private int maxLoad(int[] loads) {
    int m = 0;
    for (int v : loads) m = Math.max(m, v);
    return m;
}
```

⚠️ **What's different here vs. the other templates:** the "prune" step isn't just "is this choice valid?" — it's "can this choice possibly beat what I've already found?" That means the order you explore branches in matters (a good early `best` prunes more), which never mattered in templates 4/4a/4b/4c.

---

## 5. Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently.**

1. **What is the "base case"?** When is a solution complete?
   - All elements used? Specific sum reached? Target string formed?

2. **What are "valid choices"** at each step?
   - All remaining elements? Only unused elements? Elements that satisfy constraint?

3. **What constraints prune branches?**
   - Avoid duplicates? Skip used elements? Check sum/condition?

4. **Should I start from scratch each recursion or move forward?**
   - **Permutations**: Start from 0 each time (mark used via boolean array)
   - **Combinations**: Pass index, move forward only (`i + 1`)
   - **Combination Sum (reuse allowed)**: Move forward with `i`, not `i + 1` — same element can be picked again
   - **Subsets**: Include/exclude pattern or index-based

5. **Do I need to track "used"?**
   - Use boolean array for permutations
   - Use index for combinations
   - Use include/exclude for subsets

---

## 6. Common Traps & Mistakes

❌ **Trap 1: Forgot to add COPY to result**
```java
result.add(currentPath);                    // ❌ WRONG - same reference!
result.add(new ArrayList<>(currentPath));   // ✅ CORRECT
```

❌ **Trap 2: Forgot to backtrack (undo the choice)**
```java
currentPath.add(choice);
backtrack(...);
// ❌ Missing: currentPath.remove(...)
```

❌ **Trap 3: Wrong base case or infinite recursion**
- Make sure recursion terminates
- Check boundary conditions

❌ **Trap 4: Not pruning - timeout**
- Always add early exit conditions when possible
- Check constraints BEFORE recursing

❌ **Trap 5: Duplicate results**
- Sort input first if duplicates exist
- Skip duplicate choices in loop:
```java
if (i > start && arr[i] == arr[i - 1]) continue; // requires sorted input
```

❌ **Trap 6: Off-by-one in loop conditions**
- `i < arr.length` for all elements
- `i + 1` when moving forward without reuse (Combinations)
- `i` (not `i + 1`) when reuse is allowed (Combination Sum)

❌ **Trap 7: Missing an argument in the recursive call**
- Every parameter the method declares must be passed on every call — including `result`. A silently-missing collection argument is a compile error, but a silently-wrong index/count argument just gives wrong answers.

---

## 7. Time & Space Complexity

Don't apply one blanket formula — it depends on the problem shape:

| Pattern | Time |
|---|---|
| Permutations | O(N! × N) |
| Subsets | O(2ᴺ × N) |
| Combinations (choose k) | O(C(n,k) × k) |
| Combination Sum (reuse allowed) | depends on target/candidates — no simple closed form, bounded by depth × branching |
| Grid DFS (Word Search) | O(N × M × 4ᴸ) where L = word length |

**Space:** O(N) recursion depth + O(N) call stack (excluding result storage)

**Explain why in one line:**
> Backtracking explores the decision tree for the given problem shape (permutation/combination/subset/grid), and the depth is bounded by input size.

---

## 8. Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

**Problem 1: Permutations**
- Name: LeetCode 46 - Permutations
- Key insight: Use boolean `used[]` to track which elements are in current path. Start from index 0 each time.
- Pattern: Include/Exclude with tracking

**Problem 2: Combinations**
- Name: LeetCode 77 - Combinations
- Key insight: Pass `index` to only explore forward. No need to track used.
- Pattern: Start-from-index (no revisiting)

**Problem 3: Subsets**
- Name: LeetCode 78 - Subsets
- Key insight: Include every intermediate state as a valid solution. At each step, decide to include or exclude.
- Pattern: Binary (include/exclude) decision

**Problem 4: N-Queens**
- Name: LeetCode 51 - N-Queens
- Key insight: Place one queen per row, track columns and diagonals to avoid conflicts.
- Pattern: Constraint-based pruning

**Problem 5: Word Search**
- Name: LeetCode 79 - Word Search
- Key insight: Backtrack on 2D grid, mark visited cells, restore them on backtrack.
- Pattern: 2D grid traversal with state restoration

**Problem 6: Combination Sum**
- Name: LeetCode 39 - Combination Sum
- Key insight: Elements can be reused — recurse with `i`, not `i + 1`.
- Pattern: Start-from-index with reuse

**Problem 7: Target Sum**
- Name: LeetCode 494 - Target Sum
- Key insight: Fixed 2-way choice per index (`+`/`-`), no candidate loop. Base case needs `index==n` AND `sum==target` together, not sum alone. Returns a count (`int`), not a `result` list — no explicit unchoose needed since there's no shared mutable state.
- Pattern: Fixed binary-choice counting (see [4c](#4c-fixed-binary-choice--counting-skeleton-target-sum-style))

---

## 9. My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> "Backtracking = DFS + Undo. Choose → Explore → Unchoose."

### Interview Phrasing:
> "I'll use backtracking to systematically explore all valid combinations. At each step, I choose a candidate, recurse to build the solution, then undo the choice to try alternatives."

### My Favorite Variation:
> **The "choices" pattern**: Instead of looping through candidates, think of each recursive call as making ONE choice from available options. This makes pruning intuitive.

### What I Always Forget:
> - **Copy when adding to result!** `new ArrayList<>(path)`
> - **Undo the choice!** `path.remove(path.size() - 1)`
> - **Prune early!** Check constraints before recursing, not after
> - **Sort input first** if you need to skip duplicates
> - **Pass every parameter** on every recursive call (esp. `result`)
> - **Reuse vs no-reuse**: `i` vs `i + 1` in the loop's recursive call

### Interview Tips:
> - Start with a simple example (3 elements)
> - Draw the recursion tree to see structure
> - Clearly state your base case and pruning strategy
> - Practice one permutation, one combination problem before interview
> - Time complexity = number of solutions × work per solution

---

## 📋 Quick Checklist Before Coding

```
☐ Identified the problem as backtracking (all solutions, combinatorial)
☐ Wrote down: What is "complete solution"?
☐ Wrote down: What are valid "choices" at each step?
☐ Identified: Do I need a "used" array/set?
☐ Clear base case: path.size() == target OR other condition
☐ Pruning logic: When to skip a branch?
☐ Decided: reuse allowed (i) or not (i + 1)?
☐ ✅ Will add COPY to result list
☐ ✅ Will UNDO the choice (backtrack)
☐ ✅ Every recursive call passes every parameter
☐ ✅ Traced through with small example
☐ ✅ Tested edge cases (empty input, single element)
```

---

## 🎯 Quick Reference - Problem Types

| Problem Type | Loop Strategy | Track Used? | Base Case | Time |
|---|---|---|---|---|
| **Permutations** | `i=0 to n` | Boolean[] | `path.size()==n` | O(N!×N) |
| **Combinations** | `i=start to n`, recurse `i+1` | No | `path.size()==k` | O(C(n,k)×k) |
| **Combination Sum** | `i=start to n`, recurse `i` (reuse) | No | `sum==target` | varies |
| **Subsets** | Include/Exclude | No | Every state valid | O(2ᴺ×N) |
| **Partition** | `i=start to n` | No | `sum==target` | varies |
| **Word Search** | 4 directions | Visited marker | `word found` | O(NM×4ᴸ) |
| **Target Sum** | fixed 2 choices (+/-) per index | No | `index==n && sum==target` | O(2ᴺ) |
| **Letter Combinations** | candidate set changes per index | No | `index==digits.length` | O(4ᴺ×N) worst case |
| **Min/Max backtracking** (e.g. job scheduling) | `i=0 to k` (slots/workers) | No | all items assigned | varies — bound by branch-and-bound prune |

---

## 12. Pinterest Senior SWE Priority Tracker

Based on real candidate-report data pulled from a third-party interview-question aggregator (paraphrased titles, not an official Pinterest source — treat confidence column accordingly).

| Problem | Template | Evidence at Pinterest | Confidence | Priority | Status |
|---|---|---|---|---|---|
| **Combination Sum** | 4 | "Generate All Hyperparameter Combinations" (MLE, 120k solved) | Likely match | Tier 2 | ✅ Done |
| **Optimal Account Balancing** | 4e | Recurs 4x under different names — incl. "Settle Group Expenses with Transfers" tagged **SWE Senior+** | High | **Tier 1 — top priority** | ✅ Done |
| **Target Sum** | 4c | "Decide Target via Subsequence Plus/Multiply Expression" (SWE, 120k solved) | Likely match | Tier 1 | ✅ Done |
| **Sudoku Solver** | 4b | "Solve a 9x9 Sudoku puzzle" (MLE, Hard, 11k solved) | Confirmed | Tier 1 | Not started |
| **Minimize Result by Adding Parentheses to Expression** | 4e hybrid* | "Insert Parentheses to Minimize Expression Value" (MLE, Hard, 61k solved) | Confirmed | Tier 2 | ✅ Done |
| **Expression Add Operators** | 4 extended | Possibly bundled in "Solve Expression and Tree-List Problems" (**MLE Senior+**) | Low | Tier 3 | Not started |
| **Word Search** | 4b | Possibly bundled in "Solve Two Grid Search Problems" (SWE) | Low | Tier 3 | ✅ Done |
| **Factor Combinations** | 4 | No evidence found | None | Tier 4 | Not started |
| **Split a String Into the Max Number of Unique Substrings** | 4e hybrid | No evidence found | None | Tier 4 | Not started |
| **Letter Combinations of a Phone Number** | 4d | No evidence found | None | Tier 4 | Not started |
| **Unique Paths III** | 4b | No evidence found | None | Tier 4 | Not started |
| **Find Minimum Time to Finish All Jobs** | 4e | No evidence found | None | Tier 4 | Not started |

\* **Fixed depth vs. variable depth.** Minimize Result uses the 4e *framing* (enumerate all choices, score each, keep running best) but needs no recursion — you make exactly **2** choices (where `(` goes, where `)` goes), so it collapses into 2 nested loops with no unchoose step. The tell: nothing mutates, so nothing needs restoring.

> **Rule:** variable depth (unknown until runtime) → recursion required. Fixed, small depth → nested loops suffice.
> Target Sum recurses because depth is `nums.length`. Minimize Result doesn't because depth is always 2.
> If it asked for **k** parentheses pairs, you couldn't write `k` loops at compile time → real backtracking.

**Interview line if asked "why no backtracking here?":**
> "The decision space is exactly two choices, so recursion adds stack depth without adding expressiveness. If it were k parentheses pairs, I'd need the recursive version."