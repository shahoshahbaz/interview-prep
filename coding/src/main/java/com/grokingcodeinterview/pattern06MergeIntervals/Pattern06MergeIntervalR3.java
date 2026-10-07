package com.grokingcodeinterview.pattern06MergeIntervals;

import java.util.*;

import static com.Utility.makeItBold;

public class Pattern06MergeIntervalR3 {

   /*
     Given a list of appointments, find all the conflicting appointments.
      Example:    Appointments: [[4,5], [2,3], [3,6], [5,7], [7,8]]    Output:[ [4,5] , [3,6]], [[3,6]  [5,7]] ] .
      Example 2: Appointments: [[1,2], [3,4], [5,6]] Output: []
      Example: Appointments: [[1,5], [2,6], [3,7]] Output: [[1,5], [2,6]], [[1,5], [3,7]], [[2,6], [3,7]]
 */

    /**
     * [[4,5], [2,3], [3,6], [5,7], [7,8]]
     * [[2,3], [3,6], [4,5],  [5,7], [7,8]]
     * [[1,5], [2,6], [3,7]]
     */

    public static List<List<Interval>> findConflictingAppointments(List<Interval> intervals){
        List<List<Interval>> result = new ArrayList<>();
        if (intervals == null || intervals.isEmpty()    ) return result;

        intervals.sort((a, b )-> a.start - b.start);

        for (int i =0; i<intervals.size() -1; i++){
            Interval current= intervals.get(i);
            for (int j = i+1; j<intervals.size() ; j++){
                Interval next = intervals.get(j);
                if(doesOverlap(current, next)){
                    List<Interval> conflictIntervals = new ArrayList<>();
                    conflictIntervals.add(current);
                    conflictIntervals.add(next);

                    result.add(conflictIntervals);
                }
            }
        }

        return result;

    }


    /*
    Given an array of intervals representing â€˜Nâ€™ appointments,
     find out if a person can attend all the appointments.
    Example 1: Appointments: [[1,4], [2,5], [7,9]] Output: false
    Explanation: Since [1,4] and [2,5] overlap, a person cannot attend both of these appointments.
    Example 2:  Appointments: [[6,7], [2,4], [13, 14], [8,12], [45, 47]] Output: true
    Explanation: None of the appointments overlap, therefore a person can attend all of them.
    Example 3: Appointments: [[4,5], [2,3], [3,6]] Output: false
    Explanation: Since [4,5] and [3,6] overlap, a person cannot attend both of these appointments.
    Constraints:
    1 <= intervals.length <= 10^4
    intervals[i].length == 2
    0 <= starti < endi <= 10^6
    */

    /**
     *  [[4,5], [2,3], [3,6]]
     *  [ [2,3], [3,6], [4,5]]
     */
    public static boolean canAttendAllAppointments(Interval[] intervals){
        if (intervals.length == 1) return true;

        Arrays.sort(intervals, (a, b) ->a.start - b.start);

        for (int i = 0; i< intervals.length -1; i++){


        }

        return true;


    }

    private static boolean doesOverlap(Interval interval1, Interval interval2){
        return interval1.end > interval2.start;
    }



    /*
     * Problem 1: Given a set of intervals, find out if any two intervals overlap.
     *
     * Example:
     *
     * Intervals: [[1,4], [2,5], [7,9]]
     * Output: true
     * Explanation: Intervals [1,4] and [2,5] overlap
     */


    public static boolean doesOverlap(List<Interval> intervals){
        if (intervals == null || intervals.isEmpty() || intervals.size() ==1 ) return false;

        intervals.sort((a, b) ->a.start - b.start);

        for (int i= 0; i< intervals.size() -1; i++){
            Interval currInterval = intervals.get(i);
            Interval nextInterval = intervals.get(i+1);
            if (nextInterval.start<= currInterval.end){
                return true;
            }
        }

        return false;
    }
    /*
problem Statement
 Given a list of intervals,
  merge all the overlapping intervals to produce a * list that has only mutually exclusive intervals.
  Example 1:  Intervals: [[1,4], [2,5], [7,9]]  * Output: [[1,5], [7,9]]
  Explanation: Since the first two intervals [1,4] and [2,5] overlap, we merged them into one [1,5].
 */

    public static List<Interval> merge(List<Interval> intervals){
        if (intervals == null || intervals.isEmpty()||  intervals.size() ==1) return intervals;
        List<Interval> result = new ArrayList<>();

        Collections.sort(intervals, (a, b) -> a.start - b.start);
        result.add(intervals.get(0));
//        int start = intervals.get(0).start;
//        int end = intervals.get(0).end;

        for (int i =1; i< intervals.size(); i++){
            int currStart = intervals.get(i).start;
            int currEnd = intervals.get(i).end;

            int lastIndex = result.size() -1;
            if(currStart <= result.get(lastIndex).end){
                result.get(lastIndex).end = Math.max(currEnd, result.get(lastIndex).end);
            }else{
                result.add(intervals.get(i));
            }
        }
        return result;

    }
     /*
    Given a list of non-overlapping intervals sorted by their start time,
    insert a given interval at the correct position and merge all necessary intervals to produce a list that has only mutually exclusive intervals.
    Example 1:     Input: Intervals=[[1,3], [5,7], [8,12]], New Interval=[4,6]
    Output: [[1,3], [4,7], [8,12]]
    Explanation: After insertion, since [4,6] overlaps with [5,7], we merged them into one [4,7].
    */




    public static List<Interval> insertInterval(List<Interval> intervals, Interval newInterval){
        LinkedList<Interval> result = new LinkedList<>();

        if (intervals == null || intervals.isEmpty() ) {
            result.add(newInterval);
            return result;
        }

        // now we have 3 cases
        // 2. the interval that overlap with interval

//        for (int i = 0; i< intervals.size(); i++){
//            Interval currInterval = intervals.get(i);
//
            int index =0;
            int size = intervals.size();
        // 1. any intervals that comes before
            while (index< size && intervals.get(index).end< newInterval.start ){
                result.add(intervals.get(index));
                index ++;
            }

            // add the new interval
            result.add(newInterval);

            while (index<size &&  result.getLast().end>= intervals.get(index).start){
                result.getLast().end = Math.max(intervals.get(index).end, result.getLast().end );
                index++;
            }

            while (index< size ){
                result.add(intervals.get(index));
                index++;
            }
            return result;

    }

    /*
    Given two lists of intervals,
    find the intersection of these two lists.
    Each list consists of disjoint intervals sorted on their start time.

    Example 1: Input: arr1=[[1, 3], [5, 6], [7, 9]], arr2=[[2, 3], [5, 7]]
    Output: [2, 3], [5, 6], [7, 7]
    Explanation: The output list contains the common intervals between the two lists.
    Example 2:

    Input: arr1=[[1, 3], [5, 7], [9, 12]], arr2=[[5, 10]]
    Output: [5, 7], [9, 10]
    Explanation: The output list contains the common intervals between the two lists.
    Constraints:

    0 <= arr1.length, arr2.length <= 1000
    arr1.length + arr2.length >= 1
    0 <= start i < end i <= 10^99
    end i < start i+1
    0 <= start j < end j <= 10^9
    end j < start j+1
    */

    /**
     * arr1=[[1, 3], [5, 6], [7, 9]], arr2=[[2, 3], [5, 7]]
     * max(1, 2) and min(3, 3) => [2, 3]
     * move the one has bigger end
     */


    public static List<Interval> findIntersection(Interval[] intervals1, Interval[] intervals2){
        int index1 = 0;
        int index2 = 0;
        List<Interval> result = new ArrayList<>();

        while (index1< intervals1.length && index2< intervals2.length){

            int start = Math.max(intervals1[index1].start, intervals2[index2].start);
            int end = Math.min(intervals1[index1].end, intervals2[index2].end);

            if(start<= end){
                result.add(new Interval(start, end));
            }

            if (intervals1[index1].end< intervals2[index2].end){
                index1 ++;
            }else{
                index2++;
            }




        }

        return result;
    }
    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("P03. Interval Intersection");
        System.out.println("==============================================================");

        Interval[] intervalsAP03 =  { new Interval(1, 3), new Interval(5, 6), new Interval(7, 9) };

        Interval[] intervalsBP03 = { new Interval(2, 3), new Interval(5, 7) };
        List<Interval> result = findIntersection(intervalsAP03, intervalsBP03);
        System.out.println("Inputs: " + Arrays.toString(intervalsAP03) +"," + Arrays.toString(intervalsBP03) + " ,output: " +
                makeItBold(result.toString()) + "Expected: [[2, 3], [5, 6], [7, 7]]");

        intervalsAP03 = new Interval[] { new Interval(1, 3), new Interval(5, 7), new Interval(9, 12) };
        intervalsBP03 =  new Interval[]{ new Interval(5, 10) };

        result = findIntersection(intervalsAP03, intervalsBP03);
        System.out.println("Inputs: " + Arrays.toString(intervalsAP03) +"," + Arrays.toString(intervalsBP03) + " ,output: " +
                makeItBold(result.toString()) + " Expected: [[5, 7], [9, 10]]");


        intervalsAP03= new Interval[]{ new Interval(1, 3), new Interval(5, 7), new Interval(9, 12) };
        intervalsBP03 = new Interval[] { new Interval(2, 4), new Interval(6, 8), new Interval(11, 13) };
        result = findIntersection(intervalsAP03, intervalsBP03);
        System.out.println("Inputs: " + Arrays.toString(intervalsAP03) +"," + Arrays.toString(intervalsBP03) + " ,output: " +
                makeItBold(result.toString()) + " Expected: [[2, 3], [6, 7], [11, 12]]");


        System.out.println("==========================");
        System.out.println("P02. Insert Interval..");
        System.out.println("==========================");
        // Test Case 1: Basic insertion and merging
        List<Interval> intervalsP02 = new ArrayList<>(List.of(new Interval(1, 3),new Interval(5, 7),new Interval(8, 12) ));
        Interval newIntervalP02 = new Interval(4, 6);
        List<Interval> resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("Input: " + intervalsP02 + " ,new Interval:"+ newIntervalP02 +
                " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [1, 3], [4, 7], [8, 12]");
        // Expected: [1, 3], [4, 7], [8, 12]

        // Test Case 2: New interval before all
        intervalsP02 = new ArrayList<>(List.of(new Interval(5, 7),new Interval(8, 12)));
        newIntervalP02 = new Interval(1, 3);
        resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("Input: " + intervalsP02 + " ,new Interval:"+ newIntervalP02 +
                " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [1, 3], [5, 7], [8, 12]");

        // Expected: [1, 3], [5, 7], [8, 12]

        // Test Case 3: New interval after all
        intervalsP02 = new ArrayList<>(List.of(
                new Interval(1, 3),
                new Interval(5, 7)
        ));
        newIntervalP02 = new Interval(8, 10);
        resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("Input: " + intervalsP02 + " ,new Interval:"+ newIntervalP02 +
                " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [1, 3], [5, 7], [8, 10]");
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
                " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [0, 15]");
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
                " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [1, 3], [5, 12]");
        // Expected: [1, 3], [5, 12]


        intervalsP02 = new ArrayList<>(List.of(new Interval(5,8)));


        newIntervalP02 = new Interval(1, 6);
        resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("Input: " + intervalsP02 + " ,new Interval:"+ newIntervalP02 + " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [1, 8]");


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
        System.out.println("==========================================================");
        System.out.println("P04. Conflicting Appointments");
        System.out.println("==========================================================");
        Interval[] intervals04 = {new Interval(1,4), new Interval(2,5), new Interval(7,9)};

        System.out.println("Input: " + Arrays.toString(intervals04) + " => Can attend all appointments: " + canAttendAllAppointments(intervals04) + ", Expected: false");
        intervals04 = new Interval[]{new Interval(1,4), new Interval(2,5), new Interval(7,9)};
        System.out.println("Input: " + Arrays.toString(intervals04) + " => Can attend all appointments: " + canAttendAllAppointments(intervals04) + ", Expected: false");
        intervals04 = new Interval[]{new Interval(6,7), new Interval(2,4), new Interval(13, 14), new Interval(8,12), new Interval(45, 47)};
        System.out.println("Input: " + Arrays.toString(intervals04) + " => Can attend all appointments: " + canAttendAllAppointments(intervals04) + ", Expected: true");
        intervals04 = new Interval[]{new Interval(4,5), new Interval(2,3), new Interval(3,6)};
        System.out.println("Input: " + Arrays.toString(intervals04) + " => Can attend all appointments: " + canAttendAllAppointments(intervals04) + ", Expected: false");
        intervals04 = new Interval[]{new Interval(1,2), new Interval(3,4), new Interval(5,6)};
        System.out.println("Input: " + Arrays.toString(intervals04) + " => Can attend all appointments: " + canAttendAllAppointments(intervals04) + ", Expected: true");
        intervals04 = new Interval[]{new Interval(1,5), new Interval(5,10), new Interval(10,15)};
        System.out.println("Input: " + Arrays.toString(intervals04) + " => Can attend all appointments: " + canAttendAllAppointments(intervals04) + ", Expected: true");
        //Example 2:  Appointments: [[6,7], [2,4], [13, 14], [8,12], [45, 47]] Output: true
        intervals04 = new Interval[]{new Interval(6,7), new Interval(2,4), new Interval(13, 14), new Interval(8,12), new Interval(45, 47)};
        System.out.println("Input: " + Arrays.toString(intervals04) + " => Can attend all appointments: " + canAttendAllAppointments(intervals04) + ", Expected: true");

        System.out.println("==========================================================");
        System.out.println("P05. Find All The Conflicting Appointments");
        System.out.println("==========================================================");

        List<Interval> appointments05 = new ArrayList<>(Arrays.asList(
                new Interval(4, 5),
                new Interval(2, 3),
                new Interval(3, 6),
                new Interval(5, 7),
                new Interval(7, 8)
        ));
        List<List<Interval>> conflicts = findConflictingAppointments(appointments05);
        System.out.println("Input:" + appointments05 + ", Conflicts: " + makeItBold(conflicts.toString()) +", Expected: [[[3, 6], [4, 5]], [[3, 6], [5, 7]]]");




        // Test Case 2: No conflicts

        appointments05 = new ArrayList<>(Arrays.asList(
                new Interval(4, 5),
                new Interval(2, 3),
                new Interval(3, 6),
                new Interval(5, 7),
                new Interval(7, 8)
        ));
        conflicts = findConflictingAppointments(appointments05);
        System.out.println("Input:" + appointments05 + ", conflicts: " + makeItBold(conflicts.toString()) +", Expected: [[[3, 6], [4, 5]], [[3, 6], [5, 7]]]");


        // Test Case 3: All appointments conflict
        appointments05 = new ArrayList<>(Arrays.asList(
                new Interval(4, 5),
                new Interval(2, 3),
                new Interval(3, 6),
                new Interval(5, 7),
                new Interval(7, 8)
        ));
        conflicts = findConflictingAppointments(appointments05);
        System.out.println("Input:" + appointments05 + ", conflicts: " + makeItBold(conflicts.toString()) +", Expected: [[[3, 6], [4, 5]], [[3, 6], [5, 7]]]");



        // Test Case 4: Edge case - empty list
        appointments05 = new ArrayList<>();
        conflicts = findConflictingAppointments(appointments05);
        System.out.println("Input:" + appointments05 + ", conflicts: " + makeItBold(conflicts.toString()) +", Expected: []");


        appointments05 = new ArrayList<>(Arrays.asList(
                new Interval(3, 6)
        ));
        conflicts = findConflictingAppointments(appointments05);
        System.out.println("Input:" + appointments05 + ", conflicts: " + makeItBold(conflicts.toString()) +", Expected: []");




        // Test Case 6: Back-to-back appointments (no conflict)
        appointments05 = new ArrayList<>(Arrays.asList(
                new Interval(1, 3),
                new Interval(3, 5),
                new Interval(5, 7)
        ));
        conflicts = findConflictingAppointments(appointments05);
        System.out.println("Input:" + appointments05 + ", conflicts: " + makeItBold(conflicts.toString()) +", Expected: []");







    }
}

