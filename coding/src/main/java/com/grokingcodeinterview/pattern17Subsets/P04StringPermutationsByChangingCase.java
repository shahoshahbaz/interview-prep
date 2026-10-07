package com.grokingcodeinterview.pattern17Subsets;

import java.util.ArrayList;
import java.util.List;

/*
Given a string, find all of its permutations preserving the character sequence but changing case.

Example 1:

Input: "ad52"
Output: "ad52", "Ad52", "aD52", "AD52"
Example 2:

Input: "ab7c"
Output: "ab7c", "Ab7c", "aB7c", "AB7c", "ab7C", "Ab7C", "aB7C", "AB7C"
Constraints:

1 <= str.length <= 12
str consists of lowercase English letters, uppercase English letters, and digits.
 */

/**
 * this is a backtracking problem and it is BFS type problem
 *  so the tree will be like this for input "a1b"
 *                   ""
 *                /       \
 *              a          A
 *             / \        /  \
 *           a1   A1    a1    A1
 *          / \    / \   / \    / \
 *        a1b A1b a1B A1B a1b A1B A1b A1B
 */
public class P04StringPermutationsByChangingCase {
    public static  List<String> findLetterCaseStringPermutations(String str) {
        List<String> result = new ArrayList<>();
        backtrack(str.toCharArray(), 0, new StringBuilder(), result);
        return result;
    }

    public static void backtrack(char[] chars, int index, StringBuilder current, List<String> result){

        // Base case: when we've processed the entire String
        if (index == chars.length){
            result.add(current.toString());
            return;
        }

        char ch = chars[index];

        if(Character.isLetter(ch)) {
            // lower case
            current.append(Character.toLowerCase(ch)); // why we append it to current, because we are building the current permutation
            backtrack(chars, index + 1, current, result); // now we move to the next index
            current.deleteCharAt(current.length() - 1); // backtrack, remove the last character added

            // upper case
            current.append(Character.toUpperCase(ch)); // why we append it to current, because we are building the current permutation
            backtrack(chars, index + 1, current, result); // now we move to the next index
            current.deleteCharAt(current.length() - 1); // backtrack, remove the last character added
        }else{
            current.append(ch); // if it's a digit, just append it
            backtrack(chars, index +1, current, result); //move to next index
            current.deleteCharAt(current.length() -1); // backtrack
        }
    }

    public static void main(String[] args) {
        System.out.println("Input: ad52");
        System.out.println("Output: " + findLetterCaseStringPermutations("ad52"));

        System.out.println("\nInput: ab7c");
        System.out.println("Output: " + findLetterCaseStringPermutations("ab7c"));
    }
}

