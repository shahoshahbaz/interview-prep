package com.grokingcodeinterview.pattern20TopKElements;
/*
 Kth Smallest Number (easy)
 Problem Statement
 Given an unsorted array of numbers, find Kth smallest number in it.

 Please note that it is the Kth smallest number in the sorted order, not the Kth distinct element.

 Note: For a detailed discussion about different approaches to solve this problem, take a look at Kth Smallest Number.

 Example 1:

 Input: [1, 5, 12, 2, 11, 5], K = 3
 Output: 5
 Explanation: The 3rd smallest number is '5', as the first two smaller numbers are [1, 2].
 Example 2:

 Input: [1, 5, 12, 2, 11, 5], K = 4
 Output: 5
 Explanation: The 4th smallest number is '5', as the first three small numbers are [1, 2, 5].
 Example 3:

 Input: [5, 12, 11, -1, 12], K = 3
 Output: 11
 Explanation: The 3rd smallest number is '11', as the first two small numbers are [5, -1].
 Constraints:

 1 <= k <= nums.length <= 105
 -104 <= nums[i] <= 104

 */

import java.util.PriorityQueue;

/**
 * [1, 5, 12, 2, 11, 5], K = 3 *  Output: 5
 * use maxHeap
 * [12,  5,1] then insert 2 <12 poll() then [5, 2,1]
 * 11> 5 do nothing.
 * 5 <
 */


public class P02KthSmallestNumber {
    public static  int findKthSmallestNumber(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b)-> b-a);
        for(int i =0; i< nums.length; i++){
            if (i< k){
                maxHeap.add(nums[i]);
                continue;
            }

            if (nums[i]< maxHeap.peek()){
                maxHeap.poll();
                maxHeap.add(nums[i]);
            }
        }
        return maxHeap.peek();
    }

    public static void main(String[] args) {
        // add some test cases
        int result = findKthSmallestNumber(new int[] { 1, 5, 12, 2, 11, 5 }, 3); // expect 5
        System.out.println("Kth smallest number is: " + result);
        result = findKthSmallestNumber(new int[] { 1, 5, 12, 2, 11, 5 }, 4); // expect 5
        System.out.println("Kth smallest number is: " + result);
        result = findKthSmallestNumber(new int[] { 5, 12, 11, -1, 12 }, 3); // expect 11
        System.out.println("Kth smallest number is: " + result);
    }

}

