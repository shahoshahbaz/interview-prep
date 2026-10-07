package com.ds.bst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinarySearchTree {
     public Node root;


    public void insert (int data){
        root = insertRec(root, data);
    }


    private Node insertRec(Node root, int data){
        if (root == null){
            root = new Node(data);
            return root;
        }
        if (data< root.data){
            root.left = insertRec(root.left,data);
        }else if (data> root.data){
            root.right = insertRec(root.right, data);
        }
        return root;
    }
    public boolean contains(int data){
        return containsRec(root, data);
    }
    private boolean containsRec(Node root, int data){
        if (root == null){
            return false;
        }

        if (root.data == data){
            return true;
        }else if (root.data > data){
            // go left
            return containsRec(root.left, data);

        }else{
            return containsRec(root.right, data);
        }

    }

    public int minValue(Node current){

        while (current.left != null){
            current = current.left;
        }
        return current.data;

    }
    public Node deleteNode(int value){
        return deleteNode(root, value);
    }
    public Node deleteNode(Node currentNode, int value){

        if (root == null ) return null;
        if (value< currentNode.data){
            currentNode.left = deleteNode(currentNode.left, value);
        }else if(value> currentNode.data){
            currentNode.right = deleteNode(currentNode.right, value);
        }else{

            // Node to be deleted is found

            // Case 1: Node has no children (leaf node)
            if (currentNode.left == null && currentNode.right == null){
                return null;
            }

            // Case 2: Node has only one child
            else if (currentNode.left == null ){
                currentNode = currentNode.right;
            }else if (currentNode.right == null){
                currentNode = currentNode.left;
            }else{
                 // Case 3: Node has two children
                // Find the  smallest value in the right subtree
                int subTreeMin = minValue(currentNode.right);
                currentNode.data = subTreeMin;
                currentNode.right = deleteNode(currentNode.right, subTreeMin);

            }
        }
        return  currentNode;
    }

    public ArrayList<Integer> inOrder(){
        ArrayList<Integer> list = new ArrayList<>();
        inOrder(root, list);
        return list;
    }
    private void inOrder(Node root, List<Integer> list ){
        if (root == null) return;
        inOrder(root.left, list);
        list.add(root.data);
        inOrder(root.right, list);
    }
    // Method to print the tree structure
//    public void printTree() {
//        printTreeRec(root, 0);
//    }
    // Print the tree structure
    public void printTree() {
        List<List<String>> lines = new ArrayList<>();
        List<Node> level = new ArrayList<>();
        List<Node> next = new ArrayList<>();
        level.add(root);
        int nn = 1;
        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<>();
            nn = 0;
            for (Node n : level) {
                if (n == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa = Integer.toString(n.data);
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();
                    next.add(n.left);
                    next.add(n.right);
                    if (n.left != null) nn++;
                    if (n.right != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<Node> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = '/';
                        }
                        if (line.get(j) != null) {
                            c = '\\';
                        }
                    }
//                    System.out.print(c);

                    if (line.get(j) != null) {
                        for (int k = 0; k < hpw * 2 - 1; k++) {
                            System.out.print(" ");
                        }
                    }
                }
                System.out.println();
            }

            for (int j = 0; j < line.size(); j++) {
                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }

    // Print the tree structure
    public void printTree(Node root) {
        List<List<String>> lines = new ArrayList<>();
        List<Node> level = new ArrayList<>();
        List<Node> next = new ArrayList<>();
        level.add(root);
        int nn = 1;
        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<>();
            nn = 0;
            for (Node n : level) {
                if (n == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa = Integer.toString(n.data);
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();
                    next.add(n.left);
                    next.add(n.right);
                    if (n.left != null) nn++;
                    if (n.right != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<Node> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = '/';
                        }
                        if (line.get(j) != null) {
                            c = '\\';
                        }
                    }
//                    System.out.print(c);

                    if (line.get(j) != null) {
                        for (int k = 0; k < hpw * 2 - 1; k++) {
                            System.out.print(" ");
                        }
                    }
                }
                System.out.println();
            }

            for (int j = 0; j < line.size(); j++) {
                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }

    // Recursive method to print tree structure
    private void printTreeRec(Node node, int space) {
        // Base case
        if (node == null) {
            return;
        }

        // Increase distance between levels
        int COUNT = 5;
        space += COUNT;

        // Process right child first
        printTreeRec(node.right, space);

        // Print current node after space
        System.out.println();
        for (int i = COUNT; i < space; i++) {
            System.out.print(" ");
        }
        System.out.println(node.data);

        // Process left child
        printTreeRec(node.left, space);
    }
    private void clear(){
        this.root = null;

    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(50);
        bst.insert(30);
        bst.insert(20);
        bst.insert(40);
        bst.insert(70);
        bst.insert(60);
        bst.insert(80);
        bst.insert(2);
        bst.insert(300);
        bst.insert(4000);

        bst.printTree();
        System.out.println("does it contains '2'? "+  bst.contains(2));
        System.out.println("does it contains '2'? "+  bst.contains(4001));
        System.out.println("The min value is : " + bst.minValue(bst.root));
        System.out.println("===============================");
        List<Integer> list = Arrays.asList(15, 10, 20, 8, 12, 17, 25, 22, 27);
        bst.clear();
        list.forEach(bst::insert);
        bst.printTree();
        System.out.println("Remvoing node with a value of 25:");
        bst.deleteNode(25);

        bst.printTree();

        System.out.println("===============================");
        System.out.println("InOrder traverse: ");

        List<Integer> inOrderList = bst.inOrder();
        System.out.println(inOrderList);






    }
}
