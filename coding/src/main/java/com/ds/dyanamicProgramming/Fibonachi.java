package com.ds.dyanamicProgramming;

import java.util.HashMap;

public class Fibonachi {

    static int counter=0;
    /**
     * this solution is top-down solution with
     *  time complexity of O(n) and space complexity of O(n)
     *  - solution uses memoization approach
     */

    public static int fibUsingTopDown(int n, HashMap<Integer, Integer> map){
        counter ++;
        if (map.containsKey(n)){
            return map.get(n);
        }
        if ( n ==0 || n ==1){
            map.put(0, 0);
            return n;
        }
        map.put (n, fibUsingTopDown(n -1, map) + fibUsingTopDown(n-2, map));
        return map.get(n);

    }
    public static int fibUsingTopDown(int n){
        HashMap<Integer, Integer> map = new HashMap<>();
        return fibUsingTopDown(n, map);

    }

    /**
     * second solution uses bottom up
     * this solution has time complexity o(n) and
     * space complexity O(n)
     *
     */
    public static int fibUsingBottomUp(int n){
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0,0);
        map.put(1,1);
        fibUsingBottomUp(n, map);

        return map.get(n);

    }
    public static int fibUsingBottomUp(int n, HashMap<Integer, Integer> map){

        for (int i =2; i<=n;i ++){
            counter++;
            map.put(i, map.get(i-1)+ map.get(i-2));
        }
        return map.get(n);

    }

    /**
     * There is another approach it is similar to bottom-up but with the space complexity O(1)
     * to Optimize, you only need to keep track of the last two Fibonacci numbers at any given point.
     * This is because to calculate fib(n), you only need fib(n-1) and fib(n-2).
     * Instead of storing all the Fibonacci numbers up to n in an array or HashMap,
     * you can just store the last two values and update them iteratively.
     *
     */
    public static  int fib(int n ){
        if (n == 1 || n ==0 ) return n;
        int first = 1;
        int second =0;
        int current =0;
        for (int i =2; i<= n; i++){
            current = first + second;    // fib(i) = fib(i-1) + fib(i-2)
            second = first;  // Move first to second
            first = current;  // Move current to first
        }
        return current;


    }
    public static void main(String[] args) {

        //using top-bottm
        System.out.println(fib(10));
        System.out.println("Counter: " +counter);

        counter =0;
        //using bottom-up approach
        System.out.println(fibUsingBottomUp(10));
        System.out.println("Counter: " +counter);

        counter =0;
        //using optimize solution
        System.out.println(fib(10));

    }
}
