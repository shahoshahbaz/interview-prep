package com.grokingcodeinterview.pattern24FibonacciNumbers;

import java.util.Arrays;

import static com.Utility.makeItBold;

public class P09PartitionEqualSubsetSum {
    /*
    Problem Statement:
    Given an integer array nums, return true if you can partition the array into
    two subsets such that the sum of the elements in both subsets is equal, or
    false otherwise.

    Example 1:
    Input: nums = [1,5,11,5]
    Output: true
    Explanation: The array can be partitioned as [1, 5, 5] and [11].

    Example 2:
    Input: nums = [1,2,3,5]
    Output: false
    Explanation: The array cannot be partitioned into equal sum subsets.

    Constraints:
    1 <= nums.length <= 200
    1 <= nums[i] <= 100
    */

    public static boolean canPartition(int[] nums) {

        int sum =0;
        for(int num:nums){
            sum += num;
        }
        if(sum %2 != 0)  return false;

        int target = sum /2;
        // dp[s] = true if some subset of the numbers processed so far can sum to exactly s
        boolean[] dp = new boolean[target +1];
        dp[0] = true;

        for(int num: nums){
            // walk backward from'target' to 'num'

            for(int s = target; s>=num; s--){
                // if we can make sum s by including num or not including num
                // dp[s] : is  including num, dp[s-num]: is not including num
               dp[s] = dp[s]|| dp[s-num];
            }
        }


        return dp[target];
    }

    public static void main(String[] args) {

        // ---- Test Case 1: Example 1 ----
        int[] nums1 = {1, 5, 11, 5};
        boolean expected1 = true;
        boolean result1 = canPartition(nums1);
        System.out.println("Input: " + Arrays.toString(nums1)
                + " | Output: " + makeItBold(String.valueOf(result1))
                + " | Expected: " + expected1);

        // ---- Test Case 2: Example 2 ----
        int[] nums2 = {1, 2, 3, 5};
        boolean expected2 = false;
        boolean result2 = canPartition(nums2);
        System.out.println("Input: " + Arrays.toString(nums2)
                + " | Output: " + makeItBold(String.valueOf(result2))
                + " | Expected: " + expected2);

        // ---- Edge Case 1: single element (can never partition into two non-empty equal subsets... but check the actual rule) ----
        int[] nums3 = {1};
        boolean expected3 = false;
        boolean result3 = canPartition(nums3);
I ne        System.out.println("Input: " + Arrays.toString(nums3)
                + " | Output: " + makeItBold(String.valueOf(result3))
                + " | Expected: " + expected3);

        // ---- Edge Case 2: two equal elements ----
        int[] nums4 = {2, 2};
        boolean expected4 = true;
        boolean result4 = canPartition(nums4);
        System.out.println("Input: " + Arrays.toString(nums4)
                + " | Output: " + makeItBold(String.valueOf(result4))
                + " | Expected: " + expected4);

        // ---- Edge Case 3: odd total sum (immediate false) ----
        int[] nums5 = {1, 2, 5};
        boolean expected5 = false;
        boolean result5 = canPartition(nums5);
        System.out.println("Input: " + Arrays.toString(nums5)
                + " | Output: " + makeItBold(String.valueOf(result5))
                + " | Expected: " + expected5);

        // ---- Edge Case 4: all same values, even count ----
        int[] nums6 = {3, 3, 3, 3};
        boolean expected6 = true;
        boolean result6 = canPartition(nums6);
        System.out.println("Input: " + Arrays.toString(nums6)
                + " | Output: " + makeItBold(String.valueOf(result6))
                + " | Expected: " + expected6);
    }
}
