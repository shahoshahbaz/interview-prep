package com.grokingcodeinterview.pattern23Knapsack;

import java.util.Arrays;

import static com.Utility.makeItBold;

public class Pattern24knapsackR3 {
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
        int n = nums.length;

        boolean[][] dp = new boolean[n][sum +1];

        // first col
        for (int i =0; i< n; i++){
            dp[i][0] = true;
        }

        // first row
        for (int s =0; s<= sum; s++){
            dp[0][s] = nums[0]== s;
        }

        for (int i =1; i< n; i++){
            for (int s = 1; s<= sum; s++){
                if(dp[i-1][s]){
                    dp[i][s] = dp[i-1][s];
                }else if (nums[i]<= s){
                    dp[i][s] = dp[i-1][s- nums[i]];
                }
            }
        }
        return dp[n-1][sum];
    }
     /*
    Problem Statement
    Given two integer arrays to represent weights and profits of â€˜Nâ€™ items,
    we need to find a subset of these items which will give us maximum profit
    such that their cumulative weight is not more than a given number â€˜C.â€™
    Each item can only be selected once, which means either we put an item in the knapsack
    or we skip it.
    example:
    Input: profits = [1, 6, 10, 16], weights = [1, 2, 3, 5], capacity = 7 output: 22
    Input: profits = [1, 6, 10, 16], weights = [1, 2, 3, 5], capacity = 6 output: 17
    input: profits = [1, 6, 10, 16], weights = [1, 2, 3, 5], capacity = 5 output: 16
     */

   public static int knapsack(int[] profits, int[] weights, int capacity){
       // edge case
       if (capacity == 0 || profits.length == 0 ) return 0;
       int n = profits.length;
       int[][] dp = new int[n][capacity +1];

       // populate the first row
       for (int c =0; c<= capacity; c++){
           if (weights[0]<=c)
               dp[0][c] = profits[0];
       }

       // populate the first column
       for (int i =0; i< n; i++){
           dp[i][0] =0;
       }

       for (int i =1; i<n; i++){
           for(int c =1; c<= capacity; c++){

               int profit1 =0; int profit2 = 0;

               // profit1: exclude the current profit
               profit1 = dp[i-1][c];

               // profit2 = include the current profit
               if (weights[i]<=c)
                   profit2 = profits[i] +dp[i-1][c- weights[i]];

               dp[i][c] =Math.max(profit1, profit2);
           }
       }

       return dp[n-1][capacity];

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


   public static boolean canPartitionBottomUp(int[] nums){
       int sum =0;
       for(int num: nums)
           sum += num;

       if(sum %2 == 1) return false;

       sum /=2 ;
       int n = nums.length;

       boolean[][] dp = new boolean [n][sum+1];

       // first column, so for some zero we can skip all the elements

       for (int i =0; i< n; i++)
           dp[i][0]= true;

       // first row, the only time that sum equals to nums[0] when they are equals

       for (int s =0; s<= sum; s++)
           dp[0][s] = nums[0]== s;

       for (int i =1; i<n ; i++){
           for (int s = 1; s<=sum; s++){
               // skip the current element
               if (dp[i-1][s]){
                   dp[i][s] = dp[i-1][s];
               }else if (nums[i] <= s){
                   dp[i][s] = dp[i-1][s-nums[i]];
               }
           }
       }

       return dp[n-1][sum];
   }
     public static void main(String[] args) {
         System.out.println("==================================");
         System.out.println("P01. Knapsack");
         System.out.println("==================================");
         int[] profitsP01 = {1, 6, 10, 16};
         int[] weightsP01 = {1, 2, 3, 5};
         int capacityP01 = 7;
         System.out.println("Inputs: " + "profits = " + Arrays.toString(profitsP01) + ", weights = " + Arrays.toString(weightsP01) + ", capacity = " + capacityP01
                 + " Output: " + makeItBold(knapsack(profitsP01, weightsP01, capacityP01) +"") + ", Expected: 22");
         // give more example

         profitsP01 = new int[]{1, 6, 10, 16};
         weightsP01 = new int[]{1, 2, 3, 5};
         capacityP01 = 6;
         System.out.println("Inputs: " + "profits = " + Arrays.toString(profitsP01) + ", weights = " + Arrays.toString(weightsP01) + ", capacity = " + capacityP01
                 + " Output: " + makeItBold(knapsack(profitsP01, weightsP01, capacityP01) +"") + ", Expected: 17");
         // give me edge case example
         profitsP01 = new int[]{1, 6, 10, 16};
         weightsP01 = new int[]{1, 2, 3, 5};
         capacityP01 = 5;
         System.out.println("Inputs: " + "profits = " + Arrays.toString(profitsP01) + ", weights = " + Arrays.toString(weightsP01) + ", capacity = " + capacityP01
                 + " Output: " + makeItBold(knapsack(profitsP01, weightsP01, capacityP01) +"") + ", Expected: 16");

         // edge case: capacity is 0
         profitsP01 = new int[]{1, 6, 10, 16};
         weightsP01 = new int[]{1, 2, 3, 5};
         capacityP01 = 0;
         System.out.println("Inputs: " + "profits = " + Arrays.toString(profitsP01) + ", weights = " + Arrays.toString(weightsP01) + ", capacity = " + capacityP01
                 + " Output: " + makeItBold(knapsack(profitsP01, weightsP01, capacityP01) +"") + ", Expected: 0");

         // edge case: no items
         profitsP01 = new int[]{};
         weightsP01 = new int[]{};
         capacityP01 = 7;
         System.out.println("Inputs: " + "profits = " + Arrays.toString(profitsP01) + ", weights = " + Arrays.toString(weightsP01) + ", capacity = " + capacityP01
                 + " Output: " + makeItBold(knapsack(profitsP01, weightsP01, capacityP01) +"") + ", Expected: 0");
         // edge case: all items are too heavy
         profitsP01 = new int[]{1, 6, 10, 16};
         weightsP01 = new int[]{8, 9, 10, 11};
         capacityP01 = 7;
         System.out.println("Inputs: " + "profits = " + Arrays.toString(profitsP01) + ", weights = " + Arrays.toString(weightsP01) + ", capacity = "
                 + " Output: " + makeItBold(knapsack(profitsP01, weightsP01, capacityP01) +"") + ", Expected: 0");

         // more complex case
         profitsP01 = new int[]{60, 100, 120};
         weightsP01 = new int[]{10, 20, 30};
         capacityP01 = 50;
         System.out.println("Inputs: " + "profits = " + Arrays.toString(profitsP01) + ", weights = " + Arrays.toString(weightsP01) + ", capacity = " + capacityP01
                 + " Output: " + makeItBold(knapsack(profitsP01, weightsP01, capacityP01) +"") + ", Expected: 220");

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

