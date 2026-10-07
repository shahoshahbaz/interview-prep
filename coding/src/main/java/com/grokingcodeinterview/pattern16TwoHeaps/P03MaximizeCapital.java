package com.grokingcodeinterview.pattern16TwoHeaps;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Given a set of investment projects with their respective profits, we need to find the most profitable projects.
 * We are given an initial capital and are allowed to invest only in a fixed number of projects. Our goal is to choose projects that give us the maximum profit. Write a function that returns the maximum total capital after selecting the most profitable projects.
 * We can start an investment project only when we have the required capital. After selecting a project, we can assume that its profit has become our capital, and that we have also received our capital back.
 * Example 1:
 * Input: Project Capitals=[0,1,2], Project Profits=[1,2,3], Initial Capital=1, Number of Projects=2
 * Output: 6
 * Explanation:
 * 1. With initial capital of â€˜1â€™, we will start the second project which will give us profit of â€˜2â€™. Once we selected our first project, our total capital will become 3 (profit + initial capital).
 * 2. With â€˜3â€™ capital, we will select the third project, which will give us â€˜3â€™ profit.
 * After the completion of the two projects, our total capital will be 6 (1+2+3).
 */

/**
 * Solution:
 *          how to solve this problem.  we can use two heaps to solve this problem
 *          -one min heap to store the projects based on their capital requirement
 *            min heap will help us to get the projects that can be started with the current capital because the smallest capital requirement will be at the top
 *         one max heap to store the projects based on their profit
 *          - max heap will help us to get the project with the maximum profit that can be started with the current capital beacause the largest profit will be at the top
 *
 *         we will iterate for the number of projects we can select
 *         in each iteration, we will add all the projects that can be started with the current
 *         capital to the max heap
 *         then we will select the project with the maximum profit from the max heap
 *         and add its profit to the current capital
 *         we will repeat this process for the number of projects we can select
 *
 */
public class P03MaximizeCapital {

    public final boolean DEBUG = true;

    public void log(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }

    public int findMaximumCapital(int[] capital, int[] profits,
                                  int numberOfProjects, int initialCapital) {
        log("Initial Capital: " + initialCapital + " Number of Projects: " + numberOfProjects + " Capital Requirements: " + Arrays.toString(capital) + " Profits: " +Arrays.toString(profits));



        PriorityQueue<Integer> minCapitalHeap = new PriorityQueue<>((a, b) -> capital[a] - capital[b]);
        PriorityQueue<Integer> maxProfitHeap = new PriorityQueue<>((a, b) -> profits[b] - profits[a]);

        // step1: add all the projects to the min heap based on their capital requirement
        for (int i=0; i<capital.length; i++) {
            log("Adding project with capital: " + capital[i] + " and profit: " + profits[i] + " to the min heap");


            minCapitalHeap.offer(i); // why `i` index and not capital[i] because we need to access both capital and profits arrays so we will store the index of the project in the heap
            log("Current min heap capital requirements: " + minCapitalHeap + "top capital requirement: " + minCapitalHeap.peek());

        }
        // step2: iterate for the number of projects we can select
        int availableCapital = initialCapital;
        for (int i=0; i<numberOfProjects; i++) {
            // step3: add all the projects that can be started with the current capital to the max heap
            while (!minCapitalHeap.isEmpty() && capital[minCapitalHeap.peek()] <= availableCapital) {
                log("Adding project with capital: " + capital[minCapitalHeap.peek()] + " and profit: " + profits[minCapitalHeap.peek()] + " to the max heap");
                maxProfitHeap.offer(minCapitalHeap.poll()); // add the project index to the max heap

            }
            // step4: if we have any project that can be started, select the project with the maximum profit

            if (maxProfitHeap.isEmpty()) {
                break; // we cannot start any project
            }
            availableCapital += profits[maxProfitHeap.poll()]; // add the profit of the selected project to the available capital

        }
        return availableCapital;


    }

    public static void main(String[] args) {
        P03MaximizeCapital maximizeCapital = new P03MaximizeCapital();
        int result = maximizeCapital.findMaximumCapital(new int[] {0, 1, 2}, new int[] {1, 2, 3}, 2, 1);
        System.out.println("Maximum capital: " + result);
        // another big example
        result = maximizeCapital.findMaximumCapital(new int[] {0, 1, 2, 3}, new int[] {1, 2, 3, 5}, 3, 0);
        System.out.println("Maximum capital: " + result);

        result = maximizeCapital.findMaximumCapital(new int[] {0, 1, 2, 3, 5}, new int[] {1, 2, 3, 5, 6}, 3, 4);
        System.out.println("Maximum capital: " + result);

        System.out.println("Another test case");
        result = maximizeCapital.findMaximumCapital(new int[] {0, 1, 2, 3}, new int[] {4, 3, 2, 1}, 3, 0);
        System.out.println("Maximum capital: " + result);


    }
}

