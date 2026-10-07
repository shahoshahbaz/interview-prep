package com.grokingcodeinterview.pattern20TopKElements;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/*
 * Given an unsorted array of numbers, find the â€˜Kâ€™ largest numbers in it.
 *
 * Example 1:
 *
 * Input: [3, 1, 5, 12, 2, 11], K = 3
 * Output: [5, 12, 11]
 * Example 2:
 *
 * Input: [5, 12, 11, -1, 12], K = 3
 * Output: [12, 11, 12]
 * Constraints:
 *
 * 1 <= nums.length <= 105
 * -105 <= nums[i] <= 105
 * k is in the range [1, the number of unique elements in the array].
 * It is guaranteed that the answer is unique.
 */

/**
 *  [3, 1, 5, 12, 2, 11], and K=3
 *  insert k element into the heap [1, 3, 5]
 *  insert 12 > 1 then [3,5, 12]
 *  insert 2 do nothing
 *  insert 11> 3 [5, 11, 12]
 */
public class P01TopKNumber {

    public static List<Integer> findKLargestNumbers(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int i =0; i< nums.length; i++){
            if (i< k){
                minHeap.add(nums[i]);
                continue;

            }
            if (minHeap.peek() != null && nums[i]> minHeap.peek()){
                minHeap.poll();
                minHeap.add(nums[i]);
            }


        }
        return new ArrayList<>(minHeap);
    }

    public static void main(String[] args) {
        // some test cases
        List<Integer> result = findKLargestNumbers(new int[] { 3, 1, 5, 12, 2, 11 }, 3);
        System.out.println("Here are the K largest numbers: " + result);
        result = findKLargestNumbers(new int[] { 5, 12, 11, -1, 12 }, 3);
        System.out.println("Here are the K largest numbers: " + result);
        // more test cases
        result = findKLargestNumbers(new int[] { 1, 2, 3, 4, 5 }, 2);
        System.out.println("Here are the K largest numbers: " + result);
        result = findKLargestNumbers(new int[] { 10, 9, 8, 7, 6 }, 4);
        System.out.println("Here are the K largest numbers: " + result);


    }
}

