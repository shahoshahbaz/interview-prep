package com.grokingcodeinterview.pattern04FastAndSlowPointers;
/*
Given the head of a Singly LinkedList,
 write a method to modify the LinkedList such that
 the nodes from the second half of the LinkedList are inserted alternately
  to the nodes from the first half in reverse order.
  So if the LinkedList has nodes 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null,
   your method should return 1 -> 6 -> 2 -> 5 -> 3 -> 4 -> null.
Your algorithm should use only constant space the Input: LinkedList should be modified in-place.

Example 1:

Input:: 2 -> 4 -> 6 -> 8 -> 10 -> 12 -> null
Output: 2 -> 12 -> 4 -> 10 -> 6 -> 8 -> null
Example 2:

Input:: 2 -> 4 -> 6 -> 8 -> 10 -> null
Output: 2 -> 10 -> 4 -> 8 -> 6 -> null
Constraints:

The number of nodes in the list is in the range [].
1 <= Node.val <= 1000
 */

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import static com.Utility.makeItBold;

/**
 * 2 -> 4 -> 6 -> 8S -> 10 -> 12
 * 2SF -> 4S -> 6SF -> 8 -> 10F -> null
 * reverse second half // 12 -> 10 -> 8
 * 2 -> 4 -> 6 -> 8 -> 12-> 10
 * secondHaf = 12
 * currernt= 2 //
 * secondHalfNext = secondHlaf.next; //10
 *  currNex = current.next; //4
 * curr.next = secondHalf; 2->10
 * secondHalf.next = currNex;
 * current = currNex; //4
 * secondHalf = secondHalfNext; //10
 * how to kep
 *

 */
public class P06RearrangeALinkedList {

    public static Node rearrangeList(Node head) {
        if (head == null || head.next == null) return head;

        // get middle Node
        Node slow = head;
        Node fast = head;


        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast != null)
            slow = slow.next; // 2SF -> 4S -> 6SF -> 8S -> 10F -> 12F => mid = '8'

        // slow will be last Node
        Node secondHalf = slow.next; // => 10 ->12
        slow.next = null; // in the new list, slow will be the last node: head 2 ->4 -> 6-> 8-> null
        // first reverse secondHalf
        Node reverseHead = reverse(secondHalf); // 12 -> 10
        Node curr = head;
        while (curr != null && reverseHead != null) {
            Node currentNext = curr.next; // 2C ->4CN -> 6-> 8-> null
            Node reverseHeadNext = reverseHead.next; // 12RH -> 10RHN -> null

            curr.next = reverseHead; // 2 ->12 ->4
            reverseHead.next = currentNext;
            // move both pointer
            curr = currentNext;
            reverseHead = reverseHeadNext;

        }

        return head;
    }

    public static Node reverse(Node head) {
        Node curr = head;
        Node prev = null;
        while (curr != null) {
            Node nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        return prev;
    }

    public static void main(String[] args) {

        System.out.println("===================================================");
        System.out.println("P06. Rearrange a Linked List");
        System.out.println("===================================================");
        LinkedList listP06   = new LinkedList();
        listP06.createLinkedList(new int[]{1, 2, 3, 4, 5, 6});

        System.out.print("Input: " +listP06.toString() +
                " ,Output: " + makeItBold(rearrangeList(listP06.getHead()).toString()) + ", Expected Output: 1->6->2->5->3->4->null\n");
        /// some tests can be added here
        listP06.clear();
        listP06.createLinkedList(new int[]{2, 4, 6, 8, 10, 12});
        System.out.print("Input: " +listP06.toString() +
                " ,Output: " + makeItBold(rearrangeList(listP06.getHead()).toString()) + ", Expected Output: 2->12->4->10->6->8->null\n");
        listP06.clear();
        listP06.createLinkedList(new int[]{2, 4, 6, 8, 10});
        System.out.print("Input: " +listP06.toString() +
                " ,Output: " + makeItBold(rearrangeList(listP06.getHead()).toString()) + ", Expected Output: 2->10->4->8->6 -> null\n");

    }
}
