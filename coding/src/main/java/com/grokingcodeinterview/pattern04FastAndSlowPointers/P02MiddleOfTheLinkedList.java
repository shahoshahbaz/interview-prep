package com.grokingcodeinterview.pattern04FastAndSlowPointers;
/*
Given the head of a Singly LinkedList, write a method to return the middle node of the LinkedList.

If the total number of nodes in the LinkedList is even, return the second middle node.

Example 1:

Input: 1 -> 2 -> 3 -> 4 -> 5 -> null
Output: 3
Example 2:

Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null
Output: 4
Example 3:

Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> null
Output: 4
 */

import com.ds.singleLinkedList.Node;

/**
 * odd element: 1SF -> 2S -> 3SF -> 4 -> 5F -> null  when odd number :fast.next is null return s
 * event element: 1SF -> 2S -> 3SF -> 4S -> 5F -> 6 -> nullF when even Number :fast is null return s
 */
public class P02MiddleOfTheLinkedList {


    public Node findMiddle(Node head) {
        Node slow = head;
        Node fast = head;

        while(fast!=null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        // some tests can be added here
        P02MiddleOfTheLinkedList solution = new P02MiddleOfTheLinkedList();
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        Node result = solution.findMiddle(head);
        System.out.println("Middle Node: " + result.data); // Expected output: 3

         // another test case with even number of nodes
        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        head2.next.next.next.next.next = new Node(6);
        Node result2 = solution.findMiddle(head2);
        System.out.println("Middle Node: " + result2.data); // Expected output: 4

    }
}

