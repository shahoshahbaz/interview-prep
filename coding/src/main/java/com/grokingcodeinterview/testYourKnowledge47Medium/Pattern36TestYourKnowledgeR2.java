package com.grokingcodeinterview.testYourKnowledge47Medium;


import java.util.*;

public class Pattern36TestYourKnowledgeR2 {

    /*
Problem Statement
Given a list of strings, the task is to group the anagrams together.

An anagram is a word or phrase formed by rearranging the letters of another, such as "cinema", formed from "iceman"

You can return the answer in any order.

Examples
Example 1:
Input: ["dog", "god", "hello"]
Output: [["dog", "god"], ["hello"]]
Justification: "dog" and "god" are anagrams, so they are grouped together. "hello" does not have any anagrams in the list, so it is in its own group.
Example 2:
Input: ["listen", "silent", "enlist"]
Output: [["listen", "silent", "enlist"]]
Justification: All three words are anagrams of each other, so they are grouped together.
Example 3:
Input: ["abc", "cab", "bca", "xyz", "zxy"]
Output: [["abc", "cab", "bca"], ["xyz", "zxy"]]
Justification: "abc", "cab", and "bca" are anagrams, as are "xyz" and "zxy".
Constraints:

1 <= strs.length <= 10^4
0 <= strs[i].length <= 100
strs[i] consists of lowercase English letters.
 */

    /**
     * input: ["dog", "god", "hello"]
     * freqMap (d : 1, o:1, d: 1)
     * // number of chars in string so if it is not, then move on
     */


    public static List<List<String>> groupAnagrams(String[] strings){
        Map<String, List<String>> map = new HashMap<>();

        for (String str: strings){
            char[] chs = str.toCharArray();
            Arrays.sort(chs);

            String key = Arrays.toString(chs);
            List<String >list = map.getOrDefault(key, new ArrayList<>());
            list.add(str);
            map.put(key, list);



        }

        return new ArrayList<>(map.values());
    }

    /*
    problem Statement:
    Given an unsorted array of integers,
     find the length of the longest consecutive sequence of numbers in it. A consecutive sequence means the numbers in the sequence are contiguous without any gaps. For instance, 1, 2, 3, 4 is a consecutive sequence, but 1, 3, 4, 5 is not.

    Examples    Input: [10, 11, 14, 12, 13] Output: 5
    Justification: The entire array forms a consecutive sequence from 10 to 14.
    Input: [3, 6, 4, 100, 101, 102] Output: 3
    Justification: There are two consecutive sequences, [3, 4] and [100,101,102]. The latter has a maximum length of 3.
    Input: [z4, 3, 6, 2, 5, 8, 4, 7, 0, 1] Output: 9
    Justification: The longest consecutive sequences here are [0, 1, 2,, 3, 4, 5, 6, 7, 8].
    Input: [7, 8, 10, 11, 15] Output: 2
    Justification: The longest consecutive sequences here are [7,8] and [10,11], both of length 2.
    Constraints:

    0 <= nums.length <= 10^5
    -10^9 <= nums[i] <= 10^9
*/

    /**
     * [10, 11, 14, 12, 13]
     * {10}
     */
    public static int longestConsecutive(int[] nums){
        //[10, 11, 14, 12, 13]
        Set<Integer> set =new HashSet<>();
        int longestLength = 0;
        for (int num: nums){ set.add(num);        }

        for (int num: nums){
            if (!set.contains(num -1)){
                int current = num;
                int currentLength = 1;


            while (set.contains(current+1)){
                current ++;
                currentLength ++;
            }
            longestLength = Math.max(longestLength, currentLength);
            }
        }
        return longestLength;

    }
    public static void main(String[] args) {
        System.out.println("===============================");
        System.out.println("P20.LongestConsecutiveSequence");
        System.out.println("===============================");
        int[] numsP20 = {10, 11, 14, 12, 13};
        System.out.println("Input: "+ Arrays.toString(numsP20) + " => Output: "+ longestConsecutive(numsP20) +" ,Expected Output: 5");
        numsP20 = new int[]{3, 6, 4, 100, 101, 102};
        System.out.println("Input: "+ Arrays.toString(numsP20) + " => Output: "+ longestConsecutive(numsP20) +" ,Expected Output: 3");
        numsP20 = new int[]{4, 3, 6, 2, 5, 8, 4, 7, 0, 1};
        System.out.println("Input: "+ Arrays.toString(numsP20) + " => Output: "+ longestConsecutive(numsP20) +" ,Expected Output: 9");
        numsP20 = new int[]{7, 8, 10, 11, 15};
        System.out.println("Input: "+ Arrays.toString(numsP20) + " => Output: "+ longestConsecutive(numsP20) +" ,Expected Output: 2");
        numsP20 = new int[]{};
        System.out.println("Input: "+ Arrays.toString(numsP20) + " => Output: "+ longestConsecutive(numsP20) +" ,Expected Output: 0");
        numsP20 = new int[]{1};
        System.out.println("Input: "+ Arrays.toString(numsP20) + " => Output: "+ longestConsecutive(numsP20) +" ,Expected Output: 1");
        numsP20 = new int[]{1, 2, 0, 1};
        System.out.println("Input: "+ Arrays.toString(numsP20) + " => Output: "+ longestConsecutive(numsP20) +" ,Expected Output: 3");
    }


}

