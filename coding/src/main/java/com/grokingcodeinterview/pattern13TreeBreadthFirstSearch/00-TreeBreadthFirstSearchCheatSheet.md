# 🔹 Interview Coding Pattern – Cheat Sheet



---

## 1️⃣ Pattern Name

**Tree Breadth-First Search (BFS) / Level-Order Traversal**

---

## 2️⃣ When to Use This Pattern (Recognition Signals)

- Need to process a **tree level by level**
- Finding **maximum/minimum at each level**
- Need to return results **grouped by level**
- Problems involving **depth, height, or level distance**
- **Right/Left view** of a tree
- Visiting nodes in **layer order** (siblings before children)
- **Perfect for wide, shallow trees** (more efficient than DFS)

### Complexity Hint:
Time: **O(N)** where N is number of nodes (visit each node once)
Space: **O(W)** where W is max width of tree (queue size)

### One-Line Trigger Thought:
> "If the problem asks about levels, siblings, or layer-by-layer, use BFS with a Queue!"

---

## 3️⃣ Core Idea (Mental Model)

**The Core Pattern:**
1. Use a **Queue** to maintain FIFO processing order
2. Process nodes **level by level** (not randomly like DFS)
3. For each level, track `levelSize = queue.size()` at the start
4. Process exactly `levelSize` nodes before moving to next level
5. Add children to queue after processing parent

**Why Queue?**
- Queue = FIFO (First In First Out)
- This ensures we process all nodes at level N before any node at level N+1
- DFS (Stack/Recursion) would interleave levels

**Key Mental Image:**
```
        1          Level 0: Process [1], add [2,3]
       / \
      2   3        Level 1: Process [2,3], add [4,5,6,7]
     / \ / \
    4 5 6  7       Level 2: Process [4,5,6,7]
```

**Rule:** If you can't explain it simply, you don't understand it yet.

---

## 3️⃣ Level Order Traversal vs Breadth-First Search (BFS)

**Are they the same?** Almost, but with subtle differences:

| Aspect | Level Order Traversal | Breadth-First Search (BFS) |
|--------|----------------------|---------------------------|
| **Definition** | Visit nodes grouped by their level/depth in order | Visit nodes based on distance from start (no nesting requirement) |
| **Used For** | Tree problems requiring per-level processing | Graph problems, any graph traversal where you need "closest nodes first" |
| **Structure** | Only works on **Trees** (no cycles, parent is singular) | Works on **Graphs** (cycles allowed, multiple paths) |
| **Grouping** | Must group results BY LEVEL explicitly | Just visit in order of distance, no grouping needed |
| **Queue** | Same queue process, but captures `levelSize` to group by level | Same queue process, no level grouping |
| **Example** | "Find max value at EACH level" → must separate by level | "Find shortest path in a graph" → just visit nodes in order |

**Key Insight:**
```
Tree Level Order:
        1          
       / \
      2   3        
     / \ / \
    4 5 6  7       

Result: [[1], [2,3], [4,5,6,7]]  ← Grouped by LEVEL explicitly


Graph BFS:
    1 -- 2
    |    |
    3 -- 4

Result: [1, 2, 3, 4]  ← Just visited in order, no grouping
```

**In This Course:**
- **Pattern 13: Tree BFS** = BFS for trees WITH level grouping (this cheat sheet)
- **Pattern 12: Level Order Traversal** = BFS for trees WITHOUT level grouping

**Practical Difference:**
```java
// Plain BFS (no level awareness)
while (!queue.isEmpty()) {
    TreeNode current = queue.poll();
    sum += current.val;  // NO levelSize grouping
    if (current.left != null) queue.offer(current.left);
    if (current.right != null) queue.offer(current.right);
}

// BFS with level grouping
while (!queue.isEmpty()) {
    int levelSize = queue.size();  // ← Key difference!
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < levelSize; i++) {
        TreeNode current = queue.poll();
        max = Math.max(max, current.val);
        if (current.left != null) queue.offer(current.left);
        if (current.right != null) queue.offer(current.right);
    }
    result.add(max);  // ← Save per-level result
}
```

**Bottom Line for Interviews:**
- **"BFS"** = General term (works on graphs and trees)
- **"Level Order Traversal"** = BFS on a tree, visiting all nodes
- **"Tree BFS with Level Grouping"** = BFS on a tree, grouping/processing by level (THIS PATTERN)

---

## 4️⃣ Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

### Template 1️⃣: Plain BFS (NO Level Grouping)

**Use this when:** You don't care about depth/levels, just visit all nodes

**Examples:** Sum of all nodes, search for value, serialization, clone tree

```java
public <ReturnType> bfs(TreeNode root) {
    if (root == null) return <default>;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        TreeNode current = queue.poll();
        
        // PROCESS NODE
        // e.g., sum += current.val;
        
        if (current.left != null) queue.offer(current.left);
        if (current.right != null) queue.offer(current.right);
    }
    
    return <result>;
}
```

📌 **Key Point:** No `levelSize` → all nodes processed uniformly without level awareness

---

### Template 2️⃣: BFS with Level Boundaries (YOUR PATTERN) ⭐

**Use this when:** You must process nodes by level (THIS IS PATTERN 13)

**Examples:** Max value per level, level sum, even-odd tree, left/right view

```java
public <ReturnType> bfsByLevel(TreeNode root) {
    if (root == null) return <default>;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    <ResultContainer> result = new <Container>();
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();  // ← CAPTURE LEVEL BOUNDARY
        
        // Reset per-level state here
        // e.g., int levelMax = Integer.MIN_VALUE;
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode current = queue.poll();
            
            // PROCESS CURRENT LEVEL NODE
            // e.g., levelMax = Math.max(levelMax, current.val);
            
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }
        
        // SAVE PER-LEVEL RESULT (after processing all nodes at this level)
        // result.add(levelMax);
    }
    
    return result;
}
```

🔑 **Key Point:** `levelSize = queue.size()` BEFORE the loop ensures you process exactly one level per outer iteration

⚠️ **Important:** Keep this structure. Only change the logic inside comments.

---

## 5️⃣ Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently.**

1. **What container to return?** 
   - Single value (int, boolean) → Just accumulate in variable
   - Per-level result → `List<Integer>` (one entry per level)
   - All nodes grouped by level → `List<List<Integer>>`
   - Full tree structure → `List<TreeNode>`

2. **What to do with current node's value?**
   - Compare & track max? → `levelMax = Math.max(levelMax, current.val)`
   - Sum all values? → `sum += current.val`
   - Collect in list? → `currentLevel.add(current.val)`
   - Check condition? → `if (current.val == target)`

3. **When to save the level result?**
   - **INSIDE** the inner loop? → Save after each node
   - **OUTSIDE** the inner loop? → Save after entire level processed
   - **NOT AT ALL?** → Just accumulate (e.g., sum of all)

4. **Do you need to track depth/level number?**
   - Use separate counter if needed
   - Or use `result.size()` as current level number

---

## 6️⃣ Common Traps & Mistakes

👉 **Tip:** This section gets more valuable over time as you add your personal pitfalls.

**❌ Mistake 1: Forgetting `int levelSize = queue.size()`**
- If you don't capture level size BEFORE processing, you'll process newly added nodes in same iteration
- The new nodes added during this level's processing will be processed immediately
- This breaks the level-by-level guarantee

**✅ Fix:** Always get `int levelSize = queue.size()` BEFORE the `for` loop

---

**❌ Mistake 2: Not initializing `levelMax` before processing level**
- `Integer.MIN_VALUE` is required for max comparisons (handles negative numbers)
- Forgetting to initialize = wrong result

**✅ Fix:** Initialize once per level: `int levelMax = Integer.MIN_VALUE;`

---

**❌ Mistake 3: Adding children before processing parent**
- Doesn't break, but confusing
- Always process parent first, then add children

**✅ Fix:** Process node logic → THEN add children

---

**❌ Mistake 4: Using `.add()` instead of `.offer()` for queue**
- Both work, but `.offer()` is the Queue convention
- Shows you know queue semantics

**✅ Fix:** Use `.offer()` to add, `.poll()` to remove

---

**❌ Mistake 5: Null checks on children**
- Must check `left != null` and `right != null` before adding
- Null nodes will cause issues when dequeued

**✅ Fix:** Always check before `queue.offer()`

---

## 7️⃣ Time & Space Complexity

**Time:** **O(N)** where N = number of nodes in tree
- Visit each node exactly once
- Each node enqueued once and dequeued once
- Operations inside loops (offer, poll, comparisons) are O(1)

**Space:** **O(W)** where W = maximum width of tree
- Queue stores nodes level by level
- Worst case: a complete tree where max width = N/2 (bottom level)
- Best case: a linear chain where width = 1
- For result storage, depends on what you return:
  - `List<Integer>` for max per level = O(H) where H = height
  - `List<List<Integer>>` = O(N) to store all nodes

**Explain why in one line:**
> We visit each node once (O(N) time), and at any moment the queue holds at most one complete level of the tree (O(W) space where W is max width). If returning per-level results, space for result storage is O(H) for height or O(N) in worst case.

---

## 8️⃣ Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

**Problem 1: Largest Value at Each Level** 
- Name: **P01LargestValueOnEachLevelOfABinaryTree.java**
- Key insight: Use `levelSize` loop, track `levelMax`, save after processing level
- Pattern: Process all nodes at level → save max → move to next level

**Problem 2: Sum of All Nodes** 
- Name: **P00TreeBreadthFirstSearch.java**
- Key insight: Just accumulate sum across ALL levels, no need to track levels separately
- Pattern: Add all values to running sum, one queue, no nesting

**Problem 3: Maximum Depth**
- Name: **P03MaximumDepthOfABinaryTree.java**
- Key insight: Maximum depth = number of levels = result.size() at the end
- Pattern: Each iteration = one level, count iterations

**Problem 4: Left/Right View of Tree**
- Name: **P072LeftViewOfBinaryTree.java, P07RightViewOfBinaryTree.java**
- Key insight: For left view, take first node of each level. For right view, take last node of each level
- Pattern: Save `queue.peek()` (left) or process last in level (right)

---

## 9️⃣ My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> "**Level-by-level processing**: Always get `levelSize = queue.size()` BEFORE the loop. This is your insurance that you process exactly one level per outer loop iteration."

### Interview Phrasing:
> "We'll use BFS with a queue to traverse the tree level by level. At each level, we capture the size to ensure we process exactly those nodes before moving to the next level."

### My Favorite Variation:
> **Two-Queue Technique**: Some problems use two queues (current level, next level) instead of `levelSize`. Both are correct, but `levelSize` is cleaner and more memory-efficient.

### What I Always Forget:
> **Null checks!** Always verify `left != null` and `right != null` before adding to queue. One null pointer exception and interview is over.

---

## 📋 Quick Checklist Before Coding

- [ ] Initialize Queue with `new LinkedList<>()`
- [ ] Add root to queue at start
- [ ] Handle null root edge case
- [ ] **Get `levelSize = queue.size()` BEFORE inner loop**
- [ ] Inner loop runs exactly `levelSize` times
- [ ] Process node BEFORE adding children
- [ ] Check `!= null` before adding children to queue
- [ ] Decide where to save level result (inside/outside inner loop)
- [ ] Return correct type (single value, List, List<List>, etc.)
- [ ] Test with edge cases: null tree, single node, unbalanced tree

---

## 📚 Algorithm Patterns Reference

For a comprehensive visual reference of all algorithm patterns including BFS, DFS, graphs, and more, see:

[Graph & Tree Reference Guide](../common/graph_tree_down_ref.html)
