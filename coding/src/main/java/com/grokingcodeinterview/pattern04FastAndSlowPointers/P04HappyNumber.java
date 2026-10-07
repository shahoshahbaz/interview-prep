package com.grokingcodeinterview.pattern04FastAndSlowPointers;
/*
Any number will be called a happy number if, after repeatedly replacing it with a number equal to the sum of the square of all of its digits,
 leads us to the number 1.
 All other (not-happy) numbers will never reach 1. Instead, they will be stuck in a cycle of numbers that does not include 1
 .
Given a positive number n, return true if it is a happy number otherwise return false.

Example 1:

Input: 23
Output: true (23 is a happy number)
Explanations: Here are the steps to find out that 23 is a happy number:

 = 4 + 9 = 13
 = 1 + 9 = 10
 = 1 + 0 = 1

Example 2:

Input: 12
Output: false (12 is not a happy number)
Explanations: Here are the steps to find out that 12 is not a happy number:

 = 1 + 4 = 5
= 25
 = 4 + 25 = 29
 = 4 + 81 = 85
 = 64 + 25 = 89
 = 64 + 81 = 145
 = 1 + 16 + 25 = 42
 = 16 + 4 = 20
 = 4 + 0 = 4
 = 16
 = 1 + 36 = 37
 = 9 + 49 = 58
 = 25 + 64 = 89

Please note the cycle from 89 -> 89.

Constraints:

1 <= n <= 231 - 1
 */

/**
 * 23 /10 = 2  and remainder = 3 2/1=
 */
public class P04HappyNumber {

    public  static boolean isHappyNumber(int num){
// this wrong approach will lead to infinite loop for non happy number
//        int slow = num;
//        int fast = num;
        int slow = squareOfDigits(num);
        int fast = squareOfDigits(squareOfDigits(num));

        while(true){ // we can also use while(true) with break condition inside insted of fast != 1 && slow !=1 && slow!= fast
            slow = squareOfDigits(slow);
            fast = squareOfDigits(squareOfDigits(fast));
            if (slow ==1 || fast == 1) return true;
            if (slow == fast) return false;
        }


    }

    public  static int squareOfDigits(int num){
        int sum =0;
        while (num> 0){
            int remainder = num % 10;
            sum += (remainder * remainder);
            num =num/ 10;
        }
        return sum;
    }

    public static void main(String[] args) {
        // some tests can be added here

        int num1 = 23;
        boolean result1 = isHappyNumber(num1);
        System.out.println("Is " + num1 + " a happy number? " + result1); // Expected output: true
        int num2 = 12;
        boolean result2 = isHappyNumber(num2);
        System.out.println("Is " + num2 + " a happy number? " + result2); // Expected output: false
        // some more test cases
        int num3 = 19;
        boolean result3 = isHappyNumber(num3);
        System.out.println("Is " + num3 + " a happy number? " + result3); // Expected output: true
        int num4 = 4;
        boolean result4 = isHappyNumber(num4);
        System.out.println("Is " + num4 + " a happy number? " + result4); // Expected output: false
    }
}

