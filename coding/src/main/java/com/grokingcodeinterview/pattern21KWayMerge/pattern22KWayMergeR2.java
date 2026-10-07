package com.grokingcodeinterview.pattern21KWayMerge;

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import java.util.List;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;
import static com.Utility.printArrayList;

public class pattern22KWayMergeR2 {

 /*
Problem Statement
Given â€˜Mâ€™ sorted list, find the Kâ€™th the smallest number among all the arrays.

Example 1:Input: L1=[2, 6, 8], L2=[3, 6, 7], L3=[1, 3, 4], K=5 Output: 4
Explanation: The 5th smallest number among all the arrays is 4, this can be verified from
the merged list of all the arrays: [1, 2, 3, 3, 4, 6, 6, 7, 8]
Example 2:

Input: L1=[5, 8, 9], L2=[1, 7], K=3 Output: 7
Explanation: The 3rd smallest number among all the arrays is 7.
 */

    /**
     * Input: L1=[2, 6, 8], L2=[3, 6, 7], L3=[1, 3, 4], K=5 out: 4
     * kth smalles
     * entry(value, elementIndex, listIndex)
     * maxHeap   insert  first elemtn of each arraythe heap until is >k then
     */
    public static class Entry{
        int value;
        int  elementIndex;
        int listIndex;

        public Entry(int value, int elementIndex, int listIndex){
            this.value = value;
            this.elementIndex = elementIndex;
            this.listIndex = listIndex;
        }
    }
    public static int findKthSmallest(List<List<Integer>> lists, int k){

        PriorityQueue<Entry> minHeap = new PriorityQueue<>((a, b)-> a.value - b.value);

        for (int i =0; i<lists.size(); i++){
            if (!lists.get(i).isEmpty()){
                Entry entry = new Entry (lists.get(i).get(0), 0, i);
                minHeap.offer(entry);
            }
        }

        int counter =0;
        int result = -1;

        while (!minHeap.isEmpty()){

            Entry entry = minHeap.poll();
            counter ++;
            result = entry.value;
            if (counter == k) return result;

            int nextElementIndex = entry.elementIndex +1;
            int currentListIndex = entry.listIndex;
            if (nextElementIndex < lists.get(currentListIndex).size()) {
                int value = lists.get(currentListIndex).get(nextElementIndex);
                minHeap.offer(new Entry(value,nextElementIndex, currentListIndex));
            }
        }

        return result;

    }

/*
Problem Statement
Given an array of â€˜Kâ€™ sorted LinkedLists, merge them into one sorted list.

Example 1:

Input: L1=[2, 6, 8], L2=[3, 6, 7], L3=[1, 3, 4]
Output: [1, 2, 3, 3, 4, 6, 6, 7, 8]
Example 2:

Input: L1=[5, 8, 9], L2=[1, 7]
Output: [1, 5, 7, 8, 9]
Constraints:

k == lists.length
0 <= k <= 104
0 <= lists[i].length <= 500
-104 <= lists[i][j] <= 104
lists[i] is sorted in ascending order.
The sum of lists[i].length will not exceed 104.

*/

    /**
     * Input: L1=[2, 6, 8], L2=[3, 6, 7], L3=[1, 3, 4]
     *  minHeap ={1->..,  2->..., 3->....}
     */
    public static Node merge(Node[] lists){

        PriorityQueue<Node> minHeap = new PriorityQueue<>((a,b)-> a.data -b.data);



        for (Node head: lists){
            if (head != null){
                minHeap.offer(head);

            }
        }
        Node dummy = new Node(0);
        Node current = dummy;
        while (!minHeap.isEmpty()){
            Node node = minHeap.poll(); //1C-> 3C> 4
            current.next = node;
            current = current.next;

            if (node.next != null){
                minHeap.offer(node.next);
            }

        }

    return dummy.next;

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


        Node[] lists = new Node[3];
        lists[0] = head1;
        lists[1] = head2;
        lists[2] = head3;

        System.out.println("Input K sorted lists: " + makeItBold("" +printArrayList(lists)));

        Node mergedHead = merge(lists);
        listP01.setHead(mergedHead);
        System.out.print("Merged list: ");
        listP01.printList();
        System.out.println("Expected output: " + makeItBold("[1 -> 2 -> 3 -> 3 -> 4 -> 6 -> 6 -> 7 -> 8 -> Null] \n"));

        // give me 3 more test cases

        listP01 = new LinkedList();
        head1 = listP01.createLinkedList(new int[]{5,8,9});
        listP01 = new LinkedList();
        head2 = listP01.createLinkedList(new int[]{1,7});
        lists = new Node[2];
        lists[0] = head1;
        lists[1] = head2;
        System.out.println("Input K sorted lists: " + makeItBold("" +printArrayList(lists)));
        mergedHead = merge(lists);
        listP01.setHead(mergedHead);
        System.out.print("Merged list: ");
        listP01.printList();
        System.out.println("Expected output: " + makeItBold("[1 -> 5 -> 7 -> 8 -> 9 -> Null] \n"));

        listP01 = new LinkedList();
        head1 = listP01.createLinkedList(new int[]{});
        listP01 = new LinkedList();
        head2 = listP01.createLinkedList(new int[]{});
        lists = new Node[2];
        lists[0] = head1;
        lists[1] = head2;
        System.out.println("Input K sorted lists: " + makeItBold("" +printArrayList(lists)));
        mergedHead = merge(lists);
        listP01.setHead(mergedHead);
        System.out.print("Merged list: ");
        listP01.printList();
        System.out.println("Expected output: " + makeItBold("[Null] \n"));

        listP01 = new LinkedList();
        head1 = listP01.createLinkedList(new int[]{0,2,4});
        listP01 = new LinkedList();
        head2 = listP01.createLinkedList(new int[]{1,3,5});
        lists = new Node[2];
        lists[0] = head1;
        lists[1] = head2;
        System.out.println("Input K sorted lists: " + makeItBold("" +printArrayList(lists)));
        mergedHead = merge(lists);
        listP01.setHead(mergedHead);
        System.out.print("Merged list: ");
        listP01.printList();
        System.out.println("Expected output: " + makeItBold("[0 -> 1 -> 2 -> 3 -> 4 -> 5 -> Null] \n"));


        System.out.println("===============================");
        System.out.println("P-2. Kth Smallest Number in M Sorted Lists");
        System.out.println("===============================");

        // add 4 test cases
        List<List<Integer>> listsP02 = List.of(
                List.of(2, 6, 8),
                List.of(3, 6, 7),
                List.of(1, 3, 4)
        );
        int kP02 = 5;
        int resultP02 = findKthSmallest(listsP02, kP02);
        System.out.println("Input:" + makeItBold(listsP02.toString()) + ", K: " + makeItBold(""+kP02) +
                ", Output: " + makeItBold(""+resultP02) + ", Expected: " + makeItBold("4"));
        listsP02 = List.of(
                List.of(5, 8, 9),
                List.of(1, 7)
        );
        kP02 = 3;
        resultP02 = findKthSmallest(listsP02, kP02);
        System.out.println("Input:" + makeItBold(listsP02.toString()) + ", K: " + makeItBold(""+kP02) +
                ", Output: " + makeItBold(""+resultP02) + ", Expected: " + makeItBold("7"));


        listsP02 = List.of(
                List.of(1, 4, 7),
                List.of(2, 5, 8),
                List.of(3, 6, 9)
        );
        kP02 = 4;
        resultP02 = findKthSmallest(listsP02, kP02);
        System.out.println("Input:" + makeItBold(listsP02.toString()) + ", K: " + makeItBold(""+kP02) +
                ", Output: " + makeItBold(""+resultP02) + ", Expected: " + makeItBold("4"));
        listsP02 = List.of(
                List.of(10, 20, 30),
                List.of(5, 15, 25),
                List.of(1, 2, 3)
        );
        kP02 = 6;
        resultP02 = findKthSmallest(listsP02, kP02);
        System.out.println("Input:" + makeItBold(listsP02.toString()) + ", K: " + makeItBold(""+kP02) +
                ", Output: " + makeItBold(""+resultP02) + ", Expected: " + makeItBold("15"));





    }
}

