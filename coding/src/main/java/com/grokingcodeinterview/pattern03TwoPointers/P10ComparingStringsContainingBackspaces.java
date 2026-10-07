package com.grokingcodeinterview.pattern03TwoPointers;

/*
Problem Statement
Given two strings containing backspaces (identified by the character ‘#’),
check if the two strings are equal.

Example 1: Input: str1="xy#z", str2="xzz#" Output: true
Explanation: After applying backspaces the strings become "xz" and "xz" respectively.
Example 2:

Input: str1="xy#z", str2="xyz#" Output: false
Explanation: After applying backspaces the strings become "xz" and "xy" respectively.

Example 3: Input: str1="xp#", str2="xyz##" Output: true
Explanation: After applying backspaces the strings become "x" and "x" respectively.
In "xyz##", the first '#' removes the character 'z' and the second '#' removes the character 'y'.
Example 4:

Input: str1="xywrrmp", str2="xywrrmu#p" Output: true
Explanation: After applying backspaces the strings become "xywrrmp" and "xywrrmp" respectively.
Constraints:

1 <= str1.length, str2.length <= 200
str1 and str2 only contain lowercase letters and '#' characters.
 */

import static com.Utility.log;

/**
 * str1="xy#z", str2="xzz#"
 *
 *  xy#z  i = 2 => remove (i-1) => xz
 *  "xzz#" i = 3 => remove (i-1 -> xz
 *
 *  str1="xp#", str2="xyz##"
 *  i = 2, subString(0, 2-1)
 *
 *
 */
public class P10ComparingStringsContainingBackspaces {

    public static boolean compareStrings(String str1, String str2) {
        log("Comparing strings:");
        log("String1: " + str1);
        log("String2: " + str2);

        int index1 = str1.length() - 1;
        int index2 = str2.length() - 1;

        int skip1 = 0;
        int skip2 = 0;

        while (index1 >= 0 || index2 >= 0) {

            // Find next valid char in str1
            while (index1 >= 0) {
                char c = str1.charAt(index1);
                if (c == '#') {
                    skip1++;
                    log("Found '#' in String1 at index " + index1 + ", skip1 now: " + skip1);
                    index1--;
                } else if (skip1 > 0) {
                    log("Skipping char '" + c + "' in String1 at index " + index1 + ", skip1 left: " + (skip1 - 1));
                    skip1--;
                    index1--;
                } else {
                    log("Next valid char in String1: '" + c + "' at index " + index1);
                    break;
                }
            }

            // Find next valid char in str2
            while (index2 >= 0) {
                char c = str2.charAt(index2);
                if (c == '#') {
                    skip2++;
                    log("Found '#' in String2 at index " + index2 + ", skip2 now: " + skip2);
                    index2--;
                } else if (skip2 > 0) {
                    log("Skipping char '" + c + "' in String2 at index " + index2 + ", skip2 left: " + (skip2 - 1));
                    skip2--;
                    index2--;
                } else {
                    log("Next valid char in String2: '" + c + "' at index " + index2);
                    break;
                }
            }

            char ch1 = (index1 >= 0) ? str1.charAt(index1) : '\0';
            char ch2 = (index2 >= 0) ? str2.charAt(index2) : '\0';

            log("Comparing chars: '" + ch1 + "' and '" + ch2 + "'");

            if (ch1 != ch2) {
                log("Mismatch found, returning false");
                return false;
            }

            index1--;
            index2--;
        }

        log("All characters matched, returning true");
        return true;
    }

    public static void main(String[] args) {
        // some test caeses
        String str1P10 = "xy#z";
        String str2P10 = "xzz#";
        boolean result = compareStrings(str1P10, str2P10);

        System.out.println("String1: " + str1P10 + ", String2: " + str2P10  + " Are the two strings equal? " + result + " Expected: true"); // expected output: true


        str1P10 = "xy#z";
        str2P10 = "xyz#";

        result = compareStrings(str1P10, str2P10);
        System.out.println("String1: " + str1P10 + ", String2: " + str2P10  + " Are the two strings equal? " + result + "Expected: false"); // expected output: false


        str1P10 = "xp#";
        str2P10 = "xyz##";
        result = compareStrings(str1P10, str2P10);
        System.out.println("String1: " + str1P10 + ", String2: " + str2P10  + " Are the two strings equal? " + result + " Expected: true"); // expected output: true

        str1P10 = "xywrrmp";
        str2P10 = "xywrrmu#p";
        result = compareStrings(str1P10, str2P10);
        System.out.println("String1: " + str1P10 + ", String2: " + str2P10  + " Are the two strings equal? " + result + " Expected: true"); // expected output: true


    }
}
