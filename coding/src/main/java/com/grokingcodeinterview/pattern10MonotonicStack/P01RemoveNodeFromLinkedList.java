package com.grokingcodeinterview.pattern10MonotonicStack;


import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.Utility.makeItBold;


/*
 Problem Statement
 Given the head node of a singly linked list,
 modify the list such that any node that has a node
 with a greater value to its right gets removed. The function should return the head of the modified list.
 Examples:
 Input: 5 -> 3 -> 7 -> 4 -> 2 -> 1  Output: 7 -> 4 -> 2 -> 1
 Explanation: 5 and 3 are removed as they have nodes with larger values to their right.
 Input: 1 -> 2 -> 3 -> 4 -> 5  Output: 5
 Explanation: 1, 2, 3, and 4 are removed as they have nodes with larger values to their right.
 */

/**
 * this Next Greater style because we are looking for next greater on the right side so decreaing stack
 * 'Greater' and 'decreasing' both have 'g' so easier to remember
 *
 */
public class P01RemoveNodeFromLinkedList {
    private static final boolean DEBUG = false;
    private static void log(String msg){
        if(DEBUG)
            System.out.println(msg);
    }
    public static Node removeNode(Node head){
        if (head == null) return head;
        Deque<Node> stack = new ArrayDeque<>();
        Node current = head;

        while(current !=null){
            while(!stack.isEmpty() && stack.peek().data<current.data) {
                stack.pop();
            }

            stack.push(current);
            current = current.next;
        }

        log(stack.toString());
        // reconstruct the linked list from stack, the stack is in reverse order
        Node newHead = null;

        while(!stack.isEmpty()){
            Node node = stack.pop(); // get node from stack
            node.next= newHead; // point to newHead
            newHead = node;// update newHead
        }
        return newHead;

//
    }
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("P01.RemoveNodeFromLinkedList");
        System.out.println("==============================================");
        LinkedList listP01 = new LinkedList();
        listP01.createLinkedList(new int[]{5, 3, 7, 4, 2, 1});
        System.out.println("Input: " + listP01.getHead() + " ,output: " + makeItBold(removeNode( listP01.getHead())+" ,Expected: 7->4->2->1"));
        listP01.clear();
        listP01.createLinkedList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Input: " + listP01.getHead() + " ,output: " + makeItBold(removeNode( listP01.getHead()) +" ,Expected: 5"));
        listP01.clear();
        listP01.createLinkedList(new int[]{5, 4, 3, 2, 1});
        System.out.println("Input: " + listP01.getHead() + " ,output: " + makeItBold(removeNode( listP01.getHead()) +" ,Expected: 5->4->3->2->1"));
        // give me more complex
        listP01.clear();
        listP01.createLinkedList(new int[]{2, 7, 3, 5, 1, 6, 4});
        System.out.println("Input: " + listP01.getHead() + " ,output: " + makeItBold(removeNode( listP01.getHead()) +" ,Expected: 7->6->4"));



    }














}

