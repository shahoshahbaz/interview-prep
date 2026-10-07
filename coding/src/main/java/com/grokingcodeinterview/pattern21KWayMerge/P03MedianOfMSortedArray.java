package com.grokingcodeinterview.pattern21KWayMerge;

import java.util.Arrays;
import java.util.PriorityQueue;

import static com.Utility.makeItBold;

/*
problem statment
You are given M sorted arrays of integers.
 Each array may have a different length, and the total number of elements across all arrays may be either even or odd.
Your task is to find the median value of all the numbers present in these M arrays when they are conceptually combined into a single sorted sequence.

Definition of Median

If the total number of elements is odd, the median is the middle element in the sorted order.
If the total number of elements is even, the median is the average of the two middle elements.

Your task is to find the median value of all the numbers present in these M arrays

when they are conceptually combined into a single sorted sequence.
Input: [[1, 3], [2]] Output: 2
Input: [[1, 2], [3, 4]]  Output: 2.5
Input: [[1, 5, 9], [2, 3], [6, 7, 8, 10]] Output: 6
Input:[[1, 2, 2], [2, 2, 3]] Output: 2
Input:[[-5, -3, -1], [2, 4, 6]] Output: 0.5
Input: [[7, 9, 11]] Output: 9
Input: [[], [1, 2, 3]] Output: 2

 */

/**
 * dry-run example for the second approach (Binary Search on Value Range):
 * Input: [1, 5, 9], [2, 3], [6, 7, 8, 10]]
 * 1. Find global min and max: min = 1, max = 10, totalCount = 10 -> even -> need to find 5th and 6th smallest
 */

public class P03MedianOfMSortedArray {
    // NOTE: this problem can be solved with 2 appraoch , try all for approaches

    /*

 | Approach                          | Time Complexity                 | Space Complexity | Notes                                                                 |
|--------------------------------|-------------------------|----------|----------------------------------------------------------------------|
| Min-Heap Kâ€™th Smallest         | O(K Ã— log M)            | O(M)     | K = N/2 for median, M = number of arrays; good for small to medium arrays | // why O(K Ã— log M)? because we are pushing K elements into the heap, each push/pop operation takes O(log M) time
| Binary Search on Value Range   | O(log(R) Ã— M Ã— log L)   | O(1)     | R = value range (max - min), L = max length of an array; optimal, no extra space |

  */
    public static class Entry {
        int value;
        int elementIndex;
        int arrayIndex;
        public Entry(int value, int elementIndex, int arrayIndex){
            this.value = value;
            this.elementIndex= elementIndex;
            this.arrayIndex = arrayIndex;
        }
    }
    public static double findMedianOfMSortedArray(int[][] nums){

        PriorityQueue<Entry> minHeap = new PriorityQueue<>( (a,b)-> a.value -b.value);

        int totalCount =0;

        for(int i =0; i< nums.length; i++ ){
            if (nums[i].length >0){
                minHeap.offer(new Entry(nums[i][0], 0, i));
                totalCount += nums[i].length;
            }
        }
        int firstMedianIndex = (totalCount -1)/2;
        int secondMedianIndex = totalCount /2;

        int count = 0;
        int firstMedian = 0;
        int secondMedian = 0;

        while(!minHeap.isEmpty()){
            Entry curr = minHeap.poll();
            if(count == firstMedianIndex) firstMedian = curr.value;
            if (count == secondMedianIndex) {
                secondMedian = curr.value;
                break;
            }
            count++;

            int nextIndex = curr.elementIndex +1;
            if (nextIndex< nums[curr.arrayIndex].length){
                minHeap.offer(
                        new Entry(nums[curr.arrayIndex][nextIndex], nextIndex, curr.arrayIndex)
                );
            }

        }
        return (totalCount %2  == 0) ? (firstMedian + secondMedian)/2.0: secondMedian;

    }

    /**
     * this method finds the median of M sorted arrays using binary search on value range.
     * Steps:
     * 1. Find the global minimum and maximum values across all arrays to define the search range.
     * 2. Calculate the total number of elements to determine if the median is a single middle element (odd count) or the average of two middle elements (even count).
     * 3. Use the kthSmallestElement method to find the required median elements based on the total count.
     * 4. Return the median value.
     */
    public static double findMedianOfMSortedArray_BinarySearch(int[][] nums){
        // find golab min and max accross all array
        int totalCount =0;
        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;

        for(int[] num: nums){
            if (num.length > 0) {
                minValue = Math.min(minValue, num[0]);
                maxValue = Math.max(maxValue, num[num.length - 1]);
                totalCount += num.length;

            }
        }

        if (totalCount %2 ==1) {
            // odd -> first kth element
            return kthSmallestElement(nums, (totalCount + 1) / 2, minValue, maxValue);
        }else{
            // even -> find two middle elements
            double firstMedian = kthSmallestElement (nums, totalCount/2, minValue, maxValue);
            double secondMedian = kthSmallestElement(nums, totalCount/2 +1, minValue, maxValue);
            return (firstMedian + secondMedian)/2.0;
        }


    }

    // count number of elements<= target, return the index of first elements>= target

    /**
     *  this method finds the ceiling index for a target in a sorted array.
     *  The ceiling is defined as the index of the first element in the array that is greater than the target.
     *
     *  run-dry example:
     *  nums = [1, 3, 5, 7, 9], target = 5 *  output: 3 (index of first element greater than 5, which is 7) is this ceiling? yes
     *  index:  0  1  2  3  4
     *  values: 1, 3, 5, 7, 9
     *  i= 0 ...4
     *  i =0 -> start = 0, end = 5 (0, 5) mid = 2 -> nums[2] = 5 <= 5  yes, -> start = mid +1 =3
     *  i =1 -> start = 3, end = 5 (3,5) mid = 4 -> nums[4] = 9 <= 5 no -> end = mid =4
     *  i =2 -> start = 3, end = 4 (3,4) mid = 3 -> nums[3] = 7 <= 5 no -> end = mid =3
     *  i =3 -> start = 3, end = 3 -> exit loop
     *  return start = 3 (ceiling index) nums[3] = 7 is the first element greater than 5
     */
    public static int getCeilingIndex(int[] nums, int target ){
        int start = 0;
        int end = nums.length ;

        while (start< end){
            int mid = start +(end -start)/2;

            if (nums[mid]<= target){
                start = mid+1;
            }else{
                end = mid;
            }
        }

        return start;
    }

/**
     finds the kth Smallest Number across all sorted array without merging them
     kthSmallestElement finds the number that would appear in position k if you combined all the arrays and sorted them â€” without actually merging or sorting them.
    Or even simpler:
    It answers: â€œWhat is the k-th smallest value overall?â€

 how we calculate this?
    1. We perform a binary search on the range of possible values (from the global minimum to the global maximum across all arrays).
        We start with low as the minimum value and high as the maximum value found across all arrays

        NOTE: low and high are value range not indices
        NOTE: (low, high)-> mid -> count of elements <= mid -> adjust low or high based on the count compared to k

    2. For each mid-value in this range, we count how many numbers across all arrays are less than or equal to mid using the ceilingIndex function.
        ceilingIndex method returns the index of the first element greater than mid, which effectively gives us the count of elements less than or equal to mid in that array.

        NOTE: so ceilingIndex of target = number of elements <= target

    3. If this count is less than k, it means the k-th smallest number must be larger than mid, so we adjust our search range to [mid + 1, high].

        NOTE: count < k -> low = mid +1  //meaning k-th smallest is larger than mid so we move low up to mid +1

    4. If the count is greater than or equal to k, it means the k-th smallest number is less than or equal to mid, so we adjust our search range to [low, mid].

        NOTE: count >= k -> high = mid  //meaning k-th smallest is less than or equal to mid so we move high down to mid

    5. We continue this process until low meets high, at which point low will be positioned at the k-th smallest number.

    NOTE: return low or high when low == high



    run-dry example:
    Input: nums = [[1, 5, 9], [2, 3], [6, 7, 8, 10]], k = 6, low = 1, high = 10 expected output: 6

    low = 1, high = 10  (1, 10 )-> mid = 5, count = 0 --> loop through each array to get count of elements <= mid(5)
       getCeilingIndex([1,5,9], 5) = 2 (elements <=5 are 1,5)
       getCeilingIndex([2,3], 5) = 2 (elements <=5 are 2,3)
       getCeilingIndex([6,7,8,10], 5) = 0 (elements <=5 are none)
       count = 2 + 2 + 0 = 4 < k(6) -> low = mid +1 = 6
    low = 6, high = 10 (6,10) -> mid = 8, count =0 --> loop through each array to get count of elements <= mid(8)
       getCeilingIndex([1,5,9], 8) = 2 (elements <=8 are 1,5)
       getCeilingIndex([2,3], 8) = 2 (elements <=8 are 2,3)
       getCeilingIndex([6,7,8,10], 8) = 3 (elements <=8 are 6,7,8)
       count = 2 + 2 + 3 = 7 >= k(6) -> high = mid = 8

    low = 6, high = 8 (6,8) -> mid = 7, count =0 --> loop through each array to get count of elements <= mid(7)
         getCeilingIndex([1,5,9], 7) = 2 (elements <=7 are 1,5)
         getCeilingIndex([2,3], 7) = 2 (elements <=7 are 2,3)
         getCeilingIndex([6,7,8,10], 7) = 2 (elements <=7 are 6,7)
         count = 2 + 2 + 2 = 6 >= k(6) -> high = mid = 7
    low = 6, high = 7 (6,7) -> mid = 6, count =0 --> loop through each array to get count of elements <= mid(6)
            getCeilingIndex([1,5,9], 6) = 2 (elements <=6 are 1,5)
            getCeilingIndex([2,3], 6) = 2 (elements <=6 are 2,3)
            getCeilingIndex([6,7,8,10], 6) = 1 (elements <=6 are 6)
            count = 2 + 2 + 1 = 5 < k(6) -> low = mid +1 = 7
    low = 7, high = 7 -> exit loop return low = 7

*/

    public static int kthSmallestElement(int[][] nums, int k, int low, int high){
       // low and high are range not indices
        while (low< high){
            int mid = low + (high -low)/2; // mid is value not index
            int count =0;
            for (int[] num: nums){
                count += getCeilingIndex(num, mid);
            }

            if (count<k){
                low = mid+1;
            }else{
                high = mid;
            }
        }

        return low;
    }
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("P03. Median of M Sorted Arrays");
        System.out.println("==============================================");
        int[][] numsP03 = new int[][]{{1, 3}, {2}};
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + " ,output: " + makeItBold(findMedianOfMSortedArray(numsP03) +"") +", Expected: 2.0");
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + " ,output: " + makeItBold(findMedianOfMSortedArray_BinarySearch(numsP03) +"") +", Expected: 2.0 <- using `Binary search on value");
        numsP03 = new int[][]{ {1, 2},{3, 4}};
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + " ,output: " + makeItBold(findMedianOfMSortedArray(numsP03) +"") +", Expected: 2.5");
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + " ,output: " + makeItBold(findMedianOfMSortedArray_BinarySearch(numsP03) +"") +", Expected: 2.5 <- using `Binary search on value");
        numsP03 = new int[][]{{1, 5, 9},{2, 3}, {6, 7, 8, 10} };
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + " ,output: " + makeItBold(findMedianOfMSortedArray(numsP03) +"") +", Expected: 6.0");
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + " ,output: " + makeItBold(findMedianOfMSortedArray_BinarySearch(numsP03) +"") +", Expected: 6.0 <- using `Binary search on value");
        numsP03 = new int[][]{{1, 2, 2},{2, 2, 3}};
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + " ,output: " + makeItBold(findMedianOfMSortedArray(numsP03) +"") +", Expected: 2.0");
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + " ,output: " + makeItBold(findMedianOfMSortedArray_BinarySearch(numsP03) +"") +", Expected: 2.0 <- using `Binary search on value");
        numsP03 = new int[][]{{-5, -3, -1},{2, 4, 6}};
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + " ,output: " + makeItBold(findMedianOfMSortedArray(numsP03) +"") +", Expected: 0.5");
        System.out.println("Input:  " + Arrays.deepToString(numsP03) + " ,output: " + makeItBold(findMedianOfMSortedArray_BinarySearch(numsP03) +"") +", Expected: 0.5 <- using `Binary search on value");

    }
}

