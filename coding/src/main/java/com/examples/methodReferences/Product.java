package com.examples.methodReferences;

public class Product {

    private  String name;
    private  double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Static method for comparing products by price

    public static int compareByPrice(Product a, Product b){
        return Double.compare(a.getPrice(), b.getPrice());
    }
    public void printProduct(){
        System.out.println(name + ": $" + price);
    }

    @Override
    public String toString() {
        return name +": $" + price;
    }
}
