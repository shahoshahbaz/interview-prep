package com.grokingcodeinterview.pattern20TopKElements;

import java.util.*;

public class Pattern21TopKElementR3{


    public static class Record {
        int freq;
        int value;
        public Record(int freq, int value){
            this.freq = freq;
            this.value = value;
        }
    }


    public static List<Integer> findTopKFrequentNumbers(int[] nums, int k){
        List<Integer > list = new ArrayList<>();
         // edge cases
        if(nums == null ) return list;

        Map<Integer, Integer> freqMap = new HashMap<>();

        for(int num:nums) freqMap.put(num, freqMap.getOrDefault(num, 0) +1);



        PriorityQueue<Record> minHeap = new PriorityQueue<>((r1,r2) -> r1.freq - r2.freq);



        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            minHeap.offer(new Record(entry.getKey(), entry.getValue()));
            if (minHeap.size() > k) {
                minHeap.poll(); // evict the current weakest
            }

        }

        while(!minHeap.isEmpty()){
            list.add(minHeap.poll().value);
        }

        return list;
    }

    public  static String sortCharacterByFrequency(String str){

        // use StringBuilder to build string
        Map<Character, Integer> freqMap = new HashMap<>();

        for (char ch: str.toCharArray()){
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) +1);
        }

        PriorityQueue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>( (e1, e2) -> e2.getValue() - e1.getValue());

        for(Map.Entry<Character, Integer> entry: freqMap.entrySet() ){
            maxHeap.offer(entry);


        }

        StringBuilder sb = new StringBuilder();

        while(!maxHeap.isEmpty()){
            Map.Entry<Character, Integer> entry = maxHeap.poll();

            for (int i =0; i< entry.getValue(); i++){
                sb.append(entry.getKey());

            }
        }

        return sb.toString();
    }
}

