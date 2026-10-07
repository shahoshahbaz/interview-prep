package com.grokingcodeinterview.pattern03TwoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class pattern02TwoPointerR2 {

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
     *    xy#z
     *    left, right = 3.. 0
     *    z then # right-- x
     *    xz
     *
     */

    public static boolean compareStrings(String str1, String str2){

        int n1 = str1.length();
        int n2 = str2.length();

        int index1 = n1 -1;
        int index2 = n2 -1;

        while (index1>0 || index2>0){

            int counter1 =0;
            while (index1>0){
                char ch = str1.charAt(index1);
                if (ch =='#'){
                    counter1 ++;
                    index1--;
                }else if (counter1> 0){
                    counter1 --;
                    index1--;
                }else{
                    break;
                }
            }

        }
 return false;
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


    public static List<List<Integer>> searchQuadruplets(int[] nums, int target)
    {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        if (n <4 ) return result;

        Arrays.sort(nums);

        for (int i =0;i< n-3; i++ ){
            if (i> 0 && nums[i] == nums[i-1]) continue;
            for (int j =i+1; j< n-2; j++){
                if (j> 0 && nums[j] == nums[j-1])  j++;


                int left = j+1;
                int right = n-1;

                while (left< right){
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];

                    if (sum == target){
                        result.add( Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        left++;
                        right --;

                        while (left<right && nums[left] == nums[left-1]) left++;
                        while (left<right && nums[right] == nums[right +1 ]) right ++;

                    }else if (sum < target){
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
     * Input: [1, 2, 5, 3, 7, 10, 9, 12]     * Output: 5
     *
     * found left  = 3  nums[left] < nums[left+1]
     * found right  =  6 nums[right]> nums[right -1
     * found min and max between left and right
     *  min = 3, max = 10
     * @param nums
     * @return
     */


    public static int smallestsubArrayLength(int[] nums){

        int n = nums.length;
        int left = 0;
        int right = nums.length -1;

//        found initial left that is out of order
        while (left<right && nums[left]<= nums[left+1] )
            left++;
        // foudn the inital right that is out of order
        while(left< right && nums[right]>= nums[right -1])
            right --;
        if (left == right) return 0; // the array is sorted

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = left; i<= right; i++){
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }

        while ( left> 0  && nums[left-1] > min) {

            left--;
        }while (right+1 < n && nums[right+1]< max){

        }
            right ++;

        return right - left + 1;

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
     *  unosrted
     *   arr[i] + arr[j] + arr[k] < target
     *
     *   [-1, 4, 2, 1, 3], target=5     * Output: 4
     *
     *
     *   sorted: [ -1, 1,2, 3, 4]
     *   i = 0, l = 1, e = 3; sum = -1 + 1+ 4 = 4 < 5 counter + (3 -1 + 1) then counter = 3
     *   i =0, l =2, e = 3; sum = -1 + 2 + 4 = 5 == 5
     *   i = 0 , l = 2, e =3 out of loop
     *   i =1, l
     *
     *
     * @param nums
     * @param target
     * @return
     */

    public static int searchTripletsWithSmallerSum(int[] nums, int target ){
        int n = nums.length;
        if (n< 3) return 0;


        Arrays.sort(nums);
        int counter =0;

        for (int i =0; i< n-2; i++){
            // skip duplicate
            if (i> 0  && nums[i-1] == nums[i]) continue;

            int left = i+1;
            int right = n-1;

            while (left<right){
                int sum = nums[i] + nums[left] + nums[right];
                if (sum < target ){
                    counter  += (right - left +1);
                    left++;

                }else{
                    right --;
                }

            }
        }
        return counter;
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
     * unsorted array
     * unique triplet that add up to zeor
     *   sort[-3, 0, 1, 2, -1, 1, -2] [-3, -2, -1, 0, 1, 1, 2] n = 6
     *   i =0, ...n
     *   l = i+1 , right = n-1;
     *
     *
     */
      public static List<List<Integer>> searchTriplets(int[] nums) {
          List<List<Integer>> result = new ArrayList<>();
          int n = nums.length;

          Arrays.sort(nums); // sort the array

          for (int i =0;i<n-2; i++){ // iterate over i from 0, n-3

              if (i> 0 && nums[i-1] == nums[i]) continue; // avoid duplicate for the first one

              int left = i+1;// next elemnt
              int right = n-1;

              while (left< right){
                  int sum = nums[i] + nums[left] + nums[right];

                  if (sum == 0){
                      result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                      left++;
                      right --;
                      while (left< right && nums[left-1] == nums[left]) left++;
                      while (right>0 && nums[right] == nums[right +1]) right --;

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
     * Input: [-3, -1, 0, 1, 2]      * Output: [0, 1, 1, 4, 9]
     * left = 0,
     * right = n-1;
     * Index = n-1;
     * l = 0, r = 4,  l^2 >= r^2 then result[Index]= l^2 , index --;
     * * l =1, r = 4  result[index] =  r*r r--, index --
     * l = 1, r = 3 resu....
     *
     *
     */

    public static  int[] makeSquares(int[] nums){
        int n= nums.length;
        if (n == 1) return new int[]{nums[0] * nums[0]};

        int left = 0;
        int right = n-1;
        int index = n-1;
        int[] result = new int[ n];

        while (left< right && index >= 0){
            int leftSquare = nums[left] * nums[left];
            int rightSquare = nums[right] * nums[right];

            if (leftSquare>= rightSquare){
                result[index] = leftSquare;
                left++;
                index --;

            }else{
                result[index] = rightSquare;
                right --;
                index --;
            }


        }
        return result;


    }
        /*

Given an array of numbers sorted in ascending order and a target sum,
find a pair in the array
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
Explanation: The numbers at i dex 0 and 2 add up to 11: 2+9=11
Constraints:

2 <= arr.length <= 104
-109 <= arr[i] <= 109
-109 <= target <= 109
Only one valid answer exists.
 */


    public static int[] search(int[] nums, int target){
        // it is already sorted

        int n = nums.length;
        int start = 0;
        int end = n-1;

        while (start<end){
            int sum = nums[start] + nums[end];
            if (sum == target){
                return new int[]{start, end};
            }else if (sum< target){
                start++;
            }else{
                end --;
            }
        }

        return new int[] {-1, -1};

    }

    /*
 * Problem Statement
Given an array of unsorted numbers and a target number,
*  find a triplet in the array whose sum is as close to the target number
* as possible,
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
     * Input: [-1, 0, 2, 3], target=3      * Output: 2
     * sort INput [ -1, 0, 2, 3]
     * closetSum = max, closestDiff = max
     * i = 0, l = 1, r = 3, sum  = 2, closestDiff = 1, closestSum = 2
     * i =0, l = 2, r = 3 , sum = 4, closestDiff = 1, closesum = 2
     * i =0 , l = 2, r= 2 out of while loop
     * ....
     *
     * Input: [0, 0, 1, 1, 2, 6], target=5  Output: 4
     *  * closetSum = max, closestDiff = max
     * i =0, l=1, r = 5, then sum = 6, diff = 6
     *
     * */
    public  static int searchTriplets(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length;
        int smallestSum = Integer.MAX_VALUE;
        int closestDiff = Integer.MAX_VALUE;
        for (int i = 0; i < n - 2; i++) {
            int left = i + 1;
            int right = n - 1;


            while (left < right) {

                int currentSum = nums[i] + nums[left] + nums[right];
                int currentDiff = Math.abs(target - currentSum);

                if (currentDiff == 0) {
                    return currentSum;
                } else {
                    if (currentDiff < closestDiff ||
                            (currentDiff == closestDiff && currentSum < smallestSum)) {
                        smallestSum = currentSum;
                        closestDiff = currentDiff;
                    }
                    if (currentSum < target) {
                        left++;
                    } else {
                        right--;
                    }


                }

            }

        }
        return smallestSum;
    }


        public static void swap(int[] arr, int index1, int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
    /*
    Given an array of sorted numbers, move all non-duplicate number instances at the beginning of the array in-place.
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
     * sorted ascending
     * not to use extra space
     * move all all the unique number instce at the beginnign
     * Input: [2, 3, 3, 3, 6, 9, 9]     * Output: 4
     * when the are not equal move l++ and then swap then r++
     * and when they are equal just move r++
     * l = 0, r =0  2 = 2 r++
     * l = 0, r=1  L++, swap(1, 1), r++; -> Input: [2, 3, 3, 3, 6, 9, 9]
     * l =1, r = 2 3 ==3 r++
     * l =1, r = 3 3 == 3 r++
     * l =1, r = 4, 3 !=6 then l =2, swap(2, 4) and r++ then Input: [2,3,6, 3, 3, 9, 9]
     *
     *
     * @param nums
     * @return
     */

    public static int moveElements(int[] nums){
        if (nums.length ==1) return 1;

        int left =0;
        int right = 0;
        while (right < nums.length){
            if (nums[left] == nums[right]){
                right ++;

            }else{
                left ++;
                swap(nums, left, right);
                right++;
            }

        }
        return left +1;


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
     * Input: [3, 2, 3, 6, 3, 10, 9, 3], Key=3     * Output: 4
     * l =0, r =0, 3==3 r++
     * l =0, r=1 swap(l, r) l++, r ++ [2, 2l, 3,  6r, 3, 10, 9, 3]
     * l = 1, r = 2, r++
     * l =1 , r = 3 swap(l, r), l++, r++,  [2, 6, 3l,  2, 3r, 10, 9, 3]
     *
     *
     */
    public static int remove(int[] nums, int key){
        return 0;

    }
    public static void main(String[] args) {
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

        // some test cases
        System.out.println(searchTriplets(new int[] {-1, 0, 2, 3}, 3)); //2
        System.out.println(searchTriplets(new int[] {-3, -1, 1, 2}, 1)); //0
        System.out.println(searchTriplets(new int[] {1, 0, 1, 1}, 100)); //3
        System.out.println(searchTriplets(new int[] {0, 0, 1, 1, 2, 6}, 5)); //4

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
        System.out.print(" , Expected output: [0, 3]");
        System.out.println(" Pair with target sum: [" + result[0] + ", " + result[1] + "]");

        System.out.println("p");
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


        System.out.println("=================================");
        System.out.println("p11. Min  windows sort");
        System.out.println("=================================");

        int resultS = smallestsubArrayLength(new int[] {1, 2, 5, 3, 7, 10, 9, 12});
        System.out.println("Length of smallest subarray to sort: " + resultS); // Expected output: 5
        resultS = smallestsubArrayLength(new int[] {1, 3, 2, 0, -1, 7, 10});
        System.out.println("Length of smallest subarray to sort: " + resultS); // Expected output: 5
        resultS = smallestsubArrayLength(new int[] {3, 2, 1});
        System.out.println("Length of smallest subarray to sort: " + resultS); // Expected output: 3
        resultS = smallestsubArrayLength(new int[] {1, 2, 3});
        System.out.println("Length of smallest subarray to sort: " + resultS); // Expected   output: 0
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


        System.out.println("===========================");
        System.out.println("P10. Compare Strings with Backspaces..");
        System.out.println("===========================");
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