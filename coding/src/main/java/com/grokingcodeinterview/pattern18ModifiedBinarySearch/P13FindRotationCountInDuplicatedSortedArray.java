package com.grokingcodeinterview.pattern18ModifiedBinarySearch;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Problem Statement:
Given an array of numbers which is sorted in ascending order and is rotated â€˜kâ€™ times around a pivot and duplicates, find â€˜kâ€™.
the array  contain duplicates.
Note: You need to solve the problem in  time complexity.

Example 1: Input: [10, 15, 1, 1, 3, 8] Output: 2
Explanation: The array has been rotated 2 times.
Input: [4, 5, 7, 9, 10, -1, 2,2] Output: 5
Explanation: The array has been rotated 5 times.
Example 3: Input: [1, 1, 3, 8, 10] Output: 0
Explanation: The array has not been rotated.


Constraints:

1 <= arr.length <= 5000
-104 <= arr[i] <= 104

arr is an ascending array that is possibly rotated.
*/
public class P13FindRotationCountInDuplicatedSortedArray {
    /**
     * Input: [3, 3, 3, 4, 5, 1, 2, 3], output: 5
     * dry-run example"
     * Index:  0  1  2  3  4  5  6  7
     * value:  3, 3, 3, 4, 5, 1, 2, 3
     * (0, 7) mid = 3, nums[mid] = 4, nums[end] = 3  -> nums[mid]> nums[end] -> left side sorted -> start = mid + 1 -> (4,7)
     * (4, 7) mid = 5, nums[mid] = 1, nums[end] = 3  -> nums[mid]< nums[end] -> right side sorted -> end = mid -> (4,5)
     * (4, 5) mid = 4, nums[mid] = 5, nums[end] = 1  -> nums[mid]> nums[end] -> left side sorted -> start = mid + 1 -> (5,5)
     * start == end -> 5
     *
     * Input: [4, 5, 7, 9, 10, -1, 2, 2] Output: 5
     * Index:  0  1  2  3  4  5  6  7
     * value:  4, 5, 7, 9,10,-1, 2, 2
     *
     * (0, 7) mid = 3, nums[mid] = 9, nums[end] = 2  -> nums[mid]> nums[end] -> left side sorted -> start = mid + 1 -> (4,7)
     * (4, 7) mid = 5, nums[mid] = -1, nums[end] = 2  -> nums[mid]< nums[end] -> right side sorted -> end = mid -> (4,5)
     * (4, 5) mid = 4, nums[mid] = 10, nums[end] = -1  -> nums[mid]> nums[end] -> left side sorted -> start = mid + 1 -> (5,5)
     * start == end -> 5
     *
     * Input: [10, 15, 1, 1, 3, 8] Output: 2
     * Index:  0  1  2  3  4  5
     * value: 10,15, 1, 1, 3, 8
     * (0, 5) mid = 2, nums[mid] = 1, nums[end] = 8  -> nums[mid]< nums[end] -> right side sorted -> end = mid -> (0,2)
     * (0, 2) mid = 1, nums[mid] = 15, nums[end] = 1  -> nums[mid]> nums[end] -> left side sorted -> start = mid + 1 -> (2,2)
     * start == end -> 2
     * Input: [3, 3, 3, 4, 5, 1, 2, 3] Output: 5
     *  Index:  0  1  2  3  4  5  6  7
     *  value:  3, 3, 3, 4, 5, 1, 2, 3
     * (0, 7) mid = 3, nums[mid] = 4, nums[end] = 3  -> nums[mid]> nums[end] -> left side sorted -> start = mid + 1 -> (4,7)
     * (4, 7) mid = 5, nums[mid] = 1, nums[end] = 3  -> nums[mid]< nums[end] -> right side sorted -> end = mid -> (4,5)
     * (4, 5) mid = 4, nums[mid] = 5, nums[end] = 1  -> nums[mid]> nums[end] -> left side sorted -> start = mid + 1 -> (5,5)
     * start == end -> 5
     * how it works:
     *  1. If the  nums[mid] < nums[end],  mid is in the right sorted portion of the array, so the smallest element must be in the left side including mid.
     *  NOTE: if nums[mid] < nums[end] -> search space (start, mid) ->  end = mid, not mid -1, because mid could be the smallest element.
     *  2. If nums[mid]> nums[end], mid is in the left sorted portion of the array, so the smallest element must be in the right sidee xcluding mid.
     *
     *  NOTE: if nums[mid] > nums[end] -> search space (mid + 1, end) -> start = mid + 1, because mid cannot be the smallest element.
     *  3. If the nums[mid] == nums[end], we cannot determine which side is sorted, so we reduce the search space by decrementing end by 1.
        NOTE: if nums[mid] == nums[end] -> cannot decide -> end--
        4. The loop continues until start meets end, at which point start (or end)

     | Condition                | Meaning                         | Action            |
     | ------------------------ | ------------------------------- | ----------------- |
     | `nums[mid] < nums[end]`  | `mid` is in right sorted region | `end = mid`       |
     | `nums[mid] > nums[end]`  | `mid` is in left sorted region  | `start = mid + 1` |
     | `nums[mid] == nums[end]` | Ambiguous (duplicates)          | `end--`           |
         *
     */

    public static int findNumberRotationsInDuplicatedSortedArray(int[] nums){

        int start = 0;
        int end = nums.length - 1;

        while (start < end) {
            int mid = start + (end - start) / 2;

            if (nums[mid] < nums[end]) { // why compare to nums[end]? because we are finding the smallest element
                end = mid; // this means, right side is soreted, so smallest element is in left side including mid
            } else if (nums[mid] > nums[end]) { // why compare to nums[end]? because we are finding the smallest element, smallest element is
                start = mid + 1;// this means, left side is sorted, so smallest element is in right side excluding mid
            } else {

                end--; // when nums[mid] == nums[end], we cannot decide which side is sorted, so we reduce the search space by decrementing end by 1

            }
        }

        // start == end â†’ index of smallest element
        return start;
    }

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("P13.Rotation Count With Duplicate");
        System.out.println("==============================================");

        int[] numsP13 = {4, 5, 6, 7, 0, 1, 2};
        int resultP13 = findNumberRotationsInDuplicatedSortedArray(numsP13);
        System.out.println("Input: " + Arrays.toString(numsP13) + ",output: " + makeItBold(resultP13+"") + " ,Expected: 4");
        // give example with duplicate elements
        int[] numsP13b = {4, 5, 6, 7, 0, 1, 2, 2};
        int resultP13b = findNumberRotationsInDuplicatedSortedArray(numsP13b);
        System.out.println("Input: " + Arrays.toString(numsP13b) + ",output: " + makeItBold(resultP13b+"") + " ,Expected: 4");
        int[] numsP13c = {10, 15, 1, 3, 8, 8};
        int resultP13c = findNumberRotationsInDuplicatedSortedArray(numsP13c);
        System.out.println("Input: " + Arrays.toString(numsP13c) + ",output: " + makeItBold(resultP13c+"") + " ,Expected: 2");
        int[] numsP13d = {1, 1, 1, 1, 1};
        int resultP13d = findNumberRotationsInDuplicatedSortedArray(numsP13d);
        System.out.println("Input: " + Arrays.toString(numsP13d) + ",output: " + makeItBold(resultP13d+"") + " ,Expected: 0");

    }
}

