package com.grokingcodeinterview.testYourKnowledge46Easy;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Problem Statement
You are given an array prices where prices[i] is the price of a given stock on the  day.

You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.

Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.

Examples
Input: [3, 2, 6, 5, 0, 3]
Expected Output: 4
Justification: Buy the stock on day 2 (price = 2) and sell it on day 3 (price = 6). Profit = 6 - 2 = 4.
Input: [8, 6, 5, 2, 1]
Expected Output: 0
Justification: Prices are continuously dropping, so no profit can be made.
Input: [1, 2]
Expected Output: 1
Justification: Buy on day 1 (price = 1) and sell on day 2 (price = 2). Profit = 2 - 1 = 1.
Constraints:

1 <= prices.length <= 105
0 <= prices[i] <= 104
 */
public class P03BestTimeToBuyAndSell {
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        int  minPrice = prices[0];
        int maxProfit = 0;
        for (int i =1; i<prices.length; i++){
            if(prices[i]< minPrice){
                minPrice = prices[i];
            }
            maxProfit = Math.max(maxProfit, prices[i]- minPrice);
        }
        return maxProfit;
    }

    public static int maxProfitTwoPointer(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        int left = 0;
        int right = 1;
        int maxProfit = 0;
        while (right < prices.length){
            if(prices[left] < prices[right]){
                maxProfit = Math.max(maxProfit, prices[right] - prices[left]);
            }else{
                left = right;
            }
            right++;
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("P03.BestTimeToBuyAndSell");
        System.out.println("=================================");
        int[] pricesP03 = {3, 2, 6, 5, 0, 3};
        System.out.println("Input: " + Arrays.toString(pricesP03) +"output:" + makeItBold(maxProfitTwoPointer(pricesP03) +"") +" expected: 4");
        pricesP03 = new int[]{8, 6, 5, 2, 1};
        System.out.println("Input: " + Arrays.toString(pricesP03) +"output:" + makeItBold(maxProfitTwoPointer(pricesP03) +"") +" expected: 0");
        pricesP03 = new int[]{1, 2};
        System.out.println("Input: " + Arrays.toString(pricesP03) +"output:" + makeItBold(maxProfitTwoPointer(pricesP03) +"") +" expected: 1");
        pricesP03 = new int[]{1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(pricesP03) +"output:" + makeItBold(maxProfit(pricesP03) +"") +" expected: 4");

    }
}

