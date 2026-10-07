package com.grokingcodeinterview.pattern15IslandMatrixTraversal;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a 2D array (i.e., a matrix) containing only 1s (land) and 0s (water),
count the number of islands in it.
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
public class P01NumberOfIsland {

    public static int countIslands(int[][] matrix) {
        int count =0;
        for(int i =0; i< matrix.length; i++){
            for(int j =0; j< matrix[0].length; j++){
                if(matrix[i][j] == 1){
                    count++;
                    dfs(matrix, i, j);
                }
            }
       }
        return count;
    }

    public static void dfs(int[][] matrix, int row, int col){
        if(row <0 || row>= matrix.length || col < 0 || col>=  matrix[0].length || matrix[row][col]== 0){
            return;
        }
        matrix[row][col] =0; // mark as visited

        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        for(int[] direction: directions){
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            dfs(matrix, newRow, newCol);
        }
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




