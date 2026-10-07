package com.grokingcodeinterview.pattern37realInterviewProblems;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
  Question was asked in PointClickCare (PCC)
 Problem Statement

Given an array of strings where each string represents a time range in the format "H:MM AM/PM - H:MM AM/PM", find the largest gap (in minutes) between any two meetings. Return the result in HH:MM format.
A gap is defined as the time between the end of one meeting and the start of the next meeting that begins after it.

Input:
javaString[] timeRanges = {
    "9:00 AM - 11:30 PM",
    "1:15 PM - 3:45 PM",
    "7:00 AM - 8:30 AM",
    "10:00 PM - 1:00 AM",
    "12:00 PM - 12:45 PM"
};
Output:
The largest gap is HH:MM

Constraints:

Time is in 12-hour format with AM/PM
Meetings may span midnight (e.g., "10:00 PM - 1:00 AM")
Meetings may overlap — overlapping meetings have a gap of 0
The array is not sorted
1 <= timeRanges.length <= 10^4
 */
public class MeetingGapFinder {



    public static String findLargestGap(String[] timeRanges){
        int[][] intervals = new int [timeRanges.length][2];
        for (int i =0; i< timeRanges.length; i++){
            String[] parts = timeRanges[i].split(" - ");
            int start = toMinutes(parts[0]);
            int end = toMinutes(parts[1]);
            // Handle overnight: if end < start, the meeting crosses midnight → add 1440.
            if (end< start) end += 1440;  // 24 * 60
            intervals[i] = new int[]{start, end};

        }

        Arrays.sort(intervals, (a, b) ->a[0] -  b[0] );

        int maxGap = 0;
        int furthestEnd = intervals[0][1];
        for (int i  =1; i< intervals.length; i++){
            int gap =  intervals[i][0] - furthestEnd;
            if (gap>maxGap ) maxGap = gap;
            furthestEnd = Math.max(furthestEnd, intervals[i][1]);
        }

        return String.format("%02d:%02d", maxGap / 60, maxGap % 60);

    }
    public static int toMinutes(String time){
        String[] parts = time.trim().split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1].substring(0, 2));
        String period = parts[1].substring(3);

        if (period.equals("PM") && hours != 12) hours +=12;
        if(period.equals("AM") && hours == 12) hours = 0;

        return hours * 60 + minutes;

    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("Meeting Gap Finder");
        System.out.println("==================================================================");
        String[] timeRanges = {
                "9:00 AM - 11:30 PM",
                "1:15 PM - 3:45 PM",
                "7:00 AM - 8:30 AM",
                "10:00 PM - 1:00 AM",
                "12:00 PM - 12:45 PM"
        };
        System.out.println("Input: " + Arrays.toString(timeRanges)
                + ", output: " + makeItBold(findLargestGap(timeRanges))
                + ", expected: " + makeItBold("00:30")); // ← fix here

    }
}
