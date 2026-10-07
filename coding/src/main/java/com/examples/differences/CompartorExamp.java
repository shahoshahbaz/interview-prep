package com.examples.differences;

/**
 * Comparator:
 * Used to define custom orderings for objects.
 * It is typically used when you need to define multiple ways to compare objects.
 * You can have many comparators for a class, each defining a different sort order.
 * The Comparator interface is found in java.util and
 * has two methods:
 * compare(T o1, T o2) and
 * equals(Object obj) (though equals is rarely overridden directly).
 */
public class CompartorExamp {



//    public static void main(String[] args) {
//
//
//        Comparator<Student> nameComparator = (s1, s2)-> s1.getName() .compareTo(s1.getName());
//        Comparator<Student> ageComparator = (s1, s2)-> s2.getAge() - s1.getAge(); // Comparator to sort students by age in descending order
//        Comparator<Student> ageComparatorAscendingOrder = (s1, s2) ->s1.getAge() - s2.getAge();
//        List<Student> students = Arrays.asList(new Student("Alice", 22), new Student("Bob", 25));
//
//        // Sorting by name
//        System.out.println("// Sorting by name");
//        Collections.sort(students, nameComparator);  // Sorts alphabetically by name
//        students.forEach(System.out::println);
//
//        // Sorting by age in descending order
//        System.out.println("// Sorting by age in descending order");
//        Collections.sort(students, ageComparator);
//        students.forEach(System.out::println);// Sorts by age in descending order
//
//        System.out.println("// Sorting by age in ascending order");
//        Collections.sort(students, ageComparatorAscendingOrder);
//        students.forEach(System.out::println);// Sorts by age in ascending order
//

//    }
}
