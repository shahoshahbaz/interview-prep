package com.grokingcodeinterview.pattern10MonotonicStack;

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import static com.Utility.makeItBold;

public class Pattern10MonoticStackR1 {
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
    public static class  Entry{
        char ch;
        int counter =0;

        public Entry(char ch, int counter){
            this.ch = ch;
            this.counter = counter;
        }

}
public static String removeDuplicates(String str, int k){
        ArrayDeque<Entry> stack = new ArrayDeque<>();


        for (char ch: str.toCharArray()){

            if (stack.isEmpty() || stack.peek().ch != ch){
                stack.push(new Entry(ch, 1));
            }else if (stack.peek().ch == ch && stack.peek().counter == k -1){
                stack.pop();
            }else{
                stack.peek().counter++;
            }


        }

        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            Entry entry = stack.pop();
            for (int i =0;i<entry.counter; i++){
                sb.append(entry.ch);
            }
        }

        return sb.reverse().toString();


}
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

    public static int[] dailyTemperatures(int[] temperatures){
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int[] result = new int[temperatures.length -1];

        for (int i =0; i< temperatures.length; i++){

            while(!stack.isEmpty() && temperatures[i]> temperatures[stack.peek()]){
                int prevIndex = stack.pop();
                result[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }
        return result;
    }
    /*
    Problem Statement
    Given the head node of a singly linked list,
    modify the list such that any node that has a node
    with a greater value to its right gets removed. The function should return the head of the modified list.
    Examples:
    Input: 5 -> 3 -> 7 -> 4 -> 2 -> 1
    Output: 7 -> 4 -> 2 -> 1
    Explanation: 5 and 3 are removed as they have nodes with larger values to their right.
    Input: 1 -> 2 -> 3 -> 4 -> 5
    Output: 5
    Explanation: 1, 2, 3, and 4 are removed as they have nodes with larger values to their right.
   */

    /**
     * this is next greater element type problem so we use monotonic decreasing stack
     */
    public static Node removeNode(Node head){

        Deque<Node> stack = new java.util.LinkedList<>();
        Node current = head;

        while (current!= null ){
            // pop all smaller elements
            while (!stack.isEmpty() && stack.peek().data < current.data){ // monotonic increasing stack why? becuase we want to remove nodes which have greater value on right side
                stack.pop();
            }
            // then push current
            stack.push(current);
            // move the current pointer
            current = current.next;
        }

        // reconstruct the linked list from stack, why? becuase stack now contains only those nodes which do not have greater value on right side
        Node newHead = null;
        while (!stack.isEmpty()){ // reconstruct the linked list from stack, how? by popping elements from stack and linking them

            Node node = stack.pop();
            node.next = newHead; // link the popped node to the new head
            newHead = node; // update the new head
        }
        return newHead;
    }

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("P01.RemoveNodeFromLinkedList");
        System.out.println("==============================================");



        LinkedList listP01 = new LinkedList();
        listP01.createLinkedList(new int[]{5, 3, 7, 4, 2, 1});
        System.out.println("Input: " + makeItBold( listP01.getHead()+"") + ",  output: " + makeItBold(removeNode( listP01.getHead())+", Expected: 7 -> 4 -> 2 -> 1"));
        listP01.clear();
        listP01.createLinkedList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Input: " + makeItBold( listP01.getHead()+"") +
                ",  output: " + makeItBold(removeNode( listP01.getHead()) +", Expected: 5"));
        listP01.clear();
        listP01.createLinkedList(new int[]{5, 4, 3, 2, 1});
        System.out.println("Input: " + makeItBold( listP01.getHead()+"") + ",  output: " + makeItBold(removeNode( listP01.getHead()) +", Expected: 5 -> 4 -> 3 -> 2 -> 1"));

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

