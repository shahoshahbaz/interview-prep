package com.grokingcodeinterview.pattern06MergeIntervals;

import java.util.*;

import static com.Utility.makeItBold;

/*
 * Given a list of appointments, find all the conflicting appointments.

Example:

Appointments: [[4,5], [2,3], [3,6], [5,7], [7,8]]
Output:
[4,5] and [3,6] conflict.
[3,6] and [5,7] conflict.
 */
public class P05FindAllTheConflictingAppointments {

    public static List<List<Interval>> findConflictingAppointments(List<Interval> intervals) {
        List<List<Interval>> conflictAllAppointments = new ArrayList<>();
        List<Interval> sorted = new ArrayList<>(intervals);

        if ( intervals == null ||intervals.size() == 0) return conflictAllAppointments;
        // sor the list
        Collections.sort(sorted, Comparator.comparingInt(a -> a.start));


        for (int i = 0; i < sorted.size()-1; i++) {
            Interval current = sorted.get(i);
            for (int j = i + 1; j < sorted.size() ; j++) {
                Interval next = sorted.get(j);
                if (doesConflict(current, next)) {
                    List<Interval> conflictAppointments = new ArrayList<>();

                    conflictAppointments.add(current);
                    conflictAppointments.add(next);
                    conflictAllAppointments.add(conflictAppointments);


                } else {
                    break;
                }



            }
        }
        return conflictAllAppointments;
    }

    public static boolean doesConflict(Interval interval1, Interval interval2){
         return interval1.end>interval2.start;
    }

    public static void main(String[] args) {
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

