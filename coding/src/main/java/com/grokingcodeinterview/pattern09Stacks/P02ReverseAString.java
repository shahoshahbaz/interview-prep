package com.grokingcodeinterview.pattern09Stacks;

import java.util.Stack;

import static com.Utility.makeItBold;

/*
 * Given a string, write a function that uses a stack to reverse the string. The function should return the reversed string.
 * Examples
 * Example 1: Input: "Hello, World!" Output: "!dlroW ,olleH"
 * Example 2: Input: "OpenAI" Output: "IAnepO"
 * Example 3: Inut: "Stacks are fun!" Output: "!nuf era skcatS"
 */
public class P02ReverseAString {

    public static String reverseString(String str){
        if (str== null || str.length() <=1) return str;

        Stack<Character> stack = new Stack<>();
        for (char c: str.toCharArray() ){
            stack.push(c);
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()){
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("P02.ReverseAString");
        System.out.println("=================================");
        // Test cases
        String strP02 = "Hello, World!";
        String resultP02 = reverseString(strP02);
        System.out.println("Input: \"" + strP02 + "\" Reversed: \"" + makeItBold(resultP02) + "\" Expected: " + makeItBold("!dlroW ,olleH"));
        strP02 = "OpenAI";
        resultP02 = reverseString(strP02);
        System.out.println("Input: \"" + strP02 + "\" Reversed: \"" + makeItBold(resultP02) + "\" Expected: " + makeItBold("IAnepO"));
        strP02 = "Stacks are fun!";
        resultP02 = reverseString(strP02);
        System.out.println("Input: \"" + strP02 + "\" Reversed: \"" +  makeItBold(resultP02) + "\" Expected: " + makeItBold("!nuf era skcatS"));



    }
}

