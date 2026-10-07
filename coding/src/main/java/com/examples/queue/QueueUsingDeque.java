package com.examples.queue;

import java.util.ArrayDeque;
import java.util.Deque;

public class QueueUsingDeque {
    private Deque<Integer> deque = new ArrayDeque<>();

    public void enqueue(int element) {
        deque.addLast(element);
    }

    public int dequeue() {
        return deque.removeFirst();
    }

    public int peek() {
        return deque.peekFirst();
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public static void main(String[] args) {
        // Queue example
        QueueUsingDeque queue = new QueueUsingDeque();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("Queue dequeue: " + queue.dequeue()); // Output: 1
        System.out.println("Queue peek: " + queue.peek()); // Output: 2
    }

}
