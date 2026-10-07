package com.grokingcodeinterview.pattern10MonotonicStack;

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import java.util.*;

import static com.Utility.makeItBold;

public class Pattern10MonoticStackR3 {
/*
Problem Statement
You are given a string s and an integer k. Your task is to remove groups of identical, consecutive characters from
 the string such that each group has exactly k characters.
  The removal of groups should continue until it's no longer possible to make any more removals.
  The result should be the final version of the string after all possible removals have been made.

Examples
Input: s = "abbbaaca", k = 3 Output: "ca"
Explanation: First, we remove "bbb" to get "aaaca". Then, we remove "aaa" to get "ca".
Input: s = "abbaccaa", k = 3 Output: "abbaccaa"
Explanation: There are no instances of 3 adjacent characters being the same.
Input: s = "abbacccaa", k = 3 Output: "abb"
Explanation: First, we remove "ccc" to get "abbaaa". Then, we remove "aaa" to get "abb".
Constraints:
1 <= s.length <= 10^55
2 <= k <= 10&44
s only contains lowercase English letters.
 */

    public static class Pair{
        char ch;
        int freq;

        public Pair (char ch, int freq){
            this.ch = ch;
            this.freq = freq;
        }
    }

    public static String removeDuplicates(String str, int k){

        Deque<Pair> stack = new ArrayDeque<>();

        for (char ch: str.toCharArray()){

            if (stack.isEmpty() || stack.peek().ch != ch ){
                stack.push(new Pair(ch, 1));
            }else{ // here top has same element as ch
                int freq = stack.peek().freq;
                if (freq == k-1){
                    stack.pop();
                }else{
                    stack.peek().freq++;
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        while(!stack.isEmpty()){
            Pair pair = stack.pop();
            for (int i =0; i< pair.freq; i++){
                sb.append(pair.ch);
            }
        }

        return sb.reverse().toString();
    }


    /*
Problem Statement
Given an array of integers arr, return the sum of the minimum values from all possible contiguous subarrays within arr.
 Since the result can be very large, return the final sum modulo (10^9+7).

Examples
Example 1: Input: arr = [3, 1, 2, 4, 5] Expected Output: 30
Explanation:
The subarrays are: [3], [1], [2], [4], [5], [3,1], [1,2], [2,4], [4,5], [3,1,2],
 [1,2,4], [2,4,5], [3,1,2,4], [1, 2, 4, 5], [3, 1, 2, 4, 5].
The minimum values of these subarrays are: 3, 1, 2, 4, 5, 1, 1, 2, 4, 1, 1, 2, 1, 1, 1.
Summing these minimums: 3 + 1 + 2 + 4 + 5 + 1 + 1 + 2 + 4 + 1 + 1 + 2 + 1 + 1 + 1 = 30.
Example 2: Input: arr = [2, 6, 5, 4] Expected Output: 36
Explanation:
The subarrays are: [2], [6], [5], [4], [2,6], [6,5], [5,4], [2,6,5], [6,5,4], [2,6,5,4].
The minimum values of these subarrays are: 2, 6, 5, 4, 2, 5, 4, 2, 4, 2.
Summing these minimums: 2 + 6 + 5 + 4 + 2 + 5 + 4 + 2 + 4 + 2 = 36.
Example 3: Input: arr = [7, 3, 8] Expected Output: 35
Explanation:
The subarrays are: [7], [3], [8], [7,3], [3,8], [7,3,8].
The minimum values of these subarrays are: 7, 3, 8, 3, 3, 3.
Summing these minimums: 7 + 3 + 8 + 3 + 3 + 3 = 27.
Constraints:

1 <= arr.length <= 3 * 104
1 <= arr[i] <= 3 * 104
 */

    /**
     *  Input: [3, 1, 2, 4, 5] Expected Output: 30
     *  Index:  0  1, 2  3  4
     *  value:  3  1  2  4  5
     *  next smaller [ 1, -1, -1, -1, -1]
     *  prev smaller [ 5, 5, 1, 2, 3]
     *

     */
    public static int sumSubArray(int[] nums){

        int n = nums.length;
        int[] prev = new int[n]; // store index
        int[] next = new int[n];// store index
         // use increasing monotic stack
        Deque<Integer> stack = new ArrayDeque<>();

        // find nextSmaller element;
        for (int i =0; i< n; i++){
            while (!stack.isEmpty() && nums[i]< nums[stack.peek()]){
                next[stack.pop()] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()){
            next[stack.pop()] = n;
        }

        stack.clear();


        // get smaller previous one
        for (int i = n-1; i>=0 ; i-- ){
            while (!stack.isEmpty() && nums[i]< nums[stack.peek()]){
                prev[stack.pop()] = i;
            }
            stack.push(i);
        }

        while(!stack.isEmpty()){
            prev[stack.pop()] = -1;
        }

        int sum =0;
        for (int i =0; i< n; i++){
            int left = i - prev[i];
            int right = next[i] - i;

            sum += (nums[i] * left * right) % 1_000_000_007;


        }

        return sum;
    }

    /*
 Problem statement
Given an array of integers temperatures representing daily temperatures,
 calculate how many days you have to wait until a warmer temperature.
  If there is no future day for which this is possible, put 0 instead.

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

    /**
     * [70, 73, 75, 71, 69, 72, 76, 73]  Output: [1, 1, 4, 2, 1, 1, 0, 0]
     * s:[73]
     * result:[1, 1,
     */

    public static int[] dailyTemperatures(int[] temperatures){
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int[] result = new int[temperatures.length];

        for (int i =0; i< temperatures.length; i++){
            while(!stack.isEmpty() && temperatures[i]> temperatures[stack.peek()]){
                int index = stack.pop();
                result[index] = i - index;

            }
            stack.push(i);
        }

        while (!stack.isEmpty()){
            result[stack.pop()] = 0;
        }
        return result;
    }
/*
Problem Statement
You are given a string s consisting of lowercase English letters.
 A duplicate removal consists of choosing two adjacent and equal letters and removing them.
 We repeatedly make duplicate removals on s until we no longer can.Return the final string after all such duplicate removals have been made.

Examples Input: s = "abccba"  Output: ""
Explanation: First, we remove "cc" to get "abba". Then, we remove "bb" to get "aa". Finally, we remove "aa" to get an empty string.

Input: s = "foobar" Output: "fbar" Explanation: We remove "oo" to get "fbar".
Input: s = "fooobar" Output: "fobar"
Explanation: We remove the pair "oo" to get "fobar".
Input: s = "abcd" Output: "abcd"
Explanation: No adjacent duplicates so no changes.
Constraints:
1 <= s.length <= 10^55
s consists of lowercase English letters.

*/

    /**
     * foobar
     */
    public static String removeAllAdjacentDuplicate(String str){
        if (str == null || str.length() ==1) return str;
        ArrayDeque<Character> stack = new ArrayDeque<>();

        for (char ch: str.toCharArray()){

            if (!stack.isEmpty() && stack.peek().equals(ch)){
                stack.pop();

            }else{
                stack.push(ch);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()){
            sb.append(stack.pop());
        }

        return sb.reverse().toString();


    }
    /*
 Problem Statement
 Given the head node of a singly linked list,
 modify the list such that any node that has a node
 with a greater value to its right gets removed. The function should return the head of the modified list.
 Examples:
 Input: 5->3->7->4->2->1  Output: 7->4->2->1
 Explanation: 5 and 3 are removed as they have nodes with larger values to their right.
 Input: 1->2->3->4->5  Output: 5
 Explanation: 1, 2, 3, and 4 are removed as they have nodes with larger values to their right.
 */

    /**
     *
     *  5->3->7->4->2->1
     *  c: 5
     *  stack(5 ->..,  3 ->..
     */
    public static Node removeNode(Node head){
        if (head == null) return null;

        Node curr = head;

        ArrayDeque<Node> stack = new ArrayDeque<>();

        while (curr != null){
            while (!stack.isEmpty() && curr.data> stack.peek().data){
                stack.pop();
            }
            stack.push(curr);

            curr = curr.next;
        }
        Node newHead = null;
        while (!stack.isEmpty()){
             Node newNode = stack.pop();
             newNode.next = newHead;
             newHead = newNode;
        }
        return newHead;
    }

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("P01.RemoveNodeFromLinkedList");
        System.out.println("==============================================");
        LinkedList listP01 = new LinkedList();
        listP01.createLinkedList(new int[]{5, 3, 7, 4, 2, 1});
        System.out.println("Input: " + listP01.getHead() + " ,output: " + makeItBold(removeNode( listP01.getHead())+" ,Expected: 7->4->2->1"));
        listP01.clear();
        listP01.createLinkedList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Input: " + listP01.getHead() + " ,output: " + makeItBold(removeNode( listP01.getHead()) +" ,Expected: 5"));
        listP01.clear();
        listP01.createLinkedList(new int[]{5, 4, 3, 2, 1});
        System.out.println("Input: " + listP01.getHead() + " ,output: " + makeItBold(removeNode( listP01.getHead()) +" ,Expected: 5->4->3->2->1"));
        // give me more complex
        listP01.clear();
        listP01.createLinkedList(new int[]{2, 7, 3, 5, 1, 6, 4});
        System.out.println("Input: " + listP01.getHead() + " ,output: " + makeItBold(removeNode( listP01.getHead()) +" ,Expected: 7->6->4"));
        System.out.println("==============================================");
        System.out.println("P02.RemoveAllAdjacentDuplicatesInString");
        System.out.println("==============================================");
        String inputP02 = "abccba";
        System.out.println("Input: " + inputP02 + ",  output: " + makeItBold(removeAllAdjacentDuplicate(inputP02)) + ", Expected: \"\"");
        inputP02 = "foobar";
        System.out.println("Input: " + inputP02 + ",  output: " + makeItBold(removeAllAdjacentDuplicate(inputP02)) + ", Expected: \"fbar\"");
        inputP02 = "fooobar";
        System.out.println("Input: " + inputP02 + ",  output: " + makeItBold(removeAllAdjacentDuplicate(inputP02)) + ", Expected: \"fobar\"");
        inputP02 = "abcd";
        System.out.println("Input: " + inputP02 + ",  output: " + makeItBold(removeAllAdjacentDuplicate(inputP02)) + ", Expected: \"abcd\"");
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

        System.out.println("==============================================");
        System.out.println("P06.Sum Of Subarray Minimums");
        System.out.println("==============================================");
        int[] numsP06 = new int[]{3,1,2,4,5};
        System.out.println("Input: " + Arrays.toString(numsP06) + " => Output: " + makeItBold(sumSubArray(numsP06)+"") + " Expected: 30");
        numsP06 = new int[]{2,6,5,4};
        System.out.println("Input: " + Arrays.toString(numsP06) + " => Output: " + makeItBold(sumSubArray(numsP06)+"") + " Expected: 36");
        numsP06 = new int[]{7,3,8};
        System.out.println("Input: " + Arrays.toString(numsP06) + " => Output: " + makeItBold(sumSubArray(numsP06)+"") + " Expected: 27");
        numsP06 = new int[]{11,81,94,43,3};
        System.out.println("Input: " + Arrays.toString(numsP06) + " => Output: " + makeItBold(sumSubArray(numsP06)+"") + " Expected: 444");
        System.out.println("=====================================================");
        System.out.println("P05. Remove All Adjacent Duplicates In String");
        System.out.println("====================================================");
        String strP05 = "abbbaaca";
        int kP05 = 3;

        String resultP05 = removeDuplicates(strP05, kP05);
        System.out.print("Input: " + strP05 + ", k= " + kP05 + " => Output: " +makeItBold( resultP05) + ", Expected: ca");
        strP05 = "abbaccaa";
        kP05 = 3;
        resultP05 = removeDuplicates(strP05, kP05);
        System.out.print("\nInput: " + strP05 + ", k= " + kP05 + " => Output: " +makeItBold( resultP05) + ", Expected: abbaccaa");
        strP05 = "abbacccaa";
        kP05 = 3;
        resultP05 = removeDuplicates(strP05, kP05);
        System.out.print("\nInput: " + strP05 + ", k= " + kP05 + " => Output: " +makeItBold( resultP05) + ", Expected: abb");






    }



}

