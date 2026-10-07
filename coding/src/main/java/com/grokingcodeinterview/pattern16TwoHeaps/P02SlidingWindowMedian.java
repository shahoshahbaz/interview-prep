package com.grokingcodeinterview.pattern16TwoHeaps;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Problem Statement
 * Given an array of numbers and a number â€˜kâ€™, find the median of all the â€˜kâ€™ sized sub-arrays (or windows) of the array.
 */
public class P02SlidingWindowMedian {

    PriorityQueue<Integer> lowerHalf = new PriorityQueue<>((a, b) -> b - a); // max heap // the largest number will be at the top
    PriorityQueue<Integer> upperHalf = new PriorityQueue<>((a, b) -> a - b); // min heap // the smallest number will be at tht top
    Map<Integer, Integer> delayed = new HashMap<>(); // to keep track of the numbers that need to be removed lazily

    public void insertNum(int num) {
        // step1: where to insert the number
        // if the number is smaller than the largest number in the lower half, it belongs to
        if (lowerHalf.isEmpty() || num <= lowerHalf.peek()) {
            lowerHalf.offer(num);
        } else {
            upperHalf.offer(num);

        }
        // step2: re-balancing heap if needed
        reBalance();

    }

    public void reBalance(){
        // the size difference between the two heaps should not be more than 1
        // if it is, we need to move the top element from the larger heap to the smaller heap
        if(lowerHalf.size()> upperHalf.size() +1){
            upperHalf.offer(lowerHalf.poll());

        } else if(upperHalf.size()> lowerHalf.size() +1){
            lowerHalf.offer(upperHalf.poll());
        }
    }


    public void remove (int num){
        // step1: remove the number from the appropriate heap


        if(num <= lowerHalf.peek()){
            lowerHalf.remove(num);
        } else {
            upperHalf.remove(num);

        }
        // step2: re-balance the heaps if needed
        reBalance();





    }
    // another approach is to use a lazy removal technique where we keep a hash map to count the occurrences of each number and only remove them when they reach the top of the heap. This can be more efficient in terms of time complexity.
    public void removeLazy(int num){

        // step1: mark the number as removed in a hash map
        delayed.put(num,delayed.getOrDefault(num, 0)+1);

        // step2: when we need to remove a number, we check if it is at the top of the heap
        // if it is, we remove it and decrement its count in the hash map
        // if it is not, we just decrement its count in the hash map
        prune(lowerHalf);
        prune(upperHalf);

        // step3: re-balance the heaps if needed
        reBalance();
    }

    public void prune(PriorityQueue<Integer> heap){
        // remove the elements that are marked as removed in the hash map
        // from the top of the heap until we find an element that is not marked as removed
        // or the heap is empty

        while(!heap.isEmpty()){

            int num = heap.peek();
            if(delayed.containsKey(num)){
                delayed.put(num, delayed.get(num)-1);
                if(delayed.get(num) == 0){
                    delayed.remove(num);
                }
                heap.poll();
            } else {
                break;
            }
        }
    }

    public double findMedian(){
        // if both heaps are of equal size, the median is the average of the two top elements
        // if one heap is larger, the median is the top element of that heap

        if (lowerHalf.size() == upperHalf.size()) {
            return (lowerHalf.peek() + upperHalf.peek()) / 2.0;
        } else if (lowerHalf.size() > upperHalf.size()) {
            return lowerHalf.peek();
        } else {
            return upperHalf.peek();
        }
    }

    public double[] findSlidingWindowMedian(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            insertNum(nums[i]);
            if (i - k + 1 >= 0) { // if we have at least 'k' elements in the sliding window
                result[i - k + 1] = findMedian();
                remove(nums[i - k + 1]); // remove the element going out of the sliding window
                // or use lazy removal
                // removeLazy(nums[i - k + 1]);

            }
        }
        return result;
    }

    public double[] findSlidingWindowMedianWithLazyRemoval(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            insertNum(nums[i]);
            if (i - k + 1 >= 0) { // if we have at least 'k' elements in the sliding window
                result[i - k + 1] = findMedian();

                // use lazy removal
                // removeLazy(nums[i - k + 1]);

            }
        }
        return result;
    }

    public static void main(String[] args) {
        P02SlidingWindowMedian slidingWindowMedian = new P02SlidingWindowMedian();
        long start = System.nanoTime();
        double[] result = slidingWindowMedian.findSlidingWindowMedian(new int[] { 1, 2, -1, 3, 5 }, 2);
        long end = System.nanoTime();
        long duration = end - start;
        System.out.print("Sliding window medians are: ");
        for (double num : result)
            System.out.print(num + " ");
        System.out.println("Time taken by normal removal: " + duration + " nanoseconds");

        System.out.println();

        // measure time taken by lazy removal
        start = System.nanoTime();

        result = slidingWindowMedian.findSlidingWindowMedianWithLazyRemoval(new int[] { 1, 2, -1, 3, 5 }, 2);
        end = System.nanoTime();
        duration = end - start;


        System.out.print("Sliding window medians with lazy removal are: ");

        System.out.println("Time taken by lazy removal: " + duration + " nanoseconds");

        for (double num : result)
            System.out.print(num + " ");
        System.out.println();


        slidingWindowMedian = new P02SlidingWindowMedian();
        result = slidingWindowMedian.findSlidingWindowMedian(new int[] { 1, 2, -1, 3, 5 }, 3);
        System.out.print("Sliding window medians are: ");
        for (double num : result)
            System.out.print(num + " ");

        // Test with a very large array and large window size
        int largeSize = 1000000;
        int largeWindow = 50000;
        int[] largeArray = new int[largeSize];
        for (int i = 0; i < largeSize; i++) {
            largeArray[i] = i % 1000; // fill with some repeating pattern
        }

        slidingWindowMedian = new P02SlidingWindowMedian();
        start = System.nanoTime();
        result = slidingWindowMedian.findSlidingWindowMedian(largeArray, largeWindow);
        end = System.nanoTime();
        duration = end - start;
        System.out.println("Time taken by normal removal (large array): " + duration + " nanoseconds");

        slidingWindowMedian = new P02SlidingWindowMedian();
        start = System.nanoTime();
        result = slidingWindowMedian.findSlidingWindowMedianWithLazyRemoval(largeArray, largeWindow);
        end = System.nanoTime();
        duration = end - start;
        System.out.println("Time taken by lazy removal (large array): " + duration + " nanoseconds");
    }



}

