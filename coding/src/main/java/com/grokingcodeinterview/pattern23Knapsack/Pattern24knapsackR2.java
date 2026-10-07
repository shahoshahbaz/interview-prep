package com.grokingcodeinterview.pattern23Knapsack;

import java.util.Arrays;

import static com.Utility.makeItBold;

public class Pattern24knapsackR2 {
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

    public static boolean hasSubSetEqualsToSum_bottomUp(int[] nums, int sum){
        if (nums == null || nums.length == 0) return false;

        int n = nums.length;
        boolean [][] dp = new boolean[n][sum+1];

        // first columns
        for (int i =0; i<n; i++)
            dp[i][0] = true;
        // first row
        for (int s =0; s<=sum; s++)
            dp[0][s] = nums[0]==s;

        for (int i =1; i<n; i++){
            for (int s =1; s<= sum; s++){
                if (dp[i-1][s]){
                    dp[i][s] = dp[i-1][s];
                }else if (nums[i]<=s){
                    dp[i][s] = dp[i-1][s - nums[i]];
                }
            }
        }

        return dp[n-1][sum];
    }
    /*
Statement
Given a set of positive numbers, find if we can partition it into two subsets such that the sum of elements
 in both subsets is equal.

Example 1: Input: {1, 2, 3, 4} Output: True
Explanation: The given set can be partitioned into two subsets with equal sum: {1, 4} & {2, 3}
Example 2: Input: {1, 1, 3, 4, 7} Output: True
Explanation: The given set can be partitioned into two subsets with equal sum: {1, 3, 4} & {1, 7}
Example 3: Input: {2, 3, 4, 6} Output: False
Explanation: The given set cannot be partitioned into two subsets with equal sum.
Constraints:

1 <= nums.length <= 200
1 <= nums[i] <= 100
 */
    /**
     * Input: {1, 2, 3, 4} Output: True
     *   sum = 10 sum/2 = 5 n = 4
     *   num[0] =1
     *     0 1 2 3 4 5
     * 1 0 t t f f f f
     * 2 1 t t t
     * 3 2 t
     * 4 3 t
     * i =1, s =1
     *     s = 1 : dp[i -1][s] : dp[0][1] true
     *     s = 2: dp[i -1][s] : dp[0] [2] false then s>=nums[i] 2>=2 yes dp[i][s] = dp[i -1][s-nums[i]] = dp[0][2 - 2] true
     */
   public static boolean canPartitionBottomUp(int[] nums){
        // find the sum of all elements
        int sum =0;
        for(int num: nums)
            sum += num;

        if(sum  %2 == 1) return false;
        sum = sum /2;
        int n = nums.length;
        Boolean[][] dp = new Boolean [nums.length][sum +1];

        // this is 0/1 knapsack problem.

        // first columns
        for (int i =0; i< n; i++ ){
            dp[i][0] = true;
        }

        for (int s =1; s<= sum; s++){
            dp[0][s] = nums[0] == s;
        }

        for (int i =1; i<n ; i++){
            for(int s =1; s<= sum; s++){
                if(dp[i-1][s]){
                    dp[i][s] = dp[i-1][s];
                }else if(s>= nums[i]){
                    dp[i][s] = dp[i -1][s -nums[i]];
                }
            }
        }

        return dp[n-1][sum];

    }
    public static void main(String[] args) {

        System.out.println("===========================");
        System.out.println("P02. Equal Subset Sum Partition");
        System.out.println("===========================");

        int[] numsP02 = {1, 2, 3, 4};
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBottomUp(numsP02) +"") + " ,Expected: true <== Tabulation");
        numsP02 = new int[]{1, 1, 3, 4, 7};
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBottomUp(numsP02) +"") + " ,Expected: true <== Tabulation");
        numsP02 = new int[]{2, 3, 4, 6};
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBottomUp(numsP02) +"") + " ,Expected: false <== Tabulation");
        numsP02 = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBottomUp(numsP02) +"") + " ,Expected: true <== Tabulation");

        numsP02 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBottomUp(numsP02) +"") + " ,Expected: false <== Tabulation");

        System.out.println("==================================");
        System.out.println("P03. Subset Sum");
        System.out.println("==================================");
        int[] numsP03 = {1, 2, 3, 7};
        int sumP03 = 6;
//        System.out.println("Inputs: " + Arrays.toString(numsP03) + ", sum = " + sumP03
//                + " Output: " + makeItBold(hasSubsetEqualsToSum_bf(numsP03, sumP03) +"") + ", Expected: true <== Brute Force");
        System.out.println("Inputs: " + Arrays.toString(numsP03) + ", sum = " + sumP03
                + " Output: " + makeItBold(hasSubSetEqualsToSum_bottomUp(numsP03, sumP03) +"") + ", Expected: true <== Tabulation");
        numsP03 = new int[]{1, 2, 7, 1, 5};
        sumP03 = 10;
//        System.out.println("Inputs: " + Arrays.toString(numsP03) + ", sum = " + sumP03
//                + " Output: " +makeItBold(hasSubsetEqualsToSum_bf(numsP03, sumP03) +"") + ", Expected: true <== Brute Force");
        System.out.println("Inputs: " + Arrays.toString(numsP03) + ", sum = " + sumP03
                + " Output: " + makeItBold(hasSubSetEqualsToSum_bottomUp(numsP03, sumP03) +"") + ", Expected: true <== Tabulation");
        numsP03 = new int[]{1, 3, 4, 8};
        sumP03 = 6;
//        System.out.println("Inputs: " + Arrays.toString(numsP03) + ", sum = " + sumP03
//                + " Output: " +makeItBold(hasSubsetEqualsToSum_bf(numsP03, sumP03) +"") + ", Expected: false <== Brute Force");

        System.out.println("Inputs: " + Arrays.toString(numsP03) + ", sum = " + sumP03
                + " Output: "  + makeItBold(hasSubSetEqualsToSum_bottomUp(numsP03, sumP03) +"") + ", Expected: false <== Tabulation");




    }
}

