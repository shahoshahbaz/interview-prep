package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static com.Utility.makeItBold;
/*
Problem Statement
Given the root of a binary tree, return the bottom-up level order traversal of its nodes' values. (i.e., the lowest level comes first in left to right order.)

Example 1: Input: root = [1, 2, 3, 4, 5, 6, 7] Expected Output: [[4, 5, 6, 7], [2, 3], [1]]
Justification:
The third level has 4, 5, 6, and 7 nodes.
The second level has 2 and 3 nodes.
The first level has a single node with the value 1.
Example 2: Input: root = [12, 7, 1, null, 9, 10, 5] Expected Output: [[9, 10, 5], [7, 1], [12]]
Justification:
The third level has 9, 10, and 5 nodes.
The second level has 7 and 1 nodes.
The first level has a single node with the value 12.
Example 3: Input: root = [6,5,2,null,null,1,6,3,56,3] Expected Output: [[3,56,3],[1,6],[5,2],[6]]
Justification:
The fourth level has 3, 56, and 3 nodes.
The third level has 1, and 6 nodes.
The second level has 5 and 2 nodes.
The first level has a single node with the value 6.
Constraints:
The number of nodes in the tree is in the range [0, 2000].
-1000 <= Node.val <= 1000
 */
public class P03ReverseLevelOrderTraversal {
    public static List<List<Integer>>  bottomUpLevelOrderTraversal(TreeNode root){
        LinkedList<List<Integer>> result = new LinkedList<>();

        if (root == null) return result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()){
            int levelSize = queue.size();
            List<Integer> list = new LinkedList<>();

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                list.add(curr.val);

                if (curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);

            }

            result.addFirst(list);

        }
        return result;

    }
    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("P03. Reverse Level Order Traversal");
        System.out.println("=========================================================");
        // some test case example
        TreeNode rootP03 = new TreeNode(3);
        rootP03.left = new TreeNode(9);
        rootP03.right = new TreeNode(20);
        rootP03.right.left = new TreeNode(15);
        rootP03.right.right = new TreeNode(7);
        System.out.println("Input: "+ rootP03.toLevelOrderString() +" ,Output: " + makeItBold(bottomUpLevelOrderTraversal(rootP03).toString())+
                " ,Expected: [[15, 7], [9, 20], [3]]");

        rootP03 = new TreeNode(1);
        rootP03.left = new TreeNode(2);
        rootP03.right = new TreeNode(3);
        rootP03.left.left = new TreeNode(4);
        rootP03.left.right = new TreeNode(5);
        System.out.println("Input: "+ rootP03.toLevelOrderString() +" ,Output: " + makeItBold(bottomUpLevelOrderTraversal(rootP03).toString())+
                " ,Expected: [[4, 5], [2, 3], [1]]");
        rootP03 = new TreeNode(1);
        rootP03.left = new TreeNode(2);
        System.out.println("Input: "+ rootP03.toLevelOrderString() + " ,Output: " + makeItBold(bottomUpLevelOrderTraversal(rootP03).toString())+
                " ,Expected: [[2], [1]]");



    }
}

