package com.grokingcodeinterview.pattern05SlidingWindow;

import java.util.Arrays;
import java.util.HashMap;

import static com.Utility.makeItBold;

public class pattern05SlidingWindowR2 {
/*
 Problem statement
 You are visiting a farm to collect fruits.  The farm has a single row of fruit trees.
 You will be given two baskets, and your goal is to pick as many fruits as possible to be placed in the given baskets.
  You will be given an array of characters where each character represents a fruit tree. The farm has following restrictions:
 Each basket can have only one type of fruit. There is no limit to how many fruit a basket can hold.
 You can start with any tree, but you can’t skip a tree once you have started.
 You will pick exactly one fruit from every tree until you cannot, i.e., you will stop when you have to pick from a third fruit type.
 Write a function to return the maximum number of fruits in both baskets.

 Example 1: Input: arr=['A', 'B', 'C', 'A', 'C']  Output: 3
 Explanation: We can put 2 'C' in one basket and one 'A' in the other from the subarray ['C', 'A', 'C']

 Example 2: Input: arr = ['A', 'B', 'C', 'B', 'B', 'C']  Output: 5
 Explanation: We can put 3 'B' in one basket and two 'C' in the other basket. This can be done if we start with the second letter: ['B', 'C', 'B', 'B', 'C']

 NOTE:  Reworded Version (Generic Format):
 You are given an array of characters. Your task is to find the length of the longest subarray that contains at most two distinct characters.
 You can start at any index, but once you start, you must consider consecutive elements only (no skipping).
 Return the length of the longest contiguous subarray with at most two different characters.
 */

    /**
     * Input: arr=['A', 'B', 'C', 'A', 'C']  Output: 3
     * freqMap: A:1 B:1 C:1
     */
    public static int findMaxNumber(char[] chars){

        HashMap<Character, Integer> freqMap = new HashMap<>();

        int longest = Integer.MIN_VALUE;
        int windowStart =0;

        for (int windowEnd=0; windowEnd< chars.length; windowEnd++){
            char ch = chars[windowEnd];
            freqMap.put(ch, freqMap.getOrDefault(ch,0) +1);

            while (freqMap.size()>2){
                char startChar = chars[windowStart];
                freqMap.put(startChar, freqMap.get(startChar) -1);
                if (freqMap.get(startChar) ==0){
                    freqMap.remove(startChar);
                }
                windowStart ++;
            }
            longest = Math.max(longest, (windowEnd - windowStart)+1);
        }

        return longest== Integer.MIN_VALUE? 0: longest;

    }
    /*
 Problem Statement #
 Given a string, find the length of the longest substring in it  with no more than K distinct characters.
  You can assume that K is less than or equal to the length of the given string.
 Example 1:   Input: String="araaci", K=2  Output: 4
 Explanation: The longest substring with no more than '2' distinct characters is "araa".

 Example 2:  Input: String="araaci", K=1  Output: 2
 Explanation: The longest substring with no more than '1' distinct characters is "aa".

 Example 3:  Input: String="cbbebi", K=3  Output: 5
 Explanation: The longest substrings with no more than '3' distinct characters are "cbbeb" & "bbebi".
 */

    /**
     * longest substring no moare than K distincChars
     * Input: String="araaci", K=2  Output: 4
     */
    public static  int findLongestSubstringWithNoMoreThanKDistinctChars(String str, int k){

        HashMap<Character, Integer> freqMap = new HashMap<>();

        int longestSub = Integer.MIN_VALUE;


        int windowStart =0;
        for (int windowEnd=0; windowEnd< str.length(); windowEnd++){
            char ch = str.charAt(windowEnd);
            freqMap.put(ch, freqMap.getOrDefault(ch, 0)+1);

            if (freqMap.size()>k ){
                char startChar = str.charAt(windowStart);
                freqMap.put(startChar, freqMap.get(startChar) -1);
                if (freqMap.get(startChar) == 0){
                    freqMap.remove(startChar);
                }

                windowStart ++;
            }

            longestSub = Math.max(longestSub, (windowEnd - windowStart +1));



        }

        return longestSub == Integer.MIN_VALUE? 0: longestSub;

    }
    /*
Given an array of positive numbers and a positive number 'k,'
 find the maximum sum of any contiguous subarray of size 'k'.

Example 1:

Input: arr = [2, 1, 5, 1, 3, 2], k=3
Output: 9
Explanation: Subarray with maximum sum is [5, 1, 3].
Example 2:

Input: arr = [2, 3, 4, 1, 5], k=2
Output: 7
Explanation: Subarray with maximum sum is [3, 4].
*/

    /**
     * Input: arr = [2, 1, 5, 1, 3, 2], k=3     * Output: 9
     * s =0, e =0, ... e= 5
     */

    public static int findMaxSum(int[] nums, int k){


        int n = nums.length;
        if (n< k) return 0;
        int startWindow =0;
        int currentSum=0;
        int maxSum = Integer.MIN_VALUE;
        for (int endWindow = 0; endWindow<n; endWindow++ ){
            currentSum += nums[endWindow];
            if (endWindow>= k-1){
                maxSum = Math.max(maxSum, currentSum);
                currentSum -= nums[startWindow];
                startWindow ++;
            }

        }
        return maxSum;

    }

/*
Given an array, find the average of each subarray of ‘K’ contiguous elements in it.

Let's understand this problem with an example:

Array: [1, 3, 2, 6, -1, 4, 1, 8, 2], K=5
Here, we are asked to find the average of all subarrays of '5' contiguous elements in the given array. Let's solve this:

For the first 5 numbers (subarray from index 0-4), the average is:
The average of next 5 numbers (subarray from index 1-5) is:
For the next 5 numbers (subarray from index 2-6), the average is:
...
Here is the final output containing the averages of all subarrays of size 5:

Output: [2.2, 2.8, 2.4, 3.6, 2.8]
 */
    /**
     * Array: [1, 3, 2, 6, -1, 4, 1, 8, 2], K=5  output* [2.2, 2.8, 2.4, 3.6, 2.8] lenght = 5
     *  what is the size of output array ?
     *  nums.length = n, 9  - 5 +1
     *  k = 5,
     *  [1, 3, 2, 6, -1, 4, 1, 8, 2]
     *  [1, 3, 2, 6, -1] a[0] ... a[4]
     *  [3, 2, 6, -1, 4] a[1] .. a[5]
     *  [2, 6, -1, 4, 1] a[2] .. a[6]
     *  ..
     *  [-1, 4, 1, 8, 2]  a[5] .. a[9]
     *  startWinodw = 0
     *  endWindow = 0.. n
     *  s =0, ... k... n
     *
     *
     */
    public static double[] findAverages(int[] nums, int k){
        int n = nums.length;

        double [] averages = new double[n-k+1];

        int windowStart = 0;
        int sumWindow=0;
        for (int windowEnd =0; windowEnd < n; windowEnd++){
            sumWindow += nums[windowEnd]; // add the next element to the window

            if (windowEnd>=k -1){ // why k-1 ? because we need to include k elements in the window, so windowEnd start from 0 to k-1 element
                averages[windowStart] = (double) (sumWindow) /k;
                sumWindow -= nums[windowStart]; // remove the element going out of the window
                windowStart++; // r
            }


        }

        return averages;

    }

    public static void main(String[] args) {
        int[] numsP01 = {1, 3, 2, 6, -1, 4, 1, 8, 2};
        int k = 5;
        double[] result = findAverages(numsP01, k);
        System.out.println("Arrays: " + makeItBold(Arrays.toString(numsP01))  +" ,Averages of each subarray of size "
                + k + ": " + makeItBold(Arrays.toString(result)) +"\t" + "EXPECTED:"+ makeItBold(" [2.2, 2.8, 2.4, 3.6, 2.8]"));

        //more test cases can be added here
        numsP01 = new int[]{2, 5, 1, 8, 2, 3, 6};
        k = 3;
        result = findAverages(numsP01, k);
        System.out.println("Arrays: " + makeItBold(Arrays.toString(numsP01))  +" ,Averages of each subarray of size "
                + k + ": " + makeItBold(Arrays.toString(result)) +"\t" + "EXPECTED:"+ makeItBold(" [2.6666666666666665, 4.0, 3.6666666666666665, 4.333333333333333, 3.6666666666666665]"));
        numsP01 = new int[]{10, 20, 30, 40, 50};
        k = 2;
        result = findAverages(numsP01, k);
        System.out.println("Arrays: " + makeItBold(Arrays.toString(numsP01))  +" ,Averages of each subarray of size "
                + k + ": " + makeItBold(Arrays.toString(result)) +"\t" + "EXPECTED:"+ makeItBold(" [15.0, 25.0, 35.0, 45.0]"));

        numsP01 = new int[]{5, 15, 25, 35, 45, 55};
        k = 4;
        result = findAverages(numsP01, k);
        System.out.println("Arrays: " + makeItBold(Arrays.toString(numsP01))  +" ,Averages of each subarray of size "
                + k + ": " + makeItBold(Arrays.toString(result)) +"\t" + "EXPECTED:"+ makeItBold(" [20.0, 30.0, 40.0]"));

        System.out.println("p02. max subarrayof Size k");
        // Test Case 1: Normal case
        int[] nums1 = {2, 1, 5, 1, 3, 2};
        System.out.println("Test Case 1: " + findMaxSum(nums1, 3)); // Expected: 9

        // Test Case 2: Array with all positive numbers
        int[] nums2 = {1, 2, 3, 4, 5};
        System.out.println("Test Case 2: " + findMaxSum(nums2, 2)); // Expected: 9

        // Test Case 3: Array with a single element
        int[] nums3 = {10};
        System.out.println("Test Case 3: " + findMaxSum(nums3, 1)); // Expected: 10

        // Test Case 4: Array with size less than k
        int[] nums4 = {1, 2};
        System.out.println("Test Case 4: " + findMaxSum(nums4, 3)); // Expected: 0

        // Test Case 5: Array with all elements equal
        int[] nums5 = {5, 5, 5, 5};
        System.out.println("Test Case 5: " + findMaxSum(nums5, 2)); // Expected: 10

        // Test Case 6: Array with k equal to array size
        int[] nums6 = {1, 2, 3, 4};
        System.out.println("Test Case 6: " + findMaxSum(nums6, 4)); // Expected: 10

        // Test Case 7: Empty array
        int[] nums7 = {};
        System.out.println("Test Case 7: " + findMaxSum(nums7, 3)); // Expected: 0

        // Test Case 8: Large k value
        int[] nums8 = {1, 2, 3, 4, 5};
        System.out.println("Test Case 8: " + findMaxSum(nums8, 6)); // Expected: 0

        // Test Case 9: Array with negative numbers
        int[] nums9 = {-1, -2, -3, -4, -5};
        System.out.println("Test Case 9: " + findMaxSum(nums9, 2)); // Expected: -3

        // Test Case 10: Array with mixed positive and negative numbers
        int[] nums10 = {3, -2, 5, -1, 6};
        System.out.println("Test Case 10: " + findMaxSum(nums10, 3)); // Expected: 10

        System.out.println("================================================");
        System.out.println("P03. Longest Substring with K Distinct Characters");
        System.out.println("================================================");
        // Test Case 1: Example from the problem statement
        String strP03 = "araaci";
        int kP03 = 2;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("4"));

        // Test Case 2: Example from the problem statement
        strP03 = "araaci";
        kP03 = 1;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("2"));

        // Test Case 3: Example from the problem statement
        strP03 = "cbbebi";
        kP03 = 3;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("5"));

        // Test Case 4: Single character string
        strP03 = "a";
        kP03 = 1;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("1"));

        // Test Case 5: Empty string
        strP03 = "";
        kP03 = 2;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("0"));

        // Test Case 6: k equals string length
        strP03 = "abcde";
        kP03 = 5;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("5"));

        // Test Case 7: k greater than string length
        strP03 = "abc";
        kP03 = 5;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("3"));

        // Test Case 8: String with all identical characters
        strP03 = "aaaaa";
        kP03 = 1;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("5"));

        // Test Case 9: k = 0 (edge case)
        strP03 = "abc";
        kP03 = 0;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("0"));

        // Test Case 10: String with exactly k distinct characters
        strP03 = "abcba";
        kP03 = 3;
        System.out.println("Input: " + makeItBold(strP03) + ", k: " + makeItBold(String.valueOf(kP03)) +
                ", Output: " + makeItBold(String.valueOf(findLongestSubstringWithNoMoreThanKDistinctChars(strP03, kP03))) +
                ", Expected: " + makeItBold("5"));

        System.out.println("======================================");
        System.out.println("P04. Fruits into Baskets.");
        System.out.println("======================================");
        // add some test cases with printed expected output
        char[] charsP04 = new char[]{'A', 'B', 'C', 'B', 'B', 'C'};
        System.out.println("Input:" + makeItBold(Arrays.toString(charsP04)) +
                ", length of the longest subarray: " + makeItBold(""+ findMaxNumber(charsP04)) + ", Expected outPut: 5 " );
        charsP04 = new char[]{'A', 'A', 'A', 'A'};
        System.out.println("Input:" + makeItBold(Arrays.toString(charsP04)) +
                ", length of the longest subarray: " + makeItBold(""+ findMaxNumber(charsP04)) + ", Expected outPut: 4 " );

        charsP04 = new char[]{'A', 'B', 'C', 'D', 'E'};
        System.out.println("Input:" + makeItBold(Arrays.toString(charsP04)) +
                ", length of the longest subarray: " + makeItBold(""+ findMaxNumber(charsP04)) + ", Expected outPut: 2 " );
        // give me more edge test cases
        charsP04 = new char[]{'A', 'B', 'A', 'C', 'A', 'B', 'B', 'A'};
        System.out.println("Input:" + makeItBold(Arrays.toString(charsP04)) +
                ", length of the longest subarray: " + makeItBold(""+ findMaxNumber(charsP04)) + ", Expected outPut: 4" );
        charsP04 = new char[]{};
        System.out.println("Input:" + makeItBold(Arrays.toString(charsP04)) +
                ", length of the longest subarray: " + makeItBold(""+ findMaxNumber(charsP04)) + ", Expected outPut: 0 " );






    }

}
