package com.grokingcodeinterview.pattern26Backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.Utility.makeItBold;

/*
     Problem Statement:
    Given an array of distinct positive integers candidates and a target integer target,
    return a list of all unique combinations of candidates
     where the chosen numbers sum to target.
     You may return the combinations in any order.

    The same number may be chosen from candidates an unlimited number of times.
    Two combinations are unique if the frequency of at least one of the chosen numbers is different.

    Example 1: Input: candidates = [2, 3, 6, 7], target = 7 Output: [[2, 2, 3], [7]]
    Explanation: The elements in these two combinations sum up to 7.
    Example 2: Input: candidates = [2, 4, 6, 8], target = 10  Output: [[2,2,2,2,2], [2,2,2,4], [2,2,6], [2,4,4], [2,8], [4,6]]
    Explanation: The elements in these six combinations sum up to 10.
    Constraints:

    1 <= candidates.length <= 30
    2 <= candidates[i] <= 40
    All elements of candidates are distinct.
    1 <= target <= 40
 */

/**
 * Time: O(N^(T/M)) â€” N candidates branching at each level, max depth T/M (target Ã· smallest candidate).
 * power: becuase we have N choies at each level
 * T/M: becuase we can have at most T/M levels of recursion,
 * where T is the target and M is the minimum value in candidates.
 * Space: O(T/M) recursion depth, excluding the output list.
 */
public class P01CombinationSum {
    public static List<List<Integer>> combinationSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backTrack(nums, target,0,  new ArrayList<>(), result);
        return result;
    }

    public static void backTrack(int[] nums, int remaining, int start, List<Integer> path, List<List<Integer>> result){
        if (remaining ==0 ){
            result .add(new ArrayList<>(path));
            return;
        }
        if (remaining <0) return;
        // The algorithm only allows choosing current element or elements after it.
        //It never goes backward. so uniqueness is guaranteed by the start index.
        for (int i = start; i< nums.length; i++){
            int current = nums[i];
            path.add(current);
            //by passing i,-NOT i+1-,  the recursion can pick the same index again
            backTrack(nums, remaining - current, i, path, result);

            path.remove (path.size() -1);

        }
    }


    public static void main(String[] args) {

        System.out.println("===========================");
        System.out.println("P01. Combination Sum");
        System.out.println("===========================");
        int[] numsP01 = {2, 3, 6, 7};
        int targetP01 = 7;
        List<List<Integer>> resultP01 = combinationSum(numsP01, targetP01);
        System.out.println("Input:  " + Arrays.toString(numsP01) + ", target = " + targetP01 +" ,Output: " + makeItBold(resultP01 +"") +" ,Expected: [[2, 2, 3], [7]]");

          numsP01 = new int[]{2, 4, 6, 8};
         targetP01 = 10;
        List<List<Integer>> resultP02 = combinationSum(numsP01, targetP01);
        System.out.println("Input:  " + Arrays.toString(numsP01) + ", target = " + targetP01 +" ,Output: " + makeItBold(resultP02 +"") +" ,Expected: [[2, 2, 2, 2, 2], [2, 2, 2, 4], [2, 2, 6], [2, 4, 4], [2, 8], [4, 6]]");

        numsP01 = new int[]{2, 3, 5};
        targetP01 = 8;
        List<List<Integer>> resultP03 = combinationSum(numsP01, targetP01);
        System.out.println("Input:  " + Arrays.toString(numsP01) + ", target = " + targetP01 +" ,Output: " + makeItBold(resultP03 +"") +" ,Expected: [[2, 2, 2, 2], [2, 3, 3], [3, 5]]");


    }




}

