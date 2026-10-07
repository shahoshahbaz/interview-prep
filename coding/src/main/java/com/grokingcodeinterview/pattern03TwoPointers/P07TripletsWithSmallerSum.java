package com.grokingcodeinterview.pattern03TwoPointers;

import java.util.Arrays;

/*
Given an array arr of unsorted numbers
and a target sum,
 count all triplets in it such that arr[i] + arr[j] + arr[k] < target
  where i, j, and k are three different indices.
   Write a function to return the count of such triplets.

Example 1:

Input: [-1, 0, 2, 3], target=3
Output: 2
Explanation: There are two triplets whose sum is less than the target: [-1, 0, 3], [-1, 0, 2]
Example 2:

Input: [-1, 4, 2, 1, 3], target=5
Output: 4
Explanation: There are four triplets whose sum is less than the target:
[-1, 1, 4], [-1, 1, 3], [-1, 1, 2], [-1, 2, 3]
Constraints:

n == arr.length
0 <= n <= 3500
-100 <= arr[i] <= 100
-100 <= target <= 100
 */

/**
 * Input: [-1, 4, 2, 1, 3], target=5
 *  sort [-1, 1, 2,  3, 4] target 4
 */
public class P07TripletsWithSmallerSum {

    public static int searchTriplets(int[] arr, int target) {

        ;
        if (arr == null || arr.length <3)  return 0;
        // sort the array
        int n = arr.length;
        Arrays.sort(arr);
        int tripleCounter=0;
        for (int i =0; i< n-2; i++){
            // do I need to skip duplicates here? no, because we are counting the number of triplets
            int left = i+1;
            int right = n-1;
            while (left< right){ // why left < right not left <= right? because we need 3 different indices
                int sum = arr[left] + arr[right] + arr[i];
                // what about duplicate? we are counting the number of triplets, so we don't care about duplicates
                if ( sum <  target){
                    tripleCounter += (right - left);  // why not right - left+ 1?
                                    // because we want arr[left] + arr[right] + arr[i] < target and we already know arr[left] + arr[right] + arr[i] < target
                                            // I don't  get it why we don't do +1 here! why? because we are counting the number of triplets , not the number of pairs

                    left++;
                }else {
                    right--;
                }
            }
        }
        return tripleCounter;

    }

    public static void main(String[] args) {
        // some test cases
        System.out.println(P07TripletsWithSmallerSum.searchTriplets(new int[] {-1, 0, 2, 3}, 3)); //2
        System.out.println(P07TripletsWithSmallerSum.searchTriplets(new int[] {-1, 4, 2, 1, 3}, 5)); //4
        System.out.println(P07TripletsWithSmallerSum.searchTriplets(new int[] {0, -1, 2, 1, -3}, 2)); // 8




    }
}
