package com.grokingcodeinterview.pattern14Graphs;

import com.ds.graph.ArrayListGraph;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    ArrayListGraph graph;
    public BFS(ArrayListGraph graph){
        this.graph = graph;
    }
    public void bfs(int startVertex){
        boolean[] visited = new boolean[graph.adjacencyList.length];
        Queue<Integer> queue = new LinkedList<>();

        visited[startVertex] = true;
        queue.add(startVertex);

        while (!queue.isEmpty()){
            int current = queue.poll();
            System.out.print(current  + "\t" );

            //Explore all adjacent vertices
            for (int neighbor: graph.adjacencyList[current] ){
                if (!visited[neighbor]){
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }



        }


    }
public static void main(String[] args) {
    // Example usage:
    // Create a graph with 5 vertices
    ArrayListGraph graph = new ArrayListGraph(5);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 3);
    graph.addEdge(1, 4);
    graph.printGraph();

    BFS bfs = new BFS(graph);
    System.out.print("BFS traversal starting from vertex 0:\t");
    bfs.bfs(0);
}

}

