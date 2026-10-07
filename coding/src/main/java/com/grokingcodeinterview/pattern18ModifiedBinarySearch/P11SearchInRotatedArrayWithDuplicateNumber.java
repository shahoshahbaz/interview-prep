package com.grokingcodeinterview.pattern18ModifiedBinarySearch;

/*
 Problem Statement:
You are given an array of integers that was originally sorted in ascending order but then rotated at an unknown pivot.
Unlike the classic problem, this array may contain duplicate values, which makes some of the normal binary-search checks unreliable.
Your task is to search for a given key in this rotated array and return its index if found.
If the key is not present, return -1.
Example 1: Input: nums = [3, 7, 3, 3, 3] Key = 7 Output: 1
Example 2: Input: nums = [2, 2, 2, 3, 2] key = 3 Output: 3
Example 3: Input: nums = [4, 4, 4, 5, 1, 2, 3, 4] key = 1 Output: 4
 */

import java.util.Arrays;

import static com.Utility.makeItBold;

/**
 * nums = [4, 4, 4, 5, 1, 2, 3, 4] key = 1  * Output: 4
 * s = 0, e= 7 , mid = 3  now 4< 5 left is sorted
 * key is not beween [s, mid] then we skip the left side and we continue our search with [mid +1, end] = [4, 7]
 * s = 4, e = 7, mid = 5,  1< 2 so leftside is sorted,
 * key is between[1, 2] then
 * s= 4, e = 5 mid = 4, nums[mid] == key then key = 4
 *
 *  Input: nums = [3, 7, 3, 3, 3]Key = 7  * Output: 1
 *  s = 0, e= 4, mid =2, now nums[0]  == nums[mid] and nums[4] = nums[mid] then from each side we move the start and end
 *  s =1, e = 3, mid = 2,   7> 3 right side sorted 7 is [3, 3]  no then seach in [1,1]
 *  s =1, e = 1, mid =1 then we found our number.
 *
 *
 */
public class P11SearchInRotatedArrayWithDuplicateNumber {
    public static int searchInRotatedArrayWithDuplicate(int[] nums, int key){
        int start = 0;
        int end = nums.length -1;
        while (start<= end){
            int mid = start + (end -start)/2;

            if (nums[mid ] == key) return mid;

            if(nums[start] == nums[mid]  && nums[end] == nums[mid]){
                start ++;
                end --;
            }else if (nums[start]<= nums[mid]){ // left side is sorted
                // now check if key is in [start, mid -1]
                if (key>= nums[start] && key< nums[mid]){ // if yes, do the seach in [start, mid -1]
                    end = mid -1;
                }else{ // if not search [mid +1, end]
                    start = mid +1;
                }
        }else { // right side is sorted
                // now check if the key is in this range [mid , end]
                if (key> nums[mid] && key<= nums[end]){
                    start = mid +1;
                }else{
                    end = mid -1;
                }
            }
            }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("P11. Search In Rotated Array With Duplicate Number");
        System.out.println("==============================================");

        int[] numsP11 = new int[]{4, 4, 4, 5, 1, 2, 3, 4};
        int keyP11 = 1;
        System.out.println("Input: " +Arrays.toString(numsP11)+ ", key = " + keyP11 +", Output: "+ makeItBold(searchInRotatedArrayWithDuplicate(numsP11, keyP11)+"")+ " ,Expected: 4");
        numsP11 = new int[]{3, 7, 3, 3, 3};
        keyP11 = 7;
        System.out.println("Input: " +Arrays.toString(numsP11)+ ", key = " + keyP11 +", Output: "+ makeItBold(searchInRotatedArrayWithDuplicate(numsP11, keyP11)+"")+ " ,Expected: 1");
        numsP11 = new int[]{2, 2, 2, 3, 2};
        keyP11 = 3;
        System.out.println("Input: " +Arrays.toString(numsP11)+ ", key = " + keyP11 +", Output: "+ makeItBold(searchInRotatedArrayWithDuplicate(numsP11, keyP11)+"")+ " ,Expected: 3");
        numsP11 = new int[]{1, 1, 3, 1};
        keyP11 = 3;
        System.out.println("Input: " +Arrays.toString(numsP11)+ ", key = " + keyP11 +", Output: "+ makeItBold(searchInRotatedArrayWithDuplicate(numsP11, keyP11)+"")+ " ,Expected: 2");

    }
}

