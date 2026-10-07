package com.grokingcodeinterview.pattern03TwoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
Problem Statement
Given two strings containing backspaces (identified by the character ‘#’),
check if the two strings are equal.

 Example 1: Input: str1="xy#z", str2="xzz#" Output: true
Explanation: After applying backspaces the strings become "xz" and "xz" respectively.
 Example 2:

Input: str1="xy#z", str2="xyz#" Output: false
Explanation: After applying backspaces the strings become "xz" and "xy" respectively.

  Example 3: Input: str1="xp#", str2="xyz##" Output: true
Explanation: After applying backspaces the strings become "x" and "x" respectively.
        In "xyz##", the first '#' removes the character 'z' and the second '#' removes the character 'y'.
Example 4:

Input: str1="xywrrmp", str2="xywrrmu#p" Output: true
Explanation: After applying backspaces the strings become "xywrrmp" and "xywrrmp" respectively.
        Constraints:

        1 <= str1.length, str2.length <= 200
str1 and str2 only contain lowercase letters and '#' characters.
 */
/**
 *  Input: str1="xy#z", str2="xzz#" Output: true
 *   xy#z : xz
 *   l = 0,r =1,  then back space
 *   num[r] == '#' the r--
 *   [x,y,#,z]
 *   l = 0...3 delteCharat(l-1)
 *   sb(x.z)
 * x,y,#,z
 *  x l = 0, xy
 *  y l = 1
 *  #  remove DeltecharAt(1) x
 *  z l = 1
 */

class pattern02TwoPointerR1 {
 public static boolean compareStrings(String str1, String str2){
        str1 = removeChar(str1);
        str2 = removeChar(str2);

        return str1.equals(str2);

    }

    public static String removeChar(String str){
        StringBuilder sb = new StringBuilder();
        int left = 0;

        for(char ch: str.toCharArray()){
            if (ch !='#'){
                sb.append(ch);
            }else if (sb.length()> 0){
                sb.deleteCharAt(sb.length() -1);

            }
        }

       return sb.toString();
    }

    /*
Problem Statement
Given an array,
 find the length of the smallest subarray in it
  which when sorted will sort the whole array.

Example 1:

Input: [1, 2, 5, 3, 7, 10, 9, 12]
Output: 5
Explanation: We need to sort only the subarray [5, 3, 7, 10, 9] to make the whole array sorted
Example 2:

Input: [1, 3, 2, 0, -1, 7, 10]
Output: 5
Explanation: We need to sort only the subarray [1, 3, 2, 0, -1] to make the whole array sorted
Example 3:

Input: [1, 2, 3]
Output: 0
Explanation: The array is already sorted
Example 4:

Input: [3, 2, 1]
Output: 3
Explanation: The whole array needs to be sorted.
Constraints:

1 <= arr.length <= 104
-105 <= arr[i] <= 105
 */

    /**
     * Input: [1, 2, 5, 3, 7, 10, 9, 12]      * Output: 5  [1, 2, , 3,5,  7, 9, 10, , 12]   5, 3, 7, 10, 9
     *
     *
     *
      *
     * @param nums
     * @return
     */

    public static int smallestsubArrayLength(int[] nums){
        if (nums== null || nums.length ==1) return 0;

        int left = 0;
        while (left< nums.length-1 && nums[left]< nums[left+1]) left++;
        int right = nums.length -1;
        while (right>0 && nums[right]> nums[ right -1]) right --;
        // find a min and max in between left and right
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = left; i<=right; i++ ){
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);

        }

        // now compare the beginning of array with min
        while (left>0 && nums[left -1]> min ) left--;
        while (right< nums.length -1 && nums[right +1] < max) right ++;

        return right - left +1;



    }

    /*
Problem Statement
Given an array containing 0s, 1s and 2s, sort the array in-place.
 You should treat numbers of the array as objects,
  hence, we can’t count 0s, 1s, and 2s to recreate the array.

The flag of the Netherlands consists of three colors: red, white and blue;
 and since our input array also consists of three different numbers that is
  why it is called Dutch National Flag problem.

Examples
Example 1
Input: arr = [1, 0, 2, 1, 0]
Output: [0, 0, 1, 1, 2]
Explanation:
All 0s are moved to the front, 1s in the middle, and 2s at the end.
The relative order within each group doesn't matter.
Example 2
Input: arr= [2, 2, 0, 1, 2, 0]
Output: [0, 0, 1, 2, 2, 2]
Explanation:
All 0s come first, followed by the 1, and then all 2s at the end.
Sorting is done in-place without using extra space or counting.
Constraints:

n == arr.length
1 <= n <= 300
arr[i] is either 0, 1, or 2.
 */
    /**
     * ort in place
     * contains only 0, 1, 2
     * l =0, mid =0, right = n-1;
     * arr= [2, 2, 0, 1, 2, 0] output: [0, 0, 1, 2, 2, 2]
     * l =0, mid = 0, right = 5  [0, 2, 0, 1, 2, 2] right --
     * l =0, mid = 0, right = 4  [0, 2, 0, 1, 2, 2] mid++, left++
     * l =1, mid = 1 right = 4  [0, 2, 0, 1, 2, 2] right --
     * l =1, mid = 1 right = 3  [0, 1, 0, 2, 2, 2] right --
     * l =1, mid = 1 right = 2  [0, 1, 0, 2, 2, 2] mid++
     * l =1, mid = 2 right = 2  [0, 1, 0, 2, 2, 2] swap(l, mid) l++, mid++
     * l =2, mid = 3 right = 2  [0, 0, 1, 2, 2, 2] swap(l, mid) l++, mid++
     *
     *
     *
     *
     *
     *
     *
     */
        public static void sortInplace(int[] nums){

            int n = nums.length;
            int left =0;
            int right = n-1;
            int mid = 0;

            while (mid<= right){

                if (nums[mid] ==0){
                    swap(nums, left, mid);
                    left++;
                    mid ++;
                } else if(nums[mid] ==2){
                    swap(nums, right, mid);
                    right --;
                }else{
                    mid++;
                }
            }


        }


 /*
Given an array of unsorted numbers and a target number,
find all unique quadruplets in it, whose sum is equal to the target number.

Example 1:

Input: [4, 1, 2, -1, 1, -3], target=1
Output: [-3, -1, 1, 4], [-3, 1, 1, 2]
Explanation: Both the quadruplets add up to the target.
Example 2:

Input: [2, 0, -1, 1, -2, 2], target=2
Output: [-2, 0, 2, 2], [-1, 0, 1, 2]
Explanation: Both the quadruplets add up to the target.
Constraints:

1 <= nums.length <= 200
-109 <= nums[i] <= 109
-109 <= target <= 109
 */

    /**
     * unsorted  array
     * find all unique quadruplet  which sum == target
     * this needs to be uniqe so avoid duplicate
     *
     * sort the array
     * i = 0, n-3
     * j =i+1, n-2
     * l = j+1, right = n-1
     */

    public static  List<List<Integer>> searchQuadruplets(int[] nums, int target) {

        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;


        if (n< 4) return result;

        Arrays.sort(nums);

        for (int i =0; i< n-3; i++){
            if (i>0 && nums[i] == nums[i-1]) continue;
            for (int j = i+1; j<n-2; j++){
                if (j>1 && nums[j] == nums[j -1]) continue;
                int left = j+1;
                int right = n-1;

                while (left< right){
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];

                    if (sum == target){
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        left++;
                        right --;

                        while (left< right && nums[left] == nums[left -1]) left++; //avoid duplicates
                        while (right> 0 && nums[right] == nums[right +1]) right --; // avoid duplicates
                    } else if (sum > target){
                        right --;
                    }else{
                        left++;
                    }
                }
            }
        }
 return result;
    }

    /*
Given an array arr of unsorted numbers
and a target sum,
 count all triplets in it such that arr[i] + arr[j] + arr[k] < target
  where i, j, and k are three different indices.
   Write a function to return the count of such triplets.

Example 1:

Input: [-1, 0, 2, 3], target=3
Output: 2
Explanation: There are two triplets whose sum is less than the target: [-1, 0, 3], [-1, 0, 2]
Example 2:

Input: [-1, 4, 2, 1, 3], target=5
Output: 4
Explanation: There are four triplets whose sum is less than the target:
[-1, 1, 4], [-1, 1, 3], [-1, 1, 2], [-1, 2, 3]
Constraints:

n == arr.length
0 <= n <= 3500
-100 <= arr[i] <= 100
-100 <= target <= 100
 */

    /**
     * arr[i] + arr[j] + arr[k] < target
     *  sort the array
     */
    public static int searchTripletsWithSmallerSum(int[] nums, int target) {
        int n = nums.length;
        if (n< 3) return 0;
        int counter = 0;
        for (int i =0; i< n -2; i++){


            int left = i+1;
            int right = n -1;

            while (left<= right){
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == target){

                    left++;
                    right --;


                }else if (sum < target){
                    counter +=(right - left);
                    left ++;



                }else{
                    right --;


                }

            }
        }
        return 0;
    }


    /*
 * Problem Statement
Given an array of unsorted numbers and a target number,
*  find a triplet in the array whose sum is as close to the target number as possible,
*  return the sum of the triplet.
*  If there are more than one such triplet,
*  return the sum of the triplet with the smallest sum.

Example 1:

Input: [-1, 0, 2, 3], target=3
Output: 2
Explanation: The triplet [-1, 0, 3] has the sum '2' which is closest to the target.

There are two triplets with distance '1' from the target: [-1, 0, 3] & [-1, 2, 3].
*  Between these two triplets, the correct answer will be [-1, 0, 3] as
* it has a sum '2' which is less than the sum of the other triplet which is '4'.
*  This is because of the following requirement:
* 'If there are more than one such triplet,
*  return the sum of the triplet with the smallest sum.'
Example 2:

Input: [-3, -1, 1, 2], target=1
Output: 0
Explanation: The triplet [-3, 1, 2] has the closest sum to the target.
Example 3:

Input: [1, 0, 1, 1], target=100
Output: 3
Explanation: The triplet [1, 1, 1] has the closest sum to the target.
Example 4:

Input: [0, 0, 1, 1, 2, 6], target=5
Output: 4
Explanation: There are two triplets with distance '1' from target: [1, 1, 2] & [0, 0, 6]. Between these two triplets, the correct answer will be [1, 1, 2] as it has a sum '4' which is less than the sum of the other triplet which is '6'. This is because of the following requirement: 'If there are more than one such triplet, return the sum of the triplet with the smallest sum.'
Constraints:

3 <= arr.length <= 500
-1000 <= arr[i] <= 1000
-104 <= target <= 104
 */
    /**
     *  sum of tirplet close to target
     *  If there are more than one such triplet,      return the sum of the triplet with the smallest sum.
     *  Input: [-1, 0, 2, 3], target=3      * Output: 2
     *  sort the arry
     *  minSum = max
     *  ClosestSum = max
     *  i =0, l = 1, r = 3, sum =2, target = 3, closetToTarget
    **/
    public  static int searchTriplets(int[] nums, int target){

//


return 0;
    }

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

    /**
     * Input: [-3, 0, 1, 2, -1, 1, -2]     * Output: [[-3, 1, 2], [-2, 0, 2], [-2, 1, 1], [-1, 0, 1]]
     * sort the nums => [-3, -2, -1, 0, 1, 1, 2]
     *  i =0,...6
     *  l = i+1,r = 6
     *   i =0, l =1, r = 6 sum < 0 , left++
     *   i =0, l = 2, r = 6 sum  < 0 , left++
     *   i =0, le = 3, r = 6 sum < 0 , left++
     *   i = 0, l = 4, r = 6 sum  = 0 record this set [-3, 1, 2
     *   whil(l< r && nums[left] == nums[left+1} left++
     *
     *
     *
     */
   public static List<List<Integer>> searchTriplets(int[] arr){

       List< List<Integer>> result = new ArrayList<>();
       Arrays.sort(arr);

       int n = arr.length;
       for (int i =0; i< n; i++){
           if  (i>0 && arr[i] == arr[i-1]) continue;

           int left = i+1;
           int right = n-1;

           while (left< right){
               int sum = arr[i] + arr[left] + arr[right];

               if (sum ==0){
                   result.add(Arrays.asList(arr[i], arr[left], arr[right]));
                   left++;
                   right --;

                   while (left< right && arr[left] == arr[left-1]) left++;
                   while (left< right && arr[right] == arr[right+1]) right --;

               }else if (sum<0){
                   left++;
               }else{
                   right --;
               }


           }

       }
       return result;

   }

    /*

Given an array of numbers sorted in ascending order and a target sum, find a pair in the array
 whose sum is equal to the given target.
Write a function to return the indices of the two numbers (i.e. the pair)
 such that they add up to the given target. If no such pair exists return [-1, -1].

Example 1:
Input: [1, 2, 3, 4, 6], target=6
Output: [1, 3]
Explanation: The numbers at index 1 and 3 add up to 6: 2+4=6
Example 2:

Input: [2, 5, 9, 11], target=11
Output: [0, 2]
Explanation: The numbers at index 0 and 2 add up to 11: 2+9=11
Constraints:

2 <= arr.length <= 104
-109 <= arr[i] <= 109
-109 <= target <= 109
Only one valid answer exists.
 */

    /**
     * sorted in ascending order,
     * find a pair that sum equals to given target
     * Input =[1 2 3 4 6] target = 6, output [1, 3]
     * left = 0, right = 4, 1+6 > 6 then right = 4
     * left = 0, right = 3, 1+ 4 = 5< 6 then left = 1
     * left =1, right = 4  6 =6
     */
    public static int[] search(int[] arr, int targetSum){

        int n = arr.length;

        // edge case
        if (n ==2 && (arr[0]+ arr[1]) == targetSum ) return new int[]{arr[0], arr[1]};

        int left = 0;
        int right = n-1;

        while (left<= right){
            int sum = arr[left] + arr[right];

            if (sum == targetSum){
                return new int[]{left, right};
            }else if (sum> targetSum ){
                right --;
            }else{
                left++;
            }
        }
             return null;





    }
/*

Given an array of sorted numbers,
* move all non-duplicate number instances at the beginning of the array in-place.
*  The non-duplicate numbers should be sorted
* and you should not use any extra space so
* that the solution has constant space complexity i.e.,
Move all the unique number instances at the beginning
*  of the array and after moving return the length of the subarray that has no duplicate in it.

Example 1:

Input: [2, 3, 3, 3, 6, 9, 9]
Output: 4
Explanation: The first four elements after moving element will be [2, 3, 6, 9].
Example 2:

Input: [2, 2, 2, 11]
Output: 2
Explanation: The first two elements after moving elements will be [2, 11].
Constraints:

1 <= nums.length <= 3 * 104
-100 <= nums[i] <= 100
nums is sorted in non-decreasing order.
 */

    /**
     * [2, 3, 3, 3, 6, 9, 9]  Output: 4 [2, 3, 6, 9]
     *  i =0,...
     *  left = 0 rigth= 0  then right =1
     *  left =0 right =1   then left =1 swap(1,1) then , right = 2 [2, 3,3,3, 6, 9, 9]
     *  left =1, right =2 then right =3,
     *  left =1, right = 3 then right ++ right = 4
     *  left = 1, right = 4 then right ++ right = 5
     *  left =1, right  5 , left++, swap(left, right),right++
     *  left = 2, right = 6, [2, 3, 6, 3, 3, 9, 9] , left++, swap(left, right), right ++;
     *  left = 3, right = 7  [2 3 6, 9,3,9, 9]
     *
     *
     */
    public static int moveElements(int[] arr){

        int n = arr.length;
        if (n == 1) return 1;
        int left = 0;
        int right = 0;
        while (right<n){
            if (arr[left] == arr[right]){
                right++;
            }else{
                left++;

                // swap the element
                swap(arr, left, right);
                right++;
            }
        }
        return left+1;

    }
    public static void swap(int[] arr, int index1, int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    /*
Problem 1: Given an unsorted array of numbers and a target ‘key’,
 remove all instances of ‘key’ in-place and return the new length of the array.

Example 1:

Input: [3, 2, 3, 6, 3, 10, 9, 3], Key=3
Output: 4
Explanation: The first four elements after removing every 'Key' will be [2, 6, 10, 9].
Example 2:

Input: [2, 11, 2, 2, 1], Key=2
Output: 2
Explanation: The first two elements after removing every 'Key' will be [11, 1].
 */

    /** return new length
     * unsorted
     * Input: [3, 2, 3, 6, 3, 10, 9, 3], Key=3     * Output: 4
     * n = 8
     * Explanation: The first four elements after removing every 'Key' will be [2, 6, 10, 9].
     * l = 0,r = 0 arr[0] === key then l= 0,
     * l = 0, r =1 2!=3 swap(l, r) and then left++, so [2,3, 3 6 3 10 9 3]
     * l = 1, r = 2  arr[2] == key yes,
     * l=1, r = 3, arr[3] =6 != 3,  swap(1, 3) so [2,6, 3, 3,3, 10, 9, 3] left = 2
     * l = 2, r = 4, arr[4] = 3  the do nothing
     * l = 2, r = 5 arr[5] = 10!=3 swap(2, 5) so [2, 6 10, 3, 3, 3, 9, 3] then left = 3
     * l = 3 , r = 6 arr[6] = 9 != 3 swap(3, 6) so [2, 6, 10, 9, 3, 3, 3,3,3] thenleft = 4
     * l= 4, r = 7 arr[7] = 3 do ntoheing
     * out of the loop
     *

     */

    public static int remove(int[] nums, int key){

        int left = 0;
        int right = 0;

        while (right<nums.length){
            if (nums[right] == key){
                right++;
            }else{
                swap(nums, left, right);
                left++;
                right++;
            }
        }
        return left;
    }

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

    /**
     * Input: [-2, -1, 0, 2, 3]     * Output: [0, 1, 4, 4, 9]
     * n = 5,
     * last elementIndex = 4
     * left = 0, righ = n-1, right = 4 => , 4, 9 then fill it out from end [9], right --;
     * left = 0, righ = 3, enelemtnIndex = 3 , [0,0,0,0,9], then[0 0 0 0 4, 9] right --;
     * left = 0, right = 2, endlmentIndex =2,  then[0, 0,4, 4,9] then left++;
     * left =1, right = 2, elemntIndex = 1, then [0, , 1, 4, 4, 9] then  left++
     *
     *
     *
     *
     */
    public static int[] makeSquares(int[] arr){
        int n = arr.length;

        int[] result = new int[n];

        int resultIndex = n-1;

        int left =0;
        int right = n-1;

        while (left<=right){

            int squareLeft = arr[left] * arr[left];
            int squareRight = arr[right] * arr[right];

            if (squareRight >=squareLeft){
                result[resultIndex] = squareRight;

                right --;
            }else{
                result[resultIndex] = squareLeft;

                left ++;
            }
            resultIndex--;
        }

        return result;

    }

    public static void main(String[] args) {
        System.out.println("P01:PariWithTargetSum..");
        int arr[] = {1, 2, 3, 4, 6};
        int[] result = search(arr, 6);
        System.out.print(Arrays.toString(arr) +"sum = 6");
        System.out.print(" , Expected output: [1, 3]");
        System.out.println(" Pair with target sum: [" + result[0] + ", " + result[1] + "]"); //expected output: [1, 3]

        arr = new int[] { 2, 5, 9, 11 };
        result = search(arr, 11); //expected output: [0, 2]
        System.out.print(Arrays.toString(arr) +" sum = 11");
        System.out.print(" , Expected output: [0, 2]");
        System.out.println(" ,Pair with target sum: [" + result[0] + ", " + result[1] + "]");
        // additional test cases
        arr= new int[] { 1, 3, 5, 7, 9 };
        result = search(arr, 8);  //expected output: [0, 4]
        System.out.print(Arrays.toString(arr)+" sum = 8");
        System.out.print(" , Expected output: [-3, 4]");
        System.out.println(" Pair with target sum: [" + result[0] + ", " + result[1] + "]");

        System.out.println("P02.FindNonDuplicateNumber...");
        int[] arr1 = {2, 3, 3, 3, 6, 9, 9};

        System.out.print(Arrays.toString(arr1) +"\t" );
        int length1 = moveElements(arr1);
        System.out.print(Arrays.toString(arr1) +"\t" );


        System.out.println("Length of non-duplicate subarray: " + length1+", Expected output: 4"); // Expected output: 4

        int[] arr2 = {2, 2, 2, 11};
        System.out.print(Arrays.toString(arr2)+"\t" );
        int length2 = moveElements(arr2);
        System.out.print(Arrays.toString(arr2)  +"\t");
        System.out.println("Length of non-duplicate subarray: " + length2 + ", Expected output: 2"); // Expected output: 2


        int[] arr3 = {1, 1, 2, 3, 4, 4, 5};
        System.out.print(Arrays.toString(arr3)+"\t" );

        int length3 = moveElements(arr3);
        System.out.print(Arrays.toString(arr3)+"\t" );

        System.out.println("Length of non-duplicate subarray: " + length3+ " ,Expected output: 5"); // Expected output: 5

        System.out.println("P05.Remove Key In place.....\n");
      arr1 = new int[]{3, 2, 3, 6, 3, 10, 9, 3};
        System.out.print(Arrays.toString(arr1) +", key = 3\t");
        int length = remove(arr1, 3);
        System.out.print(Arrays.toString(arr1) +"\t");

        System.out.println(", The length is :"+ length +" ,Expected output: 4"); // Output: 4 (After removing 3s, the array becomes {2, 6, 10, 9})
//        System.out.println(Arrays.toString(Arrays.copyOfRange(arr1, 0, length  )));


        // Test case 2
         arr2 = new int[]{2, 11, 2, 2, 1};
        System.out.print(Arrays.toString(arr2)+", key = 2 \t");
       length2 = remove(arr2, 2);
        System.out.print(Arrays.toString(arr2)+"\t");
        System.out.println("The length is :"+ length2 +" ,Expected output = 3") ;

        System.out.print(Arrays.toString(arr2)+", key = 11\t");

         length3 = remove(arr2, 11);
        System.out.print(Arrays.toString(arr2)+"\t");
        System.out.println("The length is :"+ length3 +" ,Expected output = 4") ;

//        System.out.println(Arrays.toString(Arrays.copyOfRange(arr2, 0, length2 )));


        System.out.println("P03. Squaring A sorted Array...");

         arr = new int[] { -2, -1, 0, 2, 3 };
        System.out.print(Arrays.toString(arr));
       result = makeSquares(arr);
        System.out.println(Arrays.toString(result) +", Expected output: [0, 1, 4, 4, 9]");

        arr = new int[] { -3, -1, 0, 1, 2 };
        System.out.print(Arrays.toString(arr));
        result = makeSquares(arr);
        System.out.println(Arrays.toString(result) +", Expected output: [0, 1, 1, 4, 9 ]"); // Expected output: [0, 1, 1, 4, 9 ]

        System.out.println("P04. TripleSumToZero..");
        int[] nums = new int[] { -3, 0, 1, 2, -1, 1, -2 };
        List<List<Integer>> result1 = searchTriplets(nums);
        System.out.println("Triplets summing to zero for this array " + Arrays.toString(nums) + " : " + result1 + "Expected output: [[-3, 1, 2], [-2, 0, 2], [-2, 1, 1], [-1, 0, 1]]");
        nums = new int[] { -5, 2, -1, -2, 3 };
        result1 = searchTriplets(nums);
        System.out.println("Triplets summing to zero for this array " + Arrays.toString(nums) + " : " + result1 + " Expected output: [[-5, 2, 3], [-2, -1, 3]]");

        System.out.println("===========================");
        System.out.println("P09. Quadruple Sum to target..");
        // add some tests
        arr = new int[] {4, 1, 2, -1, 1, -3};
        int target = 1;
        System.out.print("Input array: " + Arrays.toString(arr) + ", target: " + target );
        List<List<Integer>> resultQ = searchQuadruplets(arr, target);
        System.out.println(", Quadruplets summing to target " + target + " : " + resultQ);

        // Expected output: [[-3, -1, 1, 4], [-3, 1, 1, 2]]
        arr = new int[] {2, 0, -1, 1, -2, 2};
        target = 2;
        resultQ = searchQuadruplets(arr, target);
        System.out.println("Quadruplets summing to target " + target + " : " + resultQ);
        // Expected output: [[-2, 0, 2, 2], [-1, 0, 1, 2]]
        System.out.println("P08. Dutch Nationla Flagg");

        // some test cases
        int nums1[] = {1, 0, 2, 1, 0};
        System.out.println("Original array: " + Arrays.toString(nums1));
        sortInplace(nums1);
        System.out.println("Sorted array: " + Arrays.toString(nums1));

        int nums2[] = {2, 2, 0, 1, 2, 0};
        System.out.println("Original array: " + Arrays.toString(nums2));
        sortInplace(nums2);
        System.out.println("Sorted array: " + Arrays.toString(nums2));

        System.out.println("p11. Min   windows sort");

        int resultS = smallestsubArrayLength(new int[] {1, 2, 5, 3, 7, 10, 9, 12});
        System.out.println("Length of smallest subarray to sort: " + resultS); // Expected output: 5
        resultS = smallestsubArrayLength(new int[] {1, 3, 2, 0, -1, 7, 10});
        System.out.println("Length of smallest subarray to sort: " + resultS); // Expected output: 5
        resultS = smallestsubArrayLength(new int[] {3, 2, 1});
        System.out.println("Length of smallest subarray to sort: " + resultS); // Expected output: 3
        resultS = smallestsubArrayLength(new int[] {1, 2, 3});
        System.out.println("Length of smallest subarray to sort: " + resultS); // Expected   output: 0

        System.out.println("P11. Minmum Windows sort");
        // add some test cases here
        int ResultP11 = smallestsubArrayLength(new int[] {1, 2, 5, 3, 7, 10, 9, 12});
        System.out.println("Length of smallest subarray to sort: " + ResultP11); // Expected output: 5
        ResultP11 = smallestsubArrayLength(new int[] {1, 3, 2, 0, -1, 7, 10});
        System.out.println("Length of smallest subarray to sort: " + ResultP11); // Expected output: 5
        ResultP11 = smallestsubArrayLength(new int[] {3, 2, 1});
        System.out.println("Length of smallest subarray to sort: " + ResultP11); // Expected output: 3
        ResultP11 = smallestsubArrayLength(new int[] {1, 2, 3});
        System.out.println("Length of smallest subarray to sort: " + ResultP11); // Expected   output: 0


        // some test caeses
        String str1 = "xy#z";
        String str2 = "xzz#";
        boolean resultP10 = compareStrings(str1, str2);
        System.out.println("are "+ str1 + " and "+ str2 + " equal after backspace processing? " + resultP10); // expected output: true

//          boolean result2 = compareStringsTwoPointers(str1, str2);
//        System.out.println("Are the two strings equal? " + result2); // expected output: true
        str1 = "xy#z";
        str2 = "xyz#";
        resultP10 = compareStrings(str1, str2);
        System.out.println("are "+ str1 + " and "+ str2 + " equal after backspace processing? " + resultP10); // expected output: false
//        result2 = compareStringsTwoPointers(str1, str2);
//        System.out.println("Are the two strings equal? " + result2); // expected output: false
        str1 = "xp#";
        str2 = "xyz##";
        resultP10 = compareStrings(str1, str2);
        System.out.println("are "+ str1 + " and "+ str2 + " equal after backspace processing? " + resultP10); // expected output: true
        str1 = "xywrrmp";
        str2 = "xywrrmu#p";
        resultP10 = compareStrings(str1, str2);
        System.out.println("are "+ str1 + " and "+ str2 + " equal after backspace processing? " + resultP10); // expected output: true

    }
}