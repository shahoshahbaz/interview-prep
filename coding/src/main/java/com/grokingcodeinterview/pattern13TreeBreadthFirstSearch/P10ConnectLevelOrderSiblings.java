package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

/*
Problem Statement
Given a root of the binary tree, connect each node with its level order successor.
The last node of each level should point to a null node.
Examples
Example 1: Input: root = [1, 2, 3, 4, 5, 6, 7] output: [1, 2, 3, 4, 5, 6, 7] (with next pointers connected as described
Example 2: Input: root = [12, 7, 1, 9, null, 10, 5] output: [12, 7, 1, 9, null, 10, 5] (with next pointers connected as described
example 3: Input: root = [1] output: [1] (with next pointer connected as described
example 4: Input: root = [] output: [] (with next pointer connected as described
example 5: Input: root = [1, 2, null, 3] output: [1, 2, null, 3] (with next pointer connected as described
example 6: Input: root = [1, null, 2, null, 3] output: [1, null, 2, null, 3] (with next pointer connected as described

 */
/**
 *  Example 1: Input: root = [1, 2, 3, 4, 5, 6, 7] output: [1, 2, 3, 4, 5, 6, 7] (with next pointers connected as described
 *
 *           1c -> null
 *          / \
 *         2p c-> 3 -> null
 *        / \  / \
 *       4 -> 5 -> 6 -> 7 -> null
 *
 *  give me more examples
 *  Example 2: Input: root = [12, 7, 1, 9, null, 10, 5] output: [12, 7, 1, 9, null, 10, 5] (with next pointers connected as described
 *       12 -> null
 *       / \
 *      7(p)   1(c -> null
 *     /   / \
 *    9p-> 10c  5 -> null
 *    output: [12, 7, 1, 9, null, 10, 5] (with next pointers connected as described
 */


import java.util.ArrayDeque;
import java.util.Deque;

import static com.Utility.makeItBold;

public class P10ConnectLevelOrderSiblings {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode next; // pointer to the next level order sibling

        TreeNode(int x) {
            val = x;
            left = null;
            right = null;
            next = null;
        }
        public String toLevelOrderString(){
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            Deque<TreeNode> queue = new ArrayDeque<>();
            queue.offer(this);

            while(!queue.isEmpty()){
                TreeNode curr = queue.poll();
                sb.append(curr.val).append(" ");
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
            sb.append("]");
            return sb.toString().trim();
        }

    }
    public static TreeNode connectLevelOrderSiblings(TreeNode  root){
        if (root == null) return null;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()){
            int levelSize = queue.size();

            TreeNode prev = null;
            for(int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                if (prev != null)
                    prev.next = curr;

                prev = curr;
                if(curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);
            }
            prev.next = null;
        }

        return root;
    }

    public static String getLevelOrderUsingNextString(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        TreeNode levelStart = root;

        while (levelStart != null) {
            sb.append("[");
            TreeNode curr = levelStart;
            levelStart = null;

            while (curr != null) {
                sb.append(curr.val).append(" ");

                if (levelStart == null) {
                    if (curr.left != null) levelStart = curr.left;
                    else if (curr.right != null) levelStart = curr.right;
                }
                curr = curr.next;
            }
            sb.append("-> null] ");
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P10. Connect Level Order Siblings");
        System.out.println("==============================================================");

        TreeNode rootP10 = new TreeNode(1);
        rootP10.left = new TreeNode(2);
        rootP10.right = new TreeNode(3);
        rootP10.left.left = new TreeNode(4);
        rootP10.left.right = new TreeNode(5);
        rootP10.right.left = new TreeNode(6);
        rootP10.right.right = new TreeNode(7);

        System.out.println("Input: " + rootP10.toLevelOrderString() + ", output:" + makeItBold(getLevelOrderUsingNextString(connectLevelOrderSiblings(rootP10))) + ", Expected: [1 -> null] [2 3 -> null] [4 5 6 7 -> null]");
         rootP10 = new TreeNode(12);
        rootP10.left = new TreeNode(7);
        rootP10.right = new TreeNode(1);
        rootP10.left.left = new TreeNode(9);
        rootP10.right.left = new TreeNode(10);
        rootP10.right.right = new TreeNode(5);
        System.out.println("Input: " + rootP10.toLevelOrderString() + ", output:" + makeItBold(getLevelOrderUsingNextString(connectLevelOrderSiblings(rootP10))) + ", Expected: [12 -> null] [7 1 -> null] [9 10 5 -> null]");


    }
}
