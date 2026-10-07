package com.grokingcodeinterview.pattern14Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.Utility.makeItBold;

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
public class P03FindEventualSafeStates {
    public static List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        List<Integer> result = new ArrayList<>();
        int[] states = new int[n]; //  unvistied = 0, states = 1, safe = 2
        for (int i = 0; i < n; i++) {
            if (dfs(graph, i, states)) {
                result.add(i);
            }
        }

        Collections.sort(result);

        return result;
    }

    public static boolean dfs(int[][] graph, int start, int[] states) {
        if (states[start] == 1) return false;
        if (states[start] == 2) return true;

        states[start] = 1;
        for (int nei : graph[start]) {
            if (!dfs(graph, nei, states)) {
                return false;
            }

        }
        states[start] = 2;
        return true;

    }

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("P03. Find Eventual Safe States");
        System.out.println("===========================================");
        int[][] graphP03 = {{1, 2}, {2, 3}, {2}, {}, {5}, {6}, {}};
        List<Integer> resultP03 = eventualSafeNodes(graphP03);
        System.out.println("Input: " + Arrays.deepToString(graphP03) + " Output: " + makeItBold(resultP03 + "") + ", Expected: [3, 4, 5, 6] <== DFS with Pruning");
        graphP03 = new int[][]{{1, 2}, {2, 3}, {5}, {0}, {}, {}, {4}};
        resultP03 = eventualSafeNodes(graphP03);
        System.out.println("Input: " + Arrays.deepToString(graphP03) + " Output: " + makeItBold(resultP03 + "") + ", Expected: [2, 4, 5, 6] <== DFS with Pruning");
        graphP03 = new int[][]{{1, 2, 3}, {2, 3}, {3}, {}, {0, 1, 2}};
        resultP03 = eventualSafeNodes(graphP03);
        System.out.println("Input: " + Arrays.deepToString(graphP03) + " Output: " + makeItBold(resultP03 + "") + ", Expected: [0, 1, 2, 3, 4] <== DFS with Pruning");
    }
}
