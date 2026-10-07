package com.ds.graph;

import java.util.*;

/**
 * MapGraph is a graph implementation using a HashMap to store the adjacency list.
 * It supports both undirected and directed edges.
 *
 * @param <T> the type of vertices in the graph
 */
public class MapGraph<T>  implements Graph<T> {

    private  final Map<T, List<T>> adjacentListMap;
    Iterable<T> vertices;

    public MapGraph(Iterable<T> vertices) {
            this.vertices = vertices;
            this.adjacentListMap = new HashMap<>();
        }


    @Override
    public void addEdge(T source, T destination) {
        adjacentListMap.putIfAbsent(source, new LinkedList<>());
        adjacentListMap.putIfAbsent(destination, new LinkedList<>());

        if (!adjacentListMap.get(source).contains(destination)) {
            adjacentListMap.get(source).add(destination);
        }
        if (!adjacentListMap.get(destination).contains(source)) {
            adjacentListMap.get(destination).add(source);
        }
    }

    @Override
    public void addEdges(T source, Iterable<T> edges) {
        for (T edge : edges) {
            addEdge(source, edge);
        }

    }
  @Override
    public void addDirectedEdge(T source, T destination) {
        adjacentListMap.putIfAbsent(source, new LinkedList<>());
        adjacentListMap.putIfAbsent(destination, new LinkedList<>());

        if (!adjacentListMap.get(source).contains(destination)) {
            adjacentListMap.get(source).add(destination);
        }
    }

    @Override
    public void addDirectedEdges(T source, Iterable<T> edges) {
        for( T edge : edges) {
            addDirectedEdge(source, edge);
        }

    }



    public Set<T> getVertices() {
        return adjacentListMap.keySet();
    }

    @Override
    public Map<T, ? extends List<T>> getAdjacentList() {
        return adjacentListMap;
    }



    public void printGraph() {
        StringBuilder sb = new StringBuilder();
        sb.append("adjacentList(Map):").append("{").append("\n");
        for (Map.Entry<T, List<T>> entry : adjacentListMap.entrySet()) {

            sb.append("  ").append( "[" +entry.getKey()+"]").append(" -> ");
            for (T neighbor : entry.getValue()) {
                sb.append("[").append(neighbor) .append("]").append( " ");
            }
            sb.append("\n");
        }
        sb.append("}");
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        // Use with Integer
        MapGraph<Integer> intMapGraph = new MapGraph<>(Arrays.asList(1, 2, 3, 4));
        intMapGraph.addEdges(1, Arrays.asList(2, 3));

        intMapGraph.addEdge(2, 4);
        intMapGraph.addEdge(3, 4);
        intMapGraph.printGraph();

        // Use with Character
        MapGraph<Character> charMapGraph = new MapGraph<>(Arrays.asList('A', 'B', 'C', 'D'));
        charMapGraph.addEdges('A', Arrays.asList('B', 'C'));
        charMapGraph.addEdge('C', 'D');
        charMapGraph.printGraph();
    }
}
