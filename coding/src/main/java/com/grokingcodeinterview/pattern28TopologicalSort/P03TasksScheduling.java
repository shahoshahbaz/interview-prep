package com.grokingcodeinterview.pattern28TopologicalSort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.Utility.makeItBold;

/*
Problem Statement
There are â€˜Nâ€™ tasks, labeled from â€˜0â€™ to â€˜N-1â€™.
Each task can have some prerequisite tasks which need to be completed before it can be scheduled.
Given the number of tasks and a list of prerequisite pairs, find out if it is possible to schedule all the tasks.
Example 1: Input: Tasks=6, Prerequisites=[2, 5], [0, 5], [0, 4], [1, 4], [3, 2], [1, 3] Output: true
Explanation: A possible scheduling of tasks is: [0 1 4 3 2 5]
Example 2: Input: Tasks=3, Prerequisites=[0, 1], [1, 2] Output: true
Explanation: To execute task '1', task '0' needs to finish first. Similarly, task '1' needs to finish before '2' can be scheduled. One possible scheduling of tasks is: [0, 1, 2]
Example 3: Input: Tasks=3, Prerequisites=[0, 1], [1, 2], [2, 0] Output: false
Explanation: The tasks have a cyclic dependency, therefore they cannot be scheduled.
 */
public class P03TasksScheduling {
    public static boolean isSchedulingPossible(int n, int[][] prerequisites){
        List<Integer>[] graph = new ArrayList[n];
        int[] indegree = new int[n];

        for(int i =0; i< n; i++){
            graph[i] = new ArrayList<>();
        }
        // build graph
        for (int[] edge: prerequisites){
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);

            indegree[v]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int i =0 ;i< n; i++){
            if(indegree[i] == 0){
                queue.offer(i);
            }
        }

        // process level by level
        List<Integer> result = new ArrayList<>();

        while(!queue.isEmpty()){
            int node = queue.poll();
            result.add(node);

            // check the neighbor of nodes
            for(int nei: graph[node]){
                indegree[nei]--;
                if(indegree[nei] == 0)
                    queue.offer(nei);
            }
        }

        return result.size() == n;

    }
    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("Tasks Scheduling");
        System.out.println("===========================");
        int nP03 = 6;
        int[][] edgesP03 = {{5,2}, {5,0}, {4,0}, {4,1}, {2,3}, {3,1}};
        System.out.println("Input: Tasks=6, Prerequisites=[2, 5], [0, 5], [0, 4], [1, 4], [3, 2], [1, 3]   Output: " + makeItBold(isSchedulingPossible(nP03, edgesP03)+" ") + "   Expected: true");

        nP03 = 3;
        edgesP03 = new int[][]{{0, 1}, {1, 2}};
        System.out.println("Input: Tasks=3, Prerequisites=[0, 1], [1, 2]   Output: " + makeItBold(isSchedulingPossible(nP03, edgesP03)+" ") + "   Expected: true");

        nP03 = 3;
        edgesP03 = new int[][]{{0, 1}, {1, 2}, {2, 0}};
        System.out.println("Input: Tasks=3, Prerequisites=[0, 1], [1, 2], [2, 0]   Output: " + makeItBold(isSchedulingPossible(nP03, edgesP03)+" ") + "   Expected: false");

    }
}

