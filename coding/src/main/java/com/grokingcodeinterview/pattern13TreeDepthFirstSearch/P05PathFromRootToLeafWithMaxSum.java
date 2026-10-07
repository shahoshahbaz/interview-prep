package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.ArrayList;
import java.util.List;

/* Max Sum Root-to-Leaf Path

Problem Statement:
Given the root of a binary tree, find the root-to-leaf path with the maximum sum of node values along that path. Return the maximum sum.

A leaf node is a node with no children.

        Example 1:

Input:
        1
      / \
     2   3
   /   / \
  9   4   5

Expected Output: 13

Justification:

Path 1 â†’ 2 â†’ 9 = 12
Path 1 â†’ 3 â†’ 4 = 8
Path 1 â†’ 3 â†’ 5 = 9
Maximum is 12...

Example 2

Input:
      10
    /  \
    5    15
  /      \
7        20

Expected Output: 45

Justification:

Path 10 â†’ 5 = 15
Path 10 â†’ 15 â†’ 7 = 32
Path 10 â†’ 15 â†’ 20 = 45 â† maximum
Maximum sum = 45

Example 2:

Input: root = [-3]  (single node)
Expected Output: -3

        (Single-node tree â€” that node is both root and leaf.)

/**
 *              [10,
 *            5,   -3,
 *         3,  2,   null, 11]  Output: 18
 */
public class P05PathFromRootToLeafWithMaxSum {

    public static class Result{
        int maxSum= Integer.MIN_VALUE;
        List<Integer> maxPath = new ArrayList<>();
    }


    private static int findMaxRootToLeafPath(TreeNode root){
        if (root == null ) return 0;
        Result result = new Result();
         findMaxRootToLeafPath(root,0,  new ArrayList<>(),result);
         return result.maxSum;
    }

    private static void findMaxRootToLeafPath(TreeNode currentNode,int currentSum , List<Integer> currentPath,  Result result){
        if (currentNode == null ){
            return;
        }
        currentPath.add(currentNode.val);
        currentSum += currentNode.val;

        if (currentNode.left == null && currentNode.right == null && currentSum >= result.maxSum){
                result.maxSum = currentSum;
                result.maxPath = new ArrayList<>(currentPath);
            }


        findMaxRootToLeafPath(currentNode.left, currentSum, currentPath, result);
        findMaxRootToLeafPath(currentNode.right, currentSum, currentPath, result);

        currentPath.remove(currentPath.size() -1);
    }
    public static void main(String[] args) {
        // Example 1: [1, 2, 3, null, 5]
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.right = new TreeNode(5);
        System.out.println("Example 1: Input = [1, 2, 3, null, 5]");
        System.out.println("Expected Output (max path sum) = [1, 2, 5]");
        System.out.print("Actual Output   = ");
        System.out.println(findMaxRootToLeafPath(root1));
        System.out.println();

        // Example 2: [10, 5, 12, 4, 7, null, 15]
        TreeNode root2 = new TreeNode(10);
        root2.left = new TreeNode(5);
        root2.right = new TreeNode(12);
        root2.left.left = new TreeNode(4);
        root2.left.right = new TreeNode(7);
        root2.right.right = new TreeNode(15);
        System.out.println("Example 2: Input = [10, 5, 12, 4, 7, null, 15]");
        System.out.println("Expected Output (max path sum) = [10, 12, 15]");
        System.out.print("Actual Output   = ");
        System.out.println(findMaxRootToLeafPath(root2));
        System.out.println();

        // Example 3: [1, 2, null, 3]
        TreeNode root3 = new TreeNode(1);
        root3.left = new TreeNode(2);
        root3.left.left = new TreeNode(3);
        System.out.println("Example 3: Input = [1, 2, null, 3]");
        System.out.println("Expected Output (max path sum) = [1, 2, 3]");
        System.out.print("Actual Output   = ");
        System.out.println(findMaxRootToLeafPath(root3));
        System.out.println();

        // Example 4: [1]
        TreeNode root4 = new TreeNode(1);
        System.out.println("Example 4: Input = [1]");
        System.out.println("Expected Output (max path sum) = [1]");
        System.out.print("Actual Output   = ");
        System.out.println(findMaxRootToLeafPath(root4));
        System.out.println();

        // Example 5: []
        TreeNode root5 = null;
        System.out.println("Example 5: Input = []");
        System.out.println("Expected Output (max path sum) = []");
        System.out.print("Actual Output   = ");
        System.out.println(findMaxRootToLeafPath(root5));
        System.out.println();

        // Example 6: [5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1]
        TreeNode root6 = new TreeNode(5);
        root6.left = new TreeNode(4);
        root6.right = new TreeNode(8);
        root6.left.left = new TreeNode(11);
        root6.left.left.left = new TreeNode(7);
        root6.left.left.right = new TreeNode(2);
        root6.right.left = new TreeNode(13);
        root6.right.right = new TreeNode(4);
        root6.right.right.right = new TreeNode(1);
        System.out.println("Example 6: Input = [5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1]");
        System.out.println("Expected Output (max path sum) = [5, 4, 11, 7]");
        System.out.print("Actual Output   = ");
        System.out.println(findMaxRootToLeafPath(root6));
        System.out.println();
    }

}

