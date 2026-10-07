package com.grokingcodeinterview.pattern21KWayMerge;

import java.util.List;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;

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
public class P02KthSmallestNumberInMSortedLists {

    // Entry class to store value along with its originating list and index
    public static class Entry{
        int value;
        int listIndex; // which list
        int elementIndex; // index in that list

        public Entry(int value, int listIndex, int elementIndex){
            this.value = value;
            this.listIndex = listIndex;
            this.elementIndex = elementIndex;
        }



    }

    public  static int findKthSmallest(List<List<Integer>> lists, int k) {

        PriorityQueue<Entry> minHeap = new PriorityQueue<>((a, b)->a.value - b.value);

        // step1: put the 1st element of each list in the minHeap
        for (int i =0; i< lists.size();i++){
            if (!lists.get(i).isEmpty() ){
                minHeap.offer(new Entry(lists.get(i).get(0), i, 0));
            }
        }

        int count =0;
        int result = -1;
        // Step 2: until we find kth smallest, pop from minHeap and add the next element of that list
        while(!minHeap.isEmpty()){

            Entry entry = minHeap.poll();
            result = entry.value;
            count ++;
            if (count == k) return result;

            int listIndex = entry.listIndex; // get the list index
            int elementIndex = entry.elementIndex; // get the element index

            if (elementIndex + 1< lists.get(listIndex).size()){ // if there is a next element in the same list

                int nextValue = lists.get(listIndex).get(elementIndex+1); // get value of next element
                minHeap.offer(new Entry(nextValue, listIndex, elementIndex +1)); // add next element as Entry to minHeap
            }
        }
        // if we reach here, that means we couldn't find kth smallest

        return -1; // if k is more than total elements

    }

    public static void main(String[] args) {
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

