package com.grokingcodeinterview.pattern05SlidingWindow;

import java.util.Arrays;
import java.util.HashMap;

import static com.Utility.makeItBold;

/*
Given an array, find the average of each subarray of ‘K’ contiguous elements in it.

Let's understand this problem with an example:

Array: [1, 3, 2, 6, -1, 4, 1, 8, 2], K=5
Here, we are asked to find the average of all subarrays of '5' contiguous elements in the given array.
Let's solve this:

For the first 5 numbers (subarray from index 0-4), the average is:
The average of next 5 numbers (subarray from index 1-5) is:
For the next 5 numbers (subarray from index 2-6), the average is:
...
Here is the final output containing the averages of all subarrays of size 5:

Output: [2.2, 2.8, 2.4, 3.6, 2.8]
 */

public class P00FindAverageEachSubarrayOfK {
    public static double[] findAverages(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];
        double windowSum = 0;
        int windowStart = 0;

        for (int i =0; i< k; i++){
            windowSum += nums[i];

        }
        result[windowStart] = windowSum;
        for (int windowEnd = k; windowEnd< nums.length; windowEnd ++){


            windowSum += 1;
        }

        return result;
    }
    public static int findLength(String str, int k){

        int longest = Integer.MIN_VALUE;
        int winStart =0;

        HashMap<Character, Integer> freqMap = new HashMap<>();

        for(int winEnd =0;winEnd < str.length(); winEnd ++){
            char ch = str.charAt(winEnd);
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) +1);
            while (freqMap.size()>k){
                char leftChar = str.charAt(winStart);
                freqMap.put(leftChar, freqMap.get(leftChar) -1);
                if(freqMap.get(leftChar) ==0){
                    freqMap.remove (leftChar);
                }

                winStart ++;
            }
            longest = Math.max(longest, winEnd - winStart+1);

        }
        return longest;
    }


    public static void main(String[] args) {
        // 4 tests can be added here

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
                + k + ": " + makeItBold(Arrays.toString(result)) +"\t" + "EXPECTED:"+ makeItBold(" [2.6666666666666665, 4.666666666666667, 3.6666666666666665, 4.333333333333333, 3.6666666666666665]"));
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



    }
}
