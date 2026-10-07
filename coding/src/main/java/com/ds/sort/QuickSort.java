package com.ds.sort;

import java.util.Arrays;

public class QuickSort {

 public static void quickSort(int[] arr){
     quickSort(arr, 0, arr.length);
 }
    public static void quickSort(int[] arr, int left, int right){
        if (left< right) {
            int pivotIndex = pivot(arr, left, right);
            quickSort(arr, left, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, right);
        }
    }
    private static int pivot (int[] arr, int pivotIndex, int endIndex){

        int swapIndex = pivotIndex;

        for (int i= pivotIndex + 1; i<= endIndex; i++){

            if (arr[i ]< arr[pivotIndex]){
                swapIndex ++;
                swap(arr, swapIndex, i);

            }

        }
        swap(arr, pivotIndex, swapIndex);
        return swapIndex;

    }

    public static void swap(int[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {

        int[] myArray = {4,6,1,7,3,2,5};

        System.out.println("Original array: " +  Arrays.toString( myArray ) );

        int returnedIndex = pivot(myArray, 0, 6);

        System.out.println( "\nReturned Index: " + returnedIndex);


        quickSort( myArray, 0, 6);
        System.out.println( "Quick sort: " +  Arrays.toString( myArray ) );


        /*
            EXPECTED OUTPUT:
            ----------------
            Returned Index: 3
            [2, 1, 3, 4, 6, 7, 5]

         */

    }

}
