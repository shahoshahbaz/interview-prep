package com.grokingcodeinterview.pattern15IslandMatrixTraversal;

import java.util.*;

import static com.Utility.makeItBold;

/*
You are given a 2D matrix containing only 1s (land) and 0s (water).

An island is a connected set of 1s (land) and is surrounded by either an edge or 0s (water). Each cell is considered connected to other cells horizontally or vertically (not diagonally).

Two islands are considered the same if and only if they can be translated (not rotated or reflected) to equal each other.

Write a function to find the number of distinct islands in the given matrix.

Example 1:Input: matrix = [[1,1,0,0,0],
                 [1,1,0,0,0],
                 [0,0,0,1,1],
                 [0,0,0,1,1]], output = 1
Explanation: There are two islands in the given matrix, but they are considered the same because one can be translated (not rotated or reflected) to equal the other.
Example 2:Input: matrix = [[1,1,0,1,1],
                 [1,0,0,0,0],
                 [0,0,0,0,1],
                 [1,1,0,1,1]], output = 3
Explanation: There are four islands in the given matrix, but there are only three distinct islands because the first and the fourth islands are considered the same. The second and the third islands are considered different because one cannot be translated (not rotated or reflected) to equal the other.
Example 3:Input: matrix = [[1,1,1,0,0],
                 [1,0,0,0,0],
                 [1,1,0,1,1],
                 [0,0,0,1,1]], output = 2
Explanation: There are three islands in the given matrix, but there are only two distinct islands because
Constraints:
 */

/**
 * LEFT:L
 * RIGHT: R
 * UP: UP
 * DOWN: DWON
 * BACKTRACK: B
 * ORIGIN: O
 * matrix =        [[1,1,0,0,0],
 *                  [1,1,0,0,0],
 *                  [0,0,0,1,1],
 *                  [0,0,0,1,1]]
 *    ORBDL
 *    ORBDL
 */

public class P06NumberOfDistinctIslands {

    public static int findNumberOfDistinctIslands(int[][] matrix){

        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        Set<String> set= new HashSet<>();


        for (int r =0; r< m; r++){
            for(int c =0; c< n; c++){

                if(matrix[r][c] ==1 && !visited[r][c]  ){
                    StringBuilder islandTraversal= new StringBuilder();
                    dfs(matrix, visited, islandTraversal , r, c,"O");
                    set.add(islandTraversal.toString());
                }
            }
        }
        return set.size();
    }

    public static void dfs(int[][] matrix, boolean[][] visited, StringBuilder islandTraverse, int r, int c, String direction ){
        if (r<0 || r>= matrix.length || c<0 || c>= matrix[0].length )
            return ;
        if( matrix[r][c] == 0 || visited[r][c])
            return ;
        visited[r][c] = true;
        islandTraverse.append(direction);
        dfs(matrix, visited,  islandTraverse, r +1, c,"U");
        dfs(matrix, visited,  islandTraverse, r -1, c,"D");
        dfs(matrix, visited,  islandTraverse, r, c+1,"R");
        dfs(matrix, visited,  islandTraverse, r, c -1,"L");

        islandTraverse.append("B");




    }


    public static void main(String[] args) {
        System.out.println("===================================================================");
        System.out.println("P06. Number of Distinct Islands");
        System.out.println("===================================================================");
        int[][] matrixP06 = {{1,1,0,1,1},
                             {1,0,0,0,0},
                             {0,0,0,0,1},
                             {1,1,0,1,1}};
        int numberOfDistinctIslands = findNumberOfDistinctIslands(matrixP06);

        System.out.println("Input: " + Arrays.deepToString(matrixP06) +", output = " + makeItBold(numberOfDistinctIslands +" ")  +", Expected = 3");

        matrixP06 = new int[][]{{1,1,1,0,0},
                          {1,0,0,0,0},
                          {1,1,0,1,1},
                          {0,0,0,1,1}};
        numberOfDistinctIslands = findNumberOfDistinctIslands(matrixP06);
        System.out.println("Input: " + Arrays.deepToString(matrixP06) +", output = " + makeItBold(numberOfDistinctIslands +" ")  +", Expected = 2");

        matrixP06 = new int[][]{{1,1,0,0,0},
                          {1,1,0,0,0},
                          {0,0,0,1,1},
                          {0,0,0,1,1}};
        numberOfDistinctIslands = findNumberOfDistinctIslands(matrixP06);
        System.out.println("Input: " + Arrays.deepToString(matrixP06) +", output = " + makeItBold(numberOfDistinctIslands +" ")  +", Expected = 1");



    }
}

