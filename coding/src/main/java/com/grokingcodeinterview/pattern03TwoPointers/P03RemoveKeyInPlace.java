package com.grokingcodeinterview.pattern03TwoPointers;
/*
Problem 1: Given an unsorted array of numbers and a target ‘key’,
 remove all instances of ‘key’ in-place and return the new length of the array.

Example 1:

Input: [3, 2, 3, 6, 3, 10, 9, 3], Key=3
Output: 4
Explanation: The first four elements after removing every 'Key' will be [2, 6, 10, 9].
Example 2:

Input: [2, 11, 2, 2, 1], Key=2
Output: 2
Explanation: The first two elements after removing every 'Key' will be [11, 1].
 */

import java.util.Arrays;

/**
 * Input: [3, 2, 3, 6, 3, 10, 9, 3], Key=3 * Output: 4
 *  l = -1 , r = 0 nums[r] = key  r++
 *  l =-1, r = 1  nums[1 ] != key then l++, nums[l] = nums[r] then nums[0] =nums[1] and then r++ -> then [2,2,3, 6, 3, 10, 9, 3]
 *  l = 0, r =2, nums[2] = key r++
 *  l = =0, r = 3 nums[3]!= key l++, nums[1] = 6 then [2,6,3, 6, 3, 10, 9, 3] r++
 *  l =1, r = 4 nums[4] == key r++
 *  l=1, r = 5 nums[5] != keythen l = 2 , nums[2] = 10, r++ then [2,6,10, 6, 3, 10, 9, 3] r++
 *  l =2, , r = 6 nums[6] =  9 l++, nums[3] = 9 and r++ then    [2,6,10, 9, 3, 10, 9, 3] r++
 *
 *
 *
 *
 *  *
 *  *
 */
public class P03RemoveKeyInPlace {

    public  static int removeKeyInPlace(int[] arr, int key) {
        int left = 0;
        for (int right = 0; right < arr.length; right++) {
            if (arr[right] != key) {   // check right, not left
                arr[left] = arr[right];
                left++;
            }
            // if it IS the key, do nothing — skip it
        }
        return left;
    }

    public static void main(String[] args) {

        // Test case 1

        int[] arr1 = new int[]{3, 2, 3, 6, 3, 10, 9, 3};

        int length = removeKeyInPlace(arr1, 3);
        System.out.println("Input:" + Arrays.toString(arr1) + ", key: 3" + " Length after removing key: " + length + ", Expected output: 4" + ", First part of array: " + Arrays.toString(Arrays.copyOfRange(arr1, 0, length)));



        // Test case 2
        int[] arr2 = new int[]{11, 1, 2, 2, 1};

        int length2 = removeKeyInPlace(arr2, 2);
        System.out.println("Input:" + Arrays.toString(arr2) + ", key: 2" + " Length after removing key: " + length2 + ", Expected output: 2" + ", First part of array: " + Arrays.toString(Arrays.copyOfRange(arr2, 0, length2)));


    }
}
