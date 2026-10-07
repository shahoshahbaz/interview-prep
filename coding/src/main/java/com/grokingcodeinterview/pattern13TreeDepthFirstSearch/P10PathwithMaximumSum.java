package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;
import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeVisualizer;

/**
Find the path with the maximum sum in a given binary tree. Write a function that returns the maximum sum.
A path can be defined as a sequence of nodes between any two nodes and doesnâ€™t necessarily pass through the root. The path must contain at least one node.
 Example 1  Input: [1, 2, 3] Output: 6
 Example 2  Input: [-10, 9, 20, null, null, 15, 7] Output: 42
 Example 3  Input: [2, -1] Output: 2
 Example 4  Input: [1, -2, -3, 1, 3, -2, null, -1] Output: 3
 Example 5  Input: [-3] Output: -3


 **/
public class P10PathwithMaximumSum {
        static class Result {
            int maxSum = Integer.MIN_VALUE;
        }
    public static int pathWithMaxSum(TreeNode root){
        Result result = new Result();
        if (root == null) return 0;
         dfs(root, result);
        return result.maxSum;
    }

    public static int dfs(TreeNode currentNode, Result result){
        if (currentNode == null) {
            return 0;
        }

        int leftSum = Math.max(0, dfs(currentNode.left, result)); // to avoid negative value
        int rightSum = Math.max(0, dfs(currentNode.right, result)); // to avoid negative value

        int currentMaxPath = leftSum+ rightSum + currentNode.val;
        result.maxSum = Math.max(result.maxSum, currentMaxPath);

        return currentNode.val + Math.max(leftSum, rightSum);

    }public static void main(String[] args) {
        // Example 1: [1, 2, 3]
        TreeNode root1 = TreeNode.buildTree(new Integer[]{1, 2, 3});
        TreeVisualizer.printTree(root1);
        int result1 = pathWithMaxSum(root1);
        System.out.println("Output: " + result1 + ", Expected: 6, Test Passed? " + (result1 == 6));

       // Example 2: [-10, 9, 20, null, null, 15, 7]
       TreeNode root2 = new TreeNode(-10);
       root2.left = new TreeNode(9);
       root2.right = new TreeNode(20);
       root2.right.left = new TreeNode(15);
       root2.right.right = new TreeNode(7);
       TreeVisualizer.printTree(root2);
       int result2 = pathWithMaxSum(root2);
       System.out.println("Output: " + result2 + ", Expected: 42, Test Passed? " + (result2 == 42));

       // Example 3: [2, -1]
       TreeNode root3 = TreeNode.buildTree(new Integer[]{2, -1});
       TreeVisualizer.printTree(root3);
       int result3 = pathWithMaxSum(root3);
       System.out.println("Output: " + result3 + ", Expected: 2, Test Passed? " + (result3 == 2));

       // Example 4: [1, -2, -3, 1, 3, -2, null, -1]
       TreeNode root4 = TreeNode.buildTree(new Integer[]{1, -2, -3, 1, 3, -2, null, -1});
       TreeVisualizer.printTree(root4);
       int result4 = pathWithMaxSum(root4);
       System.out.println("Output: " + result4 + ", Expected: 3, Test Passed? " + (result4 == 3));

       // Example 5: [-3]
       TreeNode root5 = TreeNode.buildTree(new Integer[]{-3});
       TreeVisualizer.printTree(root5);
       int result5 = pathWithMaxSum(root5);
       System.out.println("Output: " + result5 + ", Expected: -3, Test Passed? " + (result5 == -3));}

}

