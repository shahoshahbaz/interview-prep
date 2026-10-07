package com.examples.streams;


import java.util.List;

public class StreamExample {
// ===========================
    // Sample Data
    // ===========================

    static List<String> names = List.of(
            "Alice", "Bob", "Charlie", "Anna", "Brian", "Catherine", "David"
    );

    static List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    static List<Product> products = List.of(
            new Product("iPhone 15", "Electronics", 999.99, 50),
            new Product("MacBook Pro", "Electronics", 2499.99, 20),
            new Product("AirPods Pro", "Electronics", 249.99, 100),
            new Product("Galaxy S24", "Electronics", 899.99, 45),
            new Product("Gaming Chair", "Gaming", 499.99, 15),
            new Product("PS5 Controller", "Gaming", 69.99, 150),
            new Product("Running Shoes", "Sports", 129.99, 75),
            new Product("Yoga Mat", "Sports", 49.99, 120),
            new Product("Protein Powder", "Sports", 59.99, 0),
            new Product("Coffee Maker", "Kitchen", 199.99, 40),
            new Product("Air Fryer", "Kitchen", 119.99, 60),
            new Product("Toaster", "Kitchen", 39.99, 0)
    );


    public static void main(String[] args) {
        System.out.println("================================================");
        System.out.println("  JAVA STREAM API — COMPLETE EXAMPLES");
        System.out.println("================================================\n");
        filterExamples();
        mapExamples();
        collectExamples();
//        sortedExamples();
//        reduceExamples();
//        findExamples();
//        matchExamples();
//        flatMapExamples();
//        combinedExamples();
//        realWorldExamples();
    }

    // ===========================
    // 1. FILTER
    // Keep elements that match a condition, remove the rest
    // ===========================

    static void filterExamples(){
        System.out.println("--- 1. FILTER ---");

        List<Integer> evens = numbers.stream()
                .filter(n -> n%2 ==0)
                .toList();

        System.out.println("Even numbers: " + evens);


        // keep greater than 5
        List<Integer> greaterThan5 = numbers.stream()
                .filter(n -> n> 5)
                .toList();
        System.out.println("Greater than 5: " + greaterThan5);

        // keep names starting with A
        List<String> aNames = names.stream()
                .filter(str -> str.startsWith("A"))
                .toList();

        System.out.println("Names starting with A: " + aNames);
        // keep products in Electronics category

        List<Product> electronics = products.stream()
                .filter(p -> p.getCategory().equals("Electronics"))
                .toList();
        System.out.println("Electronics: " + electronics.stream()
                .map(Product::getName).toList());


        // keep products out of stock
        List<Product> outOfStocks = products.stream().
                filter(p -> p.getStock() ==0).toList();

        System.out.println("Out of stock: " + outOfStocks.stream()
                .map(Product::getName).toList());
        System.out.println();
    }

    static void mapExamples(){
        System.out.println("--- 2. MAP ---");

        // convert names to uppercase

        List<String> upperNames = names.stream()
                .map(n -> n.toUpperCase())
                .toList();

        System.out.println("Uppercase names: " + upperNames);

        // get length of each name

        List<Integer> nameLengths = names.stream()
                .map(n -> n.length())
                .toList();
        System.out.println("Name lengths: " + nameLengths);
        // → [5, 3, 7, 4, 5, 9, 5]

        // double each number

        List<Integer> doubled = numbers.stream()
                .map(n -> n *2)
                .toList();
        System.out.println("Doubled numbers: " + doubled);

        // extract product names from products — String to String

        List<String> productNames = products.stream()
                .map(p -> p.getName())
                .toList();

        System.out.println("Product names: " + productNames);
        // → [iPhone 15, MacBook Pro, ...]

        // same using method reference — cleaner shorthand
        List<String> productNamesRef = products.stream()
                .map(Product:: getName).toList();
        System.out.println("Product names (method ref): " + productNamesRef);

        // apply 10% discount to all prices
        List<Double> discountedPrices = products.stream()
                .map(p-> p.getPrice() * 0.90)
                .toList();
        System.out.println("Discounted prices (10% off): " + discountedPrices);
        // → [899.991, 2249.991, ...]

        System.out.println();



    }

    // ===========================
    // 3. COLLECT
    // Gather stream results into a collection
    // ===========================
    static void collectExamples() {
        System.out.println("--- 3. COLLECT ---");

    }



    static class Product {
        private String name;
        private String category;
        private Double price;
        private Integer stock;

        public Product(String name, String category, Double price, Integer stock) {
            this.name = name;
            this.category = category;
            this.price = price;
            this.stock = stock;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public Double getPrice() {
            return price;
        }

        public Integer getStock() {
            return stock;
        }

        @Override
        public String toString() {
            return name + "($" + price + ")";
        }
    }

    static class Order {
        private String orderId;
        private List<String> items;

        public Order(String orderId, List<String> items) {
            this.orderId = orderId;
            this.items = items;
        }

        public List<String> getItems() {
            return items;
        }
    }

    static class ProductDTO {
        private String name;
        private Double price;

        public ProductDTO(String name, Double price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return name + "($" + price + ")";
        }

    }
}
