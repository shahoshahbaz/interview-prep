# Depth-First Search (DFS) in Graph

## Table of Contents
1. [Definition](#1-definition)
2. [When to Use This Pattern](#2-when-to-use-this-pattern-recognition-signals)
3. [Core Idea (Mental Model)](#3-core-idea-mental-model)
4. [Standard Code Template](#4-standard-code-template-skeleton)
5. [Key Decisions to Make](#5-key-decisions-to-make-interview-gold)
6. [Common Traps & Mistakes](#6-common-traps--mistakes)
7. [Time & Space Complexity](#7-time--space-complexity)
8. [Canonical Problems](#8-canonical-problems-must-know)
9. [My Personal Notes](#9-my-personal-notes-critical-section)
10. [Quick Checklist Before Coding](#-quick-checklist-before-coding)

---

## 1. Definition

Depth-First Search is a graph traversal algorithm that explores as far as possible along each branch before backtracking. It uses **recursion** (or an explicit stack) to maintain the exploration state.

**Key difference from BFS:**
- DFS → goes deep first (stack / recursion)
- BFS → goes wide first (queue)
- DFS explores all paths. BFS guarantees shortest path in unweighted graphs.

---

## 2. When to Use This Pattern (Recognition Signals)

### Complexity Hint:
- Exploring all nodes/paths in a graph
- Detecting cycles
- Finding connected components
- Topological sorting
- Backtracking problems

### One-Line Trigger Thought:
> "If the problem asks to explore deeply or find all paths — DFS is your answer."

**Common Interview Keywords:**
- explore, traverse, visit all nodes
- connected components, provinces, clusters, islands, regions
- find path, all paths, backtracking
- cycle detection, topological sort, dependency graph

---

## 3. Core Idea (Mental Model)

**DFS explores: go deep → go deeper → backtrack**

```
Graph:
0 — 1 — 3
|
2 — 4

DFS from 0: 0 → 1 → 3 → back → 2 → 4
```

**Key Insight:** DFS is used for structure problems (exploring all paths), while BFS is used for shortest path problems.

---

## 4. Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

### Recursive DFS (Single Component)
```java
void dfs(int node, List<Integer>[] adj, boolean[] visited) {
    visited[node] = true;

    for (int neighbor : adj[node]) {
        if (!visited[neighbor]) {
            dfs(neighbor, adj, visited);
        }
    }
}

// Driver
boolean[] visited = new boolean[n];
dfs(start, adj, visited);
```

### DFS for Disconnected Graph
```java
boolean[] visited = new boolean[n];

for (int i = 0; i < n; i++) {
    if (!visited[i]) {
        dfs(i, adj, visited);  // each call = one component
    }
}
```

### DFS with Return Value (e.g. find path)
```java
boolean dfs(int node, int target, List<Integer>[] adj, boolean[] visited) {
    if (node == target) return true;
    visited[node] = true;

    for (int neighbor : adj[node]) {
        if (!visited[neighbor])
            if (dfs(neighbor, target, adj, visited)) return true;
    }
    return false;
}
```

### DFS on Grid (Matrix)
```java
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
boolean[][] visited = new boolean[rows][cols];

void dfs(int r, int c, char[][] grid) {
    if (r < 0 || r >= rows || c < 0 || c >= cols) return;
    if (visited[r][c] || grid[r][c] == '0') return;

    visited[r][c] = true;
    for (int[] d : dirs)
        dfs(r + d[0], c + d[1], grid);
}
```

### 3-Color DFS (Cycle Detection in Directed Graph)
```java
// 0 = WHITE, 1 = GREY (on stack), 2 = BLACK (done)
int[] color = new int[n];

boolean hasCycle(int node, List<Integer>[] adj) {
    color[node] = 1;  // GREY = currently on stack
    for (int neighbor : adj[node]) {
        if (color[neighbor] == 1) return true;  // back edge = cycle
        if (color[neighbor] == 0 && hasCycle(neighbor, adj)) return true;
    }
    color[node] = 2;  // BLACK = fully explored
    return false;
}
```

---

## 5. Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently:**

1. Is the graph connected or disconnected? (need outer loop?)
2. Do I need to detect cycles? (use 3-color instead of boolean visited)
3. What am I returning? (boolean, count, path?)
4. Should I process before or after recursing? (pre-order vs post-order)
5. Is this a grid problem? (use direction array, no adj list needed)

---

## 6. Common Traps & Mistakes

- **Trap 1:** Forgetting to mark visited → infinite recursion on cycles
- **Trap 2:** Assuming graph is connected → missing entire components
- **Trap 3:** Not returning recursive result → `if (dfs(...))` not `dfs(...)`
- **Trap 4:** Using 2-color visited for directed cycle detection → use 3-color (WHITE/GREY/BLACK)
- **Trap 5:** Forgetting bounds check in grid DFS → ArrayIndexOutOfBoundsException

---

## 7. Time & Space Complexity

**Time:** O(V + E)
- Each node is visited once
- Each edge is explored once

**Space:** O(V)
- Visited array: O(V)
- Recursion stack: O(V) worst case (linear graph)

**Explain why in one line:**
> DFS visits each vertex and edge exactly once, and the call stack depth is proportional to the longest path in the graph.

---

## 8. Canonical Problems (Must-Know)

**Problem 1:**
- Name: Number of Islands (Easy)
- Key insight: DFS from each unvisited land cell, mark entire island visited, count calls

**Problem 2:**
- Name: Number of Provinces / Connected Components (Medium)
- Key insight: Run DFS from every unvisited node — each call = one component

**Problem 3:**
- Name: Find Eventual Safe States (Medium)
- Key insight: 3-color DFS — nodes that are NOT part of a cycle are safe

**Problem 4:**
- Name: Course Schedule / Topological Sort (Medium)
- Key insight: Post-order DFS — process node after all neighbors are fully explored

---

## 9. My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> "Mark `visited[node] = true` BEFORE recursing — prevents infinite loops."

### DFS vs BFS — one line rule:
> **Explore all paths / cycle detection → DFS. Shortest path / minimum steps → BFS.**

### Interview Phrasing:
> "We use DFS to recursively explore each unvisited neighbor, marking visited nodes to avoid cycles."

### What I Always Forget:
> Two things: checking for disconnected graph (need outer loop), and returning the recursive result (`if (dfs(...)) return true` not just `dfs(...)`).

### 3-Color vs 2-Color:
> For **undirected** graphs — boolean visited[] is enough.
> For **directed** graphs — need 3-color to distinguish "currently on stack" (GREY) from "fully done" (BLACK).

---

## 📋 Quick Checklist Before Coding

- [ ] Is the graph connected or disconnected? (need outer loop?)
- [ ] Do I need visited[]? (almost always YES)
- [ ] Directed or undirected? (directed → consider 3-color for cycle detection)
- [ ] What am I returning? (count, boolean, path?)
- [ ] Am I returning the recursive result? (`if (dfs(...)) return true`)
- [ ] Is this a grid? (use dirs array, bounds check first)
- [ ] Handle empty graph / single node edge cases