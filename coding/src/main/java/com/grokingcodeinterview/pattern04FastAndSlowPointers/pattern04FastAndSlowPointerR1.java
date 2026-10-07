package com.grokingcodeinterview.pattern04FastAndSlowPointers;

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

public class pattern04FastAndSlowPointerR1 {
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

    /**
     * 2sf -> 4s -> 6sf -> 4 -> 2f -> null
     * 2sf -> 4s -> 6sf -> 4 -> 2f -> null
     */

    public static boolean isPalindrome(Node head){
        if (head == null || head.next == null) return true;

        Node slow = head;
        Node fast = head;
        Node headFirstHalf = head;
        Node lastNodeOfFirstHalf =head;

        while(fast!=null && fast.next != null){
            lastNodeOfFirstHalf = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // by end of the above loop slow is at middle and lastNodeOfFirstHalf is at last node of first half
        if (fast != null)  // odd number of nodes, skip the middle node
            slow = slow.next;


        // slow points to middle node
        slow.next = reverse(slow.next);

        Node node1 = head;
        Node node2 = slow.next;

        while (node2 != null){
            if (node1.data != node2.data){
                return false;
            }
            node1 = node1.next;
            node2 = node2.next;
        }

        reverse(slow.next);

        return true;



    }

    public static Node reverse(Node head){
        if (head == null) return null;

        Node curr = head;
        Node prev = null;
        while (curr!= null){
            Node nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        return prev;
    }


    /*
Any number will be called a happy number if,
 after repeatedly replacing it with a number equal to the sum of the square of all of its digits,
 leads us to the number 1.
 All other (not-happy) numbers will never reach 1. Instead, they will be stuck in a cycle of numbers that does not include 1
 .
Given a positive number n, return true if it is a happy number otherwise return false.

Example 1:

Input: 23
Output: true (23 is a happy number)
Explanations: Here are the steps to find out that 23 is a happy number:

 = 4 + 9 = 13
 = 1 + 9 = 10
 = 1 + 0 = 1

Example 2:

Input: 12
Output: false (12 is not a happy number)
Explanations: Here are the steps to find out that 12 is not a happy number:

 = 1 + 4 = 5
= 25
 = 4 + 25 = 29
 = 4 + 81 = 85
 = 64 + 25 = 89
 = 64 + 81 = 145
 = 1 + 16 + 25 = 42
 = 16 + 4 = 20
 = 4 + 0 = 4
 = 16
 = 1 + 36 = 37
 = 9 + 49 = 58
 = 25 + 64 = 89

Please note the cycle from 89 -> 89.

Constraints:

1 <= n <= 231 - 1
 */

    public static boolean isHappyNumber(int num){

        int slow = num;
        int fast = num;

        do{
            slow = sumOfSquare(slow);

            fast = sumOfSquare( sumOfSquare(fast));
            if (slow ==1 || fast ==1) return true;
        }while(slow == fast);

        return false;
    }

    public  static int sumOfSquare(int num){
        int sum = 0;
        while (num> 0){
            sum += (num%10) * (num%10) ;
            num = num/10;
        }
        System.out.println(sum);
        return sum;
    }


    /*
    Given the head of a Singly LinkedList that contains a cycle, write a function to find the starting node of the cycle.
            example:
            1 -> 2 -> 3 -> 4 -> 5
            ^         |
            |_________|
    Output: Node with value 3
    Constraints:
    The number of nodes in the list is in the range [1, 10^4].
            -10^5 <= Node.val <= 10^5
            */

    public static Node findCycleStart(Node head){
        if (head == null  || head.next == null) return null;

        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast){
                break;
            }
        }
        slow = head;
        while (fast != slow){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }


    /*
Given the head of a Singly LinkedList, write a method to return the middle node of the LinkedList.

If the total number of nodes in the LinkedList is even, return the second middle node.

Example 1:

Input: 1 -> 2 -> 3 -> 4 -> 5 -> null
Output: 3
Example 2:

Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null
Output: 4
Example 3:

Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> null
Output: 4
 */

    /**
     * Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null     * Output: 4
     *  Input: 1SF -> 2S -> 3SF -> 4S -> 5 -> 6F -> null
     *
     *  Input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> null     * Output: 4
     *
     *  1SF -> 2S -> 3SF -> 4S -> 5F -> 6 -> 7F -> null
     */

    public static Node findMiddle(Node head){
        if (head == null) return null;

        Node slow = head;
        Node fast = head;

        while(fast != null && fast.next != null){
            slow = slow .next;
            fast = fast.next.next;
        }
        return slow;

    }
    /*
Given the head of a Singly LinkedList, write a function to determine if the LinkedList has a cycle in it or not.

Constraints:

The number of the nodes in the list is in the range [0, 104].
-105 <= Node.val <= 105
*/
    public  static boolean hasCycle(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {

        System.out.println("P01. Has Cycle...");
        // some tests can be added here
        // Create a linked list with a cycle for testing

        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        // Creating a cycle here
        head.next.next.next.next.next = head.next; // Cycle here
        boolean result = hasCycle(head);
        System.out.println("Linked List has cycle: " + result); // Expected output: true
        // another test case without cycle
        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        boolean result2 = hasCycle(head2);
        System.out.println("Linked List has cycle: " + result2); // Expected output: false

        System.out.println("==============================");
        System.out.println("P02. Middle of the The linkedList");
        System.out.println("==============================");
        // some tests can be added here

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        Node resultNode = findMiddle(head);
        System.out.println("Middle Node: " + resultNode.data); // Expected output: 3

        // another test case with even number of nodes
        head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        head2.next.next.next.next.next = new Node(6);
        resultNode = findMiddle(head2);
        System.out.println("Middle Node: " + resultNode.data); // Expected output: 4


        System.out.println("P03 Start of LinkedList.. ");

        // some tests can be added here

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        // Creating a cycle here
        head.next.next.next.next.next = head.next.next; // Cycle starts at node with value
        Node cycleStartNode = findCycleStart(head);
        System.out.println("Cycle starts at node with value: " + (cycleStartNode != null ? cycleStartNode.data : "null")); // Expected output: 3
        // another test cases with cycle
        // [1,2,3,4,5,6]        //2
        //[1,2,3,4,5,6]         //1
        //[1,2,3,4,5,6]        //3
        head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        head2.next.next.next.next.next = new Node(6);
        // Creating a cycle here
        head2.next.next.next.next.next.next = head2.next; // Cycle starts at node with value 2
        Node cycleStartNode2 = findCycleStart(head2);
        System.out.println("Cycle starts at node with value: " + (cycleStartNode2 != null ? cycleStartNode2.data : "null")); // Expected output: 2
        System.out.println("P04. Happy Number");

        // some tests can be added here

        int num1 = 23;
        boolean resultp04 = isHappyNumber(num1);
        System.out.println("Is " + num1 + " a happy number? " + resultp04); // Expected output: true
        int num2 = 12;
        resultp04 = isHappyNumber(num2);
        System.out.println("Is " + num2 + " a happy number? " + result2); // Expected output: false
        // some more test cases
        int num3 = 19;
        resultp04 = isHappyNumber(num3);
        System.out.println("Is " + num3 + " a happy number? " + resultp04); // Expected output: true
        int num4 = 4;
        resultp04 = isHappyNumber(num4);
        System.out.println("Is " + num4 + " a happy number? " + resultp04); // Expected output: false    }
        System.out.println("===================================");
        System.out.println("P05. Palindrome Linked List...");
        System.out.println("===================================");
        LinkedList listO05 = new LinkedList();
        listO05.createLinkedList(new int[]{2, 4, 6, 4, 2});
        System.out.println(listO05.getHead() + " Is it palindrome?" + isPalindrome(listO05.getHead()) + ", List after the checking: " + listO05.getHead());

//        list.printList();

        listO05.createLinkedList(new int[]{2, 4, 6, 4, 2, 2});


        System.out.println(listO05.getHead() + " Is it palindrome?" + isPalindrome(listO05.getHead())+ ", List after the checking: " + listO05.getHead());
    }
}

