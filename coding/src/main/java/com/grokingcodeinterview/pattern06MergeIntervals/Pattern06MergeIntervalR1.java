package com.grokingcodeinterview.pattern06MergeIntervals;

import java.util.*;

import static com.Utility.makeItBold;

public class Pattern06MergeIntervalR1 {

    /*
 We are given a list of Jobs. Each job has a Start time, an End time, and a CPU load when it is running.
 Our goal is to find the maximum CPU load at any time if all the jobs are running on the same machine.
 Example 1:  Jobs: [[1,4,3], [2,5,4], [7,9,6]]  Output: 7
 Explanation: Since [1,4,3] and [2,5,4] overlap, their maximum CPU load (3+4=7) will be when both the jobs are running at the same time i.e., during the time interval (2,4).
 Example 2:  Jobs: [[6,7,10], [2,4,11], [8,12,15]]  Output: 15
 Explanation: None of the jobs overlap, therefore we will take the maximum load of any job which is 15.
 Example 3:  Jobs: [[1,4,2], [2,4,1], [3,6,5]]  Output: 8
 Explanation: Maximum CPU load will be 8 as all jobs overlap during the time interval [3,4].

 */
    public static int findMaxCPULoad(List<Job> jobs){

        if (jobs == null || jobs.isEmpty()) return 0;
        if (jobs.size()==1) return jobs.get(0).cpuLoad;

        // sort the list
        jobs.sort((a,b) -> a.start - b.start);

        PriorityQueue<Job> minHeap = new PriorityQueue<>((a, b) -> a.end- b.end);

        int currCPULoad =0;
        int maxCPULoad = 0;

        for (Job job: jobs){
            while (!minHeap.isEmpty() && job.start>= minHeap.peek().end){
                currCPULoad -= minHeap.poll().cpuLoad;
            }

            minHeap.offer(job);
            currCPULoad += job.cpuLoad;

            maxCPULoad = Math.max(maxCPULoad, currCPULoad);
        }

        return maxCPULoad;
    }

    public static  class Job{
        int start;
        int end;
        int cpuLoad;

        public Job(int start, int end, int cpuLoad) {
            this.start = start;
            this.end = end;
            this.cpuLoad = cpuLoad;


        }

        @Override
        public String toString() {
            return "[" +
                     start +
                    "," + end +
                    "," + cpuLoad +
                    ']';
        }
    }

    /*
  Given a list of intervals representing the start and end time of â€˜Nâ€™ meetings, find the minimum number of rooms required to hold all the meetings.

  Example 1:   Meetings: [[1,4], [2,5], [7,9]]  Output: 2
  Explanation: Since [1,4] and [2,5] overlap, we need two rooms to hold these two meetings. [7,9] can occur in any of the two rooms later.
  count the number of overlap meeting. 2 over lapping so we need to 2 rooms
  Example 2: Meetings: [[6,7], [2,4], [8,12]]   Output: 1
  Explanation: None of the meetings overlap, therefore we only need one room to hold all meetings.
  Example 3:  Meetings: [[1,4], [2,3], [3,6]]   Output:2
  Explanation: Since [1,4] overlaps with the other two meetings [2,3] and [3,6], we need two rooms to hold all the meetings.
  Example 4:  Meetings: [[4,5], [2,3], [2,4], [3,5]]   Output: 2
 * Explanation: We will need one room for [2,3] and [3,5], and another room for [2,4] and [4,5].
 */

    /**
     * [[1,4], [2,5], [7,9]]
     * minHeap(1, 4)
     */
    public static int countMinimumMeetingRooms(List<Interval> meetings){
        if(meetings == null) return 0;
        if(meetings.size()<= 1 ) return meetings.size();

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        meetings.sort((a, b) ->a.start - b.start);

        minHeap.add(meetings.get(0).end);
        for (int i =1; i< meetings.size(); i++){
            Interval curr = meetings.get(i);

            if(!minHeap.isEmpty() && curr.start >= minHeap.peek()){
                minHeap.poll();
            }
            minHeap.offer(curr.end);


        }

        return minHeap.size();
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
    public static boolean canAttendAllAppointments(Interval[] intervals){
        if (intervals.length == 1) return true;

        for (int i =0; i< intervals.length -1; i++){

            Interval interval1 = intervals[i];
            Interval interval2 = intervals[i+1];

            if (interval1.end > interval2.start){
                return false;
            }

        }

        return true;


    }

    /*
            * Given a list of appointments, find all the conflicting appointments.
       Example:
    Appointments: [[4,5], [2,3], [3,6], [5,7], [7,8]]
    Output:
            [4,5] and [3,6] conflict.
[3,6] and [5,7] conflict.
 */

    /**
     * [[2,3], [3,6], [4,5],  [5,7], [7,8]]
     * i, i+1
     * i.end> (i+1).start
     */
    public static List<List<Interval>> findConflictingAppointments(List<Interval> intervals){

        // sort the list
        intervals.sort((t1,t2) ->t1.start - t2.start);
        List<List<Interval>> result = new ArrayList<>();

        for (int i =0; i<intervals.size() -1; i++){
            Interval interval1 = intervals.get(i);
            for (int j =i+1; j<intervals.size(); j++){
                Interval interval2 = intervals.get(j);

                if (interval1.end> interval2.start){
                    result.add(Arrays.asList(interval1, interval2));
                }
            }
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
     *  arr1=[[1, 3], [5, 6], [7, 9]], arr2=[[2, 3], [5, 7]]
     *   [1, 3], [2, 3] end> start max(starts) and min(ends)
     *   [5, 6] [2, 3] => 6>2 [5, 3] not valid
     *   [5, 6][5, 7]  [5, 6] add to the list
     *   [7, 9][5, 7] [7,7]
     *
     *
     *
     *
     */
    public static  List<Interval> findIntersection(Interval[] intervals1, Interval[] intervals2){

        List<Interval> result = new ArrayList<>();

        int index1 =0;
        int index2 = 0;

        while (index1< intervals1.length && index2< intervals2.length){

            Interval interval1 = intervals1[index1];
            Interval interval2 = intervals2[index2];

            int start = Math.max(interval1.start, interval2.start);
            int end = Math.min(interval1.end, interval2.end);

            if (start<=end){
                result.add(new Interval(start, end));
            }

            // moving indexes
            if (interval1.end<= interval2.end){ // move the pointer for the interval which is finishing first
                index1++;
            }else{
                index2++;
            }


        }
        return result;

    }
/*
 Given a list of non-overlapping intervals sorted by their start time,
  insert a given interval at the correct position and merge all necessary intervals to produce a list that has only mutually exclusive intervals.
 *
 Example 1:
 *
 Input: Intervals=[[1,3], [5,7], [8,12]], New Interval=[4,6]
 Output: [[1,3], [4,7], [8,12]]
 Explanation: After insertion, since [4,6] overlaps with [5,7], we merged them into one [4,7].
 Input: Intervals = [1, 3][5,7][ 8,10]  new Interval [0, 15]
 me
 
 */

    /**
     * Intervals [1,3], [5,7], [8,12]], New Interval=[4,6]
     * sort the list
     *  non-overlapping to the list[[1,3]]
     *  add the new intervals  [1, 3][4,6]
     *  [1, 3][4, 7]
     *

     */
    public static List<Interval> insertInterval(List<Interval> intervals,Interval newInterval){
        // step 1: sor the list
        intervals.sort(Comparator.comparingInt(o -> o.start));

        List<Interval> result = new LinkedList<>();

        int index =0;

        // add non-overlapping intervals to the list
        while (index< intervals.size() && intervals.get(index).end< newInterval.start){
            result.add(intervals.get(index));
            index++;
        }
        result.add(newInterval);

        // now add the rest if overlap merge them

        while(index<intervals.size()  && result.get(result.size() -1).end>= intervals.get(index).start){
                result.get(result.size() -1).end = Math.max(result.get(result.size() -1).end, intervals.get(index).end );
                result.get(result.size() -1).start = Math.min(result.get(result.size() -1).start, intervals.get(index).start );
                index++;
            }

        while(index<intervals.size()){
            result.add(intervals.get(index));
            index++;
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
    public static boolean doesOverlap(List<Interval> intervals){

        if (intervals.isEmpty() || intervals.size() <=1) return false;

        intervals.sort((a, b) -> a.start - b.start);


        for (int i = 0; i< intervals.size() -1; i++){

            Interval curr = intervals.get(i);
            Interval next = intervals.get(i+1);

            if (curr.end >= next.start){
                return true;
            }
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
     * nexIntervalStart<= currentIntervalEnd
     *
     */

    public static List<Interval> merge(List<Interval> intervals){


        LinkedList<Interval> mergedIntervals = new LinkedList<>();

        if (intervals.size() ==1) return intervals;
        if (intervals.size() ==0) return mergedIntervals;

        intervals.sort((a, b) -> a.start - b.start);

       // add the first element
        mergedIntervals.add(intervals.get(0));

        for (int i =1; i< intervals.size(); i++){

            Interval nextInterval= intervals.get(i);

            if (nextInterval.start<=mergedIntervals.getLast().end){
                mergedIntervals.getLast().end = Math.max(nextInterval.end, mergedIntervals.getLast().end);
            }else{
                mergedIntervals.add(nextInterval);
            }

        }

        return mergedIntervals;

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
        System.out.println("Test Case 1: " +doesOverlap(intervalsP001)); // true


        // Test Case 2: Non-overlapping intervals
        intervalsP001 = Arrays.asList(
                new Interval(1, 2),
                new Interval(3, 4),
                new Interval(5, 6)
        );
        System.out.println("Test Case 2: " +doesOverlap(intervalsP001)); // false


        // Test Case 3: Single interval (edge case)
        intervalsP001 = Arrays.asList(
                new Interval(1, 10)
        );
        System.out.println("Test Case 3: " +doesOverlap(intervalsP001)); // false


        // Test Case 4: Two intervals that overlap
        intervalsP001 = Arrays.asList(
                new Interval(5, 10),
                new Interval(8, 12)
        );
        System.out.println("Test Case 4: " +doesOverlap(intervalsP001)); // true


        // Test Case 5: Touching intervals (non-overlapping if inclusive-exclusive)
        intervalsP001 = Arrays.asList(
                new Interval(1, 3),
                new Interval(3, 5)
        );
        System.out.println("Test Case 5: " +doesOverlap(intervalsP001)); // true


        // Test Case 6: Completely nested intervals
        intervalsP001 = Arrays.asList(
                new Interval(1, 10),
                new Interval(2, 3),
                new Interval(4, 8)
        );
        System.out.println("Test Case 6: " +doesOverlap(intervalsP001)); // true

        // Test Case 7: Random order intervals
        intervalsP001 = Arrays.asList(
                new Interval(10, 20),
                new Interval(1, 5),
                new Interval(4, 6)
        );
        System.out.println("Test Case 7: " +doesOverlap(intervalsP001)); // true

        System.out.println("==========================");
        System.out.println("P02. Insert Interval..");
        System.out.println("==========================");
        // Test Case 1: Basic insertion and merging

        List<Interval> intervalsP02 = new ArrayList<>(List.of(new Interval(1, 3),new Interval(5, 7),new Interval(8, 12) ));
        Interval newIntervalP02 = new Interval(4, 6);
        List<Interval> resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("Input: " + intervalsP02 + " ,new Interval:"+ newIntervalP02 + " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [1, 3], [4, 7], [8, 12]");
        // Expected: [1, 3], [4, 7], [8, 12]

        // Test Case 2: New interval before all
        intervalsP02 = new ArrayList<>(List.of(new Interval(5, 7),new Interval(8, 12)));
        newIntervalP02 = new Interval(1, 3);
        resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("\nTest Case 2 Result:");
        for (Interval interval : resultP02) {
            System.out.print("[" + interval.start + ", " + interval.end + "] ");
        }
        // Expected: [1, 3], [5, 7], [8, 12]

        // Test Case 3: New interval after all
        intervalsP02 = new ArrayList<>(List.of(
                new Interval(1, 3),
                new Interval(5, 7)
        ));
        newIntervalP02 = new Interval(8, 10);
        resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("\nTest Case 3 Result:");
        for (Interval interval : resultP02) {
            System.out.print("[" + interval.start + ", " + interval.end + "] ");
        }
        // Expected: [1, 3], [5, 7], [8, 10]

        // Test Case 4: New interval overlaps all
        intervalsP02 = new ArrayList<>(List.of(
                new Interval(1, 3),
                new Interval(5, 7),
                new Interval(8, 12)
        ));
        newIntervalP02 = new Interval(0, 15);
        resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("\nTest Case 4 Result:");
        for (Interval interval : resultP02) {
            System.out.print("[" + interval.start + ", " + interval.end + "] ");
        }
        // Expected: [0, 15]

        // Test Case 5: New interval overlaps some
        intervalsP02 = new ArrayList<>(List.of(
                new Interval(1, 3),
                new Interval(5, 7),
                new Interval(8, 12)
        ));
        newIntervalP02 = new Interval(6, 10);
        resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("\nTest Case 5 Result:");
        for (Interval interval : resultP02) {
            System.out.print("[" + interval.start + ", " + interval.end + "] ");
        }
        // Expected: [1, 3], [5, 12]

        intervalsP02 = new ArrayList<>(List.of(new Interval(5,8)));

        newIntervalP02 = new Interval(1, 6);
        resultP02 = insertInterval(intervalsP02, newIntervalP02);
        System.out.println("Input: " + intervalsP02 + " ,new Interval:"+ newIntervalP02 + " ,Output: " + makeItBold(resultP02.toString()) + ",Expected: [1, 8]");


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

        System.out.println("==========================================================");
        System.out.println("P06. Minimum Meeting Rooms");
        System.out.println("==========================================================");
        List<Interval> IntervalP06 = Arrays.asList( new Interval(1, 4), new Interval(2, 5), new Interval(7, 9));
        System.out.println("Input: " + IntervalP06 + ", output: " + makeItBold(countMinimumMeetingRooms(IntervalP06) + "") + ", expected: 2");

        IntervalP06 = Arrays.asList( new Interval(6, 7), new Interval(2, 4), new Interval(8, 12));
        System.out.println("Input: " + IntervalP06 + ", output: " + makeItBold(countMinimumMeetingRooms(IntervalP06) + "") + ", expected: 1");
        IntervalP06 = Arrays.asList( new Interval(1, 4), new Interval(2, 3), new Interval(3, 6));
        System.out.println("Input: " + IntervalP06 + ", output: " + makeItBold(countMinimumMeetingRooms(IntervalP06) + "") + ", expected: 2");
        IntervalP06 = Arrays.asList( new Interval(4, 5), new Interval(2, 3), new Interval(2, 4), new Interval(3, 5));
        System.out.println("Input: " + IntervalP06 + ", output: " + makeItBold(countMinimumMeetingRooms(IntervalP06) + "") + ", expected: 2");
        IntervalP06 = Arrays.asList( new Interval(1, 10), new Interval(2, 7), new Interval(3, 6));
        System.out.println("Input: " + IntervalP06 + ", output: " + makeItBold(countMinimumMeetingRooms(IntervalP06) + "") + ", expected: 3");

        System.out.println("==========================================================");
        System.out.println("P07. Maximum CPU Load");
        System.out.println("==========================================================");

        List<Job> jobsP07 = Arrays.asList(
                new Job(1, 4, 3),
                new Job(2, 5, 4),
                new Job(7, 9, 6)
        );
        System.out.println("Input: " + jobsP07 + ",output: " + makeItBold(findMaxCPULoad(jobsP07) + "") + ", expected: 7");

        jobsP07 = Arrays.asList(
                new Job(6, 7, 10),
                new Job(2, 4, 11),
                new Job(8, 12, 15)
        );
        System.out.println("Input: " + jobsP07 + ",output: " + makeItBold(findMaxCPULoad(jobsP07) + "") + ", expected: 15");

        jobsP07 = Arrays.asList(
                new Job(1, 4, 2),
                new Job(2, 4, 1),
                new Job(3, 6, 5)
        );
        System.out.println("Input: " + jobsP07 + ",output: " + makeItBold(findMaxCPULoad(jobsP07) + "") + ", expected: 8");










    }
}

