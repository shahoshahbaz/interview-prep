package com.grokingcodeinterview.testYourKnowledge46Easy;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static com.Utility.makeItBold;

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
 * {[]}
 * stack:
 */
public class P04ValidParentheses {
    public static boolean isValid(String str){
        if(str == null) return true;
        if(str.length() %2 == 1) return false;
        Deque<Character> stack = new ArrayDeque<>();

        Map<Character,Character> map = Map.of(')', '(','}', '{',']', '[' );

        for (Character ch: str.toCharArray()){
            if (!map.containsKey(ch)){
                stack.push(ch);
            }else if( stack.isEmpty() || stack.peek() != map.get(ch)){
                return false;
            }else{
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

    }



}

