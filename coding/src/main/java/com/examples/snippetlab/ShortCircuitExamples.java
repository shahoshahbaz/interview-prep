package com.examples.snippetlab;
public class ShortCircuitExamples {

    public static void main(String[] args) {
        System.out.println("Short-Circuit OR (||) Example:");
        orExample();

        System.out.println("\nShort-Circuit AND (&&) Example:");
        andExample();

        System.out.println("\nAvoid NullPointerException with Short-Circuit:");
        nullCheckExample();
    }

    // Example of short-circuiting with OR (||)
    public static void orExample() {
        boolean result = true || expensiveOperation();
        // The second part (expensiveOperation) is NEVER called
        System.out.println("Result: " + result);
    }

    // Example of short-circuiting with AND (&&)
    public static void andExample() {
        boolean result = false && expensiveOperation();
        // The second part (expensiveOperation) is NEVER called
        System.out.println("Result: " + result);
    }

    // Avoiding a potential NullPointerException with short-circuit
    public static void nullCheckExample() {
        String name = null;

        // Short-circuit prevents the second condition from running if name is null
        if (name != null && name.length() > 3) {
            System.out.println("Name is long enough");
        } else {
            System.out.println("Name is either null or too short");
        }
    }

    // Simulates an expensive or dangerous operation
    public static boolean expensiveOperation() {
        System.out.println("⚠️ expensiveOperation() was called!");
        return true;
    }
}