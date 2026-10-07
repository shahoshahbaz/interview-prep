package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.ArrayList;
import java.util.List;

import static com.Utility.makeItBold;

/*
 * Given a binary tree, return all root-to-leaf paths.
 * Example: [1, 2, 3, null, 5] output [[1,2,5], [1, 3]]
 * Example: [10, 5, 12, 4, 7, null, 15] output [  [10, 5, 4],[10, 5, 7],[10, 12, 15]]
 */
public class P04AllRootToLeafPaths {



    public static  List<List<Integer>> findAllRootToPath(TreeNode root){
        List<List<Integer>> allPaths = new ArrayList<>();
        if (root == null ) return allPaths;
        findAllRootToPath(root, new ArrayList<>(), allPaths);
        return allPaths;
    }

    public static void findAllRootToPath(TreeNode currentNode, List<Integer> path, List<List<Integer>> allPaths){
        if (currentNode == null)
            return;
        path.add(currentNode.val);
        if (currentNode.left == null && currentNode.right == null){
            allPaths.add(new ArrayList<>(path));
        }
        findAllRootToPath(currentNode.left, path, allPaths);
        findAllRootToPath(currentNode.right, path, allPaths);

        // backtrack currentNode
        path.remove (path.size() -1);
    }
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("P04. All Root to Leaf Paths");
        System.out.println("==================================================");

        // Example 1: [1, 2, 3, null, 5]
        TreeNode rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(2);
        rootP04.right = new TreeNode(3);
        rootP04.left.right = new TreeNode(5);
        List<List<Integer>> listP04 = findAllRootToPath(rootP04);
        System.out.println("Input:  " + rootP04.toLevelOrderString() +"output: " +makeItBold(listP04.toString()) + ", Expected Output: [[1, 2, 5], [1, 3]]");

        // Example 2: [10, 5, 12, 4, 7, null, 15]
       rootP04 = new TreeNode(10);
        rootP04.left = new TreeNode(5);
        rootP04.right = new TreeNode(12);
        rootP04.left.left = new TreeNode(4);
        rootP04.left.right = new TreeNode(7);
        rootP04.right.right = new TreeNode(15);
        listP04 = findAllRootToPath(rootP04);
        System.out.println("Input:  " + rootP04.toLevelOrderString() +"output: " + makeItBold(listP04.toString()) + ", Expected Output: [[10, 5, 4], [10, 5, 7], [10, 12, 15]]");

        // Example 3: [1, 2, null, 3]
         rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(2);
        rootP04.left.left = new TreeNode(3);

       listP04 = findAllRootToPath(rootP04);
        System.out.println("Input:  " + rootP04.toLevelOrderString() +"output: " + makeItBold(listP04.toString()) + ", Expected Output: [[1, 2, 3]]");
        // Example 4: [1]
         rootP04 = new TreeNode(1);
        listP04 = findAllRootToPath(rootP04);
        System.out.println("Input:  " + rootP04.toLevelOrderString() +"output: " + makeItBold(listP04.toString()) + ", Expected Output: [[1]]");

        // Example 5: []
        TreeNode root5 = null;
        listP04 = findAllRootToPath(root5);
        System.out.println("Input:  " + (root5 == null ? "[]" : root5.toLevelOrderString()) +"output: " + makeItBold(listP04.toString()) + ", Expected Output: []");
    }


}

