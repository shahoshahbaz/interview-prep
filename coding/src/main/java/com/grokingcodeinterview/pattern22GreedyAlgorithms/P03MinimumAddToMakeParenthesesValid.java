package com.grokingcodeinterview.pattern22GreedyAlgorithms;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a string str containing '(' and ')' characters,
find the minimum number of parentheses that need to be added to a string of parentheses to make it valid.
A valid string of parentheses is one
where each opening parenthesis '(' has a corresponding closing parenthesis ')' and vice versa. The goal is to determine the least amount of additions needed to achieve this balance.

Example 1: Input: "(()" Expected Output: 1
Justification: The string has two opening parentheses and one closing parenthesis. Adding one closing parenthesis at the end will balance it.
Example 2: Input: "))(("  Expected Output: 4
Justification: There are two closing parentheses at the beginning and two opening at the end. We need two opening parentheses
before the first closing and two closing parentheses after the last opening to balance the string.
Example 3: Input: "(()())(" Expected Output: 1
Justification: The string has three opening parentheses and three closing parentheses, with an additional opening parenthesis at the end. Adding one closing parenthesis at the end will balance it.
Constraints:
1 <= s.length <= 1000
s[i] is either '(' or ')'.
 */
public class P03MinimumAddToMakeParenthesesValid {
    /**
     *   Input: "))(("  Expected Output: 4
     *   balance: b
     *   counter : c
     *   ) b: -1  => counter = 1 and b = 0
     *   ) b = -1 => counter = 2 and b =0
     *   ( b = 1
     *   ( b =2
     *   return counter + b
     *
     *   Input ="())(()" expected outPut L 2
     *   ( b = 1
     *   ) b =0
     *   ) b = -1 => counter = 1, b = 0
     *   (  b =1
     *   ( b =2
     *   ) b =1,
     *   return b+ counter = 1+1 = 2
     *
     *   Input "))))))" outPut = 6
     *   b= -1 coutner =1, b =0
     *   b= -1 coutner =2, b =0
     *   b= -1 coutner =3, b =0
     *   b= -1 coutner =4, b =0
     *   b= -1 coutner =5, b =0
     *   b= -1 coutner =6, b =0
     *   return 6 + 0;
     *
     *
     *
     */
    public  static int minAddToMakeValid(String str) {

        int missingOpen = 0; // '(' we must add
        int missingClose = 0; // ')' we must add

        for (char ch : str.toCharArray()) {

            if (ch == '(') {
                missingClose++; // need one more closing parenthesis so missingClose++
            } else { // ch == ')'
                if (missingClose > 0) { //there is a matching opening parenthesis
                    missingClose--; // then we reduce the count of missing closing parenthesis
                } else { // there is no matching opening parenthesis
                    missingOpen++; // we need an extra opening parenthesis
                }
            }


        }
        return missingOpen + missingClose;
    }

    public static void main(String[] args) {


        System.out.println("==============================================================");
        System.out.println(makeItBold("P03. Minimum Add To Make Parentheses Valid"));
        System.out.println("==============================================================");
        String strP03 = "(()";
        System.out.println("Input: " + makeItBold(strP03) + " Expected Output: "+ makeItBold("1") + " Actual Output: " + makeItBold(minAddToMakeValid(strP03) + ""));

        strP03 = "))((";
        System.out.println("Input: " + makeItBold(strP03) + " Expected Output: "+ makeItBold("4") + " Actual Output: " + makeItBold(minAddToMakeValid(strP03) + ""));

        strP03 = "(()())(";
        System.out.println("Input: " + makeItBold(strP03) + " Expected Output: "+ makeItBold("1") + " Actual Output: " + makeItBold(minAddToMakeValid(strP03) + ""));

        strP03 = "())(()";
        System.out.println("Input: " + makeItBold(strP03) + " Expected Output: "+ makeItBold("2") + " Actual Output: " + makeItBold(minAddToMakeValid(strP03) + ""));


    }
}

