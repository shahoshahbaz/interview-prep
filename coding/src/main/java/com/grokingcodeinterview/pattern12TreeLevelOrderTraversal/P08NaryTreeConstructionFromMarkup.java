package com.grokingcodeinterview.pattern12TreeLevelOrderTraversal;

import java.util.*;

public class P08NaryTreeConstructionFromMarkup {

    public static class NAryNode {
        public String val; // Value of the node
        public List<NAryNode> children; // List to store children of the current node

        // Default constructor
        public NAryNode() {
            children = new ArrayList<>(); // Initialize the children list
        }

        // Constructor with value
        public NAryNode(String _val) {
            val = _val;
            children = new ArrayList<>(); // Initialize the children list
        }

        // Constructor with value and children
        public NAryNode(String _val, List<NAryNode> _children) {
            val = _val;
            children = _children; // Assign provided children list
        }
    }
    public static List<String> extractTags(String markup) {
        List<String> tags = new ArrayList<>();  // fix: List<String>, ArrayList<>

        int index = 0;
        while (index < markup.length()) {        // fix: .length(), not .size()
            int openBracket = markup.indexOf('<', index);
            int closedBracket = markup.indexOf('>', openBracket);  // fix: indexOf capital O

            tags.add(markup.substring(openBracket + 1, closedBracket));  // fix: tags.add, not list.add
            index = closedBracket + 1;
        }

        return tags;
    }

    public static boolean isClosingTag(String str){
        return str.startsWith("/");
    }

    public static String getTagName(String str){
       if(isClosingTag(str)){
           return str.substring(1);
       }
        return str;
    }

    public static NAryNode buildTree(String markup) {
        NAryNode root = null;

        List<String> tags = extractTags(markup);
        Stack<NAryNode> stack = new Stack<>();  // fix: was `new LinkedList<>()` â€” type mismatch

        for (String tag : tags) {
            if (!isClosingTag(tag)) {
                NAryNode newNode = new NAryNode(getTagName(tag));  // fix: create node only here, use getTagName not raw tag
                if (stack.isEmpty()) {                      // fix: needs () â€” it's a method call
                    root = newNode;
                } else {
                    stack.peek().children.add(newNode);      // fix: .children.add(newNode), not .add(tag)
                }
                stack.push(newNode);                         // fix: push newNode (Node), not tag (String)
            } else { // is closing tag
                stack.pop();                                  // fix: typo `stak` -> `stack`, added `;`
            }
        }

        return root;
    }

    public  static List<List<String>> levelOrder(NAryNode root){
        List<List<String>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<NAryNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            List<String> list = new ArrayList<>();

            for(int i =0; i< levelSize; i++){
                NAryNode node = queue.poll();
                list.add(node.val);
                List<NAryNode > children = node.children;

                for(NAryNode child: children){
                    queue.offer(child);
                }


            }

            result.add(list);
        }
        return result;

    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("P08 N-ary Tree Construction From Markup");
        System.out.println("==================================================================");
        // I need 3 test case to test the construction of an N-ary tree from markup
        String markupP08 = "<root><child1></child1><child2></child2></root>";
            System.out.println("Test Case 1: " + markupP08 + " => " + levelOrder(buildTree(markupP08)) + ", expected output:[[root][child1, child2]]");
    }
}

