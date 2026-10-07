package com.grokingcodeinterview.pattern22GreedyAlgorithms;

import java.util.Arrays;

/*
  max number of non-overlapping intervalsNon-overlapping Intervals (Activity Selection)
    Problem: Given a list of intervals, find the maximum number of non-overlapping intervals you can select. Two intervals overlap if they share any point in common (touching at an endpoint counts as non-overlapping, i.e. [1,2] and [2,3] do NOT overlap).
   Input: intervals = [[1,2],[2,3],[3,4],[1,3]]     Output: 3
    Explanation: Remove [1,3] and you're left with [1,2],[2,3],[3,4] â€” all non-overlapping.
    Input: intervals = [[1,2],[1,2],[1,2]]     Output: 1
    Explanation: All three overlap each other, so you can only keep one.
    Input: intervals = [[1,2],[2,3]]     Output: 2
    Explanation: They only touch at point 2, which doesn't count as overlapping.
 */
public class P0MaxNumberOfNonOverlappingIntervals {
    public static int maxNonOverlapping(int[][] intervals){
        // sort key by end time
        Arrays.sort(intervals, (a, b)->a[1] - a[2]);


        int counter =0;
        int lastEnd = Integer.MIN_VALUE;
        // DECISION UNIT: take it or skip it each interval, one pass
        for(int[] interval: intervals){
            int start = interval[0];
            int end = interval[1];
            // local rules: does it start after the last one taken ended
            if(start>= lastEnd){
                // commit: update and never revisit this choice
                counter++;
                lastEnd = end;
            }
            // else: skip, permanently - no backtracking
        }
         return counter;
    }
}

