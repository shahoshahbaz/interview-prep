package com.grokingcodeinterview.pattern03TwoPointers;

/*Problem Statement
Given an array of unsorted numbers,
find all unique triplets in it that add up to zero.
Example 1
Input: [-3, 0, 1, 2, -1, 1, -2]
Output: [[-3, 1, 2], [-2, 0, 2], [-2, 1, 1], [-1, 0, 1]]
Explanation: There are four unique triplets whose sum is equal to zero.
Example 2
Input: [-5, 2, -1, -2, 3]
Output: [[-5, 2, 3], [-2, -1, 3]]
Explanation: There are two unique triplets whose sum is equal to zero.
Constraints:

3 <= arr.length <= 3000
-105 <= arr[i] <= 105
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * [-3, 0, 1, 2, -1, 1, -2] => sort => [-3, -2, -1, 0, 1, 1, 2]=> set
 *  i =0, left = 1, right =6 then sum = arr[i] + arr[left] + arr[right] = -3 + -2 + 2 = -3 <0 => left++
 *  i =0, left = 2, right =6 then sum = arr[i] + arr[left] + arr[right] = -3 + -1 + 2 = -2 <0 => left++
 *  i =0, left = 3, right =6 then sum = arr[i] + arr[left] + arr[right] = -3 + 0 + 2 = -1 <0 => left++
 *  i =0, left = 4, right =6 then sum = arr[i] + arr[left] + arr[right] = -3 + 1 + 2 = 0 => add to result, left++, right--
 *  check for duplicates
 *  i =0, left = 5, right =5 => break
 *  i =1, left = 2, right =6 then sum = arr[i] + arr[left] + arr[right] = -2 + -1 + 2 = -1 <0 => left++
 *  i =1, left = 3, right =6 then sum = arr[i] + arr[left] + arr[right] = -2 + 0 + 2 = 0 => add to result, left++, right--
 *  check for duplicates
 *  i =1, left = 4, right =5 then sum = arr[i] + arr[left] + arr[right] = -2 + 1 + 1 = 0 => add to result, left++, right--
 *  check for duplicates?
 *  i =1, left = 5, right =4 => break
 *  i =2, left = 3, right =6 then sum = arr[i] + arr[left] + arr[right] = -1 + 0 + 2 = 1 >0 => right--
 *  i =2, left = 3, right =5 then sum = arr[i] + arr[left] + arr[right] = -1 + 0 + 1 = 0 => add to result, left++, right--
 *  check for duplicates?
 *  i =2, left = 4, right =4 => break
 *  i =3, left = 4, right =6 then sum = arr[i] + arr[left] + arr[right] = 0 + 1 + 2 = 3 >0 => right--
 *  i =3, left = 4, right =5 then sum = arr[i] + arr[left] + arr[right] = 0 + 1 + 1 = 2 >0 => right--
 *  i =3, left = 4, right =4 => break
 *  i =4, left = 5, right =6 then sum = arr[i] + arr[left] + arr[right] = 1 + 1 + 2 = 4 >0 => right--
 *  i =4, left = 5, right =5 => break
 *  i =5, left = 6, right =6 => break
 *  i =6, left = 7, right =6 => break
 *  return result
 *
 *
 *
 */
public class P05TripletSumToZero {

    public static List<List<Integer>> searchTriplets(int[] nums) {
        List<List<Integer>> triplets = new ArrayList<>();
        // sorting array
        Arrays.sort(nums);

        int n= nums.length;

        for (int i =0; i< n; i++){

            if (i> 0 && nums[i] == nums[i-1] ) continue; // skip same element to avoid duplicate triplets, why we should use while here? because we are only skipping one element


            int left = i+1;
            int right = n-1;

            while(left< right){

                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0 ){
                    triplets.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right --;

                    while( left< right && nums[left -1] == nums[left]) left++; // skip same element to avoid duplicate triplets
                    while (left< right  && nums[right +1] == nums[right] ) right --;   // skip same element to avoid duplicate triplets
                }else if (sum<0){
                    left++;
                }else{
                    right --;
                }
            }
        }

        return triplets;

    }

    public static void main(String[] args) {

        int[] nums = new int[] { -3, 0, 1, 2, -1, 1, -2 };
        List<List<Integer>> result = searchTriplets(nums);
        System.out.println("Triplets summing to zero for this array " + Arrays.toString(nums) + " : " + result + "Expected output: [[-3, 1, 2], [-2, 0, 2], [-2, 1, 1], [-1, 0, 1]]");
        nums = new int[] { -5, 2, -1, -2, 3 };
        result = searchTriplets(nums);
        System.out.println("Triplets summing to zero for this array " + Arrays.toString(nums) + " : " + result + " Expected output: [[-5, 2, 3], [-2, -1, 3]]");
    }
}
