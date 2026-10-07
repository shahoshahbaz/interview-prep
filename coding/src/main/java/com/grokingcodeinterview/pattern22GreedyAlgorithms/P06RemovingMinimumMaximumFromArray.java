package com.grokingcodeinterview.pattern22GreedyAlgorithms;

/*
 Problem Statement:
Determine the minimum number of deletions required to remove the smallest and the largest elements from an array of integers.
In each deletion, you are allowed to remove either the first (leftmost) or the last (rightmost) element of the array.

Examples
Example 1: Input: [3, 2, 5, 1, 4] Expected Output: 3
Justification: The smallest element is 1 and the largest is 5. Removing 4, 1, and then 5 (or 5, 4, and then 1) in three moves is the most efficient strategy.
Example 2: Input: [7, 5, 6, 8, 1]Expected Output: 2
Justification: Here, 1 is the smallest, and 8 is the largest. Removing 1 and then 8 in two moves is the optimal strategy.
Example 3:

Input: [2, 4, 10, 1, 3, 5] Expected Output: 4
Justification: The smallest is 1 and the largest is 10. One strategy is to remove 2, 4, 10, and then 1 in four moves.
Constraints:

1 <= nums.length <= 105
-105 <= nums[i] <= 105
The integers in nums are distinct.
 */

import java.util.Arrays;

import static com.Utility.makeItBold;

/**
 * dry run example:
 * Input: [3, 2, 5, 1, 4] Expected Output: 3
 * Index:  0  1  2  3  4
 * value:  3  2  5  1  4
 * minIndex = 3 (value 1)  maxIndex = 2 (value 5)
 * leftMostIndex = min(3, 2) = 2;
 * rightMostIndex = max(3,2) = 3
 * costLeft = rightMostIndex + 1 = 3 + 1 = 4
 * costRight = n - leftMostIndex = 5 - 2 = 3
 * costBoth = (leftMostIndex + 1) + (n - rightMostIndex) = (2 + 1) + (5 - 3) = 3 + 2 = 5
 * min(costLeft, costRight, costBoth) = min(4, 3, 5) = 3
 * Output: 3
 *
 * another complex example:
 * Input: [2, 4, 10, 1, 3, 5] Expected Output: 4
 * Index:  0  1  2   3  4  5
 * value:  2  4  10  1  3  5
 * minIndex = 3(value 1), maxIndex= 2(value 10)
 * leftMostIndex = min(3,2) = 2
 * rightMostIndex = max(3, 2) = 3
 * costLeft = 3+1 = 4
 * costRight = 6 - 2 = 4
 * costBothRightandLeft = 8
 * min = 4;
 */
public class P06RemovingMinimumMaximumFromArray {
    public static int numberOfDeletion(int[] nums){
        int n = nums.length;
        // find minIndex and maxIndex
        int min= Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int minIndex = -1;
        int maxIndex = -1;

        for (int i =0; i< nums.length;i++){
            if (nums[i]<min){
                min = nums[i];
                minIndex = i;
            }
            if (nums[i]> max){
                max = nums[i];
                maxIndex = i;
            }
        }
        // get leftMost and rightMost index
        int leftMostIndex = Math.min(minIndex, maxIndex); // what is leftMostIndex? it is the smaller index why is it called leftMostIndex? because it is the leftmost index
        int rightMostIndex = Math.max(minIndex, maxIndex);// this is rightMostIndex

        // why we have three cost? because we have three options to remove min and max
        // 1. remove from left only
        int costLeft = rightMostIndex + 1; // why we use rightMostIndex + 1? because we need to remove all elements from 0 to rightMostIndex
        // 2. remove from right only
        int costRight = n - leftMostIndex; // why we use n -leftMostIndex? because we need to remove all elements from leftMostIndex to n-1
        // 3. remove from both sides
        int costBoth =(leftMostIndex+1) + (n-rightMostIndex); // why we use (leftMostIndex+1) + (n-rightMostIndex)? because we need to remove all elements from 0 to leftMostIndex
        // and from rightMostIndex to n-1
        return Math.min(costBoth, Math.min(costLeft, costRight));


    }

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("P06. Removing Minimum and Maximum from Array");
        System.out.println("===================================");
        int[] numsP06 = {3, 2, 5, 1, 4};
        System.out.println("Input: " + Arrays.toString(numsP06) +", Output: " + makeItBold(numberOfDeletion(numsP06)+"")  +" ,Expected: 3");
        numsP06 = new int[]{7, 5, 6, 8, 1};
        System.out.println("Input: " + Arrays.toString(numsP06) +", Output: " + makeItBold(numberOfDeletion(numsP06)+"")  +" ,Expected: 2");
        numsP06 = new int[]{2, 4, 10, 1, 3, 5};
        System.out.println("Input: " + Arrays.toString(numsP06) +", Output: " + makeItBold(numberOfDeletion(numsP06)+"")  +" ,Expected: 4");
        numsP06 = new int[]{1,2,3,4,5,6,7,8,9,10};
        System.out.println("Input: " + Arrays.toString(numsP06) +", Output: " + makeItBold(numberOfDeletion(numsP06)+"")  +" ,Expected: 2");

    }
}

