package com.grokingcodeinterview.pattern37realInterviewProblems;

/*
Given the root of a binary tree and an integer targetSum, return true if the tree has a root-to-leaf path such that adding up all the values along the path equals targetSum.

A leaf is a node with no children.

Example: Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22, output: true
Example: Input: root = [1,2,3], targetSum = 5, output: false
Example: Input: root = [1,2], targetSum = 0, output: false
Example: Input: root = [1,2], targetSum = 1, output: false

Constraints:

The number of nodes in the tree is in the range [0, 5000].
-1000 <= Node.val <= 1000
-1000 <= targetSum <= 1000
 */

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

public class LC112 {

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;

        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }



    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("LC112: Path Sum");
        System.out.println("=====================================");
        TreeNode rootLC112 = new TreeNode(5);
        rootLC112.left = new TreeNode(4);
        rootLC112.right = new TreeNode(8);
        rootLC112.left.left = new TreeNode(11);
        rootLC112.right.left = new TreeNode(13);
        rootLC112.right.right = new TreeNode(4);
        rootLC112.left.left.left = new TreeNode(7);
        rootLC112.left.left.right = new TreeNode(2);
        rootLC112.right.right.right = new TreeNode(1);

        System.out.println("Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22, output: " + new LC112().hasPathSum(rootLC112, 22) + " Expected Output: true");
        rootLC112 = new TreeNode(1);
        rootLC112.left = new TreeNode(2);
        rootLC112.right = new TreeNode(3);
        System.out.println("Input: root = [1,2,3], targetSum = 5, output: " + new LC112().hasPathSum(rootLC112, 5) + " Expected Output: false");
        rootLC112 = new TreeNode(1);
        rootLC112.left = new TreeNode(2);
        System.out.println("Input: root = [1,2], targetSum = 0, output: " + new LC112().hasPathSum(rootLC112, 0) + " Expected Output: false");
        rootLC112 = new TreeNode(1);
        rootLC112.left = new TreeNode(2);
        System.out.println("Input: root = [1,2], targetSum = 1, output: " + new LC112().hasPathSum(rootLC112, 1) + " Expected Output: false");
    }
}

