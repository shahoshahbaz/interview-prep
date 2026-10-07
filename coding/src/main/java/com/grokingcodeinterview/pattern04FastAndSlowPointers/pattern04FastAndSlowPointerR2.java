package com.grokingcodeinterview.pattern04FastAndSlowPointers;

import com.ds.singleLinkedList.LinkedList;
import com.ds.singleLinkedList.Node;

import static com.Utility.makeItBold;

public class pattern04FastAndSlowPointerR2 {
/*
Given the head of a Singly LinkedList,
 write a method to modify the LinkedList such that
 the nodes from the second half of the LinkedList are inserted alternately
  to the nodes from the first half in reverse order.
  So if the LinkedList has nodes 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null,
   your method should return 1 -> 6 -> 2 -> 5 -> 3 -> 4 -> null.
Your algorithm should use only constant space the input LinkedList should be modified in-place.

Example 1:

Input: 2 -> 4 -> 6 -> 8 -> 10 -> 12 -> null
Output: 2 -> 12 -> 4 -> 10 -> 6 -> 8 -> null
Example 2:

Input: 2 -> 4 -> 6 -> 8 -> 10 -> null
Output: 2 -> 10 -> 4 -> 8 -> 6 -> null
Constraints:

The number of nodes in the list is in the range [].
1 <= Node.val <= 1000
 */

    /** output: 2 -> 12 -> 4 -> 10 -> 6 -> 8 -> null
     * Input: 2sf -> 4s -> 6sf -> 8s -> 10f -> 12 -> f null even list
     * 2 -> 4 -> 6 -> 8s -> 10 -> 12 ->  null 12-10
     *
     * Input: 2sf -> 4s -> 6sf -> 8s -> 10f -> f null odd list
     */

    public static Node rearrangeList(Node head){
        // step1. find middle node

        Node slow = head;
        Node fast = head;
        Node firstHalf = head;

        while(fast != null && fast.next != null){
            slow= slow.next;
            fast = fast.next.next;
        }

        if (fast!= null){
            slow = slow .next;
        }
        Node secondHalf = slow.next;
        // step: cut the second half

        slow .next = null;

        // step 3: revers second hafl

        Node reverseSecondHalf = reverse(secondHalf);

        Node p1 = firstHalf;
        Node p2 = reverseSecondHalf;

        while ( p2!= null){
            Node p1Next = p1.next;
            Node p2Next = p2.next;
            p1.next = p2;
            p2.next = p1Next;

            p1 = p1Next;
            p2 = p2Next;
        }

        return head;

    }

   /*
Given the head of a Singly LinkedList, write a method to check if the LinkedList is a palindrome or not.

Your algorithm should use constant space and the input LinkedList should be in the original form once the algorithm is finished. The algorithm should have O(N) time complexity where â€˜Nâ€™ is the number of nodes in the LinkedList.

Example 1: Input: 2 -> 4 -> 6 -> 4 -> 2 -> null Output: true
Example 2: Input: 2 -> 4 -> 6 -> 4 -> 2 -> 2 -> null Output: false
Constraints:
The number of nodes in the list is in the range [].
0 <= Node.val <= 9
 */

    /**
     * Example 1: Input: 2 -> 4 -> 6 -> 4 -> 2 -> null Output: true
     * // find the middle nod 2 -> 4 -> 6m -> 4s -> 2f -> null
     * Example 2: Input: 2 -> 4 -> 6 -> 4ms -> 2 -> 2 -> null Output: false
     *
     */
    public static boolean isPalindrome(Node head){
        // find middle node
        Node slow = head;
        Node fast = head;
        Node firstHalfEnd= head; // get last node of first half

        while(fast != null && fast.next != null){
            firstHalfEnd= slow;
            slow = slow.next;
            fast = fast.next.next;

        }

        Node middleNode = null;
        // check for odd length of list
        if (fast != null){
            middleNode = slow;
            slow= slow.next;

        }
        // cuthe firtpart
        firstHalfEnd .next = null;
        Node secondHalfStart = slow;

        // reverse second half
        Node reverseSecondHalf = reverse(secondHalfStart);

        // compare halves
        Node p1 = head;
        Node p2 = reverseSecondHalf;
        boolean isPalindrome = true;
        while (p2 != null){
            if (p1.data != p2.data){
                isPalindrome = false;
                break;
            }

            p1 = p1.next;
            p2 = p2.next;

        }
        // reconsturct the list
        Node restoreSecondHalf= reverse(reverseSecondHalf);
        // connect them
        if (middleNode != null){
            middleNode.next = restoreSecondHalf;
            firstHalfEnd.next = middleNode;
        }else{
            firstHalfEnd.next = restoreSecondHalf;
        }
        return isPalindrome;

    }


    public static Node reverse(Node head){
        Node curr = head;
        Node prev = null;

        while (curr!= null){
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
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
        if (head == null || head.next == null) return false;

        Node slow = head;
        Node fast = head;

        while(fast != null && fast.next != null){
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

        System.out.println(makeItBold(listO05.getHead() +"") + " Is it palindrome?" + makeItBold(isPalindrome(listO05.getHead())+"") + ", List after the checking: " +makeItBold( listO05.getHead()+""));

//        list.printList();
        listO05.clear();

        listO05.createLinkedList(new int[]{2, 4, 6, 4, 2, 2});


        System.out.println(makeItBold(listO05.getHead() +"") + " Is it palindrome?" + makeItBold(isPalindrome(listO05.getHead())+"") + ", List after the checking: " +makeItBold( listO05.getHead()+""));


        System.out.println("===================================================");
        System.out.println("P06. Rearrange a Linked List");
        System.out.println("===================================================");
        LinkedList listP06   = new LinkedList();
        listP06.createLinkedList(new int[]{1, 2, 3, 4, 5, 6});

        System.out.print("Original Linked List: " + makeItBold(listP06.toString()) +
                " , Rearranged Linked List: " + makeItBold(rearrangeList(listP06.getHead()).toString()) + ", Expected Output: 1 -> 6 -> 2 -> 5 -> 3 -> 4 -> null\n");
        /// some tests can be added here
        listP06.clear();
        listP06.createLinkedList(new int[]{2, 4, 6, 8, 10, 12});
        System.out.print("Original Linked List: " + makeItBold(listP06.toString()) +
                " , Rearranged Linked List: " + makeItBold(rearrangeList(listP06.getHead()).toString()) + ", Expected Output: 2 -> 12 -> 4 -> 10 -> 6 -> 8 -> null\n");
        listP06.clear();
        listP06.createLinkedList(new int[]{2, 4, 6, 8, 10});
        System.out.print("Original Linked List: " + makeItBold(listP06.toString()) +
                " , Rearranged Linked List: " + makeItBold(rearrangeList(listP06.getHead()).toString()) + ", Expected Output: 2 -> 10 -> 4 -> 8 -> 6 -> null\n");



    }
}

