package com.grokingcodeinterview.pattern31PrefixSum;

import java.util.Arrays;

import static com.Utility.makeItBold;

public class pattern32PrefixSumR1 {
        /*
    Problem Statement
    Given an input array of integers nums, find an integer array,
    let's call it differenceArray, of the same length as an input integer array.
    Each element of differenceArray, i.e., differenceArray[i], should be calculated as follows:
    take the sum of all elements to the left of index i in array nums
     (let's call it leftSum(i), and
      subtract it from the sum of all elements to the right of index i in array nums (let's call it rightSum(i)), taking the absolute value of the result:
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
    public static int[] leftRightDifference(int[] nums){
        int n = nums.length;
        int[] result = new int[n];

        int rightMost = 0;
        int leftMost = 0;
        for (int num: nums)
            rightMost += num;
        for (int i =0; i<n; i++){
            rightMost = rightMost - nums[i];
            result[i] = Math.abs(rightMost- leftMost);
            leftMost = leftMost + nums[i];

        }
        return result;
    }

    /*
    Given an integer array nums, return the leftmost middleIndex (i.e., the smallest amongst all the possible ones).
    A middleIndex is an index where the sum of the numbers to the left of this index is equal to the sum of the numbers to the right of this index.

    You can consider the left sum 0 for middleIndex == 0, and right sum 0 for middleIndex == nums.length - 1.

    If no middleIndex exists in nums, return -1.

    Examples
    Example 1:
    Input: nums = [1, 7, 3, 6, 5, 6]
    Expected Output: 3
    Justification: The sum of the numbers to the left of index 3 (1 + 7 + 3 = 11) is equal to the sum of the numbers to the right of index 3 (5 + 6 = 11).
    Example 2:
    Input: nums = [2, 1, -1]
    Expected Output: 0
    Justification: The sum of the numbers to the left of index 0 is considered to be 0. The sum of the numbers to the right of index 0 (1 + -1 = 0) is also 0.
    Example 3:
    Input: nums = [2, 3, 5, 5, 3, 2]
    Expected Output: -1
    Justification: There is no middleIndex exists in the array.
    Constraints:

    1 <= nums.length <= 100
    -1000 <= nums[i] <= 1000
 */
    /**
     Input: nums = [1, 7, 3, 6, 5, 6]      Expected Output: 3
      1, 8, 11, 17, 22, 28
     28
     rightmost = 28 00 - 1 = 27
     leftSum = 1
     rightMost 28 -1 -7 = 20
     leftmost = 8
     rightmost = 28 - 8 -3 = 17
     leftmost = 11
     rightmost = 28 -11 - 6 = 28 -17 = 11
     */
    public static int findMiddleIndex(int[] nums) {
        int totalSum =0;
        for (int num:nums) {
            totalSum += num;
        }
        int leftSum = 0;
        for (int i =0; i< nums.length; i++) {

            int rightSum = totalSum - leftSum - nums[i];
            if (leftSum == rightSum) {
                return i;
            }
            leftSum += nums[i];
        }
        return -1;

    }
    /*
Given an array nums and a range query (i, j), find the sum of elements between indices i and j.
Example
Input: arr = [1, 2, 3, 4], i = 1, j = 3
Output: 9
Justification: The sum of 2, 3 and 4 is 9.
 */
    public static int computeSumQuery(int[] prefixSum, int i, int j){


        return (i ==0)? prefixSum[j]: prefixSum[j] - prefixSum[ i-1];


    }
    private static int[] computePrefixSum(int[] nums){
        int[] prefixSum = new int[nums.length];

        prefixSum[0] = nums[0];
        for (int i =1; i< nums.length; i++){

            prefixSum[i] = prefixSum[i-1] +  nums[i];

        }
        return prefixSum;
    }

    public static void main(String[] args) {


        System.out.println("=================================================================");
        System.out.println("P01. Range Sum Query :");
        System.out.println("=================================================================");

        int[] numsP01 = {1, 2, 3, 4};
        int iP01 = 1;
        int jP01 = 3;
        int[] prefixP01 = computePrefixSum(numsP01);
        System.out.println("Input: " + Arrays.toString(numsP01) + ", i = " + iP01 + ", j = " + jP01 + ", Output: " + makeItBold(computeSumQuery(prefixP01, iP01, jP01) +" ") +"Expected: 9");

        iP01 = 0;
        jP01 = 2;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", i = " + iP01 + ", j = " + jP01 + ", Output: " + makeItBold(computeSumQuery(prefixP01, iP01, jP01) +" ") +"Expected: 6");

        iP01 = 0;
        jP01 = 3;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", i = " + iP01 + ", j = " + jP01 + ", Output: " + makeItBold(computeSumQuery(prefixP01, iP01, jP01) +" ") +"Expected: 10");

        System.out.println("=========================");
        System.out.println("P03: FIND MIDDLE INDEX IN ARRAY");
        System.out.println("=========================\n");
        int[] numsP03 = {1, 7, 3, 6, 5, 6};
        System.out.println("Input: " + Arrays.toString(numsP03) +", Output: " + makeItBold(findMiddleIndex(numsP03)+" ") + "Expected: 3");
        numsP03 = new int[]{2, 1, -1};
        System.out.println("Input: " + Arrays.toString(numsP03) +", Output: " + makeItBold(findMiddleIndex(numsP03)+" ") + "Expected: 0");
        numsP03 = new int[]{2, 3, 5, 5, 3, 2};
        System.out.println("Input: " + Arrays.toString(numsP03) +", Output: " + makeItBold(findMiddleIndex(numsP03)+" ") + "Expected: -1");

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


