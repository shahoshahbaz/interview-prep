package com.grokingcodeinterview.pattern12TreeLevelOrderTraversal;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static com.Utility.makeItBold;

/*
 problem statement
 Given a binary tree, perform a zigzag level order traversal.
 Return the result as a list of lists where nodes at each level are traversed
 alternating between left-to-right and right-to-left directions.

 Example 1: Input: root = [3,9,20,null,null,15,7]  Expected Output: [[3],[20,9],[15,7]]
 Explanation: Level 0: [3] (left-to-right). Level 1: [20,9] (right-to-left). Level 2: [15,7] (left-to-right).

 Example 2: Input: root = [1]  Expected Output: [[1]]
 Explanation: Single node tree.

 Example 3: Input: root = [1,2,3,4,null,null,5]  Expected Output: [[1],[3,2],[4,5]]
 Explanation: Zigzag pattern for each level.
 */
public class P05ZigzagTraversal {
    private static final boolean DEBUG = true;

        public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            LinkedList<List<Integer>> result = new LinkedList<>();

            if (root == null) return result;

            Deque<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);
            boolean rightToLeft = false;

            while(!queue.isEmpty()){
                int levelSize = queue.size();
                LinkedList<Integer> list = new LinkedList<>();

                for (int i =0; i<levelSize; i++){
                    TreeNode curr = queue.poll();
                    if (rightToLeft)
                        list.addFirst(curr.val);
                    else
                        list.addLast(curr.val);

                    if (curr.left != null)
                         queue.offer(curr.left);
                   if (curr.right != null)
                          queue.offer(curr.right);

                }
                result.add(list);
                rightToLeft = !rightToLeft;
            }
            return result;


    }

    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("P05. Zigzag Level Order Traversal");
        System.out.println("============================================================");

        // Test Case 1: Basic Binary Tree
        System.out.println("Test Case 1: Basic Binary Tree");
        TreeNode rootP05 = new TreeNode(3);
        rootP05.left = new TreeNode(9);
        rootP05.right = new TreeNode(20);
        rootP05.right.left = new TreeNode(15);
        rootP05.right.right = new TreeNode(7);
//        TreeVisualizer.printTree(rootP05);
        System.out.println("Input: [3,9,20,null,null,15,7] => Output: " + makeItBold(zigzagLevelOrder(rootP05) + "") + " ,Expected: [[3], [20, 9], [15, 7]]");

        // Test Case 2: Single Node
        System.out.println("\nTest Case 2: Single Node");
        rootP05 = new TreeNode(1);
//        TreeVisualizer.printTree(rootP05);
        System.out.println("Input: [1] => Output: " + makeItBold(zigzagLevelOrder(rootP05) + "") + " ,Expected: [[1]]");

        // Test Case 3: Complex Tree
        System.out.println("\nTest Case 3: Complex Tree");
        rootP05 = new TreeNode(1);
        rootP05.left = new TreeNode(2);
        rootP05.right = new TreeNode(3);
        rootP05.left.left = new TreeNode(4);
        rootP05.right.right = new TreeNode(5);
        //TreeVisualizer.printTree(rootP05);
        System.out.println("Input: [1,2,3,4,null,null,5] => Output: " + makeItBold(zigzagLevelOrder(rootP05) + "") + " ,Expected: [[1],[3,2],[4,5]]");

        // Test Case 4: Deeper Tree
        System.out.println("\nTest Case 4: Deeper Tree");
        rootP05 = new TreeNode(1);
        rootP05.left = new TreeNode(2);
        rootP05.right = new TreeNode(3);
        rootP05.left.left = new TreeNode(4);
        rootP05.left.right = new TreeNode(5);
        rootP05.right.left = new TreeNode(6);
        rootP05.right.right = new TreeNode(7);
        //TreeVisualizer.printTree(rootP05);
        System.out.println("Input: [1,2,3,4,5,6,7] => Output: " + makeItBold(zigzagLevelOrder(rootP05) + "") + " ,Expected: [[1],[3,2],[4,5,6,7]]");

        // give me compliecated test case
        System.out.println("\nTest Case 5: More Complex Tree");
        rootP05 = new TreeNode(1);
        rootP05.left = new TreeNode(2);
        rootP05.right = new TreeNode(3);
        rootP05.left.left = new TreeNode(4);
        rootP05.left.right = new TreeNode(5);
        rootP05.right.left = new TreeNode(6);
        rootP05.right.right = new TreeNode(7);
        rootP05.left.left.left = new TreeNode(8);
        rootP05.left.left.right = new TreeNode(9);
        rootP05.left.right.left = new TreeNode(10);
        rootP05.right.left.right = new TreeNode(11);
        rootP05.right.right.left = new TreeNode(12);
        //TreeVisualizer.printTree(rootP05);
        System.out.println("Input: [1,2,3,4,5,6,7,8,9,10,null,11,12] => Output: " + makeItBold(zigzagLevelOrder(rootP05) + "") + " ,Expected: [[1],[3,2],[4,5,6,7],[12,11,10,9,8]]");
    }
}

