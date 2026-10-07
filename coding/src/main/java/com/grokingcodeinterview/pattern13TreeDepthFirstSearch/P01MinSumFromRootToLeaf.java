package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;
import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeVisualizer;

import static com.Utility.makeItBold;

/*
 Problem Statement
 Given the root of a binary tree, explore all possible root-to-leaf paths
 , compute the sum of values along each path, and return the minimum sum.
 *
 A leaf node is a node with no children.

 Example 1:
 Input: root = [10, 5, 15, null, null, 7, 20] Expected Output: 15
 Justification: The path with the minimum sum is 10 -> 5. The sum is 10 + 5 = 15.
 */
public class P01MinSumFromRootToLeaf {


    public static int minRootToLeafSum(TreeNode root){
        if (root == null) return 0;

        int left = minRootToLeafSum(root.left);
        int right = minRootToLeafSum(root.right);

        if(root.left == null ) return root.val + right;
        if(root.right == null) return root.val + left;
        return root.val + Math.min(left, right);

    }

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("P01. P01MinimumSumFromRootToLeaf ");
        System.out.println("======================================");
        
        // Example 1: root = [10, 5, 15, null, null, 7, 20]
        TreeNode rootP01 = new TreeNode(10);
        rootP01.left = new TreeNode(5);
        rootP01.right = new TreeNode(15);
        rootP01.right.left = new TreeNode(7);
        rootP01.right.right = new TreeNode(20);
        TreeVisualizer.printTree(rootP01);
        System.out.println("Input: " + rootP01.toLevelOrderString() + " ,Output: " + makeItBold(minRootToLeafSum(rootP01) +"") + " Expected: 15");
         rootP01 = new TreeNode(7);
        System.out.println("Input: " + rootP01.toLevelOrderString() + " ,Output: " + makeItBold(minRootToLeafSum(rootP01) +"") + " Expected: 7");

         rootP01 = null;
        System.out.println("Input: " + rootP01 + " ,Output: " + makeItBold(minRootToLeafSum(rootP01) +"") + " Expected: " + Integer.MAX_VALUE);

        // Example 3: Left-skewed tree [5, 4, null, 3, null, 2, null, 1]
        rootP01 = new TreeNode(5);
        rootP01.left = new TreeNode(4);
        rootP01.left.left = new TreeNode(3);
        rootP01.left.left.left = new TreeNode(2);
        rootP01.left.left.left.left = new TreeNode(1);
        System.out.println("Input: " + rootP01.toLevelOrderString() + " ,Output: " + makeItBold(minRootToLeafSum(rootP01) +"") + " Expected: 15");
        // Example 4: Right-skewed tree [1, null, 2, null, 3, null, 4]
        rootP01 = new TreeNode(1);
        rootP01.right = new TreeNode(2);
        rootP01.right.right = new TreeNode(3);
        rootP01.right.right.right = new TreeNode(4);

        System.out.println("Input: " + rootP01.toLevelOrderString() + " ,Output: " + makeItBold(minRootToLeafSum(rootP01) +"") + " Expected: 10");

        // Example 5: Tree with negative values [2, -1, 3, null, null, -4, 5]
         rootP01 = new TreeNode(2);
        rootP01.left = new TreeNode(-1);
        rootP01.right = new TreeNode(3);
        rootP01.right.left = new TreeNode(-4);
        rootP01.right.right = new TreeNode(5);
        System.out.println("Input: " + rootP01.toLevelOrderString() + " ,Output: " + makeItBold(minRootToLeafSum(rootP01) +"") + " Expected: 1");
    }
}

