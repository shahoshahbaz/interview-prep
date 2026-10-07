package com.ds.tree;

import com.ds.bst.BinarySearchTree;
import com.ds.bst.Node;

import java.util.ArrayList;

/**
 * For comparison, here are the other common binary tree traversal methods:
 *
 * Inorder Traversal (L, Root, R): Visit the left subtree, then the root, then the right subtree. This results in a sorted order for Binary Search Trees (BST).
 * Postorder Traversal (L, R, Root): Visit the left subtree, then the right subtree, and finally the root. This is useful for certain algorithms like deleting a tree or evaluating a postfix expression.
 * Preorder Traversal (Root, L, R): Visit the root first, then recursively traverse the left and right subtrees.
 */

public class DepthFirstSearch {

    public ArrayList<Integer> preOrder(Node root){

        ArrayList<Integer> results = new ArrayList<>();
        preOrder(root, results);
        return  results;

    }
    public  void preOrder(Node root, ArrayList<Integer> results){
        if (root == null){
            return;
        }

        results.add(root.data);
        preOrder(root.left, results);
        preOrder(root.right, results);

    }

    public ArrayList<Integer> postOrder(Node root){
        ArrayList<Integer> results = new ArrayList<>();
        postOrder(root, results);
        return results;
    }

    public void postOrder(Node root, ArrayList<Integer> results){

        if(root == null) return;

        postOrder(root.left, results);
        postOrder(root.right, results);
        results.add(root.data);
    }

    public ArrayList<Integer> inOrder(Node root){
        ArrayList<Integer> results = new ArrayList<>();
        inOrder(root, results);
        return results;
    }
    public void inOrder(Node root, ArrayList<Integer> results){
        if (root == null) return;
        inOrder(root.left, results);
        results.add(root.data);
        inOrder(root.right, results);

    }


    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(47);
        bst.insert(21);
        bst.insert(76);
        bst.insert(18);
        bst.insert(27);
        bst.insert(52);
        bst.insert(82);

        bst.printTree();

        DepthFirstSearch dfs = new DepthFirstSearch();
        ArrayList<Integer> results = dfs.preOrder(bst.root);
        System.out.println("DFS-PreOrder: " + results);
        results.clear();
        results = dfs.postOrder(bst.root);
        System.out.println("DFS-PostOrder: " + results);
        results.clear();
        results = dfs.inOrder(bst.root);
        System.out.println("DFS-InOrder: " + results);


    }

}
