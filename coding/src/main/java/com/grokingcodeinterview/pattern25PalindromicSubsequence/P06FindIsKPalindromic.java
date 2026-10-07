package com.grokingcodeinterview.pattern25PalindromicSubsequence;

import static com.Utility.makeItBold;

/*
Problem Statement
You are given a string s and an integer k.
Determine whether the string can be transformed into a palindrome by performing at most k deletions.
Return true if it is possible false otherwise
Example 1 :Input: s = "abcdeca", k = 2  Output: true
Explanation: Delete 'b' and 'e' â†’ "acdca" which is a palindrome.
Example 2: Input: s = "acdcb", k = 1 â€” Output: false
Explanation: Needs 2 deletions ("acdcb" â†’ "cdc"), but k = 1 is not enough.
Example 3: Input: s = "abcba", k = 0 â€” Output: true
Explanation: Already a palindrome, needs 0 deletions.
Example 4: Input: s = "abcd", k = 3 â€” Output: true
Explanation: Deleting 'a','b','c' leaves "d", which is a palindrome.
Example 5: Input: s = "leetcode", k = 2 â€” Output: false
Explanation: Requires 5 deletions, but only 2 allowed.
 */
public class P06FindIsKPalindromic {
    public static boolean isKPalindromic(String str, int k) {
        if (str == null) return false;
        if (str.isEmpty()|| str.length() ==1) return true;

        return (str.length() -findLPS(str))<= k;


    }

    private static int findLPS(String str) {
        if (str == null) return 0;

        int n = str.length();
        int[][] dp = new int[n][n];

        for (int i =0; i< n; i++)
            dp[i][i] = 1;

        for(int startIndex = n-1; startIndex>=0; startIndex--) {
            for (int endIndex = startIndex + 1; endIndex < n; endIndex++) {
                if (str.charAt(startIndex) == str.charAt(endIndex)) {
                    dp[startIndex][endIndex] = 2 + dp[startIndex + 1][endIndex - 1];
                } else {
                    dp[startIndex][endIndex] = Math.max(dp[startIndex + 1][endIndex], dp[startIndex][endIndex - 1]);
                }
            }
        }
        return dp[0][n -1];


    }
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("P06. Is K Palindromic");
        System.out.println("=====================================");

        String strP06 = "abcdeca";
        int kP06 = 2;
        System.out.println("Input: " + strP06 + " , k = " + kP06 + " ,Output: " + makeItBold(isKPalindromic(strP06, kP06) +"") + " Expected: true");

        strP06 = "abcdeca";
        kP06 = 1;
        System.out.println("Input: " + strP06 + " , k = " + kP06 + " ,Output: " +  makeItBold(isKPalindromic(strP06, kP06) +"") + " Expected: false");

        strP06 = "acdcbca";
        kP06 = 2;
        System.out.println("Input: " + strP06 + " , k = " + kP06 + " ,Output: " + makeItBold(isKPalindromic(strP06, kP06) +"") + " Expected: true");
    }



}

