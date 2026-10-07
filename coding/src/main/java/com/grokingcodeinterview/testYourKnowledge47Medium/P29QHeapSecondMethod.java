package com.grokingcodeinterview.testYourKnowledge47Medium;
/*
QHeap1

Problem Statement:
Implement a heap-like data structure that
 supports three types of queries, given as an integer array of commands. There are three query types:

1 v â€” Add an element with value v to the heap.
2 v â€” Delete one instance of the element with value v from the heap.
3 â€” Print the current minimum element in the heap.

Process the queries in order and output the result of every 3 query.

Example 1:Input queries:
    1 4
    1 9
    3
    2 4
    3

    Processing:
    1 4  -> add 4        (heap: {4})
    1 9  -> add 9        (heap: {4, 9})
    3    -> print min    -> output: 4
    2 4  -> delete 4      (heap: {9})
    3    -> print min    -> output: 9

    Expected Output:
    4
    9

Example 2:Input queries:
        1 7
        1 2
        1 7
        3
        2 7
        3

Processing:
1 7  -> add 7           (heap: {7})
1 2  -> add 2           (heap: {2, 7})
1 7  -> add 7           (heap: {2, 7, 7})
3    -> print min       -> output: 2
2 7  -> delete one 7    (heap: {2, 7})
3    -> print min       -> output: 2

Expected Output:
2
2
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/** use HashMap and Priority Queue **/
public interface P29QHeapSecondMethod {
    public static List<Integer> qHeap(List<String> queries) {
        List<Integer> result = new ArrayList<>();

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        HashMap<Integer, Integer> deleteCount = new HashMap<>(); // value: how many time has been deleted
        if (queries == null || queries.isEmpty()) return result;

        for (String query : queries) {
            String[] parts = query.split(" ");
            int option = Integer.parseInt(parts[0]);
            Integer value = (parts.length == 2) ? Integer.parseInt(parts[1]) : null;


            if (option == 1) { // add

                minHeap.offer(value);


            } else if (option == 2) { // removing element
                deleteCount.put(value, deleteCount.getOrDefault(value, 0) + 1);

            } else { // print minHeap

                while (!minHeap.isEmpty() && deleteCount.getOrDefault(minHeap.peek(), 0) > 0) {
                    int top = minHeap.poll();
                    deleteCount.put(top, deleteCount.get(top) - 1);
                }

            }
            result.add(minHeap.peek());
        }

        return result;

    }

}

