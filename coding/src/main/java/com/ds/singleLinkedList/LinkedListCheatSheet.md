# Linked List Cheat Sheet

Use this page as a structured reference for linked list patterns.

## Table of Contents

- [1. Dummy Node in Linked List](#1-dummy-node-in-linked-list)

---

## 1. Dummy Node in Linked List

A dummy node (also called a sentinel node) is a placeholder node that does not store real business data. It acts as a stable anchor at the head (sometimes tail) of a linked list.

When performing insertion, deletion, or merge operations, you often need special handling for the head.

```java
// Deleting a node without a dummy node
public ListNode delete(ListNode head, int val) {
    if (head == null) return null;
    if (head.val == val) return head.next; // special case for head

    ListNode curr = head;
    while (curr.next != null) {
        if (curr.next.val == val) {
            curr.next = curr.next.next;
            break;
        }
        curr = curr.next;
    }
    return head;
}
```

The `if (head.val == val)` branch exists only because deleting the head changes the list entry point.

Prepend a fake node before the real head. Then operate from `dummy`, and return `dummy.next`.

```java
public ListNode delete(ListNode head, int val) {
    ListNode dummy = new ListNode(0); // value is irrelevant
    dummy.next = head;

    ListNode curr = dummy;
    while (curr.next != null) {
        if (curr.next.val == val) {
            curr.next = curr.next.next; // works even for first real node
            break;
        }
        curr = curr.next;
    }
    return dummy.next; // possibly new head
}
```

No head special-case logic is needed.

Use a dummy node when:

- You may modify the head (insert/delete/reorder near index 0).
- You are building a new list (for example, merge operations).
- You want a consistent `prev` pointer from the start of traversal.

```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;

    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            curr.next = l1;
            l1 = l1.next;
        } else {
            curr.next = l2;
            l2 = l2.next;
        }
        curr = curr.next;
    }

    curr.next = (l1 != null) ? l1 : l2;
    return dummy.next;
}
```

Without a dummy node, head initialization becomes more error-prone.

- Read-only traversal (search, count, sum).
- Simple iteration with no structural changes.


| Operation | Needs Dummy? | Why |
|---|---|---|
| Count length | No | Read-only; head does not change |
| Search / find | No | Read-only traversal |
| Delete a node | Yes | Head might be deleted |
| Insert at position | Yes | Head might change |
| Merge / build new list | Yes | Building from scratch needs an anchor |
| Reverse a list | Usually No* | Head changes, but commonly handled directly with pointer reversal |

\*You can still use a dummy node in some reversal variants, but it is not required for the standard reverse implementation.
