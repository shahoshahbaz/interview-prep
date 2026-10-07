package com.grokingcodeinterview.pattern18ModifiedBinarySearch;

import java.util.Arrays;

import static com.Utility.makeItBold;

/*
problem statement:
Given a Bitonic array, find index of a given â€˜keyâ€™ is present in it.
 An array is considered bitonic if it is first monotonically increasing and then monotonically decreasing.
In other words, a bitonic array starts with a sequence of increasing elements, reaches a peak element, and then follows with a sequence of decreasing elements. The peak element is the maximum value in the array.
Write a function to return the index of the â€˜keyâ€™. If the 'key' appears more than once, return the smaller index. If the â€˜keyâ€™ is not present, return -1.
Example 1: Input: [1, 3, 8, 4, 3], key=4 Output: 3
Example 2: Input: [3, 8, 3, 1], key=8 Output: 1
Example 3: Input: [1, 3, 8, 12], key=12 Output: 3
Example 4: Input: [10, 9, 8], key=10 Output: 0
 */

/**
     *  Input: [1, 2, 3, 4, 5, 12, 11, 10, 9, 8, 8, 7, 6, 1, 2] key = 8, output:
        *         [0, 1, 2, 3, 4,  5, 6,  7,  8, 9,10,11, 12,13, 14]
        *  peakIndex = 5
        *  length = 15
        *  ascending true;
        *  (0, 4) mid = 2, nums[2] = 3< 8 start = mid +1 = 3
        *  (3, 4) mid = 3, nums[3] = 4<8, start = mid +1 = 4
        *  (4, 4) mid = 4, nums[4] = 5 <8, start = mid +1 = 5 returun -1;
        *  descending
       *  (5,14) mid = 9, nums[9] = 8 result = 9 end = mid -1;
        *  (5, 8) mid = 6 nums[6] = 11> 9 end = mid -1 end = 5
        *  (5, 5) mid = 5 nums[5] = 12> 0 end = mid -1 = 4
        *  (5, 4) result = 9
        *
        *
        *
        *
        */
public class P09SearchBitonicArray {
    public  static int searchInBitonicArray(int[] arr, int key) {
        int maxIndex = findMax(arr);
//        because we need first occurrance, we don't stop
//        if (arr[maxIndex]== key){
//            return maxIndex;
//        }
         int resultIndex = binarySearch(arr, 0, maxIndex -1, key, true);
        return (resultIndex!= -1 )? resultIndex:  binarySearch(arr, maxIndex , arr.length -1, key, false);
    }

    public static int binarySearch(int[] arr, int start, int end, int key, boolean isAscending){

        int result = -1;

        while (start <= end){
            int mid = start + (end - start)/2;

            if (arr[mid] == key){
                result = mid;
                end = mid - 1;   //  // keep searching left for first occurrence
            }
            else if (isAscending){
                if (arr[mid] < key) start = mid + 1;
                else end = mid - 1;
            }
            else { // descending
                if (arr[mid] < key)  end = mid - 1;   // ðŸ”‘ FIX
                else
                    start =mid + 1;
            }
        }
        return result;
    }
    public static int findMax(int arr[]){

        int start = 0;
        int end = arr.length -1;

        while (start < end){ // why not equals? because we are comparing mid with mid +1, so if start == end, mid +1 will be out of bounds

            int mid = start + (end -start)/2;

            if (arr[mid]< arr[mid+1]){
                start = mid +1;
            }else{
                end = mid;
            }
        }
        return start;
    }

    public static void main(String[] args) {

        System.out.println("==============================================================");
        System.out.println(makeItBold("P09. Search Bitonic Array"));
        System.out.println("==============================================================");
        int[] numsP09 = {1, 3, 8, 4, 3};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=4, Output: " + makeItBold(searchInBitonicArray(numsP09, 4) +"")+ ", Expected Output: 3"); //
       numsP09 = new int[] {3, 8, 3, 1};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=8, Output: " + makeItBold(searchInBitonicArray(numsP09, 8) +"")+ ", Expected Output: 1"); //
        numsP09 = new int[]{1, 3, 8, 12};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=12, Output: " + makeItBold(searchInBitonicArray(numsP09, 12) +"")+ ", Expected Output: 3"); //
        numsP09 =  new int[]{10, 9, 8};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=10, Output: " + makeItBold(searchInBitonicArray(numsP09, 10) +"")+ ", Expected Output: 0"); //
        // an example when the ky doesn't exist
        numsP09 =  new int[]{1, 3, 8, 4, 3};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=6, Output: " + makeItBold(searchInBitonicArray(numsP09, 6) +"")+ ", Expected Output: -1"); //
        // an example when the key is duplicated
        numsP09 =  new int[]{1, 3, 8, 8, 3,1};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=8, Output: " + makeItBold(searchInBitonicArray(numsP09, 8) +"")+ ", Expected Output: 2"); //
        numsP09 =  new int[]{1, 3, 3, 8, 2,1};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=3, Output: " + makeItBold(searchInBitonicArray(numsP09, 3) +"")+ ", Expected Output: 1"); //
        numsP09 = new int[]{1, 2, 3, 4, 5, 12, 11, 10, 9, 8, 8, 7, 6, 1, 2};
        System.out.println("Input: " + Arrays.toString(numsP09) + ", key=3, Output: " + makeItBold(searchInBitonicArray(numsP09, 8) +"")+ ", Expected Output: 9"); //


    }
}

