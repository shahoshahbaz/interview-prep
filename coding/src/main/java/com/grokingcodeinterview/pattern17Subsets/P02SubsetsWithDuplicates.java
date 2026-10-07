package com.grokingcodeinterview.pattern17Subsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
iven a set of numbers that might contain duplicates, find all of its distinct subsets.

Example 1:

Input: [1, 3, 3]
Output: [], [1], [3], [1,3], [3,3], [1,3,3]
Example 2:

Input: [1, 5, 3, 3]
Output: [], [1], [5], [3], [1,5], [1,3], [5,3], [1,5,3], [3,3], [1,3,3], [3,3,5], [1,5,3,3]
 */
public class P02SubsetsWithDuplicates {
    public static List<List<Integer>> findSubsets(int[] nums) {
        List<List<Integer>> subSets = new ArrayList<>();
        Arrays.sort(nums);
        // create empty subset and added to subSets
        subSets.add(new ArrayList<>());
        int startIndex =0; // to handle duplicates
        int endIndex =0; // to handle duplicates

        // iterate over the nums
        for(int i =0;i<nums.length;i++) {
            startIndex = 0; // reset startIndex for each number
            // if current and previous are same , update startIndex to endIndex of previous number
            if (i > 0 && nums[i] == nums[i - 1]) {
                startIndex = endIndex + 1;
            }
            endIndex = subSets.size() - 1; // why? because we will be adding new subsets to subSets
            int n = subSets.size();
            for (int j = startIndex; j < n; j++) { // why we start from startIndex? to avoid duplicates
                // copy the current subset
                List<Integer> newSubset = new ArrayList<>(subSets.get(j));
                // add the current number
                newSubset.add(nums[i]);
                // add the new subset to subSets
                subSets.add(newSubset);
            }
        }
        return subSets;

    }
     // main method to test the code
    public static void main(String[] args) {
        // the array contains duplicates , print all distinct subsets and expected output
        // in console
        int[] nums1 = {1, 3, 3};
        System.out.println("Input: [1, 3, 3]");
        System.out.println("Output: " + findSubsets(nums1));
        System.out.println("expected Output: [], [1], [3], [1,3], [3,3], [1,3,3]");
        int[] nums2 = {1, 5, 3, 3};
        System.out.println("Input: [1, 3]");
        System.out.println("Output: " + findSubsets(nums2));
        System.out.println("expected Output: [], [1], [5], [3], [1,5], [1,3], [5,3], [1,5,3], [3,3], [1,3,3], [3,3,5], [1,5,3,3]");
        
    }
}

