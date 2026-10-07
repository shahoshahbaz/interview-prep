package com.grokingcodeinterview.pattern07CyclicSort;

import java.util.Arrays;

import static com.Utility.makeItBold;
import static com.grokingcodeinterview.pattern07CyclicSort.P02FindTheMissingNumber.swap;

/*
 * We are given an unsorted array containing â€˜nâ€™ numbers taken from the range 1 to â€˜nâ€™.
 * The array originally contained all the numbers from 1 to â€˜nâ€™,
 * but due to a data error, one of the numbers got duplicated which also resulted
 * in one number going missing. Find both these numbers.
 * Example 1: Input: [3, 1, 2, 5, 2] Output: [2, 4]
 * Explanation: '2' is duplicated and '4' is missing.
 * Example 2: Input: [3, 1, 2, 3, 6, 4] Output: [3, 5]
 * Explanation: '3' is duplicated and '5' is missing.
 */
public class P06FindTheCorruptPair {
    public static int[] corruptPair(int[] nums){
        int[] pair = new int[2];
        if (nums == null ) return pair;
        int i= 0;
        while (i< nums.length){
            int correctedIndex = nums[i] -1;
            if (nums[i] != nums[correctedIndex]){
                swap(nums, i, correctedIndex);

            }else{
                i++;
            }
        }
        for (i =0; i< nums.length; i++){
            int correctIndex = nums[i] -1;
            if (i != correctIndex){
                pair[0] = nums[i];
                pair[1] = i+1;
                break;
            }
        }
        return pair;
    }

    public static void main(String[] args) {
        System.out.println("===================================================");
        System.out.println("P06. Find the Corrupt Pair");
        System.out.println("===================================================");
      int[] numsP06 = new int[]{3, 1, 2, 5, 2};
        int[] result06 = corruptPair( numsP06);

        System.out.println("Input: " + Arrays.toString(numsP06) +
                " ,Output: " + makeItBold(Arrays.toString(result06)) + ", Expected Output: [2, 4]");

        numsP06 = new int[]{3, 1, 2, 3, 6, 4};
        result06 = corruptPair( numsP06);
        System.out.println("Input: " + Arrays.toString(numsP06) +
                " ,Output: " + makeItBold(Arrays.toString(result06)) + ", Expected Output: [3, 5]");
        numsP06 = new int[]{1, 5, 3, 2, 2, 7, 6, 4, 8, 8};
        result06 = corruptPair( numsP06);
        System.out.println("Input: " + Arrays.toString(numsP06) +
                " ,Output: " + makeItBold(Arrays.toString(result06)) + ", Expected Output: [2, 9]");

    }
}

