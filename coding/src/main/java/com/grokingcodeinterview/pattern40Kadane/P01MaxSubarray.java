package com.grokingcodeinterview.pattern40Kadane;

import java.util.Arrays;

/*
  Given an integer array nums, find the subarray with the largest sum and return that sum.
  example:  Input:  [-2, 1, -3, 4, -1, 2, 1, -5, 4] Output: 6
  example:  Input:  [1] Output: 1
  example:  Input:  [5,4,-1,7,8] Output: 23
  Constraints:
    1 <= nums.length <= 10^5
    -10^4 <= nums[i] <= 10^4

 */

/**
 * How this algortihm works:
 * 1. Initialize two variables, maxSum and currentSum, to the first element of the array.
 * 2. Iterate through the array starting from the second element.
 * 3. For each element, update currentSum to be the maximum of the current element and the sum of currentSum and the current element.
 * 4. Update maxSum to be the maximum of maxSum and currentSum.
 * 5. Return maxSum.
 *
 * dry-run example:
 * Input [-2, 3, -1, 4, -6, 2, 3] output: 6
 *
 * Input : -2, 3, -1, 4, -6, 2, 3
 * Index : 0   1   2  3   4  5  6
 *
 * maxSum : ms, currentSum: cs
 *
 * ms = -2, cs: -2,
 * i = 1;  nums[1] = 3 currentSum =max(3, -2+3) => currentSum  = 3, maxSum = 3
 * i = 2 ; nums[2] = -1 currentSum = max(-1, -1 + 3(currentSum) = 2   maxSum = 3 // [3, -1
 * i = 3 ; nums[3] = 4 currentSum = max(4, 4+ 2(currentSum)) = 6, maxSum = 6 // [3,  -1, 4
 * i = 4 ; nums[4= -6 currentSum = max( -6, 6-6(currentSum)) = 0, maxSum = 6
 * i = 5 ; nums[5 ] = 2 currentSum = max(2, 0+ 2)  = 2, maxSum = 6;
 * i = 6 ; nums[6] = 3 currentSum = max(3,  2 + 3) = 5 maxSum = 6
 *
 */
public class P01MaxSubarray {

    public static int maxSubArray(int[] nums) {
        int maxSum = nums[0];
        int currentSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("P02. Max Subarray");
        System.out.println("=====================================");

        int[] numsP01 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Input: " + Arrays.toString(numsP01) + " output: " + maxSubArray(numsP01) +" Expected Output: 6"); // Output: 6

        numsP01 =  new int[]{1};
        System.out.println("Input: " + Arrays.toString(numsP01) + " output: " + maxSubArray(numsP01) +" Expected Output: 1"); // Output: 1

        numsP01 = new int[]{5, 4, -1, 7, 8};
        System.out.println("Input: " + Arrays.toString(numsP01) + " output: " + maxSubArray(numsP01) +" Expected Output: 23"); // Output: 23
        numsP01 = new int[] {-2, 3, -1, 4, -6, 2, 3};
        System.out.println("Input: " + Arrays.toString(numsP01) + " output: " + maxSubArray(numsP01) +" Expected Output: 6"); // Output: 6
    }
}
