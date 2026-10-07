package com.grokingcodeinterview.pattern15IslandMatrixTraversal;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a 2D array (i.e., a matrix) containing only 1s (land) and 0s (water), find the biggest island in it. Write a function to return the area of the biggest island.
An island is a connected set of 1s (land) and is surrounded by either an edge or 0s (water). Each cell is considered connected to other cells horizontally or vertically (not diagonally).
Example 1: Input: matrix = [[1, 1, 0, 0, 0],
           [1, 1, 0, 1, 1],
           [0, 0, 0, 1, 0],
           [1, 0, 0, 0, 0],
           [1, 1, 1, 0, 1]] output: 5

Explanation: The matrix has three islands. The biggest island has 5 cells .
Example 2: Input: matrix = [[1, 1, 1],
           [1, 1, 1],
           [1, 1, 1]] output: 9
Explanation: The matrix has one big island with 9 cells.
Example 3: Input: matrix = [[0, 0, 0],
              [0, 0, 0]] output: 0
Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 50
matrix[i][j] is '0' or '1'.
 */
public class P02BiggestIsland {
    public static int maxAreaOfIsland(int[][] matrix) {

        if (matrix == null || matrix.length ==0 ) return 0;

        int biggestIsland = 0;
        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean[][] visited = new boolean[rows][cols];

        for (int r = 0; r<rows; r++){
            for(int c =0; c<cols; c++){
                if (matrix[r][c] ==1 && !visited[r][c]){
                    biggestIsland = Math.max(biggestIsland, findSizeOfIsland(matrix,visited, r, c));
                }
            }
        }
        return biggestIsland;
    }

    private static int findSizeOfIsland(int[][] matrix, boolean [][] visited, int sr, int sc){
        int rows = matrix.length;
        int cols = matrix[0].length;
        if (sr<0 || sr >=  rows || sc<0 || sc>= cols || matrix[sr][sc] == 0 || visited[sr][sc]){
            return 0;
        }
        visited[sr][sc] = true;

        int[][] directions = {{1, 0},{-1, 0}, {0, 1}, {0, -1}};
        int counter =1;
        for (int[] direction: directions){
            int nr = sr + direction[0];
            int nc = sc + direction[1];

            counter += findSizeOfIsland(matrix, visited, nr, nc);


        }
        return counter;
    }

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("P02. Biggest Island");
        System.out.println("=================================");


        int[][] matrixP02 = {
                {1,1,0,0,0},
                {1,1,0,1,1},
                {0,0,0,1,0},
                {1,0,0,0,0},
                {1,1,1,0,1}
        }; // Largest island area = 4
        System.out.println("Input: matrixP02 = " + Arrays.deepToString(matrixP02) + " Output: " + makeItBold(maxAreaOfIsland(matrixP02) +"") + " Expected Output: 4");
         matrixP02 = new int[][] {
                {1,1,1},
                {1,1,1},
                {1,1,1}
        }; // 9

        System.out.println("Input: matrixP02 = " + Arrays.deepToString(matrixP02) + " Output: " + makeItBold(maxAreaOfIsland(matrixP02) +"") + " Expected Output: 9");
         matrixP02 = new int[][] {
                {0,0,0},
                {0,0,0}
        }; // 0
        System.out.println("Input: matrixP02 = " + Arrays.deepToString(matrixP02) + " Output: " + makeItBold(maxAreaOfIsland(matrixP02) +"") + " Expected Output: 0");
         matrixP02 =  new int[][]{
                {1,0,1,0},
                {0,1,0,1},
                {1,0,1,0}
        }; // largest single-cell islands -> 1
        System.out.println("Input: matrixP02 = " + Arrays.deepToString(matrixP02) + " Output: " + makeItBold(maxAreaOfIsland(matrixP02) +"") + " Expected Output: 1");
    matrixP02 = new int[][] {
                {1}
        }; // 1
        System.out.println("Input: matrixP02 = " + Arrays.deepToString(matrixP02) + " Output: " + makeItBold(maxAreaOfIsland(matrixP02) +"") + " Expected Output: 1");

        matrixP02 = new int[][] {
                {1,1,0,0,1},
                {1,0,0,1,1},
                {0,0,1,0,0},
                {1,1,0,0,1},
                {1,0,0,1,1}
        }; // largest island area = 4
        System.out.println("Input: matrixP02 = " + Arrays.deepToString(matrixP02) + " Output: " + makeItBold(maxAreaOfIsland(matrixP02) +"") + " Expected Output: 3");



    }
}

