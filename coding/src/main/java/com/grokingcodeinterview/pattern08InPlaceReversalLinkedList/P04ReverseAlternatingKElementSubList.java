package com.grokingcodeinterview.pattern08InPlaceReversalLinkedList;

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import static com.Utility.makeItBold;

/*
    problem statement:
    Given the head of a LinkedList and a number â€˜kâ€™, reverse every alternating â€˜kâ€™ sized sub-list starting from the head.

    If, in the end, you are left with a sub-list with less than â€˜kâ€™ elements, reverse it too.

    example: Input: 1-> 2-> 3-> 4-> 5-> 6-> 7-> 8-> nll and k = 2 2-> 1-> 3-> 4-> 6-> 5-> 7-> 8-> nll k
    Example :  Input: 1 â†’ 2 â†’ 3 â†’ 4 â†’ 5 â†’ 6 â†’ 7 â†’ 8 â†’ null k = 2 Output: 2 â†’ 1 â†’ 3 â†’ 4 â†’ 6 â†’ 5 â†’ 7 â†’ 8 â†’ null
    Example: Input: 1 â†’ 2 â†’ 3 â†’ 4 â†’ 5 â†’ 6 â†’ 7 â†’ 8 â†’ null k = 3 Output: 3 â†’ 2 â†’ 1 â†’ 4 â†’ 5 â†’ 6 â†’ 9 â†’ 8 â†’ 7 â†’ null
    Example: Input: 1 â†’ 2 â†’ 3 â†’ 4 â†’ null k = 1 Output: 1 â†’ 2 â†’ 3 â†’ 4 â†’ null
 */
public class P04ReverseAlternatingKElementSubList {

    public  static Node reverseAlternatingKElement(Node head, int k){
        if (k<=1 || head == null) return head;
        Node current = head;
        Node previous = null;



        while(current!= null){
            Node lastNodeOfPreviousPart = previous;

            // after reversing sublist 'current' will become the last node of the sub-list
            Node lastNodeOfSubListBeforeReversal = current;



            // reverse 'k' nodes
           Node[] revesedList =  reverseSubList(current, k);
           previous =  revesedList[0];
           current = revesedList[1];

           // connect with the previous part
            if(lastNodeOfPreviousPart == null){

                head = previous; // set the header
            }else{
                // previous is now the first node of the sublist
                lastNodeOfPreviousPart.next = previous;
            }

            // connect with the next part
            lastNodeOfSubListBeforeReversal.next = current;
            // skip 'k' nodes
            for (int i =0; current != null && i<k; i++){
                previous = current;
                current = current.next;
            }
        }
        return head;
    }

    public  static Node[] reverseSubList(Node head, int k){
        Node curr = head;
        Node prev= null;
        Node next = null;
        for(int i =0; curr != null && i<k; i++ ){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;

        }
        return new Node[]{prev,curr};
    }

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("P04. Reverse Alternating K Element SubList... ");
        System.out.println("===================================");
        LinkedList listP04 = new LinkedList();
        listP04.createLinkedList(new int[]{1,2,3,4,5,6,7,8});
        System.out.print("Input: "+ listP04.toString() + ", k=2");
        listP04.setHead( reverseAlternatingKElement(listP04.getHead(), 2));
        System.out.println(", Output: " + makeItBold(listP04.getHead().toString()) + ", Expected: 2->1->3->4->6->5->7->8->null");

        listP04.clear();
        listP04.createLinkedList(new int[]{1,2,3,4,5,6,7,8});
        System.out.print("Input: "+ listP04.toString() + ", k=3");
        listP04.setHead( reverseAlternatingKElement(listP04.getHead(), 3));
        System.out.println(", Output: " + makeItBold(listP04.getHead().toString()) + ", Expected: 3->2->1->4->5->6->8->7->null");

        listP04.clear();
        listP04.createLinkedList(new int[]{1,2,3,4});
        System.out.print("Input: "+ listP04.toString() + ", k=1");
        listP04.setHead( reverseAlternatingKElement(listP04.getHead(), 1));
        System.out.println(", Output: " + makeItBold(listP04.getHead().toString()) + ", Expected: 1->2->3->4->null");
        listP04.clear();
        listP04.createLinkedList(new int[]{1,2,3,4,5});
        System.out.print("Input: "+ listP04.toString() + ", k=2");
        listP04.setHead( reverseAlternatingKElement(listP04.getHead(), 2));
        System.out.println(", Output: " + makeItBold(listP04.getHead().toString()) + ", Expected: 2->1->3->4->5->null");
        listP04.clear();
        listP04.createLinkedList(new int[]{1,2,3,4,5,6,7});
        System.out.print("Input: "+ listP04.toString() + ", k=3");
        listP04.setHead( reverseAlternatingKElement(listP04.getHead(), 3));
        System.out.println(", Output: " + makeItBold(listP04.getHead().toString()) + ", Expected: 3->2->1->4->5->6->7->null");




    }




}

