package com.grokingcodeinterview.pattern14Graphs;

import java.util.*;

import static com.Utility.makeItBold;
/*
Accounts Merge (LeetCode 721, Medium)

Given a list of accounts, where each accounts[i] is a list of strings: the first element is a name, and the rest are emails belonging to that account.
Two accounts belong to the same person if they share at least one common email â€” even if the account names differ,
since the same person can have multiple accounts.
Merge all accounts belonging to the same person: return a list of merged accounts, where the first element of each is the name, followed by all unique emails sorted alphabetically. Accounts themselves can be returned in any order.

Example 1:
Input:

accounts = [
  ["John", "johnsmith@mail.com", "john_newyork@mail.com"],
  ["John", "johnsmith@mail.com", "john00@mail.com"],
  ["Mary", "mary@mail.com"],
  ["John", "johnnybravo@mail.com"]
]

Output:

[
  ["John", "john00@mail.com", "john_newyork@mail.com", "johnsmith@mail.com"],
  ["Mary", "mary@mail.com"],
  ["John", "johnnybravo@mail.com"]
]

Explanation: The first two "John" accounts share johnsmith@mail.com, so they merge into one account with all three unique emails, sorted. The third "John" account has no shared email with the others, so it stays separate â€” same name doesn't mean same person.

Example 2:
Input: accounts = [["Alex","alex@mail.com"],["Alex","alex@mail.com","alex2@mail.com"]]
Output: [["Alex","alex2@mail.com","alex@mail.com"]]
Explanation: Both accounts share alex@mail.com, so they merge.
 */
public class P09AccountsMerge {

    /**
     * graph:
     * key: email, value list of email;
     * johnsmith@mail.com    --> [john_newyork@mail.com, john00@mail.com]
     * john_newyork@mail.com --> [johnsmith@mail.com]
     * john00@mail.com       --> [johnsmith@mail.com]
     * mary@mail.com         --> []
     * johnnybravo@mail.com  --> []
     *
     * emailToName:
     * johnsmith@mail.com    --> "John"
     * john_newyork@mail.com --> "John"
     * john00@mail.com       --> "John"
     * mary@mail.com          --> "Mary"
     * johnnybravo@mail.com  --> "John"
     */

    public static  List<List<String>> accountsMerge(List<List<String>> accounts){
        List<List<String>> mergedAccounts = new ArrayList<>();
        if(accounts == null || accounts.isEmpty()) return mergedAccounts;
        // step1: build graph and emailToName map
        // graph here is adjacecy map, where key : email, value: list of email that are connected to the key email
        // emailToName map is to map each email to name of account holder(ke: email, value: name);


        // key: email, list of all other email
        // what is better name of graph? emailGraph, emailAdjMap, emailConnections, emailNetwork, emailRelations, emailLinks, emailAssociations, emailNeighbors, emailEdges, emailAdjacencyList
        Map<String, List<String>> emailAdj = new HashMap<>();

        Map<String , String> emailToName = new HashMap<>();

        for(List<String> account: accounts){
            String name = account.get(0);
            String firstEmail = account.get(1);

            emailAdj.putIfAbsent(firstEmail, new ArrayList<>());
            emailToName.put(firstEmail, name);
            // building the graph(emailAdj) and emailToName map
            for(int i = 2; i< account.size(); i++){
                String otherEmail = account.get(i);
                emailAdj.putIfAbsent(otherEmail, new ArrayList<>());

                emailToName.put(otherEmail, name);
                emailAdj.get(firstEmail).add(otherEmail);
                emailAdj.get(otherEmail).add(firstEmail);
            }
        }
        // step2: traverse graph and find connected components
        // the set if for keeping track of visited emails
        Set<String> visited = new HashSet<>();

        for (String email: emailAdj.keySet()){

            if(!visited.contains(email)){
                List<String> component = new ArrayList<>();
                dfs(email, emailAdj, visited, component);

                Collections.sort(component);
                component.add(0, emailToName.get(email)); // add name to the componenet but first one
                mergedAccounts.add(component);
            }
        }



        return mergedAccounts;

    }

    public static void dfs(String email, Map<String, List<String>> graph, Set<String> visited, List<String> component){
        if(visited.contains(email)) return;
        visited.add(email);
        component.add(email);
            // is this wrong? the graph
        for (String nei: graph.get(email)){
            dfs(nei, graph, visited, component);
        }

    }

    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("P09. Accounts Merge");
        System.out.println("====================================");

        // Test Case 1: Basic merge with 2 accounts for same person + 1 separate
        List<List<String>> accounts1 = List.of(
                List.of("John", "johnsmith@mail.com", "john00@mail.com"),
                List.of("John", "johnsmith@mail.com", "john_newyork@mail.com"),
                List.of("Mary", "mary@mail.com"),
                List.of("John", "johnnybravo@mail.com")
        );
        List<List<String>> result1 = accountsMerge(accounts1);
        System.out.println("Input: " + accounts1);
        System.out.println("Output: " + makeItBold(result1.toString()));
        System.out.println("Expected: John with merged emails, Mary, and John with single email\n");

        // Test Case 2: Single account per person (no merging)
        List<List<String>> accounts2 = List.of(
                List.of("David", "david0@mail.com", "david1@mail.com"),
                List.of("David", "davidreedkelly@mail.com"),
                List.of("David", "avid@mail.com", "david85@mail.com")
        );
        List<List<String>> result2 = accountsMerge(accounts2);
        System.out.println("Input: " + accounts2);
        System.out.println("Output: " + makeItBold(result2.toString()));
        System.out.println("Expected: 3 separate merged groups\n");

        // Test Case 3: All accounts belong to same person
        List<List<String>> accounts3 = List.of(
                List.of("Alice", "alice@mail.com", "alice_alice@mail.com"),
                List.of("Alice", "alicesmith@mail.com", "alice@mail.com"),
                List.of("Alice", "alice_work@mail.com", "alice_alice@mail.com")
        );
        List<List<String>> result3 = accountsMerge(accounts3);
        System.out.println("Input: " + accounts3);
        System.out.println("Output: " + makeItBold(result3.toString()));
        System.out.println("Expected: Single Alice account with all emails merged\n");
    }
}


