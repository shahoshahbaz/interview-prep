package com.grokingcodeinterview.pattern24FibonacciNumbers;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a number 'n',
 implement a method to count how many possible ways there are to express 'n' as the sum of 1, 3, or 4.

Example 1: n : 4 output: 4
Explanation: Following are the four ways we can express 'n' : {1,1,1,1}, {1,3}, {3,1}, {4}
Example 2: n : 5 output: 6
Explanation: Following are the six ways we can express 'n' : {1,1,1,1,1}, {1,1,3}, {1,3,1}, {3,1,1},
{1,4}, {4,1}
 */
public class P03NumberFactors {
    public static int countWaysSumBruteForce(int num){
        if (num == 0) return 1; // why ? because we have found a valid combination that sums up to the original number, so we count it as one way.
        if (num == 1) return 1;
        if (num == 2 ) return 1;
        if (num == 3 ) return 2;
        return countWaysSumBruteForce(num-1) + countWaysSumBruteForce(num-3) + countWaysSumBruteForce(num-4);
    }

    public static int countWaysSumTopDown(int num){
        int[] dp = new int[num+1];

        return countWaysSumTopDown(dp, num);

    }

    private static int countWaysSumTopDown(int[] dp, int num){
        if(num == 0 ) dp[0] = 1;
        if( num == 1 ) dp[1] =1 ;
        if (num == 2 ) dp[2] = 1;
        if (num == 3 ) dp[3] = 2;


        if(dp[num] == 0)
          dp[num] = countWaysSumTopDown(dp, num -1) + countWaysSumTopDown(dp, num - 3) + countWaysSumTopDown(dp, num -4);
        return dp[num];
    }

    public static int countWaysSumBottomTop(int num) {
        int[] dp = new int[num +1];
        dp[0] = 1;
        dp[1] =1;
        dp[2] =1;
        dp[3] = 2;
        for (int i = 4; i<= num; i++)
            dp[i] = dp[i -1] + dp[i-3] +dp[i -4];
        return dp[num];
    }

    public static int countWaysSumBottomTopOptimized(int num) {
        if (num == 0) return 1;
        if (num == 1) return 1;
        if (num == 2) return 1;
        if (num == 3) return 2;

        int n0 = 1; // dp[i-4]
        int n1 = 1; // dp[i-3]
        int n2 = 1; // dp[i-2] (kept for shifting only)
        int n3 = 2; // dp[i-1]

        for (int i = 4; i <= num; i++) {
            int temp = n3 + n1 + n0; // dp[i-1] + dp[i-3] + dp[i-4]

            n0 = n1;
            n1 = n2;
            n2 = n3;
            n3 = temp;
        }

        return n3;
    }

    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P03. Number factors");
        System.out.println("==============================================================");
        int nP03 = 4;
        System.out.println("Input: " + nP03 + ", Output: " +    makeItBold(countWaysSumBruteForce(nP03) + "")
                + ", Expected Output: 4 <= brute force");
        System.out.println("Input: " + nP03 + ", Output: " +    makeItBold(countWaysSumTopDown(nP03) + "")
                + ", Expected Output: 4 <= top down");
        System.out.println("Input: " + nP03 + ", Output: " +   makeItBold(countWaysSumBottomTop(nP03) + "")
                + ", Expected Output: 4 <= bottom up");
        System.out.println("Input: " + nP03 + ", Output: " +  makeItBold(countWaysSumBottomTopOptimized(nP03) + "")
                + ", Expected Output: 4 <= bottom up-optimized");

        nP03 = 5;
        System.out.println("Input: " + nP03 + ", Output: " +    makeItBold(countWaysSumBruteForce(nP03) + "")
                + ", Expected Output: 6 <= brute force");
        System.out.println("Input: " + nP03 + ", Output: " +   makeItBold(countWaysSumTopDown(nP03) + "")
                + ", Expected Output: 6 <= top down");
        System.out.println("Input: " + nP03 + ", Output: " +   makeItBold(countWaysSumBottomTop(nP03) + "")
                + ", Expected Output: 6 <= bottom up");
        System.out.println("Input: " + nP03 + ", Output: " +  makeItBold(countWaysSumBottomTopOptimized(nP03) + "")
                + ", Expected Output: 6 <= bottom up-optimized");

        nP03 = 6;
        System.out.println("Input: " + nP03 + ", Output: " +  makeItBold(countWaysSumBruteForce(nP03) + "")
                + ", Expected Output: 9 <= brute force");
        System.out.println("Input: " + nP03 + ", Output: " + makeItBold(countWaysSumTopDown(nP03) + "")
                + ", Expected Output: 9 <= top down");
        System.out.println("Input: " + nP03 + ", Output: " +  makeItBold(countWaysSumBottomTop(nP03) + "")
                + ", Expected Output: 9 <= bottom up");
        System.out.println("Input: " + nP03 + ", Output: " +  makeItBold(countWaysSumBottomTopOptimized(nP03) + "")
                + ", Expected Output: 9 <= bottom up-optimized");

    }
}

