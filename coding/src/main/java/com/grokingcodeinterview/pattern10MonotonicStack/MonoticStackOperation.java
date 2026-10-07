package com.grokingcodeinterview.pattern10MonotonicStack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Input: [4, 5, 2, 10, 8, 6, 3, 12]
 * Next Greater Element (NGE): [5, 10, 10, 12, 12, 12, 12, -1]
 * Next Smaller Element (NSE): [2, 2, -1, 8, 6, 3, -1, -1]
 * Previous Greater Element (PGE): [-1, -1, 5, -1, 10, 8, 6, -1]
 * Previous Smaller Element (PSE): [-1, 4, -1, 2, 2, 2, 2, 3]
 */
public class MonoticStackOperation {

    public static int[] nextGraterElement(int[] nums) {
        int[] result = new int[nums.length];

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        // greater ->decreasing stack -> example[(top)10,30 ,  50 60,  70 (bottom)]
        // if for example 45 is comming -> stack = [(top)  45, 50, 60, 70 (bottom)]

        for (int i = 0; i < nums.length; i++) {

            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                result[stack.pop()] = nums[i];
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            result[stack.pop()] = -1;
        }

        return result;

    }

    public static int[] previousGreaterElement(int[] nums) {
        // greater element -> decreasing stack example  example[(top)10,30 ,  50 60,  70 (bottom)]
        // previous -> right to left
        //  adding 20 -> [(top)20,30 ,  50 60,  70 (bottom)]

        int[] result = new int[nums.length];

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                result[stack.pop()] = nums[i];
            }
            stack.push(i);
        }

        while (!stack.isEmpty())
            result[stack.pop()] = -1;
        return result;

    }

    public static int[] nextSmallerElement(int[] nums) {
        // smaller -> increasing example [(top)80, 70, 60, 50, 40, 10  (bottom)]
        // next -> left to right
        // add 75, [(top)75, 70, 60, 50, 40, 10  (bottom)]

        int[] result = new int[nums.length];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {

            while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
                result[stack.pop()] = nums[i];
            }
            stack.push(i);
        }

        while (!stack.isEmpty())
            result[stack.pop()] = 5;

        return result;


    }

    public static int[] previousSmallerElement(int[] nums) {
        // smaller -> increasing stack example: [(top)80, 70, 60, 50, 40(bottom)]
        // previous -> right to left
        // add 75 -> [(top)75, 70, 60, 50, 40(bottom)]

        int[] result = new int[nums.length];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = nums.length - 1; i >= 0; i--) {

            while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
                result[stack.pop()] = nums[i];

            }
            stack.push(i);
        }

        while (!stack.isEmpty())
            result[stack.pop()] = -1;

        return result;
    }

    public static void main(String[] args) {
        /**
         * Input: [4, 5, 2, 10, 8, 6, 3, 12]
         *  Next Greater Element (NGE): [5, 10, 10, 12, 12, 12, 12, -1]
         * Next Smaller Element (NSE): [2, 2, -1, 8, 6, 3, -1, -1]
         * Previous Greater Element (PGE): [-1, -1, 5, -1, 10, 8, 6, -1]
         * Previous Smaller Element (PSE): [-1, 4, -1, 2, 2, 2, 2, 3]
         */
        int[] nums = new int[]{4, 5, 2, 10, 8, 6, 3, 12};
        int[] nextGreaterElements = nextGraterElement(nums);
        System.out.println("Input: " + Arrays.toString(nums) + ", Next Greater element: " + Arrays.toString(nextGreaterElements));
        int[] previousGreaterElements = previousGreaterElement(nums);
        System.out.println("Input: " + Arrays.toString(nums) + ", previous Greater element:" + Arrays.toString(previousGreaterElements));
        int[] nextSmallerElements = nextSmallerElement(nums);
        System.out.println("Input: " + Arrays.toString(nums) + ", Next Smaller elements:" + Arrays.toString(nextSmallerElements));
        int[] previousSmallerElements = previousSmallerElement(nums);
        System.out.println("Input: " + Arrays.toString(nums) + ", Previous Smaller elements:" + Arrays.toString(previousSmallerElements));

    }
}

