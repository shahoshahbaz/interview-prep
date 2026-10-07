package com.grokingcodeinterview.pattern16TwoHeaps;

/**
 * Problem Statement
 * Given an array of intervals, find the next interval of each interval.
 * In a list of intervals, for an interval â€˜iâ€™ its next interval â€˜jâ€™ will have the smallest â€˜startâ€™ greater than or equal to the â€˜endâ€™ of â€˜iâ€™.
 *
 * Write a function to return an array containing indices of the next interval of each input interval.
 * If there is no next interval of a given interval, return -1. It is given that none of the intervals have the same start point.
 *
 */

import java.util.PriorityQueue;

/**
 * Solution:
 * To solve this problem, we can use two heaps (priority queues):
 * 1. A max-heap to store the intervals based on their start times.
 * 2. A max-heap to store the intervals based on their end times.
 *  We will follow these steps:
 *  - Initialize two max-heaps: one for start times and one for end times.
 *  - Add all intervals to both heaps, storing their indices.
 *  - For each interval in the end-time heap, we will:
 *  - Remove intervals from the start-time heap that have a start time less than the end
 *  time of the current interval.
 *  - The top of the start-time heap will be the next interval for the current interval
 *  (if the heap is not empty).
 *  - Store the index of this next interval in the result array.
 *  - If the start-time heap is empty, store -1 in the result array.
 *  - Reinsert the current interval back into the start-time heap (as it might be the next interval for other intervals).
 *  - Return the result array.
 *
 *  example:
 *  Input: Intervals [[2, 3], [3, 4], [5, 6]]
 *  Output: [1, 2, -1]
 *  dry run:
 *  - Start-time heap: [0: (2, 3), 1: (3, 4), 2: (5, 6)] => max-heap based on start time => [(5, 6), (3, 4), (2, 3)]
 *  - End-time heap: [0: (2, 3), 1: (3, 4), 2: (5, 6)] => max-heap based on end time => [(5, 6), (3, 4), (2, 3)]
 *  - Result array: [-1, -1, -1]
 *  - Process interval
 *   - endIndex = maxEndHeap.poll() = 2 (interval [5, 6]): - No start intervals with start >= 6, result[2] = -1
 *   - endIndex = 1 (interval [3, 4]): - Next interval => while start intervals with start(=5) >= 4 then , maxStartHeap.poll() = 2,  startIndex = 2 ,  maxStartHeap is now [(3,4), (2,3)] , result[1] = 2
 *   - endIndex = 0 (interval [2, 3]): - Next interval => while start intervals with start(=3) >= 3 then , maxStartHeap.poll() = 1,  startIndex = 1 ,  maxStartHeap is now [(2,3)] , result[0] = 1
 *   Final result: [1, 2, -1]
 *
 *
 *
 *  *
 */
public class P04NextInterval {
    class Interval {
        int start = 0;
        int end = 0;

        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static int[] findNextInterval(Interval[] intervals) {
        int n = intervals.length;
        int[] result = new int[n];
        PriorityQueue<Integer> maxStartHeap = new PriorityQueue<>((a, b) -> intervals[b].start - intervals[a].start);
        PriorityQueue<Integer> maxEndHeap = new PriorityQueue<>((a, b) -> intervals[b].end - intervals[a].end);

        for (int i = 0; i < n; i++) {
            maxStartHeap.offer(i);
            maxEndHeap.offer(i);
        }

        for (int i = 0; i < n; i++) {
            // get the interval with the largest end time

            int endIndex = maxEndHeap.poll();
            result[endIndex] = -1; // default value if no next interval is found
            // find the next interval
            // the next interval will be the one with the smallest start time that is >= end time
            // since we are using a max heap, we will keep removing the top element until we find the next interval
            if (maxStartHeap.peek() != null && intervals[maxStartHeap.peek()].start >= intervals[endIndex].end) {
                int startIndex = maxStartHeap.poll();
                while (maxStartHeap.peek() != null && intervals[maxStartHeap.peek()].start >= intervals[endIndex].end) {
                    startIndex = maxStartHeap.poll();
                }
                result[endIndex] = startIndex;
                maxStartHeap.offer(startIndex); // put it back as it might be the next interval for other intervals
            }
        }


        return result;
    }

    public static void main(String[] args) {
        //example 1
        Interval[] intervals = new Interval[]{new P04NextInterval().new Interval(2, 3), new P04NextInterval().new Interval(3, 4), new P04NextInterval().new Interval(5, 6)};
        int[] result = P04NextInterval.findNextInterval(intervals);
        System.out.print("Next interval indices: ");
        for (int index : result) {
            System.out.print(index + " ");
        }
        System.out.println();
        // some more examples
        intervals = new Interval[]{new P04NextInterval().new Interval(3, 4), new P04NextInterval().new Interval(1, 5), new P04NextInterval().new Interval(4, 6)};
        result = P04NextInterval.findNextInterval(intervals);
        System.out.print("Next interval indices: ");
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
