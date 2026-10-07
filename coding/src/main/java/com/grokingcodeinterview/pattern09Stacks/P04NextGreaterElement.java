package com.grokingcodeinterview.pattern09Stacks;

import java.util.Arrays;
import java.util.Stack;

import static com.Utility.makeItBold;

/*
 * Given an array, print the Next Greater Element (NGE) for every element.
 * The Next Greater Element for an element x is
 * the first greater element on the right side of x
 * in the array.
 * Elements for which no greater element exist, consider the next greater element as -1.
 * Examples
 * Example 1:  Input: [4, 5, 2, 25]   Output: [5, 25, 25, -1]
 * Example 1:   Input: [13, 7, 6, 12]   Output: [-1, 12, 12, -1]
 * Example 1:   Input: [1, 2, 3, 4, 5]   Output: [2, 3, 4, 5, -1]
 * Constraints:
 * 1 <= arr.length <= 104
 * -10^9 <= arr[i] <= 10^9
 */
public class P04NextGreaterElement {


    public static int[] nextGreaterElementBruteForce(int[] nums){
        int[] result = new int[nums.length];

        for (int i =0; i< nums.length; i++){
            result[i] = -1;
            for (int j = i+1; j< nums.length; j++){
                if (nums[j]> nums[i]){
                    result[i] = nums[j];
                    break;
                }
            }
        }
        return result;
    }

    public static int[] findNextGreaterElements(int[] nums){
        if (nums == null) return null;
        int[] result = new int[nums.length];

        // we store indices....
        Stack<Integer> stack = new Stack<>();


        for(int i =0; i< nums.length; i++){
            while(!stack.isEmpty() && nums[i] >nums[stack.peek()] ){
                result[stack.pop()] =nums[i] ;
            }
            stack.push(i);
            System.out.println(i+"-" + stack.toString());
        }

        while(!stack.isEmpty()){
            result[stack.pop()] = -1;
        }
        return result;
    }

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("P04. Stack: Next Greater Element");
        System.out.println("=====================================");
        int[] inputP04 = {4, 5, 2, 25};
        int[] outputP04 = nextGreaterElementBruteForce(inputP04);
        System.out.println("Input: " + Arrays.toString(inputP04) + " , Next Greater Elements (Brute Force): " + makeItBold(Arrays.toString(outputP04)) +
                "\t EXPECTED: " + "[5, 25, 25, -1]");

        inputP04 = new int[]{13, 7, 6, 12};
        outputP04 = nextGreaterElementBruteForce(inputP04);
        System.out.println("Input: " + Arrays.toString(inputP04) + " , Next Greater Elements (Brute Force): " + makeItBold(Arrays.toString(outputP04)) +
                "\t EXPECTED: " + "[-1, 12, 12, -1]");
        inputP04 = new int[]{1, 2, 3, 4, 5};
        outputP04 = nextGreaterElementBruteForce(inputP04);
        System.out.println("Input: " + Arrays.toString(inputP04) + " , Next Greater Elements (Brute Force): " + makeItBold(Arrays.toString(outputP04)) +
                "\t EXPECTED: " + "[2, 3, 4, 5, -1]");
        }
}

