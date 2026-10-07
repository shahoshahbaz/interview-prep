package com.ds.sort;

import java.util.Arrays;

public class InsertionSort {
    /**
     * /*
     * Example: [7, 4, 5, 2]
     * Initial array: [7, 4, 5, 2]
     *
     * First iteration (i = 1):
     * - temp = 4, j = 0
     * - Compare 4 < 7 → Shift 7
     * - Array: [7, 7, 5, 2]
     * - Insert 4 at index 0
     * - Array: [4, 7, 5, 2]
     *
     * Second iteration (i = 2):
     * - temp = 5, j = 1
     * - Compare 5 < 7 → Shift 7
     * - Array: [4, 7, 7, 2]
     * - Insert 5 at index 1
     * - Array: [4, 5, 7, 2]
     *
     * Third iteration (i = 3):
     * - temp = 2, j = 2
     * - Compare 2 < 7 → Shift 7
     * - Array: [4, 5, 7, 7]
     * - Compare 2 < 5 → Shift 5
     * - Array: [4, 5, 5, 7]
     * - Compare 2 < 4 → Shift 4
     * - Array: [4, 4, 5, 7]
     * - Insert 2 at index 0
     * - Array: [2, 4, 5, 7]
     *
     * Sorted array: [2, 4, 5, 7]
     * */


    public static void insertionSort(int[] array){
        for (int i =1; i< array.length; i++){
            int temp = array[i];
            int j = i -1;
            /*The condition j > -1 in the while loop is essential because it ensures that we do not go beyond
             the beginning of the array while comparing and shifting elements.*/
            while ( j> -1 && temp < array[j]){
                array[j + 1] = array[j];
                array[j] = temp;
                j--;
            }
        }
    }
    public static void main(String[] args) {

        int[] myArray = {4,2,6,5,1,3};

        System.out.println( Arrays.toString(myArray) );
        insertionSort(myArray);

        System.out.println( Arrays.toString(myArray) );

        /*

            EXPECTED OUTPUT:
            ----------------
            [1, 2, 3, 4, 5, 6]

         */

    }

}

