package com.grokingcodeinterview.pattern07CyclicSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.Utility.makeItBold;

public class pattern08CyclicSortR1 {
    /*
    Problem Statement
    Given an unsorted array containing numbers, find the smallest missing positive number in it.
            Note: Positive numbers start from '1'.

    Example 1: Input: [-3, 1, 5, 4, 2] Output: 3
    Explanation: The smallest missing positive number is '3'
    Example 2: Input: [3, -2, 0, 1, 2] Output: 4
    Example 3: Input: [3, 2, 5, 1] Output: 4
    Example 4: Input: [33, 37, 5] Output: 1
    Constraints:

            1 <= nums.length <=
            -2&31 <= nums[i] <= 2^31 - 1
            */

    /**
     * Input: [-3, 1, 5, 4, 2] Output: 3 range 1, 5 [1,2,... 5]
     * nums[0] = 1 => nums[nums[1] -1] = 1 correctIndex = nums[i] -1
     * Input: [-3, 1, 5, 4, 2], correctIndex = nums[i]-1;
     * i =0; nums[0] =-3 c= -4
     * i =1, nums[1] = 1, c = 0,  -3 != 1swap( 1, 0) [1, -3, 5, 4, 2]
     * i = 2 c = 4,  .. [1, -3, 2, 4, 5]
     *
     *
     */
    public static int findMissingPositive(int[] nums){

        int i =0;
        while (i< nums.length) {

            int correctIndex = nums[i] -1;

            if (correctIndex>= 0 &&  correctIndex< nums.length &&nums[correctIndex] != nums[i]){
                swap(nums, i, correctIndex);
            }else{
                i++;
            }

        }
        for (i =0;i< nums.length; i++){
            if (nums[i] != i+1){
                return i+1;
            }
        }
        return -1;
    }
    /*
     * We are given an unsorted array containing â€˜nâ€™ numbers taken from the range 1 to â€˜nâ€™.
     * The array originally contained all the numbers from 1 to â€˜nâ€™,
     * but due to a data error, one of the numbers got duplicated which also resulted
     * in one number going missing. Find both these numbers.
     * Example 1: Input: [3, 1, 2, 5, 2] Output: [2, 4]
     * Explanation: '2' is duplicated and '4' is missing.
     * Example 2: Input: [3, 1, 2, 3, 6, 4] Output: [3, 5]
     * Explanation: '3' is duplicated and '5' is missing.
     */

    /**
     * range is 1 to n
     * Input: [3, 1, 2, 5, 2] Output: [2, 4]  afte sorting = [1, 2, .. 5]
     * nums[0] = 1,... => nums[nums[1] -1] = 1,
     * correctIndex = nums[i] -1;
     * [3, 1, 2, 5, 2] => [1, 2, 3, 2, 5]  duplicate number is nums[i] and i
     * num[3] = 4
     *
     *
     */

    public static int[] corruptPair(int[] nums){

        int i =0;

        while (i< nums.length){
            int correctIndex = nums[i]-1 ;

            if ( nums[correctIndex] != nums[i]){
                swap(nums,i, correctIndex);

            }else{
                i++;
            }
        }

        for (i =0; i< nums.length; i++){
            if (nums[i] != i+1){
                return new int[]{nums[i], i+1};
            }

        }
        return null;
    }

    /*
     * Problem Statement
     * We are given an unsorted array containing n numbers taken from the range 1 to n.
     * The array has some numbers appearing twice,
     * find all these duplicate numbers using constant space.
     *  Example 1: Input: [3, 4, 4, 5, 5]  Output: [4, 5]
     *  Example 2: Input: [5, 4, 7, 2, 3, 5, 3]  Output: [3, 5]
     *
     */

    /**
     *  range 1 to n [1, 2, 3] correctIndex = nums[i]-1
     *  [5, 4, 7, 2, 3, 5, 3]  Output: [3, 5]
     *
     */
    public static List<Integer> duplicatesNumber(int[] nums){

        int i =0;
        while (i< nums.length){
            int correctIndex = nums[i] -1;

            if (nums[correctIndex ] !=  nums[i]){
                swap(nums, i, correctIndex);
            }else{
                i++;
            }
        }

        List<Integer> result = new ArrayList<>();

        for (i =0; i< nums.length; i++){

            if (nums[i] != i+1){
                result.add(nums[i]);
            }

        }
        return result;


}
    /*
     * Problem Statement
     * We are given an unsorted array containing n+1 numbers
     * from the range 1 to n. The array has only one duplicate
     * but it can be repeated multiple times.
     * Find that duplicate number without using any extra space.
     * You are, however, allowed to modify the input array.
     *
     * Example 1: Input: [1, 4, 4, 3, 2] Output: 4
     * Example 2: Input: [2, 1, 3, 3, 5, 4] Output: 3
     * Example 3: Input: [2, 4, 1, 4, 4] Output: 4
     *
     */

    /**
     *  range form 1 to n
     *  Input: [1, 4, 4, 3, 2] Output: 4
     *  [1,2,3,4]
     *  nums[0] = 1
     *  correctIndex =value(nums[i] -1;
     */
    public static int findDuplicateNumber(int[]  nums){

        int i =0;

        while (i< nums.length){
            int correctIndex = nums[i] -1;

            if (nums[correctIndex] != nums[i]){
                swap(nums, i, correctIndex);
            }else{
                i++;
            }
        }

        for (i =0; i<nums.length; i++){
            if (nums[i] != i+1){
                return nums[i];
            }
        }
        return -1;

    }


    /*
  * Problem Statement
 * We are given an unsorted array containing numbers taken from the range 1 to â€˜nâ€™.
 The array can have duplicates, which means some numbers will be missing. Find all those missing numbers.
   *Example 1:
 * Input: [2, 3, 1, 8, 2, 3, 5, 1]   Output: 4, 6, 7
 * Explanation: The array should have all numbers from 1 to 8, due to duplicates 4, 6, and 7 are missing.
 * Example 2:  Input: [2, 4, 1, 2] Output: 3
 */

    /**
     * number are from the range 1 to n
     * length of arraay is n
     * [2, 3, 1, 8, 2, 3, 5, 1]   Output: 4, 6, 7
     *      validation if (nums[correcteIndex] != i -1)swap (i, correctIndex)
     * [1, 2, 3, X, 5, X,X , 8] correctIndex = nums[i] -1 1
     * [2, 3, 1, 8, 2, 3, 5, 1]   i =0. correctedIndex= nums[0]-1 = 2 -1 =1 . validation num[1] =3 !=
     * correctedIndex = 1 then 1!=2-1 so swap(0, 1)
     * [3, 2, 1, 8, 2, 3, 5, 1] i =0 correctIndex = num[0] -1 =3-1 = 2 correctIndex = 2,
     */
    public  static List<Integer> findNumbers(int[] nums){

        int n = nums.length;
        int i =0;
        while(i< n){
            int correctIndex = nums[i] -1;
            if(nums[i] !=nums[correctIndex] ){
                swap(nums, i, correctIndex);
            }else{
                i++;
            }
        }
        List<Integer> results = new ArrayList<>();

        for ( i =0; i< n; i++){
            if (nums[i]  != i +1){
                results.add(i+1);
            }
        }
        return results;

   }


    /*
     *
     * We are given an array containing n distinct numbers taken from the range 0 to n. Since the array has only n numbers out of the total n+1 numbers, find the missing number.
     * Example 1:  Input: [4, 0, 3, 1] Output: 2
     * Example 2:  Input: [8, 3, 5, 2, 4, 6, 0, 1]  Output: 7
     * Constraints:
     * n == nums.length
     * 1 <= n <=10 ^4
     * 0 <= nums[i] <= n
     * All the numbers of nums are unique.
     */

    /**
     * numbers are unique
     * range is from 0 to n
     * length of array is n
     * Input: [4, 0, 3, 1] Output: 2
     * sum of n+1 number = n(n-1)/2
     * then sum of all number
     *
     */
    public static int findMissingNumber(int[] nums) {

        int n = nums.length;
        int currentSum = 0;
        int targetSum = n * (n + 1) / 2;
        for (int num : nums) {
            currentSum += num;
        }
        return targetSum - currentSum;
    }

    /**
     * numbers are unique
     * range is from 0 to n
     * length of array is n
     * Input: [4, 0, 3, 1] Output: 2
     * size of array = 4, then number should be 0 to 3
     * cyclic sort [0, 1, 2, 3]
     * correctIndex = nums[i]
     * Input: [4, 0, 3, 1] Output: 2
     * i = 0, correctIndex  = 4 skip
     *  i =1, correctIndex = 1, nums[1] = 0 != 1 swap (0,1) [0, 4, 3, 1]
     *  i =1 correctIndex = 4, skip
     *  i = 2, correctIndex = nums[2] = 3, swap(3, 2)  [0, 4, 1, 3]
     *  i = 2, correctIndex =nums[2] = 1, swap(1, 2)  [0, 1, 4, 3]
     *  i = 2 correctIndex = nums[2] = 4 skip
     *  i = 3 correcIndex = nums[3] = 3 , i = 4
     *
     */

    public  static int findMissingNumberUsingCyclicSort(int[] nums) {

        int n = nums.length;
        int i =0;

        while (i< n) {
            int correctIndex = nums[i];
            if (correctIndex < n && nums[correctIndex] != i) {
                swap(nums, correctIndex, i);
            }else{
                i++;
            }
        }

        for ( i =0; i< n; i++){
            if (nums[i] != i){
                return i;
            }
        }
        return 0;

    }

    /*
     * You are given an unsorted integer array of size n. The array contains all integers from 1 to n, but in random order. Your task is to sort the array in-place so that the numbers are ordered from 1 to n.
     * You must solve the problem in O(n) time using constant extra space.
     *
     * You are not allowed to use any built-in sort functions or additional data structures.
     *
     * Example 1:
     * Input: [3, 1, 5, 4, 2]
     * Output: [1, 2, 3, 4, 5]
     *
     * Example 2:
     * Input: [2, 6, 4, 3, 1, 5]
     * Output: [1, 2, 3, 4, 5, 6]
     *
     * Example 3:
     * Input: [1, 5, 6, 4, 3, 2]
     * Output: [1, 2, 3, 4, 5, 6]

     */

    /**
     * nums is between 1, ... n
     *
     */

    public static int[] sort(int[] nums){

        for (int i =0; i<nums.length; i++ ){
            int correctIndex = nums[i] -1;
            if (nums[i] != nums[correctIndex]){
                swap(nums, i, correctIndex);
            }
        }
        return nums;

    }

    public static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }



    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("P01. Cyclic Sort: Sort the Array");
        System.out.println("=====================================");

        // lets have 4 test cases here

        int[] numsP01 = {3, 1, 5, 4, 2};
        int[] sortedArrayP01 = sort(numsP01);

        System.out.println("Input: " + makeItBold(Arrays.toString(new int[]{3, 1, 5, 4, 2})) + " , Sorted: " + makeItBold(Arrays.toString(sortedArrayP01)) +
                "\t EXPECTED: " + makeItBold("[1, 2, 3, 4, 5]"));

        numsP01 = new int[]{2, 6, 4, 3, 1, 5};
        sortedArrayP01 = sort(numsP01);
        System.out.println("Input: " + makeItBold(Arrays.toString(new int[]{2, 6, 4, 3, 1, 5})) + " , Sorted: " + makeItBold(Arrays.toString(sortedArrayP01)) +
                "\t EXPECTED: " + makeItBold("[1, 2, 3, 4, 5, 6]"));
        numsP01 = new int[]{1, 5, 6, 4, 3, 2};
        sortedArrayP01 = sort(numsP01);
        System.out.println("Input: " + makeItBold(Arrays.toString(new int[]{1, 5, 6, 4, 3, 2})) + " , Sorted: " + makeItBold(Arrays.toString(sortedArrayP01)) +
                "\t EXPECTED: " + makeItBold("[1, 2, 3, 4, 5, 6]"));

        System.out.println("=====================================");
        System.out.println("P02. Cyclic Sort: Find the Missing Number");
        System.out.println("=====================================");

        int[] numsP02 = {4, 0, 3, 1};
        int missingNumberP02 = findMissingNumber(numsP02);
        System.out.println("Input: " + makeItBold("[4, 0, 3, 1]") + " , Missing Number: " + makeItBold(missingNumberP02+"") +
                "\t EXPECTED: " + makeItBold("2"));

        numsP02 = new int[]{8, 3, 5, 2, 4, 6, 0, 1};
        missingNumberP02 = findMissingNumber(numsP02);
        System.out.println("Input: " + makeItBold("[8, 3, 5, 2, 4, 6, 0, 1]") + " , Missing Number: " + makeItBold(missingNumberP02 +"") +
                "\t EXPECTED: " + makeItBold("7"));

        // Using Cyclic Sort
        numsP02 = new int[]{4, 0, 3, 1};
        missingNumberP02 = findMissingNumberUsingCyclicSort(numsP02);
        System.out.println("Input: " + makeItBold("[4, 0, 3, 1]") + " , Missing Number Using Cyclic Sort: " + makeItBold(missingNumberP02 +"") +
                "\t EXPECTED: " + makeItBold("2"));

        numsP02 = new int[]{8, 3, 5, 2, 4, 6, 0, 1};
        missingNumberP02 = findMissingNumberUsingCyclicSort(numsP02);
        System.out.println("Input: " + makeItBold("[8, 3, 5, 2, 4, 6, 0, 1]") + " , Missing Number Using Cyclic Sort: " + makeItBold(missingNumberP02 +"") +
                "\t EXPECTED: " + makeItBold("7"));

        System.out.println("===========================");
        System.out.println("`P03. Find All Missing Numbers");
        System.out.println("===========================");

        int[] inputP03 =  new int[]{2, 3, 1, 8, 2, 3, 5, 1};
        List<Integer> resultP03 = findNumbers(inputP03);
        System.out.println("Input: " + Arrays.toString(inputP03) + " => Missing numbers: " + makeItBold(resultP03.toString()) + " , Expected: [4, 6, 7]");
        inputP03 = new int[]{2, 3, 1, 8, 2, 3, 5, 1};
        resultP03 = findNumbers(inputP03);
        System.out.println("Input: " + Arrays.toString(inputP03) + " => Missing numbers: " + makeItBold(resultP03.toString()) + " , Expected: [4, 6, 7]"); // expected: [4, 6, 7]

        inputP03 = new int[]{2, 3, 1, 8, 2, 3, 5, 1};
        resultP03 = findNumbers(inputP03);
        System.out.println("Input: " + Arrays.toString(inputP03) + " => Missing numbers: " + makeItBold(resultP03.toString()) + " , Expected: [4, 6, 7]");

        // different test case
        inputP03 = new int[]{2, 4, 1, 2};
        resultP03 = findNumbers(inputP03);
        System.out.println("Input: " + Arrays.toString(inputP03) + " => Missing numbers: " + makeItBold(resultP03.toString()) + " , Expected: [3]");

        System.out.println("====================================");
        System.out.println("P04. Find the Duplicate Number");
        System.out.println("====================================");
        int[] inputsP04 ={1, 4, 4, 3, 2};
        System.out.println("Input: " + Arrays.toString(inputsP04) + " => Output: " + makeItBold(findDuplicateNumber(inputsP04)+"") +", Expected: 4");
        inputsP04 =  new int[]{2, 1, 3, 3, 5, 4};
        System.out.println("Input: " + Arrays.toString(inputsP04) + " => Output: " + makeItBold(findDuplicateNumber(inputsP04)+"") +", Expected: 3");
        inputsP04 = new int[] {2, 4, 1, 4, 4};
        System.out.println("Input: " + Arrays.toString(inputsP04) + " => Output: " + makeItBold(findDuplicateNumber(inputsP04)+"") +", Expected: 4");
        inputsP04 =  new int[]{3, 1, 3, 4, 2};
        System.out.println("Input: " + Arrays.toString(inputsP04) + " => Output: " + makeItBold(findDuplicateNumber(inputsP04)+"") +", Expected: 3");
        inputsP04 = new int[] {1, 1};
        System.out.println("Input: " + Arrays.toString(inputsP04) + " => Output: " + makeItBold(findDuplicateNumber(inputsP04)+"") +", Expected: 1");
        System.out.println("==========================================");
        System.out.println("P05. Find All Duplicate Numbers");
        System.out.println("==========================================");


        int[] numsP05 = {3, 4, 4, 5, 5};
        System.out.println("Input: " + Arrays.toString(numsP05) +", Output: " + makeItBold(duplicatesNumber(numsP05).toString() ) + "Expected: [4, 5]");
        numsP05 = new int[]{5, 4, 7, 2, 3, 5, 3};
        System.out.println("Input: " + Arrays.toString(numsP05) +", Output: " + makeItBold(duplicatesNumber(numsP05).toString() ) + " Expected: [3, 5]");
        numsP05 = new int[]{1, 2, 3, 4};
        System.out.println("Input: " + Arrays.toString(numsP05) +", Output: " + makeItBold(duplicatesNumber(numsP05).toString() ) + " Expected: []");
        numsP05 = new int[] {5, 4, 7, 2, 3, 5, 3};
        System.out.println("I" + "nput: " + Arrays.toString(numsP05) +", Output: " + makeItBold(duplicatesNumber(numsP05).toString() ) + " Expected: [3, 5]");

        System.out.println("===================================================");
        System.out.println("P06. Find the Corrupt Pair");
        System.out.println("===================================================");
        int[] numsP06 = new int[]{3, 1, 2, 5, 2};
        int[] result06 = corruptPair( numsP06);

        System.out.println("Input: " + Arrays.toString(numsP06) +
                " ,Output: " + makeItBold(Arrays.toString(result06)) + ", Expected Output: [2, 4]");

        numsP06 = new int[]{3, 1, 2, 3, 6, 4};
        result06 = corruptPair( numsP06);
        System.out.println("Input: " + Arrays.toString(numsP06) +
                " ,Output: " + makeItBold(Arrays.toString(result06)) + ", Expected Output: [3, 5]");
        numsP06 = new int[]{1, 5, 3, 2, 2, 7, 6, 4, 8, 8};
        result06 = corruptPair( numsP06);
        System.out.println("Input: " + Arrays.toString(numsP06) +
                " ,Output: " + makeItBold(Arrays.toString(result06)) + ", Expected Output: [2, 9]");




        System.out.println("===============================================================");
        System.out.println("P07. Find the Smallest Missing Positive Number");
        System.out.println("===============================================================");

        int[] numsP07 = {-3, 1, 5, 4, 2};
        System.out.println("input: " + Arrays.toString(numsP07) +", output: " + makeItBold(findMissingPositive(numsP07)+"") +" ,Expected: 3");
        numsP07 = new int[]{3, -2, 0, 1, 2};
        System.out.println("input: " + Arrays.toString(numsP07) +", output: " + makeItBold(findMissingPositive(numsP07)+"") +" ,Expected: 4");
        numsP07 = new int[]{3, 2, 5, 1};
        System.out.println("input: " + Arrays.toString(numsP07) +", output: " + makeItBold(findMissingPositive(numsP07)+"") +" ,Expected: 4");
        numsP07 = new int[]{33, 37, 5};
        System.out.println("input: " + Arrays.toString(numsP07) +", output: " + makeItBold(findMissingPositive(numsP07)+"") +" ,Expected: 1");



    }




}

