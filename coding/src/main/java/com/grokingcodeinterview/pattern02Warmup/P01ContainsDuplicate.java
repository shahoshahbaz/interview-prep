package com.grokingcodeinterview.pattern02Warmup;

import java.util.HashSet;
import java.util.Set;

/*Given an integer array nums,
return true if any value appears at least twice in the array, and return false if every element is distinct.

Examples
Example 1:
Input: nums= [1, 2, 3, 4]
Output: false
Explanation: There are no duplicates in the given array.
Example 2:
Input: nums= [1, 2, 3, 1]
Output: true
Explanation: '1' is repeating.
*/

public class P01ContainsDuplicate {

public static boolean containsDuplicate(int[] nums) {
    Set<Integer> set = new HashSet<>();

    for (int num: nums){
        if (!set.add(num)){
            return true;
        }
    }

    return false;
}
    public static void main(String[] args) {


    // add some test cases
    int[] nums1 = {1, 2, 3, 4};
    System.out.println("Test Case 1: " + containsDuplicate(nums1)); // false
    int[] nums2 = {1, 2, 3, 1};
    System.out.println("Test Case 2: " + containsDuplicate(nums2)); // true
    }
}
