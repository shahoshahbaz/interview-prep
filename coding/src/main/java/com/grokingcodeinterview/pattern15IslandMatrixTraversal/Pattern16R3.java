package com.grokingcodeinterview.pattern15IslandMatrixTraversal;

import java.util.Arrays;

import static com.Utility.makeItBold;

public class Pattern16R3 {
    /*
Problem Statement
Given a 2D array (i.e., a matrix) containing only 1s (land) and 0s (water), count the number of islands in it.
An island is a connected set of 1s (land) and is surrounded by either an edge or 0s (water).
Each cell is considered connected to other cells horizontally or vertically (not diagonally).
Example : Input: matrix = [[1, 1, 0, 0, 0],
                           [0, 1, 0, 0, 1],
                           [1, 0, 0, 1, 1],
                           [0, 0, 0, 0, 0],
                           [1, 0, 1, 0, 1]] output: 6
example 2: Input: matrix = [[1, 1, 1, 1, 0],
        [1, 1, 0, 1, 0],
        [1, 1, 0, 0, 0],
        [0, 0, 0, 0, 0]] output: 1
example 3: Input: matrix = [[1, 0, 1, 0, 1],
        [0, 0, 0, 0, 0],
        [1, 0, 1, 0, 1]] output: 6
Constraints:
        1 <= matrix.length, matrix[0].length <= 100
matrix[i][j] is either 0 or 1.
*/
    public static int countIslands(int[][] matrix ){
        int n = matrix.length;
        int m = matrix[0].length;

        boolean[][] visited = new boolean[n][m];

        int count =0;
        for (int r =0; r< n; r++){
            for(int c =0 ; c< m; c++ ){
                if (matrix[r][c] ==1 &&  !visited[r][c]  ){
                    dfs(matrix, r, c, visited);
                    count++;
                }
            }
        }

        return count;

    }

    public static boolean dfs( int[][] matrix, int r, int c , boolean[][] visited){

        if (r< 0 || r>= matrix.length || c<0 || c>= matrix[r].length || visited[r][c] || matrix[r][c] ==0 ){
            return false;
        }
        visited[r][c] = true;
        int [][] directions = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};

        for(int[] dir: directions){
            int nr = r + dir[0];
            int nc = c + dir[1];
            dfs(matrix, nr, nc, visited);
        }

        return true;

    }
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("P01. Number of Island");
        System.out.println("=================================");
        int[][] matrixP01 = {
                {1, 1, 0, 0, 0},
                {0, 1, 0, 0, 1},
                {1, 0, 0, 1, 1},
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1}
        };
        System.out.println("Input: matrixP01 = " + Arrays.deepToString(matrixP01) + " Output: " + makeItBold(countIslands(matrixP01) +"") + " Expected Output: 6");

        matrixP01 =  new int[][] {
                {1, 1, 1, 1, 0},
                {1, 1, 0, 1, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        System.out.println("Input: matrixP01 = " + Arrays.deepToString(matrixP01) + " Output: " + makeItBold(countIslands(matrixP01) +"") + " Expected Output: 1");
        matrixP01 =  new int[][]  {
                {1, 0, 1, 0, 1},
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1}
        };
        System.out.println("Input: matrixP01 = " + Arrays.deepToString(matrixP01) + " Output: " + makeItBold(countIslands(matrixP01) +"") + " Expected Output: 6");

    }
}

