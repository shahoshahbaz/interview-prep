package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a binary tree, find the length of its diameter.
The diameter of a tree is the number of nodes on the longest path between any two leaf nodes.
The diameter of a tree may or may not pass through the root.

example 1:
Input: root = [1, 2, 3, 4, 5] output: 4
Explanation: The longest path between any two leaf nodes is 4, which is the path [4, 2, 1, 3] or [5, 2, 1, 3].
example 2:
Input: root = [1, 2, null, 3, null, 4, null] output: 4
Explanation: The longest path between any two leaf nodes is 4, which is the path [3, 2, 1, 4].
example 3: Input: root = [1, 2, 3, 4, 5, 6, 7] output: 5
Explanation: The longest path between any two leaf nodes is 5, which is the path [4, 2, 1, 3, 7] or [5, 2, 1, 3, 6].
Note: You can always assume that there are at least two leaf nodes in the given tree.
Constraints:

n == edges.length + 1
1 <= n <= 104
0 <= ai, bi < n
ai != bi
 */
public class P09TreeDiameter {

    public static int lengthOfDiameter(TreeNode root){

        int[] diameter = new int[1];

        height(root, diameter);
        return diameter[0];
    }

    private static int height(TreeNode root, int[] diameter){
        if (root == null ) return 0;

        int heightLeft = height(root.left, diameter);
        int heightRight = height(root.right, diameter);
        diameter[0] = Math.max(diameter[0], heightLeft + heightRight +1);

       return 1 + Math.max(heightLeft, heightRight);


    }
    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("P09. Tree Diameter");
        System.out.println("===========================");

        TreeNode rootP09 =TreeNode.buildTree(new Integer[]{1, 2, 3, 4, 5, null, null});
//        TreeVisualizer.printTree(rootP09);
        System.out.println("Input: " + rootP09.toLevelOrderString() +", output: "+ makeItBold(lengthOfDiameter(rootP09) +"") + " Expected Output: 4");

        rootP09 = TreeNode.buildTree(new Integer[]{1, 2, null, 3, null, 4, null});
//        TreeVisualizer.printTree(rootP09);
        System.out.println("Input: " + rootP09.toLevelOrderString() +", output: "+ makeItBold(lengthOfDiameter(rootP09) +"") + " Expected Output: 4");

         rootP09 = TreeNode.buildTree(new Integer[]{1, 2, 3, 4, 5, 6, 7});
//        TreeVisualizer.printTree(rootP09);
        System.out.println("Input: " + rootP09.toLevelOrderString() +", output: "+ makeItBold(lengthOfDiameter(rootP09) +"") + " Expected Output: 5");
         rootP09 = TreeNode.buildTree(new Integer[]{1});
//        TreeVisualizer.printTree(rootP09);
        System.out.println("Input: " + rootP09.toLevelOrderString() +", output: "+ makeItBold(lengthOfDiameter(rootP09) +"") + " Expected Output: 1");
         rootP09 = TreeNode.buildTree(new Integer[]{});
//        TreeVisualizer.printTree(rootP09);
        System.out.println("Input:  []" + ", output: "+makeItBold(lengthOfDiameter(rootP09) +"") + " Expected Output: 0");
    }

}

