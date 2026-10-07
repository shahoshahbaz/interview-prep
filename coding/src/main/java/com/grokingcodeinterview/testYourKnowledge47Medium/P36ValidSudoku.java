package com.grokingcodeinterview.testYourKnowledge47Medium;

import java.util.HashSet;
import java.util.Set;

/*
Problem Statement
Determine if a 9x9 Sudoku board is valid. A valid Sudoku board will hold the following conditions:

Each row must contain the digits 1-9 without repetition.
Each column must contain the digits 1-9 without repetition.
The 9 3x3 sub-boxes of the grid must also contain the digits 1-9 without repetition.
Note:

The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
You need to validate only filled cells.
Example 1:
Input:
[["5","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
Expected Output: true
Justification: This Sudoku board is valid as it adheres to the rules of no repetition in each row, each column, and each 3x3 sub-box.
Example 2:
Input:
[["8","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
Expected Output: false
Justification: The first and fourth rows both contain the number '8', violating the Sudoku rules.
Example 3:
Input:
[[".",".","4",".",".",".","6","3","."]
,[".",".",".",".",".",".",".",".","."]
,["5",".",".",".",".",".",".","9","."]
,[".",".",".","5","6",".",".",".","."]
,["4",".","3",".",".",".",".",".","1"]
,[".",".",".","7",".",".",".",".","."]
,[".",".",".","5",".",".",".",".","."]
,[".",".",".",".",".",".",".",".","."]
,[".",".",".",".",".",".",".",".","."]]
Expected Output: false
Justification: The fourth column contains the number '5' two times, violating the Sudoku rules.
Constraints:

board.length == 9
board[i].length == 9
board[i][j] is a digit 1-9 or '.'.
 */
public class P36ValidSudoku {

    public static boolean isValidSudoku(char[][] board) {

        Set<String> seen = new HashSet<>();

        for (int r =0; r< 9; r++ ){
            for(int c =0; c< 9; c++){
                char d = board[r][c];
                if(d =='.') continue;

                int boxNumber= (r/3) * 3 +c/ 3;
                String rowKey = d+" row "+ r;
                String colKey = d+" col " + c;
                String boxKey = d+ " box " + boxNumber;

                if(seen.contains(rowKey) || seen.contains(colKey) || seen.contains(boxKey)){
                    return false;
                }



                seen.add(rowKey);
                seen.add(colKey);
                seen.add(boxKey);

            }
        }




        return true;
    }
}

