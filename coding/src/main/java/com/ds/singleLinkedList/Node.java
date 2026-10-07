package com.ds.singleLinkedList;

public class Node {

    public int data;
    public Node next;

    public Node(int data){ this.data = data;}

    @Override
    public String toString() {

        if (next == null){
            return data + "-> null";
        }
        StringBuilder sb = new StringBuilder();
        Node current = next;
        sb.append(data).append("->");
        while (current!= null){
            sb.append(current.data).append("->");
            current = current.next;
        }
        sb.append("null");
        return sb.toString();
    }
}
