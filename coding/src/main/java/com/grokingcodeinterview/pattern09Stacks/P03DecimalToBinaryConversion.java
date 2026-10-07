package com.grokingcodeinterview.pattern09Stacks;

import java.util.Stack;

import static com.Utility.makeItBold;

/*
 Given a positive integer n, write a function that returns its binary equivalent as a string. The function should not use any in-built binary conversion function.
 Example 1:  Input: 2  Output: "10"
 Example 2:  Input: 7  Output: "111"
 2/2 = 1
 2%2 = 0

 18 /2 = 9
 18 %2 = 0 push
 9 / 2 = 4
 9 % 2 = 1 push
 4 / 2 =2
 4 % 2 = 0  push
2 / 2 =1
2 % 2 =0 push
1/ 2 = 0
1 % 2  =1  push
 pop =10010
 */
public class P03DecimalToBinaryConversion {

    public static String convertDecimalToBinary(int num){
        StringBuilder sb = new StringBuilder();
        if (num ==0) return "0";

        Stack<String> stack = new Stack<>();
        while (num> 0){
            stack.push( String.valueOf(num%2)  );
             num /= 2;
        }

        while(!stack.isEmpty())
            sb.append(stack.pop());

        return sb.toString();
    }

    public static void main(String[] args) {
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


    }
}

