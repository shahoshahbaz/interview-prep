package com.grokingcodeinterview.pattern14Graphs;

import java.util.ArrayList;
import java.util.List;

import static com.Utility.makeItBold;

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
public class P04MinimumNumberOfVerticesToReachAllNodes {
    public  static List<Integer> findSmallestSetOfVertices (int n ,List<List<Integer>> edges){
        int[] indegree = new int[n];
        for(List<Integer> edge: edges){
            int u = edge.get(0);
            int v = edge.get(1);
            indegree[v] ++;
        }
        List<Integer> result= new ArrayList<>();
        for(int i=0; i< n; i++){
            if(indegree[i] ==0){
                result.add(i);
            }
        }

        return result;
    }

    public static void main(String[] args) {
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

