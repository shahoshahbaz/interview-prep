package com.grokingcodeinterview.pattern20TopKElements;

import java.util.*;

import static com.Utility.*;

public class Pattern21TopKElementR2 {
/*
problem statement:
Design a class to efficiently find the Kth largest element in a stream of numbers.
The class should have the following two things:
The constructor of the class should accept an integer array containing initial numbers from the stream and an integer â€˜Kâ€™.
The class should expose a function add(int num) which will store the given number and return the Kth largest number.
Example 1:
Input: [3, 1, 5, 12, 2, 11], K = 4
1. Calling add(6) should return '5'.
2. Calling add(13) should return '6'.
2. Calling add(4) should still return '6'.
Constraints:

1 <= k <= 10^4
0 <= nums.length <= 10^4
-104 <= nums[i] <= 10^4
-104 <= val <= 10^4
At most 10^4 calls will be made to add.
It is guaranteed that there will be at least k elements in the array when you search for the kth element.
 */
    public static class KthLargestHelper{

        int k ;
        PriorityQueue<Integer> minHeap;
        public KthLargestHelper(int[] nums, int k){
            this.k =k;
            minHeap = new PriorityQueue<>();
            for (int num:nums){
                add(num);
            }

        }

        public int add(int num){
            minHeap.offer(num);
            if(minHeap.size() >k ){
                minHeap.poll();
            }
            return minHeap.peek();



    }

}

    /*
 Problem Statement
 Given a sorted number array and two integers â€˜Kâ€™ and â€˜Xâ€™,
 find â€˜Kâ€™ closest numbers to â€˜Xâ€™ in the array.
 Return the numbers in the sorted order. â€˜Xâ€™ is not necessarily present in the array.
 *
 Example 1:  Input: [5, 6, 7, 8, 9], K = 3, X = 7   Output: [6, 7, 8]
 Example 2:  Input: [2, 4, 5, 6, 9], K = 3, X = 6   Output: [4, 5, 6]
 Example 3:  Input: [2, 4, 5, 6, 9], K = 3, X = 10  Output: [5, 6, 9]
 Constraints:

 1 <= k <= arr.length
 1 <= arr.length <= 104
 arr is sorted in ascending order.
 -104 <= arr[i], x <= 104
 */

    public static  List<Integer> findClosestElements(int[] nums, int k, int x){

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> {
            int diff = Math.abs(b - x) - Math.abs(a - x);
            if (diff != 0) return diff;
            return b-a;
        });

        for (int num: nums){
            maxHeap.offer(num);
            if (maxHeap.size()> k){
                maxHeap.poll();
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!maxHeap.isEmpty()){
            result.add(maxHeap.poll());

        }

        Collections.sort(result);

        return result;


    }
/*
Problem Statement
Given â€˜Nâ€™ ropes with different lengths,
 we need to connect these ropes into one big rope with minimum cost.
  The cost of connecting two ropes is equal to the sum of their lengths.


Example 1: Input: [1, 3, 11, 5] Output: 33
Explanation: First connect 1+3(=4), then 4+5(=9), and then 9+11(=20). So the total cost is 33 (4+9+20)
Example 2: Input: [3, 4, 5, 6] Output: 36
Explanation: First connect 3+4(=7), then 5+6(=11), 7+11(=18). Total cost is 36 (7+11+18)
Example 3: Input: [1, 3, 11, 5, 2] Output: 42
Explanation: First connect 1+2(=3), then 3+3(=6), 6+5(=11), 11+11(=22). Total cost is 42 (3+6+11+22)
Constraints:
1 <= ropLengths.length <= 104
1 <= ropLengths[i] <= 104
 */

    /**
     * [3, 4, 5, 6] Output: 36
     * minHeap = [3, 4, 5, 6)
     * 3, 4 cost = 7 ,  minHeap = [5,6, 7]
     * 5, 6, cost = 7+11 = 18 minheap [7, 11]
     * 7, 11 = cost = 18 + 18  cost = 36
     *
     */
    public  static int minimumCostToConnectRopes(int[] ropeLengths){

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int rope: ropeLengths){
            minHeap.offer(rope);
        }

        int cost = 0;
        while (minHeap.size()>= 2){
            int length = minHeap.poll() + minHeap.poll();
            cost = cost + length;
            minHeap.offer(length);

        }

        return cost;

}
/*
Problem Statement
Given an array of numbers nums and an integer K,
 find the maximum number of distinct elements
 after removing exactly K elements from the nums array.

Example 1: Input: nums = [7, 3, 5, 8, 5, 3, 3], K=2 Expected Output: 3
Explanation: We can remove two occurrences of 3 to be left with 3 distinct numbers [7, 3, 8],
 we have to skip 5 because it is not distinct and occurred twice.
 Another solution could be to remove one instance of '5' and '3' each to be left with three distinct numbers [7, 5, 8], in this case, we have to skip 3 because it occurred twice.

Example 2: Input: [3, 5, 12, 11, 12], and K=3Expected Output: 2
Explanation: We can remove one occurrence of 12, after which all numbers will become distinct. Then we can delete any two numbers which will leave us 2 distinct numbers in the result.

Example 3:Input: [1, 2, 3, 3, 3, 3, 4, 4, 5, 5, 5], and K=2 Expected Output: 3
Explanation: We can remove one occurrence of '4' to get three distinct numbers 1, 2 and 4.
Constraints:

1 <= arr.length <= 105
1 <= arr[i] <= 109
0 <= k <= arr.length
 */

    public static  int findMaximumDistinctElements(int[] nums, int k){

        LOGGING_FLAG = true;

        HashMap<Integer, Integer> freqMap = new HashMap<>();
        for (int num: nums){
            freqMap.put(num, freqMap.getOrDefault(num, 0) +1);

        }
        log("freqMap:" +makeItBold(freqMap.toString()));

        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>((a,b)-> b.getValue() -a.getValue());
        int distinctElementCounter =0;

        for (Map.Entry<Integer, Integer> entry: freqMap.entrySet()){
            if (entry.getValue() ==1){
                distinctElementCounter ++;
            }else{
                maxHeap.add(entry);
            }
        }

        log("maxHeap:" + makeItBold(maxHeap.toString()));

        while (k>0 && !maxHeap.isEmpty()){
            Map.Entry<Integer, Integer> entry = maxHeap.poll();
            log("Processing element " + makeItBold(""+ entry.getKey()) + " with frequency " + makeItBold(""+ entry.getValue()));

            int freqElement = entry.getValue();
            log("Frequency of element " + makeItBold(""+ entry.getKey()) + " is " + makeItBold(""+ freqElement));
             int removal = freqElement -1;
             log("To make element " + makeItBold(""+ entry.getKey()) + " distinct, need to remove " + makeItBold(""+ removal) + " occurrences");

             if (k>= removal){
                 log("Removing " + makeItBold(""+removal) + " from element " + makeItBold(""+ entry.getKey()));
                 k -= removal;
                 distinctElementCounter++;
             }else{
                 log("Cannot remove all occurrences of element " + makeItBold(""+ entry.getKey()) + ", breaking out");
                 break;
             }
            log("Remaining k: " + makeItBold(""+k));

        }
        log("Distinct element count so far: " + makeItBold(""+ distinctElementCounter) + ", Remaining k: " + makeItBold(""+k));
        if (k>0){
            log("Removing distinct elements using remaining k: " + makeItBold(""+k));
            distinctElementCounter -= k;
        }
        log("Final distinct element count: " + makeItBold(""+ distinctElementCounter));
        return distinctElementCounter;

    }

    /*
Problem Statement
Given a string, sort it based on the decreasing frequency of its characters.

Example 1:

Input: "Programming"
Output: "rrggmmPiano"
Explanation: 'r', 'g', and 'm' appeared twice, so they need to appear before any other character.
Example 2:

Input: "abcbab"
Output: "bbbaac"
Explanation: 'b' appeared three times, 'a' appeared twice, and 'c' appeared only once.
Constraints:

1 <= str.length <= 5 * 105
str consists of uppercase and lowercase English letters and digits.
 */
    public  static String sortCharacterByFrequency(String str){
        if (str.length()<=1) return str;

        HashMap<Character, Integer> freqMap = new HashMap<>();

        for (char ch: str.toCharArray()){
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) +1);
        }

        PriorityQueue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>((a, b )-> b.getValue()- a.getValue());

        for (Map.Entry<Character, Integer> entry: freqMap.entrySet()){
            maxHeap.offer(entry);
        }

        StringBuilder sb = new StringBuilder();

        while(!maxHeap.isEmpty()){
            Map.Entry entry =maxHeap.poll();

            for (int i =0; i<(int) entry.getValue(); i++){
                sb.append(entry.getKey());

            }

        }
         return sb.toString();
    }

    /*
    Problem Statement
    Given an unsorted array of numbers, find the top â€˜Kâ€™ frequently occurring numbers in it.
            Example 1:  Input: [1, 3, 5, 12, 11, 12, 11], K = 2  Output: [12, 11]
    Explanation: Both '11' and '12' appeared twice.

    Example 2:  Input: [5, 12, 11, 3, 11], K = 2  Output: [11, 5] or [11, 12] or [11, 3]
    Explanation: Only '11' appeared twice; all other numbers appeared once.

            Constraints:
            1 <= nums.length <= 105
            -105 <= nums[i] <= 105
    k is in the range [1, the number of unique elements in the array].
    It is guaranteed that the answer is unique.
   */

    /**
     freqMap: [1:1, 3: 1, 5:1, 11:2, 12:2]
     minHeap: [1:1, 3: 1, 5:1, 11:2, 12:2]

     *
     */


    public static List<Integer> findTopKFrequentNumbers(int[] nums, int k){

        List<Integer> result = new ArrayList<>();
        HashMap<Integer, Integer> freqMap = new HashMap<>();

        for (int num: nums){
            freqMap.put(num, freqMap.getOrDefault(num, 0)+1);

        }

        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>((a,b) -> a.getValue() - b.getValue());

        for (Map.Entry<Integer, Integer> entry: freqMap.entrySet()){
            minHeap.offer(entry);
            if (minHeap.size()> k){
                minHeap.poll();
            }
        }

        while (!minHeap.isEmpty()){
            result.add(minHeap.poll().getKey());
        }

        return result;
    }
    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int distFromOrigin() {
            // ignoring sqrt
            return (x * x) + (y * y);
        }
    }
/*
Problem Statement
Given an array of points in a 2D plane,
 find â€˜Kâ€™ closest points to the origin.

Example 1: Input: points = [[1,2],[1,3]], K = 1 Output: [[1,2]]
Explanation: The Euclidean distance between (1, 2) and the origin is sqrt(5).
The Euclidean distance between (1, 3) and the origin is sqrt(10).
Since sqrt(5) < sqrt(10), therefore (1, 2) is closer to the origin.
Example 2:

Input: point = [[1, 3], [3, 4], [2, -1]], K = 2
Output: [[1, 3], [2, -1]]
Constraints:

1 <= k <= points.length <= 104
-104 <= xi, yi <= 104
 */
public static List<Point> findClosestPoints(Point[] points, int k){
    List<Point> result = new ArrayList<>();
    if (points.length ==1 && k ==1) {
        result.add(points[0]);
        return result;
    }
    PriorityQueue<Point> maxHeap = new PriorityQueue<>((a,b)-> b.distFromOrigin() - a.distFromOrigin());
    for (Point point: points){
        maxHeap.offer(point);

        if (maxHeap.size()> k){
            maxHeap.poll();
        }
    }

    while (!maxHeap.isEmpty()){
        result.add(maxHeap.poll());
    }
    return result;



}

    /*
     * Given an unsorted array of numbers, find the â€˜Kâ€™ largest numbers in it.
     *
     * Example 1:
     *
     * Input: [3, 1, 5, 12, 2, 11], K = 3
     * Output: [5, 12, 11]
     * Example 2:
     *
     * Input: [5, 12, 11, -1, 12], K = 3
     * Output: [12, 11, 12]
     * Constraints:
     *
     * 1 <= nums.length <= 105
     * -105 <= nums[i] <= 105
     * k is in the range [1, the number of unique elements in the array].
     * It is guaranteed that the answer is unique.
     */
    //
    // find K largest element
    /**
     *   [3, 1, 5, 12, 2, 11], K = 3
     *   min[]

     */

    public static List<Integer> findKLargestNumbers (int[] nums, int k){

        List<Integer> results = new ArrayList<>();
        if (nums == null) return results;

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num: nums){
            minHeap.offer(num);
        }

        while (!minHeap.isEmpty() && minHeap.size()> k){
            minHeap.poll();
        }

        while(!minHeap.isEmpty()){
            results.add(minHeap.poll());
        }

        return results;



    }

/*
    Kth Smallest Number (easy)
    Problem Statement
    Given an unsorted array of numbers, find Kth smallest number in it.

    Please note that it is the Kth smallest number in the sorted order, not the Kth distinct element.

            Note: For a detailed discussion about different approaches to solve this problem, take a look at Kth Smallest Number.

            Example 1:

    Input: [1, 5, 12, 2, 11, 5], K = 3
    Output: 5
    Explanation: The 3rd smallest number is '5', as the first two smaller numbers are [1, 2].
    Example 2:

    Input: [1, 5, 12, 2, 11, 5], K = 4
    Output: 5
    Explanation: The 4th smallest number is '5', as the first three small numbers are [1, 2, 5].
    Example 3:

    Input: [5, 12, 11, -1, 12], K = 3
    Output: 11
    Explanation: The 3rd smallest number is '11', as the first two small numbers are [5, -1].
    Constraints:

            1 <= k <= nums.length <= 105
            -104 <= nums[i] <= 104
            */

    /**
     * Input: [1, 5, 12, 2, 11, 5], K = 3     *     Output: 5
     *
     * maxHeap = [ 12, 5,1]
     * if
     * @param nums
     * @param k
     * @return
     */
    public static int findKthSmallestNumber (int[] nums, int k){

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b ) -> b -a);


        for (int i =0; i< k; i++){
            maxHeap.offer(nums[i]);
        }

        for (int i = k; i< nums.length; i++){

            if (!maxHeap.isEmpty() && maxHeap.peek()>  nums[i]){
                maxHeap.poll();
                maxHeap.offer(nums[i]);
            }
        }

        return maxHeap.peek();

    }


    public static void main(String[] args) {

        System.out.println("++++++++++++++++++++++++++++");
        System.out.println("P01. Top K largest number");
        // some 4 test cases

         int[] arrP01 = new int[] { 3, 1, 5, 12, 2, 11 };
        System.out.print("Array is: " + Arrays.toString(arrP01) +"\t");
        List<Integer> result = findKLargestNumbers(arrP01, 3);
        System.out.println("Here are the K largest numbers: " + result); // expected [5, 12, 11]
        arrP01 = new int[] { 5, 12, 11, -1, 12 };
        System.out.print("Array is: " + Arrays.toString(arrP01) +"\t");
        result = findKLargestNumbers(arrP01, 3);
        System.out.println("Here are the K largest numbers: " + result); // expected [12, 11, 12]

        arrP01 = new int[] { 1, 2, 3, 4, 5 };
        System.out.print("Array is: " + Arrays.toString(arrP01) +"\t");
        result = findKLargestNumbers(arrP01, 2);
        System.out.println("Here are the K largest numbers: " + result); // expected [4, 5]
        arrP01 = new int[] { 5, 5, 5, 5, 5 };
        System.out.print("Array is: " + Arrays.toString(arrP01) +"\t");
        result = findKLargestNumbers(arrP01, 1);
        System.out.println("Here are the K largest numbers: " + result); // expected [5]



        // add some test cases
        int resultp02 = findKthSmallestNumber(new int[] { 1, 5, 12, 2, 11, 5 }, 3); // expect 5
        System.out.println("Kth smallest number is: " + resultp02);
        resultp02 = findKthSmallestNumber(new int[] { 1, 5, 12, 2, 11, 5 }, 4); // expect 5
        System.out.println("Kth smallest number is: " + resultp02);
        resultp02 = findKthSmallestNumber(new int[] { 5, 12, 11, -1, 12 }, 3); // expect 11
        System.out.println("Kth smallest number is: " + resultp02);

        System.out.println("==================================");
        System.out.println("P03 K closest Points to the origin");
        System.out.println("==================================");


        // some test cases
        Point[] points = new Point[] {new Point(1, 2),
                new Point(1, 3),
                new Point(3, 4)};
        List<Point> resultP03 =findClosestPoints(points, 2);
        System.out.println("Here are the k closest points to the origin: ");
        for (Point p : resultP03) {
            System.out.println("[" + p.x + " , " + p.y + "]");
        }

        // expected [[1,2], [1,3]]

        // more test cases
       Point[] points2 = new Point[] {  new Point(1, 3),
                new Point(3, 4),
                new Point(2, -1)};
        List<Point> result2 = findClosestPoints(points2, 2);
        System.out.println("Here are the k closest points to the origin: ");
        for (Point p : result2) {
            System.out.println("[" + p.x + " , " + p.y + "]");

        }
        // expected [[1,3], [2,-1]]



        System.out.println("==============================================");
        System.out.println("P05. find top K frequent numbers...");
        System.out.println("==============================================");

        int[] numsP05 = {1, 3, 5, 12, 11, 12, 11};
        int kP05 =2;


        List<Integer> resultP05 = findTopKFrequentNumbers(numsP05, kP05);
        System.out.println("Input: " +makeItBold(Arrays.toString(numsP05)) + ", K: " + makeItBold(""+kP05) +
                ", Output: " + makeItBold(resultP05.toString()) + ", Expected: " + makeItBold("[12, 11]"));

        numsP05 = new int[]{5, 12, 11, 3, 11};
        kP05 =2;
        resultP05 = findTopKFrequentNumbers(numsP05, kP05);
        System.out.println("Input: " +makeItBold(Arrays.toString(numsP05)) + ", K: " + makeItBold(""+kP05) +
                ", Output: " + makeItBold(resultP05.toString()) + ", Expected: " + makeItBold("[11, 5] or [11, 12] or [11, 3]"));

        numsP05 = new int[]{5, 12, 11, 3, 11};
        kP05 =2;
        resultP05 = findTopKFrequentNumbers(numsP05, kP05);
        System.out.println("Input: " +makeItBold(Arrays.toString(numsP05)) + ", K: " + makeItBold(""+kP05) +
                ", Output: " + makeItBold(resultP05.toString()) + ", Expected: " + makeItBold("[11, 5] or [11, 12] or [11, 3]"));

        numsP05 = new int[]{4, 4, 4, 4, 4};
        kP05 =1;
        resultP05 = findTopKFrequentNumbers(numsP05, kP05);
        System.out.println("Input: " +makeItBold(Arrays.toString(numsP05)) + ", K: " + makeItBold(""+kP05) +
                ", Output: " + makeItBold(resultP05.toString()) + ", Expected: " + makeItBold("[4]"));



        System.out.println("=======================================");
        System.out.println("P06. Frequency Sort");
        System.out.println("=======================================");

        String str06 = "Programming";
        System.out.println("Iput: " + makeItBold(str06) + ", Sort chars by frequnency :" +makeItBold(sortCharacterByFrequency(str06))+" Expected: rrggmmPiano");
        str06 = "abcbab";
        System.out.println("Iput: " + makeItBold(str06) + ", Sort chars by frequnency :" +makeItBold(sortCharacterByFrequency(str06))+" Expected: bbbaac");
        str06 = "tree";
        System.out.println("Iput: " + makeItBold(str06) + ", Sort chars by frequnency :" +makeItBold(sortCharacterByFrequency(str06))+" Expected: eert");
        str06 = "Aabb";
        System.out.println("Iput: " + makeItBold(str06) + ", Sort chars by frequnency :" +makeItBold(sortCharacterByFrequency(str06))+" Expected: bbAa");

        System.out.println("==========================");
        System.out.println("P09. Maximum Distinct Elements after removing K elements");
        System.out.println("==========================");


        int[] numsP09 = new int[] { 7, 3, 5, 8, 5, 3, 3 };


        int resultP09 = findMaximumDistinctElements(numsP09, 2);
        System.out.print(Arrays.toString(numsP09) + ", K=2 => ");
        System.out.println("Maximum distinct numbers after removing K elements: " + resultP09 + " Expected resultP09 is 3");
        numsP09 = new int[] { 3, 5, 12, 11, 12 };
        resultP09 = findMaximumDistinctElements(numsP09, 3);
        System.out.print(Arrays.toString(numsP09) + ", K=3 => ");
        System.out.println("Maximum distinct numbers after removing K elements: " + resultP09 + " Expected resultP09 is 2");
        numsP09 = new int[] { 1, 2, 3, 3, 3, 3, 4, 4, 5, 5, 5 };
        resultP09 = findMaximumDistinctElements(numsP09, 2);
        System.out.print(Arrays.toString(numsP09) + ", K=2 => ");
        System.out.println("Maximum distinct numbers after removing K elements: " + resultP09 + " Expected resultP09 is 3");
        numsP09 = new int[] { 1, 2, 3, 4, 5 };
        resultP09 = findMaximumDistinctElements(numsP09, 3);
        System.out.print(Arrays.toString(numsP09) + ", K=3 => ");
        System.out.println("Maximum distinct numbers after removing K elements: " + resultP09 + " Expected resultP09 is 2");

        System.out.println("==========================");
        System.out.println("P04. Connect ropes");
        System.out.println("==========================");
        // give me some exapmles with expected output
        int[] ropeLengths = {1, 3, 11, 5};
        int resultP04 = minimumCostToConnectRopes(ropeLengths);
        System.out.println("ropeLengths: "+ makeItBold(Arrays.toString(ropeLengths))
                + ", Minimum cost to connect ropes: " + makeItBold(String.valueOf(resultP04)) + " (Expected: 33)");
        // more examples
        int[] ropeLengths2 = {3, 4, 5, 6};
        resultP04 = minimumCostToConnectRopes(ropeLengths2);
        System.out.println("ropeLengths: "+ makeItBold(Arrays.toString(ropeLengths2))
                + ", Minimum cost to connect ropes: " + makeItBold(String.valueOf(resultP04)) + " (Expected: 36)");

        // give me 2 more examples
        int[] ropeLengths3 = {1, 3, 11, 5, 2};
        resultP04 = minimumCostToConnectRopes(ropeLengths3);
        System.out.println("ropeLengths: "+ makeItBold(Arrays.toString(ropeLengths3))
                + ", Minimum cost to connect ropes: " + makeItBold(String.valueOf(resultP04)) + " (Expected: 42)");
        int[] ropeLengths4 = {5, 2, 1, 4, 3};
        resultP04 = minimumCostToConnectRopes(ropeLengths4);
        System.out.println("ropeLengths: "+ makeItBold(Arrays.toString(ropeLengths4))
                + ", Minimum cost to connect ropes: " + makeItBold(String.valueOf(resultP04)) + " (Expected: 33)");

        System.out.println("================================================");
        System.out.println("P08. K closest elment ");
        System.out.println("================================================");
        int[] numsP08 = { 5, 6, 7, 8, 9 };
        int kP08 = 3, xP08 = 7;  List<Integer> resultP08 = findClosestElements(numsP08, kP08, xP08);
        System.out.println("Input: " + Arrays.toString(numsP08) + ", K = " + kP08 + ", X = " + xP08 + " ,Output: " + makeItBold(resultP08.toString()) +", Expected Output: [6, 7, 8]");

        numsP08 = new int[] { 2, 4, 5, 6, 9 };
        kP08 = 3;
        xP08 = 6;
        resultP08 = findClosestElements(numsP08, kP08, xP08);
        System.out.println("Input: " + Arrays.toString(numsP08) + ", K = " + kP08 + ", X = " + xP08 +", Output: " + makeItBold(resultP08.toString()) + ", Expected Output: [4, 5, 6]");

        numsP08 =  new int[]{ 2, 4, 5, 6, 9 };
        kP08 = 3;
        xP08 = 10;
        resultP08 = findClosestElements(numsP08, kP08, xP08);
        System.out.println("Input: " + Arrays.toString(numsP08) + ", K = " + kP08 + ", X = " + xP08 +", Output: " + makeItBold(resultP08.toString()) + ", Expected Output: [5, 6, 9]");
        numsP08 = new int[] { 1, 3, 5, 7, 9 };
        kP08 = 2;
        xP08 = 4;
        resultP08 = findClosestElements(numsP08, kP08, xP08);
        System.out.println("Input: " + Arrays.toString(numsP08) + ", K = " + kP08 + ", X = " + xP08 +", Output: " + makeItBold(resultP08.toString()) + ", Expected Output: [3, 5]");


        System.out.println("===============================================================");
        System.out.println("P07 Kth Largest Number in a Stream (medium)");
        System.out.println("===============================================================");

        int[] numsP07 = {3, 1, 5, 12, 2, 11};
        int k = 4;

        KthLargestHelper streamP07 = new KthLargestHelper(numsP07, k);

        System.out.println("Input Array: "+ Arrays.toString(numsP07) +  " K:"  +  k +  " ,current Heap" +
                streamP07.minHeap.toString());

        System.out.println(" streamP07.add(6): " + makeItBold(streamP07.add(6) + "")  +" ,expected: 5" );
        System.out.println(" streamP07.add(13): " + makeItBold(streamP07.add(13) + "") +" ,expected: 6");
        System.out.println(" streamP07.add(4): " + makeItBold(streamP07.add(4) + "")  +" ,expected: 6"   );



    }
}

