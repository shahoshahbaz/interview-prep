package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.Utility.makeItBold;

/**
 * Given a root of the binary tree, return the sum of all nodes of the binary tree.
 */
public class P01SumOfAllNodes {
    public static int sumOfNodes(TreeNode root) {

        if (root == null) return 0;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int sum =0;

        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            sum += curr.val;
            if (curr.left != null) queue.offer(curr.left);
            if(curr.right != null) queue.offer(curr.right);
        }

        return sum;


    }

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println("P01. Sum of all nodes in a binary tree");
        System.out.println("==================================");
        // Example 1
        TreeNode rootP01 = new TreeNode(1);
        rootP01.left = new TreeNode(2);
        rootP01.right = new TreeNode(3);
        System.out.println("Input: "+ rootP01.toLevelOrderString() +" ,Output: " + makeItBold(sumOfNodes(rootP01)+"")+
                " ,Expected: 6");


        // Example 2
        rootP01 = new TreeNode(4);
        rootP01.left = new TreeNode(9);
        rootP01.right = new TreeNode(7);
        rootP01.left.left = new TreeNode(2);
        rootP01.left.right = new TreeNode(6);
        System.out.println("Input: "+ rootP01.toLevelOrderString() +" ,Output: " + makeItBold(sumOfNodes(rootP01)+"")+
                " ,Expected: 28");
        // Example 3

        rootP01 = new TreeNode(10);
        rootP01.left = new TreeNode(5);
        rootP01.left.left = new TreeNode(3);
        rootP01.left.right = new TreeNode(7);
        rootP01.left.right.right = new TreeNode(9);
        System.out.println("Input: "+ rootP01.toLevelOrderString() +" ,Output: " + makeItBold(sumOfNodes(rootP01)+"")+
                " ,Expected: 34");

    }
}

