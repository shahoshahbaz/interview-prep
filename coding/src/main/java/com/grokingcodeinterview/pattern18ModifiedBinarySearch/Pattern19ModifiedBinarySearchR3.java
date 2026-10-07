package com.grokingcodeinterview.pattern18ModifiedBinarySearch;

import java.util.Arrays;

import static com.Utility.makeItBold;

class Pattern19ModifiedBinarySearchR3 {

/*
Problem Statement:
Given an array of numbers which is sorted in ascending order and is rotated â€˜kâ€™ times around a pivot and duplicates, find â€˜kâ€™.
the array  contain duplicates.
Note: You need to solve the problem in  time complexity.

Example 1: Input: [10, 15, 1, 1, 3, 8] Output: 2
Explanation: The array has been rotated 2 times.
Input: [4, 5, 7, 9, 10, -1, 2,2] Output: 5
Explanation: The array has been rotated 5 times.
Example 3: Input: [1, 1, 3, 8, 10] Output: 0
Explanation: The array has not been rotated.


Constraints:

1 <= arr.length <= 5000
-104 <= arr[i] <= 104

arr is an ascending array that is possibly rotated.
*/


    /**
     *  Input:[10, 15, 1, 1, 3, 8] Output: 2
     *  Index  0   1   2  3  4  5
     *
     *  (0, 5) -> mid = 2 nums[mid]< nums[end] 1<8 yes, end = mid
     *  (0, 2) -> mid = 1 nums[mid]< nums[end] 10< 1 no , nums[mid]> nums[end] 10>1 yes strt = mid +1;
     *  (2, 2) return 2;
     */

  public static int findNumberRotationsInDuplicatedSortedArray(int [] nums){

      int start = 0;
      int end = nums.length -1;

      while (start < end){
          int mid = start + (end - start)/2;

          if (nums[mid]< nums[end]){
              end = mid;
          }else if (nums[mid]> nums[end]){
              start = mid+1;
          }else{
              end --;
          }
      }
      return start;
  }
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

    /**  Input = [3, 7, 3, 3, 3] Key = 7 Output: 1
     *   Index =  0  1  2  3  4
     *   (0, 4) -> mid =2  nums[mid] == nums[key] && nums[mid] == nums[end] start++ , end --;
     *   (1, 3) -> mid = 2  nums[start] < nums[mid] 7< 3 no  else  nums[mid]<key<= nums[end] no end  =mid-1
     *   (1, 2) -> mid = 1 return mid;
     *   */


    public static int searchInRotatedArrayWithDuplicate(int[] nums, int key){

        int start =0;
        int end = nums.length -1;

        while(start<= end){
            int mid = start + (end - start)/2;

            if (nums[mid] == key) return mid;
            else if (nums[mid] == nums[start] && nums[mid] == nums[end]){
                start ++;
                end --;
            } else if (nums[start]< nums[mid]){
                if (nums[start]<= key && key < nums[mid])
                    end = mid -1;
                else
                    start = mid +1;
            }else{
                if (nums[mid]< key && key<= nums[end])
                    start = mid +1;
                else
                    end = mid -1;
            }
        }
        return -1;

    }
    /*
Problem Statement:
Given an array of numbers which is sorted in ascending order and is rotated â€˜kâ€™ times around a pivot and duplicates, find â€˜kâ€™.
the array  contain duplicates.
Note: You need to solve the problem in  o(logn) time complexity.

Example 1: Input: [10, 15, 1, 1, 3, 8] Output: 2
Explanation: The array has been rotated 2 times.
Input: [4, 5, 7, 9, 10, -1, 2,2] Output: 5
Explanation: The array has been rotated 5 times.
Example 3: Input: [1, 1, 3, 8, 10] Output: 0
Explanation: The array has not been rotated.


Constraints:

1 <= arr.length <= 5000
-104 <= arr[i] <= 104

arr is an ascending array that is possibly rotated.
*/

    /**
     * Input: [4, 5, 7, 9, 10, -1, 2, 2] Output: 5
     * Index : 0  1  2  3  4    5  6  7
     *
     * (
     *
     *
     *
     *
     *
     */

    public static int findNumberRotationsInSortedArray(int[] nums){

        int start =0;
        int end = nums.length -1;
        if (nums[start]< nums[end]) return 0;

        while ( start< end){
            int mid = start + (end -start)/2;

            if (nums[mid]< nums[end])
                end = mid;
            else if (nums[mid]> nums[end]){
                start = mid+1;
            }
        }
        return start;
        }

    /*
problem statement:
Given an array of numbers which is sorted in ascending order and also rotated by some arbitrary number,
find if a given â€˜keyâ€™ is present in it.
Write a function to return the index of the â€˜keyâ€™ in the rotated array.
If the â€˜keyâ€™ is not present, return -1. You can assume that the given array does not have any duplicates.
Note: You need to solve the problem in o(logn) time complexity.

Example 1: Input: [10, 15, 1, 3, 8], key = 15 Output: 1
Explanation: '15' is present in the array at index '1'.
Example 2: Input: [4, 5, 7, 9, 10, -1, 2], key = 10 Output: 4
Explanation: '10' is present in the array at index '4'.
Constraints:
1 <= arr.length <= 5000
-104 <= arr[i] <= 104
All values of nums are unique.
arr is an ascending array that is possibly rotated.
-104 <= key <= 104
 */
    /**
     * Input: [10, 15, 1, 3, 8], key = 15 Output: 1
     * Index   0    1  2  3  4
     * (0, 4) mid = 2, nums[2] =1  compare it with start and end index
     * num[start]10< num[mid]1 no  so go to right num[mid]< key<= nums[end] 1< 15< 18 no
     * end = mid -1
     * (0, 1) mid = 1 nums[1] ==
     *
     */

    /**
     * Input: [10, 15, 1, 3, 8], key = 15 Output: 1
     * Index: 0  1  2 3 4
     * value 10 15  1 3 8
     * (0, 4) -> mid = 2,  num[mid] = 1 nums[start] < nums[mid]  start = mid+1; 10< 1 no
     * num[mid]< num[end]  1 8< end = mid-1;
     * (0, 1) -> mid = 0; nums[start]<= nums[mid] 10< 10 no end =
     *
     */
    public static int searchInRotatedArray(int[] nums, int key){

        int start =0;
        int end = nums.length -1;

        while(start<=end){

            int mid = start + (end -start)/2;

            if (nums[mid] == key) return mid;
            else if(nums[start]<nums[mid]){
                if (nums[start]<=key &&  key< nums[mid] ){
                    end = mid -1;
                }else{
                    start = mid +1;
                }
            }else {
                if (nums[mid]< key && key<= nums[end]){
                    start = mid+1;
                }else{
                    end = mid -1;
                }
            }
        }
        return -1;
    }

    /*
problem statement:
Given a Bitonic array, find index of a given â€˜keyâ€™ is present in it.
 An array is considered bitonic if it is first monotonically increasing and then monotonically decreasing.
In other words, a bitonic array starts with a sequence of increasing elements, reaches a peak element,
 and then follows with a sequence of decreasing elements. The peak element is the maximum value in the array.
Write a function to return the index of the â€˜keyâ€™. If the 'key' appears more than once, return the smaller index.
 If the â€˜keyâ€™ is not present, return -1.
Example 1: Input: [1, 3, 8, 4, 3], key=4 Output: 3
Example 2: Input: [3, 8, 3, 1], key=8 Output: 1
Example 3: Input: [1, 3, 8, 12], key=12 Output: 3
Example 4: Input: [10, 9, 8], key=10 Output: 0
 */

    /**
     * Input: [1, 2, 3, 4, 5, 12, 11, 10, 9, 8, 8, 7, 6, 1, 2] key = 8, output:
     * index: [0, 1, 2, 3, 4,  5, 6,  7,  8, 9,10,11, 12,13, 14]
     * // find peakIndex = 7,
     * // check left side binarySearch(0, 6, true) or check right side binarySearch(7, 14, false)
     * how to find peadk Index
     * (0, 14) = mid = 7  compare to mid+1 nums[mid]> nums[mid+1] 10> 9
**/

    public static int searchInBitonicArray(int[] nums, int key){
        // find peak Index
        int start =0;
        int end = nums.length -1;

        while (start<end){
            int mid = start + (end -start)/2;
            if (nums[mid]<nums[mid+1]){
                start = mid+1;
            }else
                end = mid;
        }
        int peakIndex = start;


        int left = binarySearch(nums, 0, peakIndex -1, key, true);
        return (left!= -1)? left: binarySearch (nums, peakIndex,nums.length -1, key , false);
    }

    public static int binarySearch2(int[] nums, int start, int end, int key , boolean isAscending){
        int result = -1;

        while(start<end){

            int mid = start +(end -start)/2;

            if (nums[mid] == key){
                result = mid;
                end = mid -1;
            } else if (isAscending){
                if (nums[mid]< key)
                    start = mid +1;
                else
                    end = mid -1;
            }else{
                if (nums[mid]< key)
                    end = mid-1;
                else
                    start = mid+1;
            }

        }
        return result;
    }
    /*
problem Statement:
 Given a sorted array of numbers, find if a given number â€˜keyâ€™ is present in the array.
 Though we know that the array is sorted, we donâ€™t know if itâ€™s sorted in ascending or descending order. You should assume that the array can have duplicates.
 Write a function to return the index of the â€˜keyâ€™ if it is present in the array, otherwise return -1.
 Example 1: Input: [4, 6, 10], key = 10  Output: 2
 Example 2: Input: [1, 2, 3, 4, 5, 6, 7], key = 5  Output: 4
 Example 3: Input: [10, 6, 4], key = 10 Output: 0
 Example 4: Input: [10, 6, 4], key = 4
 Output: 2
 Constraints:
 1 <= nums.length <= 104
 -104 < nums[i], target < 104
 */

    public static int searchInAgnosticBinary(int[] nums, int key) {
        int start = 0;
        int end = nums.length - 1;
        boolean isAscending = nums[start] <= nums[end];

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (nums[mid] == key) return mid;
            if (nums[mid] < key) {
                if (isAscending) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            } else {
                if (isAscending) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
        }

        return -1;


    }
/*
Problem Statement
Find the maximum value in a given Bitonic array. An array is considered bitonic if it is first monotonically increasing and then monotonically decreasing.
In other words, a bitonic array starts with a sequence of increasing elements, reaches a peak element, and then follows with a sequence of decreasing elements.
The peak element is the maximum value in the array.

Example 1: Input: [1, 3, 8, 12, 4, 2] Output: 12
Explanation: The maximum number in the input bitonic array is '12'.
Example 2: Input: [3, 8, 3, 1] Output: 8
Example 3: Input: [1, 3, 8, 12] Output: 12
Example 4: Input: [10, 9, 8] Output: 10
Constraints:
1 <= arr.length <= 105
-105 <= arr[i] <= 105
 */


    /**
     * Input: [1, 3, 8, 12, 4, 2] Output: 12
     * start:0, end = 5 mid = 2 nums[mid]8<12 start = mid+1
     * start = 3, end = 5 , mid = 4, nums[mid]4< 2 no then end = mid
     * start = 3, end = 4, mid = 3 nums[3]12 <4 no then end = 3
     * start = 3, end = 3,
     *
     */
    public static int findMaxInBitonicArray(int[] nums) {
        int start = 0;
        int end = nums.length - 1;

        while (start < end) {
            int mid = start + (end - start) / 2;

            if (nums[mid] < nums[mid + 1]) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return nums[start];
    }

 /*

Given an array of numbers sorted in ascending order,
 find the element in the array that has the minimum difference with the given â€˜keyâ€™.

Example 1:Input: [4, 6, 10], key = 7 Output: 6
Explanation: The difference between the key '7' and '6' is minimum than any other number in the array

Example 2: Input: [4, 6, 10], key = 4 Output: 4
Example 3: Input: [1, 3, 8, 10, 15], key = 12 Output: 10

Example 4: Input: [4, 6, 10], key = 17 Output: 10
*/

    /**
     * do regular binary serach
     * [4, 6, 10], key = 7 Output: 6
     * s = 0, e = 2, mid = 1 nums[1] = 6 < 7 then go right
     * s= 2 e=2 mid =2, 10> key then e = 1
     * s = 2, e = 1
     * abs(6-7)<  abbs(1- - 7)
     *
     *
     */


    public static int searchMinDiffElement(int[] nums, int key) {

        int n = nums.length;
        int start = 0;
        int end = n - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (nums[mid] == key) {
                return nums[mid];
            } else if (nums[mid] < key) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        if (start >= n) {
            return nums[n - 1];
        }
        if (end < 0) {
            return nums[0];
        }


        if (Math.abs(key - nums[start]) <= Math.abs(key - nums[end])) {
            return nums[start];
        }
        return nums[end];

    }
        /*
Given an array of numbers sorted in ascending order,
find the range of a given number â€˜keyâ€™.
The range of the â€˜keyâ€™ will be the first and last position of the â€˜keyâ€™ in the array.

Write a function to return the range of the â€˜keyâ€™. If the â€˜keyâ€™ is not present return [-1, -1].

Example 1:

Input: [4, 6, 6, 6, 9], key = 6
Output: [1, 3]

Example 2:
Input: [1, 3, 8, 10, 15], key = 10
Output: [3, 3]

Example 3:

Input: [1, 3, 8, 10, 15], key = 12
Output: [-1, -1]
Constraints:

0 <= nums.length <= 105
-109 <= nums[i] <= 109
nums is a non-decreasing array.
-109 <= target <= 109
*/

    /**
     * sorted ascending order
     * The range of the â€˜keyâ€™ will be the first and last position of the â€˜keyâ€™ in the array.
     * Input: [4, 6, 6, 6, 9], key = 6      * Output: [1, 3]
     * s =0, e = 4, mid = 2,  nums[mid ]= key
     */
    public static int[] findRange(int[] nums, int key) {

        int[] result = new int[]{-1, -1};

        result[0] = binarySearch(nums, key, true);
        if (result[0] != -1) {
            result[1] = binarySearch(nums, key, false);
        }

        return result;
    }

    public static int binarySearch(int[] nums, int start, int end, int key, boolean isAscending) {

        int result = -1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == key) {
                result = mid;
                end = mid - 1;
            } else if (isAscending) {
                if (nums[mid] < key) start = mid + 1;
                else end = mid - 1;

            } else {
                if (nums[mid] < key) end = mid - 1;
                else start = mid + 1;
            }
        }
        return result;
    }

    public static int binarySearch(int[] nums, int key, boolean firstStart) {
        int keyIndex = -1;

        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (nums[mid] == key) {
                keyIndex = mid;

                if (firstStart) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else if (nums[mid] < key) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return keyIndex;
    }

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

    /**
     * Floor of key, biggest element<= key
     * Input: [1, 3, 8, 10, 15], key = 12 Output: 3
     * s:0, e: 4, mid: 2 , nums[2] = 8< 12 go right s = mid+1
     * s:3, e: 4, id = 3 , nums[3] = 10< 12 go right s = mid +1
     * s:4, e:4, mid:4, nums[4]=15>12 go left e = mid -1
     * s: 4, e: 3 out of loop return e
     */
    public static int searchFloorOfANumber(int[] nums, int key) {

        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {

            int mid = start + (end - start) / 2;

            if (nums[mid] == key) return mid;
            if (nums[mid] < key) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }

        }

        return end;

    }

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

    /**
     * Input: [4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30], key = 16     * Output: 6
     * s = 0 e =1 reader.get(1)< 16
     * s =1, e = 0+ (2) *2 = 4 reader.get(40 = 12< 16
     * s = 4, e = 1 + (4) *2 = 9   reader.get(9) = 22> 16
     * do the classic binary search and with s= 4, e = 9
     */
    static class ArrayReader {
        int[] arr;

        ArrayReader(int[] arr) {
            this.arr = arr;
        }

        public int get(int index) {
            if (index >= arr.length) return Integer.MAX_VALUE;
            return arr[index];
        }
    }

    public static int searchInInfiniteArray(ArrayReader arrayReader, int key) {

        int start = 0;
        int end = 1;

        while (arrayReader.get(end) <= key) {
            int newStart = end;
            end = start + (end - start + 1) * 2;
            start = newStart;
        }

        while (start <= end) {
            int mid = start + (end - start) / 2;
            int value = arrayReader.get(mid);

            if (value == key) {
                return mid;
            } else if (value < mid) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return -1;

    }
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

    /**
     * find ceiling of key , smallestNumber>= key
     * Input: [1, 3, 8, 10, 15], key = 12 Output: 4
     * s :0, e: 4, mid = 2 nums[2] = 8 < 12 go right s = mid +1
     * s: 3, e: 4, mid = 3 nums[3]= 10 < 12 go right s = mid +1
     * s = 4, e: 4 mid = 4 nums[4] = 15> 12 go left e= mid -1;
     * s =4, e: 3 out of loop
     *
     *
     */


    public static int searchCeilingOfANumber(int[] nums, int key) {
        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {

            int mid = start + (end - start) / 2;

            if (nums[mid] == key) return mid;
            if (nums[mid] < key) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return start;

    }

/*
problem statement:
Given an array of lowercase letters sorted in ascending order,
 find the smallest letter in the given array greater than a given â€˜keyâ€™.
Assume the given array is a circular list,
which means that the last letter is assumed to be connected with the first letter.
This also means that the smallest letter in the given array is
greater than the last letter of the array and is also the first letter of the array.
Write a function to return the next letter of the given â€˜keyâ€™.
Example 1: Input: ['a', 'c', 'f', 'h'], key = 'f' Output: 'h'
Explanation: The smallest letter greater than 'f' is 'h' in the given array.
Example 2: Input: ['a', 'c', 'f', 'h'], key = 'b' Output: 'c'
Explanation: The smallest letter greater than 'b' is 'c'.
Example 3: Input: ['a', 'c', 'f', 'h'], key = 'm' Output: 'a'
Explanation: As the array is assumed to be circular, the smallest letter greater than 'm' is 'a'.
Example 4: Input: ['a', 'c', 'f', 'h'], key = 'h' Output: 'a'
Explanation: As the array is assumed to be circular, the smallest letter greater than 'h' is 'a'.
Constraints:
2 <= letters.length <= 10^4
letters[i] is a lowercase English letter.
letters is sorted in non-decreasing order.
letters contains at least two different characters.
key is a lowercase English letter.
 */

    /**
     * Input: ['a', 'c', 'f', 'h'], key = 'b' Output: 'c'
     * s: 0, e: 3, mid = 1, cha[1]=c > b then go left end = mid -1;
     * s:0, e: 0, mid = 9, char[0]=a < b then go right start = mid+1
     * s:1, e: 0, out of loop return s
     * <p>
     * ['a', 'c', 'f', 'h'], key = 'm' Output: 'a'
     * s:0, e= 3, mid:1, char[1]= c< m then start= mid+1
     * s:2, e:3, mid:2, char[2]=f< m then start = mid +1
     * s:3, e:3, mid:3, char[3] = h < m then start = mid+1
     * s:4, e:3 out of the loop
     * return char[s%length]
     */
    public static char nextGreatestLetter(char[] letters, char key) {

        int n = letters.length;
        int start = 0;
        int end = n - 1;

        while (start <= end) {

            int mid = start + (end - start) / 2;


            if (letters[mid] <= key) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return letters[start % n];

    }

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("P01. Search in Order Agnostic Binary Search");
        System.out.println("==============================================");
        int[] numsP01 = {4, 6, 10};
        int keyP01 = 10;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", key = " + keyP01 + " ,output: " + makeItBold(searchInAgnosticBinary(numsP01, keyP01) + "") + " ,Expected Output: 2");
        numsP01 = new int[]{1, 2, 3, 4, 5, 6, 7};
        keyP01 = 5;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", key = " + keyP01 + " ,output: " + makeItBold(searchInAgnosticBinary(numsP01, keyP01) + "") + " ,Expected Output: 4");
        numsP01 = new int[]{10, 6, 4};
        keyP01 = 10;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", key = " + keyP01 + " ,output: " + makeItBold(searchInAgnosticBinary(numsP01, keyP01) + "") + " ,Expected Output: 0");
        numsP01 = new int[]{10, 6, 4};
        keyP01 = 4;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", key = " + keyP01 + " ,output: " + makeItBold(searchInAgnosticBinary(numsP01, keyP01) + "") + " ,Expected Output: 2");
        System.out.println("==============================================");
        System.out.println("P02. Ceiling Of A Number");
        System.out.println("==============================================");

        int[] numsP02 = {4, 6, 10};
        int keyP02 = 6;
        System.out.println("Input: " + Arrays.toString(numsP02) + ", key = " + keyP02 + " ,output: " + makeItBold(searchCeilingOfANumber(numsP02, keyP02) + "") + " ,Expected Output: 1");
        numsP02 = new int[]{1, 3, 8, 10, 15};
        keyP02 = 12;
        System.out.println("Input: " + Arrays.toString(numsP02) + ", key = " + keyP02 + " ,output: " + makeItBold(searchCeilingOfANumber(numsP02, keyP02) + "") + " ,Expected Output: 4");
        numsP02 = new int[]{4, 6, 10};
        keyP02 = 17;
        System.out.println("Input: " + Arrays.toString(numsP02) + ", key = " + keyP02 + " ,output: " + makeItBold(searchCeilingOfANumber(numsP02, keyP02) + "") + " ,Expected Output: -1");
        numsP02 = new int[]{4, 6, 10};
        keyP02 = -1;
        System.out.println("Input: " + Arrays.toString(numsP02) + ", key = " + keyP02 + " ,output: " + makeItBold(searchCeilingOfANumber(numsP02, keyP02) + "") + " ,Expected Output: 0");
        System.out.println("==============================================");
        System.out.println("P03. Floor Of Number");
        System.out.println("==============================================");
        int numsP03[] = {4, 6, 10};
        int keyP03 = 6;
        System.out.print("Input Array: " + Arrays.toString(numsP03) + ", key = " + keyP03 + " ,output: " + makeItBold(searchFloorOfANumber(numsP03, keyP03) + "") + " ,Expected Output: 1");
        numsP03 = new int[]{1, 3, 8, 10, 15};
        keyP03 = 12;
        System.out.print("\nInput Array: " + Arrays.toString(numsP03) + ", key = " + keyP03 + " ,output: " + makeItBold(searchFloorOfANumber(numsP03, keyP03) + "") + " ,Expected Output: 3");
        numsP03 = new int[]{4, 6, 10};
        keyP03 = 17;
        System.out.print("\nInput Array: " + Arrays.toString(numsP03) + ", key = " + keyP03 + " ,output: " + makeItBold(searchFloorOfANumber(numsP03, keyP03) + "") + " ,Expected Output: 2");
        numsP03 = new int[]{4, 6, 10};
        keyP03 = -1;
        System.out.print("\nInput Array: " + Arrays.toString(numsP03) + ", key = " + keyP03 + " ,output: " + makeItBold(searchFloorOfANumber(numsP03, keyP03) + "") + " ,Expected Output: -1");
        System.out.println("==============================================");
        System.out.println("P04. Next Letter");
        System.out.println("==============================================");
        char[] lettersP04 = {'a', 'c', 'f', 'h'};
        char keyP04 = 'f';
        System.out.println("Input: " + Arrays.toString(lettersP04) + ", key = '" + keyP04 + ",output: " + makeItBold(nextGreatestLetter(lettersP04, keyP04) + "") + " ,Expected Output: h");
        lettersP04 = new char[]{'a', 'c', 'f', 'h'};
        keyP04 = 'b';
        System.out.println("Input: " + Arrays.toString(lettersP04) + ", key = '" + keyP04 + ",output: " + makeItBold(nextGreatestLetter(lettersP04, keyP04) + "") + " ,Expected Output: c");
        lettersP04 = new char[]{'a', 'c', 'f', 'h'};
        keyP04 = 'm';
        System.out.println("Input: " + Arrays.toString(lettersP04) + ", key = '" + keyP04 + ",output: " + makeItBold(nextGreatestLetter(lettersP04, keyP04) + "") + " ,Expected Output: a");
        lettersP04 = new char[]{'a', 'c', 'f', 'h'};
        keyP04 = 'h';
        System.out.println("Input: " + Arrays.toString(lettersP04) + ", key = '" + keyP04 + ",output: " + makeItBold(nextGreatestLetter(lettersP04, keyP04) + "") + " ,Expected Output: a");
        System.out.println("==============================================================");
        System.out.println(makeItBold("P09. Search Bitonic Array"));
        System.out.println("==============================================================");
        int[] numsP09 = {1, 3, 8, 4, 3};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=4, Output: " + makeItBold(searchInBitonicArray(numsP09, 4) + "") + ", Expected Output: 3"); //
        numsP09 = new int[]{3, 8, 3, 1};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=8, Output: " + makeItBold(searchInBitonicArray(numsP09, 8) + "") + ", Expected Output: 1"); //
        numsP09 = new int[]{1, 3, 8, 12};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=12, Output: " + makeItBold(searchInBitonicArray(numsP09, 12) + "") + ", Expected Output: 3"); //
        numsP09 = new int[]{10, 9, 8};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=10, Output: " + makeItBold(searchInBitonicArray(numsP09, 10) + "") + ", Expected Output: 0"); //
        // an example when the ky doesn't exist
        numsP09 = new int[]{1, 3, 8, 4, 3};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=6, Output: " + makeItBold(searchInBitonicArray(numsP09, 6) + "") + ", Expected Output: -1"); //
        // an example when the key is duplicated
        numsP09 = new int[]{1, 3, 8, 8, 3, 1};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=8, Output: " + makeItBold(searchInBitonicArray(numsP09, 8) + "") + ", Expected Output: 2"); //
        numsP09 = new int[]{1, 3, 3, 8, 2, 1};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=3, Output: " + makeItBold(searchInBitonicArray(numsP09, 3) + "") + ", Expected Output: 1"); //

        System.out.println("==============================================");
        System.out.println("P08. Bitonic Array Maximum");
        System.out.println("==============================================");
        // Test cases
        int[] numsP08 = {1, 3, 8, 12, 4, 2};
        System.out.println("Input: " + Arrays.toString(numsP08) + " ,output: " + makeItBold(findMaxInBitonicArray(numsP08) + "") + " ,Expected: 12");
        numsP08 = new int[]{3, 8, 3, 1};
        System.out.println("Input: " + Arrays.toString(numsP08) + " ,output: " + makeItBold(findMaxInBitonicArray(numsP08) + "") + " ,Expected: 8");
        numsP08 = new int[]{1, 3, 8, 12};
        System.out.println("Input: " + Arrays.toString(numsP08) + " ,output: " + makeItBold(findMaxInBitonicArray(numsP08) + "") + " ,Expected: 12");
        numsP08 = new int[]{10, 9, 8};
        System.out.println("Input: " + Arrays.toString(numsP08) + " ,output: " + makeItBold(findMaxInBitonicArray(numsP08) + "") + " ,Expected: 10");

        System.out.println("==============================================");
        System.out.println("P10. Search In Rotated Array");
        System.out.println("==============================================");
        int[] numsP10 = {10, 15, 1, 3, 8};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=15, Output: " + makeItBold(searchInRotatedArray(numsP10, 15) + "") + ", Expected Output: 1"); //
        numsP10 = new int[]{4, 5, 7, 9, 10, -1, 2};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=10, Output: " + makeItBold(searchInRotatedArray(numsP10, 10) + "") + ", Expected Output: 4"); //
        numsP10 = new int[]{1, 3, 8, 12};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=12, Output: " + makeItBold(searchInRotatedArray(numsP10, 12) + "") + ", Expected Output: 3"); //
        numsP10 = new int[]{10, 9, 8};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=10, Output: " + makeItBold(searchInRotatedArray(numsP10, 10) + "") + ", Expected Output: 0"); //
        numsP10 = new int[]{10, 9, 8};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=7, Output: " + makeItBold(searchInRotatedArray(numsP10, 7) + "") + ", Expected Output: -1"); //

        System.out.println("==============================================");
        System.out.println("P12. Rotation Count");
        System.out.println("==============================================");
        int[] numsP12 = {10, 15, 1, 3, 8};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( findNumberRotationsInSortedArray(numsP12) +"") + " Expected: 2");
        numsP12 = new int[]{4, 5, 7, 9, 10, -1, 2};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( findNumberRotationsInSortedArray(numsP12) +"") + " Expected: 5");
        numsP12 = new int[]{1, 3, 8, 10};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( findNumberRotationsInSortedArray(numsP12) +"") + " Expected: 0");
        numsP12 = new int[]{3,4,5,1,2};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( findNumberRotationsInSortedArray(numsP12) +"") + " Expected: 3");
        numsP12 = new int[]{2,1};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( findNumberRotationsInSortedArray(numsP12) +"") + " Expected: 1");
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

        System.out.println("==============================================");
        System.out.println("P13.Rotation Count With Duplicate");
        System.out.println("==============================================");

        int[] numsP13 = {4, 5, 6, 7, 0, 1, 2};
        int resultP13 = findNumberRotationsInDuplicatedSortedArray(numsP13);
        System.out.println("Input: " + Arrays.toString(numsP13) + ",output: " + makeItBold(resultP13+"") + " ,Expected: 4");
        // give example with duplicate elements
        int[] numsP13b = {4, 5, 6, 7, 0, 1, 2, 2};
        int resultP13b = findNumberRotationsInDuplicatedSortedArray(numsP13b);
        System.out.println("Input: " + Arrays.toString(numsP13b) + ",output: " + makeItBold(resultP13b+"") + " ,Expected: 4");
        int[] numsP13c = {10, 15, 1, 3, 8, 8};
        int resultP13c = findNumberRotationsInDuplicatedSortedArray(numsP13c);
        System.out.println("Input: " + Arrays.toString(numsP13c) + ",output: " + makeItBold(resultP13c+"") + " ,Expected: 2");
        int[] numsP13d = {1, 1, 1, 1, 1};
        int resultP13d = findNumberRotationsInDuplicatedSortedArray(numsP13d);
        System.out.println("Input: " + Arrays.toString(numsP13d) + ",output: " + makeItBold(resultP13d+"") + " ,Expected: 0");





    }
}
