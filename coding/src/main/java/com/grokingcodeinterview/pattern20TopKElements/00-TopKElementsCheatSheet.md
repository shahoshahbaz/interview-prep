# 🔹 Interview Coding Pattern – Top K Elements – Cheat Sheet

---

## 📑 Table of Contents

1. [Pattern Name](#1️⃣-pattern-name)
2. [When to Use This Pattern](#2️⃣-when-to-use-this-pattern-recognition-signals)
3. [Core Idea (Mental Model)](#3️⃣-core-idea-mental-model)
4. [Standard Code Templates](#4️⃣-standard-code-template-skeleton)
5. [Key Decisions to Make](#5️⃣-key-decisions-to-make-interview-gold)
6. [Common Traps & Mistakes](#6️⃣-common-traps--mistakes)
7. [Time & Space Complexity](#7️⃣-time--space-complexity)
8. [Bucket Sort — O(n) Alternative](#🪣-bucket-sort--on-alternative-frequency-based-problems)
9. [Canonical Problems (Must-Know)](#8️⃣-canonical-problems-must-know)
10. [My Personal Notes](#9️⃣-my-personal-notes-critical-section)
11. [Quick Checklist Before Coding](#📋-quick-checklist-before-coding)
12. [Related Patterns & When to Switch](#📚-related-patterns--when-to-switch)
13. [Quick Links to Problems in This Package](#🔗-quick-links-to-problems-in-this-package)
14. [Final Wisdom](#✨-final-wisdom)

---

## 1️⃣ Pattern Name

**Top K Elements Pattern** (Heap-based Selection)

---

## 2️⃣ When to Use This Pattern (Recognition Signals)

### ✅ You see these keywords:
- "Find **K largest** / **K smallest**"
- "Find **Kth** largest / smallest"
- "Find **top K** frequent"
- "Find **K closest** to target"
- "Merge **K sorted** lists"
- "**Scheduler** with priority"
- "Stream of numbers, find **top K at any time**"

### Complexity Hint:
- **Array with K selections** → O(n log k) with heap
- **Better than** O(n log n) sorting in most cases when k << n

### One-Line Trigger Thought:
> *"I need to track the top K elements and discard the rest efficiently."*

---

## 3️⃣ Core Idea (Mental Model)

### 🎯 **The Golden Question:**
**"What do I want ON TOP of the heap?"**

That's it. Once you answer this, choosing the correct heap becomes automatic.

### 📊 **Rule of Thumb:**

| Want to find... | Heap Type | Why? | Top of Heap |
|---|---|---|---|
| **K largest** | Min-Heap | Keep worst (smallest) of K largest on top to replace | Smallest of K |
| **K smallest** | Max-Heap | Keep worst (largest) of K smallest on top to replace | Largest of K |
| **K closest** | Max-Heap | Keep farthest distance on top | Farthest distance |
| **K frequent** | Min-Heap | Keep least frequent of K on top | Least frequent |
| **Merge K sorted** | Min-Heap | Always get next smallest element | Smallest value |

### **The Two Strategies:**

1. **Gatekeeper Strategy (Max-Heap for K smallest)**
   - Maintain K smallest elements in a **max-heap**
   - Top of heap = **worst** (largest) of the K
   - When new element < heap.peek(), remove top and insert new
   - Final answer: K smallest ✅

2. **Next-Best Provider Strategy (Min-Heap for K largest)**
   - Maintain K largest elements in a **min-heap**
   - Top of heap = **worst** (smallest) of the K
   - When new element > heap.peek(), remove top and insert new
   - Final answer: K largest ✅

---

## 4️⃣ Standard Code Template (Skeleton)

### **Template 1: Find K Largest Numbers (Min-Heap)**

```java
public List<Integer> findKLargestNumbers(int[] nums, int k) {
    // Step 1: Create min-heap (natural ordering)
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    
    // Step 2: Insert first K elements
    for (int i = 0; i < Math.min(k, nums.length); i++) {
        minHeap.add(nums[i]);
    }
    
    // Step 3: For remaining elements, keep only if > heap top
    for (int i = k; i < nums.length; i++) {
        if (nums[i] > minHeap.peek()) {
            minHeap.poll();
            minHeap.add(nums[i]);
        }
    }
    
    // Step 4: Return result
    return new ArrayList<>(minHeap);
}
```

### **Template 2: Find K Smallest Numbers (Max-Heap)**

```java
public int findKthSmallestNumber(int[] nums, int k) {
    // Step 1: Create max-heap with custom comparator
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
    
    // Step 2: Insert first K elements
    for (int i = 0; i < Math.min(k, nums.length); i++) {
        maxHeap.add(nums[i]);
    }
    
    // Step 3: For remaining elements, keep only if < heap top
    for (int i = k; i < nums.length; i++) {
        if (nums[i] < maxHeap.peek()) {
            maxHeap.poll();
            maxHeap.add(nums[i]);
        }
    }
    
    // Step 4: Return Kth smallest (top of max-heap)
    return maxHeap.peek();
}
```

### **Template 3: Custom Objects (with Comparator)**

```java
public List<Integer> topKFrequent(int[] nums, int k) {
    // Step 1: Count frequencies
    Map<Integer, Integer> frequencyMap = new HashMap<>();
    for (int num : nums) {
        frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
    }
    
    // Step 2: Create min-heap with custom comparator (by frequency)
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(
        (a, b) -> frequencyMap.get(a) - frequencyMap.get(b)
    );
    
    // Step 3: Maintain K frequent elements
    for (int num : frequencyMap.keySet()) {
        minHeap.add(num);
        if (minHeap.size() > k) {
            minHeap.poll();
        }
    }
    
    // Step 4: Return result
    return new ArrayList<>(minHeap);
}
```

⚠️ **Important:** Keep this structure. Only fill in the logic details (like custom comparators).

---

## 5️⃣ Key Decisions to Make (Interview Gold)

### ❓ Decision 1: Min-Heap or Max-Heap?

**Ask yourself:** "What's the WORST element in my K selected items?"

- **If worst = smallest** → Use **Min-Heap** (for K largest)
- **If worst = largest** → Use **Max-Heap** (for K smallest)

### ❓ Decision 2: Single Pass or Two Pass?

**One Pass (More efficient):**
```
- Insert into heap while maintaining size
- If heap.size() > k, poll immediately
```

**Two Pass (More readable):**
```
- Insert first K elements
- Compare remaining elements with heap.peek()
- Only replace if beneficial
```

### ❓ Decision 3: How to Compare Custom Objects?

```java
// For numbers (natural order)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// For max-heap (reverse order)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

// For custom objects (by frequency)
PriorityQueue<Integer> heap = new PriorityQueue<>(
    (a, b) -> frequencyMap.get(a) - frequencyMap.get(b)
);

// For custom objects (by multiple criteria)
PriorityQueue<Point> heap = new PriorityQueue<>(
    (p1, p2) -> {
        long dist1 = (long) p1.x * p1.x + (long) p1.y * p1.y;
        long dist2 = (long) p2.x * p2.x + (long) p2.y * p2.y;
        return Long.compare(dist1, dist2); // min-heap by distance
    }
);
```

### ❓ Decision 4: Heap Size Management?

```java
// Option A: Add all, then maintain size in loop
minHeap.add(element);
if (minHeap.size() > k) minHeap.poll();

// Option B: Check size before adding
if (minHeap.size() < k) {
    minHeap.add(element);
} else if (element > minHeap.peek()) {
    minHeap.poll();
    minHeap.add(element);
}
```

✅ **If you can answer these, you can code confidently.**

---

## 6️⃣ Common Traps & Mistakes

### 🚨 **Trap 1: Wrong Heap Type**
- **Wrong:** Using Min-Heap to find K smallest
- **Correct:** Use Max-Heap for K smallest (to keep worst on top)
- **Test:** Find 3 smallest in [1,5,12,2,11]. Max-heap keeps 12 on top so it gets removed first.

### 🚨 **Trap 2: Comparator Direction**
- **Wrong:** `(a, b) -> a - b` for max-heap
- **Correct:** `(a, b) -> b - a` for max-heap
- **Test:** Verify heap.peek() is actually the maximum

### 🚨 **Trap 3: Forgetting to Handle k > array.length**
```java
// Wrong
for (int i = 0; i < k; i++) { ... }  // IndexOutOfBoundsException!

// Correct
for (int i = 0; i < Math.min(k, nums.length); i++) { ... }
```

### 🚨 **Trap 4: Integer Overflow in Distance Calculations**
```java
// Wrong
int dist = p.x * p.x + p.y * p.y; // Can overflow!

// Correct
long dist = (long) p.x * p.x + (long) p.y * p.y;
```

### 🚨 **Trap 5: Heap Still Contains Unprocessed Elements**
- **Issue:** After loop, heap might have leftover elements
- **Solution:** Ensure final heap.size() == k before returning

### 🚨 **Trap 6: Off-by-One with Kth Element**
- Kth smallest = element at position K (not K-1) in sorted order
- Final answer is `heap.peek()`, not `heap.poll()` (preserves data)

---

## 7️⃣ Time & Space Complexity

**Time:** O(n log k)
- n iterations through array
- Each heap operation = O(log k) because heap size is at most k

**Space:** O(k)
- Heap stores at most k elements

**Explain why in one line:**
> *Heap has max k elements, so insert/remove takes log(k). We do this n times, giving O(n log k) total.*

📌 **Precision footnote:** technically it's **O(n + k log k)** — building the frequency map is a separate O(n) pass, and the heap only ever touches the k *distinct* entries. Commonly stated as O(n log k) since k ≤ n, but worth knowing the exact breakdown if pressed.

⚠️ **Tie-breaking note:** when the comparator returns 0 (equal priority), Java's heap resolves ties arbitrarily — insertion order is not guaranteed. If a problem needs deterministic tie-breaking (e.g., "smallest value wins ties"), add a secondary comparator key: `.thenComparingInt(...)`.

---

## 🪣 Bucket Sort — O(n) Alternative (Frequency-Based Problems)

**When it applies:** the "priority" you're ranking by is a **bounded integer** — most commonly frequency count, which can never exceed `n` (array/string length). When priority is bounded like this, you can skip the heap entirely and index directly into buckets.

**Why it's faster:** heap operations cost O(log k) each because they rely on comparisons. Bucket sort avoids comparisons altogether — frequency becomes a direct array index, so placement is O(1).

```java
public List<Integer> topKFrequentBucket(int[] nums, int k) {
    Map<Integer, Integer> freqMap = new HashMap<>();
    for (int num : nums) {
        freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
    }

    // bucket[i] = list of numbers that occur exactly i times
    List<Integer>[] buckets = new List[nums.length + 1];
    for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
        int freq = entry.getValue();
        if (buckets[freq] == null) buckets[freq] = new ArrayList<>();
        buckets[freq].add(entry.getKey());
    }

    // walk buckets from highest frequency down, collecting k numbers
    List<Integer> result = new ArrayList<>();
    for (int freq = buckets.length - 1; freq >= 1 && result.size() < k; freq--) {
        if (buckets[freq] != null) {
            result.addAll(buckets[freq]);
        }
    }

    return result;
}
```

**Complexity:** O(n) time, O(n) space — true linear, no `log k` factor.

**Interview framing:** *"Since frequency is bounded by the array length, I can bucket by frequency instead of using a heap — this drops the log(k) factor entirely and gets me O(n)."* This is a common follow-up eBay interviewers ask after the heap solution ("can you do better than O(n log k)?").

**Caveat:** only works when priority is a small bounded integer. Doesn't generalize to arbitrary comparators (e.g., K Closest Points by distance) — for those, the heap remains the right tool.

---

## 8️⃣ Canonical Problems (Must-Know)

### **Problem 1: Top K Largest Numbers**
- **Name:** Find K Largest Numbers in an Array (easy)
- **Key insight:** Use **min-heap** of size K. Top = smallest of K largest. Replace when new element > top.
- **Pattern:** Elementary gatekeeping

### **Problem 2: Kth Smallest Number**
- **Name:** Kth Smallest Number in an Array (easy)
- **Key insight:** Use **max-heap** of size K. Top = largest of K smallest. Final answer = heap.peek().
- **Pattern:** Track K and return Kth directly

### **Problem 3: K Closest Points to Origin**
- **Name:** K Closest Points to the Origin (medium)
- **Key insight:** Max-heap by distance. Top = farthest of K closest. Replace when new point closer.
- **Pattern:** Custom comparator on distance

### **Problem 4: Connect Ropes**
- **Name:** Connect Ropes (medium)
- **Key insight:** Min-heap to always grab two shortest ropes. Cost = sum of rope lengths.
- **Pattern:** Greedy with heap

### **Problem 5: Top K Frequent Elements**
- **Name:** Top K Frequent Elements (medium)
- **Key insight:** Min-heap by frequency. Top = least frequent of K. Custom comparator on frequency map.
- **Pattern:** Pre-processing with hash map + heap

### **Problem 6: Frequency Sort**
- **Name:** Frequency Sort (medium)
- **Key insight:** Max-heap by frequency, drain fully (not capped at k) — build output string ordered by descending frequency. Bucket sort is the O(n) alternative.
- **Pattern:** Pre-processing with hash map + heap (or bucket array)

### **Problem 7: Kth Largest Number in a Stream**
- **Name:** Kth Largest Number in a Stream (medium)
- **Key insight:** Min-heap capped at size K, persisted across calls (not rebuilt each time). `add()` inserts one value and returns current Kth largest = `heap.peek()`.
- **Pattern:** Online/streaming variant of the gatekeeper strategy

### **Problem 8: K Closest Numbers**
- **Name:** K Closest Numbers (medium)
- **Key insight:** Either binary search for the insertion point + two-pointer expansion around it, or max-heap by `|num - target|` capped at K.
- **Pattern:** Two-pointer alternative to heap when array is sorted

### **Problem 9: Maximum Distinct Elements**
- **Name:** Maximum Distinct Elements (medium)
- **Key insight:** Greedy — keep all frequency-1 numbers as distinct, then use remaining "removal budget" on the cheapest duplicates first (min-heap by frequency).
- **Pattern:** Greedy + heap, not a pure top-K selection

### **Problem 10: Sum of Elements**
- **Name:** Sum of Elements (medium)
- **Key insight:** Min-heap to sort ascending, sum elements between the K1th and K2th smallest (skip first K1, sum until K2).
- **Pattern:** Range selection via heap, not just top/bottom K

---

## 9️⃣ My Personal Notes (Critical Section)

### 🧠 Mental Shortcut:
> **"K smallest = Max-Heap gatekeeper. K largest = Min-Heap gatekeeper."**
>
> Both are gatekeepers! They just guard different K values.

### 💬 Interview Phrasing:
> "I'll maintain a heap of size K. For K largest, I use a min-heap so the smallest of my K is on top—when I see a bigger element, I remove the smallest and add the bigger one."

### 🎯 My Favorite Variation:
> **Connected Ropes Problem:** Instead of finding top K, use heap greedily. Always combine two smallest ropes (min-heap strategy). Repeat until one rope left.
>
> **Why it works:** Greedy choice of combining smallest ropes first minimizes total cost (similar to Huffman coding).

### ⚠️ What I Always Forget:
> 1. **Integer overflow when computing distances!** Always cast to `long` first.
> 2. **Comparator direction for max-heap!** Test with simple example: [1,2,3], want 3 on top = reverse order.
> 3. **Comparator needs to return consistent results** or heap breaks (don't compare floating point directly).

---

## 📋 Quick Checklist Before Coding

- [ ] **Understood the problem**: K largest? K smallest? Kth? Top K frequent?
- [ ] **Chose heap type**: Min-heap for K largest? Max-heap for K smallest?
- [ ] **Comparator correct**: Tested with 3-element example?
- [ ] **Handled edge cases**: k > array length? Empty array? Duplicates?
- [ ] **Overflow check**: Using `long` for distance/product calculations?
- [ ] **Final result correct**: Does heap have exactly K elements? (or 1 for Kth)
- [ ] **Complexity acceptable**: O(n log k) good? Space O(k) within limits?
- [ ] **Code walkthrough**: Traced through example [3,1,5,12,2,11], K=3?

---

## 📚 Related Patterns & When to Switch

| Pattern | Use When |
|---------|----------|
| **Heap (Top K)** | Need K best/worst items from large dataset |
| **Quick Select** | Want average O(n), willing to risk O(n²) worst case |
| **Sorting** | Need all elements sorted, or k = n |
| **Hash Map** | Need to count frequencies before heap (like Top K Frequent) |
| **Two Heaps** | Need both top K and bottom K simultaneously |
| **Sliding Window** | Fixed window of K, need top K within each window |

---

## 🔗 Quick Links to Problems in This Package

1. **P01TopKNumber.java** - Find K Largest (Min-Heap)
2. **P02KthSmallestNumber.java** - Kth Smallest (Max-Heap)
3. **P03KClosestPointsToTheOrigin.java** - K Closest (Max-Heap, custom comparator)
4. **P04ConnectRopes.java** - Connect Ropes (Min-Heap, greedy)
5. **P05TopKFrequentNumbers.java** - Top K Frequent (Min-Heap, frequency map)
6. **P06FrequencySort.java** - Sort by Frequency (Max-Heap on frequency)
7. **P07KthLargestNumberInAStream.java** - Online K Largest (Min-Heap, stream processing)
8. **P08KClosestNumbers.java** - K Closest in Sorted Array (Two-pointer or heap)
9. **P09MaximumDistinctElements.java** - Max Distinct (Greedy with heap)
10. **P10SumOfElements.java** - Sum of K Closest (Min-Heap)

---

## ✨ Final Wisdom

> **Top K Elements is not just about heaps.**
>
> It's about **strategic selection under constraints.**
>
> Master the heap type decision, and you can solve 90% of Top K problems.
>
> When in doubt: **"What do I want on top?"** → Answer determines everything.