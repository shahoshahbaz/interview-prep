package com.grokingcodeinterview.pattern11HashMaps;

import java.util.HashMap;
import java.util.Map;

import static com.Utility.makeItBold;

/*
 Problem Statement
Given two strings, one representing a ransom note and the other representing the available letters from a magazine, determine
if it's possible to construct the ransom note using only the letters from the magazine. Each letter from the magazine can be used only once.
Example 1: Input: Ransom Note = "hello", Magazine = "hellworld" Expected Output: true
Justification: The word "hello" can be constructed from the letters in "hellworld".
Example 2: Input: Ransom Note = "notes", Magazine = "stoned" Expected Output: true
Justification: The word "notes" can be fully constructed from "stoned" from its first 5 letters.
Example 3: Input: Ransom Note = "apple", Magazine = "pale" Expected Output: false
Justification: The word "apple" cannot be constructed from "pale" as we are missing one 'p'.
Constraints:

1 <= ransomNote.length, magazine.length <= 10^5
ransomNote and magazine consist of lowercase English letters.
 */
public class P05RansomNote {
    public static boolean canConstruct(String ransomNote, String magazine) {
        if (magazine == null || magazine.isEmpty()) return false;
        if (magazine.length()< ransomNote.length()) return false;

        Map<Character, Integer> magazineFreqMap  = new HashMap<>();

        for (char ch: magazine.toCharArray()){
            magazineFreqMap.put(ch, magazineFreqMap.getOrDefault(ch, 0) +1);
        }


        for(char ch: ransomNote.toCharArray()){
            if (!magazineFreqMap.containsKey(ch)){
                return false;
            }else{
                magazineFreqMap.put(ch, magazineFreqMap.get(ch) -1);
                if (magazineFreqMap.get(ch) ==0)
                    magazineFreqMap.remove(ch);
            }
        }
        return true;


    }
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("P05.Ransom Note");
        System.out.println("=====================================");

        String magazine = "hellworld";
        String ransomNote = "hello";

        System.out.println("Input: Ransom Note = \""+ ransomNote +"\", Magazine = \""+ magazine +"\" => Output: "+ makeItBold(canConstruct(ransomNote, magazine) +"") +" ,Expected Output: true");
        magazine = "stoned";
        ransomNote = "notes";
        System.out.println("Input: Ransom Note = \""+ ransomNote +"\", Magazine = \""+ magazine +"\" => Output: "+ makeItBold(canConstruct(ransomNote, magazine) +"") +" ,Expected Output: true");
        magazine = "pale";
        ransomNote = "apple";
        System.out.println("Input: Ransom Note = \""+ ransomNote +"\", Magazine = \""+ magazine +"\" => Output: "+ makeItBold(canConstruct(ransomNote, magazine) +"") +" ,Expected Output: false");
        magazine = "aabbcc";
        ransomNote = "abc";
        System.out.println("Input: Ransom Note = \""+ ransomNote +"\", Magazine = \""+ magazine +"\" => Output: "+ makeItBold(canConstruct(ransomNote, magazine) +"") +" ,Expected Output: true");
        magazine = "abc";
        ransomNote = "aabbcc";
        System.out.println("Input: Ransom Note = \""+ ransomNote +"\", Magazine = \""+ magazine +"\" => Output: "+ makeItBold(canConstruct(ransomNote, magazine) +"") +" ,Expected Output: false");


    }
}

