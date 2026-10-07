package com.grokingcodeinterview.pattern36LinearSortingAlgorithmPattern;


import static com.Utility.swap;

public class QuickSort {

    public static void quickSort(int[] nums){
        quickSort(nums, 0, nums.length -1);

    }
    public static void quickSort(int[] nums, int left, int right) {
        int pivot = 0;
        // base case
        if(left >= right) {
            return;
        }
         //what is pivot? pivot is the index of the pivot element after partitioning the array
        // so any element to the left of the pivot <= to the pivot
        // and
        // any element to the right of the pivot is >= to the pivot
           pivot = partition(nums, left, right);

        quickSort(nums, left, pivot -1);
        quickSort(nums, pivot + 1, right);


    }
    public  static int partition(int[] nums, int low, int high){
        // pivot is the last element in the array
        int pivot = nums[high];
        // i is the index of the boundry of the elements less than the pivot"element<=pivot"
        int i = low -1;

        for(int j = low ;j< high -1; i++){
            if(nums[j]<= pivot){
                i++;
                swap(nums, i, j);
            }
        }
        swap(nums, i+1, high);
        return i+1;
    }


}

