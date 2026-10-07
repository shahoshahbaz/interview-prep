package com.grokingcodeinterview.pattern02Warmup;

import java.util.HashMap;
import java.util.HashSet;

/*
 * Problem Statement
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 *
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, using all the original letters exactly once.
 *
 * Example 1:
 *
 * Input: s = "listen", t = "silent"
 * Output: true
 * Example 2:
 *
 * Input: s = "rat", t = "car"
 * Output: false
 * Example 3:
 *
 * Input: s = "hello", t = "world"
 * Output: false
 * Constraints:
 *
 * 1 <= s.length, t.length <= 5 * 104
 * s and t consist of lowercase English letters.
 */
public class P05ValidAnagram {

    public static boolean   isAnagram(String s, String t) {
        // edge case
        if (s == null || t == null || s.length() != t.length())
            return false;
        HashMap<Character, Integer> frequencyChars = new HashMap<>();

        for (char ch: s.toCharArray()){
            frequencyChars.put(ch, frequencyChars.getOrDefault(ch, 0) +1);

        }

        for (char ch: t.toCharArray()){
            if (frequencyChars.containsKey(ch)){
                frequencyChars.put(ch, frequencyChars.get(ch) -1);
                if (frequencyChars.get(ch) ==0)
                    // remove the ch
                    frequencyChars.remove(ch);
            }else{
                return false;
            }

        }
         return frequencyChars.size() ==0;
    }

    public static void main(String[] args) {

        String s1 = "listen";
        String t1 = "silent";
        System.out.println("Is Anagram: " + isAnagram(s1, t1)); // true

        String s2 = "rat";
        String t2 = "car";
        System.out.println("Is Anagram: " +isAnagram(s2, t2)); // false

        String s3 = "hello";
        String t3 = "world";
        System.out.println("Is Anagram: " +isAnagram(s3, t3)); // false
    }
}
