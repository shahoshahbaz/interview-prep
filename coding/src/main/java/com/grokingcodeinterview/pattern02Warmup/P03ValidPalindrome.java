package com.grokingcodeinterview.pattern02Warmup;

/*
 * Problem Statement
 * A phrase is a palindrome if, after converting all uppercase letters into lowercase letters
 * and removing all non-alphanumeric characters, it reads the same forward and backward.
 *  Alphanumeric characters include letters and numbers.
 *
 * Given a string s, return true if it is a palindrome, or false otherwise.
 *
 * Example 1:
 *
 * Input: sentence = "A man, a plan, a canal, Panama!"
 * Output: true
 * Explanation: "amanaplanacanalpanama" is a palindrome.
 * Example 2:
 *
 * Input: sentence = "Was it a car or a cat I saw?"
 * Output: true
 * Explanation: Explanation: "wasitacaroracatisaw" is a palindrome.
 * Constraints:
 *
 * 1 <= s.length <= 2 * 105
 * s consists only of printable ASCII characters.
 */
public class P03ValidPalindrome {

    public boolean isPalindrome(String s){
        if (s == null || s.length() <=1) return false;
        int left = 0;
        int right = s.length() -1;
        s = s.toLowerCase();

        while (left < right){
            char leftChar = s.charAt(left);
            char rightChar = s.charAt(right);

            if (!Character.isLetter(leftChar )){
                left ++;
                continue;
            }
            if (!Character.isLetter(rightChar )){
                right --;
                continue;
            }

            if (leftChar == rightChar){
                left++;
                right--;
            }else{
                return false;
            }

        }

        return true;


    }

    public static void main(String[] args) {
        P03ValidPalindrome solution = new P03ValidPalindrome();
        String sentence1 = "A man, a plan, a canal, Panama!";
        System.out.println("Is palindrome: " + solution.isPalindrome(sentence1)); // true
        String sentence2 = "Was it a car or a cat I saw?";
        System.out.println("Is palindrome: " + solution.isPalindrome(sentence2)); // true
        String sentence3 = "Hello, World!";
        System.out.println("Is palindrome: " + solution.isPalindrome(sentence3)); // false
    }
}
