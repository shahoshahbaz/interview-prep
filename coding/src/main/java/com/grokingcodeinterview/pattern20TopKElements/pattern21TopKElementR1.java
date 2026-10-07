package com.grokingcodeinterview.pattern20TopKElements;

import java.util.*;

import static com.Utility.makeItBold;

public class pattern21TopKElementR1 {
/*
Problem Statement
Given an array, find the sum of all numbers between the K1â€™th and K2â€™th smallest elements of that array.

Example 1:

Input: [1, 3, 12, 5, 15, 11], and K1=3, K2=6
Output: 23
Explanation: The 3rd smallest number is 5 and 6th smallest number 15. The sum of numbers coming
between 5 and 15 is 23 (11+12).
Example 2:

Input: [3, 5, 8, 7], and K1=1, K2=4
Output: 12
Explanation: The sum of the numbers between the 1st smallest number (3) and the 4th smallest
number (8) is 12 (5+7).

 */

    /**
     *  [1, 3, 12, 5, 15, 11], and K1=3, K2=6
     *
     *  minHeap(1,3, 5, 11,12, 15)   3rd and 6th
     *
     */

    public static int findSumOfElements(int[] nums, int k1, int k2){




        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num: nums){
            minHeap.offer(num);

        }

        for (int i =0; i<k1; i++){
            minHeap.poll();

        }
        int sum =0;

        for (int i =0; i<(k2 -k1-1); i++){
            sum += minHeap.poll();
        }


        return sum;

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

    /**
     * Input: nums = [7, 3, 5, 8, 5, 3, 3], K=2 Expected Output: 3
     * freqMap(7,1)(3, 3), (5, 2) (8, 1)
     * maxHeap  (3, 3) (5, 2)(7, 1) (8,1)
     * while (k> 0){
     *     maxHeap.poll
     * }
     *
     */
    public static  int findMaximumDistinctElements(int[] nums, int k){

        HashMap<Integer,Integer> freqMap = new HashMap<>();

        for (int num: nums){
            freqMap.put(num, freqMap.getOrDefault(num, 0)+1);

        }
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap =
                new PriorityQueue<>((a,b) ->b.getValue() - a.getValue());

        for (Map.Entry<Integer, Integer> entry: freqMap.entrySet()){
            maxHeap.offer(entry);
        }

        while (!maxHeap.isEmpty() && k> 0){
            Map.Entry<Integer,Integer> entry = maxHeap.poll();
            k = k - entry.getValue();
        }
        return maxHeap.size();

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
 public static List<Integer> findClosestElements(int[] nums, int k, int x){

     PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) ->Math.abs(x-b) - Math.abs(x-a));



     for (int num:nums){
         maxHeap.offer(num);
         if (maxHeap.size()> k){
             maxHeap.poll();
         }
     }

     List<Integer> list = new ArrayList<>();
     while (!maxHeap.isEmpty()){
         list.add(maxHeap.poll());
     }
     Collections.sort(list);
     return list;



}
    /*
Kth Largest Number in a Stream (medium)
Problem Statement
Design a class to efficiently find the Kth largest element in a stream of numbers.

The class should have the following two things:

The constructor of the class should accept
 an integer array containing initial numbers from the stream and an integer â€˜Kâ€™.
The class should expose a function add(int num) which will store the given number
 and return the Kth largest number.
Example 1:

Input: [3, 1, 5, 12, 2, 11], K = 4
1. Calling add(6) should return '5'.
2. Calling add(13) should return '6'.
2. Calling add(4) should still return '6'.
Constraints:

1 <= k <= 104
0 <= nums.length <= 104
-104 <= nums[i] <= 104
-104 <= val <= 104
At most 104 calls will be made to add.
It is guaranteed that there will be at least k elements in the array when you search for the kth element.
 */

    /**
     * the Kth largest element
     *  [3, 1, 5, 12, 2, 11], K = 4
     *  maxHeap ( 3, 5,12, 11)
     *  add(6) (5, 6, 12, 11
     *
     *
     *
     *
     *
     */    public static class KthLargestHelper{

        PriorityQueue<Integer> minHeap;
        int k;

        public KthLargestHelper(int[] nums, int k ){
            minHeap = new PriorityQueue<>();
            this.k = k;
            for (int num: nums){
                minHeap.offer(num);
                if (minHeap.size()>k){
                    minHeap.poll();
                }
            }


        }

        public int add(int num){

            if (minHeap!= null){
                minHeap.add(num);
                if (minHeap.size() > this.k){
                    minHeap.poll();
                }

            }

            return minHeap.peek();

        }

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
public  static String sortCharacterByFrequency(String str) {

    HashMap<Character, Integer> freqMap = new HashMap<>();

    for (char ch: str.toCharArray()){
        freqMap.put(ch, freqMap.getOrDefault(ch,0) +1);
    }

    PriorityQueue<Map.Entry<Character, Integer>> maxHeap =
            new PriorityQueue<>((a,b)-> b.getValue() - a.getValue());

    for (Map.Entry<Character, Integer> entry: freqMap.entrySet()){
        maxHeap.offer(entry);

    }
    StringBuilder sb = new StringBuilder();
    while (!maxHeap.isEmpty()){
        Map.Entry<Character, Integer> entry = maxHeap.poll();
        char ch = entry.getKey();
        int freq = entry.getValue();

        for (int i =1; i<= freq; i++){
            sb.append(ch);
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
     * Example 1:  Input: [1, 3, 5, 12, 11, 12, 11], K = 2  Output: [12, 11]
     *  minHeap <Map.entry>
     *  freqMap[ (1, 1) (3, 10, (5,1), (12, 2), (11, 1)]
     */

    public static List<Integer> findTopKFrequentNumbers(int[] nums, int k){

        List<Integer> result = new ArrayList<>();

        HashMap<Integer, Integer> freqMap = new HashMap<>();

        for (int num: nums){
            freqMap.put(num, freqMap.getOrDefault(num, 0) +1);
        }

        PriorityQueue<Map.Entry<Integer,Integer>> minHeap = new PriorityQueue<>((a, b)->a.getValue()- b.getValue());

        for (Map.Entry<Integer, Integer> entry: freqMap.entrySet()){

            minHeap.offer(entry);

            if (minHeap.size()>k){
                minHeap.poll();
            }

        }

        while(!minHeap.isEmpty()){
            Map.Entry<Integer,Integer> entry = minHeap.poll();
            result.add(entry.getKey());

        }
        return result;



    }

/*
Problem Statement
Given â€˜Nâ€™ ropes with different lengths,
 we need to connect these ropes into one big rope with minimum cost.
  The cost of connecting two ropes is equal to the sum of their lengths.


Example 1:

Input: [1, 3, 11, 5]
Output: 33
Explanation: First connect 1+3(=4), then 4+5(=9), and then 9+11(=20). So the total cost is 33 (4+9+20)
Example 2:

Input: [3, 4, 5, 6]
Output: 36
Explanation: First connect 3+4(=7), then 5+6(=11), 7+11(=18). Total cost is 36 (7+11+18)
Example 3:

Input: [1, 3, 11, 5, 2]
Output: 42
Explanation: First connect 1+2(=3), then 3+3(=6), 6+5(=11), 11+11(=22). Total cost is 42 (3+6+11+22)
Constraints:

1 <= ropLengths.length <= 104
1 <= ropLengths[i] <= 104
 */

    /**
     * minimum cost to connect ropes
     * Input: [1, 3, 11, 5, 2]      * Output: 42
     *
     *
     * maxheap = [1, 2, 3, 5, 11]
     * 1+2 = 3 cost = 3
     * then offer(3)
     * maxheap = [3, 3, 5, 11]
     * cos = 6+3 =9
     * lenght = 6;
     * maxHeap = [5, 6,11]
     * length = 11
     * cost = 11 +9 = 20
     * maxHeap = [11, 11]
     * cost = 20 + 22= 43
     *
     * ....
     * Input: [1, 3, 11, 5, 2]      * Output: 42
     * maxHeap( 1, 2, 3, 5, 11]
     * newlenght = 3, cost = 3
     * minHeap (3, 3, 5,11)
     * newLenath = 6, cost =
     *
     *
     *
     */

    public  static int minimumCostToConnectRopes(int[] ropeLengths){

        PriorityQueue<Integer> minHeap = new PriorityQueue<> ((a, b) -> Integer.compare(a, b));

        int totalCost =0;
        for (int length: ropeLengths){
            minHeap.offer(length);
        }

        while(!minHeap.isEmpty() && minHeap.size()> 1){
            int newLength = minHeap.poll() + minHeap.poll();
            totalCost += newLength;
            minHeap.offer(newLength);
        }

        return totalCost;
    }
/*
Problem Statement
Given an array of points in a 2D plane,
 find â€˜Kâ€™ closest points to the origin.

Example 1:

Input: points = [[1,2],[1,3]], K = 1
Output: [[1,2]]
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
    public static double getEuclideanDistance(Point point){
        return Math.sqrt(Math.pow(point.x, 2.0) +Math.pow(point.y, 2.0));
    }
    public static List<Point> findClosestPoints(Point[] points, int k){


        PriorityQueue<Point> maxHeap = new PriorityQueue<>((a, b) -> (int) (getEuclideanDistance(b) - getEuclideanDistance(a)));

        for (Point p: points){
            maxHeap.add(p);
            if (maxHeap.size() > k){
                maxHeap.poll();
            }

        }

        return new ArrayList<Point>(maxHeap);
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
    Given an array of points in a 2D plane, find â€˜Kâ€™ closest points to the origin.

            Example 1:

    Input: points = [[1,2],[1,3]], K = 1
    Output: [[1,2]]
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
     *  Input: [1, 5, 12, 2, 11, 5], K = 4     *  Output: 5
     *  maxHeap = [12, 5, 1, 2]
     *  i = 4, 11 < 12 then  maxHeap = [11, 5, 1, 2]
     *  i = 5, maxHeap [5,...
     * @param nums
     * @param k
     * @return
     */
    public static int findKthSmallestNumber(int[] nums, int k){

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b)-> b-a);
        for (int i = 0; i<k; i++ ){
            maxHeap.add(nums[i]);
        }

        for (int i = k; i< nums.length; i++){
            if (!maxHeap.isEmpty() && nums[i]< maxHeap.peek()){
                maxHeap.poll();
                maxHeap.add(nums[i]);
            }
        }

        return maxHeap.peek();

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

    /**
     * fin k largest elemnt
     *  [3, 1, 5, 12, 2, 11], K = 3 [5, 12, 11]
     *  min Heap: [1, 3, 5]
     *  now i = 3 if bigger than top , remove top and insert
     *  minHeap = [3, 5,12]
     *  i = 4,
     *  i = 5 minHeap = [5, 11, 12]
     * @param nums
     * @param k
     * @return
     */
    public static List<Integer> findKLargestNumbers(int[] nums, int k){

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int i =0; i< k; i++){
            minHeap.add(nums[i]);
        }
        for (int i =k; i< nums.length;i++){
            if (  !minHeap.isEmpty() &&nums[i]> minHeap.peek()){
                minHeap.poll();
                minHeap.add(nums[i]);
            }
        }

        return new ArrayList<>(minHeap);

    }

    public static void main(String[] args) {

        System.out.println("++++++++++++++++++++++++++++");
        System.out.println("P01. Top K largest number");
        // some test cases
        List<Integer> result = findKLargestNumbers(new int[] { 3, 1, 5, 12, 2, 11 }, 3);
        System.out.println("Here are the K largest numbers: " + result); // expected [5, 12, 11]
        result = findKLargestNumbers(new int[] { 5, 12, 11, -1, 12 }, 3);
        System.out.println("Here are the K largest numbers: " + result); // expected [12, 11, 12]
        // more test cases
        result = findKLargestNumbers(new int[] { 1, 2, 3, 4, 5 }, 2); // expected [4, 5]
        System.out.println("Here are the K largest numbers: " + result);
        result = findKLargestNumbers(new int[] { 10, 9, 8, 7, 6 }, 4); // expected [7, 8, 9, 10]
        System.out.println("Here are the K largest numbers: " + result);
        System.out.println("++++++++++++++++++++++++++++");
        System.out.println("P02. Kth Smallest Number");

        // add some test cases
        int resultp02 = findKthSmallestNumber(new int[] { 1, 5, 12, 2, 11, 5 }, 3); // expect 5
        System.out.println("Kth smallest number is: " + resultp02);
        resultp02 = findKthSmallestNumber(new int[] { 1, 5, 12, 2, 11, 5 }, 4); // expect 5
        System.out.println("Kth smallest number is: " + resultp02);
        resultp02 = findKthSmallestNumber(new int[] { 5, 12, 11, -1, 12 }, 3); // expect 11
        System.out.println("Kth smallest number is: " + resultp02);

        System.out.println("P03 K closest Points to the origin");


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

        // give me some exapmles with expected output
        int[] ropeLengths = {1, 3, 11, 5};
        int resultP04 = minimumCostToConnectRopes(ropeLengths);
        System.out.println("ropeLengths: "+ makeItBold(Arrays.toString(ropeLengths))
                + ", Minimum cost to connect ropes: " + makeItBold(String.valueOf(resultP04))); // expected 33
        // more examples
        int[] ropeLengths2 = {3, 4, 5, 6};
        resultP04 = minimumCostToConnectRopes(ropeLengths2);
        System.out.println("ropeLengths: "+ makeItBold(Arrays.toString(ropeLengths2))
                + ", Minimum cost to connect ropes: " + makeItBold(String.valueOf(resultP04))); // expected 36


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



        // add 4 examples with expected output
        System.out.println(sortCharacterByFrequency("Programming")); // Expected: "rrggmmPiano"
        System.out.println(sortCharacterByFrequency("abcbab")); // Expected: "bbbaac"
        System.out.println(sortCharacterByFrequency("tree")); // Expected: "eert"
        System.out.println(sortCharacterByFrequency("Aabb")); // Expected: "bbAa"


        System.out.println("===============================================================");
        System.out.println("P07 Kth Largest Number in a Stream (medium)");
        System.out.println("===============================================================");

        int[] nums = {3, 1, 5, 12, 2, 11};
        int k = 4;

        KthLargestHelper stream = new KthLargestHelper(nums, k);

        System.out.println("Input Array: "+ makeItBold(Arrays.toString(nums)) +" K:"  +makeItBold(""+ k) +  " ,current Heap" +
                makeItBold(stream.minHeap.toString()));

        System.out.println(" stream.add(6): " + makeItBold(stream.add(6) + "")  +" ,expected: "+  makeItBold(""+5) );
        System.out.println(" stream.add(13): " + makeItBold(stream.add(13) + "") +" ,expected: "+  makeItBold(""+6) );
        System.out.println(" stream.add(4): " + makeItBold(stream.add(4) + "")  +" ,expected: "+  makeItBold(""+6) );


        // Example 1
        int[] numsP08 = { 5, 6, 7, 8, 9 };
        int kP08 = 3, xP08 = 7;
        System.out.println("Input: " + Arrays.toString(numsP08) + ", K = " + kP08 + ", X = " + xP08);
        List<Integer> resultP08 = findClosestElements(numsP08, kP08, xP08);
        System.out.println("Output: " + resultP08);
        System.out.println("Expected Output: [6, 7, 8]\n");

        // Example 2
        numsP08 = new int[] { 2, 4, 5, 6, 9 };
        kP08 = 3;
        xP08 = 6;
        System.out.println("Input: " + Arrays.toString(numsP08) + ", K = " + kP08 + ", X = " + xP08);
        resultP08 = findClosestElements(numsP08, kP08, xP08);
        System.out.println("Output: " + resultP08);
        System.out.println("Expected Output: [4, 5, 6]\n");

        // Example 3
        numsP08 =  new int[]{ 2, 4, 5, 6, 9 };
        kP08 = 3;
        xP08 = 10;
        System.out.println("Input: " + Arrays.toString(numsP08) + ", K = " + kP08 + ", X = " + xP08);
        resultP08 = findClosestElements(numsP08, kP08, xP08);
        System.out.println("Output: " + resultP08);
        System.out.println("Expected Output: [5, 6, 9]\n");

        // Example 4
        numsP08 = new int[] { 1, 3, 5, 7, 9 };
        kP08 = 2;
        xP08 = 4;
        System.out.println("Input: " + Arrays.toString(numsP08) + ", K = " + kP08 + ", X = " + xP08);
        resultP08 = findClosestElements(numsP08, kP08, xP08);
        System.out.println("Output: " + resultP08);
        System.out.println("Expected Output: [3, 5]\n");


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


        System.out.println("===================================");
        System.out.println("P10. Sum of Elements... ");
        System.out.println("===================================");
        int numsP10[] = new int[] {1, 3, 12, 5, 15, 11};
        int k1P10 =3;
        int k2P10 =6;
        System.out.println("Input: "  +makeItBold("[1, 3, 12, 5, 15, 11]") + ", k1: "
                + makeItBold("3") + ", k2: " + makeItBold("6") +", output:" + makeItBold(findSumOfElements(numsP10,k1P10,k2P10)+" ,expected: 23"));

        numsP10 = new int[] {3, 5, 8, 7};
        k1P10 =1;
        k2P10 =4;
        System.out.println("Input: "  +makeItBold("[3, 5, 8, 7]") + ", k1: "
                + makeItBold("1") + ", k2: " + makeItBold("4") +" ,output:" + makeItBold(findSumOfElements(numsP10,k1P10,k2P10)+"  ,expected: 12"));

        numsP10 = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        k1P10 =2;
        k2P10 =5;
        System.out.println("Input: "  +makeItBold("[1, 2, 3, 4, 5, 6, 7, 8, 9]") + ", k1: "
                + makeItBold("2") + ", k2: " + makeItBold("5") +" ,output:" + makeItBold(findSumOfElements(numsP10,k1P10,k2P10)+"  ,expected: 12"));

        numsP10 = new int[] {10, 20, 30, 40, 50};
        k1P10 =1;
        k2P10 =3;
        System.out.println("Input: "  +makeItBold("[10, 20, 30, 40, 50]") + ", k1: "
                + makeItBold("1") + ", k2: " + makeItBold("3") +" ,output:" + makeItBold(findSumOfElements(numsP10,k1P10,k2P10)+"  ,expected: 20"));








    }
}

