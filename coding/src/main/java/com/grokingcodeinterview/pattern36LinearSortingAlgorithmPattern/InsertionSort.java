package com.grokingcodeinterview.pattern36LinearSortingAlgorithmPattern;

import java.util.Arrays;

public class InsertionSort {

    /*
    Insertion Sort Algorithm
    the complexity:
        Time complexity: O(n^2) in the worst case, (when the array is sorted in reverse order)
                        O(n) in the best case (when the array is already sorted)
            Space complexity: O(1) - in-place sorting algorithm
     */
    public static void insertionSort(int[] nums){
        for (int i = 1; i< nums.length; i++){

            int key = nums[i]; // element to be inserted

            int j = i-1;// index of the last element in the sorted portion

            //until j is greater than or equal to 0 and
            // the current element is greater than the key
            while (j>=0 && nums[j]> key){ //
                nums[j+1] = nums[j]; // insert the current element to the right
                j = j-1; // go to the next element in the sorted portion
            }

            // now j is the index of the last element in the sorted portion that is less than or equal to the key,
            // so we insert the key at j+1
            nums[j+1] = key;
        }
    }

    public static void main(String[] args) {
        int[] nums = {5, 2, 9, 1, 5, 6};
        System.out.println("Original array: " + Arrays.toString(nums));
        insertionSort(nums);
        System.out.println("Sorted array: " + Arrays.toString(nums));
        nums = new int[]{3, 0, -2, 5, -1, 4};
        System.out.println("Original array: " + Arrays.toString(nums));
        insertionSort(nums);
        System.out.println("Sorted array: " + Arrays.toString(nums));

    }
}

