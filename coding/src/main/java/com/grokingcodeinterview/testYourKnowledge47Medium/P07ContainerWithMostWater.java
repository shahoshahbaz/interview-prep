package com.grokingcodeinterview.testYourKnowledge47Medium;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Problem Statement
Given an array of non-negative integers, where each integer represents the height of a vertical line positioned at index i. You need to find the two lines that, when combined with the x-axis, form a container that can hold the most water.

The goal is to find the maximum amount of water (area) that this container can hold.

Note: The water container's width is the distance between the two lines, and its height is determined by the shorter of the two lines.

Examples
Example 1:

Input: [1,3,2,4,5]
Expected Output: 9
Justification: The lines at index 1 and 4 form the container with the most water. The width is 3 * (4-1), and the height is determined by the shorter line, which is 3. Thus, the area is 3 * 3 = 9.
Example 2:

Input: [5,2,4,2,6,3]
Expected Output: 20
Justification: The lines at index 0 and 4 form the container with the most water. The width is 5 * (4-0), and the height is determined by the shorter line, which is 5. Thus, the area is 5 * 4 = 20.
Example 3:

Input: [2,3,4,5,18,17,6]
Expected Output: 17
Justification: The lines at index 4 and 5 form the container with the most water. The width is 17 * (5-4), and the height is determined by the shorter line, which is 17. Thus, the area is 17 * 1 = 17.
Constraints:

n == height.length
2 <= n <= 105
0 <= height[i] <= 104
 */
public class P07ContainerWithMostWater {
    public static  int maxAreaBruteForce(int[] heights) {
        int maxArea = 0; // To store the maximum area found
        for (int i =0; i< heights.length; i++){
            for (int j = i+1; j< heights.length; i++){
                int width = j-i;
                int height = Math.min(heights[i],heights[j] );
                maxArea = Math.max(maxArea, width* height);
            }
        }
        return maxArea;
    }

    public static int MaxAreaTwoPointer(int[] heights){
        int left = 0;
        int right = heights.length -1;
        int maxArea =0;

        while (left<right){
            int width = right - left;
            int height = Math.min(heights[left], heights[right]);
            maxArea = Math.max(maxArea, width * height);
            if (heights[left] < heights[right]) {
                left++;

            }else{
                right --;
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("P07. Container With Most Water");
        System.out.println("==================================================================");
        int[] heightsP07 = {1,3,2,4,5};
        System.out.println("Input: " + Arrays.toString(heightsP07) + ", output: " + makeItBold(MaxAreaTwoPointer(heightsP07) +"") + ", expected: " + makeItBold("9"));
        heightsP07 = new int[]{5,2,4,2,6,3};
        System.out.println("Input: " + Arrays.toString(heightsP07) + ", output: " + makeItBold(MaxAreaTwoPointer(heightsP07) +"") + ", expected: " + makeItBold("20"));
        heightsP07 = new int[]{2,3,4,5,18,17,6};
        System.out.println("Input: " + Arrays.toString(heightsP07) + ", output: " + makeItBold(MaxAreaTwoPointer(heightsP07) +"") + ", expected: " + makeItBold("17"));

    }
}

