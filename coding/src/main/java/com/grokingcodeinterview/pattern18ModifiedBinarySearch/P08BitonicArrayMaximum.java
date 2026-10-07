package com.grokingcodeinterview.pattern18ModifiedBinarySearch;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Problem Statement
Find the maximum value in a given Bitonic array. An array is considered bitonic if it is first monotonically increasing and then monotonically decreasing.
In other words, a bitonic array starts with a sequence of increasing elements, reaches a peak element, and then follows with a sequence of decreasing elements.
The peak element is the maximum value in the array.

Example 1: Input: [1, 3, 8, 12, 4, 2] Output: 12
Explanation: The maximum number in the input bitonic array is '12'.
Example 2: Input: [3, 8, 3, 1] Output: 8
Example 3: Input: [1, 3, 8, 12] Output: 12
Example 4: Input: [10, 9, 8] Output: 10
Constraints:
1 <= arr.length <= 105
-105 <= arr[i] <= 105
 */

/**
 * Input: [1, 3, 8, 12, 4, 2] Output: 12
 * s =0, e = 5, mid = 2, arr[2]? arr[3] 8<12 then start = 3
 * s = 3, e = 5, mid =4, arr[4]? arr[5] 4>2, end = 4
 * s = 3, e = 4, mid = 3, arr[3}? arr[4], 12> 4, start = 4
 *
 *
 */
public class P08BitonicArrayMaximum {
    public  static int findMaxInBitonicArray(int[] arr) {
        if (arr != null && arr.length ==1) return arr[0];

        int start = 0;
        int end = arr.length-1;

        while (start< end){

            int mid = start + (end -start)/2;

            if (arr[mid]<arr[mid+1]){ // we are in the increasing part of the bitonic array
                start = mid +1; // because we know the mid+1 element is greater than mid
            }else{ // we are in the decreasing part of the bitonic array
                end = mid; // because we know the middle element could  still be maximum
            }
        } // at the end of the while loop, start == end pointing to the maximum number because of the above 2 checks

        return arr[start];
    }

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("P08. Bitonic Array Maximum");
        System.out.println("==============================================");
        // Test cases
        int[] numsP08 = {1, 3, 8, 12, 4, 2};
        System.out.println("Input: " + Arrays.toString(numsP08) + " ,output: " + makeItBold(findMaxInBitonicArray(numsP08)+"") + " ,Expected: 12");
        numsP08 = new int[]{3, 8, 3, 1};
        System.out.println("Input: " + Arrays.toString(numsP08) + " ,output: " + makeItBold(findMaxInBitonicArray(numsP08)+"") + " ,Expected: 8");
        numsP08 = new int[]{1, 3, 8, 12};
        System.out.println("Input: " + Arrays.toString(numsP08) + " ,output: " + makeItBold(findMaxInBitonicArray(numsP08)+"") + " ,Expected: 12");
        numsP08 = new int[]{10, 9, 8};
        System.out.println("Input: " + Arrays.toString(numsP08) + " ,output: " + makeItBold(findMaxInBitonicArray(numsP08)+"") + " ,Expected: 10");
        

    }
}

