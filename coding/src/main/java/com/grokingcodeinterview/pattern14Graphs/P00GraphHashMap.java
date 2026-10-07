package com.grokingcodeinterview.pattern14Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P00GraphHashMap {

    Map<Integer, List<Integer>> graph;

    public P00GraphHashMap(){
        graph = new HashMap<>();

    }

    public void addNode(int u){
        graph.putIfAbsent(u, new ArrayList<>());
    }

    public void addEdge(int u, int v){
        addNode(u);
        addNode(v);
        graph.get(u).add(v);
        graph.get(v).add(u);
    }
    public void printGraph() {
        for (int node : graph.keySet()) {
            System.out.print(node + " -> ");
            for (int neighbor : graph.get(node)) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // one example
        P00GraphHashMap graph = new P00GraphHashMap();
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.printGraph();
    }
}

