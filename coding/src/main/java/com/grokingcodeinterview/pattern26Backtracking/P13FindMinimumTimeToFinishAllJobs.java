package com.grokingcodeinterview.pattern26Backtracking;

/*
ind Minimum Time to Finish All Jobs (LeetCode 1723)

You have k workers and n jobs, where jobs[i] is how long job i takes. Assign every job to exactly one worker (each worker can get zero, one, or several jobs). A worker's total working time is the sum of durations of jobs assigned to them. Return the minimum possible value of the maximum working time across all workers â€” i.e., find the assignment that makes the busiest worker as idle as possible.

Example 1:
Input: jobs = [3,2,3], k = 3
Output: 3
Explanation: 3 jobs, 3 workers â€” give each worker exactly one job: worker 1 gets job of length 3, worker 2 gets length 2, worker 3 gets length 3. Working times are [3,2,3], so the busiest worker takes 3. No assignment can do better, since some worker must take the length-3 job alone or combined with something else (which would only increase their time).

Example 2:
Input: jobs = [1,2,4,7,8], k = 2
Output: 11
Explanation: 5 jobs, 2 workers. Best split: worker A gets {1,2,8} (total 11), worker B gets {4,7} (total 11). Busiest worker's time is 11. Any other split makes one worker busier â€” e.g. {7,8} and {1,2,4} gives times 15 and 7, and 15 > 11.

Constraints: 1 <= k <= jobs.length <= 12, 1 <= jobs[i] <= 10^7.
 */
public class P13FindMinimumTimeToFinishAllJobs {
    public int minimumTimeRequired(int[] jobs, int k) {
        if(jobs == null || jobs.length == 0 || k <= 0) {
            return 0;
        }
        int[] workLoads = new int[k];

        int best = Integer.MAX_VALUE;
        return backtrack(jobs, 0, workLoads, best);


    }

    private static int backtrack(int[] jobs, int jobIndex, int[] workerLoads, int best){
        if(jobIndex == jobs.length){
            best = Math.min(best, maxLoad(workerLoads) );
            return best;
        }

        for(int worker =0; worker< workerLoads.length; worker++ ){
            if(workerLoads[worker] + jobs[jobIndex] >= best) continue;

            // choose
            workerLoads[worker] += jobs[jobIndex];

            // explore
            best = Math.min(best, backtrack(jobs, jobIndex +1, workerLoads, best));

            // unchoose
            workerLoads[worker] += jobs[jobIndex];

            if(workerLoads[worker] ==0) break;
        }

        return  best;

    }

    private static int maxLoad(int[] workerLoads){
        int max =0;
        for(int load: workerLoads){
            max = Math.max(max, load);
        }
        return max;
    }
}

