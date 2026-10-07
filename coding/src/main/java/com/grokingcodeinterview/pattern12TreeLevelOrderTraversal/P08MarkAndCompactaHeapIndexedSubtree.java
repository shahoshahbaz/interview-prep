package com.grokingcodeinterview.pattern12TreeLevelOrderTraversal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/*
Mark and Compact a Heap-Indexed Subtree

Setup: A binary tree is stored implicitly in an array using heap-index convention:
for a node at index i, its left child is at 2i + 1 and its right child is at 2i + 2.
 Some array slots may be empty/null (representing no node at that position).

Problem: Given the array-encoded tree and a target index k,
 find all descendants of the node at index k (i.e., mark the entire subtree rooted at k), t
 hen compact the result â€” return the marked subtree's values as a clean, gap-free list/array (typically in level order).

Example:

Input array (heap-indexed):
index:  0   1   2   3   4   5   6
value: [1,  2,  3,  4,  5,  6,  7]

Tree representation:
                1(0)
              /      \
            2(1)      3(2)
           /   \      /   \
         4(3)  5(4)  6(5)  7(6)

Target k = 1 (node value 2)

Subtree rooted at index 1: node 2, its children 4 and 5
Marked indices: {1, 3, 4}

Output (compacted, level order): [2, 4, 5]

Edge cases to consider:

k out of bounds or array slot at k is null â†’ return empty list
Sparse array with nulls in the middle of the subtree (a node's child index exists but is null â€” subtree traversal stops there)
k = 0 â†’ whole tree is the subtree
 */

/**
 * index:  0   1   2   3   4   5   6
 * value: [1,  2,  3,  4,  5,  6,  7] k = 1(node 2) output[2, 4, 5]
 */
public class P08MarkAndCompactaHeapIndexedSubtree {

    public static List<Integer> markAndCompactSubtree(Integer[] tree, int k){
        List<Integer> result = new ArrayList<>();
        if (k<0 || k>= tree.length || tree[k] == null) return result;



        Queue<Integer> queue = new LinkedList<>();
        queue.offer(k); // offer index

        while (!queue.isEmpty()){
            int levelSize = queue.size();

            for(int i =0; i< levelSize; i++){
                int index = queue.poll();
                result.add(index);

                   int leftChildIndex = 2*index +1;

                    if(leftChildIndex< tree.length && tree[leftChildIndex] != null ) {
                        queue.offer(leftChildIndex);
                    }

                    int rightChildIndex = 2*index +2;
                    if(rightChildIndex< tree.length && tree[rightChildIndex] != null) {
                        queue.add(rightChildIndex);
                    }
            }
        }
    return result;
    }
}

