package com.grokingcodeinterview.pattern11HashMaps;
/*
Problem Statement:
Given a string, determine the length of the longest palindrome that can be constructed using the characters from the string. You don't need to return the palindrome itself, just its maximum possible length.

Input: "applepie" Expected Output: 5
Justification: The longest palindrome that can be constructed from the string is "pepep", which has a length of 5. There are are other palindromes too but they all will be of length 5.
Input: "aabbcc"Expected Output: 6
Justification: We can form the palindrome "abccba" using the characters from the string, which has a length of 6.
Input: "bananas"Expected Output: 5
Justification: The longest palindrome that can be constructed from the string is "anana", which has a length of 5.
Constraints:

1 <= s.length <= 2000
s consists of lowercase and/or uppercase English letters only.
 */

import java.util.HashMap;

import static com.Utility.makeItBold;

/**
 * "applepie" Expected Output: 5
 * a:1p:2l =1 e:2 i :1
 */
public class P04LongestPalindrome {
    public static int longestPalindrome(String str){
        HashMap<Character, Integer> freqMap = new HashMap<>();
        for (char ch: str.toCharArray()){
            freqMap.put(ch, freqMap.getOrDefault(ch, 0)+1);
        }

        int length =0;
        boolean oddFound = false;


        for (int freq: freqMap.values()){
            if (freq %2 ==0 ){
                length += freq;
            }else{
                length += freq -1;
                oddFound = true;
            }

        }

        if(oddFound) length ++;
        return length;

    }
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("P04.Longest Palindrome");
        System.out.println("=====================================");
        String str04 = "applepie";
        System.out.println("Input: "+ str04 + " => Output: "+ makeItBold(longestPalindrome(str04) +"") +" ,Expected Output: 5");
        str04 = "aabbcc";
        System.out.println("Input: "+ str04 + " => Output: "+ makeItBold(longestPalindrome(str04) +"") +" ,Expected Output: 6");
        str04 = "bananas";
        System.out.println("Input: "+ str04 + " => Output: "+ makeItBold(longestPalindrome(str04) +"") +" ,Expected Output: 5");
        str04 = "abccccdd";
        System.out.println("Input: "+ str04 + " => Output: "+ makeItBold(longestPalindrome(str04) +"") +" ,Expected Output: 7");
        str04 = "a";
        System.out.println("Input: "+ str04 + " => Output: "+ makeItBold(longestPalindrome(str04) +"") +" ,Expected Output: 1");
        str04 = "bb";
        System.out.println("Input: "+ str04 + " => Output: "+ makeItBold(longestPalindrome(str04) +"") +" ,Expected Output: 2");



    }



}

