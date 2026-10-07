package com.grokingcodeinterview.pattern07CyclicSort;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
 * You are given an unsorted integer array of size n. The array contains all integers from 1 to n, but in random order. Your task is to sort the array in-place so that the numbers are ordered from 1 to n.
 * You must solve the problem in O(n) time using constant extra space.
 *
 * You are not allowed to use any built-in sort functions or additional data structures.
 *
 * Example 1:  Input: [3, 1, 5, 4, 2]  Output: [1, 2, 3, 4, 5]
 * Example 2:  Input: [2, 6, 4, 3, 1, 5]  Output: [1, 2, 3, 4, 5, 6]
 * Example 3:  Input: [1, 5, 6, 4, 3, 2]  Output: [1, 2, 3, 4, 5, 6]
 *
 */
public class P01CyclicSort {
    public static int[] sort(int[] nums){
        if (nums == null) return null;

        int i =0;
        while ( i< nums.length){
            int targetIndex = nums[i] -1;
            if (nums[i] != nums[targetIndex]){
                swap(nums, i, targetIndex);
            }else{
                 i++;
            }
        }
        return nums;
    }

    public static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("P01. Cyclic Sort: Sort the Array");
        System.out.println("=====================================");

        // lets have 4 test cases here

        int[] numsP01 = {3, 1, 5, 4, 2};
        int[] sortedArrayP01 = sort(numsP01);

        System.out.println("Input: " + makeItBold(Arrays.toString(new int[]{3, 1, 5, 4, 2})) + " , Sorted: " + makeItBold(Arrays.toString(sortedArrayP01)) +
                "\t EXPECTED: " + makeItBold("[1, 2, 3, 4, 5]"));

        numsP01 = new int[]{2, 6, 4, 3, 1, 5};
        sortedArrayP01 = sort(numsP01);
        System.out.println("Input: " + makeItBold(Arrays.toString(new int[]{2, 6, 4, 3, 1, 5})) + " , Sorted: " + makeItBold(Arrays.toString(sortedArrayP01)) +
                "\t EXPECTED: " + makeItBold("[1, 2, 3, 4, 5, 6]"));
        numsP01 = new int[]{1, 5, 6, 4, 3, 2};
        sortedArrayP01 = sort(numsP01);
        System.out.println("Input: " + makeItBold(Arrays.toString(new int[]{1, 5, 6, 4, 3, 2})) + " , Sorted: " + makeItBold(Arrays.toString(sortedArrayP01)) +
                "\t EXPECTED: " + makeItBold("[1, 2, 3, 4, 5, 6]"));


    }

}

