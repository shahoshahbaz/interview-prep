package com.grokingcodeinterview.pattern25PalindromicSubsequence;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a sequence, find the length of its Longest Palindromic Subsequence (LPS). In a palindromic subsequence, elements read the same backward and forward.

A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without changing the order of the remaining elements.

Example 1: Input: "abdbca" Output: 5
Explanation: LPS is "abdba".
Example 2: Input: = "cddpd" Output: 3
Explanation: LPS is "ddd".
Example 3: Input: = "pqr" Output: 1
Explanation: LPS could be "p", "q" or "r".
Constraints:

1 <= st.length <= 1000
sy consists only of lowercase English letters.
 */
public class p01LongestPalindromicSubsequence {

    public static int findLPSubSequenceLengthBruteForce(String str){
        return findLPSubSequenceLengthBruteForce(str, 0, str.length() -1);
    }
    private static int findLPSubSequenceLengthBruteForce(String str, int startIndex, int endIndex){
        if(startIndex> endIndex) return 0;

        if(startIndex == endIndex) return 1;

        if(str.charAt(startIndex) == str.charAt(endIndex))
            return 2+ findLPSubSequenceLengthBruteForce(str, startIndex +1, endIndex -1);
        int c1 = findLPSubSequenceLengthBruteForce(str, startIndex+ 1, endIndex);
        int c2 = findLPSubSequenceLengthBruteForce(str, startIndex, endIndex -1);
        return Math.max(c1, c2);

    }

    public static int findLPSequenceLengthTopDown(String str){
        Integer[][] dp = new Integer[str.length()][str.length()];
        return findLPSequenceLengthTopDown(dp, str, 0, str.length() -1);
    }
    private static int findLPSequenceLengthTopDown(Integer[][] dp, String str, int startIndex, int endIndex){
        if(startIndex> endIndex) return 0;
        if(startIndex == endIndex) return 1;

        if(dp[startIndex][endIndex] == null){
            if(str.charAt(startIndex) == str.charAt(endIndex)){
                dp[startIndex][endIndex] = 2 + findLPSequenceLengthTopDown(dp, str, startIndex +1, endIndex -1);
            }else{
                int c1 = findLPSequenceLengthTopDown(dp, str, startIndex +1, endIndex);
                int c2 = findLPSequenceLengthTopDown(dp, str, startIndex, endIndex -1);
                dp[startIndex][endIndex] = Math.max(c1, c2);
            }

        }
        return dp[startIndex][endIndex];

    }

    /**
     * dp[start][end] represents:
     * The length of the longest palindromic subsequence in the substring str[start ... end].
     * So:
     *
     * Rows = start index in the string
     * Columns = end index in the string
     * Each cell answers: â€œWhat is the LPS length between these two boundaries?â€
     *
     * Example:
     * dp[2][5] = LPS length in substring str[2...5].
     */
    public static int findLPSequenceLengthBottomUp(String str){

        int n = str.length();

        int[][] dp = new int[n][n];// what dp represetn and what about row and columns
        for(int i =0; i< n; i++){
            dp[i][i]=1;
        }
        /**
         * dp[i][j] stores LPS length for substring str[i...j].
         * dp[i][i] = 1 because a single character is a palindrome.
         * dp[0][n-1] is the LPS for the entire string.
         * Looping start from n-1 â†’ 0 ensures all required subproblems are precomputed.
         */

            for(int startIndex = n-1; startIndex >= 0; startIndex --){
                for(int endIndex = startIndex+1; endIndex <n; endIndex ++){
                    if(str.charAt(startIndex) == str.charAt(endIndex)){
                        dp[startIndex][endIndex] = 2+ dp[startIndex +1][endIndex -1];
                }else{
                    dp[startIndex][endIndex] = Math.max(dp[startIndex +1][endIndex], dp[startIndex][endIndex -1]);
                }
            }
        }
        return dp[0][n-1]; // what dp[0][n-1] represent? when leftIndex = 0 and rightIndex =n-1, means whole string str
    }

    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P01. Longest Palindromic Subsequence");
        System.out.println("==============================================================");
        String strP01 = "abdbca";
        System.out.println("Input: " + strP01 + ", Output: " +    makeItBold(findLPSubSequenceLengthBruteForce(strP01) + "")
                + ", Expected Output: 5 <= brute force");
        System.out.println("Input: " + strP01 + ", Output: " +    makeItBold(findLPSequenceLengthTopDown(strP01) + "")
                + ", Expected Output: 5 <= top down");
        System.out.println("Input: " + strP01 + ", Output: " +   makeItBold(findLPSequenceLengthBottomUp(strP01) + "")
                + ", Expected Output: 5 <= bottom up");
        strP01 = "cddpd";
        System.out.println("Input: " + strP01 + ", Output: " +    makeItBold(findLPSubSequenceLengthBruteForce(strP01) + "")
                + ", Expected Output: 3 <= brute force");
        System.out.println("Input: " + strP01 + ", Output: " +   makeItBold(findLPSequenceLengthTopDown(strP01) + "")
                + ", Expected Output: 3 <= top down");
        System.out.println("Input: " + strP01 + ", Output: " +   makeItBold(findLPSequenceLengthBottomUp(strP01) + "")
                + ", Expected Output: 3 <= bottom up");
        strP01 = "pqr";
        System.out.println("Input: " + strP01 + ", Output: " +    makeItBold(findLPSubSequenceLengthBruteForce(strP01) + "")
                + ", Expected Output: 1 <= brute force");
        System.out.println("Input: " + strP01 + ", Output: " +   makeItBold(findLPSequenceLengthTopDown(strP01) + "")
                + ", Expected Output: 1 <= top down");
        System.out.println("Input: " + strP01 + ", Output: " +  makeItBold(findLPSequenceLengthBottomUp(strP01) + "")
                + ", Expected Output: 1 <= bottom up");


    }


}

