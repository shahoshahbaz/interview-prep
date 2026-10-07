package com.grokingcodeinterview.pattern14Graphs;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphUtils {

    // Convert Edge List -> Adjacency List
    public static List<List<Integer>> buildAdjList(int n, int[][] edges) {

        List<List<Integer>> adjList = new ArrayList<>();

        for (int i =0; i<n; i++){ adjList.add(new ArrayList<>());}

        for (int[] edge:edges){
            int source = edge[0];
            int des = edge[1];

            adjList.get(source).add(des);
            // If undirected graph, also add reverse:
            // adj.get(v).add(u);
        }
        return adjList;



    }

    // Convert Adjacency List -> Edge List

    public static List<int[]> buildEdgeList(List<List<Integer>> adj) {
        List<int[]> edges  = new ArrayList<>();

        for (int u = 0; u<adj.size(); u++){
            for (int v:adj.get(u)){
                edges.add(new int[]{u, v});
            }
        }
    return  edges;
    }

    public static void main(String[] args) {
        // ---------------- Example 1: Edge List -> Adjacency List ----------------
        int n = 6;
        int[][] edges = { {0,1}, {0,2}, {2,5}, {3,4}, {4,2} };

        System.out.println("Edge List:");
        for (int[] e : edges) {
            System.out.println(Arrays.toString(e));
        }

        List<List<Integer>> adjList = buildAdjList(n, edges);

        System.out.println("\nAdjacency List:");
        for (int i = 0; i < adjList.size(); i++) {
            System.out.println(i + " -> " + adjList.get(i));
        }

        // ---------------- Example 2: Adjacency List -> Edge List ----------------
        List<List<Integer>> adj = new ArrayList<>();
        adj.add(Arrays.asList(1, 2)); // 0 -> 1,2
        adj.add(Arrays.asList());     // 1 -> []
        adj.add(Arrays.asList(5));    // 2 -> 5
        adj.add(Arrays.asList(4));    // 3 -> 4
        adj.add(Arrays.asList(2));    // 4 -> 2
        adj.add(Arrays.asList());     // 5 -> []

        System.out.println("\nAdjacency List (input):");
        for (int i = 0; i < adj.size(); i++) {
            System.out.println(i + " -> " + adj.get(i));
        }

        List<int[]> edgeList = buildEdgeList(adj);

        System.out.println("\nEdge List (converted):");
        for (int[] e : edgeList) {
            System.out.println(Arrays.toString(e));
        }
    }
}


