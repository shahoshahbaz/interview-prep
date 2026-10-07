package com.grokingcodeinterview.pattern05SlidingWindow;

import java.util.Arrays;

/**
 * Given an array nums with positive numbers and a positive integer target,
 * return the count of contiguous subarrays whose product is less than the target number.
 *
 * Examples
 * Example 1:
 * Input: nums = [2, 5, 3, 10], target=30
 * Output: 6
 * Explanation: There are six contiguous subarrays ([2], [5], [2, 5], [3], [5, 3], [10]) whose product is less than the target.
 * Example 2:
 * Input: nums = [8, 2, 6, 5], target=50
 * Output: 7
 * Explanation: There are seven contiguous subarrays ([8], [2], [8, 2], [6], [2, 6], [5], [6, 5]) whose product is less than the target.
 * Example 3:
 * Input: nums = [10, 5, 2, 6], k = 0
 * Expected Output: 0
 * Explanation: Subarrays with product less than 0 doesn't exists.
 *  [2, 5, 3, 10], target=30
 */
public class P10CountingSubarraysWithProductLessThanATarget {

    public static int  countSubarrays(int [] nums, int target){
        if (nums == null || nums.length==0 || target <= 1) return 0;



        int windowStart = 0;
        int count =0;
        int product=1;
        for (int windowEnd = 0; windowEnd< nums.length; windowEnd ++){
            product *=nums[windowEnd];

            while(product >= target){
                product /=nums[windowStart];
                windowStart ++;
            }

            count += windowEnd - windowStart +1;


        }
        return count;
    }

    public static void main(String[] args) {
        testCase(new int[]{2, 5, 3, 10}, 30, 6);
        testCase(new int[]{8, 2, 6, 5}, 50, 7);
        testCase(new int[]{10, 5, 2, 6}, 0, 0);
        testCase(new int[]{1, 2, 3}, 0, 0);
        testCase(new int[]{1, 1, 1}, 2, 6); // All 6 subarrays of [1,1,1] are valid
        testCase(new int[]{1, 1, 1}, 1, 0); // None valid
        testCase(new int[]{10}, 9, 0);      // Single element greater than target
        testCase(new int[]{1}, 2, 1);       // Single element less than target
        testCase(new int[]{1, 2, 3, 4}, 10, 7); // [1],[1,2],[2],[2,3],[3],[4],[3,4]
    }

    private static void testCase(int[] nums, int target, int expected) {
        int actual = countSubarrays(nums, target);
        System.out.println("Input: nums = " + Arrays.toString(nums) + ", target = " + target);
        System.out.println("Expected Output: " + expected);
        System.out.println("Actual Output  : " + actual);
        System.out.println(actual == expected ? "✅ PASS\n" : "❌ FAIL\n");
    }
}
