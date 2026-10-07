package com.grokingcodeinterview.pattern19BitwiseXOR;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Problem Statement
In a non-empty array of numbers, every number appears exactly twice except two numbers that appear only once. Find the two numbers that appear only once.

Example 1:

Input: [1, 4, 2, 1, 3, 5, 6, 2, 3, 5]
Output: [4, 6]
Example 2:

Input: [2, 1, 3, 2]
Output: [1, 3]
Constraints:

1 <= nums.length <= 3 * 104
-3 * 104 <= nums[i] <= 3 * 104
Each elem
 */
public class P04TwoSingleNumbers {
    public static int[] findTwoSingleNumbers(int[] nums) {

        int xorResult = 0;
        // get the xor of all numbers
        for (int num: nums){
            xorResult = xorResult ^ num;
        }

        // get the rightmost set bit, how to get rightmost set bit? xorResult & -xorResult
        int rightmostSetBit = xorResult & -xorResult; // why we need rightmost set bit? because we can use this bit to partition the numbers into two groups

        // now divide numbers in the array into two groups based on the rightmost set bit (partitioning)
        int num1 =0;
        int num2 =0;
        for (int num: nums){
            // by  doing this and operation, we can check if the bit is set to 1 or 0, if set to 1, it means the number belongs to one group, else belongs to another group

            if ((num & rightmostSetBit) !=0){ // check if the num in the array's bit is set to 1 // see the basic operation example
                num1 = num1 ^ num; // so if bit is set to 1, xor it to num1, num1 will have one single number
                // the xor operation will cancel out the duplicate numbers, so only the single number will be left
            } else {
                num2 = num2 ^ num; // else xor it to num2 , num2 will have the other single number

            }
        }

        return new int[] {num1, num2};
    }
    public static void main(String[] args) {
        // some example
        int[] numsp04 = new int[] { 1, 4, 2, 1, 3, 5, 6, 2, 3, 5 };
        int[] resultP04 = findTwoSingleNumbers(numsp04);
        System.out.println( "Input: " + makeItBold(Arrays.toString(numsp04)) + " single numbers are: " + makeItBold("[" +resultP04[0] + ", " + resultP04[1]+"]")  + " Expected Output: [4, 6]");



        numsp04 = new int[] { 2, 1, 3, 2 };
        resultP04 = findTwoSingleNumbers(numsp04);

        System.out.println( "Input: " + makeItBold(Arrays.toString(numsp04)) + " single numbers are: " + makeItBold("["+resultP04[0] + ", " + resultP04[1]+"]")  + " Expected Output: [3, 1]");



    }
}

