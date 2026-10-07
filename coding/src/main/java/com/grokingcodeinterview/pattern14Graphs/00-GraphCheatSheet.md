# Graph — Cheat Sheet

## Table of Contents
1. [Definition](#1-definition)
2. [When to Use](#2-when-to-use)
3. [Graph Types](#3-graph-types)
4. [Graph Representation](#4-graph-representation)
5. [Build Graph from Input](#5-build-graph-from-input)
6. [Adjacency Matrix vs List](#6-adjacency-matrix-vs-list)
7. [Key Decisions to Make](#7-key-decisions-to-make)
8. [Common Traps & Mistakes](#8-common-traps--mistakes)
9. [Time & Space Complexity](#9-time--space-complexity)
10. [My Personal Notes](#10-my-personal-notes)
11. [Quick Checklist Before Coding](#-quick-checklist-before-coding)

---

## 1. Definition

**Graph = Nodes (Vertices) + Edges (Connections)**

```
   A
  / \
 B   C
  \ /
   D

Edges: A-B, A-C, B-D, C-D
```

---

## 2. When to Use

**One-Line Trigger:**
> "Can I model this as nodes and edges representing relationships?"

| Problem Signal | Graph Type |
|---|---|
| prerequisites / dependencies | Directed (DAG) |
| shortest path / min steps | Unweighted → BFS |
| cost / distance / weight | Weighted → Dijkstra |
| connected components / provinces | Undirected |
| cycle detection | Directed → 3-color DFS |
| islands / regions in grid | Grid as graph |
| scheduling / ordering | Topological sort |

---

## 3. Graph Types

| Type | Meaning | Use Case |
|---|---|---|
| Undirected | A ↔ B (both ways) | Social networks, islands |
| Directed | A → B (one way) | Prerequisites, task scheduling |
| Weighted | edges have cost | GPS, routing |
| Unweighted | all edges equal | BFS for shortest path |
| Cyclic | contains a loop | General graphs |
| Acyclic (DAG) | no cycles | Topological sort |
| Connected | all nodes reachable | Single component |
| Disconnected | multiple components | Need outer loop in DFS/BFS |

---

## 4. Graph Representation

### Adjacency List (default — use this)
```
0 → [1, 2]
1 → [0, 3]
2 → [0, 3]
3 → [1, 2]
```

### Adjacency Matrix
```
     0  1  2  3
  0  0  1  1  0
  1  1  0  0  1
  2  1  0  0  1
  3  0  1  1  0

matrix[i][j] = 1 → edge exists
matrix[i][j] = 0 → no edge
Symmetric for undirected. NOT symmetric for directed.
```

---

## 5. Build Graph from Input

> **Note:** "Edge list" and "adjacency matrix" are not the same thing — no such term as "edge matrix". Edge list (`int[][] edges`, raw `[u, v]` pairs) is usually the **input** format. Adjacency matrix/list (Section 4) are **built representations** you convert the edge list into below.

### From edge list → adjacency list (most common)
```java
List<Integer>[] adj = new ArrayList[n];
for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

for (int[] edge : edges) {
    int u = edge[0], v = edge[1];
    adj[u].add(v);
    adj[v].add(u);  // remove this line for directed graph
}
```

---

## 6. Adjacency Matrix vs List

| | Matrix | List |
|---|---|---|
| Space | O(V²) | O(V + E) |
| Check edge exists | O(1) | O(degree) |
| Iterate neighbors | O(V) | O(degree) |
| Best for | Dense graphs, O(1) edge check | Everything else |
| Default choice | ❌ | ✅ |

> **Golden Rule:** Default to adjacency list — works for 95% of interview problems.

### Traversal syntax by representation

**Adjacency matrix → iterate neighbors**
```java
// Outer loop — visit every node (handles disconnected graphs)
for (int i = 0; i < n; i++) {
    if (!visited[i]) dfs(i, matrix, visited);
}

void dfs(int i, int[][] matrix, boolean[] visited) {
    visited[i] = true;
    // Don't iterate like a list — scan the row for 1s
    for (int j = 0; j < n; j++) {
        if (matrix[i][j] == 1 && !visited[j]) {
            dfs(j, matrix, visited);
        }
    }
}
```

**Grid as graph (no adj list needed)**
```java
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

for (int[] d : dirs) {
    int nr = r + d[0], nc = c + d[1];
    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
            && !visited[nr][nc] && grid[nr][nc] == '1') {
        // valid neighbor
    }
}
```

---

## 7. Key Decisions to Make

1. **Directed or undirected?** → determines if you add reverse edge
2. **Connected or disconnected?** → need outer loop for disconnected
3. **Weighted or unweighted?** → BFS for shortest path (unweighted), Dijkstra (weighted)
4. **Need cycle detection?** → 3-color DFS (directed), Union-Find or DFS+parent (undirected)
5. **What input format?** → edge list, adj list, adj matrix, or grid?

---

## 8. Common Traps & Mistakes

**Trap 1: Treating adjacency matrix like an adjacency list**
```java
for (int nei : matrix[i])  // ❌ WRONG — iterates values not neighbors
for (int j = 0; j < n; j++) if (matrix[i][j] == 1)  // ✅ correct
```

**Trap 2: Forgetting disconnected graph needs outer loop**
```java
// ❌ Wrong — misses components not reachable from node 0
dfs(0, adj, visited);

// ✅ Correct
for (int i = 0; i < n; i++)
    if (!visited[i]) dfs(i, adj, visited);
```

**Trap 3: Forgetting to add reverse edge for undirected**
```java
adj[u].add(v);           // ❌ directed only
adj[u].add(v);
adj[v].add(u);           // ✅ undirected
```

**Trap 4: Confusing adjacency matrix with grid**
- Matrix → nodes and relationships → scan row for `1`s
- Grid → physical positions → use direction array `dirs`

---

## 9. Time & Space Complexity

| Algorithm | Time | Space |
|---|---|---|
| DFS / BFS | O(V + E) | O(V) |
| Topological Sort | O(V + E) | O(V) |
| Union Find | O(α(N)) ≈ O(1) | O(N) |
| Dijkstra | O((V + E) log V) | O(V) |

---

## 10. My Personal Notes

### edges vs graph — one line:
> **edges** = raw input data &nbsp;|&nbsp; **graph (adj list)** = ready for traversal

### Interview pattern:
```
edges → build adj list → DFS or BFS → solution
```

### Directed cycle detection — which to use:
| Method | When |
|---|---|
| 3-color DFS | Detect cycle during DFS |
| Kahn's BFS | Detect cycle + get topo order in one pass |

### Grid vs adjacency matrix — never confuse:
| | Grid | Adjacency Matrix |
|---|---|---|
| Purpose | Physical positions | Graph relationships |
| Traversal | `dirs` array | Scan row for `1`s |
| Neighbors | Adjacent cells | Nodes where `matrix[i][j] == 1` |

---

## 📋 Quick Checklist Before Coding

- [ ] Directed or undirected? (reverse edge needed?)
- [ ] Connected or disconnected? (outer loop needed?)
- [ ] What input format? (edge list, matrix, grid?)
- [ ] Build adj list first if given edge list
- [ ] Mark visited before recursing / offering to queue
- [ ] Handle empty graph / single node edge cases