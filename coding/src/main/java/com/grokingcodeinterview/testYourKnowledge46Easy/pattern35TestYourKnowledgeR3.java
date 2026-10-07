package com.grokingcodeinterview.testYourKnowledge46Easy;

import java.util.Map;
import java.util.Stack;

public class pattern35TestYourKnowledgeR3 {
    public boolean isValid(String s) {
        Map<Character, Character> map = Map.of(')', '(', '}', '{',']', '[');

        Stack<Character> stack = new Stack<>();

        for (char ch: s.toCharArray()){
            if(map.values().contains(ch)){
                stack.push(ch);
            }else if(map.keySet().contains(ch)){
                if(stack.isEmpty() && stack.peek() != map.get(ch)){
                    return false;
                }
                    stack.pop();

            }
        }

        return stack.isEmpty();


    }
}

