package com.grokingcodeinterview.pattern19BitwiseXOR;

import java.util.Arrays;

import static com.Utility.makeItBold;

public class Pattern20BitwiseXORR3 {

/*
 Every non-negative integer N has a binary representation, for example, 8 can be represented as â€œ1000â€ in binary and 7 as â€œ0111â€ in binary.
The complement of a binary representation is the number in binary that we get when we change every 1 to a 0 and every 0 to a 1. For example, the binary complement of â€œ1010â€ is â€œ0101â€.
For a given positive number N in base-10, return the complement of its binary representation as a base-10 integer.

Example 1: Input: 8 Output: 7
Explanation: 8 is 1000 in binary, its complement is 0111 in binary, which is 7 in base-10.
Example 2: Input: 10 Output: 5
Explanation: 10 is 1010 in binary, its complement is 0101 in binary, which is 5 in base-10.
*/

    /**
     * 8 =1000 output = 7 0111
     * countbits = 4
     * a mask = 1<<4 = 10000 -1 = 01111
     *
     */

    public static int  bitwiseComplement(int num){

        if (num == 0) return 1;
        int number = num;
        int numberOfBits =0;
        while (number> 0){
            numberOfBits++;
            number = number>>1; //1000 : 1000>1 100

        }

        int mask = (1<<numberOfBits) -1 ; //1<<4: 10000 -1 = 01111

        return num^ mask;  //1000 ^01111
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
     *
     * Input: [1, 4, 2, 1, 3, 5, 6, 2, 3, 5]      * Output: [4, 6]
     * xor all number, so what left is result of xor of two unknow number
     * find rightmostSetBit
     * now do the partition by
     */
       public static int[] findTwoSingleNumbers(int[] nums){

           int xorResult = 0;
           for (int num: nums){
               xorResult = xorResult ^ num;
           }

           int rightMostSetBit = xorResult & -xorResult;

           int num1 =0;
           int num2 =0;

           for (int num: nums){
               if ((rightMostSetBit &num) !=0){
                   num1 = num1 ^ num;
               }else{
                   num2 = num2 ^ num;
               }
           }

           return new int[]{num1, num2};
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

    public static int findMissingNumber(int[] nums){
    // length of array is n-1;
    int length = nums.length;
    int n = length +1;

    int xorResult=0;

    for (int i =1; i<= n; i++){
     xorResult = xorResult ^i;
    }

    int xorNumbers = 0;
    for (int num: nums){
        xorNumbers = xorNumbers ^ num;
    }

    return xorNumbers ^ xorNumbers;
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
        System.out.println("P04. Finding two single numbers in the array");
        System.out.println("=================================");
        int[] numsp04 = new int[] { 1, 4, 2, 1, 3, 5, 6, 2, 3, 5 };
        int[] resultP04 = findTwoSingleNumbers(numsp04);
        System.out.println( "Input: " + makeItBold(Arrays.toString(numsp04)) + " single numbers are: " + makeItBold("[" +resultP04[0] + ", " + resultP04[1]+"]")  + " Expected Output: [4, 6]");



        numsp04 = new int[] { 2, 1, 3, 2 };
        resultP04 = findTwoSingleNumbers(numsp04);

        System.out.println( "Input: " + makeItBold(Arrays.toString(numsp04)) + " single numbers are: " + makeItBold("["+resultP04[0] + ", " + resultP04[1]+"]")  + " Expected Output: [3, 1]");

        System.out.println("=================================");
        System.out.println("P03 Complement Of Base 10 Number");
        System.out.println("=================================");
        int numsP03 =5; // binary 101, complement is 010 which is 2
        int complement = bitwiseComplement(numsP03);
        System.out.println("Complement of " + makeItBold(""+numsP03) + " is: "+
                makeItBold(""+complement) + " Expected value: " + makeItBold("2"));

        numsP03 =1; // binary 1, complement is 0
        complement = bitwiseComplement(numsP03);
        System.out.println("Complement of " + makeItBold(""+numsP03) + " is: " +
                makeItBold(""+complement) + " Expected value: " + makeItBold("0"));

        numsP03 =0; // edge case, complement is 1
        complement = bitwiseComplement(numsP03);
        System.out.println("Complement of " + makeItBold(""+numsP03) + " is: " +
                makeItBold(""+complement) + " Expected value: " + makeItBold("1"));

        numsP03 =10; // binary 1010, complement is 0101 which is 5
        complement = bitwiseComplement(numsP03);
        System.out.println("Complement of " + makeItBold(""+numsP03) + " is: " +
                makeItBold(""+complement) + " Expected value: " + makeItBold("5"));

        numsP03 =8; // binary 1000, complement is 0111 which is 7
        complement = bitwiseComplement(numsP03);
        System.out.println("Complement of " + makeItBold(""+numsP03) + " is: " +
                makeItBold(""+complement) + " Expected value: " + makeItBold("7"));





    }
}

