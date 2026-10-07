package com.examples.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Common Methods of the Queue Interface:
 *      add(E e): Inserts the specified element into the queue. Throws an exception if the capacity is exceeded.
 *      offer(E e): Inserts the specified element into the queue. Returns false if it fails to insert, instead of throwing an exception.
 *      poll(): Retrieves and removes the head of the queue, returning null if the queue is empty.
 *      remove(): Retrieves and removes the head of the queue, but throws an exception if the queue is empty.
 *      peek(): Retrieves but does not remove the head of the queue, returning null if the queue is empty.
 *      element(): Retrieves but does not remove the head of the queue. Throws an exception if the queue is empty.
 * Best Collections to Implement Queue Interface:
 *  LinkedList:
 *      Characteristics: Implements both the Queue and Deque interfaces. It can be used as a simple FIFO queue or a double-ended queue.
 *      Usage: Good for general-purpose queue operations, as it supports insertion and removal from both ends.
 *  PriorityQueue:
 *      Characteristics: A priority-based queue where elements are ordered based on their natural ordering (or by a custom comparator).
 *      Usage: Useful when you need to process elements based on priority, not just insertion order.
 *  ArrayDeque:
 *      Characteristics: A resizable array implementation of the Deque interface, which can also function as a queue. It performs well for both FIFO and LIFO operations.
 *      Usage: More efficient than LinkedList for queue operations due to better memory locality.
 * Recommendation:
 * For general-purpose queues: LinkedList or ArrayDeque are good choices. ArrayDeque is generally preferred for performance reasons.
 * For priority-based processing: PriorityQueue is the best choice. It automatically orders elements based on priority.
 */
public class QueueExamples {

    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        // Add elements to the queue
        System.out.println("Adding elements using add():");
        queue.add("Alice");   // Throws exception if it fails
        queue.add("Bob");
        queue.add("Charlie");

        queue.forEach(System.out::println);

        // Offer elements to the queue
        System.out.println("\nOffering elements using offer():");
        queue.offer("David"); // Returns false if it fails (e.g., full queue)
        queue.offer("Eve");
        queue.forEach(System.out::println);

        // Peek at the head of the queue (does not remove it)
        System.out.println("\nPeek at the head using peek():");
        System.out.println("Head: " + queue.peek());  // Output: Alice

        // Element (similar to peek but throws an exception if empty)
        System.out.println("\nGet head using element():");
        System.out.println("Head: " + queue.element());  // Output: Alice

        // Poll elements (removes the head and returns it)
        System.out.println("\nPolling elements using poll():");
        System.out.println("Polled: " + queue.poll());  // Removes Alice
        System.out.println("Polled: " + queue.poll());  // Removes Bob
        queue.forEach(System.out::println);

        // Remove (similar to poll but throws an exception if empty)
        System.out.println("\nRemoving elements using remove():");
        System.out.println("Removed: " + queue.remove());  // Removes Charlie
        queue.forEach(System.out::println);

        // Edge cases: peek, poll, element, and remove on an empty queue
        System.out.println("\nClearing queue and testing edge cases:");
        queue.clear();

        System.out.println("Peek on empty queue: " + queue.peek());  // Returns null
        System.out.println("Poll on empty queue: " + queue.poll());  // Returns null

        try {
            System.out.println("Element on empty queue: " + queue.element());  // Throws exception
        } catch (Exception e) {
            System.out.println("Exception on element(): " + e);
        }

        try {
            System.out.println("Remove on empty queue: " + queue.remove());  // Throws exception
        } catch (Exception e) {
            System.out.println("Exception on remove(): " + e);
        }



    }
}
