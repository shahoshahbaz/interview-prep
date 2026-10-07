package com.ds.doubelinkedList;

/**
 * A class representing a doubly linked list.
 * Each node in the list contains a reference to the next node and the previous node.
 */
public class DoublyLinkedListWithHeadAndTail  implements  DoublyLinkedList{
    public Node head; // Head of the list
    public Node tail; // Tail of the list



public void removeDoubleLinkedList(){
    head = null;
    tail = null;
}

    /**
     * Appends a new node with the given data to the end of the list.
     * @param new_data The data to be stored in the new node.
     * Example:
     * DoublyLinkedList list = new DoublyLinkedList();
     * list.append(1);
     * list.append(2);
     * list.append(3);
     * // List now: 1 <-> 2 <-> 3
     */
    public void append(int new_data) {
        Node new_node = new Node(new_data);
        if (head == null) {
            head = new_node;
            tail = new_node;
            return;
        }
        tail.next = new_node;
        new_node.prev = tail;
        tail = new_node;
    }

    /**
     * Prints the elements of the list from head to tail.
     * Example:
     * DoublyLinkedList list = new DoublyLinkedList();
     * list.append(1);
     * list.append(2);
     * list.append(3);
     * list.printList(); // Output: 1 2 3
     */
      public void printDoubllyList(){
        Node temp = head;
        StringBuilder sb = new StringBuilder();
        sb.append("DD: ");
        while (temp != null){
            sb.append(temp.data);
            if (temp.next != null){
                sb.append("<=>");
            }else{
                sb.append("->null");
            }

            temp = temp.next;


        }
        System.out.println(sb.toString() + "\nHead: " + head.data +", tail: "+ tail.data);
    }

    @Override
    public void printDoubllyList(Node head) {

    }


    /**
     * Main method to demonstrate the usage of the DoublyLinkedList class.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        DoublyLinkedListWithHeadAndTail list = new DoublyLinkedListWithHeadAndTail();
        list.append(1);
        list.append(2);
        list.append(3);
        list.append(4);
        list.append(5);

        System.out.println("Original list:");
        list.printDoubllyList(); // Output: 1 2 3 4 5


    }
}

