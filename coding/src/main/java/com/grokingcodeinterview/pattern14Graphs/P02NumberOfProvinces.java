    package com.grokingcodeinterview.pattern14Graphs;

import java.util.Arrays;

    import static com.Utility.makeItBold;

    /*
        Problem Statement
    There are n cities. Some of them are connected in a network. If City A is directly connected to City B, and City B is directly connected to City C, city A is indirectly connected to City C.

    If a group of cities are connected directly or indirectly, they form a province.

    Given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise, determine the total number of provinces.
    Example 1: Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]] Expected Output: 2
    Justification: Here, city 1 and 2 form a single provenance, and city 3 is one province itself.
    Example 2: Input: isConnected = [1,0,0],[0,1,0],[0,0,1]] Expected Output: 3
    Justification: In this scenario, no cities are connected to each other, so each city forms its own province.
    Example 3: Input: isConnected = [[1,0,0,1],[0,1,1,0],[0,1,1,0],[1,0,0,1]] Expected Output: 2
    Justification: Cities 1 and 4 form a province, and cities 2 and 3 form another province, resulting in a total of 2 provinces.
    Constraints:

    1 <= n <= 200
    n == isConnected.length
    n == isConnected[i].length
    isConnected[i][j] is 1 or 0.
    isConnected[i][i] == 1
    isConnected[i][j] == isConnected[j][i]
     */
public class P02NumberOfProvinces {

    public static int findNumberOfProvinces(int[][] isConnected){
        int n = isConnected.length;
        boolean[] visited =new boolean[n];
        int provinces =0;

        for (int city =0; city<n; city++){
            if(!visited[city]){
                dfs(city, isConnected, visited);
                provinces++;
            }
        }

        return provinces;
    }

    public static void dfs(int city, int[][] isConnected, boolean[] visited){
        visited[city] = true;

        for(int nei =0; nei<isConnected.length; nei++){
            if(isConnected[city][nei] ==1 && !visited[nei]){
                dfs(nei, isConnected, visited);
            }
        }
    }


    public static void main(String[] args) {

        System.out.println("===========================");
        System.out.println("P02. Number of Provinces");
        System.out.println("===========================");

        int[][] isConnectedP02 = {{1,1,0},{1,1,0},{0,0,1}};
        System.out.println("Input: " + Arrays.deepToString(isConnectedP02) +", output: " + makeItBold(findNumberOfProvinces(isConnectedP02)+"") + ", Expected Output: 2");
        isConnectedP02 = new int[][]{{1,0,0},{0,1,0},{0,0,1}};
        System.out.println("Input: " + Arrays.deepToString(isConnectedP02) +", output: " + makeItBold(findNumberOfProvinces(isConnectedP02)+"") + ", Expected Output: 3");
        isConnectedP02 = new int[][]{{1,0,0,1},{0,1,1,0},{0,1,1,0},{1,0,0,1}};
        System.out.println("Input: " + Arrays.deepToString(isConnectedP02) +", output: " + makeItBold(findNumberOfProvinces(isConnectedP02)+"") + ", Expected Output: 2");
        isConnectedP02 = new int[][]{{1,0,0,0,0},{0,1,0,0,0},{0,0,1,0,0},{0,0,0,1,1},{0,0,0,1,1}};
        System.out.println("Input: " + Arrays.deepToString(isConnectedP02) +", output: " + makeItBold(findNumberOfProvinces(isConnectedP02)+"") + ", Expected Output: 4");
    }
}

