package com.grokingcodeinterview.pattern25PalindromicSubsequence;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a string, find the total number of palindromic substrings in it.
Please note we need to find the total number of substrings and not subsequences.
Example 1:
Input: "abdbca" Output: 7
Explanation: Here are the palindromic substrings, "a", "b", "d", "b", "c", "a", "bdb".
Example 2: Input: = "cddpd" Output: 7
Explanation: Here are the palindromic substrings, "c", "d", "d", "p", "d", "dd", "dpd".
Example 3: Input: = "pqr" Output: 3
Explanation: Here are the palindromic substrings,"p", "q", "r".
Constraints:
1 <= st.length <= 1000
st consists only of lowercase English letters.
 */
public class P03CountOfPalindromicSubstrings {

    public static int countPalindromicSubstrings(String str) {
        if (str == null) return 0;
        int n = str.length();
        boolean[][] dp = new boolean[n][n];

        int counter = 0;
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
            counter++;
        }

        for (int startIndex =n-1; startIndex>=0; startIndex --) {
            for (int endIndex = startIndex + 1; endIndex < n; endIndex++) {
                if (str.charAt(startIndex) == str.charAt(endIndex)) {
                    if (endIndex - startIndex == 1 || dp[startIndex + 1][endIndex - 1]) {
                        dp[startIndex][endIndex] = true;
                        counter++;
                    }
                }
            }
        }

         return counter;
    }

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("P03. Count of Palindromic Substrings");
        System.out.println("=====================================");

        String strP03 = "abdbca";
        System.out.println("Input: " + strP03 + " ,Output: " + makeItBold(countPalindromicSubstrings(strP03) +"") + " Expected: 7");

        strP03 = "cddpd";
        System.out.println("Input: " + strP03 + " ,Output: " +  makeItBold(countPalindromicSubstrings(strP03) +"") + " Expected: 7");

        strP03 = "pqr";
        System.out.println("Input: " + strP03 + " ,Output: " + makeItBold(countPalindromicSubstrings(strP03) +"")+ " Expected: 3");
     }
    }


