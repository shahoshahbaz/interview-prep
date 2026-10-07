# 🔹 Interview Coding Pattern – Cheat Sheet

---

## Pattern Name

**Fast & Slow Pointers (Tortoise & Hare)**

---

## When to Use This Pattern (Recognition Signals)

Use this pattern when:

* You are traversing a **linked list** or sequence step-by-step
* You need to detect **cycles** or find a **middle point**
* You must compare elements from **both ends** of a list
* The problem hints at **"one pass"** or **O(1) space**

### Complexity Hint:

* One pointer moves faster → implicit compression of time

---

## Core Idea (Mental Model)

Two pointers move through the structure at different speeds.

* **Slow** → moves 1 step
* **Fast** → moves 2 steps

This lets you:

* Detect cycles (they meet)
* Find middle (slow ends at middle)
* Split list into halves

**Rule:** If fast reaches the end, slow is at a meaningful position.

---

## Standard Code Template (Skeleton)

**This stays identical across problems. Only logic changes.**

```java
Node slow = head;
Node fast = head;

while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
// slow is now at the middle / meeting point
```

⚠️ **Important:** Never touch this structure unless you know *why*.

---

## Dummy Node: When to Use vs Skip

Fast & slow problems split into two camps — the pointer logic is identical, but whether you need a `dummy` node depends on what happens **after** you find your position.

**Use a dummy node when:**
* The **head itself might change or get removed** (e.g., Remove Nth Node From End — if `n` equals list length, the head is the node being deleted)
* You're **building/reconnecting a new list** and need a stable anchor before the real head exists

**Skip the dummy node when:**
* You're doing a **read-only traversal** — just finding a position, counting, or comparing (e.g., Middle of the LinkedList, Linked List Cycle, Palindrome LinkedList check)
* The head is guaranteed to stay the head no matter what the algorithm does

👉 **Rule of thumb:** dummy node = "the head might not survive this operation." No dummy = "I'm just looking, not rearranging."

| Problem | Dummy? | Why |
|---|---|---|
| Middle of LinkedList | ❌ No | Read-only, head never moves |
| Linked List Cycle | ❌ No | Read-only, just detecting |
| Palindrome LinkedList | ❌ No | Reads/compares, doesn't restructure the returned list |
| Remove Nth Node From End | ✅ Yes | Head may be the node removed |
| Rearrange a LinkedList | ✅ Yes | Head node gets spliced/reordered |

⚠️ **Trap:** adding a dummy node "just in case" on a read-only traversal is a common off-by-one source — it shifts your pointer math for no reason.

---

## Key Decisions to Make (Interview Gold)

Ask yourself:

* Do I need the **exact middle** or **first half end**?
* Should fast start at `head` or `head.next`?
* Do I stop at `fast != null` or `fast.next != null`?
* Am I modifying the list (reverse, merge) after splitting?

👉 These decisions separate correct vs buggy solutions.

---

## Common Traps & Mistakes

* ❌ Using wrong while condition → off-by-one middle
* ❌ Forgetting odd vs even length behavior
* ❌ Not breaking the list before reversing second half
* ❌ NullPointerException from `fast.next.next`

👉 **Personal tip:** Draw 3–5 nodes on paper every time.

---

## Time & Space Complexity

**Time:** O(n)

**Space:** O(1)

**Explain why in one line:**

> Each pointer traverses the list once without extra memory

---

## Canonical Problems (Must-Know)

These are your **anchor problems**.

**Problem 1:**

* Name: Linked List Cycle
* Key insight: Fast and slow must meet inside cycle

**Problem 2:**

* Name: Palindrome Linked List
* Key insight: Split → reverse second half → compare

---

## My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:

> "Fast hits null → slow is meaningful"

### My Favorite Variation:

> Find start of cycle after detection

### What I Always Forget:

> Even-length lists shift the middle left/right

---

## 📋 Quick Checklist Before Coding

* ☐ Drew list with odd + even length
* ☐ Picked correct while condition
* ☐ Considered null safety
* ☐ Decided: does the head change? → dummy node or not
* ☐ Explained pointer movement verbally