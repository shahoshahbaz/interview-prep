package com.grokingcodeinterview.pattern04FastAndSlowPointers;
/*
Given the head of a Singly LinkedList, write a function to determine if the LinkedList has a cycle in it or not.

Constraints:

The number of the nodes in the list is in the range [0, 104].
-105 <= Node.val <= 105
 */

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

/**
 *  1 ->2-> ->3 ->4 ->5 -> 6
 *            < -----------
 */

public class P01LinkedListCycle {

    public  static boolean hasCycle(Node head) {

            Node slow = head;
            Node fast = head;

            while (fast != null && fast.next != null){
                slow =slow .next;
                fast = fast.next.next;
                if (slow == fast){
                    return true;
                }
            }
            return false;
    }

        public static void main(String[] args) {
            // some tests can be added here
            // Create a linked list with a cycle for testing

            Node head = new Node(1);
            head.next = new Node(2);
            head.next.next = new Node(3);
            head.next.next.next = new Node(4);
            head.next.next.next.next = new Node(5);
            // Creating a cycle here
            head.next.next.next.next.next = head.next; // Cycle here
            boolean result = hasCycle(head);
            System.out.println("Linked List has cycle: " + result); // Expected output: true
             // another test case without cycle
            Node head2 = new Node(1);
            head2.next = new Node(2);
            head2.next.next = new Node(3);
            head2.next.next.next = new Node(4);
            head2.next.next.next.next = new Node(5);
            boolean result2 = hasCycle(head2);
            System.out.println("Linked List has cycle: " + result2); // Expected output: false



        }
}

