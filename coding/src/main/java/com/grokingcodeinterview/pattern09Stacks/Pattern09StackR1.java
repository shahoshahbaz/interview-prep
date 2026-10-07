package com.grokingcodeinterview.pattern09Stacks;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

import static com.Utility.makeItBold;


public class Pattern09StackR1 {

    /*
     * problem statement
     * Given an absolute file path in a Unix-style file system,
     * simplify it by converting ".." to the previous directory and removing any "." or multiple slashes.
     * The resulting string should represent the shortest absolute path.
     * Examples
     * Example 1: Input: path = "/a//b////c/d//././/.."  Expected Output: "/a/b/c"
     * Explanation: Convert multiple slashes (//) into single slashes (/). "." refers to the current directory and is ignored.
     * ".." moves up one directory, so "d" is removed.
     *   The simplified path is "/a/b/c".
     *
     * Example 2  Input: path = "/../"  Expected Output: "/"
     * Explanation:  ".." moves up one directory, but we are already at the root ("/"), so nothing happens.
     * The final simplified path remains "/".
     *
     * Example 3  Input: path = "/home//foo/"  Expected Output: "/home/foo"
     * Explanation:  Convert multiple slashes (//) into single slashes (/).
     * The final simplified path is "/home/foo".
     *
     * Constraints:
     *  1 <= path.length <= 3000
     * path consists of English letters, digits, period '.', slash '/' or '_'.
     * path is a valid absolute Unix path.
     *
     *
     *
     */

    /** simplify it by converting ".." to the previous directory and removing any "." or multiple slashes.
     * shortest absolute path
     * Input: path = "/a//b////c/d//././/.."  Expected Output: "/a/b/c"
     *  convert multiple slashes (//) into single slashes (/). // =>1
     * "." refers to the current directory and is ignored.
     * .." moves up one directory, so "d" is removed.
     * /a//b////c/d//././/..
     *  /a//b////c/d//././/..
     *  ["", "a", "", "b", "", "", "", "c", "d", "", ".", ".", "", "..", ]
     */

    public static String simplifyPath(String path){
        if (path == null) return null;
        Deque<String> stack = new ArrayDeque<>();
        String[] components = path.split("/");

        for (String str: components){
            if (str.equals("") || str.equals(".")) {
                continue;
            }else if (str.equals("..")) {
                if (!stack.isEmpty()) stack.pop();
            }else{
                stack.push(str);
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            sb.insert(0, "/" +stack.pop());
        }

        return sb.length() ==0? "/ ":sb.toString();

    }
    /*
     * Given a stack, sort it using only stack operations (push and pop).
     * You can use an additional temporary stack, but you may not copy the elements
     * into any other data structure (such as an array). The values in the stack are to be sorted in descending order, with the largest elements on top.
     * Examples
     * 1. Input: [34, 3, 31, 98, 92, 23]     Output: [3, 23, 31, 34, 92, 98]
     * 2. Input: [4, 3, 2, 10, 12, 1, 5, 6]   Output: [1, 2, 3, 4, 5, 6, 10, 12]
     * 3. Input: [20, 10, -5, -1]     Output: [-5, -1, 10, 20]
     *   [34, 98,92 ,31, 23 ] temp[3 ,23 ,31 , ]  num = 3
     */

    /**
     *  s =[34, 3, 31, 98, 92, 23]
     *  tempstack [34,]
     *  s[34, 3, 31, 98, 92, 23], t=[]
     *
     *  s[ 3, 31, 98, 92, 23], t=[34, 3]

     *  s.top< temp.top => temp.push(s.pop)
     *  else
     *
     */
    public static Stack<Integer> sortStack(Stack<Integer > stack){

        Stack<Integer> tempStack = new Stack<>();

        while (!stack.isEmpty()){
            int num = stack.pop();
             while (!tempStack.isEmpty() && tempStack.peek()> num){
                 stack.push(tempStack.pop());
             }
             tempStack.push(num);

        }

        while (!tempStack.isEmpty()){
            stack.push(tempStack.pop());
        }
        return stack;

    }


    /*
     * Given an array, print the Next Greater Element (NGE) for every element.
     * The Next Greater Element for an element x is
     * the first greater element on the right side of x
     * in the array.
     * Elements for which no greater element exist, consider the next greater element as -1.
     * Examples
     * Example 1:  Input: [4, 5, 2, 25]   Output: [5, 25, 25, -1]
     * Example 1:   Input: [13, 7, 6, 12]   Output: [-1, 12, 12, -1]
     * Example 1:   Input: [1, 2, 3, 4, 5]   Output: [2, 3, 4, 5, -1]
     * Constraints:
     * 1 <= arr.length <= 104
     * -10^9 <= arr[i] <= 10^9
     */

    /**
     * Input: [4, 5, 2, 25]   Output: [5, 25, 25, -1]
     * we store indices in the stack
     *
     *
     */
    public static int[] nextGreaterElement(int[] nums){

        Stack<Integer> stack = new Stack<>();
        int n = nums.length;
        int[] result = new int[n];

        for (int i =0; i< n; i++){
            while(!stack.isEmpty() && nums[i]> nums[stack.peek()]){
                result[stack.pop()] = nums[i];

            }
            stack.push(i);

        }

        while (!stack.isEmpty()){
            result[stack.pop()]  =-1;
        }

        return result;

    }

    /*
    Given a positive integer n, write a function that returns its binary equivalent as a string. The function should not use any in-built binary conversion function.
 Example 1:  Input: 2  Output: "10"
 Example 2:  Input: 7  Output: "111"
 example 3: input = 3 output: 011
     */

    /**
     * num = 3
     * stack(110)
     * num = 3,  num%2 = 1  num/2 = 1
     * 1
     */

    public static String convertDecimalToBinary(int num){
        Deque<Integer> stack = new ArrayDeque<>();

        while (num>0){
            int rem = num%2;
            stack.push(rem);
            num = num/2;



        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){

            sb.append(stack.pop());

        }
        return sb.toString();
    }
    /*
     * Given a string, write a function that uses a stack to reverse the string. The function should return the reversed string.
     * Examples
     * Example 1: Input: "Hello, World!" Output: "!dlroW ,olleH"
     * Example 2: Input: "OpenAI" Output: "IAnepO"
     * Example 3: Inut: "Stacks are fun!" Output: "!nuf era skcatS"
     */

    public  static String reverseString(String str){
        Deque<Character> stack = new ArrayDeque<>();

        for (Character ch: str.toCharArray()){
            stack.push(ch);

        }

        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()){
            char ch = stack.pop();
            sb.append(ch);
        }
        return sb.toString();
    }


    /*
    Given a string s containing (, ), [, ], {, and } characters. Determine if a given string of parentheses is balanced.
    A string of parentheses is considered balanced if every opening parenthesis has a corresponding closing parenthesis in the correct order.
    Example 1: Input: String s = "{[()]}"; Expected Output: true
    Example 2: Input: string s = "{[}]"  Expected Output: false
            */

    public static  boolean isParenthesesBalanced(String str){

        Deque<Character> stack = new ArrayDeque<>();


        for (char ch: str.toCharArray()){

            if (ch == '(' || ch == '[' || ch == '{' ){
                stack.push(ch);
            }else if (ch == ']' || ch == ')' || ch == '}'){

                if (stack.isEmpty()){
                   return false;
                }else{
                    char popChar = stack.pop();

                    if ( (ch == ')' && popChar != '(') ||
                            (ch == ']'  && popChar != '[') ||
                            ( ch == '}'  && popChar != '{')){
                        return false;
                    }
                }
            }

        }
        return !stack.isEmpty();

    }



    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("P01.BalancedParentheses");
        System.out.println("=================================");

        // add more test cased

        String strP01 = "{[()]}";
        boolean resultP01 = isParenthesesBalanced(strP01);
        System.out.println("Input: \"" + strP01 + "\" is balanced? " + makeItBold(resultP01 +"") + "Expected: " + makeItBold("true")); // Expected: true

        strP01 = "{[}]";
        resultP01 = isParenthesesBalanced(strP01);
        System.out.println("Input: \"" + strP01 + "\" is balanced? " + makeItBold(resultP01 +"") + " Expected: " + makeItBold("false"));

        strP01 = "((()))";
        resultP01 = isParenthesesBalanced(strP01);
        System.out.println("Input: \"" + strP01 + "\" is balanced? " + makeItBold(resultP01 +"") + " Expected: " + makeItBold("true"));

        // Additional Test Cases
        strP01 = "(a + b) * (c + d)";
         resultP01 = isParenthesesBalanced(strP01);
        System.out.println("Input: \"" + strP01 + "\" is balanced? " + makeItBold(resultP01 +"") + " Expected: " + makeItBold("true"));

        strP01 = "((a + b)";
        resultP01 = isParenthesesBalanced(strP01);
        System.out.println("Input: \"" + strP01 + "\" is balanced? " + makeItBold(resultP01 +"") + " Expected: " + makeItBold("false"));


        System.out.println("=================================");
        System.out.println("P02.ReverseAString");
        System.out.println("=================================");
        // Test cases
        String strP02 = "Hello, World!";
        String resultP02 = reverseString(strP02);
        System.out.println("Input: \"" + strP02 + "\" Reversed: \"" + makeItBold(resultP02) + "\" Expected: " + makeItBold("!dlroW ,olleH"));
        strP02 = "OpenAI";
        resultP02 = reverseString(strP02);
        System.out.println("Input: \"" + strP02 + "\" Reversed: \"" + makeItBold(resultP02) + "\" Expected: " + makeItBold("IAnepO"));
        strP02 = "Stacks are fun!";
        resultP02 = reverseString(strP02);
        System.out.println("Input: \"" + strP02 + "\" Reversed: \"" +  makeItBold(resultP02) + "\" Expected: " + makeItBold("!nuf era skcatS"));

        System.out.println("=================================");
        System.out.println("P03. Decimal to Binary Conversion");
        System.out.println("=================================");

        int numP03 = 2;
        System.out.println("Input: " + makeItBold(numP03+"") + " => Output: " + makeItBold( convertDecimalToBinary(numP03) ) + " ,Expected: \"10\"");
        numP03 = 18;
        System.out.println("Input: " + makeItBold(numP03+"") + " => Output: " + makeItBold( convertDecimalToBinary(numP03) ) + " ,Expected: \"10010\"");
        numP03 = 0;
        System.out.println("Input: " + makeItBold(numP03+"") + " => Output: " + makeItBold( convertDecimalToBinary(numP03) ) + " ,Expected: \"0\"");
        numP03 = 1;
        System.out.println("Input: " + makeItBold(numP03+"") + " => Output: " + makeItBold( convertDecimalToBinary(numP03) ) + " ,Expected: \"1\"");
        numP03 = 7;
        System.out.println("Input: " + makeItBold(numP03+"") + " => Output: " + makeItBold( convertDecimalToBinary(numP03) ) + " ,Expected: \"111\"");

        System.out.println("=====================================");
        System.out.println("P04. Stack: Next Greater Element");
        System.out.println("=====================================");
        int[] inputP04 = {4, 5, 2, 25};
        int[] outputP04 = nextGreaterElement(inputP04);
        System.out.println("Input: " + Arrays.toString(inputP04) + " , Next Greater Elements: " + makeItBold(Arrays.toString(outputP04)) +
                "\t EXPECTED: " + "[5, 25, 25, -1]");

        inputP04 = new int[]{13, 7, 6, 12};
        outputP04 = nextGreaterElement(inputP04);
        System.out.println("Input: " + Arrays.toString(inputP04) + " , Next Greater Elements: " + makeItBold(Arrays.toString(outputP04)) +
                "\t EXPECTED: " + "[-1, 12, 12, -1]");
        inputP04 = new int[]{1, 2, 3, 4, 5};
        outputP04 = nextGreaterElement(inputP04);
        System.out.println("Input: " + Arrays.toString(inputP04) + " , Next Greater Elements: " + makeItBold(Arrays.toString(outputP04)) +
                "\t EXPECTED: " + "[2, 3, 4, 5, -1]");

        System.out.println("=====================================");
        System.out.println("P06. Sorting A Stack");
        System.out.println("=====================================");


        // add some test case

        // Test Case 1
        Stack<Integer> stack06 = new Stack<>();
        for (int n : new int[]{34, 3, 31, 98, 92, 23}){
            stack06.push(n);
        }


        System.out.println("Original Stack 1:" + stack06.toString() + makeItBold(sortStack(stack06).toString()) + "Expected Output: [3, 23, 31, 34, 92, 98]");

        // Test Case 2
        stack06 = new Stack<>();
        for (int n : new int[]{4, 3, 2, 10, 12, 1, 5, 6}) {
            stack06.push(n);
        }

        System.out.println("\nOriginal Stack 2: +" + stack06.toString() + makeItBold(sortStack(stack06).toString()) + " Expected Output: [1, 2, 3, 4, 5, 6, 10, 12]");
        stack06 = new Stack<>();
        for (int n : new int[]{20, 10, -5, -1}) {
            stack06.push(n);
        }

        System.out.println("\nOriginal Stack 3: +" + stack06.toString() + makeItBold(sortStack(stack06).toString()) + " Expected Output: [-5, -1, 10, 20]");
        // Edge Case: Empty stack
        Stack<Integer> emptyStack = new Stack<>();
        System.out.println("\nOriginal Empty Stack: +" + emptyStack.toString() + makeItBold(sortStack(emptyStack).toString()) + " Expected Output: []");
        System.out.println("============================================================");
        System.out.println("P07. Simplify Path");
        System.out.println("============================================================");
        String path07 = "/a//b////c/d//././/..";
        System.out.println("Input:" +path07 + " => Output:" + makeItBold(simplifyPath(path07)) +" ,Expected: /a/b/c");
        path07 = "/../";
        System.out.println("Input:" +path07 + " => Output:" + makeItBold(simplifyPath(path07)) +" ,Expected: /");
        path07 = "/home//foo/";
        System.out.println("Input:" +path07 + " => Output:" + makeItBold(simplifyPath(path07)) +" ,Expected: /home/foo");
        path07 = "/a/./b/../../c/";
        System.out.println("Input:" +path07 + " => Output:" + makeItBold(simplifyPath(path07)) +" ,Expected: /c");
        path07 = "/a//b////c/d//././/..";
        System.out.println("Input:" +path07 + " => Output:" + makeItBold(simplifyPath(path07)) +" ,Expected: /a/b/c");








    }
}

