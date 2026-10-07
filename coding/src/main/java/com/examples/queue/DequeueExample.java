package com.examples.queue;

import java.util.ArrayDeque;
import java.util.Deque;

public class DequeueExample {




    public static void main(String[] args) {
        Deque<Integer> deque  = new ArrayDeque<>();

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        System.out.println(deque);
        System.out.printf("Front of dequeue is: %d and last Element is: %d", deque.getFirst(), deque.getLast());

        // work with dequeue as stack
        // we now stack is LIFO(lastInLastOut)
 deque.push(5);
        System.out.println(deque);
        System.out.println();deque.pop();
        // so push(intger) it will beadding to element infrom



    }
}
