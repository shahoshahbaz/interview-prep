package com.grokingcodeinterview.pattern20TopKElements;

import java.util.*;

import static com.Utility.makeItBold;

/*
 Problem Statement
 Given an unsorted array of numbers, find the top â€˜Kâ€™ frequently occurring numbers in it.
 Example 1:  Input: [1, 3, 5, 12, 11, 12, 11], K = 2  Output: [12, 11]
 Explanation: Both '11' and '12' appeared twice.

 Example 2:  Input: [5, 12, 11, 3, 11], K = 2  Output: [11, 5] or [11, 12] or [11, 3]
 Explanation: Only '11' appeared twice; all other numbers appeared once.

 Constraints:
 1 <= nums.length <= 105
 -105 <= nums[i] <= 105
 k is in the range [1, the number of unique elements in the array].
 It is guaranteed that the answer is unique.
 */

/*
 Top K Frequent Elements (Min heap and Bucket Sort variant)

Problem Statement

Given an unsorted array of numbers, find the top K frequently occurring numbers in it. Solve it in O(n) time using bucket sort instead of a heap.

Example 1: Input: nums = [1, 3, 5, 12, 11, 12, 11], K = 2 Output: [12, 11]
Explanation: Both '11' and '12' appeared twice â€” the two highest frequencies in the array.

Example 2: Input: nums = [5, 12, 11, 3, 11], K = 2 Output: [11, 5] or [11, 12] or [11, 3]
Explanation: Only '11' appeared twice; all other numbers appeared once, so any one of them can fill the second spot.

Constraints
1 <= nums.length <= 10^5
-10^5 <= nums[i] <= 10^5
k is in the range [1, the number of unique elements in the array]
It is guaranteed that the answer is unique
 */

/**
 * NOTE: this question can be solved using bucket sort as well, but here we are using minHeap to solve it.
 * so there are two approaches to solve this problem:
 * 1. Using MinHeap (PriorityQueue) :O(nlogk) time complexity and O(n) space complexity
 * 2. Using Bucket Sort: O(n) time complexity and O(n) space complexity
 *
 * why we can use bucket sort? becuase the frequency of any number can be at most n,
 * so we can create an array of size n+1, where the index represents the frequency and the value at that index is list of the numbers that have that frequncy
 * for example, if we have an array [1, 1, 2, 2, 3], so the list will be like this:
 * index 0: []
 * index 1: [3]
 * index 2: [1, 2]
 * index 3: []
 * and  here 3 has frequency 1(index1(number of freq): [3(the number with freq 1)]),
 * 1 and 2 has frequency 2(index2(number of freq): [1, 2(the numbers with freq 2)]) and index3 is empty because no number has frequency 3.
 */
public class P05TopKFrequentNumbers {

    public static class Record{
        int num;
        int freq;

        public Record(int num, int freq){
            this.num = num;
            this.freq = freq;
        }
    }

    public static List<Integer> findTopKFrequentNumbers(int[] nums, int k){
        List<Integer > list = new ArrayList<>();
        // edge cases
        if(nums == null ) return list;

        Map<Integer, Integer> freqMap = new HashMap<>();

        for(int num:nums) freqMap.put(num, freqMap.getOrDefault(num, 0) +1);



        PriorityQueue<Record> minHeap = new PriorityQueue<>((r1, r2) -> r1.freq - r2.freq);



        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            minHeap.offer(new Record(entry.getKey(), entry.getValue()));
            if (minHeap.size() > k) {
                minHeap.poll(); // evict the current weakest
            }

        }

        while(!minHeap.isEmpty()){
            list.add(minHeap.poll().num);
        }

        return list;
    }

    public List<Integer> findTopKFrequentNumbersUsingBucketSort(int[] nums, int k){
        List<Integer> result = new ArrayList<>();

        HashMap<Integer, Integer> freqMap = new HashMap<>();

        for(int num: nums){
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        ArrayList<Integer>[] bucket = new ArrayList[nums.length];
        for(Map.Entry<Integer, Integer> entry: freqMap.entrySet()){
            int freq = entry.getValue();
            int key = entry.getValue();

            if(bucket[freq] == null) bucket[freq] = new ArrayList<>();

            bucket[freq].add(key);


        }

        for(int freq = bucket.length; freq>=1 && result.size()< k; freq++){
            if (bucket[freq] != null){
                result.addAll(bucket[freq]);
            }




        }

        return result;

    }

    public static void main(String[] args) {

         int[] numsP05 = {1, 3, 5, 12, 11, 12, 11};
         int kP05 =2;


        List<Integer> resultP05 = findTopKFrequentNumbers(numsP05, kP05);
        System.out.println("Input: " +makeItBold(Arrays.toString(numsP05)) + ", K: " + makeItBold(""+kP05) +
                ", Output: " + makeItBold(resultP05.toString()) + ", Expected: " + makeItBold("[12, 11]"));

        numsP05 = new int[]{5, 12, 11, 3, 11};
        kP05 =2;
        resultP05 = findTopKFrequentNumbers(numsP05, kP05);
        System.out.println("Input: " +makeItBold(Arrays.toString(numsP05)) + ", K: " + makeItBold(""+kP05) +
                ", Output: " + makeItBold(resultP05.toString()) + ", Expected: " + makeItBold("[11, 5] or [11, 12] or [11, 3]"));

        numsP05 = new int[]{5, 12, 11, 3, 11};
        kP05 =2;
        resultP05 = findTopKFrequentNumbers(numsP05, kP05);
        System.out.println("Input: " +makeItBold(Arrays.toString(numsP05)) + ", K: " + makeItBold(""+kP05) +
                ", Output: " + makeItBold(resultP05.toString()) + ", Expected: " + makeItBold("[11, 5] or [11, 12] or [11, 3]"));

        numsP05 = new int[]{4, 4, 4, 4, 4};
        kP05 =1;
        resultP05 = findTopKFrequentNumbers(numsP05, kP05);
        System.out.println("Input: " +makeItBold(Arrays.toString(numsP05)) + ", K: " + makeItBold(""+kP05) +
                ", Output: " + makeItBold(resultP05.toString()) + ", Expected: " + makeItBold("[4]"));





    }
}

