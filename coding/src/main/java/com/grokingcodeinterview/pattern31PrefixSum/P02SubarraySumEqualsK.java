package com.grokingcodeinterview.pattern31PrefixSum;

import java.util.HashMap;
import java.util.Map;

import static com.Utility.makeItBold;

/*
Problem Statement

Given an integer array nums and an integer k, return the number of continuous subarrays whose sum equals k.

Example: Input: nums = [1, 2, 3], k = 3  Output: 2
Explanation: The subarrays that sum to 3 are: [1, 2], [3]
Example: Input: nums = [1, 1, 1], k = 2  Output: 2
Explanation: The subarrays that sum to 2 are: [1, 1], [1, 1]
Example: Input: nums = [1, 2, 3], k = 4  Output: 1
Explanation: The subarray that sums to 4 is: [1, 3]

 */
public class P02SubarraySumEqualsK {
    public static int subarraySum(int[] nums, int target){

        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1);
        int currentSum =0;
        int count =0;
        for (int num: nums){
            currentSum += num;
            int previousSum = currentSum -target;
            count += prefixCount.getOrDefault(previousSum, 0);
            prefixCount.put(currentSum, prefixCount.getOrDefault(currentSum, 0) +1);
        }

        return count;

//        // key:prefix sum, value: count of how many times this prefix sum has occurred.
//        Map<Integer,Integer> prefixCount = new HashMap<>();
//        // prefix sum 0 has one count (the empty subarray)
//        prefixCount.put(0, 1);
//
//        int currentSum =0;
//        int count =0;
//
//        for(int num: nums){
//            currentSum += num;
//            // currentSum -target gives us the prefix . why?
//            // because currentSum- previousSum = target
//            // how do we know currentSum- previousSum is target?
//            // because we are looking for subarrays that sum to target, so if we find a previousSum such that currentSum - previousSum = target,
//            // then we have found a subarray that sums to target.
//            // so previousSum is the prefix sum andV
//            // here previousSum is the key in the map
//            // check if there is a prefix sum
//
//            int previousSum = currentSum - target;
//            count += prefixCount.getOrDefault(previousSum, 0);
//            prefixCount.put(currentSum, prefixCount.getOrDefault(currentSum, 0) +1);
//        }
//
//        return count;



    }
    public static void main(String[] args) {
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
        System.out.println("Input: nums = [1, 2, 3], k = 4  Output: " + makeItBold(subarraySum(numsP00, kP00) +", Expected: 0")); // 0

        numsP00 = new int[]{1, 2, 1, 2, 1};
            kP00 = 3;
        System.out.println("Input: nums = [1, 2, 1, 2, 1], k = 3  Output: " + makeItBold(subarraySum(numsP00, kP00) +", Expected: 4 ")); // 4


    }
}

