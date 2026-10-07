package com.grokingcodeinterview.pattern06MergeIntervals;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;

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
public class P06MinimumMeetingRooms {
    class Meeting {
        int start;
        int end;

        public Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    public static int countMinimumMeetingRooms(List<Interval> meetings) {
        if (meetings == null) return 0;
        if (meetings.size() <= 1) return meetings.size();

        // Min-heap to track the earliest 'end' time of ongoing meetings
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Sort meetings by start time to process them in order
        meetings.sort((a, b) -> Integer.compare(a.start, b.start));

        // Add end time of the first meeting to the heap
        minHeap.add(meetings.get(0).end);

        for (int i = 1; i < meetings.size(); i++) {
            Interval current = meetings.get(i);

            // If the current meeting starts after or when the earliest meeting ends
            // Then we can reuse that room (remove the top element from the heap)
            if(!minHeap.isEmpty() && current.start >= minHeap.peek()){
                minHeap.poll();
            }

            // Book a new room or reuse the same one by adding current meeting's end time
            minHeap.add(current.end);
        }

        // The number of rooms used is the number of overlapping meetings at peak
        return minHeap.size();
    }
    public static void main(String[] args) {
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
    }


}


