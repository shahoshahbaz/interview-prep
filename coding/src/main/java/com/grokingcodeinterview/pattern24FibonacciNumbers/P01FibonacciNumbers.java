package com.grokingcodeinterview.pattern24FibonacciNumbers;

import static com.Utility.makeItBold;

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
public class P01FibonacciNumbers {

    public static int fibonacciBrutForce(int n) {
     if (n ==0) return 0;
     if (n ==1) return 1;

     return  fibonacciBrutForce(n-1) + fibonacciBrutForce(n-2);
    }



    public static int fibonacciTopDown(int n){
        int[] dp = new int[n+1];

        return fibonacciTopDown(dp, n ) ;

    }

    private static int fibonacciTopDown(int[] dp , int n){

        // base
        if (n<2) return n;

        if(dp[n] ==0)
            dp[n] = fibonacciTopDown(dp, n-1) + fibonacciTopDown(dp, n-2);
        return dp[n];
    }

    public static int fibonacciBottomUp(int n){
        if(n<2) return n;

        int[] dp = new int[n +1];

        dp[0] = 0;
        dp[1] = 1;
        for (int i =2; i<=n; i++)
            dp[i] = dp[ i -1] + dp[i -2];

        return dp[n];
    }

    public static int fibonacciBottomUpOptimized(int n){
        if (n< 2) return n;

        int n1 =0;
        int n2 = 1;
        int temp;

        for (int i =2; i<= n; i++){
            temp = n1 + n2;

            n1 = n2;
            n2 = temp;
        }
        return n2;
    }



    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P01. Fibonacci numbers");
        System.out.println("==============================================================");
        int nP01 = 5;
        System.out.println("Input: " + nP01 + ", Output: " + makeItBold(fibonacciBrutForce(nP01) +"") + ", Expected Output: 5 <= brute force");
        System.out.println("Input: " + nP01 + ", Output: " + makeItBold(fibonacciTopDown(nP01) +"") + ", Expected Output: 5 <= top down with memoization");
        System.out.println("Input: " + nP01 + ", Output: " + makeItBold(fibonacciBottomUp(nP01) +"") + ", Expected Output: 5 <= bottom up with tabulation");
        System.out.println("Input: " + nP01 + ", Output: " + makeItBold(fibonacciBottomUpOptimized(nP01) +"") + ", Expected Output: 5 <= bottom up with tabulation and space optimization");

        nP01 = 10;
        System.out.println("Input: " + nP01 + ", Output: " + makeItBold(fibonacciBrutForce(nP01) +"") + ", Expected Output: 55 <= brute force");
        System.out.println("Input: " + nP01 + ", Output: " + makeItBold(fibonacciTopDown(nP01) +"") + ", Expected Output: 55 <= top down with memoization");
        System.out.println("Input: " + nP01 + ", Output: " + makeItBold(fibonacciBottomUp(nP01) +"") + ", Expected Output: 55 <= bottom up with tabulation");
        System.out.println("Input: " + nP01 + ", Output: " + makeItBold(fibonacciBottomUpOptimized(nP01) +"") + ", Expected Output: 55 <= bottom up with tabulation and space optimization");

        nP01 = 30;
        System.out.println("Input: " + nP01 + ", Output: " + makeItBold(fibonacciBrutForce(nP01) +"") + ", Expected Output: 832040 <= brute force");
        System.out.println("Input: " + nP01 + ", Output: " + makeItBold(fibonacciTopDown(nP01) +"") + ", Expected Output: 832040 <= top down with memoization");
        System.out.println("Input: " + nP01 + ", Output: " + makeItBold(fibonacciBottomUp(nP01) +"") + ", Expected Output: 832040 <= bottom up with tabulation");
        System.out.println("Input: " + nP01 + ", Output: " + makeItBold(fibonacciBottomUpOptimized(nP01) +"") + ", Expected Output: 832040 <= bottom up with tabulation and space optimization");


         nP01 = 10;

    }
}

