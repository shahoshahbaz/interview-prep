package com.grokingcodeinterview.pattern19BitwiseXOR;

public class P00BasicOperation {

    public static void main(String[] args) {
     // and opeation
     // lest's have some examples of and operation
        // what does and operation do?
        // it compares each bit of two numbers and returns 1 if both bits are 1
        // it is used to mask bits, meaning to extract specific bits from a number


        System.out.println("=================================");
        System.out.println("And Operation Examples:");
        System.out.println("=================================");
        int b = 6; // 0110
        int bit1 = 1; // 0001
        System.out.println("and operation of 6 & 1: " + (b & bit1)); // 0000 = 0
        int bit2 = 2; // 0010
        System.out.println("and operation of 6 & 2: " + (b & bit2)); // 0010 = 2
        int bit3 = 4; // 0100
        System.out.println("and operation of 6 & 4: " + (b & bit3)); // 0100 = 4
        int bit4 = 8; // 0101
        System.out.println("and operation of 6 & 8: " + (b & bit4)); // 0000 = 0

        System.out.println("=================================");
        System.out.println("OR Operation Examples:");
        System.out.println("=================================");
        // or operation
        // let's have some examples of or operation
        // what does or operation do?
        // it compares each bit of two numbers and returns 1 if at least one bit is
        // it is used to set bits, meaning to turn on specific bits in a number
        int c = 5; // 0101
        int bit5 = 1; // 0001
        System.out.println("or operation of 5 | 1: " + (c | bit5)); // 0101 = 5
        int bit6 = 2; //
        System.out.println("or operation of 5 | 2: " + (c | bit6)); // 0111 = 7
        int bit7 = 4; // 0100
        System.out.println("or operation of 5 | 4: " + (c | bit7)); // 0101 = 5
        int bit8 = 8; // 1000
        System.out.println("or operation of 5 | 8: " + (c | bit8)); // 1101 = 13

        // xor operation
        // let's have some examples of xor operation
        // what does xor operation do?
        // it compares each bit of two numbers and returns 1 if the bits are different
        // it is used to toggle bits, meaning to flip specific bits in a number
        System.out.println("=================================");
        System.out.println("XOR Operation Examples:");
        System.out.println("=================================");
        int d = 9; // 1001
        int bit9 = 1; // 0001
        System.out.println("xor operation of 9 ^ 1: " + Integer.toBinaryString(d) +"^"+ Integer.toBinaryString(bit9) +" is: " + (d ^ bit9)); // 1000 = 8
        int bit10 = 2; // 0010
        System.out.println("xor operation of 9 ^ 2: " + Integer.toBinaryString(d) +"^"+ Integer.toBinaryString(bit10) +" is: " + (d ^ bit10)); // 1011 = 11
        int bit11 = 4; // 0100
        System.out.println("xor operation of 9 ^ 4: " + Integer.toBinaryString(d) +"^"+ Integer.toBinaryString(bit11) +" is: " + (d ^ bit11)); // 1101 = 13
        int bit12 = 8; // 1000
        System.out.println("xor operation of 9 ^ 8: " + Integer.toBinaryString(d) +"^"+ Integer.toBinaryString(bit12) +" is: " + (d ^ bit12)); // 0001 = 1

        System.out.println("xor operation of 9 ^ 9: " + Integer.toBinaryString(d) +"^"+ Integer.toBinaryString(d) +" is: " + (d ^ d)); // 0000 = 0


        // how to get binary of number in java?
        System.out.println("Binary of 9 is: " + Integer.toBinaryString(d));


        // example of NOT operation
        // continue from here
        //.....
        System.out.println("=================================");
        System.out.println("Find the rightmost set bit:");
        System.out.println("=================================");
        int num = 12; // 1100
        int rightmostSetBit = num & -num; // two's complement // -num is two's complement of num
        System.out.println("Rightmost set bit of 12 is: " + rightmostSetBit); // 0100 = 4
        // why this works?  because in two's complement, -num is obtained by inverting all bits of num and adding 1
        // so when we do num & -num, all bits except the rightmost set bit

        // check if rightmostBit is set to zero or one

//        boolean isRightmostBitSet = (num);










    }
}

