package com.grokingcodeinterview.pattern05SlidingWindow;

import java.util.Arrays;
import java.util.HashMap;

import static com.Utility.makeItBold;

/*
 Problem statement
 You are visiting a farm to collect fruits.  The farm has a single row of fruit trees.
 You will be given two baskets, and your goal is to pick as many fruits as possible to be placed in the given baskets.
  You will be given an array of characters where each character represents a fruit tree. The farm has following restrictions:
 Each basket can have only one type of fruit. There is no limit to how many fruit a basket can hold.
 You can start with any tree, but you can’t skip a tree once you have started.
 You will pick exactly one fruit from every tree until you cannot, i.e., you will stop when you have to pick from a third fruit type.
 Write a function to return the maximum number of fruits in both baskets.

 Example 1: Input: arr=['A', 'B', 'C', 'A', 'C']  Output: 3
 Explanation: We can put 2 'C' in one basket and one 'A' in the other from the subarray ['C', 'A', 'C']

 Example 2: Input: arr = ['A', 'B', 'C', 'B', 'B', 'C']  Output: 5
 Explanation: We can put 3 'B' in one basket and two 'C' in the other basket. This can be done if we start with the second letter: ['B', 'C', 'B', 'B', 'C']

 NOTE:  Reworded Version (Generic Format):
 You are given an array of characters. Your task is to find the length of the longest subarray that contains at most two distinct characters.
 You can start at any index, but once you start, you must consider consecutive elements only (no skipping).
 Return the length of the longest contiguous subarray with at most two different characters.
 */

/**
 *  Example 1: Input: arr=['A', 'B', 'C', 'A', 'C']
 *  find the length of Longest subaarray tha contains at most two distinct char
 *  at most two >=2
 *  *
 *
 */
public class P04FruitsintoBaskets {

    public static  int findMaxNumber(char[] chars ){
        if (chars == null || chars.length == 0) return 0;

        int longestLength = Integer.MIN_VALUE;
        int windowStart =0;


        HashMap<Character, Integer> freqChars = new HashMap<>();

        for (int windowEnd =0; windowEnd< chars.length; windowEnd ++){
            char ch= chars[windowEnd];
            freqChars.put(ch, freqChars.getOrDefault(ch, 0) +1);

            // shrinking
            while (freqChars.size()>2){
                char startChar = chars[windowStart];
                freqChars.put(startChar, freqChars.get(startChar) -1);
                if (freqChars.get(startChar) == 0)
                    freqChars.remove(startChar);
                windowStart ++;
            }

            longestLength = Math.max(longestLength, windowEnd - windowStart +1);


        }

        return longestLength;
    }

    public static void main(String[] args) {


        System.out.println("======================================");
        System.out.println("P04. Fruits into Baskets.");
        System.out.println("======================================");
        // add some test cases with printed expected output
        char[] charsP04 = new char[]{'A', 'B', 'C', 'B', 'B', 'C'};
        System.out.println("Input:" + makeItBold(Arrays.toString(charsP04)) +
                ", length of the longest subarray: " + makeItBold(""+ findMaxNumber(charsP04)) + ", Expected outPut: 5 " );
        charsP04 = new char[]{'A', 'A', 'A', 'A'};
        System.out.println("Input:" + makeItBold(Arrays.toString(charsP04)) +
                ", length of the longest subarray: " + makeItBold(""+ findMaxNumber(charsP04)) + ", Expected outPut: 4 " );

        charsP04 = new char[]{'A', 'B', 'C', 'D', 'E'};
        System.out.println("Input:" + makeItBold(Arrays.toString(charsP04)) +
                ", length of the longest subarray: " + makeItBold(""+ findMaxNumber(charsP04)) + ", Expected outPut: 2 " );
        // give me more edge test cases
        charsP04 = new char[]{'A', 'B', 'A', 'C', 'A', 'B', 'B', 'A'};
        System.out.println("Input:" + makeItBold(Arrays.toString(charsP04)) +
                ", length of the longest subarray: " + makeItBold(""+ findMaxNumber(charsP04)) + ", Expected outPut: 4 " );
        charsP04 = new char[]{};
        System.out.println("Input:" + makeItBold(Arrays.toString(charsP04)) +
                ", length of the longest subarray: " + makeItBold(""+ findMaxNumber(charsP04)) + ", Expected outPut: 0 " );



    }
}
