package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.Utility.makeItBold;

/*
 Given a binary tree, populate an array to represent its level-by-level traversal.
 You should populate the
 values of all nodes of each level from left to right in separate sub-arrays.
 */
public class P02BinaryTreeLevelOrderTraversal {
    public static  List<List<Integer>> binaryTreeLevelOrdertraverse(TreeNode root){
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()){
            int levelSize = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                list.add(curr.val);

                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);

            }

            result.add(list);
        }
        return result;

    }
    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("P03. Binary Tree Level Order Traversal");
        System.out.println("=========================================================");

        // some test case example
        TreeNode rootP02 = new TreeNode(3);
        rootP02.left = new TreeNode(9);
        rootP02.right = new TreeNode(20);
        rootP02.right.left = new TreeNode(15);
        rootP02.right.right = new TreeNode(7);
        System.out.println("Input: "+ rootP02.toLevelOrderString() +" ,Output: " + makeItBold(binaryTreeLevelOrdertraverse(rootP02).toString())+
                " ,Expected: [[3], [9, 20], [15, 7]]");

        rootP02 = new TreeNode(1);
        rootP02.left = new TreeNode(2);
        rootP02.right = new TreeNode(3);
        rootP02.left.left = new TreeNode(4);
        rootP02.left.right = new TreeNode(5);
        System.out.println("Input: "+ rootP02.toLevelOrderString() +" ,Output: " + makeItBold(binaryTreeLevelOrdertraverse(rootP02).toString())+
                " ,Expected: [[1], [2, 3], [4, 5]]");

        rootP02 = new TreeNode(1);
        rootP02.left = new TreeNode(2);
        System.out.println("Input: "+ rootP02.toLevelOrderString() +" ,Output: " + makeItBold(binaryTreeLevelOrdertraverse(rootP02).toString())+
                " ,Expected: [[1], [2]]");


    }
}

