package com.ds.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Graph <T> {

    void addEdge(T source, T destination);

    void addEdges(T source, Iterable<T> edges);

    void addDirectedEdge(T source, T destination);

    void addDirectedEdges(T source, Iterable<T> edges);

    Set<T> getVertices();

    Map<T, ? extends List<T>> getAdjacentList();

    void printGraph();
}
