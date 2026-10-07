package com.grokingcodeinterview.pattern19BitwiseXOR;

public class p02SingleNumber {
    /*
    In a non-empty array of integers,
     every number appears twice except for one, find that single number.

Example 1:

Input: 1, 4, 2, 1, 3, 2, 3
Output: 4
Example 2:

Input: 7, 9, 7
Output: 9
     */

    public static int findSingleNumber(int[] nums) {

        int xorArray = 0;
        for (int num: nums){
            xorArray = xorArray ^ num;
        }

        return xorArray;
        
    }

    public static void main(String[] args) {
        // some exampleS
        int[] arr = new int[] { 1, 4, 2, 1, 3, 2, 3 };
        int result = findSingleNumber(arr);
        System.out.println("Single number is: " + result); //4
        arr = new int[] { 7, 9, 7 };
        result = findSingleNumber(arr);
        System.out.println("Single number is: " + result); //9

    }
}

