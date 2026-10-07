package com.grokingcodeinterview.testYourKnowledge47Medium;

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import static com.Utility.makeItBold;

/*
Remove Nth Node From End of List (medium)
Problem Statement
Given a linked list,
remove the last nth node from the end of the list and return the head of the modified list.

Example 1:

Input: list = 1 -> 2 -> 3 -> 4 -> 5, n = 2
Expected Output: 1 -> 2 -> 3 -> 5
Justification: The 2nd node from the end is "4", so after removing it, the list becomes [1,2,3,5].
Example 2:

Input: list = 10 -> 20 -> 30 -> 40, n = 4
Expected Output: 20 -> 30 -> 40
Justification: The 4th node from the end is "10", so after removing it, the list becomes [20,30,40].
Example 3:

Input: list = 7 -> 14 -> 21 -> 28 -> 35, n = 3
Expected Output: 7 -> 14 -> 28 -> 35
Justification: The 3rd node from the end is "21", so after removing it, the list becomes [7,14,28,35].
Constraints:

The number of nodes in the list is sz.
1 <= sz <= 30
0 <= Node.val <= 100
1 <= n <= sz
 */

/**
 *  7 -> 14 -> 21 -> 28 -> 35, n = 3
 *  L: 5
 *  n = 3 diff: 2
 */
public class P09RemoveNthNodeFromEndOfList {

     public static Node removeNth(Node head, int n) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node slow = dummy;
        Node fast = dummy;

        for (int i = 0; i<= n+1; i++){
            fast = fast.next;
        }

        while(fast != null){
            slow = slow.next;
            fast = fast.next;
        }
        Node deletedNode = slow.next;
        slow.next = deletedNode.next;
        deletedNode.next = null;

        return dummy.next;
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("P09 Remove Nth Node From End Of List");
        System.out.println("==================================================================");
        LinkedList listP09 = new LinkedList();
        listP09.createLinkedList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Input: " + listP09.toString());
                Node result = removeNth(listP09.getHead(), 2);
        LinkedList resultListP09 = new LinkedList();
        resultListP09.setHead(result);
        System.out.println(", n = 2, output: " + makeItBold(resultListP09.toString() ) + ", expected: " + makeItBold("1 -> 2 -> 3 -> 5 -> null"));

        listP09 = new LinkedList();
        listP09.createLinkedList(new int[]{10, 20, 30, 40});
        System.out.print("Input: " + listP09.toString());
        result = removeNth(listP09.getHead(), 4);
        resultListP09 = new LinkedList();
        resultListP09.setHead(result);
        System.out.println(", n = 4, output: " + makeItBold(resultListP09.toString() ) + ", expected: " + makeItBold("20 -> 30 -> 40 -> null"));
        listP09 = new LinkedList();
        listP09.createLinkedList(new int[]{7, 14, 21, 28, 35});
        System.out.print("Input: " + listP09.toString());
        result = removeNth(listP09.getHead(), 3);
        resultListP09 = new LinkedList();
        resultListP09.setHead(result);
        System.out.println(", n = 3, output: " + makeItBold(resultListP09.toString() ) + ", expected: " + makeItBold("7 -> 14 -> 28 -> 35 -> null"));


    }
}

