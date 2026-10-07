package com.grokingcodeinterview.testYourKnowledge47Medium;

import java.util.*;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a list of strings, the task is to group the anagrams together.

An anagram is a word or phrase formed by rearranging the letters of another, such as "cinema", formed from "iceman"

You can return the answer in any order.

Examples
Example 1:
Input: ["dog", "god", "hello"]
Output: [["dog", "god"], ["hello"]]
Justification: "dog" and "god" are anagrams, so they are grouped together. "hello" does not have any anagrams in the list, so it is in its own group.
Example 2:
Input: ["listen", "silent", "enlist"]
Output: [["listen", "silent", "enlist"]]
Justification: All three words are anagrams of each other, so they are grouped together.
Example 3:
Input: ["abc", "cab", "bca", "xyz", "zxy"]
Output: [["abc", "cab", "bca"], ["xyz", "zxy"]]
Justification: "abc", "cab", and "bca" are anagrams, as are "xyz" and "zxy".
Constraints:

1 <= strs.length <= 10^4
0 <= strs[i].length <= 100
strs[i] consists of lowercase English letters.
 */
public class P02GroupAnagrams {
        public static List<List<String>> groupAnagrams(String[] strs) {
            if (strs == null) return new ArrayList<>();
            Map<String, List<String>> map = new LinkedHashMap<>();

            for (String str: strs){
                char[] chars = str.toCharArray();
                Arrays.sort(chars);
                String sorted = new String(chars);
                map.putIfAbsent(sorted,new ArrayList<>());
                map.get(sorted).add(str);
                }


            return new ArrayList<>(map.values());



        }
        public static void main(String[] args) {
            System.out.println("=================================");
            System.out.println("P02.GroupAnagrams");
            System.out.println("=================================");

            String[] strP02 = {"dog", "god", "hello"};
            System.out.println("Input: " + Arrays.toString(strP02) +"output:" + makeItBold(groupAnagrams(strP02).toString()) +" expected: [[dog, god], [hello]]");
            strP02 = new String[]{"listen", "silent", "enlist"};
            System.out.println("Input: " + Arrays.toString(strP02) +"output:" + makeItBold(groupAnagrams(strP02).toString()) +" expected: [[listen, silent, enlist]]");
            strP02 = new String[]{"abc", "cab", "bca", "xyz", "zxy"};
            System.out.println("Input: " + Arrays.toString(strP02) +"output:" + makeItBold(groupAnagrams(strP02).toString()) +" expected: [[abc, cab, bca], [xyz, zxy]]");

        }
}

