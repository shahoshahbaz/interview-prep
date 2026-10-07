# 🔹 Interview Coding Pattern – Two Heaps – Cheat Sheet

---

## Table of Contents

1. [Pattern Name](#pattern-name)
2. [When to Use This Pattern](#when-to-use-this-pattern-recognition-signals)
3. [Core Idea (Mental Model)](#core-idea-mental-model)
4. [Standard Code Templates](#standard-code-template-skeleton)
5. [Key Decisions to Make](#key-decisions-to-make-interview-gold)
6. [Common Traps & Mistakes](#common-traps--mistakes)
7. [Time & Space Complexity](#time--space-complexity)
8. [Lazy Deletion — Handling Removal from a Heap](#lazy-deletion--handling-removal-from-a-heap)
9. [Canonical Problems (Must-Know)](#canonical-problems-must-know)
10. [My Personal Notes](#my-personal-notes-critical-section)
11. [Quick Checklist Before Coding](#quick-checklist-before-coding)
12. [Related Patterns & When to Switch](#related-patterns--when-to-switch)
13. [Quick Links to Problems in This Package](#quick-links-to-problems-in-this-package)
14. [Final Wisdom](#final-wisdom)

---

## Pattern Name

**Two Heaps Pattern** (Max-Heap / Min-Heap Split)

---

## When to Use This Pattern (Recognition Signals)

### ✅ You see these keywords:
- "Find the **median** of a stream / running median"
- "**Sliding window median**"
- "Maximize **capital** given a budget and unlockable projects"
- "Schedule / merge based on a **midpoint or boundary** between two halves"
- "At any point in time, tell me the **middle value**"
- Two competing greedy pools — "cheapest affordable" vs "most profitable available"

### Complexity Hint:
- **Insert + query the boundary at any time** → O(log n) insert, O(1) query with two heaps
- **Beats** re-sorting the whole dataset (O(n log n)) on every query

### One-Line Trigger Thought:
> *"I need to know what's at the BOUNDARY between two halves of my data, and that boundary keeps shifting as data streams in."*

---

## Core Idea (Mental Model)

### 🎯 **The Golden Question:**
**"What sits right at the dividing line, and which half does it belong to?"**

Split your data into a **lower half** and an **upper half**. Store the lower half in a **max-heap** (so its largest element — the boundary — is on top) and the upper half in a **min-heap** (so its smallest element — the other boundary — is on top). The answer always lives at the two heap tops.

### 📊 **Rule of Thumb:**

| Want to find... | Structure | Why? | Top of Heap |
|---|---|---|---|
| **Median of a stream** | Max-Heap (lower half) + Min-Heap (upper half) | Median sits at the boundary between the two halves | Larger of lower half / smaller of upper half |
| **Sliding window median** | Same two heaps + lazy deletion | Window shifts, so elements must be removable, not just insertable | Same as above, minus stale entries |
| **Max capital / IPO-style greedy** | Min-Heap (by requirement, e.g. capital needed) + Max-Heap (by reward, e.g. profit) | First heap finds what's *currently affordable*, second heap picks the *best* among those | Cheapest requirement / best reward |
| **Scheduling around "next interval"** | (Often TreeMap/binary search instead) | Two heaps aren't the natural fit here — flag it as an edge case | — |

### **The Two Halves Invariant:**

1. **Max-Heap = lower half** (the "small" numbers). Its root is the **largest of the small numbers** — the left boundary.
2. **Min-Heap = upper half** (the "large" numbers). Its root is the **smallest of the large numbers** — the right boundary.
3. **Balance rule:** sizes differ by at most 1. Max-heap is allowed exactly one extra element (by convention), never the min-heap.
4. Every element in the max-heap ≤ every element in the min-heap — always. This is the invariant every insertion must preserve.

---

## Standard Code Template (Skeleton)

### **Template 1: Find Median from a Data Stream**

```java
class MedianFinder {
    // lower half — largest of the small numbers on top
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    // upper half — smallest of the large numbers on top
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    public void addNum(int num) {
        // Step 1: always insert into maxHeap first
        maxHeap.add(num);

        // Step 2: push maxHeap's largest into minHeap
        // (guarantees every maxHeap element <= every minHeap element)
        minHeap.add(maxHeap.poll());

        // Step 3: rebalance — maxHeap may hold at most one more than minHeap
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.add(minHeap.poll());
        }
    }

    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        }
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
}
```

### **Template 2: Sliding Window Median (Two Heaps + Lazy Deletion)**

```java
public double[] medianSlidingWindow(int[] nums, int k) {
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    Map<Integer, Integer> toRemove = new HashMap<>(); // lazy deletion counter
    double[] result = new double[nums.length - k + 1];

    for (int i = 0; i < nums.length; i++) {
        // Step 1: insert like MedianFinder
        if (maxHeap.isEmpty() || nums[i] <= maxHeap.peek()) {
            maxHeap.add(nums[i]);
        } else {
            minHeap.add(nums[i]);
        }
        rebalance(maxHeap, minHeap);

        // Step 2: once window is full, record median
        if (i >= k - 1) {
            result[i - k + 1] = getMedian(maxHeap, minHeap);

            // Step 3: mark outgoing element for lazy removal
            int outgoing = nums[i - k + 1];
            toRemove.merge(outgoing, 1, Integer::sum);
            if (outgoing <= maxHeap.peek()) {
                maxHeap.remove(outgoing); // O(k) — acceptable for k small, see trap notes
            } else {
                minHeap.remove(outgoing);
            }
            rebalance(maxHeap, minHeap);
        }
    }
    return result;
}
// ⚠️ Important: Keep this structure. rebalance()/getMedian() are the same
// logic as Template 1, just factored into helpers.
```

### **Template 3: Two Heaps as "Affordable vs Best" (Maximize Capital / IPO)**

```java
public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
    int n = profits.length;

    // Step 1: min-heap by capital required — "what can I unlock next?"
    PriorityQueue<int[]> byCapital = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    for (int i = 0; i < n; i++) {
        byCapital.add(new int[]{capital[i], profits[i]});
    }

    // Step 2: max-heap by profit — "of what I can afford, what's best?"
    PriorityQueue<Integer> byProfit = new PriorityQueue<>(Collections.reverseOrder());

    // Step 3: k rounds — move every affordable project into byProfit, take the best
    for (int round = 0; round < k; round++) {
        while (!byCapital.isEmpty() && byCapital.peek()[0] <= w) {
            byProfit.add(byCapital.poll()[1]);
        }
        if (byProfit.isEmpty()) break; // nothing affordable — stop early
        w += byProfit.poll();
    }

    return w;
}
```

⚠️ **Important:** Keep this structure. Only fill in the logic details (like custom comparators).

---

## Key Decisions to Make (Interview Gold)

### ❓ Decision 1: Which heap gets the "extra" element when sizes are unequal?

**Ask yourself:** "Do I always want an odd-count median to come from a specific side?"

- Convention: **max-heap (lower half)** holds the extra element. Then:
    - Odd total → median = `maxHeap.peek()`
    - Even total → median = average of both tops
- Either convention works as long as you're **consistent** — pick one and stick with it.

### ❓ Decision 2: Insert-then-rebalance, or decide-then-insert?

**Insert-then-rebalance (safer, what the templates use):**
```
- Always insert into maxHeap first
- Move maxHeap's top into minHeap
- If minHeap now has more elements, move its top back to maxHeap
```
This guarantees the cross-heap invariant (`maxHeap ≤ minHeap`) is never violated, because you always route the value through the "gate" (maxHeap → minHeap) before it's compared for balance.

**Decide-then-insert (used in sliding window / affordability variants):**
```
- Compare new value against maxHeap.peek() directly
- Insert into the correct heap immediately
- Rebalance sizes afterward
```
Slightly more error-prone — a wrong comparison inserts into the wrong half and silently corrupts the median.

### ❓ Decision 3: Do I need removal (lazy deletion), or is this insert-only?

```java
// Insert-only (MedianFinder, IPO) — no removal ever needed
// Removal required (Sliding Window Median) — use lazy deletion, see section 8
```

### ❓ Decision 4: What am I actually splitting on?

- **Value-based split** (median problems): split by numeric value, boundary = median.
- **Constraint-based split** (IPO/greedy problems): one heap isn't "half the data" at all — it's a *gatekeeper* heap (what's currently accessible) feeding a *selector* heap (what's best among accessible). This is a different mental model from the median case — don't force every two-heap problem into the "median" box.

✅ **If you can answer these, you can code confidently.**

---

## Common Traps & Mistakes

### 🚨 **Trap 1: Wrong Heap Direction**
- **Wrong:** `PriorityQueue<Integer>()` for the lower half (that's a min-heap — wrong boundary is exposed)
- **Correct:** Lower half = max-heap (`Collections.reverseOrder()`), upper half = min-heap (natural order)
- **Test:** Insert [1,2,3]. Lower half top should be the largest of the small numbers, not the smallest.

### 🚨 **Trap 2: Skipping the "Route Through maxHeap First" Step**
- **Wrong:** Comparing new value to `minHeap.peek()` and `maxHeap.peek()` separately, then inserting directly into "the right one"
- **Why it breaks:** edge cases (empty heap, value equal to boundary) are easy to get backwards
- **Safer:** always insert into maxHeap, push its top to minHeap, then rebalance (Template 1) — this makes the invariant self-enforcing

### 🚨 **Trap 3: Imbalance Drift**
- **Issue:** Forgetting to rebalance after every single insertion (not just some) lets the size gap exceed 1, silently breaking the median
- **Solution:** Rebalance unconditionally after every insert — never skip it "because it's probably fine"

### 🚨 **Trap 4: `PriorityQueue.remove(value)` is O(k), Not O(log k)**
- **Issue:** Java's heap has no efficient arbitrary-element removal — `remove(x)` does a linear scan
- **Why it matters:** in Sliding Window Median this is technically O(k) per removal, which is fine for small k but is the reason production-grade solutions use **lazy deletion** instead (see Section 8)

### 🚨 **Trap 5: Integer Overflow / Double Precision on the Average**
```java
// Wrong
return (maxHeap.peek() + minHeap.peek()) / 2; // integer division truncates!

// Correct
return (maxHeap.peek() + minHeap.peek()) / 2.0;
```

### 🚨 **Trap 6: Forgetting the "Nothing Affordable Yet" Exit (Greedy/IPO variant)**
- **Issue:** If the gatekeeper heap has nothing that fits the current budget, the selector heap stays empty forever — looping `k` times regardless still terminates, but only if you `break` on an empty selector heap
- **Solution:** Always check `if (selectorHeap.isEmpty()) break;` before polling

---

## Time & Space Complexity

**Time:** O(log n) per insertion, O(1) per median query
- Each insertion touches at most 2 heap operations (insert + rebalance) = O(log n)
- `findMedian()` is just peeking two tops = O(1)
- For n numbers inserted with a query after each: **O(n log n)** total — same asymptotic cost as sorting once, but the two-heap approach gives you the median **incrementally, after every insertion**, which sorting-from-scratch cannot do efficiently.

**Space:** O(n)
- Both heaps together hold every element seen so far

**Explain why in one line:**
> *Two heaps let me maintain the boundary between the lower and upper halves incrementally — each insert is O(log n) instead of re-sorting the whole stream, which would be O(n log n) per query.*

📌 **Sliding Window Median footnote:** with **lazy deletion**, each slide is O(log k) amortized (insert + mark-for-removal), not O(k) — the O(k) cost only shows up if you use `PriorityQueue.remove()` directly instead of the lazy-deletion counter map.

---

## Lazy Deletion — Handling Removal from a Heap

**When it applies:** any two-heap problem where elements need to leave the structure over time — most commonly **Sliding Window Median**, where the outgoing element must be removed as the window slides.

**Why it's needed:** Java's `PriorityQueue` has no efficient way to remove an arbitrary (non-root) element — `remove(x)` is O(k). Lazy deletion avoids ever doing that scan.

**The idea:** instead of physically removing a stale element from the heap, just **note that it should be ignored** in a hash map counter. Only clean it up when it would otherwise surface at the top of the heap.

```java
Map<Integer, Integer> toRemove = new HashMap<>();

// mark for lazy removal
toRemove.merge(outgoingValue, 1, Integer::sum);

// before trusting heap.peek(), prune any stale top
private void pruneStale(PriorityQueue<Integer> heap, Map<Integer, Integer> toRemove) {
    while (!heap.isEmpty() && toRemove.getOrDefault(heap.peek(), 0) > 0) {
        int top = heap.poll();
        toRemove.merge(top, -1, Integer::sum);
        if (toRemove.get(top) == 0) toRemove.remove(top);
    }
}
```

**Complexity:** amortized O(log k) per operation — every element is pushed and popped at most once overall, even though "removal" is deferred.

**Interview framing:** *"Since heap removal of an arbitrary element is O(k), I'll use lazy deletion — mark the outgoing value in a counter map, and only actually pop it once it bubbles to the top. This keeps every operation O(log k) amortized."*

**Caveat:** heap `size()` becomes unreliable while stale entries linger — track the *true* window size separately (e.g., `k`) rather than trusting `heap.size()` directly for balance checks.

---

## Canonical Problems (Must-Know)

### **Problem 1: Find the Median of a Number Stream**
- **Name:** Find Median from Data Stream (hard, but conceptually the entry point) — 🔴 Pinterest P1
- **Key insight:** Max-heap for lower half, min-heap for upper half. Insert-then-rebalance keeps the boundary correct. Median = top(s) of the heaps.
- **Pattern:** The canonical two-heap split

### **Problem 2: Sliding Window Median**
- **Name:** Sliding Window Median (hard)
- **Key insight:** Same two-heap split as Problem 1, plus **lazy deletion** to handle the outgoing element as the window slides.
- **Pattern:** Two-heap median + removal

### **Problem 3: Maximize Capital**
- **Name:** Maximize Capital / IPO (hard)
- **Key insight:** Not a value-split — it's a **gatekeeper heap** (min-heap by capital required) feeding a **selector heap** (max-heap by profit). Move everything currently affordable into the selector, then greedily take the best.
- **Pattern:** Two heaps as sequential filters, not a median boundary

### **Problem 4: Next Interval**
- **Name:** Next Interval (hard)
- **Key insight:** Classic framing uses two max-heaps (by start time, by end time) plus original-index tracking to find, for each interval, the interval with the smallest start that is ≥ its end. In practice often solved more cleanly with sorted arrays + binary search (TreeMap) — worth knowing the heap version for pattern completeness, but recognize when a simpler tool fits better.
- **Pattern:** Two heaps for boundary-matching between two sorted views of the same data

---

## My Personal Notes (Critical Section)

### 🧠 Mental Shortcut:
> **"Max-heap holds the small half. Min-heap holds the large half. The median lives where they touch."**
>
> If a problem isn't really about a numeric median (like IPO), ask instead: *"Which heap is my gatekeeper, and which is my selector?"*

### 💬 Interview Phrasing:
> "I'll split the data into two heaps — a max-heap for the lower half so its largest element is on top, and a min-heap for the upper half so its smallest is on top. The median is always at this boundary, and I keep the heaps balanced within one element of each other on every insert."

### 🎯 My Favorite Variation:
> **IPO / Maximize Capital:** Two heaps that *aren't* a value split at all — one heap is just "what's currently reachable," the other is "the best of what's reachable." A good reminder that "two heaps" is a structural pattern (two ordered views over the same or related data), not strictly a median technique.

### ⚠️ What I Always Forget:
> 1. **Always route new inserts through maxHeap first**, then rebalance — don't try to decide "which heap" up front with an if/else, it's easy to get the boundary condition backwards.
> 2. **Integer division on the average!** Always use `/ 2.0`.
> 3. **`PriorityQueue.remove(x)` is O(k)**, not O(log k) — reach for lazy deletion when removal happens repeatedly (sliding window).
> 4. **Rebalance after every single insertion**, unconditionally — skipping "because it's probably still balanced" is how the invariant silently breaks.

---

## Quick Checklist Before Coding

- [ ] **Understood the problem**: Is this a value-split (median-style) or a gatekeeper/selector-split (IPO-style)?
- [ ] **Chose heap types**: Max-heap for lower half / min-heap for upper half (or gatekeeper/selector roles)?
- [ ] **Insertion order correct**: Routing through maxHeap first, or comparing directly — and is the comparison direction right?
- [ ] **Rebalance every insert**: Not just "sometimes," every single time?
- [ ] **Removal needed?**: If yes, is lazy deletion in place (not raw `remove()`)?
- [ ] **Overflow/precision check**: Using `/ 2.0` for averages, `long` for any overflow-prone sums?
- [ ] **Final result correct**: Odd/even count both handled? Empty-heap edge case handled?
- [ ] **Complexity acceptable**: O(log n) insert, O(1) query good? O(n) space within limits?
- [ ] **Code walkthrough**: Traced through a small example by hand, watching heap sizes after each step?

---

## Related Patterns & When to Switch

| Pattern | Use When |
|---------|----------|
| **Two Heaps** | Need the boundary between two halves (median), or a gatekeeper + selector pair, updated incrementally |
| **Top K (Single Heap)** | Only need the best/worst K items, not a two-sided boundary |
| **Sorting** | Data is static, one-time query — no repeated insertion/removal |
| **TreeMap / Sorted Set** | Need removal of arbitrary elements efficiently (O(log n)) — avoids the lazy-deletion workaround entirely, good alternative for Sliding Window Median and Next Interval |
| **Sliding Window (single pointer)** | Window-based problem but the aggregate needed is sum/count/distinct-count, not median |
| **Greedy + Single Heap** | Sequential best-choice problems that don't need two simultaneous ordered views |

---

## Quick Links to Problems in This Package

1. **P01FindMedianOfANumberStream.java** - Running Median (Max-Heap + Min-Heap)
2. **P02SlidingWindowMedian.java** - Windowed Median (Two Heaps + Lazy Deletion)
3. **P03MaximizeCapital.java** - IPO Greedy (Gatekeeper Min-Heap + Selector Max-Heap)
4. **P04NextInterval.java** - Interval Boundary Matching (Two Heaps or TreeMap alternative)

---

## Final Wisdom

> **Two Heaps is not just "median finding."**
>
> It's about maintaining **two ordered views of the same data simultaneously**, so a boundary or best-choice query resolves in O(log n) instead of a full re-sort.
>
> Master the "which heap holds what, and why" decision, and the rest of the pattern is mechanical.
>
> When in doubt: **"What sits at the boundary, and which side does it belong to?"** → Answer determines everything.