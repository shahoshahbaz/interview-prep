package com.examples.lambdaExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

public class PredicateExamples {
    public static void main(String[] args) {
        Predicate<String> predicate = s -> s.length() < 4;

        System.out.println(predicate.test("shsh"));
        System.out.println(predicate.test("shaho"));

        ArrayList<Integer> numbers = new ArrayList<>( Arrays.asList(3, 5, 7, 2, 9, 10, 4) );
        numbers.removeIf(n -> n < 6);
        System.out.println("updated list: " + numbers);

    }
}
