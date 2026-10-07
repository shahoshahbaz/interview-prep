package com.grokingcodeinterview.pattern11HashMaps;

import java.util.HashMap;
import java.util.Map;


/*
LRU Cache
Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
Implement the LRUCache class:

LRUCache(int capacity) â€” Initialize the LRU cache with positive size capacity.
int get(int key) â€” Return the value of the key if it exists, otherwise return -1. Accessing a key counts as using it "recently."
void put(int key, int value) â€” Update the value of the key if it exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.

Both get and put must run in O(1) average time complexity.
Example:
LRUCache cache = new LRUCache(2);
cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.put(4, 4);    // evicts key 1
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4
*/
public class P06DesignLRUCache {

 // see this imgage: {@link src/main/java/com/grokingcodeinterview/pattern11HashMap/LRU_hashmap_linkedlist_relationship.png}
    public static class LRU{

        private static class Node {
            int value;
            Node next;
            Node prev;
            int key;

            public Node(int key, int value){
                this.key = key;
                this.value = value;
            }
        }

        // the map is  your lookup shortcut("where is Key in the linked list?") to find the node in the linked list
        private Map<Integer, Node> map;
        // the postion of tail and head
        // they are dummy node and they point to the first and last node of the linked list
        // for example  head(0) <= 1 <=> 2 <=> 3 => tail(0)
        // so don't think head should be inthe right side and tail should be in the left side, they are just dummy node to help us to add and remove node from the linked list

        Node tail ; // LRU
        Node head;// MRU

        int capacity;
        public LRU(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>();
            tail = new Node(0, 0);
            head = new Node(0,0);
            head.next = tail;
            tail.prev = head;

        }

        public int get(int key) {

           Node node = map.get(key);
            if(node == null) return -1;
            moveToFront(node);
            return node.value;

        }

        public void put(int key, int value) {
            Node node = map.get(key);
            if(node != null){
                node.value = value;
                moveToFront(node);
                return;
            }

            if(capacity == map.size()){
                Node lru= tail.prev;
                removeNode(lru);
                map.remove(lru.key);

            }

            Node newNode = new Node(key, value);
            addToFront(newNode);
            map.put(key, newNode);

        }
        //t
        private void addToFront(Node node){
            node.next = head.next;
            node.prev = head;

            head.next.prev = node;
            head.next = node;


        }

        private void removeNode(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void moveToFront(Node node){
            removeNode(node);
            addToFront(node);



        }
    }
}

