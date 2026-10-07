package com.ds.stack;

public class StackR1 {
    Node top;
    public static class Node {
        Node next;
        int val;

        public Node(  int value){
            this.val = value;
        }
    }

    public  void push(int value){
        Node newNode = new Node(value);
        if(top == null){
            top = newNode;
        }else{
            newNode.next = top;


            top = newNode;
        }
    }

    public boolean isEmpty(){
        return top == null;
    }
    public Node pop(){
        if(isEmpty()){
            return null;
        }else{
            Node temp = top;
            top = top.next;
            temp.next = null;
            return temp;


        }
    }
    public Node peek(){
        if(isEmpty()) return null;
        return top;

    }
}
