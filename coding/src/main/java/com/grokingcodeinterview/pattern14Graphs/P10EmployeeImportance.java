package com.grokingcodeinterview.pattern14Graphs;

import java.util.*;

/*
You have a list of employees,
 each with a unique id, an importance value, and a list of subordinates (employee ids they manage).
  Given an employee id,
   return the total importance of that employee plus all of their subordinates, recursively (subordinates' subordinates count too).

Example:
Input: employees = [[1,5,[2,3]],[2,3,[]],[3,3,[]]], id = 1
Output: 11
Explanation: Employee 1 has importance 5, and has two subordinates (2 and 3), each with importance 3. Total = 5 + 3 + 3 = 11.

This is a clean first BFS problem because there's no grid, no distance tracking â€” just: BFS from the given id, sum up importance as you visit each node via its subordinates list. Basically your cheat sheet's "Basic BFS (Single Source)" template with a running sum instead of a visited[] check.
 */
public class P10EmployeeImportance {
    private static  class  Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;
    }

    public int getImportance(List<Employee> employees, int id){

        Map<Integer, Employee> empMap = new HashMap<>();

        for (Employee emp: employees){
            empMap.put(emp.id, emp);

        }

        int total = 0;

        Queue<Integer> queue= new ArrayDeque<>();
        queue.offer(id);

        while (!queue.isEmpty()){
            int currId = queue.poll();
            Employee currEmp =empMap.get(currId);
            total += currEmp.importance;

            for(int subId: currEmp.subordinates){
                queue.offer(subId);
            }
        }

        return total;



    }
}

