package com.grokingcodeinterview.pattern24FibonacciNumbers;

import java.util.Arrays;

public class P07UniquePathII {
    /*
    Unique Paths II

A robot is located at the top-left corner of an m x n grid. The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid.

Now consider that some obstacles are added to the grid. An obstacle and space are marked as 1 and 0 respectively in the grid.

Return the number of possible unique paths that the robot can take to reach the bottom-right corner.

Example 1:

Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
Output: 2

Grid visualization:
0 0 0
0 1 0
0 0 0

Explanation: There is one obstacle in the middle of the 3x3 grid.
There are two ways to reach the bottom-right corner:
1. Right -> Right -> Down -> Down
2. Down -> Down -> Right -> Right

Example 2:

Input: obstacleGrid = [[0,1],[0,0]]
Output: 1

Grid visualization:
0 1
0 0

Constraints:

m == obstacleGrid.length
n == obstacleGrid[i].length
1 <= m, n <= 100
obstacleGrid[i][j] is 0 or 1
     */
    public static int uniquePathsWithObstacles(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int [][] dp = new int[m][n];

        // dp[i][j]: number of unique way to reach cell(i, j)

        dp[0][0] = grid[0][0] == 1? 0:1;

        // first row
        for(int j =1; j< n; j++){
            dp[0][j] = (grid[0][j] ==1) ? 0: dp[0][j-1];
        }
        // first column
        for(int i =1; i< m; i++){
            dp[i][0] =(grid[i][0] == 1)? 0: dp[i-1][0];

        }

        for(int i =1;i <m; i++){
            for(int j =1; j<n; j++){
                if(grid[i][j] == 1)
                    dp[i][j] =0;
                else dp[i][j] = dp[i-1][j] +dp[i][j-1];
            }
        }

        return dp[m-1][n-1];

    }
    public static void main(String[] args) {

        // ---- Example 1 ----
        int[][] grid1 = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        int expected1 = 2;
        int result1 = uniquePathsWithObstacles(grid1);

        System.out.println("Example 1");
        System.out.println("Input:    " + Arrays.deepToString(grid1));
        System.out.println("Output:   " + result1);
        System.out.println("Expected: " + expected1);
        System.out.println(result1 == expected1 ? "PASS" : "FAIL");
        System.out.println();

        // ---- Example 2 ----
        int[][] grid2 = {
                {0, 1},
                {0, 0}
        };
        int expected2 = 1;
        int result2 = uniquePathsWithObstacles(grid2);

        System.out.println("Example 2");
        System.out.println("Input:    " + Arrays.deepToString(grid2));
        System.out.println("Output:   " + result2);
        System.out.println("Expected: " + expected2);
        System.out.println(result2 == expected2 ? "PASS" : "FAIL");
    }
}

