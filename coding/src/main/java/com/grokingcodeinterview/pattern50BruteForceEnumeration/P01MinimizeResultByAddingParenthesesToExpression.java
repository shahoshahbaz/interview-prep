package com.grokingcodeinterview.pattern50BruteForceEnumeration;

import static com.Utility.makeItBold;

/*
    Problem Statement:
    You are given a 0-indexed string expression of the form "<num1>+<num2>" where num1 and num2 represent positive integers.
    Add a pair of parentheses to expression such that after the addition of parentheses, expression is a valid mathematical expression
    and evaluates to the smallest possible value. The left parenthesis must be added to the left of '+' and the right parenthesis must
    be added to the right of '+'.
    Return expression after adding a pair of parentheses such that expression evaluates to the smallest possible value.
    It is guaranteed that the minimum value can always be achieved.

    Example 1: Input: expression = "247+38"  Output: "2(47+38)"
    Explanation: (47+38) = 85, then 2 * 85 = 170.
    Example 2: Input: expression = "12+34"   Output: "1(2+3)4"
    Explanation: (2+3) = 5, then 1 * 5 * 4 = 20.

    Constraints:
    3 <= expression.length <= 10
    expression consists of digits from '1' to '9' and '+'.
    expression starts and ends with digits.
    expression contains exactly one '+'.
    The original value of expression, and all possible values after adding one pair of parentheses lie in the range [1, 10^9].
 */
public class P01MinimizeResultByAddingParenthesesToExpression {

    public static  String minimizeResult(String expression){

        String[] parts = expression.split("\\+");
        String num1 = parts[0];
        String num2 = parts[1];
        int minResult = Integer.MAX_VALUE;
        String bestExp ="";
        for(int i =0; i< num1.length(); i++){
            for (int j =1; j<= num2.length(); j++ ){
                String prefix = num1.substring(0, i);
                String insideLeft = num1.substring(i);
                String insideRight = num2.substring(0, j);
                String suffix = num2.substring((j));
//                System.out.println(prefix + "(" + insideLeft + "+" + insideRight + ")" + suffix);

                // empty side means No Multiplire -> 1
                int leftMul = prefix.isEmpty()? 1:Integer.parseInt(prefix);
                int rightMul = suffix.isEmpty()? 1:Integer.parseInt(suffix);

                int sum = Integer.parseInt(insideLeft) + Integer.parseInt(insideRight);
                int value = leftMul *sum * rightMul;

                if (minResult> value){
                    minResult = value;
                    bestExp = prefix+"(" + insideLeft + "+" + insideRight +")" + suffix;

                }


            }
        }
        return bestExp;
    }

    public static void main(String[] args) {

        System.out.println("===========================");
        System.out.println("P13. Minimize Result by Adding Parentheses to Expression");
        System.out.println("===========================");

        // Test 1: prefix shrinks left part, giving smaller product
        String expr1 = "247+38";
        String result1 = minimizeResult(expr1);
        System.out.println("Input:  " + expr1 + " ,Output: " + makeItBold(result1) + " ,Expected: 2(47+38)");

        // Test 2: isolating a single digit on each outer side minimises
        String expr2 = "12+34";
        String result2 = minimizeResult(expr2);
        System.out.println("Input:  " + expr2 + " ,Output: " + makeItBold(result2) + " ,Expected: 1(2+3)4");

        // Test 3: both sides are single digits â€” only one placement possible
        String expr3 = "1+1";
        String result3 = minimizeResult(expr3);
        System.out.println("Input:  " + expr3 + " ,Output: " + makeItBold(result3) + " ,Expected: (1+1)");

        // Test 4: greedy should wrap all digits (no outer multipliers)
        String expr4 = "999+999";
        String result4 = minimizeResult(expr4);
        System.out.println("Input:  " + expr4 + " ,Output: " + makeItBold(result4) + " ,Expected: (999+999)");

        // Test 5: asymmetric lengths
        String expr5 = "9+99";
        String result5 = minimizeResult(expr5);
        System.out.println("Input:  " + expr5 + " ,Output: " + makeItBold(result5) + " ,Expected: (9+99)");
    }
}



