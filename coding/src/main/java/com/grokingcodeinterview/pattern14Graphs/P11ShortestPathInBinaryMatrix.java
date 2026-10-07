package com.grokingcodeinterview.pattern14Graphs;


import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import static com.Utility.makeItBold;

/*
Given an n x n binary matrix grid, return the length of the shortest clear path from top-left (0,0) to bottom-right (n-1,n-1). If no clear path exists, return -1.

A clear path:

Consists only of cells with value 0 (both start and end cells must be 0, including if n == 1).
Consecutive cells in the path are connected 8-directionally (horizontally, vertically, or diagonally adjacent).
The length of a clear path is the number of visited cells (including start and end).

Example 1:
Input: grid = [[0,1],[1,0]]
Output: 2
Explanation: Path is (0,0) â†’ (1,1), moving diagonally. Length = 2 cells.

Example 2:
Input: grid = [[0,0,0],[1,1,0],[1,1,0]]
Output: 4
Explanation: Path is (0,0) â†’ (0,1) â†’ (0,2) â†’ (1,2) â†’ (2,2)... wait, that's 5 â€” actual shortest: (0,0) â†’ (0,1) â†’ (1,2) â†’ (2,2) using diagonal moves, length 4.

Example 3:
Input: grid = [[1,0,0],[1,1,0],[1,1,0]]
Output: -1
Explanation: Start cell (0,0) is blocked (grid[0][0] == 1), so no path can exist.
 */
public class P11ShortestPathInBinaryMatrix {
    public int shortestPathBinaryMatrix(int[][] grid){
        int n = grid.length;

        if (grid[0][0] ==1 ||grid[n-1][n -1] ==1) return -1;

        int[][] dirs ={{0, 1},{0, -1},{1, 0},{-1, 0},{1, 1},{1, -1},{-1, 1},{-1, -1} };

        int[][] dist = new int[n][n];
        for(int[] row: dist) Arrays.fill(row, -1);

        Queue<int[] > queue = new ArrayDeque<>();

        dist[0][0] = 1;
        queue.offer(new int[]{0, 0});

        while(!queue.isEmpty()){
            int [] curr = queue.poll();
            int r =curr[0]; int c = curr[1];

            if(r == n-1 && c == n-1) return dist[r][c];
            for(int[] d :dirs){
                int nr = r + d[0];
                int nc = c +d[1];

                if(nr>=0 && nr< n && nc>=0 && nc< n && grid[nr][nc] ==0  && dist[nr][nc] ==-1){
                    dist[nr][nc]= dist[r][c] +1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return -1;

    }

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("P11. Shortest Path in Binary Matrix");
        System.out.println("==========================================");

        P11ShortestPathInBinaryMatrix solver = new P11ShortestPathInBinaryMatrix();

        // Test Case 1: Simple path with diagonal move
        int[][] grid1 = {{0, 1}, {1, 0}};
        System.out.println("Input: " + Arrays.deepToString(grid1) + ", output: " + makeItBold(solver.shortestPathBinaryMatrix(grid1) + "") + ", Expected Output: 2");

        // Test Case 2: Path with multiple moves
        int[][] grid2 = {{0, 0, 0}, {1, 1, 0}, {1, 1, 0}};
        System.out.println("Input: " + Arrays.deepToString(grid2) + ", output: " + makeItBold(solver.shortestPathBinaryMatrix(grid2) + "") + ", Expected Output: 4");

        // Test Case 3: Blocked start cell
        int[][] grid3 = {{1, 0, 0}, {1, 1, 0}, {1, 1, 0}};
        System.out.println("Input: " + Arrays.deepToString(grid3) + ", output: " + makeItBold(solver.shortestPathBinaryMatrix(grid3) + "") + ", Expected Output: -1");

        // Test Case 4: Single cell (0,0)
        int[][] grid4 = {{0}};
        System.out.println("Input: " + Arrays.deepToString(grid4) + ", output: " + makeItBold(solver.shortestPathBinaryMatrix(grid4) + "") + ", Expected Output: 1");

        // Test Case 5: No path available
        int[][] grid5 = {{0, 0, 1}, {0, 1, 0}, {1, 1, 0}};
        System.out.println("Input: " + Arrays.deepToString(grid5) + ", output: " + makeItBold(solver.shortestPathBinaryMatrix(grid5) + "") + ", Expected Output: -1");
    }
}

