package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a root of the binary tree and an integer key,
 find the level order successor of the node containing
 the given key as a value in the tree.

The level order successor is the node that appears right after
the given node in the level order traversal.
examples
Example 1: Input: root = [1, 2, 3, 4, 5, 6, 7], key = 3 output: 4
Example 2: Input: root = [12, 7, 1, null, 9, 10, 5, null, 3], key = 9 output: 10
Example 3: Input: root = [1, 2, 3], key = 3 output: null
Constraints:
The number of nodes in the tree is in the range [0, 105].
-1000 <= Node.val <= 1000

 */
public class P09LevelOrderSuccessor {
    /**
     * Input: root = [1, 2, 3, 4, 5, 6, 7], key = 3 output: 4

     */
    public static Integer findLevelOrderSuccessor(TreeNode root, int key){
        if (root == null) return null;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size(); // this is not requred for this problem but we can use it to make sure we are processing one level at a time

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                if(curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);

                if (curr.val == key  && !queue.isEmpty() ){
                    return queue.peek().val;


                }
            }
        }
        return null;

    }
    public static void main(String[] args) {

        System.out.println("==============================================================");
        System.out.println("P09. Level Order Successor");
        System.out.println("==============================================================");

        TreeNode rootP09 = new TreeNode(1);
        rootP09.left = new TreeNode(2);
        rootP09.right = new TreeNode(3);
        rootP09.left.left = new TreeNode(4);
        rootP09.left.right = new TreeNode(5);
        rootP09.right.left = new TreeNode(6);
        rootP09.right.right = new TreeNode(7);
        System.out.println("Input: " + rootP09.toLevelOrderString() + ", key: 3, output: " + makeItBold(findLevelOrderSuccessor(rootP09, 3) +"") + ", Expected: 4");

        rootP09 = new TreeNode(12);
        rootP09.left = new TreeNode(7);
        rootP09.right = new TreeNode(1);
        rootP09.left.left = new TreeNode(9);
        rootP09.right.left = new TreeNode(10);
        rootP09.right.right = new TreeNode(5);
        System.out.println("Input: " + rootP09.toLevelOrderString() + ", key: 9, output: " + makeItBold(findLevelOrderSuccessor(rootP09, 9) +"") + ", Expected: 10");

        rootP09 = new TreeNode(1);
        rootP09.left = new TreeNode(2);
        rootP09.right = new TreeNode(3);
        System.out.println("Input: " + rootP09.toLevelOrderString() + ", key: 3, output: " + makeItBold(findLevelOrderSuccessor(rootP09, 3) +"") + ", Expected: null");

    }

}

