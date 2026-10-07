package com.grokingcodeinterview.pattern37realInterviewProblems;

import java.util.ArrayList;
import java.util.List;

/*
There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return true if you can finish all courses. Otherwise, return false.
example 1: Input: numCourses = 2, prerequisites = [[1,0]] Output: true Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So it is possible.
example 2: Input: numCourses = 2, prerequisites = [[1,0],[0,1]] Output: false Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0, and to take course 0 you
e should also have finished course 1. So it is impossible.
example 3: Input: numCourses = 3, prerequisites = [[1,0],[2,1]] Output: true Explanation: There are a total of 3 courses to take. To take course 1 you should have finished course 0, and to take course 2 you should have finished course 1. So it is possible.

 */
public class L207 {

    public  static boolean canFinish(int numCourses, int[][] prerequisites) {

        List<List<Integer>> adj = new ArrayList<>();

        for(int i =0; i< numCourses; i++)
            adj.add(new ArrayList<>());

        for(int[] pre: prerequisites){
            adj.get(pre[0]).add(pre[1]);
        }

        int[] visited = new int[numCourses]; // 0 white, 1: grey, 2: black

        for (int i =0; i<numCourses; i++){
            if(visited[i] ==0){
                if(hasCycle(i, adj, visited)) return false;
            }
        }

        return true;

    }

    public static boolean hasCycle(int i , List<List<Integer>> adj, int[] visited){
        visited[i] = 1;
        List<Integer> neighbors = adj.get(i);
        for (int nei: neighbors){
            if(visited[nei] ==1) return true;
            if (visited[nei] == 2) continue;
            if(hasCycle(nei, adj, visited)) return true;
        }

        visited[i] = 2;

        return false;


    }


    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("L207: Course Schedule");
        System.out.println("===========================================");

        int numCourses = 2;
        int[][] prerequisites = {{1,0}};
        System.out.println("Input: numCourses = " + numCourses + ", prerequisites = [[1,0]] Output: " + canFinish(numCourses, prerequisites) + " Expected Output: true");
    }


}
