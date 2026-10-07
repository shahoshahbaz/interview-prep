package com.examples.collections.list;
import java.util.ArrayList;
import java.util.List;

public class RemoveExample {

    /**
     * Key Points
     * Ambiguity: If we only use window.remove(nums[indexToRemove]);,
     * we risk calling the remove(int index) method if the value happens
     * to match an index of the list. In this specific case,
     * we are fine because 1 is not an index of the list after removing it.
     * However, if our list contained fewer elements or if we tried to remove a
     * value that could be confused as an index, it could lead to unexpected results.
     *
     * Explicit Intent: Using Integer.valueOf() ensures that we are always removing an element based on its value,
     * eliminating the chance of ambiguity about whether we mean to remove an index or a value.
     *
     */
    public static void main(String[] args) {
        // Step 1: Initialize a list with some integer values
        List<Integer> window = new ArrayList<>();
        window.add(0); // index 0
        window.add(1); // index 1
        window.add(2); // index 2
        window.add(3); // index 3
        window.add(4); // index 4

        // Step 2: Attempt to remove an element
        int indexToRemove = 1; // Let's say we want to remove the value at index 1, which is 1

        // Here we will demonstrate using nums array
        int[] nums = {0, 1, 2, 3, 4}; // Example array

        // Using nums[indexToRemove]
        System.out.println("Original List: " + window);
        window.remove(nums[indexToRemove]); // window.remove(1)
        System.out.println("List after removing nums[indexToRemove]: " + window);

        // Now let's see the behavior using Integer.valueOf
        window.add(1); // Re-adding 1 for further demonstration
        System.out.println("List before using Integer.valueOf: " + window);

        // Using Integer.valueOf
        window.remove(Integer.valueOf(nums[indexToRemove])); // window.remove(Integer.valueOf(1))
        System.out.println("List after removing using Integer.valueOf: " + window);
    }
}
