package com.grokingcodeinterview.pattern25PalindromicSubsequence;

/*
 Problem Statement
Given a string s, determine the minimum number of characters you must insert into the string so that the resulting string becomes a palindrome.
Example 1: Input: "mbadm" Output: 2
Explanation:You can make "mbadm" into a palindrome with 2 insertions, for example:
"mbdadbm", "mdabdba"
Example 2: Input: "aebcbda" Output: 2
Explanation: Longest palindromic subsequence is "abcba" â†’ length 5.
String length is 7 â†’ need 7 - 5 = 2 insertions.
Example 3: Input: "zzazz" Output: 0
Explanation: Already a palindrome â†’ no insertions needed.
 */

/**
 * Input: "mbadm" Output: 2
 *   "m b a d m"
 *   foun longest
 */
public class P05MinimumInsertionToMakeItPalindrome {

    public static int minInsertion(String str){
        if(str == null) return 0;
        // minimum insertion = string length - longest palindromic subsequence
        return str.length() - findLPS(str);
    }
    // find longest palindromic subsequence
    private static int findLPS(String str){
        if (str == null)
            return 0;
        int n = str.length();
        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        for (int startIndex = n-1; startIndex>=0; startIndex --){
            for (int endIndex = startIndex+1; endIndex<n; endIndex ++){
                if(str.charAt(startIndex) == str.charAt(endIndex)){
                    dp[startIndex][endIndex] = 2+ dp[startIndex +1][endIndex -1];
                }else{
                    dp[startIndex][endIndex] = Math.max(dp[startIndex][endIndex-1], dp[startIndex+1][endIndex ]);
                }
            }
        }
        return dp[0][n-1];
    }
    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("P05. Minimum Insertion to make it Palindrome");
        System.out.println("=====================================");
        String strP05 = "abdbca";
        System.out.println("Input: " + strP05 + " ,Output: " + minInsertion(strP05) + " Expected: 1");
        strP05 = "cddpd";
        System.out.println("Input: " + strP05 + " ,Output: " + minInsertion(strP05) + " Expected: 2");
        strP05 = "pqr";
        System.out.println("Input: " + strP05 + " ,Output: " + minInsertion(strP05) + " Expected: 2");

    }


}

