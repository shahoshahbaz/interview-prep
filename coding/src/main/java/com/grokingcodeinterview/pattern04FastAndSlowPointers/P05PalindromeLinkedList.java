package com.grokingcodeinterview.pattern04FastAndSlowPointers;
/*
Given the head of a Singly LinkedList, write a method to check if the LinkedList is a palindrome or not.

Your algorithm should use constant space and the input LinkedList should be in the original form once the algorithm is finished. The algorithm should have O(N) time complexity where â€˜Nâ€™ is the number of nodes in the LinkedList.

Example 1:

Input: 2 -> 4 -> 6 -> 4 -> 2 -> null
Output: true
Example 2:

Input: 2 -> 4 -> 6 -> 4 -> 2 -> 2 -> null
Output: false
Constraints:

The number of nodes in the list is in the range [].
0 <= Node.val <= 9
 */

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import static com.Utility.makeItBold;

/**
 step 1. find middle
 step 2. handle odd length and sore middle node
 step 3 .cut from middle and reverse
 step 4. reverse second half
 step 5. compare both halves
 step 6. restore the list. for odd length, connect middle node between two halves


 */
public class P05PalindromeLinkedList {

    public static boolean isPalindrome(Node head){

        if (head == null  || head.next == null) return true;

        Node slow = head;
        Node fast = head;
        Node firstHalfEnd = head; // why head is first Half end ? because when we reach middle, slow will be at middle, and first half end will be just before slow

        Node middleNode = null;

        // step1. find middle

        while (fast != null && fast.next != null){
            firstHalfEnd = slow; // keep updating first half end to slow before moving slow ahead
            slow = slow.next;
            fast = fast.next.next;
        }

        // step 2. handle odd length
        if (fast != null){ // odd length - fast is not null, meaning list has odd nodes
            middleNode = slow;
            slow = slow.next; // move slow one step ahead to start of second half
        }
        // For even length, slow is already at the first node of second half, firstHalfEnd is correct

        // step 3. cut the list before reverse
        // firstHalfEnd is pointing to the last node of first half, cut it
        Node secondHalfHead = slow; // slow is already at start of second half
        firstHalfEnd.next = null; // disconnect the two halves

        // step 4. reverse second half
        Node reversedSecondHalfHead = reverseList(secondHalfHead);

        // step 5. compare both half

        Node p1 = head;
        Node p2 = reversedSecondHalfHead;
        boolean isPalindrome = true;

        while (p2 != null){
            if (p1.data !=  p2.data){
                isPalindrome = false;
                break;

            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // step 6. restore the list
        Node restoredSecondHalf = reverseList(reversedSecondHalfHead);

        if (middleNode != null){
            // odd length: firstHalfEnd -> middleNode -> restoredSecondHalf
            firstHalfEnd.next = middleNode;
            middleNode.next = restoredSecondHalf;
        } else {
            // even length: firstHalfEnd -> restoredSecondHalf
            firstHalfEnd.next = restoredSecondHalf;
        }

        return isPalindrome;
    }


    public static Node reverseList(Node head){
        Node curr= head;
        Node prev = null;

        while (curr!= null){
            Node nextNode = curr.next;
            curr.next = prev;
            prev = curr;

            curr = nextNode;
        }
        return prev;
    }


    public static void main(String[] args) {


        System.out.println("===================================");
        System.out.println("P05. Palindrome Linked List...");
        System.out.println("===================================");
        LinkedList listO05 = new LinkedList();
        listO05.createLinkedList(new int[]{2, 4, 6, 4, 2});

        System.out.println(makeItBold(listO05.getHead() +"") + " Is it palindrome?" + makeItBold(isPalindrome(listO05.getHead())+"") + ", List after the checking: " +makeItBold( listO05.getHead()+""));

//        list.printList();

        listO05.createLinkedList(new int[]{2, 4, 6, 4, 2, 2});


        System.out.println(makeItBold(listO05.getHead() +"") + " Is it palindrome?" + makeItBold(isPalindrome(listO05.getHead())+"") + ", List after the checking: " +makeItBold( listO05.getHead()+""));
    }
}
