package com.ds.sort;

import java.util.Arrays;

public class MergeSort {

    public static int[] merge(int[] array1, int[] array2){

        // both arrays supposed to sorted
        int[] combined = new int[array1.length + array2.length];
        int index = 0;
        int i =0;
        int j =0;

        while (i < array1.length && j< array2.length){
            if(array1[i] < array2[j]){
               combined[index] = array1[i];
               index ++;
               i ++;

            }else{
                combined[index] = array2[j];
                index ++;
                j++;
            }
        }
        while(i < array1.length){
            combined[index] = array1[i];
            index ++;
            i++;
        }

        while(j < array2.length){
            combined[index] = array2[j];
            index ++;
            j++;
        }

    return combined;

    }

    public static  int[] mergeSort(int[] array){

        if (array.length ==1 ) return array;
        int midIndex = array.length /2;
        int[] left = mergeSort(Arrays.copyOfRange(array, 0, midIndex));
        int[] right = mergeSort(Arrays.copyOfRange(array, midIndex, array.length));

        return merge(left, right);

    }


    public static void main(String[] args) {
        int[] array1 = {1,3,7,8};
        int[] array2 = {2,4,5,6};

        System.out.println("Array1: " + Arrays.toString(array1));
        System.out.println("Array2: " + Arrays.toString(array2));
        System.out.println("Combined Array is: " + Arrays.toString(merge(array1, array2)));

        System.out.println("=======================");

        int[] originalArray = {3,1,4,2};

        int [] sortedArray = mergeSort(originalArray);

        System.out.println( "\nOriginal Array: " + Arrays.toString( originalArray ) );

        System.out.println( "\nSorted Array: " + Arrays.toString( sortedArray ) );


    }
}
