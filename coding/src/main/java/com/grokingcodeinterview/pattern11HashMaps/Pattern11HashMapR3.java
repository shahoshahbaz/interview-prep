package com.grokingcodeinterview.pattern11HashMaps;

import java.util.*;

public class Pattern11HashMapR3 {
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

    public List<List<String>> groupAnagrams(String[] strs){
        List<List<String>> result = new ArrayList<>();

        if(strs == null || strs.length ==0) return result;
        if(strs.length ==1) {
            result.add(Collections.singletonList(strs[0]));
            return result;
        }

        HashMap<String, List<String>> map = new HashMap<>();
        for(String str: strs){
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);
            map.computeIfAbsent(sorted,  x -> new ArrayList<>() ).add(str);


        }

        return new ArrayList<>(map.values());
    }

}

