package com.grokingcodeinterview.testYourKnowledge47Medium;

/*
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

import com.ds.singleLinkedList.Node;

/**
 * 0S -> 1 -> 2 -> 3F -> 4 -> 5-> null, n = 2
 * 0S -> 1S -> 2S -> 3S -> 4F -> 5-> nullF, n = 2
 *
 */
public class Pattern36TestYourKnowlegeR3 {
    public static Node removeNth(Node head, int n){
        Node dummy = new Node(0);
        dummy.next = head;


        Node slow = dummy ;
        Node fast = dummy;

        for (int i =0; i<= n;i++){
            fast = fast.next;

        }

        while(fast != null ){
            slow = slow.next;
            fast = fast.next;

        }
        slow.next= slow.next.next;

        return dummy.next;

    }
}

