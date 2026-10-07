package com.grokingcodeinterview.pattern25PalindromicSubsequence;

import static com.Utility.makeItBold;

public class pattern26PalindromicSubsequenceR3 {
    /*
        Problem Statement
        Given a string, we want to cut it into pieces such that each piece is a palindrome.
        Write a function to return the minimum number of cuts needed.
        Example 1: Input: "abdbca" Output: 3
        Explanation: Palindrome pieces are "a", "bdb", "c", "a".
        Example 2: Input: = "cddpd" Output: 2
        Explanation: Palindrome pieces are "c", "d", "dpd".
        Example 3: Input: = "pqr" Output: 2
        Explanation: Palindrome pieces are "p", "q", "r".
        Example 4: Input: = "pp" Output: 0
        Explanation: We do not need to cut, as "pp" is a palindrome.
        Constraints:

        1 <= st.length <= 16
        s contains only lowercase English letters.
         */

    public static int findMPPCuts(String str){
        int n = str.length();

        // find the palindrome dp
        boolean[][] dp = new boolean[n][n];

        for(int i = 0; i<n; i ++){
            dp[i][i] = true;
        }

        for (int startIndex = n-1; startIndex >= 0; startIndex --){
            for(int endIndex = startIndex+1; endIndex <n ; endIndex ++){
                if(str.charAt(startIndex) == str.charAt(endIndex)){
                    if(endIndex - startIndex ==1  || dp[startIndex +1][endIndex -1]){
                        dp[startIndex][endIndex] = true;
                    }
                }
            }
        }

        ///  find the number of cuts

        int[] cuts = new int[n];

        for (int startIndex = n-1; startIndex>=0; startIndex --){
            int minCuts = Integer.MAX_VALUE;
            for(int endIndex = startIndex; endIndex <n; endIndex ++ ){
                if(dp[startIndex][endIndex]){
                    if(endIndex == n-1){
                        minCuts = 0;

                    }else{
                        minCuts = Math.min(minCuts, 1 + cuts[endIndex +1]);
                    }
                }
            }

            cuts[startIndex] = minCuts;
        }

        return cuts[0];

    }

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
    public static int findMinimumDeletions(String str) {
        {
            return str.length() - findLPS(str);
        }
    }
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
    public static int minInsertion(String str){
        return str.length() - findLPS(str);
    }
    public static int findLPS(String str){
        int n = str.length();
        int[][] dp = new int[n][n]; // dp[i][j]: the longest palindrom subsequence from i to j

        for(int i =0; i< n; i++){
            dp[i][i] = 1;
        }

        for(int startIndex = n-1; startIndex>=0 ; startIndex--){
            for(int endIndex = startIndex+1; endIndex<n; endIndex ++){
                if(str.charAt(startIndex) == str.charAt(endIndex)){
                    dp[startIndex][endIndex] = 2 + dp[startIndex +1][endIndex -1];
                }else{
                    dp[startIndex][endIndex] = Math.max(dp[startIndex+1][endIndex], dp[startIndex][endIndex-1]);
                }
            }
        }
        return dp[0][n-1];
    }

    /*
    Problem Statement
    Given a string, find the length of its Longest Palindromic Substring (LPS).
     In a palindromic string, elements read the same backward and forward.

    Example 1: Input: "abdbca" Output: 3
    Explanation: LPS is "bdb".
    Example 2: Input: = "cd
    dpd" Output: 3
    Explanation: LPS is "dpd".
    Example 3: Input: = "pqr" Output: 1
    Explanation: LPS could be "p", "q" or "r".
    Constraints:
    1 <= st.length <= 1000
    st consists only of lowercase English letters.
 */
    public static int findLPSubStringLengthBruteForce(String str){

        if(str.length() ==1) return 1;
        return findLPSubStringLengthBruteForce(str, 0, str.length() -1);
    }
    public static int findLPSubStringLengthBruteForce(String str, int startIndex, int endIndex){
        if (startIndex>endIndex) return 0;
        if (startIndex == endIndex) return 1;
        if (str.charAt(startIndex) == str.charAt(endIndex)){
            int innerLength = findLPSubStringLengthBruteForce(str, startIndex +1, endIndex-1);
            if (innerLength +2 == (endIndex -startIndex+1)){
                return endIndex - startIndex +1;
            }
        }
            int c1 = findLPSubStringLengthBruteForce(str, startIndex+1, endIndex);
            int c2 = findLPSubStringLengthBruteForce(str, startIndex, endIndex -1);


        return Math.max(c1, c2);
    }
    public static int findLPSubStringLengthBottomUp(String str){
        int n = str.length();
        if(n<=1) return n;

        int[][] dp = new int[n][n];

        for(int i =0; i<n; i++)
            dp[i][i] = 1;

        for (int si= n-1; si>= 0; si--){
            for(int ei = si+1; ei< n; ei++){
                if(str.charAt(si) == str.charAt(ei) ){
                    dp[si][ei] = 2 + dp[si+1][ei-1];
                }else{
                    dp[si][ei] = Math.max(dp[si+1][ei ], dp[si][ei -1]);
                }
            }
        }
        return dp[0][n-1];
    }

    /*
        Problem Statement
        Given a sequence, find the length of its Longest Palindromic Subsequence (LPS).
         In a palindromic subsequence, elements read the same backward and forward.
        A subsequence is a sequence that can be derived from another sequence
         by deleting some or no elements without changing the order of the remaining elements.
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
    public static int findLPSubSequenceLengthBruteForce(String str){
        if (str == null || str.isEmpty()) return 0;
        return findLPSubSequenceLengthBruteForce(str, 0, str.length() -1);
    }
    private static int findLPSubSequenceLengthBruteForce(String str, int leftIndex, int rightIndex){
        if (leftIndex> rightIndex) return 0;
        if (leftIndex == rightIndex) return 1;

        if (str.charAt(leftIndex) == str.charAt(rightIndex)){
            return 2+ findLPSubSequenceLengthBruteForce(str, leftIndex +1, rightIndex -1);

        }
        int c1 = findLPSubSequenceLengthBruteForce(str, leftIndex +1, rightIndex);
        int c2 = findLPSubSequenceLengthBruteForce(str, leftIndex, rightIndex -1);

        return Math.max(c1, c2);
    }
    public static int findLPSequenceLengthBottomUp(String str){
        int n = str.length();
        if ( n<= 1 ) return n;

        boolean [][] dp = new boolean[n][n]; // dp[i] [j] the substring from i,..j is palindrome


        for (int i =0; i<n; i++){
            dp[i][i] = true;
        }
        int maxLength =0;

        for (int startIndex = n-1; startIndex>=0; startIndex--){
            for(int endIndex = startIndex+1; endIndex<n; endIndex++){
                if(str.charAt(startIndex) == str.charAt(endIndex)){
                    if(endIndex - startIndex == 1 || dp[startIndex +1][endIndex -1]){
                        dp[startIndex][endIndex] = true;
                        maxLength = Math.max(maxLength, endIndex - startIndex +1);

                    }
                }
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P01. Longest Palindromic Subsequence");
        System.out.println("==============================================================");
        String strP01 = "abdbca";
        System.out.println("Input: " + strP01 + ", Output: " + makeItBold(findLPSubSequenceLengthBruteForce(strP01) + "")
                + ", Expected Output: 5 <= brute force");
//        System.out.println("Input: " + strP01 + ", Output: " +    makeItBold(findLPSequenceLengthTopDown(strP01) + "")
//                + ", Expected Output: 5 <= top down");
        System.out.println("Input: " + strP01 + ", Output: " + makeItBold(findLPSequenceLengthBottomUp(strP01) + "")
                + ", Expected Output: 5 <= bottom up");
        strP01 = "cddpd";
        System.out.println("Input: " + strP01 + ", Output: " + makeItBold(findLPSubSequenceLengthBruteForce(strP01) + "")
                + ", Expected Output: 3 <= brute force");
//        System.out.println("Input: " + strP01 + ", Output: " +   makeItBold(findLPSequenceLengthTopDown(strP01) + "")
//                + ", Expected Output: 3 <= top down");
        System.out.println("Input: " + strP01 + ", Output: " + makeItBold(findLPSequenceLengthBottomUp(strP01) + "")
                + ", Expected Output: 3 <= bottom up");
        strP01 = "pqr";
        System.out.println("Input: " + strP01 + ", Output: " + makeItBold(findLPSubSequenceLengthBruteForce(strP01) + "")
                + ", Expected Output: 1 <= brute force");
//        System.out.println("Input: " + strP01 + ", Output: " +   makeItBold(findLPSequenceLengthTopDown(strP01) + "")
//                + ", Expected Output: 1 <= top down");
        System.out.println("Input: " + strP01 + ", Output: " + makeItBold(findLPSequenceLengthBottomUp(strP01) + "")
                + ", Expected Output: 1 <= bottom up");

        System.out.println("==============================================================");
        System.out.println("P02. Longest Palindromic Substring");
        System.out.println("==============================================================");
        String strP02 = "abdbca";
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthBruteForce(strP02) +"") + ", Expected Output: 3 <= brute force");
//        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthTopDown(strP02) +"") + ", Expected Output: 3 <= top down with memoization");
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthBottomUp(strP02) +"") + ", Expected Output: 3 <= bottom up with tabulation");

        strP02 = "cddpd";
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthBruteForce(strP02) +"") + ", Expected Output: 3 <= brute force");
//        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthTopDown(strP02) +"") + ", Expected Output: 3 <= top down with memoization");
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthBottomUp(strP02) +"") + ", Expected Output: 3 <= bottom up with tabulation");

        strP02 = "pqr";
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthBruteForce(strP02) +"") + ", Expected Output: 1 <= brute force");
//        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthTopDown(strP02) +"") + ", Expected Output: 1 <= top down with memoization");
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthBottomUp(strP02) +"") + ", Expected Output: 1 <= bottom up with tabulation");
        System.out.println("=====================================");
        System.out.println("P05. Minimum Insertion to make it Palindrome");
        System.out.println("=====================================");
        String strP05 = "abdbca";
        System.out.println("Input: " + strP05 + " ,Output: " + minInsertion(strP05) + " Expected: 1");
        strP05 = "cddpd";
        System.out.println("Input: " + strP05 + " ,Output: " + minInsertion(strP05) + " Expected: 2");
        strP05 = "pqr";
        System.out.println("Input: " + strP05 + " ,Output: " + minInsertion(strP05) + " Expected: 2");
        System.out.println("=====================================");
        System.out.println("P04. Minimum Deletions to make it Palindrome");
        System.out.println("=====================================");

        String strP04 = "abdbca";
        System.out.println("Input: " + strP04 + " ,Output: " + makeItBold(findMinimumDeletions(strP04) +"") +" Expected: 1");
        strP04 = "cddpd";
        System.out.println("Input: " + strP04 + " ,Output: " + makeItBold(findMinimumDeletions(strP04) +"") +" Expected: 2");
        strP04 = "pqr";
        System.out.println("Input: " + strP04 + " ,Output: " + makeItBold(findMinimumDeletions(strP04) +"") +" Expected: 2");

        System.out.println("=====================================");
        System.out.println("P07. Palindromic Partitioning");
        System.out.println("=====================================");

        String strP07 = "abdbca";
        System.out.println("Input: " + strP07 + " ,Output: " + makeItBold(findMPPCuts(strP07) +"") + " Expected: 3");
        strP07 = "cddpd";
        System.out.println("Input: " + strP07 + " ,Output: " + makeItBold(findMPPCuts(strP07) +"") + " Expected: 2");
        strP07 = "pqr";
        System.out.println("Input: " + strP07 + " ,Output: " + makeItBold(findMPPCuts(strP07) +"") + " Expected: 2");
        strP07 = "pp";
        System.out.println("Input: " + strP07 + " ,Output: " + makeItBold(findMPPCuts(strP07) +"") + " Expected: 0");






    }
}

