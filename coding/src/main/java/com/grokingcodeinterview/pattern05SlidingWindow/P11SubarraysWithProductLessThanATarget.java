package com.grokingcodeinterview.pattern05SlidingWindow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given an array with positive numbers and a positive target number, find all of its contiguous subarrays whose product is less than the target number.
 *
 * Note: This problem is very similar to the previous one. Here, we are trying to find all the subarrays, whereas in the previous problem, we focused on finding only the count of such subarrays.
 *
 * Example 1:
 *
 * Input: [2, 5, 3, 10], target=30
 * Output: [2], [5], [2, 5], [3], [5, 3], [10]
 * Explanation: There are six contiguous subarrays whose product is less than the target.
 * Example 2:
 *
 * Input: [8, 2, 6, 5], target=50
 * Output: [8], [2], [8, 2], [6], [2, 6], [5], [6, 5]
 * Explanation: There are seven contiguous subarrays whose product is less than the target.
 * Constraints:
 *
 * 1 <= arr.length <= 3 * 104
 * 1 <= arr[i] <= 1000
 * 0 <= k <= 106
 */
public class P11SubarraysWithProductLessThanATarget {

    public static  List<List<Integer>> findSubarrays(int[] arr, int target){
        List<List<Integer>> result = new ArrayList<>();
        if (arr == null || arr.length ==0 || target <=1) return result;
        int windowStart =0;
        int product =1;

        for (int windowEnd = 0; windowEnd <arr.length; windowEnd++){
            product *= arr[windowEnd];

            while (product>= target){
                product /= arr[windowStart];
                windowStart++;
            }
            LinkedList<Integer> tempList = new LinkedList<>();
            for (int i = windowEnd; i>= windowStart; i--){
                tempList.addFirst(arr[i]);
                result.add(new ArrayList<>(tempList));
            }


        }
    return result;
    }
    public static void main(String[] args) {
        printTest(new int[]{2, 5, 3, 10}, 30);
        printTest(new int[]{8, 2, 6, 5}, 50);
        printTest(new int[]{10, 5, 2, 6}, 0);
        printTest(new int[]{1, 2, 3}, 0);
    }

    private static void printTest(int[] arr, int target) {
        List<List<Integer>> result = findSubarrays(arr, target);
        System.out.println("Input: " + java.util.Arrays.toString(arr) + ", Target: " + target);
        System.out.println("Output: " + result);
        System.out.println("Count: " + result.size());
        System.out.println("-----");
    }
}
