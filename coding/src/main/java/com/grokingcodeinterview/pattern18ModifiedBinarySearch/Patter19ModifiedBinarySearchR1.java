package com.grokingcodeinterview.pattern18ModifiedBinarySearch;


import java.util.Arrays;

import static com.Utility.makeItBold;

class Patter19ModifiedBinarySearchR1 {
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
    public static int countRotationsInArrayWithDuplicate(int[] nums){

        int start = 0;
        int end = nums.length -1;

        while (start<= end){
            int mid = start + (end - start)/2;

            if (mid< end && nums[mid]> nums[mid+1]){
                return mid+1;
            }
            if (start>mid && nums[mid -1]> nums[mid]){
                return mid;
            }
            if (nums[mid] == nums[start] && nums[end ] == nums[mid]){
                if (nums[start]> nums[start+ 1]){
                    return start +1;
                }
                start ++;
                if (nums[end -1]> nums[end]){
                    return end;
                }
                end --;
            }
        }
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
     * [10, 15, 1, 3, 8] Output: 2
     * s = 0, e = 4 m = 2 10 >1 //go to left side end = mid
     * s = e = 2
     */

    public static int countRotations(int [] nums){

        int start = 0;
        int end = nums.length -1;

        while (start<= end){
            int mid = start + (end -start)/2;

            if (mid>start && nums[mid -1]> nums[mid]){

                return mid;
            }
            if (mid< end && nums[mid] > nums[mid +1] ){
                return mid+1;
            }

            if (nums[start]< nums[mid]){ // left side is sorted
                start = mid+1;
            }else{
                end = mid -1;
            }
        }

       return 0;
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

    /**
     * Input: nums = [3, 7, 3, 3, 3] Key = 7 Output: 1
     * s = 0, e = 4 mid = 2   nums[0] 3 nums[2]=3 vs nums[2]3 num[4] =3  = > s = 1, e = 3,
     * s =1, e = 3 mid = 2 nums[2]= 3  nums[1] = 7  num[start(1)] =7 nums[ mid(2)] = 3 7>3 // right side is sorted
     * nums[mid(2)]< key(7)<= nums[ end(3)] no
     * end = mid -1; so s = 1, e = 1, mid =1 return 1
     *
     *
     */
    public static int searchInRotatedArrayWithDuplicate(int[] nums, int key){

        int start = 0;
        int end = nums.length -1;

        while (start<= end){
            int mid = start + (end -start)/2;

            if (nums[mid] == key){
                return mid;
            }
            if (nums[start] == nums[ mid] && nums[end] == nums[mid]){
                start++;
                end --;
            }else if (nums[start]<=nums[mid]){ // left side is sorted
                if (nums[start] <= key && key < nums[mid]){
                    end = mid -1;
                }else{
                    start = mid +1;
                }

            }else{ // right side is sorted
                if (nums[mid]<key && key<=nums[end]){
                    start = mid +1;
                }else{
                    end = mid  -1;
                }
            }
        }
    return -1;
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
     * Input: [4, 5, 7, 9, 10, -1, 2], key = 10 Output: 4
     * s =0, e = 6 mid = 3 num[3 ] = 9 < num[mid+1]  start = mid +1 s: 4
     */
    public static int searchInRotatedArray(int[] nums, int key){

        int start = 0;
        int end = nums.length -1;

        while (start<= end){

            int mid = start + (end -start)/2;

            if (nums[mid] == key){
                return mid;
            }else if (nums[start]<= nums[mid]){// left is sorted
                if (nums[start]<= key && key< nums[mid]){
                    end = mid -1;
                }else{
                    start = mid+1;
                }
            }else{ // right is sorted
                if ( nums[mid]< key &&  key<= nums[end]){
                    start = mid+1;
                }else{
                    end = mid -1;
                }
            }
        }
        return -1;
    }
    /*
    Given a Bitonic array, find if a given â€˜keyâ€™ is present in it. An array is considered bitonic if it is first monotonically increasing and then monotonically decreasing.
    In other words, a bitonic array starts with a sequence of increasing elements, reaches a peak element, and then follows with a sequence of decreasing elements. The peak element is the maximum value in the array.

    Write a function to return the index of the â€˜keyâ€™. If the 'key' appears more than once, return the smaller index.
    If the â€˜keyâ€™ is not present, return -1.

    Example 1:     Input: [1, 3, 8, 4, 3], key=4     Output: 3
    Example 2:     Input: [3, 8, 3, 1], key=8     Output: 1
    Example 3:     Input: [1, 3, 8, 12], key=12     Output: 3
    Example 4:     Input: [10, 9, 8], key=10     Output: 0
   */

    /**
     *  Input: [1, 3, 8, 4, 3], key=4     Output: 3
     *  s =0, e = 4, mid = 2 nums[2] 8>4 end = 2
     *  s = 0, e =2 mid =1  nums[1] = 3< 8 s = 2
     *  s =2, e =2 md =2 nums[2] = 8> 4
     *  s = 2 e= 2 mid = 2 nums[2 ] = 8 >4 e = 2
     */

    public static int searchInBitonicArray(int[] nums, int key){
        // find the maxIndex
        int maxIndex = findMaxIndex(nums);
        int resultIndex = -1;
        resultIndex = binarySearch(nums, key, 0, maxIndex, true);
        if(resultIndex == -1) {
            resultIndex = binarySearch(nums, key, maxIndex+1, nums.length,  false);
        }
        return resultIndex;
    }

    public static int binarySearch(int[] nums, int key, int start, int end, boolean ascending){

        int result = -1;

        while (start<= end){

            int mid = start +(end -start)/2
                    ;

            if (nums[mid] == key){
                return mid;
            }else{
                if(ascending){
                    if (nums[mid] < key){
                        start = mid+1;
                    }else{
                        end = mid -1;
                    }
                }else{
                    if (nums[mid] < key) {

                        end = mid -1;
                    }else{
                        start = mid+1;
                    }
                }
            }
        }

        return result;
    }

    public static int findMaxIndex(int[] nums){
        int start =0;
        int n = nums.length;
        int end = n-1;

        while(start<end){
            int mid = start + (end - start)/2;
            if (nums[mid]< nums[mid+1]){
                start = mid+1;
            }else{
                end = mid;
            }


        }
        return start;
    }




    /*
Given an array of numbers sorted in ascending order, find the element in the array that has the minimum difference with the given â€˜keyâ€™.

Example 1:
Input: [4, 6, 10], key = 7
Output: 6
Explanation: The difference between the key '7' and '6' is minimum than any other number in the array

Example 2:
Input: [4, 6, 10], key = 4
Output: 4
Example 3:

Input: [1, 3, 8, 10, 15], key = 12
Output: 10

Example 4:
Input: [4, 6, 10], key = 17
Output: 10
 */



    public static int searchMinDiffElement(int[] nums, int key){

        int n = nums.length;
        int start = 0;
        int end = n -1;

        while (start< end){
            int mid = start + (end - start)/2;

            if ( nums[mid]<key){
                start = mid+1;
            }else if(nums[mid]> key){
                end = mid -1;
            }else{
                return nums[mid];
            }

            if (start>= nums.length){
                return nums[nums.length-1];
            }

            if (end<0){
                return nums[0];
            }

            if (Math.abs(start - key)<= Math.abs(end - key)){
                return nums[start];
            }

            return nums[end];


        }
        if (start>= n ){
            return nums[n-1];
        }
        if (end< 0){
            return nums[0];
        }
        if (Math.abs(start- key) <= Math.abs(end -key)){
            return nums[start];
        }
        return nums[end];




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

    /**   smallestchar> key
     *  ['a', 'c', 'f', 'h'], key = 'f' output 'h'
     *  l = 0, r = 3 mid = 1 c< f , l = 2
     *  l =2, r = 3, mid =2 f<=f, l = 3,r = 3
     *  l =3, r =3, mid = 3,h> f return mid, r
     *  out = r=1
     *
     *  ['a', 'c', 'f', 'h'], key = 'm'
     *  l = 0, r =3, mid =1, c<m,
     *  l = 2, r=3 , mid = 2 f< m,
     *  l = 3, r = 3 mid = 3, h<m , l = 4 return arr[l%4 ]  = a
     *
     * ['a', 'c', 'f', 'h'], key = 'b' Output: 'c'
     * l =0, r = 3, mid =1 c>b
     * l =0, r =2 md =1 c>b
     * l=0, r =1 md = 0, a<b
     * l = 1, r=1 md =1 c>B
     * l = 1 , r= 0 out l =1
     *
     * @param letters
     * @param key
     * @return
     */
    public  static char nextGreatestLetter(char[] letters, char key) {
        //edge case
        int n = letters.length;
//        smallestchar> key


        int left = 0;
        int right = n-1;

        while(left<= right){
            int mid = left + (right -left)/2;

            if (letters[mid]<= key){
                left = mid +1;
            }else{
                right = mid -1;
            }


        }
        return letters[left% n];


    }



    /*
     * Given a sorted array of numbers,
     *  find if a given number â€˜keyâ€™ is present in the array.
     *  Though we know that the array is sorted,
     * we donâ€™t know if itâ€™s sorted in ascending or descending order.
     *  You should assume that the array can have duplicates.
     *
     * Write a function to return the index of the â€˜keyâ€™ if it is present in the array, otherwise return -1.
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
    public static int search(int[] arr, int key) {
        if (arr.length ==1 && arr[0]== key) return 0;
        if (arr.length == 1 && arr[0] != key) return -1;
        if (arr[0]< arr[arr.length -1] && arr[0]> key) return  -1;
        if (arr[0]> arr[arr.length -1] && arr[0]< key) return  -1;


        int start =0;
        int end = arr.length-1;
        boolean isAscending = arr[0]< arr[arr.length -1];

        while(start<= end){

            int mid = start + (end- start)/2;

            if (arr[mid ] == key){
                return mid;
            }else{
                if (isAscending){
                    if (arr[mid]<key){
                        start = mid +1;

                    }else{
                        end = mid -1;
                    }
                }else{
                    if (arr[mid]<key){
                        end = mid -1;
                    }else{
                        start = mid +1;
                    }
                }
            }
        }

        return -1;

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
     * ascending order
     * find smallestElement>=key
     * Input: [4, 6, 10], key = 6
     * s = 0, e = 2, mid = 1 ,
     * Input: [1, 3, 8, 10, 15], key = 12
     * Output: 4
     * s =0; e = 4 mid = 2 , 8< 12
     * s = 3 e = 4, mid = 3, 10<12
     *
     *  [1, 3, 8, 10, 15, 18, 19 , 20], key = 12
     *  s = 0, e = 7 mid = 3, 10<12 s = mid +1
     *  s = 4, e = 7, mid = 5  18>12 , end = 4
     *
     *  [1, 3, 8, 10, 15, 18, 19 , 20, 21], key = 12
     *  s=0, e =8 mid = 4, 15>12
     *  e = 5, s =0, mid = 2, 8<12
     *  s = 3, 5, mid = 4 ,15>12
     *  s = 3, e = 3, mid = 3,10<12
     *  s = 4, e = 3,  so start
     * @param arr
     * @param key
     * @return
     */
    public static int searchCeilingOfANumber(int[] arr, int key) {
        if (arr == null) return -1;

        int n = arr.length;
        if (arr[n-1]< key) return -1;


        int start = 0;
        int end = n-1;
        while(start<= end){
            int mid = start + (end - start)/2;
            if (arr[mid] == key){
                return mid;
            }else if (arr[mid]<key){
                start = mid +1;
            }else {
                end = mid -1;
            }

        }
        return start;

    }
    /*
Given an array of numbers sorted in ascending order,
 find the floor of a given number â€˜keyâ€™.
 The floor of the â€˜keyâ€™ will be the biggest element in the given array smaller than or equal to the â€˜keyâ€™

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
     * biggestNumber<=key
     * Input: [1, 3, 8, 10, 15], key = 12
     * s = 0, e =4, mid = 2, 8<12 go right
     * s =3, e =4 mid = 3, 10 <12 go right\
     * s = 4, e = 4, mid = 4, 15>12 go left
     * s = 4, e = 3, erturn end;
     * edge case
     * if (arr[0]>key) rturn -1;
     * @param arr
     * @param key
     * @return
     */
    public  static int searchFloorOfANumber(int[] arr, int key){
        if (arr == null) return -1;
        if (arr[0]>key) return -1;

        int n = arr.length;
        int start = 0;
        int end = n-1;

        while(start<=end){

            int mid = start + (end - start)/2;

            if (arr[mid] == key){
                return mid;
            }else if (arr[mid]< key){
                start = mid +1;
            }else{
                end = mid -1;
            }

        }
        return end;

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
     * sorting ascending
     * range will be first and last element
     * Input: [4, 6, 6, 6, 9], key = 6     * Output: [1, 3]
     * s = 0, e = 4
     * find first occurenace:
     * s = 0, e = 4, mid = 2, 6==6 Index =2, e = 1
     * s = 0, e = 1, mid = 0, 4< 6 Index = 2,s = 1
     * s=1, e = 1, mid =1, 6==6 index = 1, e = 0
     * out of loop return index
     * find last occurance
     * s = 0, e = 4, mid =2, Index = 2, s = 3
     * s= 3, e= 4, mid 3, Index = 3, s = 4,
     * s = 4, e= 4 mid = 4, Index = 3, e = 3
     * out of loop return index = 3
     *
     * Input: [4, 6, 6, 6, 6, 9], key = 6     * Output: [1, 4]
     * s = 0, e = 5, mid = 2 bs(0, 2) bs(3, 5)
     * bs(0,2) s = 0, e = 2, mid =1, return 1;
     * bs(3, 5) s = 3, e = 5, mid =4 return 4
     *
     */
    public static  int[] findRange(int[] arr, int key) {
        int[] result =new int[]{-1,-1};

        int n = arr.length;

        if (n == 0 ) return result;
        if (n ==1 && arr[n-1] == key) return new int[]{0, 0};
        result[0] = findRange(arr, key, true);
        if (result[0]!= -1){
            result[1] = findRange(arr, key, false);
        }





    return result;
    }
    private static int findRange(int[] arr, int key, boolean firstOccurrence){
        int start = 0, end = arr.length -1;
        int index = -1;


        while (start<= end){

            int mid = start + (end -start)/2;

            if (arr[mid] == key){
                index = mid;
                if (firstOccurrence){
                    end = mid -1;

                }else{
                    start = mid +1;
                }

            }else if (arr[mid]> key){
                end = mid -1;
            }else{
                start = mid +1;
            }
        }
        return index;
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
     * Ascending sorted,
     * Input: [4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30], key = 16  * Output: 6
     * Explanation: The key is present at index '6' in the array.
     * the end is on unknow? so waht we can do
     * start = 0, end =1 , nums[1]< key then newStart = 1, end= 1+2 *2 = 5, start = 1;
     * start = 1, end =5, nums[5]< key then newStrat = 6, end = 5 + 4*2  = 13
     * start = 6, end = 13 > key,
     * now we find start and end now we can do binary search
     * start = 6, end =13 mid = 9 nums[9] =22> key end = 8
     * start =6, end = 8 mid = 7 nums[8] = 20 > key end = 6
     * start 6, end =6 mid = 6 find it
     *
     *
     *
     */
    public static int search(ArrayReader reader, int key) {

        int start = 0;
        int end = 1;

        // find the box
        while (reader.get(end)< key){
            int newStart = end+1;
            end = start + (end -start+1) *2;
            start = newStart;
        }

        //now do classic binary search based on start and end
        while (start<= end){
            int mid = start + (end -start)/2;
             int midValue = reader.get(mid);
            if (midValue == key){
                return mid;
            } else if (midValue > key){
                end = mid -1;

            }else{
                start = mid+1;
            }
        }
        return -1;
    }

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
    /*
Given an array of numbers sorted in ascending order, find the element in the array that has the minimum difference with the given â€˜keyâ€™.

Example 1:
Input: [4, 6, 10], key = 7
Output: 6
Explanation: The difference between the key '7' and '6' is minimum than any other number in the array

Example 2:
Input: [4, 6, 10], key = 4
Output: 4
Example 3:

Input: [1, 3, 8, 10, 15], key = 12
Output: 10

Example 4:
Input: [4, 6, 10], key = 17
Output: 10
 */

    /**
     * Ascending order
     *  find element that has min diff with key
     *  Input: [4, 6, 10], key = 7    * Output: 6
     *
     *  O(log n)
     *  minDif =MAX
     *  minELment
     *  start =0, end = 3, mid = 1 nums[1] =6 < 7 first minDiff =1 map(1, 6)
     *  start = 2, end = 3 mid  =2 nums[2] = 10> 7 miDiff =  ma(3, 7)
     *  start 2 end = 3 mid =1, out off loep mind = 1, 6
     *
     *  Input: [1, 3, 8, 10, 15], key = 12     * Output: 10
     *
     *  start = 0, end = 4 mid = 2, nums[2] = 8 < 12 minDiff = 4, (4, 8)
     *  start = 3, end = 4 mid = 3 nums[3] = 10< 12 midDiff = 2, (2, 10)
     *  start  = 4 end = 4 , mid  = 4 nums[4] =
     *
     *
     */


    public static void main(String[] args) {
        System.out.println("P01:OrderAgnosticBinarySearch....");
        // some test cases
        int[] arr1 = {4, 6, 10};
        int key1 = 10;
        System.out.println("Input: [4, 6, 10], key = 10");
        System.out.println("Output: " + search(arr1, key1));
        System.out.println("Expected Output: 2");
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7};
        int key2 = 5;
        System.out.println("Input: [1, 2, 3, 4, 5, 6, 7], key = 5");
        System.out.println("Output: " + search(arr2, key2));
        System.out.println("Expected Output: 4");
        int[] arr3 = {10, 6, 4};
        int key3 = 10;
        System.out.println("Input: [10, 6, 4], key = 10");
        System.out.println("Output: " + search(arr3, key3));
        System.out.println("Expected Output: 0");
        int[] arr4 = {10, 6, 4};
        int key4 = 4;
        System.out.println("Input: [10, 6, 4], key = 4");
        System.out.println("Output: " + search(arr4, key4));
        System.out.println("Expected Output: 2");

        System.out.println("P02:CeilingOfNumber");
         arr3 = new int[]{4,6,10};
        System.out.print(Arrays.toString(arr3));
        System.out.print(" ,Ceiling Of 6: ");
        System.out.print(searchCeilingOfANumber(arr3, 6)); //1
        // print the expected output
        System.out.println (", Expected Output: 1");
         arr4 =new int[] {1,3,8,10,15};
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

        System.out.println("NextLetter");
        // Test cases
        char[] letters1 = {'a', 'c', 'f', 'h'};
        System.out.println(Arrays.toString(letters1));
        System.out.print("Next letter after 'f' is: " + nextGreatestLetter(letters1, 'f') + ", expected: h\n");
        System.out.print("Next letter after 'b' is: " + nextGreatestLetter(letters1, 'b') + ", expected: c\n");
        System.out.print("Next letter after 'm' is: " + nextGreatestLetter(letters1, 'm') + ", expected: a\n");
        System.out.print("Next letter after 'h' is: " + nextGreatestLetter(letters1, 'h') + ", expected: a\n");

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

        System.out.println("P06. SearchInASortedInfiniteArray");


        int[] nums1 = {4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30};
        ArrayReader reader1 = new ArrayReader(nums1);
         key1 = 16;
        System.out.print("Input: " + Arrays.toString(nums1) + ", key = " + key1);
        int result1 = search(reader1, key1);
        System.out.print(" ,Key " + key1 + " found at index: " + result1 + ", Expected value: 6\n");// should return 6

        // Example 2
        int[] nums2 = {1, 3, 8, 10, 15};
        ArrayReader reader2 = new ArrayReader(nums2);
         key2 = 15;
        System.out.print("Input: " + Arrays.toString(nums2) + ", key = " + key2);
        int result2 = search(reader2, key2);
        System.out.print(" ,Key " + key2 + " found at index: " + result2 + ", Expected value: 4\n");// should return 4

        // Example 3
        key3 = 200;
        System.out.print("Input: " + Arrays.toString(nums2) + ", key = " + key3);
        int result3 = search(reader2, key3);

        System.out.print( " , Key " + key3 + " found at index: " + result3 + ", Expected value: -1\n");// should return -1
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
        int[] numsP09 = {1, 3, 8, 4, 3};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=4, Output: " + makeItBold(searchInBitonicArray(numsP09, 4) +"")+ ", Expected Output: 3"); //
        numsP09 = new int[] {3, 8, 3, 1};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=8, Output: " + makeItBold(searchInBitonicArray(numsP09, 8) +"")+ ", Expected Output: 1"); //
        numsP09 = new int[]{1, 3, 8, 12};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=12, Output: " + makeItBold(searchInBitonicArray(numsP09, 12) +"")+ ", Expected Output: 3"); //
        numsP09 =  new int[]{10, 9, 8};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=10, Output: " + makeItBold(searchInBitonicArray(numsP09, 10) +"")+ ", Expected Output: 0"); //
        // an example when the ky doesn't exist
        numsP09 =  new int[]{1, 3, 8, 4, 3};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=6, Output: " + makeItBold(searchInBitonicArray(numsP09, 6) +"")+ ", Expected Output: -1"); //
        // an example when the key is duplicated
        numsP09 =  new int[]{1, 3, 8,  8, 3,1};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=8, Output: " + makeItBold(searchInBitonicArray(numsP09, 8) +"")+ ", Expected Output: 2"); //

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
