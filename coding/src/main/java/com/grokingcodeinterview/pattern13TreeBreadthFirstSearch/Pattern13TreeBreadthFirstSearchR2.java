package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;


import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;

import java.util.*;

import static com.Utility.makeItBold;

public class Pattern13TreeBreadthFirstSearchR2 {
     /*
    problem statement:
    Given a root of the binary tree, return an array containing nodes in its left view.
    The left view of a binary tree consists of nodes that are visible when the tree is viewed from the left side. For each level of the tree, the first node encountered in that level will be included in the left view.

            Examples
    Example 1
    Input: root = [1, 2, 3, 4, 5, 6, 7] Expected Output: [1, 2, 4]
    Input: root = [12, 7, 1, null, 9, 10, 5, null, 3] Expected Output: [12, 7, 9, 3]
    Input: root = [1, null, 2, null, 3, null, 4] Expected Output: [1, 2, 3, 4]

    constraints:
    The number of nodes in the tree is in the range [0, 10^5].
            -10^5 <= Node.val <= 10^5
     */
    public static  List<Integer>  leftViewBinaryTraverse(TreeNode root){

        List<Integer> list= new ArrayList<>();

        if (root == null) return list;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();

            for (int i =0; i< levelSize; i++){

                TreeNode curr = queue.poll();

                if (i == 0) list.add(curr.val);
                if(curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);
            }
        }
        return list;
    }
    /*
Problem Statement
Given a root of the binary tree and an integer key,
 find the level order successor of the node containing
 the given key as a value in the tree.

The level order successor is the node that appears right after
the given node in the level order traversal.
examples
Example 1: Input: root = [1, 2, 3, 4, 5, 6, 7], key = 3 output: 4
Example 2: Input: root = [12, 7, 1, null, 9, 10, 5, null, 3], key = 9 output: 10
Example 3: Input: root = [1, 2, 3], key = 3 output: null
Constraints:
The number of nodes in the tree is in the range [0, 105].
-1000 <= Node.val <= 1000

 */

    public static Integer findLevelOrderSuccessor(TreeNode root, int key){
        int result = Integer.MAX_VALUE;
        if (root == null) return null;
       Deque<TreeNode> queue = new ArrayDeque<>();
       queue.offer(root);

       while(!queue.isEmpty()){
           int levelSize = queue.size();

           for(int i =0; i< levelSize; i++){
               TreeNode curr = queue.poll();
               if (curr.val == key) {
                   result =  (queue.isEmpty())? Integer.MAX_VALUE: queue.peek().val;
                   break;
               }
               if(curr.left != null) queue.offer(curr.left);
               if(curr.right != null) queue.offer(curr.right);
           }
       }
        return result == Integer.MAX_VALUE? null: result;

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

    public static P10ConnectLevelOrderSiblings.TreeNode       connectLevelOrderSiblings(P10ConnectLevelOrderSiblings.TreeNode root){
        if (root == null) return null;
        Deque<P10ConnectLevelOrderSiblings.TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            P10ConnectLevelOrderSiblings.TreeNode  prev = null;
            for (int i =0; i< levelSize; i++){
                P10ConnectLevelOrderSiblings.TreeNode curr = queue.poll();

                if (prev == null){ //
                    prev = curr;
                }else{
                    prev.next = curr;
                    prev = curr;
                }

                if (i == levelSize -1) {
                    curr.next = null;
                }


                if (curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);

            }
        }

        return root;

    }
    public static String getLevelOrderUsingNextString(P10ConnectLevelOrderSiblings.TreeNode root) {
        StringBuilder sb = new StringBuilder();
        P10ConnectLevelOrderSiblings.TreeNode levelStart = root;

        while (levelStart != null) {
            sb.append("[");
            P10ConnectLevelOrderSiblings.TreeNode curr = levelStart;
            levelStart = null;

            while (curr != null) {
                sb.append(curr.val).append(" ");

                if (levelStart == null) {
                    if (curr.left != null) levelStart = curr.left;
                    else if (curr.right != null) levelStart = curr.right;
                }
                curr = curr.next;
            }
            sb.append("-> null] ");
        }
        return sb.toString();
    }

    /*
     problem statement:
     Given a root of the binary tree, return an array containing nodes in its right view.
     The right view of a binary tree consists of nodes that are visible when the tree is viewed from the right side. For each level of the tree, the last node encountered in that level will be included in the right view.
     Examples
     Example 1
     Input: root = [1, 2, 3, 4, 5, 6, 7]  Expected Output: [1, 3, 7]
     Input: root = [12, 7, 1, null, 9, 10, 5, null, 3]  Expected Output: [12, 1, 5, 3]
        Input: root = [1, null, 2, null, 3, null, 4]     Expected Output: [1, 2, 3, 4]

        constraints:
        The number of nodes in the tree is in the range [0, 10^5].
        -10^5 <= Node.val <= 10^5
     */

    /**
     * Input: root = [1, 2, 3, 4, 5, 6, 7]  Expected Output: [1, 3, 7]
     *                  1
     *                /  \
     *               2    3
     *             /  \  /  \
     *            4   5  6   7

     */
    public static List<Integer> rightViewBinaryTraverse(TreeNode root){
        List<Integer> list = new ArrayList<>();
        if (root == null) return list;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();

            for (int i =0 ; i< levelSize; i++){
                TreeNode curr = queue.poll();
                if (i == levelSize -1) list.add(curr.val); // if this is last node, adde it to tlist

                if(curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);
            }
        }
        return list;

    }
    /*
  Find the largest value on each level of a binary tree.
 */
    public static List<Integer> LargestValues(TreeNode root){
        List<Integer> list = new ArrayList<>();

        if(root == null) return list;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){

            int levelSize = queue.size();
            int largest = Integer.MIN_VALUE;

            for (int i =0; i<levelSize; i++){
                TreeNode curr = queue.poll();

                largest = Math.max(largest, curr.val);

                if(curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);

            }

            list.add(largest);

        }
        return list;
    }
    /*
    Problem Statement:
 Given a binary tree, find its maximum depth (or height) using Tree BFS traversal.
 examples
    Example 1: Input: root = [3,9,20,null,null,15, 7] output: 3
    Example 2: Input: root = [1,null,2] output: 2
    Example 3: Input: root = [] output: 0
    Example 4: Input: root = [1] output: 1
    Constraints:
    The number of nodes in the tree is in the range [0, 105].
    -1000 <= Node.val <= 1000
 */

    /**
     * Input: root = [3,9,20,null,null,15, 7] output: 3
     *       3
     *     /   \
     *    9     20
     *         /  \
     *        15   7
     */
    public static int findMaxDepth(TreeNode root){

        if (root == null) return 0;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int currentLevel =0;

        while (!queue.isEmpty()){
            int levelSize = queue.size();
            currentLevel++;

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();

                if (curr.left != null) queue.offer(curr.left);
                if( curr.right != null) queue.offer(curr.right);


            }
        }
        return currentLevel;

    }
    /*
    Problem Statement
    Given a binary tree, populate an array to represent its zigzag level order traversal.
     You should populate the values of all nodes of the first level from left to right,
      then right to left for the next level and keep alternating in the same manner for the following levels.

    Example 1: Input: root = [1, 2, 3, 4, 5, 6, 7] Expected Output: [[1], [3, 2], [4, 5, 6, 7]]
    justification:
    The first level has 1 node.
    The second level has 3 and 2 nodes in reverse order.
    The third level has 4, 5, 6, and 7 nodes.
    Example 2: Input: root = [12, 7, 1, null, 9, 10, 5] Expected Output: [[12], [1, 7], [9, 10, 5]]
    justification:
    The first level has 12 node.
    The second level has 1 and 7 nodes in reverse order.
    The third level has 9, 10, and 5 nodes.
    Constraints:
    The number of nodes in the tree is in the range [0, 2000].
    -1000 <= Node.val <= 1000
 */
    public static List<List<Integer>> zigzagTraversal(TreeNode root){
        LinkedList<List<Integer>> result = new LinkedList<>();
        if (root == null ) return  result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        boolean leftToRight = true;
        while(!queue.isEmpty()){
            int levelSize = queue.size();

            LinkedList<Integer> listLevel = new LinkedList<>();

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();

                if(leftToRight)
                    listLevel.add(curr.val);
                else
                    listLevel.addFirst(curr.val);


                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);

            }
            result.add(listLevel);

            leftToRight = !leftToRight;

        }
        return result;
    }

    public static List<Double>  findLevelAverages(TreeNode root){
        List<Double> result = new ArrayList<>();
        if (root == null) return result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();

            double sumLevel =0;

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                sumLevel += curr.val;

                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }

            result.add((double)sumLevel/levelSize);
        }
        return result;
    }
        /*
    Problem Statement
    Given the root of a binary tree, return the bottom-up level order traversal of its nodes' values. (i.e., the lowest level comes first in left to right order.)

    Example 1: Input: root = [1, 2, 3, 4, 5, 6, 7] Expected Output: [[4, 5, 6, 7], [2, 3], [1]]
    Justification:
    The third level has 4, 5, 6, and 7 nodes.
    The second level has 2 and 3 nodes.
    The first level has a single node with the value 1.
    Example 2: Input: root = [12, 7, 1, null, 9, 10, 5] Expected Output: [[9, 10, 5], [7, 1], [12]]
    Justification:
    The third level has 9, 10, and 5 nodes.
    The second level has 7 and 1 nodes.
    The first level has a single node with the value 12.
    Example 3: Input: root = [6,5,2,null,null,1,6,3,56,3] Expected Output: [[3,56,3],[1,6],[5,2],[6]]
    Justification:
    The fourth level has 3, 56, and 3 nodes.
    The third level has 1, and 6 nodes.
    The second level has 5 and 2 nodes.
    The first level has a single node with the value 6.
    Constraints:
    The number of nodes in the tree is in the range [0, 2000].
    -1000 <= Node.val <= 1000
 */

    public static  List<List<Integer>>  bottomUpLevelOrderTraversal(TreeNode root){

        LinkedList<List<Integer>> result = new LinkedList<>();

        if (root == null) return result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            List<Integer> listLevel = new ArrayList<>();

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();

                listLevel.add(curr.val);

                if(curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);
            }

            result.addFirst(listLevel);
        }


        return result;
    }
    /*
 Given a binary tree, populate an array to represent its level-by-level traversal.
 You should populate the
 values of all nodes of each level from left to right in separate sub-arrays.
 */
    public static List<List<Integer>>  binaryTreeLevelOrdertraverse(TreeNode root){
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            List<Integer> levelList = new ArrayList<>();
            for (int i =0; i< levelSize; i++){

                TreeNode curr = queue.poll();
                levelList.add(curr.val);

                if (curr.left != null) queue.add(curr.left);
                if (curr.right != null) queue.add(curr.right);

            }

            result.add(levelList);
        }
        return result;
    }

    /*
     * Given a root of the binary tree, return the sum of all nodes of the binary tree.
     */

    public static long sumOfNodes(TreeNode root){
        if (root == null) return 0;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int sum = 0;

        while(!queue.isEmpty()){
            int levelSize = queue.size();

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();

                sum += curr.val;

                if (curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);
            }
        }

        return sum;
    }
    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println("P01. Sum of all nodes in a binary tree");
        System.out.println("==================================");
        // Example 1
        TreeNode rootP01 = new TreeNode(1);
        rootP01.left = new TreeNode(2);
        rootP01.right = new TreeNode(3);
        System.out.println("Input: "+ rootP01.toLevelOrderString() +" ,Output: " + makeItBold(sumOfNodes(rootP01)+"")+
                " ,Expected: 6");


        // Example 2
        rootP01 = new TreeNode(4);
        rootP01.left = new TreeNode(9);
        rootP01.right = new TreeNode(7);
        rootP01.left.left = new TreeNode(2);
        rootP01.left.right = new TreeNode(6);
        System.out.println("Input: "+ rootP01.toLevelOrderString() +" ,Output: " + makeItBold(sumOfNodes(rootP01)+"")+
                " ,Expected: 28");
        // Example 3

        rootP01 = new TreeNode(10);
        rootP01.left = new TreeNode(5);
        rootP01.left.left = new TreeNode(3);
        rootP01.left.right = new TreeNode(7);
        rootP01.left.right.right = new TreeNode(9);
        System.out.println("Input: "+ rootP01.toLevelOrderString() +" ,Output: " + makeItBold(sumOfNodes(rootP01)+"")+
                " ,Expected: 34");
        System.out.println("=========================================================");
        System.out.println("P03. Binary Tree Level Order Traversal");
        System.out.println("=========================================================");

        // some test case example
        TreeNode rootP02 = new TreeNode(3);
        rootP02.left = new TreeNode(9);
        rootP02.right = new TreeNode(20);
        rootP02.right.left = new TreeNode(15);
        rootP02.right.right = new TreeNode(7);
        System.out.println("Input: "+ rootP02.toLevelOrderString() +" ,Output: " + makeItBold(binaryTreeLevelOrdertraverse(rootP02).toString())+
                " ,Expected: [[3], [9, 20], [15, 7]]");

        rootP02 = new TreeNode(1);
        rootP02.left = new TreeNode(2);
        rootP02.right = new TreeNode(3);
        rootP02.left.left = new TreeNode(4);
        rootP02.left.right = new TreeNode(5);
        System.out.println("Input: "+ rootP02.toLevelOrderString() +" ,Output: " + makeItBold(binaryTreeLevelOrdertraverse(rootP02).toString())+
                " ,Expected: [[1], [2, 3], [4, 5]]");

        rootP02 = new TreeNode(1);
        rootP02.left = new TreeNode(2);
        System.out.println("Input: "+ rootP02.toLevelOrderString() +" ,Output: " + makeItBold(binaryTreeLevelOrdertraverse(rootP02).toString())+
                " ,Expected: [[1], [2]]");

        System.out.println("=========================================================");
        System.out.println("P03. Reverse Level Order Traversal");
        System.out.println("=========================================================");
        // some test case example
        TreeNode rootP03 = new TreeNode(3);
        rootP03.left = new TreeNode(9);
        rootP03.right = new TreeNode(20);
        rootP03.right.left = new TreeNode(15);
        rootP03.right.right = new TreeNode(7);
        System.out.println("Input: "+ rootP03.toLevelOrderString() +" ,Output: " + makeItBold(bottomUpLevelOrderTraversal(rootP03).toString())+
                " ,Expected: [[15, 7], [9, 20], [3]]");

        rootP03 = new TreeNode(1);
        rootP03.left = new TreeNode(2);
        rootP03.right = new TreeNode(3);
        rootP03.left.left = new TreeNode(4);
        rootP03.left.right = new TreeNode(5);
        System.out.println("Input: "+ rootP03.toLevelOrderString() +" ,Output: " + makeItBold(bottomUpLevelOrderTraversal(rootP03).toString())+
                " ,Expected: [[4, 5], [2, 3], [1]]");
        rootP03 = new TreeNode(1);
        rootP03.left = new TreeNode(2);
        System.out.println("Input: "+ rootP03.toLevelOrderString() + " ,Output: " + makeItBold(bottomUpLevelOrderTraversal(rootP03).toString())+
                " ,Expected: [[2], [1]]");

        System.out.println("==============================================================");
        System.out.println("P05. Level Averages in a Binary Tree");
        System.out.println("==============================================================");

        TreeNode root04 = new TreeNode(12);
        root04.left = new TreeNode(7);
        root04.right = new TreeNode(1);
        root04.left.left = new TreeNode(9);
        root04.left.right = new TreeNode(2);
        root04.right.left = new TreeNode(10);
        root04.right.right = new TreeNode(5);
        System.out.println("Input: " + root04.toLevelOrderString() +", output: " + makeItBold(findLevelAverages(root04).toString()) +" Expected: [12.0, 4.0, 6.5]");

        root04 = new TreeNode(3);
        root04.left = new TreeNode(9);
        root04.right = new TreeNode(20);
        root04.right.left = new TreeNode(15);
        root04.right.right = new TreeNode(7);
        System.out.println("Input: " + root04.toLevelOrderString() +", output: " + makeItBold(findLevelAverages(root04).toString()) +" Expected: [3.0, 14.5, 11.0]");

        root04 = new TreeNode(5);
        root04.left = new TreeNode(3);
        root04.right = new TreeNode(8);
        root04.left.left = new TreeNode(1);
        root04.left.right = new TreeNode(4);
        root04.right.left = new TreeNode(7);
        root04.right.right = new TreeNode(9);
        System.out.println("Input: " + root04.toLevelOrderString() +", output: " + makeItBold(findLevelAverages(root04).toString()) +" Expected: [5.0, 5.5, 5.25]");

        System.out.println("======================================================================");
        System.out.println("P04: Zigzag Traversal");
        System.out.println("======================================================================");
        // some example with input and expected output
        TreeNode rootP04 = new TreeNode(3);
        rootP04.left = new TreeNode(9);
        rootP04.right = new TreeNode(20);
        rootP04.right.left = new TreeNode(15);
        rootP04.right.right = new TreeNode(7);
        System.out.println("Input: "+ rootP04.toLevelOrderString() +" ,Output: " + makeItBold(zigzagTraversal(rootP04).toString())+
                " ,Expected: [[3], [20, 9], [15, 7]]");

        rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(2);
        rootP04.right = new TreeNode(3);
        rootP04.left.left = new TreeNode(4);
        rootP04.left.right = new TreeNode(5);
        System.out.println("Input: "+ rootP04.toLevelOrderString() +" ,Output: " + makeItBold(zigzagTraversal(rootP04).toString())+
                " ,Expected: [[1], [3, 2], [4, 5]]");
        rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(2);
        rootP04.right = new TreeNode(3);
        rootP04.left.left = new TreeNode(4);
        rootP04.right.right = new TreeNode(5);
        System.out.println("Input: "+ rootP04.toLevelOrderString() +" ,Output: " + makeItBold(zigzagTraversal(rootP04).toString())+
                " ,Expected: [[1], [3, 2], [4, 5]]");

        // give more more complex tree
        rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(2);
        rootP04.right = new TreeNode(3);
        rootP04.left.left = new TreeNode(4);
        rootP04.left.right = new TreeNode(5);
        rootP04.right.left = new TreeNode(6);
        rootP04.right.right = new TreeNode(7);
        rootP04.left.left.left = new TreeNode(8);
        rootP04.left.left.right = new TreeNode(9);
        rootP04.right.right.left = new TreeNode(10);
        rootP04.right.right.right = new TreeNode(11);
        System.out.println("Input: "+ rootP04.toLevelOrderString() +" ,Output: " + makeItBold(zigzagTraversal(rootP04).toString())+
                " ,Expected: [[1], [3, 2], [4, 5, 6, 7], [11, 10, 9, 8]]"); System.out.println("==============================================================");
        System.out.println("P08. Maximum Depth of a Binary Tree");
        System.out.println("==============================================================");

        // Example 1: Complete binary tree
        TreeNode rootP08 = new TreeNode(1);
        rootP08.left = new TreeNode(2);
        rootP08.right = new TreeNode(3);
        rootP08.left.left = new TreeNode(4);
        rootP08.left.right = new TreeNode(5);
        System.out.println("Input: " + rootP08.toLevelOrderString() +", output: " + makeItBold(findMaxDepth(rootP08) +"") + ", Expected: 3");
        // Example 2: Tree with only left children
        rootP08 = new TreeNode(1);
        rootP08.left = new TreeNode(2);
        rootP08.left.left = new TreeNode(3);
        rootP08.left.left.left = new TreeNode(4);
        System.out.println("Input: " + rootP08.toLevelOrderString() +", output: " + makeItBold(findMaxDepth(rootP08) +"") + ", Expected: 4");

        // Example 3: Tree with only right children
        rootP08 = new TreeNode(1);
        rootP08.right = new TreeNode(2);
        rootP08.right.right = new TreeNode(3);
        rootP08.right.right.right = new TreeNode(4);
        System.out.println("Input: " + rootP08.toLevelOrderString() +", output: " + makeItBold(findMaxDepth(rootP08) +"") + ", Expected: 4");


        // Example 4: Tree with one node
        rootP08 = new TreeNode(1);
        System.out.println("Input: " + rootP08.toLevelOrderString() +", output: " + makeItBold(findMaxDepth(rootP08) +"") + ", Expected: 1");

        // Example 5: Empty tree
        rootP08 = null;
        System.out.println("Input: " + rootP08 +", output: " + makeItBold(findMaxDepth(rootP08) +"") + ", Expected: 0");
        System.out.println("==============================================================");
        System.out.println("P06. Largest Value on Each Level of a Binary Tree");
        System.out.println("==============================================================");
        TreeNode rootP06 = new TreeNode(1);
        rootP06.left = new TreeNode(3);
        rootP06.right = new TreeNode(2);
        rootP06.left.left = new TreeNode(5);
        rootP06.left.right = new TreeNode(3);
        rootP06.right.right = new TreeNode(9);
        System.out.println("Input: " + rootP06.toLevelOrderString() +", output: " + makeItBold(LargestValues(rootP06).toString()) +" Expected: [1, 3, 9]");

        rootP06 = new TreeNode(1);
        rootP06.left = new TreeNode(2);
        rootP06.right = new TreeNode(3);
        rootP06.left.left = new TreeNode(4);
        rootP06.right.right = new TreeNode(5);
        System.out.println("Input: " + rootP06.toLevelOrderString() +", output: " + makeItBold(LargestValues(rootP06).toString()) +" Expected: [1, 3, 5]");

        rootP06 = new TreeNode(1);
        rootP06.left = new TreeNode(2);
        rootP06.right = new TreeNode(3);
        rootP06.left.left = new TreeNode(4);
        rootP06.left.right = new TreeNode(5);
        rootP06.right.left = new TreeNode(6);
        rootP06.right.right = new TreeNode(7);
        System.out.println("Input: " + rootP06.toLevelOrderString() +", output: " + makeItBold(LargestValues(rootP06).toString()) +" Expected: [1, 3, 7]");
        System.out.println("=======================================================");
        System.out.println("P12. Right view of binary tree");
        System.out.println("=======================================================");
        // Example 1: Complete binary tree
        TreeNode rootP12 = new TreeNode(1);
        rootP12.left = new TreeNode(2);
        rootP12.right = new TreeNode(3);
        rootP12.left.left = new TreeNode(4);
        rootP12.left.right = new TreeNode(5);
        rootP12.right.left = new TreeNode(6);
        rootP12.right.right = new TreeNode(7);
        System.out.println("Input: " + rootP12.toLevelOrderString()+", Output: " +
                makeItBold(rightViewBinaryTraverse(rootP12).toString())
                + ", Expected Output: [1, 3, 7]");

        // Example 2: Tree with nulls
        rootP12 = new TreeNode(12);
        rootP12.left = new TreeNode(7);
        rootP12.right = new TreeNode(1);
        rootP12.left.right = new TreeNode(9);
        rootP12.right.left = new TreeNode(10);
        rootP12.right.right = new TreeNode(5);
        rootP12.left.right.left = new TreeNode(3);
        System.out.println("Input: " + rootP12.toLevelOrderString()+", Output: " +
                makeItBold(rightViewBinaryTraverse(rootP12).toString())
                + ", Expected Output: [12, 1, 5, 3]");

        // Example 3: Single node
        rootP12 = new TreeNode(42);
        System.out.println("Input: " + rootP12.toLevelOrderString()+", Output: " +
                makeItBold(rightViewBinaryTraverse(rootP12).toString())
                + ", Expected Output: [42]");

        // Example 4: Empty tree
        System.out.println("Input: null, Output: " +
                makeItBold(rightViewBinaryTraverse(null).toString())
                + ", Expected Output: []");

        System.out.println("==============================================================");
        System.out.println("P10. Connect Level Order Siblings");
        System.out.println("==============================================================");

        P10ConnectLevelOrderSiblings.TreeNode rootP10 = new P10ConnectLevelOrderSiblings.TreeNode(1);
        rootP10.left = new P10ConnectLevelOrderSiblings.TreeNode(2);
        rootP10.right = new P10ConnectLevelOrderSiblings.TreeNode(3);
        rootP10.left.left = new P10ConnectLevelOrderSiblings.TreeNode(4);
        rootP10.left.right = new P10ConnectLevelOrderSiblings.TreeNode(5);
        rootP10.right.left = new P10ConnectLevelOrderSiblings.TreeNode(6);
        rootP10.right.right = new P10ConnectLevelOrderSiblings.TreeNode(7);

        System.out.println("Input: " + rootP10.toLevelOrderString() + ", output:" + makeItBold(getLevelOrderUsingNextString(connectLevelOrderSiblings(rootP10))) + ", Expected: [1 -> null] [2 3 -> null] [4 5 6 7 -> null]");
        rootP10 = new P10ConnectLevelOrderSiblings.TreeNode(12);
        rootP10.left = new P10ConnectLevelOrderSiblings.TreeNode(7);
        rootP10.right = new P10ConnectLevelOrderSiblings.TreeNode(1);
        rootP10.left.left = new P10ConnectLevelOrderSiblings.TreeNode(9);
        rootP10.right.left = new P10ConnectLevelOrderSiblings.TreeNode(10);
        rootP10.right.right = new P10ConnectLevelOrderSiblings.TreeNode(5);
        System.out.println("Input: " + rootP10.toLevelOrderString() + ", output:" + makeItBold(getLevelOrderUsingNextString(connectLevelOrderSiblings(rootP10))) + ", Expected: [12 -> null] [7 1 -> null] [9 10 5 -> null]");
        System.out.println("==============================================================");
        System.out.println("P09. Level Order Successor");
        System.out.println("==============================================================");

        TreeNode rootP09 = new TreeNode(1);
        rootP09.left = new TreeNode(2);
        rootP09.right = new TreeNode(3);
        rootP09.left.left = new TreeNode(4);
        rootP09.left.right = new TreeNode(5);
        rootP09.right.left = new TreeNode(6);
        rootP09.right.right = new TreeNode(7);
        System.out.println("Input: " + rootP09.toLevelOrderString() + ", key: 3, output: " + makeItBold(findLevelOrderSuccessor(rootP09, 3) +"") + ", Expected: 4");

        rootP09 = new TreeNode(12);
        rootP09.left = new TreeNode(7);
        rootP09.right = new TreeNode(1);
        rootP09.left.left = new TreeNode(9);
        rootP09.right.left = new TreeNode(10);
        rootP09.right.right = new TreeNode(5);
        System.out.println("Input: " + rootP09.toLevelOrderString() + ", key: 9, output: " + makeItBold(findLevelOrderSuccessor(rootP09, 9) +"") + ", Expected: 10");

        rootP09 = new TreeNode(1);
        rootP09.left = new TreeNode(2);
        rootP09.right = new TreeNode(3);
        System.out.println("Input: " + rootP09.toLevelOrderString() + ", key: 3, output: " + makeItBold(findLevelOrderSuccessor(rootP09, 3) +"") + ", Expected: null");

        System.out.println("==============================================================");
        System.out.println("P13. Left view of binary tree");
        System.out.println("==============================================================");
        // Example 1: Complete binary tree
        TreeNode rootP13 = new TreeNode(1);
        rootP13.left = new TreeNode(2);
        rootP13.right = new TreeNode(3);
        rootP13.left.left = new TreeNode(4);
        rootP13.left.right = new TreeNode(5);
        rootP13.right.left = new TreeNode(6);
        rootP13.right.right = new TreeNode(7);
        System.out.println("Input: " + rootP13.toLevelOrderString() + ", Output: " +
                makeItBold(leftViewBinaryTraverse(rootP13).toString())
                + ", Expected Output: [1, 2, 4]");

        // Example 2: Tree with missing nodes
        rootP13 = new TreeNode(12);
        rootP13.left = new TreeNode(7);
        rootP13.right = new TreeNode(1);
        rootP13.left.right = new TreeNode(9);
        rootP13.right.left = new TreeNode(10);
        rootP13.right.right = new TreeNode(5);
        rootP13.left.right.left = new TreeNode(3);
        System.out.println("Input: " + rootP13.toLevelOrderString() + ", Output: " +
                makeItBold(leftViewBinaryTraverse(rootP13).toString())
                + ", Expected Output: [12, 7, 9, 3]");
        rootP13 = new TreeNode(42);
        System.out.println("Input: " + rootP13.toLevelOrderString() + ", Output: " +
                makeItBold(leftViewBinaryTraverse(rootP13).toString())
                + ", Expected Output: [42]");
        // Example 4: Empty tree
        System.out.println("Input: null, Output: " +
                makeItBold(leftViewBinaryTraverse(null).toString())
                + ", Expected Output: []");





    }
}

