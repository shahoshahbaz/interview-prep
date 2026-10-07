package com.grokingcodeinterview.pattern18ModifiedBinarySearch;
/*
problem statement:
Given an array of numbers which is sorted in ascending order and also rotated by some arbitrary number,
find if a given â€˜keyâ€™ is present in it.
Write a function to return the index of the â€˜keyâ€™ in the rotated array.
If the â€˜keyâ€™ is not present, return -1. You can assume that the given array does not have any duplicates.
Note: You need to solve the problem in o(logn) time complexity.

Example 1: Input: [10, 15, 1, 3, 8], key = 15 Output: 1
Explanation: '15' is present in the array at index '1'.
Example 2: Input: [4, 5, 7, 9, 10, -1, 2], key = 10 Output: 4
Explanation: '10' is present in the array at index '4'.
Constraints:
1 <= arr.length <= 5000
-104 <= arr[i] <= 104
All values of nums are unique.
arr is an ascending array that is possibly rotated.
-104 <= key <= 104
 */

import java.util.Arrays;

import static com.Utility.makeItBold;

/**
 * Input: [4, 5, 7, 9, 10, -1, 2], key = 10  * Output: 4
 * s = 0, e = 6, mid = 3,
 * 1. nums[0](4) ?? nums[mid](9) // left is ascending order
 * 4< 10< 9? no then start = 4
 * s = 4, e =6  mid = 5
 * nums[4](10) >> nums[5](-1) // right is acesning order
 * -1< 10< 2 no then end = 4
 * s = 4, e= 4 mid= 4, key == nums[mid] return
 *
 *
 *
 * so s = 3
 * */
public class P10SearchInRotatedArray {
    public static int searchInRotatedArray(int[] nums, int key){
        // find the rotation index
        int start = 0;
        int n = nums.length;
        int end = n-1;

        while(start<=end) {
            int mid = start + (end - start) / 2;
            // compare start with mid
            if (nums[mid] == key) {
                return mid;
            } else if (nums[start] <= nums[mid]) {
                // left half [start, mid] is sorted
                if (nums[start] <= key && key < nums[mid]) {
                    // key lies in left half-> search [start,mid -1]/
                    end = mid - 1;
                } else {
                    // key lies in right half -> search[ mid+1, end]
                    start = mid + 1; // if not search [mid +1, end]
                }
            } else {
                // right side[mid, end] is sorted
                if (nums[mid] < key && key <= nums[end])
                { //key lies in right half-> search [mid+1, end]
                    start = mid + 1;
                } else {
                    // key lies in left half-> [start, mid -1]
                    end = mid - 1;
                }
            }
        }

        return -1;

    }

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("P10. Search In Rotated Array");
        System.out.println("==============================================");
        int[] numsP10 = {10, 15, 1, 3, 8};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=15, Output: " + makeItBold(searchInRotatedArray(numsP10, 15) +"")+ ", Expected Output: 1"); //
        numsP10 = new int[] {4, 5, 7, 9, 10, -1, 2};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=10, Output: " + makeItBold(searchInRotatedArray(numsP10, 10) +"")+ ", Expected Output: 4"); //
        numsP10 = new int[]{1, 3, 8, 12};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=12, Output: " + makeItBold(searchInRotatedArray(numsP10, 12) +"")+ ", Expected Output: 3"); //
        numsP10 =  new int[]{10, 9, 8};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=10, Output: " + makeItBold(searchInRotatedArray(numsP10, 10) +"")+ ", Expected Output: 0"); //







    }
}

