package com.grokingcodeinterview.pattern11HashMaps;

import java.util.HashMap;

import static com.Utility.makeItBold;

/*
Problem Statement
Given a string, determine the maximum number of times the word "balloon" can be formed using the characters from the string. Each character in the string can be used only once.
Example 1: Input: "balloonballoon" Expected Output: 2
Justification: The word "balloon" can be formed twice from the given string.
Example 2: Input: "bbaall"Expected Output: 0
Justification: The word "balloon" cannot be formed from the given string as we are missing the character 'o' twice.
Example 3: Input: "balloonballoooon"
Expected Output: 2

Justification: The word "balloon" can be formed twice, even though there are extra 'o' characters.
Example 4: Input: "loonbalxballpoon" Expected Output: 2
Example 5: Input: "leetcode" Expected Output: 0
Constraints:

1 <= text.length <= 104
text consists of lower case English letters only.
 */
public class P03MaximumNumberOfBalloons {
    public static int findMaximumNumberOfBalloons(String str){

        String balloon =  "balloon";
        HashMap<Character, Integer> balloonFreqMap = new HashMap<>();
        for (char ch: balloon.toCharArray()){
            balloonFreqMap.put(ch, balloonFreqMap.getOrDefault(ch, 0) +1);

        }
        HashMap<Character, Integer> stringFreqMap = new HashMap<>();
        for (char ch: str.toCharArray()){
            stringFreqMap.put(ch, stringFreqMap.getOrDefault(ch, 0) +1);
        }

        int maxBalloons = Integer.MAX_VALUE;

        for(char ch: balloonFreqMap.keySet()){
            if(!stringFreqMap.containsKey(ch)){
                return 0;
            }
            int possible = stringFreqMap.get(ch)/ balloonFreqMap.get(ch);
            maxBalloons = Math.min(maxBalloons, possible);
        }
        return maxBalloons;

    }
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("P03.Maximum Number Of Balloons");
        System.out.println("=====================================");
        String strP93 = "balloonballoon";
        System.out.println("Input: "+ strP93 + " => Output: "+ makeItBold(findMaximumNumberOfBalloons(strP93)+"") +" ,Expected Output: 2");
        strP93 = "bbaall";
        System.out.println("Input: "+ strP93 + " => Output: "+ makeItBold(findMaximumNumberOfBalloons(strP93)+"") +" ,Expected Output: 0");
        strP93 = "balloonballoooon";
        System.out.println("Input: "+ strP93 + " => Output: "+ makeItBold(findMaximumNumberOfBalloons(strP93)+"") +" ,Expected Output: 2");
        strP93 = "loonbalxballpoon";
        System.out.println("Input: "+ strP93 + " => Output: "+ makeItBold(findMaximumNumberOfBalloons(strP93)+"") +" ,Expected Output: 2");
        strP93 = "leetcode";
        System.out.println("Input: "+ strP93 + " => Output: "+ makeItBold(findMaximumNumberOfBalloons(strP93)+"") +" ,Expected Output: 0");
        

    }

}

