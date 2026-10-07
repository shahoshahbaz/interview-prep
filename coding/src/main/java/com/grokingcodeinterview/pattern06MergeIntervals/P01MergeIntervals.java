package com.grokingcodeinterview.pattern06MergeIntervals;

import java.util.*;

import static com.Utility.makeItBold;

/*
problem Statement
 Given a list of intervals,
  merge all the overlapping intervals to produce a * list that has only mutually exclusive intervals.
  Example 1:  Intervals: [[1,4], [2,5], [7,9]]  * Output: [[1,5], [7,9]]
  Explanation: Since the first two intervals [1,4] and [2,5] overlap, we merged them into one [1,5].
 */

/**
 *   Intervals: [[1,4], [2,5], [7,9]]
 */

public class P01MergeIntervals {

    public static List<Interval> merge(List<Interval> intervals){
        if (intervals == null || intervals.isEmpty()) return null;
        // sort the intervals
        intervals.sort((a, b) -> Integer.compare(a.start, b.start));
        LinkedList<Interval> result = new LinkedList<>();
        result.add(intervals.get(0));
        for (int i =1; i< intervals.size(); i++){
            // compare the the nextInterval Start with currentMergeOne
            Interval nextInterval = intervals.get(i);
            if ( nextInterval.start <= result.getLast().end  ){
                result.getLast().end = Math.max(result.getLast().end, nextInterval.end);
            }else{
                result.add(nextInterval);
            }

        }
        return result;

    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("P01 Merge Intervals");
        System.out.println("==================================================================");

        // Test Case 1: Basic interval merging

        List<Interval> intervals1 = new ArrayList<>(Arrays.asList(
            new Interval(1, 3),
            new Interval(2, 5),
            new Interval(7, 9)
        ));
        System.out.println("Input: "+ intervals1 +", output:" + makeItBold(merge(intervals1).toString()) + ", expected: [[1, 5], [7, 9]]");

        // Test Case 2: All intervals merge into one
        List<Interval> intervals2 = new ArrayList<>(Arrays.asList(
            new Interval(1, 4),
            new Interval(2, 6),
            new Interval(3, 8),
            new Interval(5, 9)
        ));
        System.out.println("Input: "+ intervals2 +", output:" + makeItBold(merge(intervals2).toString()) + ", expected: [[1, 9]]");

        // Test Case 3: No overlapping intervals
        List<Interval> intervals3 = new ArrayList<>(Arrays.asList(
            new Interval(1, 2),
            new Interval(3, 4),
            new Interval(5, 6),
            new Interval(7, 8)
        ));
        System.out.println("Input: "+ intervals3 +", output:" + makeItBold(merge(intervals3).toString()) + ", expected: [[1, 2], [3, 4], [5, 6], [7, 8]]");

        // Test Case 4: One interval fully contained within another
        List<Interval> intervals4 = new ArrayList<>(Arrays.asList(
            new Interval(1, 5),
            new Interval(2, 3),
            new Interval(6, 9)
        ));
        System.out.println("Input: "+ intervals4 +", output:" + makeItBold(merge(intervals4).toString()) + ", expected: [[1, 5], [6, 9]]");

        // Test Case 5: Intervals that touch but don't overlap
        List<Interval> intervals5 = new ArrayList<>(Arrays.asList(
            new Interval(1, 3),
            new Interval(3, 5),
            new Interval(5, 7)
        ));
        System.out.println("Input: "+ intervals5 +", output:" + makeItBold(merge(intervals5).toString()) + ", expected: [[1, 7]]");

        // Test Case 6: Empty input list
        List<Interval> intervals6 = new ArrayList<>();
        List<Interval> merged6 = merge(intervals6);
        System.out.println("Input: "+ intervals6 +", output:" + makeItBold(String.valueOf(merged6)) + ", expected: []");

        // Test Case 7: Single interval
        List<Interval> intervals7 = new ArrayList<>(Arrays.asList(
            new Interval(2, 5)
        ));
        System.out.println("Input: "+ intervals7 +", output:" + makeItBold(merge(intervals7).toString()) + ", expected: [[2, 5]]");

        // Test Case 8: Unsorted intervals
        List<Interval> intervals8 = new ArrayList<>(Arrays.asList(
            new Interval(6, 8),
            new Interval(1, 3),
            new Interval(2, 4),
            new Interval(5, 7)
        ));
        System.out.println("Input: "+ intervals8 +", output:" + makeItBold(merge(intervals8).toString()) + ", expected: [[1, 4], [5, 8]]");
    }


}

