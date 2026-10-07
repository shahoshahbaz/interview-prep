package com.examples;

import java.util.Arrays;
import java.util.List;

public class MethodReferences {

    public static void main(String[] args) {
        List<Person> roster = Person.createRoster();
        roster.forEach(System.out::println);

        Person[] rosterAsArray = roster.toArray(new Person[roster.size()]);

        // Without method reference
        Arrays.sort(rosterAsArray, new PersonAgeComparator());

        // with Lambda expression
        Arrays.sort(rosterAsArray,(Person a, Person b) -> a.getBirthday().compareTo(b.getBirthday()));

        // wtih method reference
        Arrays.sort(rosterAsArray, Person::compareByAge);



    }
}
