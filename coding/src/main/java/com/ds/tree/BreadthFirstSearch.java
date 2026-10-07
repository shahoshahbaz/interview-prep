package com.ds.tree;

import com.ds.bst.BinarySearchTree;
import com.ds.bst.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {

    public ArrayList<Integer> bfs(BinarySearchTree bst){

        Node currentNode = bst.root;
        Queue<Node> queue = new LinkedList<>();
        ArrayList<Integer> results = new ArrayList<>();

        // Add current node to the queue
        queue.add(currentNode);

        while(!queue.isEmpty()){
            currentNode = queue.remove();
            results.add(currentNode.data);

            if (currentNode.left != null){
                queue.add(currentNode.left);
            }

            if (currentNode.right != null){
                queue.add(currentNode.right);
            }


        }



    return results;
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
        BreadthFirstSearch bfs= new BreadthFirstSearch();
        System.out.println("BFS:" + bfs.bfs(bst));

    }
}
