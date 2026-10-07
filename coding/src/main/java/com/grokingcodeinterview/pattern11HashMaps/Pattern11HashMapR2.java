package com.grokingcodeinterview.pattern11HashMaps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.Utility.makeItBold;

public class Pattern11HashMapR2 {

     /*
Problem Statement
Given an array of integers, identify the highest value that appears only once in the array. If no such number exists, return -1.

Example 1: Input: [5, 7, 3, 7, 5, 8] Expected Output: 8
Justification: The number 8 is the highest value that appears only once in the array.
Example 2: Input: [1, 2, 3, 2, 1, 4, 4] Expected Output: 3
Justification: The number 3 is the highest value that appears only once in the array.
Example 3: Input: [9, 9, 8, 8, 7, 7] Expected Output: -1
Justification: There is no number in the array that appears only once.
Constraints:
1 <= nums.length <= 2000
0 <= nums[i] <= 1000
 */

    public static int largeUniqueNumber(int[] nums){
        if (nums == null) return -1;
        if(nums.length == 1) return 0;

        Map<Integer, Integer> freqMap = new HashMap<>();

        for (int num: nums){
            freqMap.put(num, freqMap.getOrDefault(num, 0) +1);
        }
        int max = Integer.MIN_VALUE;
        for (int num: nums){
            if(freqMap.get(num) ==1){
                max =Math.max(max, num);
            }

        }

        return max == Integer.MIN_VALUE ? -1: max;
    }

/*
Problem Statement:
Given a string, determine the length of the longest palindrome that
can be constructed using the characters from the string.
You don't need to return the palindrome itself, just its maximum possible length.

Input: "applepie" Expected Output: 5
Justification: The longest palindrome that can be constructed from the string is "pepep", which has a length of 5.
There are are other palindromes too but they all will be of length 5.
Input: "aabbcc"Expected Output: 6
Justification: We can form the palindrome "abccba" using the characters from the string, which has a length of 6.
Input: "bananas"Expected Output: 5
Justification: The longest palindrome that can be constructed from the string is "anana", which has a length of 5.
Constraints:

1 <= s.length <= 2000
s consists of lowercase and/or uppercase English letters only.
 */

    /**
     * applepie (a:1, p: 2, l: 1, e: 2, i: 1)
     * 2+2+1, get any thing bigger than 1
     * bananas b: 1, a: 3, n: 2 s: 1
     */
    public static int longestPalindrome(String str){

        HashMap<Character, Integer> freqMap = new HashMap<>();

        for(char ch: str.toCharArray()){
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) +1);
        }

        int length =0;
        boolean oddFound = false;

        for (int freq: freqMap.values()){

            if ( freq %2 == 0){
                length += freq;
            }else{

                length += (freq -1);
                oddFound = true;

                }
        }

        if (oddFound) length ++;

        return length;

}
/*
Problem Statement
Given a string, determine the maximum number of times the word "balloon" can be formed using the characters from the string. Each character in the string can be used only once.
Example 1: Input: "balloonballoon" Expected Output: 2
Justification: The word "balloon" can be formed twice from the given string.
Example 2: Input: "bbaall"Expected Output: 0
Justification: The word "balloon" cannot be formed from the given string as we are missing the character 'o' twice.
Example 3: Input: "balloonballoooon"
Expected Output: 2

Justification: The word "balloon" can be formed twice, even though there are extra 'o' characters.
Example 4: Input: "loonbalxballpoon" Expected Output: 2
Example 5: Input: "leetcode" Expected Output: 0
Constraints:

1 <= text.length <= 104
text consists of lower case English letters only.
 */

    /**
     *  balloonballoooon (b: 2, a:2, l: 4,o:6, n: 2) : size: 5
     *  balloon (b:1, a: 1, l: 2, o:2, n: 1)  size = 5
     *  2/1 = 2, 2/1=2 , ;/4 = 2, 6/2 3 2 /2 = 2
     *  if the the character doesn't exist return 0;
     *
     *
     *
     *
     */
    public static int findMaximumNumberOfBalloons(String str){

        HashMap<Character, Integer> freqMapBalloon = new HashMap<>();
        for (char ch: "balloon".toCharArray()){
            freqMapBalloon.put(ch, freqMapBalloon.getOrDefault(ch, 0 )+1);

        }
        HashMap<Character, Integer> freqMapStr = new HashMap<>();
        for (char ch: str.toCharArray()){
            freqMapStr.put(ch, freqMapStr.getOrDefault(ch, 0)+ 1);
        }

//        if (freqMapBalloon.size() !=  freqMapStr.size()) return 0;

        int maxBallon = Integer.MAX_VALUE;
        for (char ch : freqMapBalloon.keySet()){
            if (!freqMapStr.containsKey(ch) ){
                return 0;
            }else{
                int possible =   freqMapStr.get(ch) / freqMapBalloon.get(ch);
                maxBallon = Math.min( maxBallon, possible );

           }


        }

        return maxBallon;



    }

    /*
    Problem Statement
    Given a string, identify the position of the first character that appears only once in the string.
    If no such character exists, return -1.

    Examples
    Example 1: Input: "apple" Expected Output: 0
    Justification: The first character 'a' appears only once in the string and is the first character.
            Example 2: Input: "abcab" Expected Output: 2
    Justification: The first character that appears only once is 'c' and its position is 2.
    Example 3: Input: "abab" Expected Output: -1
    Justification: There is no character in the string that appears only once.
            Constraints:

            1 <= s.length <= 10^5
    s consists of only lowercase English letters.
 */
    public static int firstUniqueChar(String str){
        if (str == null) return -1;
        if (str.length() == 1) return 0;
        Map<Character, Integer> freqMap = new HashMap<>();

        for (char ch: str.toCharArray()){
            freqMap.put(ch, freqMap.getOrDefault(ch, 0)+1);
        }

        for (int i = 0;i< str.length();i++){
            if(freqMap.get(str.charAt(i)) ==1){
                return i;
            }


        }
        return -1;
    }
    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("P01. First Non Repeating Character... ");
        System.out.println("===================================");
        System.out.println("Input:apple, Output: " +makeItBold(firstUniqueChar("apple") +"") + ", expected: 0");
        System.out.println("Input:abcab, Output: " +makeItBold(firstUniqueChar("abcab") +"") + ", expected: 2");
        System.out.println("Input:abab, Output: " +makeItBold(firstUniqueChar("abab") +"") + ", expected: -1");
        System.out.println("Input:aabbccdde, Output: " +makeItBold(firstUniqueChar("aabbccdde") +"") + ", expected: 8");
        System.out.println("Input:zzxyyx, Output: " +makeItBold(firstUniqueChar("zzxyyx") +"") + ", expected: -1");
        System.out.println("=====================================");
        System.out.println("P03.Maximum Number Of Balloons");
        System.out.println("=====================================");
        String strP93 = "balloonballoon";
        System.out.println("Input: "+ strP93 + " => Output: "+ makeItBold(findMaximumNumberOfBalloons(strP93)+"") +" ,Expected Output: 2");
        strP93 = "bbaall";
        System.out.println("Input: "+ strP93 + " => Output: "+ makeItBold(findMaximumNumberOfBalloons(strP93)+"") +" ,Expected Output: 0");
        strP93 = "balloonballoooon";
        System.out.println("Input: "+ strP93 + " => Output: "+ makeItBold(findMaximumNumberOfBalloons(strP93)+"") +" ,Expected Output: 2");
        strP93 = "loonbalxballpoon";
        System.out.println("Input: "+ strP93 + " => Output: "+ makeItBold(findMaximumNumberOfBalloons(strP93)+"") +" ,Expected Output: 2");
        strP93 = "leetcode";
        System.out.println("Input: " + strP93 + " => Output: " + makeItBold(findMaximumNumberOfBalloons(strP93) + "") + " ,Expected Output: 0");
        System.out.println("=====================================");
        System.out.println("P04.Longest Palindrome");
        System.out.println("=====================================");
        String str04 = "applepie";
        System.out.println("Input: "+ str04 + " => Output: "+ makeItBold(longestPalindrome(str04) +"") +" ,Expected Output: 5");
        str04 = "aabbcc";
        System.out.println("Input: "+ str04 + " => Output: "+ makeItBold(longestPalindrome(str04) +"") +" ,Expected Output: 6");
        str04 = "bananas";
        System.out.println("Input: "+ str04 + " => Output: "+ makeItBold(longestPalindrome(str04) +"") +" ,Expected Output: 5");
        str04 = "abccccdd";
        System.out.println("Input: "+ str04 + " => Output: "+ makeItBold(longestPalindrome(str04) +"") +" ,Expected Output: 7");
        str04 = "a";
        System.out.println("Input: "+ str04 + " => Output: "+ makeItBold(longestPalindrome(str04) +"") +" ,Expected Output: 1");
        str04 = "bb";
        System.out.println("Input: "+ str04 + " => Output: "+ makeItBold(longestPalindrome(str04) +"") +" ,Expected Output: 2");

        System.out.println("===========================");
        System.out.println("P02.LargestUniqueNumber");
        System.out.println("===========================");
        int[] numsP92 = {5, 7, 3, 7, 5, 8};
        System.out.println("Input: "+ Arrays.toString(numsP92) + " => Output: "+ largeUniqueNumber(numsP92) +" ,Expected Output: 8");
        numsP92 = new int[]{1, 2, 3, 2, 1, 4, 4};
        System.out.println("Input: "+ Arrays.toString(numsP92) + " => Output: "+ largeUniqueNumber(numsP92) +" ,Expected Output: 3");
        numsP92 = new int[]{9, 9, 8, 8, 7, 7};
        System.out.println("Input: "+ Arrays.toString(numsP92) + " => Output: "+ largeUniqueNumber(numsP92) +" ,Expected Output: -1");
        numsP92 = new int[]{2, 2, 3, 4};
        System.out.println("Input: "+ Arrays.toString(numsP92) + " => Output: "+ largeUniqueNumber(numsP92) +" ,Expected Output: 4");
        numsP92 = new int[]{2, 2, 3, 3};
        System.out.println("Input: "+ Arrays.toString(numsP92) + " => Output: "+ largeUniqueNumber(numsP92) +" ,Expected Output: -1");

    }
}

