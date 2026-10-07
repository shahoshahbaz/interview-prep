package com.grokingcodeinterview.pattern29UnionFind;

/*
 * Problem: Number of Connected Components in an Undirected Graph

(LeetCode 323)
Given n nodes labeled 0 to n-1 and a list of undirected edges, return the number of connected components.
Input:  n = 5, edges = [[0,1],[1,2],[3,4]]
Output: 2
*
* run dry example:
* edges = [[0,1], [1,2], [3,4]];
* parents [0, 1, 2, 3, 4]
* edge [0, 1] is connedcted = no
* connected: fix(0) =0 vs find(1) =1 the union(x, y) => parent[0] = 1 parents[1, 1, 2, 3, 4] counter = 4;
* edge[1, 2] connected?
* connected, find(1) =1 vs find (2) = 2 no  union(1, 2) = parent[find(1)] = find(2)
find(1) = 1, find(2) = 2 -> parent[1] = 2 => parents[1, 2, 2, 3, 4] counter = 3;
* [3, 4] connected: find(3)= 3 vs find(4)= 4 no
* unionFind(3, 4) = > parent [3] = 4  parents[1, 2, 2, 4, 4] counter = 2;
*  find(0) parent[0] =1 vs 0 parent[0] = find(1)
* find(1) parent[1] = 2 vs 1 so parent[1] = find(2}
* find(2) = 2;
* edge [1, 2]
 */
public class p00NumberOfConnectedComponents {

    private int[] parent;
    private int find(int i){
        if(parent[i] == i) return i;
        return find(parent[i]);
    }
    private boolean connected(int x, int y){
        return find(x) == find(y);
    }

    private void union(int x, int y){
        parent[find(x)] = find(y);
    }
    public int countComponents(int n, int[][] edges) {
        parent = new int[n];
        for (int i =0; i<n ; i++) parent[i] =i;

        int count = n;
        for(int[] edge: edges){
            if(!connected(edge[0], edge[1]) ){
                union(edge[0], edge[1]);
                count --;
            }
        }
    return count;
    }
}

