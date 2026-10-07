package com.grokingcodeinterview.pattern18ModifiedBinarySearch;

/*
problem statement:
Given an array of numbers sorted in ascending order,  find the ceiling of a given number â€˜keyâ€™.
 The ceiling of the â€˜keyâ€™ will be the smallest element in the given array greater than or equal to the â€˜keyâ€™.
Write a function to return the index of the ceiling of the â€˜keyâ€™.  If there isnâ€™t any ceiling return -1.

Example 1: Input: [4, 6, 10], key = 6 Output: 1
Explanation: The smallest number greater than or equal to '6' is '6' having index '1'.
Example 2: Input: [1, 3, 8, 10, 15], key = 12 Output: 4
Explanation: The smallest number greater than or equal to '12' is '15' having index '4'.
Example 3: Input: [4, 6, 10], key = 17 Output: -1
Explanation: There is no number greater than or equal to '17' in the given array.
Example 4: [4, 6, 10], key = -1
Output: 0
Explanation: The smallest number greater than or equal to '-1' is '4' having index '0'.
Constraints:

1 <= arr.length <= 10^4
-10^4 < arr[i], key < 10^4
Given array contains distinct values sorted in ascending order.
 */

import java.util.Arrays;

import static com.Utility.makeItBold;

/**
 *   *  smallestNumber>=key
 *      *  example: [1, 3, 8, 10, 15], key = 12
 *      *  s = 0, en= 5 mid = 2 mid[2] = 8< 12 , so go to right = s = mid +1
 *      *  s = 3, e =5, mid = 4, mid[4] = 10<12 go to right = s = mid +1
 *      *  s = 5, e =5 , mid = 5, mid[5] = 15> go to left, e = mid -1 , e = 4
 *      *  return start = 5;
 *      * [2, 5, 9, 14, 18, 21, 24, 30, 33, 37, 40] key,20
 *      * s = 0, e = 11, mid = 5, mid[5] = 21 > 20 then go left end = 6
 *      * s = 0, e = 6, mid = 3, mid[3] = 14 < 20 then go right start = 4
 *      * s = 4, e = 6, mid = 5, mid[5] = 21> 20 then go left  end = 4
 *      * s = 4, e= 4, mid = 4, mid[4] = 18<20 then go right start = 5
 *      * return start = 5 nice
 *
 *  next example [4, 6, 10], key = -1
 *  s = 0, e = 2, mid = 1, arr[mid] = 6> -1 then go to left = e = 0
 *  s = 0, e = 0 mid = 0 arr[mid] = 4> -1, then go to left : e =-1
 *  return 0;
 *  next example: [4, 6, 10], key = 17
 *  17>10 then -1;
 *  s = 0, e = 2, mid = 1,  mid[1= 6<17 go to right: s = 2
 *  s = 2, e = 2, mid = 2, mid[2] = 10 10< 17 then got right
 *  s =
 */
public class P02CeilingOfNumber {
    public static int searchCeilingOfANumber(int[] arr, int key) {
        if (key > arr[arr.length -1]){
            return -1;
        }

        int start = 0;
        int end = arr.length -1;
        int ceilingIndex = -1;


        while (start<= end){
            int mid = start + (end -start)/2;

            if (arr[mid] == key){
                return mid;
            }else if (arr[mid]< key){
                start = mid+1;

            }else{ // valid ceiling candidate
                ceilingIndex = mid;
               end = mid -1;
            }
    }
        return ceilingIndex;

}

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("P02. Ceiling Of A Number");
        System.out.println("==============================================");

        int[] numsP02 = {4, 6, 10};
        int keyP02 = 6;
        System.out.println("Input: " + Arrays.toString(numsP02) + ", key = " + keyP02 +" ,output: " +makeItBold(searchCeilingOfANumber( numsP02, keyP02)+"") +" ,Expected Output: 1");
        numsP02 = new int[]{1, 3, 8, 10, 15};
        keyP02 = 12;
        System.out.println("Input: " + Arrays.toString(numsP02) + ", key = " + keyP02 +" ,output: " +makeItBold(searchCeilingOfANumber( numsP02, keyP02)+"") +" ,Expected Output: 4");
        numsP02 = new int[]{4, 6, 10};
        keyP02 = 17;
        System.out.println("Input: " + Arrays.toString(numsP02) + ", key = " + keyP02 +" ,output: " +makeItBold(searchCeilingOfANumber( numsP02, keyP02)+"") +" ,Expected Output: -1");
        numsP02 = new int[]{4, 6, 10};
        keyP02 = -1;
        System.out.println("Input: " + Arrays.toString(numsP02) + ", key = " + keyP02 +" ,output: " +makeItBold(searchCeilingOfANumber( numsP02, keyP02)+"") +" ,Expected Output: 0");

    }
}

