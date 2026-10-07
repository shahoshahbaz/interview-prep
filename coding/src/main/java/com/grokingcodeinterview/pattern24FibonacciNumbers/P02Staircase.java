package com.grokingcodeinterview.pattern24FibonacciNumbers;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a stair with 'n' steps,
implement a method to count how many possible ways are there to reach the top of the staircase,
given that, at every step you can either take 1 step, 2 steps, or 3 steps.

Example 1  Input: n = 3 output: 4
Explanation: Following are the four ways we can climb : {1,1,1}, {1,2}, {2,1}, {3}
Example 2: Input n = 4 output: 7

Explanation: Following are the seven ways we can climb : {1,1,1,1}, {1,1,2}, {1,2,1}, {2,1,1},
{2,2}, {1,3}, {3,1}
Constraints:

1 <= n <= 45.
 */
public class P02Staircase {
    public static int countWaysBruteForce(int n ){
        if(n == 0 ) return 1;
        if(n== 1) return 1;
        if(n == 2) return 2;

        return countWaysBruteForce(n -1) + countWaysBruteForce(n-2) + countWaysBruteForce(n -3);
    }
    public static int countWaysTopDown(int n){

        Integer[] dp = new Integer[n +1];

         return countWaysTopDown(dp, n);

    }
    private static int countWaysTopDown(Integer[] dp, int n) {
        if (n == 0) return 1;
        if (n < 0) return 0;

        if (dp[n] == null)
            dp[n] = countWaysTopDown(dp, n - 1) + countWaysTopDown(dp, n - 2) + countWaysTopDown(dp, n - 3);

        return dp[n];
    }
    public static int countWaysBottomUp(int n){

        Integer[] dp = new Integer[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 3;

        for (int i = 4; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
        }
        return dp[n];
    }
    public static int countWaysBottomUpOptimized(int n){
        if (n == 1) return 1;
        if (n == 2) return 2;
        if (n == 3) return 3;

        int n1 = 1; // ways to climb 1 step
        int n2 = 2; // ways to climb 2 steps
        int n3 = 3; // ways to climb 3 steps

        for (int i = 4; i <= n; i++) {
            int current = n1 + n2 + n3;
            n1 = n2;
            n2 = n3;
            n3 = current;
        }
        return n3;
    }

    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P02. Staircase");
        System.out.println("==============================================================");
        int nP02 = 4;
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBruteForce(nP02) +"") + ", Expected Output: 7 <= brute force");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(P02Staircase.countWaysTopDown(nP02) +"") + ", Expected Output: 7 <= top down with memoization");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBottomUp(nP02) +"") + ", Expected Output: 7 <= bottom up with tabulation");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBottomUpOptimized(nP02) +"") + ", Expected Output: 7 <= bottom up with tabulation and space optimization");

        nP02 = 5;
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBruteForce(nP02) +"") + ", Expected Output: 13 <= brute force");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(P02Staircase.countWaysTopDown(nP02) +"") + ", Expected Output: 13 <= top down with memoization");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBottomUp(nP02) +"") + ", Expected Output: 13 <= bottom up with tabulation");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBottomUpOptimized(nP02) +"") + ", Expected Output: 13 <= bottom up with tabulation and space optimization");

        nP02 = 6;
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBruteForce(nP02) +"") + ", Expected Output: 24 <= brute force");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(P02Staircase.countWaysTopDown(nP02) +"") + ", Expected Output: 24 <= top down with memoization");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBottomUp(nP02) +"") + ", Expected Output: 24 <= bottom up with tabulation");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBottomUpOptimized(nP02) +"") + ", Expected Output: 24 <= bottom up with tabulation and space optimization");


    }

}

