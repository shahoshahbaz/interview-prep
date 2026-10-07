package com.grokingcodeinterview.pattern18ModifiedBinarySearch;
/*
Given an infinite sorted array (or an array with unknown size),
 find if a given number â€˜keyâ€™ is present in the array. Write a function to return the index of the â€˜keyâ€™ if it is present in the array, otherwise return -1.

Since it is not possible to define an array with infinite (unknown) size,
 you will be provided with an interface ArrayReader to read elements of the array. ArrayReader.get(index) will return the number at index; if the arrayâ€™s size is smaller than the index, it will return Integer.MAX_VALUE.

Example 1:

Input: [4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30], key = 16
Output: 6
Explanation: The key is present at index '6' in the array.
Example 2:

Input: [4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30], key = 11
Output: -1
Explanation: The key is not present in the array.
Example 3:

Input: [1, 3, 8, 10, 15], key = 15
Output: 4
Explanation: The key is present at index '4' in the array.
Example 4:

Input: [1, 3, 8, 10, 15], key = 200
Output: -1
Explanation: The key is not present in the array.
Constraints:

1 <= reader.length <= 104
-104 <= reader[i], target <= 104
reader is sorted in a strictly increasing order.
 */

import java.util.Arrays;

/**
 * here we don't know the size of array
 */


public class P06SearchInASortedInfiniteArray {

    static class ArrayReader {
        int[] arr;

        ArrayReader(int[] arr) {
            this.arr = arr;
        }

        public int get(int index) {
            if (index >= arr.length)
                return Integer.MAX_VALUE;
            return arr[index];
        }
    }

    // Assume you'll implement this later
    public static int search(ArrayReader reader, int key) {
         // first find the proper bounds why? because we don't know the size of array
        // how we can find the bounds? by exponential backoff approach how? we can start with a box of size 2 and keep doubling it until
        // we find the key is less than the end of the box
        int start = 0;
        int end = 1;
        while(reader.get(end)<key){
           int newStart = end+1; // next box start
            end = end +(end -start +1)*2; // double the box size
            start = newStart;
        }

 // then apply binary search

       while(start<= end) {
           int mid = start+ (end -start)/2;
           if (reader.get(mid) < key) {
               start = mid + 1;
           } else if (reader.get(mid) > key) {
               end = mid - 1;
           } else {
               return mid;
           }
       }
        // TODO: your search logic goes here later
        return -1;
    }

    public static void main(String[] args) {
        int[] nums1 = {4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30};
        ArrayReader reader1 = new ArrayReader(nums1);
        int key1 = 16;
        System.out.print("Input: " + Arrays.toString(nums1) + ", key = " + key1);
        int result1 = search(reader1, key1);
        System.out.print(" ,Key " + key1 + " found at index: " + result1 + ", Expected value: 6\n");// should return 6

        // Example 2
        int[] nums2 = {1, 3, 8, 10, 15};
        ArrayReader reader2 = new ArrayReader(nums2);
        int key2 = 15;
        System.out.print("Input: " + Arrays.toString(nums2) + ", key = " + key2);
        int result2 = search(reader2, key2);
        System.out.print(" ,Key " + key2 + " found at index: " + result2 + ", Expected value: 4\n");// should return 4

        // Example 3
        int key3 = 200;
        System.out.print("Input: " + Arrays.toString(nums2) + ", key = " + key3);
        int result3 = search(reader2, key3);

        System.out.print( " , Key " + key3 + " found at index: " + result3 + ", Expected value: -1\n");// should return -1
    }
}

