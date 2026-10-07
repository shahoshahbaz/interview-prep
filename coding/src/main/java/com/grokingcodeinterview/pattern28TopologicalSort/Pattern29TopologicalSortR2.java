package com.grokingcodeinterview.pattern28TopologicalSort;

import java.util.*;

import static com.Utility.makeItBold;

public class Pattern29TopologicalSortR2 {
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
    public static boolean isSchedulingPossible(int n, int[][] prerequisites){

        List<Integer>[] graph = new ArrayList[n];
        int[] indegree = new int[n];
        for(int i =0; i< n; i++){
            graph[i] = new ArrayList<>();
        }
        for(int [] pre: prerequisites){
            int u = pre[0];
            int v = pre[1];
            graph[u].add(v);
            indegree[v]++;
        }

        List<Integer> result = new ArrayList<>();

        Queue<Integer> queue = new LinkedList<>();

        for (int i =0; i< n; i++){
            if(indegree[i] ==0){
                queue.offer(i);
            }
        }

        while(!queue.isEmpty()){
            int node = queue.poll();
            result.add(node);

            for (int nie: graph[node]){
                indegree[nie] --;
                if(indegree[nie] ==0){
                    queue.offer(nie);
                }
            }
        }


        return result.size() == n;


    }
    /*
Problem Statement
Topological Sort of a directed graph (a graph with unidirectional edges) is a linear ordering of its vertices such that
 for every directed edge (U, V) from vertex U to vertex V, U comes before V in the ordering.
Given a directed graph, find the topological ordering of its vertices. If the graph is cyclic, return an empty array.

Example 1 :Input: Vertices=7, Edges=[6, 4], [6, 2], [5, 3], [5, 4], [3, 0], [3, 1], [3, 2], [4, 1]
Output: Following are all valid topological sorts for the given graph:
1) 5, 6, 3, 4, 0, 1, 2
2) 6, 5, 3, 4, 0, 1, 2
3) 5, 6, 4, 3, 0, 2, 1
4) 6, 5, 4, 3, 0, 1, 2
5) 5, 6, 3, 4, 0, 2, 1
6) 5, 6, 3, 4, 1, 2, 0

There are other valid topological ordering of the graph too.

Example 2: Input: Vertices=4, Edges=[3, 2], [3, 0], [2, 0], [2, 1]
Output: Following are the two valid topological sorts for the given graph:
1) 3, 2, 0, 1
2) 3, 2, 1, 0

Example 3: Input: Vertices=5, Edges=[4, 2], [4, 3], [2, 0], [2, 1], [3, 1]
Output: Following are all valid topological sorts for the given graph:
1) 4, 2, 3, 0, 1
2) 4, 3, 2, 0, 1
3) 4, 3, 2, 1, 0
4) 4, 2, 3, 1, 0
5) 4, 2, 0, 3, 1
 */
    public static List<Integer> topologicalSortBFS(int n , int[][] edges){
      List<List<Integer>> adj = new ArrayList<>();
      int[] indegree = new int[n];

      for(int i =0; i< n; i++){
          adj.add(new ArrayList<>());

      }

      for (int[] edge: edges){
          int u = edge[0];
          int v = edge[1];
          adj.get(u).add(v);
          indegree[v]++;

      }

      Deque<Integer> queue = new ArrayDeque<>();

      for (int i =0; i< n;i++){
          if (indegree[i] ==0) queue.offer(i);
      }

      List<Integer > result = new ArrayList<>();

      while(!queue.isEmpty()){
          int node = queue.poll();
          result.add(node);

          for(int nei: adj.get(node)){
              indegree[nei]--;

              if(indegree[nei] ==0){
                  queue.offer(nei);
              }
          }


      }

      if(result.size() != n) return new ArrayList<>();
      return result;



    }
    /*
    There are â€˜Nâ€™ courses, labeled from â€˜0â€™ to â€˜N-1â€™.
    Each course can have some prerequisite courses which need to be completed before it can be taken.
    Given the number of courses and a list of prerequisite pairs,
    find if it is possible for a student to take all the courses.
    Example 1: Input: numCourses=2, prerequisites=[[1, 0]] Output: true
    Explanation: There are a total of 2 courses to take. To take course 1 you need to finish course 0. So it is possible.
    Example 2: Input: numCourses=2, prerequisites=[[1, 0], [0, 1]] Output: false
    Explanation: There are a total of 2 courses to take. To take course 1 you need to finish course 0, and to take course 0 you need to finish course 1. So it is impossible.
    Example 3: Input: numCourses=4, prerequisites=[[0, 1], [0, 2], [1, 3], [2, 3]] Output: true
    Explanation: There are a total of 4 courses to take. To take course 0 you need to finish both courses 1 and 2. Both courses 1 and 2 require you
    to finish course 3. So it is possible.
     */
    public static boolean canFinish(int n, int[][] prerequisites){

        List<Integer>[] graph = new ArrayList[n];
        int[] indegree = new int[n];
        List<Integer> result = new ArrayList<>();
        for(int i =0; i<n; i++)
            graph[i ] = new ArrayList<>();

        for(int[] pre: prerequisites){
            int u = pre[0];
            int v = pre[1];
            graph[u].add(v);
            indegree[v]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        for(int i =0; i< n; i++){
            if(indegree[i] ==0){
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()){
            int node = queue.poll();
            result.add(node);

            for(int nei: graph[node]){
                indegree[nei] --;
                if(indegree[nei] ==0){
                    queue.offer(nei);
                }
            }
        }

        return result.size() == n;




    }
    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("P04. Course Schedule");
        System.out.println("===========================");
        int numCoursesP04 = 2;
        int[][] prerequisitesP04 = {{1, 0}};
        System.out.println("Input: numCourses = " + numCoursesP04 + ", prerequisites = [[1, 0]]   Output: " + makeItBold(canFinish(numCoursesP04, prerequisitesP04)+" ") + "   Expected: true");

        numCoursesP04 = 2;
        prerequisitesP04 = new int[][]{{1, 0}, {0, 1}};
        System.out.println("Input: numCourses = " + numCoursesP04 + ", prerequisites = [[1, 0], [0, 1]]   Output: " + makeItBold(canFinish(numCoursesP04, prerequisitesP04)+" ") + "   Expected: false");

        numCoursesP04 = 4;
        prerequisitesP04 = new int[][]{{0, 1}, {0, 2}, {1, 3}, {2, 3}};
        System.out.println("Input: numCourses = " + numCoursesP04 + ", prerequisites = [[0, 1], [0, 2], [1, 3], [2, 3]]   Output: " + makeItBold(canFinish(numCoursesP04, prerequisitesP04)+" ") + "   Expected: true");
        System.out.println("===========================");
        System.out.println("P01. Topological Sort");
        System.out.println("===========================");
        int nP01 = 7;
        int[][] edgesP01 = {{6, 4}, {6, 2}, {5, 3}, {5, 4}, {3, 0}, {3, 1}, {3, 2}, {4, 1}};
        System.out.println("Input: Vertices= " + nP01 + ", Edges= " + Arrays.deepToString(edgesP01)
                + ", Output: " + topologicalSortBFS(nP01, edgesP01) +
                ", Expected Output: [5, 6, 3, 4, 0, 1, 2] or [6, 5, 3, 4, 0, 1, 2] + or [5, 6, 4, 3, 0, 2, 1] or [6, 5, 4, 3, 0, 1, 2] or [5, 6, 3, 4, 0, 2, 1] or [5, 6, 3, 4, 1, 2, 0]");
        nP01 = 4;
        int[][] edgesP01_2 = {{3, 2}, {3, 0}, {2, 0}, {2, 1}};
        System.out.println("Input: Vertices= " + nP01 + ", Edges= " + Arrays.deepToString(edgesP01_2)
                + ", Output: " + topologicalSortBFS(nP01, edgesP01_2) +
                ", Expected Output: [3, 2, 0, 1] or [3, 2, 1, 0]");
        nP01 = 5;
        int[][] edgesP01_3 = {{4, 2}, {4, 3}, {2, 0}, {2, 1}, {3, 1}};
        System.out.println("Input: Vertices= " + nP01 + ", Edges= " + Arrays.deepToString(edgesP01_3)
                + ", Output: " + topologicalSortBFS(nP01, edgesP01_3) +
                ", Expected Output: [4, 2, 3, 0, 1] or [4, 3, 2, 0, 1] or [4, 3, 2, 1, 0] or [4, 2, 3, 1, 0] or [4, 2, 0, 3, 1]");
        System.out.println("===========================");
        System.out.println("P03.Tasks Scheduling");
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

