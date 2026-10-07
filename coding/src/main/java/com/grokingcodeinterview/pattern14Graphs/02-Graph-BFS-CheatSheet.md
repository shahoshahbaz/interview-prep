# Breadth-First Search (BFS) in Graph

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

Breadth-First Search is a graph traversal algorithm that explores all neighbors at the current depth before moving to the next level. It uses a **Queue** (FIFO) to maintain the exploration order.

**Key difference from DFS:**
- DFS → goes deep first (stack / recursion)
- BFS → goes wide first (queue)
- BFS guarantees **shortest path** in unweighted graphs. DFS does not.

---

## 2. When to Use This Pattern (Recognition Signals)

### Complexity Hint:
- Finding shortest path (unweighted graph)
- Level-by-level traversal
- Minimum steps / hops to reach a target
- Spreading problems (fire, infection, water flow)

### One-Line Trigger Thought:
> "If the problem asks for the **minimum** number of steps, moves, or hops — BFS is your answer."

**Common Interview Keywords:**
- shortest path, minimum steps, minimum distance
- level by level, layer by layer
- nearest, closest, fewest moves
- spread, infection, flood fill
- reachable in k steps

---

## 3. Core Idea (Mental Model)

**BFS explores: all neighbors first → then their neighbors → level by level**

```
Graph:
0 — 1 — 3
|
2 — 4

BFS from 0: 0 → 1, 2 → 3, 4
Level 0: [0]
Level 1: [1, 2]
Level 2: [3, 4]
```

**Key Insight:** BFS finds the shortest path because it always explores closer nodes before farther ones.

---

## 4. Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

### Basic BFS (Single Source)
```java
void bfs(int start, List<Integer>[] adj, boolean[] visited) {
    Queue<Integer> queue = new LinkedList<>();
    visited[start] = true;
    queue.offer(start);

    while (!queue.isEmpty()) {
        int node = queue.poll();

        for (int neighbor : adj[node]) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;  // mark BEFORE offering — not after polling
                queue.offer(neighbor);
            }
        }
    }
}
```

### BFS with Shortest Distance
```java
int[] dist = new int[n];
Arrays.fill(dist, -1);
dist[src] = 0;
Queue<Integer> queue = new LinkedList<>();
queue.offer(src);

while (!queue.isEmpty()) {
    int node = queue.poll();
    for (int neighbor : adj[node]) {
        if (dist[neighbor] == -1) {
            dist[neighbor] = dist[node] + 1;
            queue.offer(neighbor);
        }
    }
}
// dist[target] = shortest path, -1 = unreachable
```

### BFS for Disconnected Graph
```java
boolean[] visited = new boolean[n];

for (int i = 0; i < n; i++) {
    if (!visited[i]) {
        bfs(i, adj, visited);  // each call = one component
    }
}
```

### BFS on Grid (Matrix)
```java
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};  // 4 directions
boolean[][] visited = new boolean[rows][cols];
Queue<int[]> queue = new LinkedList<>();
queue.offer(new int[]{startRow, startCol});
visited[startRow][startCol] = true;

while (!queue.isEmpty()) {
    int[] curr = queue.poll();
    int r = curr[0], c = curr[1];

    for (int[] d : dirs) {
        int nr = r + d[0];
        int nc = c + d[1];
        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                && !visited[nr][nc] && grid[nr][nc] == '1') {
            visited[nr][nc] = true;
            queue.offer(new int[]{nr, nc});
        }
    }
}
```

---

## 5. Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently:**

1. Is the graph connected or disconnected? (need the outer loop?)
2. Do I need shortest distance? (use `dist[]` array instead of `visited[]`)
3. Is this a grid problem? (use direction array `dirs`, no adj list needed)
4. Do I need to track level boundaries? (use `int levelSize = queue.size()`)
5. What am I returning? (distance, boolean, count, path?)

---

## 6. Common Traps & Mistakes

- **Trap 1:** Marking visited **after** polling instead of **before** offering → same node gets added to queue multiple times
- **Trap 2:** Assuming graph is connected → missing entire components
- **Trap 3:** Forgetting bounds check in grid BFS → ArrayIndexOutOfBoundsException
- **Trap 4:** Using DFS when problem asks for **minimum** → DFS does not guarantee shortest path
- **Trap 5:** Not initializing `dist[]` to -1 → can't distinguish unvisited from distance-0 node

---

## 7. Time & Space Complexity

**Time:** O(V + E)
- Each node is enqueued and dequeued once
- Each edge is checked once

**Space:** O(V)
- Queue holds at most O(V) nodes
- visited / dist array: O(V)
- Grid BFS: O(rows × cols)

**Explain why in one line:**
> BFS visits each vertex and edge exactly once using a queue, so time is O(V+E) and space is proportional to the number of nodes in the queue.

---

## 8. Canonical Problems (Must-Know)

**Problem 1:**
- Name: Find if Path Exists in Graph (Easy)
- Key insight: BFS from src, return true if target is reached

**Problem 2:**
- Name: Number of Islands (Easy)
- Key insight: BFS from each unvisited land cell, mark entire island visited, count calls

**Problem 3:**
- Name: Shortest Path in Binary Matrix (Medium)
- Key insight: BFS guarantees shortest path — track level or dist[] for step count

**Problem 4:**
- Name: Number of Provinces / Connected Components (Medium)
- Key insight: BFS from every unvisited node — each call = one province

---

## 9. My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> "Mark visited BEFORE offering to queue — not after polling. Otherwise the same node gets added multiple times."

### BFS vs DFS — one line rule:
> **Shortest path / minimum steps → BFS. Explore all paths / cycle detection → DFS.**

### Interview Phrasing:
> "We use BFS because it explores nodes level by level, guaranteeing the shortest path in an unweighted graph."

### What I Always Forget:
> In grid BFS — the bounds check must come BEFORE accessing `grid[nr][nc]`, otherwise you get an out-of-bounds exception.

### Key difference from Tree BFS:
> Graph BFS needs `visited[]` to prevent revisiting. Tree BFS does not — trees are acyclic.

---

## 📋 Quick Checklist Before Coding

- [ ] Is the graph connected or disconnected? (need outer loop?)
- [ ] Do I need shortest path? (use dist[] not just visited[])
- [ ] Is this a grid? (use dirs array, no adj list needed)
- [ ] Mark visited BEFORE offering to queue
- [ ] Bounds check in grid: row/col in range AND not visited AND valid cell value
- [ ] Handle empty graph / single node edge cases