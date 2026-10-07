# 🔹 Interview Coding Pattern – Monotonic Stack

---

## 1️⃣ Pattern Name

**Monotonic Stack Pattern** – An optimized variation of Stack that maintains elements in monotonic (increasing or decreasing) order

---

## 2️⃣ When to Use This Pattern (Recognition Signals)

- ✅ **Next Greater/Smaller Element** → Find the next/previous element that is greater or smaller than current
- ✅ **Daily Temperatures** → Distance to next warmer day
- ✅ **Stock Span** → Number of consecutive days with price ≤ current
- ✅ **Largest Rectangle in Histogram** → Maximum rectangular area
- ✅ **Trapping Rain Water** → Water trapped between bars
- ✅ **Remove Duplicates** → Adjacent duplicate removal maintaining order
- ✅ **Asteroid Collision** → Collision detection in linear sequence
- ✅ **Decode String** → Processing nested structures with undo capability

### Complexity Hint:
- **Time:** O(n) – Each element is pushed and popped at most once
- **Space:** O(n) – Stack stores up to n elements in worst case

### One-Line Trigger Thought:
> **"Do I need to find next/previous greater/smaller element OR maintain relative order?"** → Monotonic Stack is your answer.

---

## 3️⃣ Core Idea (Mental Model)

A **Monotonic Stack** maintains elements in a **specific order** (either increasing or decreasing). Unlike regular stacks:

**Key Properties:**
1. **Maintains Monotonic Property** – Stack elements are always in increasing or decreasing order
2. **Optimal for Comparisons** – Top element is always your reference for comparison
3. **Efficient Pair Finding** – Find relationships between elements in single pass O(n)
4. **Visual Analogy:** Think of **building heights in a skyline** where you only keep visible peaks

**Why It Works:**
- When you encounter an element that breaks the monotonic order, all previous elements less important than it can be discarded
- You can process and store results as you pop, getting O(n) instead of O(n²)

**Rule:** If you can't explain it simply, you don't understand it yet.

---

## 4️⃣ Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

```java
import java.util.*;

public class MonoticStackProblem {
    
    // Choose DECREASING for Finding GREATER
    // Choose INCREASING for Finding SMALLER
    public static int[] solveProblem(int[] nums) {
        Deque<Integer> stack = new ArrayDeque<>();  // Use Deque, not Stack
        int[] result = new int[nums.length];
        
        // 1️⃣ ITERATION: Process each element
        for (int i = 0; i < nums.length; i++) {
            
            // 2️⃣ DECISION: Pop when condition is met
            // FOR DECREASING STACK (finding greater): pop when nums[i] > nums[stack.peek()]
            // FOR INCREASING STACK (finding smaller): pop when nums[i] < nums[stack.peek()]
            while (!stack.isEmpty() && shouldPopCondition(nums[i], nums[stack.peek()])) {
                
                // 3️⃣ ACTION: Process popped element
                int poppedIdx = stack.pop();
                result[poppedIdx] = calculateResult(i, poppedIdx, nums);
            }
            
            // 4️⃣ PUSH: Add current index/value to stack
            stack.push(i);
        }
        
        // 5️⃣ CLEANUP: Handle remaining elements (usually -1 or 0)
        while (!stack.isEmpty()) {
            result[stack.pop()] = DEFAULT_VALUE;  // -1, 0, etc.
        }
        
        return result;
    }
    
    private static boolean shouldPopCondition(int current, int stackTop) {
        // DECREASING: return current > stackTop;
        // INCREASING: return current < stackTop;
        return false;
    }
    
    private static int calculateResult(int currentIdx, int poppedIdx, int[] nums) {
        // Calculate based on problem requirements
        // Could be the value, distance, or any computation
        return 0;
    }
    
    private static final int DEFAULT_VALUE = -1;
}
```

⚠️ **Important:** Keep this structure. Only change the comparison condition and result calculation.

---

## 5️⃣ Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently.**

1. **Which Stack Type?**
   - Ask yourself: "What am I looking for?"
     - Looking for GREATER? → Use decreasing stack (pop smaller when you find greater)
     - Looking for SMALLER? → Use increasing stack (pop larger when you find smaller)

   - **Decreasing Stack** → Finding **NEXT/PREVIOUS GREATER** elements
   - **Increasing Stack** → Finding **NEXT/PREVIOUS SMALLER** elements
   - **Remember:** "INCREASING finds SMALLER" (both have 'S'), "DECREASING finds GREATER" (both have 'G')
   
2. **What Goes in Stack?**
   - **Indices** (most common) → Allows you to calculate distances or store results by index
   - **Values** (sometimes) → Direct element comparison
   - **Objects** (complex) → Store pairs of (index, value) for complex problems

3. **What's the Pop Condition?**
   - **Decreasing Stack:** `nums[i] > nums[stack.peek()]` → Pop when you find larger element
   - **Increasing Stack:** `nums[i] < nums[stack.peek()]` → Pop when you find smaller element
   - **Always check:** `!stack.isEmpty()` before peeking

4. **How to Calculate Result?**
   - **Find Value:** `nums[i]` (the element that caused the pop)
   - **Find Distance:** `i - poppedIdx` (how far away)
   - **Find Count:** `i - lastIndex[stack.peek()]` (count of elements)
   - **Complex:** Combination of above

5. **What about Remaining Elements?**
   - Elements left in stack = **no greater/smaller element found**
   - Usually set to: `-1` (not found), `0` (no days), or skip entirely

---

## 6️⃣ Common Traps & Mistakes

👉 **Tip:** This section gets more valuable over time as you add your personal pitfalls.

### ❌ Trap 1: Using Stack Instead of Deque
```java
// ❌ WRONG - Stack has limited operations
Stack<Integer> stack = new Stack<>();

// ✅ CORRECT - Deque is more efficient
Deque<Integer> stack = new ArrayDeque<>();
```

### ❌ Trap 2: Wrong Comparison Operator
```java
// ❌ WRONG - This creates INCREASING stack, won't find greater
while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
    // This is for finding SMALLER, not GREATER!
}

// ✅ CORRECT - For DECREASING stack finding GREATER
while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
    result[stack.pop()] = nums[i];
}
```

### ❌ Trap 3: Forgetting to Check isEmpty()
```java
// ❌ WRONG - Crash on empty stack
int top = nums[stack.peek()];

// ✅ CORRECT - Always check first
if (!stack.isEmpty()) {
    int top = nums[stack.peek()];
}
```

### ❌ Trap 4: Not Handling Remaining Elements
```java
// ❌ WRONG - Leaves result array with garbage values
for (int i = 0; i < nums.length; i++) { /* ... */ }
return result;

// ✅ CORRECT - Set remaining to default value
while (!stack.isEmpty()) {
    result[stack.pop()] = DEFAULT_VALUE;
}
return result;
```

### ❌ Trap 5: Confusing Direction (Left-to-Right vs Right-to-Left)
```java
// ❌ WRONG - Iterating backwards when problem needs forward
for (int i = nums.length - 1; i >= 0; i--) { /* ... */ }

// ✅ CORRECT - Understand the problem requirement
// Most problems go left-to-right for "Next" Greater
// Right-to-left for "Previous" Greater
for (int i = 0; i < nums.length; i++) { /* ... */ }
```

### ❌ Trap 6: Off-by-One in Distance Calculation
```java
// ❌ WRONG - Calculates wrong distance
result[poppedIdx] = i;  // This is the index, not distance!

// ✅ CORRECT - Calculate actual distance
result[poppedIdx] = i - poppedIdx;
```

### ❌ Trap 7: Pushing Too Early
```java
// ❌ WRONG - Pushes before processing all comparisons
if (condition) {
    stack.push(i);
    // But what if we should pop this too?
}

// ✅ CORRECT - Pop all necessary elements, THEN push
while (!stack.isEmpty() && shouldPop()) {
    process(stack.pop());
}
stack.push(i);  // Only after all pops
```

---

## 7️⃣ Time & Space Complexity

**Time:** O(n) – Each element is pushed once and popped at most once

**Space:** O(n) – Stack can contain up to n elements in worst case (strictly increasing/decreasing array)

**Explain why in one line:**
> While the inner `while` loop looks like it could cause O(n²), each element is only pushed and popped **exactly once** across the entire iteration, giving us amortized O(n) time with O(n) space for the stack.

---

## 8️⃣ Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

### **Problem 1: Next Greater Element**
- **Name:** Next Greater Element / Next Greater Element II
- **Key insight:** 
  - Use **DECREASING** stack to maintain candidates for "greater"
  - Pop when you find a larger element → it's the NGE
  - Store **indices** in stack
  - Remaining elements get `-1`
- **Pattern:** Basic greater element finding
- **Complexity:** O(n) time, O(n) space
- **Code Signature:** `while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) { result[stack.pop()] = nums[i]; }`

### **Problem 2: Daily Temperatures**
- **Name:** Daily Temperatures / Next Greater Element with Distance
- **Key insight:** 
  - Same as NGE but calculate **distance instead of value**
  - Use DECREASING stack (comparing temperatures)
  - Result is `i - poppedIdx` (days to wait)
  - Remaining elements get `0` (no warmer day)
- **Pattern:** Converting "next element" to "distance to next"
- **Complexity:** O(n) time, O(n) space
- **Code Signature:** `result[poppedIdx] = i - poppedIdx;`

### **Problem 3: Remove All Adjacent Duplicates in String**
- **Name:** Remove All Adjacent Duplicates / Remove Duplicates
- **Key insight:** 
  - Use stack for **character-by-character** comparison
  - Pop if `stack.peek() == currentChar` (adjacent duplicate found)
  - Push if different or stack empty
  - Not traditional monotonic but same stack principle
  - Build result from remaining stack elements
- **Pattern:** String manipulation with undo capability
- **Complexity:** O(n) time, O(n) space
- **Code Signature:** `if (!stack.isEmpty() && stack.peek() == ch) { stack.pop(); } else { stack.push(ch); }`

---

## 9️⃣ My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut - The "S" and "G" Rule:
 Ask yourself: "What am I looking for?
- 🔴 Looking for **GREATER**? →
  - Use **decreasing** stack (pop smaller when you find greater)
-  🔵 Looking for **SMALLER**? → 
  - Use **increasing** stack (pop larger when you find smaller)

```
 
🔴 INCREASING Stack     → Find SMALLER  (both have 'S')
   Pop condition: current < stack.top

🔵 DECREASING Stack     → Find GREATER  (both have 'G')
   Pop condition: current > stack.top
```

### Interview Phrasing:
> "I'll use a **decreasing [or increasing] monotonic stack** to efficiently find the **next [or previous] greater [or smaller]** element. 
> This allows me to solve it in **O(n) time** instead of **O(n²)** by ensuring each element is processed only once."

### Visual Decision Flow:
```
Question: "Find next _____ element?"

        ↓
    Greater?  →  DECREASING stack  →  pop if current > peek
        ↓
    Smaller?  →  INCREASING stack  →  pop if current < peek
```

### Key Implementation Pattern:
```java
// DECREASING Stack (for finding GREATER)
Deque<Integer> stack = new ArrayDeque<>();
int[] result = new int[nums.length];

for (int i = 0; i < nums.length; i++) {
    // Pop all smaller elements
    while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
        result[stack.pop()] = nums[i];  // Found it!
    }
    stack.push(i);
}

// Remaining = -1 (not found)
while (!stack.isEmpty()) {
    result[stack.pop()] = -1;
}
```

### Common Variations & Their Differences:

| Variation | Direction | Stack Type | Pop Condition | Result Meaning |
|---|---|---|---|---|
| Next Greater | Left→Right | Decreasing | `>` | The value |
| Previous Greater | Right→Left | Decreasing | `>` | The value |
| Next Smaller | Left→Right | Increasing | `<` | The value |
| Previous Smaller | Right→Left | Increasing | `<` | The value |
| Distance to Greater | Left→Right | Decreasing | `>` | Distance (i - idx) |
| Occurrence Count | Left→Right | Decreasing | `>` | Count of elements |

### What I Always Forget:
> 1. **Use Deque, not Stack** – Stack().peek() is slower, use ArrayDeque
> 2. **`!stack.isEmpty()` FIRST** – Always check before peek() or pop()
> 3. **Push AFTER popping** – Pop all matches first, then push current
> 4. **Handle remaining stack** – Last loop for elements with no pair
> 5. **Test with edge cases** – Strictly increasing, strictly decreasing, duplicates
> 6. **Compare the right thing** – `nums[i]` vs `nums[stack.peek()]`, not `i` vs `stack.peek()`
> 7. **Verify direction** – "Next" = left-to-right, "Previous" = right-to-left

---

## 📋 Quick Checklist Before Coding

```
✓ Do I need to find GREATER or SMALLER?
  → GREATER = DECREASING stack
  → SMALLER = INCREASING stack

✓ Do I need to store indices or values?
  → Usually INDICES for calculating results

✓ What direction? (Left-to-right or right-to-left?)
  → "Next" = Left-to-right
  → "Previous" = Right-to-left

✓ What's the pop condition exactly?
  → DECREASING: nums[i] > nums[stack.peek()]
  → INCREASING: nums[i] < nums[stack.peek()]

✓ Check isEmpty() before every peek/pop?
  → All while conditions checked!

✓ Push after ALL pops completed?
  → Yes, push is the last operation in loop

✓ Handle remaining stack elements?
  → Set to appropriate default (-1, 0, etc.)

✓ Test edge cases?
  → Empty array, single element, all increasing, all decreasing, duplicates

✓ Correct result calculation?
  → Value? Distance? Count? What exactly?

✓ Return value or build from stack?
  → Pre-allocated array? String from stack? Other?

✓ Traced through with example?
  → Step-by-step with small array (3-5 elements)
```

---

## 🎯 Quick Reference

### Decision Matrix at a Glance

| Need to Find | Stack Type | Pop Condition | Example |
|:---|:---|:---|:---|
| **Next Greater** ↗️ | Decreasing | `nums[i] > nums[stack.peek()]` | Next Greater Element |
| **Next Smaller** ↙️ | Increasing | `nums[i] < nums[stack.peek()]` | Daily Temps (warmer) |
| **Previous Greater** | Decreasing | Same but R→L | Stock Span |
| **Previous Smaller** | Increasing | Same but R→L | Restaurant Ratings |
| **Max Rectangle** 📦 | Decreasing | Height comparison | Largest Rectangle in Histogram |
| **Adjacent Pair** | N/A | Equality check | Remove Adjacent Duplicates |

### Common Code Snippets

**Finding Next Greater:**
```java
while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
    result[stack.pop()] = nums[i];
}
```

**Finding Distance to Next Greater:**
```java
while (!stack.isEmpty() && temps[i] > temps[stack.peek()]) {
    result[stack.pop()] = i - stack.peek();
}
```

**Finding Next Smaller:**
```java
while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
    result[stack.pop()] = nums[i];
}
```

---

## 🚀 Pro Tips for Interviews

1. **Start by identifying:** GREATER or SMALLER?
2. **Choose stack type immediately:** DECREASING or INCREASING?
3. **Write pop condition first:** It guides everything else
4. **Then handle the push:** Usually just `stack.push(i)`
5. **Test with simple example:** 3-5 elements by hand
6. **Mention time complexity:** "O(n) because each element is processed once"
7. **Explain space:** "O(n) for the stack in worst case"

---

**Remember:**
- Monotonic Stack is a powerful optimization for problems involving comparisons and relationships. Master the "S & G Rule" and you can handle most problems! 🚀
- G → Decreasing stack
- S → Increasing stack

