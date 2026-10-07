package com.grokingcodeinterview.pattern07CyclicSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.Utility.makeItBold;

public class pattern08CyclicSortR3 {
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
     * Input: [-3, 1, 5, 4, 2] Output: 3 [1, 2, 3, 4,5] out put = 3
     * Index:  0  1   2  3  4             0, 1, 2, 3, 4
     * correctIndex = nums[i] -1;
     * num[i] = i+1
     */
    public static int findMissingPositive(int[] nums){

        int i =0;

        while (i< nums.length){
            int correctIndex = nums[i] -1;
            if (correctIndex>=0 && correctIndex< nums.length && nums[correctIndex] != nums[i]){
                swap(nums, i, correctIndex);
            }else
                i++;
        }

        for ( i =0; i< nums.length; i++){
            if (nums[i]!= i+1){
                return i+1;
            }

        }
        return -1;
    }

    /*
     * Problem Statement
     * We are given an unsorted array containing numbers taken from the range 1 to â€˜nâ€™. The array can have duplicates, which means some numbers will be missing.
     *  Find all those missing numbers.
     * Example 1: Input: [2, 3, 1, 8, 2, 3, 5, 1]   Output: 4, 6, 7
     * Explanation: The array should have all numbers from 1 to 8, due to duplicates 4, 6, and 7 are missing.
     * Example 2:  Input: [2, 4, 1, 2] Output: 3
     *
     */

    /**
     * range from 1 to n  [1, 2, ....n]
     * nums[0] =1
     * correctectIndex = nums[i] -1;
     */

    public static List<Integer>  findNumbers(int[] nums){
        List<Integer> result = new ArrayList<>();

        int i =0;

        while(i<nums.length){

            int correctIndex = nums[i] -1;

            if (correctIndex< nums.length && nums[correctIndex] != nums[i]){
                swap(nums, correctIndex, i);
            }else{
                i++;
            }

        }

        for (i =0; i< nums.length; i++){
            if (nums[i] != i+1){
                result.add(i+1);
            }
        }

        return result;

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
     * range 1 to n
     *   Input: [3, 4, 4, 5, 5]  Output: [4, 5] [1, 2, .., 5]
     *   num[0 ] = 1, num[nums[0] -1] = 1
     *   correctIndex = nums[0] -1
     */

    public static List<Integer> duplicatesNumber(int[] nums){
        List<Integer> result = new ArrayList<>();
        if (nums == null) return result;
        int i =0;
        while (i<nums.length){

            int correctIndex = nums[i] -1;

            if (correctIndex< nums.length && nums[correctIndex] != nums[i]){
                swap(nums, correctIndex, i);
            }else{
                i++;
            }



        }


        for (i =0; i< nums.length; i++){
            if (nums[i] != i+1){
                result.add(nums[i]);
            }
        }
        return result;
    }
    /*
     * You are given an unsorted integer array of size n. The array contains all integers from 1 to n, but in random order.
     * Your task is to sort the array in-place so that the numbers are ordered from 1 to n.
     * You must solve the problem in O(n) time using constant extra space.
     *
     * You are not allowed to use any built-in sort functions or additional data structures.
     *
     * Example 1:  Input: [3, 1, 5, 4, 2]  Output: [1, 2, 3, 4, 5]
     * Example 2:  Input: [2, 6, 4, 3, 1, 5]  Output: [1, 2, 3, 4, 5, 6]
     * Example 3:  Input: [1, 5, 6, 4, 3, 2]  Output: [1, 2, 3, 4, 5, 6]
     *
     */

    /**
     * Input: [3, 1, 5, 4, 2]  Output: [1, 2, 3, 4, 5]
     * output nums[0] =1 ....
     * correctIndex = num[i] -1;
     */


    public static int[] sort(int[] nums){
        int i =0;
        while ( i<nums.length){
            int correctIndex = nums[i] -1;

            if (nums[correctIndex] !=nums[i]){
                swap(nums, i, correctIndex);
            }else{
                i++;
            }

        }
        return nums;


    }

    /*
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
     * Input: [4, 0, 3, 1] Output: 2
     * range from 0 to 3 output =[0, 1, 2, 3]
     * correctIndex = value;
     *
     */

    public static int findMissingNumberUsingCyclicSort(int[] nums){

        int i =0;
        while(i<nums.length){
            int correctIndex = nums[i];
            if ( correctIndex< nums.length && nums[i] != nums[correctIndex]){
                swap(nums, i, correctIndex);
            }else{
                i++;
            }
        }

        for (i =0; i< nums.length; i++){
            if (nums[i] !=i){
                return i;
            }
        }
        return -1;

    }

    public static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("P02. Cyclic Sort: Find the Missing Number");
        System.out.println("=====================================");


        // Using Cyclic Sort
        int[] numsP02 = new int[]{4, 0, 3, 1};
         int missingNumberP02 = findMissingNumberUsingCyclicSort(numsP02);
        System.out.println("Input: " + makeItBold("[4, 0, 3, 1]") + " , Missing Number Using Cyclic Sort: " + makeItBold(missingNumberP02 +"") +
                "\t EXPECTED: " + makeItBold("2"));

        numsP02 = new int[]{8, 3, 5, 2, 4, 6, 0, 1};
        missingNumberP02 = findMissingNumberUsingCyclicSort(numsP02);
        System.out.println("Input: " + makeItBold("[8, 3, 5, 2, 4, 6, 0, 1]") + " , Missing Number Using Cyclic Sort: " + makeItBold(missingNumberP02 +"") +
                "\t EXPECTED: " + makeItBold("7"));

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
        System.out.println("Input: " + Arrays.toString(numsP05) +", Output: " + makeItBold(duplicatesNumber(numsP05).toString() ) + " Expected: [3, 5]");

        System.out.println("===========================");
        System.out.println("P03. Find All Missing Numbers");
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

