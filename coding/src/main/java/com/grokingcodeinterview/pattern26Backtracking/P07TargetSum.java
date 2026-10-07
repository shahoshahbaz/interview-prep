package com.grokingcodeinterview.pattern26Backtracking;

import java.util.HashMap;

/*
we can imporve the pervious solution by using memoization to store the results of subproblems and avoid redundant calculations.
 */

/**
 * time complexity: O(n * sum),
 * where n is the length of the input array nums and sum is the total sum of all elements in nums.
 * In the worst case, we may need to explore all possible combinations of adding and subtracting elements,
 * which can result in a time complexity of O(2^n).
 * However, with memoization,
 * we can reduce the number of redundant calculations and achieve a time complexity of O(n * sum).
 *
 */
public class P07TargetSum {

    public static int findTargetSumWays(int[] nums, int target) {
        HashMap<String, Integer> map = new HashMap<>();
        return backtrack(nums,  0, 0, target, map);

    }
    public static int backtrack(int[] nums, int index, int currSum, int target, HashMap<String, Integer>  memo){

        if(index == nums.length){
            return currSum == target? 1:0;
        }
        String key = index +"," + currSum;
        if(memo.containsKey(key)) return memo.get(key);

        int addWays = backtrack(nums, index+1, currSum + nums[index], target, memo);
        int subtractWays = backtrack(nums, index+1, currSum- nums[index], target, memo);
        int ways = addWays + subtractWays;

        memo.put(key, ways);

        // where backtracking happens, we explore both adding and subtracting the current number and
        // sum the ways to reach the target from both paths.no,
        // how we backtrack the element we added or subtracted,
        // we don't need to explicitly remove it from currSum
        // because we are passing a new value to the next recursive call.
        return addWays + subtractWays;
    }

}

