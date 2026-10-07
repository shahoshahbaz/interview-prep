package com.examples.differences;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * he Comparable interface is found in java.lang and has only one method: compareTo(T o).
 * Classes implementing Comparable must override this method to define how two objects of that class are compared.
 */
public class ComparableExample {
    public static class  Student implements Comparable<Student> {

        private String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }


        @Override
        public int compareTo(Student s) {
            return this.age -s.age ;
        }
    }

    public static void main(String[] args) {

        List<Student> students = Arrays.asList(new Student("Alice", 22), new Student("Bob", 25));
        Collections.sort(students);  // Will sort by age because of compareTo()


    }

}
