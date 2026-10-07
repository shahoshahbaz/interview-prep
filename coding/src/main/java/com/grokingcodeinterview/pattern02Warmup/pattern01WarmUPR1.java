package com.grokingcodeinterview.pattern02Warmup;

import java.util.*;

// 23 /10 = 2 rem = 3
class pattern01WarmUPR1 {


        public static boolean containsDuplicate(int[] nums) {
            HashSet< Integer> set = new HashSet<>();
            for (int num:nums){
                if (!set.add(num)){
                    return true;
                }
            }
        return false;
        }
        public static boolean checkIfPangram(String str){
            if (str == null || str.length()<26) return false;

            str = str.toLowerCase();
            boolean[]  freq = new boolean[26];

            for (int i =0; i< str.length(); i++){
                char ch = str.charAt(i);
                if (Character.isLetter(ch)){
                    freq['z' - ch] = true;
                }

            }

            for (int i =0; i< freq.length; i++){
                if (!freq[i]){
                    return false;
                }
            }
            return true;
        }

    public  static boolean isPalindrome(String str){

            if(str == null || str.length()<1){
                return false;
            }

            str = str.toLowerCase();

            int left =0;
            int right = str.length() -1;
            while( left<right){
                char leftChar = str.charAt(left);
                char rightChar = str.charAt(right);
                if (!Character.isLetter(leftChar)){
                    left++;
                    continue;
                }
                if (!Character.isLetter(rightChar)){
                    right --;
                    continue;
                }
                if (leftChar != rightChar){
                    return false;
                }
                left++;
                right --;

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
    public static String reverseVowels(String s){
            if (s == null || s.length()<2) return s;

            Set<Character> vowels = new HashSet<>();
            List<Character> vowelsList = Arrays.asList('a','A','e','E','u','U','o', 'O');

             vowels.addAll(vowelsList);

             int n = s.length();
             int left = 0;
             int right = n -1;
             char[] chars= s.toCharArray();
             while (left< right){
                 char charLeft = chars[left];
                 char charRight = chars[right];


                 if (!vowels.contains(charLeft)){
                     left++;
                     continue;
                 }
                 if (!vowels.contains(charRight)){
                     right--;
                     continue;
                 }

                 //swap the values
                  char temp = chars[left];
                 chars[left] = chars[right];
                 chars[right] = temp;
                 left ++;
                 right --;

             }
             return new String(chars);
    }

    public static boolean isAnagram(String s, String t) {
            if (s != null && t!= null && s.length() != t.length() ){
                return false;
            }

            HashMap<Character, Integer> charsFreq = new HashMap<>();

            for (int i =0; i<s.length(); i++){
                char ch = s.charAt(i);
                charsFreq.put(ch, charsFreq.getOrDefault(ch, 0) +1);

            }

            for (int i = 0; i< t.length(); i++){
                char ch = t.charAt(i);
                if (!charsFreq.containsKey(ch)){
                    return false;
                }else{
                    charsFreq.put(ch, charsFreq.get(ch) -1);
                    if (charsFreq.get(ch) == 0){
                        charsFreq.remove(ch);
                    }
                }

            }
            return true;
    }

    public static  int shortestDistance(String[] words, String word1, String word2) {
            if (words == null || words.length<2) return 0;
            int index1=0;
            int index2 =0;

            for (int i=0; i< words.length; i++){
                if (words[i].equals(word1)){
                    index1 = i;

                }else if (words[i].equals(word2)){
                    index2 = i;
                }
            }
            return Math.abs(index1 -  index2);
    }

    public static int numGoodPairs(int[] nums){
            if (nums.length ==1) return 0;
            HashMap<Integer, Integer> freq = new HashMap();
            int goodpairsCount = 0;

            for (int num: nums){
                freq.put(num, freq.getOrDefault(num, 0) +1);
//                goodpairsCount += freq.get(num) -1;
            }
             for (int k: freq.keySet()){
                 int n = freq.get(k);
                 goodpairsCount += n * (n-1)/2;
             }
            return goodpairsCount;


    }

    /*
     * Problem Statement
     * Given a non-negative integer x, return the square root of x rounded down to the nearest integer.
     * The returned integer should be non-negative as well.
     *
     * You must not use any built-in exponent function or operator.
     *
     * For example, do not use pow(x, 0.5) in c++ or x ** 0.5 in python.
     *
     * Example 1:
     *
     * Input: x = 8
     * Output: 2
     * Explanation: The square root of 8 is 2.8284, and since we need to return the floor of the square root (integer), hence we returned 2.
     * Example 2:
     *
     * Input: x = 4
     * Output: 2
     * Explanation: The square root of 4 is 2.
     * Example 3:
     *
     * Input: x = 2
     * Output: 1
     * Explanation: The square root of 2 is 1.414, and since we need to return the floor of the square root (integer), hence we returned 1.
     * Constraints:
     *
     * 0 <= x <= 231 - 1
     */

    /**
     * x = 8
     *  l= 2, r = 4 mid = 3, 9> 8, r = 2
     *  l = 2, r = 2 mid = 2 ,4<8 , s= 3 then r = 2
     *
     *
     * @param x
     * @return
     */

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
    public static void main(String[] args) {
        // add some test cases
        int[] nums1 = {1, 2, 3, 4};
        System.out.println("Test Case 1: " + containsDuplicate(nums1)); // false
        int[] nums2 = {1, 2, 3, 1};
        System.out.println("Test Case 2: " + containsDuplicate(nums2)); // true

        // panagrams
        System.out.println("P02-PANAGRAM");
        String sentence1 = "TheQuickBrownFoxJumpsOverTheLazyDog";
        System.out.println("Test Case 1: " + checkIfPangram(sentence1)); // true
        String sentence2 = "This is not a pangram";
        System.out.println("Test Case 2: " + checkIfPangram(sentence2));

        System.out.println("ValidPalindorm.....");
      sentence1 = "A man, a plan, a canal, Panama!";
        System.out.println("Is palindrome: " + isPalindrome(sentence1)); // true
       sentence2 = "Was it a car or a cat I saw?";
        System.out.println("Is palindrome: " + isPalindrome(sentence2)); // true
        String sentence3 = "Hello, World!";
        System.out.println("Is palindrome: " + isPalindrome(sentence3)); // false

        System.out.println("Reverse vowels....");

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

        System.out.println("ShortestesWordDistance.....");
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

        System.out.println("Good pairs.....");
         nums1 = new int[]{1, 2, 3, 1, 1, 3};
        System.out.println(Arrays.toString(nums1));
        System.out.println("Number of Good Pairs: " + numGoodPairs(nums1)); // 4

         nums2 = new int[]{1, 1, 1, 1};
        System.out.println(Arrays.toString(nums2));
        System.out.println("Number of Good Pairs: " + numGoodPairs(nums2)); // 6
        int[] nums3 = new int[]{1, 2, 3};
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


    }

    }




