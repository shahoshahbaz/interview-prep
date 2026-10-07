package com.examples.methodReferences;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * There are four types of method references:
 *
 * Reference to a Static Method
 * Syntax: ClassName::staticMethodName
 *
 * Reference to an Instance Method of a Particular Object
 * Syntax: instance::instanceMethodName
 *
 * Reference to an Instance Method of an Arbitrary Object of a Particular Type
 * Syntax: ClassName::instanceMethodName
 *
 * Reference to a Constructor
 * Syntax: ClassName::new
 */
interface  Greeter {
    void greet();
}
public class MethodReferenceExamples {

    private static void sayHello() {
        System.out.println("Hello there");
    }

    public static void main(String[] args) {
        Greeter g = () -> {
            System.out.println("Hello");
        };
        g.greet();

        g = MethodReferenceExamples::sayHello;
        g.greet();

        //  Reference to a Static Method
        // example:
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.forEach(MethodReferenceExamples::printNumber);
//        Reference to an Instance Method of a Particular Object
        // example:


        MethodReferenceExamples instance = new MethodReferenceExamples();
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        // Using method reference to an instance method of a particular object
        names.forEach(instance::print);


        System.out.println("*** another example ******************");
        /// another example
        List<Person> people = new ArrayList<>(Arrays.asList(
                new Person("Alice", 30),
                new Person("Bob", 25),
                new Person("Charlie", 35),
                new Person("David", 28)
        ));
        // Using a method reference to sort by name (static method reference)
        people.sort(Person::compareByName);
        System.out.println("Sorted by name: ");
        people.forEach(System.out::print);

        // Using a method reference to print each person's information (instance method reference)
        System.out.println("\nUsing instance method reference to print each person:");
        people.forEach(Person::printPerson);



        // Using a method reference to print each person's name only (instance method of arbitrary object)
        System.out.println("\nUsing method reference to print names:");
        people.forEach(person -> System.out.println(person.getName()));


    }

    public static void printNumber(int number) {
        System.out.println(number);
    }

    public void print(String s) {
        System.out.println(s);
    }



}


