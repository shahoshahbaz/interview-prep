package com.grokingcodeinterview.pattern26Backtracking;

import java.util.HashSet;
import java.util.Set;

import static com.Utility.makeItBold;

/*
    Problem Statement
    Given a string s, return the maximum number of unique substrings that the given string can be split into.
    You can split string s into any list of non-empty substrings, where the concatenation of the substrings forms the original string.
     However, you must split the substrings such that all of them are unique.
    A substring is a contiguous sequence of characters within a string.
    Example 1:    Input: s = "aab"     Output: 2
    Explanation: Two possible ways to split the given string into maximum unique substrings are: ['a', 'ab'] & ['aa', 'b'], both have 2 substrings; hence the maximum number of unique substrings in which the given string can be split is 2.
    Example 2:  Input: s = "abcabc"  Output: 4
    Explanation: Four possible ways to split into maximum unique substrings are: ['a', 'b', 'c', 'abc'] & ['a', 'b', 'cab', 'c'] &  ['a', 'bca', 'b', 'c'] & ['abc', 'a', 'b', 'c'], all have 4 substrings.
    Constraints:
    1 <= s.length <= 16
    s contains only lower case English letters.
 */
public class P04SplitAStringIntoTheMaxNumberOfUniqueSubstrings {

    public static int maxUniqueSplit(String s){
        return splitAndCount(s, 0, new HashSet<>());
    }

    private static int splitAndCount(String str, int start,Set<String> set){
        // we  consume the whole string and we are at the end of string.
        if(start == str.length()) return set.size();

        int count = 0;
        // why i = start +1 ?
        // because we want to start from the next character after the current start index,
        // and we want to go until the end of the string, which is str.length()
        for(int i= start +1; i<=str.length(); i++){
            // why str.substring(start, i) ? because we want to get the substring from the current start index to the current i index,
            String string = str.substring(start, i);
            if(set.add(string)){

                count = Math.max(count, splitAndCount(str, i, set));
                set.remove(string);

            }

        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("P04. Split A String Into The Max Number Of Unique Substrings");
        System.out.println("===========================");
        String strP04 = "aab";
        System.out.println("Input: " + strP04 +", output: "+ makeItBold(maxUniqueSplit(strP04) +"") + " Expected Output: 2");
        strP04 = "abcabc";
        System.out.println("Input: " + strP04 +", output: "+ makeItBold(maxUniqueSplit(strP04) +"") + " Expected Output: 4");
        strP04 = "aaaaa";
        System.out.println("Input: " + strP04 +", output: "+ makeItBold(maxUniqueSplit(strP04) +"") + " Expected Output: 2");

    }
}
