package com.grokingcodeinterview.pattern14Graphs;

import java.util.*;

import static com.Utility.makeItBold;

/*
Reconstruct Itinerary (LeetCode 332, Hard)

You're given a list of airline tickets, where tickets[i] = [from_i, to_i] represents a flight departing from from_i and arriving at to_i.
Reconstruct the itinerary in order, starting from "JFK", and return it as a list of strings.

Constraints/rules:

You must use all the tickets exactly once.
If there are multiple valid itineraries, return the one that is lexicographically smallest when read as a single string.
Assume it's always possible to reconstruct a valid itinerary using all tickets.

Example 1:
Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
Output: ["JFK","MUC","LHR","SFO","SJC"]

Example 2:
Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
Explanation: ["JFK","SFO","ATL","JFK","ATL","SFO"] is also valid, but lexicographically larger â€” since both use all 5 tickets, the smaller one wins.
 */
public class P06ReconstructItinerary {

    public  static List<String> reconstructItinerary(List<List<String>> tickets){

        LinkedList<String> path = new LinkedList<>();

        Map<String, PriorityQueue<String>> map = new HashMap<>();


        for (List<String> ticket: tickets){
            String from= ticket.get(0);
            String to = ticket.get(1);

            if(!map.containsKey(from)){

            map.put(from, new PriorityQueue<>());

            }
            map.get(from).offer(to);
        }



        if(tickets.size() == 0) return path;

        // we need to find ticket start with JFK

        dfs( "JFK", map, path);
        return path;

    }

    public static void dfs( String city,  Map<String, PriorityQueue<String>> map, List<String>  path){
        PriorityQueue<String> destinations = map.get(city);

        while (destinations != null && !destinations.isEmpty()){
            String next = destinations.poll();
            dfs(next, map, path);
        }

        path.addFirst(city);

    }

    public static void main(String[] args) {
        System.out.println("================================");
        System.out.println("P06. Reconstruct Itinerary");
        System.out.println("================================");

        P06ReconstructItinerary solver = new P06ReconstructItinerary();

        List<List<String>> tickets1 = List.of(
                List.of("MUC", "LHR"),
                List.of("JFK", "MUC"),
                List.of("SFO", "SJC"),
                List.of("LHR", "SFO")
        );
        List<String> expected1 = List.of("JFK", "MUC", "LHR", "SFO", "SJC");
        runCase(solver, tickets1, expected1);

        List<List<String>> tickets2 = List.of(
                List.of("JFK", "SFO"),
                List.of("JFK", "ATL"),
                List.of("SFO", "ATL"),
                List.of("ATL", "JFK"),
                List.of("ATL", "SFO")
        );
        List<String> expected2 = List.of("JFK", "ATL", "JFK", "SFO", "ATL", "SFO");
        runCase(solver, tickets2, expected2);

        List<List<String>> tickets3 = List.of(
                List.of("JFK", "KUL"),
                List.of("JFK", "NRT"),
                List.of("NRT", "JFK")
        );
        List<String> expected3 = List.of("JFK", "NRT", "JFK", "KUL");
        runCase(solver, tickets3, expected3);
    }

    private static void runCase(P06ReconstructItinerary solver, List<List<String>> tickets, List<String> expected) {
        List<String> actual = reconstructItinerary(tickets);
        System.out.println("Input: " + tickets + ", output: " + makeItBold(actual.toString()) + ", Expected Output: " + expected);
    }
}

