package com.grokingcodeinterview.pattern04FastAndSlowPointers;

import com.ds.singleLinkedList.Node;

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
public class P03StartOfLinkedListCycle {
    public  static Node findCycleStart(Node head){
        if (head == null ) return null;
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next !=null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast){
                break;
            }
        }
        slow = head;
        while (slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        // some tests can be added here

        Node head = new Node(1);
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
        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        head2.next.next.next.next.next = new Node(6);
        // Creating a cycle here
        head2.next.next.next.next.next.next = head2.next; // Cycle starts at node with value 2
        Node cycleStartNode2 = findCycleStart(head2);
        System.out.println("Cycle starts at node with value: " + (cycleStartNode2 != null ? cycleStartNode2.data : "null")); // Expected output: 2


    }
}

