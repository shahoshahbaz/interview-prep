package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.Utility.makeItBold;
/*
    problem statement:
    Given a root of the binary tree, return an array containing nodes in its left view.
    The left view of a binary tree consists of nodes that are visible when the tree is viewed from the left side. For each level of the tree, the first node encountered in that level will be included in the left view.
    
    Examples
    Example 1
    Input: root = [1, 2, 3, 4, 5, 6, 7] Expected Output: [1, 2, 4]
    Input: root = [12, 7, 1, null, 9, 10, 5, null, 3] Expected Output: [12, 7, 9, 3]
    Input: root = [1, null, 2, null, 3, null, 4] Expected Output: [1, 2, 3, 4]
    
    constraints:
    The number of nodes in the tree is in the range [0, 10^5].
    -10^5 <= Node.val <= 10^5
 */
public class P13LeftViewOfBinaryTree {
    public static List<Integer> leftViewBinaryTraverse(TreeNode root){
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()){
            int levelSize = queue.size();

            for(int i =0; i< levelSize; i++){
                TreeNode currentNode = queue.poll();
                if (i == 0){
                    result.add(currentNode.val);
                }
                if (currentNode.left != null) queue.offer(currentNode.left);
                if (currentNode.right != null) queue.offer(currentNode.right);

            }
        }
        return result;


    }

    public static void main(String[] args) {

        System.out.println("==============================================================");
        System.out.println("P13. Left view of binary tree");
        System.out.println("==============================================================");
        // Example 1: Complete binary tree
        TreeNode rootP13 = new TreeNode(1);
        rootP13.left = new TreeNode(2);
        rootP13.right = new TreeNode(3);
        rootP13.left.left = new TreeNode(4);
        rootP13.left.right = new TreeNode(5);
        rootP13.right.left = new TreeNode(6);
        rootP13.right.right = new TreeNode(7);
        System.out.println("Input: " + rootP13.toLevelOrderString() + ", Output: " +
                makeItBold(leftViewBinaryTraverse(rootP13).toString())
                + ", Expected Output: [1, 2, 4]");

        // Example 2: Tree with missing nodes
         rootP13 = new TreeNode(12);
        rootP13.left = new TreeNode(7);
        rootP13.right = new TreeNode(1);
        rootP13.left.right = new TreeNode(9);
        rootP13.right.left = new TreeNode(10);
        rootP13.right.right = new TreeNode(5);
        rootP13.left.right.left = new TreeNode(3);
        System.out.println("Input: " + rootP13.toLevelOrderString() + ", Output: " +
                makeItBold(leftViewBinaryTraverse(rootP13).toString())
                + ", Expected Output: [12, 7, 9, 3]");
       rootP13 = new TreeNode(42);
        System.out.println("Input: " + rootP13.toLevelOrderString() + ", Output: " +
                makeItBold(leftViewBinaryTraverse(rootP13).toString())
                + ", Expected Output: [42]");
        // Example 4: Empty tree
        System.out.println("Input: null, Output: " +
                makeItBold(leftViewBinaryTraverse(null).toString())
                + ", Expected Output: []");
    }
}

