package com.grokingcodeinterview.pattern12TreeLevelOrderTraversal;

import java.util.*;

import static com.Utility.makeItBold;

/*
 problem statement
 Given a binary tree, perform a level order traversal in reverse order.
 Return the result as a list of lists where each inner list represents nodes at each level
 traversed from bottom to top (reverse order).

 Example 1: Input: root = [3,9,20,null,null,15,7]  Expected Output: [[15,7],[9,20],[3]]
 Explanation: Level order from bottom to top.

 Example 2: Input: root = [1]  Expected Output: [[1]]
 Explanation: Single node tree.

 Example 3: Input: root = []  Expected Output: []
 Explanation: Empty tree.
 */

/**
 *  [3,9,20,null,null,15,7]  output: [[15,7],[9,20],[3]]
 *
 * roo
 */
public class P01ReverseLevelOrderTraversal {


    public static List<List<Integer>> reverseLevelOrder(TreeNode root) {
       if(root == null) return null;
       LinkedList<List<Integer>> result = new LinkedList<>();

        Deque<TreeNode> queue = new ArrayDeque<>();

        queue.offer(root);

        while(!queue.isEmpty()){

            int levelSize = queue.size();
            List<Integer> levelNodes = new LinkedList<>();

            for(int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                levelNodes.add(curr.val);
                if (curr.left != null)
                    queue.offer(curr.left);
                if (curr.right != null)
                    queue.offer(curr.right);

            }
            result.addFirst( levelNodes);


        }


    return result;
    }

    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("P01. Reverse Level Order Traversal");
        System.out.println("============================================================");

        // Test Case 1: Basic Binary Tree
        System.out.println("Test Case 1: Basic Binary Tree");
        TreeNode rootP01 = new TreeNode(3);
        rootP01.left = new TreeNode(9);
        rootP01.right = new TreeNode(20);
        rootP01.right.left = new TreeNode(15);
        rootP01.right.right = new TreeNode(7);
        TreeVisualizer.printTree(rootP01);
        System.out.println("Input: [3,9,20,null,null,15,7] => Output: " + makeItBold(reverseLevelOrder(rootP01)+"") + " ,Expected: [[15,7],[9,20],[3]]");

        // Test Case 2: Single Node
        System.out.println("\nTest Case 2: Single Node");
        rootP01 = new TreeNode(1);
        TreeVisualizer.printTree(rootP01);
        System.out.println("Input: [1] => Output: " + makeItBold(reverseLevelOrder(rootP01)+"") + " ,Expected: [[1]]");

        // Test Case 3: Empty Tree
        System.out.println("\nTest Case 3: Empty Tree");
        rootP01 = null;
        System.out.println("Input: [] => Output: " + makeItBold(reverseLevelOrder(rootP01)+"") + " ,Expected: []");

        // Test Case 4: Deeper Tree
        System.out.println("\nTest Case 4: Deeper Tree");
        rootP01 = new TreeNode(1);
        rootP01.left = new TreeNode(2);
        rootP01.right = new TreeNode(3);
        rootP01.left.left = new TreeNode(4);
        rootP01.left.right = new TreeNode(5);
        rootP01.right.left = new TreeNode(6);
        rootP01.right.right = new TreeNode(7);
        TreeVisualizer.printTree(rootP01);
        System.out.println("Input: [1,2,3,4,5,6,7] => Output: " + makeItBold(reverseLevelOrder(rootP01)+"") + " ,Expected: [[4,5,6,7],[2,3],[1]]");
    }
}

