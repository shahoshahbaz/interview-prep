package com.grokingcodeinterview.pattern12TreeLevelOrderTraversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class P07NaryTreeLevelOrderTraversal {

// Definition for a NAryNode.
 private static class NAryNode {
     public int val; // Value of the node
     public List<NAryNode> children; // List to store children of the current node

     // Default constructor
     public NAryNode() {
         children = new ArrayList<>(); // Initialize the children list
     }

     // Constructor with value
     public NAryNode(int _val) {
         val = _val;
         children = new ArrayList<>(); // Initialize the children list
     }

         // Constructor with value and children
     public NAryNode(int _val, List<NAryNode> _children) {
         val = _val;
         children = _children; // Assign provided children list
     }
 }
    public static List<List<Integer>> levelOrder(NAryNode root) {
        List<List<Integer>> result = new ArrayList<>(); // Result list to store levels
        if(root == null) return result;
        ArrayDeque<NAryNode> queue = new ArrayDeque<>();
        queue.add(root);

        while(!queue.isEmpty()){

            int levelSize = queue.size();
            List<Integer> listLevel = new ArrayList<>();

            for (int i =0; i< levelSize; i++){
                NAryNode node = queue.poll();
                listLevel.add(node.val);
                List<NAryNode> children = node.children;
                for(NAryNode child: children){
                    queue.offer(child);
                }

            }
            result.add(listLevel);
        }
        return result; // Return the level order traversal
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("P07 N-ary Tree Level Order Traversal");
        System.out.println("==================================================================");
        // I need 3 test case to test the level order traversal of an N-ary tree

        NAryNode rootP07= new NAryNode(1);
        rootP07.children.add(new NAryNode(3));
        rootP07.children.add(new NAryNode(2));
        rootP07.children.add(new NAryNode(4));
        rootP07.children.get(0).children.add(new NAryNode(5));
        rootP07.children.get(0).children.add(new NAryNode(6));
        List<List<Integer>> resultP07 = levelOrder(rootP07);
        System.out.println("Input: [1, null, 3, 2, 4, null, 5, 6], output: " + resultP07 + ", expected: [[1], [3, 2, 4], [5, 6]]");

        rootP07= new NAryNode(1);
        rootP07.children.add(new NAryNode(2));
        rootP07.children.add(new NAryNode(3));
        rootP07.children.add(new NAryNode(4));
        rootP07.children.add(new NAryNode(5));
        rootP07.children.get(1).children.add(new NAryNode(6));
        List<List<Integer>> resultP07_2 = levelOrder(rootP07);
        System.out.println("Input: [1, null, 2, 3, 4, 5, null, 6], output: " + resultP07_2 + ", expected: [[1], [2, 3, 4, 5], [6]]");

        rootP07= new NAryNode(1);
        rootP07.children.add(new NAryNode(2));
        rootP07.children.add(new NAryNode(3));
        rootP07.children.add(new NAryNode(4));
        rootP07.children.add(new NAryNode(5));
        rootP07.children.get(0).children.add(new NAryNode(6));
        rootP07.children.get(0).children.add(new NAryNode(7));
        rootP07.children.get(1).children.add(new NAryNode(8));
        rootP07.children.get(1).children.add(new NAryNode(9));
        rootP07.children.get(2).children.add(new NAryNode(10));
         List<List<Integer>> resultP07_3 = levelOrder(rootP07);
        System.out.println("Input: [1, null, 2, 3, 4, 5, null, 6, 7, null, 8, 9, null, 10], output: " + resultP07_3 + ", expected: [[1], [2, 3, 4, 5], [6, 7, 8, 9], [10]]");

    }
}

