package com.grokingcodeinterview.pattern06MergeIntervals;

import java.util.*;

import static com.Utility.makeItBold;

public class Pattern06MergeIntervalR2 {

    /*
 * Given a list of appointments, find all the conflicting appointments.

Example:

Appointments: [[4,5], [2,3], [3,6], [5,7], [7,8]]
Output:
[4,5] and [3,6] conflict.
[3,6] and [5,7] conflict.
 */

    public static  List<List<Interval>> findConflictingAppointments(List<Interval> intervals){
        List<List<Interval>> result = new ArrayList<>();
        if (intervals == null) return result;

        intervals.sort((a,b) -> a.start - b.start);


        for (int i =0; i< intervals.size() -1; i++){
            for (int j = i+1; j< intervals.size(); j++){
                if (intervals.get(i).end> intervals.get(j).start){
                    result.add(Arrays.asList(intervals.get(i), intervals.get(j)));
                    break;
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
1 <= intervals.length <= 104
intervals[i].length == 2
0 <= starti < endi <= 106
 */

    /**
     * Appointments: [[1,4], [2,5], [7,9]] Output: false
     * i.end> i.start
     */

    public static boolean canAttendAllAppointments (Interval[] intervals )
    {
        // sorthe array,
        Arrays.sort(intervals, (a, b) ->a.start - b.start);

        for (int i =0; i<intervals.length-1;i++){
            if (intervals[i].end> intervals[i+1].start){
                return false;
            }

        }
return true;
    }
    /*
    Given a list of non-overlapping intervals sorted by their start time,
    insert a given interval at the correct position and merge all necessary intervals to produce a list that has only mutually exclusive intervals.
    Example 1:
    Input: Intervals=[[1,3], [5,7], [8,12]], New Interval=[4,6]
    Output: [[1,3], [4,7], [8,12]]
    Explanation: After insertion, since [4,6] overlaps with [5,7], we merged them into one [4,7].
    */

    /**
     * sorted by started time
     * insert the correct postion
     * [[1,3], [5,7], [8,12]], New Interval=[4,6]
     * step1. insert all interval that small than the new interval
     * [[1,3]
     * step. 2 insert the new interval
     *
     *
     * step 3 merge the interval.
     * [[1,3], [4,6]] vs [5, 7]
     */
    public  static List<Interval> insertInterval(List<Interval> intervals, Interval newInterval){

        List<Interval> result = new ArrayList<>();
        if (intervals.isEmpty()) {
            result.add(newInterval);
            return result;
        }

        // step1
        int i =0;
        while (i< intervals.size() && intervals.get(i).end< newInterval.start){

                result.add(intervals.get(i));
                i++;

        }

//        step 2.
        result.add(newInterval);

        //merge the result
        while(i< intervals.size()){
            Interval lastInterval = result.get(result.size()-1);
            if (lastInterval.end> intervals.get(i).start){
                int newStart = Math.min(lastInterval.start, intervals.get(i).start);
                int newEnd = Math.max(lastInterval.end, intervals.get(i).end);
                result.get(result.size() -1).end = newEnd;
                result.get(result.size() -1).start = newStart;
                i++;
            }else {
                break;
            }

        }

        while (i<intervals.size()){
            result.add(intervals.get(i));
            i++;
        }

        return result;


    }


    /*
    Given two lists of intervals,
    find the intersection of these two lists.
    Each list consists of disjoint intervals sorted on their start time.

            Example 1:

    Input: arr1=[[1, 3], [5, 6], [7, 9]], arr2=[[2, 3], [5, 7]]
    Output: [2, 3], [5, 6], [7, 7]
    Explanation: The output list contains the common intervals between the two lists.
            Example 2:

    Input: arr1=[[1, 3], [5, 7], [9, 12]], arr2=[[5, 10]]
    Output: [5, 7], [9, 10]
    Explanation: The output list contains the common intervals between the two lists.
            Constraints:

            0 <= arr1.length, arr2.length <= 1000
    arr1.length + arr2.length >= 1
            0 <= starti < endi <= 109
    endi < starti+1
            0 <= startj < endj <= 109
    endj < startj+1
  */

    /**
     * Input: arr1=[[1, 3], [5, 6], [7, 9]], arr2=[[2, 3], [5, 7]]
     *     Output: [2, 3], [5, 6], [7, 7]
     *     sorted
     *     rule of intersectoin
     *     if (index1,end> index2. start
     *
     *     and rule of moving
     *     smaller or equal end move first
     *
     */

    public static  List<Interval> findIntersection(Interval[] intervals1, Interval[] intervals2){

        int n1 = intervals1.length;
        int n2 = intervals2.length;
        int index1 =0;
        int index2 = 0;

        List<Interval> result = new ArrayList<>();

        while (index1< n1 && index2<n2 ){
            Interval interval1 = intervals1[index1];
            Interval interval2 = intervals2[index2];

           if (interval1.end > interval2.start){

               int start = Math.max(interval1.start, interval2.start);
               int end = Math.min(interval1.end, interval2.end);

               if (start<= end){
                   result.add(new Interval(start, end));
               }
           }

           if( interval1.end<= interval2.end){
               index1++;

           }else {
               index2++;
           }



        }
        return result;

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

    /**
     * [[1,4], [2,5], [7,9]]
     */

    public static boolean doesOverlap(List<Interval> intervals){

        Collections.sort(intervals,(a, b) -> a.start -b.start);
        int i =0;
        while (i< intervals.size() -1){
            Interval currInterval = intervals.get(i);
            Interval nextInterval = intervals.get(i+1);
            if (currInterval.end>= nextInterval.start){
                return true;
            }
            i++;
        }
        return false;
    }
    /*
problem Statement
 Given a list of intervals,
  merge all the overlapping intervals to produce a
   * list that has only mutually exclusive intervals.
  Example 1:  Intervals: [[1,4], [2,5], [7,9]]  * Output: [[1,5], [7,9]]
  Explanation: Since the first two intervals [1,4] and [2,5] overlap, we merged them into one [1,5].
 */

    /**
     * Intervals: [[1,4], [2,5], [7,9]]  * Output: [[1,5], [7,9]]
     * [[1, 4]] [2, 5]
     * [[1, 5]] [7, 9]  => [[1, 5] [7, 9]]
     *
     */

    public static List<Interval> merge(List<Interval> intervals){


        LinkedList<Interval> mergedList = new LinkedList<>();
        if (intervals.isEmpty()) return mergedList;

        intervals.sort((o1, o2) -> o1.start - o2.start);

        mergedList.add(intervals.get(0));

        for (int i =1; i<intervals.size(); i++){
            Interval currInterval = intervals.get(i);

            if(mergedList.getLast().end >= currInterval.start){
                mergedList.getLast().end = Math.max(mergedList.getLast().end, currInterval.end);
            }else{
                mergedList.add(currInterval);
            }



        }
        return mergedList;
    }
    private static void printResult(List<Interval> original, List<Interval> merged) {
        System.out.println("Original intervals:");
        for (Interval interval : original) {
            System.out.print("[" + interval.start + ", " + interval.end + "] ");
        }

        System.out.println("\nMerged intervals:");
        if (merged == null) {
            System.out.println("[]");
            return;
        }
        for (Interval interval : merged) {
            System.out.print("[" + interval.start + ", " + interval.end + "] ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        // Test Case 1: Basic interval merging
        System.out.println("Test Case 1: Basic interval merging");
        List<Interval> intervals1 = new ArrayList<>(Arrays.asList(
                new Interval(1, 3),
                new Interval(2, 5),
                new Interval(7, 9)
        ));
        printResult(intervals1, merge(intervals1));
        // Expected: [1, 5], [7, 9]

        // Test Case 2: All intervals merge into one
        System.out.println("\nTest Case 2: All intervals merge into one");
        List<Interval> intervals2 = new ArrayList<>(Arrays.asList(
                new Interval(1, 4),
                new Interval(2, 6),
                new Interval(3, 8),
                new Interval(5, 9)
        ));
        printResult(intervals2, merge(intervals2));
        // Expected: [1, 9]

        // Test Case 3: No overlapping intervals
        System.out.println("\nTest Case 3: No overlapping intervals");
        List<Interval> intervals3 = new ArrayList<>(Arrays.asList(
                new Interval(1, 2),
                new Interval(3, 4),
                new Interval(5, 6),
                new Interval(7, 8)
        ));
        printResult(intervals3, merge(intervals3));
        // Expected: [1, 2], [3, 4], [5, 6], [7, 8]

        // Test Case 4: One interval fully contained within another
        System.out.println("\nTest Case 4: One interval fully contained within another");
        List<Interval> intervals4 = new ArrayList<>(Arrays.asList(
                new Interval(1, 5),
                new Interval(2, 3),
                new Interval(6, 9)
        ));
        printResult(intervals4, merge(intervals4));
        // Expected: [1, 5], [6, 9]

        // Test Case 5: Intervals that touch but don't overlap
        System.out.println("\nTest Case 5: Intervals that touch but don't overlap");
        List<Interval> intervals5 = new ArrayList<>(Arrays.asList(
                new Interval(1, 3),
                new Interval(3, 5),
                new Interval(5, 7)
        ));
        printResult(intervals5, merge(intervals5));
        // Expected: [1, 7]

        // Test Case 6: Empty input list
        System.out.println("\nTest Case 6: Empty input list");
        List<Interval> intervals6 = new ArrayList<>();
        printResult(intervals6, merge(intervals6));
        // Expected: Empty list

        // Test Case 7: Single interval
        System.out.println("\nTest Case 7: Single interval");
        List<Interval> intervals7 = new ArrayList<>(Arrays.asList(
                new Interval(2, 5)
        ));
        printResult(intervals7, merge(intervals7));
        // Expected: [2, 5]

        // Test Case 8: Unsorted intervals
        System.out.println("\nTest Case 8: Unsorted intervals");
        List<Interval> intervals8 = new ArrayList<>(Arrays.asList(
                new Interval(6, 8),
                new Interval(1, 3),
                new Interval(2, 4),
                new Interval(5, 7)
        ));
        printResult(intervals8, merge(intervals8));
        // Expected: [1, 4], [5, 8]

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
        System.out.println("Input:" + appointments05 + ", Conflicts: " + makeItBold(conflicts.toString()) +", Expected: [[4, 5], [3, 6]], [[3, 6], [5, 7]]");




        // Test Case 2: No conflicts

        appointments05 = new ArrayList<>(Arrays.asList(
                new Interval(4, 5),
                new Interval(2, 3),
                new Interval(3, 6),
                new Interval(5, 7),
                new Interval(7, 8)
        ));
        conflicts = findConflictingAppointments(appointments05);
        System.out.println("Input:" + appointments05 + ", conflicts: " + makeItBold(conflicts.toString()) +", Expected: [[4, 5], [3, 6]], [[3, 6], [5, 7]]");


        // Test Case 3: All appointments conflict
        appointments05 = new ArrayList<>(Arrays.asList(
                new Interval(4, 5),
                new Interval(2, 3),
                new Interval(3, 6),
                new Interval(5, 7),
                new Interval(7, 8)
        ));
        conflicts = findConflictingAppointments(appointments05);
        System.out.println("Input:" + appointments05 + ", conflicts: " + makeItBold(conflicts.toString()) +", Expected: [[4, 5], [3, 6]], [[3, 6], [5, 7]]");


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

