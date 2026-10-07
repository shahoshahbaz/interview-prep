package com.grokingcodeinterview.pattern23Knapsack;

public class P00TabulationVsMemoization {
    public static int fibonacciTabulation(int n) {
        if (n == 0) return 0;
        int[] dp = new int[n + 1];

        // base case
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++)
            dp[i] = dp[i - 1] + dp[i - 2];

        return dp[n];
    }

    public static int fibonacciMemoization(int n){

        int[] memoize = new int[n+1];
        return fibonacci(n, memoize);
}
public static int fibonacci(int n, int[] memoize){
    if(n< 2)
        return n;

    if(memoize[n] !=0)
        return memoize[n];

    memoize[n] = fibonacci(n-1, memoize) + fibonacci(n-1, memoize );
    return memoize[n];

}

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("P00. Fibonnacci Tabulation vs Memoization");
        System.out.println("===================================");
        int n00 = 6;
        System.out.println("Fibonacci of " + n00 + " : " + fibonacciTabulation(n00));
        int n01 = 7;
        System.out.println("Fibonacci of " + n01 + " : " + fibonacciTabulation(n01));
        int n02 = 8;
        System.out.println("Fibonacci of " + n02 + " : " + fibonacciTabulation(n02));
        int n03 = 50;
        System.out.println("Fibonacci of " + n03 + " : " + fibonacciTabulation(n03));


    }
}

