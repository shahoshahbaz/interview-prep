package com.grokingcodeinterview.pattern21KWayMerge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;

/*
Problem Statement: Merge K Sorted Arrays (List of Arrays)
You are given a list of K sorted arrays, where each array is sorted in non-decreasing order.
Your task is to merge all arrays from the list into a single sorted list and return it.

Example 1:Input: [[1,4,7],[2,5,8],[3,6,9]] â†’ Output: [1,2,3,4,5,6,7,8,9]
Example 2:Input: [[1,3,3],[2,3,4],[0,6]] â†’ Output: [0,1,2,3,3,3,4,6]
Example 3: Input: [[],[1,2],[],[3]] â†’ Output: [1,2,3]
Example 4: Input: [[5,10,15]] â†’ Output: [5,10,15]
Example 5: Input: [[],[],[]] â†’ Output: []
Example 6: Input: [[1],[2,3,4,5],[6,7]] â†’ Output: [1,2,3,4,5,6,7]
Example 7: Input: [[-3,-1],[0,2,4],[1]] â†’ Output: [-3,-1,0,1,2,4]
Example 8: Input: [[1,1,1],[1,1],[1]] â†’ Output: [1,1,1,1,1,1]
Each array may have a different length
Arrays may contain duplicates
 */
public class P04MergeKSortedArrays {

    public static class Entry{
        int value;
        int elementIndex;
        int arrayIndex;

        public Entry(int value, int elementIndex, int arrayIndex){
            this.value = value;
            this.elementIndex = elementIndex;
            this.arrayIndex = arrayIndex;
        }
    }
    public static List<Integer> mergeListOfKSortedArray(List<int[]> list){

        PriorityQueue<Entry> minHeap = new PriorityQueue<>((a,b)->a.value - b.value);

        List<Integer> result =new ArrayList<>();
        for (int i =0; i< list.size(); i++){
            int [] nums = list.get(i);
            if(nums.length>0){
                minHeap.offer(new Entry(nums[0], 0, i));
            }
        }

        while(!minHeap.isEmpty()){

            Entry curr = minHeap.poll();
            result.add(curr.value); // adding the value to the output list
            int nextElementIndex = curr.elementIndex +1;
            int arrayIndex = curr.arrayIndex;

            if (nextElementIndex< list.get(arrayIndex).length){
                minHeap.offer( new Entry(list.get(arrayIndex)[nextElementIndex], nextElementIndex, arrayIndex));
            }
        }


        return result;

    }
    public static void main(String[] args) {
//        // some example to test
        System.out.println("===================================");
        System.out.println(" P04. Merge K Sorted Arrays");
        System.out.println("===================================");

        List<int[]> listP04= new ArrayList<>();
        listP04 = new ArrayList<>();
        listP04.add(new int[]{1,4,7});
        listP04.add(new int[]{2,5,8});
        listP04.add(new int[]{3,6,9});
        List<Integer> resultP04 = mergeListOfKSortedArray(listP04);
        System.out.println("Input: "+ listP04.stream().map(Arrays::toString).toList()+", Output: " + makeItBold(resultP04.toString()) +      ", Expected: " + "[1, 2, 3, 4, 5, 6, 7, 8, 9]");
        listP04 = new ArrayList<>();
        listP04.add(new int[]{1,3,5,7});
        listP04.add(new int[]{2,4,6,8});
        listP04.add(new int[]{0,9,10,11});
        resultP04 = mergeListOfKSortedArray(listP04);
        System.out.println("Input: "+ listP04.stream().map(Arrays::toString).toList()+", Output: " + makeItBold(resultP04.toString()) +      ", Expected: " + "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]");
        listP04 = new ArrayList<>();
        listP04.add(new int[]{5,10,15});
        listP04.add(new int[]{3,6,9,12,15});
        listP04.add(new int[]{8,16,24});
        resultP04 = mergeListOfKSortedArray(listP04);
        System.out.println("Input: "+ listP04.stream().map(Arrays::toString).toList()+", Output: " + makeItBold(resultP04.toString()) +      ", Expected: " + "[3, 5, 6, 8, 9, 10, 12, 15, 15, 16, 24]");
        listP04 = new ArrayList<>();
        listP04.add(new int[]{1,5,9});
        listP04.add(new int[]{2,6,10});
        listP04.add(new int[]{3,7,11});
        listP04.add(new int[]{4,8,12});
        resultP04 = mergeListOfKSortedArray(listP04);
        System.out.println("Input: "+ listP04.stream().map(Arrays::toString).toList()+", Output: " + makeItBold(resultP04.toString()) +      ", Expected: " + "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]");

    }
}

