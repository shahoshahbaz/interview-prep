package com.grokingcodeinterview.pattern02Warmup;

/*
 * Problem Statement
 * Given a non-negative integer x, return the square root of x rounded down to the nearest integer.
 * The returned integer should be non-negative as well.
 *
 * You must not use any built-in exponent function or operator.
 *
 * For example, do not use pow(x, 0.5) in c++ or x ** 0.5 in python.
 *
 * Example 1:
 *
 * Input: x = 8
 * Output: 2
 * Explanation: The square root of 8 is 2.8284, and since we need to return the floor of the square root (integer), hence we returned 2.
 * Example 2:
 *
 * Input: x = 4
 * Output: 2
 * Explanation: The square root of 4 is 2.
 * Example 3:
 *
 * Input: x = 2
 * Output: 1
 * Explanation: The square root of 2 is 1.414, and since we need to return the floor of the square root (integer), hence we returned 1.
 * Constraints:
 *
 * 0 <= x <= 231 - 1
 */

/**
 * 8 -> 2.8284 -> 2
 * naive approach: iterate from 1 to x and find the largest i such that i*i <= x : O(n)
 * optimal approach: binary search: O(log n)
 * left = 2, right = x/2, why left =2? because sqrt(0)=0, sqrt(1)=1, so we can start from 2
 * why right = x/2? because sqrt(x) <= x/2 for x>=4,
 * could I also use right = x? yes but it will be less optimal
 * why sqrt(x) <= x/2? because for x>=4, x/2 * x/2 = x^2/4 >= x => x^2 >= 4x => x >=4
 * so we can limit our search space to [2, x/2]
 * the idea is to find the mid and check if mid*mid == x, if yes return mid
 *
 * if mid*mid < x, we need to go to the right side, so left = mid +1
 * if mid*mid > x, we need to go to the left side, so right = mid -1
 * if we exit the loop, it means we didn't find the exact square root, so we return right,
 * why right? because at the end of the loop, left will be greater than right,
 * and right will be the largest number such that right*right <= x
 *
 *
 */
public class P08Sqrt {
    public static  int sqrt(int x){

        int left = 2;
        int right =x/2; // why x/2? because sqrt(x) <= x/2 for x>=4
        while (left<= right){
            int mid = left + (right -left)/2;
            long squareOfmid =(long) mid * mid; // why long? to avoid overflow  and handle large numbers

            if (squareOfmid == x){
                return mid;
            }else if (squareOfmid<x) {
                left = mid +1;
            }else{
                right = mid -1;

            }
        }
        return right; // why we return right? because at the end of the loop,
                    // left will be greater than right,
                       // and right will be the largest number
                // such that right*right <= x

    }

    public static void main(String[] args) {
        
        int x1 = 8;
        System.out.println("Sqrt of " + x1 + " is: " + sqrt(x1)); // 2

        int x2 = 4;
        System.out.println("Sqrt of " + x2 + " is: " + sqrt(x2)); // 2

        int x3 = 2;
        System.out.println("Sqrt of " + x3 + " is: " + sqrt(x3)); // 1
        int x4 = 15;
        System.out.println("Sqrt of " + x4 + " is: " + sqrt(x4)); // 3);

    }
}
