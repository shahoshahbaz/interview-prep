package com.grokingcodeinterview.pattern15IslandMatrixTraversal;

import java.util.Arrays;

import static com.Utility.makeItBold;

    /*
    You are given a 2D matrix containing different characters, you need to find if there exists any cycle consisting of the same character in the matrix.
    A cycle is a path in the matrix that starts and ends at the same cell
    and has four or more cells. From a given cell, you can move to one of the cells adjacent to it -
    in one of the four directions (up, down, left, or right), if it has the same character value of the current cell.
    Write a function to find if the matrix has a cycle.
    Example 1: Input: matrix = [["a","a","a","a"],
                      ["a","b","b","a"],
                      ["a","b","b","a"],
                      ["a","a","a","a"]] , output = true
    Explanation: There are several cycles in the given matrix,
    Example 2: Input: matrix = [["c","c","c","a"],
                      ["c","d","c","c"],
                      ["c","c","e","c"],
                      ["f","c","c","c"]], output = true
    Explanation: There are several cycles in the given matrix,
    Example 3: Input: matrix = [["a","b","b"],
                      ["b","z","b"],
                      ["b","b","a"]], output = false
    Explanation: There are no cycles in the given matrix.
    Constraints:
    1 <= matrix.length <= 500
    1 <= matrix[i].length <= 500
    */
public class P07matrixHasCycle {
    public static boolean matrixHasCycle(char [][] matrix){

        int m = matrix.length;
        int n = matrix[0].length;

        boolean[][] visited = new boolean[m][n];

        for (int r =0; r< m; r++){
            for(int c=0; c<n; c++){
                if(!visited[r][c]){
                    if(dfs(matrix, visited, r, c, -1, -1, matrix[r][c]))
                        return true;
                }
            }
        }
        return false;
    }

    public static boolean dfs(char[][] matrix, boolean[][] visited, int r, int c,int parentR, int parentC, char ch ){
        if(r< 0 || r>=matrix.length || c<0 || c>= matrix[0].length){
            return false;
        }
        if(matrix[r][c] != ch) return false;


        if(visited[r][c]) return true;

        visited[r][c] = true;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] dir: directions ){
            int nr = r + dir[0];
            int nc = c + dir[1];
            if(nr == parentR && nc == parentC) continue;

            if(dfs(matrix, visited, nr, nc, r, c, ch)){
                return true;
            }

        }
        return false;
    }
    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("P07. matrix Has Cycle");
        System.out.println("=========================================================");
        char[][] matrixP07 = {{'a','a','a','a'},
                           {'a','b','b','a'},
                           {'a','b','b','a'},
                           {'a','a','a','a'}};
        boolean resultP07 = matrixHasCycle(matrixP07);
        System.out.println("Input: " + Arrays.deepToString(matrixP07) + " output = " + makeItBold(resultP07+ "") +", Expected: true");

        matrixP07 = new char[][]{{'c','c','c','a'},
                           {'c','d','c','c'},
                           {'c','c','e','c'},
                           {'f','c','c','c'}};
        resultP07 = matrixHasCycle(matrixP07);
        System.out.println("Input: " + Arrays.deepToString(matrixP07) + " output = " + makeItBold(resultP07+ "") +", Expected: true");
        matrixP07 = new char[][]{{'a','b','b'},
                           {'b','z','b'},
                           {'b','b','a'}};
        resultP07 = matrixHasCycle(matrixP07);
        System.out.println("Input: " + Arrays.deepToString(matrixP07) + " output = " + makeItBold(resultP07+ "") +", Expected: false");

    }
}

