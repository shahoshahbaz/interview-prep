package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.*;

public class Pattern13TreeBreadthFirstSearchR3 {
    public static  List<List<Integer>> binaryTreeLevelOrdertraverse(TreeNode root){
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) return result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()){
            int levelSize = queue.size();
            List<Integer> list = new ArrayList<>();

            for (int i =0; i< levelSize; i++){
                TreeNode node = queue.poll();

                list.add(node.val);

                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);


            }
            result.add(list);




        }
        return result;
    }

    public static LinkedList<LinkedList<Integer>>  bottomUpLevelOrderTraversal(TreeNode root){
        LinkedList<LinkedList<Integer>> result = new LinkedList<>();

        if(root == null) return result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();

            LinkedList<Integer> list = new LinkedList<>();

            for(int i =0; i< levelSize; i++){
                TreeNode node = queue.poll();
                list.add(node.val);

                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);

            }
            result.addFirst(list);
        }

        return result;


    }
    public static List<LinkedList<Integer>> zigzagTraversal(TreeNode root) {
        LinkedList<LinkedList<Integer>> result = new LinkedList<>();
        if(root == null) return result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        boolean leftToRight = true;

        while (!queue.isEmpty()){
            int levelSize = queue.size();

            LinkedList<Integer> list = new LinkedList<>();

            for(int i =0; i< levelSize; i++){
                TreeNode node = queue.poll();
                if(leftToRight){
                    list.add(node.val);
                }else{
                    list.addFirst(node.val);
                }

                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);
            }
            leftToRight = !leftToRight;
            result.add(list);
        }
        return result;
    }

    public static List<Double> findLevelAverages(TreeNode root){
        List<Double> list = new ArrayList<>();

        if(root == null) return list;

        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            double currSum =0.0;
            for (int i =0; i< levelSize; i++ ){
                TreeNode node = queue.poll();
                currSum += node.val;

                if(node.left != null ) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);

            }
            list.add(currSum/levelSize);
        }

        return list;
    }
    /*
     root = [3,9,20,null,null,15,7]
        3
       /\
       9 20
         /\
         15 7

         root = [2,null,3,null,4,null,5,null]
                        2
                        \
                        3
                         \
                          4

     */
    public static int findMinDepth(TreeNode root){
        if (root == null) return 0;


        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int currDepth =1;



        while(!queue.isEmpty()){
            int levelSize= queue.size();
            for(int i =0; i< levelSize; i++){
                TreeNode node = queue.poll();
                if (node.left == null && node.right == null) return currDepth;
                if(node.left != null ) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);

            }
            currDepth ++;
        }
        return currDepth;
    }
    /**
     *   Example 1: Input: root = [3,9,20,null,null,15, 7] output: 3
     *                          3
     *                         / \
     *                        9   20
     *                           / \
     *                          15  17
     *
     *
     *
     *     Example 2: Input: root = [1,null,2] output: 2
     *     Example 3: Input: root = [] output: 0
     *     Example 4: Input: root = [1] output: 1

     */
    public static int findMaxDepth(TreeNode root){
        if(root == null) return 0;
        int currDepth = 0;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        currDepth ++;

        while(!queue.isEmpty()){
            int levelSize = queue.size();

            for(int i =0; i<levelSize; i++){
                TreeNode node = queue.poll();



                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);
            }
            currDepth ++;

        }
        return currDepth;
    }

    /**
     * Example 1: Input: root = [1, 2, 3, 4, 5, 6, 7], key = 3 output: 4
     *                                1
     *                               /   \
     *                               2    3
     *                            / \     / \
     *                            4  5  6    7
     * Example 2: Input: root = [12, 7, 1, null, 9, 10, 5, null, 3], key = 9 output: 10
     *
     *                               12,
     *                               /   \
     *                              7,    1,
     *                            /  \    / \
     *                          null, 9,  10, 5,
     *                               /  \
     *                            null,  3
     *
     * Example 3: Input: root = [1, 2, 3], key = 3 output: null
     */
    public static Integer findLevelOrderSuccessor(TreeNode root, int key){
        if (root == null) return null;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            for(int i =0; i<levelSize; i++){
                TreeNode node = queue.poll();

                if(!queue.isEmpty() && node.val == key) return queue.peek().val;

                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);
            }
        }
        return null;
    }
    /*
    Problem Statement
    Given a root of the binary tree, connect each node with its level order successor.
    The last node of each level should point to a null node.
            Examples
    Example 1: Input: root = [1, 2, 3, 4, 5, 6, 7] output: [1, 2, 3, 4, 5, 6, 7] (with next pointers connected as described
    Example 2: Input: root = [12, 7, 1, 9, null, 10, 5] output: [12, 7, 1, 9, null, 10, 5] (with next pointers connected as described
    example 3: Input: root = [1] output: [1] (with next pointer connected as described
    example 4: Input: root = [] output: [] (with next pointer connected as described
    example 5: Input: root = [1, 2, null, 3] output: [1, 2, null, 3] (with next pointer connected as described
    example 6: Input: root = [1, null, 2, null, 3] output: [1, null, 2, null, 3] (with next pointer connected as described

 */

    public static class TreeNodewithNext{
        TreeNodewithNext left;
        TreeNodewithNext right;
        TreeNodewithNext next;
        int val;

        public TreeNodewithNext(TreeNodewithNext left, TreeNodewithNext right, TreeNodewithNext next, int val) {
            this.left = left;
            this.right = right;
            this.next = next;
            this.val = val;
        }
    }

    /**
     * [12, 7, 1, 9, null, 10, 5] output: [12, 7, 1, 9, null, 10, 5]
     *
     *
     *                        12rc,
     *
     *                     7c,    1
     *
     *                9, null,  10,  5
     */

    public static TreeNodewithNext connectLevelOrderSiblings(TreeNodewithNext root){
        if(root == null) return null;
        TreeNodewithNext curr ;
        Deque<TreeNodewithNext>  queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();

            for(int i=0; i<levelSize; i++){
                 curr = queue.poll();
    // TODO: The last node on each level points to null.
                if(i == levelSize -1){
                    curr.next = null;


                }else{
                    curr.next = queue.peek();
                }
                if(curr.left != null) queue.offer(curr.left);
                if(curr .right != null) queue.offer(curr.right);
            }

        }

        return root;
    }

    /**
     * Input:
     *         1c
     *        / \
     *       2   3
     *      / \ / \
     *     4  5 6  7
     *
     * Output:
     * 1 â†’ 2 â†’ 3 â†’ 4 â†’ 5 â†’ 6 â†’ 7 â†’ null
     */
    public static TreeNodewithNext connectAllLevelOrderSiblings(TreeNodewithNext root){

        if(root == null) return null;


        Deque<TreeNodewithNext> queue = new ArrayDeque<>();

        queue.offer(root);




        while(!queue.isEmpty()){


                TreeNodewithNext node = queue.poll();
                node.next = queue.peek();



                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);



        }
        return root;
    }

    /**
     * Input:
     *         1
     *        / \
     *       2   3
     *      / \   \
     *     4   5   6
     *
     * Output: [1, 3, 6]
     */

    public static  List<Integer> rightViewBinaryTraverse(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        if (root == null)return result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){

            int levelSize = queue.size();
            for(int i =0; i<levelSize; i++){
                TreeNode node = queue.poll();
                if(i == levelSize -1) result.add(node.val);

                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);

            }



        }

        return result;


    }

     public static List<Integer> leftViewBinaryTraverse(TreeNode root){
        List<Integer> list = new ArrayList<>();
        if (root == null ) return list;


        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();

            for(int i =0; i< levelSize; i++){
                TreeNode node = queue.poll();
                if( i == 0) list.add(node.val);

                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);
            }
        }
        return list;
     }


















































































}

