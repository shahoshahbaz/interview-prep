package com.grokingcodeinterview.pattern22GreedyAlgorithms;

import java.util.Arrays;
import java.util.Comparator;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a collection of pairs where each pair contains two elements [a, b] and a < b,
find the maximum length of a chain you can form using pairs.
A pair [a, b] can follow another pair [c, d] in the chain if b < c.
You can select pairs in any order and don't need to use all the given pairs.

Example 1: Input: [[1,2], [3,4], [2,3]]  Expected Output: 2
Justification: The longest chain is [1,2] -> [3,4]. The chain [1,2] -> [2,3] is invalid because 2 is not smaller than 2.
Example 2:  Input: [[5,6], [1,2], [8,9], [2,3]] Expected Output: 3
Justification: The chain can be [1,2] -> [5,6] -> [8,9] or [2,3] -> [5,6] -> [8, 9].
Example 3: Input: [[7,8], [5,6], [1,2], [3,5], [4,5], [2,3]] Expected Output: 3
Justification: The longest possible chain is formed by chaining [1,2] -> [3,5] -> [7,8].
Constraints:
n == pairs.length
1 <= n <= 1000
-1000 <= lefti < righti <= 1000
 */

/**
 * [[1,2], [3,4], [2,3]]
 * sort by second element : [1,2], [2,3], [3,4]
 * currentEnd =2
 * if (currentEnd< pairs[0]p[i]
 * i =1  2< 2 no
 * i = 1 2<3 yes, counter=2, currend = 4
 */
public class P02MaximumLengthOfPairChain {
    public static int findLongestChain(int[][] pairs) {

        // step 1: sort paris by second elemnets(b) ascending
        Arrays.sort(pairs, Comparator.comparingInt(pair -> pair[1]));
        int lastEnd = pairs[0][1];
        int count =1;

        // step2: iterate and apply greedy choice
        for(int i =1; i< pairs.length; i++){
            if( lastEnd< pairs[i][0]){
                count++;
                lastEnd = pairs[i][1];
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("P02. Maximum Length Of Pair Chain... ");
        System.out.println("===================================");
        int[][] pairsP02 = {{1,2}, {3,4}, {2,3}};
        System.out.println("Input: " + Arrays.deepToString(pairsP02) +
                ", output:" + makeItBold(findLongestChain(pairsP02) +"")+ ", expected: 2");
        pairsP02 = new int[][]{{5,6}, {1,2}, {8,9}, {2,3}};
        System.out.println("Input: " + Arrays.deepToString(pairsP02) +
                ", output:" + makeItBold(findLongestChain(pairsP02) +"")+ ", expected: 3");
        pairsP02 = new int[][]{{7,8}, {5,6}, {1,2}, {3,5}, {4,5}, {2,3}};
        System.out.println("Input: " + Arrays.deepToString(pairsP02) +
                ", output:" + makeItBold(findLongestChain(pairsP02) +"")+ ", expected: 3");
        pairsP02 = new int[][]{{1,3}, {2,4}, {3,5}, {6,7}, {8,9}};
        System.out.println("Input: " + Arrays.deepToString(pairsP02) +
                ", output:" + makeItBold(findLongestChain(pairsP02) +"")+ ", expected: 3");
    }
}
