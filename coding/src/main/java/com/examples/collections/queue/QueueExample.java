package com.examples.collections.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueExample {
    public static void main(String[] args) {
        // Create a Queue using LinkedList
        Queue<String> queue = new LinkedList<>();

        // Add elements to the queue
        queue.add("Apple");
        queue.add("Banana");
        queue.add("Cherry");
        queue.add("Avacado");

        // Access elements using queue methods
        System.out.println("Head of the queue: " + queue.peek());  // Retrieves the head
        System.out.println("Removed: " + queue.poll());            // Removes the head

        System.out.println("New head of the queue: " + queue.peek()); // Retrieves the new head


        // Use a lambda expression to print all elements in the queue
        System.out.println("Use a lambda expression to print all elements in the queue");
        queue.stream().forEach(element -> System.out.println(element));

        queue.clear();
        queue.add("Apple");
        queue.add("Banana");
        queue.add("Cherry");
        queue.add("Avacado");


        // If you want to filter and collect the elements greater than 10
        System.out.println("Elements Start with A:");
        queue.stream()
                .filter(element -> element.startsWith("A"))
                .forEach(element -> System.out.println(element));

        // convert the filter value to a list
        System.out.println("convert the filter value to a list");
        List<String> list =queue.stream()
                .filter(e->e.startsWith("A"))
                    .toList();

        System.out.println(list);

    }
}

