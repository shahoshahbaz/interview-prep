package com.grokingcodeinterview.pattern31PrefixSum;

import java.util.Arrays;

import static com.Utility.makeItBold;

    /*
    Problem Statement
    Given an input array of integers nums, find an integer array, let's call it differenceArray, of the same length as an input integer array.
    Each element of differenceArray, i.e., differenceArray[i], should be calculated as follows: take the sum of all elements to the left of index i in array nums
     (let's call it leftSum(i), and subtract it from the sum of all elements to the right of index i in array nums (let's call it rightSum(i)), taking the absolute value of the result:
    differenceArray[i] = | leftSumi - rightSumi |
    If there are no elements to the left or right of i, the corresponding sum should be taken as 0.
    Example 1: Input: nums = [2, 5, 1, 6, 1]  Expected Output: [13, 6, 0, 7, 14]
    Explanation:
    For i=0: |(0) - (5+1+6+1)| = |0 - 13| = 13
    For i=1: |(2) - (1+6+1)| = |2 - 8| = 6
    For i=2: |(2+5) - (6+1)| = |7 - 7| = 0
    For i=3: |(2+5+1) - (1)| = |8 - 1| = 7
    For i=4: |(2+5+1+6) - (0)| = |14 - 0| = 14
    Example 2:  Input: nums = [3, 3, 3] Expected Output: [6, 0, 6]
    Explanation:
    For i=0: |(0) - (3+3)| = 6
    For i=1: |(3) - (3)| = 0
    For i=2: |(3+3) - (0)| = 6
    Example 3:

    Input: nums = [1, 2, 3, 4, 5]
    Expected Output: [14, 11, 6, 1, 10]
    Explanation:
    Calculations for each index i will follow the above-mentioned logic.
    Constraints:

    1 <= nums.length <= 1000
    1 <= nums[i] <= 105
     */
public class P04LeftAndRightSumDifferences {

    public static int[  ] leftRightDifference(int[] nums){
        int n = nums.length;
        // claculate totalSum
        int[] result = new int[n];

        int leftMost =0;
        int rightMost = 0;
        for (int num: nums){
            rightMost += num;
        }

        for(int i =0; i<n; i++){
            rightMost -= nums[i];
            result[i] = Math.abs(rightMost - leftMost);
            leftMost += nums[i];
        }

        return result;

    }

    public static void main(String[] args) {
        System.out.println("===================================================");
        System.out.println("P04. left and right sum differences");
        System.out.println("===================================================");

        int[] numsP04 = {10, 4, 8, 3};

        System.out.println("Input: " + Arrays.toString(numsP04) + ", Output: " + makeItBold(Arrays.toString(leftRightDifference(numsP04))) + " Expected: [15, 1, 11, 22]");

        numsP04 = new int[]{1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(numsP04) + ", Output: " + makeItBold(Arrays.toString(leftRightDifference(numsP04))) + " Expected: [14, 11, 6, 1, 10]");
        numsP04 = new int[]{3, 3, 3};
        System.out.println("Input: " + Arrays.toString(numsP04) + ", Output: " + makeItBold(Arrays.toString(leftRightDifference(numsP04))) + " Expected: [6, 0, 6]");
    }
}

