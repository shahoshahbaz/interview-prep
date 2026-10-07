# 🔹 Interview Coding Pattern – Stack (LIFO)

---

## 1️⃣ Pattern Name

**Stack Pattern** – Last In, First Out (LIFO) Data Structure

---

## 2️⃣ When to Use This Pattern (Recognition Signals)

- ✅ **Bracket/Parenthesis matching** → Balanced parentheses, valid expressions
- ✅ **Processing in reverse order** → Reverse strings, reverse operations
- ✅ **Next/Previous element problems** → Next greater element, previous smaller element
- ✅ **Depth-first traversal** → Tree/graph DFS, expression evaluation
- ✅ **Undo/Redo functionality** → Browser history, text editor operations
- ✅ **Function call stack** → Recursion, expression parsing
- ✅ **Path simplification** → File path simplification, removing redundant operations
- ✅ **Temporary storage with LIFO property** → Keeping track of state that needs to be rolled back

### Complexity Hint:
- Most stack operations: **O(1)** per operation
- But the overall solution complexity depends on the problem (usually O(n))

### One-Line Trigger Thought:
> **"Do I need to process elements in reverse order or match opening/closing pairs?"** → Stack is your answer.

---

## 3️⃣ Core Idea (Mental Model)

Think of a stack as a **stack of plates in a cafeteria**:
- You add plates to the **top** (push)
- You remove plates from the **top** (pop)
- You can only see the **top plate** (peek)
- The last plate you placed is the first one you remove

**Key Properties:**
1. **LIFO (Last In, First Out)** – The most recently added element is removed first
2. **Simple operations** – Push, Pop, Peek are all O(1)
3. **Perfect for problems that need "backtracking"** – You can undo operations easily

**Rule:** If you can't explain it simply, you don't understand it yet.

---

## 4️⃣ Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

```java
import java.util.Stack;

public class StackProblem {
    
    public static <T> void solveProblem(T[] input) {
        Stack<T> stack = new Stack<>();
        
        // 1️⃣ ITERATION: Process each element
        for (T element : input) {
            
            // 2️⃣ DECISION: Check condition with stack top
            while (!stack.isEmpty() && shouldPop(stack.peek(), element)) {
                // 3️⃣ ACTION: Process or store result
                T popped = stack.pop();
                processPopped(popped, element);
            }
            
            // 4️⃣ PUSH: Add current element to stack
            stack.push(element);
        }
        
        // 5️⃣ CLEANUP: Handle remaining elements in stack
        while (!stack.isEmpty()) {
            T remaining = stack.pop();
            handleRemaining(remaining);
        }
    }
    
    private static boolean shouldPop(Object stackTop, Object current) {
        // Define your comparison logic here
        return false;
    }
    
    private static void processPopped(Object popped, Object current) {
        // Define what to do with popped elements
    }
    
    private static void handleRemaining(Object element) {
        // Define what to do with elements left in stack
    }
}
```

⚠️ **Important:** Keep this structure. Only fill in the logic.

---

## 5️⃣ Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently.**

1. **What goes in the stack?**
   - Raw elements? Or indices? Or pointers to elements?
   - Example: Next Greater Element stores **indices**, not values
   
2. **What's the comparison condition?**
   - When should we pop from stack?
   - When should we push to stack?
   - Example: For balanced parentheses, pop when you find closing bracket

3. **What do we store as result?**
   - In the stack itself? In a result array? In a map?
   - Example: Balanced parentheses stores result as boolean, NGE stores in result array

4. **What about remaining elements?**
   - Do we need to process elements left in the stack after iteration?
   - Example: In NGE, remaining elements have -1 as their answer

5. **Is the order important?**
   - Are we processing left-to-right? Right-to-left?
   - Example: Next Greater Element goes left-to-right, Previous Greater goes right-to-left

---

## 6️⃣ Common Traps & Mistakes

👉 **Tip:** This section gets more valuable over time as you add your personal pitfalls.

### ❌ Trap 1: Forgetting to Check if Stack is Empty
```java
// ❌ WRONG
char top = stack.pop();  // NPE if stack is empty!

// ✅ CORRECT
if (!stack.isEmpty()) {
    char top = stack.pop();
}
```

### ❌ Trap 2: Wrong Comparison Direction
```java
// ❌ WRONG - This gives NEXT SMALLER, not next greater
while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
    result[stack.pop()] = nums[i];
}

// ✅ CORRECT - This gives NEXT GREATER
while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
    result[stack.pop()] = nums[i];
}
```

### ❌ Trap 3: Storing Values Instead of Indices
```java
// ❌ WRONG - Can't track which element we're setting result for
stack.push(nums[i]);

// ✅ CORRECT - Store index, retrieve value with nums[index]
stack.push(i);
result[stack.pop()] = nums[i];
```

### ❌ Trap 4: Not Handling Edge Cases
- Empty input array
- Single element array
- All elements in decreasing/increasing order
- Duplicate elements

### ❌ Trap 5: Processing in Wrong Direction
```java
// Some problems need right-to-left processing
// Example: Previous Greater Element requires iterating backwards
for (int i = nums.length - 1; i >= 0; i--) {
    // Process
}
```

---

## 7️⃣ Time & Space Complexity

**Time:** O(n) – Each element is pushed and popped at most once

**Space:** O(n) – Stack can contain up to n elements in worst case

**Explain why in one line:**
> Each element is visited exactly twice (push once, pop once), and in worst case (strictly increasing array) all elements remain in stack, giving O(n) space.

---

## 8️⃣ Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

### **Problem 1: Balanced Parentheses**
- **Name:** Valid Parentheses / Balanced Parentheses
- **Key insight:** 
  - Use stack to match opening and closing brackets
  - On opening bracket → push to stack
  - On closing bracket → pop from stack and verify it matches
  - At end, stack must be empty
- **Pattern:** Matching pairs, validation
- **Complexity:** O(n) time, O(n) space

### **Problem 2: Next Greater Element**
- **Name:** Next Greater Element
- **Key insight:** 
  - Iterate through array left-to-right
  - **Store indices in stack**, not values
  - Pop when you find a larger element
  - Remaining elements = -1
- **Pattern:** Finding relationships between elements
- **Complexity:** O(n) time, O(n) space
- **Variant:** Previous Greater Element (iterate right-to-left)

### **Problem 3: Simplify Path**
- **Name:** Simplify File Path / Unix Path Simplification
- **Key insight:** 
  - Split path by "/"
  - Push directory names to stack
  - Pop on ".." (go up one directory)
  - Ignore "." (current directory) and empty strings (multiple slashes)
  - Reconstruct path from stack contents
- **Pattern:** State management with ability to undo
- **Complexity:** O(n) time, O(n) space

---

## 9️⃣ My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> **"Stack = Matching + Reversal + Undo"**
> - Use when you need to match pairs (parentheses)
> - Use when you need to process in reverse (reverse string)
> - Use when you need to undo/backtrack (path simplification)

### Interview Phrasing:
> "I'll use a stack to store... [values/indices] and pop whenever... [condition]. This allows me to efficiently find... [what you're looking for]."

### Key Implementation Pattern:
```java
// Standard flow for most stack problems
Stack<Integer> stack = new Stack<>();

for (int i = 0; i < nums.length; i++) {
    // While condition defines when to pop
    while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
        result[stack.pop()] = nums[i];
    }
    // Push after popping all smaller elements
    stack.push(i);
}

// Handle remaining elements
while (!stack.isEmpty()) {
    result[stack.pop()] = DEFAULT_VALUE;  // Usually -1
}
```

### Common Variations:
1. **Store values vs indices:** NGE stores indices, Balanced Parentheses stores chars
2. **Iterate direction:** Left-to-right for NGE, right-to-left for PGE
3. **Comparison operators:** `>` for greater, `<` for smaller
4. **Result storage:** Array, boolean, string, or just side effects

### What I Always Forget:
> 1. **Check isEmpty() before pop()** – Always!
> 2. **Handle remaining elements** – Don't forget elements left in stack after main loop
> 3. **Test with edge cases** – Empty, single element, all increasing/decreasing arrays
> 4. **Verify direction** – Am I iterating the right way? Do I need reverse?

---

## 📋 Quick Checklist Before Coding

```
✓ Did I understand what goes into the stack? (values or indices?)
✓ Did I define the pop condition clearly?
✓ Did I check isEmpty() before every pop()?
✓ Did I handle elements remaining in stack after main loop?
✓ Did I verify iteration direction? (left-to-right vs right-to-left)
✓ Did I test with edge cases? (empty, single, all increasing, all decreasing)
✓ Did I return/store the result correctly?
✓ Is my comparison operator correct? (> or < or ==)
✓ Did I verify with the given examples?
```

---

## 🎯 Quick Reference

| Problem Type | Key Decision | Comparison | Direction |
|---|---|---|---|
| Balanced Parentheses | Store char/type | Match pairs | Left-to-right |
| Next Greater Element | Store indices | `>` | Left-to-right |
| Previous Greater Element | Store indices | `>` | Right-to-left |
| Simplify Path | Store directory names | Special logic | Left-to-right |
| Reverse String | Store characters | N/A | Left-to-right |
| Sorting Stack | Store values | `<` | Iterative building |

---

**Remember:** Master the template, understand the core idea, and most stack problems become straightforward! 🚀

