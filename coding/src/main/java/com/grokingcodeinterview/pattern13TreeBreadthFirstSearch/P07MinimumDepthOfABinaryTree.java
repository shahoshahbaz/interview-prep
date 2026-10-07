package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

import static com.Utility.makeItBold;

/*
  Problem Statement
 Given a root of the binary tree, find the minimum depth of a binary tree.
 The minimum depth is the number of nodes along the shortest path from the root node to the nearest leaf node.
 
 Examples
 Example 1: Input: root = [3,9,20,null,null,15,7] output: 2
 Example 2: Input: root = [2,null,3,null,4,null,5,null] output: 5
 Example 3: Input: root = [1] output: 1
 Example 4: Input: root = [] output: 0
 Constraints:
 The number of nodes in the tree is in the range [0, 105].
 -1000 <= Node.val <= 1000
 
 */
public class P07MinimumDepthOfABinaryTree {
    public static int findMinDepth(TreeNode root){
        if (root == null) return 0;
        int currentDepth=0;


        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()){
            int levelSize = queue.size();
            currentDepth++;
            for (int i =0; i< levelSize; i++){
                TreeNode currentNode = queue.poll();
                if (currentNode.left == null && currentNode.right == null){
                    return currentDepth;
                }
                if (currentNode.left != null) queue.offer(currentNode.left);
                if (currentNode.right != null) queue.offer(currentNode.right);
            }
        }
        return currentDepth;



    }

    public static void main(String[] args) {

        System.out.println("==============================================================");
        System.out.println("P07. Minimum Depth of a Binary Tree");
        System.out.println("==============================================================");
        // Example 1: Complete binary tree
        TreeNode rootP07 = new TreeNode(1);
        rootP07.left = new TreeNode(2);
        rootP07.right = new TreeNode(3);
        rootP07.left.left = new TreeNode(4);
        rootP07.left.right = new TreeNode(5);
        System.out.println("Input: " + rootP07.toLevelOrderString() +makeItBold( findMinDepth(rootP07) +"")+ ",Expected: 2");

        // Example 2: Tree with only left children
        rootP07 = new TreeNode(1);
        rootP07.left = new TreeNode(2);
        rootP07.left.left = new TreeNode(3);
        rootP07.left.left.left = new TreeNode(4);
        System.out.println("Input: " + rootP07.toLevelOrderString() +" ,output: " +makeItBold( findMinDepth(rootP07) +"")+ ",Expected: 4");




        // Example 3: Tree with only right children
        rootP07 = new TreeNode(1);
        rootP07.right = new TreeNode(2);
        rootP07.right.right = new TreeNode(3);
        rootP07.right.right.right = new TreeNode(4);
        System.out.println("Input: " + rootP07.toLevelOrderString() + " ,output: " +makeItBold( findMinDepth(rootP07) +"")+ ",Expected: 4");


        // Example 4: Tree with one node
        rootP07 = new TreeNode(1);
        System.out.println("Input: " + rootP07.toLevelOrderString() +" ,output: " +makeItBold( findMinDepth(rootP07) +"")+ ",Expected: 1");

        // Example 5: Empty tree
        rootP07 = null;
        System.out.println("Input: " + rootP07 + " ,output: " +makeItBold( findMinDepth(rootP07) +"")+ ",Expected: 0");

    }
}

