package com.grokingcodeinterview.pattern14Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.Utility.makeItBold;

public class Pattern15GraphR1 {
    /*
    Problem Statement
    Given a directed acyclic graph with n nodes labeled from 0 to n-1, determine the smallest number of initial nodes such that you can access all the nodes by traversing edges. Return these nodes.
    Example 1: Input: n = 6, edges = [[0,1],[0,2],[2,5],[3,4],[4,2]]  Expected Output: [0,3]
    Justification: Starting from nodes 0 and 3, you can reach all other nodes in the graph. Starting from node 0, you can reach nodes 1, 2, and 5. Starting from node 3, you can reach nodes 4 and 2 (and by extension 5).
    Example 2: Input: n = 3 edges = [[0,1],[2,1]] Expected Output: [0,2]
    Justification: Nodes 0 and 2 are the only nodes that don't have incoming edges. Hence, you need to start from these nodes to reach node 1.
    Example 3: Input: n = 5 edges = [[0,1],[2,1],[3,4]] Expected Output: [0,2,3]
    Justification: Node 1 can be reached from both nodes 0 and 2, but to cover all nodes, you also need to start from node 3.
    Constraints:
    2 <= n <= 10^5
    1 <= edges.length <= min(, n * (n - 1) / 2)
    edges[i].length == 2
    0 <= fromi, toi < n
    All pairs (fromi, toi) are distinct.
     */

    public static List<Integer>  findSmallestSetOfVertices(int n, List<List<Integer>> edges){
        List<Integer> result = new ArrayList<>();
        int[] indegree = new int[n];

        for (List<Integer> edge: edges){
            indegree[edge.get(1)] ++;
        }

        for (int i =0; i< n; i++){
            if(indegree[i]==0)
                result.add(i);
        }

        return result;
    }

    /*
    Problem Statement
    You are given a directed graph with n nodes, labeled from 0 to n-1. This graph is described by a 2D integer array graph,
     where graph[i] is an array of nodes adjacent to node i, indicating there is a directed edge from node i to each of the nodes in graph[i].
      A node is called a terminal node if it has no outgoing edges.
      A node is considered safe if every path starting from that node leads to a terminal node (or another safe node).
    Return an array of all safe nodes in ascending order.

    Example 1: Input: graph = [[1,2],[2,3],[2],[],[5],[6],[]] Expected Output: [3,4,5,6]
    Explanation:
    Node 3 is a terminal node.
    Node 4 leads to node 5, which is a safe node.
    Node 5 leads to node 6, which is a terminal node.
    Node 6 is a terminal node.
    Example 2: Input: graph = [[1,2],[2,3],[5],[0],[],[],[4]] Expected Output: [2,4,5,6]
    Explanation:
    Node 2 leads to node 5, which is a terminal node.
    Node 4 is a terminal node.
    Node 5 is a terminal node.
    Node 6 leads to node 4, which is a terminal node.
    Example 3: Input: graph = [[1,2,3],[2,3],[3],[],[0,1,2]] Expected Output: [0,1,2,3,4]
    Explanation:
    Node 3 is a terminal node.
    Node 2 leads to node 3, which is a terminal node.
    Node 1 leads to node 2, which is a safe node, and node 3, which is a terminal node.
    Similarly, all node leads to either a terminal or a safe node.
    Constraints:
    n == graph.length
    1 <= n <= 104
    0 <= graph[i].length <= n
    0 <= graph[i][j] <= n - 1
    graph[i] is sorted in a strictly increasing order.
    The graph may contain self-loops.
    The number of edges in the graph will be in the range [1, 4 * 104].
 */
    public static List<Integer> eventualSafeNodes(int[][] graph){
        int n = graph.length;
        int[] state = new int[n]; // visited: 1, safe: 2, unvisited: 0
        List<Integer> result = new ArrayList<>();
        for (int i =0; i<n; i++){
            if(dfs(graph, i, state)){
                result.add(i);
            }
        }

        Collections.sort(result);
        return result;
    }
    public static boolean dfs(int[][] graph, int start, int[] state){
        if(state[start] == 1) return false;
        if(state[start] == 2) return true;

        state[start] =1;
        for (int nei: graph[start]){
            if(!dfs(graph, nei, state)){
                return false;
            }
        }

        state[start] =2;
        return true;
    }
    /*
        Problem Statement
    There are n cities. Some of them are connected in a network. If City A is directly connected to City B, and City B is directly connected to City C, city A is indirectly connected to City C.

    If a group of cities is connected directly or indirectly, they form a province.

    Given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise, determine the total number of provinces.
    Example 1: Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]] Expected Output: 2
    Justification: Here, city 1 and 2 form a single provenance, and city 3 is one province itself.
    Example 2: Input: isConnected = [1,0,0],[0,1,0],[0,0,1]] Expected Output: 3
    Justification: In this scenario, no cities are connected to each other, so each city forms its own province.
    Example 3: Input: isConnected = [[1,0,0,1],[0,1,1,0],[0,1,1,0],[1,0,0,1]] Expected Output: 2
    Justification: Cities 1 and 4 form a province, and cities 2 and 3 form another province, resulting in a total of 2 provinces.
    Constraints:

    1 <= n <= 200
    n == isConnected.length
    n == isConnected[i].length
    isConnected[i][j] is 1 or 0.
    isConnected[i][i] == 1
    isConnected[i][j] == isConnected[j][i]
     */
    public static int findNumberOfProvinces(int[][] isConnected){
        int n = isConnected.length;
        boolean[] visited = new boolean[n];

        int provinces =0;

        for (int city =0; city<n; city++){
            if(!visited[city]){
                dfs(city,isConnected, visited);
                provinces++;
            }
        }
        return provinces;
    }
    public static void dfs(int city, int[][] isConnected,boolean[] visited){

        visited[city] = true;
        for(int nei =0; nei< isConnected[city].length;nei++){
            if(isConnected[city][nei] ==1 && !visited[nei]){
                dfs(nei, isConnected, visited);
            }
        }
    }
    /*
Problem Statement
Given an undirected graph,
 represented as a list of edges.
  Each edge is illustrated as a pair of integers [u, v],
   signifying that there's a mutual connection between node u and node v.

You are also given starting node start, and a destination node end,
 return true if a path exists between the starting node and the destination node. Otherwise, return false.

Example 1: Input: n = 4, edges = [[0,1],[1,2],[2,3]], start = 0, end = 3 Output: true
Justification: There's a path from node 0 -> 1 -> 2 -> 3.
Example 2: Input: n = 4, edges = [[0,1],[2,3]], start = 0, end = 3  Output: false
Justification: Nodes 0 and 3 are not connected, so no path exists between them.
Example 3: Input: n = 5, edges = [[0,1],[3,4]], start = 0, end = 4  Output: false
Justification: Nodes 0 and 4 are not connected in any manner.
Constraints:

1 <= n <= 2 * 105
0 <= edges.length <= 2 * 105
edges[i].length == 2
0 <= ui, vi <= n - 1
ui != vi
0 <= source, destination <= n - 1
There are no duplicate edges.
There are no self edges.

 */
    public static boolean validPath(int n, int[][] edges, int start, int end){

        List<Integer>[] graph = new ArrayList[n];
        for(int i = 0;i< n; i++)
            graph[i] = new ArrayList<>();

        for (int[] edge:edges){
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        boolean[] visited = new boolean[n];
        return dfs(start, end, graph,visited);

    }
    public static boolean dfs(int start , int end, List<Integer>[] graph,boolean[] visited ){
        if(start == end){
            return true;
        }
        visited[start] = true;
        for(int nei: graph[start]){
            if(!visited[nei] && dfs(nei, end, graph, visited)){
                    return true;

            }
        }
        return false;
    }
    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("P01. Find if Path Exists in Graph");
        System.out.println("===========================");

        int nP01 = 3;
        int[][] edgesP01 = { {0, 1}, {1, 2}, {2, 0} };
        int startP01 = 0;
        int endP01 = 2;

        boolean resultP06 =validPath(nP01, edgesP01, startP01, endP01);

        System.out.println("Input: n = " + nP01 + ", edges = " + Arrays.deepToString(edgesP01) + ", start = " + startP01 + ", end = " + endP01 + " output: " + makeItBold(resultP06+"") + ", Expected Output: true");

        nP01 = 6;
        edgesP01 = new int[][] { {0, 1}, {0, 2}, {3, 5}, {5, 4}, {4, 3} };
        startP01 = 0;
        endP01 = 5;
        resultP06 =validPath(nP01, edgesP01, startP01, endP01);
        System.out.println("Input: n = " + nP01 + ", edges = " + Arrays.deepToString(edgesP01) + ", start = " + startP01 + ", end = " + endP01 + " output: " + makeItBold(resultP06+"") + ", Expected Output: false");

        nP01 = 10;
        edgesP01 = new int[][] { {0, 7}, {0, 8}, {6, 1}, {2, 0}, {0, 4}, {5, 8}, {4, 7}, {1, 3}, {3, 5}, {6, 5} };
        startP01 = 7;
        endP01 = 5;
        resultP06 =validPath(nP01, edgesP01, startP01, endP01);
        System.out.println("Input: n = " + nP01 + ", edges = " + Arrays.deepToString(edgesP01) + ", start = " + startP01 + ", end = " + endP01 + " output: " + makeItBold(resultP06+"") + ", Expected Output: true");
        // and example with no edges
        nP01 = 5;
        edgesP01 = new int[][] {};
        startP01 = 0;
        endP01 = 4;
        resultP06 =validPath(nP01, edgesP01, startP01, endP01);
        System.out.println("Input: n = " + nP01 + ", edges = " + Arrays.deepToString(edgesP01) + ", start = " + startP01 + ", end = " + endP01 + " output: " + makeItBold(resultP06+"") + ", Expected Output: false");
        // and example with disconnected graph
        nP01 = 5;
        edgesP01 = new int[][] { {0, 1}, {1, 2}, {3, 4} };
        startP01 = 0;
        endP01 = 4;
        resultP06 =validPath(nP01, edgesP01, startP01, endP01);
        System.out.println("Input: n = " + nP01 + ", edges = " + Arrays.deepToString(edgesP01) + ", start = " + startP01 + ", end = " + endP01 + " output: " + makeItBold(resultP06+"") + ", Expected Output: false");

        System.out.println("===========================");
        System.out.println("P02. Number of Provinces");
        System.out.println("===========================");

        int[][] isConnectedP02 = {{1,1,0},{1,1,0},{0,0,1}};
        System.out.println("Input: " + Arrays.deepToString(isConnectedP02) +", output: " + makeItBold(findNumberOfProvinces(isConnectedP02)+"") + ", Expected Output: 2");
        isConnectedP02 = new int[][]{{1,0,0},{0,1,0},{0,0,1}};
        System.out.println("Input: " + Arrays.deepToString(isConnectedP02) +", output: " + makeItBold(findNumberOfProvinces(isConnectedP02)+"") + ", Expected Output: 3");
        isConnectedP02 = new int[][]{{1,0,0,1},{0,1,1,0},{0,1,1,0},{1,0,0,1}};
        System.out.println("Input: " + Arrays.deepToString(isConnectedP02) +", output: " + makeItBold(findNumberOfProvinces(isConnectedP02)+"") + ", Expected Output: 2");
        isConnectedP02 = new int[][]{{1,0,0,0,0},{0,1,0,0,0},{0,0,1,0,0},{0,0,0,1,1},{0,0,0,1,1}};
        System.out.println("Input: " + Arrays.deepToString(isConnectedP02) +", output: " + makeItBold(findNumberOfProvinces(isConnectedP02)+"") + ", Expected Output: 4");
        System.out.println("===========================================");
        System.out.println("P03. Find Eventual Safe States");
        System.out.println("===========================================");
        int[][] graphP03 = {{1, 2}, {2, 3}, {2}, {}, {5}, {6}, {}};
        List<Integer> resultP03 = eventualSafeNodes(graphP03);
        System.out.println("Input: " + Arrays.deepToString(graphP03) + " Output: " + makeItBold(resultP03 + "") + ", Expected: [3,4,5,6] <== DFS with Pruning");
        graphP03 = new int[][]{{1, 2}, {2, 3}, {5}, {0}, {}, {}, {4}};
        resultP03 = eventualSafeNodes(graphP03);
        System.out.println("Input: " + Arrays.deepToString(graphP03) + " Output: " + makeItBold(resultP03 + "") + ", Expected: [2,4,5,6] <== DFS with Pruning");
        graphP03 = new int[][]{{1, 2, 3}, {2, 3}, {3}, {}, {0, 1, 2}};
        resultP03 = eventualSafeNodes(graphP03);
        System.out.println("Input: " + Arrays.deepToString(graphP03) + " Output: " + makeItBold(resultP03 + "") + ", Expected: [0,1,2,3,4] <== DFS with Pruning");
        System.out.println("===========================================");
        System.out.println("P04. Minimum Number of Vertices to Reach All Nodes");
        System.out.println("===========================================");
        int nP04 = 6;
        List<List<Integer>> edgesP04 = new ArrayList<>();
        edgesP04.add(List.of(0,1));
        edgesP04.add(List.of(0,2));
        edgesP04.add(List.of(2,5));
        edgesP04.add(List.of(3,4));
        edgesP04.add(List.of(4,2));
        System.out.println("Input: nP04 = " + nP04 + " edgesP04 = " + edgesP04 + " Output: " + makeItBold(findSmallestSetOfVertices(nP04, edgesP04).toString()) + " Expected Output: [0,3]");

        nP04 = 3;
        edgesP04 = new ArrayList<>();
        edgesP04.add(List.of(0,1));
        edgesP04.add(List.of(2,1));
        System.out.println("Input: nP04 = " + nP04 + " edgesP04 = " + edgesP04 + " Output: " + makeItBold(findSmallestSetOfVertices(nP04, edgesP04).toString()) + " Expected Output: [0,2]");

        nP04 = 5;
        edgesP04 = new ArrayList<>();
        edgesP04.add(List.of(0,1));
        edgesP04.add(List.of(2,1));
        edgesP04.add(List.of(3,4));
        System.out.println("Input: nP04 = " + nP04 + " edgesP04 = " + edgesP04 + " Output: " + makeItBold(findSmallestSetOfVertices(nP04, edgesP04).toString()) + " Expected Output: [0,2,3]");


    }
}

