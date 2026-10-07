package com.grokingcodeinterview.pattern10MonotonicStack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import static com.Utility.makeItBold;

/*
Problem Statement
Given an array of integers arr, return the sum of the minimum values from all possible contiguous subarrays within arr.
 Since the result can be very large, return the final sum modulo (10^9 + 7).

Examples
Example 1: Input: arr = [3, 1, 2, 4, 5] Expected Output: 30
Explanation:
The subarrays are: [3], [1], [2], [4], [5], [3,1], [1,2], [2,4], [4,5], [3,1,2],
 [1,2,4], [2,4,5], [3,1,2,4], [1, 2, 4, 5], [3, 1, 2, 4, 5].
The minimum values of these subarrays are: 3, 1, 2, 4, 5, 1, 1, 2, 4, 1, 1, 2, 1, 1, 1.
Summing these minimums: 3 + 1 + 2 + 4 + 5 + 1 + 1 + 2 + 4 + 1 + 1 + 2 + 1 + 1 + 1 = 30.
Example 2: Input: arr = [2, 6, 5, 4] Expected Output: 36
Explanation:
The subarrays are: [2], [6], [5], [4], [2,6], [6,5], [5,4], [2,6,5], [6,5,4], [2,6,5,4].
The minimum values of these subarrays are: 2, 6, 5, 4, 2, 5, 4, 2, 4, 2.
Summing these minimums: 2 + 6 + 5 + 4 + 2 + 5 + 4 + 2 + 4 + 2 = 36.
Example 3: Input: arr = [7, 3, 8] Expected Output: 35
Explanation:
The subarrays are: [7], [3], [8], [7,3], [3,8], [7,3,8].
The minimum values of these subarrays are: 7, 3, 8, 3, 3, 3.
Summing these minimums: 7 + 3 + 8 + 3 + 3 + 3 = 27.
Constraints:

1 <= arr.length <= 3 * 104
1 <= arr[i] <= 3 * 104
 */

public class P06SumOfSubarrayMinimums {

    public static int sumSubArray(int[] nums){
        int MOD = 1_000_000_007;
        int n = nums.length;

        int[] prev = new int[n]; // this store indices
        int[] next = new int[n]; // this store indices

        Deque<Integer> stack = new ArrayDeque<>();

        // find previous smaller Element s(stricly smaller no equals) -> increasing stack(top-> bottom increasing)

        for (int i =0; i< n; i++){
            while (!stack.isEmpty() && nums[stack.peek()]> nums[i]){
                stack.pop();
            }
            prev[i] = stack.isEmpty()? -1: stack.peek();
            stack.push(i);
        }
        stack.clear();

        // next Smaller or equal -> still using increasing (top -> bottom increasing) but direction of loop is  from right to left
        for (int i =n-1; i>= 0; i--){
            while(!stack.isEmpty() && nums[stack.peek()]> nums[i]){
                stack.pop();
            }
            next[i] = stack.isEmpty()? n: stack.peek(); // why n? becuase if no smaller element on right side then we can consider the subarray till end of array
            stack.push(i);
        }
        long result = 0;
        // [start ........ i ........ end]
        for (int i =0; i< n; i++){
            int left = i -prev[i];  // number of choice of  start that includes nums[i] as minimum is from previous smaller +1 to i
            int right = next[i] - i; // numbe of choice of end that includes nums[i] as minimum is from i to next smaller -1
            // so total subarrays where nums[i] is minimum is left * right
            result += ( nums[i] * left * right ) % MOD;
        }

        return(int) result;

    }
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("P06.Sum Of Subarray Minimums");
        System.out.println("==============================================");
        int[] numsP06 = new int[]{3,1,2,4,5};
        System.out.println("Input: " + Arrays.toString(numsP06) + " => Output: " + makeItBold(sumSubArray(numsP06)+"") + " Expected: 30");
        numsP06 = new int[]{2,6,5,4};
        System.out.println("Input: " + Arrays.toString(numsP06) + " => Output: " + makeItBold(sumSubArray(numsP06)+"") + " Expected: 36");
        numsP06 = new int[]{7,3,8};
        System.out.println("Input: " + Arrays.toString(numsP06) + " => Output: " + makeItBold(sumSubArray(numsP06)+"") + " Expected: 27");
        numsP06 = new int[]{11,81,94,43,3};
        System.out.println("Input: " + Arrays.toString(numsP06) + " => Output: " + makeItBold(sumSubArray(numsP06)+"") + " Expected: 444");


    }
}

