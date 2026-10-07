# 🔹 Interview Coding Pattern – In-Place Reversal of Linked List

---

## 1️⃣ Pattern Name

**In-Place Reversal of Linked List**

---

## 2️⃣ When to Use This Pattern (Recognition Signals)

Use this pattern when the problem asks you to:

- Reverse a linked list **without extra memory**
- Reverse **part** of a linked list (sublist)
- Reverse in **groups (k nodes)**
- Check palindrome by reversing half
- Reorder list by reversing second half

### Typical Phrases in Problem Statement

- "Reverse the linked list…"
- "Modify the list in place…"
- "O(1) extra space…"

### Complexity Hint

If they emphasize **constant space**, this pattern is screaming at you.

### One-Line Trigger Thought

> "I need to reverse pointers, not values."

---

## 3️⃣ Core Idea (Mental Model)

You reverse a linked list by **re-pointing `next` pointers**, one node at a time.

Think of it as:
- Detaching the current node
- Pointing it backward
- Moving forward safely

You always need **three pointers**:
- `prev` → already reversed part
- `current` → node being processed
- `next` → saved forward reference

**Rule:** Never lose access to the rest of the list.

---

## 4️⃣ Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

```java
public static ListNode reverse(ListNode head) {
    ListNode prev = null;
    ListNode current = head;

    while (current != null) {
        ListNode next = current.next; // save
        current.next = prev;          // reverse
        prev = current;               // advance prev
        current = next;               // advance current
    }

    return prev;
}
```

### ⚠️ Important Notes

- Order of operations matters
- One wrong pointer update = broken list

---

## 5️⃣ Key Decisions to Make (Interview Gold)

✅ **Answer these before coding:**

### Am I reversing:
- Entire list?
- From left to right?
- Every k nodes?

### Do I need to reconnect:
- Node before the reversed part?
- Node after the reversed part?

### Edge Cases to Consider
- Is the list length odd or even?
- Do I need to restore the list after checking something (e.g., palindrome)?

---

## 6️⃣ Common Traps & Mistakes

❌ Forgetting to save `next` before reversing

❌ Returning `head` instead of `prev`

❌ Losing connection to unreversed part

❌ Off-by-one errors in sublist reversal

❌ Messing up reconnection logic after reversal

### 👉 Pro Tip

Draw 3 nodes on paper and trace pointer changes. If you can't trace it, don't code it.

---

## 7️⃣ Time & Space Complexity

| Metric | Value | Reason |
|--------|-------|--------|
| **Time** | O(n) | Each node is visited once |
| **Space** | O(1) | Pointers are reversed in place |

---

## 8️⃣ Canonical Problems (Must-Know)

These are your anchor problems. Master these first.

### Problem 1: Reverse Linked List
- **Key Insight:** Maintain `prev`, `current`, `next`

### Problem 2: Reverse Sublist (m to n)
- **Key Insight:** Reverse only between bounds, reconnect both ends

### Problem 3: Reverse Nodes in k-Group
- **Key Insight:** Reverse fixed-size chunks recursively or iteratively

### Problem 4: Palindrome Linked List
- **Key Insight:** Reverse second half, compare, restore

---

## 9️⃣ My Personal Notes (Critical Section)

*This is what separates seniors from juniors.*

### Mental Shortcut
> "Reverse pointers until the stopping condition, then reconnect."

### Interview Phrasing
> "We reverse the list in place by re-pointing next pointers using three references."

### My Favorite Variation
Reverse second half to compare two halves (palindrome check)

### What I Always Forget
Reconnecting the tail of the reversed section back to the remaining list

---

## 🔗 In-Place Reversal Connection (Plumbing) Template

**This is the critical part when reversing k-node groups or sublists.**

### The Three Pointers You Need

1. **`tailOfPrevSubList`**: Last node of previously reversed segment
2. **`tailOfCurrentSubList`**: First node of current segment (becomes last after reversal)
3. **`curr`**: Head of next segment (after reversal)

### Step-by-Step Connection Logic

**BEFORE reversing:**
```
Save: tailOfCurrentSubList = curr  // This will be the TAIL after reversal
```

**AFTER reversing:**
```
prev = new head of reversed segment
curr = head of next segment
```

**CONNECT (the plumbing):**
```java
if (tailOfPrevSubList != null) {
    tailOfPrevSubList.next = prev;  // Connect previous segment's tail to current segment's new head
} else {
    head = prev;  // First segment, update the main head
}

tailOfCurrentSubList.next = curr;  // Connect current segment's tail to next segment's head

prev = tailOfCurrentSubList;  // Move prev pointer forward for next iteration
```

### Visual Example (k=3)

```
Original:  1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8

After reversing first 3 nodes:
           3 -> 2 -> 1 -> [4 -> 5 -> 6 -> 7 -> 8]
                    ↑    ↑
              tailOfCurrentSubList  curr (head of next segment)
```

### Key Insight

You're **sandwiching** the reversed segment between:
- **Left connection**: Previous segment's tail → Current segment's new head
- **Right connection**: Current segment's tail → Next segment's head

This ensures every segment connects properly without losing any nodes.

### Real Code Example (from P03ReverseEveryKElementSubList.java)

```java
Node tailOfPrevSubList = prev;
Node tailOfCurrentSubList = curr;  // Save BEFORE reversing

Node[] reversed = reverseKNodes(curr, k);
prev = reversed[0];  // New head of reversed segment
curr = reversed[1];  // Next segment head

// THE PLUMBING
if (tailOfPrevSubList != null) {
    tailOfPrevSubList.next = prev;
} else {
    head = prev;  // First segment
}

tailOfCurrentSubList.next = curr;  // Connect to next part
prev = tailOfCurrentSubList;  // Move for next iteration
```

---

## 📋 Quick Checklist Before Coding

- [ ] Did I save `next` before reversing?
- [ ] Did I move pointers in the correct order?
- [ ] Did I return the correct new head?
- [ ] Did I reconnect all list segments?
- [ ] Did I test with 1-node and 2-node lists?

