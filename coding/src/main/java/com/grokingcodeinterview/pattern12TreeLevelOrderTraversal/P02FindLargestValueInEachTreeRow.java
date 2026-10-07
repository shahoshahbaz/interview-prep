package com.grokingcodeinterview.pattern12TreeLevelOrderTraversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.Utility.makeItBold;

/*
 problem statement
 Given a binary tree, find the largest value in each row of the tree.
 Return the result as a list where each element represents the largest value at each level.

 Example 1: Input: root = [1,3,2,5,3,null,9]  Expected Output: [1,3,9]
 Explanation: At level 0: largest is 1. At level 1: largest is 3. At level 2: largest is 9.

 Example 2: Input: root = [1,2,3]  Expected Output: [1,3]
 Explanation: At level 0: largest is 1. At level 1: largest is 3.

 Example 3: Input: root = [1]  Expected Output: [1]
 Explanation: Single node tree.
 */
public class P02FindLargestValueInEachTreeRow {
    private static final boolean DEBUG = true;

    public static List<Integer> largestValues(TreeNode root) {

        if (root == null) return new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();

        queue.offer(root);
        int max= 0;
        while(!queue.isEmpty()){
            int levelSize = queue.size();
            max= Integer.MIN_VALUE;

            for (int i =0; i<levelSize; i++ ){
                TreeNode curr = queue.poll();
                max = Math.max(max, curr.val);
                if (curr.left != null)
                    queue.offer(curr.left);
                if (curr.right != null)
                    queue.offer(curr.right);
            }
            result.add(max);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("P02. Find Largest Value in Each Tree Row");
        System.out.println("============================================================");

        // Test Case 1: Basic Binary Tree
        System.out.println("Test Case 1: Basic Binary Tree");
        TreeNode rootP02 = new TreeNode(1);
        rootP02.left = new TreeNode(3);
        rootP02.right = new TreeNode(2);
        rootP02.left.left = new TreeNode(5);
        rootP02.left.right = new TreeNode(3);
        rootP02.right.right = new TreeNode(9);
        TreeVisualizer.printTree(rootP02);
        System.out.println("Input: [1,3,2,5,3,null,9] => Output: " + makeItBold(largestValues(rootP02)+"") + " ,Expected: [1,3,9]");

        // Test Case 2: Two Level Tree
        System.out.println("\nTest Case 2: Two Level Tree");
        rootP02 = new TreeNode(1);
        rootP02.left = new TreeNode(2);
        rootP02.right = new TreeNode(3);
        TreeVisualizer.printTree(rootP02);
        System.out.println("Input: [1,2,3] => Output: " + makeItBold(largestValues(rootP02)+"") + " ,Expected: [1,3]");

        // Test Case 3: Single Node
        System.out.println("\nTest Case 3: Single Node");
        rootP02 = new TreeNode(1);
        TreeVisualizer.printTree(rootP02);
        System.out.println("Input: [1] => Output: " + makeItBold(largestValues(rootP02)+"") + " ,Expected: [1]");

        // Test Case 4: Deeper Tree
        System.out.println("\nTest Case 4: Deeper Tree");
        rootP02 = new TreeNode(1);
        rootP02.left = new TreeNode(2);
        rootP02.right = new TreeNode(3);
        rootP02.left.left = new TreeNode(4);
        rootP02.left.right = new TreeNode(5);
        rootP02.right.left = new TreeNode(6);
        rootP02.right.right = new TreeNode(7);
        TreeVisualizer.printTree(rootP02);
        System.out.println("Input: [1,2,3,4,5,6,7] => Output: " + makeItBold(largestValues(rootP02)+"") + " ,Expected: [1,3,7]");
    }
}

