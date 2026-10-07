package com.grokingcodeinterview.pattern23Knapsack;

import java.util.Arrays;

public class P04CoinChange {
/*
Coin Change

You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.

Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.

You may assume that you have an infinite number of each kind of coin.

Example 1:

Input: coins = [1,2,5], amount = 11
Output: 3
Explanation: 11 = 5 + 5 + 1

Example 2:

Input: coins = [2], amount = 3
Output: -1
Explanation: No combination of 2s can sum to 3 (odd amount, only even coin).

Example 3:

Input: coins = [1], amount = 0
Output: 0
Explanation: Zero coins needed to make amount 0.

Constraints:

1 <= coins.length <= 12
1 <= coins[i] <= 2^31 - 1
0 <= amount <= 10^4
 */
    public  static int coinChange(int[] coins, int amount) {
        // dp[i] = min number of coins to reach i amount;
        int[] dp = new int[amount+1];
        Arrays.fill(dp, amount +1);
        dp[0] = 0;
        for (int coin: coins){
            for(int amt= coin; amt<=amount;  amt ++ ){
                dp[amt] = Math.min(dp[amt], dp[amt - coin] +1);
            }
        }


        return dp[amount]> amount? -1: dp[amount];
    }
    public static void main(String[] args) {

        // ---- Example 1 ----
        int[] coins1 = {1, 2, 5};
        int amount1 = 11;
        int expected1 = 3;
        int result1 = coinChange(coins1, amount1);

        System.out.println("Example 1");
        System.out.println("Input:    coins=" + Arrays.toString(coins1) + ", amount=" + amount1);
        System.out.println("Output:   " + result1);
        System.out.println("Expected: " + expected1);
        System.out.println(result1 == expected1 ? "PASS" : "FAIL");
        System.out.println();

        // ---- Example 2 ----
        int[] coins2 = {2};
        int amount2 = 3;
        int expected2 = -1;
        int result2 = coinChange(coins2, amount2);

        System.out.println("Example 2");
        System.out.println("Input:    coins=" + Arrays.toString(coins2) + ", amount=" + amount2);
        System.out.println("Output:   " + result2);
        System.out.println("Expected: " + expected2);
        System.out.println(result2 == expected2 ? "PASS" : "FAIL");
        System.out.println();

        // ---- Example 3 ----
        int[] coins3 = {1};
        int amount3 = 0;
        int expected3 = 0;
        int result3 = coinChange(coins3, amount3);

        System.out.println("Example 3");
        System.out.println("Input:    coins=" + Arrays.toString(coins3) + ", amount=" + amount3);
        System.out.println("Output:   " + result3);
        System.out.println("Expected: " + expected3);
        System.out.println(result3 == expected3 ? "PASS" : "FAIL");
    }
}

