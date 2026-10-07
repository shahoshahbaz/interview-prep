package com.grokingcodeinterview.pattern05SlidingWindow;

/*
 * Problem Statement
 * Given an array containing 0s and 1s,
 * if you are allowed to replace no more than ‘k’ 0s with 1s,
 * find the length of the longest contiguous subarray having all 1s.
 *
 * Example 1:
 *
 * Input: Array=[0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1], k=2
 * Output: 6
 * Explanation: Replace the '0' at index 5 and 8 to have the longest contiguous subarray of 1s having length 6.
 * Example 2:
 * Input: Array=[0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1], k=3
 * Output: 9
 * Explanation: Replace the '0' at index 6, 9, and 10 to have the longest contiguous subarray of 1s having length 9.
 * Example 3:
 * Input: Array=[1, 0, 0, 1, 1, 0, 1, 1], k=2
 * Output: 6
 * Explanation: By flipping 0 at the second and fifth index in the list, we get [1, 0, 1, 1, 1, 1, 1, 1], which has 6 consecutive 1s.
 * Constraints:
 * 1 <= arr.length <=
 * arr[i] is either 0 or 1.
 * 0 <= k <= nums.length
 */
public class P06LongestSubarrayWithOnesAfterReplacement {
    public static int findLength(int[] arr, int k){
         int longest = Integer.MIN_VALUE;
         int windowStart = 0;
         int currentOnes =0;

         for (int windowEnd = 0; windowEnd < arr.length; windowEnd++){
             if (arr[windowEnd] ==1){
                 currentOnes ++;
             }


             while (windowEnd - windowStart +1 -currentOnes >k){
                 if (arr[windowStart] ==1){
                     currentOnes --;
                 }
                 windowStart++;
             }
             longest  =Math.max(longest, windowEnd - windowStart + 1);

         }
         return longest;
    }

    public static void main(String[] args) {
        System.out.println(findLength(new int[]{1, 0, 0, 1, 1, 0, 1, 1}, 2)); // 6
        System.out.println(findLength(new int[]{0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1}, 2)); // 6
        System.out.println(findLength(new int[]{0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1}, 3)); // 9
        System.out.println(findLength(new int[]{1, 1, 1}, 1)); // 3
        System.out.println(findLength(new int[]{0, 0, 0}, 0)); // 0
    }
}
