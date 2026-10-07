package com.grokingcodeinterview.pattern09Stacks;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static com.Utility.makeItBold;

/*
 Given a string s containing (, ), [, ], {, and } characters. Determine if a given string of parentheses is balanced.
  A string of parentheses is considered balanced if every opening parenthesis has a corresponding closing parenthesis in the correct order.
  Example 1: Input: String s = "{[()]}"; Expected Output: true
  Example 2: Input: string s = "{[}]"  Expected Output: false
 */
public class P01BalancedParentheses {

    public static  boolean isParenthesesBalanced(String str){
        if (str == null ) return true;
        if(str.length() %2 == 0) return false;


        Deque<Character> stack = new ArrayDeque<>();
        // Map to hold the corresponding opening parentheses for each closing parentheses
        // key is closing parentheses and value is opening parentheses
        Map<Character, Character> map = Map.of('}','{', ']','[', ')','(');

        for (char c: str.toCharArray()) {
            if (map.containsKey(c)){ // closing parentheses
                if(stack.isEmpty() || stack.pop() != map.get(c)) return false;
            }else{
                stack.push(c);//opening parentheses
            }

        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("P01.BalancedParentheses");
        System.out.println("=================================");

        // add more test cased

        String strP01 = "{[()]}";
        boolean resultP01 = isParenthesesBalanced(strP01);
        System.out.println("Input: \"" + strP01 + "\" is balanced? " + makeItBold(resultP01 +"") + "Expected: " + "true"); // Expected: true

        strP01 = "{[}]";
        resultP01 = isParenthesesBalanced(strP01);

        System.out.println("Input: \"" + strP01 + "\" is balanced? " + makeItBold(resultP01 +"") + " Expected: " + "false");

        strP01 = "((()))";
        resultP01 = isParenthesesBalanced(strP01);
        System.out.println("Input: \"" + strP01 + "\" is balanced? " + makeItBold(resultP01 +"") + " Expected: " + "true");

        // Additional Test Cases
         strP01 = "(a + b) * (c + d)";
        boolean resultP02 = isParenthesesBalanced(strP01);
        System.out.println("Input: \"" + strP01 + "\" is balanced? " + makeItBold(resultP02 +"") + " Expected: " + "true");

        strP01 = "((a + b)";
        resultP01 = isParenthesesBalanced(strP01);
        System.out.println("Input: \"" + strP01 + "\" is balanced? " + makeItBold(resultP01 +"") + " Expected: " + "false");





    }
}

