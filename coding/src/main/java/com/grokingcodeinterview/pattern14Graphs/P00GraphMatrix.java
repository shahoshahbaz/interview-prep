package com.grokingcodeinterview.pattern14Graphs;

public class P00GraphMatrix {

    int[][] graph;
    int n;


    public P00GraphMatrix(int n){
        this.n = n;
        this.graph = new int[n][n];
    }

    public void addEdge(int u, int v){
        graph[u][v] =1;
        graph[v][u] =1;
    }

    public void printGraph() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // some example
        P00GraphMatrix graph = new P00GraphMatrix(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.printGraph();
    }
}

