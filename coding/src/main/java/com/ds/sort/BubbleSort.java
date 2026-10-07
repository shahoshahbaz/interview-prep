package com.ds.sort;

import java.util.Arrays;

/**
 * How Bubble Sort Works:
 * Pass through the array: Start from the beginning of the array and compare the first two elements.
 * If the first element is greater than the second, swap them.
 * If the first element is smaller, move to the next pair and repeat the comparison.
 * Bubble up the largest element: By the end of the first pass, the largest element in the array has "bubbled up" to its correct position at the end.
 * Repeat the process: After each pass, you can ignore the last element, as it's already in place. Continue with the remaining unsorted part of the array.
 * Stop when no more swaps are needed: If no elements were swapped in a full pass, it means the array is fully sorted, and the algorithm can stop.
 *
 * Example:
 * Array: [5, 1, 4, 2, 8]
 * Non-Optimized Bubble Sort
 * Passes and Comparisons:
 *
 * 1st Pass (i = 4): 4 comparisons
 * 2nd Pass (i = 3): 3 comparisons
 * 3rd Pass (i = 2): 2 comparisons
 * 4th Pass (i = 1): 1 comparison
 * Total Comparisons (Non-Optimized):
 * 10 comparisons
 *
 * Optimized Bubble Sort
 * Passes and Comparisons:
 *
 * 1st Pass (i = 4): 4 comparisons
 * 2nd Pass (i = 3): 3 comparisons
 * 3rd Pass (i = 2): 2 comparisons (Early exit after this pass)
 */
public class BubbleSort {
    public static void bubbleSort(int[] array){

        for (int i = array.length-1; i>0;i--){
            for (int j=0; j < i; j++){
               if (array[j] > array[j +1 ]){
                   swap(array, j, j+1);

               }
            }
        }


    }

    public static void bubbleSortOptimized(int[] array){

        for (int i = array.length-1; i>0;i--){
            boolean swapped= false;
            for (int j=0; j < i; j++){
                if (array[j] > array[j +1 ]){
                    swap(array, j, j+1);
                    swapped = true;

                }
            }
            /**
             * You could add an early exit condition (as mentioned in the previous response) to improve performance.
             * If no swaps are made during a pass, the array is already sorted, and there's no need to continue
             */
            if (!swapped){
                break; // see the example
            }
        }


    }


    public static void swap(int[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {

        int[] myArray = {4, 2, 6, 5, 1, 3};
        System.out.println("Original array: " +Arrays.toString(myArray));
        bubbleSort(myArray);

        System.out.println("Bubble Sort: " + Arrays.toString(myArray));
    }
    }
