package com.examples.functionInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Functional Programming (FP) in Java is a programming paradigm that treats computation as the evaluation of
 * mathematical functions and avoids changing state or mutable data.
 * Key Characteristics of Functional Programming:
 * First-Class Functions: Functions can be passed as arguments, returned from other functions, or assigned to variables.
 *               Immutability: Data is treated as immutable, meaning once created, its state cannot change.
 *              Pure Functions: Functions with no side effects, meaning they don’t change any state and always produce the same output given the same input.
 *              Higher-Order Functions: Functions that take other functions as parameters or return them.
 * Key Concepts in Java Functional Programming:
 *      Lambda Expressions: Enable you to treat functionality as a method argument, or pass code as data.
 *      Functional Interfaces: Interfaces that have exactly one abstract method, e.g., Function<T, R>, Predicate<T>, Consumer<T>, etc.
 *      Method References: A shorthand notation of lambda expressions to refer to methods directly by their names.
 *      Streams API: Helps with functional-style operations on collections such as filtering, mapping, reducing, et
 */

@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}

public class FunctionalInterfaceExample {


    public static int calculate(int a, int b, MathOperation operation){
        return operation.operate(a, b);
    }

    public static List<Integer> filterNumbers(List<Integer> numbers, Predicate<Integer> predicate) {
        List<Integer> result = new ArrayList<>();
        for (Integer number : numbers) {
            if (predicate.test(number)) {
                result.add(number);
            }
        }
        return result;
    }
    public static void main(String[] args) {

        /**  Example of Passing a Lambda Expression to a Method with custom made function interface**/



        // Passing lambda expressions to the calculate method
        System.out.println("Addition: " + calculate(10, 5, (x, y) -> x + y ));
        System.out.println("Subtraction: " + calculate(10, 5, (x, y) -> x - y));
        System.out.println("Multiplication: " + calculate(10, 5, (x, y) -> x * y));
        System.out.println("Division: " + calculate(10, 5, (x, y) -> x / y));

        /***Create new scratch file from selection **/

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Using a lambda expression to filter even numbers
        List<Integer> evenNumbers = filterNumbers(numbers, n -> n % 2 == 0);
        System.out.println("Even numbers: " + evenNumbers);

        // Using a lambda expression to filter odd numbers
        List<Integer> oddNumbers = filterNumbers(numbers, n -> n % 2 != 0);
        System.out.println("Odd numbers: " + oddNumbers);

        // example of ReplaceAll




    }


}
