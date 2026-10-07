# 🔹 Interview Coding Pattern – Modified Binary Search

---

## 1️⃣ Modified Binary Search



---

## 2️⃣ When to Use This Pattern (Recognition Signals)

- **Sorted array** or partially sorted structure (rotated, bitonic, etc.)
- Need to find an element, boundary, or peak
- **Problem says "O(log n)" time complexity** → Think binary search
- Array property changes at a specific point (ascending → descending, rotated, etc.)
- Problems involving: finding in rotated arrays, finding peaks, boundaries, positions in sorted arrays

### Complexity Hint:
- If it involves a sorted array and asks for O(log n) → **Binary Search**
- If the array is modified/rotated/bitonic but still searchable → **Modified Binary Search**

### One-Line Trigger Thought:
> "If the array is sorted or has a sorted property, I can eliminate half the search space with each comparison—binary search!"

---

## 3️⃣ Core Idea (Mental Model)

**Standard Binary Search** divides a sorted array in half repeatedly. 

**Modified Binary Search** adapts this when:
1. The array is sorted but we don't know the direction (ascending/descending)
2. The array is rotated (circular)
3. The array is bitonic (increasing then decreasing)
4. We need to find boundaries, peaks, or floors/ceilings instead of exact matches
5. The search space has multiple conditions to check

**Key Principle:** Always determine which half is in sorted order, then decide which half contains the answer.

**Rule:** If you can't explain it simply, you don't understand it yet.

---

## 4️⃣ Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

```java
public static int search(int[] arr, int key) {
    int left = 0;
    int right = arr.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;  // Avoid overflow
        
        if (arr[mid] == key) {
            return mid;  // Found the element
        }
        
        // ============ LOGIC CHANGES HERE ============
        // Determine which half is sorted
        // Then check if key is in the sorted half
        // If yes, search that half; if no, search the other half
        // ============================================
        
        if (/* condition to go right */) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return -1;  // Not found
}
```

⚠️ **Important:** Keep this structure. Only fill in the logic inside the while loop.

---

## 5️⃣ Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently.**

1. **Which comparison determines sorted half?**
   - Compare `arr[left]` with `arr[mid]`
   - Compare `arr[mid]` with `arr[right]`
   - Compare `arr[mid]` with `arr[mid+1]`

2. **Is the target in the sorted half?**
   - Check: `arr[left] <= key <= arr[mid]` (if left half is sorted)
   - Check: `arr[mid] <= key <= arr[right]` (if right half is sorted)

3. **What edge cases exist?**
   - Duplicates in array?
   - Single element array?
   - All elements same?

4. **What am I actually searching for?**
   - An exact match?
   - A boundary (first/last occurrence)?
   - A peak or valley?
   - A position (ceiling, floor, next letter)?

---

## 6️⃣ Common Traps & Mistakes

👉 **Tip:** This section gets more valuable over time as you add your personal pitfalls.

| Trap | Why It Breaks | Solution |
|------|---------------|----------|
| Using `(left + right) / 2` | Integer overflow in some languages | Use `left + (right - left) / 2` |
| Checking `left < right` | Misses single element case | Use `left <= right` |
| Not identifying which half is sorted | Can't determine search direction | Always check `arr[left]` vs `arr[mid]` first |
| Forgetting about duplicates | Can't determine sorted half | Special handling for duplicates: shrink pointers |
| Off-by-one in boundary conditions | Wrong half selected | Be careful with `mid`, `mid+1`, `mid-1` |
| Wrong comparison operator | Searches wrong half | Trace through an example manually |
| Not updating boundaries correctly | Infinite loop or missed answer | Update `left = mid + 1` or `right = mid - 1` |

---

## 7️⃣ Time & Space Complexity

**Time:** O(log n)

**Space:** O(1)

**Explain why in one line:**
> We eliminate half the search space with each comparison, giving us logarithmic time. No extra space needed for the iterative approach.

---

## 8️⃣ Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

**Problem 1: Order-Agnostic Binary Search** 
- Name: Find element in ascending OR descending sorted array
- Key insight: Determine direction by comparing first and last element, then do standard binary search

**Problem 2: Search in Rotated Sorted Array**
- Name: Find element in rotated sorted array
- Key insight: One half is always sorted; check if key is in sorted half, then eliminate the other half

**Problem 3: Find Peak in Bitonic Array**
- Name: Find maximum in array that increases then decreases
- Key insight: Compare `arr[mid]` with `arr[mid+1]` to determine which side has the peak

**Problem 4: Find Ceiling/Floor**
- Name: Find smallest element >= target or largest element <= target
- Key insight: Track closest valid element while searching; adjust boundaries to find boundaries

**Problem 5: Search in Infinite Sorted Array**
- Name: Find element in infinite array (can only access via index)
- Key insight: Exponentially expand right boundary until target is in range, then binary search

---

## 9️⃣ My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> "Sorted array + target search → **Can I eliminate half?** If yes, it's binary search. If yes but modified → Check which half is sorted first."

### Interview Phrasing:
> "Since the array is sorted, I can use binary search to achieve O(log n). Let me first determine which half is in sorted order, then check if the target could be in that half..."

### My Favorite Variation:
> **Bitonic Array Problems** - The key insight is comparing mid with mid+1 to determine which side contains the peak.

### What I Always Forget:
> Always use `left + (right - left) / 2` for mid to avoid integer overflow. Initialize with `left = 0` and `right = arr.length - 1`, and use `left <= right` for the loop condition.

### Binary Search (Target NOT Found) 

**Invariant when the loop ends (start > end):**
```
arr[end] < target < arr[start]
end = start - 1
```

**Pointer Meaning:**

- **`start`** 
  - Insertion index for target.
  - Points to Ceiling(target) (smallest element ≥ target).
  - May be n (no ceiling exists).

- **`end`** 
  - Last index before insertion point. 
  - Points to Floor(target) (largest element < target). 
  - May be -1 (no floor exists).

**Floor / Ceiling Mapping:**

- **Floor(target)** → Index: `end` → Value: largest value < target
- **Ceiling(target)** → Index: `start` → Value: smallest value ≥ target

**Edge Cases:**

- **target < arr[0]** → start = 0, end = -1 (ceiling exists, no floor)
- **target > arr[n-1]** → start = n, end = n-1 (no ceiling, floor exists)

**One-line memory hook:**
> After binary search fails: `start = ceiling`, `end = floor`, and `end = start - 1`.

---

## 📋 Quick Checklist Before Coding

- [ ] Is the array sorted? (Or partially sorted with a predictable pattern?)
- [ ] Can I eliminate half the search space based on comparisons?
- [ ] What am I searching for? (exact match, boundary, peak, etc.)
- [ ] Are there duplicates? If yes, how do they affect my logic?
- [ ] Which half is sorted at each step? (Compare `arr[left]` to `arr[mid]`)
- [ ] Is my target in the sorted half? (Check the range)
- [ ] Is my loop condition `left <= right`? (To catch single elements)
- [ ] Am I using `left + (right - left) / 2` for mid?
- [ ] Do I update pointers correctly? (`left = mid + 1` or `right = mid - 1`, not mid)
- [ ] Have I tested on edge cases? (empty, single element, all duplicates)

---

## 🎯 Practice Problems Map

| Problem Type | File | Key Technique |
|--------------|------|---|
| Order Agnostic | P01OrderAgnosticBinarySearch | Determine direction first |
| Boundary Search | P02CeilingOfNumber, P03FloorOFNumber | Track closest valid element |
| Position Search | P04NextLetter, P05NumberRange | Binary search for first/last |
| Infinite Array | P06SearchInASortedInfiniteArray | Expand range exponentially |
| Bitonic Peak | P08BitonicArrayMaximum | Compare mid with mid+1 |
| Bitonic Search | P09SearchBitonicArray | Find peak, then binary search halves |
| Rotated Array | P10SearchInRotatedArray | Find which half is sorted |
| Rotation Count | P12RotationCount | Find rotation pivot point |
