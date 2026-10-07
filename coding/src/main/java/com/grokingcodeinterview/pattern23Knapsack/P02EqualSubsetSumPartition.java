package com.grokingcodeinterview.pattern23Knapsack;

import java.util.Arrays;

import static com.Utility.makeItBold;
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
public class P02EqualSubsetSumPartition {
     /**
     * Brute Force: Time Complexity: O(2^n) where n is the number of elements in the input array.
     * This is because in the worst case, we will be exploring all possible subsets of the input array.
     * how it works:
     * 1. Calculate the sum of all elements in the input array.
     * If the sum is odd, we can immediately return false, as it's impossible to partition an odd sum into two equal subsets.
     * 2. If the sum is even, we will try to find a subset of the input array that sums up to sum/2.
     * We can use a recursive approach to explore all possible subsets of the input array.
     * We will start with the first element and recursively explore two possibilities:
     *  1. Include the current element in the subset
     *  2. Exclude the current element from the subset
     *
     *
     */
    /**
     *
     *
     */

    public static boolean canPartitionBruteForce(int[] nums){
         int sum =0;
         for (int num: nums)
             sum+= num;

         if (sum %2 !=0)
             return false;

         return canPartitionBruteForce(nums, sum/2, 0);

     }

     private static boolean canPartitionBruteForce(int[] nums, int sum, int currentIndex){
         // base check
         if (sum ==0)
             return true;
         if (nums.length == 0 || currentIndex>= nums.length)
             return false;

         if(nums[currentIndex]<= sum){
             // Include the number
             if(canPartitionBruteForce(nums, sum - nums[currentIndex], currentIndex +1))
                 return true;
         }
        // Exclude the number
         return canPartitionBruteForce(nums, sum, currentIndex + 1);
     }
     public static boolean canPartitionTopDown(int[] nums){

         int sum = 0;
         for(int num: nums)
             sum += num;

         if(sum % 2 != 0) return false;

         Boolean[][] dp = new Boolean[nums.length][sum/2 +1];
         return canPartitionTopDown(dp, nums, sum/2, 0);
     }

     private static boolean canPartitionTopDown(Boolean[][] dp, int[] nums, int sum, int currentIndex){
        if (sum == 0) return true;

        if (nums.length ==0 || currentIndex>= nums.length) return false;
        if (dp[currentIndex][sum] == null) {
            if (nums[currentIndex] <= sum) {
                if (canPartitionTopDown(dp, nums, sum - nums[currentIndex], currentIndex + 1)) {
                    dp[currentIndex][sum] = true;
                    return true;
                }
            }

            dp[currentIndex][sum] = canPartitionTopDown(dp, nums, sum, currentIndex + 1);
        }
        return dp[currentIndex][sum];

     }

    /**
     * how it works:
     * 1. Calculate the sum of all elements in the input array.
     * If the sum is odd, we can immediately return false, as it's impossible to partition
     * 2. If the sum is even, we will try to find a subset of the input array that sums up to sum/2.
     * in bottom up approach we will:
     * 1. Create a 2D boolean array. the rows will represent the elements and the columns will represent the possible sums from 0 to sum/2.
     * 2. first column will be true because we can always make the sum 0 with an empty set.
     * 3. first row will be true if the first element is equal to the column index, otherwise false.
     * 4. we will fill the rest of the dp array by iterating through the elements and the possible sums.
     * for each element and sum, we will check if we can make the sum by
     * 1. including the current element
     * 2. excluding the current element
     * 
     * /**
      Input: {1, 2, 3, 4}  Output: True
     
      sum = 10  sum/2 = 5  n = 4
     
               s â†’
             0  1  2  3  4  5
     
      0      T  F  F  F  F  F
     
      i = 1 (num[0] = 1)
      1      T  T  F  F  F  F
     
      i = 2 (num[1] = 2)
      2      T  T  T  T  F  F
     
      i = 3 (num[2] = 3)
      3      T  T  T  T  T  T
     
      i = 4 (num[3] = 4)
      4      T  T  T  T  T  T
     
      Final: dp[4][5] = True
     /
     *
     *
     */
    public static boolean canPartitionBottomUp(int[] nums){
         int n = nums.length;
         int sum = 0;
         for(int num: nums)
             sum += num;
         if(sum %2 == 1) return false;

          int target = sum / 2;

         // first column
        /**
         * Why first column is set to true: The first column represents sum = 0.
         We can always make sum 0 by selecting no elements (empty subset), so dp[row][0] = true for all rows.
        */
        boolean[][] dp = new boolean[n][sum + 1]; // dp[i][s] = true if we can achieve sum s using elements from index 0 to i
         for (int  row =0; row< n; row++)
             dp[row][0] = true; // we can always make the sum 0 with an empty set, so we set the first column to true.

        // first row
        /**
        Why first row is set conditionally: The first row represents using only the first element nums[0]. We can only achieve:
        Sum 0 (always true, handled by first column)
        Sum
        All otequal to nums[0] value (set to true if nums[0] == s)her sums are false (impossible with just one element
        */
        for (int s = 1; s<= sum; s++)
             dp[0][s] = nums[0] == s;
        // give my an example in 2D array for this dp array:
        /**
            * nums = {1, 2, 3, 4} sum = 5, n = 4
            * sum /=2; ==> sum= 2 => sum +1 = 3
            * dp array will be: dp[4][3]
            *         0      1     2
         *         ----------------------
         *       0 | true  false  false
         *       1 | true  true   false
         *       2 | true  true   true
         *       3 | true  true   true
         *
         *
         */


         for (int i =1; i< n; i++){
             for (int s = 1; s<= sum; s++){
                 if (dp[i-1][s]){
                     dp[i][s] = dp[i-1][s]; // skip the current element
                 }else if(s>= nums[i]){
                     dp[i][s] = dp[i -1][s -nums[i]]; //include the currenet element
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
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBruteForce(numsP02) +"") + " ,Expected: true <== Brute Force");
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionTopDown(numsP02) +"") + " ,Expected: true <== Memoization");
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBottomUp(numsP02) +"") + " ,Expected: true <== Tabulation");
        numsP02 = new int[]{1, 1, 3, 4, 7};
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBruteForce(numsP02) +"") + " ,Expected: true <== Brute Force");
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionTopDown(numsP02) +"") + " ,Expected: true <== Memoization");
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBottomUp(numsP02) +"") + " ,Expected: true <== Tabulation");
        numsP02 = new int[]{2, 3, 4, 6};
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBruteForce(numsP02) +"") + " ,Expected: false <== Brute Force");
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionTopDown(numsP02) +"") + " ,Expected: false <== Memoization");
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBottomUp(numsP02) +"") + " ,Expected: false <== Tabulation");
        numsP02 = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBruteForce(numsP02) +"") + " ,Expected: true <== Brute Force");
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionTopDown(numsP02) +"") + " ,Expected: true <== Memoization");
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBottomUp(numsP02) +"") + " ,Expected: true <== Tabulation");

        numsP02 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBruteForce(numsP02) +"") + " ,Expected: false <== Brute Force");
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionTopDown(numsP02) +"") + " ,Expected: false <== Memoization");
        System.out.println("Input: " + Arrays.toString(numsP02) + ", outPut: " + makeItBold(canPartitionBottomUp(numsP02) +"") + " ,Expected: false <== Tabulation");


    }
}

