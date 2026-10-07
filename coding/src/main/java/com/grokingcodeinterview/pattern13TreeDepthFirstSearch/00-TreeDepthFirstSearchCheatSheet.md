# Tree DFS — Cheat Sheet

## Table of Contents
1. [Definition](#1-definition)
2. [When to Use](#2-when-to-use)
3. [Core Idea](#3-core-idea)
4. [Standard Code Templates](#4-standard-code-templates)
5. [Key Decisions to Make](#5-key-decisions-to-make)
6. [Common Traps & Mistakes](#6-common-traps--mistakes)
7. [Time & Space Complexity](#7-time--space-complexity)
8. [Canonical Problems](#8-canonical-problems)
9. [My Personal Notes](#9-my-personal-notes)
10. [Quick Checklist Before Coding](#-quick-checklist-before-coding)

---

## 1. Definition

Tree DFS explores as far as possible down each branch before backtracking. Uses **recursion** (call stack) instead of a queue.

**Key difference from Graph DFS:**
- No `visited[]` needed — trees are acyclic
- Base case is `node == null` not a visited check

---

## 2. When to Use

**One-Line Trigger:**
> "I need to explore each branch completely and track the path along the way."

| Problem Signal | Use |
|---|---|
| "all paths from root to leaf" | DFS + backtrack |
| "path sum / path exists" | DFS + carry sum down |
| "count paths with target sum" | DFS + return count |
| "max / min path sum" | DFS + return value |
| "diameter of tree" | DFS post-order |
| "serialize / deserialize" | DFS pre-order |
| "validate BST" | DFS + pass bounds |

---

## 3. Core Idea

**Three traversal orders:**
```
Pre-order:  process → left → right   (path problems)
In-order:   left → process → right   (BST problems)
Post-order: left → right → process   (height, diameter, LCA)
```

**Two DFS styles — pick one:**

```
Style 1: Backtracking
  add node → recurse → remove node
  Used for: collecting all paths

Style 2: Return-based
  recurse left → recurse right → combine
  Used for: sum, count, max/min, properties
```

---

## 4. Standard Code Templates

### Style 1 — Backtracking (collect all paths)
```java
void dfs(TreeNode node, List<Integer> path, List<List<Integer>> result) {
    if (node == null) return;

    path.add(node.val);                          // add

    if (node.left == null && node.right == null)
        result.add(new ArrayList<>(path));       // copy — not reference!

    dfs(node.left,  path, result);
    dfs(node.right, path, result);

    path.remove(path.size() - 1);               // backtrack
}
```

### Style 2 — Return-based (sum / count / max)
```java
int dfs(TreeNode node) {
    if (node == null) return 0;                 // base case

    int left  = dfs(node.left);
    int right = dfs(node.right);

    return 1 + Math.max(left, right);           // combine & return
}
```

### Style 3 — Carry value down (path sum check)
```java
boolean dfs(TreeNode node, int remaining) {
    if (node == null) return false;

    remaining -= node.val;

    if (node.left == null && node.right == null)
        return remaining == 0;                  // leaf check

    return dfs(node.left, remaining)
        || dfs(node.right, remaining);
}
```

---

## 5. Key Decisions to Make

1. **Pre / In / Post order?** — path problems → pre, BST → in, height/LCA → post
2. **Backtracking needed?** → yes if building a reusable path list
3. **Copy or reference?** → always `new ArrayList<>(path)` when storing
4. **Return value or collect in list?** → sum/count/max → return; all paths → collect
5. **Leaf condition?** → `node.left == null && node.right == null`

---

## 6. Common Traps & Mistakes

**Trap 1: Storing reference instead of copy**
```java
result.add(path);                    // ❌ stores reference — path changes later
result.add(new ArrayList<>(path));   // ✅ stores a copy
```

**Trap 2: Forgetting to backtrack**
```java
path.add(node.val);
dfs(node.left, path, result);
dfs(node.right, path, result);
// ❌ missing: path.remove(path.size() - 1)
```

**Trap 3: Wrong leaf check**
```java
if (node == null) // ❌ null is not a leaf
if (node.left == null && node.right == null) // ✅ both children null = leaf
```

**Trap 4: Backtracking too early**
```java
path.remove(path.size() - 1); // ❌ before recursing on children
dfs(node.left, path, result);
```

---

## 7. Time & Space Complexity

**Time:** O(n) — visit each node once
**Space:** O(h) — recursion stack depth = tree height
- Balanced tree: O(log n)
- Skewed tree: O(n)
- Plus O(n) output space if storing all paths

---

## 8. Canonical Problems

| Problem | Style | Key Insight |
|---|---|---|
| All root-to-leaf paths | Backtrack | Copy path at leaf |
| Path sum exists | Carry down | Subtract at each node, check at leaf |
| Count paths for sum | Return-based | Count from left + right |
| Tree diameter | Post-order return | diameter = left height + right height |
| Max path sum | Post-order return | Track global max, return single side |

---

## 9. My Personal Notes

### Two styles in one line:
> **Backtrack** = add → recurse → remove (path problems)
> **Return-based** = recurse left + right → combine (value problems)

### What I always forget:
> 1. `new ArrayList<>(path)` — copy not reference
> 2. `path.remove(path.size() - 1)` — backtrack after BOTH children
> 3. Leaf = both children null, NOT node == null

### Post-order pattern for diameter / height:
```java
int left  = dfs(node.left);   // ask left
int right = dfs(node.right);  // ask right
max = Math.max(max, left + right); // update global
return 1 + Math.max(left, right);  // return height
```

---

## 📋 Quick Checklist Before Coding

- [ ] Which style? Backtrack or return-based?
- [ ] Which order? Pre / In / Post?
- [ ] Storing path? Use `new ArrayList<>(path)` not reference
- [ ] Backtracking? Remove after BOTH recursive calls
- [ ] Leaf condition: `left == null && right == null`
- [ ] Null check at top: `if (node == null) return ...`