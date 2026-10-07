package com.grokingcodeinterview.pattern18ModifiedBinarySearch;

import java.util.Arrays;

import static com.Utility.makeItBold;

class Patter19ModifiedBinarySearchR2 {
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
     *
     *
     *
     *
     *
     *
     *
     */
    public static int countRotationsInArrayWithDuplicate(int [] nums){
    /// I need another review for this.
    return 0;
    }
    /*
Problem Statement:
Given an array of numbers which is sorted in ascending order and is rotated â€˜kâ€™ times around a pivot, find â€˜kâ€™.
You can assume that the array does not have any duplicates.
Note: You need to solve the problem in  time complexity O(log n).

Example 1: Input: [10, 15, 1, 3, 8] Output: 2
Explanation: The array has been rotated 2 times.
Input: [4, 5, 7, 9, 10, -1, 2] Output: 5
Explanation: The array has been rotated 5 times.
Example 3: Input: [1, 3, 8, 10] Output: 0
Explanation: The array has not been rotated.
Constraints:
1 <= arr.length <= 5000
-10^4 <= arr[i] <= 10^4
All values of nums are unique.
arr is an ascending array that is possibly rotated.
 */

    /**
     * Input: [10, 15, 1, 3, 8] Output: 2
     * s =0; end = 4 mid =2  compare nums[mid] nums[statr] if nums[start]<= nums[mid] left side is soreted
     * else right side is sorted.
     * s =0, end = 4, mid =2  nums[start 0]10> num[mid 2]1  go left
     *
     */
    public static int countRotations (int[] nums) {

        int start = 0;
        int end = nums.length -1;
        // rotation is smallest element
        while (start <=end){
            int mid = start +(end -start )/2;
           // check if mid is the rotation point by comparing mid with mid -1 and mid +1

            // check if mid is rotation point when mid is greater than start
            if (mid > start && nums[mid-1]> nums[mid]){
                return mid;
            }
            // check if mid is rotation point when mid is less than end
            if (mid< end && nums[mid]> nums[mid+1]){
                return mid +1;
            }
            // now decide which side to go
            if (nums[start]<nums[mid]){ // left side is sorted so go right
                start = mid+1;
            } else{ // right side is sorted so go left
                end = mid-1;
            }
        }
        return 0;


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
     * s=0, e:4 mid =2 num[2] =1  rotation point
     * 1< 3
     * in ascending oder nums[i]< nums[i+1
     * rotation pont  num[i]> num[i+1]
     * s = 0 e = 4 mid =2 nums[2]1 < 3mid[3] yes, go left e = mid -1 , e = 2
     * s = 0, e = 2 mid = 1 , num[1]15 < num[2]1 break rotation = 1
     * find the rotation index = 1
     * binarySearch (0, rotationIndex, ascending) or binarSearch (rotationIndex+1, nums.length -1, ascending)
     */
    public static int searchInRotatedArray(int[] nums, int key){
        // find the rotation index
        int start = 0;
        int n = nums.length;
        int end = n-1;

        while(start<=end) {
            int mid = start + (end - start) / 2;
            // compare start with mid
            if (nums[mid] == key) {
                return mid;
            }
            if (nums[start] <= nums[mid]) { // left side is sorted
                if (nums[start] <= key && key < nums[mid]) { // if yest, search [start, mid -1]
                    end = mid - 1;
                } else {
                    start = mid + 1; // if not search [mid +1, end]
                }
            } else { // right side is soreted
                if (nums[mid] < key && key <= nums[end]) { //search[mid+1, end]
                    start = mid + 1;
                } else {
                    end = mid - 1; // search [start, mid -1]
                }
            }
        }

        return -1;

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
    public static int searchInRotatedArrayWithDuplicate(int [] nums, int key){

        int result = -1;
        int start = 0;
        int end = nums.length -1;

        while (start<= end){
            int mid = start + (end -start)/2;

            if (nums[mid] == key) return mid;
            if (nums[mid] == nums[start] && nums[mid ] == nums[end] ){
                start ++;
                end --;

            }else if (nums[start]<= nums[mid]){ // [start, mid] sorted
                if (nums[start]<= key && key< nums[mid]){ // so search [ start, mid -1]
                    end = mid -1;
                }else{ // so search [mid +1, end]
                    start = mid+1;
                }
            }else { // [mid, end ] is sorted
                if (nums[mid] < key && key <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }

        return result;
    }
    /*
    Given a Bitonic array, find if a given â€˜keyâ€™ is present in it. An array is considered bitonic if it is first monotonically increasing and then monotonically decreasing.
    In other words, a bitonic array starts with a sequence of increasing elements, reaches a peak element, and then follows with a sequence of decreasing elements. The peak element is the maximum value in the array.

    Write a function to return the index of the â€˜keyâ€™. If the 'key' appears more than once, return the smaller index. If the â€˜keyâ€™ is not present, return -1.

    Example 1:     Input: [1, 3, 8, 4, 3], key=4     Output: 3
    Example 2:     Input: [3, 8, 3, 1], key=8     Output: 1
    Example 3:     Input: [1, 3, 8, 12], key=12     Output: 3
    Example 4:     Input: [10, 9, 8], key=10     Output: 0
   */

    /**
     * Input: [1, 3, 8, 4, 3], key=4     Output: 3 n = 4
     * peekIndex = 2
     * binarySearch (num, 0, 2, true) or binarySearch(nums, 3, 4 , false)
     *  binarySearch (num, 0, 2, true)
     *  start = 0, end = 2, mid =1, nums[1]3< 4 start = mid +1;
     *  start = 1 end = 2, mid =1, nums[1] = 3 < 4 start = mid +1
     *  start- 2 end =2 mid =2, nums[2] 8 >4 end = mid -1 end = 1
     *  start =2 end =1
     *  binarySearch(num 3, 4, 4, false)
     *  start = 3, end = 4 mid = 3 nums[3] = 4  found the key start = mid-1;
     *
     *  start = 4
     */

    public static int searchInBitonicArray(int[] nums, int key){
        // find the peakIndex
        int start = 0;
        int end = nums.length -1;

        while (start< end){
            int mid = start + (end -start)/2;
            if (nums[mid]<= nums[mid+1]){
                start = mid+1;
            }else
                end = mid;
        }

        int maxIndex = start;
        int result = -1;
        result = binarySearchInBitonicArray(nums, 0, maxIndex, key, true);
        return result != -1? result: binarySearchInBitonicArray(nums, maxIndex +1, nums.length -1,key,  false);

    }

    public static int binarySearchInBitonicArray(int[] nums, int start, int end, int key, boolean isAscending){

        int result = -1;

        while (start<= end){
            int mid = start + (end - start)/2;

            if (nums[mid] == key){
                result = mid;
                end = mid -1;

            }else if (nums[mid]< key){
                if (isAscending){
                    start = mid +1;
                }else{
                    end = mid+1;
                }
            }else{
                if (isAscending){
                    end = mid -1;
                }else{
                    start = mid-1;
                }
            }
        }

        return result;
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
     * [4, 6, 10], key = 7 Output: 6
     * s = 0, e =2 mid =1 mid[1] = 6 < key = > s = 2
     * s = 2, e = 2 mid = 2 mid[2] = 10> key => end =  1 break the loop
     * s = 2, e = 1,
     *
     */
    public static int searchMinDiffElement(int[] nums, int key){

        int start =0;
        int end = nums.length -1;

        while (start<=end){
            int mid = start+(end -start)/2;

            if (nums[mid] == key){
                return nums[mid];
            } else if(nums[mid]< key){
                start = mid +1;
            }else{
                end = mid -1;
            }
        }


        if (start>= nums.length){
            return nums[nums.length -1];
        }
        if(end<0){
            return nums[0];
        }

        if (Math.abs(key - nums[start])<= Math.abs(key - nums[end]) ){
            return nums[start];
        }
        return nums[end];


    }
/*
Given an array of numbers sorted in ascending order,
 find the floor of a given number â€˜keyâ€™.
 The floor of the â€˜keyâ€™ will be the biggest element in the given array smaller
 than or equal to the â€˜keyâ€™

Write a function to return the index of the floor of the â€˜keyâ€™. If there isnâ€™t a floor, return -1.

Example 1:

Input: [4, 6, 10], key = 6
Output: 1
Explanation: The biggest number smaller than or equal to '6' is '6' having index '1'.
Example 2:

Input: [1, 3, 8, 10, 15], key = 12
Output: 3
Explanation: The biggest number smaller than or equal to '12' is '10' having index '3'.
Example 3:

Input: [4, 6, 10], key = 17
Output: 2
Explanation: The biggest number smaller than or equal to '17' is '10' having index '2'.
Example 4:

Input: [4, 6, 10], key = -1
Output: -1
Explanation: There is no number smaller than or equal to '-1' in the given array.
 */
    /**
     * floor is biggestNumber<=key
     *
     Input: [1, 3, 8, 10, 15], key = 12     Output: 3
     s = 0, e = 4, mid = 2 8 <12 , s = 3,
     s = 3, e = 4, mid = 3, 10<12 , s= 4
     s = 4, e = 4, mid = 4, 15> 12 e = 3
     out of the loop return s ;

     Input: [4, 6, 10], key = -1     Output: -1
     s = 0; e = 2; mid = 1 6> -1, e = 0
     s = 9, e = 0 mid = 0 4>-1, e = -1
     s = 0, e = -1,

     Input: [4, 6, 10], key = 17      Output: 2
     s =0 e = 2, mid = 1, 6< 17
     s = 1 e= 2, mid = 1 6< 17
     s = 2, e =2 mid = 2, 10< 17
     s = 3, e = 2 out of the loop
     */
    public static int searchFloorOfANumber(int[] nums, int key){

        int start = 0;
        int end = nums.length-1;
        if (nums[0]> key) return -1;

        while (start<= end){
            int mid = start + (end -start)/2;
            if (nums[mid] == key){
                return mid;
            }else if (nums[mid]< key){
                start = mid+1;

            }else {
                end = mid - 1;
            }

        }
            return end;

    }



    /*
 * Given a sorted array of numbers,
 *  find if a given number â€˜keyâ€™ is present in the array.
 *  Though we know that the array is sorted,
 *  we donâ€™t know if itâ€™s sorted in ascending or descending order.
 *  You should assume that the array can have duplicates.
 *
 * Write a function to return the index of the â€˜keyâ€™
 * if it is present in the array, otherwise return -1.
 *
 * Example 1:
 *
 * Input: [4, 6, 10], key = 10
 * Output: 2
 * Example 2:
 *
 * Input: [1, 2, 3, 4, 5, 6, 7], key = 5
 * Output: 4
 * Example 3:
 *
 * Input: [10, 6, 4], key = 10
 * Output: 0
 * Example 4:
 *
 * Input: [10, 6, 4], key = 4
 * Output: 2
 * Constraints:
 *
 * 1 <= nums.length <= 104
 * -104 < nums[i], target < 104
 */

     static int search(int[] nums, int key){

         int n = nums.length;
         int start = 0;
         int end = n-1;

         boolean ascending = (nums[0]<= nums[end])? true:false;

         while (start<=end){
             int mid = start + (end -start)/2;
             if (nums[mid] == key){
                 return mid;

             }else if (nums[mid]< key){

                 if (ascending){
                     start  = mid+1;
                 }else{
                     end = mid -1;
                 }
             }else{
                 if (ascending){
                     end = mid -1;
                 }else{
                     start = mid+1;
                 }
             }


         }

         return -1;

    }

    /**
     *
     * @param letters
     * @param key
     * @return
     */
    public static char nextGreatestLetter(char[] letters, char key) {
        int n = letters.length;
        if (letters[n-1] <key) return letters[0];

        int left = 0;
        int right = n-1;


        while(left<=right){
            int mid = left + (right - left)/2;

            if (letters[left]<= letters[mid]) {

                left = mid + 1;
            }else{

                right = mid -1;

            }
        }
        return letters[left%n];
    }
        /*
Given an array of numbers sorted in an ascending order,
 find the ceiling of a given number â€˜keyâ€™.
 The ceiling of the â€˜keyâ€™ will be the smallest element in the given array greater than or equal to the â€˜keyâ€™.

Write a function to return the index of the ceiling of the â€˜keyâ€™.
 If there isnâ€™t any ceiling return -1.

Example 1:

Input: [4, 6, 10], key = 6
Output: 1
Explanation: The smallest number greater than or equal to '6' is '6' having index '1'.
Example 2:

Input: [1, 3, 8, 10, 15], key = 12
Output: 4
Explanation: The smallest number greater than or equal to '12' is '15' having index '4'.
Example 3:

Input: [4, 6, 10], key = 17
Output: -1
Explanation: There is no number greater than or equal to '17' in the given array.
Example 4:

Input: [4, 6, 10], key = -1
Output: 0
Explanation: The smallest number greater than or equal to '-1' is '4' having index '0'.
Constraints:

1 <= arr.length <= 104
-104 < arr[i], key < 104
Given array contains distinct values sorted in ascending order.
 */

    /**
     * smallestNumber>= key
     * return index such smallestNumber
     * ascending order
     * Input: [1, 3, 8, 10, 15], key = 12  Output: 4 arr[4] =15
     * s =0 e = 4 , mid = 2 8<12 go right
     * s = 3, e = 4, mid = 3 , 10<12
     * s= 4, e=4, mid = 4, 15>12 smallestNumberIndex = 4
     * s = 3, e = 3 out of loop
     *
     * @param arr
     * @param key
     * @return
     */
        /*
    Given an array of numbers sorted in an ascending order,
    find the ceiling of a given number â€˜keyâ€™.
    The ceiling of the â€˜keyâ€™ will be the smallest element in the given array greater than or equal to the â€˜keyâ€™.

    Write a function to return the index of the ceiling of the â€˜keyâ€™.
    If there isnâ€™t any ceiling return -1.

    Example 1:

    Input: [4, 6, 10], key = 6
    Output: 1
    Explanation: The smallest number greater than or equal to '6' is '6' having index '1'.
    Example 2:

    Input: [1, 3, 8, 10, 15], key = 12
    Output: 4
    Explanation: The smallest number greater than or equal to '12' is '15' having index '4'.
    Example 3:

    Input: [4, 6, 10], key = 17
    Output: -1
    Explanation: There is no number greater than or equal to '17' in the given array.
    Example 4:

    Input: [4, 6, 10], key = -1
    Output: 0
    Explanation: The smallest number greater than or equal to '-1' is '4' having index '0'.
    Constraints:

            1 <= arr.length <= 104
            -104 < arr[i], key < 104
    Given array contains distinct values sorted in ascending order.
 */

    /**
     * find celingnumber
     * smallestNumber>= key
     * Input: [1, 3, 8, 10, 15], key = 12    *     Output: 4
     * s =0; end = 4 mid = 2 , 8<12 start = 3
     * s = 3, e = 4, mid = 3 10< 12 start = 4
     * start = 4 , end = 4, mid = 4  15> 12; end = 3
     * return start;
     */

    public static int searchCeilingOfANumber(int[] nums, int  key){
        if ( key> nums[nums.length-1]) return -1;
        if (key< nums[0] ) return 0;

        int start = 0;
        int end = nums.length -1;

        while (start<= end){
            int mid = start + (end - start)/2;

            if (nums[mid] == key){
                return mid;
            }else if (nums[mid]< key){
                start = mid+1;
            }else{
                end = mid -1;
            }
        }
        return start;

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
     * Input: [4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30], key = 16     *   Output: 6
     * l = 0, e = 1 get(1)< key then double size the box and start from end , because it is ascending order l  =1, e = l+ (e-l +1) *2
     * l  =1 , e = 4 get(4) < key the n double , l = 4, e = 1 + (4-1+1) *2 = 9
     * l =  = 4, e = 9, get(9)> 22 then do regular binary search
     *
     */
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
    public static int search(ArrayReader reader, int key) {

        int start = 0;
        int end =0;

        while (reader.get(end)<= key){
            if (reader.get(end) ==key)
                return end;
            int newStart = end;
            end = start + (end -start +1) *2;
            start = newStart;
        }

        // do classic seracdh
        while (start<= end) {
            int mid = start + (end - start) / 2;

            if (reader.get(mid) < key) {
                start = mid + 1;

            } else if (reader.get(mid) > key){
                end = mid - 1;
            }else{
                return mid;
            }
        }

        return -1;

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
     * Input: [4, 5, 6, 6, 6, 6, 6, 7  9], key = 6      * Output: [1, 3]
     * found lower bound
     * s =0, e =  8 mid = 4, 6 = 6 index = 4
     * s = 0, e =3 mid = 1, 5< 6 then go right
     *  s= 2, e = 3 mid = 2, 6== 6 index = 3
     *  s = 3 , e = 3 , mid =3
     *  s = 3 = e = 3 mid =  index = 3
     *
     *
     */
    public static int[] findRange(int[] nums, int key){

        if (nums.length ==0 ) return new int[]{-1, -1};
        if (nums.length == 1 && nums[0] == key) return new  int[]{0, 0};
        int [] result = new int[]{-1, -1};
        result[0] = binarySearch(nums, key, true);
        if (result[0] != -1){
            result[1] = binarySearch(nums, key, false);
        }

        return result;




    }

    public static int binarySearch(int[] nums, int key, boolean lowerLimit){

        int start = 0;
        int end = nums.length -1;
        int index= -1;

        while (start <= end){
            int mid = start + (end - start)/2;
            if (nums[mid] == key){
                index = mid;
                if (lowerLimit){
                    end = mid -1;
                }else{
                    start = mid +1;
                }

            }else if (nums[mid]< key){
                start = mid+1;

            }else{
                end  = mid  -1;
            }
        }

        return index;
    }

       /*
Given an array of lowercase letters sorted in ascending order,
 find the smallest letter in the given array greater than a given â€˜keyâ€™.

Assume the given array is a circular list,
 which means that the last letter is assumed to be connected with the first letter.
  This also means that the smallest letter in the given array is
   greater than the last letter of the array and is also the first letter of the array.

Write a function to return the next letter of the given â€˜keyâ€™.

Example 1:

Input: ['a', 'c', 'f', 'h'], key = 'f'
Output: 'h'
Explanation: The smallest letter greater than 'f' is 'h' in the given array.

Example 2:
Input: ['a', 'c', 'f', 'h'], key = 'b'
Output: 'c'
Explanation: The smallest letter greater than 'b' is 'c'.

Example 3:
Input: ['a', 'c', 'f', 'h'], key = 'm'
Output: 'a'
Explanation: As the array is assumed to be circular, the smallest letter greater than 'm' is 'a'.

Example 4:
Input: ['a', 'c', 'f', 'h'], key = 'h'
Output: 'a'
Explanation: As the array is assumed to be circular, the smallest letter greater than 'h' is 'a'.

Constraints:

2 <= letters.length <= 104
letters[i] is a lowercase English letter.
letters is sorted in non-decreasing order.
letters contains at least two different characters.
key is a lowercase English letter.
 *
 */

    public static void main(String[] args) {
        // Test cases
        char[] letters1 = {'a', 'c', 'f', 'h'};
        System.out.println(Arrays.toString(letters1));
        System.out.print("Next letter after 'f' is: " + nextGreatestLetter(letters1, 'f') + ", expected: h\n");
        System.out.print("Next letter after 'b' is: " + nextGreatestLetter(letters1, 'b') + ", expected: c\n");
        System.out.print("Next letter after 'm' is: " + nextGreatestLetter(letters1, 'm') + ", expected: a\n");
        System.out.print("Next letter after 'h' is: " + nextGreatestLetter(letters1, 'h') + ", expected: a\n");

        System.out.println("P02:CeilingOfNumber");
        int[] arr3 = new int[]{4,6,10};
        System.out.print(Arrays.toString(arr3));
        System.out.print(" ,Ceiling Of 6: ");
        System.out.print(searchCeilingOfANumber(arr3, 6)); //1
        // print the expected output
        System.out.println (", Expected Output: 1");
        int[] arr4 =new int[] {1,3,8,10,15};
        System.out.print(Arrays.toString(arr4));
        System.out.print(" ,Ceiling Of 12: ");
        System.out.print(searchCeilingOfANumber(arr4, 12)); //4
        System.out.println (", Expected Output: 4");
        int[] arr5 = {4,6,10};
        System.out.print(Arrays.toString(arr5));
        System.out.print(" ,Ceiling Of 17: ");
        System.out.print(searchCeilingOfANumber(arr5, 17)); //-1
        System.out.println (", Expected Output: -1");
        int[] arr6 = {4,6,10};
        System.out.print(Arrays.toString(arr6));
        System.out.print(" ,Ceiling Of -1: ");
        System.out.print(searchCeilingOfANumber(arr6, -1)); //0
        System.out.println (", Expected Output: 0");


        System.out.println("P06. SearchInASortedInfiniteArray");


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

        System.out.println("\nP05.NumberRange...");

        int[] arr = new int[] { 4, 6, 6, 6, 9 };
        int[] result = findRange(new int[] { 4, 6, 6, 6, 9 }, 6);
        System.out.print(Arrays.toString(arr) + " key: 6" );
        System.out.println(" , Range: [" + result[0] + ", " + result[1] + "]");
        arr = new int[] { 1, 3, 8, 10, 15 };
        result = findRange(arr, 10);
        System.out.print(Arrays.toString(arr) + " key: 10" );
        System.out.println(" ,Range: [" + result[0] + ", " + result[1] + "]");
        arr = new int[] { 1, 3, 8, 10, 15 };
        result = findRange(arr, 12);
        System.out.print(Arrays.toString(arr) + " key: 12" );
        System.out.println(" ,Range: [" + result[0] + ", " + result[1] + "]");

        arr = new int[] { 4, 6, 6, 6,6, 6, 6, 9 };
        result = findRange(arr, 6);
        System.out.print(Arrays.toString(arr) + " key: 6" );
        System.out.println(" ,Range: [" + result[0] + ", " + result[1] + "]");

        System.out.println("P03. Next Letter....");
        System.out.println("NextLetter");
        // Test cases

        // some test cases
        System.out.println("P01. Order Agnostic Binary Search..");
        int[] arr1 = new int[]{4, 6, 10};
        key1 = 10;
        System.out.println("Input: [4, 6, 10], key = 10");
        System.out.println("Output: " + search(arr1, key1));
        System.out.println("Expected Output: 2");
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7};
         key2 = 5;
        System.out.println("Input: [1, 2, 3, 4, 5, 6, 7], key = 5");
        System.out.println("Output: " + search(arr2, key2));
        System.out.println("Expected Output: 4");
       arr3 = new int[]{10, 6, 4};
        key3 = 10;
        System.out.println("Input: [10, 6, 4], key = 10");
        System.out.println("Output: " + search(arr3, key3));
        System.out.println("Expected Output: 0");
         arr4 = new int[]{10, 6, 4};
        int key4 = 4;
        System.out.println("Input: [10, 6, 4], key = 4");
        System.out.println("Output: " + search(arr4, key4));
        System.out.println("Expected Output: 2");

        System.out.println("P03: Floor of Number:");
        arr1 = new int[] {4,6,10};
        System.out.print(Arrays.toString(arr1)  +", key =6, FloorIndex is: ");
        System.out.println(searchFloorOfANumber(arr1, 6)); //1
        arr2 = new int[]{1,3,8,10,15};
        System.out.print(Arrays.toString(arr2)  +", key =12, FloorIndex is: ");
        System.out.println(searchFloorOfANumber(arr2, 12)); //3
        arr3 =  new int[]{4,6,10};
        System.out.print(Arrays.toString(arr3)  +", key =17, FloorIndex is: ");
        System.out.println(searchFloorOfANumber(arr3, 17)); //2
        arr4 = new int[] {4,6,10};
        System.out.print(Arrays.toString(arr4)  +", key =-1, FloorIndex is: ");
        System.out.println(searchFloorOfANumber(arr4, -1)); //-1

        System.out.println("===========================");
        System.out.println("P01.Celling of a Number.");
        System.out.println("===========================");


         arr3 = new int[] {4,6,10};
        System.out.print(Arrays.toString(arr3));
        System.out.print(" ,Ceiling Of 6: ");
        System.out.print(searchCeilingOfANumber(arr3, 6)); //1
        // print the expected output
        System.out.println (", Expected Output: 1");
         arr4 = new int[] {1,3,8,10,15};
        System.out.print(Arrays.toString(arr4));
        System.out.print(" ,Ceiling Of 12: ");
        System.out.print(searchCeilingOfANumber(arr4, 12)); //4
        System.out.println (", Expected Output: 4");
         arr5 = new int[] {4,6,10};
        System.out.print(Arrays.toString(arr5));
        System.out.print(" ,Ceiling Of 17: ");
        System.out.print(searchCeilingOfANumber(arr5, 17)); //-1
        System.out.println (", Expected Output: -1");
        arr6 = new int[]{4,6,10};
        System.out.print(Arrays.toString(arr6));
        System.out.print(" ,Ceiling Of -1: ");
        System.out.print(searchCeilingOfANumber(arr6, -1)); //0
        System.out.println (", Expected Output: 0");


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

        System.out.println("==============================================================");
        System.out.println(makeItBold("P09. Search Bitonic Array"));
        System.out.println("==============================================================");

        int[] arrP09 = {1, 3, 8, 4, 3};
        System.out.println("Input: " + makeItBold(Arrays.toString(arrP09)) + ", key=4, Output: " + makeItBold(searchInBitonicArray(arrP09, 4) +"")+ ", Expected Output: 3"); //



        arrP09 = new int[] {3, 8, 3, 1};
        System.out.println("Input: " + makeItBold(Arrays.toString(arrP09)) + ", key=8, Output: " + makeItBold(searchInBitonicArray(arrP09, 8) +"")+ ", Expected Output: 1"); //

        arrP09 = new int[]{1, 3, 8, 12};
        System.out.println("Input: " + makeItBold(Arrays.toString(arrP09)) + ", key=12, Output: " + makeItBold(searchInBitonicArray(arrP09, 12) +"")+ ", Expected Output: 3"); //

        arrP09 =  new int[]{10, 9, 8};

        System.out.println("Input: " + makeItBold(Arrays.toString(arrP09)) + ", key=10, Output: " + makeItBold(searchInBitonicArray(arrP09, 10) +"")+ ", Expected Output: 0"); //

        System.out.println("==============================================");
        System.out.println("P10. Search In Rotated Array");
        System.out.println("==============================================");
        int[] numsP10 = {10, 15, 1, 3, 8};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=15, Output: " + makeItBold(searchInRotatedArray(numsP10, 15) +"")+ ", Expected Output: 1"); //
        numsP10 = new int[] {4, 5, 7, 9, 10, -1, 2};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=10, Output: " + makeItBold(searchInRotatedArray(numsP10, 10) +"")+ ", Expected Output: 4"); //
        numsP10 = new int[]{1, 3, 8, 12};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=12, Output: " + makeItBold(searchInRotatedArray(numsP10, 12) +"")+ ", Expected Output: 3"); //
        numsP10 =  new int[]{10, 9, 8};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=10, Output: " + makeItBold(searchInRotatedArray(numsP10, 10) +"")+ ", Expected Output: 0"); //
        numsP10 =  new int[]{10, 9, 8};
        System.out.println("Input: " + Arrays.toString(numsP10) + ", key=7, Output: " + makeItBold(searchInRotatedArray(numsP10, 7) +"")+ ", Expected Output: -1"); //

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
        System.out.println("P12. Rotation Count");
        System.out.println("==============================================");
        int[] numsP12 = {10, 15, 1, 3, 8};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( countRotations(numsP12) +"") + " Expected: 2");
        numsP12 = new int[]{4, 5, 7, 9, 10, -1, 2};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( countRotations(numsP12) +"") + " Expected: 5");
        numsP12 = new int[]{1, 3, 8, 10};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( countRotations(numsP12) +"") + " Expected: 0");
        numsP12 = new int[]{3,4,5,1,2};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( countRotations(numsP12) +"") + " Expected: 3");
        numsP12 = new int[]{2,1};
        System.out.println("Input: " + Arrays.toString(numsP12) +"output: " +makeItBold( countRotations(numsP12) +"") + " Expected: 1");

        System.out.println("==============================================");
        System.out.println("P13.Rotation Count With Duplicate");
        System.out.println("==============================================");

        int[] numsP13 = {4, 5, 6, 7, 0, 1, 2};
        int resultP13 = countRotationsInArrayWithDuplicate(numsP13);
        System.out.println("Input: " + Arrays.toString(numsP13) + ",output: " + makeItBold(resultP13+"") + " ,Expected: 4");
        // give example with duplicate elements
        int[] numsP13b = {4, 5, 6, 7, 0, 1, 2, 2};
        int resultP13b = countRotationsInArrayWithDuplicate(numsP13b);
        System.out.println("Input: " + Arrays.toString(numsP13b) + ",output: " + makeItBold(resultP13b+"") + " ,Expected: 4");
        int[] numsP13c = {10, 15, 1, 3, 8, 8};
        int resultP13c = countRotationsInArrayWithDuplicate(numsP13c);
        System.out.println("Input: " + Arrays.toString(numsP13c) + ",output: " + makeItBold(resultP13c+"") + " ,Expected: 2");
        int[] numsP13d = {1, 1, 1, 1, 1};
        int resultP13d = countRotationsInArrayWithDuplicate(numsP13d);
        System.out.println("Input: " + Arrays.toString(numsP13d) + ",output: " + makeItBold(resultP13d+"") + " ,Expected: 0");









    }
}
