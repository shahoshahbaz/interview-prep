package com.grokingcodeinterview.pattern11HashMaps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.Utility.makeItBold;

public class pattern11HashMapR1 {


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

    public static int longestPalindrome(String str){
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch: str.toCharArray()){
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) +1);

        }

        int length = 0;
        boolean oddFound = false;

        for (int freq: freqMap.values()){
            if (freq %2 == 0)
                length += freq;
            else{
                length += (freq -1);
                oddFound= true;
            }
        }

        if(oddFound) length++;
        return length;
    }
    /*
 Problem Statement
Given two strings, one representing a ransom note and the other representing the available letters from a magazine,
 determine if it's possible to construct the ransom note using only the letters from the magazine. Each letter from the magazine can be used only once.

Example 1: Input: Ransom Note = "hello", Magazine = "hellworld" Expected Output: true
Justification: The word "hello" can be constructed from the letters in "hellworld".
Example 2: Input: Ransom Note = "notes", Magazine = "stoned" Expected Output: true
Justification: The word "notes" can be fully constructed from "stoned" from its first 5 letters.
Example 3: Input: Ransom Note = "apple", Magazine = "pale" Expected Output: false
Justification: The word "apple" cannot be constructed from "pale" as we are missing one 'p'.
Constraints:

1 <= ransomNote.length, magazine.length <= 10^5
ransomNote and magazine consist of lowercase English letters.
 */
    public static boolean canConstruct(String ransomNote, String magazine){
        HashMap<Character, Integer> freqMapMagazine = new HashMap<>();

        for (char ch: magazine.toCharArray()){
            freqMapMagazine.put(ch, freqMapMagazine.getOrDefault(ch, 0)+1);

        }

        for (char ch: ransomNote.toCharArray()){
            if (!freqMapMagazine.containsKey(ch)){
                return false;
            }else{
                freqMapMagazine.put(ch, freqMapMagazine.get(ch) -1);
                if (freqMapMagazine.get(ch) == 0)
                    freqMapMagazine.remove(ch);
            }
        }

        return true;

    }
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
        Map<Integer,Integer> freqMap = new HashMap<>();

        for (int num: nums){
            freqMap.put(num, freqMap.getOrDefault(num, 0)+1);
        }
        int highestValue = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> entry: freqMap.entrySet()){
            if (entry.getValue() ==1){
                highestValue = Math.max(highestValue, entry.getKey());
            }
        }
        return highestValue == Integer.MIN_VALUE? -1: highestValue;
    }



    public static int firstUniqueChar(String str){

        if (str ==null) return -1;

        Map<Character, Integer> freqMap = new HashMap<>();

        for (char ch: str.toCharArray()){
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) +1);
        }

        for (int i =0; i< str.length(); i++){
            if (freqMap.get(str.charAt(i)) ==1 ){
                return i;
            }
        }

        return -1;
    }

    public static class LRU {
        private int capacity;

        private class Node {
            int value;
            int key;
            Node next;
            Node prev;

            public Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        private Map<Integer, Node> map;

        Node head = new Node(0, 0);// MRU
        Node tail = new Node(0, 0);// LRU

        public LRU(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>();
            head.next = tail;
            tail.prev = head;

        }

        public Integer get(int key) {
            // check if key exist

            Node node = map.get(key);
            if (node == null) return null;

            removeNode(node);
            addToFront(node);

            return node.value;


        }

        public void put(int key, int value){
            // check if exisit
            Node node = map.getOrDefault(key, null);
            if(node != null){
                node.value = value;
                removeNode(node);
                addToFront(node);
                return;

        }

            // check the capacity => remove the LRU(tail
            if(map.size() == capacity){
                Node lru = tail.prev;
                removeNode(lru);
                map.remove(lru.key);

            }

            node = new Node(key, value);
            addToFront(node);
            map.put(key, node);
    }


        private void removeNode(Node node) {

            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;

        }

        public void addToFront(Node node) {
            node.next = head.next;
            node.prev = head;

            head.next.prev = node;
            head.next = node;

        }


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

        System.out.println("=====================================");
        System.out.println("P05.Ransom Note");
        System.out.println("=====================================");

        String magazine = "hellworld";
        String ransomNote = "hello";

        System.out.println("Input: Ransom Note = \""+ ransomNote +"\", Magazine = \""+ magazine +"\" => Output: "+ makeItBold(canConstruct(ransomNote, magazine) +"") +" ,Expected Output: true");
        magazine = "stoned";
        ransomNote = "notes";
        System.out.println("Input: Ransom Note = \""+ ransomNote +"\", Magazine = \""+ magazine +"\" => Output: "+ makeItBold(canConstruct(ransomNote, magazine) +"") +" ,Expected Output: true");
        magazine = "pale";
        ransomNote = "apple";
        System.out.println("Input: Ransom Note = \""+ ransomNote +"\", Magazine = \""+ magazine +"\" => Output: "+ makeItBold(canConstruct(ransomNote, magazine) +"") +" ,Expected Output: false");
        magazine = "aabbcc";
        ransomNote = "abc";
        System.out.println("Input: Ransom Note = \""+ ransomNote +"\", Magazine = \""+ magazine +"\" => Output: "+ makeItBold(canConstruct(ransomNote, magazine) +"") +" ,Expected Output: true");
        magazine = "abc";
        ransomNote = "aabbcc";
        System.out.println("Input: Ransom Note = \""+ ransomNote +"\", Magazine = \""+ magazine +"\" => Output: "+ makeItBold(canConstruct(ransomNote, magazine) +"") +" ,Expected Output: false");

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





    }
}

