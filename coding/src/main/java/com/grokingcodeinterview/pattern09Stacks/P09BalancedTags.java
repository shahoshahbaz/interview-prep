package com.grokingcodeinterview.pattern09Stacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
Problem: Balanced Tags (HTML/XML Validation)
Given a string representing a simplified HTML/XML document, determine whether all the tags are properly balanced and nested.
Rules:

Every opening tag <tag> must have a matching closing tag </tag> with the same tag name.
Tags must be closed in the correct nested order â€” the most recently opened tag must be the next one closed.
A closing tag with no matching open tag, or tags closed out of order, makes the document invalid.
Example:
Input: s = "<div><p></p></div>"
Output: true
Explanation: <div> opens, <p> opens, </p> closes p correctly, </div> closes div correctly.

Input: s = "<div><p></div></p>"
Output: false
Explanation: <p> was opened but </div> tries to close before </p> â€” wrong order.

Input: s = "<a><b><c></c></b></a>"
Output: true

Input: s = "<div></span>"
Output: false
Explanation: Closing tag name doesn't match the most recent opening tag.

Input: s = "<div>"
Output: false
Explanation: Opening tag never closed.

Input: s = "hello<b>world</b>"
Output: true
Explanation: Plain text outside tags is ignored â€” only tag structure matters.

 */
public class P09BalancedTags {
    public boolean isBalanced(String str) {
        List<String> tags = extractTags(str);

        Stack<String> stack = new Stack<>();

        for(String tag: tags){
            if(!isClosingTag(tag)){
                stack.push(tag);
            }else if(stack.isEmpty() || !stack.peek().equals( tag.substring(1))){
                return false;
            }else {
                stack.pop();
            }
        }

    return stack.isEmpty();
    }
    //hello<b>world</b> => ["b", "/b"]
    /**
     * index =0, ob= 5 cb = 7, sub(6, 5) = b
     * index = 8 ob

     */

    public static boolean isClosingTag(String tag){
        return tag.startsWith("/");
    }
    public static List<String> extractTags(String str){
        List<String> list = new ArrayList<>();

        int index =0;
        while(index< str.length()){
            int openBracket = str.indexOf('<', index );
            if(openBracket == -1 ) return list;
            int closeBracket = str.indexOf('>', openBracket);
            if( closeBracket == -1) return list;

            String tag = str.substring(openBracket +1, closeBracket );
            list.add(tag);
            index = closeBracket +1;
        }
        return list;
    }

}

