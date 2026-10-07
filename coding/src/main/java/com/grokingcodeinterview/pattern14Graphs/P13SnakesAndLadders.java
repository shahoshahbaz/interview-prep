package com.grokingcodeinterview.pattern14Graphs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
public class P13SnakesAndLadders {
/*
problem Statement
You are given a game board of size 10 x 10, represented as a 2D array. The board contains ladders and snakes, which can either help or hinder your progress.
The board is numbered from 1 to 100, starting from the bottom-left corner and moving right and then up in a zigzag pattern. The goal is to reach square 100 from square
    1 in the fewest number of moves possible. You can roll a standard six-sided die to determine how many squares to move forward.

    example:
    ladders = [[32,62],[42,68],[12,98]]
    snakes = [[95,13],[97,25],[93,37],[79,27],[75,19],[49,47],[67,17]]
    Explanation: The ladders array represents the starting and ending squares of the ladders,
     while the snakes array represents the starting and ending squares of the snakes. For example, if you land on square 32, you can climb up the ladder to square 62, while if you land on square 95, you will slide down the snake to square 13.

     example2:
     ladders = [[3,90],[56,76],[32,62],[42,68],[12,98]]
     snakes = [[95,13],[97,25],[93,37],[79,27],[75,19],[49,47],[67,17]]
     Explanation: In this example, there are more ladders and snakes on the board, which can make it more challenging to reach square 100. However, with careful planning and strategy, it is still possible to reach the goal in a few moves.





 */
    public static int quickestWayUp(int[][] ladders, int[][] snakes){
        int[] board = new int[101];

        // initialize the board with -1 to indicate that there are no ladders or snakes on those squares
        Arrays.fill(board, -1);

        for(int[] ladder: ladders) board[ladder[0]] = ladder[1];
        for(int[] snake: snakes) board[snake[0]] = snake[1];

        // diceRoles[i] = min number of dice rolls to reach square i
        int[] diceRoles = new int[101];

        Arrays.fill(diceRoles, -1);
        // we start from square 1, so it takes 0 rolls to reach square 1
        diceRoles[1] =0;

        // queue will store the current square we are at
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(1);

        while (!queue.isEmpty()){
            int currentSquare= queue.poll();
            if(currentSquare == 100) return diceRoles[currentSquare];
            for(int dice = 1; dice <= 6; dice++){

                int nextSquare = dice + currentSquare;
                if(nextSquare>100) break;

                if(board[nextSquare] != -1)
                    nextSquare = board[nextSquare];
                if(diceRoles[nextSquare] == -1){
                    diceRoles[nextSquare] = diceRoles[currentSquare] +1;
                    queue.offer(nextSquare);
                }
            }
        }

        return -1;

    }

    public static void main(String[] args) {
        int[][] ladders = {{32,62},{42,68},{12,98}};
        int[][] snakes = {{95,13},{97,25},{93,37},{79,27},{75,19},{49,47},{67,17}};

        int result = quickestWayUp(ladders, snakes);
        System.out.println(result); // expected 3
        // second example
        ladders = new int[][]{{3,90},{56,76},{32,62},{42,68},{12,98}};
        snakes = new int[][]{{95,13},{97,25},{93,37},{79,27},{75,19},{49,47},{67,17}};
        result = quickestWayUp(ladders, snakes);
        System.out.println(result); // expected 2
    }
}

