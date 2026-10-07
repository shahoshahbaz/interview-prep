package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;
import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeVisualizer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.Utility.makeItBold;

public class Pattern13TreeBreadthFirstSearchR1 {
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
    public static List<Integer> leftViewBinaryTraverse(TreeNode root){
        List<Integer> result = new ArrayList<>();
        if(root == null) return result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){

            int levelSize = queue.size();
            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();

                if(i == 0) result.add(curr.val);

                if (curr.left != null) queue.offer(curr.left);
                if(curr.right != null ) queue.offer(curr.right);
            }
        }

        return result;

    }
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

    /**
     * root = [1, 2, 3, 4, 5, 6, 7]  Expected Output: [1, 3, 7]
     *           1
     *         /  \
     *       2     3
     *      / \   /  \
     *    4   5  6   7
     */
    public static List<Integer> rightViewBinaryTraverse(TreeNode root){
        List<Integer> result = new ArrayList<>();

        if(root == null) return result;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                if (i == levelSize -1) result.add(curr.val);

                if(curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);
            }
        }
        return result;
    }
    /*
    Problem Statement:
 Given a binary tree, find its maximum depth (or height) using Tree BFS traversal.
 examples
    Example 1: Input: root = [3,9,20,null,null,15, 7] output: 3
    Example 2: Input: root = [1,null,2] output: 2
    Example 3: Input: root = [] output: 0
    Example 4: Input: root = [1] output: 1
    Constraints:
    The number of nodes in the tree is in the range [0, 105].
    -1000 <= Node.val <= 1000
 */
    public static int findMaxDepth(TreeNode root){

        if(root == null) return 0;

        Deque<TreeNode> queue = new ArrayDeque<>();

        queue.offer(root);
        int currLevel =0;


        while(!queue.isEmpty()){
            int levelSize = queue.size();
            currLevel++;

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();



                if(curr.left != null) queue.offer(curr.left);
                if( curr.right != null ) queue.offer(curr.right);
            }
        }

        return currLevel;
    }
    /*
  Problem Statement
 Given a root of the binary tree, find the minimum depth of a binary tree.
 The minimum depth is the number of nodes along the shortest path from the root node to the nearest leaf node.

 Examples
 Example 1: Input: root = [3,9,20,null,null,15,7] output: 2
 Example 2: Input: root = [2,null,3,null,4,null,5,null] output: 5
 Example 3: Input: root = [1] output: 1
 Example 4: Input: root = [] output: 0
 Constraints:
 The number of nodes in the tree is in the range [0, 105].
 -1000 <= Node.val <= 1000

 */

    /**
     * Input: root = [3,9,20,null,null,15,7] output: 2
     *         3
     *        / \
     *       9   20
     *           / \
     *          15  7
     * l = 0;
     */
    public static int findMinDepth(TreeNode root){
        if (root == null) return 0;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int currLevel =0;

        while (!queue.isEmpty()){
            int levelSize = queue.size();
            currLevel ++;
            for(int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();

                if(curr.left == null  && curr.right == null){
                    return currLevel;
                }
                if (curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);

            }
        }

        return currLevel;

    }



    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P07. Minimum Depth of a Binary Tree");
        System.out.println("==============================================================");
        // Example 1: Complete binary tree
        TreeNode rootP07 = new TreeNode(1);
        rootP07.left = new TreeNode(2);
        rootP07.right = new TreeNode(3);
        rootP07.left.left = new TreeNode(4);
        rootP07.left.right = new TreeNode(5);
        TreeVisualizer.printTree(rootP07);
        System.out.println("Input: " + rootP07.toLevelOrderString() +makeItBold( findMinDepth(rootP07) +"")+ ",Expected: 2");

        // Example 2: Tree with only left children
        rootP07 = new TreeNode(1);
        rootP07.left = new TreeNode(2);
        rootP07.left.left = new TreeNode(3);
        rootP07.left.left.left = new TreeNode(4);
        System.out.println("Input: " + rootP07.toLevelOrderString() +" ,output: " +makeItBold( findMinDepth(rootP07) +"")+ ",Expected: 4");




        // Example 3: Tree with only right children
        rootP07 = new TreeNode(1);
        rootP07.right = new TreeNode(2);
        rootP07.right.right = new TreeNode(3);
        rootP07.right.right.right = new TreeNode(4);
        System.out.println("Input: " + rootP07.toLevelOrderString() + " ,output: " +makeItBold( findMinDepth(rootP07) +"")+ ",Expected: 4");


        // Example 4: Tree with one node
        rootP07 = new TreeNode(1);
        System.out.println("Input: " + rootP07.toLevelOrderString() +" ,output: " +makeItBold( findMinDepth(rootP07) +"")+ ",Expected: 1");

        // Example 5: Empty tree
        rootP07 = null;
        System.out.println("Input: " + rootP07 + " ,output: " +makeItBold( findMinDepth(rootP07) +"")+ ",Expected: 0");
        System.out.println("==============================================================");
        System.out.println("P08. Maximum Depth of a Binary Tree");
        System.out.println("==============================================================");

        // Example 1: Complete binary tree
        TreeNode rootP08 = new TreeNode(1);
        rootP08.left = new TreeNode(2);
        rootP08.right = new TreeNode(3);
        rootP08.left.left = new TreeNode(4);
        rootP08.left.right = new TreeNode(5);
        System.out.println("Input: " + rootP08.toLevelOrderString() +", output: " + makeItBold(findMaxDepth(rootP08) +"") + ", Expected: 3");
        // Example 2: Tree with only left children
        rootP08 = new TreeNode(1);
        rootP08.left = new TreeNode(2);
        rootP08.left.left = new TreeNode(3);
        rootP08.left.left.left = new TreeNode(4);
        System.out.println("Input: " + rootP08.toLevelOrderString() +", output: " + makeItBold(findMaxDepth(rootP08) +"") + ", Expected: 4");

        // Example 3: Tree with only right children
        rootP08 = new TreeNode(1);
        rootP08.right = new TreeNode(2);
        rootP08.right.right = new TreeNode(3);
        rootP08.right.right.right = new TreeNode(4);
        System.out.println("Input: " + rootP08.toLevelOrderString() +", output: " + makeItBold(findMaxDepth(rootP08) +"") + ", Expected: 4");


        // Example 4: Tree with one node
        rootP08 = new TreeNode(1);
        System.out.println("Input: " + rootP08.toLevelOrderString() +", output: " + makeItBold(findMaxDepth(rootP08) +"") + ", Expected: 1");

        // Example 5: Empty tree
        rootP08 = null;
        System.out.println("Input: " + rootP08 +", output: " + makeItBold(findMaxDepth(rootP08) +"") + ", Expected: 0");

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

