package com.grokingcodeinterview.pattern05SlidingWindow;

import java.util.Arrays;
import java.util.HashMap;

import static com.Utility.makeItBold;

public class Pattern05SlidingWindowR1 {
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
     * find longest list of subarray that contains at most two distinct chars
     * arr = ['A', 'B', 'C', 'B', 'B', 'C']  Output: 5
     * s =0, e =0,
     * e =0 freqMap (A, 1) maxLength =1
     * e = 1 freqMap(A, 1)(B, 1) maxLength = 2
     * e  = 2 freqpMap (A, 1)(B, 1) (C, 1) shrink
     * s =1 maxLength   = 2
     *
     */
    public static  int findMaxNumber(char[] chars ){

        if (chars == null ) return 0;
        if (
                chars.length ==1) return 1;

        int windowStart =0;
        HashMap<Character, Integer> freqMap = new HashMap<>();

        int maxLength = Integer.MIN_VALUE;

        for (int windowEnd = 0; windowEnd<chars.length; windowEnd++){
            char ch = chars[windowEnd];
            freqMap.put (ch, freqMap.getOrDefault(ch, 0) +1);


            while (freqMap.size()> 2){
                char charStart = chars[windowStart];
                freqMap.put(charStart,  freqMap.get(charStart) -1);
                if (freqMap.get(charStart) == 0){
                    freqMap.remove(charStart);
                }
                windowStart++;
            }

            maxLength = Math.max(maxLength, windowEnd - windowStart +1);



        }

        return maxLength;

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
     * no more than k meaning < =k
     * Input: String="araaci", K=2  Output: 4
     * s=0, longest = -2133154 e =0 condition k distinct char
     * s = 0, e = 0, map(a, 1)  longest =1
     * s = 0, e = 1 map [(a, 1), (r,1)] longest = 2
     * s = 0, e = 2 map [(a, 2), (r,1)] longest = 3
     * s = 0, e = 3 map [(a, 3), (r,1)] longest = 4
     * s =0 , e = 4  map [(a, 3), (r,1) (c, 1)] volidated longest = 4
     * s =1, e = 4 map [(a, 2), (r,1) (c, 1)]
     * s = 2 , e = 4, map [(a, 2),  (c, 1)] longest = max(4, (4-2+1) = 4
     * s = 2, e = 5 map [(a, 2),  (c, 1), (i, 1)] voilated
     * s = 3 e = 5 map
     */
    public static int findLongestSubstringWithNoMoreThanKDistinctChars(String str, int k){
        int windowStart = 0;
        int longestSub = Integer.MIN_VALUE;
        HashMap<Character, Integer> freqMap = new HashMap<>();

        for (int windowEnd = 0; windowEnd< str.length(); windowEnd ++){

            char ch = str.charAt(windowEnd);

            freqMap.put(ch, freqMap.getOrDefault(ch, 0) +1);

            while (freqMap.size()>k){
                char windowStartChar = str.charAt(windowStart);
                freqMap.put(windowStartChar, freqMap.get(windowStartChar) -1);
                if (freqMap.get(windowStartChar) == 0){
                    freqMap.remove(windowStartChar);
                }
                windowStart ++;
            }

            longestSub = Math.max(longestSub, (windowEnd - windowStart +1));
        }
    return longestSub ==Integer.MIN_VALUE? 0: longestSub ;
}

    /*
     * Given a string, find the length of the longest substring in it
     *  with no more than K distinct characters.
     *
     * You can assume that K is less than or equal to the length of the given string.
     *
     * Example 1:
     *
     * Input: String="araaci", K=2
     * Output: 4
     * Explanation: The longest substring with no more than '2' distinct characters is "araa".
     * Example 2:
     *
     * Input: String="araaci", K=1
     * Output: 2
     * Explanation: The longest substring with no more than '1' distinct characters is "aa".
     * Example 3:
     *
     * Input: String="cbbebi", K=3
     * Output: 5
     * Explanation: The longest substrings with no more than '3' distinct characters are "cbbeb" & "bbebi".
     */
/*
Given an array of positive integers and a number ‘S,’
find the length of the smallest contiguous subarray
whose sum is greater than or equal to 'S'. Return 0 if no such subarray exists.

Example 1:

Input: arr = [2, 1, 5, 2, 3, 2], S=7
Output: 2
Explanation:  The smallest subarray with a sum greater than or equal to '7' is [5, 2].
Example 2:

Input: arr = [2, 1, 5, 2, 8], S=7
Output: 1
Explanation: The smallest subarray with a sum greater than or equal to '7' is [8].
Example 3:

Input: arr = [3, 4, 1, 1, 6], S=8
Output: 3
Explanation: Smallest subarrays with a sum greater than or equal to '8' are [3, 4, 1] or [1, 1, 6].
Constraints:

1 <= S <=
1 <= arr.length <= 105
1 <= arr[i] <= 104
 */

    /**
     * smallest subarray whoes sum>=s(condition)
     * Input: arr = [2, 1, 5, 2, 3, 2], S=7      * Output: 2
     * [2, 1, 5, 2, 3, 2]
     * s =0, e = 0, sum = 2
     * s =0, e = 1, sum = 3
     * s = 0, e = 2 sum = 8>7 , smallestSub = 3, sum = 8 -2 = 6< 7, s = 1 out of while loop
     * s =1, e = 3, sum = 6+ 2> 7, smallestsub = min(3,3) = 3, sum = 8 - 1 = 7, s = 2
     * s = 2, e = 4, sum =7 smallestSub = min(3,3) = 3, sum =
     *
     */

    public static int findSmallestSubarrayWithAGreaterSum(int[] nums, int s){
        int smallestSub = Integer.MAX_VALUE;
        int windowStart =0;
        int sum=0;
        for (int windowEnd = 0; windowEnd < nums.length; windowEnd ++){
            sum += nums[windowEnd];

            while (sum>=s && windowStart<= windowEnd){
                smallestSub = Math.min(smallestSub, (windowEnd - windowStart +1));
                sum -= nums[windowStart];
                windowStart++;
            }

        }
        return smallestSub == Integer.MAX_VALUE ? 0: smallestSub;

    }
/*
problem statement
Given an array of positive integers and a number ‘S,’
find the length of the smallest contiguous subarray
whose sum is greater than or equal to 'S'. Return 0 if no such subarray exists.

Example 1:

Input: arr = [2, 1, 5, 2, 3, 2], S=7
Output: 2
Explanation: The smallest subarray with a sum greater than or equal to '7' is [5, 2].
Example 2:

Input: arr = [2, 1, 5, 2, 8], S=7
Output: 1
Explanation: The smallest subarray with a sum greater than or equal to '7' is [8].
Example 3:

Input: arr = [3, 4, 1, 1, 6], S=8
Output: 3
Explanation: Smallest subarrays with a sum greater than or equal to '8' are [3, 4, 1] or [1, 1, 6].
Constraints:

1 <= S <=
1 <= arr.length <= 105
1 <= arr[i] <= 104
 */

    /**
     * length of smallest subarray  its sum >= 'S'
     * Input: arr = [2, 1, 5, 2, 3, 2], S=7      * Output: 2
     * we=0,.., 2 2+1+ 5 = 8 sum = 8 8>7 yes, then
     *
     *
     *
     *
     */

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
     *  arr = [2, 1, 5, 1, 3, 2], k=3
     *  [2, 1, 5] k = 3,
     *  [1, 5, 3] nums[3 - 3]
     */

    public static int findMaxSum(int[] nums, int k){
        if (nums.length < k) return 0;

        int windowStart =0;
        int windowSum =0;
        int maxSum = Integer.MIN_VALUE;

        for (int windowEnd =0; windowEnd< k; windowEnd++){
            windowSum += nums[windowEnd];
        }

        maxSum = Math.max(windowSum, maxSum);

        for (int windowEnd= k; windowEnd< nums.length; windowEnd++){
            windowSum += nums[windowEnd];
            windowSum -= nums[windowEnd -k];
            maxSum =  Math.max(windowSum, maxSum);
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
     * [1, 3, 2, 6, -1, 4, 1, 8, 2], K=5      output* [2.2, 2.8, 2.4, 3.6, 2.8]
     * length =  arrayLength -k +1
     *
     * 8* 8/5 =
     */
    public static double[] findAverages(int[] nums, int k){
        int n = nums.length;

        double[] result = new double[n - k +1];

        double  windowSum =0;
        int windowStart = 0;
        for (int windowEnd = 0; windowEnd< n; windowEnd++){
            windowSum += nums[windowEnd];
            if (windowEnd >= k-1){
                result[windowStart] = windowSum/k;
                windowSum -= nums[windowStart];
                windowStart++;
            }
        }

        return result;





    }
    public static void main(String[] args) {

        int[] nums = {1, 3, 2, 6, -1, 4, 1, 8, 2};
        int k = 5;
        double[] result = findAverages( nums,k );
        System.out.print("Arrays: " + Arrays.toString(nums) );
        System.out.print(" ,Averages of each subarray of size " + k + ": ");
        System.out.println(Arrays.toString(result) +"\t" + "EXPECTED: [2.2, 2.8, 2.4, 3.6, 2.8]");

        //more test cases can be added here
        nums = new int[]{2, 5, 1, 8, 2, 3, 6};
        k = 3;
        result = findAverages(nums, k);
        System.out.print("Arrays: " + Arrays.toString(nums) );
        System.out.print(" ,Averages of each subarray of size " + k + ": ");
        System.out.println(Arrays.toString(result) +"\t" + "EXPECTED: [2.6666666666666665, 4.666666666666667, 3.6666666666665, 4.333333333333333, 3.6666666666666665]");

        nums = new int[]{10, 20, 30, 40, 50};
        k = 2;
        result = findAverages(nums, k);
        System.out.print("Arrays: " + Arrays.toString(nums) );
        System.out.print(" ,Averages of each subarray of size " + k + ":  ");
        System.out.println(Arrays.toString(result) +"\t" + "EXPECTED: [15.0, 25.0, 35.0, 45.0]");

        nums = new int[]{5, 15, 25, 35, 45, 55};
        k = 4;
        result = findAverages(nums, k);
        System.out.print("Arrays: " + Arrays.toString(nums) );
        System.out.print(" ,Averages of each subarray of size " + k + ":  ");
        System.out.println(Arrays.toString(result) +"\t" + "EXPECTED: [20.0, 30.0, 40.0]");

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

        System.out.println("============================");
        // Test Case 1: Example from problem statement
        int[] numsP02 = {2, 1, 5, 2, 3, 2};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=7 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 7)) +
                makeItBold(" Expected: 2"));

        // Test Case 2: Another example from problem
        numsP02 = new int[] {2, 1, 5, 2, 8};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=7 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 7)) +
                makeItBold(" Expected: 1"));

        // Test Case 3: Third example
        numsP02 = new int[]{3, 4, 1, 1, 6};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=8 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 8)) +
                makeItBold(" Expected: 3"));

        // Test Case 4: No subarray meets the condition
        numsP02 = new int[]{1, 2, 3, 4};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=15 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 15)) +
                makeItBold(" Expected: 0"));

        // Test Case 5: All elements equal
        numsP02 = new int[]{4, 4, 4, 4};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=12 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 12)) +
                makeItBold(" Expected: 3"));
        numsP02 = new int[]{4, 4, 4, 4};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=8 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 8)) +
                makeItBold(" Expected: 2"));

        // Test Case 6: Single element array that satisfies
        numsP02 = new int[]{10};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=5 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 5)) +
                makeItBold(" Expected: 1"));

        // Test Case 7: Single element array that doesn't satisfy
        numsP02 = new int[]{3};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=5 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 5)) +
                makeItBold(" Expected: 0"));

        // Test Case 8: Empty array
        numsP02 = new int[]{};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=5 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 5)) +
                makeItBold(" Expected: 0"));

        // Test Case 9: Target sum is 0
        numsP02 = new int[]{1, 2, 3};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=0 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 0)) +
                makeItBold(" Expected: 1"));

        // Test Case 10: Exactly matching sum
        numsP02 = new int[]{2, 3, 1, 2, 4, 3};
        System.out.println("Input: " + makeItBold(Arrays.toString(numsP02)) + ", S=7 "
                + makeItBold("Output: " + findSmallestSubarrayWithAGreaterSum(numsP02, 7)) +
                makeItBold(" Expected: 2"));

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
        char[] chars = new char[]{'A', 'B', 'C', 'B', 'B', 'C'};
        System.out.println("Input:" + makeItBold(Arrays.toString(chars)) +
                ", length of the longest subarray: " + makeItBold(""+ findMaxNumber(chars)) + ", Expected outPut: 5 " );
        chars = new char[]{'A', 'A', 'A', 'A'};
        System.out.println(findMaxNumber(chars)); // Expected output: 4
        chars = new char[]{'A', 'B', 'C', 'D', 'E'};
        System.out.println(findMaxNumber(chars)); // Expected output: 2


    }
}
