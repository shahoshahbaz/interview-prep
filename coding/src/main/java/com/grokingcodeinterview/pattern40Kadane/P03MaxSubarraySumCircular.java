package com.grokingcodeinterview.pattern40Kadane;

/*

 LC918. Maximum Sum Circular Subarray(Medium)

 Given a circular integer array nums of length n, return the maximum possible sum of a non-empty subarray of nums.

 A circular array means the end of the array connects to the beginning of the array. Formally, the next element of nums[i] is nums[(i + 1) % n] and the previous element of nums[i] is nums[(i - 1 + n) % n].

 A subarray may only include each element of the fixed buffer nums at most once. Formally, for a subarray nums[i], nums[i + 1], ..., nums[j], there does not exist i <= k1, k2 <= j with k1 % n == k2 % n.
 Example 1:  Input: nums = [1,-2,3,-2]  Output: 3
 Explanation: Subarray [3] has maximum sum 3.
 Example 2:  Input: nums = [5,-3,5]  Output: 10
 Explanation: Subarray [5,5] has maximum sum 5 + 5 = 10.
 Example 3:  Input: nums = [-3,-2,-3]  Output: -2
 Explanation: Subarray [-2] has maximum sum -2.
 Constraints:
 n == nums.length
 1 <= n <= 3 * 10^4
 -3 * 104 <= nums[i] <= 3 *
 */

/**
 * LC918. Maximum Sum Circular Subarray
 *
 * Key Insight: Two cases for the answer:
 *   Case 1 - Normal:   max subarray does NOT wrap → regular Kadane's
 *   Case 2 - Circular: max subarray DOES wrap     → total - minSubarray
 *
 * Example: [5, -3, 5, -2, 3, -8, 10]  total = 10
 *
 *   Case 1: maxSum = 10  (subarray [10])
 *   Case 2: [5, -3, 5, | -2, 3, -8, | 10]
 *                        ^  minSum  ^
 *           total - minSum = 10 - (-7) = 17  ← winner
 *           wrapping subarray = [5, -3, 5] + [10] = 17
 *
 *   Answer: Math.max(maxSum, total - minSum) = 17
 *
 * Edge case: all negatives → return maxSum (total - minSum = 0, wrong)
 */
public class P03MaxSubarraySumCircular {


    public static int maxSubarraySumCircle(int[] nums){

        int n = nums.length;
        int currMax = nums[0];
        int maxSum = nums[0];
        int currMin = nums[0];
        int minSum = nums[0];
        int totalSum = nums[0];

        for (int i =1; i<n ; i++ ){
            totalSum += nums[i];
            currMax = Math.max(nums[i], nums[i] + currMax);
            currMin = Math.max(nums[i], nums[i] +currMin);
            maxSum = Math.max(maxSum, currMax);
            minSum = Math.min(minSum, currMin);


        }

        if(maxSum < 0) return maxSum; // all numbers are negative, return the maximum number
        return Math.max(maxSum, totalSum - minSum);


    }

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("P03. Max Subarray Sum Circular");
        System.out.println("=====================================");
        int[] numsP03 = {1, -2, 3, -2};
        System.out.println("Input: " + java.util.Arrays.toString(numsP03) + " output: " + maxSubarraySumCircle(numsP03) +" Expected Output: 3"); // Output: 3
        numsP03 = new int[]{5, -3, 5};
        System.out.println("Input: " + java.util.Arrays.toString(numsP03) + " output: " + maxSubarraySumCircle(numsP03) +" Expected Output: 10"); // Output: 10
        numsP03 = new int[]{-3, -2, -3};
        System.out.println("Input: " + java.util.Arrays.toString(numsP03) + " output: " + maxSubarraySumCircle(numsP03) +" Expected Output: -2"); // Output: -2


    }
}
