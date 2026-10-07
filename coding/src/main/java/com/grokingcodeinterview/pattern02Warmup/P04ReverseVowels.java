package com.grokingcodeinterview.pattern02Warmup;

import java.util.Arrays;
import java.util.HashSet;

/*
 * roblem Statement
 * Given a string s, reverse only all the vowels in the string and return it.
 *
 * The vowels are 'a', 'e', 'i', 'o', and 'u', and they can appear in both lower and upper cases, more than once.
 *
 * Example 1:
 *
 * Input: s= "hello"
 * Output: "holle"
 * Example 2:
 *
 * Input: s= "AEIOU"
 * Output: "UOIEA"
 * Example 3:
 *
 * Input: s= "DesignGUrus"
 * Output: "DusUgnGires"
 * Constraints:
 *
 * 1 <= s.length <= 3 * 105
 */
public class P04ReverseVowels {
    public  static String reverseVowels(String s){
        HashSet<Character> set = new HashSet<>();
        set.addAll(Arrays.asList('a','e','i','o', 'u', 'A','E','E','O', 'U' ));
        char[] chars = s.toCharArray();
        int left = 0;
        int right = s.length() -1;

        while (left<right){

            char leftChar = s.charAt(left);
            char rightChar = s.charAt(right);
            if (!set.contains(leftChar)){
                left ++;
                continue;
            }
            if (!set.contains(rightChar)){
                right--;
                continue;
            }



            // swap left and right
            swap (chars, left, right);
            left ++;
            right--;


        }

        return new  String(chars);
    }

    public static void swap (char[] chars, int left, int right){
        char temp = chars[left];
        chars[left] = chars[right];
        chars[right] = temp;

    }

    public static void main(String[] args) {
        String s1 = "hello";
        System.out.println("Test Case 1: " + reverseVowels(s1)); // holle
        String s2 = "AEIOU";
        System.out.println("Test Case 2: " + reverseVowels(s2)); // UOIEA
        String s3 = "DesignGUrus";
        System.out.println("Test Case 3: " + reverseVowels(s3)); // DusUgnGires
    }


}
