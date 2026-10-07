package com.examples.collections.list;

import java.util.LinkedList;

public class LinkedListExample {
    public static void main(String[] args) {
        // Declare and initialize a LinkedList
        LinkedList<String> tasks = new LinkedList<>();

        // Adding elements
        tasks.add("Task 1");
        tasks.add("Task 2");
        tasks.addFirst("Task 0"); // Adding to the beginning
        tasks.addLast("Task 3");  // Adding to the end

        // Printing the LinkedList
        System.out.println("Tasks: " + tasks);

        // Accessing an element
        String firstTask = tasks.get(0);
        System.out.println("First task: " + firstTask);

        // Modifying an element
        tasks.set(1, "Updated Task 2");
        System.out.println("Updated Tasks: " + tasks);

        // Removing an element
        tasks.remove("Task 0");
        System.out.println("After removal: " + tasks);

        // Size of the LinkedList
        System.out.println("Number of tasks: " + tasks.size());

        // Iterating through the LinkedList
        System.out.println("Iterating through tasks:");
        for (String task : tasks) {
            System.out.println(task);
        }

        // Checking if an element exists
        boolean hasTask2 = tasks.contains("Task 2");
        System.out.println("Contains Task 2: " + hasTask2);

        // Clearing the LinkedList
        tasks.clear();
        System.out.println("After clearing: " + tasks);
    }
}

