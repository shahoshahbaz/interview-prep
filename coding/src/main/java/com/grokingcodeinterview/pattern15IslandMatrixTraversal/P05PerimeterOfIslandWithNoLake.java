package com.grokingcodeinterview.pattern15IslandMatrixTraversal;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Problem Statement
You are given a 2D matrix containing only 1s (land) and 0s (water).
An island is a connected set of 1s (land) and is surrounded by either an edge or 0s (water).
 Each cell is considered connected to other cells horizontally or vertically (not diagonally).
There are no lakes on the island, so the water inside the island is not connected to
 the surrounding water. A cell is a square with a side length of 1.
The given matrix has only one island, write a function to find the perimeter of that island.
example: Input: matrix = [[0,1,0,0],
                          [1,1,1,0],
                          [0,1,0,0],
                          [1,1,0,0]] output = 16
explanation: The perimeter is the 16 .
example: Input: matrix = [[1]] output = 4
example: Input: matrix = [[1,0]] output = 4
Constraints:
1 <= grid.length, grid[0].length <= 100
0 <= grid[i][j] <=1
 */
public class P05PerimeterOfIslandWithNoLake {

    public static int findIslandPerimeter(int[][] matrix){
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];

        for (int r =0; r<matrix.length; r++){
            for(int c =0; c< matrix[0].length; c++){
                if(matrix[r][c] ==1 && !visited[r][c]){
                    return dfs(matrix, visited, r, c);
                }
            }
        }
        return 0;

    }

    public static int dfs(int[][] matrix, boolean[][] visited, int r, int c){
        // if we are out of bounds or we are on water, then we have found an edge
        if(r<0 || r>= matrix.length || c<0 || c>= matrix[0].length){
            return 1;
        }
        // if we are on water, then we have found an edge
        if ( matrix[r][c] ==0){
            return 1;
        }
        // if we are here, it means we are on land and within bounds
        if(visited[r][c])
            return 0;
        // mark the cell as visited, and it means we are on land and within bounds, so we check for the 4 directions
        visited[r][c] = true;
        int[][] directions = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
        int edgeCount =0;
        for (int[] dir: directions){
            int nr = r + dir[0];
            int nc = c + dir[1];
            edgeCount += dfs(matrix, visited, nr, nc);

        }
        return edgeCount;
    }
    public static void main(String[] args) {
        System.out.println("===================================================================");
        System.out.println("P05. Perimeter of Island with No Lake");
        System.out.println("===================================================================");

        int[][] matrixP05 = {{0,1,0,0},
                          {1,1,1,0},
                          {0,1,0,0},
                          {1,1,0,0}};
        int perimeterP05   = findIslandPerimeter(matrixP05);
        System.out.println("Input: " + Arrays.deepToString(matrixP05) + " output: " + makeItBold(perimeterP05 +"") + " Expected: 16" );
        matrixP05 = new int[][] {{1}};
        perimeterP05   = findIslandPerimeter(matrixP05);
        System.out.println("Input: " + Arrays.deepToString(matrixP05) + " output: " + makeItBold(perimeterP05 +"") + " Expected: 4" );
        matrixP05 = new int[][] {{1,0}};
        perimeterP05   = findIslandPerimeter(matrixP05);
        System.out.println("Input: " + Arrays.deepToString(matrixP05) + " output: " + makeItBold(perimeterP05 +"") + " Expected: 4" );
        matrixP05 = new int[][] {{1,1,1},
                                  {1,1,1},
                                  {1,1,1}};
        perimeterP05   = findIslandPerimeter(matrixP05);
        System.out.println("Input: " + Arrays.deepToString(matrixP05) + " output: " + makeItBold(perimeterP05 +"") + " Expected: 12" );
    }

}

