package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class P01LargestValueOnEachLevelOfABinaryTree {
    public static List<Integer> maxEachLevel(TreeNode root){
        List<Integer> result = new ArrayList<>();

        if (root == null) return result;
        if (root .left == null  && root.right == null){
            result.add(root.val);
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int levelMax = Integer.MIN_VALUE;

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            for (int i =0; i<levelSize; i++){
                TreeNode currentNode = queue.poll();
                levelMax = Math.max(currentNode.val, levelMax);
            }
            result.add(levelMax);
        }

       return result;
    }
}

