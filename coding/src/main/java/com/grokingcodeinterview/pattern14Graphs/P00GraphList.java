package com.grokingcodeinterview.pattern14Graphs;

import java.util.ArrayList;
import java.util.List;

public class P00GraphList {

    List<Integer>[] graph;

    public P00GraphList(int n){
        this.graph = new ArrayList[n];
        for (int i =0; i<n; i++){
        graph[i] = new ArrayList<Integer>();
        }
    }

    public void addEdge(int u, int v){
        graph[u].add(v);
        graph[v].add(u);
    }

    public void printGraph() {
        for (int i = 0; i < graph.length; i++) {
            System.out.print(i + " -> ");
            for (int neighbor : graph[i]) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // add some example
        P00GraphList graph = new P00GraphList(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.printGraph();

    }
}

