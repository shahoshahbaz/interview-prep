package com.grokingcodeinterview.testYourKnowledge47Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.Utility.makeItBold;

/*
Number of Connected Components in an Undirected Graph (LeetCode 323, Medium)

You have a graph of n nodes labeled 0 to n - 1. You are given an integer n and an array edges where edges[i] = [a_i, b_i] indicates that there is an undirected edge between nodes a_i and b_i in the graph. Return the number of connected components in the graph.

Example 1:
Input: n = 5, edges = [[0,1],[1,2],[3,4]]
Output: 2
Explanation: Nodes 0-1-2 form one component, nodes 3-4 form another.

Example 2:
Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]
Output: 1
Explanation: All nodes are connected in a single chain: 0-1-2-3-4.
 */
public class P23NumberOfConnectedComponentsInanUndirectedGraph {

    public  static int countComponents(int n, int[][] edges){
        int components = 0;

        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++){
            adj[i] = new ArrayList<>();
        }

        boolean[] visited = new boolean[n];

        for (int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            adj[u].add(v);
            adj[v].add(u);
        }

        for (int i =0; i< n; i++){
            if (!visited[i] ){
                dfs(adj, visited, i);
                components++;
            }
        }
        return components ;
    }

    public static void dfs(List<Integer>[] adj, boolean[] visited, int node){
        if(visited[node]) return ;
        visited[node] = true;

        for (int nei: adj[node]){
            if(!visited[nei]){
                dfs(adj, visited, nei);
            }
        }

    }

    public static void main(String[] args) {
        System.out.println("=======================================================");
        System.out.println("P23. Number of Connected Components in Undirected Graph");
        System.out.println("=======================================================");

        int[][] edges1 = {{0, 1}, {1, 2}, {3, 4}};
        int n1 = 5;
        System.out.println("Input: n=" + n1 + ", edges=" + Arrays.deepToString(edges1) + ", output: " + makeItBold(countComponents(n1, edges1) + "") + ", Expected Output: 2");

        int[][] edges2 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};
        int n2 = 5;
        System.out.println("Input: n=" + n2 + ", edges=" + Arrays.deepToString(edges2) + ", output: " + makeItBold(countComponents(n2, edges2) + "") + ", Expected Output: 1");

        int[][] edges3 = {};
        int n3 = 4;
        System.out.println("Input: n=" + n3 + ", edges=" + Arrays.deepToString(edges3) + ", output: " + makeItBold(countComponents(n3, edges3) + "") + ", Expected Output: 4");

        int[][] edges4 = {{0, 1}, {2, 3}, {4, 5}, {6, 7}, {0, 7}};
        int n4 = 8;
        System.out.println("Input: n=" + n4 + ", edges=" + Arrays.deepToString(edges4) + ", output: " + makeItBold(countComponents(n4, edges4) + "") + ", Expected Output: 3");
    }
}

