package com.grokingcodeinterview.pattern40Kadane;

import java.util.Arrays;

/*
Given an integer array nums, find a subarray that has the largest product, and return the product.
The test cases are generated so that the answer will fit in a 32-bit integer.

Note that the product of an array with a single element is the value of that element.
Example 1: Input: nums = [2,3,-2,4] Output: 6
Explanation: [2,3] has the largest product 6.
Example 2: Input: nums = [-2,0,-1] Output: 0
Explanation: The result cannot be 2, because [-2,-1] is not a subarray.

Constraints:

1 <= nums.length <= 2 * 10^4
-10 <= nums[i] <= 10
The product of any subarray of nums is guaranteed to fit in a 32-bit integer.
 */

/**
 * Input: [-2, 3, -4, 5] ,Output: 120
 * Index   0   1   2  3
 * maxP = -2, minP= -2, res = -2
 * i = 1, nums[i] = 3 temp= -2, maxP = max(3, max, (-6, -6)) = 3, minP = min(3, min(-6, -6))  = -6, res = 3
 * maxP =3, minP= -6, rest = 3
 * i = 2 nums[i] = -4, temp = 3, maxP = max(-4, max(-12, 24))) = 24, minP = min(-4, min(-12, -4 * -6))= -12
 * maxP = 24, minP = -12, res = 24
 * i = 3, nums[i] = 5, temp = 24, maxP = max(5, max(120, -60)) = 120, minP = min(5, min( 120, -60)) = -60
 * maxP = 120, minP = -60, res = 120
 */
public class P02MaxProduct {
    public static int maxProduct(int[] nums) {


        int maxProduct = nums[0];
        int minProduct = nums[0];
        int result = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int tempMax = maxProduct;
            maxProduct = Math.max(nums[i], Math.max(nums[i] * tempMax , nums[i] * minProduct));
            minProduct = Math.min(nums[i], Math.min(nums[i] * tempMax, nums[i] * minProduct));
            result = Math.max(result, maxProduct);
        }
        return result;

    }

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("LC152. Maximum Product Subarray");
        System.out.println("=====================================");
        int[] numsP02 = {2, 3, -2, 4};
        System.out.println("Input: " + Arrays.toString(numsP02) + " ,Output: " + maxProduct(numsP02) + " Expected: 6");
        numsP02 = new int[]{-2, 0, -1};
        System.out.println("Input: " + Arrays.toString(numsP02) + " ,Output: " + maxProduct(numsP02) + " Expected: 0");
        numsP02 = new int[]{-2, 3, -4};
        System.out.println("Input: " + Arrays.toString(numsP02) + " ,Output: " + maxProduct(numsP02) + " Expected: 24");
        numsP02 = new int[]{-2, 3, -4, 5};
        System.out.println("Input: " + Arrays.toString(numsP02) + " ,Output: " + maxProduct(numsP02) + " Expected: 120");

    }
}
