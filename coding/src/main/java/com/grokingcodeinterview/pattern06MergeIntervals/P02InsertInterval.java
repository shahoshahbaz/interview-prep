package com.grokingcodeinterview.pattern06MergeIntervals;

import java.util.ArrayList;
import java.util.List;

import static com.Utility.makeItBold;

/*
 Given a list of non-overlapping intervals sorted by their start time,
  insert a given interval at the correct position and merge all necessary intervals to produce a list that has only mutually exclusive intervals.
 * Example 1: Input: Intervals=[[1,3], [5,7], [8,12]], New Interval=[4,6]
 Output: [[1,3], [4,7], [8,12]]
 Explanation: After insertion, since [4,6] overlaps with [5,7], we merged them into one [4,7].
 */

/**
 *  Input: Intervals=[[1,3], [5,7], [8,12]], New Interval=[4,6]
 *  [1, 3] [4, 6] [5, 7]
 *
 *  Input: Intervals=[[1s,3e], [5,7], [8,12]], New Interval=[4s,6e]
 *
 *
 *  max(newInterval.end, currentInterval.end)
 *  min(newInterval.start, currentInterval.start
 *
 */
public class P02InsertInterval {
    public  static List<Interval> insertInterval(List<Interval> intervals, Interval newInterval){
        List<Interval> result = new ArrayList<>();
        // the list already soreted

        int i =0;
        // step1: add all all not overlaping interval
        while(i< intervals.size() && intervals.get(i).end < newInterval.start){
            result.add(intervals.get(i));
            i++;
        }

        // step2: Merge all overlapping intervals
        while (i< intervals.size() && intervals.get(i).start <= newInterval.end){
            newInterval.start = Math.min (newInterval.start, intervals.get(i).start);
            newInterval.end = Math.max(newInterval.end,  intervals.get(i).end);
            i++;
        }

        // step 3: Add the merged interval

        result.add(newInterval);

        // step 4: Add remaining intervals
        while (i< intervals.size()){
            result.add(intervals.get(i));
            i++;
        }








    return result;
    }

    public static void main(String[] args) {
        System.out.println("==========================");
        System.out.println("P02. Insert Interval..");
        System.out.println("==========================");


         // Test Case 1: Basic insertion and merging
        
        List<Interval> intervalsP02 = new ArrayList<>(List.of(new Interval(1, 3),new Interval(5, 7),new Interval(8, 12) ));
        Interval newIntervalP02 = new Interval(4, 6);
         List<Interval> resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("Input: " + intervalsP02 + " ,new Interval:"+ newIntervalP02 +
                " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [[1, 3], [4, 7], [8, 12]]");
        // Expected: [1, 3], [4, 7], [8, 12]

        // Test Case 2: New interval before all
        intervalsP02 = new ArrayList<>(List.of(new Interval(5, 7),new Interval(8, 12)));
        newIntervalP02 = new Interval(1, 3);
         resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("Input: " + intervalsP02 + " ,new Interval:"+ newIntervalP02 +
                " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [[1, 3], [5, 7], [8, 12]]");

        // Expected: [1, 3], [5, 7], [8, 12]

        // Test Case 3: New interval after all
         intervalsP02 = new ArrayList<>(List.of(
            new Interval(1, 3),
            new Interval(5, 7)
        ));
        newIntervalP02 = new Interval(8, 10);
         resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("Input: " + intervalsP02 + " ,new Interval:"+ newIntervalP02 +
                " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [[1, 3], [5, 7], [8, 10]]");
        // Expected: [1, 3], [5, 7], [8, 10]

        // Test Case 4: New interval overlaps all
         intervalsP02 = new ArrayList<>(List.of(
            new Interval(1, 3),
            new Interval(5, 7),
            new Interval(8, 12)
        ));
        newIntervalP02 = new Interval(0, 15);
         resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("Input: " + intervalsP02 + " ,new Interval:"+ newIntervalP02 +
                " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [[0, 15]]");
        // Expected: [0, 15]

        // Test Case 5: New interval overlaps some
        intervalsP02 = new ArrayList<>(List.of(
            new Interval(1, 3),
            new Interval(5, 7),
            new Interval(8, 12)
        ));
         newIntervalP02 = new Interval(6, 10);
         resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("Input: " + intervalsP02 + " ,new Interval:"+ newIntervalP02 +
                " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [[1, 3], [5, 12]]");


        intervalsP02 = new ArrayList<>(List.of(new Interval(5,8)));

        newIntervalP02 = new Interval(1, 6);
        resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("Input: " + intervalsP02 + " ,new Interval:"+ newIntervalP02 + " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [[1, 8]]");




    }
}

