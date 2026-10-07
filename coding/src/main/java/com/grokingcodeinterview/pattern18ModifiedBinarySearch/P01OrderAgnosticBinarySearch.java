package com.grokingcodeinterview.pattern18ModifiedBinarySearch;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
problem Statement:
 Given a sorted array of numbers, find if a given number â€˜keyâ€™ is present in the array. Though we know that the array is sorted, we donâ€™t know if itâ€™s sorted in ascending or descending order. You should assume that the array can have duplicates.
 Write a function to return the index of the â€˜keyâ€™ if it is present in the array, otherwise return -1.
 Example 1: Input: [4, 6, 10], key = 10  Output: 2
 Example 2: Input: [1, 2, 3, 4, 5, 6, 7], key = 5  Output: 4
 Example 3: Input: [10, 6, 4], key = 10 Output: 0
 Example 4: Input: [10, 6, 4], key = 4
 Output: 2
 Constraints:
 1 <= nums.length <= 104
 -104 < nums[i], target < 104
 */
public class P01OrderAgnosticBinarySearch {
    public static int searchInAgnosticBinary(int[] arr, int key) {


     int n = arr.length;
     int left = 0;
     int right = n -1;
     boolean ascending = arr[0]< arr[arr.length -1];

     while (left<= right){

     int mid = left +  (right - right)/2; // why not (left + right)/2 to avoid overflow, but in java int overflow is not an issue as it wraps around

     if (arr[mid] == key)
         return mid;
     if (ascending){
         // got to right or left as usual
         if (arr[mid]< key){ //
             // go right
             left = mid+1; // move left pointer
         }else{
             right = mid -1; // move right pointer
         }
     } else{
         if (arr[mid]< key){
             right = mid -1; // move right pointer
         }else{
             left = mid+ 1; // move left pointer
         }
     }



    }

     return -1;
    }

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("P01. Search in Order Agnostic Binary Search");
        System.out.println("==============================================");
        int[] numsP01 = {4, 6, 10};
        int keyP01 = 10;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", key = " + keyP01 +" ,output: " +makeItBold( searchInAgnosticBinary(numsP01, keyP01) +"") +" ,Expected Output: 2");
        numsP01 = new int[]{1, 2, 3, 4, 5, 6, 7};
        keyP01 = 5;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", key = " + keyP01 +" ,output: " +makeItBold( searchInAgnosticBinary(numsP01, keyP01) +"") +" ,Expected Output: 4");
        numsP01 = new int[]{10, 6, 4};
        keyP01 = 10;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", key = " + keyP01 +" ,output: " +makeItBold( searchInAgnosticBinary(numsP01, keyP01) +"") +" ,Expected Output: 0");
        numsP01 = new int[]{10, 6, 4};
        keyP01 = 4;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", key = " + keyP01 +" ,output: " +makeItBold( searchInAgnosticBinary(numsP01, keyP01) +"") +" ,Expected Output: 2");


    }

}

