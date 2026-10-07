package com.grokingcodeinterview.pattern24FibonacciNumbers;

import java.util.Arrays;

import static com.Utility.makeItBold;

public class Pattern25FibonacciNumbersR1 {
    /*
There are n houses built in a line. A thief wants to steal the maximum possible money from these houses.
 The only restriction the thief has is that he can't steal from two consecutive houses,
  as that would alert the security system. How should the thief maximize his stealing?

Problem Statement
Given a number array representing the wealth of n houses,
 determine the maximum amount of money the thief can steal without alerting the security system.

Example 1: Input: {2, 5, 1, 3, 6, 2, 4} Output: 15
Explanation: The thief should steal from houses 5 + 6 + 4
Example 2: Input: {2, 10, 14, 8, 1} Output: 18
Explanation: The thief should steal from houses 10 + 8
Constraints:

1 <= wealth.length <= 100
0 <= wealth[i] <= 400
 */

    public static int findMaxStealBottomUp(int[] wealth){
        int n = wealth.length;
        if (n==0) return 0;
        if(n==1) return wealth[0];

        int[] dp= new int[n];
        dp[0] = 0;
        dp[1] = Math.max(wealth[0], wealth[1]);

        for(int i =2; i<n; i++){
            dp[i] = Math.max(wealth[i]+ dp[i-2] , dp[i-1]);
        }

        return dp[n-1];
    }



    /*
    Problem Statement
    Given a staircase with â€˜nâ€™ steps and an array of 'n' numbers representing the fee that you have to pay if you take the step.
    Implement a method to calculate the minimum fee required to reach the top of the staircase (beyond the top-most step).
     At every step, you have an option to take either 1 step, 2 steps, or 3 steps. You should assume that you are standing at the first step.

    Example 1:

    Number of stairs (n) : 6  Fee: {1,2,5,2,1,2} Output: 3
    Explanation: Starting from index '0', we can reach the top through: 0->3->top
    The total fee we have to pay will be (1+2).
    Example 2: Number of stairs (n): 4 Fee: {2,3,4,5} Output: 5
    Explanation: Starting from index '0', we can reach the top through: 0->1->top
    The total fee we have to pay will be (2+3).
 */

    public static int findMinFeeBottomUp(int[] fee){
        int n = fee.length;
        int [] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = fee[0];
        dp[2]= fee[0];
        dp[3] = fee[0];
        for (int i = 4; i<=n; i++)
            dp[i] = Math.min(fee[i-1] + dp[i-1],Math.min(fee[ i-2] + dp[i-2] ,fee[i -3] + dp[i-3] ) );
        return dp[n];
    }
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

    /**
     * Input = {2,1,1,1,4} Output = 3 min number of jumps
     * 0( ->2 -> 3> 4
     */

    public static int countMinJumps(int [] nums){
        return 0;

    }

    /*
    Problem Statement
    Given a number 'n',
     implement a method to count how many possible ways there are to express 'n' as the sum of 1, 3, or 4.

    Example 1: n : 4 output: 4
    Explanation: Following are the four ways we can express 'n' : {1,1,1,1}, {1,3}, {3,1}, {4}
    Example 2: n : 5 output: 6
    Explanation: Following are the six ways we can express 'n' : {1,1,1,1,1}, {1,1,3}, {1,3,1}, {3,1,1},
    {1,4}, {4,1}
 */
    public static int countWaysSumBruteForce(int n){
        if(n== 0) return 1;
        if (n ==1) return 1;
        if (n ==2) return 1;
        if (n == 3) return 2;
        return countWaysSumBruteForce(n-1) + countWaysSumBruteForce(n-3) + countWaysSumBruteForce(n-4);

    }
    public static int countWaysSumTopDown(int n){
        int[] dp = new int[n+1];
        return countWaysSumTopDown(dp, n);
    }
    private static int countWaysSumTopDown(int[] dp, int n){
        if( n== 0) return 1;
        if (n ==1 ) return 1;
        if(n == 2) return 1;
        if(n == 3) return 2;
        if(dp[n] ==0)
            dp[n] = countWaysSumTopDown(dp, n-1) + countWaysSumTopDown(dp, n-3) + countWaysSumTopDown(dp,n-4);
        return dp[n];
    }
    public static int countWaysSumBottomTop(int n){
        int[] dp = new int[n +1];
        dp[0] = 1;
        dp[1] =1;
        dp[2] =1;
        dp[3] = 2;
        for (int i = 4; i<= n; i++)
            dp[i] = dp[i -1] + dp[i-3] +dp[i -4];
        return dp[n];
    }
    public static int countWaysSumBottomTopOptimized(int n){
        if(n == 0) return 1;
        if(n == 1 ) return 1;
        if(n == 2) return 1;
        if(n == 3) return 3;

        int n0 = 1;
        int n1 = 1;
        int n2 = 1;
        int n3 = 2;
        int temp;
        for (int i = 4;i<= n; i++){
            temp = n3 + n1 + n0;
            n0 = n1;
            n1 = n2;
            n2= n3;
            n3 = temp;
        }
        return n3;

    }
    /*
    Problem Statement
    Given a stair with 'n' steps,
    implement a method to count how many possible ways are there to reach the top of the staircase,
    given that, at every step you can either take 1 step, 2 steps, or 3 steps.

    Example 1  Input: n = 3 output: 4
    Explanation: Following are the four ways we can climb : {1,1,1}, {1,2}, {2,1}, {3}
    Example 2: Input n = 4 output: 7

    Explanation: Following are the seven ways we can climb : {1,1,1,1}, {1,1,2}, {1,2,1}, {2,1,1},
    {2,2}, {1,3}, {3,1}
    Constraints:

    1 <= n <= 45.
 */

    public static int countWaysBruteForce(int n){
        if (n == 0) return 1;
        if (n ==1 ) return 1;
        if (n == 2) return 2;
        return countWaysBruteForce(n-1) + countWaysBruteForce(n-2 ) + countWaysBruteForce(n-3);
    }
    public static int countWaysTopDown(int n){
        int[] dp = new int[n+1];
        return countWaysTopDown(dp, n);
    }
    private static int countWaysTopDown(int[] dp, int n){
        if ( n== 0 || n ==1) return n;
        if (n == 2) return 2;
        if(dp[n] == 0){
            dp[n] = countWaysTopDown(dp, n-1) + countWaysTopDown(dp, n-2) +  countWaysTopDown(dp, n-3);
        }
        return dp[n];
    }
    public static int countWaysBottomUp(int n){
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for (int i =3; i<=n; i++)
            dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
        return dp[n];
    }
    public static int countWaysBottomUpOptimized(int n){

        int n0 =1;
        int n1 = 1;
        int n2 = 2;
        int temp ;
        for (int i= 3;i<= n; i++ ){
            temp = n0+ n1 + n2;
            n0 =n1;
            n1 = n2;
            n2 = temp;
        }
        return n2;
    }


    /*
Problem Statement
Write a function to calculate the nth Fibonacci number.
Fibonacci numbers are a series of numbers in which each number is the sum of the two preceding numbers. First few Fibonacci numbers are: 0, 1, 1, 2, 3, 5, 8, ...
Mathematically we can define the Fibonacci numbers as:
    Fib(n) = Fib(n-1) + Fib(n-2), for n > 1
    Given that: Fib(0) = 0, and Fib(1) = 1
Constraints:

0 <= n <= 30
 */
    public static int fibonacci_BF(int n){
        if (n <2) return n;
        return fibonacci_BF(n-1) + fibonacci_BF(n -2);
    }
    public static int fibonacci_topDown(int n){
        int[] dp = new int[n+1];
        dp[0] =0;
        dp[1] =1;
        return fibonacci_topDown_rec(dp, n);
    }
    private static int fibonacci_topDown_rec(int[] dp, int n){
        if (n <2) return n;
        if(dp[n] ==0)
            dp[n] = fibonacci_topDown_rec(dp, n-1) + fibonacci_topDown_rec(dp, n-2);

        return dp[n];

    }

    public static int fibonacci_bottomUp(int n){
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i =2; i<=n; i++){
            dp[i] = dp[i -1] + dp[ i-2];
        }

        return dp[n];
    }

    public static int fibonacci_bottomUp_Optimized(int n){
        if (n<2) return n;
        int n0 =0;
        int n1 =1;

        int temp =0;
        for (int i =2; i<= n; i++){
             temp = n0 + n1;
            n0 = n1;
            n1 = temp;
        }
        return n1;
    }
    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P03. Number factors");
        System.out.println("==============================================================");
        int nP03 = 4;
        System.out.println("Input: " + nP03 + ", Output: " +    makeItBold(countWaysSumBruteForce(nP03) + "")
                + ", Expected Output: 4 <= brute force");
        System.out.println("Input: " + nP03 + ", Output: " +    makeItBold(countWaysSumTopDown(nP03) + "")
                + ", Expected Output: 4 <= top down");
        System.out.println("Input: " + nP03 + ", Output: " +   makeItBold(countWaysSumBottomTop(nP03) + "")
                + ", Expected Output: 4 <= bottom up");
        System.out.println("Input: " + nP03 + ", Output: " +  makeItBold(countWaysSumBottomTopOptimized(nP03) + "")
                + ", Expected Output: 4 <= bottom up-optimized");

        nP03 = 5;
        System.out.println("Input: " + nP03 + ", Output: " +    makeItBold(countWaysSumBruteForce(nP03) + "")
                + ", Expected Output: 6 <= brute force");
        System.out.println("Input: " + nP03 + ", Output: " +   makeItBold(countWaysSumTopDown(nP03) + "")
                + ", Expected Output: 6 <= top down");
        System.out.println("Input: " + nP03 + ", Output: " +   makeItBold(countWaysSumBottomTop(nP03) + "")
                + ", Expected Output: 6 <= bottom up");
        System.out.println("Input: " + nP03 + ", Output: " +  makeItBold(countWaysSumBottomTopOptimized(nP03) + "")
                + ", Expected Output: 6 <= bottom up-optimized");

        nP03 = 6;
        System.out.println("Input: " + nP03 + ", Output: " +  makeItBold(countWaysSumBruteForce(nP03) + "")
                + ", Expected Output: 9 <= brute force");
        System.out.println("Input: " + nP03 + ", Output: " + makeItBold(countWaysSumTopDown(nP03) + "")
                + ", Expected Output: 9 <= top down");
        System.out.println("Input: " + nP03 + ", Output: " +  makeItBold(countWaysSumBottomTop(nP03) + "")
                + ", Expected Output: 9 <= bottom up");
        System.out.println("Input: " + nP03 + ", Output: " +  makeItBold(countWaysSumBottomTopOptimized(nP03) + "")
                + ", Expected Output: 9 <= bottom up-optimized");
        System.out.println("==============================================================");
        System.out.println("P02. Staircase");
        System.out.println("==============================================================");
        int nP02 = 4;
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBruteForce(nP02) +"") + ", Expected Output: 7 <= brute force");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(P02Staircase.countWaysTopDown(nP02) +"") + ", Expected Output: 7 <= top down with memoization");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBottomUp(nP02) +"") + ", Expected Output: 7 <= bottom up with tabulation");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBottomUpOptimized(nP02) +"") + ", Expected Output: 7 <= bottom up with tabulation and space optimization");

        nP02 = 5;
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBruteForce(nP02) +"") + ", Expected Output: 13 <= brute force");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(P02Staircase.countWaysTopDown(nP02) +"") + ", Expected Output: 13 <= top down with memoization");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBottomUp(nP02) +"") + ", Expected Output: 13 <= bottom up with tabulation");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBottomUpOptimized(nP02) +"") + ", Expected Output: 13 <= bottom up with tabulation and space optimization");

        nP02 = 6;
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBruteForce(nP02) +"") + ", Expected Output: 24 <= brute force");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(P02Staircase.countWaysTopDown(nP02) +"") + ", Expected Output: 24 <= top down with memoization");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBottomUp(nP02) +"") + ", Expected Output: 24 <= bottom up with tabulation");
        System.out.println("Input: " + nP02 + ", Output: " + makeItBold(countWaysBottomUpOptimized(nP02) +"") + ", Expected Output: 24 <= bottom up with tabulation and space optimization");

        System.out.println("==============================================================");
        System.out.println("P04. Minimum jumps to reach the end");
        System.out.println("==============================================================");
        int[] jumpsP04 = {2,1,1,1,4};
        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +     makeItBold(countMinJumps(jumpsP04) +"" )+ ", Expected: 3  <= brute force");
//        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +     makeItBold(countMinJumpsTopDown(jumpsP04) +"" )+ ", Expected: 3  <= top down");
//        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +     makeItBold(countMinJumpsBottomUp(jumpsP04) +"" )+ ", Expected: 3  <= bottom up");
        jumpsP04 = new int[]{1,1,3,6,9,3,0,1,3};
        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +     makeItBold(countMinJumps(jumpsP04) +"" )+ ", Expected: 4 <= brute force");
//        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +     makeItBold(countMinJumpsTopDown(jumpsP04) +"" )+ ", Expected: 4 <= top down");
//        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +     makeItBold(countMinJumpsBottomUp(jumpsP04) +"" )+ ", Expected: 4 <= bottom up");
        jumpsP04 = new int[]{1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9};
        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +    makeItBold(countMinJumps(jumpsP04) +"" )+ ", Expected: 3 <= brute force");
//        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +    makeItBold(countMinJumpsTopDown(jumpsP04) +"" )+ ", Expected: 3 <= top down");
//        System.out.println("Input: " + Arrays.toString(jumpsP04) + ", Output: " +    makeItBold(countMinJumpsBottomUp(jumpsP04) +"" )+ ", Expected: 3 <= bottom up");

        System.out.println("==============================================================");
        System.out.println("P05. Minimum jumps with fee");
        System.out.println("==============================================================");
        int[] feeP05 = {1,2,5,2,1,2};
//        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFee(feeP05) +"" )+ ", Expected: 3  <= brute force");
//        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFeeTopDown(feeP05) +"" )+ ", Expected: 3  <= top down");
        System.out.println("Input: " +  makeItBold("fee: " +  Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFeeBottomUp(feeP05) +"" )+ ", Expected: 3  <= bottom up");

        feeP05 = new int[]{2,3,4,5};
//        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFee(feeP05) +"" )+ ", Expected: 5 <= brute force");
//        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFeeTopDown(feeP05) +"" )+ ", Expected: 5 <= top down");
        System.out.println("Input: " +  makeItBold("fee: " +  Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFeeBottomUp(feeP05) +"" )+ ", Expected: 5 <= bottom up");

        feeP05 = new int[]{1,2,3,4,5,6,7,8,9,10};
//        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFee(feeP05) +"" )+ ", Expected: 16 <= brute force");
//        System.out.println("Input: " +  makeItBold("fee: " +  java.util.Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFeeTopDown(feeP05) +"" )+ ", Expected: 16 <= top down");
        System.out.println("Input: " +  makeItBold("fee: " +  Arrays.toString(feeP05)) + ", Output: " +     makeItBold(findMinFeeBottomUp(feeP05) +"" )+ ", Expected: 16 <= bottom up");

        System.out.println("===========================================");
        System.out.println("P06. House Thief");
        System.out.println("===========================================");
        int[] wealthP06 = {2, 5, 1, 3, 6 , 2, 4};
//        System.out.println("Input: " + Arrays.toString(wealthP06) + "Output: " + findMaxSteal(wealthP06) +", Expected: 15 <== recursive");
//        System.out.println("Input: " + Arrays.toString(wealthP06)+ "Output: " + findMaxStealTopDown(wealthP06) +", Expected: 15 <== top down");
        System.out.println("Input: " + Arrays.toString(wealthP06)+ "Output: " + findMaxStealBottomUp(wealthP06) +", Expected: 15 <== bottom up");
        wealthP06 = new int[]{2, 10, 14, 8, 1};
//        System.out.println("Input: " + Arrays.toString(wealthP06) + "Output: " + findMaxSteal(wealthP06) +", Expected: 18 <== recursive");
//        System.out.println("Input: " + Arrays.toString(wealthP06)+ "Output: " + findMaxStealTopDown(wealthP06) +", Expected: 18 <== top down");
        System.out.println("Input: " + Arrays.toString(wealthP06)+ "Output: " + findMaxStealBottomUp(wealthP06) +", Expected: 18 <== bottom up");
        wealthP06 = new int[]{2, 10, 14, 8, 1};
        System.out.println("Input: " + Arrays.toString(wealthP06)+ "Output: " + findMaxStealBottomUp(wealthP06) +", Expected: 18 <== bottom up ");

    }
}

