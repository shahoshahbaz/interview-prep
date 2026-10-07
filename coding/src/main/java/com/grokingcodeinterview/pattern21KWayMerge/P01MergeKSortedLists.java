package com.grokingcodeinterview.pattern21KWayMerge;

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import java.util.Arrays;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;

public class P01MergeKSortedLists {

/*
Problem Statement:
Given an array of â€˜Kâ€™ sorted LinkedLists, merge them into one sorted list.

Example 1: Inputs: L1=[2, 6, 8], L2=[3, 6, 7], L3=[1, 3, 4] Output: [1, 2, 3, 3, 4, 6, 6, 7, 8]
Example 2: Input: L1=[5, 8, 9], L2=[1, 7] Output: [1, 5, 7, 8, 9]
Constraints:

k == lists.length
0 <= k <= 10^4
0 <= lists[i].length <= 500
-10^4 <= lists[i][j] <= 10^4
lists[i] is sorted in ascending order.
The sum of lists[i].length will not exceed 104.
*/

    public static Node merge(Node[] lists) {

        // Min-heap comparing nodes by their data value
        PriorityQueue<Node> minHeap = new PriorityQueue<>((a, b)->a.data - b.data);

        // Add the head of each non-null list to the heap
        for (Node head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        // Dummy node to simplify building the linked list
        Node dummy = new Node(0);
        Node current = dummy; // Pointer to build the merged list

        // Extract the smallest node and push its next node into the heap
        while (!minHeap.isEmpty()) {
            Node node = minHeap.poll();
            current.next = node;        // attach smallest node
            current = current.next;     // move forward the current pointer, this ensures the next pointer is ready for the next iteration



            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }

        return dummy.next; // return head of merged list
    }


    public static void main(String[] args) {

        System.out.println("========================");
        System.out.println("P01. Merge K Sorted Lists");
        System.out.println("==========================");

        LinkedList listP01 = new LinkedList();
        Node head1 = listP01.createLinkedList(new int[]{2,6,8,});
        listP01 = new LinkedList();
        Node head2 = listP01.createLinkedList(new int[]{3,6,7});
        listP01 = new LinkedList();
        Node head3 = listP01.createLinkedList(new int[]{1,3,4});


        Node[] listsP01 = new Node[3];
        listsP01[0] = head1;
        listsP01[1] = head2;
        listsP01[2] = head3;


        System.out.println("Inputs:" + Arrays.toString(listsP01));

        Node mergedHead = merge(listsP01);
        LinkedList resultP01 = new LinkedList();
        resultP01.setHead(mergedHead);
        System.out.println(" ,output: "+ makeItBold(resultP01.toString())+" ,Expected: 1->2->3->3->4->6->6->7->8->Null");

        // give me 3 more test cases

        listP01 = new LinkedList();
        head1 = listP01.createLinkedList(new int[]{5,8,9});
        listP01 = new LinkedList();
        head2 = listP01.createLinkedList(new int[]{1,7});
        listsP01 = new Node[2];
        listsP01[0] = head1;
        listsP01[1] = head2;
        mergedHead = merge(listsP01);
        resultP01.clear();
        resultP01.setHead(mergedHead);

        System.out.println("Inputs:" + Arrays.toString(listsP01) + " ,output: "+ makeItBold(resultP01.toString())+" ,Expected: 1->5->7->8->9->Null");

        listP01 = new LinkedList();
        head1 = listP01.createLinkedList(new int[]{});
        listP01 = new LinkedList();
        head2 = listP01.createLinkedList(new int[]{});
        listsP01 = new Node[2];
        listsP01[0] = head1;
        listsP01[1] = head2;
        mergedHead = merge(listsP01);

        resultP01.clear();
        resultP01.setHead(mergedHead);
        System.out.println("Inputs:" + Arrays.toString(listsP01) + " ,output: "+ makeItBold(resultP01.toString())+" ,Expected: Null");


        listP01 = new LinkedList();
        head1 = listP01.createLinkedList(new int[]{0,2,4});
        listP01 = new LinkedList();
        head2 = listP01.createLinkedList(new int[]{1,3,5});
        listsP01 = new Node[2];
        listsP01[0] = head1;
        listsP01[1] = head2;
        mergedHead = merge(listsP01);
        resultP01.clear();
        resultP01.setHead(mergedHead);
        System.out.println("Inputs:" + Arrays.toString(listsP01) + " ,output: "+ makeItBold(resultP01.toString())+" ,Expected: 0->1->2->3->4->5->Null");


    }
}

