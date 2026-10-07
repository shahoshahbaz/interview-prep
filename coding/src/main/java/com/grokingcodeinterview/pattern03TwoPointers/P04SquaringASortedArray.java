package com.grokingcodeinterview.pattern03TwoPointers;
/*
Squaring a Sorted Array
Given a sorted array,
 create a new array containing squares of all the numbers
 of the input array in the sorted order.

Example 1:

Input: [-2, -1, 0, 2, 3]
Output: [0, 1, 4, 4, 9]
Example 2:

Input: [-3, -1, 0, 1, 2]
Output: [0, 1, 1, 4, 9]
Constraints:

1 <= arr.length <= 104
-104 <= arr[i] <= 104
arr is sorted in non-decreasing order.
 */

import java.util.Arrays;

/**
 * [-2(L), -1, 0, 2, 3(R)] 4, 9
 *   [-2(L), -1, 0(R), 2(), 3(R)] 4,4
 *  * [
 */
public class P04SquaringASortedArray {

    public  static int[] makeSquares(int[] arr) {
//       if(arr.length ==1) return  new int[1]{(arr[0] * arr[0]) } ;

        int left = 0;
        int right = arr.length -1;
        int[] newArray = new int[arr.length];
        int highestSquareIndex = arr.length -1;
        while(left<right) {
            int squareLeft = arr[left] * arr[left];
            int squareRight = arr[right] * arr[right];
            if (squareRight >= squareLeft){
                newArray[highestSquareIndex] = squareRight;
                right --;

            }else{
                newArray[highestSquareIndex] = squareLeft;
                left ++;

            }
            highestSquareIndex--;
        }
        return newArray;
    }

    public static void main(String[] args) {


        int[] arr = new int[] { -2, -1, 0, 2, 3 };
        System.out.print(Arrays.toString(arr));
        int[] result = makeSquares(arr);
        System.out.println(Arrays.toString(result) +", Expected output: [0, 1, 4, 4, 9]");

        arr = new int[] { -3, -1, 0, 1, 2 };
        System.out.print(Arrays.toString(arr));
       result = makeSquares(arr);
        System.out.println(Arrays.toString(result) +", Expected output: [0, 1, 1, 4, 9 ]"); // Expected output: [0, 1, 1, 4, 9 ]
        }

    }

