package com.grokingcodeinterview.pattern06MergeIntervals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;

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

/**
 * ðŸ”§ Hints
 *  âœ… Hint 1: Sort the jobs
 *  Start by sorting the jobs by start time. That way, you can process jobs as they come in chronological order.
 *  âœ… Hint 2: Use a MinHeap based on end time
 *  Youâ€™ll want to use a min-heap (priority queue) where:
 *  Each element in the heap represents a job that is currently running.
 *  You use the jobâ€™s end time as the heap key, so the earliest-ending job is always at the top.
 *  This allows you to efficiently remove jobs that are no longer overlapping with the current one.
 *  âœ… Hint 3: Keep track of current CPU load
 *  As you iterate through jobs:
 *  Remove all jobs from the heap that have ended before the current jobâ€™s start time.
 *  For each job added to the heap, accumulate the CPU load.
 *  At each step, track the max CPU load seen so far.
 *  âœ… Hint 4: Your variables
 *  Youâ€™ll want:
 *  A PriorityQueue to store jobs (or a custom class with end time).
 *  An int currentLoad to track CPU load of jobs in the heap.
 *  An int maxLoad to record the highest observed load.
 */

/**
 * [[1,4,3], [2,5,4], [7,9,6]]  Output: 7
 *  sor the job by start time => [[1,4,3], [2,5,4], [7,9,6]]
 *  minHeap = [] // the min heap is based on end time
 *  currentLoad = 0, maxLoad = 0
 *  process job [1,4,3] => minHeap = [[1,4,3]], currentLoad = 3, maxLoad = 3
 *  process job [2,5,4] => minHeap = [[1,4,3], [2,5,4]], currentLoad = 7, maxLoad = 7
 *  process job [7,9,6] => here
 *  here start time of current job is 7 > 4 end time of the top job in the min heap, so we remove [1,4,3] from the min heap and update currentLoad = 7 - 3 = 4
 *  now start time of current job is 7 > 5 end time of the top job in the min heap, so we remove [2,5,4] from the min heap and update currentLoad = 4 - 4 = 0
 *  now we add [7,9,6] to the min heap and update currentLoad = 0 + 6 = 6, maxLoad = 7
 *  return maxLoad = 7
 *
 *  next example: [[1,4,2], [2,4,1], [3,6,5]]  Output: 8
 *  sort the job by start time: [[1,4,2], [2,4,1], [3,6,5]]
 *  minHeap on end time : minHeap :[];
 *  job [1, 4, 2] : minHeap[[1, 4, 2]], currCPULoad: 2, maxCPUload: 2
 *  job [2, 4, 1]  2 is not > 4, minHeap =[[1,4,2], [2,4,1] ] curr: 3, maxCPULoad = 3
 *  job [3, 6, 5 3  is not > 4 min Heap[all] curr = 8 , max = 8
 *
 *  *  */
public class P07MaximumCPULoad {
    static   class Job{
        int start;
        int end;
        int cpuLoad;
        public  Job (int start, int end, int cpuLoad){
            this.start = start;
            this.end = end;
            this.cpuLoad = cpuLoad;
        }

        @Override
        public String toString() {
            return "["  + start + ", " + end + ", " + cpuLoad + ']';
        }
    }
    public static  int findMaxCPULoad(List<Job> jobs){

        if (jobs == null || jobs.isEmpty()) return 0;
        if (jobs.size()==1) return jobs.get(0).cpuLoad;

        // Sort the List by start time
        jobs.sort(Comparator.comparingInt(a-> a.start));

        PriorityQueue<Job> minHeap = new PriorityQueue<>((a, b) ->a.end - b.end
        );


        int currCpuLoad = 0;
        int maxCpuLoad = 0;

        for (Job job:jobs){
            // remove all jobs that has ended before current Job
            while (!minHeap.isEmpty() && job.start>= minHeap.peek().end){
                currCpuLoad -= minHeap.poll().cpuLoad;
            }
            // Add currentJob
            minHeap.offer(job);
            currCpuLoad += job.cpuLoad;

            maxCpuLoad = Math.max(currCpuLoad, maxCpuLoad);
        }

        return maxCpuLoad;
    }
    public static void main(String[] args) {
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

