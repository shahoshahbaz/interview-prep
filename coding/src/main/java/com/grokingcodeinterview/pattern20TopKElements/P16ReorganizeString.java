package com.grokingcodeinterview.pattern20TopKElements;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/*
Problem Statement

Given a string, rearrange its characters so that no two adjacent characters are the same. If a valid rearrangement is not possible, return an empty string.

Example 1: Input: "aappp" Output: "papap"
Explanation: In "papap", no two adjacent characters are the same.

Example 2: Input: "Programming" Output: "rgmrgmiPnaor" (or any other valid rearrangement)

Explanation: All characters are separated such that no two same characters are adjacent.

Example 3: Input: "aapa" Output: ""

Explanation: In all arrangements of this string, at least two 'a's will come together, e.g. "apaa", "paaa". So no valid arrangement is possible.

Constraints

1 <= str.length <= 500
str consists of lowercase English letters
 */

/**
 * Example 1: Input: "aappp" Output: "papap"
 * minHeap = (a, 2) (p, 2)
 * minHeap.poll = (a, 2)
 * a
 */
public class P16ReorganizeString {

    public static class Record{
        char ch;
        int freq;

        public Record(char ch, int freq){
            this.ch = ch;
            this.freq = freq;
        }
    }
    public static String reorganizeString(String str) {
        StringBuilder result  = new StringBuilder();
        if (str == null) return "";

        HashMap<Character, Integer> freqMap = new HashMap<>();

        for(char ch: str.toCharArray()){
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) +1);
        }

        PriorityQueue<Record> maxHeap = new PriorityQueue<>((a, b)-> b.freq -a.freq);

        for(Map.Entry<Character, Integer> entry: freqMap.entrySet()){
            maxHeap.offer(new Record(entry.getKey(), entry.getValue()));
        }

        while (maxHeap.size() >=2) {
            Record first = maxHeap.poll();
            Record second = maxHeap.poll();

            result.append(first.ch);
            result.append(second.ch);

            first.freq--;
            second.freq--;

            if (first.freq > 0) maxHeap.offer(first);

            if (second.freq>0) maxHeap.offer(second);

        }


        while(!maxHeap.isEmpty()){
            Record last = maxHeap.poll();
            if(last.freq>1) return "";
            result.append(last.ch);
        }

     return result.toString();
    }
}

