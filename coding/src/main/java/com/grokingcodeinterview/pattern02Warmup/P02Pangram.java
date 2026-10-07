package com.grokingcodeinterview.pattern02Warmup;

/*
 * Problem Statement
 * A pangram is a sentence where every letter of the English alphabet appears at least once.
 *
 * Given a string sentence containing English letters (lower or upper-case), return true if sentence is a pangram, or false otherwise.
 *
 * Note: The given sentence might contain other characters like digits or spaces, your solution should handle these too.
 *
 * Example 1:
 *
 * Input: sentence = "TheQuickBrownFoxJumpsOverTheLazyDog"
 * Output: true
 * Explanation: The sentence contains at least one occurrence of every letter of the English alphabet either in lower or upper case.
 * Example 2:
 *
 * Input: sentence = "This is not a pangram"
 * Output: false
 * Explanation: The sentence doesn't contain at least one occurrence of every letter of the English alphabet.
 * Constraints:
 *
 * 1 <= sentence.length <= 1000
 * sentence consists of lower or upper-case English letters.
 */
public class P02Pangram {
    public boolean checkIfPangram(String sentence){
        boolean[] frequency = new boolean[26];

        sentence =sentence.toLowerCase();

        for (int i =0 ; i< sentence.length(); i++){
            char ch = sentence.charAt(i);
            if (Character.isLetter(ch)){
                frequency[ 'z' - ch] = true;
            }

        }

        for (int i =0 ; i< frequency.length; i++){
            if (!frequency[i]) return false;
        }
        return true;


    }

    public static void main(String[] args) {
        P02Pangram pangram = new P02Pangram();
        String sentence1 = "TheQuickBrownFoxJumpsOverTheLazyDog";
        System.out.println("Test Case 1: " + pangram.checkIfPangram(sentence1)); // true
        String sentence2 = "This is not a pangram";
        System.out.println("Test Case 2: " + pangram.checkIfPangram(sentence2)); // false
    }
}
