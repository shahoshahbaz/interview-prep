package com.grokingcodeinterview.pattern03TwoPointers;
/*
Problem Statement
Given an array of numbers sorted in ascending order and a target sum, find a pair in the array whose sum is equal to the given target.
Write a function to return the indices of the two numbers (i.e. the pair) such that they add up to the given target. If no such pair exists return [-1, -1].

Example 1:
Input: [1, 2, 3, 4, 6], target=6
Output: [1, 3]
Explanation: The numbers at index 1 and 3 add up to 6: 2+4=6
Example 2:

Input: [2, 5, 9, 11], target=11
Output: [0, 2]
Explanation: The numbers at index 0 and 2 add up to 11: 2+9=11
Constraints:

2 <= arr.length <= 104
-109 <= arr[i] <= 109
-109 <= target <= 109
Only one valid answer exists.
 */
public class P01PairWithTargetSum {
    public static int[] search(int[] arr, int targetSum){
        int left =0;
        int right = arr.length -1;

        while(left<right){
            int sum = arr[left] + arr[right];
            if (sum == targetSum){
                return new int[]{left, right};
            }else if (sum < targetSum){
                left ++;
            }else{
                right --;
            }
        }
        return new int[] { -1, -1 };
    }

    public static void main(String[] args) {
        int[] result = search(new int[] { 1, 2, 3, 4, 6 }, 6);
        System.out.println("Pair with target sum: [" + result[0] + ", " + result[1] + "]"); //expected output: [1, 3]

        result = search(new int[] { 2, 5, 9, 11 }, 11); //expected output: [0, 2]
        System.out.println("Pair with target sum: [" + result[0] + ", " + result[1] + "]");
         // additional test cases
        result = search(new int[] { -3, 0, 1, 2, 4, 5 }, 1);  //expected output: [0, 4]
        System.out.println("Pair with target sum: [" + result[0] + ", " + result[1] + "]");
    }
}
