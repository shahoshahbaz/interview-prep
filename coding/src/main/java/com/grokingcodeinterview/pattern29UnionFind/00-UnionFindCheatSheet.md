# Union-Find (Disjoint Set Union) Pattern — Cheat Sheet

---

## 1. Definition

**Union-Find** is a data structure that efficiently maintains a collection of disjoint (non-overlapping) sets and supports two main operations:

- **Union (merge):** Merge two sets into one
- **Find (query):** Determine which set an element belongs to, or check if two elements are in the same set

**Also known as:** Disjoint Set Union (DSU), Merge-Find Set

---

## 2. When to Use This Pattern (Recognition Signals)

✅ **Use when you see:**
- "Are nodes connected?"
- "Find connected components"
- "Check if adding edge creates a cycle"
- "Count number of connected components"
- "Provinces / friend circles / islands with coordinates"
- "Minimum spanning tree (Kruskal's algorithm)"
- "Detect cycles in undirected graphs"
- "Equivalence classes"

### Complexity Hint:

With path compression + union by rank: **O(α(n))** ≈ **O(1)** amortized
- Without optimizations: **O(log n)**

### One-Line Trigger Thought:

> "If the problem is about grouping/connectivity and I need fast membership queries, use Union-Find."

---

## 3. Core Idea (Mental Model)

**Imagine a forest of trees where each node points to its parent. The root of the tree represents the "representative" or "group leader."**

```
Before union:        After union(1, 3):
Nodes: 0 1 2 3       0  1-3
                     |  |
Each is its own       2
component
```

**Two key insights:**
1. **Find:** Follow parent pointers until you reach the root (group representative)
2. **Union:** Connect the roots of two components

**Rule:** If you can't explain it simply, you don't understand it yet.

---

## 4. Standard Code Template (Skeleton)

### Basic Implementation

```java
class UnionFind {
    int[] parent;
    int[] rank;
    
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        
        // Each node is its own parent initially
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }
    
    // Find with path compression
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }
    
    // Union with rank
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) return;  // Already in same set
        
        // Union by rank: attach smaller tree under larger
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
    
    // Check if two nodes are connected
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}
```

### Usage Example

```java
UnionFind uf = new UnionFind(5);
uf.union(0, 1);
uf.union(1, 2);
uf.union(3, 4);

System.out.println(uf.connected(0, 2));  // true (0-1-2)
System.out.println(uf.connected(0, 3));  // false (separate groups)
```

⚠️ **Important:** Keep this structure. Only modify if needed for specific problems.

---

## 5. Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently.**

### Decision 1: Do I need Path Compression?
- **Yes (Always!)** — Dramatically speeds up future finds
- Code: `parent[x] = find(parent[x]);` in find method

### Decision 2: Do I need Union by Rank?
- **Yes (Recommended)** — Keeps tree balanced, O(log n) becomes O(α(n))
- Alternative: Union by Size (also works, slightly less elegant)

### Decision 3: What problem am I solving?

| Problem Type | Approach | Extra Tracking |
|---|---|---|
| Connected Components | Union all edges, count roots | Count roots at end |
| Cycle Detection | Try to union; if already connected, cycle exists | Check before union |
| Minimum Spanning Tree (Kruskal) | Sort edges by weight, union greedily | Stop when components = 1 |
| Islands / Coordinates | Convert coordinates to indices, then union | Convert (r,c) → r*cols+c |

### Decision 4: Index Conversion for Grids?

For 2D grids, convert (row, col) to 1D index:
```java
int index = row * cols + col;
```

Reverse (optional):
```java
int row = index / cols;
int col = index % cols;
```

---

## 6. Common Traps & Mistakes

👉 **Tip:** This section gets more valuable over time as you add your personal pitfalls.

| Mistake | Impact | Fix |
|---------|--------|-----|
| ❌ Forgetting path compression | Inefficient; becomes O(n) | Add `parent[x] = find(parent[x])` |
| ❌ Not initializing parent array | NullPointerException | Set `parent[i] = i` for all i |
| ❌ Comparing roots incorrectly | Wrong connectivity | Always compare `find(x)` and `find(y)` |
| ❌ Union both ways in undirected | Unnecessary (one direction enough) | Only do `parent[rootX] = rootY` |
| ❌ Forgetting to track root count | Wrong component count | Count roots remaining (elements with parent[i] == i) |
| ❌ Not handling edge cases | Crash on empty input | Check n > 0 at start |

---

## 7. Time & Space Complexity

**Time:**
- `find(x)`: **O(α(n))** ≈ **O(1)** with path compression + union by rank
  - Without optimizations: O(log n)
- `union(x, y)`: **O(α(n))** ≈ **O(1)** amortized
- N unions + M finds: **O((N + M) × α(n))**

**Space:** **O(n)**
- Parent array: O(n)
- Rank array: O(n)

**Explain why in one line:**
> Path compression and union by rank make most operations nearly constant time, and we only store parent/rank pointers for each element.

---

## 8. Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

### Problem 1: Number of Connected Components
- **Pattern:** Union all edges, then count nodes where `parent[i] == i`
- **Key insight:** Each component has exactly one root (where parent[i] == i)
- **Example:** Edges [[0,1],[1,2],[3,4]] → 2 components

### Problem 2: Redundant Connection (Cycle Detection)
- **Pattern:** Process edges one by one; if nodes already connected, this edge is redundant
- **Key insight:** `if (find(u) == find(v)) return [u, v];` — this edge creates the cycle
- **Example:** Edges [[1,2],[1,3],[2,3]] → return [2,3] or [1,3]

### Problem 3: Number of Provinces (Graph as Adjacency Matrix)
- **Pattern:** Scan matrix; if connected, union nodes; count final components
- **Twist:** Input is matrix, not edge list; need to scan diagonal pairs
- **Example:** `[[1,1,0],[1,1,0],[0,0,1]]` → 2 provinces

### Problem 4: Graph Valid Tree
- **Pattern:** Check (1) no cycle AND (2) connected
- **Key insight:** Valid tree iff N-1 edges and all connected
- **Code:** Try each union; if already connected, cycle exists

### Problem 5: Accounts Merge (with Maps)
- **Pattern:** Use HashMap to map email→account; union all emails under same account
- **Twist:** Requires map-based union-find (not just integers)
- **Code:** `uf.union(accountToId.get(email1), accountToId.get(email2));`

---

## 9. My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> "Union-Find = Quick way to answer **'Are A and B in the same group?'** in nearly O(1) time."

### Path Compression Explanation:
```
Before path compression:     After:
    0 (root)               0 (root)
    |                      |
    1              5 → 0 directly
    |                      
    2
    |
    3
    |
    4
    |
    5  (find(5) is slow: O(n))    (find(5) is fast: O(1))
```

### Interview Phrasing:
> "I'll use Union-Find to efficiently track connected components. With path compression and union by rank, each operation is nearly constant time. First, I'll union all related elements, then check connectivity or count components as needed."

### Union by Rank vs Union by Size:
Both work! Pick one:
- **Union by Rank:** Track tree height; attach smaller height to larger
- **Union by Size:** Track component size; attach smaller to larger

### What I Always Forget:
> **Always do path compression in find()!** It's a one-line optimization that makes a huge difference. Without it, the data structure degrades to O(log n) or even O(n).

### Real Interview Experience:
> "Union-Find seems simple but is incredibly powerful for connectivity problems. The key is recognizing when to use it (connectivity/components) vs when to use DFS/BFS (shortest path/reachability). Most competitive programmers use Union-Find for undirected graphs and DFS/BFS for graphs where path matters."

---

## 10. Quick Comparison: Union-Find vs DFS/BFS

| Aspect | Union-Find | DFS/BFS |
|--------|-----------|---------|
| **Use for** | Connectivity, components, cycles | Shortest path, reachability, traversal |
| **Speed** | O(α(n)) per operation | O(V + E) for entire graph |
| **Space** | O(n) | O(V + E) + O(V) for recursion |
| **Incremental** | ✅ Good for adding edges one-by-one | ❌ Requires full graph |
| **Cycle detection (undirected)** | ✅ Easy: `find(u) == find(v)` | ⚠️ Harder: need to track parent |
| **Cycle detection (directed)** | ❌ Doesn't work for directed | ✅ Use DFS with colors |

**Golden Rule:**
- **Connectivity / Components → Union-Find ✅**
- **Path / Reachability → DFS/BFS ✅**

---

## 📋 Quick Checklist Before Coding

- [ ] Identified this as a connectivity/components problem?
- [ ] Initialized parent array with `parent[i] = i`?
- [ ] Implemented find with path compression?
- [ ] Implemented union by rank or size?
- [ ] Correctly handled the input format (edges vs matrix)?
- [ ] If 2D grid, converted (row, col) to 1D index?
- [ ] Union all relevant pairs?
- [ ] Know what to return (component count / cycle detected / valid tree)?
- [ ] Counted components correctly (count roots: `parent[i] == i`)?
- [ ] Tested with edge cases (single node, disconnected graph)?

---

## 📚 Algorithm Patterns Reference

For a comprehensive visual reference of all algorithm patterns including Union-Find, Graph patterns, and more, see:

[Graph & Tree Reference Guide](../common/graph_tree_down_ref.html)
