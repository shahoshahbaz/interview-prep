package com.grokingcodeinterview.pattern19BitwiseXOR;

/*
    Problem statement: You are given an array of n-1 integers in the range from 1 to n.
     Exactly one number from this range is missing in the array. Your task is to find the missing number.

Constraints
1 <= n <= 10,000
The array contains distinct integers. The numbers in the array are in the range [1, n].
Array length = n - 1.
Examples
Example 1: Input: [1, 2, 4, 6, 3, 7, 8] Output: 5
Explanation: Numbers 1 to 8 are expected. 5 is missing.
Example 2: Input: [1, 2, 3, 5] Output: 4
Explanation: Numbers 1 to 5 are expected. 4 is missing.
 */

import java.util.Arrays;

/**
 * This senctece â€œAn array of n-1 integers in the range from 1 to nâ€ breadk down
 * â€œin the range of 1 to nâ€ means numbers should be between 1, 2, ..,  n. ex: n = 5 then full set is [1, 2,3, 4, 5]
 * â€œan array of n-1 integersâ€ means array length is n-1
 */
public class P01FindMissingNumber {

    public static int findMissingNumber(int[] nums){
        // size of array is n -1
        // number should be 1, ...n
        int n = nums.length + 1; // include the missing number
        int x1 = 0;
        // calculate xor of all numbers from 1 to n
        for (int i =1;i<= nums.length +1; i++){ // why n not n-1 because n is the missing number
            x1 = x1 ^i;
        }

        int x2 = 0;
        for (int i =0; i<nums.length; i++){ //n-1 because nums length is n-1
            x2 = x2 ^ nums[i];
        }
        return x1 ^x2; // why this works because same numbers will cancel out leaving only the missing number
    }

    public static void main(String[] args) {

        int[] nums = {1,2,4,6,3,7,8};
        System.out.print("Original Array: " + Arrays.toString(nums)+ "\t");
        System.out.println("Missing number is: " + findMissingNumber(nums) + ", Expected output is: 5");

        int[] nums2 = {1,2,3,5};
        System.out.print("Original Array: " + Arrays.toString(nums2)+ "\t");
        System.out.println("Missing number is: " + findMissingNumber(nums2)+ ", Expected output is: 4 ");

    }


}

