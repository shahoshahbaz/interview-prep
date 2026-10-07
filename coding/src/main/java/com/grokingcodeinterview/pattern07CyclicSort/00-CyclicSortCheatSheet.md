# 🔹 Cyclic Sort

---

## 1️⃣ **Cyclic Sort**

A technique to sort in-place when array values fall within a known range (1 to N or 0 to N).

---

## 2️⃣ When to Use This Pattern (Recognition Signals)

Use this pattern when:

- Array size = N
- Values are in a **known range** (1 to N or 0 to N)
- Problem asks about:
  - Missing numbers
  - Duplicates
  - Corrupt pairs
  - Smallest missing positive
- You need **O(1) space** solution

### Typical Phrases in Problem Statement

- "Find missing/duplicate number"
- "Numbers from 1 to N"
- "Array length equals value range"
- "In-place sorting required"

### Complexity Hint

If they say "values match array indices" or emphasize **constant extra space**, this pattern is your answer.

### One-Line Trigger Thought

> "Each number has exactly one correct index. Let's place it there."

---

## 3️⃣ Core Idea (Mental Model)

The key insight: **When array size = N and values are 1 to N, each number belongs at exactly one index.**

Think of it as:
- `value = arr[i]` should go to index `value - 1`
- If `arr[i]` is not at its correct position, swap it
- Keep swapping until the value at position `i` is correct
- Then move to the next index

**Rule:** Every number knows where it belongs. Don't waste time—just put it there.

---

## 4️⃣ Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

```java
int i = 0;
while (i < arr.length) {
    // Calculate where arr[i] should be
    int correctIndex = arr[i] - 1; // or just arr[i] if 0-based range
    
    // If value is not at its correct index, swap it
    if (arr[i] != arr[correctIndex]) {
        swap(arr, i, correctIndex);
    } else {
        // Value is already in correct position, move forward
        i++;
    }
}
```

### ⚠️ Important Notes

- **Do NOT increment `i` when you swap** — you need to check the new value at `i`
- Only increment when the value at `i` is already correct
- Order matters: check condition BEFORE swapping

---

## 5️⃣ Key Decisions to Make (Interview Gold)

✅ **Answer these before coding:**

### Is the range 1 to N or 0 to N?
- If 1 to N → `correctIndex = arr[i] - 1`
- If 0 to N → `correctIndex = arr[i]`

### What happens after sorting?
- Find missing: scan for `arr[i] != i + 1`
- Find duplicate: the value at that index
- Find corrupt pair: missing = `i + 1`, duplicate = `arr[i]`

### Are there invalid values to skip?
- In "First Missing Positive," skip values ≤ 0 or > N
- Condition: `if (arr[i] > 0 && arr[i] <= n)`

### Can I modify the input array?
- Cyclic Sort requires in-place modification
- If read-only, this pattern won't work

---

## 6️⃣ Common Traps & Mistakes

❌ Incrementing `i` after every swap (wrong!)

❌ Using `correctIndex = arr[i]` when range is 1 to N

❌ Not checking if value is already at its position before swapping

❌ Forgetting to handle duplicate values

❌ Returning wrong index during the scan phase

❌ Confusing 0-based and 1-based ranges

### 👉 Pro Tip

Always trace through a small example (3 elements) by hand. Write down each swap. If you can't trace it, don't code it.

Example: `[3, 1, 2]`
```
i=0: arr[0]=3, correctIndex=2, arr[2]=2 ≠ 3 → swap(0,2) → [2, 1, 3]
i=0: arr[0]=2, correctIndex=1, arr[1]=1 ≠ 2 → swap(0,1) → [1, 2, 3]
i=0: arr[0]=1, correctIndex=0, arr[0]=1 = 1 → i++
i=1: arr[1]=2, correctIndex=1, arr[1]=2 = 2 → i++
i=2: arr[2]=3, correctIndex=2, arr[2]=3 = 3 → i++
Done: [1, 2, 3]
```

---

## 7️⃣ Time & Space Complexity

| Metric | Value | Reason |
|--------|-------|--------|
| **Time** | O(N) | Each element is visited and placed at correct index at most once |
| **Space** | O(1) | No extra data structures, only pointers and swaps |

---

## 8️⃣ Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

### Problem 1: Cyclic Sort (Basic)
- **Key Insight:** Place each number at `value - 1` index, no duplicates

### Problem 2: Find the Missing Number
- **Key Insight:** After sorting, scan for `arr[i] != i + 1`, that's the missing one

### Problem 3: Find All Missing Numbers
- **Key Insight:** Scan and collect all indices where `arr[i] != i + 1`

### Problem 4: Find Duplicate Number
- **Key Insight:** During swap, if `arr[i] == arr[correctIndex]`, that's the duplicate

### Problem 5: Find the Corrupt Pair
- **Key Insight:** One number is missing, one is duplicate; scan to find both

### Problem 6: Find the Smallest Missing Positive (Hard)
- **Key Insight:** Only sort valid values (> 0 and ≤ N), scan for first mismatch

---

## 9️⃣ My Personal Notes (Critical Section)

*This is what separates seniors from juniors.*

### Mental Shortcut

> "Values know their home address. Evict squatters until everyone is home."

### Interview Phrasing

> "Since the array contains numbers from 1 to N and has length N, I can use Cyclic Sort to place each number at its correct index in O(N) time and O(1) space."

### My Favorite Variation

Finding the first missing positive in an array with arbitrary values. Filter the noise, then apply Cyclic Sort to valid values. Elegant!

### What I Always Forget

**Handling the scan phase correctly:**
- After sorting, you need a SECOND loop to scan
- Find the first index where `arr[i] != i + 1`
- That index reveals the missing number

---

## 📋 Quick Checklist Before Coding

- [ ] Did I identify the range (1 to N or 0 to N)?
- [ ] Did I calculate `correctIndex` correctly?
- [ ] Am I incrementing `i` only when value is already correct?
- [ ] Did I NOT increment `i` after a swap?
- [ ] Did I handle duplicates (don't swap if values are equal)?
- [ ] Did I write the second loop to find missing/duplicate?
- [ ] Did I test with a small example (3-5 elements)?
- [ ] Did I consider edge cases (empty array, single element)?

