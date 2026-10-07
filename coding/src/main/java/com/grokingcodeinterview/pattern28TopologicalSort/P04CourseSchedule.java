package com.grokingcodeinterview.pattern28TopologicalSort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.Utility.makeItBold;

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
public class P04CourseSchedule  {
    public static boolean canFinish(int courses, int[][] prerequisites){
        List<Integer>[] graph = new ArrayList[courses];
        int[] indegree = new int[courses];

        for(int i=0; i<courses; i++){
            graph[i] = new ArrayList<>();
        }
        // build graph
        for (int[] prerequisite: prerequisites){
            int u = prerequisite[0];
            int v = prerequisite[1];
            graph[u].add(v);

            indegree[v]++;

        }

        Queue<Integer> queue = new LinkedList<>();

        for(int i =0; i<courses; i++){
            if(indegree[i] == 0){
                queue.offer(i);
            }
        }

        // processor level by level
        List<Integer> result = new ArrayList<>();

        while (!queue.isEmpty()){
            int node = queue.poll();
            result.add(node);

            // check the neighbors
            for(int nei: graph[node]){
                indegree[nei] --;
                if(indegree[nei] == 0){
                    queue.offer(nei);
                }
            }
        }

        return result.size() == courses;
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
    }
}

