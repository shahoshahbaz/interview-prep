package com.grokingcodeinterview.pattern05SlidingWindow;

import java.util.HashMap;

public class pattern05SlidingWindowR3 {

    public static double[] findAverages(int[] nums, int k){

        int n = nums.length;
        if(n<k) return null;

        double[] result = new double[n -k+1];


        int currSum = 0;
        int windowStart =0;
        for (int i =0; i<k;i++){
            currSum += nums[i];
        }

        result[windowStart] = (double)currSum/k;
        windowStart++;
        for (int windowEnd = k; windowEnd<n ; windowEnd++){
            currSum = currSum + nums[windowEnd]- nums[ windowEnd - k];

            result[windowStart] = (double)currSum/k;
            windowStart++;


        }
        return result;

    }
    public static  int findMaxFruitCount(char[] chars, int k ){
        int maxFruit = Integer.MIN_VALUE;
        int start =0;

        HashMap<Character, Integer> map = new HashMap<>();

        for (int end = 0; end< chars.length; end++){
            char ch = chars[end];
            map.put(ch, map.getOrDefault(ch, 0) +1);

            while(map.size()>k){
                char lCh = chars[start];
                map.put(lCh, map.get(lCh) -1);
                if(map.get(lCh) ==0){
                    map.remove(lCh);
                }
                start++;

            }

            maxFruit = Math.max(maxFruit, end - start+1);
        }
        return maxFruit;
    }

    public static int findMinSubArrayLen(int[] nums, int sum){
        int smallestLength = Integer.MAX_VALUE;

        int start = 0;

        int currSum =0;
        for(int end = 0; end<nums.length; end++){

            currSum += nums[end];

            while(currSum>= sum){
                smallestLength = Math.min(smallestLength, end - start +1);
                currSum -= nums[start];
                start++;

            }

        }
        return smallestLength== Integer.MAX_VALUE? 0: smallestLength;

    }
    // Given a string, find the length of the longest substring without repeating characters.
    public static int findLongestSubstring(String str) {
        int longestSub = Integer.MIN_VALUE;
        int start =0;

        HashMap<Character, Integer> lastSeen = new HashMap<>();

        for(int end =0; end<str.length(); end++){
            char rightCh = str.charAt(start);
                if(lastSeen.containsKey(rightCh)){
                    start = Math.max(start, lastSeen.get(rightCh) +1);
                }
                lastSeen.put(rightCh, end);
                longestSub = Math.max(longestSub, end - start+1);

        }
        return longestSub;


    }

}
