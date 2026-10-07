package com.grokingcodeinterview.pattern10MonotonicStack;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.Utility.makeItBold;

/*
Problem Statement
You are given a string s and an integer k. Your task is to remove groups of identical, consecutive characters from
 the string such that each group has exactly k characters.
  The removal of groups should continue until it's no longer possible to make any more removals.
  The result should be the final version of the string after all possible removals have been made.

Examples
Input: s = "abbbaaca", k = 3 Output: "ca"
Explanation: First, we remove "bbb" to get "aaaca". Then, we remove "aaa" to get "ca".
Input: s = "abbaccaa", k = 3 Output: "abbaccaa"
Explanation: There are no instances of 3 adjacent characters being the same.
Input: s = "abbacccaa", k = 3 Output: "abb"
Explanation: First, we remove "ccc" to get "abbaaa". Then, we remove "aaa" to get "abb".
Constraints:
1 <= s.length <= 10^55
2 <= k <= 10&44
s only contains lowercase English letters.
 */

/**
 * Input: s = "abbbaaca", k = 3 Output: "ca"
 * [(a, 1)(b,2)
 */
public class P05RemoveAllAdjacentDuplicatesInString {
    static class Pair {
        char ch;
        int counter;
        public Pair(char ch, int counter){
            this.ch = ch;
            this.counter = counter;
        }
    }

    public static String removeDuplicates(String s, int k) {

        Deque<Pair> stack = new ArrayDeque<>();

        for (char ch : s.toCharArray()) {

            if (stack.isEmpty() || stack.peek().ch != ch) {
                stack.push(new Pair(ch, 1));
            } else if (stack.peek().counter == k - 1) {
                stack.pop(); // remove the group
            } else {
                stack.peek().counter++; // just increment
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            Pair p = stack.pop();
            for (int i = 0; i < p.counter; i++) {
                sb.append(p.ch);
            }
        }

        return sb.reverse().toString();
    }
    public static void main(String[] args) {

        System.out.println("=====================================================");
        System.out.println("P05. Remove All Adjacent Duplicates In String");
        System.out.println("====================================================");
        String strP05 = "abbbaaca";
        int kP05 = 3;

        String resultP05 = removeDuplicates(strP05, kP05);
        System.out.print("Input: " + strP05 + ", k= " + kP05 + " => Output: " +makeItBold( resultP05) + ", Expected: ca");
        strP05 = "abbaccaa";
        kP05 = 3;
        resultP05 = removeDuplicates(strP05, kP05);
        System.out.print("\nInput: " + strP05 + ", k= " + kP05 + " => Output: " +makeItBold( resultP05) + ", Expected: abbaccaa");
        strP05 = "abbacccaa";
        kP05 = 3;
        resultP05 = removeDuplicates(strP05, kP05);
        System.out.print("\nInput: " + strP05 + ", k= " + kP05 + " => Output: " +makeItBold( resultP05) + ", Expected: abb");


    }

}

