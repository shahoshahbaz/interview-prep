package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.Utility.makeItBold;

/*
 problem statement:
 Given a root of the binary tree, return an array containing nodes in its right view.
 The right view of a binary tree consists of nodes that are visible when the tree is viewed from the right side. For each level of the tree, the last node encountered in that level will be included in the right view.
 Examples
 Example 1
 Input: root = [1, 2, 3, 4, 5, 6, 7]  Expected Output: [1, 3, 7]
 Input: root = [12, 7, 1, null, 9, 10, 5, null, 3]  Expected Output: [12, 1, 5, 3]
    Input: root = [1, null, 2, null, 3, null, 4]     Expected Output: [1, 2, 3, 4]

    constraints:
    The number of nodes in the tree is in the range [0, 10^5].
    -10^5 <= Node.val <= 10^5
 */
public class P12RightViewOfBinaryTree {
    public static  List<Integer> rightViewBinaryTraverse(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if(root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()){
            int levelSize = queue.size();
            TreeNode currentNode = null;
            for (int i =0; i<levelSize; i++){
                currentNode = queue.poll();
                if (currentNode.left != null) queue.offer(currentNode.left);
                if (currentNode.right != null) queue.offer(currentNode.right);

            }
            result.add(currentNode.val);

        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("=======================================================");
        System.out.println("P12. Right view of binary tree");
        System.out.println("=======================================================");
        // Example 1: Complete binary tree
        TreeNode rootP12 = new TreeNode(1);
        rootP12.left = new TreeNode(2);
        rootP12.right = new TreeNode(3);
        rootP12.left.left = new TreeNode(4);
        rootP12.left.right = new TreeNode(5);
        rootP12.right.left = new TreeNode(6);
        rootP12.right.right = new TreeNode(7);
        System.out.println("Input: " + rootP12.toLevelOrderString()+", Output: " +
                makeItBold(rightViewBinaryTraverse(rootP12).toString())
                + ", Expected Output: [1, 3, 7]");

        // Example 2: Tree with nulls
         rootP12 = new TreeNode(12);
        rootP12.left = new TreeNode(7);
        rootP12.right = new TreeNode(1);
        rootP12.left.right = new TreeNode(9);
        rootP12.right.left = new TreeNode(10);
        rootP12.right.right = new TreeNode(5);
        rootP12.left.right.left = new TreeNode(3);
        System.out.println("Input: " + rootP12.toLevelOrderString()+", Output: " +
                makeItBold(rightViewBinaryTraverse(rootP12).toString())
                + ", Expected Output: [12, 1, 5, 3]");

        // Example 3: Single node
         rootP12 = new TreeNode(42);
        System.out.println("Input: " + rootP12.toLevelOrderString()+", Output: " +
                makeItBold(rightViewBinaryTraverse(rootP12).toString())
                + ", Expected Output: [42]");

        // Example 4: Empty tree
        System.out.println("Input: null, Output: " +
                makeItBold(rightViewBinaryTraverse(null).toString())
                + ", Expected Output: []");
    }
}
