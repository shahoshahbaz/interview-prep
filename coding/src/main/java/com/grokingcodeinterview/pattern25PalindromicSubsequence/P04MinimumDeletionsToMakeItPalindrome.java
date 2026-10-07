package com.grokingcodeinterview.pattern25PalindromicSubsequence;
/*
Problem Statement
Given a string, find the minimum number of characters that we can remove to make it a palindrome.
Example 1: Input: "abdbca" Output: 1
Explanation: By removing "c", we get a palindrome "abdba".
Example 2: Input: = "cddpd" Output: 2
Explanation: Deleting "cp", we get a palindrome "ddd".
Example 3: Input: = "pqr" Output: 2
Explanation: We have to remove any two characters to get a palindrome, e.g. if we
remove "pq", we get palindrome "r".
Constraints:

1 <= st.length <= 1000
st consists only of lowercase English letters.
 */


import static com.Utility.makeItBold;

public class P04MinimumDeletionsToMakeItPalindrome {
    public static int findMinimumDeletions(String str){
        if (str == null) return 0;
        return str.length() - findLongestSubSequencePalindrome(str);

    }

    private static int findLongestSubSequencePalindrome(String str){
        if (str == null) return 0;
        int n = str.length();

        int[][] dp = new int[n][n];
            for (int i = 0; i < n; i++) {
                dp[i][i] = 1;
            }

        for (int startIndex = n-1; startIndex >=0; startIndex --){
            for (int endIndex = startIndex+1; endIndex<n; endIndex++){
                if (str.charAt(startIndex) == str.charAt(endIndex)){
                    dp[startIndex][endIndex] = 2 + dp[startIndex+1][endIndex-1];
                } else{
                    dp[startIndex][endIndex ] = Math.max(dp[startIndex+1][endIndex], dp[startIndex][endIndex -1]);
                }
            }
        }

        return dp[0][n-1];
    }

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("P04. Minimum Deletions to make it Palindrome");
        System.out.println("=====================================");

        String strP04 = "abdbca";
        System.out.println("Input: " + strP04 + " ,Output: " + makeItBold(findMinimumDeletions(strP04) +"") +" Expected: 1");
        strP04 = "cddpd";
        System.out.println("Input: " + strP04 + " ,Output: " + makeItBold(findMinimumDeletions(strP04) +"") +" Expected: 2");
        strP04 = "pqr";
        System.out.println("Input: " + strP04 + " ,Output: " + makeItBold(findMinimumDeletions(strP04) +"") +" Expected: 2");

    }

}

