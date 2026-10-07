package com.grokingcodeinterview.pattern22GreedyAlgorithms;
/* Minimum Number of Arrows to Burst Balloons

    Problem: There are spherical balloons taped onto a flat wall.
    Each balloon is represented as an interval [x_start, x_end] on the x-axis.
     An arrow shot straight up (from any x-coordinate) bursts every balloon whose interval
     contains that x-coordinate.
     Find the minimum number of arrows needed to burst all balloons.
             Input: points = [[10,16],[2,8],[1,6],[7,12]] Output: 2
                Explanation: One arrow at x=6 bursts [2,8] and [1,6].
                Another arrow at x=11 (or anywhere in 10-12) bursts [10,16] and [7,12].
              Input: points = [[1,2],[3,4],[5,6],[7,8]] Output: 4
                Explanation: No intervals overlap, so each needs its own arrow.
            Input: points = [[1,2],[2,3],[3,4],[4,5]] Output: 2
                Explanation: Arrow at x=2 bursts [1,2] and [2,3]. Arrow at x=4 bursts [3,4] and [4,5].
     */

import java.util.Arrays;

/**
 * sort by end time
 * Example A: [[1,10],[2,3],[4,5],[11,12]] â†’ 3
 * Example B: [[3,3],[1,2],[2,2],[4,5]] â†’ 3
 * Example C: [[-5,-2],[-3,1],[0,4],[2,6],[8,9]] â†’ 3
 * Example D: [[1,5],[2,6],[3,7],[4,8],[5,9]] â†’ 1
 * Example E: [[1,100],[2,3],[4,5],[6,7],[50,60],[99,100]] â†’ 5
 *
 * Example A: [[1,10],[2,3],[4,5],[11,12]] â†’ 3
 * [[1,10],[2,3],[4,5],[11,12]] => [[1,10][2,3],[4,5],[11,12]]
 * [1,10][2,3] overlap: yes, counter = 1
 * [2,3],[4,5] no overla
 * [4, 5] no overlap
 *
 * [[1,10],[2,3],[4,5],[11,12]] â†’ 3
 * [2, 3][4,5] = 1
 * [[1,5],[2,6],[3,7],[4,8],[5,9]] â†’ 1 [[1,5],[2,6],[3,7],[4,8],[5,9]]
 *
 * [1,5],[2,6] yes [2, 5]
 * [2,5],[3,7] yes [3, 5]
 * [3, 5][4, 8] yes [4,5]
 * [4, 5][ 5 ,9] es 5
 *
 *
 */

public class P0MinimumNumberOfArrowsToBurstBalloons {

    public  static int findMinArrowShots(int[][] points){

        Arrays.sort(points, (a, b) -> a[0] -b[0]);
        int counter =0;
        int[] point = points[0];
        for (int i=1 ; i<points.length; i++){
            if ( doIntersect(point,points[i] )){
                point = intersect(point, points[i]);
                continue;
            }else {
                point = points[i];
                counter++;
            }
        }
        return counter;
    }

    public static boolean doIntersect(int[] point1, int[] point2){

        int min = Math.max(point1[0], point2[0]);
        int max = Math.min(point1[1], point2[1]);
        return min< max;

    }
    public static int[] intersect(int[] point1, int[] point2){

        int min = Math.max(point1[0], point2[0]);
        int max = Math.min(point1[1], point2[1]);
        return new int[]{min, max};

    }

}

