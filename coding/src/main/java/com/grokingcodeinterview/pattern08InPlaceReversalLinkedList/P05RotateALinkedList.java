package com.grokingcodeinterview.pattern08InPlaceReversalLinkedList;

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import static com.Utility.makeItBold;

/*
  Problem Statement
  Given the head of a Singly LinkedList and a number â€˜kâ€™, rotate the LinkedList to the right by â€˜kâ€™ nodes.
  Example: 1-> 2-> 3-> 4-> 5-> 6-> null and k = 3 output: 4-> 5-> 6-> 1-> 2-> 3-> null
  Example: 1-> 2-> 3-> 4-> 5-> 6-> null and k = 4 output: 3-> 4-> 5-> 6-> 1-> 2-> null
  Example: 1-> 2-> 3-> 4-> 5-> 6-> null and k = 0 output: 1-> 2-> 3-> 4-> 5-> 6-> null
  Example: 1-> 2-> 3-> 4-> 5-> 6->7->8-> null and k = 15 output: 4-> 5-> 6->7->8->1-> 2-> 3-> null
  Example: null and k = 3 output: null
  Example: 1->2-> null and k = 3 output: 2->1-> null
    Example: 1-> null and k = 99 output: 1-> null

  Constraints:

The number of nodes in the list is in the range [0, 500].
-100 <= Node.val <= 100
0 <= k <= 2 * 10^9
 */

/**
 *  1-> 2-> 3-> 4-> 5-> 6-> null and k = 3 output: 4-> 5-> 6-> 1-> 2-> 3-> null
 *  rorate to right by K  node
 *  k = 3 , n = 6 3% 6 = 6
 *  1-> 2-> 3-> 4-> 5-> 6-> null
 * |--------------------|
 * k = 3
 *  1c-> 2c-> 3c-> 4-> 5-> 6-> null
 * |--------------------|
 *  1-> 2-> 3-> 4-> 5-> 6->7->8-> null and k = 15 output: 4-> 5-> 6->7->8->1-> 2-> 3-> null
 *  k = 15 n = 8 k = 8%15 = 8
 *
 *
 */
public class P05RotateALinkedList {
    public static Node rotate(Node head, int k){
        if(head == null || head.next == null|| k ==0  ) return head;

        // find the length of list;
        int length =0;
        Node curr = head;
        Node lastNode = head;
        while (curr!= null){
            lastNode = curr;
            curr = curr.next;
            length ++;
        }

        // normalize the k

        k = k % length;
        if (k == 0) return head;
        // connect last node to head;
        curr = head;
        lastNode.next = curr;

        for (int i =0;curr != null && i< length - k-1; i++){
            curr = curr.next;
        }
        head = curr.next;
        curr.next = null;
        return head;



    }

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("P05. Rotate A Linked List... ");
        System.out.println("===================================");

        LinkedList listP05 = new LinkedList();
        listP05.createLinkedList(new int[]{1,2,3,4,5,6,7,8});
        System.out.print("Input: " + listP05.getHead().toString() + ", k: 2, Output: ");
        listP05.setHead( rotate(listP05.getHead(), 2));
        System.out.println( makeItBold(listP05.getHead().toString()) + ", expected: 7->8->1->2->3->4->5->6->null");
        listP05.clear();
        listP05.createLinkedList(new int[]{1,2,3,4,5,6,7,8});
        System.out.print("Input: " + listP05.getHead().toString() + ", k: 3, Output: ");
        listP05.setHead( rotate(listP05.getHead(), 3));
        System.out.println( makeItBold(listP05.getHead().toString()) + ", expected: 6->7->8->1->2->3->4->5->null");
        listP05.clear();
        listP05.createLinkedList(new int[]{1,2,3,4,5,6,7,8});
        System.out.print("Input: " + listP05.getHead().toString() + ", k: 4, Output: ");
        listP05.setHead( rotate(listP05.getHead(), 4));
        System.out.println( makeItBold(listP05.getHead().toString()) + ", expected: 5->6->7->8->1->2->3->4->null");
        listP05.clear();
        listP05.createLinkedList(new int[]{1,2,3,4,5,6,7,8});
        System.out.print("Input: " + listP05.getHead().toString() + ", k: 15, Output: ");
        listP05.setHead( rotate(listP05.getHead(), 15));
        System.out.println( makeItBold(listP05.getHead().toString()) + ", expected: 2->3->4->5->6->7->8->1->null");
        listP05.clear();
        listP05.createLinkedList(new int[]{1,2});
        System.out.print("Input: " + listP05.getHead().toString() + ", k: 3, Output: ");
        listP05.setHead( rotate(listP05.getHead(), 3));
        System.out.println( makeItBold(listP05.getHead().toString()) + ", expected: 2->1->null");
        listP05.clear();
        listP05.createLinkedList(new int[]{1});
        System.out.print("Input: " + listP05.getHead().toString() + ", k: 99, Output: ");
        listP05.setHead( rotate(listP05.getHead(), 99));
        System.out.println( makeItBold(listP05.getHead().toString()) + ", expected: 1->null");



    }


}

