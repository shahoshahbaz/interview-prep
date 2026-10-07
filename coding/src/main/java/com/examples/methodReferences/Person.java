package com.examples.methodReferences;

public class Person {
    private String name;
    private int age;

    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Custom toString method
    @Override
    public String toString() {
        return name + " (" + age + ")";
    }

    // Static method for sorting by name
    public static int compareByName(Person a, Person b) {
        return a.getName().compareTo(b.getName());
    }

    // Instance method to print the person's information
    public void printPerson() {
        System.out.println(this);
    }
}
