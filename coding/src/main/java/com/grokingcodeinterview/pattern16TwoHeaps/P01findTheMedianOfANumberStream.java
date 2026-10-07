package com.grokingcodeinterview.pattern16TwoHeaps;

import java.util.PriorityQueue;

public class P01findTheMedianOfANumberStream {

    private PriorityQueue<Integer> maxHeap; // the largest number will be at the top
    private PriorityQueue<Integer> minHeap; // the smallest number will be at tht top

    public P01findTheMedianOfANumberStream() {
        maxHeap = new PriorityQueue<>((a, b) -> b - a); // max heap
        minHeap = new PriorityQueue<>(); // min heap
    }

    public void insertNum(int num) {
        // step1: where to insert the number
        // if the number is smaller than the top of maxHeap, it belongs to maxHeap
        // if the number is larger than the top of maxHeap, it belongs to minHeap
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        // step2: re-balancing heap if needed
        // the size difference between the two heaps should not be more than 1
        // if it is, we need to move the top element from the larger heap to the smaller heap

        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size() + 1) {
            maxHeap.offer(minHeap.poll());
        }


    }

    public double findMedian() {
        // if both heaps are of equal size, the median is the average of the two top elements
        // if one heap is larger, the median is the top element of that heap

        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        } else {
            return minHeap.peek();
        }
    }

    public static void main(String[] args) {
        P01findTheMedianOfANumberStream medianOfAStream = new P01findTheMedianOfANumberStream();
        System.out.println("inserting numbers: 3, 1");
        medianOfAStream.insertNum(3);
        medianOfAStream.insertNum(1);

        System.out.println("The median is so far: " + medianOfAStream.findMedian());
        System.out.println("inserting number: 5");
        medianOfAStream.insertNum(5);

        System.out.println("The median is: " + medianOfAStream.findMedian());
        medianOfAStream.insertNum(4);
        System.out.println("inserting number: 4");

        System.out.println("The median is: " + medianOfAStream.findMedian());

    }
}

