package com.grokingcodeinterview.pattern06MergeIntervals;

import java.util.*;

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
public class P04ConflictingAppointments {

    public static boolean canAttendAllAppointments(Interval[] intervals) {
        if (intervals.length ==1) return true;
        // add them to the list, then sort them

        //NOTE:we could avoid this step by sorting the array directly
        List<Interval> appointments = Arrays.asList(intervals);

        Collections.sort(appointments, Comparator.comparingInt(a ->a.start));

        for (int i =0; i< appointments.size()-1; i++){
            if(overlapAppointments(appointments.get(i), appointments.get(i+1))){
                return false;
            }
        }
        return true;




    }

    public static boolean overlapAppointments(Interval interval1, Interval interval2){
        return interval1.end> interval2.start ;
    }

    public static void main(String[] args) {
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

    }
}

