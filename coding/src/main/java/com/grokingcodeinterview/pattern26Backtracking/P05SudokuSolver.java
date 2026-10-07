package com.grokingcodeinterview.pattern26Backtracking;

/*
Sudoku Solver (LeetCode 37)

Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy all of the following rules:

Each of the digits 1-9 must occur exactly once in each row.
Each of the digits 1-9 must occur exactly once in each column.
Each of the digits 1-9 must occur exactly once in each of the nine 3Ã—3 sub-boxes of the grid.

The '.' character indicates empty cells.

Example:

Input board:

5 3 . | . 7 . | . . .
6 . . | 1 9 5 | . . .
. 9 8 | . . . | . 6 .
------+-------+------
8 . . | . 6 . | . . 3
4 . . | 8 . 3 | . . 1
7 . . | . 2 . | . . 6
------+-------+------
. 6 . | . . . | 2 8 .
. . . | 4 1 9 | . . 5
. . . | . 8 . | . 7 9

Output (board modified in place):

5 3 4 | 6 7 8 | 9 1 2
6 7 2 | 1 9 5 | 3 4 8
1 9 8 | 3 4 2 | 5 6 7
------+-------+------
8 5 9 | 7 6 1 | 4 2 3
4 2 6 | 8 5 3 | 7 9 1
7 1 3 | 9 2 4 | 8 5 6
------+-------+------
9 6 1 | 5 3 7 | 2 8 4
2 8 7 | 4 1 9 | 6 3 5
3 4 5 | 2 8 6 | 1 7 9
 */
public class P05SudokuSolver {
    public void solveSudoku(char[][] board){

        backtrack(board);

    }

    public static boolean backtrack(char[][] board){
        for (int r =0; r<9; r++){
            for(int c = 0; c<  9; c++){
                // why we are continuing if the cell is not empty? because we only want to fill empty cells, and if the cell is already filled, we don't need to do anything with it. So we skip it and move on to the next cell.
                if(board[r][c] != '.') continue;


                for(char digit = '1'; digit<='9'; digit++){
                    if(!isValid(board, r, c, digit)) continue;
                    board[r][c] = digit;  // choose
                    if(backtrack(board )) return true; // explore
                    board[r][c] = '.'; // unchoose

                }

                return false;  // no digits worked here, dead branch
            }
        }
        return true;// no empty cell left -> solved
    }

    private static boolean isValid(char[][] board, int r, int c, char d){

        // how to find the starting row and column of the 3x3 box that contains the cell (r, c)?
        // we can use integer division to find the starting row and column of the 3x3 box. The starting row is (r/3) * 3 and the starting column is (c/3) * 3. This works because integer division truncates the decimal part, so we get the index of the box that contains the cell (r, c).
        int boxR = (r/3) * 3; //this is  starting row of the 3x3 box
        int boxC = (c/3) * 3;// this is starting column of the 3x3 box
        for(int i =0; i< 9; i++){
            if(board[r][i] == d) return false; // check row for all columns
            if(board[i][c] == d) return false;// check column for all rows
            // i/3 :gives row offset 0,0,0,1,1,1,2,2,2 and
            // i%3 gives column offset 0,1,2,0,1,2,0,1,2.
            if(board[boxR +i/3][boxC + i%3] == d) return false;
        }
        return true;

    }
}

