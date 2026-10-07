# DFS vs BFS — Graph Decision Cheat Sheet

## Quick Rule
**No shortest-path / min-steps requirement → DFS**
**Any "minimum steps / hops / distance / levels" → BFS**

---

## Use DFS when
- Just need to know **if** connected / path exists
- **Cycle detection**
- Counting components / islands
- **Topological sort**
- Backtracking (all paths, permutations, combinations)

## Use BFS when
- Need **shortest path** (unweighted graph)
- **Level-by-level** processing
- Nearest neighbor / minimum steps
- Multi-source spread (e.g. rotting oranges, walls and gates)

---

## Code Templates

### DFS (Recursive)
```java
void dfs(int node, List<List<Integer>> graph, boolean[] visited) {
    visited[node] = true;
    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            dfs(neighbor, graph, visited);
        }
    }
}
```

### DFS (Iterative, using Stack)
```java
void dfsIterative(int start, List<List<Integer>> graph, boolean[] visited) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(start);
    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (visited[node]) continue;
        visited[node] = true;
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) stack.push(neighbor);
        }
    }
}
```

### BFS
```java
void bfs(int start, List<List<Integer>> graph, boolean[] visited) {
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    visited[start] = true;
    while (!queue.isEmpty()) {
        int node = queue.poll();
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.offer(neighbor);
            }
        }
    }
}
```

---

## Traps
- **Undirected graph cycle detection**: track parent, skip the edge back to parent (not just "already visited")
- **Directed graph cycle detection**: need 3-color (white/grey/black), 2 states aren't enough
- Forgetting `visited` check before adding to queue/stack in BFS → duplicates processed
- Disconnected graphs: need an outer loop over all nodes to catch every component

## Complexity
Both DFS and BFS: **O(V + E)** time, **O(V)** space (visited set + queue/stack/recursion)

## Canonical Problems
- DFS: Number of Islands, Path Exists in Graph, Course Schedule (cycle), Clone Graph
- BFS: Shortest Path in Binary Matrix, Rotting Oranges, Word Ladder, Level Order Traversal