package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import static com.Utility.makeItBold;

/*
    Given a binary tree and a number sequence,
 find if the sequence is present as a root-to-leaf path in the given tree.
     Example: Input: root = [1, 7, 9, null, null, 2, 9], sequence = [1, 9, 9]
     Output: true
     Explanation: The path 1 -> 9 -> 9 exists in the tree and matches the sequence.
     Example: Input: root = [1, 7, 9, null, null, 2, 9], sequence = [1, 0, 9]
        Output: false
        Explanation: There is no path in the tree that matches the sequence.
        Example: Input: root = [1, 0, 1, 1, null, 6, 5], sequence = [1, 0, 7]
        Output: false
        Explanation: There is no path in the tree that matches the sequence.
        Example: Input: root = [1, 7, 9, null, null, 2, 9], sequence = [1, 7, 9]
        Output: true
        Explanation: The path 1 -> 7 -> 9 exists in the tree and matches the sequence.
    Constraints:
    1 <= arr.length <= 5000
    0 <= arr[i] <= 9
Each node's value is between [0 - 9].

 */
public class P07PathWithGivenSequence {

    public static boolean findPath(TreeNode root, int[] sequence){
    if (root == null ) return false;
    return dfs(root,0,  sequence);
    }

    public static boolean dfs(TreeNode currentNode,int level, int[] sequence){
        if (currentNode == null || level> sequence.length ||
        sequence[level] != currentNode.val)
            return false;

        if (currentNode.left == null  && currentNode.right == null &&
                level == sequence.length-1)
            return true;

        return dfs(currentNode.left, level+1, sequence)||
        dfs(currentNode.right, level+1, sequence);
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("P04. Path with Given Sequence");
        System.out.println("==================================================");
        Integer[] numsP04= new Integer[]{1, 7, 9, null, null, 2, 9};
        int[] pathP04 = new int[]{1 , 9 , 9};
        TreeNode rootP04 = TreeNode.buildTree(numsP04);
//        TreeVisualizer.printTree(rootP04);
        System.out.println("Input: " + rootP04.toLevelOrderString() + " ,Sequence: " + java.util.Arrays.toString(pathP04) + " ,Output: " + makeItBold(findPath(rootP04, pathP04) +"") + " ,Expected: true");

        numsP04= new Integer[]{1, 7, 9, null, null, 2, 9};
        pathP04 = new int[]{1 , 0 , 9};
        rootP04 = TreeNode.buildTree(numsP04);
        System.out.println("Input: " + rootP04.toLevelOrderString() + " ,Sequence: " + java.util.Arrays.toString(pathP04) + " ,Output: " + makeItBold(findPath(rootP04, pathP04) +"") + " ,Expected: false");

        numsP04= new Integer[]{1, 0, 1, 1, null, 6, 5};
        pathP04 = new int[]{1 , 0 , 7};
        rootP04 = TreeNode.buildTree(numsP04);
        System.out.println("Input: " + rootP04.toLevelOrderString() + " ,Sequence: " + java.util.Arrays.toString(pathP04) + " ,Output: " + makeItBold(findPath(rootP04, pathP04) +"") + " ,Expected: false");

        numsP04= new Integer[]{1, 7, 9, null, null, 2, 9};;
         pathP04 = new int[]{1 , 7 , 9};
         rootP04 = TreeNode.buildTree(numsP04);
        System.out.println("Input: " + rootP04.toLevelOrderString() + " ,Sequence: " + java.util.Arrays.toString(pathP04) + " ,Output: " + makeItBold(findPath(rootP04, pathP04) +"") + " ,Expected: true");
        }



}

