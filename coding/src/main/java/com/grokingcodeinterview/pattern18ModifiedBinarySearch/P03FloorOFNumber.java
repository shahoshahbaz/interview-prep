package com.grokingcodeinterview.pattern18ModifiedBinarySearch;

/*
problem statement:
Given an array of numbers sorted in ascending order,  find the floor of a given number â€˜keyâ€™.
 The floor of the â€˜keyâ€™ will be the biggest element in the given array smaller than or equal to the â€˜keyâ€™
Write a function to return the index of the floor of the â€˜keyâ€™. If there isnâ€™t a floor, return -1.

Example 1: Input: [4, 6, 10], key = 6 Output: 1
Explanation: The biggest number smaller than or equal to '6' is '6' having index '1'.
Example 2: Input: [1, 3, 8, 10, 15], key = 12 Output: 3
Explanation: The biggest number smaller than or equal to '12' is '10' having index '3'.
Example 3: Input: [4, 6, 10], key = 17 Output: 2
Explanation: The biggest number smaller than or equal to '17' is '10' having index '2'.
Example 4: Input: [4, 6, 10], key = -1 Output: -1
Explanation: There is no number smaller than or equal to '-1' in the given array.
 */

import java.util.Arrays;

import static com.Utility.makeItBold;

/**
 * biggestNumber<= key
 * example: [4, 6, 10], key = 6
 * s =0, e=2, mid = 1, arr[mid] =6, return 1
 * example: [1, 3, 8, 10, 15], key = 12
 * s = 0, e= 4, mid = 2, mid[2] =8<= key // validfloor candiate floorIndex = 2
 * s = 3, en = 4, mid = 3, mid[3] = 10 <key // floorIndex = 3
 * s = 4, e = 4, md [4] = 15> key, go left,
 * s= 4, e= 3, break from loop floorIndex = 3
 */
public class P03FloorOFNumber {
    public  static int searchFloorOfANumber(int[] arr, int key){
        if (arr[0]>key){
            return -1;
        }

        int start = 0;
        int end = arr.length -1;
        int floorIndex = -1;
        while(start<= end){
            int mid = start + (end - start)/2;
            if (arr[mid]<key){ // valid floor candidate
                floorIndex = mid; // update floorIndex
                start = mid +1;

            }else if (arr[mid]> key){ // got the left
                end = mid -1;

            }else{
                return mid;
            }
        }

        return floorIndex;

    }

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("P03. Floor Of Number");
        System.out.println("==============================================");
        int numsP03[] = {4,6,10};
        int keyP03 = 6;
        System.out.print("Input Array: " + Arrays.toString(numsP03) + ", key = " + keyP03 +" ,output: " +makeItBold(searchFloorOfANumber( numsP03, keyP03) +"")+" ,Expected Output: 1");
        numsP03 = new int[]{1,3,8,10,15};
        keyP03 = 12;
        System.out.print("\nInput Array: " + Arrays.toString(numsP03) + ", key = " + keyP03 +" ,output: " +makeItBold(searchFloorOfANumber( numsP03, keyP03) +"")+" ,Expected Output: 3");
        numsP03 = new int[]{4,6,10};
        keyP03 = 17;
        System.out.print("\nInput Array: " + Arrays.toString(numsP03) + ", key = " + keyP03 +" ,output: " +makeItBold(searchFloorOfANumber( numsP03, keyP03) +"")+" ,Expected Output: 2");
        numsP03 = new int[]{4,6,10};
        keyP03 = -1;
        System.out.print("\nInput Array: " + Arrays.toString(numsP03) + ", key = " + keyP03 +" ,output: " +makeItBold(searchFloorOfANumber( numsP03, keyP03) +"")+" ,Expected Output: -1");
        }
}

