package com.grokingcodeinterview.pattern24FibonacciNumbers;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a staircase with â€˜nâ€™ steps and an array of 'n' numbers representing the fee that you have to pay if you take the step.
Implement a method to calculate the minimum fee required to reach the top of the staircase (beyond the top-most step).
 At every step, you have an option to take either 1 step, 2 steps, or 3 steps. You should assume that you are standing at the first step.

Example 1:

Number of stairs (n) : 6  Fee: {1,2,5,2,1,2} Output: 3
Explanation: Starting from index '0', we can reach the top through: 0->3->top
The total fee we have to pay will be (1+2).
Example 2: Number of stairs (n): 4 Fee: {2,3,4,5} Output: 5
Explanation: Starting from index '0', we can reach the top through: 0->1->top
The total fee we have to pay will be (2+3).
 */
public class P05MinimumJumpsWithFee {

    public static int findMinFee(int[] fee){
        return findMinFeeRecursive(fee, 0);
    }
    private static int findMinFeeRecursive(int[] fee,  int currentIndex){
        if(currentIndex > fee.length -1) return 0;

        int take1Step = findMinFeeRecursive(fee, currentIndex +1);
        int take2Step = findMinFeeRecursive(fee, currentIndex +2);
        int take3Step = findMinFeeRecursive(fee, currentIndex + 3);

        int min = Math.min(Math.min(take1Step, take2Step), take3Step);

        return min + fee[currentIndex];

    }

    public static int findMinFeeTopDown(int[] fee){
        int[] dp = new int[fee.length +1];
        return findMinFreeTopDownRec(dp, fee, 0);
    }

    private static int findMinFreeTopDownRec(int[] dp, int[] fee, int currentIndex){
        if (currentIndex > fee.length -1) return 0;

        if(dp[currentIndex] == 0){
            int take1Step = findMinFreeTopDownRec(dp, fee, currentIndex +1);
            int take2Step = findMinFreeTopDownRec(dp, fee, currentIndex +2);
            int take3Step = findMinFreeTopDownRec(dp, fee, currentIndex +3);

            dp[currentIndex] = fee[currentIndex] + Math.min(Math.min(take1Step, take2Step), take3Step);

        }


        return dp[currentIndex];
    }

    public static int findMinFeeBottomUp (int[] fee){
        int n = fee.length;
        int[] dp = new int[n + 1];
        // dp[i] will represent the minimum fee required to reach to step i,
        dp[0] = 0;
        // why dp[1] , dp[2] and dp[3] have same fee?
        // because we are standing at the first step,
        // so based on problem statement, we have 3 options to take 1 step, 2 steps or 3 steps,
        // so we can reach to step 1, step 2 and step 3 with same fee which is fee[0]
        dp[1] = fee[0];
        dp[2] = fee[0];
        dp[3] = fee[0];

        for (int i = 4; i <= n; i++) {
            // to reach to step i,
            // we can take 1 or 2 or 3 steps,
            //
            dp[i] = Math.min(
                    fee[i - 1] + dp[i - 1],// take 1 step
                    Math.min(
                            fee[i - 2] + dp[i - 2],// take 2 steps
                            fee[i - 3] + dp[i - 3] // take 3 steps
                    )
            );
        }

        return dp[n];
    }


    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P05. Minimum jumps with fee");
        System.out.println("==============================================================");
        int[] feeP05 = {1,2,5,2,1,2};
        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFee(feeP05) +"" )+ ", Expected: 3  <= brute force");
        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFeeTopDown(feeP05) +"" )+ ", Expected: 3  <= top down");
        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFeeBottomUp(feeP05) +"" )+ ", Expected: 3  <= bottom up");

        feeP05 = new int[]{2,3,4,5};
        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFee(feeP05) +"" )+ ", Expected: 5 <= brute force");
        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFeeTopDown(feeP05) +"" )+ ", Expected: 5 <= top down");
        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFeeBottomUp(feeP05) +"" )+ ", Expected: 5 <= bottom up");

        feeP05 = new int[]{1,2,3,4,5,6,7,8,9,10};
        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFee(feeP05) +"" )+ ", Expected: 16 <= brute force");
        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFeeTopDown(feeP05) +"" )+ ", Expected: 16 <= top down");
        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFeeBottomUp(feeP05) +"" )+ ", Expected: 16 <= bottom up");

    }
}

