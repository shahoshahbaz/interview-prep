package com.grokingcodeinterview.pattern02Warmup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class pattern01WarmUPR2 {
    /*Given an integer array nums,
   return true if any value appears at least twice in the array, and return false if every element is distinct.

   Examples
   Example 1:
   Input: nums= [1, 2, 3, 4]
   Output: false
   Explanation: There are no duplicates in the given array.
   Example 2:
   Input: nums= [1, 2, 3, 1]
   Output: true
   Explanation: '1' is repeating.
   */
    public static boolean containsDuplicate(int[] nums) {

        HashSet<Integer> set = new HashSet<>();

        for (int num: nums){
            if (!set.add(num)){
                return true;
            }
        }
        return false;


    }
    /*
    A phrase is a palindrome if, 
    after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters, 
    it reads the same forward and backward
    . Alphanumeric characters include letters and numbers.

Given a string s, return true if it is a palindrome, or false otherwise.

Example 1:

Input: sentence = "A man, a plan, a canal, Panama!"
Output: true
Explanation: "amanaplanacanalpanama" is a palindrome.
Example 2:

Input: sentence = "Was it a car or a cat I saw?"
Output: true
Explanation: Explanation: "wasitacaroracatisaw" is a palindrome.
Constraints:

1 <= s.length <= 2 * 105
s consists only of printable ASCII characters.
     */

    /** convert lower and upper case
     * 
     * @param s
     * @return
     */
    public static  boolean isPalindrome(String s){
        
        if (s == null || s.length() ==1 ) return true;
        
        // convert to lower case
        s = s.toLowerCase();
        int left = 0;
        int right = s.length() -1;
        
        while (left<= right ){
            
            char leftChar = s.charAt(left);
            char rightChar = s.charAt(right);
            
            if  (!Character.isLetter(leftChar)){
               left ++; 
               continue;
            }
            
            if (!Character.isLetter(rightChar)){
                right --;
                continue;
            }
            
            if (leftChar != rightChar){
                return false;
            }
            
            left ++;
            right --;
        
            
        }
        return true;
        
    }

    /*
     * Problem Statement
     * Given an array of integers nums, return the number of good pairs.
     *
     * A pair (i, j) is called good if nums[i] == nums[j] and i < j.
     *
     * Example 1:
     *
     * Input: nums = [1,2,3,1,1,3]
     * Output: 4
     * Explanation: There are 4 good pairs, here are the indices: (0,3), (0,4), (3,4), (2,5).
     * Example 2:
     *
     * Input: nums = [1,1,1,1]
     * Output: 6
     * Explanation: Each pair in the array is a 'good pair'.
     * Example 3:
     *
     * Input: words = nums = [1,2,3]
     * Output: 0
     * Explanation: No number is repeating.
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * 1 <= nums[i] <= 100
     *
     */

    /**
     *  get the freqMap(1, 3), (2, 0), (3, 2) = 3 *2/2 = 3 + 2(1)/2 = 4
     *
     */
    public  static int numGoodPairs(int[] nums){
        if (nums == null || nums.length<2) return 0;

        HashMap<Integer, Integer> freqMap = new HashMap<>();
        int countNumberOfPair = 0;
        for (int num:nums){
            freqMap.put(num, freqMap.getOrDefault(num, 0) +1);
            countNumberOfPair += (freqMap.get(num)-1);
            }
        return countNumberOfPair;
        }

    public  static int sqrt(int x){
        if(x<=1) return x;

        int left = 2;
        int right = x/2; // because sqrt(x)
        int mid = 0;
        while(left<=right){
            mid = left + (right -left)/2;
            long square = (long) mid * mid;
            if (square == x){
                return mid;
            }else if (square >x){
                right = mid -1;
            }else{
                left = mid +1;
            }

        }
        return right;

    }

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
 public static boolean checkIfPangram(String str){

     if (str.length()< 26) return false;
     boolean[] chars = new boolean[26];


     for (int i =0; i< str.length(); i++){
         char ch = str.charAt(i);

         if (Character.isLetter(ch)){
             chars['z' - Character.toLowerCase(ch) ] = true;
         }
     }

     for (boolean aChar : chars) {
         if (!aChar) {
             return false;
         }
     }

     return true;


 }
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

    public static String reverseVowels(String str){

        Set<Character> set = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));

        int left = 0;
        int right = str.length()-1;
        char[] ch = str.toCharArray();

        while (left<=right){
            if (!set.contains(ch[left])) {
                left++;
                continue;
            }
            if (!set.contains(ch[right])) {
                right--;
                continue;
            }

            // swap left and right
            char temp = ch[left];
            ch[left] = ch[right];
            ch[right] = temp;

            left++;
            right --;

        }

        return new String(ch);
    }

    /*
     * Problem Statement
     * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
     *
     * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, using all the original letters exactly once.
     *
     * Example 1:
     *
     * Input: s = "listen", t = "silent"
     * Output: true
     * Example 2:
     *
     * Input: s = "rat", t = "car"
     * Output: false
     * Example 3:
     *
     * Input: s = "hello", t = "world"
     * Output: false
     * Constraints:
     *
     * 1 <= s.length, t.length <= 5 * 104
     * s and t consist of lowercase English letters.
     */
    public static boolean isAnagram(String s1, String s2){
        if (s1.length() != s2.length()) return false;
        HashMap<Character, Integer> freq = new HashMap<>();
        for (char ch: s1.toCharArray()){
            freq.put(ch, freq.getOrDefault(ch,0) +1);

        }

        for (char ch: s2.toCharArray()){
            if (!freq.containsKey(ch)){
                return false;
            }else{
                freq.put(ch, freq.get(ch) -1);
                if (freq.get(ch) == 0)
                    freq.remove(ch);
            }

        }

        return freq.size() ==0;
    }
    /*
     * Problem Statement
     * Given an array of strings words and two different strings that already exist in the array word1 and word2,
     *  return the shortest distance between these two words in the list.
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

    /**
     * Input: words = ["a", "c", "d", "b", "a"], word1 = "a", word2 = "b"  * Output: 1
     * index1 = 0, index2 = 4, minDistance = 3, index1++, index2++;
     *
     * index 1 = 4,
     *
     */
    public static int shortestDistance(String[] str, String t1, String t2){
        int index1 = 0;
        int index2 = 0;
        int shortDistance = Integer.MAX_VALUE;
        for (int i =0; i< str.length; i++){
            if (str[i].equals(t1)){
                index1 = i;
                continue;
            }
            if (str[i].equals(t2)){
                index2 = i;
                continue;

            }
            shortDistance = Math.min(shortDistance, Math.abs(index1- index2 ));
        }
        return shortDistance;


    }


    public static void main(String[] args) {
        String sentence1 = "A man, a plan, a canal, Panama!";
        System.out.println("Is palindrome: " + isPalindrome(sentence1)); // true
        String sentence2 = "Was it a car or a cat I saw?";
        System.out.println("Is palindrome: " + isPalindrome(sentence2)); // true
        String sentence3 = "Hello, World!";
        System.out.println("Is palindrome: " + isPalindrome(sentence3)); // false

        System.out.println("Number of good pairs...");
        int[] nums1 = {1,2,3,1,1,3};
        System.out.println(Arrays.toString(nums1));
        System.out.println("Number of Good Pairs: " + numGoodPairs(nums1)); // 4

        int[] nums2 = {1,1,1,1};
        System.out.println(Arrays.toString(nums2));
        System.out.println("Number of Good Pairs: " + numGoodPairs(nums2)); // 6

        int[] nums3 = {1,2,3};
        System.out.println(Arrays.toString(nums3));
        System.out.println("Number of Good Pairs: " + numGoodPairs(nums3)); // 0

        System.out.println("Sqrt......");


        int x1 = 8;
        System.out.println("Sqrt of " + x1 + " is: " + sqrt(x1)); // 2

        int x2 = 4;
        System.out.println("Sqrt of " + x2 + " is: " + sqrt(x2)); // 2

        int x3 = 2;
        System.out.println("Sqrt of " + x3 + " is: " + sqrt(x3)); // 1
        int x4 = 15;
        System.out.println("Sqrt of " + x4 + " is: " + sqrt(x4)); // 3);

        System.out.println("P01.Contains duplicate....");
        // add some test cases
        nums1 = new int[]{1, 2, 3, 4};
        System.out.println("Test Case 1: " + containsDuplicate(nums1)); // false
         nums2 =  new int[]{1, 2, 3, 1};
        System.out.println("Test Case 2: " + containsDuplicate(nums2)); // true

        // panagrams
        System.out.println("P02-PANAGRAM");
        sentence1 = "TheQuickBrownFoxJumpsOverTheLazyDog";
        System.out.println("Test Case 1: " + checkIfPangram(sentence1)); // true
        sentence2 = "This is not a pangram";
        System.out.println("Test Case 2: " + checkIfPangram(sentence2)); // false

        System.out.println("P04. Reverse vowels....");

        String s1 = "hello";
        System.out.println("Test Case 1: " + reverseVowels(s1)); // holle
        String s2 = "AEIOU";
        System.out.println("Test Case 2: " + reverseVowels(s2)); // UOIEA
        String s3 = "DesignGUrus";
        System.out.println("Test Case 3: " + reverseVowels(s3)); // DusUgnGires

        System.out.println("Valid Anagaram....");
        s1 = "listen";
        String t1 = "silent";
        System.out.println("Is Anagram: " + isAnagram(s1, t1)); // true

        s2 = "rat";
        String t2 = "car";
        System.out.println("Is Anagram: " +isAnagram(s2, t2)); // false

        s3 = "hello";
        String t3 = "world";
        System.out.println("Is Anagram: " +isAnagram(s3, t3)); // false

        String[] words = {"practice", "makes", "perfect", "coding", "makes"};
        String word1 = "coding";
        String word2 = "practice";
        int result = shortestDistance(words, word1, word2);
        System.out.println(Arrays.toString(words) + ", word1: " + word1 + ", word2: " + word2 +"\tShortest Distance: " + result);

//        System.out.println("Shortest Distance: " + result); // Output: 3
        // Additional test case
        String[] words2 = {"a", "b", "c", "d", "e"};
        String word3 = "a";
        String word4 = "e";
        int result2 = shortestDistance(words2, word3, word4);
        System.out.println("Shortest Distance: " + result2); // Output: 4

    }

}