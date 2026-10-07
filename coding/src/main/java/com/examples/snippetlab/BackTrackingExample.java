package com.examples.snippetlab;

import java.util.ArrayList;
import java.util.List;

public class BackTrackingExample {
    /**
     * Given two integers n and k, return all possible combinations of k numbers out of the range [1, n].
     *
     * Example: Input: n = 4, k = 2 Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
     * i=1, path = [1] → recurse
     * Inside backtrack(2, 4, 2, [1])
     * i=2, path = [1,2] ✅ → save [1,2]
     * 🔙 Backtrack happens here → remove 2 → path = [1]
     * i=3, path = [1,3] ✅ → save [1,3]
     * 🔙 Backtrack again → remove 3 → path = [1]
     * i=4, path = [1,4] ✅ → save [1,4]
     * 🔙 Backtrack again → remove 4 → path = [1]
     * Done with loop, 🔙 Backtrack again → remove 1 → path = []
     */
    public  static List<List<Integer>> combine (int n , int k){
        List<List<Integer>> result = new ArrayList<>();
        backTrack(1,n,k, new ArrayList<>(), result);
        return result;
    }

    public static void backTrack(int start, int n, int k, List<Integer> path, List<List<Integer>> result){
        if (path.size() == k){
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i<= n; i++){
            path.add(i);                                // ✅ Choose
            backTrack(i+1, n, k, path, result);   // 🔁 Explore
            path.remove(path.size()-1);                       // 🔙 Un-choose (Backtrack)


        }

    }

}
