package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.Arrays;

import static com.Utility.makeItBold;
import static com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode.buildTree;

/*
 Problem Statement:
Given a binary tree where each node holds a single digit value (0â€“9), each root-to-leaf path represents a number formed by concatenating the digits along that path, from root to leaf. Find the total sum of all the numbers represented by all root-to-leaf paths in the tree.
A leaf node is a node with no children.
Example 1: Input:
       1
      / \
     0   1
    /     \
   1       6
Paths: 101, 116 Output: 217   (101 + 116)
Example 2:Input:
        4
       / \
      9   0
     / \
    5   1
Paths: 495, 491, 40 Output: 1026   (495 + 491 + 40)
Example 3:Input:
    1
     \
      5
Paths: 15 Output: 15

Example 4:Input:
          0
         / \
        1   3
       /   / \
      5   6   9
Paths: 015, 036, 039 Output: 90   (15 + 36 + 39) Note: leading zeros don't reduce the numeric value â€” 015 is just 15.

Example 5:Input:
          9
         / \
        8   7  (leaf)
       / \
      6   5


Paths: 986, 985, 97 Output: 2068   (986 + 985 + 97)Example 5 Input: [9, 8, 7, 6, 5]Paths: 986, 985, 97 Output: 2068 (986 + 985 + 97)
 * */
public class P06SumOfPathNumbers {
    public static  int sumOfPathNumbers(TreeNode root){
        if( root == null) return 0;


         return sumOfPathNumbers( root, 0);

    }

    public static int sumOfPathNumbers(TreeNode currentNode, int pathSum){
        if (currentNode == null){
            return 0;
        }

        pathSum = pathSum *10  + currentNode.val;
        if (currentNode.left == null && currentNode.right == null ){
            return pathSum;

        }
         return    sumOfPathNumbers(currentNode.left, pathSum)+
            sumOfPathNumbers(currentNode.right, pathSum);

    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("P03. Sum of Path Numbers");
        System.out.println("==================================================");
        Integer[] nums03 = {1, 2, 3};
        TreeNode root03 = buildTree(nums03);
        int result03 = sumOfPathNumbers(root03);
        System.out.println("Input: " + Arrays.toString(nums03) +", Output: " + makeItBold(result03 +"") +", Expected Output: 25 (12 + 13)");
//        TreeNode root1 = buildTree(new Integer[]{1, 2, 3});

        nums03 = new Integer[]{4, 9, 0, 5, 1};
         root03 = buildTree(nums03);
         result03 = sumOfPathNumbers(root03);
        System.out.println("Input: " + Arrays.toString(nums03) +", Output: " + makeItBold(result03 +"") +", Expected Output: 1026 (495 + 491 + 40)");
//
        nums03 = new Integer[]{1, null, 5};
         root03 = buildTree(nums03);
         result03 = sumOfPathNumbers(root03);
        System.out.println("Input: " + Arrays.toString(nums03) +", Output: " + makeItBold(result03 +"") +", Expected Output: 15");
//
        nums03 = new Integer[]{0, 1, 3, 5, null, 6, 9};
         root03 = buildTree(nums03);
         result03 = sumOfPathNumbers(root03);
        System.out.println("Input: " + Arrays.toString(nums03) +", Output: " + makeItBold(result03 +"") +", Expected Output: 90 (15 + 36 + 39)");
//

        nums03 = new Integer[]{9, 8, 7, 6, 5};
        root03 = buildTree(nums03);
        result03 = sumOfPathNumbers(root03);
        System.out.println("Input: " + Arrays.toString(nums03) +", Output: " + makeItBold(result03 +"") +", Expected Output: 2068 (986 + 985 + 97)");

    }

}

