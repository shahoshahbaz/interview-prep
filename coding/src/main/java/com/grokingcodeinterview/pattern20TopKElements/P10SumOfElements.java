package com.grokingcodeinterview.pattern20TopKElements;

/*
Problem Statement
Given an array, find the sum of all numbers between the K1â€™th and K2â€™th smallest elements of that array.

Example 1: Input: [1, 3, 12, 5, 15, 11], and K1=3, K2=6 Output: 23
Explanation: The 3rd smallest number is 5 and 6th smallest number 15. The sum of numbers coming
between 5 and 15 is 23 (11+12).
Example 2: Input: [3, 5, 8, 7], and K1=1, K2=4 Output: 12
Explanation: The sum of the numbers between the 1st smallest number (3) and the 4th smallest
number (8) is 12 (5+7).
 */

import java.util.Arrays;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;

/**
 * Input: [1, 3, 12, 5, 15, 11], and K1=3, K2=6
 * 3d smallest = 5
 * and  6th smallest = 12
 * The 3rd smallest number is 5 and 6th smallest number 15
 * 12 +
 * Output: 23
 *
 * [15, 12, 11, 5, 3, 1 ]
 */
public class P10SumOfElements {

    public  static int findSumOfElements(int[] nums, int k1, int k2) {

        // the problem didn't mentioned the the condtion on k1 and k2 and which one is bigger
        int maxK = Math.max(k1, k2);
        int minK = Math.min(k1, k2);
        if (minK<0 || maxK>nums.length || maxK - minK <=1) return 0;



        int elementSum = 0;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num: nums){
            minHeap.offer(num);
        }
        // so we need number between (min(K1,K2)+1) through (max(K1,K2)-1)
         for (int i =0; i< minK; i++) {
             minHeap.poll();
         }

         for (int i =0; i< maxK-minK -1; i++){
             elementSum = elementSum +minHeap.poll();
         }

        return elementSum;
    }

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("P10. Sum of Elements... ");
        System.out.println("===================================");
        int numsP10[] = new int[] {1, 3, 12, 5, 15, 11};
        int k1P10 =3;
        int k2P10 =6;
        System.out.println("Input: "  + Arrays.toString(numsP10) + ", k1: "
                +k1P10 + ", k2: " + k2P10 +", output:" + makeItBold(findSumOfElements(numsP10,k1P10,k2P10)+"")+" ,expected: 23");

        numsP10 = new int[] {3, 5, 8, 7};
        k1P10 =1;
        k2P10 =4;

        System.out.println("Input: "+ Arrays.toString( numsP10) + ", k1: "
                + k1P10 + ", k2: " + k2P10 +" ,output:" + makeItBold(findSumOfElements(numsP10,k1P10,k2P10)+"")+"  ,expected: 12");

        numsP10 = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        k1P10 =2;
        k2P10 =5;
        System.out.println("Input: "  +Arrays.toString( numsP10) + ", k1: "
                + k1P10 + ", k2: " + k2P10 +" ,output:" + makeItBold(findSumOfElements(numsP10,k1P10,k2P10)+"")+"  ,expected: 15");
        numsP10 = new int[] {10, 20, 30, 40, 50};
        k1P10 =1;
        k2P10 =3;
        System.out.println("Input: "  +Arrays.toString( numsP10) + ", k1: "
                + k1P10 + ", k2: " + k2P10 +" ,output:" + makeItBold(findSumOfElements(numsP10,k1P10,k2P10)+"")+"  ,expected: 60");

    }
}

