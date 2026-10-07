package com.grokingcodeinterview.pattern20TopKElements;
/*
problem statement:
Design a class to efficiently find the Kth largest element in a stream of numbers.
The class should have the following two things:
The constructor of the class should accept an integer array containing initial numbers from the stream and an integer â€˜Kâ€™.
The class should expose a function add(int num) which will store the given number and return the Kth largest number.
Example 1:
Input: [3, 1, 5, 12, 2, 11], K = 4
1. Calling add(6) should return '5'.
2. Calling add(13) should return '6'.
2. Calling add(4) should still return '6'.
Constraints:

1 <= k <= 10^4
0 <= nums.length <= 10^4
-104 <= nums[i] <= 10^4
-104 <= val <= 10^4
At most 10^4 calls will be made to add.
It is guaranteed that there will be at least k elements in the array when you search for the kth element.
 */

import java.util.Arrays;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;

/**
 * nput: [3, 1, 5, 12, 2, 11], K = 4
 * 1. Calling add(6) should return '5'.
 * 2. Calling add(13) should return '6'.
 * 2. Calling add(4) should still return '6'.
 *
 *
 */
public class P07KthLargestNumberInAStream {

    public static class KthLargestHelper {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int k;
        // --- Constructor ---
        public KthLargestHelper(int[] nums, int k) {
            this.k = k;

            for (int num: nums){
                add(num);
            }

        }

        // --- Methods ---
        public int add(int num) {
            minHeap.add(num);

            if (minHeap.size()> k){
                minHeap.poll();
            }
            return minHeap.peek();
        }
    }


    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("P07 Kth Largest Number in a Stream (medium)");
        System.out.println("===============================================================");

        int[] numsP07 = {3, 1, 5, 12, 2, 11};
        int k = 4;

        KthLargestHelper streamP07 = new KthLargestHelper(numsP07, k);

        System.out.println("Input Array: "+ Arrays.toString(numsP07) +  " K:"  +  k +  " ,current Heap" +
                        streamP07.minHeap.toString());

        System.out.println(" streamP07.add(6): " + makeItBold(streamP07.add(6) + "")  +" ,expected: 5" );
        System.out.println(" streamP07.add(13): " + makeItBold(streamP07.add(13) + "") +" ,expected: 6");
        System.out.println(" streamP07.add(4): " + makeItBold(streamP07.add(4) + "")  +" ,expected: 6"   );

    }
}

