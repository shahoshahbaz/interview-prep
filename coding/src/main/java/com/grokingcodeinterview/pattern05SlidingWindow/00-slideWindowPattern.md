# 🔹 Sliding Window / Two-Pointer Window

**Idea:** Maintain a window `[left, right]` that slides across the array/string to avoid recomputation. Left never moves backwards → O(n).

## When to Use
- "subarray/substring of size K", "longest/shortest substring with condition", "count subarrays matching X"
- Naive solution is O(n²) nested loops
- Trigger thought: *"Can I expand/shrink a window instead of recalculating?"*

## Core Loop
1. Expand `right` → add element
2. While window invalid → shrink `left`
3. Update answer when valid
4. Repeat until `right` hits end

---

## Templates

### A. Fixed-Size Window (size K constant)
```java
public int fixedSlidingWindow(int[] arr, int k) {
    int windowSum = 0, ans;
    for (int i = 0; i < k; i++) windowSum += arr[i];
    ans = windowSum;

    for (int right = k; right < arr.length; right++) {
        windowSum += arr[right] - arr[right - k]; // add incoming, remove outgoing
        ans = Math.max(ans, windowSum);
    }
    return ans;
}
```
Use for: "of size K", "last K elements". No while loop needed.

### B. Variable-Size Window (expand/shrink)
```java
public int variableSlidingWindow(int[] arr, int target) {
    int left = 0, ans = 0, windowSum = 0;
    for (int right = 0; right < arr.length; right++) {
        windowSum += arr[right];
        while (windowSum > target && left <= right) {
            windowSum -= arr[left++];
        }
        ans = Math.max(ans, right - left + 1);
    }
    return ans;
}
```
Use for: "longest/shortest/minimum", validity-based sizing. Most common variant.

> ⚠️ **The `while` condition flips depending on whether you want LONGEST or SHORTEST.** The template above is the "longest" shape — see below.

#### 🔑 Longest vs. Shortest — the `while` condition is NOT always "invalid"

| Goal | Shrink `while`... | Record answer... |
|---|---|---|
| **Longest** valid window | window is **invalid** (broken) | *after* the while, once valid again |
| **Shortest** valid window | window is **still valid** | *inside* the while, before it breaks |

**Shortest-window shape** (e.g. Smallest Subarray With a Greater Sum):
```java
public static int findSmallestSubarrayWithAGreaterSum(int[] nums, int s) {
    int minLength = Integer.MAX_VALUE;
    int currentSum = 0, windowStart = 0;

    for (int windowEnd = 0; windowEnd < nums.length; windowEnd++) {
        currentSum += nums[windowEnd];

        while (currentSum >= s) {                 // valid: keep shrinking to find smaller
            minLength = Math.min(minLength, windowEnd - windowStart + 1);
            currentSum -= nums[windowStart++];
        }
    }
    return minLength == Integer.MAX_VALUE ? 0 : minLength;
}
```
Why: once valid, a *smaller* valid window might still exist inside it — so you keep shrinking and recording until it finally breaks. This is the opposite loop shape from the "longest" template above.

**The one-line gut-check:** ask "am I trying to survive as long as possible (longest) or shrink as much as possible (shortest)?" — that tells you which side of the `while` the recording goes on.

### C. Variable-Size + HashMap (char frequency)
```java
public int variableSlidingWindowWithMap(String s, int k) {
    int left = 0, ans = 0;
    Map<Character, Integer> charCount = new HashMap<>();
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        charCount.merge(c, 1, Integer::sum);

        while (charCount.size() > k) {
            char lc = s.charAt(left);
            charCount.put(lc, charCount.get(lc) - 1);
            if (charCount.get(lc) == 0) charCount.remove(lc); // ⚠️ must remove at 0
            left++;
        }
        ans = Math.max(ans, right - left + 1);
    }
    return ans;
}
```
Use for: "k distinct chars", frequency tracking. ASCII-only? Use `int[128]` instead of HashMap for speed.

### D. Two-Pointer on Sorted Array (opposite ends)
```java
public int twoPointerSorted(int[] arr, int target) {
    int left = 0, right = arr.length - 1, count = 0;
    while (left < right) {
        int sum = arr[left] + arr[right];
        if (sum == target) { count++; left++; right--; }
        else if (sum < target) left++;
        else right--;
    }
    return count;
}
```
Use for: sorted array, pair/triplet with property (Two Sum II, 3Sum, Container With Most Water). Not a "shrinking window" — pointers move toward center.

---

## Decision Tree
Ask in this order — first match wins:

1. **Is the array sorted, and am I looking for a pair/triplet with some property?**
   → **Template D** (opposite-end two-pointer). Not really "windowing" — pointers move toward each other.

2. **Is the window size a fixed constant K?**
   → **Template A** (fixed-size). No `while` needed, just slide.

3. **Am I tracking character/element frequency (distinct counts, "at most K distinct", anagram matching)?**
   → **Template C** (variable-size + HashMap).

4. **Otherwise — numeric constraint (sum, product, count) with a variable-size window?**
   → **Template B** (variable-size). Remember: check longest-vs-shortest for which side of the `while` you record the answer (see above).


## Universal Rules
1. Never move `left` backwards
2. Window size = `right - left + 1` (not `right - left`)
3. Update answer only when window is valid
4. Each element touched ≤ 2× → O(n)

---

## Key Decisions Before Coding
- What makes the window valid/invalid?
- What data structure tracks state? (HashMap / int[] / counter)
- What's the answer metric? (length / indices / count / max-min)
- Shrink on invalid, or also to find longest/shortest?
- Need ALL valid windows? → each left-shrink step = `(right-left+1)` new windows ending at right

## Common Traps
- `left--` — never
- Mixing up longest vs. shortest logic: recording *after* the while (longest) when the problem needs *inside* the while (shortest), or vice versa
- `right - left` instead of `right - left + 1`
- HashMap when `int[128]` (ASCII) would be faster
- Forgetting to `remove()` a key at count 0
- Not testing empty array / single char / all-duplicates

## Complexity
**Time:** O(n) — each element visited at most twice.
**Space:** O(1) to O(k) depending on tracking structure.

## Canonical Problems
1. Longest Substring with K Distinct Characters — expand to k+1 distinct, shrink to k
2. Maximum Sum Subarray of Size K — fixed window
3. Longest Substring Without Repeating Characters — map of last-seen index
4. Minimum Window Substring — expand to satisfy, shrink to minimize
5. Permutation in String — fixed window, compare frequency maps

## Personal Notes
- Mental shortcut: *substring/subarray + definable validity → sliding window. Expand when valid, shrink when invalid, never go backwards.*
- Interview line: "Two pointers maintain a valid window — right expands, left shrinks to restore the condition. Each element processed once → linear time."
- Always forget: `right - left + 1`, not `right - left`.

## Checklist
- [ ] Constraint identified (valid/invalid condition)
- [ ] Data structure chosen
- [ ] Pointers initialized (`left=0, right=0`)
- [ ] Shrink condition correct
- [ ] Answer updated at right time
- [ ] Window size formula correct
- [ ] Edge cases tested (empty, single char, all dup)
- [ ] Traced one example by hand