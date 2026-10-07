package com.grokingcodeinterview.pattern10MonotonicStack;

/*
Given an array of integers temperatures representing daily temperatures,
 calculate how many days you have to wait until a warmer temperature.
  If there is no future day for which this is possible, put 0 instead.

Examples
Example 1 Input: temperatures = [70, 73, 75, 71, 69, 72, 76, 73]  Output: [1, 1, 4, 2, 1, 1, 0, 0]
Explanation: The first day's temperature is 70 and the next day's temperature is 73 which is warmer. So for the first day, you only have to wait for 1 day to get a warmer temperature. Hence, the first element in the result array is 1. The same process is followed for the rest of the days.
Example 2: Input: temperatures = [73, 72, 71, 70] Output: [0, 0, 0, 0]
Explanation: As we can see, the temperature is decreasing every day. So, there is no future day with a warmer temperature. Hence, all the elements in the result array are 0.
Example 3 : Input: temperatures = [70, 71, 72, 73] Output: [1, 1, 1, 0]
Explanation: For the first three days, the next day is warmer. But for the last day, there is no future day with a warmer temperature. Hence, the result array is [1, 1, 1, 0].
Constraints:
1 <= temperatures.length <= 105
30 <= temperatures[i] <= 100
 */

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import static com.Utility.makeItBold;

/**
 * nextGreater
 * we should use decreasing stack
 *  [70, 73, 75, 71, 69, 72, 76, 73]  Output: [1, 1, 4, 2, 1, 1, 0, 0] stack =[]
 *  i =0;  result =[], stack: [0]
 *  i =1, 73 > peek so prevIndex = 0 then result[0] = 1- 0=1 stack[1]
 *  i = 2,  75> 73 so prevIndex = 1 then result [1] = 2 -1 = 1
 *  ..
 */
public class P04DailyTemperatures {

    public static int[] dailyTemperatures(int[] temperatures) {

        Deque<Integer> stack = new ArrayDeque<>();
        int[] result = new int[temperatures.length -1];

        for (int i =0; i< temperatures.length; i++){


            while(!stack.isEmpty() &&  temperatures[i]> temperatures[stack.peek()]){
                int prevIndex = stack.pop();
                result[prevIndex] = i - prevIndex;

            }
            stack.push(i);


        }
        return result;
    }
    public static void main(String[] args) {

        System.out.println("====================");
        System.out.println("P04. Daily Temperatures");
        System.out.println("====================");

        int[] numsP04 = {70, 73, 75, 71, 69, 72, 76, 73};
        int[] resultP04 = dailyTemperatures(numsP04);
        System.out.print("Input: " + Arrays.toString(numsP04) + " => Output: " + makeItBold(Arrays.toString(resultP04)) + ", Expected: [1, 1, 4, 2, 1, 1, 0, 0]");


        numsP04 = new int[]{73, 72, 71, 70};
        resultP04 = dailyTemperatures(numsP04);
        System.out.print("\nInput: " + Arrays.toString(numsP04) + " => Output: " + makeItBold(Arrays.toString(resultP04)) + ", Expected: [0, 0, 0, 0]");

        numsP04 = new int[]{70, 71, 72, 73};
        resultP04 = dailyTemperatures(numsP04);
        System.out.print("\nInput: " + Arrays.toString(numsP04) + " => Output: " + makeItBold(Arrays.toString(resultP04)) + ", Expected: [1, 1, 1, 0]");

        numsP04 = new int[]{70, 70, 70, 70};
        resultP04 = dailyTemperatures(numsP04);
        System.out.print("\nInput: " + Arrays.toString(numsP04) + " => Output: " + makeItBold(Arrays.toString(resultP04)) + ", Expected: [0, 0, 0, 0]");




    }
}

