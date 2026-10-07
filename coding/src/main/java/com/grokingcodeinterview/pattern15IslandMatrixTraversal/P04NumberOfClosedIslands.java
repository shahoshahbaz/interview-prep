package com.grokingcodeinterview.pattern15IslandMatrixTraversal;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Problem Statement
You are given a 2D matrix containing only 1s (land) and 0s (water).
An island is a connected set of 1s (land) and is surrounded by either an edge or 0s (water).
 Each cell is considered connected to other cells horizontally or vertically (not diagonally).


   A closed island is an island that is totally surrounded by 0s (i.e., water).
    This means all horizontally and vertically connected cells of a closed island are water.
     This also means that, by definition, a closed island can't touch an edge (as then the edge cells are not connected to any water cell).
Write a function to find the number of closed islands in the given matrix.
Example 1: Input: matrix = [[1,1,1,1,1,1,1,0],
           [1,0,0,0,0,1,1,0],
           [1,0,1,0,1,1,1,0],
           [1,0,0,0,0,1,0,1],
           [1,1,1,1,1,1,1,0]] output = 2
Explanation: There are two closed islands, one is in the middle of the matrix and another one
is in the right bottom corner. Note that an island is closed only when there is no land connected to the edge of the matrix.
Example 2: Input: matrix = [[0,0,1,0,0],
           [0,1,0,1,0],t, by definition, a closed island can't touch an edge (as then the edge cell
           [0,1,1,1,0]] output = 1
Explanation: There is one closed island in the middle of the matrix.
Example 3: Input: matrix = [[1,1,1,1,1,1,1],
           [1,0,0,0,0,0,1],
           [1,0,1,1,1,0,1],
           [1,0,1,0,1,0,1],
           [1,0,1,1,1,0,1],
           [1,0,0,0,0,0,1],
           [1,1,1,1,1,1,1]] output = 2
Explanation: There are two closed islands, one is in the middle of the matrix and another one
is in the right bottom corner. Note that an island is closed only when there is no land connected to the edge of the matrix.
Constraints:
1 <= grid.length, grid[0].length <= 100
0 <= grid[i][j] <=1
 */
public class P04NumberOfClosedIslands {
    public static int  countClosedIslands(int[][] matrix){

        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new  boolean[m][n];
        int closedIslandCount =0;

        for (int r =0; r<m; r++){
            for(int c =0; c<n; c++){
                if(matrix[r][c] ==1 && !visited[r][c]){
                      if(dfs(matrix, visited, r, c)){
                          closedIslandCount++;
                      }

                }
            }
        }
     return closedIslandCount;
    }

    public static boolean dfs(int[][] matrix, boolean[][]visited, int r, int c){

        //If DFS ever tries to go outside the grid, that means:
        //ðŸ‘‰ the island is touching the boundary
        //ðŸ‘‰ therefore it is NOT closed
        if(r<0 || r>= matrix.length || c<0 || c>= matrix[0].length){
            return false;
        }
        // if we hit water or already visited land, we can consider this path as closed (for now)
        // why if already visited can be considered closed? it can be be water or land, but it doesn't matter
        // because if it is land, we have already explored it and marked it as visited and if it is water, we can consider it as closed island

        if(matrix[r][c] ==0 || visited[r][c]){
            return true;
        }
        visited[r][c] = true;

        boolean isClosed = true;
        int[][] directions = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};

        for (int[] dir: directions){
            int nr = r + dir[0];
            int nc = c+ dir[1];
            isClosed =  isClosed && dfs(matrix, visited, nr, nc);

        }

        return isClosed;

    }

    public static void main(String[] args){
        System.out.println("===========================================" );
        System.out.println("P04. Number of Closed Islands" );
        System.out.println("===========================================" );
 // Example 1 â†’ 1 closed island
        int[][] matrixP04 = {
                {0,0,0,0,0},
                {0,1,1,1,0},
                {0,1,0,1,0},
                {0,1,1,1,0},
                {0,0,0,0,0}
        };
        System.out.println("Input: " + Arrays.deepToString(matrixP04) + " Output: " + makeItBold(countClosedIslands(matrixP04) +" ")  +" Expected: 1" );
        // Example 2 â†’ 2 closed islands
       matrixP04 =  new int[][] {
                {0,0,0,0,0,0},
                {0,1,1,0,1,0},
                {0,1,1,0,1,0},
                {0,0,0,0,0,0}
        };
        System.out.println("Input: " + Arrays.deepToString(matrixP04) + " Output: " + makeItBold(countClosedIslands(matrixP04) +" ")  +" Expected: 2" );
        // Example 3 â†’ 0 closed islands (touches edge)
        matrixP04 =  new int[][] {
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        System.out.println("Input: " + Arrays.deepToString(matrixP04) + " Output: " + makeItBold(countClosedIslands(matrixP04) +" ")  +" Expected: 0" );
    }
}

