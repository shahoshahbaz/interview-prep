package com.grokingcodeinterview.testYourKnowledge47Medium;
/*
Problem Statement
Given an array of integers, return a new array where each element at index i of the new array is the product of all the numbers in the original array except the one at i.
 You must solve this problem without using division.

Examples
Input: [2, 3, 4, 5]
Expected Output: [60, 40, 30, 24]
Justification: For the first element: 3*4*5 = 60, for the second element: 2*4*5 = 40, for the third element: 2*3*5 = 30, and for the fourth element: 2*3*4 = 24.
Input: [1, 1, 1, 1]
Expected Output: [1, 1, 1, 1]
Justification: Every element is 1, so the product of all other numbers for each index is also 1.
Input: [10, 20, 30, 40]
Expected Output: [24000, 12000, 8000, 6000]
Justification: For the first element: 20*30*40 = 24000, for the second element: 10*30*40 = 12000, for the third element: 10*20*40 = 8000, and for the fourth element: 10*20*30 = 6000.
Constraints:

2 <= nums.length <= 105
-30 <= nums[i] <= 30
The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 */

import java.util.Arrays;

/**
 * Input: [2, 3, 4, 5] Expected Output: [60, 40, 30, 24]
 *
 *
 *
 */

public class P05ProductOfArrayExceptSelf {


    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[nums.length];
        int[] left = new int[n];
        int[] right = new int[n];

       Arrays.fill(left,1);
        Arrays.fill(right,1);


        // Pass 1: left products (left to right)

        for(int i = 1; i < n; i++){
            left[i] = left[i-1] * nums[i-1];
        }

    // Pass 2: multiply by right products (right to left)

        for(int i = n-2; i >= 0; i--){
            right[i] = right[i +1] *nums[i+1];

        }

        for (int i= 0; i< n; i++){
            result[i] = left[i] * right[i];
        }
        return result;
    }
}

