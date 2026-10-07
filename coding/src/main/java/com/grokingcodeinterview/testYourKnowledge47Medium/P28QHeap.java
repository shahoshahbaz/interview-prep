package com.grokingcodeinterview.testYourKnowledge47Medium;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

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
public class P28QHeap {
    public static List<Integer> qHeap(List<String> queries) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> toRemove = new PriorityQueue<>();

        for (String query: queries){
            String[] parts = query.split(" ");

            int type = Integer.parseInt(parts[0]);
            Integer value = (parts.length== 2)?Integer.parseInt(parts[1]):null;

            if(type == 1){ // adding
                minHeap.offer(value);
            }else if(type == 2){ // delete
                toRemove.offer(value);

            }else{ // print min

                while(!toRemove.isEmpty() && !minHeap.isEmpty() && toRemove.peek().equals(minHeap.peek())){
                    minHeap.poll();
                    toRemove.poll();

                }

                result.add(minHeap.peek());

            }
        }
        return  result;
    }
}

