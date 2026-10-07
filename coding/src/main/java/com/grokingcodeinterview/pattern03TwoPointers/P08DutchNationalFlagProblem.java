package com.grokingcodeinterview.pattern03TwoPointers;
/*
Problem Statement
Given an array containing 0s, 1s and 2s, sort the array in-place.
 You should treat numbers of the array as objects,
  hence, we can’t count 0s, 1s, and 2s to recreate the array.

The flag of the Netherlands consists of three colors: red, white and blue;
 and since our input array also consists of three different numbers that is
  why it is called Dutch National Flag problem.

Examples
Example 1
Input: arr = [1, 0, 2, 1, 0]
Output: [0, 0, 1, 1, 2]
Explanation:
All 0s are moved to the front, 1s in the middle, and 2s at the end.
The relative order within each group doesn't matter.
Example 2
Input: arr= [2, 2, 0, 1, 2, 0]
Output: [0, 0, 1, 2, 2, 2]
Explanation:
All 0s come first, followed by the 1, and then all 2s at the end.
Sorting is done in-place without using extra space or counting.
Constraints:

n == arr.length
1 <= n <= 300
arr[i] is either 0, 1, or 2.
 */

import java.util.Arrays;

import static com.Utility.*;

/**
 *  in this problem we have 3 regions
 *  1. region of 0s [0, low-1]
 *  2. region of 1s [low, mid-1]
 *  3. unknown region [mid, high] // why? because we don't know what elements are there
 *  4. region of 2s [high+1, n-1]
 *  we have to start with mid =0, low =0, high = n-1
 *  and we will process the unknown region until mid <= high
 *  if nums[mid] ==0 => swap(nums, low, mid) , low++, mid++,
 *  if nums[mid] ==1 => mid++
 *  if nums[mid] ==2 => swap(nums, mid, high), high--
 *
 *
 */
public class P08DutchNationalFlagProblem {

    public static int[] sort(int nums[]){
        int n = nums.length;
        if (n ==1) return nums;

        int low =0;
        int high = n-1;
        int mid = 0; //

        while (low<high){
            if (nums[mid] == 0){ // correct position
                swap(nums, low, mid);
                low++; // why we move both low and mid? because  we know that low is 0 and mid is 0 after swap , in other words we are swapping same elements and we can safely move both pointers
                mid++;
                // we know expanded both "0" region and "1" region by one element
            }else if (nums[mid] ==1){ // swap with mid and mid++

                mid++;
            }else{
                swap(nums, mid, high);
                high --;
            }
        }
        return nums;
    }



    public static void main(String[] args) {
        // some test cases
        int[] numsP08 = {1, 0, 2, 1, 0};
        System.out.print("Array: " + makeItBold(Arrays.toString(numsP08)) );
        sort(numsP08);
        System.out.println(" Sorted array: " + makeItBold(Arrays.toString(numsP08)));

       numsP08 = new int[] {2, 2, 0, 1, 2, 0};
        System.out.print("Array: " + makeItBold(Arrays.toString(numsP08)) );
        sort(numsP08);
        System.out.println(" Sorted array: " + makeItBold(Arrays.toString(numsP08)));


    }


}
