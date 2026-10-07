package com.grokingcodeinterview.pattern28TopologicalSort;

import java.util.*;

import static com.Utility.makeItBold;

/*
problem statement
Given a directed graph, check if it contains a cycle.
example: n = 4, edges = [[0,1], [1,2], [2,0]] -> true
example: n = 4, edges = [[0,1], [1,2], [2,3]] -> false
example: n= 5, edges = [[0,1], [1,2], [2,3], [3,1]] -> true


 */
public class P02DirectedGraphCycleCheck {
    public static boolean hasCycle(int n, int[][] edges){
        List<Integer>[] graph = new ArrayList[n];
        int[] indegree = new int[n];

        for(int i =0; i<n; i++){
            graph[i] = new ArrayList<>();
        }

        // build the graph
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            indegree[v]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        // first add node with indegree 0
        for (int i =0; i<n; i++){
            if(indegree[i] ==0)
                queue.offer(i);
        }

        List<Integer> result = new ArrayList<>();

        while (!queue.isEmpty()){
            int node = queue.poll();
            result.add(node);

            for(int nei: graph[node]){
                indegree[nei]--;
                if(indegree[nei] ==0){
                    queue.offer(nei);
                }
            }
        }


        return result.size() != n;



    }

    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("P02. Directed Graph Cycle Check");
        System.out.println("===========================");
        int nP02 = 4;
        int[][] edgesP02 = {{0,1}, {1,2}, {2,0}};
        System.out.println("Input: n = " + nP02 + ", edges = " + makeItBold(Arrays.deepToString(edgesP02)) + ", output: " + makeItBold(hasCycle(nP02, edgesP02) +" ") + ", Expected Output: true");
        nP02 = 4;
        edgesP02 = new int[][]{{0,1}, {1,2}, {2,3}};
        System.out.println("Input: n = " + nP02 + ", edges = " + makeItBold(Arrays.deepToString(edgesP02)) + ", output: " + makeItBold(hasCycle(nP02, edgesP02) +" ") + ", Expected Output: false");
        nP02 = 5;
        edgesP02 = new int[][]{{0,1}, {1,2}, {2,3}, {3,1}};
        System.out.println("Input: n = " + nP02 + ", edges = " + makeItBold(Arrays.deepToString(edgesP02)) + ", output: " + makeItBold(hasCycle(nP02, edgesP02) +" ") + ", Expected Output: true");
    }
}

