package com.grokingcodeinterview.pattern18ModifiedBinarySearch;
/*
Given an array of numbers sorted in ascending order,
 find the element in the array that has the minimum difference with the given â€˜keyâ€™.

Example 1: Input: [4, 6, 10], key = 7 Output: 6
Explanation: The difference between the key '7' and '6' is minimum than any other number in the array

Example 2:Input: [4, 6, 10], key = 4 Output: 4 Example 3:

Input: [1, 3, 8, 10, 15], key = 12 Output: 10

Example 4: Input: [4, 6, 10], key = 17 Output: 10
 */

import java.util.Arrays;

import static com.Utility.makeItBold;

/**
 * sorted ascending order, find min diff with an a key
 *  arr = [2, 5, 10, 12, 15], key = 11 length = 4
 *  s = 0, e = 4, mid = 2, nums[2] =12> key
 *  s = 0, e = 3, mid = 1, nums[1] =5< key
 *  s = 2, e =3, mid =2 , nums[2] =10< key
 *  s = 3, e = 3, mid = 3, nums[3] = 12> key
 *  s = 3, e= 2, break the loop
 *  s<length skip
 *  e> 0 skip
 *  compare diff of start and key and  diff lastand key elements with key
 *  s = 3, e =2,    |12 - 11| =1  |10-11| = 1 retuns nums[s]  =10
 *
 *
 *
 *
 */
public class P07MinimumDifferenceElement {
    public static int searchMinDiffElement(int[] arr, int key) {
        int n = arr.length;
        int start = 0;
        int end = n-1;
        // we run a normal binary Search
        // but if element is not found, we will have start > end
        // why

        while (start<=end){

            int mid = start + (end - start)/2;

            if (arr[mid]< key){
                start = mid +1;

            }else if (arr[mid]> key) {
                end = mid - 1;
            }else{
                return arr[mid];
            }
        }
            // now here start > end
            //if start goes out of the array bounds then key is bigger than all elements so return last element
            if (start>= n){
                return arr[n-1];
            }
            //if end goes out of the array bouunds then key is smallere than all elements so return first element
            if (end<0){
                return arr[0];
            }
           // otherwise, copare which one(start and end) is closer, the element at index 'start' or 'end'
            if (Math.abs(key -arr[start])<= Math.abs(arr[end] -key)) {
                return arr[start];
            }
                return arr[end];



    }
    public static void main(String[] args) {

        System.out.println("======================================================");
        System.out.println("P07. Minimum Difference Element");
        System.out.println("======================================================");

        int[] numsP07 = new int[]{4, 6, 10};
        System.out.println(makeItBold(Arrays.toString(numsP07)) + ", key ="+ makeItBold("7") +
                ", MinDiffElement is: "+       makeItBold(""+searchMinDiffElement(numsP07, 7)) +" Expected:" + makeItBold(" 6"));

        numsP07 = new int[]{1, 3, 8, 10, 15};
        System.out.println(makeItBold(Arrays.toString(numsP07)) + ", key ="+ makeItBold("12") +
                ", MinDiffElement is: "+       makeItBold(""+searchMinDiffElement(numsP07, 12)) +" Expected:" + makeItBold(" 10"));


        numsP07 = new int[]{4, 6, 10};
        System.out.println(makeItBold(Arrays.toString(numsP07)) + ", key ="+ makeItBold("4") +
                ", MinDiffElement is: "+       makeItBold(""+searchMinDiffElement(numsP07, 4)) +" Expected:" + makeItBold(" 4"));


    }
}

