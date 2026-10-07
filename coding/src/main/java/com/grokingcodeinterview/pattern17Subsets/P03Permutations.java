package com.grokingcodeinterview.pattern17Subsets;

import java.util.ArrayList;
import java.util.List;

/*
Given a set of distinct numbers, find all of its permutations.

Permutation is defined as the re-arranging of the elements of the set. For example, {1, 2, 3} has the following six permutations:

{1, 2, 3} {1, 3, 2} {2, 1, 3} {2, 3, 1} {3, 1, 2} {3, 2, 1}

If a set has  distinct elements it will have  permutations.

Example 1:

Input: [1,3,5]
Output: [1,3,5], [1,5,3], [3,1,5], [3,5,1], [5,1,3], [5,3,1]
Constraints:

1 <= nums.length <= 6
-10 <= nums[i] <= 10
All the numbers of nums are unique.
 */
public class P03Permutations {
    public static List<List<Integer>> findPermutations(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums,new ArrayList<>(), new boolean[nums.length], result);
        return result;
    }

    private static void backtrack(int[] nums, List<Integer> current, boolean[] used,  List<List<Integer>> result ){
        // base case
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;

        }
        // try each number
        for (int i=0; i< nums.length; i++){
            if(used[i]) continue; // skip numbers already in current premutation

            current.add(nums[i]);
            used[i] = true;

            // explore
            backtrack(nums, current, used, result);
            current.remove(current.size() -1);
            used[i] = false;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 5};
        List<List<Integer>> permutations = findPermutations(nums);
        System.out.println("Input: [1, 3, 5]");
        System.out.println("Permutations:");
        for (List<Integer> p : permutations) {
            System.out.print(p + " ");
        }
        System.out.println();

        //add mor test cases
        int[] nums2 = {1, 2};
        List<List<Integer>> permutations2 = findPermutations(nums2);
        System.out.println("Input: [1, 2]");
        System.out.println("Permutations:");
        for (List<Integer> p : permutations2) {
            System.out.print(p + " ");
        }
    }
}

