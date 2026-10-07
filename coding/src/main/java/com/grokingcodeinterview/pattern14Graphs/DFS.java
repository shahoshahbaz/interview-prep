package com.grokingcodeinterview.pattern14Graphs;

import java.util.ArrayList;
import java.util.List;

public class DFS {

    // find if path exsit between two node
    public static boolean dfs(int[][] edges,   int  start, int target){
        int n = edges.length;
        List<Integer>[] adj = new ArrayList[n];

        for (int i =0; i<n;i++){
            adj[i] = new ArrayList<>();
        }
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            adj[u].add(v);
            adj[v].add(u);
        }

        boolean[] visited = new boolean[n];

        return dfs(start, target, visited, adj);


    }

    public static boolean dfs(int start, int target, boolean[] visited, List<Integer>[] adj){

        if(start == target) return true;

        visited [start] = true;
        for (int nei: adj[start]){
            if(!visited[nei])
                if(dfs(nei, target, visited, adj)) return true;

        }

        return false;
    }
    }

