package com.grokingcodeinterview.pattern23Knapsack;

import com.ConsoleTestHarness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.ConsoleTestHarness.TestCase;
import static com.ConsoleTestHarness.TestRunner;

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
public class P01Knapsack {

    final static class KSInput {
        final int[] profits;
        final int[] weights;
        final int capacity;
        KSInput(int[] p, int[] w, int c) { this.profits = p; this.weights = w; this.capacity = c; }
    }

    public static int knapsackBruteForce(int[] profits, int[] weights, int capacity){
        return knapsackBruteForce(profits, weights, capacity, 0 );
    }
    public static int knapsackBruteForce(int[] profits, int[] weights, int capacity, int currentIndex){
        if(capacity <=0 || currentIndex >= profits.length) return 0;

        // including the currentIndex
        int profit1 = 0;
        if(weights[currentIndex]<= capacity){
            profit1 = profits[currentIndex] + knapsackBruteForce(profits, weights, capacity- weights[currentIndex], currentIndex +1);
        }
        //excluding currentIndex
        int profit2 = knapsackBruteForce(profits, weights, capacity, currentIndex +1);

        return Math.max(profit1, profit2);

    }
    public static int knapsackTopDown(int[] profits, int[] weights, int capacity){

        Integer[][] dp = new Integer[profits.length][ capacity +1];
        return knapsackTopDown(dp, profits, weights, capacity,0);


    }
    public static int knapsackTopDown(Integer[][] dp, int[] value, int[] weights, int capacity, int currentIndex){
        if(currentIndex <0 || currentIndex >= value.length) return 0;

        if(dp[currentIndex][capacity] != null){
            return dp[currentIndex][capacity];
        }
        int profit1 =0;
        if(weights[currentIndex]<= capacity)
            profit1 = value[currentIndex] + knapsackTopDown(dp, value, weights, capacity- weights[currentIndex], currentIndex+1);

        int profit2 =  knapsackTopDown(dp, value, weights, capacity, currentIndex+1);;
        dp[currentIndex][capacity] = Math.max(profit1, profit2);

        return dp[currentIndex][capacity];
    }

    public static int knapsackBottomUp(int[] value, int[] weights, int capacity){
        int n = value.length;
        if (capacity ==0 || n ==0) return 0;
        // dp[i][...] means "first i items considered" — a COUNT, not a direct array index.
        int[][] dp = new int[n +1][capacity + 1];
        for (int i = 1; i <= n; i++) {

            // dp counts items starting from 1 (dp[0] = "zero items", clean base case).
            // weights[]/value[] are 0-indexed. So "the i-th item" in dp's counting
            // physically lives at weights[i-1] / value[i-1].
            int currWeight = weights[i - 1];
            int currValue  = value[i - 1];

            for (int w = 0; w <= capacity; w++) {
                int profit1 = 0, profit2 = 0;

                // Option 1: EXCLUDE the item
                profit1 = dp[i - 1][w];

                // Option 2: INCLUDE the item
                if (currWeight <= w) {
                    profit2 = currValue + dp[i - 1][w - currWeight];
                }

                dp[i][w] = Math.max(profit1, profit2);
            }
        }
        return dp[n][capacity];
    }
    public static int knapsackBottomUp1D(int[] values, int[] weights, int capacity){
        if(capacity<=0 || values.length ==0 || values.length != weights.length  ) return 0;
        int n = values.length;
        // dp[w]: max profit for capacity w, considering all items so far.t
        // Why 1D: dp[i][w] only ever reads row i-1 — never needs older rows.
        // So keeping all n+1 rows is wasted space;
        // one row, reused, is enough. O(n×capacity) → O(capacity).
        int [] dp = new int[capacity +1 ];
        for (int i =1; i<n+1; i++){
            int currValue = values[i-1];
            int currWeight = weights[i-1];
            // why backwards? by the time you compute dp[w],
            // every cell you might read (dp[w - currWeight], which is always a smaller index)
            // has not been touched yet this pass.
            // So it's guaranteed to still be "the value from before this item existed."
            for (int w = capacity; w>= currWeight; w--){
                // include
                int include = currValue + dp[w - currWeight];
                int exclude = dp[w];
                dp[w] = Math.max(include, exclude);
            }

        }
        return dp[capacity];
    }
    public static List<Integer> knapsackGetSelectedItems(int[] profits, int[] weights, int capacity){
        List<Integer> selectedItems = new ArrayList<>();
        if (capacity <= 0 || profits.length == 0 || profits.length != weights.length)
            return selectedItems;

        int n = profits.length;

        // 2D DP table
        int[][] dp = new int[n][capacity + 1];

        // initialize first row
        for (int c = 0; c <= capacity; c++) {
            if (weights[0] <= c)
                dp[0][c] = profits[0];
        }

        // fill DP table
        for (int i = 1; i < n; i++) {
            for (int c = 0; c <= capacity; c++) {

                int include = 0;

                if (weights[i] <= c)
                    include = profits[i] + dp[i - 1][c - weights[i]];

                int exclude = dp[i - 1][c];

                dp[i][c] = Math.max(include, exclude);
            }
        }

        // ---------- Backtracking ----------
        int c = capacity;
        for (int i = n-1; i>0; i--){

            if (c >= weights[i] &&
                    dp[i][c] == profits[i] + dp[i - 1][c - weights[i]]){
                selectedItems.add(i);
                c = c -weights[i];
            }
        }
        if(c>= weights[0] && dp[0][c] == profits[0])
            selectedItems.add(0);

        Collections.reverse(selectedItems);

        return selectedItems;

    }
    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("P01. Knapsack");
        System.out.println("==================================");


        List<TestCase<KSInput, Integer>> casesP01 = List.of(
                new TestCase<>("Basic cap=7",
                        new KSInput(new int[]{1,6,10,16}, new int[]{1,2,3,5}, 7), 22),
                new TestCase<>("Basic cap=6",
                        new KSInput(new int[]{1,6,10,16}, new int[]{1,2,3,5}, 6), 17),
                new TestCase<>("Basic cap=5",
                        new KSInput(new int[]{1,6,10,16}, new int[]{1,2,3,5}, 5), 16),
                new TestCase<>("Edge cap=0",
                        new KSInput(new int[]{1,6,10,16}, new int[]{1,2,3,5}, 0), 0),
                new TestCase<>("Edge empty items",
                        new KSInput(new int[]{}, new int[]{}, 7), 0),
                new TestCase<>("Edge all heavy",
                        new KSInput(new int[]{1,6,10,16}, new int[]{8,9,10,11}, 7), 0),
                new TestCase<>("Classic cap=50",
                        new KSInput(new int[]{60,100,120}, new int[]{10,20,30}, 50), 220));

        Function<KSInput, String> prettyInput = (KSInput in) ->
                "profits=" + Arrays.toString(in.profits) +
                        ", weights=" + Arrays.toString(in.weights) +
                        ", capacity=" + in.capacity;

        Function<Integer, String> prettyOutput = (Integer v) -> String.valueOf(v);


       TestRunner.runSuite("Knapsack - Brute Force", casesP01,
                in -> knapsackBruteForce(in.profits, in.weights, in.capacity),
                prettyInput, prettyOutput);
       TestRunner.runSuite("Knapsack-Top Down", casesP01, in->knapsackTopDown(in.profits, in.weights, in.capacity)
       , prettyInput, prettyOutput);

       TestRunner.runSuite("Knapsack- Bottom UP", casesP01,
               in -> knapsackBottomUp(in.profits, in.weights, in.capacity),
               prettyInput, prettyOutput);
       TestRunner.runSuite("Knapsack- Bottom UP-1D", casesP01,
               in -> knapsackBottomUp1D(in.profits, in.weights, in.capacity),
               prettyInput, prettyOutput);

       int[] profits = new int[]{1,6,10,16};
       int[] weights = new int[]{1,2,3,5};
       int capacity = 7;
       List<Integer> selectedItems = knapsackGetSelectedItems(profits, weights, capacity);
       System.out.println("Selected item indices: " + selectedItems +", Expected: [1, 3] (profits 6,16)");











    }
}

