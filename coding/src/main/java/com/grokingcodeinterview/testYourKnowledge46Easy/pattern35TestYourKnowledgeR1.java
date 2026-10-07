package com.grokingcodeinterview.testYourKnowledge46Easy;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Map;

import static com.Utility.makeItBold;

public class pattern35TestYourKnowledgeR1 {
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

    /**
     * [3, 2 , 6, 5, 0l, 3r]
     * maxProfit =4
     * maxprofit = 1; l++
     * maxprofit = -2
     * r
     */

    public static int maxProfitTwoPointer(int[] prices){
        int left =0;
        int right = 1;
        int maxProfit = 0;

        while(right< prices.length){
            if (prices[left] < prices[right]){
                maxProfit = Math.max(maxProfit, prices[right] - prices[left]);
            }else{
                left = right;
            }
            right ++;
        }
        return maxProfit;


    }

    /*
Problem Statement
Determine if an input string containing only the characters '(', ')', '{', '}', '[', and ']' is valid. A string is considered valid if:

Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.
Each close bracket has a corresponding open bracket of the same type.
Examples
Input: "(]"
Expected Output: false
Justification: The opening parenthesis '(' is not closed by its corresponding closing parenthesis.
Input: "{[]}"
Expected Output: true
Justification: The string contains pairs of opening and closing brackets in the correct order.
Input: "[{]}"
Expected Output: false
Justification: The opening square bracket '[' is closed by a curly brace '}', which is incorrect.
Constraints:

1 <= s.length <= 10^4
s consists of parentheses only '()[]{}'.
 */

    /**
     * [{]} stack : [{
     */
    public static boolean isValid(String str){
        Map<Character, Character> map = Map.of('}','{', ')','(',']','[');
        Deque<Character> stack = new ArrayDeque<>();

        for (char ch: str.toCharArray()){
            if (!map.containsKey(ch)){
                stack.push(ch);
            }else {
                if (stack.isEmpty() || stack.peek() != map.get(ch) ) return false;
                stack.pop();

            }
        }

        return stack.isEmpty();
    }
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("P04.ValidParentheses");
        System.out.println("=================================");
        String strP04 = "(]";
        boolean resultP04 = isValid(strP04);
        System.out.println("Input: \"" + strP04 + "\" is valid? " + makeItBold(resultP04 +"") + " Expected: " + "false");
        strP04 = "{[]}";
        resultP04 = isValid(strP04);
        System.out.println("Input: \"" + strP04 + "\" is valid? "+ makeItBold(resultP04 +"") + " Expected: " + "true");
        strP04 = "[{]}";
        resultP04 = isValid(strP04);
        System.out.println("Input: \"" + strP04 + "\" is valid? "+ makeItBold(resultP04 +"") + " Expected: " + "false");
        strP04 = "((()))";
        resultP04 = isValid(strP04);
        System.out.println("Input: \"" + strP04 + "\" is valid? "+ makeItBold(resultP04 +"") + " Expected: " + "true");
        strP04 = "((())";
        resultP04 = isValid(strP04);
        System.out.println("Input: \"" + strP04 + "\" is valid? "+ makeItBold(resultP04 +"") + " Expected: " + "false");
        strP04 = "({[]})";
        resultP04 = isValid(strP04);
        System.out.println("Input: \"" + strP04 + "\" is valid? "+ makeItBold(resultP04 +"") + " Expected: " + "true");


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
        System.out.println("Input: " + Arrays.toString(pricesP03) +"output:" + makeItBold(maxProfitTwoPointer(pricesP03) +"") +" expected: 4");
    }
}

