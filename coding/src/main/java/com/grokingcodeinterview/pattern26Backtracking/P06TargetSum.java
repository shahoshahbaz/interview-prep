package com.grokingcodeinterview.pattern26Backtracking;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
 You are given an integer array nums and an integer target.
You want to build an expression out of nums by adding one of the symbols '+' or '-' before each integer in nums and then concatenating all the integers.
For example, if nums = [2, 1], you can add a '+' before 2 and a '-' before 1 and concatenate them to build the expression "+2-1".
Return the number of different expressions that you can build, which evaluates to target.
Example 1:

Input: nums = [1,1,1,1,1], target = 3
Output: 5
Explanation: There are 5 ways to assign symbols to make the sum of nums be target 3.
-1+1+1+1+1 = 3
+1-1+1+1+1 = 3
+1+1-1+1+1 = 3
+1+1+1-1+1 = 3
+1+1+1+1-1 = 3

Example 2:

Input: nums = [1], target = 1
Output: 1

Constraints:

1 <= nums.length <= 20
0 <= nums[i] <= 1000
0 <= sum(nums[i]) <= 1000
-1000 <= target <= 1000
 */
public class P06TargetSum {

    public static int findTargetSumWays(int[] nums, int target) {
        return backtrack(nums,  0, 0, target);
    }
    public static int backtrack(int[] nums, int index, int currSum, int target ){

        if(index == nums.length){
            return currSum == target? 1:0;
        }

        int addWays = backtrack(nums, index+1, currSum + nums[index], target);
        int subtractWays = backtrack(nums, index+1, currSum- nums[index], target);
        // where backtracking happens, we explore both adding and subtracting the current number and
        // sum the ways to reach the target from both paths.no,
        // how we backtrack the element we added or subtracted,
        // we don't need to explicitly remove it from currSum
        // because we are passing a new value to the next recursive call.
        return addWays + subtractWays;
    }


    public static void main(String[] args) {

        System.out.println("===========================");
        System.out.println("P06. Target Sum");
        System.out.println("===========================");

        // Test 1: multiple ways to reach target
        int[] nums1 = {1, 1, 1, 1, 1};
        int target1 = 3;
        int result1 = findTargetSumWays(nums1, target1);
        System.out.println("Input:  " + Arrays.toString(nums1) + ", target = " + target1 + " ,Output: " + makeItBold(result1 + "") + " ,Expected: 5");

        // Test 2: single element equals target
        int[] nums2 = {1};
        int target2 = 1;
        int result2 = findTargetSumWays(nums2, target2);
        System.out.println("Input:  " + Arrays.toString(nums2) + ", target = " + target2 + " ,Output: " + makeItBold(result2 + "") + " ,Expected: 1");

        // Test 3: target is negative
        int[] nums3 = {1, 2, 3};
        int target3 = -2;
        int result3 = findTargetSumWays(nums3, target3);
        System.out.println("Input:  " + Arrays.toString(nums3) + ", target = " + target3 + " ,Output: " + makeItBold(result3 + "") + " ,Expected: 1");

        // Test 4: zero in array
        int[] nums4 = {0, 0, 0, 0, 0, 0, 0, 0, 1};
        int target4 = 1;
        int result4 = findTargetSumWays(nums4, target4);
        System.out.println("Input:  " + Arrays.toString(nums4) + ", target = " + target4 + " ,Output: " + makeItBold(result4 + "") + " ,Expected: 256");

        // Test 5: no valid expressions
        int[] nums5 = {1, 2};
        int target5 = 4;
        int result5 = findTargetSumWays(nums5, target5);
        System.out.println("Input:  " + Arrays.toString(nums5) + ", target = " + target5 + " ,Output: " + makeItBold(result5 + "") + " ,Expected: 0");
    }
}

