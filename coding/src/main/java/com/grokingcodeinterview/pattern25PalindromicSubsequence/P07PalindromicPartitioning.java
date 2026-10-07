package com.grokingcodeinterview.pattern25PalindromicSubsequence;

import static com.Utility.makeItBold;

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
public class P07PalindromicPartitioning {
    public static int findMPPCuts(String str) {
       // step 1: build palindrom table
        int n = str.length();
        if (n <= 1) return 0;

        boolean[][] dp = new boolean[n][n]; // dp[startIndex][endIndex] : substring from [startIndex, endIndex] is palindrome;

        for (int i =0; i<n; i++){
            dp[i][i] = true;
        }

        for (int startIndex = n-1 ; startIndex>=0; startIndex --){
            for(int endIndex = startIndex +1; endIndex<n; endIndex++){
                if(str.charAt(startIndex) == str.charAt(endIndex)){
                    if(endIndex -startIndex == 1 || dp[startIndex+1][endIndex-1] ){
                        dp[startIndex][endIndex] = true;
                    }
                }
            }
        }

        // step2: cuts[startIndex]:minimum cuts needed for subString[startIndex.. n-1]

        int[] cuts = new int[n]; // cuts[i]: minimum cuts neeed for substring [i.. n -1]
        // cut[0] : minimum cuts needed for substring [0.. n-1] : which is whole answer
        // cut[3] : minimum cuts needed for substring [3.. n-1]
        // cut[n  -1] : minimum cuts needed for substring [n-1.. n-1] : which is 0 as single character is palindrome

        // at  any startIndex , you will try every possible palindrome that starts with startIndex : [startIndex .. n-1]
        // suppose: str[startIndex.. endIndex] is palindrome then
        // you have two parts:
        //1. left part : str[startIndex.. endIndex] : which is palindrome so we don't need to cut it
        //2. right part : str[endIndex +1.. n-1] : this part still needs optmital cuts
        // so if you cut right after endIndex then you need 1 cut + cuts[endIndex +1]
        // Why 1?
        //Because you are making one cut between: str[startIndex..endIndex] | str[endIndex+1..n-1]
        for (int startIndex = n-1; startIndex>=0; startIndex --){
            int minCuts = Integer.MAX_VALUE ;
            // endIndex start from startIndex because a single character is also a palindrome
            // str[startIndex..startIndex] is palindrome so we can start from there
            for(int endIndex = startIndex ; endIndex<n; endIndex ++){
                if(dp[startIndex][endIndex]){
                    // why if endIndex == n-1 then minCuts = 0? because if the substring from startIndex to endIndex is
                    // palindrome and endIndex is n-1 then we don't need to cut anything as the whole substring
                    // from [startIndex.. endIndex] is plaindrome
                    if(endIndex == n-1){
                        minCuts = 0;
                    }else{
                        //  why 1+ cuts[endIndex+1]? because
                        // if the substring from startIndex to endIndex is palindrome then
                        // we need to cut the string after endIndex so cuts[endIndex+1]
                        // will give us the minimum cuts needed for the substring from endIndex +1 to n-1
                        // why  we need to add 1 to the cuts ? because we need to cut the string after endIndex to get the next substring which is from endIndex +1 to n-1


                        minCuts = Math.min(minCuts, 1+ cuts[endIndex +1]);
                    }
                }
            }
            cuts[startIndex] = minCuts;
        }

    return cuts[0];
    }
    public static void main(String[] args) {
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

