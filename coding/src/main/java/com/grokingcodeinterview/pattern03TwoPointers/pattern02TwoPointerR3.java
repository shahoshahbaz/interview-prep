package com.grokingcodeinterview.pattern03TwoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.Utility.makeItBold;


public class pattern02TwoPointerR3 {


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
     * Input: [4, 1, 2, -1, 1, -3], target=1      * Output: [-3, -1, 1, 4], [-3, 1, 1, 2]
     * sort the array [-3,-1, 1, 1, 2, 4] n = 5
     * i =0, j = i+1, l = j+1, r = 4
     * i =0,   j =1, l = 2, r = 4  -3-1,1,4 sum = 1 add to list  check for duplicates and move the l, r
     * now check duplicate for i, and then j
     *  ...
     *      * sort the array [-3i,-1j, 1, 1l, 2l, 4r]
     *      , 0
     *      [2, 0, -1, 1, -2, 2],
     *      [ -2i, -1j, 0r, 1, 2, 2l]
     *  [1,1,1,1,1,1] t = 5
     *  [1i,1j,1r,1,1,1r] found
     *  [1,1i,1j,1l,1,1r] duplicate
     *
     */

    public static List<List<Integer>> searchQuadruplets(int[] nums, int target){

        List<List<Integer>> result = new LinkedList<>();
        int n = nums.length;
        Arrays.sort(nums);
        for (int i =0; i< n-3; i++ ){
                // skip duplicate
            if (i> 0 && nums[i-1] == nums[i]) continue;
            for (int j =i+1; j< n-2; j++){
                // skip duplicates
                if (j> i+1 && nums[j-1] == nums[j]) continue;

                int left = j+1;
                int right = n-1;

                while (left< right){
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target){
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        // skip duplicates
                        while(left< right && nums[left] == nums[left+1]) left++;
                        while (left < right && nums[right] == nums[right -1]) right --;
                        left++;
                        right --;
                    }else if (sum<target){
                        left++;
                    }else{
                        right --;

                    }
                }
            }
        }
        return result;
    }


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
     * Input: str1="xy#z", str2="xyz#" Output: false
     * str1 = xy#z
     * index1 = 3, skip1 =0
     * z valid char index1 = 2
     *
     * xyz#
     * # not valid one , skip2 =1, index = 2
     * z should be skipped, skip2 =0, index =0
     *
     *
     */


   public static boolean compareStrings(String str1, String str2){

       int index1= str1.length()-1;
       int index2 = str2.length() -1;
       int skip1 = 0;
       int skip2 =0;

       while (index1>=0 || index2>=0 ){

       // find valid char in str1
           while (index1>= 0){
               char ch = str1.charAt(index1);
               if (ch == '#'){
                   skip1 ++;
                   index1--;
               }else if (skip1>0){
                   skip1--;
                   index1--;
               } else {
                   break;
               }
           }

           // find valid char in str2
           while (index2>0){
               char ch = str2.charAt(index2);
               if (ch == '#'){
                   skip2++;
                   index2--;
               }else if (skip2>0){
                   skip2 --;
                   index2--;
               }else {
                   break;
               }
           }
           // compar the chars
           char ch1 = (index1>=0)? str1.charAt(index1): '\0';
           char ch2 = (index2>=0)? str2.charAt(index2): '\0';
           if (ch1 != ch2) return false;

           index1--;
           index2 --;
       }

       return true;
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
     *  Input: arr= [2, 2, 0, 1, 2, 0]      * Output: [0, 0, 1, 2, 2, 2]
     *  l =0, m =0; r = 5
     *  m =0 -> swap(m, r) r-- -> [0, 2, 0, 1, 2, 2] r--, r =4
     *  m =0 -> swam(l, r), mid ++, l++  [0, 2, 0, 1, 2, 2] m = 1, l = 1
     *  m = 1, swap(m, r) r--,   [0, 2, 0, 1, 2, 2] r = 3
     *  m = 1, swap(m, r) r-- - [0, 1, 0, 2, 2, 2] r = 2
     *  m = 1 m++, m = 2 [0, 1, 0, 2, 2, 2]
     *  m = 2 swap(m, l) l++, m++  [0, 0, 1, 2, 2, 2]
     *  m = 3, l = 2 r = 2 out of the loop
     *
     */


    public static void sortInplace(int[] nums){

        int left = 0;
        int right = nums.length -1;
        int mid =0;

        while(mid<=right){
            if(nums[mid] == 0){
                swap(nums, left, mid);
                left++;
                mid++;
            }else if (nums[mid] == 2){
                swap(nums, mid, right);
                right --;
            }else{
                mid++;
            }
        }

    }
    public static void swap(int[] arr, int index1, int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
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
     * Input: [1, 2, 5, 3, 7, 10, 9, 12]    n = 7   * Output: 5
     * l = 3,  r =6   5, 3, 7, 10, 9 out put = 5
     *
     *  Input: [1, 3, 2, 0, -1, 7, 10]  n =6     * Output: 5
     *  l =1 r = 4    [3, 2, 0, -1]  [ -1, 0 ,2, 3] -1(min) <left -1   , max > right +1
     *  l = 1, min = -1, max = 3,  -1(min)< nums[left -1] =>  -1< 1  left--
     *   l =0, min = -1, max = 3, r = 4
     *   max = 3, r+1 = 5,  nums[5] =7 3>  7
     *
     *  left = 1, left -1 = 0, nums[0] = 1 > -1 left =0
     *  right = 4, right +1 = 5 nums[5] = 7 7> max(3)
     *  example [3, 2, 1] right=1, right -1 =0
     *  r = 1, r+1<
     */



public static int smallestSubarrayLength(int[] nums){

    int n = nums.length;
    int left = 0;
    int right = n -1;

    while ( left+1< n && nums[left] <= nums[left+1]) left++;
    if (left == n-1) return 0;
    while (right > 0 && nums[right]> nums[right -1]) right--;

    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;

    for (int i = left; i<= right; i++){
        min = Math.min(min, nums[i]);
        max = Math.max(max, nums[i]);
    }

    // expand the bounder of subarray;

    while (left> 0  &&  min < nums[left -1]) left--;

    while ( right +1< n &&  max> nums[right +1]) right ++;




    return (right - left +1);


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
     * Input: [-3, 0, 1, 2, -1, 1, -2]      * Output: [[-3, 1, 2], [-2, 0, 2], [-2, 1, 1], [-1, 0, 1]]
     * i =0, ..n-2 , l = i+1, r = n-1
     * @param nums
     * @return
     */

    public static  List<List<Integer>> searchTriplets(int[] nums){

        List<List<Integer> >result = new ArrayList<>();

        int n = nums.length;
        if (n< 3) return result;

        Arrays.sort(nums); // sorting array

        for (int i =0; i< n-2; i++){

            // avoid duplicate
            if (i> 0 && nums[i-1] == nums[i]) continue;

            int left = i+1;
            int right = n-1;

            while (left<right){
                int sum = nums[i] + nums[right] + nums[left];

                if (sum == 0){

                    result.add (Arrays.asList(nums[i],  nums[left],nums[right] ));
                    left++;
                    right --;

                    // avoid duplicate for left and right
                    while (left< right  && nums[left] == nums[left -1]) left++;
                    while (left< right && nums[right] == nums[right +1]) right --;

                } else if (sum <0){
                    left ++;
                }else{
                    right --;
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
     *  nput: [-1, 4, 2, 1, 3], target=5     * Output: 4
     *  sored [-1, 1, 2, 3, 4] -1, 1, 4  counter = 4 -1+1 = 4
     *
     *
     *
     *
     *
     * @param nums
     * @param key
     * @return
     */
    public static int searchTripletsWithSmallerSum(int[] nums, int key){

        Arrays.sort(nums);
        int n = nums.length;
        int counter =0;
        for (int i =0; i< n-2; i++){
            int left = i+1;
            int right = n-1;

            while (left<right){
                int sum = nums[i] + nums[left] + nums[right];
                if (sum <key){
                    counter+= (right- left );
                    left++;
                }else{
                    right--;
                }

            }
        }
        return counter;
    }

    /*
       Problem Statement
      Given an array of unsorted numbers and a target number,
      find a triplet in the array whose sum is as close to the target number as possible,  return the sum of the triplet.
      If there are more than one such triplet,  return the sum of the triplet with the smallest sum.

    Example 1:     Input: [-1, 0, 2, 3], target=3     Output: 2
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
     *  Input: [-1, 0, 2, 3], target=3     Output: 2
     *  sort it
     *
     */
    public static int searchTripletsCloseToTarget( int[] nums, int target){

       return 0;





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

    /**
     * Input: [3, 2, 3, 6, 3, 10, 9, 3], Key=3      * Output: 4
     * left = -1    * rith = 0,.. 7
     *l = -1, r =0 nums[r] == key r++
     * l = -1 r = 1, nums[r] != key , l++, r =1 num[l] = nums[r]
     * ...
     * @param nums
     * @param key
     * @return
     */




    public static  int removeKeyInPlace(int[] nums, int key){
        int left = -1;

        for (int right = 0; right < nums.length; right ++){
            if (nums[right] != key ){
                left++;
                nums[left] = nums[right];
            }
        }
        return left+1;
    }
    /*
 Given an array of sorted numbers,
  move all non-duplicate number instances at the beginning of the array in-place.

The non-duplicate numbers should be sorted and you should not use any extra space so
that the solution has constant space complexity i.e.,
Move all the unique number instances at the beginning
of the array and after moving return the length of the subarray that has no duplicate in it.

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
     * Input: [2, 3, 3, 3, 6, 9, 9]     * Output: 4
     * lef = 0; right = 1, .. 6
     *
     * l = 0, r =1, nums[l] != num[r]  , l++, nums[l] = nums[r] r++ [2l, 3r, 3, 3, 6, 9, 9]  [2, 3l, 3r, 3, 6, 9, 9]
     * l =  1, r =2 nums[l] == nums[r] , r++; [2, 3l, 3, 3r, 6, 9, 9]
     * l =1, r = 3 nums[l ]! = nums[r], l++, l =2,  [2, 3, 3l, 3, 6r, 9, 9]
     *
     *
     *
     *
     *
     */
    public static int moveNonDuplicateNumber(int[] nums){

        int left = 0;
        for (int right = 1; right<nums.length; right++){
            if (nums[left] != nums[right]){
                left++;
                nums[left] = nums[right];
            }
        }
         return left +1;





    }
    public static void main(String[] args) {
        System.out.println("===============================");
        System.out.println("P02.FindNonDuplicateNumber...");
        System.out.println("===============================");
        int[] arr1 = {2, 3, 3, 3, 6, 9, 9};

        System.out.print(Arrays.toString(arr1)  );
        int length1 = moveNonDuplicateNumber(arr1);


        System.out.print( " ,Length of non-duplicate subarray: " + length1+", Expected output: 4"); // Expected output: 4
        System.out.print("\t"+Arrays.toString(arr1) +"\n" );


        int[] arr2 = {2, 2, 2, 11};
        System.out.print(Arrays.toString(arr2) );
        int length2 = moveNonDuplicateNumber(arr2);

        System.out.print(" ,Length of non-duplicate subarray: " + length2 + ", Expected output: 2"); // Expected output: 2
        System.out.print("\t"+Arrays.toString(arr2)  +"\n");


        int[] arr3 = {1, 1, 2, 3, 4, 4, 5};
        System.out.print(Arrays.toString(arr3) );
        int length3 = moveNonDuplicateNumber(arr3);


        System.out.print(" ,Length of non-duplicate subarray: " + length3+ " ,Expected output: 5"); // Expected output: 5
        System.out.print( "\t"+ Arrays.toString(arr3)+"\n" );

        System.out.println("================================");
        System.out.println("P03. remove key in place..");
        System.out.println("================================");

         arr1 = new int[]{3, 2, 3, 6, 3, 10, 9, 3};

        int length = removeKeyInPlace(arr1, 3);
        System.out.println("Input:" + Arrays.toString(new int[]{3, 2, 3, 6, 3, 10, 9, 3}) + ", key: 3" + " Length after removing key: " + length + ", Expected output: 4" + ", First part of array: " + Arrays.toString(Arrays.copyOfRange(arr1, 0, length)));
        // Test case 2
        arr2 = new int[]{11, 1, 2, 2, 1};

      length2 = removeKeyInPlace(arr2, 2);
        System.out.println("Input:" + Arrays.toString(new int[]{11, 1, 2, 2, 1}) + ", key: 2" + " Length after removing key: " + length2 + ", Expected output: 3" + ", First part of array: " + Arrays.toString(Arrays.copyOfRange(arr2, 0, length2)));

        // some test cases
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("P05. Triple Sum Close to target....");
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println(searchTripletsCloseToTarget(new int[] {-1, 0, 2, 3}, 3)); //2
        System.out.println(searchTripletsCloseToTarget(new int[] {-3, -1, 1, 2}, 1)); //0
        System.out.println(searchTripletsCloseToTarget(new int[] {1, 0, 1, 1}, 100)); //3
        System.out.println(searchTripletsCloseToTarget(new int[] {0, 0, 1, 1, 2, 6}, 5)); //4

        System.out.println("====================================");
        System.out.println("P07. Triplets with Smaller Sum");
        System.out.println("====================================");
        // some test cases
        System.out.println(searchTripletsWithSmallerSum(new int[] {-1, 0, 2, 3}, 3)); //2
        System.out.println(searchTripletsWithSmallerSum(new int[] {-1, 4, 2, 1, 3}, 5)); //4
        System.out.println(searchTripletsWithSmallerSum(new int[] {0, -1, 2, 1, -3}, 2)); // 8



        System.out.println("P04. TripleSumToZero..");
        int[] nums = new int[] { -3, 0, 1, 2, -1, 1, -2 };
        List<List<Integer>> result1 = searchTriplets(nums);
        System.out.println("Triplets summing to zero for this array " + Arrays.toString(nums) + " : " + result1 + "Expected output: [[-3, 1, 2], [-2, 0, 2], [-2, 1, 1], [-1, 0, 1]]");
        nums = new int[] { -5, 2, -1, -2, 3 };
        result1 = searchTriplets(nums);
        System.out.println("Triplets summing to zero for this array " + Arrays.toString(nums) + " : " + result1 + " Expected output: [[-5, 2, 3], [-2, -1, 3]]");

        System.out.println("=================================");
        System.out.println("p11. Min  windows sort");
        System.out.println("=================================");

        int[] numsP11 = {1, 2, 5, 3, 7, 10, 9, 12};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+5));

        numsP11 = new int[] {1, 3, 2, 0, -1, 7, 10};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+5));
        numsP11 = new int[] {1, 2, 3};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+0));
        numsP11 = new int[] {3, 2, 1};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+3));


        numsP11 = new int[] {5, 8, 6, 7, 9, 3, 10, 15, 12, 14};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+7));

        numsP11 = new int[] {1, 2, 3, 4, 5};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+0));
        numsP11 = new int[] {5, 9, 7, 8, 6, 10, 4, 11};
        System.out.println("Input array: " + makeItBold(Arrays.toString(numsP11)) + ", Length of smallest subarray to sort: " + makeItBold(""+smallestSubarrayLength(numsP11)) + " Expected output:" + makeItBold(""+6));


        System.out.println("P08. Dutch National Flag");

        // 4 test cases
        int[] numsP08 = {1, 0, 2, 1, 0};
        System.out.print("Original array: " + Arrays.toString(numsP08));
        sortInplace(numsP08);
        System.out.println(" Sorted array: " + Arrays.toString(numsP08));

        numsP08 = new int[]{2, 2, 0, 1, 2, 0};
        System.out.print("Original array: " + Arrays.toString(numsP08));
        sortInplace(numsP08);
        System.out.println(" Sorted array: " + Arrays.toString(numsP08));

        numsP08 = new int[]{0, 1, 2, 0, 1, 2};;
        System.out.print("Original array: " + Arrays.toString(numsP08));
        sortInplace(numsP08);
        System.out.println(" Sorted array: " + Arrays.toString(numsP08));

        numsP08 = new int[]{2, 1, 0, 2, 1, 0};;
        System.out.print("Original array: " + Arrays.toString(numsP08));
        sortInplace(numsP08);
        System.out.println(" Sorted array: " + Arrays.toString(numsP08));

        System.out.println("=================================");
        System.out.println("P10. Comparing Strings Containing Backspaces");
        System.out.println("=================================");

        // some test caeses
        String str1P10 = "xy#z";
        String str2P10 = "xzz#";
        boolean result = compareStrings(str1P10, str2P10);
        System.out.println( "String");

        System.out.println("String1: " + makeItBold(str1P10) + ", String2: " + makeItBold(str2P10)
                + " Are the two strings equal? " + makeItBold(String.valueOf(result))   + " Expected: " + makeItBold("true")        ); // expected output: true


        str1P10 = "xy#z";
        str2P10 = "xyz#";

        result = compareStrings(str1P10, str2P10);
        System.out.println("String1: " + makeItBold(str1P10) + ", String2: " + makeItBold(str2P10)
                + " Are the two strings equal? " + makeItBold(String.valueOf(result))   + " Expected: " + makeItBold("false")        ); // expected output: false


        str1P10 = "xp#";
        str2P10 = "xyz##";
        result = compareStrings(str1P10, str2P10);
        System.out.println("String1: " + makeItBold(str1P10) + ", String2: " + makeItBold(str2P10)
                + " Are the two strings equal? " + makeItBold(String.valueOf(result))   + " Expected: " + makeItBold("true")        ); // expected output: true

        str1P10 = "xywrrmp";
        str2P10 = "xywrrmu#p";
        result = compareStrings(str1P10, str2P10);
        System.out.println( "String1: " + makeItBold(str1P10) + ", String2: " + makeItBold(str2P10)
                + " Are the two strings equal? " + makeItBold(String.valueOf(result))   + " Expected: " + makeItBold("true")        ); // expected output: true

        System.out.println("==================================");
        System.out.println("P09. Find Quadruple Sum to Target.");
        System.out.println("==================================");

        // add some tests
        int [] numsP09 = new int[] {4, 1, 2, -1, 1, -3};
        int targetP09 = 1;
        System.out.print("Input array: " +makeItBold( Arrays.toString(numsP09)) + ", target: " + makeItBold(""+targetP09 ));
        List<List<Integer>> resultP09 = searchQuadruplets(numsP09, targetP09);
        System.out.print(" ,Quadruplets summing to target " +makeItBold( " : " + resultP09));
        System.out.print(" ,Expected output:  " +makeItBold("[[-3, -1, 1, 4], [-3, 1, 1, 2]] \n"));


        numsP09 = new int[] {2, 0, -1, 1, -2, 2};
        targetP09 = 2;
        resultP09 = searchQuadruplets(numsP09, targetP09);
        System.out.print("array:"+makeItBold( Arrays.toString(numsP09)) +", target: " + targetP09 );
        System.out.print(" ,Quadruplets summing to target" +makeItBold( " : " + resultP09));
        System.out.println(" ,Expected output: " + makeItBold("[[-2, 0, 2, 2], [-1, 0, 1, 2]] "));

        // two more examples
        numsP09 = new int[] {1, 0, -1, 0, -2, 2};
        targetP09 = 0;
        resultP09 = searchQuadruplets(numsP09, targetP09);
        System.out.print("array:"+makeItBold( Arrays.toString(numsP09)) +", target: " + targetP09 );
        System.out.print(" ,Quadruplets summing to target" +makeItBold( " : " + resultP09));
        System.out.println(" ,Expected output: "+ makeItBold("[[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]] "));

        numsP09 = new int[] {0, 0, 0, 0};
        targetP09 = 0;
        resultP09 = searchQuadruplets(numsP09, targetP09);
        System.out.print("array:"+makeItBold( Arrays.toString(numsP09)) +", target: " + targetP09 );
        System.out.print(" ,Quadruplets summing to target" +makeItBold( " : " + resultP09));
        System.out.println(" ,Expected output: [[0, 0, 0, 0]] ");
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
