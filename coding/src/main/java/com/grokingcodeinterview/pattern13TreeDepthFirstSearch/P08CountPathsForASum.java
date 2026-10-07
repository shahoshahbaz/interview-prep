package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.Arrays;
import java.util.HashMap;

import static com.Utility.makeItBold;

    /*
    Count Paths for a Sum (medium)
    Given a binary tree and a number â€˜Sâ€™,
     find all paths in the tree such that the sum of all the node values of each path equals â€˜Sâ€™.
     Please note that the paths can start or end at any node but all paths must follow direction from parent to child (top to bottom).
     Example: input =[1, 7, 9,6, 5, 2,3] S = 12, output= 3, E
     xplantion: There are 3 path with sum '12': 7->5, 1-> 9->2, 9->3
     Example 2: input = [5, 3, 8, 2, 4, 6, 10], S = 11, output = 2  Explanation: Paths are 5->3->3, 3->8
     Example 3: input = [10, 5, -3, 3, 2, 11, 3, -2, 1], S = 8, output = 2  Explanation: Paths are 10->-3->1, 5->3, 3->5
     **/

/**
          1
          / \
         7   9
       / \  / \
      6  5 2   3
 S = 12, output=
 */
public class P08CountPathsForASum {
    public static int countPaths(TreeNode root, int target){

        if(root == null ) return 0;

        return dfs(root, target) + countPaths(root.left, target) + countPaths(root.right , target);
    }

    private static int dfs(TreeNode root, int target){
        if(root == null) return 0;
        target -= root.val;
        int count = (target ==0) ?1:0;
        return count  + dfs(root.left,  target) + dfs(root.right, target);
    }



    public static int  countPaths_usingPrefixSum(TreeNode root, int targetSum){

        HashMap<Long, Integer> prefixSumMap = new HashMap<>();
        prefixSumMap.put(0L, 1); // what is L here>? 0L is a long literal, it represents the long value 0.
        // The L suffix indicates that the number is of type long. In Java, integer literals are of type int by default, so if you want to specify a long literal, you append an L (or l) to the number. In this case, 0L is used to represent the long value 0 in the prefixSumMap.
        return countPaths_usingPrefixSum(root, targetSum,  0L, prefixSumMap);
    }

    public static int countPaths_usingPrefixSum(TreeNode node, int targetSum,
                                                long currentSum, HashMap<Long, Integer> prefixSumMap){
        if (node == null) return 0;
        currentSum += node.val;

        int pathCount = prefixSumMap.getOrDefault(targetSum - currentSum, 0);

        prefixSumMap.put(currentSum, prefixSumMap.getOrDefault(currentSum, 0) + 1);

        pathCount += countPaths_usingPrefixSum(node.left,  targetSum , currentSum, prefixSumMap);
        pathCount += countPaths_usingPrefixSum(node.right, targetSum, currentSum, prefixSumMap);

        prefixSumMap.put(currentSum, prefixSumMap.get(currentSum) -1);

        return pathCount;

    }

    public static void main(String[] args) {
        System.out.println("==========================");
        System.out.println("P05. Count Paths for a Sum");
        System.out.println("==========================");
        // Example 1
        Integer[] numsP05 = new Integer[]{1, 7, 9, 6, 5, 2, 3};
        int sumP05 = 12;
        TreeNode rootP05 = TreeNode.buildTree(numsP05);

//        TreeVisualizer.printTree(rootP05);
        int resultP05 = countPaths(rootP05, sumP05);
        int resultP05_usingPrefixSum = countPaths_usingPrefixSum(rootP05, sumP05);
        System.out.println("Inputs: " + Arrays.toString(numsP05) +" sum :"+ sumP05 +  " ,output: "+
                makeItBold(resultP05+"") + ", expected outPut: 3");
        System.out.println("Inputs: " + Arrays.toString(numsP05) +" sum :"+ sumP05 +  " ,output: "+
                makeItBold(resultP05_usingPrefixSum+"") + ", expected outPut: 3 <== using prefix sum");

        numsP05 = new Integer[]{5, 3, 8, 2, 4, 6, 10};
        sumP05 = 11;
        rootP05 = TreeNode.buildTree(numsP05);
        resultP05 = countPaths(rootP05, sumP05);
        resultP05_usingPrefixSum = countPaths_usingPrefixSum(rootP05, sumP05);
        System.out.println("Inputs: " + Arrays.toString(numsP05) +" sum :"+ sumP05 +  " ,output: "+
                makeItBold(resultP05+"") + ", expected outPut: 0");
        System.out.println("Inputs: " + Arrays.toString(numsP05) +" sum :"+ sumP05 +  " ,output: "+
                makeItBold(resultP05_usingPrefixSum+"") + ", expected outPut: 0 <== using prefix sum");
//        TreeVisualizer.printTree(rootP05);

        // Example 3
        numsP05 = new Integer[]{10, 5, -3, 3, 2, 11, 3, -2, 1};
        sumP05 = 8;
        rootP05 = TreeNode.buildTree(numsP05);
//        System.out.println(rootP05.toLevelOrderString());
//        TreeVisualizer.printTree(rootP05);
        resultP05 = countPaths(rootP05, sumP05);
        resultP05_usingPrefixSum = countPaths_usingPrefixSum(rootP05, sumP05);
        System.out.println("Inputs: " + Arrays.toString(numsP05) +" sum :"+ sumP05 +  " ,output: "+
                makeItBold(resultP05+"") + ", expected outPut: 2");
        System.out.println("Inputs: " + Arrays.toString(numsP05) +" sum :"+ sumP05 +  " ,output: "+
                makeItBold(resultP05_usingPrefixSum+"") + ", expected outPut: 2 <== using prefix sum");
//        TreeVisualizer.printTree(rootP05);
        }

}

