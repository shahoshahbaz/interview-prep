package com.examples;

import java.util.Arrays;

public class ArrayExample {
    public static void main(String[] args) {
        // declaration of array
        int[] numbers = {1, 2,  4,3,  5};
        String[] names = { "Bob", "Charlie", "Alice"};

       // Using Arrays.toString() for 1D Arrays
        System.out.println("Printing array...");
        System.out.println(Arrays.toString(names));;
        System.out.println(Arrays.toString(numbers));;

        // sorting array
        Arrays.sort(numbers);
        Arrays.sort(names);

        System.out.println("Printing sorted  array...");
        System.out.println(Arrays.toString(names));;
        System.out.println(Arrays.toString(numbers));;

//        numbers = new int[]{1, 2, 4, 3, 5};
        System.out.println(Arrays.toString(numbers));
         names = new String[]{ "Bob", "Charlie", "Alice"};

         int index = Arrays.binarySearch(numbers, 3);
        System.out.println("Find the index of 3: " + index);

        System.out.println("Summing elements...");

        System.out.println(Arrays.stream(numbers).sum());
        System.out.println((int)Arrays.stream(numbers).max().getAsInt());
        System.out.println((int)Arrays.stream(numbers).min().getAsInt());

        int[][] matrix = new int[3][3]; // 3x3 matrix
        int[][] predefinedMatrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

    }
}
