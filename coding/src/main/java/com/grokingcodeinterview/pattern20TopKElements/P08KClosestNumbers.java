package com.grokingcodeinterview.pattern20TopKElements;

/*
 Problem Statement
 Given a sorted number array and two integers â€˜Kâ€™ and â€˜Xâ€™,
 find â€˜Kâ€™ closest numbers to â€˜Xâ€™ in the array.
 Return the numbers in the sorted order. â€˜Xâ€™ is not necessarily present in the array.
 *
 Example 1:  Input: [5, 6, 7, 8, 9], K = 3, X = 7   Output: [6, 7, 8]
 Example 2:  Input: [2, 4, 5, 6, 9], K = 3, X = 6   Output: [4, 5, 6]
 Example 3:  Input: [2, 4, 5, 6, 9], K = 3, X = 10  Output: [5, 6, 9]
 Constraints:

 1 <= k <= arr.length
 1 <= arr.length <= 104
 arr is sorted in ascending order.
 -104 <= arr[i], x <= 104
 */

import java.util.*;

import static com.Utility.makeItBold;

/**
 * * Input: [5, 6, 7, 8, 9], K = 3, X = 7 *  * Output: [6, 7, 8]
 * find the k closes number to x in the soretd array
 *  maxHeap(k elemetn) =
 *  comparator oder = ditance to x => (nums[i]-x) for ties lareger element first
 *
 *
 */
public class P08KClosestNumbers {
    //NOTE: this problem can be solved with 4 appraoch , try all for approaches
    /*
    | Approach                                        | Time Complexity  | Space Complexity |
| ----------------------------------------------- | ---------------- | ---------------- |
| **1. Binary Search + Sliding Window (optimal)** | **O(log n + k)** | **O(k)**         |
| **2. Binary Search + Two Pointers**             | **O(log n + k)** | **O(k)**         |
| **3. Binary Search + Local Window + Min-Heap**  | **O(k log k)**   | **O(k)**         |
| **4. Full Min-Heap Only (scan entire array)**   | **O(n log k)**   | **O(k)**         |

     */


    public static List<Integer> findClosesElements_UsingBinarySearchAndSlidingWindow(int[] nums, int k, int x){
        // step1: find insertion point of x

        int n = nums.length;
        int start =0;
        int end = nums.length -1;

        while (start<= end){
            int mid = start +(end - start)/2;
            if (nums[mid]>= x){
                end = mid -1;
            }else{
                start = mid +1;
            }

        }
        // start is insertion point we need to pointer   [ < X ] (left = start - 1 / end) | (start = insertion point) [ >= X ] (right = start)
        int left = start -1;
        int right = start;

        List <Integer> result = new ArrayList<>();

        while (k>0){
            if (left<0){

                result.add(nums[right]);
                right ++;

            } else if (right >= n){

                result.add(nums[left]);
                left--;
            }else if (Math.abs(nums[left] -x)<= Math.abs(nums[right] -x)){
               result.add(nums[left]);
               left--;
            }else{
                result.add(nums[right]);
                right ++;

            }
            k--;
        }

        Collections.sort(result);
        return result;
    }
    public static List<Integer> findClosestElements(int[] nums, int k, int x){

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> {
            int diff = Math.abs(b - x) - Math.abs(a - x);
            if (diff != 0) return diff;
            return b-a;
        });

        for (int num: nums){
            maxHeap.offer(num);
            if (maxHeap.size()> k){
                maxHeap.poll();
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!maxHeap.isEmpty()){
            result.add(maxHeap.poll());

        }

        Collections.sort(result);

        return result;


    }

    public static void main(String[] args) {

        System.out.println("================================================");
        System.out.println("P08. K closest elment ");
        System.out.println("================================================");
        int[] numsP08 = { 5, 6, 7, 8, 9 };
        int kP08 = 3, xP08 = 7;  List<Integer> resultP08 = findClosestElements(numsP08, kP08, xP08);
        System.out.println("Input: " + Arrays.toString(numsP08) + ", K = " + kP08 + ", X = " + xP08 + " ,Output: " + makeItBold(resultP08.toString()) +", Expected Output: [6, 7, 8]");

         numsP08 = new int[] { 2, 4, 5, 6, 9 };
         kP08 = 3;
         xP08 = 6;
        resultP08 = findClosestElements(numsP08, kP08, xP08);
        System.out.println("Input: " + Arrays.toString(numsP08) + ", K = " + kP08 + ", X = " + xP08 +", Output: " + makeItBold(resultP08.toString()) + ", Expected Output: [4, 5, 6]");

        numsP08 =  new int[]{ 2, 4, 5, 6, 9 };
        kP08 = 3;
        xP08 = 10;
         resultP08 = findClosestElements(numsP08, kP08, xP08);
        System.out.println("Input: " + Arrays.toString(numsP08) + ", K = " + kP08 + ", X = " + xP08 +", Output: " + makeItBold(resultP08.toString()) + ", Expected Output: [5, 6, 9]");
      numsP08 = new int[] { 1, 3, 5, 7, 9 };
         kP08 = 2;
        xP08 = 4;
         resultP08 = findClosestElements(numsP08, kP08, xP08);
        System.out.println("Input: " + Arrays.toString(numsP08) + ", K = " + kP08 + ", X = " + xP08 +", Output: " + makeItBold(resultP08.toString()) + ", Expected Output: [3, 5]");
    }

}

