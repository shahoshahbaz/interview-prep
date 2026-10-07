package com.grokingcodeinterview.pattern21KWayMerge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
/*
Problem Statement:
You have k lists of sorted integers. Find the smallest range that includes at least one number from each of the k lists.
We define the range [a, b] to be smaller than range [c, d] if b - a < d - c, or if b - a == d - c and a < c.

Example 1: Input: lists = [
                    [4, 10, 15, 24, 26],
                    [0, 9, 12, 20],
                    [5, 18, 22, 30]]
Output: [20, 24]
Explanation: List 1: 24 is in [20,24]. List 2: 20 is in [20,24]. List 3: 22 is in [20,24].

Example 2: Input: lists = [[1,2,3],[1,2,3],[1,2,3]]
Output: [1,1]
 */
public class P06SmallestRange {
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

    public static int[] smallestRange(List<List<Integer>> lists){

        if(lists == null) return new int[2];

        PriorityQueue<Entry> minHeap = new PriorityQueue<>(
                (a, b) -> a.value - b.value
        );
        int currentMax = Integer.MIN_VALUE;

        for(int i =0; i< lists.size(); i++){
            List<Integer> currList = lists.get(i);
            if(currList != null && !currList.isEmpty()){
                int value = currList.get(0);
                currentMax = Math.max(currentMax, value);
                minHeap.offer(new Entry(value, 0, i));

            }
        }
        int[] bestRange = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        int bestWidth = Integer.MAX_VALUE;


        while(!minHeap.isEmpty()){
            Entry entry = minHeap.poll();
            int currElementIndex = entry.elementIndex;
            int currListIndex = entry.listIndex;
            int minHeapValue = entry.value;

            int width = currentMax - minHeapValue;
            if(width< bestWidth){
                bestWidth = width;
                bestRange[0] = minHeapValue;
                bestRange[1] = currentMax;
            }


            if(currElementIndex+1 >= lists.get(currListIndex).size()){
                break;
            }

            int nextValue = lists.get(currListIndex).get(currElementIndex + 1);
            currentMax = Math.max(nextValue, currentMax);
            minHeap.offer(new Entry(nextValue,currElementIndex+1, currListIndex  ));
        }

        return bestRange;
    }
    public static void main(String[] args) {

        // Example 1
        List<List<Integer>> lists1 = new ArrayList<>();
        lists1.add(Arrays.asList(4, 10, 15, 24, 26));
        lists1.add(Arrays.asList(0, 9, 12, 20));
        lists1.add(Arrays.asList(5, 18, 22, 30));

        int[] result1 = smallestRange(lists1);
        System.out.println("Example 1 -> [" + result1[0] + ", " + result1[1] + "]");
        System.out.println("Expected  -> [20, 24]");

        // Example 2
        List<List<Integer>> lists2 = new ArrayList<>();
        lists2.add(Arrays.asList(1, 2, 3));
        lists2.add(Arrays.asList(1, 2, 3));
        lists2.add(Arrays.asList(1, 2, 3));

        int[] result2 = smallestRange(lists2);
        System.out.println("Example 2 -> [" + result2[0] + ", " + result2[1] + "]");
        System.out.println("Expected  -> [1, 1]");
    }
}

