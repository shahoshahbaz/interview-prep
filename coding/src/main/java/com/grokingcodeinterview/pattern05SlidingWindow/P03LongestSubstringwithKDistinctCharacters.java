package com.grokingcodeinterview.pattern05SlidingWindow;

import java.util.HashMap;

import static com.Utility.makeItBold;

/*
 Problem Statement #
 Given a string, find the length of the longest substring in it  with no more than K distinct characters.
  You can assume that K is less than or equal to the length of the given string.
 Example 1:   Input: String="araaci", K=2  Output: 4
 Explanation: The longest substring with no more than '2' distinct characters is "araa".

 Example 2:  Input: String="araaci", K=1  Output: 2
 Explanation: The longest substring with no more than '1' distinct characters is "aa".

 Example 3:  Input: String="cbbebi", K=3  Output: 5
 Explanation: The longest substrings with no more than '3' distinct characters are "cbbeb" & "bbebi".
 */

/**
 * longest == max length
 * no more than k distinct chars
 * String="araaci", K=2
 * Output: 4
 * * Explanation: The longest substring with no more than '2' distinct characters is "araa".
 * ws =0
 * se = 0 .. s.length
 * no more than meand <=k
 */
public class P03LongestSubstringwithKDistinctCharacters {

    public static int findLongestSubstringWithNoMoreThanKDistinctChars(String str, int k) {
        if (str == null || str.isEmpty() || k <= 0)
            return 0;

        int longest = Integer.MIN_VALUE;
        int windowStart = 0;
        HashMap<Character, Integer> freq = new HashMap<>();


        for (int windowEnd = 0; windowEnd < str.length(); windowEnd++) {
            char ch = str.charAt(windowEnd);
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);

            if (freq.size() <= k) {
                longest = Math.max(longest, windowEnd - windowStart + 1);
            }
            while (freq.size() > k) {
                char startChar = str.charAt(windowStart);
                // remove the start char
                freq.put(startChar, freq.get(startChar) - 1);
                if (freq.get(startChar) == 0) freq.remove(startChar);
                windowStart++;


            }
        }
        return longest;
    }

    public static void main(String[] args) {
        System.out.println("================================================");
        System.out.println("P03. Longest Substring with K Distinct Characters");
        System.out.println("================================================");
        // Test Case 1: Example from the problem statement
        String strP03 = "araaci";
        int kP03 = 2;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("4"));

        // Test Case 2: Example from the problem statement
        strP03 = "araaci";
        kP03 = 1;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("2"));

        // Test Case 3: Example from the problem statement
        strP03 = "cbbebi";
        kP03 = 3;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("5"));

        // Test Case 4: Single character string
        strP03 = "a";
        kP03 = 1;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("1"));

        // Test Case 5: Empty string
        strP03 = "";
        kP03 = 2;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("0"));

        // Test Case 6: k equals string length
        strP03 = "abcde";
        kP03 = 5;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("5"));

        // Test Case 7: k greater than string length
        strP03 = "abc";
        kP03 = 5;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("3"));

        // Test Case 8: String with all identical characters
        strP03 = "aaaaa";
        kP03 = 1;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("5"));

        // Test Case 9: k = 0 (edge case)
        strP03 = "abc";
        kP03 = 0;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("0"));

        // Test Case 10: String with exactly k distinct characters
        strP03 = "abcba";
        kP03 = 3;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("5"));
    }
}
