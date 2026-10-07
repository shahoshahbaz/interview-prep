package com.grokingcodeinterview.testYourKnowledge47Medium;
/*
    Problem Statement
    You have a string that represents encodings of substrings, where each encoding is of the form k[encoded_string], where k is a positive integer, and encoded_string is a string that contains letters only.

    Your task is to decode this string by repeating the encoded_string k times and return it. It is given that k is always a positive integer.

    Examples
    Input: "3[a3[c]]"
    Expected Output: "acccacccaccc"
    Justification: The inner 3[c] is decoded as ccc, and then a is appended to the front, forming acc. This is then repeated 3 times to form acccacccaccc.
    Input: "2[b3[d]]"
    Expected Output: "bdddbddd"
    Justification: The inner 3[d] is decoded as ddd, and then b is appended to the front, forming bddd. This is then repeated 2 times to form bddd bddd.
    Input: "4[z]"
    Expected Output: "zzzz"
    Justification: The 4[z] is decoded as z repeated 4 times, forming zzzz.
    Constraints:

    1 <= s.length <= 30
    s consists of lowercase English letters, digits, and square brackets '[]'.
    s is guaranteed to be a valid input.
    All the integers in s are in the range [1, 300].
 */

import java.util.ArrayDeque;
import java.util.Deque;

import static com.Utility.makeItBold;

/**
 * example: 3[ab]
 *       start    -> current
 *     hit '3'    -> k = 3
 *     hit '['    -> save 3, save current ="", reset current
 *     hit 'a'    -> current = "a"
 *     hit 'b'    -> current = "ab"
 *     hit ']'    -> freq = 3(pop),  sb = pop() = "" ,
 *                          sb.append("ab") 3 times
 *                          sb = "ababab",
 *                          current = "ababab",
 *  return current.toString() -> "ababab"
 *
 *  example "3[a3[c]]"
 *      start    -> current
 *     hit '3'    -> k = 3
 *     hit '['    -> save 3, save current ="", reset current
 *     hit 'a'    -> current = "a"
 *     hit '3'    -> k = 3
 *     hit '['    -> save 3, save current ="a", reset current and k
 *     hit 'c'    -> current = "c"
 *     hit ']'    -> freq = 3(pop),  sb = pop() = "a" ,
 *                          sb.append("c") 3 times
 *                          sb = "accc",
 *                          current = "accc",
 *     hit ']'    -> freq = 3(pop),  sb = pop() = "" ,
 *                          sb.append("accc") 3 times
 *                          sb = "acccacccaccc",
 *                          current = "acccacccaccc",
 *  return current.toString() -> "acccacccaccc"
 */
public class P03DecodeString {
    public static String decodeString(String str) {
        Deque<Integer> countStack = new ArrayDeque<>();
        Deque<StringBuilder> stringStack = new ArrayDeque<>();

        int k = 0;
        StringBuilder inner = new StringBuilder();

        for (char ch: str.toCharArray()){

            if (Character.isDigit(ch)){
                k = k* 10 + (ch -'0');
            }else if (ch =='['){
                countStack.push(k);
                k =0;
                stringStack.push(inner);
                inner = new StringBuilder();


            }else if(ch ==']'){

                int freq = countStack.pop();
                StringBuilder outer = stringStack.pop();
                outer.append(inner.toString().repeat(freq));

                inner = outer;



            }else{
                inner.append(ch);
            }
        }

        return inner.toString();

    }

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("P03.DecodeString");
        System.out.println("=================================");
        String strP03 = "3[a3[c]]";
        System.out.println("Input: " + strP03 +"output:" +makeItBold( decodeString(strP03)) +" expected: acccacccaccc");
        strP03 = "2[b3[d]]";
        System.out.println("Input: " + strP03 +"output:" + makeItBold(decodeString(strP03)) +" expected: bdddbddd");
        strP03 = "4[z]";
        System.out.println("Input: " + strP03 +"output:" + makeItBold(decodeString(strP03)) +" expected: zzzz");
         strP03 = "10[a]";
        System.out.println("Input: " + strP03 +"output:" + makeItBold(decodeString(strP03)) +" expected: aaaaaaaaaa");
         strP03 = "2[abc]3[cd]ef";
        System.out.println("Input: " + strP03 +"output:" + makeItBold(decodeString(strP03)) +" expected: abcabccdcdcdef");
    }
}

