package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import static com.Utility.makeItBold;

/*
 problem statement:
 Given a root of the binary tree and an integer â€˜Sâ€™,
  return true if the tree has a path from root-to-leaf such that the sum of all the node values of that path equals â€˜Sâ€™. Otherwise, return false.
 Examples
 Example 1:
 Input: root = [1, 2, 3, 4, 5, 6, 7], S = 10
 Expected Output: true
 Justification: The tree has 1 -> 3 -> 6 root-to-leaf path having sum equal to 10.
 Example 2:
 Input: root = [12, 7, 1, 9, null, 10, 5], S = 23
 Expected Output: true
 Justification: The tree has 12 -> 1 -> 10 root-to-leaf path having sum equal to 23.
 */

/**
 *  [           1,
 *            2,    3,
 *         4, 5,   6, 7], S = 10
 */
public class P02BinaryTreePathSum {

    private static  boolean hasPath(TreeNode root, int sum) {
        if (root == null  ) return false;
        if (root.left == null && root.right == null && sum == root.val)
            return true;
        return hasPath(root.left, sum - root.val) || hasPath(root.right, sum - root.val);
    }
    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("P02. Binary Tree Path Sum");
        System.out.println("=====================================");

            TreeNode rootP02 = new TreeNode(1);
            rootP02.left = new TreeNode(2);
            rootP02.right = new TreeNode(3);
            rootP02.left.left = new TreeNode(4);
            rootP02.left.right = new TreeNode(5);
            rootP02.right.left = new TreeNode(6);
            rootP02.right.right = new TreeNode(7);
            int sumP02 = 10;
            System.out.println("Input: " + rootP02.toLevelOrderString() + " ,S = " + sumP02 + " ,Output: " + makeItBold(hasPath(rootP02, sumP02) +"") + " Expected: true");

            // Example 2: root = [12, 7, 1, 9, null, 10, 5], S = 23
             rootP02 = new TreeNode(12);
            rootP02.left = new TreeNode(7);
            rootP02.right = new TreeNode(1);
            rootP02.left.left = new TreeNode(9);
            rootP02.right.left = new TreeNode(10);
            rootP02.right.right = new TreeNode(5);
            sumP02 = 23;
            System.out.println("Input: " + rootP02.toLevelOrderString() + " ,S = " + sumP02 + " ,Output: " + makeItBold(hasPath(rootP02, sumP02) +"") + " Expected: true");

            // Example 3: root = [5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1], S = 22
            rootP02 = new TreeNode(5);
            rootP02.left = new TreeNode(4);
            rootP02.right = new TreeNode(8);
            rootP02.left.left = new TreeNode(11);
            rootP02.left.left.left = new TreeNode(7);
            rootP02.left.left.right = new TreeNode(2);
            rootP02.right.left = new TreeNode(13);
            rootP02.right.right = new TreeNode(4);
            rootP02.right.right.right = new TreeNode(1);
             sumP02 = 22;
            System.out.println("Input: " + rootP02.toLevelOrderString() + " ,S = " + sumP02 + " ,Output: " + makeItBold(hasPath(rootP02, sumP02) +"") + " Expected: true");
            // give me an example where the output is false
            // Example 4: root = [1, 2, 3], S = 5
            rootP02 = new TreeNode(1);
            rootP02.left = new TreeNode(2);
            rootP02.right = new TreeNode(3);
            sumP02 = 5;
            System.out.println("Input: " + rootP02.toLevelOrderString() + " ,S = " + sumP02 + " ,Output: " + makeItBold(hasPath(rootP02, sumP02) +"") + " Expected: false");
        }
}

