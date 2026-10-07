package com.grokingcodeinterview.pattern05SlidingWindow;

import java.util.HashMap;

/*
 * Problem Statement
 * Given a string and a pattern,
 *  find out if the string contains any permutation of the pattern.
 *
 * Permutation is defined as the re-arranging of the characters of the string. For example, “abc” has the following six permutations:
 *
 * abc
 * acb
 * bac
 * bca
 * cab
 * cba
 * If a string has ‘n’ distinct characters, it will have n! permutations.
 *
 * Example 1:
 *
 * Input: str="oidbcaf", pattern="abc"
 * Output: true
 * Explanation: The string contains "bca" which is a permutation of the given pattern.
 * Example 2:
 *
 * Input: str="odicf", pattern="dc"
 * Output: false
 * Explanation: No permutation of the pattern is present in the given string as a substring.
 * Example 3:
 *
 * Input: str="bcdxabcdy", pattern="bcdyabcdx"
 * Output: true
 * Explanation: Both the string and the pattern are a permutation of each other.
 * Example 4:
 *
 * Input: str="aaacb", pattern="abc"
 * Output: true
 * Explanation:
 */
public class P07PermutationInAString {
    public static boolean hasPermutation(String str, String pattern){
        if (str.length() < pattern.length()) return false;
        if (pattern.length() == 0) return false;

        int windowStart =0;
        int matched =0;
        HashMap<Character, Integer> patternFreq = new HashMap<>();

        for (char c: pattern.toCharArray()){
            patternFreq.put(c, patternFreq.getOrDefault(c,0) +1);
        }


        // Expanding window
        for (int windowEnd =0; windowEnd < str.length(); windowEnd ++){
            char rightChar = str.charAt(windowEnd);
            if (patternFreq.containsKey(rightChar)){
                patternFreq.put(rightChar, patternFreq.get(rightChar) -1);
                if (patternFreq.get(rightChar) == 0)
                    matched++;
            }



            if (windowEnd - windowStart +1 > pattern.length()){
                char leftChar = str.charAt(windowStart);
                if (patternFreq.containsKey(leftChar)){
                    if (patternFreq.get(leftChar) == 0)
                        matched--;
                    patternFreq.put(leftChar, patternFreq.get(leftChar) + 1);
                }
                windowStart ++;
            }
            if (matched == patternFreq.size()){
                return true;
            }

        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(hasPermutation("oidbcaf", "abc"));      // true
        System.out.println(hasPermutation("odicf", "dc"));         // false
        System.out.println(hasPermutation("bcdxabcdy", "bcdyabcdx")); // true
        System.out.println(hasPermutation("aaacb", "abc"));        // true
        System.out.println(hasPermutation("a", "a"));              // true
        System.out.println(hasPermutation("a", "b"));              // false
        System.out.println(hasPermutation("", "a"));               // false
        System.out.println(hasPermutation("abc", ""));             // true (empty pattern)
        System.out.println(hasPermutation("abc", "abcd"));         // false
        System.out.println(hasPermutation("abcabc", "cab"));       // true
    }
}
