package com.grokingcodeinterview.pattern02Warmup;

import java.util.Arrays;
import java.util.HashMap;

public class pattern01WarmUPR3 {


     /*
    A phrase is a palindrome if,
    after converting all uppercase letters into lowercase letters
    and removing all non-alphanumeric characters,
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

    public static  boolean isPalindrome(String str){
        if (str.length() ==1) return true;

        int left =0;
        int right = str.length() -1;
        while (left<=right){

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

            if (Character.toLowerCase(leftChar) != Character.toLowerCase(rightChar)){
                return false;

            }

            left++;
            right --;

        }

 return true;

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
     * 8 2^3
     *
     */


        public static int sqrt(int num){
            if (num == 1 || num ==0) return num;

            int start =2;
            int end = num -1;
            while (start<= end){
                int mid = start + (end - start)/2;
                int result = mid * mid;
                if (result == num) {
                    return mid;
                }else if (result < num){
                    start = mid +1;
                }else{
                    end = mid -1;

                }
            }
            return end;



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
     * A pair (i, j) is called good if nums[i] == nums[j] and i < j.
     *
     *      * Example 1:
     *      *
     *      * Input: nums = [1,2,3,1,1,3]
     *      * Output: 4
     *      * Explanation: There are 4 good pairs, here are the indices: (0,3), (0,4), (3,4), (2,5).
     *
     *      [1, 3] [3, 2]  SO 3 + 1 = 4
     *      SET(1,
     */

    public static int numGoodPairs(int[] nums){

        HashMap <Integer,Integer> freq = new HashMap<>();
        int countGoodPais = 0;

        for (int num: nums){
            freq.put(num, freq.getOrDefault(num, 0 ) + 1);

        }
        for (int key: freq.keySet()){
            int n = freq.get(key);
            countGoodPais += n * (n -1)/2;

        }


        return countGoodPais;
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
     * Input: words = ["a", "c", "d", "b", "a"], word1 = "a", word2 = "b"    * Output: 1
     *  index1 = -1, index 2 = -1
     *  index1 = 0, index2 = -1,
     *  index1 =0, index2 = 3 shortest = 3
     *  index = 4, inidex 2 = 3, shortest =
     */

    public static int shortestDistance(String[] words, String t1, String t2){
        int index1 =-1;
        int index2 = -1;
        int shortest = Integer.MAX_VALUE;

        for (int i =0; i< words.length; i++){
            if (words[i].equals(t1)){
                index1= i;

            }
            if (words[i].equals(t2)){
                index2 = i;
            }

            if (index1 != -1 && index2 != -1){
                shortest = Math.min (shortest, Math.abs(index1 - index2));
            }
        }
        return shortest;
    }

    public static void main(String[] args) {
        System.out.println("P06. Shortest Word Distance...");
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

        int x1 = 8;
        System.out.println("Sqrt of " + x1 + " is: " + sqrt(x1)); // 2

        int x2 = 4;
        System.out.println("Sqrt of " + x2 + " is: " + sqrt(x2)); // 2

        int x3 = 2;
        System.out.println("Sqrt of " + x3 + " is: " + sqrt(x3)); // 1
        int x4 = 15;
        System.out.println("Sqrt of " + x4 + " is: " + sqrt(x4)); // 3);

        System.out.println("P03. Valid Palindrome..");
        String sentence1 = "A man, a plan, a canal, Panama!";
        System.out.println("Is palindrome: " + isPalindrome(sentence1)); // true
        String sentence2 = "Was it a car or a cat I saw?";
        System.out.println("Is palindrome: " + isPalindrome(sentence2)); // true
        String sentence3 = "Hello, World!";
        System.out.println("Is palindrome: " + isPalindrome(sentence3)); // false

    }
}
