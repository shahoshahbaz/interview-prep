package com.ds.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Matrix Traversal Patterns for Upper and Lower Triangle
 * Useful for Dynamic Programming problems involving intervals/subarrays
 */
public class Matrix {

    // ==================== UPPER TRIANGLE (i < j) ====================

    /**
     * Upper Triangle: Bottom-to-top rows, left-to-right per row
     * Useful for DP depending on i+1 (next row)
     * Order: (4,5), (3,4), (3,5), (2,3), (2,4), (2,5), ...
     */
    public static List<int[]> upperTriangleBottomToTop(int n) {
        List<int[]> result = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                result.add(new int[]{i, j});
            }
        }
        return result;
    }

    /**
     * Upper Triangle: Top-to-bottom rows, left-to-right per row
     * Most common and simple pattern
     * Order: (0,1), (0,2), (0,3), ..., (1,2), (1,3), ...
     */
    public static List<int[]> upperTriangleTopToBottom(int n) {
        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                result.add(new int[]{i, j});
            }
        }
        return result;
    }

    /**
     * Upper Triangle: By interval length (BEST FOR INTERVAL DP)
     * Process all pairs with same length first, then increase length
     * Order: (0,1), (1,2), (2,3), (3,4), then (0,2), (1,3), (2,4), ...
     * 
     * len = length of interval (j - i + 1)
     * Perfect for problems like:
     * - Matrix Chain Multiplication
     * - Burst Balloons
     * - Palindrome Partition
     */
    public static List<int[]> upperTriangleByIntervalLength(int n) {
        List<int[]> result = new ArrayList<>();
        for (int len = 2; len <= n; len++) {      // len = j - i + 1
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;              // j > i
                result.add(new int[]{i, j});
            }
        }
        return result;
    }

    // ==================== LOWER TRIANGLE (i > j) ====================

    /**
     * Lower Triangle: Top-to-bottom rows, left-to-right columns
     * Iterate j from 0 to i-1
     * Order: (1,0), (2,0), (2,1), (3,0), (3,1), (3,2), ...
     */
    public static List<int[]> lowerTriangleTopToBottomLeftToRight(int n) {
        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                result.add(new int[]{i, j});
            }
        }
        return result;
    }

    /**
     * Lower Triangle: Top-to-bottom rows, right-to-left columns
     * Iterate j from i-1 down to 0
     * Order: (1,0), (2,1), (2,0), (3,2), (3,1), (3,0), ...
     */
    public static List<int[]> lowerTriangleTopToBottomRightToLeft(int n) {
        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                result.add(new int[]{i, j});
            }
        }
        return result;
    }

    /**
     * Lower Triangle: Bottom-to-top rows
     * Useful when you need dp[i-1][*] ready first
     * Order: (4,0), (4,1), (4,2), (4,3), (3,0), (3,1), ...
     */
    public static List<int[]> lowerTriangleBottomToTop(int n) {
        List<int[]> result = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                result.add(new int[]{i, j});
            }
        }
        return result;
    }

    public static List<int[]> get8DirectionNeighbors(int n){

        int[][] directions8 = {
                {-1, 0},  // up
                {1, 0},   // down
                {0, -1},  // left
                {0, 1},   // right
                {-1, -1}, // left-top
                {-1, 1},  // right-top
                {1, -1},  // left-bottom
                {1, 1}    // right-bottom
        };

        // want to get all valid neighbors for a cell (i, j) in an n x n matrix
        List<int[]> neighbors = new ArrayList<>();
        for (int[] dir : directions8) {
            int newRow = dir[0];
            int newCol = dir[1];
            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                neighbors.add(new int[]{newRow, newCol});
            }

        }
        return neighbors;

    }

    // ==================== UTILITY METHODS ====================

    /**
     * Print a 2D matrix in readable format
     */
    public static void printMatrix(int[][] matrix) {
        int n = matrix.length;
        System.out.println();
        System.out.print("      ");
        for (int j = 0; j < n; j++) System.out.printf("j=%d ", j);
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.printf("i=%d | ", i);
            for (int j = 0; j < n; j++) {
                System.out.printf(" %d  ", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }


    /**
     * Print traversal order as a matrix
     */
    public static void printTraversalOrder(List<int[]> traversal, int n) {
        int[][] matrix = new int[n][n];
        for (int idx = 0; idx < traversal.size(); idx++) {
            int[] pair = traversal.get(idx);
            matrix[pair[0]][pair[1]] = idx + 1;
        }
        printMatrix(matrix);
    }
}
