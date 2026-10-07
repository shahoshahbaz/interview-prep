package com.grokingcodeinterview.pattern19BitwiseXOR;

import static com.Utility.makeItBold;

public class P03ComplementOfBase10Number {


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
        if (num ==0) return 1; // edge case
        int numberOfBits =0;
        int n = num;
        while (n >0) {
            numberOfBits++;
            n = n >> 1; // right shift by 1
        }
        // create a mask with all bits set to 1 of length numberOfBits
        int mask = (1 << numberOfBits) -1; // left shift 1 by numberOfBits and subtract 1
        // now xor num with mask to get the complement
        return num ^ mask;



    }
    public static void main(String[] args) {

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

