package com.grokingcodeinterview.pattern26Backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.Utility.makeItBold;

public class Pattern27BacktrackingPatternR2 {
    /*
       Problem Statement
       Given a string s, return the maximum number of unique substrings that the given string can be split into.
       You can split string s into any list of non-empty substrings, where the concatenation of the substrings forms the original string.
        However, you must split the substrings such that all of them are unique.
       A substring is a contiguous sequence of characters within a string.
       Example 1:    Input: s = "aab"     Output: 2
       Explanation: Two possible ways to split the given string into maximum unique substrings are: ['a', 'ab'] & ['aa', 'b'], both have 2 substrings; hence the maximum number of unique substrings in which the given string can be split is 2.
       Example 2:  Input: s = "abcabc"  Output: 4
       Explanation: Four possible ways to split into maximum unique substrings are: ['a', 'b', 'c', 'abc'] & ['a', 'b', 'cab', 'c'] &  ['a', 'bca', 'b', 'c'] & ['abc', 'a', 'b', 'c'], all have 4 substrings.
       Constraints:
       1 <= s.length <= 16
       s contains only lower case English letters.
    */
    public static int maxUniqueSplit(String str){
        return countAndSplit(str,0, new HashSet<>());
    }

    public static int countAndSplit(String str, int start, HashSet<String> set){

        if(str.length() == start) return set.size();
        int count =0;
        for (int i = start+ 1; i< str.length(); i++){
            String substring = str.substring(start, i);
            if(set.add(substring)){
                count = Math.max(count, countAndSplit(str, i, set));
                set.remove(set.size() -1);
            }
        }

        return count;
    }
    /*
    Problem Statement
    Numbers can be regarded as the product of their factors.For example, 8 = 2 x 2 x 2 = 2 x 4.
    Given an integer n, return all possible combinations of its factors. You may return the answer in any order.
    Example 1: Input: n = 8  Output: [[2, 2, 2], [2, 4]]
    Example 2: Input: n = 20   Output: [[2, 2, 5], [2, 10], [4, 5]]
    Constraints:
    2 <= n <= 10^7
     */
    public  static List<List<Integer>> getFactors(int num){
        List<List<Integer>> result = new ArrayList<>();
        return  getAllFactors(num, 2, new ArrayList<>(), result);
    }
    public static List<List<Integer>> getAllFactors(int num, int start, List<Integer> currPath, List<List<Integer>> allPath){

        for (int i = start; i<=(int) Math.sqrt(num); i++){
            if(num %i == 0){
                currPath.add(i);
                List<Integer> copyCurrPath = new ArrayList<>(currPath);
                copyCurrPath.add(num/i);
                allPath.add(copyCurrPath);
                getAllFactors(num/i, i, currPath, allPath);
                currPath.remove(currPath.size() -1);

            }
        }
        return allPath;
    }
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
    public static boolean wordExist(char[][] board, String word){

        for (int r = 0; r <board.length; r++){
            for(int c =0; c<board[0].length; c++){
                if (dfs(board, r, c, 0,  word)){
                    return true;
                }
            }
        }
        return false;

    }
    public static boolean dfs(char[][] board, int row, int col,int k,  String word){
        if (row<0 || row >= board.length  || col<0 || col >= board[0].length ||word.charAt(k) != board[row][col] ){
            return false;
        }
        if (k == word.length() -1) return true;
        char temp = board[row][col];

        board[row][col] ='/';
        boolean result = dfs(board, row+1, col, k+1, word)||
                dfs(board, row-1, col, k+1, word )||
                dfs(board, row, col+1, k+1, word )||
                dfs(board, row, col-1, k+1, word);

        board[row][col] = temp;
        return result;
    }
    /*
    Problem Statement:
   Given an array of distinct positive integers candidates and a target integer target,
   return a list of all unique combinations of candidates
    where the chosen numbers sum to target. You may return the combinations in any order.
   The same number may be chosen from candidates an unlimited number of times.
   Two combinations are unique if the frequency of at least one of the chosen numbers is different.


   Example 1: Input: candidates = [2, 3, 6, 7], target = 7 Output: [[2, 2, 3], [7]]
   Explanation: The elements in these two combinations sum up to 7.
   Example 2: Input: candidates = [2, 4, 6, 8], target = 10  Output: [[2,2,2,2,2], [2,2,2,4], [2,2,6], [2,4,4], [2,8], [4,6]]
   Explanation: The elements in these six combinations sum up to 10.
   Constraints:

   1 <= candidates.length <= 30
   2 <= candidates[i] <= 40
   All elements of candidates are distinct.
   1 <= target <= 40
*/
    public static List<List<Integer>> combinationSum (int[] nums, int targetSum){
        List<List<Integer>> result = new ArrayList<>();
        backTrack(nums, 0, targetSum,  new ArrayList<>(), result);
        return result;
    }
    public static void backTrack(int[] nums, int startIndex,  int remaining, List<Integer> currPath, List<List<Integer>> allPath ){
        if (remaining ==0){
            allPath.add(new ArrayList<>(currPath));
            return;
        }

        if(remaining <0 || startIndex>= nums.length){
            return;
        }

        for (int i = startIndex; i<nums.length; i++){

            currPath.add(nums[i]);
            backTrack(nums, i, remaining- nums[i], currPath, allPath);
            currPath.remove(currPath.size() -1);
        }
    }
    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("P01. Combination Sum");
        System.out.println("===========================");
        int[] numsP01 = {2, 3, 6, 7};
        int targetP01 = 7;
        List<List<Integer>> resultP01 = combinationSum(numsP01, targetP01);
        System.out.println("Input:  " + Arrays.toString(numsP01) + ", target = " + targetP01 +" ,Output: " + makeItBold(resultP01 +"") +" ,Expected: [[2, 2, 3], [7]]");

        numsP01 = new int[]{2, 4, 6, 8};
        targetP01 = 10;
        List<List<Integer>> resultP02 = combinationSum(numsP01, targetP01);
        System.out.println("Input:  " + Arrays.toString(numsP01) + ", target = " + targetP01 +" ,Output: " + makeItBold(resultP02 +"") +" ,Expected: [[2, 2, 2, 2, 2], [2, 2, 2, 4], [2, 2, 6], [2, 4, 4], [2, 8], [4, 6]]");

        numsP01 = new int[]{2, 3, 5};
        targetP01 = 8;
        List<List<Integer>> resultP03 = combinationSum(numsP01, targetP01);
        System.out.println("Input:  " + Arrays.toString(numsP01) + ", target = " + targetP01 +" ,Output: " + makeItBold(resultP03 +"") +" ,Expected: [[2, 2, 2, 2], [2, 3, 3], [3, 5]]");
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
        System.out.println("==============================");
        System.out.println("P03. Factor Combinations");
        System.out.println("==============================");
        int numberP03 = 8;
        System.out.println("Input: n = " + numberP03 + "   Output: " + makeItBold(getFactors(numberP03).toString()) +"   Expected: [[2, 2, 2], [2, 4]]");
        numberP03 = 20;
        System.out.println("Input: n = " + numberP03 + "   Output: " + makeItBold(getFactors(numberP03).toString()) +"   Expected: [[2, 2, 5], [2, 10], [4, 5]]");
        numberP03 = 32;
        System.out.println("Input: n = " + numberP03 + "   Output: " + makeItBold(getFactors(numberP03).toString()) +"   Expected: [[2, 2, 2, 2, 2], [2, 2, 2, 4], [2, 2, 8], [2, 4, 4], [2, 16], [4, 8]]");
        numberP03 = 37;
        System.out.println("Input: n = " + numberP03 + "   Output: " + makeItBold(getFactors(numberP03).toString()) +"   Expected: []");
        numberP03 = 100;
        System.out.println("Input: n = " + numberP03 + "   Output: " + makeItBold(getFactors(numberP03).toString()) +"   Expected: [[2, 2, 5, 5], [2, 2, 25], [2, 2, 25], [2, 5, 10], [2, 50], [4, 5, 5], [4, 25], [5, 20], [10, 10]]");
        System.out.println("===========================");
        System.out.println("P04. Split A String Into The Max Number Of Unique Substrings");
        System.out.println("===========================");
        String strP04 = "aab";
        System.out.println("Input: " + strP04 +", output: "+ makeItBold(maxUniqueSplit(strP04) +"") + " Expected Output: 2");
        strP04 = "abcabc";
        System.out.println("Input: " + strP04 +", output: "+ makeItBold(maxUniqueSplit(strP04) +"") + " Expected Output: 4");
        strP04 = "aaaaa";
        System.out.println("Input: " + strP04 +", output: "+ makeItBold(maxUniqueSplit(strP04) +"") + " Expected Output: 2");



    }
}

