package com.grokingcodeinterview.testYourKnowledge47Medium;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
problem Statement:
Given an unsorted array of integers,
 find the length of the longest consecutive sequence of numbers in it.
  A consecutive sequence means the numbers in the sequence are contiguous without any gaps.
   For instance, 1, 2, 3, 4 is a consecutive sequence, but 1, 3, 4, 5 is not.

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
public class P20LongestConsecutiveSequence {
    public  static int longestConsecutive(int[] nums) {
        if(nums == null || nums.length == 0) return 0;

        int maxLength = 0;
        Set<Integer> set = new HashSet<>();

        for (int num: nums )  set.add(num);


        for (int num: nums){
            if (!set.contains(num -1)){ // only start counting if num is the start of a sequence so this if condition will esure we are not in middle of a sequence
                // for example if we have 1, 2, 3, 4 in the array then when when
                // for num ==1,  num- 1 == 0 and it is not in the set sow we will start counting from 1
                // for num ==2 , num -1 == 1 and it is in the set so we will not start counting from 2 because we are in middle of a sequence
                // same for num ==3 and for
                int current = num;
                int currentLength = 1;

                while (set.contains(current +1)){
                    current++;
                    currentLength++;

                }
                maxLength = Math.max(maxLength, currentLength);
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
    }


}

