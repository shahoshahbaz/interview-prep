package com.ds.bst;

import java.util.ArrayList;
import java.util.Stack;

public class BinarySearchTreeIterative {
            public Node root;
     public void insert (int data){

         Node newNode = new Node(data);
         if (root == null) {
             root = newNode;
             return;
         }
         Node current = root;
         Node parent = null;
         while (current != null){

             parent = current;
             if (current.data> data){
                 // go left
                 current = current.left;
             }else{
                 current = current.right;
             }
         }
         if (parent.data> data){
             parent.left = newNode;

         }else{
             parent.right = newNode;
         }


     }

     public ArrayList<Integer>InOrder(){
         ArrayList<Integer> list= new ArrayList<>();
         if (root == null) return list;

         Node current = root;
         Stack<Node> stack = new Stack<>();

         while (current != null || !stack.isEmpty()){

              // Traverse to the leftmost node
             while (current != null){
                 stack.push(current);
                 current = current.left;
             }
             // Current must be null at this point
             current = stack.pop();
             list.add(current.data );

             // Visit the right subtree
             current = current .right;
         }
         return list;
     }
    public static void main(String[] args) {
        BinarySearchTreeIterative bst = new BinarySearchTreeIterative();


        // Insert nodes iteratively
         bst.insert( 50);
         bst.insert( 30);
         bst.insert( 20);
         bst.insert( 40);
        bst.insert( 70);
        bst.insert( 60);
        bst.insert( 80);
        System.out.println("In-order traversal after insertion:");

        System.out.println(bst.InOrder());


    }

}
