package com.grokingcodeinterview.pattern22GreedyAlgorithms;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a string s, remove all duplicate letters from the input string  while maintaining the original order of the letters.
Additionally, the returned string should be the smallest in lexicographical order among all possible results.
A string is in the smallest lexicographical order
if it appears first in a dictionary. For example, "abc" is smaller than "acb" because "abc" comes first alphabetically.

Examples:
Input: "babac" Expected Output: "abc"
Justification:
After removing 1 b and 1 a from the input string, we can get bac, and abc strings.
The final answer is 'abc', which is the smallest lexicographical string without duplicate letters.
Input: "zabccde" Expected Output: "zabcde"
Justification: Removing one of the 'c's forms 'zabcde', the smallest string in lexicographical order without duplicates.
Input: "mnopmn" Expected Output: "mnop"
Justification: Removing the second 'm' and 'n' gives 'mnop', which is the smallest possible string without duplicate characters.
Constraints:
1 <= s.length <= 10^4
s consists of lowercase English letters.
 */

/**
 * very nice dy-example of greedy algorithm using stack
 * Constraints: removing duplicate(how? using a boolean array to track used chars), smallest lexicographical order(how? using stack to maintain order) + original order(how? using last occurrence index to check if char can appear later)
 * input:  m n o p m n
 * index: 0 1 2 3 4 5
 * lastIndex: lastIndex['m'] = 4, lastIndex['n'] = 5, lastIndex['o'] = 2, lastIndex['p'] = 3 // technically lastIndex = [ - , - , - , - , - , - , - , - , - , - , - , - , 4,5,2,3, - , - , - , - , - , - , - , - , - , - ]
 * stack: empty
 * i = 0...5
 * i = 0 c: 'm', index = 'm' - 'a' = 12 used[12] = false stack is empty push 'm' used[12]= true, stack: m
 * i = 1 c: 'n'  index = 'n' - 'a' = 13 used[13] = false stack not empty, top = 'm' and 'm' < 'n' -> false -> push 'n' used[13] = true stack: 'n' 'm'
 * i = 2 c: 'o'  index = 'o' - 'a' = 14 used[14] = false stack not empty, top = 'n' and 'n' < 'o' -> false -> push 'o' used[14] = true stack : 'o' 'n' 'm'
 * i = 3 c: 'p'  index = 'p' - 'a' = 15 used[15] = false stack not empty, top = 'o' and 'o' < 'p' -> false -> push 'p' used[15] = true stack : 'p' 'o' 'n' 'm'
 * i = 4 c: 'm'  index = 'm' - 'a' = 12 used[12] = true -> skip
 * i = 5 c: 'n'  index = 'n' - 'a' = 13 used[13] = true -> skip
 * final stack: 'p' 'o' 'n' 'm' -> reverse it to get 'm' 'n' 'o' 'p'
 * another example:
 * Input: b a b a c
 * Index: 0 1 2 3 4
 * lastIndex : lastIndex['a'] = 3, lastIndex['b'] = 2, lastIndex['c'] = 4 // technically lastIndex = [3,2,- ,- ,4, - , - , - , - , - , - , - , - , - , - , - , - , - , - , - , - , - , - , - , - ]
 * stack: empty
 * i = 0...4
 * i = 0 , c: 'b' index = 'b' -'a'  = 1 used[1] false, stack is empty push 'm' used[1] true, stack: 'b'
 * i = 1 , c: 'a' index = 'a' - 'a' = 0 used[0] false, stack is not empty, top: 'b' and 'b' > 'a' and lastIndex['b'] = 2 > 1 -> true pop 'b' used[1] = false stack is empty push 'a' used[0] = true stack: 'a'
 * i = 2 , c: 'b' index = 'b' - 'a' = 1 used[1] false, stack is not empty, top: 'a' and 'a' < 'b' -> false push 'b' used[1] = true stack: 'b' 'a'
 * i = 3 , c: 'a' index = 'a' - 'a' = 0 used[0] true -> skip
 * i = 4 , c: 'c' index = 'c' - 'a = 2 used[2] false, stack is not empty, top: 'b' and 'b' < 'c' -> false push 'c' used[2] = true stack: 'c' 'b' 'a'
 * final stack: 'c' 'b' 'a' -> reverse it to get 'a' 'b' 'c'
 */
public class P04RemoveDuplicateLetters {

    public static String removeDuplicateLetters(String s) {


        int[] lastIndex = new int[26]; // we have 26 chars
        boolean[] used = new boolean[26]; // to track if char is used in result

        // step1: Get Last occurrence index of each char

        for (int i=0; i< s.length(); i++){
            lastIndex[s.charAt(i)-'a'] = i;// s.charAt(i) - 'a' gives index from 0 to 25 for example, 'a' - 'a' =0, 'b' - 'a' =1....  and we store last occurrence index of each char
        }

        // step2: Use stack to build the result
        Deque<Character> stack = new ArrayDeque<>();

        for (int i =0; i< s.length(); i++){
            char c = s.charAt(i);
            int index = c -'a';

            // if char is already used, skip it
            if (used[index]) continue;
            // remove chars that are greater than current char and can appear later
            while (!stack.isEmpty()){
                char top = stack.peek();
                if (top>c && lastIndex[top -'a']> i){ // if top char is greater than current char(why? to maintain lexicographical order) and top char can appear later(why? to keep original order) then  pop it
                    used[top- 'a'] = false; // mark top char as unused
                    stack.pop(); // remove top char
                }else{
                    break;
                }

            }
            stack.push(c); // add current char to stack
            used[index] = true;

        }

        // build result from stack
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty())
            result.append(stack.pop());

        return result.reverse().toString(); // reverse the result to get correct order
    }

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("P04. Remove Duplicate Letters... ");
        System.out.println("===================================");
        String str04 = "babac";
        System.out.println("Input: " + str04 + ", output: " + makeItBold(removeDuplicateLetters(str04)) + " ,Expected Output: abc");
        str04 = "zabccde";
        System.out.println("Input: " + str04 + ", output: " + makeItBold(removeDuplicateLetters(str04)) + " ,Expected Output: zabcde");
        str04 = "mnopmn";
        System.out.println("Input: " + str04 + ", output: " + makeItBold(removeDuplicateLetters(str04)) + " ,Expected Output: mnop");
        str04 = "cbacdcbc";
        System.out.println("Input: " + str04 + ", output: " + makeItBold(removeDuplicateLetters(str04)) + " ,Expected Output: acdb");

    }
}

