package com.grokingcodeinterview.pattern20TopKElements;

import java.util.*;

import static com.Utility.makeItBold;

/*
Problem Statement
Given an array of numbers nums and an integer K,
 find the maximum number of distinct elements  after removing exactly K elements from the nums array.

Example 1: Input: nums = [7, 3, 5, 8, 5, 3, 3], K=2 Expected Output: 3
Explanation: We can remove two occurrences of 3 to be left with 3 distinct numbers [7, 3, 8],
 we have to skip 5 because it is not distinct and occurred twice.
 Another solution could be to remove one instance of '5' and '3' each to be left with three distinct numbers [7, 5, 8], in this case,
 we have to skip 3 because it occurred twice.

Example 2: Input: [3, 5, 12, 11, 12], and K=3 Expected Output: 2
Explanation: We can remove one occurrence of 12, after which all numbers will become distinct. Then we can delete any two numbers which will leave us 2 distinct numbers in the result.

Example 3:Input: [1, 2, 3, 3, 3, 3, 4, 4, 5, 5, 5], and K=2 Expected Output: 3
Explanation: We can remove one occurrence of '4' to get three distinct numbers 1, 2 and 4.
Constraints:

1 <= arr.length <= 105
1 <= arr[i] <= 109
0 <= k <= arr.length
 */

/**
 * Input: nums = [7, 3, 5, 8, 5, 3, 3], K=2 Expected Output: 3
 * minHeap =[,5, 7]
 */
public class P09MaximumDistinctElements {

    public static  int findMaximumDistinctElements(int[] nums, int k) {

        int distinctElementsCount = 0;

        // find the frequency of each number
        Map<Integer, Integer> numFrequencyMap = new HashMap<>();
        for (int i : nums)
            numFrequencyMap.put(i, numFrequencyMap.getOrDefault(i, 0) + 1);

        PriorityQueue<Map.Entry<Integer, Integer>> minHeap =
                new PriorityQueue<Map.Entry<Integer, Integer>>(
                        (e1, e2) -> e1.getValue() - e2.getValue());

        // insert all numbers with frequency greater than '1' into the min-heap
        for (Map.Entry<Integer, Integer> entry : numFrequencyMap.entrySet()) {
            if (entry.getValue() == 1)
                distinctElementsCount++;
            else
                minHeap.add(entry);
        }

        // following a greedy approach, try removing the least frequent numbers first from
        // the min-heap
        while (k > 0 && !minHeap.isEmpty()) {
            Map.Entry<Integer, Integer> entry = minHeap.poll();
            // to make an element distinct, we need to remove all of its occurrences except one
            k -= entry.getValue() - 1;
            if (k >= 0)
                distinctElementsCount++;
        }

        // if k > 0, this means we have to remove some distinct numbers
        if (k > 0)
            distinctElementsCount -= k;

        return distinctElementsCount;
//

    }

    public static void main(String[] args) {
        System.out.println("==========================");
        System.out.println("P09. Maximum Distinct Elements after removing K elements");
        System.out.println("==========================");


        int[] numsP09 = new int[] { 7, 3, 5, 8, 5, 3, 3 };
        int kP09 = 2;

        int resultP09 = findMaximumDistinctElements(numsP09, kP09);

        System.out.println("Input: " +Arrays.toString(numsP09) + ", K= " +kP09 +", output: " + makeItBold(resultP09 +"") + " Expected: 3");
        numsP09 = new int[] { 3, 5, 12, 11, 12 };
        kP09 = 3;
        resultP09  = findMaximumDistinctElements(numsP09, 3);
        System.out.println("Input: " +Arrays.toString(numsP09) + ", K= " +kP09 +", output: " + makeItBold(resultP09 +"") + " Expected: 2");
        numsP09 = new int[] { 1, 2, 3, 3, 3, 3, 4, 4, 5, 5, 5 };
        kP09 = 2;
        resultP09  = findMaximumDistinctElements(numsP09, kP09);
        System.out.println("Input: " +Arrays.toString(numsP09) + ", K= " +kP09 +", output: " + makeItBold(resultP09 +"") + " Expected: 3");
        //Additional test cases
        numsP09 = new int[] { 1, 1, 1, 1, 1 };
        resultP09 = findMaximumDistinctElements(numsP09, 3);
        System.out.println("Input: " +Arrays.toString(numsP09) + ", K= 3 => " + ", output: " + makeItBold(resultP09 +"") + " Expected: 0");


        numsP09 = new int[] { 1 , 2, 2, 3, 3, 4, 5 };
        kP09 = 3;
        resultP09 = findMaximumDistinctElements(numsP09, kP09);
        System.out.println("Input: " +Arrays.toString(numsP09) + ", K= " +kP09 +", output: " + makeItBold(resultP09 +"") + " Expected: 4");

    }
}
