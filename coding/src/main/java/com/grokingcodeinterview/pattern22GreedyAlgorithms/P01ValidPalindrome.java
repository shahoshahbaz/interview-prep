package com.grokingcodeinterview.pattern22GreedyAlgorithms;

import static com.Utility.makeItBold;

/*
Problem Statement
Given string s, determine whether it's possible to make a given string palindrome by removing at most one character.

A palindrome is a word or phrase that reads the same backward as forward.
Example 1: Input: "racecar"
Expected Output: true
Justification: The string is already a palindrome, so no removals are needed.
Example 2: Input: "abccdba" Expected Output: true
Justification: Removing the character 'd' forms the palindrome "abccba".
Example 3: Input: "abcdef" Expected Output: false
Justification: No single character removal will make this string a palindrome.
Constraints:
1 <= s.length <= 105
str consists of lowercase English letters.
*/
public class P01ValidPalindrome {
    public static boolean isPalindromePossible(String str) {
        if (str == null || str.length() ==0) return true;
        int left = 0;
        int right = str.length() -1;
        while (left< right ){
            if (str.charAt(left) != str.charAt(right)){  // check by removing left or right
                // try removing left or right char and check palindrome for remaining
                return isPalindrome(str, left+1, right) || isPalindrome(str, left, right -1);
            }
            left++;
            right --;
        }
        return true;
    }
    public static boolean isPalindrome(String str, int left, int right){
        if (left == right ) return true;
        while (left< right){
            if (str.charAt(left) != str.charAt(right)) return false;
            left++;
            right --;

        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("P01. Valid Palindrome... ");
        System.out.println("===================================");
        String strP01 = "abccba";
        System.out.println("Input: " + strP01 +
                ", output:" + makeItBold(isPalindromePossible(strP01)+"")+ ", expected: true");
        strP01 = "racecar";
        System.out.println("Input: " + strP01 +
                ", output:" + makeItBold(isPalindromePossible(strP01)+"")+ ", expected: true");
        strP01 = "abccdba";
        System.out.println("Input: " + strP01 +
                ", output:" + makeItBold(isPalindromePossible(strP01)+"")+ ", expected: true");
        strP01 = "abcdef";
        System.out.println("Input: " + strP01 +
                ", output:" + makeItBold(isPalindromePossible(strP01)+"")+ ", expected: false");
        strP01 = "deeee";
        System.out.println("Input: " + strP01 +
                ", output:" + makeItBold(isPalindromePossible(strP01)+"")+ ", expected: true");
    }
}

