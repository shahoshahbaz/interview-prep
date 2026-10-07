package com.grokingcodeinterview.pattern06MergeIntervals;

import java.util.*;

import static com.Utility.makeItBold;

/*
 * Problem 1: Given a set of intervals, find out if any two intervals overlap.
 *
 * Example:
 *
 * Intervals: [[1,4], [2,5], [7,9]]
 * Output: true
 * Explanation: Intervals [1,4] and [2,5] overlap
 */
public class P001IntervalChecker {

    public static boolean doesOverlap(List<Interval> intervals){
        if (intervals == null || intervals.isEmpty()) return false;
        List<Interval> sortedInterval = new ArrayList<>(intervals);
        // sorting the interval
        Collections.sort(sortedInterval, Comparator.comparingInt(a -> a.start));

        for (int i =0; i < sortedInterval.size() -1; i++){
            if (sortedInterval.get(i+1).start <= sortedInterval.get(i).end ){
                return true;
            }

        }

        return false;




    }
    public static void main(String[] args) {

        System.out.println("==========================");
        System.out.println("P001. Interval checker..");

        System.out.println("==========================");

        // Test Case 1: Overlapping intervals
        List<Interval> intervalsP001 = Arrays.asList(
                new Interval(1, 4),
                new Interval(2, 5),
                new Interval(7, 9)
        );
        System.out.println("Intervals:" + intervalsP001 + "Does Overlap? " + makeItBold(doesOverlap(intervalsP001) + "") + ", Expected: true");


        // Test Case 2: Non-overlapping intervals
         intervalsP001 = Arrays.asList(
                new Interval(1, 2),
                new Interval(3, 4),
                new Interval(5, 6)
        );
        System.out.println("Intervals:" + intervalsP001 + "Does Overlap? " + makeItBold(doesOverlap(intervalsP001) + "") + ", Expected: false");
        // Test Case 3: Single interval (edge case)
         intervalsP001 = Arrays.asList(
                new Interval(1, 10)
        );
        System.out.println("Intervals:" + intervalsP001 + "Does Overlap? " + makeItBold(doesOverlap(intervalsP001) + "") + ", Expected: false");

        // Test Case 4: Two intervals that overlap
        intervalsP001 = Arrays.asList(
                new Interval(5, 10),
                new Interval(8, 12)
        );
        System.out.println("Intervals:" + intervalsP001 + "Does Overlap? " + makeItBold(doesOverlap(intervalsP001) + "") + ", Expected: true");


        // Test Case 5: Touching intervals (non-overlapping if inclusive-exclusive)
        intervalsP001 = Arrays.asList(
                new Interval(1, 3),
                new Interval(3, 5)
        );
        System.out.println("Intervals:" + intervalsP001 + "Does Overlap? " + makeItBold(doesOverlap(intervalsP001) + "") + ", Expected: true");


        // Test Case 6: Completely nested intervals
        intervalsP001 = Arrays.asList(
                new Interval(1, 10),
                new Interval(2, 3),
                new Interval(4, 8)
        );
        System.out.println("Intervals:" + intervalsP001 + "Does Overlap? " + makeItBold(doesOverlap(intervalsP001) + "") + ", Expected: true");

        // Test Case 7: Random order intervals
        intervalsP001 = Arrays.asList(
                new Interval(10, 20),
                new Interval(1, 5),
                new Interval(4, 6)
        );
        System.out.println("Intervals:" + intervalsP001 + "Does Overlap? " + makeItBold(doesOverlap(intervalsP001) + "") + ", Expected: true");

    }
}


