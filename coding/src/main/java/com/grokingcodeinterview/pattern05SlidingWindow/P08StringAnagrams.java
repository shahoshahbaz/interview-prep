package com.grokingcodeinterview.pattern05SlidingWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P08StringAnagrams {

    public  static List<Integer> findAnagrams(String str, String pattern){
        List<Integer> result = new ArrayList<>();
        if (str == null || pattern == null || str.length()<pattern.length()) return result;

        // create patterFreq
        HashMap<Character, Integer> patterFreq = new HashMap<>();
        for (char c: pattern.toCharArray()){
            patterFreq.put(c, patterFreq.getOrDefault(c, 0) +1);
        }
        int windowStart =0;
        HashMap<Character, Integer> windowFreq = new HashMap<>();
        for (int windowEnd = 0; windowEnd< str.length(); windowEnd ++){

            char rightChar = str.charAt(windowEnd);
            windowFreq.put(rightChar, windowFreq.getOrDefault(rightChar, 0 )+1);


            if (windowEnd - windowStart +1> patterFreq.size()){
                char  leftChar = str.charAt(windowStart);
                windowFreq.put(leftChar, windowFreq.get(leftChar)-1);
                if (windowFreq.get(leftChar) == 0) windowFreq.remove(leftChar);
                windowStart++;
            }
            if (windowFreq.equals(patterFreq)){
                result.add(windowStart);
            }


        }
        return result;
    }

    /**
     * Find all anagrams of pattern in the input string using an optimized approach.
     * This method tracks matched characters rather than comparing entire hash maps.
     *
     * @param str The input string to search in
     * @param pattern The pattern to find anagrams of
     * @return List of starting indices of all anagrams
     */
    public static List<Integer> findAnagramsImproved(String str, String pattern){
        List<Integer> result = new ArrayList<>();
        if (str == null|| pattern == null || str.length()< pattern.length()) return result;

        HashMap<Character, Integer> patternFreq = new HashMap<>();
        // Count frequencies of characters in the pattern
        for (char c: pattern.toCharArray()){
            patternFreq.put(c, patternFreq.getOrDefault(c, 0) +1);
        }

        int windowStart = 0;
        int matched = 0;
        HashMap<Character, Integer> winFreq = new HashMap<>();

        for (int windowEnd = 0; windowEnd < str.length(); windowEnd++){
            char rightChar = str.charAt(windowEnd);

            // Process current character if it's in the pattern
            if (patternFreq.containsKey(rightChar)){
                winFreq.put(rightChar, winFreq.getOrDefault(rightChar, 0) +1);

                // Only count as a match if the frequency exactly matches
                if (winFreq.get(rightChar).intValue() == patternFreq.get(rightChar).intValue()){
                    matched++;
                }
                // If we've added too many of this character, it's no longer a match
                else if (winFreq.get(rightChar).intValue() > patternFreq.get(rightChar).intValue()) {
                    // If we had an exact match before, we need to decrement matched
                    if (winFreq.get(rightChar).intValue() - 1 == patternFreq.get(rightChar).intValue()) {
                        matched--;
                    }
                }
            }

            // Shrink the window if it is bigger than pattern
            if (windowEnd - windowStart + 1 > pattern.length()){
                char leftChar = str.charAt(windowStart);

                if (patternFreq.containsKey(leftChar)){
                    // If removing this char breaks a match
                    if (winFreq.get(leftChar).intValue() == patternFreq.get(leftChar).intValue()){
                        matched--;
                    }
                    // If removing this char fixes a previous overcount
                    else if (winFreq.get(leftChar).intValue() > patternFreq.get(leftChar).intValue()) {
                        if (winFreq.get(leftChar).intValue() - 1 == patternFreq.get(leftChar).intValue()) {
                            matched++;
                        }
                    }

                    winFreq.put(leftChar, winFreq.get(leftChar) - 1);
                    if (winFreq.get(leftChar) == 0)
                        winFreq.remove(leftChar);
                }
                windowStart++;
            }

            // Only add to result if we have matched all characters in the pattern
            if (matched == patternFreq.size()){
                result.add(windowStart);
            }
        }

        return result;
    }

    public static List<Integer>  findStringAnagrams3(String str, String pattern) {
        int windowStart = 0, matched = 0;
        Map<Character, Integer> charFrequencyMap = new HashMap<>();
        for (char chr : pattern.toCharArray())
            charFrequencyMap.put(chr, charFrequencyMap.getOrDefault(chr, 0) + 1);

        List<Integer> resultIndices = new ArrayList<Integer>();
        // our goal is to match all the characters from the map with the current window
        for (int windowEnd = 0; windowEnd < str.length(); windowEnd++) {
            char rightChar = str.charAt(windowEnd);
            // decrement the frequency of the matched character
            if (charFrequencyMap.containsKey(rightChar)) {
                charFrequencyMap.put(rightChar, charFrequencyMap.get(rightChar) - 1);
                if (charFrequencyMap.get(rightChar) == 0)
                    matched++;
            }

            if (matched == charFrequencyMap.size()) // have we found an anagram?
                resultIndices.add(windowStart);

            if (windowEnd >= pattern.length() - 1) { // shrink the window
                char leftChar = str.charAt(windowStart++);
                if (charFrequencyMap.containsKey(leftChar)) {
                    if (charFrequencyMap.get(leftChar) == 0)
                        matched--; // before putting the character back, decrement the matched count
                    // put the character back
                    charFrequencyMap.put(leftChar, charFrequencyMap.get(leftChar) + 1);
                }
            }
        }

        return resultIndices;
    }

    public static void main(String[] args) {
        System.out.println("Test 1");
        String str1 = "ppqp", pattern1 = "pq";
        System.out.println("Original Output: " + findAnagrams(str1, pattern1));
        System.out.println("Improved Output: " + findAnagramsImproved(str1, pattern1));
        System.out.println("Expected: [1, 2]");

        System.out.println("\nTest 2");
        String str2 = "abbcabc", pattern2 = "abc";
        System.out.println("Original Output: " + findAnagrams(str2, pattern2));
        System.out.println("Improved Output: " + findAnagramsImproved(str2, pattern2));
        System.out.println("Expected: [2, 3, 4]");

        System.out.println("\nTest 3 - pattern longer than string");
        String str3 = "ab", pattern3 = "abc";
        System.out.println("Original Output: " + findAnagrams(str3, pattern3));
        System.out.println("Improved Output: " + findAnagramsImproved(str3, pattern3));
        System.out.println("Expected: []");

        System.out.println("\nTest 4 - repeated characters in pattern");
        String str4 = "aaacb", pattern4 = "aac";
        System.out.println("Original Output: " + findAnagrams(str4, pattern4));
        System.out.println("Improved Output: " + findAnagramsImproved(str4, pattern4));
        System.out.println("Expected: [0]");

        System.out.println("\nTest 5 - overlapping matches");
        String str5 = "cbaebabacd", pattern5 = "abc";
        System.out.println("Original Output: " + findAnagrams(str5, pattern5));
        System.out.println("Improved Output: " + findAnagramsImproved(str5, pattern5));
        System.out.println("Expected: [0, 6]");

        System.out.println("\nTest 6 - all same character");
        String str6 = "aaaaa", pattern6 = "a";
        System.out.println("Original Output: " + findAnagrams(str6, pattern6));
        System.out.println("Improved Output: " + findAnagramsImproved(str6, pattern6));
        System.out.println("Expected: [0, 1, 2, 3, 4]");

        System.out.println("\nTest 7 - no match");
        String str7 = "abcdefg", pattern7 = "xyz";
        System.out.println("Original Output: " + findAnagrams(str7, pattern7));
        System.out.println("Improved Output: " + findAnagramsImproved(str7, pattern7));
        System.out.println("Expected: []");

        System.out.println("\nTest 8 - same letters but not enough frequency");
        String str8 = "abcbac", pattern8 = "aabbc";
        System.out.println("Original Output: " + findAnagrams(str8, pattern8));
        System.out.println("Improved Output: " + findAnagramsImproved(str8, pattern8));
        System.out.println("Original Output: " + findStringAnagrams3(str8, pattern8));

        System.out.println("Expected: []");
    }

}
