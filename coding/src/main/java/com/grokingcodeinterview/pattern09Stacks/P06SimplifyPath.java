package com.grokingcodeinterview.pattern09Stacks;

import java.util.Stack;

import static com.Utility.makeItBold;

/*
 problem statement
 Given an absolute file path in a Unix-style file system,
 simplify it by converting ".." to the previous directory and removing any "." or multiple slashes.
 The resulting string should represent the shortest absolute path.
 Examples
 Example 1: Input: path = "/a//b////c/d//././/.."  Expected Output: "/a/b/c"
 Explanation: Convert multiple slashes (//) into single slashes (/). "." refers to the current directory and is ignored.
 ".." moves up one directory, so "d" is removed.
   The simplified path is "/a/b/c".
 Example 2  Input: path = "/../"  Expected Output: "/"
 Explanation:  ".." moves up one directory, but we are already at the root ("/"), so nothing happens.
 The final simplified path remains "/".
 Example 3  Input: path = "/home//foo/"  Expected Output: "/home/foo"
 Explanation:  Convert multiple slashes (//) into single slashes (/).
 The final simplified path is "/home/foo".
 Constraints:
  1 <= path.length <= 3000
 path consists of English letters, digits, period '.', slash '/' or '_'.
 path is a valid absolute Unix path.
 */
public class P06SimplifyPath {
    private static final boolean DEBUG = true;


    

    public static String simplifyPath(String path) {
        if (path == null)
            return null;
        Stack<String> stack = new Stack<>();
        String[] components = path.split("/");


        for (String dir: components){
            if(dir.equals("") || dir.equals(".")){
                continue;
            }else if(dir.equals("..")){
                if(!stack.isEmpty() ) stack.pop();
            }else {
                stack.push(dir);
            }
        }
        StringBuilder sb = new StringBuilder();

        // NOTE: java stack is iterable
        while (!stack.isEmpty()){
            sb.insert(0, "/" + stack.pop());
        }

        return sb.length() == 0? "/": sb.toString();

    }

    public static void main(String[] args) {
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
