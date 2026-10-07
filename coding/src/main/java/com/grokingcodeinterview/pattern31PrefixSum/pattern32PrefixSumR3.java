package com.grokingcodeinterview.pattern31PrefixSum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.Utility.makeItBold;

public class pattern32PrefixSumR3 {

    /*
    Problem Statement

    Given an integer array nums and an integer k, return the number of continuous subarrays whose sum equals k.

    Example: Input: nums = [1, 2, 3], k = 3  Output: 2
    Explanation: The subarrays that sum to 3 are: [1, 2], [3]
    Example: Input: nums = [1, 1, 1], k = 2  Output: 2
    Explanation: The subarrays that sum to 2 are: [1, 1], [1, 1]
    Example: Input: nums = [1, 2, 3], k = 4  Output: 1
    Explanation: The subarray that sums to 4 is: [1, 3]
    Example: Input: nums = [1, 2, 3], k = 5  Output: 0
    Explanation: There are no subarrays that sum to 5.
    Example: Input: nums = [1, 2, 3], k = 6  Output: 1
    Explanation: The subarray that sums to 6 is: [1, 2, 3]
   I need example that has negative numbers and zeroes as well.
     Example: Input: nums = [1, 2, -1, 3, 0], k = 3  Output: 4
    Explanation: The subarrays that sum to 3 are: [1, 2], [2, -1, 3], [-1, 3, 0], [3]

 */

    /**
     *  [1, 2, 3], k = 4  Output: 0, Expected: 1
     *  currentSum = 1; prevsum = 1 - 4 = -3 map[(0, 1), (1, 1) ]
     *  currentSum = 3 pervSum = -1, map [(0,1), (1, 1), (3,1)]
     *  currentSum = 4 prevSum = 0 counter =1
     *
     */

    public static int subarraySum(int[] nums, int target){
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0, 1);
        int currentSum = 0;
        int counter =0;
        for (int num: nums){

            currentSum += num;
            int pervSum = currentSum - target;
            counter += prefixSumCount.getOrDefault(pervSum,0);
             prefixSumCount.put(currentSum, prefixSumCount.getOrDefault(currentSum, 0) +1);


        }
        return counter;
    }
    /*
    Given an array nums and a range query (i, j), find the sum of elements between indices i and j.
    Example
    Input: arr = [1, 2, 3, 4], i = 1, j = 3
    Output: 9
 */
    public static int[] computePrefixSum(int[] nums){
        int n = nums.length;
        int[] prefixSum = new int[n];
        prefixSum[0] = nums[0];

        for(int i =1; i<n; i++){
            prefixSum[i] = prefixSum[i-1]+ nums[i];
        }
        return prefixSum;
    }

    public static int computeSumQuery(int[] prefixSum, int i, int j){
        return i== 0? prefixSum[i]: prefixSum[j]- prefixSum[j-1];
    }

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("P01. Range Sum Query :");
        System.out.println("=================================================================");

        int[] numsP01 = {1, 2, 3, 4};
        int iP01 = 1;
        int jP01 = 3;
        int[] prefixP01 = computePrefixSum(numsP01);
        System.out.println("Input: " + Arrays.toString(numsP01) + ", i = " + iP01 + ", j = " + jP01 + ", Output: " + makeItBold(computeSumQuery(prefixP01, iP01, jP01) +" ") +"Expected: 9");

        iP01 = 0;
        jP01 = 2;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", i = " + iP01 + ", j = " + jP01 + ", Output: " + makeItBold(computeSumQuery(prefixP01, iP01, jP01) +" ") +"Expected: 6");

        iP01 = 0;
        jP01 = 3;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", i = " + iP01 + ", j = " + jP01 + ", Output: " + makeItBold(computeSumQuery(prefixP01, iP01, jP01) +" ") +"Expected: 10");

        System.out.println("=================================================================");
        System.out.println("P02.Subarray Sum Equals K :");
        System.out.println("=================================================================");
        int[] numsP00 = {1, 2, 3};
        int kP00 = 3;
        System.out.println("Input: nums = [1, 2, 3], k = 3  Output: " + makeItBold(subarraySum(numsP00, kP00) +", Expected: 2 ")); // 2

        numsP00 = new int[]{1, 1, 1};
        kP00 = 2;
        System.out.println("Input: nums = [1, 1, 1], k = 2  Output: " + makeItBold(subarraySum(numsP00, kP00) +", Expected: 2 ")); // 2

        numsP00 = new int[]{1, 2, 3};
        kP00 = 4;
        System.out.println("Input: nums = [1, 2, 3], k = 4  Output: " + makeItBold(subarraySum(numsP00, kP00) +", Expected: 0 ")); // 0

        numsP00 = new int[]{1, 2, 1, 2, 1};
        kP00 = 3;
        System.out.println("Input: nums = [1, 2, 1, 2, 1], k = 3  Output: " + makeItBold(subarraySum(numsP00, kP00) +", Expected: 4 ")); // 4




    }
}

