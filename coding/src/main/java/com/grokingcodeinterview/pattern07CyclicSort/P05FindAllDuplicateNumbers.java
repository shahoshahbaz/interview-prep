package com.grokingcodeinterview.pattern07CyclicSort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.Utility.makeItBold;
import static com.grokingcodeinterview.pattern07CyclicSort.P02FindTheMissingNumber.swap;

/*
 * Problem Statement
 * We are given an unsorted array containing n numbers taken from the range 1 to n.
 * The array has some numbers appearing twice,
 * find all these duplicate numbers using constant space.
 *  Example 1: Input: [3, 4, 4, 5, 5]  Output: [4, 5]
 *  Example 2: Input: [5, 4, 7, 2, 3, 5, 3]  Output: [3, 5]
 *
 */
public class P05FindAllDuplicateNumbers {

    public  static List<Integer> duplicatesNumber(int[] nums){
      List<Integer> duplicates = new LinkedList<>();
      if(nums == null) return duplicates;
      int i =0;
      while (i<nums.length){
          int targetIndex = nums[i] -1;
          if (nums[i] != nums[targetIndex]){
              swap(nums, i, targetIndex);

          }else{
              i++;
          }

      }

      for (i =0; i< nums.length; i++){
          int correctedIndex = nums[i] -1;
          if (i != correctedIndex && !duplicates.contains(nums[i])){
              duplicates.add(nums[i]);
          }
      }
    return duplicates;
    }
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("P05. Find All Duplicate Numbers");
        System.out.println("==========================================");


        int[] numsP05 = {3, 4, 4, 5, 5};
        System.out.println("Input: " + Arrays.toString(numsP05) +", Output: " + makeItBold(duplicatesNumber(numsP05).toString() ) + "Expected: [4, 5]");
        numsP05 = new int[]{5, 4, 7, 2, 3, 5, 3};
        System.out.println("Input: " + Arrays.toString(numsP05) +", Output: " + makeItBold(duplicatesNumber(numsP05).toString() ) + " Expected: [3, 5]");
        numsP05 = new int[]{1, 2, 3, 4};
        System.out.println("Input: " + Arrays.toString(numsP05) +", Output: " + makeItBold(duplicatesNumber(numsP05).toString() ) + " Expected: []");
        numsP05 = new int[] {5, 4, 7, 2, 3, 5, 3};
        System.out.println("Input: " + Arrays.toString(numsP05) +", Output: " + makeItBold(duplicatesNumber(numsP05).toString() ) + " Expected: [3, 5]");

    }

}

