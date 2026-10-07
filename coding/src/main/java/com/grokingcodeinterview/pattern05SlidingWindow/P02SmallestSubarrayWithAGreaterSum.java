package com.grokingcodeinterview.pattern05SlidingWindow;
/*
Given an array of positive integers and a number ‘S,’
find the length of the smallest contiguous subarray
whose sum is greater than or equal to 'S'. Return 0 if no such subarray exists.

Example 1:

Input: arr = [2, 1, 5, 2, 3, 2], S=7
Output: 2
Explanation: The smallest subarray with a sum greater than or equal to '7' is [5, 2].
Example 2:

Input: arr = [2, 1, 5, 2, 8], S=7
Output: 1
Explanation: The smallest subarray with a sum greater than or equal to '7' is [8].
Example 3:

Input: arr = [3, 4, 1, 1, 6], S=8
Output: 3
Explanation: Smallest subarrays with a sum greater than or equal to '8' are [3, 4, 1] or [1, 1, 6].
Constraints:

1 <= S <=
1 <= arr.length <= 105
1 <= arr[i] <= 104
 */

import java.util.Arrays;

import static com.Utility.makeItBold;

/**
 *  Input: arr = [2, 1, 5, 2, 3, 2], S=7
 * Output: 2
 *
 */
public class P02SmallestSubarrayWithAGreaterSum {
    public static int findSmallestSubarrayWithAGreaterSum(int[] nums, int s){
        if (nums == null || nums.length == 0 || s <= 0) {
            return 0;
        }
        int minLength = Integer.MAX_VALUE;
        int currentSum = 0;
        int windowStart = 0;

        for (int windowEnd = 0; windowEnd< nums.length; windowEnd++){
            currentSum += nums[windowEnd];

            while (currentSum>= s ){
                minLength = Math.min(minLength, windowEnd - windowStart +1);
                currentSum -= nums[windowStart];
                windowStart ++;
            }
        }
        return minLength == Integer.MAX_VALUE? 0: minLength;


    }

    public static void main(String[] args) {

        // Test Case 1: Example from problem statement
        int[] numsP02 = {2, 1, 5, 2, 3, 2};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=7 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 7)) +
                makeItBold(" Expected: 2"));

        // Test Case 2: Another example from problem
        numsP02 = new int[] {2, 1, 5, 2, 8};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=7 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 7)) +
                makeItBold(" Expected: 1"));

        // Test Case 3: Third example
        numsP02 = new int[]{3, 4, 1, 1, 6};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=8 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 8)) +
                makeItBold(" Expected: 3"));

        // Test Case 4: No subarray meets the condition
        numsP02 = new int[]{1, 2, 3, 4};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=15 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 15)) +
                makeItBold(" Expected: 0"));

        // Test Case 5: All elements equal
        numsP02 = new int[]{4, 4, 4, 4};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=12 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 12)) +
                makeItBold(" Expected: 3"));
        numsP02 = new int[]{4, 4, 4, 4};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=8 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 8)) +
                makeItBold(" Expected: 2"));

        // Test Case 6: Single element array that satisfies
        numsP02 = new int[]{10};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=5 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 5)) +
                makeItBold(" Expected: 1"));

        // Test Case 7: Single element array that doesn't satisfy
        numsP02 = new int[]{3};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=5 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 5)) +
                makeItBold(" Expected: 0"));

        // Test Case 8: Empty array
        numsP02 = new int[]{};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=5 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 5)) +
                makeItBold(" Expected: 0"));

        // Test Case 9: Target sum is 0
        numsP02 = new int[]{1, 2, 3};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=0 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 0)) +
                makeItBold(" Expected: 1"));

        // Test Case 10: Exactly matching sum
        numsP02 = new int[]{2, 3, 1, 2, 4, 3};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=7 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 7)) +
                makeItBold(" Expected: 2"));
    }

}
