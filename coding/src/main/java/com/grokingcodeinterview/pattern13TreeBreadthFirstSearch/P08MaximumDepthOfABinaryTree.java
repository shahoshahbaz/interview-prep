package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

import static com.Utility.makeItBold;

/*
    Problem Statement:
 Given a binary tree, find its maximum depth (or height) using Tree BFS traversal.
 examples
    Example 1: Input: root = [3,9,20,null,null,15, 7] output: 3
    Example 2: Input: root = [1,null,2] output: 2
    Example 3: Input: root = [] output: 0
    Example 4: Input: root = [1] output: 1
    Constraints:
    The number of nodes in the tree is in the range [0, 105].
    -1000 <= Node.val <= 1000
 */
public class P08MaximumDepthOfABinaryTree {
    public static int findmaxDepth(TreeNode root){
        if (root == null) return 0;
        int currentDepth = 0;


        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            currentDepth++;
            for (int i =0; i<levelSize; i++){
                TreeNode currentNode = queue.poll();
                if (currentNode.left != null) queue.offer(currentNode.left);
                if (currentNode.right != null ) queue.offer(currentNode.right);
            }
        }
     return currentDepth;
    }

    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P08. Maximum Depth of a Binary Tree");
        System.out.println("==============================================================");

        // Example 1: Complete binary tree
        TreeNode rootP08 = new TreeNode(1);
        rootP08.left = new TreeNode(2);
        rootP08.right = new TreeNode(3);
        rootP08.left.left = new TreeNode(4);
        rootP08.left.right = new TreeNode(5);
        System.out.println("Input: " + rootP08.toLevelOrderString() +", output: " + makeItBold(findmaxDepth(rootP08) +"") + ", Expected: 3");
        // Example 2: Tree with only left children
        rootP08 = new TreeNode(1);
        rootP08.left = new TreeNode(2);
        rootP08.left.left = new TreeNode(3);
        rootP08.left.left.left = new TreeNode(4);
        System.out.println("Input: " + rootP08.toLevelOrderString() +", output: " + makeItBold(findmaxDepth(rootP08) +"") + ", Expected: 4");

        // Example 3: Tree with only right children
        rootP08 = new TreeNode(1);
        rootP08.right = new TreeNode(2);
        rootP08.right.right = new TreeNode(3);
        rootP08.right.right.right = new TreeNode(4);
        System.out.println("Input: " + rootP08.toLevelOrderString() +", output: " + makeItBold(findmaxDepth(rootP08) +"") + ", Expected: 4");


        // Example 4: Tree with one node
        rootP08 = new TreeNode(1);
        System.out.println("Input: " + rootP08.toLevelOrderString() +", output: " + makeItBold(findmaxDepth(rootP08) +"") + ", Expected: 1");

        // Example 5: Empty tree
        rootP08 = null;
        System.out.println("Input: " + rootP08 +", output: " + makeItBold(findmaxDepth(rootP08) +"") + ", Expected: 0");
         }
}

