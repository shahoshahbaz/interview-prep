package com.grokingcodeinterview.pattern03TwoPointers;

/*
 * Problem Statement
Given an array of unsorted numbers and a target number,
*  find a triplet in the array whose sum is as close to the target number as possible,
*  return the sum of the triplet.
*  If there are more than one such triplet,
*  return the sum of the triplet with the smallest sum.

Example 1:

Input: [-1, 0, 2, 3], target=3
Output: 2
Explanation: The triplet [-1, 0, 3] has the sum '2' which is closest to the target.

There are two triplets with distance '1' from the target: [-1, 0, 3] & [-1, 2, 3].
*  Between these two triplets, the correct answer will be [-1, 0, 3] as
* it has a sum '2' which is less than the sum of the other triplet which is '4'.
*  This is because of the following requirement:
* 'If there are more than one such triplet,
*  return the sum of the triplet with the smallest sum.'
Example 2:

Input: [-3, -1, 1, 2], target=1
Output: 0
Explanation: The triplet [-3, 1, 2] has the closest sum to the target.
Example 3:

Input: [1, 0, 1, 1], target=100
Output: 3
Explanation: The triplet [1, 1, 1] has the closest sum to the target.
Example 4:

Input: [0, 0, 1, 1, 2, 6], target=5
Output: 4
Explanation: There are two triplets with distance '1' from target: [1, 1, 2] & [0, 0, 6]. Between these two triplets, the correct answer will be [1, 1, 2] as it has a sum '4' which is less than the sum of the other triplet which is '6'. This is because of the following requirement: 'If there are more than one such triplet, return the sum of the triplet with the smallest sum.'
Constraints:

3 <= arr.length <= 500
-1000 <= arr[i] <= 1000
-104 <= target <= 104
 */

import java.util.Arrays;

/**
 *
 */
public class P06TripletSumCloseToTarget {

    public  static int searchTriplets(int[] arr, int target){
        int n = arr.length;
        if (n< 3) return 0;
        Arrays.sort(arr);
        int closestSum = Integer.MAX_VALUE;
        int smallestDiff = Integer.MAX_VALUE;

        for (int i =0; i< n -2;i++){
            int left = i+1;
            int right = n-1;

            while (left< right){

                int currentSum = arr[i] + arr[left] + arr[right];
                int currentDiff = Math.abs(currentSum - target);

                if (currentDiff ==0 ){
                    return currentSum;
                }

                if  (currentDiff< smallestDiff ||
                        ( currentDiff == smallestDiff && currentSum<closestSum )){
                    smallestDiff = currentDiff;
                    closestSum = currentSum;

                }

                // move pointer
                if (currentSum<target){
                    left ++;
                }else if (currentSum> target){
                    right --;
                }
            }


        }

        return closestSum;
    }

    public static void main(String[] args) {
        // some test cases
        System.out.println(searchTriplets(new int[] {-1, 0, 2, 3}, 3)); //2
        System.out.println(searchTriplets(new int[] {-3, -1, 1, 2}, 1)); //0
        System.out.println(searchTriplets(new int[] {1, 0, 1, 1}, 100)); //3
        System.out.println(searchTriplets(new int[] {0, 0, 1, 1, 2, 6}, 5)); //4






    }
}
