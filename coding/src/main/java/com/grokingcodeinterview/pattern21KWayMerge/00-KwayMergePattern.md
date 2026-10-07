# 🔹 Interview Coding Pattern – K-Way Merge

## Table of Contents
- [Pattern Name](#pattern-name)
- [When to Use This Pattern (Recognition Signals)](#when-to-use-this-pattern-recognition-signals)
- [Core Idea (Mental Model)](#core-idea-mental-model)
- [Standard Code Template (Skeleton)](#standard-code-template-skeleton)
- [Key Decisions to Make (Interview Gold)](#key-decisions-to-make-interview-gold)
- [Common Traps & Mistakes](#common-traps--mistakes)
- [Time & Space Complexity](#time--space-complexity)
- [Canonical Problems (Must-Know)](#canonical-problems-must-know)
- [My Personal Notes (Critical Section)](#my-personal-notes-critical-section)
- [Quick Checklist Before Coding](#quick-checklist-before-coding)

---

## Pattern Name

**K-Way Merge Pattern** – Efficiently merge K sorted sources using a Min-Heap

---

## When to Use This Pattern (Recognition Signals)

- You have **K sorted lists/arrays/streams** that need to be merged
- You need to find the **Kth smallest element** across multiple sorted sources
- Problems require processing elements in **globally sorted order** from multiple inputs
- You want to avoid re-sorting or scanning lists repeatedly
- Memory efficiency matters when K is large

### Complexity Hint:
- **Brute force (re-sort):** O(N log N) – throws away the sorted property
- **K-Way Merge:** O(N log K) – respects the sorted input

### One-Line Trigger Thought:
> "Multiple sorted inputs? Use a min-heap to track the 'frontier' and always grab the globally smallest next element."

---

## Core Idea (Mental Model)

Imagine you have K sorted lines at a grocery store. Instead of walking to each line to see who's next, you have a **smart manager (min-heap)** who:

1. Looks at the **first person in each line**
2. Tells you who the smallest person (globally) is
3. Processes that person
4. Looks at the **next person in that same line**
5. Updates the heap and repeats

**Why this works:**
- Each list is already sorted, so the next element from a list is always the only new candidate worth considering from that list
- The min-heap keeps track of just the "frontier" (one element per list)
- Heap operations are O(log K), so total time is O(N log K) instead of O(N log N)

**The magic:** You never need to merge everything into one array. You stream the result from the heap.

---

## Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

```java
import java.util.PriorityQueue;

// Entry class to track elements with their source list and position
public static class Entry {
    int value;
    int listIndex;      // which list this came from
    int elementIndex;   // position in that list
    
    public Entry(int value, int listIndex, int elementIndex) {
        this.value = value;
        this.listIndex = listIndex;
        this.elementIndex = elementIndex;
    }
}

public static List<Integer> solveKWayMerge(List<List<Integer>> lists) {
    // 1. Create min-heap comparing by value
    PriorityQueue<Entry> minHeap = new PriorityQueue<>((a, b) -> a.value - b.value);
    
    // 2. Add the first element of each list to the heap
    for (int i = 0; i < lists.size(); i++) {
        if (!lists.get(i).isEmpty()) {
            minHeap.offer(new Entry(lists.get(i).get(0), i, 0));
        }
    }
    
    List<Integer> result = new ArrayList<>();
    
    // 3. While heap is not empty
    while (!minHeap.isEmpty()) {
        // Poll the smallest element
        Entry current = minHeap.poll();
        result.add(current.value);  // Process current element
        
        // Add the next element from the same list
        int listIndex = current.listIndex;
        int nextIndex = current.elementIndex + 1;
        
        if (nextIndex < lists.get(listIndex).size()) {
            int nextValue = lists.get(listIndex).get(nextIndex);
            minHeap.offer(new Entry(nextValue, listIndex, nextIndex));
        }
    }
    
    return result;
}
```

⚠️ **Important:** Keep this structure. Only fill in the logic.

---

## Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently.**

1. **What data structure should the Entry class store?**
    - The VALUE (for heap comparison)
    - The LIST INDEX (to know which source list it came from)
    - The ELEMENT INDEX (to know where to get the next element from that list)
    - **Exception — linked lists:** don't build an Entry wrapper at all. Push the `Node` itself into the heap (comparator on `node.data`/`node.val`). The node's own `.next` pointer already *is* the position tracker, so when you poll a node just do `if (node.next != null) minHeap.offer(node.next);`. The Entry-with-index wrapper is only needed for **arrays**, since arrays have no built-in "next" pointer.

2. **What should the heap comparison be?**
    - Min-heap for ascending merge (smallest first)
    - Max-heap for descending merge (largest first)
    - Compare by `a.value - b.value` (min-heap)

3. **When do I stop adding elements from a list?**
    - When `elementIndex + 1 >= list.size()`
    - Check this AFTER polling and before adding the next

4. **How do I handle empty lists?**
    - Check `if (!list.isEmpty())` before adding the first element to the heap
    - Never add null or empty lists directly

5. **What's the result structure?**
    - For merge problems: A single sorted list/array
    - For Kth smallest: Just return the value when count == K
    - For streaming: Process each polled element as it comes

---

## Common Traps & Mistakes

👉 **Tip:** This section gets more valuable over time as you add your personal pitfalls.

- **Trap 1: Forgetting to add the next element**
    - ❌ You poll from heap but never add the next element → heap becomes empty too soon
    - ✅ Always add `list[i][elementIndex+1]` immediately after processing element

- **Trap 2: Not tracking list and element indices**
    - ❌ Storing only values in heap → you lose track of which list to pull from next
    - ✅ Always store Entry with value, listIndex, and elementIndex

- **Trap 3: Adding multiple elements from the same list at once**
    - ❌ Pushing both next and next-next elements → defeats the pattern
    - ✅ Only push ONE element per list at a time; let the heap decide ordering

- **Trap 4: Boundary conditions with empty lists**
    - ❌ Crash when accessing `lists.get(i).get(0)` on empty list
    - ✅ Always check `!isEmpty()` before accessing first element

- **Trap 5: Off-by-one errors with indices**
    - ❌ Using `elementIndex` instead of `elementIndex + 1` for next
    - ✅ Next element is always `elementIndex + 1`, check `< list.size()`

- **Trap 6: Mixing up `listIndex` vs `elementIndex` as the argument to `list.get(...)`**
    - ❌ `list.get(elementIndex).get(elementIndex + 1)` or `list.get(nextListIndex).get(nextIndex)` where `nextListIndex = current.listIndex + 1` — accidentally jumps to a different list, or indexes the outer `.get()` with the wrong variable
    - ✅ The outer `.get(...)` always selects **which list** → use `listIndex` (and it must stay the *same* `listIndex` as the popped Entry, never incremented). The inner `.get(...)` always selects **position within that list** → use `elementIndex + 1`. Correct form: `list.get(listIndex).get(elementIndex + 1)`
    - Recurring bug from 22-2 (Kth Smallest in M Sorted Lists) — hit this three separate times in one sitting, so drill it: *outer get = which list (unchanged), inner get = which position (incremented).*

---

## Time & Space Complexity

**Time:** O(N log K)
- N = total elements across all lists
- K = number of lists
- Each of N elements is inserted and removed from heap → O(N) operations
- Each heap operation (insert/remove) is O(log K)
- Total: O(N log K)

**Space:** O(K)
- The heap stores at most K elements (one per list, the current frontier)
- Entry objects store metadata but constant size
- Result array/list can be O(N) depending on problem

**Explain why in one line:**
> We process each element exactly once via the heap, and heap operations are O(log K) because we only keep K elements in it at any time.

---

## Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

**Problem 1: Merge K Sorted Lists**
- Name: Merge K Sorted Linked Lists
- Key insight: Use Entry class with Node references; attach to dummy node as you poll
- Example: `L1=[2,6,8], L2=[3,6,7], L3=[1,3,4]` → `[1,2,3,3,4,6,6,7,8]`

**Problem 2: Kth Smallest Element in M Sorted Lists**
- Name: Find Kth Smallest Number in M Sorted Lists
- Key insight: Stop when `count == K` and return immediately; no need to merge everything
- Example: `L1=[2,6,8], L2=[3,6,7], L3=[1,3,4], K=5` → `4`

**Problem 3: Merge K Sorted Arrays** (variant)
- Name: Merge K Sorted Arrays
- Key insight: Similar to linked lists but with arrays; Entry stores array index and position
- Example: `A1=[1,3], A2=[2,4], A3=[5,6]` → `[1,2,3,4,5,6]`

**Problem 4: Median of M Sorted Arrays**
- Name: Median of M Sorted Arrays
- Key insight: Same frontier-heap mechanics as Kth Smallest — but instead of stopping at a fixed K, first compute `totalCount = sum of all array lengths`, then poll until you reach position `totalCount/2` (and `totalCount/2 - 1` too if `totalCount` is even, to average the two middle values)
- Example: `A1=[1,3], A2=[2], A3=[5,6], totalCount=5` → median is the 3rd smallest = `3`

---

## My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> "K-way merge = one frontier element per list in a min-heap. Poll smallest, push next from its list."

### Interview Phrasing:
> "We use a min-heap to efficiently track the next smallest element across all K lists. At any moment, the heap contains exactly one element from each list—the current 'frontier.' When we process an element, we immediately add the next one from its list, maintaining the invariant."

### My Favorite Variation:
> **Max-Heap Variant:** For finding K largest elements, flip the heap to max-heap and stop at K or early-exit when heap size exceeds K.

### What I Always Forget:
> **Off-by-one indices:** Always double-check `elementIndex + 1 < list.size()` before accessing. It's easy to forget the `<` (not `<=`) and cause out-of-bounds.

### Quick Debugging Checklist:
- [ ] Entry class has value, listIndex, elementIndex?
- [ ] Heap initialized as min-heap with correct comparator?
- [ ] First elements of all non-empty lists added to heap?
- [ ] After polling, next element from same list is added?
- [ ] Loop continues while heap is not empty?
- [ ] Empty lists handled gracefully (no null pointer exceptions)?
- [ ] Result collected in correct order from heap pops?

---

## Quick Checklist Before Coding

- **Do I understand the input structure?** (arrays? linked lists? streams?)
- **Is the input definitely sorted?** (K-way merge only works on sorted inputs)
- **How many sources (K)?** (Small K = very fast; large K = still good, but slower than merge)
- **What should I return?** (Merged list? Kth element? Count?)
- **Edge cases ready?**
    - [ ] Empty lists
    - [ ] Single list
    - [ ] K = 1
    - [ ] K = number of total elements
- **Did I define the Entry class to store metadata?**
- **Did I verify the heap can extract minimum in O(log K)?**