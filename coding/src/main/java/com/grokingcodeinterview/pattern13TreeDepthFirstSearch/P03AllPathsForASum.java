package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.ArrayList;
import java.util.List;

/*
All Paths for a Sum

Problem Statement:
Given a binary tree and a target sum,
 find all root-to-leaf paths where the sum of the node values along the path equals the given target sum.

A leaf node is a node with no children.

Example 1:

Input:
        12
       /  \
      7    1
     /    /  \
    4    10   5

targetSum = 23

Tree structure:
         12
        /  \
       7    1
      /    /  \
     4    10   5

Expected Output: [[12, 7, 4], [12, 1, 10]]

Justification:

Path 12 â†’ 7 â†’ 4 = 23 âœ“
Path 12 â†’ 1 â†’ 10 = 23 âœ“
Path 12 â†’ 1 â†’ 5 = 18 âœ— (doesn't match)

Example 2:

Input: root = [1, 2, 3], targetSum = 5
Expected Output: []

(No root-to-leaf path sums to 5 â€” 1â†’2=3, 1â†’3=4.)
 */
public class P03AllPathsForASum {

    public static List<List<Integer>> finAllPath(TreeNode root, int target){
        List<List<Integer>> allPath = new ArrayList<>();

        if(root == null) return allPath;

        dfs(root, target, new ArrayList<>(), allPath);
        return allPath;





    }
    public static void dfs(TreeNode node, int targetSum, List<Integer> currPath, List<List<Integer>> allPath){
        if(node == null) return ;
        currPath.add(node.val);

        targetSum -= node.val;

        if(node.left == null && node.right == null && targetSum ==0 ) {
            allPath.add(new ArrayList<>(currPath));

        }
        dfs(node.left, targetSum, currPath, allPath);
        dfs(node.right, targetSum, currPath, allPath);

        currPath.remove(currPath.size() -1);

    }
}

