package com.grokingcodeinterview.pattern20TopKElements;

/*
Problem Statement
Given a string, sort it based on the decreasing frequency of its characters.

Example 1:

Input: "Programming"
Output: "rrggmmPiano"
Explanation: 'r', 'g', and 'm' appeared twice, so they need to appear before any other character.
Example 2:

Input: "abcbab"
Output: "bbbaac"
Explanation: 'b' appeared three times, 'a' appeared twice, and 'c' appeared only once.
Constraints:

1 <= str.length <= 5 * 105
str consists of uppercase and lowercase English letters and digits.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;

/**
 * Input: "Programming"  * Output: "rrggmmPiano"
 * map (p, 1), (r, 2) (o, 1) (g, 2)(a, 1) (m, 2) (i, 1)
 * then use min Heap the poll the record one by one and create String
 */
public class P06FrequencySort {

    public  static String sortCharacterByFrequency(String str) {

        if (str.length() <=1) return str;

        HashMap<Character, Integer> freqMap = new HashMap<>();

        PriorityQueue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>((a, b) ->Integer.compare(b.getValue(), a.getValue()));


        for (char ch: str.toCharArray()){

            freqMap.put(ch, freqMap.getOrDefault(ch, 0) +1);

        }

        maxHeap.addAll(freqMap.entrySet());

         StringBuilder sb = new StringBuilder();
        while(!maxHeap.isEmpty()) {
            Map.Entry<Character, Integer> entry = maxHeap.poll();

            for (int i =0; i< entry.getValue(); i++){
                sb.append(entry.getKey());
            }
        }

        return sb.toString();
    }
    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("P06. Frequency Sort");
        System.out.println("=======================================");

        String str06 = "Programming";
        System.out.println("Iput: " + makeItBold(str06) + ", Sort chars by frequnency :" +makeItBold(sortCharacterByFrequency(str06))+" Expected: rrggmmPiano");
         str06 = "abcbab";
        System.out.println("Iput: " + makeItBold(str06) + ", Sort chars by frequnency :" +makeItBold(sortCharacterByFrequency(str06))+" Expected: bbbaac");
         str06 = "tree";
        System.out.println("Iput: " + makeItBold(str06) + ", Sort chars by frequnency :" +makeItBold(sortCharacterByFrequency(str06))+" Expected: eert");
         str06 = "Aabb";
        System.out.println("Iput: " + makeItBold(str06) + ", Sort chars by frequnency :" +makeItBold(sortCharacterByFrequency(str06))+" Expected: bbAa");




    }
}

