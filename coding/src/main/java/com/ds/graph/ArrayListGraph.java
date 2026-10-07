package com.ds.graph;

import java.util.*;

public class ArrayListGraph implements Graph<Integer> {
    public LinkedList<Integer>[] adjacencyList;
    int vertices;

    public ArrayListGraph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];

        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }
    @Override
    public void addEdge(Integer source, Integer destination) {
        adjacencyList[source].add(destination);
        adjacencyList[destination].add(source);

    }

    @Override
    public void addEdges(Integer source, Iterable<Integer> edges) {
        for (Integer edge : edges) {
            addEdge(source, edge);

        }
    }

    @Override
    public void addDirectedEdge(Integer source, Integer destination) {
        adjacencyList[source].add(destination);
    }

    @Override
    public void addDirectedEdges(Integer source, Iterable<Integer> edges) {
        for(Integer edge: edges){
            addDirectedEdge(source, edge);
        }

    }

    @Override
    public Set<Integer> getVertices() {

        return null;
    }

    @Override
    public Map<Integer, List<Integer>> getAdjacentList() {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i<vertices; i++)
            map.put(i, adjacencyList[i]);

        return map;

    }


    @Override
    public void printGraph() {
        StringBuilder sb = new StringBuilder();
        sb.append("adjacentList(list):").append("[");
        for (int i =0; i< adjacencyList.length; i++ ){
            sb.append("[").append(i).append("]:").append(adjacencyList[i]);
            if (i !=adjacencyList.length -1){
                sb.append(", ");
            }else{
                sb.append("]");
            }
        }
        System.out.println(sb.toString());


    }
    public static void main(String[] args) {
        // Create a graph with 5 vertices
        ArrayListGraph graph = new ArrayListGraph(5);

        // Add some edges (undirected)
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        // Print the adjacency list
        System.out.println("Graph adjacency list:");
        graph.printGraph();
    }
}
