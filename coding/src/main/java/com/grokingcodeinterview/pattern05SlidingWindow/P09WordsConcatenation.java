package com.grokingcodeinterview.pattern05SlidingWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * You’re given a string s and a list of words words, where all words have the same length.
 *
 * A concatenated substring is formed by joining all the words from any permutation of words — each used exactly once, without any extra characters in between.
 *
 * For example, if words = ["ab", "cd", "ef"], then valid concatenated strings include "abcdef", "abefcd", "cdabef", "cdefab", "efabcd", and "efcdab". A string like "acdbef" is not valid because it doesn't match any complete permutation of the given words.
 *
 * Return all starting indices in s where such concatenated substrings appear. You can return the indices in any order.
 *
 * Example 1:
 *
 * Input: String="catfoxcat", Words=["cat", "fox"]
 * Output: [0, 3]
 * Explanation: The two substring containing both the words are "catfox" & "foxcat".
 * Example 2:
 *
 * Input: String="catcatfoxfox", Words=["cat", "fox"]
 * Output: [3]
 * Explanation: The only substring containing both the words is "catfox".
 */
public class P09WordsConcatenation {

    public  static List<Integer> findWordConcatenation(String str, String[] words){
        List<Integer> result =  new ArrayList<>();
        if (str == null || words == null || str.length()<words[0].length()) return result;

        //Sep 1: Create Frequency map of all words
        HashMap<String, Integer> wordFreq = new HashMap<>();
        for (String word: words){
            wordFreq.put(word, wordFreq.getOrDefault(word, 0)+1);
        }

        int wordLength = words[0].length();
        int wordCount = words.length;
        int windowLength = wordLength * wordCount;
        // Step 2: Slide a window of total length =
        for (int i =0; i<= str.length() - windowLength; i++){
            // Create a seen map in each window
            Map<String,Integer> wordsSeen = new HashMap<>();

            for (int j =0; j< wordCount; j++){
                int wordIndex = i + j* wordLength;
                if (wordIndex + wordLength > str.length()) break;
                String word = str.substring(wordIndex, wordIndex + wordLength);

                if (!wordFreq.containsKey(word)){
                    break;
                }

                wordsSeen.put(word, wordsSeen.getOrDefault(word, 0)+1);
                if (wordsSeen.get(word)> wordFreq.get(word)){
                    break;
                }
                if (j == wordCount -1){
                    result.add(i);
                }

            }

        }
        return result;
    }

    public static void main(String[] args) {
        // Test case 1
        String str1 = "catfoxcat";
        String[] words1 = {"cat", "fox"};
        List<Integer> result1 = findWordConcatenation(str1, words1);
        System.out.println("Input: str=\"" + str1 + "\", words=" + java.util.Arrays.toString(words1));
        System.out.println("Output: " + result1);
        System.out.println("Expected: [0, 3]");
        System.out.println();

        // Test case 2
        String str2 = "catcatfoxfox";
        String[] words2 = {"cat", "fox"};
        List<Integer> result2 = findWordConcatenation(str2, words2);
        System.out.println("Input: str=\"" + str2 + "\", words=" + java.util.Arrays.toString(words2));
        System.out.println("Output: " + result2);
        System.out.println("Expected: [3]");
        System.out.println();

        // Test case 3: Repeated words
        String str3 = "dogcatcatdog";
        String[] words3 = {"dog", "cat"};
        List<Integer> result3 = findWordConcatenation(str3, words3);
        System.out.println("Input: str=\"" + str3 + "\", words=" + java.util.Arrays.toString(words3));
        System.out.println("Output: " + result3);
        System.out.println("Expected: [0, 6]");
        System.out.println();

        // Test case 4: Multiple occurrences of the same word
        String str4 = "barfoothefoobarman";
        String[] words4 = {"foo", "bar"};
        List<Integer> result4 = findWordConcatenation(str4, words4);
        System.out.println("Input: str=\"" + str4 + "\", words=" + java.util.Arrays.toString(words4));
        System.out.println("Output: " + result4);
        System.out.println("Expected: [0, 9]");
        System.out.println();

        // Test case 5: No match
        String str5 = "wordgoodgoodgoodbestword";
        String[] words5 = {"word", "good", "best", "word"};
        List<Integer> result5 = findWordConcatenation(str5, words5);
        System.out.println("Input: str=\"" + str5 + "\", words=" + java.util.Arrays.toString(words5));
        System.out.println("Output: " + result5);
        System.out.println("Expected: []");
        System.out.println();

        // Test case 6: Empty string
        String str6 = "";
        String[] words6 = {"hello"};
        List<Integer> result6 = findWordConcatenation(str6, words6);
        System.out.println("Input: str=\"" + str6 + "\", words=" + java.util.Arrays.toString(words6));
        System.out.println("Output: " + result6);
        System.out.println("Expected: []");
        System.out.println();

        // Test case 7: Three words
        String str7 = "catdogcow";
        String[] words7 = {"cat", "dog", "cow"};
        List<Integer> result7 = findWordConcatenation(str7, words7);
        System.out.println("Input: str=\"" + str7 + "\", words=" + java.util.Arrays.toString(words7));
        System.out.println("Output: " + result7);
        System.out.println("Expected: [0]");
    }
}
