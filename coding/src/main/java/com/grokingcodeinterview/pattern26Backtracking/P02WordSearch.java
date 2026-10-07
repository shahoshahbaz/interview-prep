package com.grokingcodeinterview.pattern26Backtracking;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
  Problem Statement
Given an m x n grid of characters board and a string word, return true if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring.
 The same letter cell may not be used more than once.

Example 1: Input: word="ABCCED", board:

  { 'A', 'B', 'C', 'E' },
  { 'S', 'F', 'C', 'S' },
  { 'A', 'D', 'E', 'E' }
Output: true

Explanation: The word exists in the board:
-> { 'A', 'B', 'C', 'E' },
-> { 'S', 'F', 'C', 'S' },
-> { 'A', 'D', 'E', 'E' }

Example 2:

Input: word="SEE", board:

  { 'A', 'B', 'C', 'E' },
  { 'S', 'F', 'C', 'S' },
  { 'A', 'D', 'E', 'E' }
Output: true

Explanation: The word exists in the board:
-> { 'A', 'B', 'C', 'E' },
-> { 'S', 'F', 'C', 'S' },
-> { 'A', 'D', 'E', 'E' }

Constraints:

m == board.length
n = board[i].length
1 <= m, n <= 6
1 <= word.length <= 15
board and word consists of only lowercase and uppercase English letters.
 */
public class P02WordSearch {
    // what does dfs with Pruning?
    // it is a depth first search with some condition to stop the search early
    // if it is not possible to find the word in the current path.


    public static boolean wordExist(char[][] board,String word){

        for (int i =0; i<board.length; i++){
            for(int j =0; j< board[0].length; j++){
                if (dfs(board, word,i, j, 0)){
                    return true;
                }
            }
        }
        return false;
    }
    // what is the k here?
    // k is the index of the current character in the word that we are trying to match with the current cll in the board.
    public static boolean dfs(char[][] board, String word, int i, int j, int k ){
        if (i<0 || i>= board.length || j<0 || j>= board[0].length || board[i][j] != word.charAt(k)){
            return false;
        }

        if (k == word.length() -1) return true;

        // mark the current cell as visited
        char temp = board[i][j];
        board[i][j] = '/';

        boolean result = dfs(board, word, i+1, j, k+1)||
                dfs(board, word, i -1, j, k+1) ||
                dfs(board, word, i, j+1, k+1)||
                dfs(board , word, i, j -1, k+1);


        board[i][j] = temp;

        return result;

    }
    public static void main(String[] args) {

        System.out.println("===========================");
        System.out.println("P02. Word Search");
        System.out.println("===========================");

        char[][] boardP02 = {
                { 'A', 'B', 'C', 'E' },
                { 'S', 'F', 'C', 'S' },
                { 'A', 'D', 'E', 'E' }
        };

        String wordP02 = "ABCCED";

        System.out.println("Input: " + Arrays.deepToString(boardP02) + ", word: " + wordP02 + ", Output: " + makeItBold(wordExist(boardP02, wordP02)+"") + ", Expected: true");

        boardP02 = new char[][] {
                { 'A', 'B', 'C', 'E' },
                { 'S', 'F', 'C', 'S' },
                { 'A', 'D', 'E', 'E' }
        };
        wordP02 = "SEE";
        System.out.println("Input: " + Arrays.deepToString(boardP02) + ", word: " + wordP02 + ", Output: " + makeItBold(wordExist(boardP02, wordP02)+"") + ", Expected: true");

        boardP02 = new char[][] {
                { 'A', 'B', 'C', 'E' },
                { 'S', 'F', 'C', 'S' },
                { 'A', 'D', 'E', 'E' }
        };
        wordP02 = "XYZ";
        System.out.println("Input: " + Arrays.deepToString(boardP02) + ", word: " + wordP02 + ", Output: " + makeItBold(wordExist(boardP02, wordP02)+"") + ", Expected: false");

        boardP02 = new char[][] {
                { 'A', 'B', 'C', 'E' },
                { 'S', 'F', 'C', 'S' },
                { 'A', 'D', 'E', 'E' }
        };
        wordP02 = "ABCCEDX";
        System.out.println("Input: " + Arrays.deepToString(boardP02) + ", word: " + wordP02 + ", Output: " + makeItBold(wordExist(boardP02, wordP02)+"") + ", Expected: false");

    }
}

