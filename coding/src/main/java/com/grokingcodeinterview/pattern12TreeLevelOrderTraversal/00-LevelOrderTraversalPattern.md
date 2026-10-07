# 🔹 Interview Coding Pattern – Cheat Sheet Template

## Level Order Traversal (BFS - Breadth-First Search)

---

## 1️⃣ Pattern Name

**Level Order Traversal** (also known as **Breadth-First Search - BFS**)

---

## 2️⃣ When to Use This Pattern (Recognition Signals)

- ✅ "Traverse a tree **level by level**"
- ✅ "Find the **average** of each level"
- ✅ "Get the **rightmost node** in each level"
- ✅ "Find **minimum depth** of tree"
- ✅ "Connect siblings at same level"
- ✅ "Find all nodes at a specific **distance** from root"
- ✅ Working with **graphs** and need to visit nodes level by level
- ✅ Finding the **shortest path** in an unweighted graph

### Complexity Hint:
- **Time:** O(n) where n = number of nodes (must visit every node)
- **Space:** O(w) where w = maximum width/nodes at any level

### One-Line Trigger Thought:
> "If the problem asks for things organized **by level** or **by distance**, use a **Queue** and process nodes **layer by layer**"

---

## 3️⃣ Core Idea (Mental Model)

**The Queue is Your Best Friend Here!**

Imagine you're exploring a building floor by floor:
- Start at the **entrance (root)**
- Visit all people on **Floor 1** (all depth 1 nodes)
- Then move to **Floor 2** (all depth 2 nodes)
- Continue until all floors visited

**Why Queue?**
- Queue = FIFO (First-In-First-Out) ✅
- Add nodes to back → Process from front
- This naturally gives us level-by-level processing

**Key Insight:** Use `levelSize = queue.size()` to know exactly how many nodes are at the current level!

---

## 4️⃣ Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();  // 🔴 CRITICAL: Snapshot current level size
        List<Integer> currentLevel = new ArrayList<>();
        
        for (int i = 0; i < levelSize; i++) {  // Process exactly levelSize nodes
            TreeNode currNode = queue.poll();
            currentLevel.add(currNode.val);
            
            // Add next level nodes
            if (currNode.left != null)
                queue.offer(currNode.left);
            if (currNode.right != null)
                queue.offer(currNode.right);
        }
        result.add(currentLevel);
    }
    return result;
}
```

⚠️ **Important:** Keep this structure. Only fill in the logic inside the for loop.

---

## 5️⃣ Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently:**

| Decision | Options | When to Choose |
|----------|---------|---|
| **Data Structure** | Queue | 99% of BFS problems |
| **Queue Type** | `LinkedList`, `ArrayDeque`, `Deque` | `ArrayDeque` is fastest for BFS |
| **Snapshot Level Size?** | `int levelSize = queue.size()` before loop | YES! Always capture level size BEFORE the for loop |
| **What to Store in Queue?** | TreeNode, Integer, Object | Store the actual node/object |
| **Termination Condition** | `while (!queue.isEmpty())` | Standard, simple, reliable |
| **Result Structure** | `List<List<T>>`, `List<T>`, custom | Depends on problem requirements |

---

## 6️⃣ Common Traps & Mistakes

🚨 **Mistake 1: Forgetting to snapshot levelSize**
```java
// ❌ WRONG - levelSize changes inside loop!
for (int i = 0; i < queue.size(); i++) { // queue.size() changes!
    
// ✅ CORRECT
int levelSize = queue.size();
for (int i = 0; i < levelSize; i++) {
```

🚨 **Mistake 2: Not checking for null children**
```java
// ❌ WRONG - Will add null to queue!
queue.offer(currNode.left);    // What if left is null?

// ✅ CORRECT
if (currNode.left != null)
    queue.offer(currNode.left);
```

🚨 **Mistake 3: Using wrong Queue method**
```java
// ❌ WRONG - add() throws exception, offer() returns false
queue.add(node);      // Can throw exception

// ✅ CORRECT
queue.offer(node);    // Returns false, safer
queue.poll();         // Returns null if empty
```

🚨 **Mistake 4: Resetting result list incorrectly**
```java
// ❌ WRONG
List<Integer> currentLevel = result;  // Reusing reference causes issues

// ✅ CORRECT
List<Integer> currentLevel = new ArrayList<>();
result.add(currentLevel);
```

---

## 7️⃣ Time & Space Complexity

**Time:** O(n) where n = total number of nodes

**Space:** O(w) where w = maximum width (nodes at widest level)

**Explain why in one line:**
> We visit each node exactly once (Time), and the queue stores at most the widest level of the tree at any time (Space).

---

## 8️⃣ Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

**Problem 1: Level Order Traversal**
- Name: Binary Tree Level Order Traversal (LeetCode 102)
- Key insight: Capture `levelSize` before loop to process exactly one level per iteration

**Problem 2: Level Order Traversal II**
- Name: Binary Tree Level Order Traversal II (LeetCode 107)
- Key insight: Same as Problem 1 but reverse the result list at the end

**Problem 3: Binary Tree Right Side View**
- Name: Binary Tree Right Side View (LeetCode 199)
- Key insight: For each level, add only the **last node** to result

**Problem 4: Averages of Levels in Binary Tree**
- Name: Averages of Levels in Binary Tree (LeetCode 637)
- Key insight: Calculate sum of nodes at each level, divide by levelSize

---

## 9️⃣ My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> "Queue + levelSize snapshot = Instant level-by-level processing"

### Interview Phrasing:
> "We use a Queue to perform breadth-first traversal. The key is to capture the queue size at the start of each iteration, so we know exactly how many nodes belong to the current level."

### My Favorite Variation:
> **Level-by-Level with Custom Logic:**
> ```java
> while (!queue.isEmpty()) {
>     int levelSize = queue.size();
>     // Insert custom logic here (sum, min, max, etc.)
>     for (int i = 0; i < levelSize; i++) {
>         // Process node
>     }
> }
> ```

### What I Always Forget:
> - Forgetting the null checks before adding children to queue
> - Not creating a NEW ArrayList for each level (reference issue)
> - Using `queue.size()` directly in the for condition instead of snapshot
> - Not handling the empty tree case at the start

---

## 📋 Quick Checklist Before Coding

- [ ] Did I create a Queue and initialize with root?
- [ ] Did I check if root is null and return empty result?
- [ ] Did I snapshot `levelSize = queue.size()` BEFORE the for loop?
- [ ] Did I add null checks before offering left/right children?
- [ ] Did I create a NEW ArrayList for each level?
- [ ] Did I use `queue.offer()` and `queue.poll()` (not add/remove)?
- [ ] Did I handle the problem-specific logic (sum, average, rightmost, etc.)?
- [ ] Did I test with edge cases (empty tree, single node, balanced tree, skewed tree)?

---

## 🔗 Related Patterns

- **DFS (Depth-First Search):** For problems requiring recursive structure exploration
- **Binary Search:** If you need to find something specific, not just traverse
- **Graph BFS:** Same concept, works on any graph (not just trees)

---

## 🔟 HTML Reference Card (Graph vs Tree)

- Open the visual reference: [`../common/graph_tree_down_ref.html`](../common/graph_tree_down_ref.html)

