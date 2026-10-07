package com.grokingcodeinterview.pattern26Backtracking;

import java.util.ArrayList;
import java.util.List;

import static com.Utility.makeItBold;

    /*
    Problem Statement
    Numbers can be regarded as the product of their factors.For example, 8 = 2 x 2 x 2 = 2 x 4.
    Given an integer n, return all possible combinations of its factors. You may return the answer in any order.
    Example 1: Input: n = 8  Output: [[2, 2, 2], [2, 4]]
    Example 2: Input: n = 20   Output: [[2, 2, 5], [2, 10], [4, 5]]
    Constraints:
    2 <= n <= 10^7
     */
public class P03FactorCombinations {
    public static List<List<Integer>> getFactors(int n){
        return getAllFactors(n, 2, new ArrayList<>(), new ArrayList<List<Integer>>());
    }
    private static List<List<Integer>> getAllFactors(int num, int start , List<Integer> curr, List<List<Integer>> result){
        // why the loop should include(= (int)Math.sqrt(num))?
        // because we are looking for the factor pairs of num,
        // and the factor pairs of num will always be less than or equal
        // to the square root of num,
        // for example, the factor pairs of 8 are (2,4) and (4,2),
        // and the square root of 8 is 2.83,
        // so we only need to loop until 2.83 to find all the factor pairs of 8.
        for (int i = start; i<= (int)Math.sqrt(num); i++ ){

            if ( num % i == 0){
                curr.add(i);
                // why we have to creat currentCopy? because
                // we are going to change the current list by adding and removing elements,
                // why we need to add num/i to the current list? because we need to add the factor pair of i, which is num/i

                List<Integer> currCopy = new ArrayList<>(curr);
                currCopy.add( num / i);
                result.add(currCopy);
                // why num/i in below recursive call?
                // because we are going to find the factors of num/i, which is the other factor of i,
                // and we need to find the factors of num/i to find the factor combinations of num.
                getAllFactors(num/ i, i, curr, result);

                curr.remove(curr.size() -1);

            }

        }
        return result;
    }
    public static void main(String[] args) {

        System.out.println("==============================");
        System.out.println("P03. Factor Combinations");
        System.out.println("==============================");
        int numberP03 = 8;
        System.out.println("Input: n = " + numberP03 + "   Output: " + makeItBold(getFactors(numberP03).toString()) +"   Expected: [[2, 2, 2], [2, 4]]");
        numberP03 = 20;
        System.out.println("Input: n = " + numberP03 + "   Output: " + makeItBold(getFactors(numberP03).toString()) +"   Expected: [[2, 2, 5], [2, 10], [4, 5]]");
        numberP03 = 32;
        System.out.println("Input: n = " + numberP03 + "   Output: " + makeItBold(getFactors(numberP03).toString()) +"   Expected: [[2, 2, 2, 2, 2], [2, 2, 2, 4], [2, 2, 8], [2, 4, 4], [2, 16], [4, 8]]");
        numberP03 = 37;
        System.out.println("Input: n = " + numberP03 + "   Output: " + makeItBold(getFactors(numberP03).toString()) +"   Expected: []");
        numberP03 = 100;
        System.out.println("Input: n = " + numberP03 + "   Output: " + makeItBold(getFactors(numberP03).toString()) +" " +
                "  Expected: [[2, 2, 5, 5], [2, 2, 25], [2, 2, 25], [2, 5, 10], [2, 50], [4, 5, 5], [4, 25], [5, 20], [10, 10]]");



    }
}

