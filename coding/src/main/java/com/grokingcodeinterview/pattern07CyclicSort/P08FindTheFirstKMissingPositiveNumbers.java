package com.grokingcodeinterview.pattern07CyclicSort;

import java.util.*;

import static com.grokingcodeinterview.pattern07CyclicSort.P02FindTheMissingNumber.swap;

/**
 * Problem Statement
 * Given an unsorted array containing numbers and a number â€˜kâ€™, find the first â€˜kâ€™ missing positive numbers in the array.
 * Example 1: Input: [3, -1, 4, 5, 5], k=3 Output: [1, 2, 6]
 * Explanation: The smallest missing positive numbers are 1, 2 and 6.
 * Example 2: Input: [2, 3, 4], k=3 Output: [1, 5, 6]
 * Explanation: The smallest missing positive numbers are 1, 5 and 6.
 * Example 3: Input: [-2, -3, 4], k=2 Output: [1, 2]
 * Explanation: The smallest missing positive numbers are 1 and 2.
 */
public class P08FindTheFirstKMissingPositiveNumbers {
    static final boolean DEBUG = true;
    public static List<Integer> findTheFirstKMissingNumber(int[] nums, int k){
       log("original Array: "+ Arrays.toString(nums) +", k= " + k );
        List<Integer> missingNumbers = new LinkedList<>();
        // edge case , return first k number
        if (nums == null || nums.length == 0) {
            int number =1;
            for (int i =1 ;i<=k;i++){
                missingNumbers.add(number);
                number++;


            }
            return missingNumbers;
        }

        Set<Integer> seen = new HashSet<>();

        // apply cyclic sort
        int i =0;
        while(i< nums.length){
            int correctIndex = nums[i] -1;
            if (correctIndex>=0 && correctIndex< nums.length && nums[correctIndex] != nums[i]){
                swap(nums, i, correctIndex);
            }else{
                i++;
            }

        }
        log("After cyclic sorting: " +Arrays.toString(nums));


        for (i = 0; i< nums.length; i++){
            if (nums[i] != i+1){
                if (missingNumbers.size() <k ) {
                    missingNumbers.add(i + 1);
                    seen.add(nums[i]);
                }

            }
        }

        log("Missing number sofar: " + missingNumbers);
        // Add additional missing positive numbers that are beyond the array length
        int extraNumber = nums.length +1;
        while (missingNumbers.size()<k){
            // Skip any duplicates that were already in the input array
            if (!seen.contains(extraNumber)){
                missingNumbers.add(extraNumber);
            }
            extraNumber ++;

        }



        return missingNumbers;
    }
    private static void log(String msg) {
        if (DEBUG) System.out.println(msg);
    }
    public static void main(String[] args) {
            int[] nums1 = {3, -1, 4, 5, 5};
            int k1 = 3;
            System.out.println("Missing numbers: " + findTheFirstKMissingNumber(nums1, k1));

            int[] nums2 = {2, 3, 4};
            int k2 = 3;
            System.out.println("Missing numbers: " + findTheFirstKMissingNumber(nums2, k2));

            int[] nums3 = {-2, -3, 4};
            int k3 = 2;
            System.out.println("Missing numbers: " + findTheFirstKMissingNumber(nums3, k3));
        }
}

