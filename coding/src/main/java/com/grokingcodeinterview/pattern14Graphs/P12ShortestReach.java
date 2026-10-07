package com.grokingcodeinterview.pattern14Graphs;
/*
Consider an undirected graph consisting of n nodes,
 labeled 1 to n,
 where every edge has the same length: 6.
  You are given the number of nodes and edges,
   a list of edges,
    and a starting node s.
     Find the shortest distance from s to every other node in the graph.
     Return the distances in node-number order (ascending, excluding s itself).
     If a node is unreachable from s, its distance is -1.

Example 1:
Input: n = 4, edges = [[1,2],[1,3]], s = 1
Output: [6, 6, -1]
Explanation: Node 2 is reachable via one edge (distance 6), node 3 same (distance 6), node 4 is not connected to node 1 at all (distance -1). Output order is for nodes 2, 3, 4 (all nodes except the start node, in ascending order).

Example 2:
Input: n = 3, edges = [[1,2],[2,3]], s = 1
Output: [6, 12]
Explanation: Node 2 is one edge away (6), node 3 is two edges away via node 2 (6 + 6 = 12).
 */


import java.util.*;

/**
 * n = 4, edges = [[1,2],[1,3]], s = 1
 * list<>[]:
 *
 */

public class P12ShortestReach {

    public static int[] bfs(int n,  int[][] edges, int s){

        List<Integer>[]  adj = new ArrayList[n +1];
        for (int i =1;  i<=n; i++) adj[i] = new ArrayList<>();

        for (int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            adj[u].add(v);
            adj[v].add(u);

        }

        // dist is the distance from s to node i, initialized to -1 (unreachable)
        int[] dist = new int[n+1];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        // distance from s to itself is 0
        dist[s] =0;


        while(!queue.isEmpty()){
            int currNode = queue.poll();

            for (int nei: adj[currNode]){
                if(dist[nei] == -1) {
                    // each edge has a length of 6
                    dist[nei] =dist[currNode] +6;
                    queue.offer(nei);
                }

            }

        }

        // do we have to  create a new list, can we just return the array?
        // we need to return the array in ascending order, excluding s itself
        // how below code guarantees that the order is ascending?
        // because we are iterating from 1 to n, and adding the distances to the result list in that order.
        // So the result list will be in ascending order of node numbers, excluding s itself.
        /* example:if n = 4 and s = 1, and dist = [-1, 0, 6, 6, -1],
        / then the result
        i =1 => skip
        i =2 => add 6
        i =3 => add 6
        i =4 => add -1
        result = [6, 6, -1]
        another example: if n = 5 and s = 3, and dist = [-1, 6, 12, 0, -1, 18],
        then the result
        i =1 => add 6
        i =2 => add 12
        i =3 => skip
        i =4 => add -1
        i =5 => add 18
        result = [6, 12, -1, 18]

        NOTE: the result list is not sorted, but it is in the order of node numbers,
        excluding s itself.
        The problem statement does not require the result to be sorted,
        only that it is in node-number order (ascending, excluding s itself).
        */


        List<Integer> result = new ArrayList<>();
/* can not use use list and stream?is there any better or easier way to do this? like int[] array
// yes, we can use an int[] array instead of a List<Integer> and then convert it to an int[] array at the end. But using a List<Integer> is more convenient because we don't know the size of the result array in advance (it will be n-1). Using a List allows us to dynamically add elements without worrying about the size. At the end, we can convert the List to an int[] array using stream().mapToInt(Integer::intValue).toArray().
I am trying not use stream, can we use a for loop to copy the elements from the List to an int[] array? Yes, we can do that. We can create an int[] array of size n-1 and then use a for loop to copy the elements from the List to the int[] array.
 Here is how we can do it:
    int[] resultArray = new int[n-1];
 for (int i = 0; i < result.size(); i++) {
     resultArray[i] = result.get(i);
 }
 return


        */
        for (int i =1; i<= n; i++){
            if(i != s){
                result.add(dist[i]);
            }
        }
        return result.stream().mapToInt(Integer:: intValue).toArray();

    }
}


