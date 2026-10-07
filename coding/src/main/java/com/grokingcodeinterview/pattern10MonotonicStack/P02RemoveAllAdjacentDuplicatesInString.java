package com.grokingcodeinterview.pattern10MonotonicStack;

/*
Problem Statement
You are given a string s consisting of lowercase English letters.
 A duplicate removal consists of choosing two adjacent and equal letters and removing them.
 We repeatedly make duplicate removals on s until we no longer can.Return the final string after all such duplicate removals have been made.

Examples Input: s = "abccba"  Output: ""
Explanation: First, we remove "cc" to get "abba". Then, we remove "bb" to get "aa". Finally, we remove "aa" to get an empty string.

Input: s = "foobar" Output: "fbar" Explanation: We remove "oo" to get "fbar".
Input: s = "fooobar" Output: "fobar"
Explanation: We remove the pair "oo" to get "fobar".
Input: s = "abcd" Output: "abcd"
Explanation: No adjacent duplicates so no changes.
Constraints:
1 <= s.length <= 10^55
s consists of lowercase English letters.
*/

import java.util.ArrayDeque;
import java.util.Deque;

import static com.Utility.makeItBold;

/**
 * Input: s = "abccba"  Output: ""
 * a
 */
public class P02RemoveAllAdjacentDuplicatesInString {

    public static String removeAllAdjacentDuplicate(String str){

        Deque<Character> stack = new ArrayDeque<>();

        for (char ch: str.toCharArray()){
            if (!stack.isEmpty() && stack.peek() == ch){
                stack.pop();
            }else{
                stack.push(ch);
            }
        }

        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()){
            sb.append(stack.pop());
        }

        return sb.reverse().toString();

    }
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("P02.RemoveAllAdjacentDuplicatesInString");
        System.out.println("==============================================");
        String inputP02 = "abccba";
        System.out.println("Input: " + inputP02 + ",  output: " + makeItBold(removeAllAdjacentDuplicate(inputP02)) + ", Expected: \"\"");

        inputP02 = "foobar";
        System.out.println("Input: " + inputP02 + ",  output: " + makeItBold(removeAllAdjacentDuplicate(inputP02)) + ", Expected: \"fbar\"");

        inputP02 = "fooobar";
        System.out.println("Input: " + inputP02 + ",  output: " + makeItBold(removeAllAdjacentDuplicate(inputP02)) + ", Expected: \"fobar\"");

        inputP02 = "abcd";
        System.out.println("Input: " + inputP02 + ",  output: " + makeItBold(removeAllAdjacentDuplicate(inputP02)) + ", Expected: \"abcd\"");



    }
}

