package com.grokingcodeinterview.testYourKnowledge47Medium;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;
/*
Problem Statement:
Given the root of a Binary Search Tree (BST) and a value to insert, insert the value into the tree such that the BST property is maintained (every node's left subtree contains only values less than the node, and right subtree contains only values greater), then return the root of the modified tree.

You do not need to worry about balancing the tree â€” a simple, valid BST insertion is sufficient.

Example 1:Input:
                        4
                       / \
                      2   7
                     / \
                    1   3
insertValue = 5 Expected Output:
        4
       / \
      2   7
     / \  /
    1  3 5

(5 is less than 7, greater than 4, so it becomes 7's left child.)
Example 2: Input: root = null, insertValue = 10
Expected Output:  10
(Empty tree â€” the new value becomes the root.)

Example 3:Input:
    5
   / \
  3   8
insertValue = 3
Expected Output:
        5
       / \
      3   8
     /
    3

(Duplicate values â€” HackerRank's convention typically places duplicates as the left child; confirm this on the actual problem page if it matters, but this is the common default.)

    5r
   / \
  3   8
insertValue = 3


 */

public class P27BinaryTreeInsertion {

    public static TreeNode insertIntoBST(TreeNode root, int val){
        if(root == null) return new TreeNode(val);
        

        if(root.val <= val) { // got to right
            root.right= insertIntoBST(root.right, val);

        }else { // go to left
            root.left = insertIntoBST(root.left, val);
        }

        return root;
    }

}

