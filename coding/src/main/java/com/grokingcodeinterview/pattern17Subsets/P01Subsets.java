package com.grokingcodeinterview.pattern17Subsets;

import java.util.ArrayList;
import java.util.List;

/*
Problem Statement
Given a set with distinct elements, find all of its distinct subsets.

Example 1:

Input: [1, 3]
Output: [], [1], [3], [1,3]
Example 2:

Input: [1, 5, 3]
Output: [], [1], [5], [3], [1,5], [1,3], [5,3], [1,5,3]
Constraints:

1 <= nums.length <= 10
-10 <= nums[i] <= 10
All the numbers of nums are unique.
 */
public class P01Subsets {
    public static List<List<Integer>> findSubsets(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();

        // create empty subset
        subsets.add(new ArrayList<>());
        // iterate the nums
        for(int num:nums){
            int n = subsets.size();
            for (int i =0;i<n; i++){
                // copy the current list
                List<Integer> newSubsets= new ArrayList<>(subsets.get(i));
                // add the new number
                newSubsets.add(num);

                // add the new subset to to all subset
                subsets.add(newSubsets);

            }
        }
        return subsets;
    }
    // main method to test the code
    public static void main(String[] args) {
        // add 5 test cases with printof expected output in console
        int[] nums1 = {1, 3};
        System.out.println("Input: [1, 3]");
        System.out.println("Output: " + findSubsets(nums1));
        System.out.println("expected Output: [], [1], [3], [1,3]");
        int[] nums2 = {1, 5, 3};
        System.out.println("Input: [1, 5, 3]");
        System.out.println("Output: " + findSubsets(nums2));
        System.out.println("expected Output: [], [1], [5], [3], [1,5], [1,3], [5,3], [1,5,3]");
        int[] nums3 = {2};
        System.out.println("Input: [2]");
        System.out.println("Output: " + findSubsets(nums3));
        System.out.println("expected Output: [], [2]");
        int[] nums4 = {1, 2, 3, 4};
        System.out.println("Input: [1, 2, 3, 4]");
        System.out.println("Output: " + findSubsets(nums4));
        System.out.println("expected Output: [], [1], [2], [3], [4], [1,2], [1,3], [1,4], [2,3], [2,4], [3,4], [1,2,3], [1,2,4], [1,3,4], [2,3,4], [1,2,3,4]");
        int[] nums5 = {};
        System.out.println("Input: []");
        System.out.println("Output: " + findSubsets(nums5));
        System.out.println("expected Output: []");

         }

}

