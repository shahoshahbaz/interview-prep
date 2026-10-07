package com.grokingcodeinterview.pattern19BitwiseXOR;

import java.util.Arrays;

import static com.Utility.makeItBold;

public class Pattern20BitwiseXORR2 {

    /*
    Every non-negative integer N has a binary representation, for example,
     8 can be represented as â€œ1000â€ in binary and 7 as â€œ0111â€ in binary.

The complement of a binary representation is the number in binary that
we get when we change every 1 to a 0 and every 0 to a 1.
 For example, the binary complement of â€œ1010â€ is â€œ0101â€.

For a given positive number N in base-10,
return the complement of its binary representation as a base-10 integer.

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

         if (num == 0) return 1;
         int numberOfBits = 0;
         int n = num;
         // count number of bits in num
         while(n>0){
             numberOfBits++;
             n = n>>1;  // right shift by 1 which is equivalent to dividing by 2
         }
         // example num = 8 then numberOfBits = 4
         int mask = (1<< numberOfBits) -1;
         //  (1<< numberOfBits) : left shift 1 by numberOfBits
        // which will give us a number with only one bit set at position numberOfBits
        //example if number of bits = 4 then (1<< 4) = 10000 in binary which is 16 in decimal
        //  1<< numberOfBits) -1 whill give us all bits set to 1 till numberOfBits
        // example if number of bits = 4 then (1<< 4) -1 = 1111 in binary which is 15 in decimal


         return num^ mask; // now xor num with mask to get the complement


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
     * Input: [1, 4, 2, 1, 3, 5, 6, 2, 3, 5]      * Output: [4, 6]
     * xor all the number
     * result will be xor of two single number
     * find the right most bit and group the number based on that bit
     *
     * @param nums
     * @return
     */

    public static int[] findTwoSingleNumbers(int[] nums){


        if (nums.length ==1) return new int[]{-1, -1};
        int xorResult = 0;
        for (int num: nums){
            xorResult = xorResult ^ num;
        }
        int num1 = 0;
        int num2 = 0;
        int rightmostBit = xorResult & -xorResult ;

        for (int num: nums){
            if ((rightmostBit & num) != 0){
                num1 = num1 ^ num;
            }else{
                num2 = num2 ^ num;
            }
        }

        return new int[]{num1, num2};

    }

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

    public static int findSingleNumber(int[] nums){

        int xorResult = 0;
        for (int num: nums){
            xorResult = xorResult ^num;
        }
        return xorResult;
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
     *  range of number is 1 to n
     *
     * @param nums
     * @return
     */

    public static int findMissingNumber(int[] nums){

        int length = nums.length; // n-1 = length=> n= length +1;
        int n = length+1;
        int xorResult=0;
        for (int i = 1; i<= n; i++){
            xorResult = xorResult ^ i;
        }

        for (int num:nums){
            xorResult = xorResult ^num;
        }
        return xorResult;
    }
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("P01. Finding missing number in the array");
        System.out.println("=================================");

        int[] nums = {1,2,4,6,3,7,8};
        System.out.print("Original Array: " + Arrays.toString(nums)+ "\t");
        System.out.println("Missing number is: " + findMissingNumber(nums) + ", Expected output is: 5");

        int[] nums2 = {1,2,3,5};
        System.out.print("Original Array: " + Arrays.toString(nums2)+ "\t");
        System.out.println("Missing number is: " + findMissingNumber(nums2)+ ", Expected output is: 4 ");

        System.out.println("=================================");
        System.out.println("P02. Finding single number in the array");
        System.out.println("=================================");

        // some exampleS
        int[] arr = new int[] { 1, 4, 2, 1, 3, 2, 3 };
        int result = findSingleNumber(arr);
        System.out.println("Single number is: " + result); //4
        arr = new int[] { 7, 9, 7 };
        result = findSingleNumber(arr);
        System.out.println("Single number is: " + result); //9


        System.out.println("=================================");
        System.out.println("P03. Finding two Single number in the array");
        System.out.println("=================================");

        int[] arrP03 = new int[] { 1, 4, 2, 1, 3, 5, 6, 2, 3, 5 };
        int[] resultP03 = findTwoSingleNumbers(arrP03);
        System.out.println("Single numbers are: " + resultP03[0] + ", " + resultP03[1]); // expected 4,6

        arrP03 = new int[] { 2, 1, 3, 2 };
        resultP03 = findTwoSingleNumbers(arrP03);
        System.out.println("Single numbers are: " + resultP03[0] + ", " + resultP03[1]);

        System.out.println("++++++++++++++++++++++++++++");
        System.out.println("P03 Complement Of Base 10 Number");
        System.out.println("++++++++++++++++++++++++++++");
        int numsP03 =5; // binary 101, complement is 010 which is 2
        int complement = bitwiseComplement(numsP03);
        System.out.println("Complement of " + makeItBold(""+numsP03) + " is: " +
                makeItBold(""+complement) + "Expected value: " + makeItBold("2"));

        numsP03 =1; // binary 1, complement is 0
        complement = bitwiseComplement(numsP03);
        System.out.println("Complement of " + makeItBold(""+numsP03) + " is: " +
                makeItBold(""+complement) + "Expected value: " + makeItBold("0"));

        numsP03 =0; // edge case, complement is 1
        complement = bitwiseComplement(numsP03);
        System.out.println("Complement of " + makeItBold(""+numsP03) + " is: " +
                makeItBold(""+complement) + "Expected value: " + makeItBold("1"));

        numsP03 =10; // binary 1010, complement is 0101 which is 5
        complement = bitwiseComplement(numsP03);
        System.out.println("Complement of " + makeItBold(""+numsP03) + " is: " +
                makeItBold(""+complement) + "Expected value: " + makeItBold("5"));



    }
}

