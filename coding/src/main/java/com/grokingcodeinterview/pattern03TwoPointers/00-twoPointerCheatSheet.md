#  Two Pointer Pattern


## 1. Definition:

A technique where two pointers iterate through the data structure (usually an array or linked list) from different positions (start/end or both from start) to solve problems efficiently.

---

## 2. When to Use This Pattern (Recognition Signals)

- Problems involving sorted arrays or linked lists.
- Need to find pairs/triplets/segments with a certain property (sum, difference, etc.).
- In-place modifications (removal, reversal, partitioning).
- Window or segment-based logic.

### Complexity Hint:
Often reduces O(N^2) brute force to O(N) or O(N log N).

### One-Line Trigger Thought:
"Can I use two indices to scan from both ends or maintain a window?"

---

## 3. Core Idea (Mental Model)

Move two pointers towards each other (or in tandem) to efficiently process or partition the data, leveraging order or constraints.

**Rule:** If you can't explain it simply, you don't understand it yet.

---

## 4. Standard Code Templates (4 Sub-Patterns)

**There isn't one universal template — recognize which of these 4 shapes the problem needs.**

### 4a. Opposite-Direction Converging (sorted array, pair sum/comparison)
```java
int left = 0, right = arr.length - 1;
while (left < right) {
    // logic using arr[left] and arr[right]
    if (/* condition */) {
        left++;
    } else {
        right--;
    }
}
```
Used for: Pair with Target Sum, Squaring a Sorted Array.

### 4b. Same-Direction (slow/fast) — in-place overwrite/removal
```java
int slow = 0;
for (int fast = 0; fast < arr.length; fast++) {
    if (/* keep condition */) {
        arr[slow] = arr[fast];
        slow++;
    }
}
```
Used for: Find Non-Duplicate Number Instances, Remove Key in Place.

### 4c. Fix + Two-Pointer (triplet / quadruple sum)
```java
Arrays.sort(arr); // prerequisite!
for (int i = 0; i < arr.length - 2; i++) {
    if (i > 0 && arr[i] == arr[i - 1]) continue; // skip dupes
    int left = i + 1, right = arr.length - 1;
    while (left < right) {
        // sum logic; move left/right; skip dupes on match
    }
}
```
Used for: Triplet Sum to Zero, Triplet Sum Close to Target, Triplets with Smaller Sum, Quadruple Sum to Target (fix two indices instead of one).

### 4d. Three-Pointer Partition (Dutch National Flag)
```java
int low = 0, mid = 0, high = arr.length - 1;
while (mid <= high) {
    if (arr[mid] == 0) swap(low++, mid++);
    else if (arr[mid] == 1) mid++;
    else swap(mid, high--); // arr[mid] == 2, don't advance mid here
}
```
Used for: Dutch National Flag Problem.

⚠️ **Important:** Pick the matching skeleton first, then fill in the logic — don't force every problem into 4a.

### Non-Template Problems (worth their own note, don't fit 4a–4d cleanly)
- **Backspace Compare (3-10):** two pointers start at the **end** and move backward, each with its own skip-counter for `#` characters.
- **Minimum Window Sort (3-11):** pointers scan inward from both ends to find the boundary of the unsorted middle region — no sum/comparison involved.

---

## 5. Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently.**
- What do the pointers represent?
- When do they move, and in which direction?
- What is the stopping condition?
- What invariants must be maintained?

---

## 6.Common Traps & Mistakes

👉 **Tip:** This section gets more valuable over time as you add your personal pitfalls.
- Forgetting to update both pointers correctly.
- Infinite loops due to incorrect stopping condition.
- Not handling edge cases (empty array, single element).
- Overlapping pointers when not intended.
- **Dupe-skipping bug (triplet/quadruple problems):** forgetting `if (i > 0 && arr[i] == arr[i-1]) continue;` on the fixed index — the #1 real bug source in this sub-pattern. Also remember to skip dupes on `left`/`right` *after* a match is found, not just on `i`.
- **Dutch Flag bug:** advancing `mid++` after a swap-with-`high` — you haven't verified the newly swapped-in value yet, so `mid` must stay put that iteration.
- **Backspace Compare bug:** resetting the skip-counter per pointer, not sharing one counter for both sides.

---

## 7. Time & Space Complexity

**Time:** O(N)

**Space:** O(1)

**Explain why in one line:**
> Both pointers traverse the array at most once, and no extra space is used.

---

## 8. Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

**Problem 1:**
- Name: Pair with Target Sum
- Key insight: Use two pointers to find a pair in a sorted array that sums to a target.

**Problem 2:**
- Name: Remove Duplicates from Sorted Array
- Key insight: Use slow/fast pointers to overwrite duplicates in place.

**Problem 3:**
- Name: Triplet Sum to Zero
- Key insight: Sort, fix one index, then two-pointer the remaining range; skip dupes on all three positions.

**Problem 4:**
- Name: Triplet Sum Close to Target
- Key insight: Same fix + two-pointer shape, but track closest-diff instead of exact match.

**Problem 5:**
- Name: Triplets with Smaller Sum
- Key insight: When `arr[i]+arr[left]+arr[right] < target`, *every* index between left and right also works — add `(right - left)` to the count instead of checking one at a time.

**Problem 6:**
- Name: Dutch National Flag Problem
- Key insight: Three pointers (low/mid/high) partition into 3 buckets in one pass; swapping with `high` doesn't advance `mid`.

**Problem 7:**
- Name: Quadruple Sum to Target
- Key insight: Fix two indices (nested loop), two-pointer the rest; dupe-skipping needed at all four levels.

**Problem 8:**
- Name: Comparing Strings containing Backspaces
- Key insight: Walk both strings from the end backward, using a skip-counter to simulate backspace deletion — not a value/sum comparison.

**Problem 9:**
- Name: Minimum Window Sort
- Key insight: Find the first element out of ascending order from the left and from the right; that's the boundary of the subarray that needs sorting.

---

## 9. My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> "If window breaks, shrink from left"

### Interview Phrasing:
> "We use two pointers to maintain a valid window…"

### My Favorite Variation:
> Using two pointers for palindrome checking.

### What I Always Forget:
> To check for pointer overlap and off-by-one errors.

---

## 📋 Quick Checklist Before Coding

- [ ] Are the pointers initialized correctly?
- [ ] Is the stopping condition correct?
- [ ] Are both pointers updated as needed?
- [ ] Are edge cases handled?
- [ ] Is the logic inside the loop correct?