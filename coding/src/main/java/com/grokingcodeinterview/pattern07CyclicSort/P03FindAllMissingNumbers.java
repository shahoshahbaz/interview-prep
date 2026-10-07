package com.grokingcodeinterview.pattern07CyclicSort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.Utility.makeItBold;
import static com.grokingcodeinterview.pattern07CyclicSort.P02FindTheMissingNumber.swap;

/*
 * Problem Statement
 * We are given an unsorted array containing numbers taken from the range 1 to â€˜nâ€™. The array can have duplicates, which means some numbers will be missing.
 *  Find all those missing numbers.
 * Example 1: Input: [2, 3, 1, 8, 2, 3, 5, 1]   Output: 4, 6, 7
 * Explanation: The array should have all numbers from 1 to 8, due to duplicates 4, 6, and 7 are missing.
 * Example 2:  Input: [2, 4, 1, 2] Output: 3
 *
 */
public class P03FindAllMissingNumbers {
    public static List<Integer> findNumbers(int[] nums) {
        List<Integer> missingNumbers = new LinkedList<>();
        if (nums == null)
            return missingNumbers;

        int n = nums.length;
        int i = 0;
        while (i < n) {
            int targetIndex = nums[i] - 1;
            if (nums[i] != nums[targetIndex]) {
                swap(nums, i, targetIndex);
            } else {
                i++;
            }

        }

        for (i = 0; i< nums.length; i++){
            if (nums[i] != i+1){
                missingNumbers.add(i+1);
            }
        }

    return missingNumbers;
    }

    // Example usage
    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("P03. Find All Missing Numbers");
        System.out.println("===========================");

        int[] inputP03 =  new int[]{2, 3, 1, 8, 2, 3, 5, 1};
        List<Integer> resultP03 = findNumbers(inputP03);
        System.out.println("Input: " + Arrays.toString(inputP03) + " => Missing numbers: " + makeItBold(resultP03.toString()) + " , Expected: [4, 6, 7]");
        inputP03 = new int[]{2, 3, 1, 8, 2, 3, 5, 1};
        resultP03 = findNumbers(inputP03);
        System.out.println("Input: " + Arrays.toString(inputP03) + " => Missing numbers: " + makeItBold(resultP03.toString()) + " , Expected: [4, 6, 7]"); // expected: [4, 6, 7]

        inputP03 = new int[]{2, 3, 1, 8, 2, 3, 5, 1};
        resultP03 = findNumbers(inputP03);
        System.out.println("Input: " + Arrays.toString(inputP03) + " => Missing numbers: " + makeItBold(resultP03.toString()) + " , Expected: [4, 6, 7]");

        // different test case
        inputP03 = new int[]{2, 4, 1, 2};
        resultP03 = findNumbers(inputP03);
        System.out.println("Input: " + Arrays.toString(inputP03) + " => Missing numbers: " + makeItBold(resultP03.toString()) + " , Expected: [3]");






    }
}

