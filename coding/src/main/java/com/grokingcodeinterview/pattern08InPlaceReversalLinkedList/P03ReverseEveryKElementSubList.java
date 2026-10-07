package com.grokingcodeinterview.pattern08InPlaceReversalLinkedList;

/*
Given the head of a LinkedList and a number â€˜kâ€™, reverse every â€˜kâ€™ sized sub-list starting from the head.

If, in the end, you are left with a sub-list with less than â€˜kâ€™ elements, reverse it too.
example 1:
Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> null, and k = 3
Output: 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 8 -> 7 -> null

example 2:
Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> null, and k = 2
Output: 2 -> 1 -> 4 -> 3 -> 6 -> 5 -> 8 -> 7 -> null

example 3:
Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> null, and k = 1
Output: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> null

 */

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import static com.Utility.makeItBold;

/**
 * 1(c) -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> null, and k = 3
 *  tps: tailOfPrevSubList
 *  tcs: tailOfCurrentSubList
 *  1(c)(tcs) -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> null, and prev = null and tps = null
 *   reverseknodes(1,3) => returns [3,4]
 *   prev = 3
 *   curr = 4
 *   1(tcs) <- 2 <- 3 -> 4(c) -> 5 -> 6 -> 7 -> 8 -> null, tps = null
 *   tps == null => head = prev(3)
 *   1(tcs) <- 2 <- 3(H) -> 4(c) -> 5 -> 6 -> 7 -> 8 -> null, tps = null
 *   tcs.next = curr(4)
 *   prev = tcs(1)
 *   ----------------------------------------
 *   3->2->1(tps) -> 4(c)(tcs) -> 5 -> 6 -> 7 -> 8 -> null, and prev = 1 and tps = 3
 *  *
 *
 *
 *  *
 */
public class P03ReverseEveryKElementSubList {

    public static Node reverseEveryKSubList(Node head, int k){

        if (k<=1|| head == null) return head;

        Node curr = head;
        Node prev = null;

        while(curr!= null){
            Node tailOfPrevSubList = prev;
            Node tailOfCurrentSubList = curr; // after reversing this will become the last node of the sublist.

            // reverse k nodes
            Node[] reversed= reverseKNodes(curr, k); // so here curr node got updated to next sublist head

            prev = reversed[0]; // new head of the reversed sublist
            curr = reversed[1]; // next sublist head


            if (tailOfPrevSubList != null){
                tailOfPrevSubList.next = prev; // prev is now the first node of the sublist
            }else{
                head = prev; // this is the new head of the linked list
            }


            tailOfCurrentSubList.next = curr; // connect with the next part if curr is null means we have reached the end of the linked list

            prev = tailOfCurrentSubList; // prev is now the last node of the reversed sublist

        }

        return head;
    }

    // extracted function to reverse k nodes
    private static Node[] reverseKNodes(Node head, int k) {
        Node curr = head;
        Node prev = null;
        Node next = null;
        int count = 0;
        while (curr != null && count < k) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            count++;
        }
        return new Node[]{prev, curr}; // now prev points to the new head and curr points to the next sublist head
    }

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("P03. Reverse Every K Element SubList... ");
        System.out.println("===================================");

        LinkedList listP03 = new LinkedList();
        listP03.createLinkedList(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        int kP03 = 3;
        System.out.println("Input: " + listP03.getHead().toString() + ", k: " + kP03 + ", Output: " +
                makeItBold(reverseEveryKSubList(listP03.getHead(), kP03).toString()) + ", expected: 3->2->1->6->5->4->8->7->null");

        // more test cases

        listP03.clear();
        listP03.createLinkedList(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        kP03 = 2;
        System.out.println("Input: " + listP03.getHead().toString() + ", k: " + kP03 + ", Output: " +
                makeItBold(reverseEveryKSubList(listP03.getHead(), kP03).toString()) + ", expected: 2->1->4->3->6->5->8->7->null");
        listP03.clear();
        listP03.createLinkedList(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        kP03 = 1;
        System.out.println("Input: " + listP03.getHead().toString() + ", k: " + kP03 + ", Output: " +
                makeItBold(reverseEveryKSubList(listP03.getHead(), kP03).toString()) + ", expected: 1->2->3->4->5->6->7->8->null");

        // give an edge case with less than k nodes
        listP03.clear();
        listP03.createLinkedList(new int[]{1, 2});
        kP03 = 3;
        System.out.println("Input: " + listP03.getHead().toString() + ", k: " + kP03 + ", Output: " +
                makeItBold(reverseEveryKSubList(listP03.getHead(), kP03).toString()) + ", expected: 2->1->null");

        listP03.clear();
        listP03.createLinkedList(new int[]{});
        kP03 = 3;
        System.out.println("Input: " + makeItBold("null") + ", k: " + kP03 + ", Output: " +
                makeItBold((reverseEveryKSubList(listP03.getHead(), kP03)) == null?"nul":(reverseEveryKSubList(listP03.getHead(), kP03).toString()).toString()) + ", expected: null");

        listP03.clear();
        listP03.createLinkedList(new int[]{1});
        kP03 = 2;
        System.out.println("Input: " + listP03.getHead().toString() + ", k: " + kP03 + ", Output: " +
                makeItBold(reverseEveryKSubList(listP03.getHead(), kP03).toString()) + ", expected: 1->null");






    }
}

