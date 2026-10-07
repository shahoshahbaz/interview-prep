# Island Pattern — Cheat Sheet

---

## 1. Definition:

The Island Pattern is a technique for solving problems on **2D grids/matrices** by treating the grid as a graph where each cell is a node. Connected cells form **connected components (islands/clusters/regions)**. You explore these components using **DFS or BFS** to count, calculate area, find shapes, or spread values.

**Key concept:** Grid problem → Graph traversal problem

---

## 2. When to Use This Pattern (Recognition Signals)

✅ **Use when:**

- Input is a **2D grid/matrix**
- Problem talks about:
  - Connected cells / regions
  - Islands / clusters / components
  - Area / count / shape
  - Spread patterns / flooding

### Complexity Hint:

Linear scan through all cells: **O(m × n)**

### One-Line Trigger Thought:

> "If I have a 2D grid and need to explore connected regions, it's Island Pattern."

---

## 3. Core Idea (Mental Model)

**Treat each cell as a node in a graph. Connected cells (same value, same region) form edges. Explore connected components using DFS or BFS.**

```
Grid → Graph
Each cell = node
Adjacent cells = edges
Connected region = connected component (island)
```

**Rule:** If you can't explain it simply, you don't understand it yet.

---

## 4. Standard Code Template (Skeleton)

### Directions Setup (Choose One)

```java
// 4-direction (up, down, left, right)
int[][] dirs = {
    {1, 0}, {-1, 0}, {0, 1}, {0, -1}
};

// 8-direction (if diagonal needed)
int[][] dirs8 = {
    {1, 0}, {-1, 0}, {0, 1}, {0, -1},
    {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
};
```

### DFS Template

```java
void dfs(int r, int c) {
    // Boundary check
    if (r < 0 || c < 0 || r >= rows || c >= cols) return;
    
    // Invalid cell check (water / already visited / wrong value)
    if (grid[r][c] == 0) return;
    
    // Mark as visited
    grid[r][c] = 0;
    
    // Explore all directions
    for (int[] d : dirs) {
        dfs(r + d[0], c + d[1]);
    }
}
```

### BFS Template

```java
Queue<int[]> q = new LinkedList<>();
q.add(new int[]{r, c});
grid[r][c] = 0;

while (!q.isEmpty()) {
    int[] cur = q.poll();
    
    for (int[] d : dirs) {
        int nr = cur[0] + d[0];
        int nc = cur[1] + d[1];
        
        // Check bounds and validity
        if (nr >= 0 && nc >= 0 && nr < rows && nc < cols && grid[nr][nc] == 1) {
            q.add(new int[]{nr, nc});
            grid[nr][nc] = 0;  // Mark visited
        }
    }
}
```

⚠️ **Important:** Keep this structure. Only fill in the logic (what to do inside DFS/BFS).

---

## 5. Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently.**

### Decision 1: DFS or BFS?

| Use Case | Choice | Why |
|----------|--------|-----|
| Count islands / area / shape | **DFS ✅** | Recursive, simple, stack-based |
| Shortest path / distance | **BFS ✅** | Level-by-level, guarantees shortest |
| Spread pattern / rotten oranges | **BFS ✅** | Time-based spreading |

### Decision 2: Directions?

- **4-direction?** (up, down, left, right) — Most common
- **8-direction?** (with diagonals) — Read problem carefully

### Decision 3: Visited Handling?

| Option | Code | Pros | Cons |
|--------|------|------|------|
| **Modify grid (best)** | `grid[r][c] = 0;` | No extra space | Destroys original grid |
| **Separate visited array** | `boolean[][] visited;` | Preserves grid | O(m×n) extra space |

---

## 6. Common Traps & Mistakes

👉 **Tip:** This section gets more valuable over time as you add your personal pitfalls.

| Mistake | Impact | Fix |
|---------|--------|-----|
| ❌ Forgetting to mark visited | Infinite loop / stack overflow | Mark BEFORE recursion |
| ❌ Wrong directions (4 vs 8) | Wrong answer | Read problem carefully |
| ❌ Missing boundary check | IndexOutOfBounds exception | `r >= 0 && c >= 0 && r < rows && c < cols` |
| ❌ Marking visited too late | May revisit same cell | Mark immediately after dequeue/enter |
| ❌ Using DFS for shortest path | Wrong answer (not guaranteed shortest) | Use BFS for distance/shortest |
| ❌ Not checking cell value in loop | Visits invalid cells | Check `grid[nr][nc] == 1` before adding to queue |

---

## 7. Time & Space Complexity

**Time:** O(m × n)
- Scan each cell once during traversal

**Space:** O(m × n)
- Worst case: recursion stack (DFS) or queue (BFS) when island spans entire grid
- Or: visited array if using separate boolean array

**Explain why in one line:**
> Each cell is visited exactly once, and the recursion/queue depth is bounded by grid size.

---

## 8. Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

### Problem 1: Count Islands
- **Key insight:** Increment counter for each new island, DFS to mark entire island as visited
- **Code pattern:**
  ```java
  for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
          if (grid[i][j] == 1) {
              dfs(i, j);
              count++;
          }
      }
  }
  ```

### Problem 2: Max Area of Island
- **Key insight:** Return size from DFS; track maximum
- **Code pattern:**
  ```java
  int dfs(int r, int c) {
      if (invalid) return 0;
      grid[r][c] = 0;
      return 1 + dfs(r+1, c) + dfs(r-1, c) + dfs(r, c+1) + dfs(r, c-1);
  }
  ```

### Problem 3: Flood Fill
- **Key insight:** Replace connected region with new value
- **Use case:** Paint bucket tool in image editors

### Problem 4: Rotten Oranges (BFS)
- **Key insight:** Multi-source BFS, track time/distance
- **Use case:** Spread patterns, infection spread

### Problem 5: Closed Island (Boundary Check)
- **Key insight:** DFS + check if touching border; if yes, not closed
- **Use case:** Surrounded regions, enclaves

### Problem 6: Distinct Islands
- **Key insight:** Store relative positions or path of each island
- **Use case:** Count unique island shapes

---

## 9. My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**
#### Quick map for common island DFS
  1. **Flood fill / number of islands**: DFS returns void

      ``` java 
      if(out of bounds || water || visited) return;
      ```
  2. **Max area:** DFS returns int
    
      ``` java
      if(out of bounds || water || visited) return 0;
      ```
  3. **Closed island:** DFS returns boolean
  
      ``` java
     if(out of bounds) return false;
     if(water || visited) return true;
      ```

4. **Cycle detection:** DFS returns boolean
      ``` java
       if (out of bounds) → return false        // no cycle
       if (different char) → return false       // not same region
       if (visited) → return true               // cycle found
      ```
 
### Mental Shortcut:
> "Scan grid → find unvisited cell → DFS/BFS to explore entire island → repeat"

### Interview Phrasing:
> "I'll scan through the grid, and for each unvisited valid cell, I'll run DFS to explore its entire connected component, marking cells as visited. I'll count/calculate based on the problem requirement."

### My Favorite Variation:
> **Multi-source BFS:** Start with multiple sources (e.g., all rotten oranges) and spread simultaneously level-by-level. This naturally tracks distance/time.

### What I Always Forget:
> **Mark visited BEFORE recursing, not after returning.** Late marking causes revisits and infinite loops. Also, always double-check: are we looking for `1`s or `0`s? And 4-direction or 8-direction?

---

## 📋 Quick Checklist Before Coding

- [ ] Identified input as 2D grid/matrix?
- [ ] Recognized it as island/connected component problem?
- [ ] Decided: DFS or BFS?
- [ ] Decided: 4-direction or 8-direction?
- [ ] Decided: modify grid or use visited array?
- [ ] Added boundary checks: `r >= 0 && c >= 0 && r < rows && c < cols`?
- [ ] Added value check: `grid[r][c] == 1`?
- [ ] Marked visited BEFORE recursing/adding to queue?
- [ ] Set up directions array correctly?
- [ ] Tested with edge cases (empty grid, single cell, entire grid as island)?

---

## 📚 Algorithm Patterns Reference

For a comprehensive visual reference of all algorithm patterns including Island patterns, Graph patterns, and more, see:

[Graph & Tree Reference Guide](../common/graph_tree_down_ref.html)
