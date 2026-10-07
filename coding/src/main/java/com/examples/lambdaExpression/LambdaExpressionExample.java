package com.examples.lambdaExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
@FunctionalInterface
interface  Runner{
    void execute();
}
@FunctionalInterface
interface  Runner2{
    void execute(String text);
}

@FunctionalInterface
interface  Joiner {
    String join(String text1, String text2);
}
public class LambdaExpressionExample {
    public static void main(String[] args) {

        List<String> list = Arrays.asList("One", "two", "three");
        list.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        System.out.println("===================");
        list.forEach(s -> System.out.println(s));

        Runner runner =() ->{
            System.out.println("hello");
        };
        runner.execute();
        Runner2 runner2 = (String text) ->{
            System.out.println(text);
        };
        runner2.execute("Hi there");


        /**
         * * Rule for Omitting Parentheses:
         *          * Single parameter, no type declaration: Parentheses are optional.
         *          * Single parameter with type declaration: Parentheses are required.
         *          * Multiple parameters: Parentheses are required.
         */
        // Example: Single parameter, parentheses omitted
        Consumer<String> greeter = name -> System.out.println("Hello, " + name);
        greeter.accept("Shaho");

        //  Example: Single parameter with parentheses
        Consumer<String> greeter2 = (name) -> System.out.println("Hello, " + name);
        greeter2.accept("Shaho and Nasim");

        //  Example:  Parentheses required with type declaration
        Consumer<String> greeter3 = (String name) -> System.out.println("Hello, " + name);
        greeter3.accept("Hello Nasim and Shaho and Joanna");

        //  Example:  Two parameters, parentheses are required
        // Using BiFunction to represent addition of two integers
        BiFunction<Integer, Integer, Integer> addition = (a, b) -> a + b;

        System.out.println(addition.apply(1, 3));

        /**
         * Rules for Omitting Curly Braces in Lambda Expressions:
         * Single statement/expression: Curly braces can be omitted.
         * Multiple statements: Curly braces are required, and if you're returning a value, the return keyword must be used
         */

//        Example:
        BiFunction<Integer, Integer, Integer> multiplication = (a, b) -> a*b;
        // Curly braces required for multiple statements
        BiFunction<Integer, Integer, Integer> additionWithLogging = (a, b) -> {
            System.out.println("Adding " + a + " and " + b);
            return a + b;  // Explicit return required
        };

        System.out.println("==========================");
        /**
         * Rules for Omitting the return Keyword in Lambda Expressions:
        Single Expression:

        If the lambda body consists of a single expression, you can omit the return keyword as well as the curly braces.
        The result of the expression is implicitly returned without needing an explicit return statement.
         Multiple Statements:

         If the lambda body contains multiple statements, you must use the return keyword to explicitly return a value,
         and the curly braces are required as well.
         */
        //  Example:
        Joiner joiner = (t1, t2)-> t1 +t2;

        System.out.println(joiner.join("shaho ", "Shahbazpanahi"));
//        Example:
        additionWithLogging = (a, b) -> {
            System.out.println("Adding numbers: " + a + " and " + b);
            return a + b;  // Explicit return required
        };
         int sum = additionWithLogging.apply(5, 4);
        System.out.println(sum);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");

        /**
         * Lambda Expressions  Can not Modify Local Variables
         *  lambda expressions in Java cannot modify local variables that are defined outside of the lambda expression unless those variables are effectively final.
         *  ava enforces that lambda expressions can only capture and use variables that are effectively final, ensuring thread-safety and immutability
         */

        // example:

        int number =10;

        Runnable runnable = () -> {
            // Uncommenting the next line would cause a compilation error
//             number = 20; // This would try to modify the local variable
            System.out.println("number: " + number);
        };
        runnable.run();
       ;


//       Example

        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(1, 5, 1000, 3, 6, -20, 4));

        list2.removeIf(i -> i<0 || i>10);
        list2.forEach(System.out::print);
        list2.replaceAll(n -> n + 100);
        System.out.println(list2
        );








    }
}
