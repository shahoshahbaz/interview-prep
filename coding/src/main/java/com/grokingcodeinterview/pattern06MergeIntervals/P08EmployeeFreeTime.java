package com.grokingcodeinterview.pattern06MergeIntervals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class P08EmployeeFreeTime {
    public static List<Interval> findEmployeeFreeTime(List<List<Interval>> schedule){
        List<Interval> allIntervals = new ArrayList<>();
        List<Interval> merged = new ArrayList<>();
        List<Interval> result = new ArrayList<>();

        // flat the schedule
        for (List<Interval> employeeIntervals: schedule){
            allIntervals.addAll(employeeIntervals);
        }

        // sort the allIntervals
        allIntervals.sort(Comparator.comparingInt(a ->a.start));

        // step 3. merged overlapping intervals
        for (Interval interval: allIntervals){
            if (merged.isEmpty() || merged.get(merged.size()-1).end<  interval.start ){
                merged.add(interval);
            }else{
                Interval last = merged.get(merged.size()-1);
                last.end = Math.max(last.end, interval.end);

            }
        }

        Interval prev = merged.get(0);
        for (int i =1; i< merged.size();i++){
            Interval current = merged.get(i);
            result.add(new Interval(prev.end, current.start));
            prev= current;

        }

        return result;
    }

    public static void main(String[] args) {
        // Create schedule for multiple employees
        List<List<Interval>> schedule = new ArrayList<>();

        // Employee 1 schedule: [1,3], [5,6]
        List<Interval> employee1Schedule = new ArrayList<>();
        employee1Schedule.add(new Interval(1, 3));
        employee1Schedule.add(new Interval(5, 6));
        schedule.add(employee1Schedule);

        // Employee 2 schedule: [2,4]
        List<Interval> employee2Schedule = new ArrayList<>();
        employee2Schedule.add(new Interval(2, 4));
        schedule.add(employee2Schedule);

        // Employee 3 schedule: [6,8]
        List<Interval> employee3Schedule = new ArrayList<>();
        employee3Schedule.add(new Interval(6, 8));
        schedule.add(employee3Schedule);

        // Find free intervals
        List<Interval> freeTime = findEmployeeFreeTime(schedule);

        // Display results
        System.out.println("Free time intervals:");
        for (Interval interval : freeTime) {
            System.out.println("[" + interval.start + "," + interval.end + "]");
        }

        // Add another test case
        schedule = new ArrayList<>();

        // Employee 1 schedule: [1,3], [9,12]
        employee1Schedule = new ArrayList<>();
        employee1Schedule.add(new Interval(1, 3));
        employee1Schedule.add(new Interval(9, 12));
        schedule.add(employee1Schedule);

        // Employee 2 schedule: [2,4], [6,8]
        employee2Schedule = new ArrayList<>();
        employee2Schedule.add(new Interval(2, 4));
        employee2Schedule.add(new Interval(6, 8));
        schedule.add(employee2Schedule);

        // Find free intervals for the second example
        freeTime = findEmployeeFreeTime(schedule);

        // Display results for the second example
        System.out.println("\nFree time intervals for second example:");
        for (Interval interval : freeTime) {
            System.out.println("[" + interval.start + "," + interval.end + "]");
        }
    }
}

