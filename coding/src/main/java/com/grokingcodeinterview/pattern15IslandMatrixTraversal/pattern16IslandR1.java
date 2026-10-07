package com.grokingcodeinterview.pattern15IslandMatrixTraversal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.Utility.makeItBold;

public class pattern16IslandR1 {

    /*
    You are given a 2D matrix containing different characters, you need to find if there exists any cycle consisting of the same character in the matrix.
    A cycle is a path in the matrix that starts and ends at the same cell and has four or more cells. From a given cell, you can move to one of the cells adjacent to it - in one of the four directions (up, down, left, or right), if it has the same character value of the current cell.
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
    public static boolean matrixHasCycle(char[][] matrix){
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int r =0; r< m; r++){
            for(int c =0; c< n; c++){
                if(!visited[r][c] && dfs(matrix, visited, r, c, -1, -1, matrix[r][c]))
                    return true;
            }
        }
        return false;
    }

    private static boolean dfs(char[][] matrix, boolean[][] visited, int r, int c, int parentR, int parentC, char ch ){
        if(r<0 || r>= matrix.length || c< 0 || c>= matrix[0].length){
            return false;
        }
        if (r== parentR && c== parentC) return false;
        if (matrix[r][c]!= ch) return false;
        if(visited[r][c]) return true;
        visited[r][c] = true;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for(int[] dir: directions){
            int nr = r + dir[0];
            int nc = c + dir[1];
            if(dfs(matrix, visited, nr,nc , r, c, ch)){
                return true;
            }
        }
        return false;
    }
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
    public static int findNumberOfDistinctIslands(int[][] matrix){
        int m = matrix.length;
        int n = matrix[0].length;

        boolean[][] visited = new boolean[m][n];

        Set<String> set = new HashSet<>();

        for (int r =0; r< m; r++){
            for(int c=0; c< n; c++){
                if(matrix[r][c] ==1 && !visited[r][c]){
                    StringBuilder islandTraverse = new StringBuilder();
                    dfs(matrix, visited, islandTraverse, r, c, "O");
                    set.add(islandTraverse.toString());
                }
            }
        }
        return set.size();
    }

    public static void dfs(int[][] matrix, boolean[][] visited, StringBuilder sb, int r, int c, String direction){
        if(r<0 || r>= matrix.length || c<0 || c>= matrix[0].length)
            return;
        if(matrix[r][c] ==0 || visited[r][c])
            return;
        visited[r][c]= true;
        sb.append(direction);
        dfs(matrix, visited, sb, r+ 1, c, "U");
        dfs(matrix, visited, sb, r-1 , c, "D" );
        dfs(matrix, visited, sb, r, c+1, "R");
        dfs(matrix, visited, sb, r, c-1, "L");
        sb.append("B");
    }

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
    public static int[][] floodFill(int[][] matrix, int x, int y, int newColor){

        if(matrix[x][y] != newColor){
            dfs(matrix, x, y, matrix[x][y], newColor);
        }
        return matrix;
    }
    public static void dfs(int[][] matrix, int x, int y, int originalColor,  int newColor){
        if(x< 0 || x>= matrix.length || y<0 || y>= matrix[x].length){
            return;
        }
        if( matrix[x][y] != originalColor)
            return;
        matrix[x][y] = newColor;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for(int[] dir: directions){
            int nr = x +dir[0];
            int nc = y + dir[1];
            dfs(matrix, nr,nc, originalColor, newColor);
        }
    }
    /*
    Problem Statement
    Given a 2D array (i.e., a matrix) containing only 1s (land) and 0s (water),
    find the biggest island in it. Write a function to return the area of the biggest island.
    An island is a connected set of 1s (land) and is surrounded by either an edge or 0s (water).
     Each cell is considered connected to other cells horizontally or vertically (not diagonally).
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
    public  static int maxAreaOfIsland(int[][] matrix){

        int biggestIsland = 0;
        boolean [][] visited = new boolean[matrix.length][matrix[0].length];
        for (int i =0; i< matrix.length; i++){
            for(int j =0; j<matrix[i].length; j++){
                if(matrix[i][j] == 1 && !visited[i][j]){
                    biggestIsland = Math.max(biggestIsland, findIslandArea(matrix, i, j, visited));
                }
            }
        }
        return biggestIsland;
    }

    public static int findIslandArea(int[][] matrix, int row, int col, boolean[][] visited){
        if(row<0 || row>= matrix.length || col<0 || col>= matrix[row].length|| visited[row][col] || matrix[row][col] == 0  ){

            return 0;
        }

        visited[row][col] = true;

        int[][] directions = {{1, 0}, {-1, 0}, {0, -1},{0, 1}};
        int counter = 1;
        for(int[] dir: directions){
            int nr = row + dir[0];
            int nc = col + dir[1];

            counter += findIslandArea(matrix, nr, nc, visited);


        }
        return counter;


    }
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
    public static int countIslands(int[][] matrix){
        int counter =0;
        for (int row =0; row<matrix.length; row++){
            for(int col =0; col<matrix[0].length; col++){
                if(matrix[row][col] ==1){
                    dfs(matrix, row, col);
                    counter++;
                }
            }
        }

        return counter;
    }
    public static void dfs(int[][] matrix, int row,int col){
        if (row<0 || row>= matrix.length || col<0 || col>= matrix[0].length || matrix[row][col] ==0){
            return;
        }

        matrix[row][col] =0;

        int[][] directions = {{1,0}, {-1,0}, {0,1},{0, -1}};

        for (int[] dir: directions){
            dfs(matrix,row +dir[0], col+ dir[1]);
        }
    }


/*
Problem Statement
You are given a 2D matrix containing only 1s (land) and 0s (water).
An island is a connected set of 1s (land) and is surrounded by either an edge or 0s (water).
 Each cell is considered connected to other cells horizontally or vertically (not diagonally).
There are no lakes on the island, so the water inside the island is not connected to
 the water around it. A cell is a square with a side length of 1.
The given matrix has only one island, write a function to find the perimeter of that island.
example: Input: matrix = [[0,1,0,0],
                          [1,1,1,0],
                          [0,1,0,0],
                          [1,1,0,0]] output = 16
explanation: The perimeter is the 16 .
example: Input: matrix = [[1]] output = 4
example: Input: matrix = [[1,0]] output = 4
Constraints:
1 <= grid.length, grid[0].length <= 100
0 <= grid[i][j] <=1
 */

    public static int findIslandPerimeter(int[][] matrix){
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        for(int r =0; r< matrix.length; r++){
            for(int c =0; c<matrix[0].length; c++){
                if(matrix[r][c] ==1 && !visited[r][c]){
                    return dfs(matrix,visited, r, c);
                }
            }
        }

        return 0;
    }

    public static int dfs(int[][] matrix, boolean[][] visited, int r, int c){
        if(r<0 || r>= matrix.length || c<0 || c>= matrix[0].length){
            return 1;
        }
        if(visited[r][c]) return 0;
        if(matrix[r][c] ==0) return 1;

        visited[r][c] = true;

        int[][] directions = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
        int edgeCount =0;
        for(int[] dir: directions){
            int nr = r+ dir[0];
            int nc = c + dir[1];
            edgeCount += dfs(matrix, visited, nr, nc);

        }

        return edgeCount;

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
        System.out.println("Input: matrixP03 = " + Arrays.deepToString(matrixP03) + ", sr = " + srP03 + ", sc = " + scP03 + ", color = " + colorP03 + " Output: " + makeItBold(Arrays.deepToString(floodFill(matrixP03, srP03, scP03, colorP03)) + "") + " Expected Output: [[0, 0, 0], [0, 0, 0]]");

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
                " Expected Output:[[[1, 1, 0, 0, 0], [0, 1, 0, 0, 5], [1, 0, 0, 5, 5], [0, 0, 0, 0, 0], [1, 0, 1, 0, 1]]");


        System.out.println("===================================================================");
        System.out.println("P05. Perimeter of Island with No Lake");
        System.out.println("===================================================================");

        int[][] matrixP05 = {{0,1,0,0},
                {1,1,1,0},
                {0,1,0,0},
                {1,1,0,0}};
        int perimeterP05   = findIslandPerimeter(matrixP05);
        System.out.println("Input: " + Arrays.deepToString(matrixP05) + " output: " + makeItBold(perimeterP05 +"") + " Expected: 16" );
        matrixP05 = new int[][] {{1}};
        perimeterP05   = findIslandPerimeter(matrixP05);
        System.out.println("Input: " + Arrays.deepToString(matrixP05) + " output: " + makeItBold(perimeterP05 +"") + " Expected: 4" );
        matrixP05 = new int[][] {{1,0}};
        perimeterP05   = findIslandPerimeter(matrixP05);
        System.out.println("Input: " + Arrays.deepToString(matrixP05) + " output: " + makeItBold(perimeterP05 +"") + " Expected: 4" );
        matrixP05 = new int[][] {{1,1,1},
                {1,1,1},
                {1,1,1}};
        perimeterP05   = findIslandPerimeter(matrixP05);
        System.out.println("Input: " + Arrays.deepToString(matrixP05) + " output: " + makeItBold(perimeterP05 +"") + " Expected: 12" );
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
