package com.grokingcodeinterview.pattern20TopKElements;
/*
Problem Statement
Given â€˜Nâ€™ ropes with different lengths,
 we need to connect these ropes into one big rope with minimum cost.
  The cost of connecting two ropes is equal to the sum of their lengths.


Example 1:

Input: [1, 3, 11, 5]
Output: 33
Explanation: First connect 1+3(=4), then 4+5(=9), and then 9+11(=20). So the total cost is 33 (4+9+20)
Example 2:

Input: [3, 4, 5, 6]
Output: 36
Explanation: First connect 3+4(=7), then 5+6(=11), 7+11(=18). Total cost is 36 (7+11+18)
Example 3:

Input: [1, 3, 11, 5, 2]
Output: 42
Explanation: First connect 1+2(=3), then 3+3(=6), 6+5(=11), 11+11(=22). Total cost is 42 (3+6+11+22)
Constraints:

1 <= ropLengths.length <= 104
1 <= ropLengths[i] <= 104
 */

import java.util.Arrays;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;

/**
 * Input: [1, 3, 11, 5, 2]
 * Output: 42
 * Explanation: First connect 1+2(=3), then 3+3(=6), 6+5(=11), 11+11(=22). Total cost is 42 (3+6+11+22)
 * Constraints:
 * minHeap [1, 2, 3, 5, 11]
 * poll two top
 * 1+2 = 3 add(3) then [3,3,5, 11]
 * poll two top 3+3 = 6 add(6) [ 6, 5, 11]
 * ....
 * add
 *  Input: [3, 4, 5, 6]
 * Output: 36
 */
public class P04ConnectRopes {
    public  static int minimumCostToConnectRopes(int[] ropeLengths) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // first add the number
        for (int ropes: ropeLengths){
            minHeap.add(ropes);
        }
//        System.out.println("\t minHeap initial: " + minHeap);
        int totalCost =0;
        while ( !minHeap.isEmpty() && minHeap.size()>1){
            int rope1 = minHeap.poll();
            int rope2 = minHeap.poll();
            int newRope = rope1 + rope2;
            totalCost += newRope;
            minHeap.add(newRope);

        }
        return totalCost;
    }

    public static void main(String[] args) {

        System.out.println("==========================");
        System.out.println("P04. Connect ropes");
        System.out.println("==========================");
        // give me some exapmles with expected output
        int[] ropeLengths = {1, 3, 11, 5};
        int resultP04 = minimumCostToConnectRopes(ropeLengths);
        System.out.println("ropeLengths: "+ makeItBold(Arrays.toString(ropeLengths))
        + ", Minimum cost to connect ropes: " + makeItBold(String.valueOf(resultP04)) + " (Expected: 33)");
        // more examples
        int[] ropeLengths2 = {3, 4, 5, 6};
        resultP04 = minimumCostToConnectRopes(ropeLengths2);
        System.out.println("ropeLengths: "+ makeItBold(Arrays.toString(ropeLengths2))
                + ", Minimum cost to connect ropes: " + makeItBold(String.valueOf(resultP04)) + " (Expected: 36)");

        // give me 2 more examples
        int[] ropeLengths3 = {1, 3, 11, 5, 2};
        resultP04 = minimumCostToConnectRopes(ropeLengths3);
        System.out.println("ropeLengths: "+ makeItBold(Arrays.toString(ropeLengths3))
                + ", Minimum cost to connect ropes: " + makeItBold(String.valueOf(resultP04)) + " (Expected: 42)");
        int[] ropeLengths4 = {5, 2, 1, 4, 3};
        resultP04 = minimumCostToConnectRopes(ropeLengths4);
        System.out.println("ropeLengths: "+ makeItBold(Arrays.toString(ropeLengths4))
                + ", Minimum cost to connect ropes: " + makeItBold(String.valueOf(resultP04)) + " (Expected: 33)");

    }
}

