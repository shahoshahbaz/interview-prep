# Topological Sort Pattern

## Table of Contents
1. [Definition](#1-definition)
2. [When to Use This Pattern](#2-when-to-use-this-pattern-recognition-signals)
3. [Core Idea (Mental Model)](#3-core-idea-mental-model)
4. [Standard Code Template](#4-standard-code-template-skeleton)
5. [Key Decisions to Make](#5-key-decisions-to-make-interview-gold)
6. [Common Traps & Mistakes](#6-common-traps--mistakes)
7. [Time & Space Complexity](#7-time--space-complexity)
8. [Canonical Problems](#8-canonical-problems-must-know)
9. [Personal Notes](#9-my-personal-notes-critical-section)
10. [Quick Checklist](#-quick-checklist-before-coding)

---

## 1. Definition:

A way to order nodes in a directed graph such that:

**For every edge u → v, u comes before v in the ordering**

✅ **Only works on:**
- Directed graphs
- DAG (Directed Acyclic Graph)

**Real-world analogy:** Tasks with dependencies where task A must complete before task B can start.

---

## 2. When to Use This Pattern (Recognition Signals)

### 🚩 Keywords to Look For:
- "dependencies"
- "prerequisites"
- "order of execution"
- "build order"
- "task scheduling"
- "course schedule"
- "sequence"

### Complexity Hint:
O(V + E) time, O(V) space where V = vertices, E = edges

### One-Line Trigger Thought:
> "Graph problem + dependencies + ordering → **Topological Sort**"

---

## 3. Core Idea (Mental Model)

💡 **The intuition:** "Do things in an order where all dependencies are satisfied first"

**Example:** Course prerequisites
```
1 → 3
2 → 3
```

**Valid order:** [1, 2, 3] or [2, 1, 3]
**Invalid:** [3, 1, 2]  (3 depends on 1 and 2)

**Key insight:** We need to process nodes with NO incoming dependencies first, then gradually resolve their dependents.

---

## 4. Standard Code Template (Skeleton)

### ✅ Approach 1: BFS (Kahn's Algorithm) — ⭐ MOST COMMON

**Idea:**
1. Track in-degree (incoming edges) for each node
2. Start with nodes that have 0 in-degree
3. Process level by level, reducing in-degrees
4. Cycle detection: if result size ≠ number of nodes, cycle exists

```java
public List<Integer> topoSort(int n, int[][] edges) {
    // Initialize
    List<Integer>[] graph = new ArrayList[n];
    int[] inDegree = new int[n];
    
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

    // Build graph and calculate in-degrees
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1];
        graph[u].add(v);
        inDegree[v]++;
    }

    // Start with nodes having 0 in-degree
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }

    // Process level by level
    List<Integer> result = new ArrayList<>();
    while (!queue.isEmpty()) {
        int node = queue.poll();
        result.add(node);

        for (int neighbor : graph[node]) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }

    // Check for cycle
    if (result.size() != n) return new ArrayList<>();
    return result;
}
```

**Why BFS is powerful:**
- ✅ Natural cycle detection
- ✅ Very intuitive (process items ready to go)
- ✅ Easy to explain in interview
- ✅ Pattern-based (Kahn's algorithm is standard)

---

### ✅ Approach 2: DFS (Postorder Traversal)

**Idea:**
1. Go deep first (postorder traversal)
2. Add node to stack AFTER visiting all neighbors
3. Reverse the stack to get topological order
4. Track visiting state for cycle detection

```java
public List<Integer> topoSortDFS(int n, int[][] edges) {
    List<Integer>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

    for (int[] edge : edges) {
        graph[edge[0]].add(edge[1]);
    }

    boolean[] visited = new boolean[n];
    boolean[] visiting = new boolean[n]; // For cycle detection
    Stack<Integer> stack = new Stack<>();

    // Try to visit all nodes
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            if (!dfs(i, graph, visited, visiting, stack)) {
                return new ArrayList<>(); // Cycle detected
            }
        }
    }

    // Reverse to get topological order
    List<Integer> result = new ArrayList<>();
    while (!stack.isEmpty()) result.add(stack.pop());
    return result;
}

private boolean dfs(int node, List<Integer>[] graph,
        boolean[] visited, boolean[] visiting,
        Stack<Integer> stack) {

    visiting[node] = true;

    for (int neighbor : graph[node]) {
        if (visiting[neighbor]) return false; // Back edge = cycle
        if (!visited[neighbor]) {
            if (!dfs(neighbor, graph, visited, visiting, stack)) 
                return false;
        }
    }

    visiting[node] = false; // Done with this node
    visited[node] = true;
    stack.push(node); // Add after visiting all neighbors
    return true;
}
```

---

## 5. Key Decisions to Make (Interview Gold)

| Factor | BFS (Kahn) | DFS |
|--------|-----------|-----|
| **Popularity** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **Cycle detection** | Easy | Trickier (need visiting state) |
| **Code clarity** | Simple | Medium |
| **Interview confidence** | Very high | Medium |
| **Recommended** | ✅ YES (Default) | Only if asked explicitly |

### ✅ **What should YOU use?**

👉 **Default: BFS (Kahn's Algorithm)**

Only use DFS if:
- Interviewer explicitly asks
- You're very comfortable with recursion
- You want to show advanced understanding

---

## 6. Common Traps & Mistakes

❌ **Trap 1: Forgetting cycle check**
- ❌ Wrong: Process all nodes and return immediately
- ✅ Correct: Check if `result.size() == n` before returning

❌ **Trap 2: Not understanding when it works**
- ❌ Wrong: Trying to apply on cyclic graphs
- ✅ Correct: Verify it's a DAG first

❌ **Trap 3: Confusing in-degree vs out-degree**
- ❌ Wrong: Using out-degree to find starting nodes
- ✅ Correct: Start with nodes having **0 in-degree** (no dependencies)

❌ **Trap 4: Not reducing in-degrees correctly**
- ❌ Wrong: Only decrement once
- ✅ Correct: Decrement for EVERY neighbor edge

❌ **Trap 5: DFS mistake - wrong visiting state management**
- ❌ Wrong: Only use `visited` array
- ✅ Correct: Use both `visiting` (for cycle detection) and `visited` arrays

---

## 7. Time & Space Complexity

**Time:** O(V + E)
- V = number of vertices
- E = number of edges
- Must visit each vertex and edge once

**Space:** O(V)
- Graph storage: O(V + E) in adjacency list
- Queue/Stack: O(V) in worst case
- In-degree array: O(V)

**Explain why in one line:**
> BFS processes each node once and examines each edge once, visiting entire graph structure.

---

## 8. Canonical Problems (Must-Know)

### Problem 1: Course Schedule
- **Name:** LeetCode 207 - Course Schedule
- **Key insight:** Detect if any cycle exists in prerequisites (return boolean)
- **Modification:** Don't need the actual order, just cycle detection

### Problem 2: Course Schedule II  
- **Name:** LeetCode 210 - Course Schedule II
- **Key insight:** Return the actual valid ordering
- **Core difference from Problem 1:** Must return the topological order itself

### Problem 3: Alien Dictionary
- **Name:** LeetCode 269 - Alien Dictionary
- **Key insight:** Build graph from character ordering, then do topological sort
- **Twist:** Graph construction from sorted list is the hard part

### Problem 4: Graph Valid Tree / Number of Components
- **Name:** Verify topological sort exists, then count components
- **Key insight:** Often prerequisite before the actual topo sort question

---

## 9. My Personal Notes (Critical Section)

### Mental Shortcut:
> "Topological sort = **In-degree 0 first** (BFS) or **Postorder reverse** (DFS)"

### Interview Phrasing:
> "We use a queue to process nodes level by level, starting with those that have no dependencies. As we process each node, we reduce in-degrees of its neighbors, adding them to queue when their count reaches zero. At the end, we verify all nodes were processed—if not, a cycle exists."

### My Favorite Variation:
> **Kahn's Algorithm with Queue** - Most elegant, feels natural, easiest to explain

### What I Always Forget:
> **The cycle check at the end!** Must verify `result.size() == n`. This catches both cycles and disconnected components.

### Real Interview Experience:
> "Topological sort is less about the algorithm itself and more about recognizing the problem pattern. Once you recognize it's asking for ordering with dependencies, BFS with in-degree is almost mechanical."

---

## 📋 Quick Checklist Before Coding

```
☐ Identified this is a topological sort problem?
☐ Confirmed the graph is directed and acyclic (or need to detect cycle)?
☐ Decided: BFS (Kahn) or DFS? [Default: BFS]
☐ Built adjacency list correctly?
☐ Calculated in-degrees correctly for all nodes?
☐ Started queue with all 0 in-degree nodes?
☐ Decremented in-degrees when removing edges?
☐ Added to queue only when in-degree becomes 0?
☐ Added CYCLE CHECK: result.size() == n ?
☐ Returned empty list if cycle detected?
☐ Tested with example (Course Schedule)?
```

---

## 📚 Algorithm Patterns Reference

For a comprehensive visual reference of all algorithm patterns including Topological Sort, Graph patterns, and more, see:

[Graph & Tree Reference Guide](../common/graph_tree_down_ref.html)
