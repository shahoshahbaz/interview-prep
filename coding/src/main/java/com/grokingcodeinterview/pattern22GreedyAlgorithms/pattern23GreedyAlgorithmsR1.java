package com.grokingcodeinterview.pattern22GreedyAlgorithms;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;

import static com.Utility.makeItBold;


public class pattern23GreedyAlgorithmsR1 {
    /*
     Problem Statement:
    Determine the minimum number of deletions required to remove the smallest and the largest elements from an array of integers.
    In each deletion, you are allowed to remove either the first (leftmost) or the last (rightmost) element of the array.

    Examples
    Example 1: Input: [3, 2, 5, 1, 4] Expected Output: 3
    Justification: The smallest element is 1 and the largest is 5. Removing 4, 1, and then 5 (or 5, 4, and then 1) in three moves is the most efficient strategy.
    Example 2: Input: [7, 5, 6, 8, 1]Expected Output: 2
    Justification: Here, 1 is the smallest, and 8 is the largest. Removing 1 and then 8 in two moves is the optimal strategy.
    Example 3:

    Input: [2, 4, 10, 1, 3, 5] Expected Output: 4
    Justification: The smallest is 1 and the largest is 10. One strategy is to remove 2, 4, 10, and then 1 in four moves.
    Constraints:

    1 <= nums.length <= 105
    -105 <= nums[i] <= 105
    The integers in nums are distinct.
 */

    /**
     * Input: [3, 2, 5, 1, 4] Expected Output: 3
     *  smallest element  =1
     *  largest element = 5;
     *
     */
    public static int numberOfDeletion(int[] nums){
        int n = nums.length;
        int max= Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        int minIndex = -1;
        int maxIndex = -1;

        // find min and max and minIndex and maxIndex
        for (int i =0; i< nums.length; i++){
            if(nums[i]<min){
                min = nums[i];
                minIndex = i;
            }
            if(nums[i]> max){
                max = nums[i];
                maxIndex = i;
            }
        }

        // get leftMost and rightMostIndex
        int leftMostIndex = Math.min(minIndex, maxIndex);
        int rightMostIndex = Math.max(minIndex, maxIndex);
        // remove from left oly
        int costLeft = rightMostIndex +1;
        // remove from right only
        int costRight = n - leftMostIndex;
        // remove from both
        int costBoth = (leftMostIndex +1) +(n- rightMostIndex );

        return Math.min(costBoth, Math.min(costRight, costLeft));



    }
    /*
Given a string s containing 0 to 9 digits, create the largest possible palindromic number using the string characters. It should not contain leading zeroes.
A palindromic number reads the same backward as forward.

If it's not possible to form such a number using all digits of the given string, you can skip some of them.

Examples
Example 1 Input: s = "323211444" Expected Output: "432141234"
Justification: This is the largest palindromic number that can be formed from the given digits.
Example 2 Input: s = "998877" Expected Output: "987789"
Justification: "987789" is the largest palindrome that can be formed.
Example 3  Input: s = "54321" Expected Output: "5"
Justification: Only "5" can form a valid palindromic number as other digits cannot be paired.
Constraints:

1 <= num.length <= 10^55
num consists of digits.
 */
    public static String largestPalindromic(String str){

        int[] freq = new int[10];
        // get frequency
        for (char ch: str.toCharArray()){
            freq[ch -'0'] ++;
        }

        String center ="";
        for (int digit = 9 ; digit>=0; digit--){
            if (freq[digit] % 2 == 1){
                center = String.valueOf(digit);
                freq[digit]--;
                break;

            }
        }

        StringBuilder left = new StringBuilder ();
        for (int digit = 9; digit >=0 ; digit --){
            // removing leading zero
            if (digit ==0 && left.isEmpty()) continue;

            // first half will have half of any frequency, for example if we have
            //freq[5] = 8 the first half should have 4, and when freq[3] =1 then no element needs to be added.
            int counter = freq[digit]/2;
            for (int i =0; i< counter; i++){
                left.append(digit);
            }

        }

        String right = left.reverse().toString();
        left.reverse();

        return left+center + right;




    }
    /*
Problem Statement
Given a string s, remove all duplicate letters from the input string while .
Additionally, the returned string should be the smallest in lexicographical order among all possible results.
A string is in the smallest lexicographical order
if it appears first in a dictionary. For example, "abc" is smaller than "acb" because "abc" comes first alphabetically.

Examples:
Input: "babac" Expected Output: "abc"
Justification:
After removing 1 b and 1 a from the input string, we can get bac, and abc strings.
The final answer is 'abc', which is the smallest lexicographical string without duplicate letters.
Input: "zabccde" Expected Output: "zabcde"
Justification: Removing one of the 'c's forms 'zabcde', the smallest string in lexicographical order without duplicates.
Input: "mnopmn" Expected Output: "mnop"
Justification: Removing the second 'm' and 'n' gives 'mnop', which is the smallest possible string without duplicate characters.
Constraints:
1 <= s.length <= 10^4
s consists of lowercase English letters.
 */
    public static String removeDuplicateLetters(String str){

        int[] lastIndex = new int[26];
        boolean[] used = new boolean[26];
        // step: find last occurrence of each char
        for (int i =0; i< str.length(); i++){
            lastIndex[str.charAt(i) -'a'] = i;
        }

        Deque<Character> stack = new ArrayDeque<>();

        for (int i =0; i< str.length(); i++){
            char ch = str.charAt(i);
            int indexCh = ch -'a';

            if (used[indexCh]) continue;

            while (!stack.isEmpty()){
                char top = stack.peek();
                int indexTop = top -'a';
                if(top > ch && lastIndex[indexTop] > i){
                    used[indexTop] = false;
                    stack.pop();
                }else{
                    break;
                }
            }
            stack.push(ch);
            used[indexCh] = true;

        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()){
            sb.append(stack.pop());
        }

        return sb.reverse().toString();

    }
    /*
    Problem Statement
    Given a collection of pairs where each pair contains two elements [a, b] and a < b,
    find the maximum length of a chain you can form using pairs.
    A pair [a, b] can follow another pair [c, d] in the chain if b < c.
    You can select pairs in any order and don't need to use all the given pairs.

    Example 1: Input: [[1,2], [3,4], [2,3]]  Expected Output: 2
    Justification: The longest chain is [1,2] -> [3,4]. The chain [1,2] -> [2,3] is invalid because 2 is not smaller than 2.
    Example 2:  Input: [[5,6], [1,2], [8,9], [2,3]] Expected Output: 3
    Justification: The chain can be [1,2] -> [5,6] -> [8,9] or [2,3] -> [5,6] -> [8, 9].
    Example 3: Input: [[7,8], [5,6], [1,2], [3,5], [4,5], [2,3]] Expected Output: 3
    Justification: The longest possible chain is formed by chaining [1,2] -> [3,5] -> [7,8].
    Constraints:
    n == pairs.length
    1 <= n <= 1000
    -1000 <= lefti < righti <= 1000
     */
    public static int findLongestChain(int[][] pairs){

     Arrays.sort(pairs, Comparator.comparingInt(a->a[1]));

     int lastEnd = pairs[0][1];
     int counter =1;
     for (int i =1; i<pairs.length; i++){
         if (lastEnd< pairs[i][0]){
             counter ++;
             lastEnd = pairs[i][1];
         }
     }

     return counter;
 }
    /*
Problem Statement
Given a string str containing '(' and ')' characters,
find the minimum number of parentheses that need to be added to a string of parentheses to make it valid.
A valid string of parentheses is one
where each opening parenthesis '(' has a corresponding closing parenthesis ')' and vice versa. The goal is to determine the least amount of additions needed to achieve this balance.

Example 1: Input: "(()" Expected Output: 1
Justification: The string has two opening parentheses and one closing parenthesis. Adding one closing parenthesis at the end will balance it.

Example 2: Input: "))(("  Expected Output: 4
Justification: There are two closing parentheses at the beginning and two opening at the end. We need two opening parentheses
before the first closing and two closing parentheses after the last opening to balance the string.
Example 3: Input: "(()())(" Expected Output: 1
Justification: The string has three opening parentheses and three closing parentheses, with an additional opening parenthesis at the end. Adding one closing parenthesis at the end will balance it.
Constraints:
1 <= s.length <= 1000
s[i] is either '(' or ')'.
 */
    /**
     * Input: "(()" Expected Output: 1
     * (()
     * c =1
     * OpenNeeded: o
     * closeNeedeed:c
     * <p>
     * <p>
     * ))((
     * o=2, c=2 = 4
     *
     */
    public static int minAddToMakeValid(String str) {

        int openNeeded = 0;
        int closeNeeded = 0;

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '(') {
                closeNeeded++;
            } else { // if ch ')'
                if (closeNeeded > 0) {
                    closeNeeded--;
                } else {
                    openNeeded++;
                }

            }

        }

        return openNeeded + closeNeeded;


    }
    /*
Problem Statement
Given string s, determine whether it's possible to make a given string palindrome by removing at most one character.

A palindrome is a word or phrase that reads the same backward as forward.
Example 1: Input: "racecar"  Expected Output: true
Justification: The string is already a palindrome, so no removals are needed.
Example 2: Input: "abccdba" Expected Output: true
Justification: Removing the character 'd' forms the palindrome "abccba".
Example 3: Input: "abcdef" Expected Output: false
Justification: No single character removal will make this string a palindrome.
Constraints:
1 <= s.length <= 105
str consists of lowercase English letters.
 */
    /**
     * Input: "racecar"  Expected Output: true
     * racecar
     * (l, r):  if == lef++, right --
     * (1,7) (2,6), (3,5) ,(4,4)
     */
    public static boolean isPalindromePossible(String str) {
        if (str == null || str.length() == 1) return true;
        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return isPalindrome(str, left + 1, right) || isPalindrome(str, left, right - 1);
            }
            left++;
            right--;

        }
        return true;

    }
    public static boolean isPalindrome(String str, int left, int right){
        while (left< right){
            if (str.charAt(left) != str.charAt(right)){
                return false;
            }
            left++;
            right --;
        }
        return true;
    }
    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("P01. Valid Palindrome... ");
        System.out.println("===================================");
        String strP01 = "abccba";
        System.out.println("Input: " + makeItBold(strP01) +
                ", Is  palindrome possible?" + makeItBold(isPalindromePossible(strP01) + "") + ", expected: true");
        strP01 = "racecar";
        System.out.println("Input: " + makeItBold(strP01) +
                ", Is  palindrome possible?" + makeItBold(isPalindromePossible(strP01) + "") + ", expected: true");
        strP01 = "abccdba";
        System.out.println("Input: " + makeItBold(strP01) +
                ", Is  palindrome possible?" + makeItBold(isPalindromePossible(strP01) + "") + ", expected: true");
        strP01 = "abcdef";
        System.out.println("Input: " + makeItBold(strP01) +
                ", Is  palindrome possible?" + makeItBold(isPalindromePossible(strP01) + "") + ", expected: false");

        // more examples
        strP01 = "abc";
        System.out.println("Input: " + makeItBold(strP01) +
                ", Is  palindrome possible?" + makeItBold(isPalindromePossible(strP01) + "") + ", expected: false");
        strP01 = "abca";
        System.out.println("Input: " + makeItBold(strP01) +
                ", Is  palindrome possible?" + makeItBold(isPalindromePossible(strP01) + "") + ", expected: true");
        strP01 = "a";
        System.out.println("Input: " + makeItBold(strP01) +
                ", Is  palindrome possible?" + makeItBold(isPalindromePossible(strP01) + "") + ", expected: true");
        strP01 = "aa";
        System.out.println("Input: " + makeItBold(strP01) +
                ", Is  palindrome possible?" + makeItBold(isPalindromePossible(strP01) + "") + ", expected: true");
        strP01 = "ab";
        System.out.println("Input: " + makeItBold(strP01) +
                ", Is  palindrome possible?" + makeItBold(isPalindromePossible(strP01) + "") + ", expected: true");
        System.out.println("==============================================================");
        System.out.println(makeItBold("P03. Minimum Add To Make Parentheses Valid"));
        System.out.println("==============================================================");
        String strP03 = "(()";
        System.out.println("Input: " + makeItBold(strP03) + " Expected Output: " + makeItBold("1") + " Actual Output: " + makeItBold(minAddToMakeValid(strP03) + ""));

        strP03 = "))((";
        System.out.println("Input: " + makeItBold(strP03) + " Expected Output: " + makeItBold("4") + " Actual Output: " + makeItBold(minAddToMakeValid(strP03) + ""));

        strP03 = "(()())(";
        System.out.println("Input: " + makeItBold(strP03) + " Expected Output: " + makeItBold("1") + " Actual Output: " + makeItBold(minAddToMakeValid(strP03) + ""));

        strP03 = "())(()";
        System.out.println("Input: " + makeItBold(strP03) + " Expected Output: " + makeItBold("2") + " Actual Output: " + makeItBold(minAddToMakeValid(strP03) + ""));

        System.out.println("===================================");
        System.out.println("P02. Maximum Length Of Pair Chain... ");
        System.out.println("===================================");
        int[][] pairsP02 = {{1,2}, {3,4}, {2,3}};
        System.out.println("Input: " + Arrays.deepToString(pairsP02) +
                ", output:" + makeItBold(findLongestChain(pairsP02) +"")+ ", expected: 2");
        pairsP02 = new int[][]{{5,6}, {1,2}, {8,9}, {2,3}};
        System.out.println("Input: " + Arrays.deepToString(pairsP02) +
                ", output:" + makeItBold(findLongestChain(pairsP02) +"")+ ", expected: 3");
        pairsP02 = new int[][]{{7,8}, {5,6}, {1,2}, {3,5}, {4,5}, {2,3}};
        System.out.println("Input: " + Arrays.deepToString(pairsP02) +
                ", output:" + makeItBold(findLongestChain(pairsP02) +"")+ ", expected: 3");
        pairsP02 = new int[][]{{1,3}, {2,4}, {3,5}, {6,7}, {8,9}};
        System.out.println("Input: " + Arrays.deepToString(pairsP02) +
                ", output:" + makeItBold(findLongestChain(pairsP02) +"")+ ", expected: 3");

        System.out.println("===================================");
        System.out.println("P04. Remove Duplicate Letters... ");
        System.out.println("===================================");
        String str04 = "babac";
        System.out.println("Input: " + str04 + ", output: " + makeItBold(removeDuplicateLetters(str04)) + " ,Expected Output: abc");
        str04 = "zabccde";
        System.out.println("Input: " + str04 + ", output: " + makeItBold(removeDuplicateLetters(str04)) + " ,Expected Output: zabcde");
        str04 = "mnopmn";
        System.out.println("Input: " + str04 + ", output: " + makeItBold(removeDuplicateLetters(str04)) + " ,Expected Output: mnop");
        str04 = "cbacdcbc";
        System.out.println("Input: " + str04 + ", output: " + makeItBold(removeDuplicateLetters(str04)) + " ,Expected Output: acdb");

        System.out.println("===================================");
        System.out.println("P05. Largest Palindromic Number... ");
        System.out.println("===================================");
        String strP05 = "323211444";
        System.out.println("Input: " + strP05 +
                ", output:" + makeItBold(largestPalindromic(strP05))+ ", expected: 432141234");
        strP05 = "998877";
        System.out.println("Input: " + strP05 +
                ", output:" + makeItBold(largestPalindromic(strP05))+ ", expected: 987789");
        strP05 = "54321";
        System.out.println("Input: " + strP05 +
                ", output:" + makeItBold(largestPalindromic(strP05))+ ", expected: 5");
        strP05 = "0000";
        System.out.println("Input: " + strP05 +
                ", output:" + makeItBold(largestPalindromic(strP05))+ ", expected: 0");
        strP05 = "0001100";
        System.out.println("Input: " + strP05 +
                ", output:" + makeItBold(largestPalindromic(strP05))+ ", expected: 100001");
        System.out.println("===================================");
        System.out.println("P06. Removing Minimum and Maximum from Array");
        System.out.println("===================================");
        int[] numsP06 = {3, 2, 5, 1, 4};
        System.out.println("Input: " + Arrays.toString(numsP06) +", Output: " + makeItBold(numberOfDeletion(numsP06)+"")  +" ,Expected: 3");
        numsP06 = new int[]{7, 5, 6, 8, 1};
        System.out.println("Input: " + Arrays.toString(numsP06) +", Output: " + makeItBold(numberOfDeletion(numsP06)+"")  +" ,Expected: 2");
        numsP06 = new int[]{2, 4, 10, 1, 3, 5};
        System.out.println("Input: " + Arrays.toString(numsP06) +", Output: " + makeItBold(numberOfDeletion(numsP06)+"")  +" ,Expected: 4");
        numsP06 = new int[]{1,2,3,4,5,6,7,8,9,10};
        System.out.println("Input: " + Arrays.toString(numsP06) +", Output: " + makeItBold(numberOfDeletion(numsP06)+"")  +" ,Expected: 2");




    }
}

