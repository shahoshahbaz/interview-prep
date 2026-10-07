package com.grokingcodeinterview.pattern08InPlaceReversalLinkedList;
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


import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import static com.Utility.makeItBold;

/**
 *
 * Example: 1->2->3(p)->4->5->6->7->8(q)->9->10->null, p = 3, q = 8 Output: 1->2->8->7->6->5->4->3->9->10->null
 * Example: 1->2->3->4->5->6->7->8->9->10->null, p = 3, q = 8
 * perv: p
 * curr: c
 * next: n
 * lastNodeOfFirstPart: lnfp
 * lastNodeOfSubList: lnsl
 *
 * step 1: skip the first p-1 nodes => 1->2(p)->3(c)->4->5->6->7->8->9->10->null
 * step 2: store the last node of first part and last node of sublist
 *
 * step 3: reverse from p to q  after reversing    1 -> 2(lnfp)      8(pvr) -> 7 -> 6 -> 5 -> 4 -> 3(lnsl) -> null      9(c) -> 10-> null  is this correct? curr =9  prev =8 lastNodeOfSubList = 3 lastNodeOfFirstPart =2
 * step 4: connect the first part with the reversed sublist lnfp.next = prev 1 -> 2(lnfp) -->   8(pvr) -> 7 -> 6 -> 5 -> 4 -> 3(lnsl) -> null      9(c) -> 10 -> null
 * step 5: connect the last node of the sublist to the remaining nodes lnsl.next = curr 1 -> 2(lnfp) -->  8(pvr) -> 7 -> 6 -> 5 -> 4 -> 3(lnsl) --> 9(c) -> 10 -> null
 *  return head;
 *
 * i=0: next =, curr.next = null, prev = 3,
 * nextQ = 9
 *
 *
 */
public class P02ReverseASubList {

    /**
     *  c:current
     *  p: prev
     *  bs:beforeReversalStart
     *  s: reversalStart
     *  beforeReversal -> [ 2 -> 3 -> 4 ] -> afterReversal
     * */
    public static Node reverseSubList(Node head, int p, int q){

        Node curr = head;
        Node prev = null;

        for (int i =1; curr != null && i<p;i++){ // 1(p)->2(c)->3-> 4-> 5 -> null
            prev = curr;
            curr = curr.next;
        }

        // now prev points to a node before p
        // store before start and 
        // and curr point to start of reversal
        Node beforeReversalStart = prev;
        Node lastNodeOfSubList = curr; // this will become the last node of the sublist after reversal
        //1(bs)(p)->2(s)(c)->3-> 4-> 5 -> null
        // reverse nodes for  q-p+1

        for (int i = 0; curr!= null&&  i< (q-p+1); i++){
            Node nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        // curr points to rest of list
        //1(bs)->   4(s)<-3<- 2(p)-> 5(c) -> null
//                   |__________________|

        // now connect before start with prev
        if (beforeReversalStart != null){ // there is a chance that p =1 then beforeStart is null
            beforeReversalStart.next = prev;
        }else{
            head = prev;
        }

        lastNodeOfSubList.next = curr; // 1->4-> 3 -> 2-> 5 -> null;


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



    }
}

