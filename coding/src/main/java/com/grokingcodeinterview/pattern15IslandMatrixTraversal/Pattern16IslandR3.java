package com.grokingcodeinterview.pattern15IslandMatrixTraversal;

public class Pattern16IslandR3 {

    public static int countIslands(int[][] matrix){

        int n = matrix.length;
        int m = matrix[0].length;

        int counter = 0;
        for(int i =0; i< n; i++){
            for(int j =0; j<m; j++){
                if (matrix[i][j] ==1){
                    counter++;
                    dfs(matrix, i, j);

                }
            }
        }
        return counter;
    }

    public static void dfs(int[][] matrix, int row, int col){
        // validation to make sure there are not out of bount and also is not water
        if(row <0 || row>= matrix.length || col <0 || col>= matrix[0].length || matrix[row][col] ==0)
            return;

        matrix[row][col] =0; // make it visited
        int[][] directions ={{1, 0},{-1, 0},{0, 1},{0, -1}};

        for (int[] dir: directions){
            int nr = row + dir[0];
            int nc = col + dir[1];

            dfs(matrix, nr, nc);
        }
    }
}

