package com.grokingcodeinterview.pattern03TwoPointers;
/*
Problem Statement
Given an array, find the length of the smallest subarray in it which when sorted will sort the whole array.

Example 1:

Input: [1, 2, 5, 3, 7, 10, 9, 12]
Output: 5
Explanation: We need to sort only the subarray [5, 3, 7, 10, 9] to make the whole array sorted
Example 2:

Input: [1, 3, 2, 0, -1, 7, 10]
Output: 5
Explanation: We need to sort only the subarray [1, 3, 2, 0, -1] to make the whole array sorted
Example 3:

Input: [1, 2, 3]
Output: 0
Explanation: The array is already sorted
Example 4:

Input: [3, 2, 1]
Output: 3
Explanation: The whole array needs to be sorted.
Constraints:

1 <= arr.length <= 104
-105 <= arr[i] <= 105
 */

import java.util.Arrays;

import static com.Utility.*;

/**
 * index:                    0  1  2  3   4  5  6
 *  dry run example: Input: [1, 3, 2, 0, -1, 7, 10]
 *  left =0, right =6
 *  while (left< right && nums[left] <= nums[left+1]) left++; => left =1  why? because 1<3  // so element in index 1 is out of order
 *  while (left < right && nums[right] >= nums[right -1] ) right --; => right =4 why? because 10>7> -1 so element in index 4 is out of order
 *  if (left == right ) return 0; => false
 *  // find the maximum and minimum of the subarray
 *  int maxSubArray = Integer.MIN_VALUE;
 *  int minSubArray = Integer.MAX_VALUE;
 *   for (int i = left; i<= right; i++){ // left = 1, right = 4
 *     maxSubArray = Math.max(maxSubArray, nums[i]); => maxSubArray =3 why? because 3>2>0>-1
 *     minSubArray = Math.min(minSubArray, nums[i]); => minSubArray = -1
 *     }
 *     while(left>0 && nums[left -1] > minSubArray) left --; // extend the left pointer why?
 *                                                            because there are numbers greater than the minSubArray
 *     so left = 1>0 &&nums[0] =1 > -1 => left =0
 *     while (right < nums.length -1 && nums[right +1] < maxSubArray) right ++; //
 *     extend the right pointer why? because there are numbers less than the max
 *     so right = 4 <6 &&nums[5] =7 <3 => false so right =4
 *     return right - left +1 => 4 -0 +1 =5
 */
public class P11MinimumWindowSort {


    public static int smallestSubarrayLength(int[] nums) {
        log("Input array: " + Arrays.toString(nums) );

        if(nums.length == 0) return 0;
        int left = 0;
        int right = nums.length - 1;
        // find the first number out of sorting order from the beginning
        while (left< right && nums[left] <= nums[left+1]) left++;
        while (left < right && nums[right] >= nums[right -1] ) right --;
        if (left == right ) return 0;  // why? because the array is already sorted
        log("Initial left index: " + left + ", right index: " + right);
        // find the maximum and minimum of the subarray
        int maxSubArray = Integer.MIN_VALUE;
        int minSubArray = Integer.MAX_VALUE;



        for (int i = left; i<= right; i++){
            maxSubArray = Math.max(maxSubArray, nums[i]);
            minSubArray = Math.min(minSubArray, nums[i]);

        }
        log("Subarray from index " + left + " to " + right + ", min: " + minSubArray + ", max: " + maxSubArray);

        // extend the left pointer why? because there are numbers greater than the minSubArray
        while(left>0 && nums[left -1] > minSubArray) {
            log("Extending left because nums[" + (left - 1) + "]=" + nums[left - 1] + " > min=" + minSubArray);

            left--;
        }
        log("Extended left index: " + left);

        // extend the right pointer why? because there are numbers less than the maxSub
        while (right < nums.length -1 && nums[right +1] < maxSubArray) {
            log("Extending right because nums[" + (right + 1) + "]=" + nums[right + 1] + " < max=" + maxSubArray);
            right++;
        }
        log("Extended right index: " + right);


        return right - left +1;



    }

    public static void main(String[] args) {
        // add some test cases here
        int[] numsP11 = {1, 2, 5, 3, 7, 10, 9, 12};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+5));

        numsP11 = new int[] {1, 3, 2, 0, -1, 7, 10};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+5));
        numsP11 = new int[] {1, 2, 3};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+0));
        numsP11 = new int[] {3, 2, 1};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+3));


         numsP11 = new int[] {5, 8, 6, 7, 9, 3, 10, 15, 12, 14};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+7));

        numsP11 = new int[] {1, 2, 3, 4, 5};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+0));
        numsP11 = new int[] {5, 9, 7, 8, 6, 10, 4, 11};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+6));

    }
}
