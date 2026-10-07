package com.grokingcodeinterview.pattern23Knapsack;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a set of positive numbers,
 determine if a subset exists whose sum is equal to a given number â€˜Sâ€™.

Example 1: Input: {1, 2, 3, 7}, S=6 Output: True
The given set has a subset whose sum is '6': {1, 2, 3}
Example 2: Input: {1, 2, 7, 1, 5}, S=10 Output: True
The given set has a subset whose sum is '10': {1, 2, 7}
Example 3: Input: {1, 3, 4, 8}, S=6 Output: False
The given set does not have any subset whose sum is equal to '6'.
Constraints:

1 <= num.length <= 200
1 <= num[i] <= 100
 */
public class P03SubsetSum {
    public static boolean hasSubsetEqualsToSum_bf(int[] nums, int sum){

        return helper(nums, sum, 0);

    }
    private static boolean helper(int[] nums, int sum, int index){
        if (sum ==0 ) return true;
        if(index >= nums.length || sum <0 ) return false;

        if(nums[index]<= sum){
            if(helper(nums, sum - nums[index], index+1))
                return true;
        }

        return helper(nums, sum, index +1);
    }

    public static boolean hasSubSetEqualsToSum_bottomUp(int[] nums, int sum ){
        int n = nums.length;
        boolean[][] dp = new boolean[n][sum+1];
        for (int i =0; i<n;i++){
            dp[i][0] = true;

        }

        for(int s =0;s<= sum; s++){
            dp[0][s] = nums[0] ==s;
        }
        for (int i =1; i< n; i++){
            for(int s = 1; s<= sum; s++){
                if(dp[i-1][s]){
                    dp[i][s] = dp[i-1][s];
                }else if(nums[i]<s){
                    dp[i][s] = dp[i-1][s -nums[i]];
                }

            }
        }

        return dp[n -1][sum];

    }

    public static void main(String[] args){

        // give me som example
        System.out.println("==================================");
        System.out.println("P03. Subset Sum");
        System.out.println("==================================");
        int[] numsP03 = {1, 2, 3, 7};
        int sumP03 = 6;
        System.out.println("Inputs: " + Arrays.toString(numsP03) + ", sum = " + sumP03
                + " Output: " + makeItBold(hasSubsetEqualsToSum_bf(numsP03, sumP03) +"") + ", Expected: true <== Brute Force");
        System.out.println("Inputs: " + Arrays.toString(numsP03) + ", sum = " + sumP03
                + " Output: " + makeItBold(hasSubSetEqualsToSum_bottomUp(numsP03, sumP03) +"") + ", Expected: true <== Tabulation");
        numsP03 = new int[]{1, 2, 7, 1, 5};
        sumP03 = 10;
        System.out.println("Inputs: " + Arrays.toString(numsP03) + ", sum = " + sumP03
                + " Output: " +makeItBold(hasSubsetEqualsToSum_bf(numsP03, sumP03) +"") + ", Expected: true <== Brute Force");
        System.out.println("Inputs: " + Arrays.toString(numsP03) + ", sum = " + sumP03
                + " Output: " + makeItBold(hasSubSetEqualsToSum_bottomUp(numsP03, sumP03) +"") + ", Expected: true <== Tabulation");
        numsP03 = new int[]{1, 3, 4, 8};
        sumP03 = 6;
        System.out.println("Inputs: " + Arrays.toString(numsP03) + ", sum = " + sumP03
                + " Output: " +makeItBold(hasSubsetEqualsToSum_bf(numsP03, sumP03) +"") + ", Expected: false <== Brute Force");

        System.out.println("Inputs: " + Arrays.toString(numsP03) + ", sum = " + sumP03
                + " Output: "  + makeItBold(hasSubSetEqualsToSum_bottomUp(numsP03, sumP03) +"") + ", Expected: false <== Tabulation");



    }
}

