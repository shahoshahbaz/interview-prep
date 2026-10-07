package com.examples.differences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class StudentAgeComparator  implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return Integer.compare(o1.getAge(), o2.getAge());
    }
    public static void main(String[] args) {
        // Create a list of students
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 22));
        students.add(new Student("Bob", 25));
        students.add(new Student("Charlie", 20));

        // Print original list
        System.out.println("Original List:");
        for (Student student : students) {
            System.out.println(student);
        }

        // Sort students by age using the StudentAgeComparator
        Collections.sort(students, new StudentAgeComparator());

        // Print sorted list
        System.out.println("\nSorted by Age:");
        for (Student student : students) {
            System.out.println(student);
        }
    }


}
