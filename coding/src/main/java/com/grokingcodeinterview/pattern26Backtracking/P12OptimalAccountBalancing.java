package com.grokingcodeinterview.pattern26Backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.Utility.makeItBold;

public class P12OptimalAccountBalancing {

    public static int minTransfers(int[][] transactions) {

        List<Integer> nets = getNetBalancing(transactions);
        return backtrack(nets, 0);
    }
     public  static List<Integer> getNetBalancing(int[][] txs){
         HashMap<Integer, Integer> balances = new HashMap<>();
         List<Integer> nets = new ArrayList<>();

         for(int[] tx: txs){
             int from = tx[0];
             int to = tx[1];
             int value = tx[2];
             balances.merge(from,-value, Integer::sum );
             balances.merge(to, value, Integer::sum);


         }

         for (int value: balances.values()){
             if(value != 0){
                 nets.add(value);
             }

         }

//         nets = balances.values().stream()
//                 .filter(v -> v !=0)
//                 .collect(Collectors.toList());

         return nets;

     }

     public static int backtrack(List<Integer> nets, int start){
         while(start< nets.size() && nets.get(start) ==0){
             start++;
         }

         if(start == nets.size()) return 0;

         int minTxs = Integer.MAX_VALUE;

         int curr= nets.get(start);

         for (int i = start+1;i< nets.size(); i++ ){
             int other = nets.get(i);
             // the below if is to check:
             // 1. other is not zero
             // 2. other and curr have opposite signs, i.e. one is positive and the other is negative
             if(other !=0 && (other>0) != (curr> 0)){
                 // choose: how to choose? we need to update the value of nets[i] which is other, so we set it to curr + other
                 nets.set(i, curr + other);
                 // explore
                 minTxs = Math.min(minTxs, 1+ backtrack(nets,start+1 ));

                 // unchoose: how to unchoose? we need to restore the original value of nets[i] which is other, so we set it back to other
                 nets.set(i, other);

             }
         }
         return minTxs;

     }

     public static void main(String[] args) {

         System.out.println("===========================");
         System.out.println("P11. Optimal Account Balancing");
         System.out.println("===========================");

         // Test 1: simple three-person case
         int[][] txs1 = {{0, 1, 10}, {2, 0, 5}};
         int result1 = minTransfers(txs1);
         System.out.println("Input:  " + Arrays.deepToString(txs1) + " ,Output: " + makeItBold(result1 + "") + " ,Expected: 2");

         // Test 2: cycle of three people
         int[][] txs2 = {{0, 1, 10}, {1, 2, 5}, {2, 0, 5}};
         int result2 = minTransfers(txs2);
         System.out.println("Input:  " + Arrays.deepToString(txs2) + " ,Output: " + makeItBold(result2 + "") + " ,Expected: 1");

         // Test 3: single transaction
         int[][] txs3 = {{0, 1, 100}};
         int result3 = minTransfers(txs3);
         System.out.println("Input:  " + Arrays.deepToString(txs3) + " ,Output: " + makeItBold(result3 + "") + " ,Expected: 1");

         // Test 4: four people with complex debts
         int[][] txs4 = {{0, 1, 10}, {0, 2, 5}, {2, 3, 5}, {1, 3, 10}};
         int result4 = minTransfers(txs4);
         System.out.println("Input:  " + Arrays.deepToString(txs4) + " ,Output: " + makeItBold(result4 + "") + " ,Expected: 1");

         // Test 5: circular chain
         int[][] txs5 = {{0, 1, 1}, {1, 2, 1}, {2, 0, 1}};
         int result5 = minTransfers(txs5);
         System.out.println("Input:  " + Arrays.deepToString(txs5) + " ,Output: " + makeItBold(result5 + "") + " ,Expected: 0");
     }
}

