package com.grokingcodeinterview.pattern02Warmup;

/*
 * Problem Statement
 * Given an array of strings words and two different strings that already exist in the array word1 and word2, return the shortest distance between these two words in the list.
 *
 * Example 1:
 *
 * Input: words = ["the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"], word1 = "fox", word2 = "dog"
 * Output: 5
 * Explanation: The distance between "fox" and "dog" is 5 words.
 * Example 2:
 *
 * Input: words = ["a", "c", "d", "b", "a"], word1 = "a", word2 = "b"
 * Output: 1
 * Explanation: The shortest distance between "a" and "b" is 1 word. Please note that "a" appeared twice.
 * Example 3:
 *
 * Input: words = ["a", "b", "c", "d", "e"], word1 = "a", word2 = "e"
 * Output: 4
 * Explanation: The distance between "a" and "e" is 4 words.
 * Constraints:
 *
 * 2 <= words.length <= 3 * 104
 * 1 <= words[i].length <= 10
 * words[i] consists of lowercase English letters.
 * word1 and word2 are in words.
 * word1 != word2
 */
public class P06ShortestWordDistance {

    public  static int shortestDistance(String[] words, String word1, String word2) {


        int word1Index = -1;
        int word2Index =-1;

        int shortestDistance = Integer.MAX_VALUE;

        for (int i =0; i< words.length; i++){
            if (words[i].equals(word1)){
                word1Index =i;
            }
            if (words[i].equals(word2)){
                word2Index =i;
            }

        }
        if (word1Index != -1 && word2Index != -1){
            shortestDistance = Math.min(shortestDistance, Math.abs(word1Index - word2Index));

        }
        return shortestDistance;
    }
    public static void main(String[] args) {


        String[] words = {"practice", "makes", "perfect", "coding", "makes"};
        String word1 = "coding";
        String word2 = "practice";
        int result = shortestDistance(words, word1, word2);
        System.out.println("Shortest Distance: " + result); // Output: 3
        // Additional test case
        String[] words2 = {"a", "b", "c", "d", "e"};
        String word3 = "a";
        String word4 = "e";
        int result2 = shortestDistance(words2, word3, word4);
        System.out.println("Shortest Distance: " + result2); // Output: 4

    }
}
