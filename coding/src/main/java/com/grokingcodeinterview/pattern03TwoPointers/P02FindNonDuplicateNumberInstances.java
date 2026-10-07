package com.grokingcodeinterview.pattern03TwoPointers;

/*
 * Problem Statement
Given an array of sorted numbers,
* move all non-duplicate number instances at the beginning of the array in-place.
*  The non-duplicate numbers should be sorted
* and you should not use any extra space so
* that the solution has constant space complexity i.e.,
Move all the unique number instances at the beginning
*  of the array and after moving return the length of the subarray that has no duplicate in it.

Example 1:

Input: [2, 3, 3, 3, 6, 9, 9]
Output: 4
Explanation: The first four elements after moving element will be [2, 3, 6, 9].
Example 2:

Input: [2, 2, 2, 11]
Output: 2
Explanation: The first two elements after moving elements will be [2, 11].
Constraints:

1 <= nums.length <= 3 * 104
-100 <= nums[i] <= 100
nums is sorted in non-decreasing order.
 */

import java.util.Arrays;

/**
 * Input: [2, 3, 3, 3, 6, 9, 9] * Output: 4
 * l =0, r =1,... 6
 * l =0, r =1, the l =1, then nums[1] = nums[1] => [2, 3, 3, 3, 6, 9, 9]
 * l = 1, r =2, then do nothing
 * l =1, r = 3  then do nothing
 * l =1, r = 4 3 != 6 then l =2 and nums[2] = nums[4] so  [2, 3, 6, 3, 6, 9, 9]
 * l = 2, r = 5  6!= 9 then l= 3 and nums[3] = num[5] so [2, 3, 6, 9, 9]
 * l = 3, r = 6, 9 == 9
 * return l+1 = 4
 */

public class P02FindNonDuplicateNumberInstances {
    public  static int moveNonDuplicatesNumber(int[] nums){
        int left = 0;

        for (int right =1; right< nums.length; right++){
            if (nums[left] != nums[right]){ // found a non-duplicate
                left++; // move left pointer to next position
                nums[left] = nums[right]; //copy the non-duplicate number to the left pointer position. why I shouldn't use swap?
                // because we only care about the first part of the array that contains non-duplicate numbers and if we swap,
                // we might be swapping with another duplicate number.
            }


        }
        return left+1;// why? because left is index based, so length is left +1

    }

    public static void main(String[] args) {

        System.out.println("P02. Find Non-Duplicate Number Instances");
        int[] arr =new int[] {2, 3, 3, 3, 6, 9, 9};
        int length1 = moveNonDuplicatesNumber(arr);
        System.out.println("Input:" + Arrays.toString(arr) + " Length of non-duplicate subarray: " + length1 + ", Expected output: 4" + ", First part of array: " + Arrays.toString(Arrays.copyOfRange(arr, 0, length1)));

        arr =new int[] {2, 2, 2, 11};
        int length2 = moveNonDuplicatesNumber(arr);
        System.out.println("Input:" + Arrays.toString(arr) + " Length of non-duplicate subarray: " + length2 + ", Expected output: 2" + ", First part of array: " + Arrays.toString(Arrays.copyOfRange(arr, 0, length2)));



        int[] arr3 = {1, 1, 2, 3, 4, 4, 5};
        int length3 = moveNonDuplicatesNumber(arr3);
        System.out.println("Input:" + Arrays.toString(arr3) + " Length of non-duplicate subarray: " + length3 + ", Expected output: 5" + ", First part of array: " + Arrays.toString(Arrays.copyOfRange(arr3, 0, length3)));

    }
}
