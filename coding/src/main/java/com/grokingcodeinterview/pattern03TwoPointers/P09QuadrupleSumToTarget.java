package com.grokingcodeinterview.pattern03TwoPointers;
/*
Given an array of unsorted numbers and a target number,
find all unique quadruplets in it, whose sum is equal to the target number.

Example 1:

Input: [4, 1, 2, -1, 1, -3], target=1
Output: [-3, -1, 1, 4], [-3, 1, 1, 2]
Explanation: Both the quadruplets add up to the target.
Example 2:

Input: [2, 0, -1, 1, -2, 2], target=2
Output: [-2, 0, 2, 2], [-1, 0, 1, 2]
Explanation: Both the quadruplets add up to the target.
Constraints:

1 <= nums.length <= 200
-109 <= nums[i] <= 109
-109 <= target <= 109
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.Utility.makeItBold;

/**
 *  [2, 0, -1, 1, -2, 2] sort [-2, -1, 0,  1, 2, 2]
 *  sorted array
 *  iterate with first pointer i from 0 to n-4
 *    skip duplicates for i
 *    iterate with second pointer j from i+1 to n-3
 *    skip duplicates for j
 *    set left = j+1, right = n-1
 *    while left < right
 *    calculate sum = numsP09[i] + numsP09[j] + numsP09[left] + numsP09[right]
 *    if sum == target
 *    add quadruplet to result
 *    skip duplicates for left and right
 *    else if sum < target
 *    left++
 *    else
 *    right--
 *    return result
 *
 *
 *
 *
 */
public class P09QuadrupleSumToTarget {

    public static  List<List<Integer>> searchQuadruplets(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        if ( n <4) return result;

        Arrays.sort(nums);
        for (int i  =0; i< n-2; i++){
            if (i>0 && nums[i] == nums[i-1]) continue; // skip duplicates why we are not using while here? because i is already in a for loop
            for (int j = i+1; j< n -1; j++){
                if (j> i+1 && nums[j] == nums[j-1])  continue; // skip duplicates why we are not using while here? because j is already in a for loop
                int left = j+1;
                int right = n-1;

                while (left< right) {
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];

                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        while (left < right && nums[left] == nums[left + 1]) left++; // skip duplicates why we are using while here? because left is not in a for loop and we need to skip  for left
                        while (left < right && nums[right] == nums[right - 1]) right--; // skip duplicates why we are using while here? because right is not in a for loop
                        left++;
                        right --;

                    } else if (sum < target) {
                        left++; // move left pointer to right to increase sum
                    } else {
                        right--; // move right pointer to left to decrease sum
                    }

                }


            }
        }

        return result;
    }



    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("P09. Find Quadruple Sum to Target.");
        System.out.println("==================================");

        // add some tests
        int [] numsP09 = new int[] {4, 1, 2, -1, 1, -3};
        int targetP09 = 1;
        System.out.print("Input array: " +makeItBold( Arrays.toString(numsP09)) + ", target: " + makeItBold(""+targetP09 ));
        List<List<Integer>> resultP09 = searchQuadruplets(numsP09, targetP09);
        System.out.print(" ,Quadruplets summing to target " +makeItBold( " : " + resultP09));
        System.out.print(" ,Expected output:  " +makeItBold("[[-3, -1, 1, 4], [-3, 1, 1, 2]] \n"));


        numsP09 = new int[] {2, 0, -1, 1, -2, 2};
        targetP09 = 2;
        resultP09 = searchQuadruplets(numsP09, targetP09);
        System.out.print("array:"+makeItBold( Arrays.toString(numsP09)) +", target: " + targetP09 );
        System.out.print(" ,Quadruplets summing to target" +makeItBold( " : " + resultP09));
        System.out.println(" ,Expected output: " + makeItBold("[[-2, 0, 2, 2], [-1, 0, 1, 2]] "));

        // two more examples
        numsP09 = new int[] {1, 0, -1, 0, -2, 2};
        targetP09 = 0;
        resultP09 = searchQuadruplets(numsP09, targetP09);
        System.out.print("array:"+makeItBold( Arrays.toString(numsP09)) +", target: " + targetP09 );
        System.out.print(" ,Quadruplets summing to target" +makeItBold( " : " + resultP09));
        System.out.println(" ,Expected output: "+ makeItBold("[[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]] "));

        numsP09 = new int[] {0, 0, 0, 0};
        targetP09 = 0;
        resultP09 = searchQuadruplets(numsP09, targetP09);
        System.out.print("array:"+makeItBold( Arrays.toString(numsP09)) +", target: " + targetP09 );
        System.out.print(" ,Quadruplets summing to target" +makeItBold( " : " + resultP09));
        System.out.println(" ,Expected output: " + makeItBold( " [[0, 0, 0, 0]] "));


    }

    }
