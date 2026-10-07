package com.grokingcodeinterview.pattern37realInterviewProblems;

/*
 * Given an n x n binary matrix grid, return the length of the shortest clear path in the matrix. If there is no clear path, return -1.
 * A clear path in a binary matrix is a path from the top-left cell (i.e., (0, 0)) to the bottom-right cell (i.e., (n - 1, n - 1)) such that:
 * All the visited cells of the path are 0.
 * All the adjacent cells of the path are 8-directionally connected (i.e., they are different and they share an edge or a corner).
 * The length of a clear path is the number of visited cells of this path.
 *
 * Example: Input: grid = [[0,1],[1,0]] Output: 2
 * Example: Input: grid = [[0,0,0],[1,1,0],[1,1,0]] Output: 4
 * Example: Input: grid = [[1,0,0],[1,1,0],[1,1,0]] Output: -1
 *
 */

import java.util.ArrayDeque;

public class LC1091 {

    public static  int shortestPathBinaryMatrix(int[][] grid) {
        if (grid == null ) return -1;
        if (grid[0][0] ==1) return -1;
        int n = grid.length;
        int m =  grid[0].length;

        boolean[][] visited = new boolean[n][m];

        ArrayDeque<int[]> queue = new ArrayDeque<>();

        queue.offer(new int[]{0, 0, 1});
        visited[0][0] = true;


        int[][] neighbors = {{0, 1},{0, -1}, {1, 0}, {-1, 0}, {-1, -1}, {1, -1}, {-1, 1}, {1,1}};


        while (!queue.isEmpty()){
            int[] element = queue.poll();
            int r = element[0];
            int c = element[1];
            int dis = element[2];

            if (r == n-1 && c == m -1 ) return dis;




            for (int[] nei: neighbors){
                int nRow = nei[0] + r;
                int nCol = nei[1] + c;


                if (isValid(nRow, nCol, grid) && !visited[nRow][nCol]){
                    visited[nRow][nCol] = true;

                    queue.offer(new int[]{nRow, nCol,dis + 1 });
                }

            }




        }

        return -1;

    }

    public static boolean isValid(int nRow,  int nCol, int [][] grid ){

         return ( nRow >=0 && nRow < grid.length && nCol >= 0  && nCol< grid[0].length  && grid[nRow][nCol] == 0  );

    }

    public static void main(String[] args) {
        System.out.println("================================================");
        System.out.println(" LC1091: Shortest Path in Binary Matrix");
        System.out.println("================================================");

        int[][] gridLC1091 = {{0, 1}, {1, 0}};
        System.out.println("Input: grid = [[0,1],[1,0]] Output: " + shortestPathBinaryMatrix(gridLC1091) + " Expected Output: 2");
        gridLC1091 = new int[][]{{0, 0, 0}, {1, 1, 0}, {1, 1, 0}};
        System.out.println("Input: grid = [[0,0,0],[1,1,0],[1,1,0]] Output: " + shortestPathBinaryMatrix(gridLC1091) + " Expected Output: 4");
        gridLC1091 = new int[][]{{1, 0, 0}, {1, 1, 0}, {1, 1, 0}};
        System.out.println("Input: grid = [[1,0,0],[1,1,0],[1,1,0]] Output: " + shortestPathBinaryMatrix(gridLC1091) + " Expected Output: -1");
        gridLC1091 = new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 1, 0}};
        System.out.println("Input: grid = [[0,0,0],[0,1,0],[0,1,0]] Output: " + shortestPathBinaryMatrix(gridLC1091) + " Expected Output: 4 ");
    }

}
