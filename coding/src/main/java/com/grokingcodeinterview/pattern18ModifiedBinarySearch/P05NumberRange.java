package com.grokingcodeinterview.pattern18ModifiedBinarySearch;
/*
Given an array of numbers sorted in ascending order,
 find the range of a given number â€˜keyâ€™.
  The range of the â€˜keyâ€™ will be the first and last position of the â€˜keyâ€™ in the array.

Write a function to return the range of the â€˜keyâ€™. If the â€˜keyâ€™ is not present return [-1, -1].

Example 1:

Input: [4, 6, 6, 6, 9], key = 6
Output: [1, 3]
Example 2:

Input: [1, 3, 8, 10, 15], key = 10
Output: [3, 3]
Example 3:

Input: [1, 3, 8, 10, 15], key = 12
Output: [-1, -1]
Constraints:

0 <= nums.length <= 105
-109 <= nums[i] <= 109
nums is a non-decreasing array.
-109 <= target <= 109
 */

import java.util.Arrays;

/**
 *  [4, 6, 6, 6,6, 6, 6, 9], key = 6
 *  s = 0 e = 8 => mid = 4 : mid[4] = 6
 *  now to find firstOccurance go left but store last seen occurance index firstOccuranceSofar = 4
 *  e = 4-1 = 3 mid = 1 mid[1] = 6 firstOccrance = 1;
 *  go left = so e = 0, 0 nothing
 *  now to find lastOccreance go right index lastOccuranceSofar = 4
 *  s = 5 end = 8 mid = 6, last occurance = 6 go to right lastOccuranceSofar = 6
 *  s = 7 end = 8 mid = 7 last occurance = 7
 *
 *  go
 *  4, 1 6, 3, 9, 1
 *
 */
public class P05NumberRange {

    public static  int[] findRange(int[] arr, int key) {
        int[] result = new int[] { -1, -1 };
        if (key> arr[arr.length -1])
            return result;
        // find the first occurance
        result[0] = searchRange(arr, key, true);
        if (result[0] != -1){
            result[1] = searchRange(arr, key, false);

        }


        return result;
    }
    // findstart is true means find first occurrence else find last occurrence
    public static int searchRange(int[] nums, int key, boolean findStart){
        int keyIndex = -1; // default if not found it sotre the last seen index

        int start = 0;
        int end = nums.length -1;

        while (start<= end){
            int mid = start + (end - start)/2;

            if (key< nums[mid]){
                end = mid -1;
            }else if (key> nums[mid]){
                start = mid +1;
            }else{
                keyIndex = mid;
                if (findStart) { // go left to find first occurrence
                    end = mid -1;

                }else{
                    start = mid+1; // go right to find last occurrence
                }
            }
        }
        return keyIndex;
    }

    public static void main(String[] args) {
        // some test cases
        int[] arr = new int[] { 4, 6, 6, 6, 9 };
        int[] result = findRange(new int[] { 4, 6, 6, 6, 9 }, 6);
        System.out.print(Arrays.toString(arr) + " key: 6" );
        System.out.println(" , Range: [" + result[0] + ", " + result[1] + "]");
        arr = new int[] { 1, 3, 8, 10, 15 };
        result = findRange(arr, 10);
        System.out.print(Arrays.toString(arr) + " key: 10" );
        System.out.println(" ,Range: [" + result[0] + ", " + result[1] + "]");
        arr = new int[] { 1, 3, 8, 10, 15 };
        result = findRange(arr, 12);
        System.out.print(Arrays.toString(arr) + " key: 12" );
        System.out.println(" ,Range: [" + result[0] + ", " + result[1] + "]");

        arr = new int[] { 4, 6, 6, 6,6, 6, 6, 9 };
        result = findRange(arr, 6);
        System.out.print(Arrays.toString(arr) + " key: 6" );
        System.out.println(" ,Range: [" + result[0] + ", " + result[1] + "]");


    }
}

