package com.grokingcodeinterview.pattern37realInterviewProblems;

import java.util.HashMap;
import java.util.Map;

public class QuestionsR2 {

    /*
        Develop a class that implements a Set data structure.
         A Set is a collection containing no duplicate elements and is primarily used to test whether a member is contained within,
         rather than retrieving a particular member.
         Please implement the
         add(element), remove(element), contains(element),
         and  _len_ methods using arrays -- use of dict is not allowed
 */
    public static class Set <K>{
        private  int size;
        private K[]  arr;
        private static final int CAPACITY = 10;

        public Set(){
            this.arr =(K[]) new Object[CAPACITY];
            size =0;
        }

        public boolean add(K key){
            if(contains(key)){
                return false;
            }

            // check the size of arr
            if(size == arr.length ){
                K[] newArray = (K[] ) new Object[2 * CAPACITY];
                for (int i =0; i< arr.length; i++){
                    newArray[i] = arr[i];
                }
                arr = newArray;
            }
            size++;
            arr[size] = key;
             return true;
        }

        public boolean contains(K key){
            for (K k: arr){
                if(key == k){
                    return true;
                }
            }
            return false;
        }

        public boolean remove(K key){
            if(!contains(key)) return false;

            for (int i =0; i< size; i++){
                if(arr[i] == key){

                    for (int j =i; j< size; j++){
                        arr[j] = arr[j+1];
                    }
                }
            }
            size --;
            return true;
        }

        public int length(){
            return size;
        }

    }


    /*
Problem Statement: LRU Cache

Design and implement a cache data structure using the Least Recently Used (LRU) eviction policy.

The cache should support the following operations:
- get(K key): Return the value associated with the key if it exists in the cache; otherwise, return null (or -1).
- put(K key, V value): Insert or update the value of the key. If the cache reaches its capacity, it should evict the least recently used item before inserting a new one.

Requirements:
- The cache has a fixed capacity defined at initialization.
- Both get and put operations must run in O(1) time complexity.

Implement the following:
- A constructor to initialize the cache with a given capacity.
- The get(K key) method.
- The put(K key, V value) method.
*/
    public static class LRU <K, V> {

        private final Map<K, Node<K, V>> cache;
        private final Node<K, V> head;
        private final Node<K, V> tail;
        private final int capacity;

        public LRU(int capacity){
            if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity must be greater than 0");
            }
            this.capacity = capacity;
            head = new Node<>(null, null); // dummy
            tail = new Node<> (null, null); // dummy
            head.next = tail;
            tail.prev = head;
            cache = new HashMap<>();



        }
        // check  the key exist
        public V get(K key){
            Node<K, V> node = cache.get(key);
            if(node == null){
                return null;
            }

            // move the node to the front
            moveToFront(node);
            return node.value;

        }

        public void put(K key, V value){

            Node<K, V> node = cache.get(key);
            if(node != null){
                //cache.put(key, new Node(key, value));
                node.value = value;
                moveToFront(node);
                return;
            }

            node= new Node<>(key, value);
             // check the size
            if(cache.size() == this.capacity){
                // evict LRU
                evict();
            }
            cache.put(key, node);
            addToFront(node);
        }
        // create doubly LinkedLis
        public  static class Node<K, V> {
            K key;
            V value;
            Node<K, V> prev;
            Node<K, V> next;

            public Node(K key, V value ){
                this.key = key;
                this.value = value;
            }
        }
        public void evict(){
            if(head == tail){
                return;
            }
            Node<K, V> lru = this.tail.prev;
            removeNode(lru);
            this.cache.remove(lru.key);
        }
        public void moveToFront(Node<K, V> node){

            removeNode(node);
            addToFront(node);

        }

        /*
          <=> node<=>
         */
        public void removeNode(Node<K, V> node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        public void addToFront(Node<K, V> node){
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }
    }

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("LRU Cache :");
        System.out.println("=================================================================");
        QuestionsR1.LRUCache<Integer, String> cache = new QuestionsR1.LRUCache<>(3);

        System.out.print("Adding 3 items: (capacity is 3): ");

        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.printCacheOrder(); // [3=C] [2=B] [1=A]
        System.out.print("Expected result: [3=C] [2=B] [1=A] (most recently used is 3, least recently used is 1)" +"\n");


        System.out.print("Accessing key 1 (value A), so it becomes most recently used: ");
        cache.get(1); // access 1, so it becomes most recently used
        cache.printCacheOrder(); // [1=A] [3=C] [2=B]
        System.out.print("Expected result: [1=A] [3=C] [2=B] (most recently used is 1, least recently used is 2)"+"\n");

        System.out.print("Adding key 4 with value D, which should evict least recently used key 2: ");
        cache.put(4, "D"); // evicts key 2
        cache.printCacheOrder(); // [4=D] [1=A] [3=C]
        System.out.print("Expected result: [4=D] [1=A] [3=C] (most recently used is 4, least recently used is 3)"+"\n");

        System.out.print("Accessing key 3 (value C), so it becomes most recently used: ");
        cache.get(3);
        cache.printCacheOrder(); // [3=C] [4=D] [1=A]
        System.out.println("Expected result: [3=C] [4=D] [1=A] (most recently used is 3, least recently used is 1)");

        System.out.println("=================================================================");
        System.out.println("Set Implementation :");
        System.out.println("=================================================================");
        Set<Integer> set = new Set<>();
        System.out.println("Adding 1, 2, 3 to the set:");
        set.add(1);
        set.add(2);
        set.add(3);
        System.out.println("Set contains 2? " + set.contains(2)); // true
        System.out.println("Set size: " + set.length()); // 3
        System.out.println("Removing 2 from the set: " + set.remove(2)); // true
        System.out.println("Set contains 2? " + set.contains(2)); // false
        System.out.println("Set size: " + set.length()); // 2

    }
}