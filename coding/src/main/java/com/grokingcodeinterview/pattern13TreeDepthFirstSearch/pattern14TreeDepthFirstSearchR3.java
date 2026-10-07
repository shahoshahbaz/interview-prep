package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class pattern14TreeDepthFirstSearchR3 {

    /**
     *          1
     *        / \
     *       2   3
     *      / \
     *     4   5
     *
     * Paths: 1â†’2â†’4=7, 1â†’2â†’5=8, 1â†’3=4
     * Output: 4
     * dfs(1) =>  1+ min (dfs(2), dfs(3)) = 1+ (6,3) = 4
     * dfs(2) = 2 + min(dfs(4), dfs(5)) = 6
     * dfs(3 ) = 3
     *

     */

    public static int minRootToLeafSum(TreeNode root){
        if (root == null) return 0;

        int left = minRootToLeafSum(root.left);
        int right = minRootToLeafSum(root.right);

        if(root.left == null ) return root.val + right;
        if(root.right == null) return root.val + left;
        return root.val + Math.min(left, right);

    }

    private static  boolean hasPath(TreeNode root, int sum){
        if(root == null) return false;

        sum -= root.val;
        if( root.left == null && root.right== null && sum == 0) return true;
        return hasPath(root.left, sum) || hasPath(root.right, sum);


    }
    public static  List<String> findAllRootToPath(TreeNode root){
        List<String> allPath = new ArrayList<>();
        if(root == null) return allPath;

        dfs(root, "", allPath);
        return allPath;


    }
    public static void dfs(TreeNode root, String currPath,  List<String> allPath){
        if(root == null) return;
        currPath =  currPath.isEmpty()? String.valueOf(root.val):currPath + "->" + root.val;
        if(root.left == null && root.right == null)
            allPath.add(currPath);
        dfs(root.left, currPath, allPath);
        dfs(root.right, currPath, allPath);

        // / no backtracking needed â€” String is immutable

    }

    public static class Result{
        int maxSum = Integer.MIN_VALUE;
        List<Integer> maxPath = new ArrayList<>();
    }
    private static List<Integer> findMaxRootToLeafPath(TreeNode root){
        if(root == null) return null;
        Result result = new Result();

        dfs(root, 0, new ArrayList<>(), result);
        return result.maxPath;

    }
    private static void dfs(TreeNode node,int currSum , List<Integer> currentPath,  Result result){
        if(node == null) return;
        currentPath.add(node.val);
        currSum += node.val;

        if(node.left == null && node.right == null && currSum > result.maxSum){
            result.maxSum = currSum;
            result.maxPath= new ArrayList<>(currentPath);


        }
        dfs(node.left, currSum, currentPath, result);
        dfs(node.right, currSum, currentPath, result);

        // back tracking
        currentPath.remove(currentPath.size() -1);



    }

    /**
     *      1
     *        / \
     *       2   3
     *      / \
     *     4   5
     *     dfs(1 , 0) = >  sum = 1return left + right
     *              left = dfs(2, 12) => sum = 12 * 10 : return  124 + 125 = 249 + 13
     *              right = dfs(3, 1) => sum: 10 + 3 = 13
     *
     *         dfs(4, 2) sum = 40 + 0 = 40
     *         dfs(5, 2) sum = 50
     *
     *
     * Paths: 1â†’2â†’4 = 124
     *        1â†’2â†’5 = 125
     *        1â†’3   = 13
     *
     * Output: 124 + 125 + 13 = 262
     */
    public static int sumOfPathNumbers(TreeNode node){
        if(node == null) return 0;
        return dfs(node, 0);


    }

    public static int dfs(TreeNode node, int sum){
        if(node == null) return 0;
        sum +=sum *10 + node.val;
        if(node.left == null && node.right == null) return sum;

        int left = dfs(node.left, sum);
        int right = dfs(node.right, sum);
        return  left+ right;


    }
    public static int lengthOfDiameter(TreeNode root){
        if(root == null) return 0;

        int[] arr = new int[1];
        return dfsPostOrder(root, arr);




    }

    private static int dfsPostOrder(TreeNode root, int [] arr){
        if(root == null) return 0;
        int left = dfsPostOrder(root.left, arr);
        int right = dfsPostOrder(root.right, arr);
        arr[0] = Math.max(arr[0] , left +  right +1);
        return arr[0];
    }


















































}


