package com.grokingcodeinterview.pattern37realInterviewProblems;

import java.util.HashMap;
import java.util.Map;

import static com.Utility.makeItBold;

public class QuestionR3 {
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

    public static class LRU<K, V>{

        Map<K, Node<K, V>> cache ;
        Node<K, V> head;
        Node<K, V > tail;
        private   final int CAPACITY;

        public LRU(int capacity){
            this.CAPACITY = capacity;
            cache  = new HashMap<>();

            head = new Node<>(null,null);
            tail = new Node<>(null, null);

            head.next = tail;
            tail.prev = head;

        }

        public V get(K key){
            Node<K, V> node = cache.get(key);
            if (node == null){
                return null;
            }

             moveToFront(node);

            return node.value;

        }
        public void put(K key, V value){

            Node<K,V> node = cache.get(key);
            if(node != null){
                node.value = value;
                moveToFront(node);
                return;
            }

            if(cache.size() == CAPACITY){
                Node<K,V> lru = tail.prev;
                removeNode(lru);
                cache.remove(key);
            }
            Node newNode = new Node<>(key, value);
            cache.put(key, newNode );
            addToFront(newNode);
        }







        public static class Node<K, V>{
            K key;
            V value;
            Node<K, V> next;
            Node<K,V> prev;

            public Node(K key, V value){
                this.key = key;
                this.value = value;
            }
        }

        public void moveToFront(Node<K,V> node){
            removeNode(node);
            addToFront(node);
        }

        public void removeNode(Node<K,V> node){

            // link the next pointer
            node.prev.next = node.next;
            // link the prev pointer;
            node.next.prev = node.prev;

        }

        public void addToFront(Node<K, V> node){

            // connect to where head point
            node.next = head.next;
            node.prev = head;

            head.next.prev = node;
            head.next = node;
        }


        public void printCacheOrder(){
            Node<K, V> curr = head.next;
            StringBuilder sb = new StringBuilder();
            while(curr!= tail){
                sb.append("[").append(curr.key).append("=").
                        append(curr.value).append("] ");
                curr = curr.next;

            }
            System.out.print(makeItBold(sb.toString() +"\t"));
        }



    }

    /*
Develop a class that implements a Set data structure.
 A Set is a collection containing no duplicate elements and is primarily used to test whether a member is contained within,
  rather than retrieving a particular member.
   Please implement the
    add(element), remove(element), contains(element),
    and  _len_ methods using arrays -- use of dict is not allowed
 */

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("LRU Cache :");
        System.out.println("=================================================================");
        LRU<Integer, String> cache = new LRU<>(3);

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
    }
}
