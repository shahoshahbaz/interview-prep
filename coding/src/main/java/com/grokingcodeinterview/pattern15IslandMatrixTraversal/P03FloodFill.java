package com.grokingcodeinterview.pattern15IslandMatrixTraversal;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
Problem Statement
Any image can be represented by a 2D integer array (i.e., a matrix) where each cell represents the pixel value of the image.

Flood fill algorithm takes a starting cell (i.e., a pixel) and a color.
 The given color is applied to all horizontally and vertically connected cells with the same color as that of the starting cell.
 Recursively, the algorithm fills cells with the new color until it encounters a cell with a different color than the starting cell.

Given a matrix, a starting cell, and a color, flood fill the matrix.

Example 1:

Input: matrix = [[1, 1, 1],
          [1, 1, 0],
          [1, 0, 1]], sr = 1, sc = 1, color = 2
Output: [[2, 2, 2],
         [2, 2, 0],
         [2, 0, 1]]

Example 2:
Input: matrix = [[0, 0, 0],
          [0, 1, 1]], sr = 1, sc = 1, color =3
Output: [[0, 0, 0],
         [0, 3, 3]]
Example 3:
Input: matrix = [[0, 0, 0],
          [0, 0, 0]], sr = 1, sc = 1, color = 1
Output: [[0, 0, 0],
         [0, 0, 0]]
Constraints:
m == matrix.length
n == matrix[i].length
1 <= m, n <= 50
0 <= matrix[i][j], color < 2^16
0 <= sr < m
0 <= sc < n

 */
public class P03FloodFill {
    private static final int[][] DIRECTIONS = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    public static int[][] floodFill(int[][] matrix, int sr, int sc, int newColor) {
        int originalColor = matrix[sr][sc];

        // If starting cell already has the new color, nothing to do
        if (originalColor == newColor) {
            return matrix;
        }

        dfs(matrix, sr, sc, originalColor, newColor);
        return matrix;
    }

    public static void dfs(int[][] matrix, int r, int c, int originalColor, int newColor) {
        // Boundary check
        if (r < 0 || r >= matrix.length || c < 0 || c >= matrix[0].length) {
            return;
        }

        // Stop if this cell is not part of the original region
        if (matrix[r][c] != originalColor) {
            return;
        }

        // Recolor current cell
        matrix[r][c] = newColor;

        // Visit 4-directionally connected neighbors
        for (int[] dir : DIRECTIONS) {
            int nr = r + dir[0];
            int nc = c + dir[1];
            dfs(matrix, nr, nc, originalColor, newColor);
        }
    }

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("P03. Flood Fill");
        System.out.println("=================================");
        int[][] matrixP03 = {
                {1, 1, 1},
                {1, 1, 0},
                {1, 0, 1}
        };
        int srP03 = 1;
        int scP03 = 1;
        int colorP03 = 2;
        System.out.println("Input: matrixP03 = " + Arrays.deepToString(matrixP03) + ", sr = " + srP03 + ", sc = " + scP03 + ", color = " + colorP03 + " Output: " + makeItBold(Arrays.deepToString(floodFill(matrixP03, srP03, scP03, colorP03)) + "") + " Expected Output: [[2, 2, 2], [2, 2, 0], [2, 0, 1]]");

        matrixP03 = new int[][]{
                {0, 0, 0},
                {0, 1, 1}
        };
        srP03 = 1;
        scP03 = 1;
        colorP03 = 3;
        System.out.println("Input: matrixP03 = " + Arrays.deepToString(matrixP03) + ", sr = " + srP03 + ", sc = " + scP03 + ", color = " + colorP03 + " Output: " + makeItBold(Arrays.deepToString(floodFill(matrixP03, srP03, scP03, colorP03)) + "") + " Expected Output: [[0, 0, 0], [0, 3, 3]]");

        matrixP03 = new int[][]{
                {0, 0, 0},
                {0, 0, 0}
        };
        srP03 = 1;
        scP03 = 1;
        colorP03 = 1;
        System.out.println("Input: matrixP03 = " + Arrays.deepToString(matrixP03) + ", sr = " + srP03 + ", sc = " + scP03 + ", color = " + colorP03 + " Output: " + makeItBold(Arrays.deepToString(floodFill(matrixP03, srP03, scP03, colorP03)) + "") + " Expected Output: [[1, 1, 1], [1, 1, 1]] ");

        // I need one big matrix that is complex
            matrixP03 = new int[][]{
                    {1, 1, 0, 0, 0},
                    {0, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1},
                    {0, 0, 0, 0, 0},
                    {1, 0, 1, 0, 1}
            };
            srP03 = 2;
            scP03 = 3;
            colorP03 = 5;

        System.out.println("Input: matrixP03 = " + Arrays.deepToString(matrixP03) + ", sr = " + srP03 + ", sc = " + scP03 + ", color = " + colorP03 + " Output: " + makeItBold(Arrays.deepToString(floodFill(matrixP03, srP03, scP03, colorP03)) + "") +
                " Expected Output: [[1, 1, 0, 0, 0], [0, 1, 0, 0, 5], [1, 0, 0, 5, 5], [0, 0, 0, 0, 0], [1, 0, 1, 0, 1]]");

    }
}

