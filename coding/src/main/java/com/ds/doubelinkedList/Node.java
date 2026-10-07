package com.ds.doubelinkedList;

public class Node {
    public Node next;
    public Node prev;
    public int data;

    public Node (int data){
        this.data = data;
        this.prev = null;
        this.next = null;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(  ( (prev!= null)? prev.data:"null")+ "<-(prev)"+ " data=" + data + ",(next)->" + (next!= null? next.data: "null"));




//        sb.append("Node{" + " data=");
//        sb.append(data);
//        sb.append(",next=" + (next!= null? next.data: "null"));
//        sb.append(", prev=" +( (prev!= null)? prev.data:"null"));
        return sb.toString();

    }
}
