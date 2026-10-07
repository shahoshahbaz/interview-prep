package com.grokingcodeinterview.pattern09Stacks;

import java.util.Arrays;
import java.util.Stack;

import static com.Utility.makeItBold;

/*
 * Given a stack, sort it using only stack operations (push and pop).
 * You can use an additional temporary stack, but you may not copy the elements into any other data structure (such as an array). The values in the stack are to be sorted in descending order, with the largest elements on top.
 * Examples
 * 1. Input: [34, 3, 31, 98, 92, 23]     Output: [3, 23, 31, 34, 92, 98]
 * 2. Input: [4, 3, 2, 10, 12, 1, 5, 6]   Output: [1, 2, 3, 4, 5, 6, 10, 12]
 * 3. Input: [20, 10, -5, -1]     Output: [-5, -1, 10, 20]
 *   [34, 98,92 ,31, 23 ] temp[3 ,23 ,31 , ]  num = 3
 */

/**
 *
 *
 *
 *
 *
 */
public class P05SortingAStack {

    public static Stack<Integer> sortStack(Stack<Integer> stack){

        if ( stack == null) return null;
        Stack<Integer> helperStack = new Stack<>();

        while (!stack.isEmpty()){
            int num = stack.pop();

            while ( !helperStack.isEmpty() && helperStack.peek()> num){
                stack.push(helperStack.pop());
            }
            helperStack.push(num);

        }

        while (!helperStack.isEmpty()){
            stack.push(helperStack.pop());
        }
        return stack;

    }
    public static void printStack(Stack<Integer> stack) {
        // We print from top to bottom
        Stack<Integer> temp = new Stack<>();
        temp.addAll(stack);
        while (!temp.isEmpty()) {
            System.out.print(temp.pop() + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("P06. Sorting A Stack");
        System.out.println("=====================================");


        int[] numsP06 =  new int[]{34, 3, 31, 98, 92, 23};
        Stack<Integer> stack06 = new Stack<>();
        Arrays.stream(numsP06).forEach(stack06::push);
        System.out.println("Input: " + stack06.toString() + ", output: " + makeItBold(sortStack(stack06).toString()) + " ,Expected Output: [3, 23, 31, 34, 92, 98]");


        stack06 = new Stack<>();
        numsP06 = new int[]{4, 3, 2, 10, 12, 1, 5, 6};
        Arrays.stream(numsP06).forEach(stack06::push);

        System.out.println("Input: " + stack06.toString() + ", output: " + makeItBold(sortStack(stack06).toString()) + " ,Expected Output: [1, 2, 3, 4, 5, 6, 10, 12]");
        stack06 = new Stack<>();
        numsP06 = new int[]{4, 3, 2, 10, 12, 1, 5, 6};
        Arrays.stream(numsP06).forEach(stack06::push);

        System.out.println("Input: " + stack06.toString() + ", output: " + makeItBold(sortStack(stack06).toString()) + " ,Expected Output: [-5, -1, 10, 20]");

        Stack<Integer> emptyStack = new Stack<>();
        System.out.println("Input: " + emptyStack.toString() + ", output: " + makeItBold(sortStack(emptyStack).toString()) + " ,Expected Output: []");
    }
}

