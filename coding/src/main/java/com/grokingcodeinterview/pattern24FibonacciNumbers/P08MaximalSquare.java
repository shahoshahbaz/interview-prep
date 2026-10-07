package com.grokingcodeinterview.pattern24FibonacciNumbers;

import java.util.Arrays;

import static com.Utility.makeItBold;

public class P08MaximalSquare {
    /*
    Maximal Square

    Given an m x n binary matrix filled with 0's and 1's, find the largest square
    containing only 1's and return its area.
    Example 1:

Input: matrix = [ ["1","0","1","0","0"],
                  ["1","0","1","1","1"],
                  ["1","1","1","1","1"],
                  ["1","0","0","1","0"]]
Output: 4
Explanation: The largest square of 1's has side length 2, so area = 4.

Example 2:

Input: matrix = [ ["0","1"],
                  ["1","0"]]
Output: 1

Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 300
matrix[i][j] is '0' or '1'
     */
    // Brute force: O((m*n) * min(m,n)^3) time, O(1) extra space.
    // For every cell as a candidate top-left corner, try growing the square side
    // from 1 up to the max possible size, and for each candidate side, scan the
    // whole sub-square to check all cells are '1'. Track the largest valid side.
    public static int maximalSquareBruteForce(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int maxSide = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    // largest side that could possibly fit starting at (i, j)
                    int maxPossibleSide = Math.min(m - i, n - j); // what is this Math.min(m - i, n - j)??
                    // it is the maximum possible side length of a square that can fit in the matrix starting from the cell (i, j).
                    // It is determined by the distance to the bottom and right edges of the matrix.
                    // The smaller of these two distances will be the limiting factor for how large a square can be formed starting from (i, j).
                    // m -i: gives the number of row remaking from i to m -1
                    // n - j: gives the number of column remaing from j to n -1

                    for (int side = 1; side <= maxPossibleSide; side++) {
                        if (isAllOnes(matrix, i, j, side)) {
                            maxSide = Math.max(maxSide, side);
                        } else {
                            // if a smaller side already fails, bigger ones will too
                            break;
                        }
                    }
                }
            }
        }

        return maxSide * maxSide;
    }

    // Checks whether the square of the given side, with top-left corner at (row, col),
    // is filled entirely with '1's.
    private static boolean isAllOnes(char[][] matrix, int row, int col, int side) {
        for (int i = row; i < row + side; i++) {
            for (int j = col; j < col + side; j++) {
                if (matrix[i][j] == '0') {
                    return false;
                }
            }
        }
        return true;
    }

    public static int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int maxSide = 0;

        // what is dp[i][j] means, it means the max side length of square whose bottom right corner is at (i,j)
        int[][] dp = new int[m][n];


        for(int i =0; i<m ; i++){
            for(int j =0; j< n; j++){
                if(matrix[i][j] == '1'){ // if the current cell is 1, then we can form a square with bottom right corner at (i,j)
                    if(i ==0 || j ==0){ // if we are at the first row or first column, then we can only form a square of side length 1
                        dp[i][j] =1;// so the edge of grid, can only support 1X1


                    }else{
                        // if we are not at the first row or first column, then we can form a square of side length
                        // min(dp[i-1][j-1], dp[i][j-1], dp[i-1][j]) + 1
                        // dp[i-1][j-1]: right diagonal,
                        // dp[i][j-1]: left,
                        // dp[i-1][j]: up
                        dp[i][j] = Math.min(dp[i -1][j-1], Math.min(dp[i][j-1], dp[i-1][j] )  )+1;
                    }
                    maxSide = Math.max(dp[i][j], maxSide);


                }
            }
        }


        return maxSide * maxSide;
    }

    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("P08. Maximal Square");
        System.out.println("===========================");

        char[][] matrixP08 = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };
        System.out.println("Input: matrix = " + Arrays.deepToString(matrixP08)
                + ", output: " + makeItBold(maximalSquare(matrixP08) + " ")
                + ", Expected Output: 4");

        char[][] matrixP08_2 = {
                {'0', '1'},
                {'1', '0'}
        };
        System.out.println("Input: matrix = " + Arrays.deepToString(matrixP08_2)
                + ", output: " + makeItBold(maximalSquare(matrixP08_2) + " ")
                + ", Expected Output: 1");

        char[][] matrixP08_3 = {
                {'0'}
        };
        System.out.println("Input: matrix = " + Arrays.deepToString(matrixP08_3)
                + ", output: " + makeItBold(maximalSquare(matrixP08_3) + " ")
                + ", Expected Output: 0");

        char[][] matrixP08_4 = {
                {'1', '1'},
                {'1', '1'}
        };
        System.out.println("Input: matrix = " + Arrays.deepToString(matrixP08_4)
                + ", output: " + makeItBold(maximalSquare(matrixP08_4) + " ")
                + ", Expected Output: 4");
    }
}
