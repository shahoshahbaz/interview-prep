package com.grokingcodeinterview.pattern07CyclicSort;

import java.util.Arrays;

import static com.Utility.makeItBold;
import static com.grokingcodeinterview.pattern07CyclicSort.P02FindTheMissingNumber.swap;
/*
Problem Statement
Given an unsorted array containing numbers, find the smallest missing positive number in it.
Note: Positive numbers start from '1'.

Example 1: Input: [-3, 1, 5, 4, 2] Output: 3
Explanation: The smallest missing positive number is '3'
Example 2: Input: [3, -2, 0, 1, 2] Output: 4
Example 3: Input: [3, 2, 5, 1] Output: 4
Example 4: Input: [33, 37, 5] Output: 1
Constraints:

1 <= nums.length <=
-2&31 <= nums[i] <= 2^31 - 1
 */

public class P07FindTheSmallestMissingPositiveNumber {
    static boolean debug = false;
    public static int findMissingPositive (int[] nums){
        if(debug) System.out.println("Original array: "+  Arrays.toString(nums));
        if (nums == null  || nums.length ==0) return 1;
        int  i =0;
        while (i< nums.length){
            int correctIndex = nums[i] -1;
            if (correctIndex >=0 && correctIndex< nums.length && nums[i] != nums[correctIndex]){
                swap(nums, i, correctIndex);
            }else{
                i++;
            }
        }
        if(debug)System.out.println("After sorting by cyclic sort: " +Arrays.toString(nums));
        for ( i =0; i<nums.length; i++ ){
            if (nums[i] != i+1){
                return i+1;
            }

        }

        return nums.length +1;
    }
    public static void main(String[] args) {


        System.out.println("===============================================================");
        System.out.println("P07. Find the Smallest Missing Positive Number");
        System.out.println("===============================================================");

        int[] numsP07 = {-3, 1, 5, 4, 2};
        System.out.println("input: " + Arrays.toString(numsP07) +", output: " + makeItBold(findMissingPositive(numsP07)+"") +" ,Expected: 3");
        numsP07 = new int[]{3, -2, 0, 1, 2};
        System.out.println("input: " + Arrays.toString(numsP07) +", output: " + makeItBold(findMissingPositive(numsP07)+"") +" ,Expected: 4");
        numsP07 = new int[]{3, 2, 5, 1};
        System.out.println("input: " + Arrays.toString(numsP07) +", output: " + makeItBold(findMissingPositive(numsP07)+"") +" ,Expected: 4");
        numsP07 = new int[]{33, 37, 5};
        System.out.println("input: " + Arrays.toString(numsP07) +", output: " + makeItBold(findMissingPositive(numsP07)+"") +" ,Expected: 1");

    }
}

