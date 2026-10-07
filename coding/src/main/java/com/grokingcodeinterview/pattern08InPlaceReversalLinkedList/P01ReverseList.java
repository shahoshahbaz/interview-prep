package com.grokingcodeinterview.pattern08InPlaceReversalLinkedList;

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

public class P01ReverseList {
    /**
     *
     * @example: input: 1c->2n-> 3 -> null => output :3->2->1-> null
     */
    public static  Node reverse(Node head){
        if (head == null) return null;
        Node curr = head;
        Node prev = null;
        while (curr!= null){
            Node nextNode = curr.next;
            curr.next = prev;
            prev= curr;
            curr = nextNode;
        }
        return prev;
    }

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println(" P01 Reverse Linked List ");
        System.out.println("=================================================================");


        LinkedList listP01 = new LinkedList();
        listP01.createLinkedList(new int[]{1,2,3,4,5});
        System.out.print("Original List:" + listP01 +" , ");
        Node reversedHead = reverse(listP01.getHead());
        System.out.println(" , Reversed List:" + reversedHead.toString());

        // give 3 more test cases
        listP01.createLinkedList(new int[]{10,20});
        System.out.print("Original List:" + listP01 +" , ");
        reversedHead = reverse(listP01.getHead());
        System.out.println(" , Reversed List:" + reversedHead.toString());

        listP01.createLinkedList(new int[]{7});
        System.out.print("Original List:" + listP01 +" , ");
        reversedHead = reverse(listP01.getHead());
        System.out.println(" , Reversed List:" + reversedHead.toString());

        listP01.createLinkedList(new int[]{});
        System.out.print("Original List:" + listP01 +" , ");
        reversedHead = reverse(listP01.getHead());
        System.out.println(" , Reversed List:" + reversedHead);

    }

}

