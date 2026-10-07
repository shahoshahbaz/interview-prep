package com.ds.stack;



public class Stack {
    Node top;

   public class Node {
        int value;
        Node next;
        public Node (int value){this.value = value;}
    }

    public void push(int data){
        Node newNode = new Node(data);
        if (top == null){
            top = newNode;
        }else {
            newNode.next = top;
            top = newNode;
        }
    }

    public Node pop(){
       if (top == null){
           System.out.println(  "stack is empty");
           return null;
       }
       Node temp = top;
       top = top.next;
       temp.next = null;

       return temp;

   }
   public void printStack(){
       Node current = top;
       while (current !=null){
           System.out.println("| "+ current.value +" |" );
           if (current.next != null){
               System.out.println("----------");
           }
           current = current.next;
       }
   }

    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.printStack();
        System.out.println("=====================");
        System.out.println("poping top elements");
        stack.pop();
        stack.printStack();

    }
}
