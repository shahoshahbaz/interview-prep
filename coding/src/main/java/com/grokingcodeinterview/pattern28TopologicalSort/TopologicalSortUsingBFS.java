package com.grokingcodeinterview.pattern28TopologicalSort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/*
Problem Statement
Given a directed acyclic graph, return the topological order of its nodes values.
 */

public class TopologicalSortUsingBFS {
    public static List<Integer> topSortUsingBFS(int n, int[][] edges){

        List<Integer>[] graph = new ArrayList[n];
        int[] inDegree = new int[n];

        for(int i=0; i<n; i++)
            graph[i] = new ArrayList<>();

        //Build graph
        for (int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            inDegree[v]++;
        }

        // Start with nodes having 0 in-degree
        Queue<Integer> queue = new LinkedList<>();

        for(int i =0; i < n ; i++){
            // why we queue nodes with 0 in-degree? because they have no dependencies and can be processed first.
            // They are the starting points of the topological order.
            if(inDegree[i] ==0) queue.offer(i);
        }

        // process level by level
        List<Integer> result = new ArrayList<>();

        while (!queue.isEmpty()){
            int node = queue.poll();
            result.add(node);

            for(int nei: graph[node]){
                // why we decrease the in-degree of neighbors?
                // because we have processed the current node,
                // so we can remove its dependency from its neighbors
                inDegree[nei] --;
                // why we check for in-degree of neighbors?
                // because if a neighbor's in-degree become 0,
                // it means all its dependencies have been processed.
                if(inDegree[nei] ==0){
                    queue.offer(nei);
                }
            }
        }

        // check for cycle
        // why we check for cycle by comparing the size of result with n?
        // because if there is a cycle, we won't be able to process all nodes,
        // and the size of result will be less than n.
        // if there is a cycle, we return an empty list to indicate that topological sort is not possible.
        if(result.size() != n) return new ArrayList<>();
        return result;
    }

    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("Topological Sort using BFS");
        System.out.println("===========================");
        int n = 6;
        int[][] edges = {{5,2}, {5,0}, {4,0}, {4,1}, {2,3}, {3,1}};
        List<Integer> result = topSortUsingBFS(n, edges);
        System.out.println("Input: n = " + n + ", edges = " + java.util.Arrays.deepToString(edges) + ", output: " + result + ", Expected Output: [4, 5, 0, 2, 3, 1]");
        edges = new int[][]{{0,1}, {1,2}, {2,0}};
        result = topSortUsingBFS(n, edges);
        System.out.println("Input: n = " + n + ", edges = " + java.util.Arrays.deepToString(edges) + ", output: " + result + ", Expected Output: []");
        edges = new int[][]{{0,1}, {1,2}, {2,3}, {3,4}, {4,5}};
        result = topSortUsingBFS(n, edges);
        System.out.println("Input: n = " + n + ", edges = " + java.util.Arrays.deepToString(edges) + ", output: " + result + ", Expected Output: [0, 1, 2, 3, 4, 5]");
    }
}

