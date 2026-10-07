package com.grokingcodeinterview.pattern11HashMaps;

/*
Problem Statement
Given a string, identify the position of the first character that appears only once in the string.
 If no such character exists, return -1.

Examples
Example 1: Input: "apple" Expected Output: 0
Justification: The first character 'a' appears only once in the string and is the first character.
Example 2: Input: "abcab" Expected Output: 2
Justification: The first character that appears only once is 'c' and its position is 2.
Example 3: Input: "abab" Expected Output: -1
Justification: There is no character in the string that appears only once.
Constraints:

1 <= s.length <= 10^5
s consists of only lowercase English letters.
 */

import java.util.HashMap;
import java.util.Map;

import static com.Utility.makeItBold;

/**
 *  Input: "apple" Expected Output: 0
 *  a:(0,1), p:(1,2), l:(2, 1), e =(3, 1)
 */
public class P01FirstNonRepeatingCharacter
{

    public  static int firstUniqueChar(String str){
        Map<Character,Integer> freqMap = new HashMap<>();

        for (char ch: str.toCharArray()){

            freqMap.put(ch, freqMap.getOrDefault(ch, 0) +1 );

        }

        for (int i =0; i< str.length();i++){
            if (freqMap.get(str.charAt(i) ) ==1){
                return i;
            }
        }

        return -1;

}
    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("P01. First Non Repeating Character... ");
        System.out.println("===================================");


        System.out.println("Input:apple, Output: " +makeItBold(firstUniqueChar("apple") +"") + ", expected: 0");
        System.out.println("Input:abcab, Output: " +makeItBold(firstUniqueChar("abcab") +"") + ", expected: 2");
        System.out.println("Input:abab, Output: " +makeItBold(firstUniqueChar("abab") +"") + ", expected: -1");
        System.out.println("Input:aabbccdde, Output: " +makeItBold(firstUniqueChar("aabbccdde") +"") + ", expected: 8");
        System.out.println("Input:zzxyyx, Output: " +makeItBold(firstUniqueChar("zzxyyx") +"") + ", expected: -1");
        
    }
}

