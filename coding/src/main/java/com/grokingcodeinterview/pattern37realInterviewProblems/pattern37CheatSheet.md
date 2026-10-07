# Kadane's Algorithm — Max Subarray Cheat Sheet

---

## What is it?
A **Dynamic Programming** technique that finds the maximum sum of a contiguous subarray in **O(n)** time and **O(1)** space.

---

## Core Idea
At every index, ask one local question:
> *"Is the previous subarray helping me or hurting me?"*

- If **helping** → extend it: `currentSum + nums[i]`
- If **hurting** → drop it, start fresh: `nums[i]`

By making the best local decision at every step, you arrive at the best global answer.

---

## The One Key Line
```java
currentSum = Math.max(nums[i], currentSum + nums[i]);
```
- `nums[i]` alone → **fresh start** (left boundary implicitly moves to i)
- `currentSum + nums[i]` → **extend** previous subarray

You never manage indexes manually. The `for` loop handles the right boundary, and `currentSum` handles the left boundary implicitly.

---

## Template
```java
public int maxSubArray(int[] nums) {
    // 1. Seed with first element — handles all-negative arrays
    int currentSum = nums[0];
    int maxSum     = nums[0];

    // 2. Start from index 1
    for (int i = 1; i < nums.length; i++) {
        // 3. Extend or start fresh (left boundary moves implicitly)
        currentSum = Math.max(nums[i], currentSum + nums[i]);

        // 4. Update global best
        maxSum = Math.max(maxSum, currentSum);
    }

    return maxSum;
}
```

---

## Dry Run Example
```
Input: [-2, 3, -1, 4, -6, 2, 3]

Init:  currentSum = -2, maxSum = -2

i=1  nums[i]=3   max(3,  -2+3)  = 3   maxSum = 3   // fresh start
i=2  nums[i]=-1  max(-1,  3-1)  = 2   maxSum = 3   // extend [3,-1]
i=3  nums[i]=4   max(4,   2+4)  = 6   maxSum = 6   // extend [3,-1,4]
i=4  nums[i]=-6  max(-6,  6-6)  = 0   maxSum = 6   // extend (less bad)
i=5  nums[i]=2   max(2,   0+2)  = 2   maxSum = 6   // extend
i=6  nums[i]=3   max(3,   2+3)  = 5   maxSum = 6   // extend

Output: 6  →  subarray [3, -1, 4]
```

---

## Complexity
| | |
|---|---|
| Time | O(n) — single pass |
| Space | O(1) — two variables |

---

## How to Recognize in an Interview
| Signal | Example |
|---|---|
| Contiguous subarray | "find a subarray that..." |
| Maximize/minimize sum | "largest sum", "minimum sum" |
| Array has negatives | mix of positive and negative numbers |

---

## Why NOT Sliding Window?
Sliding window needs a **shrink condition** — a rule for when to move the left pointer. Negative numbers make that undefined here. Kadane's sidesteps it by tracking the best subarray *ending at each index* instead.

| | Sliding Window | Kadane's |
|---|---|---|
| Left boundary | You move explicitly | Moves implicitly via `currentSum` |
| Right boundary | You move explicitly | The `for` loop |
| Manage indexes? | Yes | No |

---

## Common Variants
| Variant | What changes |
|---|---|
| Return the subarray itself | Track `start`, `end`, `tempStart` indexes |
| Maximum product subarray | Track both `maxProduct` and `minProduct` (negatives flip sign) |
| Circular subarray | Run Kadane's normally + run on inverted array |

---

## Related Patterns
- **Prefix Sum** — another way to think about subarray sums
- **Dynamic Programming** — Kadane's is DP where `dp[i]` = best subarray ending at i
- **Divide and Conquer** — alternative O(n log n) solution to same problem

---

## LeetCode Problems
| # | Problem | Difficulty |
|---|---|---|
| 53 | Maximum Subarray | 🟡 Medium |
| 152 | Maximum Product Subarray | 🟡 Medium |
| 918 | Maximum Sum Circular Subarray | 🟡 Medium |