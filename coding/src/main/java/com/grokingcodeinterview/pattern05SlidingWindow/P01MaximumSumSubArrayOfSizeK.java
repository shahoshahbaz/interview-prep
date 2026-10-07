package com.grokingcodeinterview.pattern05SlidingWindow;
/*
Given an array of positive numbers and a positive number 'k,'
 find the maximum sum of any contiguous subarray of size 'k'.

Example 1:

Input: arr = [2, 1, 5, 1, 3, 2], k=3
Output: 9
Explanation: Subarray with maximum sum is [5, 1, 3].
Example 2:

Input: arr = [2, 3, 4, 1, 5], k=2
Output: 7
Explanation: Subarray with maximum sum is [3, 4].
 */
/**
 * * [2, 1, 5, 1, 3, 2], k=3 maxSum
 *  s =0, e =0.. arr.length
 *  if (condition is voileted) then shrink
 ***/
public class P01MaximumSumSubArrayOfSizeK {

    public static int findMaxSum(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length || k <= 0) {
            return 0; // no valid subarray
        }
        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;
        int startWindow = 0;

        for (int windowEnd = 0; windowEnd< nums.length; windowEnd ++){
            currentSum += nums[windowEnd];
            if (windowEnd >= k -1){
                maxSum = Math.max(maxSum, currentSum);
                currentSum -= nums[startWindow];
                startWindow ++;

            }
        }
        return maxSum;

    }

    public static void main(String[] args) {
        // Test Case 1: Normal case
        int[] nums1 = {2, 1, 5, 1, 3, 2};
        System.out.println("Test Case 1: " + findMaxSum(nums1, 3)); // Expected: 9

        // Test Case 2: Array with all positive numbers
        int[] nums2 = {1, 2, 3, 4, 5};
        System.out.println("Test Case 2: " + findMaxSum(nums2, 2)); // Expected: 9

        // Test Case 3: Array with a single element
        int[] nums3 = {10};
        System.out.println("Test Case 3: " + findMaxSum(nums3, 1)); // Expected: 10

        // Test Case 4: Array with size less than k
        int[] nums4 = {1, 2};
        System.out.println("Test Case 4: " + findMaxSum(nums4, 3)); // Expected: 0

        // Test Case 5: Array with all elements equal
        int[] nums5 = {5, 5, 5, 5};
        System.out.println("Test Case 5: " + findMaxSum(nums5, 2)); // Expected: 10

        // Test Case 6: Array with k equal to array size
        int[] nums6 = {1, 2, 3, 4};
        System.out.println("Test Case 6: " + findMaxSum(nums6, 4)); // Expected: 10

        // Test Case 7: Empty array
        int[] nums7 = {};
        System.out.println("Test Case 7: " + findMaxSum(nums7, 3)); // Expected: 0

        // Test Case 8: Large k value
        int[] nums8 = {1, 2, 3, 4, 5};
        System.out.println("Test Case 8: " + findMaxSum(nums8, 6)); // Expected: 0

        // Test Case 9: Array with negative numbers
        int[] nums9 = {-1, -2, -3, -4, -5};
        System.out.println("Test Case 9: " + findMaxSum(nums9, 2)); // Expected: -3

        // Test Case 10: Array with mixed positive and negative numbers
        int[] nums10 = {3, -2, 5, -1, 6};
        System.out.println("Test Case 10: " + findMaxSum(nums10, 3)); // Expected: 10
    }
}
