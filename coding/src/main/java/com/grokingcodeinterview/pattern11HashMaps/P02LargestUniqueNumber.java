package com.grokingcodeinterview.pattern11HashMaps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
Problem Statement
Given an array of integers, identify the highest value that appears only once in the array. If no such number exists, return -1.

Example 1: Input: [5, 7, 3, 7, 5, 8] Expected Output: 8
Justification: The number 8 is the highest value that appears only once in the array.
Example 2: Input: [1, 2, 3, 2, 1, 4, 4] Expected Output: 3
Justification: The number 3 is the highest value that appears only once in the array.
Example 3: Input: [9, 9, 8, 8, 7, 7] Expected Output: -1
Justification: There is no number in the array that appears only once.
Constraints:
1 <= nums.length <= 2000
0 <= nums[i] <= 1000
 */

/**
 * [5, 7, 3, 7, 5, 8] Expected Output: 8
 * 5:2, 7:2, 8:1 3:1
 */
public class P02LargestUniqueNumber {
    public static int largeUniqueNumber(int[] nums){
        HashMap<Integer, Integer> freqMap = new HashMap<>();


        for (int num:nums){
            freqMap.put(num, freqMap.getOrDefault(num, 0)+1);
        }

        int max = Integer.MIN_VALUE;
        for(Map.Entry<Integer, Integer> entry: freqMap.entrySet()){
            if (entry.getValue() ==1){
                max = Math.max(max, entry.getKey());
            }

        }
        return max == Integer.MIN_VALUE ? -1 : max;
    }
    public static void main(String[] args) {

               System.out.println("===========================");
        System.out.println("P02.LargestUniqueNumber");
        System.out.println("===========================");
        int[] numsP92 = {5, 7, 3, 7, 5, 8};
        System.out.println("Input: "+ Arrays.toString(numsP92) + " => Output: "+ largeUniqueNumber(numsP92) +" ,Expected Output: 8");
        numsP92 = new int[]{1, 2, 3, 2, 1, 4, 4};
        System.out.println("Input: "+ Arrays.toString(numsP92) + " => Output: "+ largeUniqueNumber(numsP92) +" ,Expected Output: 3");
        numsP92 = new int[]{9, 9, 8, 8, 7, 7};
        System.out.println("Input: "+ Arrays.toString(numsP92) + " => Output: "+ largeUniqueNumber(numsP92) +" ,Expected Output: -1");
        numsP92 = new int[]{2, 2, 3, 4};
        System.out.println("Input: "+ Arrays.toString(numsP92) + " => Output: "+ largeUniqueNumber(numsP92) +" ,Expected Output: 4");
        numsP92 = new int[]{2, 2, 3, 3};
        System.out.println("Input: "+ Arrays.toString(numsP92) + " => Output: "+ largeUniqueNumber(numsP92) +" ,Expected Output: -1");

    }
}

