package com.grokingcodeinterview.pattern02Warmup;


/*
 * Problem Statement
 * Given an array of integers nums, return the number of good pairs.
 *
 * A pair (i, j) is called good if nums[i] == nums[j] and i < j.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,1,1,3]
 * Output: 4
 * Explanation: There are 4 good pairs, here are the indices: (0,3), (0,4), (3,4), (2,5).
 * Example 2:
 *
 * Input: nums = [1,1,1,1]
 * Output: 6
 * Explanation: Each pair in the array is a 'good pair'.
 * Example 3:
 *
 * Input: words = nums = [1,2,3]
 * Output: 0
 * Explanation: No number is repeating.
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * 1 <= nums[i] <= 100
 *
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *  nums = [1,2,3,1,1,3]
 *  naive approach: nested loop: O(n^2)
 *
 *  optimal approach: using hashmap to store frequency: O(n)
 *  so the idea is to count frequency of each number and for each occurrence of a number,
 *  the number of good pairs that can be formed with previous occurrences is equal to frequency -1,
 *  why -1? because we are adding pairs with previous occurrences for each new occurrence.
 *  for example: if we have seen number 1 three times, the pairs are (0,3), (0,4), (3,4)
 *  => 3 occurrences -1 =2 +1 =3 pairs
 *  if we have seen number 3 two times, the pairs are (2,5)
 *  if we have seen number 2 one time, no pairs can be formed
 *
 *  another example: nums = [1,1,1,1]
 *  occurrences of 1 =4 so the pairs are:  (0,1), (0,2), (0,3), (1,2), (1,3), (2,3)
 *  => 4 occurrences -1 =3 +2 +1 =6 pairs
 * what is 3+2+1? it's the sum of first (n-1)
 * natural numbers where n is the frequency of the number is
 * so the map will be (1, 4) and the total paires 4*3/2 = 6
 * in previous approach we calculated it incrementally because
 * for each new occurrence we added frequency -1 to the counter but we could also calculate it at the end
 * using the formula n*(n-1)/2 for each frequency in the map
 * so the final formula is: for each frequency in the map: we add freq*(freq-1)/2 to the counter and
 * add them up to get the total number of good pairs
 * Example walkthrough:
 * nums = [1,2,3,1,1,3]
 * freqMap = {(1,3), (2,1), (3,2)}
 * for 1: freq =3 => pairs = 3*(3-1)/2 = 3
 * for 2: freq =1 => pairs = 1*(1-1)/2 = 0
 * for 3: freq =2 => pairs = 2*(2-1)/2 = 1
 *  so total pairs = 3 + 0 + 1 =4
 *
 *
 */
public class P07NumberOfGoodPairs {
    public  static int numGoodPairs(int[] nums){

    if (nums == null || nums.length<2) return 0;
    int goodPairsCounter =0;

        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num:nums){
            freqMap.put(num, freqMap.getOrDefault(num, 0) +1);
            goodPairsCounter += freqMap.get(num)-1;
        }
        return goodPairsCounter;
    }

    public static void main(String[] args) {
        
        int[] nums1 = {1,2,3,1,1,3};
        System.out.println(Arrays.toString(nums1));
        System.out.println("Number of Good Pairs: " + numGoodPairs(nums1)); // 4

        int[] nums2 = {1,1,1,1};
        System.out.println(Arrays.toString(nums2));
        System.out.println("Number of Good Pairs: " + numGoodPairs(nums2)); // 6

        int[] nums3 = {1,2,3};
        System.out.println(Arrays.toString(nums3));
        System.out.println("Number of Good Pairs: " + numGoodPairs(nums3)); // 0
    }
}
