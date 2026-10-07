package com.grokingcodeinterview.pattern37realInterviewProblems;

//I have solve this problem before, just practicing... =>

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;
import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeVisualizer;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

import static com.Utility.makeItBold;

///checkout this file com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.p00LevelOrderTraverse;

public class LC102 {

    public static  List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> result = new LinkedList<>();
        if (root == null) return result;

        ArrayDeque<TreeNode> queue = new ArrayDeque<>();

        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();

            List<Integer> levelNodes = new LinkedList<>();
            for (int i = 0; i< levelSize; i++){
                TreeNode node = queue.poll();
                levelNodes.add(node.val);

                if (node.left != null)  queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);





            }

            result.add(levelNodes);
        }

        return result;

    }


    public static void main(String[] args) {

        // Test Case 1: Basic Binary Tree
        System.out.println("Test Case 1: Basic Binary Tree");
        System.out.println("Tree:");
        TreeNode rootP00 = new TreeNode(3);
        rootP00.left = new TreeNode(9);
        rootP00.right = new TreeNode(20);
        rootP00.right.left = new TreeNode(15);
        rootP00.right.right = new TreeNode(7);
        TreeVisualizer.printTree(rootP00);

        System.out.println("output: " + makeItBold(levelOrder(rootP00).toString()) +
                " ,Expected output: [[3], [9, 20], [15, 7]]");

        // Test Case 2: Single Node Tree
        System.out.println("\nTest Case 2: Single Node Tree");
        rootP00 = new TreeNode(1);
        TreeVisualizer.printTree(rootP00);
        System.out.println("output: " + makeItBold(levelOrder(rootP00).toString()) +
                " ,Expected output: [[1]]");

        // Test Case 3: Empty Tree
        System.out.println("\nTest Case 3: Empty Tree");
        rootP00 = null;
        System.out.println("output: " + makeItBold(levelOrder(rootP00).toString()) +
                " ,Expected output: []");

        // Test Case 4: Deeper Tree
        System.out.println("\nTest Case 4: Deeper Tree");
        rootP00 = new TreeNode(1);
        rootP00.left = new TreeNode(2);
        rootP00.right = new TreeNode(3);
        rootP00.left.left = new TreeNode(4);
        rootP00.left.right = new TreeNode(5);
        rootP00.right.left = new TreeNode(6);
        rootP00.right.right = new TreeNode(7);
        TreeVisualizer.printTree(rootP00);
        System.out.println("output: " + makeItBold(levelOrder(rootP00).toString()) +
                " ,Expected output: [[1], [2, 3], [4, 5, 6, 7]]");

    }

}

