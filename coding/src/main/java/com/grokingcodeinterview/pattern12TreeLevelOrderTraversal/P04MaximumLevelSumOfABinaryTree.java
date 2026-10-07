package com.grokingcodeinterview.pattern12TreeLevelOrderTraversal;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.Utility.makeItBold;

/*
 Problem Statement:
 You are given the root of a binary tree. The level of its root node is 1, the level of its children is 2, and so on.
 Return the level x where the sum of the values of all nodes is the highest.
 If there are multiple levels with the same maximum sum, return the smallest level number x.

 Example 1: Input: root = [1, 20, 3, 4, 5, null, 8]  Expected Output: 2
 Explanation:n
 Level 1 has nodes: [1] with sum = 1
 Level 2 has nodes: [20, 3] with sum = 20 + 3 = 23
 Level 3 has nodes: [4, 5, 8] with sum = 4 + 5 + 8 = 17
 The maximum sum is 23 at level 2.
Example 2: Input: root = [10, 5, -3, 3, 2, null, 11, 3, -2, null, 1] Expected Output: 3
 Explanation:
 Level 1 has nodes: [10] with sum = 10
 Level 2 has nodes: [5, -3] with sum = 5 - 3 = 2
 Level 3 has nodes: [3, 2, 11] with sum = 3 + 2 + 11 = 16
 Level 4 has nodes: [3, -2, 1] with sum = 3 - 2 + 1 = 2
 The maximum sum is 16 at level 3.
Example 3: Input: root = [5, 6, 7, 8, null, null, 9, null, null, 10] Expected Output: 2
Explanation:
 Level 1 has nodes: [5] with sum = 5
 Level 2 has nodes: [6, 7] with sum = 6 + 7 = 13
 Level 3 has nodes: [8, 9] with sum = 8 + 9 = 17
 Level 4 has nodes: [10] with sum = 10
 The maximum sum is 17 at level 3.
 Constraints:
 The number of nodes in the tree is in the range [1, 104].
 -105 <= Node.val <= 105
 */

public class P04MaximumLevelSumOfABinaryTree {
    private static final boolean DEBUG = true;

    public static int maxLevelWithHighestSum(TreeNode root) {
        if (root == null) return 0;

        int level = 1;
        int bestLevel = 1;
        int maxSum = Integer.MIN_VALUE;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()){
            int levelSize = queue.size();
            int levelSum = 0;

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.pop();
                levelSum += curr.val;

                if (curr.left != null)
                    queue.offer(curr.left);
                if (curr.right != null)
                    queue.offer(curr.right);

            }
            if (levelSum > maxSum){
                maxSum = levelSum;
                bestLevel = level;
            }
            level++;

        }
        return bestLevel;

    }

    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("P04. Maximum Level Sum of a Binary Tree");
        System.out.println("============================================================");

        // Test Case 1: Basic Binary Tree
        TreeNode rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(7);
        rootP04.right = new TreeNode(0);
        rootP04.left.left = new TreeNode(7);
        rootP04.left.right = new TreeNode(-8);
//        TreeVisualizer.printTree(rootP04);
        System.out.println("Input: [1,7,0,7,-8,null,null] => Output: " + makeItBold(maxLevelWithHighestSum(rootP04) + "") + " ,Expected: 2");

        // Test Case 2: Complex Tree with Negatives
        rootP04 = new TreeNode(989);
        rootP04.right = new TreeNode(10250);
        rootP04.right.left = new TreeNode(98693);
        rootP04.right.right = new TreeNode(-89388);
        rootP04.right.left.right = new TreeNode(-32127);
//        TreeVisualizer.printTree(rootP04);
        System.out.println("Input: [989,null,10250,98693,-89388,null,null,null,-32127] => Output: " + makeItBold(maxLevelWithHighestSum(rootP04) + "") + " ,Expected: 2");

        // Test Case 3: Single Node
        rootP04 = new TreeNode(1);
//        TreeVisualizer.printTree(rootP04);
        System.out.println("Input: [1] => Output: " + makeItBold(maxLevelWithHighestSum(rootP04) + "") + " ,Expected: 1");

        // Test Case 4: Balanced Tree
        rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(2);
        rootP04.right = new TreeNode(3);
        rootP04.left.left = new TreeNode(4);
        rootP04.left.right = new TreeNode(5);
        rootP04.right.left = new TreeNode(6);
        rootP04.right.right = new TreeNode(7);
//        TreeVisualizer.printTree(rootP04);
        System.out.println("Input: [1,2,3,4,5,6,7] => Output: " + makeItBold(maxLevelWithHighestSum(rootP04) + "") + " ,Expected: 3");

        // give me an ex ample with  If there are multiple levels with the same maximum sum, return the smallest level number x.
        /** use below tree as input
         * 5
         *        / \
         *       2   3
         *      /     \
         *     1       1
         */
        rootP04 = new TreeNode(5);
        rootP04.left = new TreeNode(2);
        rootP04.right = new TreeNode(3);
        rootP04.left.left = new TreeNode(1);
        rootP04.right.right = new TreeNode(1);
//        TreeVisualizer.printTree(rootP04);
        System.out.println("Input: [5,2,3,1,null,null,1] => Output: " + makeItBold(maxLevelWithHighestSum(rootP04) + "") + " ,Expected: 1");
        }
}

