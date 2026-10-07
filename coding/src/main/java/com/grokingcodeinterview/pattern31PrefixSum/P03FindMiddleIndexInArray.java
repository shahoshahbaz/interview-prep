package com.grokingcodeinterview.pattern31PrefixSum;

import java.util.Arrays;

import static com.Utility.makeItBold;

    /*
Given an integer array nums, return the leftmost middleIndex (i.e., the smallest amongst all the possible ones).
A middleIndex is an index where the sum of the numbers to the left of this index is equal to the sum of the numbers to the right of this index.

You can consider the left sum 0 for middleIndex == 0, and right sum 0 for middleIndex == nums.length - 1.

If no middleIndex exists in nums, return -1.

Examples
Example 1:
Input: nums = [1, 7, 3, 6, 5, 6]
Expected Output: 3
Justification: The sum of the numbers to the left of index 3 (1 + 7 + 3 = 11) is equal to the sum of the numbers to the right of index 3 (5 + 6 = 11).
Example 2:
Input: nums = [2, 1, -1]
Expected Output: 0
Justification: The sum of the numbers to the left of index 0 is considered to be 0. The sum of the numbers to the right of index 0 (1 + -1 = 0) is also 0.
Example 3:
Input: nums = [2, 3, 5, 5, 3, 2]
Expected Output: -1
Justification: There is no middleIndex exists in the array.
Constraints:

1 <= nums.length <= 100
-1000 <= nums[i] <= 1000
 */
public class P03FindMiddleIndexInArray {
    public static int findMiddleIndex(int[] nums){
        // calculate the total totalSum
        int totalSum =0;
        for (int num: nums){
            totalSum +=num;
        }

        int leftSum =0;

        for(int i =0; i<nums.length; i++){
            int rightSum = totalSum -leftSum -nums[i];


            if(rightSum == leftSum){
                return i;
            }
            leftSum +=nums[i];
        }

        return -1;

    }

    public static void main(String[] args) {
        System.out.println("=========================");
        System.out.println("P03: FIND MIDDLE INDEX IN ARRAY");
        System.out.println("=========================\n");
        int[] numsP03 = {1, 7, 3, 6, 5, 6};
        System.out.println("Input: " + Arrays.toString(numsP03) +", Output: " + makeItBold(findMiddleIndex(numsP03)+" ") + "Expected: 3");
        numsP03 = new int[]{2, 1, -1};
        System.out.println("Input: " + Arrays.toString(numsP03) +", Output: " + makeItBold(findMiddleIndex(numsP03)+" ") + "Expected: 0");
        numsP03 = new int[]{2, 3, 5, 5, 3, 2};
        System.out.println("Input: " + Arrays.toString(numsP03) +", Output: " + makeItBold(findMiddleIndex(numsP03)+" ") + "Expected: -1");


    }
}

