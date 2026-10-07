package com.grokingcodeinterview.pattern22GreedyAlgorithms;
/*
Given a string s containing 0 to 9 digits, create the largest possible palindromic number using the string characters. It should not contain leading zeroes.
A palindromic number reads the same backward as forward.

If it's not possible to form such a number using all digits of the given string, you can skip some of them.

Examples
Example 1 Input: s = "323211444" Expected Output: "432141234"
Justification: This is the largest palindromic number that can be formed from the given digits.
Example 2 Input: s = "998877" Expected Output: "987789"
Justification: "987789" is the largest palindrome that can be formed.
Example 3  Input: s = "54321" Expected Output: "5"
Justification: Only "5" can form a valid palindromic number as other digits cannot be paired.
Constraints:

1 <= num.length <= 10^55
num consists of digits.
 */

import static com.Utility.makeItBold;

/**
 * largest possible Palindorme number
 * run-dry example:
 * Input: 323211444 freq = [0,2,2,2,3,0,0,0,0,0]
 * // find cneter digit: 
 *
 *
 *
 */
public class P05LargestPalindromicNumber {


    public static String largestPalindromic(String str){
        int[] freq = new int[10];

        // find the frequency of each number;
        for (char c: str.toCharArray()){
            freq[c -'0'] ++;
        }


        StringBuilder left = new StringBuilder();
        for (int d = 9; d>=0; d--){
            int counter = freq[d]/2;

            if (d ==0 && left.length() == 0) continue;

            for (int i =0; i< counter; i++){
                left.append(d);
            }
            freq[d] -=  counter *2;

        }

        // pick cneter
        String center = "";
        for (int d = 9; d>=0; d--){
            if (freq[d]>0){
                center = String.valueOf(d);
                break;
            }
        }

        // if nothing formed, return max sigle digits
        if (left.isEmpty() && center.isEmpty()){
            for (int d =9; d>=0; d--){
                if (freq[d]>0 ) return String.valueOf(d);
            }
            return "0";
        }

        String right = left.reverse().toString();
        left.reverse();
        return left + center + right;

    }
    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("P05. Largest Palindromic Number... ");
        System.out.println("===================================");
        String strP05 = "323211444";
        System.out.println("Input: " + strP05 +
                ", output:" + makeItBold(largestPalindromic(strP05))+ ", expected: 432141234");
        strP05 = "998877";
        System.out.println("Input: " + strP05 +
                ", output:" + makeItBold(largestPalindromic(strP05))+ ", expected: 987789");
        strP05 = "54321";
        System.out.println("Input: " + strP05 +
                ", output:" + makeItBold(largestPalindromic(strP05))+ ", expected: 5");
        strP05 = "0000";
        System.out.println("Input: " + strP05 +
                ", output:" + makeItBold(largestPalindromic(strP05))+ ", expected: 0");
        strP05 = "0001100";
        System.out.println("Input: " + strP05 +
                ", output:" + makeItBold(largestPalindromic(strP05))+ ", expected: 1000001");
        strP05 = "99999881100";
        System.out.println("Input: " + strP05 +
                ", output:" + makeItBold(largestPalindromic(strP05))+ ", expected: 99810901899");


    }
}

