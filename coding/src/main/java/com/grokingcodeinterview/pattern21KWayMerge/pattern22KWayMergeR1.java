package com.grokingcodeinterview.pattern21KWayMerge;

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;
import static com.Utility.printArrayList;

public class pattern22KWayMergeR1 {
/*
problem statement
You are given M sorted arrays of integers.
 Each array may have a different length, and the total number of elements across all arrays may be either even or odd.
Your task is to find the median value of all the numbers present in these M arrays when they are conceptually combined into a single sorted sequence.

Definition of Median

If the total number of elements is odd, the median is the middle element in the sorted order.
If the total number of elements is even, the median is the average of the two middle elements.

Your task is to find the median value of all the numbers present in these M arrays

when they are conceptually combined into a single sorted sequence.
Input: [[1, 3], [2]] Output: 2
Input: [[1, 2], [3, 4]]  Output: 2.5
Input: [[1, 5, 9], [2, 3], [6, 7, 8, 10]] Output: 6
Input:[[1, 2, 2], [2, 2, 3]] Output: 2
Input:[[-5, -3, -1], [2, 4, 6]] Output: 0.5
Input: [[7, 9, 11]] Output: 9
Input: [[], [1, 2, 3]] Output: 2

 */

    /**
     * Input: [[1, 3], [2]] Output: 2
     * minHeap = [1, 2,3] n = 3 is odd the median = array[n/2] =array[1] = 2
     * Input: [[1, 2], [3, 4]]  Output: 2.5 [1,2,3,4] n = 4 event median = (array[4/2] +num[4-1/2])/2 median = (3 + 2)
     *
     */

    public static double findMedianOfMSortedArray(int[][] array){

        PriorityQueue<Entry> minHeap = new PriorityQueue<>((a,b)->a.value - b.value);
        int totalLength =0;
        for (int i =0; i< array.length;i++){

            minHeap.offer(new Entry(array[i][0], 0, i));
            totalLength += array[i].length;
        }


        // if the totalLength is even we need array[n-1/2] and array[n/2]

        int firstMedianIndex=  (totalLength -1)/2;
        int secondMedianIndex = totalLength/2;
        int firstMedian =0;
        int secondMedian = 0;

        int counter =0;

        while (!minHeap.isEmpty()){
            Entry entry = minHeap.poll();
            if (counter == firstMedianIndex){
                firstMedian = entry.value;
            }
            if (counter == secondMedianIndex){
                secondMedian = entry.value;
                break;
            }
            counter ++;
            int nextElementIndex = entry.elementIndex +1;

            if (nextElementIndex< array[entry.listIndex].length){
                minHeap.offer(new Entry(array[entry.listIndex][nextElementIndex] , nextElementIndex, entry.listIndex ));
            }

        }

        return (totalLength %2 == 0)? (firstMedian + secondMedian)/2.0: secondMedian;



}
    public static double findMedianOfMSortedArray_BinarySearch(int[][] array){

        // find min and max
        int totalCount =0;
        int minValue= Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;

        for (int[] nums: array){
            if (nums.length>0 ){
                minValue =Math.min(minValue, nums[0]);
                maxValue = Math.max(maxValue, nums[1]);
                totalCount += nums.length;

            }
        }

        if (totalCount %2 == 1){
            return kthSmallestElement(array, (totalCount+1)/2, minValue, maxValue);
        }


        return 0;
    }

    public static int kthSmallestElement(int[][] array, int k, int low, int high ){
        while(low<high){
            int mid = low + (high -low)/2;
            int count =0;
            for (int[] nums:array){
                count += upperBound(nums, mid);
            }
        }
        return 0;

    }
    public static int upperBound(int[] nums, int target){
        int start = 0;
        int end = nums.length -1;

        while(start< end){
            int mid = start + (end - start)/2;
            if (nums[mid]< target)
                start = mid+1;
            else
                end = mid -1;
        }
        return start;
    }
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
     * minHeap
     * L1=[2, 6, 8], L2=[3, 6, 7], L3=[1, 3, 4],
     * entry: value, elementInde, listIndex
     */

    public static class Entry{
        int value;
        int elementIndex;
        int listIndex;

        public Entry(int value, int elementIndex, int listIndex){
            this.value = value;
            this.elementIndex = elementIndex;
            this.listIndex = listIndex;
        }
    }

    public  static int findKthSmallest(List<List<Integer>> lists, int k){


        PriorityQueue<Entry> minHeap = new PriorityQueue<>((a, b) -> a.value - b.value);

        // add the first elemnts of each node
        for (int i = 0; i< lists.size(); i++){
            if (!lists.get(i).isEmpty()) {
                minHeap.offer(new Entry(lists.get(i).get(0), 0, i));
            }


        }
        int count = 0;
        int result = -1;

        while (!minHeap.isEmpty() ){
             Entry entry = minHeap.poll();
             result = entry.value;
             count++;
             if (count == k ) return result;
             // insert the next elemnt of the same list
            int nextElementIndex = entry.elementIndex +1;
            int listIndex = entry.listIndex;

            if (nextElementIndex < lists.get(listIndex).size()){
                int nextValue = lists.get(listIndex).get(nextElementIndex);
                minHeap.offer(new Entry(nextValue, nextElementIndex, listIndex));
            }

        }
        return -1;

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
     * [5, 8, 9] 5-> 8-> 9 ->  null
     */
    public static Node merge(Node[] lists){
        int k = lists.length;
        PriorityQueue<Node> minHeap = new PriorityQueue<>((a, b) -> a.data -b.data);



        for (Node head: lists){
            if (head != null){
                minHeap.offer(head);
            }

        }

        Node dummy = new Node(0);
        Node current = dummy;

        while (!minHeap.isEmpty()){
            Node node = minHeap.poll();
            current.next = node;
            current = current.next;
            current.next = null;
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
        System.out.println(head1.data);

        Node head2 = listP01.createLinkedList(new int[]{3,6,7});
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
        System.out.println("==============================================");
        System.out.println("P03. Median of M Sorted Arrays");
        System.out.println("==============================================");
        int[][] numsP03 = new int[][]{{1, 3}, {2}};
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + "outPut: " + makeItBold(findMedianOfMSortedArray(numsP03) +"") +", Expected: 2.0");
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + "outPut: " + makeItBold(findMedianOfMSortedArray_BinarySearch(numsP03) +"") +", Expected: 2.0 <- using `Binary search on value");
        numsP03 = new int[][]{ {1, 2},{3, 4}};
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + "outPut: " + makeItBold(findMedianOfMSortedArray(numsP03) +"") +", Expected: 2.5");
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + "outPut: " + makeItBold(findMedianOfMSortedArray_BinarySearch(numsP03) +"") +", Expected: 2.5 <- using `Binary search on value");
        numsP03 = new int[][]{{1, 5, 9},{2, 3}, {6, 7, 8, 10} };
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + "outPut: " + makeItBold(findMedianOfMSortedArray(numsP03) +"") +", Expected: 6.0");
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + "outPut: " + makeItBold(findMedianOfMSortedArray_BinarySearch(numsP03) +"") +", Expected: 6.0 <- using `Binary search on value");
        numsP03 = new int[][]{{1, 2, 2},{2, 2, 3}};
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + "outPut: " + makeItBold(findMedianOfMSortedArray(numsP03) +"") +", Expected: 2.0");
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + "outPut: " + makeItBold(findMedianOfMSortedArray_BinarySearch(numsP03) +"") +", Expected: 2.0 <- using `Binary search on value");
        numsP03 = new int[][]{{-5, -3, -1},{2, 4, 6}};
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + "outPut: " + makeItBold(findMedianOfMSortedArray(numsP03) +"") +", Expected: 0.5");
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + "outPut: " + makeItBold(findMedianOfMSortedArray_BinarySearch(numsP03) +"") +", Expected: 0.5 <- using `Binary search on value");



    }
}

