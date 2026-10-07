package com.grokingcodeinterview.pattern05SlidingWindow;

import java.util.HashMap;
import java.util.Map;

/**
 * Problem Statement
 * Given a string with lowercase letters only, if you are allowed to replace no more than 'k' letters with any letter, find the length of the longest substring having the same letters after replacement.
 * Example 1: Input: str="aabccbb", k=2 Output: 5
 * Explanation: Replace the two 'c' with 'b' to have a longest repeating substring "bbbbb".
 * Example 2: Input: str="abbcb", k=1 Output: 4
 * Explanation: Replace the 'c' with 'b' to have a longest repeating substring "bbbb".
 * Example 3: Input: str="abccde", k=1 utput: 3
 * Explanation: Replace the 'b' or 'd' with 'c' to have the longest repeating substring "ccc".
 * Constraints:
 * 1 <= str.length <=10^5
 * s consists of only lowercase English letters.
 * 0 <= k <= s.length
 */
public class P05LongestSubstringSithSameLettersAfterReplacement {

  public  static int findLength(String str, int k){


      int windowStart = 0;
      int maxLength = Integer.MIN_VALUE;
      int mostRepeatedChar =0;
      HashMap<Character, Integer> charFreq = new HashMap<>();

      for (int windowEnd = 0; windowEnd< str.length(); windowEnd ++){
          char rightChar = str.charAt(windowEnd);
          charFreq.put(rightChar, charFreq.getOrDefault(rightChar, 0) +1);
          // get most repeated Char
          mostRepeatedChar = Math.max(mostRepeatedChar, charFreq.get(rightChar));

          // so in the window (windowStart - windowEnd) we have comibnation of mostRepeted char
          // and other chars, so if other chars is bigger than k, it voilate our condtion

          while (windowEnd - windowStart +1 - mostRepeatedChar> k){
              char leftChar = str.charAt(windowStart);
              charFreq.put(leftChar, charFreq.get(leftChar) -1);
              windowStart ++;

          }
          maxLength = Math.max(maxLength, windowEnd - windowStart +1);





      }

      return maxLength;

  }

    public static void main(String[] args) {
        // Test cases from examples
        System.out.println("Example 1: Input: str=\"aabccbb\", k=2");
        System.out.println("Output: " + findLength("aabccbb", 2));
        System.out.println("Expected: 5");

        System.out.println("\nExample 2: Input: str=\"abbcb\", k=1");
        System.out.println("Output: " + findLength("abbcb", 1));
        System.out.println("Expected: 4");

        System.out.println("\nExample 3: Input: str=\"abccde\", k=1");
        System.out.println("Output: " + findLength("abccde", 1));
        System.out.println("Expected: 3");

        // Additional test cases
        System.out.println("\nAdditional test: Input: str=\"\", k=0");
        System.out.println("Output: " + findLength("", 0));
        System.out.println("Expected: 0");

        System.out.println("\nAdditional test: Input: str=\"aaaaa\", k=0");
        System.out.println("Output: " + findLength("aaaaa", 0));
        System.out.println("Expected: 5");

        System.out.println("\nAdditional test: Input: str=\"abcdef\", k=3");
        System.out.println("Output: " + findLength("abcdef", 3));
        System.out.println("Expected: 4");

        System.out.println("\nAdditional test: Input: str=\"aababba\", k=1");
        System.out.println("Output: " + findLength("aababba", 1));
        System.out.println("Expected: 4");
    }
}
