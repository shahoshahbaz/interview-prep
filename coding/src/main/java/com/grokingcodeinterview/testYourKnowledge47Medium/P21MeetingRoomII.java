package com.grokingcodeinterview.testYourKnowledge47Medium;

import java.util.Arrays;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;

/*
Problem Statement
Given an array of meeting intervals where intervals[i] = [starti, endi], return the minimum number of meeting rooms needed so that no meetings overlap.

Examples
Example 1:
Input: intervals = [[10, 15], [20, 25], [30, 35]]
Expected Output: 1
Justification: There are no overlapping intervals in the given list. So, only 1 meeting room is enough for all the meetings.
Example 2:
Input: intervals = [[10, 20], [15, 25], [24, 30], [5, 14], [22, 28], [1, 4], [27, 35]]
Expected Output: 3
Justification: Let's see how many meetings overlap at the same time:
[1, 4] starts first.
Then [5, 14] begins, no overlap yet.
[10, 20] overlaps with [5, 14]
[15, 25] overlaps with [10, 20]
[22, 28] overlaps with [15, 25]
[24, 30] overlaps with both [22, 28] and [15, 25]
[27, 35] overlaps with [24, 30]
Example 3:
Input: intervals = [[10, 20], [20, 30]]
Expected Output: 1
Justification: The end time of the first meeting is the same as the start time of the second meeting. So, one meeting can be scheduled right after the other in the same room.
Constraints:

1 <= intervals.length <= 10^4
0 <= starti < endi <= 10^6
 */
public class P21MeetingRoomII {
    public static int minMeetingRooms(int[][] intervals) {
        // step1: sort the interval
       Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int[] interval: intervals){
            if (!minHeap.isEmpty() && minHeap.peek()<= interval[0]){
                minHeap.poll();
            }
            minHeap.offer(interval[1]);
        }



        return minHeap.size();
    }

    public static void main(String[] args) {
        System.out.println("========================");
        System.out.println("P.21 Meeting Room");
        System.out.println("========================");
        int[][] intervalsP21= {{10, 20}, {15, 25}, {24, 30}, {5, 14}, {22, 28}, {1, 4}, {27, 35}};
        System.out.println("Input: " + Arrays.deepToString(intervalsP21) +", output: " + makeItBold(minMeetingRooms(intervalsP21) +"") + ", expected: " + makeItBold("3"));
        intervalsP21 = new int[][]{{10, 20}, {20, 30}};
        System.out.println("Input: " + Arrays.deepToString(intervalsP21) +", output: " + makeItBold(minMeetingRooms(intervalsP21) +"") + ", expected: " + makeItBold("1"));
        intervalsP21 = new int[][]{{10, 15}, {20, 25}, {30, 35}};
        System.out.println("Input: " + Arrays.deepToString(intervalsP21) +", output: " + makeItBold(minMeetingRooms(intervalsP21) +"") + ", expected: " + makeItBold("1"));


    }
}

