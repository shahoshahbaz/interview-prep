package com.grokingcodeinterview.pattern07CyclicSort;

import java.util.Arrays;

import static com.Utility.makeItBold;
import static com.grokingcodeinterview.pattern07CyclicSort.P02FindTheMissingNumber.swap;

/*
 * Problem Statement
 * We are given an unsorted array containing n+1 numbers
 * from the range 1 to n. The array has only one duplicate
 * but it can be repeated multiple times.
 * Find that duplicate number without using any extra space.
 * You are, however, allowed to modify the input array.
 *
 * Example 1: Input: [1, 4, 4, 3, 2] Output: 4
 * Example 2: Input: [2, 1, 3, 3, 5, 4] Output: 3
 * Example 3: Input: [2, 4, 1, 4, 4] Output: 4
 *
 */
public class P04FindTheDuplicateNumber  {

    public static int findDuplicateNumber(int[] nums){
        if (nums == null) return -1;
        int n = nums.length -1;
        int i=0;
        while (i< nums.length){
            int targetIndex = nums[i] -1;
            if (nums[i] != nums[targetIndex]){
                swap(nums, i, targetIndex);
            }else{
                i++;
            }
        }

        for (i= 0; i<nums.length; i++) {
            if (nums[i] != i+1){
                return nums[i];
            }
        }
        return -1;
    }
    public static void main(String[] args) {


        System.out.println("====================================");
        System.out.println("P04. Find the Duplicate Number");
        System.out.println("====================================");
        int[] inputsP04 ={1, 4, 4, 3, 2};
        System.out.println("Input: " + Arrays.toString(inputsP04) + " => Output: " + makeItBold(findDuplicateNumber(inputsP04)+"") +", Expected: 4");
        inputsP04 =  new int[]{2, 1, 3, 3, 5, 4};
        System.out.println("Input: " + Arrays.toString(inputsP04) + " => Output: " + makeItBold(findDuplicateNumber(inputsP04)+"") +", Expected: 3");
        inputsP04 = new int[] {2, 4, 1, 4, 4};
        System.out.println("Input: " + Arrays.toString(inputsP04) + " => Output: " + makeItBold(findDuplicateNumber(inputsP04)+"") +", Expected: 4");
        inputsP04 =  new int[]{3, 1, 3, 4, 2};
        System.out.println("Input: " + Arrays.toString(inputsP04) + " => Output: " + makeItBold(findDuplicateNumber(inputsP04)+"") +", Expected: 3");
        inputsP04 = new int[] {1, 1};
        System.out.println("Input: " + Arrays.toString(inputsP04) + " => Output: " + makeItBold(findDuplicateNumber(inputsP04)+"") +", Expected: 1");

    }
}

