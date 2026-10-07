package com.ds.sort;

import java.util.Arrays;

public class SelectionSort {
    /**
     * Example: [5, 3, 8, 4, 2]
     * We will walk through how selection sort works, focusing on the if (i != minIndex) part.
     *
     * First iteration (i = 0):
     *
     * Initial array: [5, 3, 8, 4, 2]
     * Start with i = 0 (pointing to 5). So, minIndex = 0.
     * Check the elements after index 0:
     * Now, minIndex = 4 (pointing to 2), so swap 5 and 2:
     * After swap: [2, 3, 8, 4, 5]
     * Since i (0) != minIndex (4), the swap occurred.
     *
     * Second iteration (i = 1):
     * Current array: [2, 3, 8, 4, 5]
     * Start with i = 1 (pointing to 3). So, minIndex = 1.
     * Since minIndex = 1, no swap is needed because 3 is already the smallest element in the unsorted portion.
     * The if (i != minIndex) condition prevents an unnecessary swap.

     * Third iteration (i = 2):
     * Current array: [2, 3, 8, 4, 5]
     * Start with i = 2 (pointing to 8). So, minIndex = 2.
     * Check the elements after index 2:
     * Now, minIndex = 3 (pointing to 4), so swap 8 and 4:
     * After swap: [2, 3, 4, 8, 5]
     * Since i (2) != minIndex (3), the swap occurred.

     * Fourth iteration (i = 3):
     * Current array: [2, 3, 4, 8, 5]
     * Start with i = 3 (pointing to 8). So, minIndex = 3.
     * Check the elements after index 3:
     * Now, minIndex = 4 (pointing to 5), so swap 8 and 5:
     * After swap: [2, 3, 4, 5, 8]
     * Since i (3) != minIndex (4), the swap occurred.

     * Fifth iteration (i = 4):
     *
     * Current array: [2, 3, 4, 5, 8]
     * Start with i = 4 (pointing to 8). Since this is the last element, there's no need to check further.
     * minIndex = 4 (already in the correct place), so no swap is needed.
     * The if (i != minIndex) prevents a redundant swap.
     * Why the if condition matters:
     *
     * Efficiency: In the second and fifth iterations, the minIndex was equal to i, meaning the current element was
     * already the smallest one. Without the if (i != minIndex) check, the algorithm would still swap the element with
     * itself, which is unnecessary and slightly inefficient. By using the if statement, we avoid this redundancy.
     * Without if (i != minIndex):
     * If the if check were removed, the code would still work, but in cases like the second and fifth iterations
     * (where no swap was needed), the algorithm would perform unnecessary swaps like swapping 3 with itself and 8
     * with itself, which wastes processing time.
     * @param array
     */
    public static void selectionSort(int[] array){

       boolean swapped;
        for(int i =0 ; i< array.length; i++){
            swapped = false;
            int minIndex = i;
            for (int j = i +1; j< array.length; j++){
                if (array[j]< array[minIndex]){
                    minIndex = j;

                }

            }
            /**
             * The if statement ensures that a swap only happens when it's necessary.
               If the current element (at index i) is already the smallest element, there’s no need to swap it with itself, which would be inefficient.
                    Without the if check, the algorithm would unnecessarily swap elements with themselves, which doesn't affect correctness but can be an inefficient operation.
             */
            if (i != minIndex) { //
                swap(array, i, minIndex);
                swapped = true;
            }

            if (!swapped  ){
                break;
            }


        }
    }

    public static void swap(int[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    public static void main(String[] args) {

        int[] myArray = {4,2,6,5,1,3};
        System.out.println(Arrays.toString(myArray));

        selectionSort(myArray);

        System.out.println( Arrays.toString(myArray) );

        /*
            EXPECTED OUTPUT:
            ----------------
            [1, 2, 3, 4, 5, 6]

         */

    }
}
