package com.grokingcodeinterview.pattern18ModifiedBinarySearch;

/*
problem statement:
Given an array of lowercase letters sorted in ascending order,
 find the smallest letter in the given array greater than a given â€˜keyâ€™.
Assume the given array is a circular list,
which means that the last letter is assumed to be connected with the first letter.
This also means that the smallest letter in the given array is
greater than the last letter of the array and is also the first letter of the array.
Write a function to return the next letter of the given â€˜keyâ€™.
Example 1: Input: ['a', 'c', 'f', 'h'], key = 'f' Output: 'h'
Explanation: The smallest letter greater than 'f' is 'h' in the given array.
Example 2: Input: ['a', 'c', 'f', 'h'], key = 'b' Output: 'c'
Explanation: The smallest letter greater than 'b' is 'c'.
Example 3: Input: ['a', 'c', 'f', 'h'], key = 'm' Output: 'a'
Explanation: As the array is assumed to be circular, the smallest letter greater than 'm' is 'a'.
Example 4: Input: ['a', 'c', 'f', 'h'], key = 'h' Output: 'a'
Explanation: As the array is assumed to be circular, the smallest letter greater than 'h' is 'a'.
Constraints:
2 <= letters.length <= 10^4
letters[i] is a lowercase English letter.
letters is sorted in non-decreasing order.
letters contains at least two different characters.
key is a lowercase English letter.
 */

import java.util.Arrays;

import static com.Utility.makeItBold;

/**
 * Input: ['a', 'c', 'f', 'h'], key = 'f' * Output: 'h'
 *  s =0, e = 3, mid = 1, c<h => s = 2
 *  s =2, e = 3, mid =2, f==f , s = 3
 *  s =3, e =3, mid = 3 h>f, e = 2 =out ofloop
 *  return arr[s%n] h
 *
 *Input: ['a', 'c', 'f', 'h'], key = 'b' * Output: 'c'
 *  s= 0, e = 3, mid = 1, c>b, e = 2
 *  s =0, e = 2, mid = 1, c>b, e = 1
 *  s = 0, e = 1, mid = 0, a<b, s =1
 *  s =1, e =1,  mid =1, c>b e = 0
 *  out of loop return arr[s%n]= c
 *
 * Input: ['a', 'c', 'f', 'h'], key = 'h' * Output: 'a'
 * s =1, e=3, mid = 2, f<h ,  s= 3,
 * s= 3, e=3, mid = 3, h == h, s = 4
 * s= 4, e = 3, out of loop arr[s%4] = 'a
 *
 * Input: ['a', 'c', 'f', 'h'], key = 'm' * Output: 'a'
 * s = 0, e=3, mid = 1 c<m s = 2
 * s=2, e = 3 mid =2, f<m s = 3
 * s = 3, e = 3, mid = 3 h<m s= 4
 * s = 4 , e= 3 out of loop return arr[4%4] = a
 *
 */

public class P04NextLetter {

    public static char nextGreatestLetter(char[] letters, char key) {
        int n= letters.length;
        int start = 0;
        int end = n-1;


        while (start<=end){

            int mid = start + (end -start)/2;

            if (letters[mid]> key){ // why? because we need the smallest letter greater than key
                end = mid -1; // but continue to search on the left side to find a smaller letter greater than key and also all element in  right side is> key so we have to search left side

            }else{
                start = mid +1; // letters[mid] <= key
            }
        }

        return letters[start%n];
    }

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("P04. Next Letter");
        System.out.println("==============================================");
        char[] lettersP04 = {'a', 'c', 'f', 'h'};
        char keyP04 = 'f';
        System.out.println("Input: " + Arrays.toString(lettersP04) + ", key = '" + keyP04 + ",output: " + makeItBold(nextGreatestLetter(lettersP04, keyP04) + "") + " ,Expected Output: h");
        lettersP04 = new char[]{'a', 'c', 'f', 'h'};
        keyP04 = 'b';
        System.out.println("Input: " + Arrays.toString(lettersP04) + ", key = '" + keyP04 + ",output: " + makeItBold(nextGreatestLetter(lettersP04, keyP04) + "") + " ,Expected Output: c");
        lettersP04 = new char[]{'a', 'c', 'f', 'h'};
        keyP04 = 'm';
        System.out.println("Input: " + Arrays.toString(lettersP04) + ", key = '" + keyP04 + ",output: " + makeItBold(nextGreatestLetter(lettersP04, keyP04) + "") + " ,Expected Output: a");
        lettersP04 = new char[]{'a', 'c', 'f', 'h'};
        keyP04 = 'h';
        System.out.println("Input: " + Arrays.toString(lettersP04) + ", key = '" + keyP04 + ",output: " + makeItBold(nextGreatestLetter(lettersP04, keyP04) + "") + " ,Expected Output: a");
        }
}

