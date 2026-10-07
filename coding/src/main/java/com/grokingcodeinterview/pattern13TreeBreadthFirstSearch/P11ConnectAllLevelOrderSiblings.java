package com.grokingcodeinterview.pattern13TreeBreadthFirstSearch;

import java.util.ArrayDeque;
import java.util.Deque;

/*
problem statement:
Given a root of the binary tree, connect each node with its level order successor. The last node of each level should point to the first node of the next level.

Examples
Example 1
Input: root = [1, 2, 3, 4, 5, 6, 7] output: 1->2->3->4->5->6->7->null (with next pointers connected as described)
Input: root = [12, 7, 1, 9, 10, 5, 11] output: 12->7->1->9->10->5->11->null (with next pointers connected as described)
Input: root = [1, 2, 3, null, 4, null, 5] output: 1->2->3->4->5->null (with next pointers connected as described)
Input: root = [1, null, 2, null, 3, null, 4] output: 1->2->3->4->null (with next pointers connected as described)
constraints
The number of nodes in the tree is in the range [0, 10^5].
-10^5 <= Node.val <= 10^5
 */

public class P11ConnectAllLevelOrderSiblings {
    public static  class TreeNode{

        int val;
        TreeNode left;
        TreeNode right;
        TreeNode next;

        public TreeNode(int val){
            this.val = val;
            this.next = null;
            this.left = null;
            this.right = null;
        }
        public String toLevelOrderString(){
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            Deque<TreeNode> queue = new ArrayDeque<>();
            queue.offer(this);

            while(!queue.isEmpty()){
                TreeNode curr = queue.poll();
                sb.append(curr.val).append(" ");
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
            sb.append("]");
            return sb.toString().trim();
        }

        public  String getLevelOrderUsingNextString() {
            StringBuilder sb = new StringBuilder();
        TreeNode curr = this;

            while (curr != null) {

                sb.append(curr.val).append("->");
                if(curr.next == null) sb.append("null");
                curr = curr.next;
            }
            return sb.toString();
        }
    }


    public static TreeNode connectAllLevelOrderSiblings(TreeNode root){

        if (root == null) return null;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        TreeNode prev = null;
        while(!queue.isEmpty()){
            int levelSize = queue.size();

            for (int i =0; i< levelSize; i++){
                TreeNode curr= queue.poll();

                if(prev != null) prev.next = curr;
                prev = curr;

                if(curr.left != null) queue.offer(curr.left);
                if(curr.right  != null) queue.offer(curr.right);

            }
        }
        return root;
    }

    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P11. Connect All Level Order Siblings");
        System.out.println("==============================================================");
        // some example
        TreeNode rootP11 = new TreeNode(1);
        rootP11.left = new TreeNode(2);
        rootP11.right = new TreeNode(3);
        rootP11.left.left = new TreeNode(4);
        rootP11.left.right = new TreeNode(5);
        rootP11.right.left = new TreeNode(6);
        rootP11.right.right = new TreeNode(7);
        System.out.println("Input: " + rootP11.toLevelOrderString() +
                " Output: " + connectAllLevelOrderSiblings(rootP11).getLevelOrderUsingNextString()
                +",Expected: 1->2->3->4->5->6->7->null ");
       rootP11 = new TreeNode(12);
       rootP11.left = new TreeNode(7);
       rootP11.right = new TreeNode(1);
       rootP11.left.left = new TreeNode(9);
         rootP11.left.right = new TreeNode(10);
            rootP11.right.left = new TreeNode(5);
            rootP11.right.right = new TreeNode(11);
        System.out.println("Input: " + rootP11.toLevelOrderString() +
                " Output: " + connectAllLevelOrderSiblings(rootP11).getLevelOrderUsingNextString()
                +",Expected: 12->7->1->9->10->5->11->null ");
         rootP11 = new TreeNode(1);
         rootP11.left = new TreeNode(2);
         rootP11.right = new TreeNode(3);
         rootP11.left.right = new TreeNode(4);
         rootP11.right.right = new TreeNode(5);
        System.out.println("Input: " + rootP11.toLevelOrderString() +
                " Output: " + connectAllLevelOrderSiblings(rootP11).getLevelOrderUsingNextString()
                +",Expected: 1->2->3->4->5->null ");
         rootP11 = new TreeNode(1);
         rootP11.right = new TreeNode(2);
         rootP11.right.right = new TreeNode(3);
         rootP11.right.right.right = new TreeNode(4);
        System.out.println("Input: " + rootP11.toLevelOrderString() +
                " Output: " + connectAllLevelOrderSiblings(rootP11).getLevelOrderUsingNextString()
                +",Expected: 1->2->3->4->null ");



    }
}
