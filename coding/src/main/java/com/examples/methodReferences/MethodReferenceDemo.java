package com.examples.methodReferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@FunctionalInterface
interface ProductProcessor{
    void process(Product product);
}

public class MethodReferenceDemo {
    public static void main(String[] args) {

        List<Product> products = new ArrayList<>(Arrays.asList(
                new Product("Laptop", 1200),
                new Product("Smartphone", 800),
                new Product("Tablet", 400),
                new Product("Monitor", 300)
        ));
        // 1. Static Method Reference - Sorting products by price


        System.out.println("Sorted by price:");
        products.sort(Product::compareByPrice);  // Static method reference
        products.forEach(System.out::println);   // Prints sorted products


        // 2. Instance Method Reference (Particular Object) - Printing each product's details

        ProductProcessor printer = Product::printProduct;
        products.forEach(printer:: process);

        // 3. Instance Method Reference (Arbitrary Object) - Filtering and printing expensive products

        Predicate<Product> expensiveProduct=p -> p.getPrice() > 500;
        products.stream().filter(expensiveProduct).forEach(Product::printProduct);





    }
}
