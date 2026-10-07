package com.grokingcodeinterview.pattern21KWayMerge;

import com.ds.singleLinkedList.Node;

import java.util.List;
import java.util.PriorityQueue;

public class pattern22KWayMergeR3 {

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

    public static Node mergeList(Node[] list){

        PriorityQueue<Node> minHeap = new PriorityQueue<>((a, b) -> a.data - b.data);

        for (Node head: list){
            if(head != null){
                minHeap.offer(head);
            }
        }

        Node dummy = new Node(0);
        Node current = dummy;

        while(!minHeap.isEmpty()){
            Node node = minHeap.poll();
            current.next = node;
            current= current.next;

            if(node.next != null){
                minHeap.offer(node.next);
            }
        }

        return dummy.next;


    }

    public static class Entry{
        int value;
        int listIndex;
        int elementIndex;

        public Entry(int value, int listIndex, int elementIndex) {
            this.value = value;
            this.listIndex = listIndex;
            this.elementIndex = elementIndex;
        }

    }

    public static int findKthSmallest(List<List<Integer>> list, int k){

        PriorityQueue<Entry> minHeap = new PriorityQueue<>((a, b) -> a.value - b.value);

        for(int i =0; i<list.size(); i++){
            if(!list.get(i).isEmpty()){
                Entry entry = new Entry(list.get(i).get(0),i, 0);
                minHeap.offer(entry);
            }
        }
        int counter =0;
        while(!minHeap.isEmpty()){
            Entry current = minHeap.poll();
            counter ++;

            if(counter ==k){

                return current.value;
            }

            int listIndex = current.listIndex;
            int elementIndex = current.elementIndex;
            if(elementIndex+1< list.get(listIndex).size()){
                int nextValue = list.get(listIndex).get(elementIndex+1);
                minHeap.offer(new Entry(nextValue, listIndex, elementIndex +1));
            }


        }
        return -1;
    }

}

