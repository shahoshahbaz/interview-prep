package com.grokingcodeinterview.pattern14Graphs;

import java.util.*;

import static com.Utility.makeItBold;

/*
    Problem Statement
    Given an undirected graph, represented as a list of edges.
    Each edge is illustrated as a pair of integers [u, v],
    signifying that there's a mutual connection between node u and node v.
    You are also given starting node start, and a destination node end,
     return true if a path exists between the starting node and the destination node. Otherwise, return false.

    Example 1: Input: n = 4, edges = [[0,1],[1,2],[2,3]], start = 0, end = 3 Output: true
    Justification: There's a path from node 0 -> 1 -> 2 -> 3.
    Example 2: Input: n = 4, edges = [[0,1],[2,3]], start = 0, end = 3  Output: false
    Justification: Nodes 0 and 3 are not connected, so no path exists between them.
    Example 3: Input: n = 5, edges = [[0,1],[3,4]], start = 0, end = 4  Output: false
    Justification: Nodes 0 and 4 are not connected in any manner.
    Constraints:

    1 <= n <= 2 * 105
    0 <= edges.length <= 2 * 105
    edges[i].length == 2
    0 <= ui, vi <= n - 1
    ui != vi
    0 <= source, destination <= n - 1
    There are no duplicate edges.
    There are no self edges.

 */
public class P01FindIfPathExistsInGraph {
    public static  boolean validPath(int n, int[][] edges, int start, int end) {

        List<Integer>[] graph = new ArrayList[n];

        for(int i =0; i<n; i++){
            graph[i] = new ArrayList<>();
        }

        for(int i =0; i<edges.length; i++){
            int u = edges[i][0];
            int v = edges[i][1];
            graph[u].add(v);
            graph[v].add(u);
        }

        boolean[] visited = new boolean[n];

        return dfs(start, end, graph, visited);

    }

    public static boolean dfs( int start, int end, List<Integer>[] graph, boolean[] visited){

        if(start == end) return true;

        visited[start] = true;
        for(int nei: graph[start]){
            if (!visited[nei] && dfs(nei, end, graph, visited)) {

                    return true;
                            }
        }
        return false;

    }
        public static void main(String[] args) {
            System.out.println("===========================");
            System.out.println("P01. Find if Path Exists in Graph");
            System.out.println("===========================");
            int nP01 = 3;
            int[][] edgesP01 = { {0, 1}, {1, 2}, {2, 0} };
            int startP01 = 0;
            int endP01 = 2;
            boolean resultP06 =validPath(nP01, edgesP01, startP01, endP01);
            System.out.println("Input: n = " + nP01 + ", edges = " + Arrays.deepToString(edgesP01) + ", start = " + startP01 + ", end = " + endP01 + " output: " + makeItBold(resultP06+"") + ", Expected Output: true");

            nP01 = 6;
            edgesP01 = new int[][] { {0, 1}, {0, 2}, {3, 5}, {5, 4}, {4, 3} };
            startP01 = 0;
            endP01 = 5;
            resultP06 =validPath(nP01, edgesP01, startP01, endP01);
            System.out.println("Input: n = " + nP01 + ", edges = " + Arrays.deepToString(edgesP01) + ", start = " + startP01 + ", end = " + endP01 + " output: " + makeItBold(resultP06+"") + ", Expected Output: false");

            nP01 = 10;
            edgesP01 = new int[][] { {0, 7}, {0, 8}, {6, 1}, {2, 0}, {0, 4}, {5, 8}, {4, 7}, {1, 3}, {3, 5}, {6, 5} };
            startP01 = 7;
            endP01 = 5;
            resultP06 =validPath(nP01, edgesP01, startP01, endP01);
            System.out.println("Input: n = " + nP01 + ", edges = " + Arrays.deepToString(edgesP01) + ", start = " + startP01 + ", end = " + endP01 + " output: " + makeItBold(resultP06+"") + ", Expected Output: true");
            // and example with no edges
            nP01 = 5;
            edgesP01 = new int[][] {};
            startP01 = 0;
            endP01 = 4;
            resultP06 =validPath(nP01, edgesP01, startP01, endP01);
            System.out.println("Input: n = " + nP01 + ", edges = " + Arrays.deepToString(edgesP01) + ", start = " + startP01 + ", end = " + endP01 + " output: " + makeItBold(resultP06+"") + ", Expected Output: false");
            // and example with disconnected graph
            nP01 = 5;
            edgesP01 = new int[][] { {0, 1}, {1, 2}, {3, 4} };
            startP01 = 0;
            endP01 = 4;
            resultP06 =validPath(nP01, edgesP01, startP01, endP01);
            System.out.println("Input: n = " + nP01 + ", edges = " + Arrays.deepToString(edgesP01) + ", start = " + startP01 + ", end = " + endP01 + " output: " + makeItBold(resultP06+"") + ", Expected Output: false");
        }




}

