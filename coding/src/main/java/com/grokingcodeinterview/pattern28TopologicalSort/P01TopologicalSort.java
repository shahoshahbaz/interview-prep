package com.grokingcodeinterview.pattern28TopologicalSort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
Problem Statement
Topological Sort of a directed graph (a graph with unidirectional edges) is a linear ordering of its vertices such that for every directed edge (U, V) from vertex U to vertex V, U comes before V in the ordering.
Given a directed graph, find the topological ordering of its vertices. If the graph is cyclic, return an empty array.

Example 1 :Input: Vertices=7, Edges=[6, 4], [6, 2], [5, 3], [5, 4], [3, 0], [3, 1], [3, 2], [4, 1]
Output: Following are all valid topological sorts for the given graph:
1) 5, 6, 3, 4, 0, 1, 2
2) 6, 5, 3, 4, 0, 1, 2
3) 5, 6, 4, 3, 0, 2, 1
4) 6, 5, 4, 3, 0, 1, 2
5) 5, 6, 3, 4, 0, 2, 1
6) 5, 6, 3, 4, 1, 2, 0

There are other valid topological ordering of the graph too.

Example 2: Input: Vertices=4, Edges=[3, 2], [3, 0], [2, 0], [2, 1]
Output: Following are the two valid topological sorts for the given graph:
1) 3, 2, 0, 1
2) 3, 2, 1, 0

Example 3: Input: Vertices=5, Edges=[4, 2], [4, 3], [2, 0], [2, 1], [3, 1]
Output: Following are all valid topological sorts for the given graph:
1) 4, 2, 3, 0, 1
2) 4, 3, 2, 0, 1
3) 4, 3, 2, 1, 0
4) 4, 2, 3, 1, 0
5) 4, 2, 0, 3, 1
 */
public class P01TopologicalSort {
    public static List<Integer> topologicalSortBFS(int n, int[][] edges){
        List<Integer>[] graph = new ArrayList[n];
        // in general indegree[i] means number of depeneice of the node i,
        // we have to first process the nodes which have no dependencies,//
        // and then we can process the nodes which have dependencies, because we have processed the dependencies of those nodes
        int[] indegree = new int[n];
        for(int i =0; i<n; i++){
            graph[i] = new ArrayList<>();
        }

        for (int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);

            indegree[v]++;

        }

        Queue<Integer> queue = new LinkedList<>();
        // start with nodes with indegree 0
        for (int i =0; i< n; i++){
            // why we check indegree 0, because we want to start with nodes which have no dependencies,  and then we will process its neighbors
            if(indegree[i] ==0)
                queue.offer(i);
        }

        //process level by level
        List<Integer> result = new ArrayList<>();

        while(!queue.isEmpty()){
            int node = queue.poll();
           result.add(node);

            for(int nei: graph[node]){
                // why  we decrease the indegree of the neighbors?
                // because we have processed the current node,
                // so we can remove the dependcyof the niegbors

                indegree[nei] --;
                if(indegree[nei] ==0)
                    queue.offer(nei);
            }
        }
        // why we check the size of the result? because if the graph has a cycle, the we will not be able to process all the nodes
        // and the size of the result will be less than n,
        if(result.size()!= n) return new ArrayList<>();

        return result;

    }

    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("P01. Topological Sort");
        int nP01 = 7;
        int[][] edgesP01 = {{6, 4}, {6, 2}, {5, 3}, {5, 4}, {3, 0}, {3, 1}, {3, 2}, {4, 1}};
        System.out.println("Input: Vertices= " + nP01 + ", Edges= " + java.util.Arrays.deepToString(edgesP01)
                + ", Output: " + topologicalSortBFS(nP01, edgesP01) +
                ", Expected Output: [5, 6, 3, 4, 0, 1, 2] or [6, 5, 3, 4, 0, 1, 2] + or [5, 6, 4, 3, 0, 2, 1] or [6, 5, 4, 3, 0, 1, 2] or [5, 6, 3, 4, 0, 2, 1] or [5, 6, 3, 4, 1, 2, 0]");
        nP01 = 4;
        int[][] edgesP01_2 = {{3, 2}, {3, 0}, {2, 0}, {2, 1}};
        System.out.println("Input: Vertices= " + nP01 + ", Edges= " + java.util.Arrays.deepToString(edgesP01_2)
                + ", Output: " + topologicalSortBFS(nP01, edgesP01_2) +
                ", Expected Output: [3, 2, 0, 1] or [3, 2, 1, 0]");
        nP01 = 5;
        int[][] edgesP01_3 = {{4, 2}, {4, 3}, {2, 0}, {2, 1}, {3, 1}};
        System.out.println("Input: Vertices= " + nP01 + ", Edges= " + java.util.Arrays.deepToString(edgesP01_3)
                + ", Output: " + topologicalSortBFS(nP01, edgesP01_3) +
                ", Expected Output: [4, 2, 3, 0, 1] or [4, 3, 2, 0, 1] or [4, 3, 2, 1, 0] or [4, 2, 3, 1, 0] or [4, 2, 0, 3, 1]");

    }
}

