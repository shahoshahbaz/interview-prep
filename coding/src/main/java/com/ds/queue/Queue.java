package com.ds.queue;

public class Queue {

    public Node first, last;

    public class Node {
        int value;
        Node next;

        public Node(int value ){this.value = value;}
    }

    public void enqueue(int data){
        Node newNode = new Node(data);

        // if the queue is empty
        if (last == null ){
            first = last = newNode;
            return;
        }
        last.next = newNode;
        last= newNode;
        // example
        // first      last
        // 1 - 2-> 3 ->4 -> null
    }

    public Node  dequeue(){
        if (last == null){
            System.out.println("Queue is empty.");
            return null;
        }
        Node temp = first;
        first = first.next;
        temp.next = null;
        return temp;

    }
    public void printQueue(){
        Node current = first;
        System.out.print("First: ");
        while (current != null){
            System.out.print( current.value );
            if (current.next != null){
                System.out.print(  "->");
            }else{
                System.out.println(": Last");
            }
            current = current.next;
        }

    }

    public static void main(String[] args) {
        Queue queue = new Queue();
        queue.enqueue(10);
        queue.enqueue(15);
        queue.enqueue(16);
        queue.enqueue(20);
        queue.enqueue(40);
        queue.printQueue();
        System.out.println("enqueue from queue...");
        queue.dequeue();
        queue.printQueue();


    }
}

