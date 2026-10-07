package com.grokingcodeinterview.testYourKnowledge47Medium;

import java.util.*;

import static com.Utility.makeItBold;

public class pattern36TestYourKnowledgeR1 {
/*
Problem Statement
Given an array of meeting intervals where intervals[i] = [starti, endi], return the minimum number of meeting rooms needed so that no meetings overlap.

Examples
Example 1:
Input: intervals = [[10, 15], [20, 25], [30, 35]]
Expected Output: 1
Justification: There are no overlapping intervals in the given list. So, only 1 meeting room is enough for all the meetings.
Example 2:
Input: intervals = [[10, 20], [15, 25], [24, 30], [5, 14], [22, 28], [1, 4], [27, 35]]
Expected Output: 3
Justification: Let's see how many meetings overlap at the same time:
[1, 4] starts first.
Then [5, 14] begins, no overlap yet.
[10, 20] overlaps with [5, 14]
[15, 25] overlaps with [10, 20]
[22, 28] overlaps with [15, 25]
[24, 30] overlaps with both [22, 28] and [15, 25]
[27, 35] overlaps with [24, 30]
Example 3:
Input: intervals = [[10, 20], [20, 30]]
Expected Output: 1
Justification: The end time of the first meeting is the same as the start time of the second meeting. So, one meeting can be scheduled right after the other in the same room.
Constraints:

1 <= intervals.length <= 10^4
0 <= starti < endi <= 10^6
 */

    public static int minMeetingRooms(int[][] intervals){
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);



        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int[] interval : intervals){
            if (!minHeap.isEmpty() && minHeap.peek()<=interval[0] ){
                minHeap.poll();

            }
                minHeap.offer(interval[1]);

        }

        return minHeap.size();

    }


/*
Problem Statement
Given an array of non-negative integers, where each integer represents the height of a vertical line positioned at index i. You need to find the two lines that, when combined with the x-axis, form a container that can hold the most water.

The goal is to find the maximum amount of water (area) that this container can hold.

Note: The water container's width is the distance between the two lines, and its height is determined by the shorter of the two lines.

Examples
Example 1:

Input: [1,3,2,4,5]
Expected Output: 9
Justification: The lines at index 1 and 4 form the container with the most water. The width is 3 * (4-1), and the height is determined by the shorter line, which is 3. Thus, the area is 3 * 3 = 9.
Example 2:

Input: [5,2,4,2,6,3]
Expected Output: 20
Justification: The lines at index 0 and 4 form the container with the most water. The width is 5 * (4-0), and the height is determined by the shorter line, which is 5. Thus, the area is 5 * 4 = 20.
Example 3:

Input: [2,3,4,5,18,17,6]
Expected Output: 17
Justification: The lines at index 4 and 5 form the container with the most water. The width is 17 * (5-4), and the height is determined by the shorter line, which is 17. Thus, the area is 17 * 1 = 17.
Constraints:

n == height.length
2 <= n <= 105
0 <= height[i] <= 104
 */

    public static int MaxAreaTwoPointer(int[] heights){
        int left  = 0;
        int right = heights.length -1;
        int maxArea = 0;

        while(left<right){
            int width = right - left;
            int height = Math.min(heights[left], heights[right]);
            maxArea = Math.max(maxArea, width* height);
            if (heights[left] < heights[right]){
                left ++;
            }else{
                right --;
            }

        }
        return maxArea;
    }

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


    public static  List<List<String>> groupAnagrams(String[] strs){
        List<List<String>> result = new ArrayList<>();
        if (strs == null ) return result;

        Map<String, List<String>> map = new LinkedHashMap<>();

        for (String str: strs){

            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);
            List <String> list = map.putIfAbsent(sorted, new ArrayList<String>());
            map.get(sorted).add(str);

        }

        return new ArrayList<>(map.values());
    }



    /*
    Problem Statement
    You have a string that represents encodings of substrings, where each encoding is of the form k[encoded_string], where k is a positive integer, and encoded_string is a string that contains letters only.

    Your task is to decode this string by repeating the encoded_string k times and return it. It is given that k is always a positive integer.

    Examples
    Input: "3[a3[c]]"
    Expected Output: "acccacccaccc"
    Justification: The inner 3[c] is decoded as ccc, and then a is appended to the front, forming acc. This is then repeated 3 times to form acccacccaccc.
    Input: "2[b3[d]]"
    Expected Output: "bdddbddd"
    Justification: The inner 3[d] is decoded as ddd, and then b is appended to the front, forming bddd. This is then repeated 2 times to form bddd bddd.
    Input: "4[z]"
    Expected Output: "zzzz"
    Justification: The 4[z] is decoded as z repeated 4 times, forming zzzz.
    Constraints:

    1 <= s.length <= 30
    s consists of lowercase English letters, digits, and square brackets '[]'.
    s is guaranteed to be a valid input.
    All the integers in s are in the range [1, 300].
 */

    /**
     * 3[a3[c]] :
     *  countStack :      *  StringStack:      *  k =      *  current: []
     *  3 :  countStack:      *  StringStack:      *  k = 3     *  current:[]
     *  [ :  countStack:[3      *  StringStack:[]      *  k = 0     *  current:[]
     *  a : countStack :[3      *  StringStack:[]      *  k =0      *  current:[a]
     *  3 : countStack :[3      *  StringStack:      *  k = 3     *  current:[a]
     *  [ : countStack : [3,3     *  StringStack:[a]      *  k =0      *  current:[]
     *  c : countStack : [3,3     *  StringStack:[a]      *  k =0      *  current:[c]
     *  ] : countStack : [3]     *  StringStack:[]      *  k =      *  current: [accc] , sb:[accc], freq 4
     *  ] : countStack :      *  StringStack:      *  k =      *  current:
     *
     */
    public static String decodeString(String str){
        Deque<Integer> countStack = new ArrayDeque<>();
        Deque<StringBuilder> stringStack = new ArrayDeque<>();
        int k = 0;
        StringBuilder inner = new StringBuilder();

        for (char ch: str.toCharArray()){
            if (Character.isDigit(ch)){
                k = k*10 + (ch -'0');
            }else if (ch =='['){
                countStack.push(k);
                k =0;

                stringStack.push(inner);
                inner = new StringBuilder();

            }else if(ch ==']'){

                int freq = countStack.pop();
                StringBuilder outer  = stringStack.pop();
                inner.append(outer.toString().repeat(freq));

                inner = outer;

            }else{
                inner.append(ch);
            }


        }

        return inner.toString();

    }

    /*
problem Statement:
Given an unsorted array of integers,
 find the length of the longest consecutive sequence of numbers in it. A consecutive sequence means the numbers in the sequence are contiguous without any gaps. For instance, 1, 2, 3, 4 is a consecutive sequence, but 1, 3, 4, 5 is not.

Examples
Input: [10, 11, 14, 12, 13] Output: 5
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

    public static int longestConsecutive(int[] nums){
        if (nums == null || nums.length ==0) return 0;

        Set<Integer> set = new HashSet<>();
        int maxLength = 0;
        for (int num: nums) set.add(num);

        for (int num: nums){
            if (!set.contains(num -1)){ // start of seq
                int current = num;
                int lengthCurr = 1;

                while (set.contains(current +1)){
                    current++;
                    lengthCurr ++;
                }

                maxLength = Math.max(maxLength, lengthCurr);

            }
        }

        return maxLength;

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

        System.out.println("=================================");
        System.out.println("P03.DecodeString");
        System.out.println("=================================");
        String strP03 = "3[a3[c]]";
        System.out.println("Input: " + strP03 +"output:" +makeItBold( decodeString(strP03)) +" expected: acccacccaccc");
        strP03 = "2[b3[d]]";
        System.out.println("Input: " + strP03 +"output:" + makeItBold(decodeString(strP03)) +" expected: bdddbddd");
        strP03 = "4[z]";
        System.out.println("Input: " + strP03 +"output:" + makeItBold(decodeString(strP03)) +" expected: zzzz");
        strP03 = "10[a]";
        System.out.println("Input: " + strP03 +"output:" + makeItBold(decodeString(strP03)) +" expected: aaaaaaaaaa");
        strP03 = "2[abc]3[cd]ef";
        System.out.println("Input: " + strP03 +"output:" + makeItBold(decodeString(strP03)) +" expected: abcabccdcdcdef");
        System.out.println("=================================");
        System.out.println("P02.GroupAnagrams");
        System.out.println("=================================");

        String[] strP02 = {"dog", "god", "hello"};
        System.out.println("Input: " + Arrays.toString(strP02) +"output:" + makeItBold(groupAnagrams(strP02).toString()) +" expected: [[dog, god], [hello]]");
        strP02 = new String[]{"listen", "silent", "enlist"};
        System.out.println("Input: " + Arrays.toString(strP02) +"output:" + makeItBold(groupAnagrams(strP02).toString()) +" expected: [[listen, silent, enlist]]");
        strP02 = new String[]{"abc", "cab", "bca", "xyz", "zxy"};
        System.out.println("Input: " + Arrays.toString(strP02) +"output:" + makeItBold(groupAnagrams(strP02).toString()) +" expected: [[abc, cab, bca], [xyz, zxy]]");

        System.out.println("==================================================================");
        System.out.println("P07. Container With Most Water");
        System.out.println("==================================================================");
        int[] heightsP07 = {1,3,2,4,5};
        System.out.println("Input: " + Arrays.toString(heightsP07) + ", output: " + makeItBold(MaxAreaTwoPointer(heightsP07) +"") + ", expected: " + makeItBold("9"));
        heightsP07 = new int[]{5,2,4,2,6,3};
        System.out.println("Input: " + Arrays.toString(heightsP07) + ", output: " + makeItBold(MaxAreaTwoPointer(heightsP07) +"") + ", expected: " + makeItBold("20"));
        heightsP07 = new int[]{2,3,4,5,18,17,6};
        System.out.println("Input: " + Arrays.toString(heightsP07) + ", output: " + makeItBold(MaxAreaTwoPointer(heightsP07) +"") + ", expected: " + makeItBold("17"));

        System.out.println("========================");
        System.out.println("P.21 Meeting Room");
        System.out.println("========================");
        int[][] intervalsP21= {{10, 20}, {15, 25}, {24, 30}, {5, 14}, {22, 28}, {1, 4}, {27, 35}};
        System.out.println("Input: " + Arrays.deepToString(intervalsP21) +", output: " + makeItBold(minMeetingRooms(intervalsP21) +"") + ", expected: " + makeItBold("3"));
        intervalsP21 = new int[][]{{10, 20}, {20, 30}};
        System.out.println("Input: " + Arrays.deepToString(intervalsP21) +", output: " + makeItBold(minMeetingRooms(intervalsP21) +"") + ", expected: " + makeItBold("1"));
        intervalsP21 = new int[][]{{10, 15}, {20, 25}, {30, 35}};
        System.out.println("Input: " + Arrays.deepToString(intervalsP21) +", output: " + makeItBold(minMeetingRooms(intervalsP21) +"") + ", expected: " + makeItBold("1"));


    }

}

