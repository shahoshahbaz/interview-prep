package com.grokingcodeinterview.pattern24FibonacciNumbers;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Problem Statement
Given an array of positive numbers,
 where each element represents the max number of jumps that can be made forward from that element,
  write a program to find the minimum number of jumps needed to reach the end of the array (starting from the first element).
   If an element is 0, then we cannot move through that element.

Example 1: Input = {2,1,1,1,4} Output = 3
Explanation: Starting from index '0', we can reach the last index through: 0->2->3->4
Example 2: Input = {1,1,3,6,9,3,0,1,3} Output = 4
Explanation: Starting from index '0', we can reach the last index through: 0->1->2->3->8
Constraints:

1 <= jumps.length <= 104
0 <= jumps[i] <= 1000
It's guaranteed that you can reach jumps[n - 1].
 */
public class P04MinimumJumpsToReachTheEnd {

    /**
     * run dry-run example
     * Input = {2,1,1,1,4} output = 3
     complexity: O(2^n) where n is the length of the input array.
     */
    public static int countMinJumps(int[] jumps){
        return countMinJumps(jumps, 0);

    }

    private static int countMinJumps(int[] jumps, int currentIndex){//

        // if we have reached the last index, we don't
        // need any more jumps.
        if(currentIndex == jumps.length -1) return 0;
        if (jumps[currentIndex] ==0) return Integer.MAX_VALUE;

        int totalJumps = Integer.MAX_VALUE;
        int start = currentIndex +1; // start is the next index of current index and
        int end = currentIndex + jumps[currentIndex]; // end is the maximum index we can jump to from current index

        // we will try to jump to all the indices from start to end and
        // find the minimum jumps needed to reach the end of the array
        while(start<jumps.length && start<=end){

            int minJumps = countMinJumps(jumps, start++);
            if (minJumps != Integer.MAX_VALUE)
                totalJumps =  Math.min(totalJumps, minJumps +1); // why minJumps +1?
            // because we need to add 1 to the minimum jumps needed to reach the end of the array from the next index,
            // since we are jumping from current index to next index.
        }

        return totalJumps;

    }
    public static int countMinJumpsTopDown(int[] jumps){
        int[] dp = new int[jumps.length];
        return countMinJumpsTopDown(dp, jumps, 0);
    }
    private static int countMinJumpsTopDown(int[] dp, int[] jumps, int currentIndex){
        //Base case
        if(currentIndex == jumps.length -1) return 0;

        if(jumps[currentIndex] ==0) return Integer.MAX_VALUE;
        if (dp[currentIndex] != 0)
            return dp[currentIndex];

        int totalJumps = Integer.MAX_VALUE;
        int start = currentIndex +1;
        int end = currentIndex + jumps[currentIndex];

        while(start< jumps.length && start<= end){
            int minJumps = countMinJumpsTopDown(dp, jumps, start++);
            if (minJumps != Integer.MAX_VALUE)
                totalJumps= Math.min(totalJumps, minJumps + 1);
        }
        dp[currentIndex] = totalJumps;
        return dp[currentIndex];
    }
    public static int countMinJumpsBottomUp(int[] jumps){
        int n = jumps.length;
        int[] dp = new int[n];

        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[0] =0;
        for (int start =0; start<n; start++){
            if(dp[start] == Integer.MAX_VALUE || jumps[start] ==0)
                continue;

            //end â†’the farthest valid index we can jump to from `start`
            //start + nums[start] â†’ maximum jump distance
            // n - 1 â†’ last valid index
            //Math.min(...) â†’ prevents going outside the array
            int end = Math.min(n-1, start + jumps[start]);


            for (int next = start +1; next<=end; next++)
                //dp[next] â†’ update best way to reach next
                // existing best path â†’ dp[next]
                //new path through start â†’ dp[start] + 1
                
                dp[next] = Math.min(dp[next], dp[start] +1);
        }
        return dp[n-1];
    }
    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P04. Minimum jumps to reach the end");
        System.out.println("==============================================================");
     int[] jumpsP04 = {2,1,1,1,4};
//        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +     makeItBold(countMinJumps(jumpsP04) +"" )+ ", Expected: 3  <= brute force");
//        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +     makeItBold(countMinJumpsTopDown(jumpsP04) +"" )+ ", Expected: 3  <= top down");
        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +     makeItBold(countMinJumpsBottomUp(jumpsP04) +"" )+ ", Expected: 3  <= bottom up");
        jumpsP04 = new int[]{1,1,3,6,9,3,0,1,3};
//        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +     makeItBold(countMinJumps(jumpsP04) +"" )+ ", Expected: 4 <= brute force");
//        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +     makeItBold(countMinJumpsTopDown(jumpsP04) +"" )+ ", Expected: 4 <= top down");
        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +     makeItBold(countMinJumpsBottomUp(jumpsP04) +"" )+ ", Expected: 4 <= bottom up");
        jumpsP04 = new int[]{1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9};
//        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +    makeItBold(countMinJumps(jumpsP04) +"" )+ ", Expected: 3 <= brute force");
//        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +    makeItBold(countMinJumpsTopDown(jumpsP04) +"" )+ ", Expected: 3 <= top down");
        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +    makeItBold(countMinJumpsBottomUp(jumpsP04) +"" )+ ", Expected: 3 <= bottom up");


    }
}

