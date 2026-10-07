package com.grokingcodeinterview.pattern07CyclicSort;

import static com.Utility.makeItBold;

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
public class P02FindTheMissingNumber {

    public static int findMissingNumber(int[] nums) {
        int n = nums.length;

        int targetSum = n * (n+1)/2;
        int currentSum =0;
        for (int num: nums){
            currentSum += num;
        }

        return targetSum - currentSum;
    }

    public static int findMissingNumberUsingCyclicSort(int[] nums){

        if (nums == null) return -1;

        int i =0;
        while (i< nums.length){
            int correctIndex = nums[i];
            if (correctIndex < nums.length && nums[correctIndex] != i ){
                swap(nums, correctIndex, i);

            }else{
                i++;
            }
        }
        for ( i =0; i< nums.length; i++){
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


    }
}

