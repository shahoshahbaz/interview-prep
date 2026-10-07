package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.Utility.makeItBold;

/*
  Problem Statement
  Given a binary tree, populate an array to represent the averages of all of its levels.
  Constraints:
  The number of nodes in the tree is in the range [1, 104].
  -2^31 <= Node.val <= 2^31 - 1
 */
public class P05LevelAveragesInABinaryTree {
    public static List<Double> findLevelAverages(TreeNode root){
        List<Double> averages = new ArrayList<>();

        if(root == null) return averages;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            double sum = 0;
            for(int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                sum += curr.val;

                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);

            }
            averages.add((double)sum/ levelSize);
        }
        return averages;

    }
    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P05. Level Averages in a Binary Tree");
        System.out.println("==============================================================");

        TreeNode root04 = new TreeNode(12);
        root04.left = new TreeNode(7);
        root04.right = new TreeNode(1);
        root04.left.left = new TreeNode(9);
        root04.left.right = new TreeNode(2);
        root04.right.left = new TreeNode(10);
        root04.right.right = new TreeNode(5);
        System.out.println("Input: " + root04.toLevelOrderString() +", output: " + makeItBold(findLevelAverages(root04).toString()) +" Expected: [12.0, 4.0, 6.5]");

        root04 = new TreeNode(3);
        root04.left = new TreeNode(9);
        root04.right = new TreeNode(20);
        root04.right.left = new TreeNode(15);
        root04.right.right = new TreeNode(7);
        System.out.println("Input: " + root04.toLevelOrderString() +", output: " + makeItBold(findLevelAverages(root04).toString()) +" Expected: [3.0, 14.5, 11.0]");

        root04 = new TreeNode(5);
        root04.left = new TreeNode(3);
        root04.right = new TreeNode(8);
        root04.left.left = new TreeNode(1);
        root04.left.right = new TreeNode(4);
        root04.right.left = new TreeNode(7);
        root04.right.right = new TreeNode(9);
        System.out.println("Input: " + root04.toLevelOrderString() +", output: " + makeItBold(findLevelAverages(root04).toString()) +" Expected: [5.0, 5.5, 5.25]");


    }
}

