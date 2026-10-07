package com.grokingcodeinterview.pattern37realInterviewProblems;

/*
Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"] output: 1
  Example 2: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]] output: 3
 *
 */
public class LC200 {

    public static final int[][] neighbors =new int[][] {{1, 0},{-1, 0},{0, -1},{0, 1} };

    public static  int numIslands(char[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        boolean[][] visited = new boolean[n][m];
        int numberOfIsland =0 ;

        for (int r =0; r< n; r++ ){
            for(int c =0; c<m; c++){
                if(visited[r][c]) continue;
                if (dfs(r, c, grid, visited)){
                    numberOfIsland ++;
                }

            }
        }

        return numberOfIsland;

    }

    public static boolean dfs(int r, int c, char[][] grid, boolean[][] visited){
        if (r<0 || r>= grid.length || c<0 || c>= grid[0].length || visited[r][c]  || grid[r][c] == '0' ) return false;
        visited[r][c] = true;

        for(int[] nei: neighbors){
            int nr = r + nei[0];
            int nc = c + nei[1];
            dfs(nr, nc, grid, visited);
        }
        return true;
    }




    public static void main(String[] args) {

        System.out.println("================================================");
        System.out.println("LC200: Number of Islands");
        System.out.println("================================================");

        char[][] gridLC200 = new char[][] {
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        System.out.println("Input: " + gridLC200 + " output: " + numIslands(gridLC200) +" Expected Output: 1"); // Output: 1

        gridLC200 = new char[][] {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        System.out.println("Input: " + gridLC200 + " output: " + numIslands(gridLC200) +" Expected Output: 3"); // Output: 3

        gridLC200 = new char[][] {
            {'1','1','1'},
            {'0','1','0'},
            {'1','1','1'}
        };
        System.out.println("Input: " + gridLC200 + " output: " + numIslands(gridLC200) +" Expected Output: 1"); // Output: 1



    }
}
