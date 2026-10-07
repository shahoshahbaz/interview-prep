package com.grokingcodeinterview.pattern25PalindromicSubsequence;

import static com.Utility.makeItBold;

public class pattern26PalindromicSubsequenceR1 {
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
        if (n<=1) return 0;

        // find first palindrom table
        boolean[][] dp = new boolean[n][n];

        for(int i =0; i<n; i++)
            dp[i][i] = true;

        for(int startIndex = n-1; startIndex>=0; startIndex --){
            for(int endIndex = startIndex + 1; endIndex<n; endIndex++){
                if(str.charAt(startIndex) == str.charAt(endIndex)){
                    if(endIndex- startIndex == 1 || dp[startIndex+1][ endIndex -1]){
                        dp[startIndex][endIndex] = true;
                    }
                }
            }
        }


        // now find the cuts
        int[] cuts = new int[n]; // cuts[startIndex]: minmum cuts is required for [ startIndex .. n-1]
        cuts[n-1] =0;

        for (int startIndex = n-1; startIndex>=0 ; startIndex --){

            int minCuts = Integer.MAX_VALUE;
            for(int endIndex = 0; endIndex<n; endIndex++){

                if(dp[startIndex][endIndex]){
                    if(endIndex == n-1)
                        minCuts = 0;
                    else{
                        minCuts = Math.min(minCuts, 1+ cuts[endIndex +1 ]);
                    }
                }
            }

            cuts[startIndex] = minCuts;
        }

        return cuts[0];
    }
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
    public static boolean isKPalindromic(String str, int k){
        return str.length() -findLPS(str)<=k;
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
    public static int findMinimumDeletions(String str){
        return str.length() - findLPS(str);
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
     int n = str.length();
     return n- findLPS(str);

 }
 public static int findLPS(String str){
     int n = str.length();
     if (n <=1 ) return n;

     int[][] dp = new int[n][n]; // dp[i][j]: length of longest Palindrome subsequence between i and j

     for (int i =0; i<n; i++){
         dp[i][i] =1;
     }

     for (int startIndex = n-1; startIndex>=0; startIndex--){
         for (int endIndex = startIndex +1; endIndex<n; endIndex ++){
             if(str.charAt(startIndex) == str.charAt(endIndex)){
                 dp[startIndex][endIndex] = 2+ dp[startIndex+1][endIndex -1];
             }else{
                 dp[startIndex][endIndex] = Math.max(dp[startIndex +1][endIndex], dp[startIndex ][endIndex-1]);
             }
         }
     }
     return dp[0][n-1];
      }
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
    /**
     * abdbca" Output: 7
     */
    public static int countPalindromicSubstrings(String str){
        if (str == null) return 0;
        if (str.length() ==1) return 1;
        int n = str.length();
        boolean[][] dp = new boolean[n][n];
        int counter =0;
        for (int i =0; i<n; i++){
            dp[i][i] = true;
            counter++;
        }

        for (int startIndex = n-1; startIndex>=0; startIndex--){
            for (int endIndex = startIndex+1; endIndex<n; endIndex ++){
                if (str.charAt(startIndex) == str.charAt(endIndex)){
                    if (endIndex -startIndex ==1 || dp[startIndex+1][endIndex-1]){
                        dp[startIndex][endIndex] = true;
                        counter++;
                    }
                }
            }
        }
        return counter;

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
        if (str.isEmpty()) return 0;
        return findLPSubSequenceLengthBruteForce(str, 0, str.length() -1);
    }
    private static int findLPSubSequenceLengthBruteForce(String str, int startIndex, int endIndex){
        if (startIndex> endIndex ) return 0;
        if (startIndex == endIndex ) return 1;
        if (str.charAt(startIndex) == str.charAt(endIndex)){
            return 2+ findLPSubSequenceLengthBruteForce(str, startIndex+1, endIndex -1);

        }
        int c1 = findLPSubSequenceLengthBruteForce(str, startIndex+1, endIndex);
        int c2 = findLPSubSequenceLengthBruteForce(str, startIndex, endIndex -1);
        return Math.max(c1, c2);
    }
    public static int findLPSequenceLengthBottomUp(String str){
        if (str.isEmpty()) return 0;
        int n = str.length();
        int [][] dp = new int[n][n];

        for (int i =0; i< n; i++){
            dp[i][i] = 1;
        }
        //fill table by increasing Len
        for (int len = 2; len<= n; len ++){
            for (int i =0; i<= n - len; i++){
                int j = i + len -1;
                if (str.charAt(i) == str.charAt(j))
                    dp[i][j] = 2 + dp[i+1] [j-1];
                else
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
            }
        }
        return dp[0][n-1];
    }
    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P01. Longest Palindromic Subsequence");
        System.out.println("==============================================================");
        String strP01 = "abdbca";
        System.out.println("Input: " + strP01 + ", Output: " +    makeItBold(findLPSubSequenceLengthBruteForce(strP01) + "")
                + ", Expected Output: 5 <= brute force");
//        System.out.println("Input: " + strP01 + ", Output: " +    makeItBold(findLPSequenceLengthTopDown(strP01) + "")
//                + ", Expected Output: 5 <= top down");
        System.out.println("Input: " + strP01 + ", Output: " +   makeItBold(findLPSequenceLengthBottomUp(strP01) + "")
                + ", Expected Output: 5 <= bottom up");
        strP01 = "cddpd";
        System.out.println("Input: " + strP01 + ", Output: " +    makeItBold(findLPSubSequenceLengthBruteForce(strP01) + "")
                + ", Expected Output: 3 <= brute force");
//        System.out.println("Input: " + strP01 + ", Output: " +   makeItBold(findLPSequenceLengthTopDown(strP01) + "")
//                + ", Expected Output: 3 <= top down");
        System.out.println("Input: " + strP01 + ", Output: " +   makeItBold(findLPSequenceLengthBottomUp(strP01) + "")
                + ", Expected Output: 3 <= bottom up");
        strP01 = "pqr";
        System.out.println("Input: " + strP01 + ", Output: " +    makeItBold(findLPSubSequenceLengthBruteForce(strP01) + "")
                + ", Expected Output: 1 <= brute force");
//        System.out.println("Input: " + strP01 + ", Output: " +   makeItBold(findLPSequenceLengthTopDown(strP01) + "")
//                + ", Expected Output: 1 <= top down");
        System.out.println("Input: " + strP01 + ", Output: " +  makeItBold(findLPSequenceLengthBottomUp(strP01) + "")
                + ", Expected Output: 1 <= bottom up");

        System.out.println("=====================================");
        System.out.println("P03. Count of Palindromic Substrings");
        System.out.println("=====================================");

        String strP03 = "abdbca";
        System.out.println("Input: " + strP03 + " ,Output: " + makeItBold(countPalindromicSubstrings(strP03) +"") + " Expected: 7");

        strP03 = "cddpd";
        System.out.println("Input: " + strP03 + " ,Output: " +  makeItBold(countPalindromicSubstrings(strP03) +"") + " Expected: 7");

        strP03 = "pqr";
        System.out.println("Input: " + strP03 + " ,Output: " + makeItBold(countPalindromicSubstrings(strP03) +"")+ " Expected: 3");

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
        System.out.println("Input: " + strP06 + " , k = " + kP06 + " ,Output: " + makeItBold(isKPalindromic(strP06, kP06) +"") + " Expected: true");System.out.println("=====================================");
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

