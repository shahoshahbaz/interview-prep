package com.grokingcodeinterview.pattern25PalindromicSubsequence;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a string, find the length of its Longest Palindromic Substring (LPS). In a palindromic string, elements read the same backward and forward.

Example 1: Input: "abdbca" Output: 3
Explanation: LPS is "bdb".
Example 2: Input: = "cddpd" Output: 3
Explanation: LPS is "dpd".
Example 3: Input: = "pqr" Output: 1
Explanation: LPS could be "p", "q" or "r".
Constraints:
1 <= st.length <= 1000
st consists only of lowercase English letters.
 */

/**
 * dp[i][j] = true if substring s[i...j] is a palindrome
 */
public class P02LongestPalindromicSubstring {

  public static int findLPSubStringLengthBruteForce(String str) {
      return findLPSubStringLengthBruteForce(str, 0,str.length()-1);
  }


  private static int findLPSubStringLengthBruteForce(String str, int startIndex, int endIndex){
      if(startIndex > endIndex) return 0;
      if(startIndex == endIndex ) return 1;

      if(str.charAt(startIndex) == str.charAt(endIndex)){
          int innerLength =  findLPSubStringLengthBruteForce(str, startIndex+1, endIndex -1);
          int remainingLength = endIndex - startIndex + 1;
          if(remainingLength == innerLength + 2){
              return remainingLength ;
          }


      }

      int c1 = findLPSubStringLengthBruteForce(str, startIndex +1, endIndex);
      int c2 = findLPSubStringLengthBruteForce(str, startIndex, endIndex -1);

      return Math.max(c1, c2);


    }
    private static int findLPSubStringLengthTopDown(String str){
      int n = str.length();
      Integer[][] dp = new Integer[n][n];
      return findLPSubStringLengthTopDown(dp, str, 0, n-1);
    }
    private static int findLPSubStringLengthTopDown(Integer[][]dp, String str, int startIndex, int endIndex){
      if(startIndex> endIndex) return 0;
      if(startIndex == endIndex) return 1;

      if(dp[startIndex][endIndex] == null){
          if(str.charAt(startIndex) == str.charAt(endIndex)){
              int remainingLength = endIndex -startIndex +1;
              if(remainingLength == findLPSubStringLengthTopDown(dp, str, startIndex +1, endIndex -1 )+ 2){
                  dp[startIndex][endIndex] =    remainingLength;
                  return dp[startIndex][endIndex];
              }


          }
          int c1 = findLPSubStringLengthTopDown(dp, str, startIndex +1 , endIndex);
          int c2 = findLPSubStringLengthTopDown(dp, str, startIndex, endIndex -1);
          dp[startIndex][endIndex] = Math.max(c1, c2)  ;
      }
      return dp[startIndex][endIndex];
    }

    public static int  findLPSubStringLengthBottomUp(String str){
      int n = str.length();

      // dp[i][j] will be true if the string from index 'i' to index 'j' is a palindrome
      boolean [][] dp = new boolean[n][n];

      for(int i =0; i<n; i++)
          dp[i][i] = true;

      int maxLength =1;
        // why startInex is going from n-1 to 0, because we are filling the dp table from bottom up, and we need to make sure that the inner substring is already filled before we fill the outer substring.
      for (int startIndex = n-1; startIndex>=0; startIndex --){
          for (int endIndex = startIndex +1; endIndex<n; endIndex ++){
              if(str.charAt(startIndex) == str.charAt(endIndex)){
                  // if the remaining string is of length 1
                  // or the rest of the string is a palindrome
                  // then we can say that the string from startIndex to endIndex is also a palindrome

                  if(endIndex - startIndex == 1 || dp[startIndex+1][endIndex -1]){ //

                      dp[startIndex][endIndex] = true;
                      maxLength = Math.max(maxLength, endIndex - startIndex +1 );
                  }
              }
          }
      }
      return maxLength;

    }

    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P02. Longest Palindromic Substring");
        System.out.println("==============================================================");
        String strP02 = "abdbca";
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthBruteForce(strP02) +"") + ", Expected Output: 3 <= brute force");
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthTopDown(strP02) +"") + ", Expected Output: 3 <= top down with memoization");
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthBottomUp(strP02) +"") + ", Expected Output: 3 <= bottom up with tabulation");

         strP02 = "cddpd";
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthBruteForce(strP02) +"") + ", Expected Output: 3 <= brute force");
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthTopDown(strP02) +"") + ", Expected Output: 3 <= top down with memoization");
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthBottomUp(strP02) +"") + ", Expected Output: 3 <= bottom up with tabulation");

         strP02 = "pqr";
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthBruteForce(strP02) +"") + ", Expected Output: 1 <= brute force");
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthTopDown(strP02) +"") + ", Expected Output: 1 <= top down with memoization");
        System.out.println("Input: " + strP02 + ", Output: " + makeItBold(findLPSubStringLengthBottomUp(strP02) +"") + ", Expected Output: 1 <= bottom up with tabulation");
    }
}
