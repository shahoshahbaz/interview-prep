package com.grokingcodeinterview.pattern31PrefixSum;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Given an array nums and a range query (i, j), find the sum of elements between indices i and j.
Example
Input: arr = [1, 2, 3, 4], i = 1, j = 3
Output: 9
 */
public class P01RangeSumQuery {
    public static int[] computePrefixSum(int[] nums){
        int[] prefix = new int[nums.length];
        // the first element of the prefix sum is the same as the first element of the input array
        // why? because the sum of the first element is just the first element itself
        prefix[0] = nums[0];
        for(int i =1; i< nums.length; i++){
            prefix[i] = prefix[i -1] + nums[i];
        }

        return prefix;
    }

    public static int computeSumQuery(int[] prefix, int i, int j){

        if(i ==0) return prefix[j];
        return prefix[j] - prefix[i -1];


    }
    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("P01. Range Sum Query :");
        System.out.println("=================================================================");

        int[] numsP01 = {1, 2, 3, 4};
        int iP01 = 1;
        int jP01 = 3;
        int[] prefixP01 = computePrefixSum(numsP01);
        System.out.println("Input: " + Arrays.toString(numsP01) + ", i = " + iP01 + ", j = " + jP01 + ", Output: " + makeItBold(computeSumQuery(prefixP01, iP01, jP01) +" ") +"Expected: 9");

        iP01 = 0;
        jP01 = 2;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", i = " + iP01 + ", j = " + jP01 + ", Output: " + makeItBold(computeSumQuery(prefixP01, iP01, jP01) +" ") +"Expected: 6");

        iP01 = 0;
        jP01 = 3;
        System.out.println("Input: " + Arrays.toString(numsP01) + ", i = " + iP01 + ", j = " + jP01 + ", Output: " + makeItBold(computeSumQuery(prefixP01, iP01, jP01) +" ") +"Expected: 10");


    }
}

