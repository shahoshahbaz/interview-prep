package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.Utility.makeItBold;

/*
  Find the largest value on each level of a binary tree.
 */
public class P06LargestValueOnEachLevelOfBinaryTree {

    public static List<Integer> largestValues(TreeNode root){
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            int maxLevel = Integer.MIN_VALUE;

            for(int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                maxLevel = Math.max(maxLevel, curr.val);

                if(curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);
            }

            result.add(maxLevel);
        }

        return result;


    }

    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P06. Largest Value on Each Level of a Binary Tree");
        System.out.println("==============================================================");
        TreeNode rootP06 = new TreeNode(1);
        rootP06.left = new TreeNode(3);
        rootP06.right = new TreeNode(2);
        rootP06.left.left = new TreeNode(5);
        rootP06.left.right = new TreeNode(3);
        rootP06.right.right = new TreeNode(9);
        System.out.println("Input: " + rootP06.toLevelOrderString() +", output: " + makeItBold(largestValues(rootP06).toString()) +" Expected: [1, 3, 9]");

        rootP06 = new TreeNode(1);
        rootP06.left = new TreeNode(2);
        rootP06.right = new TreeNode(3);
        rootP06.left.left = new TreeNode(4);
        rootP06.right.right = new TreeNode(5);
        System.out.println("Input: " + rootP06.toLevelOrderString() +", output: " + makeItBold(largestValues(rootP06).toString()) +" Expected: [1, 3, 5]");

        rootP06 = new TreeNode(1);
        rootP06.left = new TreeNode(2);
        rootP06.right = new TreeNode(3);
        rootP06.left.left = new TreeNode(4);
        rootP06.left.right = new TreeNode(5);
        rootP06.right.left = new TreeNode(6);
        rootP06.right.right = new TreeNode(7);
        System.out.println("Input: " + rootP06.toLevelOrderString() +", output: " + makeItBold(largestValues(rootP06).toString()) +" Expected: [1, 3, 7]");



    }
}

