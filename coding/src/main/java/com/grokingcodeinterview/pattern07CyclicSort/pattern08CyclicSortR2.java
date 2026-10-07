package com.grokingcodeinterview.pattern07CyclicSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.Utility.makeItBold;

public class pattern08CyclicSortR2 {

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
    Input: [-3, 1, 5, 4, 2] Output: 3 Input = [1, 2, 3, 4, 5] output = 3
 correctIndex = nums[0] -1 = -3 -1 = -4
 i =0; i=1, c=nums[1] -1 = 0 swap(0,1)  [1, -3, 5, 4, 2]
 i = 1, c = -4, i = 2
 i =2, c = 4 -1 = 4 swap(2, 4) [1, -3, 2, 4, 5]
 i = 2, c = 2-1 = 1, swap(2, 1) [1, 2, -3, 4, 5]
 i = 2, i = 3, c = 4-1 = 3 num
 nput: [3, -2, 0, 1, 2] Output: 4 Input =[0, 1, 2, 3, 4] outut: 4
 */
public static int findMissingPositive(int[] nums){

    int i =0;
    while (i< nums.length){
        int correctIndex = nums[i] -1;
        if (correctIndex>=0 && correctIndex< nums.length && nums[correctIndex] != nums[i] ){
            swap(nums, correctIndex, i);
        }else{
            i++;
        }
    }

    for (i =0; i< nums.length;i++){
        if (nums[i] !=i+1){
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
     * Input: [3, 1, 2, 5, 2] Output: [2, 4] [1, 2, 3, 4, 5]
     * nums[i] = i+1;
     * nums[0
     * correctIndex = nums[i] -1;
     * i:0, c=num[0]-1 = c = 2
     * num[2] != nums[0] swap (0, 2)  => [2, 1, 3, 5, 2]
     * i =0, c:2 -1 =1
     * num[1] != nums[0] swap (1, 2) => [1, 2, 3, 5, 2]
     * ...
     * [1, 2, 3, 2, 5]
     */

    public static int[] corruptPair(int[] nums){
        int i =0;
        while( i< nums.length ){
            int correctIndex = nums[i] -1;

            if(nums[correctIndex] != nums[i]){
                swap(nums, correctIndex, i);
            }else{
                i++;
            }
        }

        for (i =0; i< nums.length; i++){
            if (nums[i] != i+1){
                return new int[]{ nums[i], i+1};
            }
        }
        return new int[]{-1, -1};

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
     * range 1 to n, nums= [1, 2,3,....]
     * correctIndex = nums[i] -1
     *
     */

    public static int findDuplicateNumber(int[] nums){

        int result = -1;

        int i =0;

        while (i< nums.length){
            int correctIndex = nums[i] -1;

            if (correctIndex < nums.length && nums[correctIndex] != nums[i]){
                swap(nums, i, correctIndex);
            }else{
                i++;
            }
        }


       i =0;
        while(i< nums.length){
            if (nums[i] != i+1){
                result = nums[i];
            }
            i++;

        }
        return result;
    }

    /*
     * Problem Statement
     * We are given an unsorted array containing numbers taken from the range 1 to â€˜nâ€™.
     * The array can have duplicates, which means some numbers will be missing.
     *  Find all those missing numbers.
     * Example 1: Input: [2, 3, 1, 8, 2, 3, 5, 1]   Output: 4, 6, 7
     * Explanation: The array should have all numbers from 1 to 8, due to duplicates 4, 6, and 7 are missing.
     * Example 2:  Input: [2, 4, 1, 2] Output: 3
     *
     */

    /**
     * 1 to n
     * Input: [2, 4, 1, 2] Output: 3 after sorting [1, 2, 3, 4]
     *  nums[0] = 1
     */
    public static List<Integer> findNumbers(int[] nums){

        List<Integer> result = new ArrayList<>();

        int i =0;
        while (i< nums.length ){
            int correctIndex = nums[i] -1;

            if (nums[correctIndex] !=  nums[i]){
                swap(nums, correctIndex, i);
            }else{
                i++;
            }
        }

        for ( i =0; i< nums.length; i++){
            if (nums[i]  != i +1)
                result.add(i+1);

        }

        return result;


    }

    /*
     * You are given an unsorted integer array of size n. The array contains all integers from 1 to n, but in random order. Your task is to sort the array in-place so that the numbers are ordered from 1 to n.
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
     * value is 1 to n
     *  Input: [3, 1, 5, 4, 2]  Output: [1, 2, 3, 4, 5]
     * correctIndex = value -1  arr[0] =1, arr[1] = 0
     *  i =0 , nums[0] =3 c= 2, 3 !=5 swap(0, 2) [5, 1, 3, 4, 2]
     *  i = 0
     *
     *
     */

    public static int[] sort(int [] nums){


        int i =0;
        while ( i< nums.length ){
            int correctIndex = nums[i]-1;
            if (nums[i] != nums[correctIndex]){
                swap(nums, i, correctIndex);
            }else{
                i++;
            }
        }
        return nums;

    }
    /*
     *
     * We are given an array containing n distinct numbers taken from the range 0 to n.
     *  Since the array has only n numbers out of the total n+1 numbers, find the missing number.
     * Example 1:  Input: [4, 0, 3, 1] Output: 2
     * Example 2:  Input: [8, 3, 5, 2, 4, 6, 0, 1]  Output: 7
     * Constraints:
     * n == nums.length
     * 1 <= n <=10 ^4
     * 0 <= nums[i] <= n
     * All the numbers of nums are unique.
     */

    /** range is from 0 to n
     * 2. length of array is n
     * number are unique
     *
     */
    public static int findMissingNumber(int[] nums){

        int currentSum =0;
        for (int num:nums){
            currentSum += num;
        }

        int n = nums.length;
        int targetSum = n * (n+1)/2;
        return targetSum - currentSum;

    }
    /** range is from 0 to n
     * 2. length of array is n
     * number are unique
     *  Input: [4, 0, 3, 1] Output: 2
     *  desired sorted array [0,1, 2, 3] correctIndex = nums[i] meaing nums[0] = 0
     *  correctedIndex = nums[i];
     *  Input: [4, 0, 3, 1]
     *  i =0,  correctIndex = num[0]=4 not valid index
     *  i =1 correcIndex = 0, nums[correcIndex](0) !=1 swap(i, correcindex) [0, 4, 3, 1]
     *
     *
     *  after sorting scan the array one more time and find the missing index;
     */
    public  static int findMissingNumberUsingCyclicSort(int[] nums){

        int n = nums.length;

        int i = 0;
        while (i< n){
            int correctIndex = nums[i];
            if (correctIndex < n && nums[correctIndex] != i){
                swap(nums, correctIndex, i);
            }else{
                i++;
            }

        }

        for (i =0; i< n; i++){
            if (nums[i] != i){
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

