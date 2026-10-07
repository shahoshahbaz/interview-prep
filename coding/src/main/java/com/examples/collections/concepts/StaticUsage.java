package com.examples.collections.concepts;

public class StaticUsage {
   public class Counter {
        // Static variable to keep track of count
        private static int count = 0;

        // Static method to increment the count
        public static void increment() {
            count++;
        }

        // Static method to get the current count
        public static int getCount() {
            return count;
        }
    }

    public class StaticExample {
        public static void main(String[] args) {
            // Incrementing count using static method
            Counter.increment();
            Counter.increment();

            // Getting the current count using static method
            System.out.println("Current count: " + Counter.getCount()); // Output: 2
        }
    }

    public static void main(String[] args) {
        Counter.increment();
        Counter.increment();
        System.out.println("Current Count: " + Counter.getCount());

    }
}
