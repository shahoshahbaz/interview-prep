package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a binary tree, populate an array to represent its zigzag level order traversal. You should populate the values of all nodes of the first level from left to right,
then right to left for the next level and keep alternating in the same manner for the following levels.

Example 1: Input: root = [1, 2, 3, 4, 5, 6, 7] Expected Output: [[1], [3, 2], [4, 5, 6, 7]]
justification:
The first level has 1 node.
The second level has 3 and 2 nodes in reverse order.
The third level has 4, 5, 6, and 7 nodes.
Example 2: Input: root = [12, 7, 1, null, 9, 10, 5] Expected Output: [[12], [1, 7], [9, 10, 5]]
justification:
The first level has 12 node.
The second level has 1 and 7 nodes in reverse order.
The third level has 9, 10, and 5 nodes.
Constraints:
The number of nodes in the tree is in the range [0, 2000].
-1000 <= Node.val <= 1000
 */
public class P04ZigzagTraversal {
     public static List<List<Integer>> zigzagTraversal(TreeNode root){
         LinkedList<List<Integer>> result = new LinkedList<>();

         Deque<TreeNode> queue = new ArrayDeque<>();
         queue.offer(root);
         boolean leftToRight = true;
         while(!queue.isEmpty()){

             int levelSize = queue.size();
             LinkedList<Integer> list = new LinkedList<>();
             for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                if (leftToRight){
                    list.add(curr.val);
                }else{
                    list.addFirst(curr.val);
                }

                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);

             }
             leftToRight = !leftToRight;
             result.add(list);

         }
            return result;
     }
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("P04: Zigzag Traversal");
        System.out.println("======================================================================");
         // some example with input and expected output
        TreeNode rootP04 = new TreeNode(3);
        rootP04.left = new TreeNode(9);
        rootP04.right = new TreeNode(20);
        rootP04.right.left = new TreeNode(15);
        rootP04.right.right = new TreeNode(7);
        System.out.println("Input: "+ rootP04.toLevelOrderString() +" ,Output: " + makeItBold(zigzagTraversal(rootP04).toString())+
                " ,Expected: [[3], [20, 9], [15, 7]]");

        rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(2);
        rootP04.right = new TreeNode(3);
        rootP04.left.left = new TreeNode(4);
        rootP04.left.right = new TreeNode(5);
        System.out.println("Input: "+ rootP04.toLevelOrderString() +" ,Output: " + makeItBold(zigzagTraversal(rootP04).toString())+
                " ,Expected: [[1], [3, 2], [4, 5]]");
        rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(2);
        rootP04.right = new TreeNode(3);
        rootP04.left.left = new TreeNode(4);
        rootP04.right.right = new TreeNode(5);
        System.out.println("Input: "+ rootP04.toLevelOrderString() +" ,Output: " + makeItBold(zigzagTraversal(rootP04).toString())+
                " ,Expected: [[1], [3, 2], [4, 5]]");

        // give more more complex tree
        rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(2);
        rootP04.right = new TreeNode(3);
        rootP04.left.left = new TreeNode(4);
        rootP04.left.right = new TreeNode(5);
        rootP04.right.left = new TreeNode(6);
        rootP04.right.right = new TreeNode(7);
        rootP04.left.left.left = new TreeNode(8);
        rootP04.left.left.right = new TreeNode(9);
        rootP04.right.right.left = new TreeNode(10);
        rootP04.right.right.right = new TreeNode(11);
        System.out.println("Input: "+ rootP04.toLevelOrderString() +" ,Output: " + makeItBold(zigzagTraversal(rootP04).toString())+
                " ,Expected: [[1], [3, 2], [4, 5, 6, 7], [11, 10, 9, 8]]");

    }
}

