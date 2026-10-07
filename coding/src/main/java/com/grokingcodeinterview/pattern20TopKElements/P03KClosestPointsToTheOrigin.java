package com.grokingcodeinterview.pattern20TopKElements;
/*
Problem Statement
Given an array of points in a 2D plane, find â€˜Kâ€™ closest points to the origin.

Example 1:

Input: points = [[1,2],[1,3]], K = 1
Output: [[1,2]]
Explanation: The Euclidean distance between (1, 2) and the origin is sqrt(5).
The Euclidean distance between (1, 3) and the origin is sqrt(10).
Since sqrt(5) < sqrt(10), therefore (1, 2) is closer to the origin.
Example 2:

Input: point = [[1, 3], [3, 4], [2, -1]], K = 2
Output: [[1, 3], [2, -1]]
Constraints:

1 <= k <= points.length <= 104
-104 <= xi, yi <= 104
 */

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class P03KClosestPointsToTheOrigin {

    static class   Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int distFromOrigin() {
            // ignoring sqrt
            return (x * x) + (y * y);
        }
    }

    public static  List<Point> findClosestPoints(Point[] points, int k) {
        PriorityQueue<Point>  maxHeap = new PriorityQueue<>((a, b) -> (b.distFromOrigin() -a.distFromOrigin() ));
         for(Point point :points){
             if (maxHeap.size() <k){
                 maxHeap.add(point);
             }else{
                 if (!maxHeap.isEmpty() && point.distFromOrigin()< maxHeap.peek().distFromOrigin()) {
                     maxHeap.poll();
                     maxHeap.add(point);
                 }

             }
         }

        return new ArrayList<Point>(maxHeap);
    }


    public static void main(String[] args) {
        // Test case 1
        Point[] points = new Point[] { new Point(1, 2), new Point(1, 3), new Point(3, 4)};
        int k1 = 2;
        System.out.print("Input: points = [");
        for (int i = 0; i < points.length; i++) {
            System.out.print("[" + points[i].x + "," + points[i].y + "]");
            if (i < points.length - 1) System.out.print(", ");
        }
        System.out.println("], K = " + k1);
        List<Point> result = findClosestPoints(points, k1);
        System.out.print("Output: [");
        for (int i = 0; i < result.size(); i++) {
            Point p = result.get(i);
            System.out.print("[" + p.x + "," + p.y + "]");
            if (i < result.size() - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("Expected Output: [[1,2], [1,3]]\n");

        // Test case 2
        Point[] points2 = new Point[] { new Point(1, 3), new Point(3, 4), new Point(2, -1)};
        int k2 = 2;
        System.out.print("Input: points = [");
        for (int i = 0; i < points2.length; i++) {
            System.out.print("[" + points2[i].x + "," + points2[i].y + "]");
            if (i < points2.length - 1) System.out.print(", ");
        }
        System.out.println("], K = " + k2);
        List<Point> result2 = findClosestPoints(points2, k2);
        System.out.print("Output: [");
        for (int i = 0; i < result2.size(); i++) {
            Point p = result2.get(i);
            System.out.print("[" + p.x + "," + p.y + "]");
            if (i < result2.size() - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("Expected Output: [[1,3], [2,-1]]\n");
    }


}

