package com.grokingcodeinterview.testYourKnowledge46Easy;

public class pattern35TestYourKnowledgeR2 {
    public static int maxProfit(int[] prices){
       int minPrice = prices[0];
       int maxProfit = 0;

       for(int i =1; i< prices.length; i++){


           if(prices[i]<= minPrice){
               minPrice = prices[i];
           }

           maxProfit = Math.max(prices[i] - minPrice, maxProfit );
       }
       return maxProfit;
    }
}

