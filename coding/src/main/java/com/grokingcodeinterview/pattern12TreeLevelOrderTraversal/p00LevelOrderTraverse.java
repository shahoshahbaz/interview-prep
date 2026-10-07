package com.grokingcodeinterview.pattern12TreeLevelOrderTraversal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.Utility.makeItBold;

public class p00LevelOrderTraverse {
    public  static List<List<Integer>> levelOrder (TreeNode root){
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            for (int i =0; i< levelSize; i++){
                TreeNode currNode = queue.poll();
                currentLevel.add(currNode.val);

                if (currNode.left != null)
                    queue.offer(currNode.left);
                if(currNode.right != null )
                    queue.offer(currNode.right);
            }
            result.add(currentLevel);
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("P00. Level Order Traversal of a Binary Tree");
        System.out.println("=====================================");
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

