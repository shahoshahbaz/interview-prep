package com.grokingcodeinterview.pattern19BitwiseXOR;

public class pattern20BitwiseXORR1 {
     /*
    Every non-negative integer N has a binary representation, for example, 8 can be represented as â€œ1000â€ in binary and 7 as â€œ0111â€ in binary.

The complement of a binary representation is the number in binary that we get when we change every 1 to a 0 and every 0 to a 1. For example, the binary complement of â€œ1010â€ is â€œ0101â€.

For a given positive number N in base-10, return the complement of its binary representation as a base-10 integer.

Example 1:

Input: 8
Output: 7
Explanation: 8 is 1000 in binary, its complement is 0111 in binary, which is 7 in base-10.
Example 2:

Input: 10
Output: 5
Explanation: 10 is 1010 in binary, its complement is 0101 in binary, which is 5 in base-10.
     */

    public static int  bitwiseComplement(int num){

        // find the number of bits in num
        int numberOfBits =0;
        int n = num;
        while (n> 0){
            numberOfBits ++;
            n = n>>1; //right shift it is like divede by 2
        }

        int mask = (1<< numberOfBits) -1;// what is mask for?  // left shift is like multiply by 2, what is this mask giving us?

        // example num = 8 => 1000, numberOfBits = 4 , mask = (1<<4) -1 => 10000 -1 => 1111

        return num ^ mask; // xor with mask will give us the complement 1000 ^ 1111 = 0111

    }
   /*
    Problem statement:
     You are given an array of n-1 integers
      in the range from 1 to n.
     Exactly one number from this range is missing in the array.
      Your task is to find the missing number.

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
    /**
     *  length = n-1
     *  range of elemnent is [1, n]
     *  Input: [1, 2, 4, 6, 3, 7, 8] Output: 5
     *  length = 7 so  n-1 = 7 => n = 8
     *  range is [1, 8] so
     *  first iterate from 1 to n and xor all number
     */

    public static  int findMissingNumber(int[] nums){

        int n = nums.length +1; // n-1 is length so n is length +1
        // xor all number from 1 to n
        int xorFullRange = 0;

        for (int i =1;i<= n; i++){ // n+1 because length is n-1 so n is missing number
            xorFullRange =xorFullRange ^ i;
        }

        int xorArray = 0;
        for (int num: nums){
            xorArray = xorArray ^ num;
        }

        return xorArray^xorFullRange;


    }

    /*
Problem Statement
In a non-empty array of numbers,
 every number appears exactly twice except two numbers that appear only once.
 Find the two numbers that appear only once.

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

    /**
     *   [1, 4, 2, 1, 3, 5, 6, 2, 3, 5]
     *   we can xor all numbers
     *
     */

    public static int[] findTwoSingleNumbers(int[] nums){

        int xorResult = 0;
        for (int num: nums){
            xorResult = xorResult ^ num;
        }

        // get rightmost bit
        int rightMostSetBit = xorResult  & -xorResult;

        // now create two grou
        int num1 =0;
        int num2 = 0;
        for (int num: nums){
            if ((num & rightMostSetBit) == 0){
                num1 = num1 ^num;
            }else{
                num2 = num2 ^ num;
            }
        }
        return new int[]{num1, num2};
    }

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("P01. Finding missing number in the array");
        System.out.println("=================================");

        int[] nums = {1,2,4,6,3,7,8};
        System.out.println("Missing number is: " + findMissingNumber(nums)); //5

        int[] nums2 = {1,2,3,5};
        System.out.println("Missing number is: " + findMissingNumber(nums2)); //4

        System.out.println("=================================");
        System.out.println("P03. Finding two Single number in the array");
        System.out.println("=================================");

        int[] arr = new int[] { 1, 4, 2, 1, 3, 5, 6, 2, 3, 5 };
        int[] result = findTwoSingleNumbers(arr);
        System.out.println("Single numbers are: " + result[0] + ", " + result[1]); // expected 4,6

        arr = new int[] { 2, 1, 3, 2 };
        result = findTwoSingleNumbers(arr);
        System.out.println("Single numbers are: " + result[0] + ", " + result[1]);


    }
}

