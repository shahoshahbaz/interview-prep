package com.examples.collections.list;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListExamples {
    public static void main(String[] args) {

        ArrayList<String> fruits = new ArrayList<>();

        // Adding elements
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");

        System.out.println("Fruits: " + fruits);
        // Accessing elements
        System.out.println("First fruit: "+ fruits.get(0));

        // Modifying elements
        fruits.set(1, "Blueberry");
        System.out.println("Updated Fruits: " + fruits);
        //Removing elements
        fruits.remove("Cherry");
        System.out.println("After removal: "+ fruits);

        // Size of the ArrayList
        System.out.println("Number of fruits: " + fruits.size());

        // Iterating through the ArrayList
        System.out.println("Iterating through fruits: ");

        for (String s: fruits){
            System.out.println(s);
        }
        // print the list using lambda expression
        System.out.println("print the list using lambda expression");
        fruits.forEach(s-> System.out.println(s));
        System.out.println("==================================");
        System.out.println("print the list using method reference: ");
        fruits.forEach(System.out::println);

        // checking if an element exists
        System.out.println("Contains Appple: "+ fruits.contains("Apple"));

        // clearing fruits
//        fruits.clear();
//        System.out.println("After cleaning: " + fruits);
        System.out.println("==================================");
        System.out.println("converting list to array");
        String[]  fruitsArray=fruits.toArray(new String[0]);
        Arrays.stream(fruitsArray).forEach(s -> System.out.println(s));
    }
}
