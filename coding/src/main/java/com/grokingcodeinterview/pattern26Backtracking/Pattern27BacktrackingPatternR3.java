package com.grokingcodeinterview.pattern26Backtracking;

import java.util.*;

import static com.Utility.makeItBold;

public class Pattern27BacktrackingPatternR3 {
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

    /**
     * substring (i, j)  : substring from i to j-1
     */

    public static int maxUniqueSplit(String str){
        int n = str.length();
        Set<String> set = new HashSet<>();

        return dfs(str, set, 0);

    }

    private static int dfs(String str, Set<String> set, int start){
        if (start == str.length()) return set.size();
        int maxCount =0;
        for (int i = start + 1; i<= str.length(); i++){

            String substring = str.substring(start, i);


            if  (set.add(substring)){
                maxCount = Math.max(maxCount, dfs(str, set, i));
                set.remove(substring);
            }
        }

        return maxCount;
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
    public static List<List<Integer>> getFactors(int num){
        List<List<Integer>> allComb = new ArrayList<>();
        return getFactors(num, 2,  new ArrayList<>(), allComb);

    }
    public static List<List<Integer>> getFactors(int num, int start, List<Integer> currPath, List<List<Integer>> allComb){

        for (int i =start; i<= (int)Math.sqrt(num); i++){
            if(num % i == 0){
                currPath.add(i);
                List<Integer> copyCurrPath = new ArrayList<>(currPath);
                copyCurrPath.add(num /i);
                allComb.add(copyCurrPath);

                getFactors(num /i, i, currPath, allComb);

                currPath.remove(currPath.size() -1);
            }
        }

        return allComb;
    }
    /*
  Problem Statement
    Given an m x n grid of characters board and a string word, return true if the word exists in the grid.
        The word can be constructed from letters of sequentially adjacent cells,
        where adjacent cells are horizontally or vertically neighboring.
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
        for (int i =0; i< board.length; i++){
            for (int j =0; j<board[0].length; j++){
                if(dfs(board, word, i,j, 0)){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean dfs(char[][] board, String word, int r, int c, int index){
       // base case


       if (r<0 || r>= board.length ||c<0 || c>= board[0].length|| board[r][c] != word.charAt(index)) return false;

       if(index == word.length() -1 ) return true;
       char temp = board[r][c];
       board[r][c] ='/';
       int[][] directions = {{1, 0},{-1, 0},{0, 1},{0, -1} };
       boolean found = false;
       for (int[] dir: directions){
           int nr = r+ dir[0];
           int nc = c + dir[1];
           found |= dfs(board, word, nr, nc, index+1);


       }

       board[r][c] = temp;
       return found;


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
    public static List<List<Integer>> combinationSum(int [] nums, int targetSum){

        List<List<Integer>> allPath = new ArrayList<>();

        if (nums.length ==0 ) return allPath;

        List<Integer> currPath = new ArrayList<>();

        // backTrack

        backTrack(nums, 0, targetSum, currPath, allPath);
        return allPath;


    }

    public static void backTrack(int[] nums, int start, int remaining, List<Integer> currPath, List<List<Integer>> allPaths ){

        if (remaining == 0){
            allPaths.add(new ArrayList<>(currPath));
            return;
        }

        if(remaining<0 ) return;

        for (int i = start; i<nums.length; i++){
            int curr = nums[i];
            currPath.add(curr);
            backTrack(nums, i, remaining- curr, currPath, allPaths);

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
        System.out.println("Input: n = " + numberP03 + "   Output: " + makeItBold(getFactors(numberP03).toString()) +" " +
                "  Expected: [[2, 2, 5, 5], [2, 2, 25], [2, 2, 25], [2, 5, 10], [2, 50], [4, 5, 5], [4, 25], [5, 20], [10, 10]]");

        System.out.println("===========================");
        System.out.println("P04. Split A String Into The Max Number Of Unique Substrings");
        System.out.println("===========================");
        String strP04 = "aab";
        System.out.println("Input: " + strP04 +", output: "+ makeItBold(maxUniqueSplit(strP04) +"") + " Expected Output: 2");
        strP04 = "abcabc";
        System.out.println("Input: " + strP04 +", output: "+ makeItBold(maxUniqueSplit(strP04) +"") + " Expected Output: 4");
        strP04 = "aaaaa";
        System.out.println("Input: " + strP04 +", output: "+ makeItBold(maxUniqueSplit(strP04) +"") + " Expected Output: 2");

//        System.out.println("===========================");
//        System.out.println("P04. Split A String Into The Max Number Of Unique Substrings");
//        System.out.println("===========================");
//        String strP04 = "aab";
//        System.out.println("Input: " + strP04 +", output: "+ makeItBold(maxUniqueSplit(strP04) +"") + " Expected Output: 2");
//        strP04 = "abcabc";
//        System.out.println("Input: " + strP04 +", output: "+ makeItBold(maxUniqueSplit(strP04) +"") + " Expected Output: 4");
//        strP04 = "aaaaa";
//        System.out.println("Input: " + strP04 +", output: "+ makeItBold(maxUniqueSplit(strP04) +"") + " Expected Output: 2");

    }


}

