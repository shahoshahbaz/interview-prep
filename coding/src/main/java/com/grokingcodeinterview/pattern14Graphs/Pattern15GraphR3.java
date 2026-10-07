package com.grokingcodeinterview.pattern14Graphs;

import java.util.ArrayList;
import java.util.List;

public class Pattern15GraphR3 {
    public static  boolean validPath(int n, int[][] edges, int start, int end) {

        List<List<Integer>> adjList = new ArrayList<>();

        for (int i =0; i< n; i++){
            adjList.add(new ArrayList<>());
        }

        for (int[] edge: edges){
            int u = edge[0];
            int v = edge[1];

            adjList.get(u).add(v);
            adjList.get(v).add(u);

        }
        boolean[] visited = new boolean[n];

        return dfs(start, end, adjList, visited);

    }

    private static boolean dfs(int start, int end, List<List<Integer>> adjList, boolean[] visited ){

        if(start == end) return true;
        visited[start] = true;


        for(int nei: adjList.get(start)){
            if(!visited[nei]){
                if(dfs(nei, end, adjList, visited )) return true;
            }
        }

        return false;


    }

    public static int findNumberOfProvinces(int[][] isConnected){

        if(isConnected == null) return 0;
        List<List<Integer>> adj = new ArrayList<>();
        int n = isConnected.length;
        for(int i =0; i< n; i++){
            adj.add(new ArrayList<>());
        }
        for (int i =0;i< n; i++ ){
            for(int j =0; j<n; j++){
                if(isConnected[i][j] ==1){
                    adj.get(i).add(j);
                    adj.get(j).add(j);
                }
            }
        }
        int count =0;
        boolean[] visited = new boolean[n];
        for (int i =0; i<n ; i++){
            if(!visited[i]) {
             dfs(i, adj, visited);
             count++;
            }
        }
        return count;


    }

    private static void dfs(int start, List<List<Integer>> adj, boolean[] visited){
        visited[start] =true;

        for(int node: adj.get(start)){
            if(!visited[node] ){
                dfs(node, adj, visited);
            }
        }

    }

    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        int[] state = new int[n]; // 0: unvisited, 1: visiting, 2: safe

        for (int i = 0; i < n; i++) {
            if (state[i] == 0) {
                dfs(i, graph, state);
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (state[i] == 2) {
                result.add(i);
            }
        }
        return result;
    }

    private static boolean dfs(int node, int[][] graph, int[] state) {
        if (state[node] == 1) return false; // cycle
        if (state[node] == 2) return true;  // already proven safe

        state[node] = 1; // mark visiting
        for (int nei : graph[node]) {
            if (!dfs(nei, graph, state)) {
                return false;
            }
        }
        state[node] = 2; // safe
        return true;
    }


}

