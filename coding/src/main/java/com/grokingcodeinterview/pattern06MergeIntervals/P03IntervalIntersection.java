package com.grokingcodeinterview.pattern06MergeIntervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.Utility.makeItBold;

/*
Given two lists of intervals,
find the intersection of these two lists.
Each list consists of disjoint intervals sorted on their start time.

Example 1: Input: arr1=[[1, 3], [5, 6], [7, 9]], arr2=[[2, 3], [5, 7]]
Output: [2, 3], [5, 6], [7, 7]
Explanation: The output list contains the common intervals between the two lists.
Example 2:

Input: arr1=[[1, 3], [5, 7], [9, 12]], arr2=[[5, 10]]
Output: [5, 7], [9, 10]
Explanation: The output list contains the common intervals between the two lists.
Constraints:

0 <= arr1.length, arr2.length <= 1000
arr1.length + arr2.length >= 1
0 <= start i < end i <= 10^99
end i < start i+1
0 <= start j < end j <= 10^9
end j < start j+1
*/
public class P03IntervalIntersection {

    public static  List<Interval> findIntersection(Interval[] arr1, Interval[] arr2){
    List<Interval> result = new ArrayList<>();
    // arr1=[[1, 3], [5, 7], [9, 12]], arr2=[[5, 10]]
    // edge cases
    int index1 = 0;
    int index2= 0;
    while (index1< arr1.length && index2< arr2.length){

        Interval interval1 = arr1[index1];
        Interval interval2 = arr2[index2];
        int start =  Math.max(interval1.start, interval2.start);
        int end = Math.min(interval1.end, interval2.end);
        if (start <= end){  // why do we need this check ? because there might be no intersection
            // as an example [1,3] and [4,6] => start =4 , end =3
            result.add(new Interval(start, end));
        }

        if(interval1.end<= interval2.end){ // move the pointer for the interval which is finishing first
            index1++;
        }else{
            index2++;
        }

    }
    return result;
    }

    public static void main(String[] args) {

        System.out.println("==============================================================");
        System.out.println("P03. Interval Intersection");
        System.out.println("==============================================================");

        Interval[] intervalsAP03 =  { new Interval(1, 3), new Interval(5, 6), new Interval(7, 9) };

        Interval[] intervalsBP03 = { new Interval(2, 3), new Interval(5, 7) };
        List<Interval> result = findIntersection(intervalsAP03, intervalsBP03);
        System.out.println("Inputs: " + Arrays.toString(intervalsAP03) +"," + Arrays.toString(intervalsBP03) + " ,output: " +
                makeItBold(result.toString()) + "Expected: [[2, 3], [5, 6], [7, 7]]");

         intervalsAP03 = new Interval[] { new Interval(1, 3), new Interval(5, 7), new Interval(9, 12) };
        intervalsBP03 =  new Interval[]{ new Interval(5, 10) };

         result = findIntersection(intervalsAP03, intervalsBP03);
        System.out.println("Inputs: " + Arrays.toString(intervalsAP03) +"," + Arrays.toString(intervalsBP03) + " ,output: " +
                makeItBold(result.toString()) + " Expected: [[5, 7], [9, 10]]");


        intervalsAP03= new Interval[]{ new Interval(1, 3), new Interval(5, 7), new Interval(9, 12) };
        intervalsBP03 = new Interval[] { new Interval(2, 4), new Interval(6, 8), new Interval(11, 13) };
        result = findIntersection(intervalsAP03, intervalsBP03);
        System.out.println("Inputs: " + Arrays.toString(intervalsAP03) +"," + Arrays.toString(intervalsBP03) + " ,output: " +
                makeItBold(result.toString()) + " Expected: [[2, 3], [6, 7], [11, 12]]");




    }



}

