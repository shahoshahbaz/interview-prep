package com.grokingcodeinterview.pattern08InPlaceReversalLinkedList;

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import static com.Utility.makeItBold;

public class pattern07InPlaceReversalLinkedListR3 {
/*
    problem statement:
    Given the head of a LinkedList and a number â€˜kâ€™, reverse every alternating â€˜kâ€™ sized sub-list starting from the head.
    If, in the end, you are left with a sub-list with less than â€˜kâ€™ elements, reverse it too.
    example: Input: 1-> 2-> 3-> 4-> 5-> 6-> 7-> 8-> nll and k = 2 2-> 1-> 3-> 4-> 6-> 5-> 7-> 8-> nll k
    Example :  Input: 1 â†’ 2 â†’ 3 â†’ 4 â†’ 5 â†’ 6 â†’ 7 â†’ 8 â†’ null k = 2 Output: 2 â†’ 1 â†’ 3 â†’ 4 â†’ 6 â†’ 5 â†’ 7 â†’ 8 â†’ null
    Example: Input: 1 â†’ 2 â†’ 3 â†’ 4 â†’ 5 â†’ 6 â†’ 7 â†’ 8 â†’ null k = 3 Output: 3 â†’ 2 â†’ 1 â†’ 4 â†’ 5 â†’ 6 â†’ 9 â†’ 8 â†’ 7 â†’ null
    Example: Input: 1 â†’ 2 â†’ 3 â†’ 4 â†’ null k = 1 Output: 1 â†’ 2 â†’ 3 â†’ 4 â†’ null
 */

    /**
     * Input: 1-> 2-> 3-> 4-> 5-> 6-> 7-> 8-> nll and k = 2 2-> 1-> 3-> 4-> 6-> 5-> 7-> 8-> nll k
     * prev: p
     * curr:c
     * tailOfPrevList:tp
     * tailOfcurrList: tc
     * 1tc<- 2p-> 3c-> 4-> 5-> 6-> 7-> 8-> nll, tp: null, tc:1 => tp: head head =   tc.next = 3
     * H: 2-> 1-> 3p-> 4c-> 5-> 6-> 7-> 8-> nll
     *
     */

    public static Node reverseAlternatingKElement(Node head, int k){

        if (head == null || head.next == null || k<=1) return head;
        Node curr = head;
        Node prev = null;
        Node tailOfCurrList;
        Node tailOfPrevList;

        while(curr!= null ){
            tailOfPrevList = prev;
            tailOfCurrList = curr;

            // reverse k Nodes
            for (int i =0;  curr!= null &&i< k; i++){
                Node nextNode = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextNode;
            }
             // after reversing prev point to head of revesed list and 'curr' points to start of next list

            // do the plumbing ;)
            // 1. take care of previous list
            if(tailOfPrevList != null){
                tailOfPrevList.next = prev;
            }else{
                 head = prev;
            }

            //2. take care of tail of current list
            tailOfCurrList.next = curr;

            // skip k node;
            for (int i =0; curr != null && i<k; i++){
                prev = curr;
                curr = curr.next;
            }



        }

        return head;


    }

        /*
  Problem Statement
  Given the head of a Singly LinkedList and a number â€˜kâ€™, rotate the LinkedList to the right by â€˜kâ€™ nodes.
  Example: 1-> 2-> 3-> 4-> 5-> 6-> null and k = 3 output: 4-> 5-> 6-> 1-> 2-> 3-> null
  Example: 1-> 2-> 3-> 4-> 5-> 6-> null and k = 4 output: 3-> 4-> 5-> 6-> 1-> 2-> null
  Example: 1-> 2-> 3-> 4-> 5-> 6-> null and k = 0 output: 1-> 2-> 3-> 4-> 5-> 6-> null
  Example: 1-> 2-> 3-> 4-> 5-> 6->7->8-> null and k = 15 output: 4-> 5-> 6->7->8->1-> 2-> 3-> null
  Example: null and k = 3 output: null
  Example: 1->2-> null and k = 3 output: 2->1-> null
    Example: 1-> null and k = 99 output: 1-> null

  Constraints:

The number of nodes in the list is in the range [0, 500].
-100 <= Node.val <= 100
0 <= k <= 2 * 10^9
 */



    public static Node rotate(Node head, int k){

        // find the length of list and get the last node
        Node curr = head;
        Node prev = head;
        int length =0;

        while(curr != null){
            prev = curr;
            curr = curr.next;
            length++;
        }

        // Normalize k
        k = k%length;
        if (k ==0) return head;

        // connect head and tail

        prev.next = head;

        // now move the curr to the head and
        curr = head;

        for (int i =0; i< length - k-1; i++){
            curr = curr.next;
        }
        head = curr.next;
        curr.next = null;

        return head;


    }
 /*
Given the head of a LinkedList and a number â€˜kâ€™, reverse every â€˜kâ€™ sized sub-list starting from the head.

If, in the end, you are left with a sub-list with less than â€˜kâ€™ elements, reverse it too.
example 1: Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> null, and k = 3 Output: 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 8 -> 7 -> null

example 2:Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> null, and k = 2
Output: 2 -> 1 -> 4 -> 3 -> 6 -> 5 -> 8 -> 7 -> null

example 3:
Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> null, and k = 1
Output: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> null

 */

    /**
     * 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> null, and k = 2
     * c: 1 P: null, tpl: null, tcl: 1   => H 2->1->3->4->.....-> null
     * c: 3, P:1, tpl:1, tcl = 3 => H 2->1->4->3->.....-> null
     * c: 5, P=4 1.next = 4, p
     *
     */


    public static Node reverseEveryKSubList(Node head, int k){

        if( head == null || k <=1) return head;
        Node curr = head;
        Node prev = null;

        while(curr != null){

            Node tailOfPrevList = prev;
            Node tailOfCurrList = curr;

            Node[] reversed = reverseKNodes(curr, k);
            prev = reversed[0];
            curr = reversed[1];

            if(tailOfPrevList != null){
                tailOfPrevList.next = prev;
            }else{
                head = prev;
            }
            tailOfCurrList.next = curr;

            prev =tailOfCurrList;


        }

        return head;

    }

    public static Node[] reverseKNodes(Node head, int k){

        if (head == null || k<=1) return new Node[]{null, head};
        Node curr = head;
        Node prev = null;
        int counter = 0;
        while (curr != null && counter <k){
            Node nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
            counter ++;
        }

        return new Node[]{prev, curr};
    }
/*
    Problem Statement
    Given the head of a LinkedList and two positions â€˜pâ€™ and â€˜qâ€™, reverse the LinkedList from position â€˜pâ€™ to â€˜qâ€™.
    Example: 1->2->3-> 4-> 5 -> null p = 2, q= 4 output : 1-> 4-> 3->2-> 5 -> null
    Example: 10->20->30->40->50->null, p = 1, q = 3   Output: 30->20->10->40->50->null
    Example: 5->6->7->8->9->null, p = 3, q = 5 Output: 5->6->9->8->7->null
    Example: 1->2->3->4->5->6->null, p = 2, q = 5 Output: 1->5->4->3->2->6->null
    Example: 100->200->300->400->null, p = 2, q = 3 Output: 100->300->200->400->null
    Example: 1->2->3->4->5->6->7->8->9->10->null, p = 3, q = 8 Output: 1->2->8->7->6->5->4->3->9->10->null
    Constraints:
    The number of nodes in the list is n.
    1 <= n <= 500
    -500 <= Node.val <= 500
    1 <= p <= q <= n
*/

    /**
     *  1->2->3-> 4-> 5 -> null p = 2, q= 4 output : 1-> 4-> 3->2-> 5 -> null
     *  c:1
     *  p:null
     */

    public static Node reverseSubList(Node head, int p, int q){
        Node curr = head;
        Node prev = null;

        for (int i=1; curr != null && i<p; i++){
            prev = curr;
            curr = curr.next;
        }
        Node tailOfPrevSubList = prev;
        Node tailOfCurrSubList = curr;
        // reverse q-p+1 nodes
        for(int i =1; curr != null && i<= q-p+1; i++){
            Node nextNode = curr.next;
            curr.next= prev;
            prev= curr;
            curr = nextNode;
        }

        // connect the rest of node
        if(tailOfPrevSubList != null){
            tailOfPrevSubList.next = prev;
        }else{
            head = prev;
        }

        tailOfCurrSubList.next = curr;

        return head;
    }

    public static void main(String[] args) {
        System.out.println("======================================================");
        System.out.println("P02. Reverse a Sublist of a Linked List");
        System.out.println("======================================================");

        LinkedList listP02 = new LinkedList();
        listP02.createLinkedList(new int[]{1,2,3,4,5,6,7,8,9,10});
        System.out.print("Input: " + listP02.toString());
        Node reversedSubListHead = reverseSubList(listP02.getHead(), 3, 8);
        System.out.println(" ,output:(3 to 8):" + makeItBold(reversedSubListHead.toString()) + ",Expected:1->2->8->7->6->5->4->3->9->10->null");
        listP02.clear();
        listP02.createLinkedList(new int[]{1,2,3,4,5});
        System.out.print("Input: " + listP02.toString());
        reversedSubListHead = reverseSubList(listP02.getHead(), 2, 4);
        System.out.println(" ,output:(2 to 4):" + makeItBold(reversedSubListHead.toString()) + ",Expected:1->4->3->2->5->null");

        listP02.clear();
        listP02.createLinkedList(new int[]{10,20,30,40,50});
        System.out.print("Input: " + listP02.toString());
        reversedSubListHead = reverseSubList(listP02.getHead(), 1, 3);
        System.out.println(" ,output:(1 to 3):" + makeItBold(reversedSubListHead.toString()) + ",Expected:30->20->10->40->50->null");

        listP02.clear();
        listP02.createLinkedList(new int[]{5,6,7,8,9});
        System.out.print("Input: " + listP02.toString());
        reversedSubListHead = reverseSubList(listP02.getHead(), 3, 5);
        System.out.println(" ,output:(3 to 5):" + makeItBold(reversedSubListHead.toString()) + ",Expected:5->6->9->8->7->null");

        listP02.clear();
        listP02.createLinkedList(new int[]{1,2,3,4,5,6});
        System.out.print("Input: " + listP02.toString());
        reversedSubListHead = reverseSubList(listP02.getHead(), 2, 5);
        System.out.println(" ,output:(2 to 5):" + makeItBold(reversedSubListHead.toString()) + ",Expected:1->5->4->3->2->6->null");

        listP02.clear();
        listP02.createLinkedList(new int[]{100,200,300,400});
        System.out.print("Input: " + listP02.toString());
        reversedSubListHead = reverseSubList(listP02.getHead(), 2, 3);
        System.out.println(" ,output:(2 to 3):" + makeItBold(reversedSubListHead.toString()) + ",Expected:100->300->200->400->null");

        listP02.clear();
        listP02.createLinkedList(new int[]{1,2,3,4,5,6,7,8,9,10});

        System.out.print("Input: " + listP02.toString());
        reversedSubListHead = reverseSubList(listP02.getHead(), 3, 8);

        System.out.println(" ,output:(3 to 8):" + makeItBold(reversedSubListHead.toString()) + ",Expected:1->2->8->7->6->5->4->3->9->10->null");

        System.out.println("===================================");
        System.out.println("P03. Reverse Every K Element SubList... ");
        System.out.println("===================================");

        LinkedList listP03 = new LinkedList();
        listP03.createLinkedList(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        int kP03 = 3;
        System.out.println("Input: " + listP03.getHead().toString() + ", k: " + kP03 + ", Output: " +
                makeItBold(reverseEveryKSubList(listP03.getHead(), kP03).toString()) + ", expected: 3->2->1->6->5->4->8->7->null");

        // more test cases

        listP03.clear();
        listP03.createLinkedList(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        kP03 = 2;
        System.out.println("Input: " + listP03.getHead().toString() + ", k: " + kP03 + ", Output: " +
                makeItBold(reverseEveryKSubList(listP03.getHead(), kP03).toString()) + ", expected: 2->1->4->3->6->5->8->7->null");
        listP03.clear();
        listP03.createLinkedList(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        kP03 = 1;
        System.out.println("Input: " + listP03.getHead().toString() + ", k: " + kP03 + ", Output: " +
                makeItBold(reverseEveryKSubList(listP03.getHead(), kP03).toString()) + ", expected: 1->2->3->4->5->6->7->8->null");

        // give an edge case with less than k nodes
        listP03.clear();
        listP03.createLinkedList(new int[]{1, 2});
        kP03 = 3;
        System.out.println("Input: " + listP03.getHead().toString() + ", k: " + kP03 + ", Output: " +
                makeItBold(reverseEveryKSubList(listP03.getHead(), kP03).toString()) + ", expected: 2->1->null");

        listP03.clear();
        listP03.createLinkedList(new int[]{});
        kP03 = 3;
        System.out.println("Input: " + makeItBold("null") + ", k: " + kP03 + ", Output: " +
                makeItBold((reverseEveryKSubList(listP03.getHead(), kP03)) == null?"nul":(reverseEveryKSubList(listP03.getHead(), kP03).toString()).toString()) + ", expected: null");

        listP03.clear();
        listP03.createLinkedList(new int[]{1});
        kP03 = 2;
        System.out.println("Input: " + listP03.getHead().toString() + ", k: " + kP03 + ", Output: " +
                makeItBold(reverseEveryKSubList(listP03.getHead(), kP03).toString()) + ", expected: 1->null");

        System.out.println("===================================");
        System.out.println("P05. Rotate A Linked List... ");
        System.out.println("===================================");

        LinkedList listP05 = new LinkedList();
        listP05.createLinkedList(new int[]{1,2,3,4,5,6,7,8});
        System.out.print("Input: " + listP05.getHead().toString() + ", k: 2, Output: ");
        listP05.setHead( rotate(listP05.getHead(), 2));
        System.out.println( makeItBold(listP05.getHead().toString()) + ", expected: 7->8->1->2->3->4->5->6->null");
        listP05.clear();
        listP05.createLinkedList(new int[]{1,2,3,4,5,6,7,8});
        System.out.print("Input: " + listP05.getHead().toString() + ", k: 3, Output: ");
        listP05.setHead( rotate(listP05.getHead(), 3));
        System.out.println( makeItBold(listP05.getHead().toString()) + ", expected: 6->7->8->1->2->3->4->5->null");
        listP05.clear();
        listP05.createLinkedList(new int[]{1,2,3,4,5,6,7,8});
        System.out.print("Input: " + listP05.getHead().toString() + ", k: 4, Output: ");
        listP05.setHead( rotate(listP05.getHead(), 4));
        System.out.println( makeItBold(listP05.getHead().toString()) + ", expected: 5->6->7->8->1->2->3->4->null");
        listP05.clear();
        listP05.createLinkedList(new int[]{1,2,3,4,5,6,7,8});
        System.out.print("Input: " + listP05.getHead().toString() + ", k: 15, Output: ");
        listP05.setHead( rotate(listP05.getHead(), 15));
        System.out.println( makeItBold(listP05.getHead().toString()) + ", expected: 2->3->4->5->6->7->8->1->null");
        listP05.clear();
        listP05.createLinkedList(new int[]{1,2});
        System.out.print("Input: " + listP05.getHead().toString() + ", k: 3, Output: ");
        listP05.setHead( rotate(listP05.getHead(), 3));
        System.out.println( makeItBold(listP05.getHead().toString()) + ", expected: 2->1->null");
        listP05.clear();
        listP05.createLinkedList(new int[]{1});
        System.out.print("Input: " + listP05.getHead().toString() + ", k: 99, Output: ");
        listP05.setHead( rotate(listP05.getHead(), 99));
        System.out.println( makeItBold(listP05.getHead().toString()) + ", expected: 1->null");

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

