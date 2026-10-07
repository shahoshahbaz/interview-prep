package com.grokingcodeinterview.pattern28TopologicalSort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
Problem Statement
There are â€˜Nâ€™ tasks, labeled from â€˜0â€™ to â€˜N-1â€™. Each task can have some prerequisite tasks which need to be completed before it can be scheduled.

Given the number of tasks and a list of prerequisite pairs, write a method to find the ordering of tasks we should pick to finish all tasks.
 */
public class P05TasksSchedulingOrder {
    public List<Integer> findOrder(int tasks, int[][] prerequisites) {
        List<Integer> result = new ArrayList<>();
        List<Integer>[] graph = new ArrayList[tasks];
        int[] indegree= new int[tasks];
        for(int i =0; i< tasks; i++){
            graph[i]= new ArrayList<>();

        }

        for(int[] preq: prerequisites){
            int u = preq[0];
            int v = preq[1];

            graph[u].add(v);
            indegree[v]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        for(int i =0; i< tasks; i++){
            if(indegree[i] ==0)
                queue.offer(i);
        }

        while(!queue.isEmpty()){
            int node = queue.poll();
            result.add(node);

            for (int nei: graph[node]){
                indegree[nei] --;
                if(indegree[nei] ==0){
                    queue.offer(nei);
                }
            }
        }

        if(result.size() != tasks) return new ArrayList<>();


        return result;
    }
}

