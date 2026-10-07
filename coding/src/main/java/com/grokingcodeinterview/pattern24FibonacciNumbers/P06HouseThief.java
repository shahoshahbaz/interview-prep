package com.grokingcodeinterview.pattern24FibonacciNumbers;

import java.util.Arrays;

/*
There are n houses built in a line. A thief wants to steal the maximum possible money from these houses.
 The only restriction the thief has is that he can't steal from two consecutive houses,
  as that would alert the security system. How should the thief maximize his stealing?

Problem Statement
Given a number array representing the wealth of n houses,
 determine the maximum amount of money the thief can steal without alerting the security system.

Example 1: Input: {2, 5, 1, 3, 6, 2, 4} Output: 15
Explanation: The thief should steal from houses 5 + 6 + 4
Example 2: Input: {2, 10, 14, 8, 1} Output: 18
Explanation: The thief should steal from houses 10 + 8
Constraints:

1 <= wealth.length <= 100
0 <= wealth[i] <= 400
 */

/**
 * DP meaning:
 * dp[i] = maximum money we can steal from houses[0..i]
 * So at each house i, we have 2 choices:
 *   Choice 1: steal house i, Then we cannot steal i-1, so:
 *    wealth[i] + dp[i-2]
 * Choice 2: skip house i
 *     dp[i-1]
 */
public class P06HouseThief {
    public static int findMaxSteal(int[] wealth ){
        return findMaxStealRecursive(wealth, 0);
    }
    private static int findMaxStealRecursive(int[] wealth, int currentIndex){
        // base case
        if (currentIndex >= wealth.length) return 0;

        int stealCount = wealth[currentIndex] + findMaxStealRecursive(wealth, currentIndex+2);
        int skipCount = findMaxStealRecursive(wealth, currentIndex+1);
        return Math.max(stealCount, skipCount);
    }

    public static int findMaxStealTopDown(int[] wealth){
        int[] dp = new int[wealth.length];

        return findMaxStealTopDownRecursive(dp, wealth, 0);
    }

    private static int findMaxStealTopDownRecursive(int[] dp, int[] wealth, int currentIndex){
        if (currentIndex >= wealth.length) return 0;
        if(dp[currentIndex] == 0){
            int stealCount = wealth[currentIndex] + findMaxStealTopDownRecursive(dp, wealth, currentIndex +2);
            int skipCount = findMaxStealTopDownRecursive(dp, wealth, currentIndex + 1);
            dp[currentIndex] = Math.max(stealCount, skipCount);
        }
        return dp[currentIndex];
    }

    public static int findMaxStealBottomUp(int[] wealth){

        int n = wealth.length;
        if (n==0) return 0;
        if (n ==1) return wealth[0];

        int [] dp = new int[n]; // dp[i] = maximum money we can steal from houses[0..i]
        dp[0] =wealth[0];
        dp[1] = Math.max(wealth[0], wealth[1]);

        for (int i =2; i<n; i++){
            // int stealCount = wealth[i] + dp[i-2];
            // int skipCount = dp[i-1];
            // dp[i] = Math.max(stealCount, skipCount);

            dp[i]= Math.max(wealth[i]+dp[i-2], dp[i-1]);
        }


        return dp[n-1];
    }
    public static int findMaxStealBottomUpOptimizeSpace(int[] wealth){
        if(wealth.length == 0) return 0;
        int n1= 0;
        int n2 =wealth[0];
        int temp ;
        for (int i =1; i< wealth.length ; i++){
            temp = Math.max(n1 + wealth[i] , n2);
            n1 = n2;
            n2 = temp;
        }
        return n2;

    }
    public static void main(String[] args) {

        System.out.println("===========================================");
        System.out.println("P06. House Thief");
        System.out.println("===========================================");
        int[] wealthP06 = {2, 5, 1, 3, 6 , 2, 4};
//        System.out.println("Input: " + Arrays.toString(wealthP06) + "Output: " + findMaxSteal(wealthP06) +", Expected: 15 <== recursive");
//        System.out.println("Input: " + Arrays.toString(wealthP06)+ "Output: " + findMaxStealTopDown(wealthP06) +", Expected: 15 <== top down");
        System.out.println("Input: " + Arrays.toString(wealthP06)+ "Output: " + findMaxStealBottomUp(wealthP06) +", Expected: 15 <== bottom up");
        wealthP06 = new int[]{2, 10, 14, 8, 1};
//        System.out.println("Input: " + Arrays.toString(wealthP06) + "Output: " + findMaxSteal(wealthP06) +", Expected: 18 <== recursive");
//        System.out.println("Input: " + Arrays.toString(wealthP06)+ "Output: " + findMaxStealTopDown(wealthP06) +", Expected: 18 <== top down");
        System.out.println("Input: " + Arrays.toString(wealthP06)+ "Output: " + findMaxStealBottomUp(wealthP06) +", Expected: 18 <== bottom up");
        wealthP06 = new int[]{2, 10, 14, 8, 1};
        System.out.println("Input: " + Arrays.toString(wealthP06)+ "Output: " + findMaxStealBottomUpOptimizeSpace(wealthP06) +", Expected: 18 <== bottom up optimize space");



    }
}

