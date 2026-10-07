# 🔹 Queue vs Deque vs Stack – Complete Comparison Guide

---

## 1️⃣ Quick Comparison Table

| Feature | Queue | Deque | Stack |
|---------|-------|-------|-------|
| **Full Name** | Queue | Double-Ended Queue | Stack |
| **Order** | FIFO (First In, First Out) | FIFO/LIFO (Both Ends) | LIFO (Last In, First Out) |
| **Add Front** | ❌ No | ✅ Yes | ✅ Yes (top) |
| **Add Back** | ✅ Yes | ✅ Yes | ✅ Yes (top) |
| **Remove Front** | ✅ Yes | ✅ Yes | ✅ Yes (top) |
| **Remove Back** | ❌ No | ✅ Yes | ❌ No |
| **Peek Front** | ✅ Yes | ✅ Yes | ✅ Yes (top) |
| **Peek Back** | ❌ No | ✅ Yes | ❌ No |
| **Use Case** | Task scheduling, BFS | Sliding window, deque patterns | Parentheses, DFS, undo |
| **Java Implementation** | `Queue<T>`, `LinkedList<T>` | `Deque<T>`, `ArrayDeque<T>` | `Stack<T>` |

---

## 2️⃣ Data Structure Visualization

### Queue (FIFO)
```
Front → [1] [2] [3] [4] [5] → Back
Remove from front    Add to back
     ↓                  ↑
   dequeue()         enqueue()
```

### Deque (Double-Ended)
```
Front ← [1] [2] [3] [4] [5] → Back
  ↑                              ↑
Add/Remove              Add/Remove
```

### Stack (LIFO)
```
         Top
         ↑↓
         [5]  ← Pop from here / Push here
         [4]
         [3]
         [2]
         [1]
```

---

## 3️⃣ When to Use Each (Recognition Signals)

### 🟢 **Use QUEUE When:**
- ✅ Processing items in order they arrive → Tasks, jobs, print queue
- ✅ Breadth-First Search (BFS) → Level-order traversal
- ✅ One-directional flow → Producer-consumer pattern
- ✅ Waiting line/queue discipline → Customer service, scheduler
- ✅ Sliding window problems (sometimes) → Moving average over window

**One-Line Trigger:**
> "Do I need to process things in the order they arrived?" → **Queue**

---

### 🔵 **Use DEQUE When:**
- ✅ Need to add/remove from both ends → Sliding window maximum
- ✅ Implementing both Queue AND Stack → Double-ended operations
- ✅ Palindrome checking → Compare from both ends
- ✅ Undo/Redo with limited history → Maintain both directions
- ✅ Level-order traversal with two ends → Zigzag traversal

**One-Line Trigger:**
> "Do I need flexibility at both ends?" → **Deque**

---

### 🔴 **Use STACK When:**
- ✅ Matching pairs → Balanced parentheses, valid expressions
- ✅ Reverse order processing → Previous/next element problems
- ✅ Depth-First Search (DFS) → Tree/graph traversal
- ✅ Undo/Redo functionality → Browser history, text editor
- ✅ Function call stack → Recursion, backtracking
- ✅ Expression evaluation → Postfix, infix expressions

**One-Line Trigger:**
> "Do I need to process in reverse order or match pairs?" → **Stack**

---

## 4️⃣ Core Data Structures Explained

### Queue – FIFO (First In, First Out)

**Mental Model:** Think of a **line at a coffee shop**
- First person to arrive is first to get served
- New customers join at the back (rear/tail)
- Customers leave from the front (head)

```
Real-world: Bank queue, Print queue, Task scheduler
```

**Key Properties:**
- Elements processed in arrival order
- Only add to back (enqueue)
- Only remove from front (dequeue)
- Perfect for level-order problems

---

### Deque – Double-Ended Queue

**Mental Model:** Think of a **bidirectional conveyor belt**
- Can add/remove from BOTH ends
- Combines Queue + Stack flexibility
- More powerful but slightly more complex

```
Real-world: Restaurant with drive-thru on both sides, Sliding window problems
```

**Key Properties:**
- Maximum flexibility: add/remove both ends
- Can act as Queue OR Stack
- Useful when you need to process from multiple ends

---

### Stack – LIFO (Last In, First Out)

**Mental Model:** Think of a **stack of plates in a cafeteria**
- Last plate placed is first one taken
- Add plates on top (push)
- Remove plates from top (pop)
- See only the top plate (peek)

```
Real-world: Browser back button, Text editor undo, Function call stack
```

**Key Properties:**
- Most recently added = first to be removed
- Perfect for backtracking and reversal
- Natural fit for recursive problems

---

## 5️⃣ Most Used Methods Comparison

### Queue Methods
```java
Queue<Integer> q = new LinkedList<>();

// Add/Offer (add to back)
q.add(5);           // Throws exception if full (rare with LinkedList)
q.offer(5);         // Returns false if full (preferred, safer)

// Remove/Poll (remove from front)
q.remove();         // Throws NoSuchElementException if empty
q.poll();           // Returns null if empty (preferred, safer)

// Peek (view front without removing)
q.element();        // Throws NoSuchElementException if empty
q.peek();           // Returns null if empty (preferred, safer)

// Check
q.isEmpty();
q.size();
```

**Safe Pattern:**
```java
if (!q.isEmpty()) {
    int front = q.poll();
    // process
}
```

---

### Deque Methods
```java
Deque<Integer> dq = new ArrayDeque<>();

// ===== FRONT OPERATIONS =====
dq.addFirst(5);     // Add to front
dq.removeFirst();   // Remove from front
dq.getFirst();      // Peek front (throws exception if empty)
dq.pollFirst();     // Remove front (returns null if empty)
dq.peekFirst();     // Peek front (returns null if empty)

// ===== BACK OPERATIONS =====
dq.addLast(5);      // Add to back
dq.removeLast();    // Remove from back
dq.getLast();       // Peek back (throws exception if empty)
dq.pollLast();      // Remove back (returns null if empty)
dq.peekLast();      // Peek back (returns null if empty)

// ===== GENERAL =====
dq.isEmpty();
dq.size();
dq.descendingIterator();  // Iterate in reverse
```

**Safe Pattern:**
```java
if (!dq.isEmpty()) {
    int front = dq.pollFirst();
    int back = dq.pollLast();
}
```

---

### Stack Methods
```java
Stack<Integer> stack = new Stack<>();

// Push (add to top)
stack.push(5);

// Pop (remove from top)
int top = stack.pop();  // Throws EmptyStackException if empty

// Peek (view top without removing)
int top = stack.peek(); // Throws EmptyStackException if empty

// Search (1-based index from top, -1 if not found)
int pos = stack.search(5);

// Check
stack.isEmpty();
stack.size();
```

**Safe Pattern:**
```java
if (!stack.isEmpty()) {
    int top = stack.pop();
    // process
}
```

**Modern Alternative (using Deque):**
```java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(5);
int top = stack.pop();
int peek = stack.peek();
```

---

## 6️⃣ Detailed Method Comparison

### Adding Elements
```java
Queue<Integer> q = new LinkedList<>();
Deque<Integer> dq = new ArrayDeque<>();
Stack<Integer> stack = new Stack<>();

// Adding to Queue (always back)
q.add(1);           // ← Use offer() for safety
q.offer(1);         // ← Preferred

// Adding to Deque (flexible)
dq.addFirst(1);     // Add to front
dq.addLast(1);      // Add to back
dq.push(1);         // Same as addFirst()
dq.add(1);          // Same as addLast()

// Adding to Stack (always top)
stack.push(1);      // Add to top
stack.add(1);       // Also works, but push() is idiomatic
```

---

### Removing Elements
```java
Queue<Integer> q = new LinkedList<>();
Deque<Integer> dq = new ArrayDeque<>();
Stack<Integer> stack = new Stack<>();

// Removing from Queue (always front)
q.poll();           // Remove front, null if empty ← Preferred
q.remove();         // Remove front, throws exception if empty

// Removing from Deque (flexible)
dq.pollFirst();     // Remove front, null if empty
dq.pollLast();      // Remove back, null if empty
dq.removeFirst();   // Remove front, throws exception if empty
dq.removeLast();    // Remove back, throws exception if empty
dq.pop();           // Same as removeFirst()

// Removing from Stack (always top)
stack.pop();        // Remove top, throws exception if empty
stack.remove();     // Also works, but pop() is idiomatic
```

---

### Peeking (Viewing Without Removal)
```java
Queue<Integer> q = new LinkedList<>();
Deque<Integer> dq = new ArrayDeque<>();
Stack<Integer> stack = new Stack<>();

// Peeking Queue (always front)
q.peek();           // View front, null if empty ← Preferred
q.element();        // View front, throws exception if empty

// Peeking Deque (flexible)
dq.peekFirst();     // View front, null if empty
dq.peekLast();      // View back, null if empty
dq.getFirst();      // View front, throws exception if empty
dq.getLast();       // View back, throws exception if empty

// Peeking Stack (always top)
stack.peek();       // View top, throws exception if empty ← Preferred
stack.firstElement(); // Same as peek()
```

---

## 7️⃣ Implementation Comparison

### Queue - Best Practice
```java
// ✅ PREFERRED: Use LinkedList as Queue
Queue<Integer> queue = new LinkedList<>();

queue.offer(1);
queue.offer(2);
queue.offer(3);

while (!queue.isEmpty()) {
    int current = queue.poll();
    System.out.println(current);  // Output: 1, 2, 3
}
```

**Why LinkedList?**
- O(1) operations at both ends
- More memory efficient than array-based for queues
- Better for dynamic sizes

---

### Deque - Best Practice
```java
// ✅ PREFERRED: Use ArrayDeque as Deque
Deque<Integer> deque = new ArrayDeque<>();

// Use as Queue
deque.offer(1);  // or addLast(1)
deque.poll();    // or pollFirst()

// Use as Stack
deque.push(1);   // or addFirst(1)
deque.pop();     // or removeFirst()

// Both ends operations
deque.peekFirst();  // View front
deque.peekLast();   // View back
```

**Why ArrayDeque?**
- Faster than Stack for Stack operations
- More versatile than Stack (can do Deque operations)
- Better cache locality (array-based)
- **ArrayDeque is recommended over Stack in modern Java**

---

### Stack - Best Practice
```java
// ❌ OLD WAY
Stack<Integer> stack = new Stack<>();  // Extends Vector, synchronized, slower

// ✅ MODERN WAY
Deque<Integer> stack = new ArrayDeque<>();

stack.push(1);
stack.push(2);
stack.push(3);

while (!stack.isEmpty()) {
    int top = stack.pop();
    System.out.println(top);  // Output: 3, 2, 1
}
```

**Why ArrayDeque over Stack?**
- `Stack` extends `Vector` (legacy, synchronized overhead)
- `ArrayDeque` is faster, more efficient
- Modern preference: Always use `ArrayDeque` for stack behavior

---

## 8️⃣ Common Patterns & Use Cases

### Pattern 1: BFS (Breadth-First Search) - Queue
```java
public void bfs(TreeNode root) {
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        TreeNode current = queue.poll();
        // Process current node
        
        if (current.left != null) queue.offer(current.left);
        if (current.right != null) queue.offer(current.right);
    }
}
```

---

### Pattern 2: DFS (Depth-First Search) - Stack
```java
public void dfs(TreeNode root) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(root);
    
    while (!stack.isEmpty()) {
        TreeNode current = stack.pop();
        // Process current node
        
        if (current.right != null) stack.push(current.right);
        if (current.left != null) stack.push(current.left);
    }
}
```

---

### Pattern 3: Sliding Window Maximum - Deque
```java
public int[] maxSlidingWindow(int[] nums, int k) {
    Deque<Integer> dq = new ArrayDeque<>();
    int[] result = new int[nums.length - k + 1];
    
    for (int i = 0; i < nums.length; i++) {
        // Remove indices outside current window
        while (!dq.isEmpty() && dq.peekFirst() < i - k + 1) {
            dq.pollFirst();
        }
        
        // Remove smaller elements from back
        while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) {
            dq.pollLast();
        }
        
        // Add current index
        dq.offer(i);
        
        // Store result when window is full
        if (i >= k - 1) {
            result[i - k + 1] = nums[dq.peekFirst()];
        }
    }
    
    return result;
}
```

---

### Pattern 4: Parentheses Matching - Stack
```java
public boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    
    for (char c : s.toCharArray()) {
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;
            char top = stack.pop();
            if (!matches(top, c)) return false;
        }
    }
    
    return stack.isEmpty();
}

private boolean matches(char open, char close) {
    return (open == '(' && close == ')') ||
           (open == '{' && close == '}') ||
           (open == '[' && close == ']');
}
```

---

## 9️⃣ Common Traps & Mistakes

### ❌ Trap 1: Using Stack Instead of ArrayDeque
```java
// ❌ OLD AND SLOWER
Stack<Integer> stack = new Stack<>();
stack.push(1);
stack.pop();

// ✅ MODERN AND FASTER
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1);
stack.pop();
```

---

### ❌ Trap 2: Forgetting to Check isEmpty()
```java
// ❌ WRONG
int top = stack.pop();  // NPE if stack is empty!

// ✅ CORRECT
if (!stack.isEmpty()) {
    int top = stack.pop();
}
```

---

### ❌ Trap 3: Using Wrong Operation Direction
```java
// ❌ WRONG - Adding to wrong end
Queue<Integer> q = new LinkedList<>();
q.addFirst(5);  // Queue doesn't have addFirst!

// ✅ CORRECT - Use only standard Queue methods
q.offer(5);     // Add to back
q.poll();       // Remove from front
```

---

### ❌ Trap 4: Mixing poll() and pop()
```java
// ❌ CONFUSING - Different exception behavior
int x = q.remove();      // Throws exception if empty
int y = stack.pop();     // Throws exception if empty

// ✅ CONSISTENT - Use safer methods
int a = q.poll();        // Returns null if empty
int b = stack.isEmpty() ? -1 : stack.pop();
```

---

### ❌ Trap 5: Not Understanding Deque Flexibility
```java
// ❌ Wrong - Trying to use Queue methods with Deque
Deque<Integer> dq = new ArrayDeque<>();
dq.add(1);
dq.remove();  // Removes from front (FIFO), but ambiguous

// ✅ CORRECT - Be explicit about direction
dq.addLast(1);      // Add to back
dq.removeFirst();   // Remove from front
// or
dq.addFirst(1);     // Add to front
dq.removeLast();    // Remove from back
```

---

## 🔟 Time & Space Complexity

### Queue Operations
```
Operation        | Time  | Space
-----------------|-------|-------
offer()          | O(1)  | O(1)
poll()           | O(1)  | O(1)
peek()           | O(1)  | O(1)
isEmpty()        | O(1)  | O(1)
BFS of n nodes   | O(n)  | O(w) where w = max width
```

---

### Deque Operations
```
Operation        | Time  | Space
-----------------|-------|-------
addFirst()       | O(1)  | O(1)
addLast()        | O(1)  | O(1)
removeFirst()    | O(1)  | O(1)
removeLast()     | O(1)  | O(1)
peekFirst()      | O(1)  | O(1)
peekLast()       | O(1)  | O(1)
Sliding Window   | O(n)  | O(k) where k = window size
```

---

### Stack Operations
```
Operation        | Time  | Space
-----------------|-------|-------
push()           | O(1)  | O(1)
pop()            | O(1)  | O(1)
peek()           | O(1)  | O(1)
isEmpty()        | O(1)  | O(1)
DFS of n nodes   | O(n)  | O(h) where h = height
```

---

## 1️⃣1️⃣ Quick Decision Tree

```
START: What operation do I need?
│
├─ "Process in order received?" 
│  └─ YES → Queue (FIFO)
│
├─ "Need both ends flexibility?"
│  └─ YES → Deque (FIFO/LIFO both ends)
│
├─ "Process in reverse order?"
│  └─ YES → Stack (LIFO)
│
├─ "Level-order traversal?"
│  └─ YES → Queue (BFS)
│
├─ "Parentheses/matching problem?"
│  └─ YES → Stack (LIFO)
│
└─ "Sliding window with extremes?"
   └─ YES → Deque (manipulate both ends)
```

---

## 1️⃣2️⃣ Standard Code Templates

### Queue Template (BFS Pattern)
```java
Queue<T> queue = new LinkedList<>();
queue.offer(startElement);

while (!queue.isEmpty()) {
    T current = queue.poll();
    
    // Process current
    
    // Add neighbors/children
    if (hasNext) {
        queue.offer(nextElement);
    }
}
```

---

### Deque Template (Flexible Operations)
```java
Deque<T> deque = new ArrayDeque<>();

// Add/Remove from both ends as needed
deque.addFirst(element);
deque.addLast(element);

while (!deque.isEmpty()) {
    T front = deque.pollFirst();
    T back = deque.pollLast();
}
```

---

### Stack Template (DFS Pattern)
```java
Deque<T> stack = new ArrayDeque<>();
stack.push(startElement);

while (!stack.isEmpty()) {
    T current = stack.pop();
    
    // Process current
    
    // Add to stack
    if (hasNext) {
        stack.push(nextElement);
    }
}
```

---

## 1️⃣3️⃣ My Personal Notes (Critical Section)

### Mental Shortcuts:
> - **Queue = Order Matters** (First come, first served)
> - **Deque = Flexibility** (Both ends available)
> - **Stack = Reversal** (Last one gets served first)

### Implementation Shortcuts:
```java
// Always use these:
Queue<T> q = new LinkedList<>();      // For Queue
Deque<T> dq = new ArrayDeque<>();     // For Deque (or Stack)
// NEVER use: Stack<T> (outdated)
```

### Interview Phrasing:
- "I'll use a **Queue** for BFS to process nodes level-by-level"
- "I'll use a **Deque** to maintain both ends for the sliding window"
- "I'll use a **Stack** to match opening/closing pairs"

### Key Differences Summary:
| | Queue | Deque | Stack |
|---|---|---|---|
| **Add** | Back only | Both ends | Top only |
| **Remove** | Front only | Both ends | Top only |
| **Best For** | FIFO order, BFS | Flexible, bidirectional | Reversal, DFS |
| **Java** | `LinkedList` | `ArrayDeque` | `ArrayDeque` |

---

## 1️⃣4️⃣ Quick Checklist Before Coding

```
Queue:
✓ Do I need FIFO order? (First In, First Out)
✓ Am I doing BFS or level-order traversal?
✓ Did I use offer() and poll() for safety?
✓ Did I check isEmpty() before operations?

Deque:
✓ Do I need to add/remove from both ends?
✓ Am I using explicit addFirst/addLast/pollFirst/pollLast?
✓ Did I avoid ambiguous add()/remove()?
✓ Did I check isEmpty() before operations?

Stack:
✓ Am I using ArrayDeque, not Stack?
✓ Do I need LIFO order? (Last In, First Out)
✓ Am I matching/reversing pairs correctly?
✓ Did I check isEmpty() before pop()?
```

---

## 📋 Quick Reference - When to Use What

```
BFS / Level-Order Traversal → Queue
├─ Tree/Graph level-order
├─ Shortest path problems
└─ Multi-source BFS

DFS / Recursive Simulation → Stack (ArrayDeque)
├─ Tree/Graph deep traversal
├─ Backtracking problems
└─ Expression evaluation

Sliding Window with Extremes → Deque
├─ Maximum/minimum in window
├─ Monotonic deque problems
└─ Bidirectional processing

Matching/Validation → Stack (ArrayDeque)
├─ Balanced parentheses
├─ Valid expressions
└─ Tag matching

Task Scheduling / Ordering → Queue
├─ Job queue
├─ Print queue
└─ Task processing in order
```

---

**Remember:** Master these three, and you'll handle 80% of interview data structure questions! 🚀

