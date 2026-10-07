package com.grokingcodeinterview.pattern12TreeLevelOrderTraversal;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.Utility.makeItBold;

/*
 Given a binary tree, return true if it is an Even-Odd tree. Otherwise, return false.
The Even-odd tree must follow below two rules:
At every even-indexed level (starting from 0), all node values must be odd and arranged in strictly increasing order from left to right.
At every odd-indexed level, all node values must be even and arranged in strictly decreasing order from left to right.
Examples
Example 1
Input:
    1
   / \
  10  4
 / \
3   7
Expected Output: true
Justification: The tree follows both conditions for each odd and even level. So, it is an odd-even tree.
Example 2
Input:

    5
   / \
  9   3
 /     \
12      8
Expected Output: false
Justification: Level 1 has Odd values 9 and 3 in decreasing order, but it should have even values. So, the tree is not an odd-even tree.
Example 3
Input:
    7
   / \
  10  2
 / \
12  8
Expected Output: false
Justification: At level 2 (even-indexed), the values are 12 and 8, which are even, but they should have odd values. So, the tree is not an odd-even tree.
Constraints:

The number of nodes in the tree is in the range [1, 105].
1 <= Node.val <= 106
 */

/**
 * At every even-indexed level (starting from 0),
 *      all node values must be odd and arranged in strictly increasing order from left to right.
 * At every odd-indexed level,
 *     all node values must be even and arranged in strictly decreasing order from left to right.
 */
public class P06EvenOddTree {
    private static final boolean DEBUG = true;

    public static boolean isEvenOddTree(TreeNode root) {

        if (root == null) return true   ;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level =0;

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            int prev = level % 2 ==0 ? Integer.MIN_VALUE: Integer.MAX_VALUE;

            for (int i =0; i< levelSize ; i++){
                TreeNode curr = queue.poll();
                if(level %2 == 0){ //even level
                    if (curr.val %2 ==0 || curr.val<= prev) return false;
                }else{// odd level
                    if (curr.val %2 == 1 || curr.val>= prev) return false;

                }
                prev = curr.val;

                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }




            level ++;


        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("P06. Even Odd Tree");
        System.out.println("============================================================");

        // Test Case 1: Valid Even Odd Tree
        System.out.println("Test Case 1: Valid Even Odd Tree");
        TreeNode rootP06 = new TreeNode(1);


        rootP06.left = new TreeNode(10);
        rootP06.right = new TreeNode(4);

        rootP06.left.left = new TreeNode(3);
        rootP06.left.right = new TreeNode(5);
        rootP06.right.left = new TreeNode(7);
        rootP06.right.right = new TreeNode(9);
        //TreeVisualizer.printTree(rootP06);
        System.out.println("Input: [1,10,4,3,null,7,6,2,null,6,2,null] => Output: " + makeItBold(isEvenOddTree(rootP06) + "") + " ,Expected: true");

        // Test Case 2: Invalid - Not Strictly Increasing
        System.out.println("\nTest Case 2: Invalid - Not Strictly Increasing");
        rootP06 = new TreeNode(5);
        rootP06.left = new TreeNode(4);
        rootP06.right = new TreeNode(2);
        rootP06.left.left = new TreeNode(3);
        rootP06.left.right = new TreeNode(3);
        rootP06.right.left = new TreeNode(7);
        //TreeVisualizer.printTree(rootP06);
        System.out.println("Input: [5,4,2,3,3,7] => Output: " + makeItBold(isEvenOddTree(rootP06) + "") + " ,Expected: false");

        // Test Case 3: Invalid - Even Value at Even Level
        System.out.println("\nTest Case 3: Invalid - Even Value at Even Level");
        rootP06 = new TreeNode(2);
        rootP06.left = new TreeNode(1);
        rootP06.right = new TreeNode(2);
        rootP06.left.left = new TreeNode(10);
        rootP06.left.right = new TreeNode(12);
        rootP06.right.left = new TreeNode(6);
        rootP06.right.right = new TreeNode(12);
        rootP06.left.left.left = new TreeNode(1);
        rootP06.left.left.right = new TreeNode(1);
        rootP06.left.right.left = new TreeNode(1);
        rootP06.left.right.right = new TreeNode(1);
        ////TreeVisualizer.printTree(rootP06);
        System.out.println("Input: [2,1,2,10,12,6,12,1,1,1,1] => Output: " + makeItBold(isEvenOddTree(rootP06) + "") + " ,Expected: false");

        // Test Case 4: Single Node Valid
        System.out.println("\nTest Case 4: Single Node Valid");
        rootP06 = new TreeNode(1);
        ////TreeVisualizer.printTree(rootP06);
        System.out.println("Input: [1] => Output: " + makeItBold(isEvenOddTree(rootP06) + "") + " ,Expected: true");
    }
}

