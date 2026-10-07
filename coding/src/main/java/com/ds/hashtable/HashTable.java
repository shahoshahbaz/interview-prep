package com.ds.hashtable;

import java.util.LinkedList;

public class HashTable {
    public class Node{
        public String key;
        public int value;
        public Node next;

        public Node(String key, int value){
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
    private final int SIZE = 5;
    private LinkedList<Node>[] buckets;

    public HashTable(){
        buckets= new LinkedList[SIZE];
        for (int i =0; i< SIZE; i++){
            buckets[i] = new LinkedList<>();
        }
    }
    // Hash function to map the key to an index
    private int hash(String key) {
        return Math.abs(key.hashCode() % SIZE);
    }
    public void put (String key, int value){
        int index = hash(key);
        Node newNode = new Node(key, value);

        // Check if the key already exist in the bucket
        for (Node node: buckets[index]){
            if (node.key.equals(key)){
                node.value = value;
                return;
            }
        }
        // if key doesn't exist, insert it at the head of LinkedList
        buckets[index].addFirst(newNode);
    }

    public int  get(String key){
        int index = hash(key);
        LinkedList<Node> bucket = buckets[index];
        for (Node node :bucket){
            if (node.key.equals(key)){
                return node.value;
            }

        }
        return -1;

    }
    public LinkedList<String> getKeys(){
        LinkedList<String> keys = new LinkedList<>();
        for (int i =0 ; i< SIZE; i++){
            LinkedList<Node>bucket =  buckets[i];
            for (Node node: bucket){
                keys.add(node.key);
            }

        }
        return keys;
    }

    public boolean remove (String key){
        int index = hash(key);
        LinkedList<Node> bucket = buckets[index];
        for (Node node: bucket){
            if (node.key.equals(key)){
                bucket.remove(node);
                return true;

            }
        }
        return false;
    }
    public void printHashTable() {
        System.out.println("Hash Table Visualization:");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("Bucket " + i + ": ");
            if (buckets[i].isEmpty()) {
                System.out.println("null");
            } else {
                for (Node node : buckets[i]) {
                    System.out.print("[" + node.key + " -> " + node.value + "] -> ");
                }
                System.out.println("null");
            }
        }
    }

        public static void main(String[] args) {
            HashTable hashTable = new HashTable();

            // Inserting key-value pairs
            hashTable.put("apple", 10);
            hashTable.put("banana", 20);
            hashTable.put("grape", 30);
            hashTable.put("pear", 40); // Collision with "banana"

            // Print the hash table (bucket visualization)
            hashTable.printHashTable();
            System.out.println("Get value of key = 'banana': "+ hashTable.get("banana"));
            System.out.println("=======================");
            System.out.println("Remove entry wit key of'grape'...");
            hashTable.remove("grape");
            hashTable.printHashTable();
            System.out.println("Get all key: " + hashTable.getKeys().toString());



        }

}
