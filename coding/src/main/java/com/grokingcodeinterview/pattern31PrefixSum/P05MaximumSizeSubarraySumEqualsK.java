package com.grokingcodeinterview.pattern31PrefixSum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.Utility.makeItBold;

/*
Problem Statement
Given an array of integers nums and an integer k, find the length of the longest subarray that sums to k. If no such subarray exists, return 0.
Example 1: Input: nums = [1, 2, 3, -2, 5], k = 5 Output: 2
Explanation: The longest subarray with a sum of 5 is [2, 3], which has a length of 2.
Example 2: Input: nums = [-2, -1, 2, 1], k = 1 Output: 2
Explanation: The longest subarray with a sum of 1 is [-1, 2], which has a length of 2.
Example 3: Input: nums = [3, 4, 7, 2, -3, 1, 4, 2], k = 7 Output: 4
Explanation: The longest subarray with a sum of 7 is [7, 2, -3, 1], which has a length of 4.
Constraints:
1 <= nums.length <= 2 * 105
-104 <= nums[i] <= 104
-109 <= k <= 109
 */
public class P05MaximumSizeSubarraySumEqualsK {
    public static int longestSubarraySum(int[] nums, int k){
        // Map: prefixSum -> First index where  this sum appears
        Map<Integer, Integer> firstIndex = new HashMap<>();
        // prefix sum of 0 apears at index -1 (before the array starts)
        firstIndex.put(0, -1);

        int currentSum =0;
        int maxLength =0;

        for (int i =0; i< nums.length; i++){

            //Running prefix sum up to current index
            currentSum += nums[i];
            // We need a prefix sum sucht that:
            // currentSum - previousSum = k
            // => previousSum = currentSum - k
            int needed = currentSum -k;

            // If such a prefix sum seen before,
            // the subarray (prevousIndex +1, currentIndex) has sum k
            if(firstIndex.containsKey(needed)){
                int length = i -firstIndex.get(needed);
                maxLength = Math.max(maxLength, length);
            }
           // Store ONLY the first occurance of currentSum
            // becuase earliest index gives longest subarray.
            firstIndex.putIfAbsent(currentSum, i);
        }
        return maxLength;

    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("P05: Maximum Size Subarray Sum Equals K");
        System.out.println("==================================================");
        int[] numsP05 = {1, 2, 3, -2, 5};
        int kP05 = 5;
        int resultP05 = longestSubarraySum(numsP05, kP05);
        System.out.println("Input: " + Arrays.toString(numsP05) + ", k = " + kP05 + ", Output: " + makeItBold(resultP05 +" ") +"Expected: 2");
        numsP05 = new int[]{-2, -1, 2, 1};
        kP05 = 1;
        resultP05 = longestSubarraySum(numsP05, kP05);
        System.out.println("Input: " + Arrays.toString(numsP05) + ", k = " + kP05 + ", Output: " + makeItBold(resultP05 +" ") +"Expected: 2");
        numsP05 = new int[]{3, 4, 7, 2, -3, 1, 4, 2};
        kP05 = 7;
        resultP05 = longestSubarraySum(numsP05, kP05);
        System.out.println("Input: " + Arrays.toString(numsP05) + ", k = " + kP05 + ", Output: " + makeItBold(resultP05 +" ") +"Expected: 4");
    }
}

