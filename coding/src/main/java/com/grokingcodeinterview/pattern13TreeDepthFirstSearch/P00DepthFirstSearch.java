package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;
import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeVisualizer;

import java.util.ArrayList;
import java.util.List;

public class P00DepthFirstSearch {

    public static List<List<Integer>> dfsBacktracking(TreeNode root){
        List<List<Integer>> paths = new ArrayList<>();
        if (root == null) return null;

        dfsHelper(root, new ArrayList<>(), paths);
        return paths;

    }

    public static void dfsHelper(TreeNode currentNode, List<Integer> currentPath, List<List<Integer>> paths){
        if (currentNode == null) return;

        currentPath.add(currentNode.val);

        if(currentNode.left == null && currentNode.right == null)
            paths.add(new ArrayList<>(currentPath));

        dfsHelper( currentNode.left, currentPath, paths);
        dfsHelper(currentNode.right, currentPath, paths);
        // remove what we added to the list
        currentPath.remove(currentPath.size() -1);
    }
    public static int dfsDivideAndConquer(TreeNode root){
        if (root == null) return 0;
        int left = dfsDivideAndConquer(root.left);
        int right = dfsDivideAndConquer(root.right);
        return root.val +left + right;
    }

    public static void main(String[] args) {
        // add some examples
            TreeNode root = new TreeNode(1);
            root.left = new TreeNode(2);
            root.right = new TreeNode(3);
            root.left.left = new TreeNode(4);
            root.left.right = new TreeNode(5);
            TreeVisualizer.printTree(root);
        System.out.println("Test 1  Backtracking (Expected [[1, 2, 4], [1, 2, 5], [1, 3]]): " + dfsBacktracking(root));
        System.out.println("Test 1  Divide and Conquer (Expected 15): " + dfsDivideAndConquer(root));

        root = new TreeNode(1);
        TreeVisualizer.printTree(root);
        System.out.println("Test 2 backtracking (Expected [[1]]): " + dfsBacktracking(root));
        System.out.println("Test 2 Divide and Conquer (Expected 1): " + dfsDivideAndConquer(root));

        root = null;
        System.out.println("Test 3 backtracking (Expected []): " + dfsBacktracking(root));
        System.out.println("Test 3 Divide and Conquer (Expected 0): " + dfsDivideAndConquer(root));
        // more complex tree example
            root = new TreeNode(1);
            root.left = new TreeNode(2);
            root.right = new TreeNode(3);
            root.left.left = new TreeNode(4);
            root.left.right = new TreeNode(5);
            root.right.left = new TreeNode(6);
            root.right.right = new TreeNode(7);
            TreeVisualizer.printTree(root);
        System.out.println("Test 4 backtracking (Expected [[1, 2, 4], [1, 2, 5], [1, 3, 6], [1, 3, 7]]): " + dfsBacktracking(root));
        System.out.println("Test 4 Divide and Conquer (Expected 28): " + dfsDivideAndConquer(root));
    }
}

