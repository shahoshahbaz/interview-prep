package com.grokingcodeinterview.pattern14Graphs;
/*
Bus Routes (LeetCode 815, Hard)

You are given an array routes representing bus routes where routes[i] is a bus route that the ith bus repeats forever. For example, if routes[0] = [1, 5, 7], this means the 0th bus travels in the sequence 1 â†’ 5 â†’ 7 â†’ 1 â†’ 5 â†’ 7 â†’ 1 â†’ ... forever.

You start at bus stop source (not on any bus initially, standing at the stop) and want to reach bus stop target. Travel is done by buses only. Return the minimum number of buses you must take to reach target. Return -1 if it's not possible.

Example 1:
Input: routes = [[1,2,7],[3,6,7]], source = 1, target = 6
Output: 2
Explanation: Take bus 0 to stop 7, then take bus 1 to stop 6.

Example 2:
Input: routes = [[7,12],[4,5,15],[6],[15,19],[9,12,13]], source = 15, target = 12
Output: -1
 */

/**
 * 1 ->2
 */

import java.util.*;

import static com.Utility.makeItBold;

public class P05BusRoutes {

    //    public static class busRoutes{
//        int routeNumber;
//        int stop;
//
//        public busRoutes( int routeNumber, int stop)
//    }
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if(source == target) return 0;
        // key: busId, value: list of stops
        Map<Integer, List<Integer>> stopToBuses = new HashMap<>();

        for(int busId =0; busId <routes.length; busId++){
            for (int stop: routes[busId]){
                stopToBuses.computeIfAbsent(stop, k-> new ArrayList<>()).add(busId);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visitedStops = new HashSet<>();
        Set<Integer> visitedBuses = new HashSet<>();

        queue.offer(source);
        visitedStops.add(source);
        int numBuses =0;

        while (!queue.isEmpty()){
            int levelSize = queue.size();

            for (int i =0; i<levelSize; i++){
                int stop = queue.poll();
                if(stop == target) return numBuses;

                for (int bus: stopToBuses.getOrDefault(stop, Collections.emptyList())){
                    if(visitedBuses.contains(bus)) continue;
                    visitedBuses.add(bus);

                    for(int nextStop : routes[bus]){
                        if(!visitedStops.contains(nextStop)){
                            visitedStops.add(nextStop);
                            queue.offer(nextStop);
                        }
                    }
                }

            }
            numBuses ++;
        }


return -1;
    }

    private static void runCase(String label, int[][] routes, int source, int target, int expected) {
        P05BusRoutes solver = new P05BusRoutes();
        int result = solver.numBusesToDestination(routes, source, target);
        System.out.println(label);
        System.out.println("  Input  : routes = " + routesToString(routes) + ", source = " + source + ", target = " + target);
        System.out.println("  Output : " + makeItBold(String.valueOf(result)));
        System.out.println("  Expected: " + expected);
        System.out.println();
    }

    private static String routesToString(int[][] routes) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < routes.length; i++) {
            sb.append(Arrays.toString(routes[i]));
            if (i < routes.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("P05. Bus Routes");
        System.out.println("===========================\n");

        // Test 1: Take bus 0 to stop 7, then bus 1 to stop 6 â†’ 2 buses
        runCase("Test 1: Two-bus journey via shared stop 7",
                new int[][]{{1, 2, 7}, {3, 6, 7}},
                1, 6, 2);

        // Test 2: No connection between source and target â†’ -1
        runCase("Test 2: No path from 15 to 12",
                new int[][]{{7, 12}, {4, 5, 15}, {6}, {15, 19}, {9, 12, 13}},
                15, 12, -1);

        // Test 3: Source equals target, no bus needed â†’ 0
        runCase("Test 3: Source == Target",
                new int[][]{{1, 2, 3}, {4, 5, 6}},
                5, 5, 0);

        // Test 4: Target is on the same first bus â†’ 1 bus
        runCase("Test 4: Source and target share the same bus route",
                new int[][]{{1, 2, 3, 4, 5}},
                1, 5, 1);

        // Test 5: Three hops required: bus 0 â†’ bus 1 â†’ bus 2 â†’ 3 buses
        runCase("Test 5: Three-bus chain",
                new int[][]{{0, 1}, {1, 2}, {2, 3}},
                0, 3, 3);
    }

}
