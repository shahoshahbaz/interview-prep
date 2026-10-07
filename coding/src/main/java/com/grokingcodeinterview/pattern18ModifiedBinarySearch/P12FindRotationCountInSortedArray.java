package com.grokingcodeinterview.pattern18ModifiedBinarySearch;

/*
Problem Statement:
Given an array of numbers which is sorted in ascending order and is rotated â€˜kâ€™ times around a pivot, find â€˜kâ€™.
You can assume that the array does not have any duplicates.
Note: You need to solve the problem in  time complexity O(log n).

Example 1: Input: [10, 15, 1, 3, 8] Output: 2
Explanation: The array has been rotated 2 times.
Input: [4, 5, 7, 9, 10, -1, 2] Output: 5
Explanation: The array has been rotated 5 times.
Example 3: Input: [1, 3, 8, 10] Output: 0
Explanation: The array has not been rotated.
Constraints:
1 <= arr.length <= 5000
-10^4 <= arr[i] <= 10^4
All values of nums are unique.
arr is an ascending array that is possibly rotated.
 */

import java.util.Arrays;

import static com.Utility.makeItBold;

/**
 * soreted in ascending order, is rotated K time, here we are looking to find smallest element index
 Input: [4, 5, 7, 9, 10, -1, 2] otput: 5
    dry-run example:

 index:  0  1  2  3   4   5   6
 value:  4, 5, 7, 9, 10, -1, 2
 (0, 6) mid = 3, nums[mid] > nums[end], left side is sorted -> start = mid + 1
 (4, 6) mid = 5, nums[mid] < nums[end], right side is sorted -> end = mid
 (4, 5) mid = 4, nums[mid] > nums[end], left side is sorted -> start = mid + 1
 (5, 5) start == end â†’ index of smallest element return start = 5


 */
public class P12FindRotationCountInSortedArray {
    public static int findNumberRotationsInSortedArray(int[] nums){
        int start = 0;
        int end = nums.length -1;
        if (nums[start]< nums[end]) return 0;

        while(start< end){
            int mid = start + (end - start)/2;
            if (nums[mid]< nums[end]) {
                end = mid;
            }else if (nums[mid]> nums[end]) {
                start = mid + 1;
            }
        }



        return start;
    }

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("P12. Rotation Count");
        System.out.println("==============================================");
        int[] numsP12 = {10, 15, 1, 3, 8};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( findNumberRotationsInSortedArray(numsP12) +"") + " Expected: 2");
        numsP12 = new int[]{4, 5, 7, 9, 10, -1, 2};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( findNumberRotationsInSortedArray(numsP12) +"") + " Expected: 5");
        numsP12 = new int[]{1, 3, 8, 10};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( findNumberRotationsInSortedArray(numsP12) +"") + " Expected: 0");
        numsP12 = new int[]{3,4,5,1,2};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( findNumberRotationsInSortedArray(numsP12) +"") + " Expected: 3");
        numsP12 = new int[]{2,1};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( findNumberRotationsInSortedArray(numsP12) +"") + " Expected: 1");



    }

}

