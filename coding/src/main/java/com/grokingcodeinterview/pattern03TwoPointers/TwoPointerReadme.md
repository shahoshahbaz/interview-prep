# Two-Pointer — Simple Version (Thumb Rules)

## 1️⃣ If array is sorted → use opposite directions
- Start one pointer at the start, one at the end.
- Move inward depending on condition (sum too big → move right left, sum too small → move left right).
- **Used for:** Two-sum, triplet sum, container with most water.

## 2️⃣ If array is unsorted → use same direction
- Start both pointers from beginning (or one ahead).
- One moves fast, one moves slow to track a range or condition.
- **Used for:** Subarrays, removing duplicates, sliding window, slow/fast linked list.

## 3️⃣ If you’re finding a window (subarray/substring) → grow & shrink
- Move right to expand window until condition breaks.
- Move left to shrink window until it’s valid again.
- **Used for:** Longest substring, smallest subarray with sum ≥ target.

## 4️⃣ If question says “in-place” → two-pointer cleanup
- Use two pointers to overwrite or rearrange elements without extra space.
- **Used for:** Removing duplicates, sorting colors, moving zeros.

## 5️⃣ If you need to detect middle or cycle in linked list → fast & slow
- fast moves 2 steps, slow moves 1.
- **Used for:** Cycle detection, finding middle node.

---

### ✅ Shortcut Summary:

| Goal                        | Pointer Direction | Typical Use                  |
|-----------------------------|-------------------|------------------------------|
| Sorted array?               | Opposite          | Two-sum, triplet, optimize   |
| Unsorted array?             | Same              | Window, fast/slow            |
| Need smallest/largest window?| Expand + shrink   | Sliding window               |
| In-place modification?      | Same              | Cleanup problems             |
| Linked list traversal?      | Fast & slow       | Cycle/middle                 |

| Problem Type          | Start Pointers         | Move Direction                       | Why?                    |
| --------------------- | ---------------------- | ------------------------------------ | ----------------------- |
| **Remove duplicates** | slow=0, fast=1         | both forward                         | compare adjacent values |
| **Move zeros**        | slow=0, fast=0         | both forward                         | scan + reposition       |
| **Dutch flag**        | low=0, mid=0, high=n-1 | mid fwd, high back                   | 3-way partition         |
| **Sliding window**    | left=0, right=0        | right fwd, left fwd only when needed | maintain constraints    |
| **Two ends (2-sum)**  | left=0, right=n-1      | toward middle                        | sum comparison          |
let’s boil it down into one clean table that answers those three points directly.

| Question                                                     | Quick Rule                                                                            | What It Means                                                                                                                                                                                                                               |
| ------------------------------------------------------------ | ------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **How to detect which two-pointer pattern a problem needs?** | Look at what you're comparing.                                                        | Adjacent values → forward pointers; Sum/search → opposite ends; Constraints → sliding window.                                                                                                                                               |
| **How to know if swapping is needed?**                       | If you’re *rearranging* elements or *partitioning*, you swap.                         | Move zeros, Dutch flag → yes; Remove duplicates, 2-sum, sliding window → no.                                                                                                                                                                |
| **How to know if both pointers move or only one moves?**     | If they work *together*, both move. If one scans and the other marks, only one moves. | Remove duplicates → fast always moves, slow only when unique; Sliding window → right moves, left moves only on violation; Dutch flag → mid moves every time, others conditionally; Two ends → only one pointer moves per step based on sum. |


⭐ Side-by-Side Comparison of Filterring /comparisgon
| Topic                  | Read/Write (Filtering)                         | Classic Two-Pointer (Comparison)                 |
| ---------------------- | ---------------------------------------------- | ------------------------------------------------ |
| Goal                   | Keep good values / remove bad                  | Compare two elements or positions                |
| Pointer roles          | read scans, write compacts                     | both used for logic decisions                    |
| Starting points        | read=0, write=0                                | often different (0/1 or 0/n-1)                   |
| Direction              | both forward                                   | various: inward, outward, mixed                  |
| Comparison?            | ❌ No                                           | ✔️ Yes                                           |
| Typical output         | new length or compact array                    | index pairs, partitions, validations             |
| What triggers movement | read always moves; write moves on good element | depends on comparison result                     |
| Typical problems       | remove key, move zeros, filter                 | 2-sum, remove duplicates, Dutch flag, palindrome |

🎯 Final takeaway
⭐ Read/Write = Filtering problems

“Scan everything → write only what we want.”

⭐ Classic Two-Pointer = Comparison problems

“Move pointers based on how their values relate.”