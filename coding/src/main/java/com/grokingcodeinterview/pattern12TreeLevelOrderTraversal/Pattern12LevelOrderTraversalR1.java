package com.grokingcodeinterview.pattern12TreeLevelOrderTraversal;

import java.util.*;

import static com.Utility.makeItBold;


/*
 * Review 1: Level Order Traversal Pattern
 * This class contains all test cases from P01 through P06
 * Purpose: Practice and implement the Level Order Traversal pattern yourself
 * Instructions: Implement the empty methods below and test them with the test cases
 */
public class Pattern12LevelOrderTraversalR1 {

    // ============================================================
    // P03. MAXIMUM WIDTH OF BINARY TREE - IMPLEMENT THIS
    // ============================================================
    /*
        Problem Statement
        Given the root of a binary tree, find the maximum width of the tree.

        The maximum width is the widest level in the tree. The width of a level is the number of nodes between the leftmost and rightmost non-null nodes,
         where the null nodes between the end-nodes that would be present in a complete binary tree extending down to that level are also counted into the length calculation.
        You can assume that the result will fit within a 32-bit signed integer.

        Example 1 Input: root = [1, 2, 3, 4, null, null, 5] Output: 4
        Justification: The maximum width is at the last level between nodes 4 and 5. It counts four positions: [4, null, null, 5].
        Example 2: Input: root = [1, 2, 3, 4, null, 5, 6, null, 7]  Output: 4
        Justification: The maximum width is between nodes 4 and 6 at level 3, counting four positions: [4, null, 5, 6].
        Example 3: Input: root = [1, 2, null, 3, 4, null, null, 5]  Output: 2
        Justification: The maximum width is at the third level, between nodes 3 and 4. It counts two positions: [3, 4].
        Constraints:  The number of nodes in the tree is in the range [1, 3000].
        -100 <= Node.val <= 100
  */
    public static class Pair{
        TreeNode node;
        long index;
        public Pair(TreeNode node,  long index){
            this.node = node;
            this.index = index;
        }
    }
    public static int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;

        Deque<Pair> queue = new ArrayDeque<>();

        queue.offer(new Pair(root, 0));

        int maxWidth =0;
        while(!queue.isEmpty()){
            int levelSize = queue.size();
            long first =0;
            long last = 0;
            long minIdx = queue.peek().index;

            for (int i =0; i< levelSize; i++){
                Pair curr = queue.pop();
                long idx = curr.index -minIdx;
                if (i ==0) first = idx;
                if (i == levelSize -1) last = idx;

                if (curr.node.left != null)
                    queue.offer(new Pair(curr.node.left, 2 * idx + 1));
                if (curr.node.right != null)
                    queue.offer(new Pair(curr.node.right, 2*idx +2));

            }

            maxWidth = Math.max(maxWidth, (int)(last - first +1) );

        }


        return maxWidth;
    }
    // ============================================================
    // P00. REVERS ORDER TRAVERSAL - IMPLEMENT THIS
    // ============================================================
    /*
    problem statement
    Given a binary tree, perform a level order traversal in reverse order.
    Return the result as a list of lists where each inner list represents nodes at each level
    traversed from bottom to top (reverse order).
    Example 1: Input: root = [3,9,20,null,null,15,7]  Expected Output: [[15,7],[9,20],[3]]
    Explanation: Level order from bottom to top.
    Example 2: Input: root = [1]  Expected Output: [[1]]
    Explanation: Single node tree.
    Example 3: Input: root = []  Expected Output: []
    Explanation: Empty tree.
    */

    public static List<List<Integer>> reverseLevelOrder(TreeNode root){
        if (root == null) return new ArrayList<>();
        LinkedList<List<Integer>> result = new LinkedList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();

        queue.offer(root);

        while (!queue.isEmpty()){

            int levelSize = queue.size();
            List<Integer> levelNodes = new LinkedList<>();

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                levelNodes.add(curr.val);

                if (curr.left != null)
                    queue.offer(curr.left);
                if (curr.right != null)
                    queue.offer(curr.right);
            }

            result.addFirst(levelNodes);

        }


 return result;
    }


    // ============================================================
    // P00. ORDER TRAVERSAL - IMPLEMENT THIS
    // ============================================================

    /**
     *  [3,9,20,null,null,15,7]
     *
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();


        queue.offer(root);

        while(!queue.isEmpty()){

            int levelSize = queue.size();
            List<Integer> levelNodes = new ArrayList<>();

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                levelNodes.add(curr.val);
                if (curr.left != null){
                    queue.offer(curr.left);
                }
                if (curr.right != null){
                    queue.offer(curr.right);
                }

            }

            result.add(levelNodes);
        }
        return result;
    }

    // ============================================================
    // P02. FIND LARGEST VALUE IN EACH TREE ROW - IMPLEMENT THIS
    // ============================================================
    public static List<Integer> largestValues(TreeNode root) {
        // solve first time in the main class
        return null;
    }



    // ============================================================
    // P04. MAXIMUM LEVEL SUM OF A BINARY TREE - IMPLEMENT THIS
    // ============================================================
    public static int maxLevelWithHighestSum(TreeNode root) {
        if (root == null) return 0;
        Deque<TreeNode> queue = new ArrayDeque<>();
        int level =1;
        int bestLevel =1;
        int maxSumLevel = Integer.MIN_VALUE;

        queue.offer(root);

        while (!queue.isEmpty()){
            int levelSize = queue.size();
            int sumLevel= 0;

            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                sumLevel += curr.val;

                if (curr.left != null)
                    queue.offer(curr.left);
                if (curr.right != null)
                    queue.offer(curr.right);
            }
            if (sumLevel> maxSumLevel){
                maxSumLevel = sumLevel;
                bestLevel = level;
            }
            level++;

        }


        return bestLevel;
    }

    // ============================================================
    // P05. ZIGZAG LEVEL ORDER TRAVERSAL - IMPLEMENT THIS
    // ============================================================
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        return null;
    }

    // ============================================================
    // P06. EVEN ODD TREE - IMPLEMENT THIS
    // ============================================================
        /*
     Given a binary tree, return true if it is an Even-Odd tree. Otherwise, return false.
    The Even-odd tree must follow below two rules:
    At every even-indexed level (starting from 0), all node values must be odd and arranged in strictly increasing order from left to right.
    At every odd-indexed level, all node values must be even and arranged in strictly decreasing order from left to right.
    Examples
    Example 1
    Input:
        1
       / \
      10  4
     / \
    3   7
    Expected Output: true
    Justification: The tree follows both conditions for each odd and even level. So, it is an odd-even tree.
    Example 2
    Input:

        5
       / \
      9   3
     /     \
    12      8
    Expected Output: false
    Justification: Level 1 has Odd values 9 and 3 in decreasing order, but it should have even values. So, the tree is not an odd-even tree.
    Example 3
    Input:
        7
       / \
      10  2
     / \
    12  8
    Expected Output: false
    Justification: At level 2 (even-indexed), the values are 12 and 8, which are even, but they should have odd values. So, the tree is not an odd-even tree.
    Constraints:

    The number of nodes in the tree is in the range [1, 105].
    1 <= Node.val <= 106
     */

    /**
     * The Even-odd tree must follow below two rules:
     *     At every even-indexed level (starting from 0), even level %2 == 0
     *     all node values must be odd and   if curr.value %2 = 0
     *     arranged in strictly increasing order from left to right. prev< nex => prev>= nex: prev: MAXValue
     *     At every odd-indexed level, all node values must be even and arranged in strictly decreasing order from left to right.
     *
     */
    public static boolean isEvenOddTree(TreeNode root) {
        if (root == null) return true;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        int level =0;

        while (!queue.isEmpty()){
            int levelSize = queue.size();
            int prev = (level %2 == 0) ? Integer.MIN_VALUE: Integer.MAX_VALUE;
            for (int i =0; i< levelSize; i++){
                TreeNode curr = queue.poll();
                if (level % 2 ==0){ // even level

                    if(curr.val % 2 == 0 || prev>= curr.val) return false;

                }else{// odd level
                    if (curr.val %2 ==1 || prev<= curr.val) return false;
                }

                prev = curr.val;


                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null ) queue.offer(curr.right);

            }
            level++;


        }


        return true;
    }

    public static void main(String[] args) {
        System.out.println("\n========== REVIEW 1: LEVEL ORDER TRAVERSAL PATTERN ==========\n");

        // Test Case 1: Basic Binary Tree
        System.out.println("Test Case 1: Basic Binary Tree");
        System.out.println("Tree:");
        TreeNode rootP00 = new TreeNode(3);
        rootP00.left = new TreeNode(9);
        rootP00.right = new TreeNode(20);
        rootP00.right.left = new TreeNode(15);
        rootP00.right.right = new TreeNode(7);
        TreeVisualizer.printTree(rootP00);

        System.out.println("output: " + makeItBold(levelOrder(rootP00).toString()) +
                " ,Expected output: [[3], [9, 20], [15, 7]]");

        // Test Case 2: Single Node Tree
        System.out.println("\nTest Case 2: Single Node Tree");
        rootP00 = new TreeNode(1);
        TreeVisualizer.printTree(rootP00);
        System.out.println("output: " + makeItBold(levelOrder(rootP00).toString()) +
                " ,Expected output: [[1]]");

        // Test Case 3: Empty Tree
        System.out.println("\nTest Case 3: Empty Tree");
        rootP00 = null;
        System.out.println("output: " + makeItBold(levelOrder(rootP00).toString()) +
                " ,Expected output: []");

        // Test Case 4: Deeper Tree
        System.out.println("\nTest Case 4: Deeper Tree");
        rootP00 = new TreeNode(1);
        rootP00.left = new TreeNode(2);
        rootP00.right = new TreeNode(3);
        rootP00.left.left = new TreeNode(4);
        rootP00.left.right = new TreeNode(5);
        rootP00.right.left = new TreeNode(6);
        rootP00.right.right = new TreeNode(7);
        TreeVisualizer.printTree(rootP00);
        System.out.println("output: " + makeItBold(levelOrder(rootP00).toString()) +
                " ,Expected output: [[1], [2, 3], [4, 5, 6, 7]]");


        // ============================================================
        // P02. FIND LARGEST VALUE IN EACH TREE ROW
        // ============================================================
        System.out.println("\n============================================================");
        System.out.println("P02. Find Largest Value in Each Tree Row");
        System.out.println("============================================================");

        // Test Case 1: Basic Binary Tree
        System.out.println("Test Case 1: Basic Binary Tree");
        TreeNode rootP02 = new TreeNode(1);
        rootP02.left = new TreeNode(3);
        rootP02.right = new TreeNode(2);
        rootP02.left.left = new TreeNode(5);
        rootP02.left.right = new TreeNode(3);
        rootP02.right.right = new TreeNode(9);
        TreeVisualizer.printTree(rootP02);
        System.out.println("Input: [1,3,2,5,3,null,9] => Output: " + makeItBold(largestValues(rootP02) + "") + " ,Expected: [1,3,9]");

        // Test Case 2: Two Level Tree
        System.out.println("\nTest Case 2: Two Level Tree");
        rootP02 = new TreeNode(1);
        rootP02.left = new TreeNode(2);
        rootP02.right = new TreeNode(3);
        TreeVisualizer.printTree(rootP02);
        System.out.println("Input: [1,2,3] => Output: " + makeItBold(largestValues(rootP02) + "") + " ,Expected: [1,3]");

        // Test Case 3: Single Node
        System.out.println("\nTest Case 3: Single Node");
        rootP02 = new TreeNode(1);
        TreeVisualizer.printTree(rootP02);
        System.out.println("Input: [1] => Output: " + makeItBold(largestValues(rootP02) + "") + " ,Expected: [1]");

        // Test Case 4: Deeper Tree
        System.out.println("\nTest Case 4: Deeper Tree");
        rootP02 = new TreeNode(1);
        rootP02.left = new TreeNode(2);
        rootP02.right = new TreeNode(3);
        rootP02.left.left = new TreeNode(4);
        rootP02.left.right = new TreeNode(5);
        rootP02.right.left = new TreeNode(6);
        rootP02.right.right = new TreeNode(7);
        TreeVisualizer.printTree(rootP02);
        System.out.println("Input: [1,2,3,4,5,6,7] => Output: " + makeItBold(largestValues(rootP02) + "") + " ,Expected: [1,3,7]");


        // ============================================================
        // P05. ZIGZAG LEVEL ORDER TRAVERSAL
        // ============================================================
        System.out.println("\n============================================================");
        System.out.println("P05. Zigzag Level Order Traversal");
        System.out.println("============================================================");

        // Test Case 1: Basic Binary Tree
        System.out.println("Test Case 1: Basic Binary Tree");
        TreeNode rootP05 = new TreeNode(3);
        rootP05.left = new TreeNode(9);
        rootP05.right = new TreeNode(20);
        rootP05.right.left = new TreeNode(15);
        rootP05.right.right = new TreeNode(7);
        TreeVisualizer.printTree(rootP05);
        System.out.println("Input: [3,9,20,null,null,15,7] => Output: " + makeItBold(zigzagLevelOrder(rootP05) + "") + " ,Expected: [[3],[20,9],[15,7]]");

        // Test Case 2: Single Node
        System.out.println("\nTest Case 2: Single Node");
        rootP05 = new TreeNode(1);
        TreeVisualizer.printTree(rootP05);
        System.out.println("Input: [1] => Output: " + makeItBold(zigzagLevelOrder(rootP05) + "") + " ,Expected: [[1]]");

        // Test Case 3: Complex Tree
        System.out.println("\nTest Case 3: Complex Tree");
        rootP05 = new TreeNode(1);
        rootP05.left = new TreeNode(2);
        rootP05.right = new TreeNode(3);
        rootP05.left.left = new TreeNode(4);
        rootP05.right.right = new TreeNode(5);
        TreeVisualizer.printTree(rootP05);
        System.out.println("Input: [1,2,3,4,null,null,5] => Output: " + makeItBold(zigzagLevelOrder(rootP05) + "") + " ,Expected: [[1],[3,2],[4,5]]");

        // Test Case 4: Deeper Tree
        System.out.println("\nTest Case 4: Deeper Tree");
        rootP05 = new TreeNode(1);
        rootP05.left = new TreeNode(2);
        rootP05.right = new TreeNode(3);
        rootP05.left.left = new TreeNode(4);
        rootP05.left.right = new TreeNode(5);
        rootP05.right.left = new TreeNode(6);
        rootP05.right.right = new TreeNode(7);
        TreeVisualizer.printTree(rootP05);
        System.out.println("Input: [1,2,3,4,5,6,7] => Output: " + makeItBold(zigzagLevelOrder(rootP05) + "") + " ,Expected: [[1],[3,2],[4,5,6,7]]");



        System.out.println("============================================================");
        System.out.println("P01. Reverse Level Order Traversal");
        System.out.println("============================================================");

        // Test Case 1: Basic Binary Tree
        System.out.println("Test Case 1: Basic Binary Tree");
        TreeNode rootP01 = new TreeNode(3);
        rootP01.left = new TreeNode(9);
        rootP01.right = new TreeNode(20);
        rootP01.right.left = new TreeNode(15);
        rootP01.right.right = new TreeNode(7);
        System.out.println("Input: [3,9,20,null,null,15,7] => Output: " + makeItBold(reverseLevelOrder(rootP01)+"") + " ,Expected: [[15,7],[9,20],[3]]");

        // Test Case 2: Single Node
        System.out.println("\nTest Case 2: Single Node");
        rootP01 = new TreeNode(1);
        TreeVisualizer.printTree(rootP01);
        System.out.println("Input: [1] => Output: " + makeItBold(reverseLevelOrder(rootP01)+"") + " ,Expected: [[1]]");

        // Test Case 3: Empty Tree
        System.out.println("\nTest Case 3: Empty Tree");
        rootP01 = null;
        System.out.println("Input: [] => Output: " + makeItBold(reverseLevelOrder(rootP01)+"") + " ,Expected: []");

        // Test Case 4: Deeper Tree
        System.out.println("\nTest Case 4: Deeper Tree");
        rootP01 = new TreeNode(1);
        rootP01.left = new TreeNode(2);
        rootP01.right = new TreeNode(3);
        rootP01.left.left = new TreeNode(4);
        rootP01.left.right = new TreeNode(5);
        rootP01.right.left = new TreeNode(6);
        rootP01.right.right = new TreeNode(7);
        TreeVisualizer.printTree(rootP01);
        System.out.println("Input: [1,2,3,4,5,6,7] => Output: " + makeItBold(reverseLevelOrder(rootP01)+"") + " ,Expected: [[4,5,6,7],[2,3],[1]]");


        System.out.println("============================================================");
        System.out.println("P03. Maximum Width of Binary Tree");
        System.out.println("============================================================");

// Test Case 1: Basic Binary Tree
        System.out.println("Test Case 1: Basic Binary Tree");
        TreeNode rootP03 = new TreeNode(1);
        rootP03.left = new TreeNode(3);
        rootP03.right = new TreeNode(2);
        rootP03.left.left = new TreeNode(5);
        rootP03.left.right = new TreeNode(3);
        rootP03.right.right = new TreeNode(9);
//        TreeVisualizer.printTree(rootP03);
        System.out.println("Input: [1,3,2,5,3,null,9] => Output: " + makeItBold(widthOfBinaryTree(rootP03)+"") + " ,Expected: 4");

// Test Case 2: Wider Tree
        System.out.println("\nTest Case 2: Wider Tree");
        rootP03 = new TreeNode(1);
        rootP03.left = new TreeNode(3);
        rootP03.right = new TreeNode(2);
        rootP03.left.left = new TreeNode(5);
        rootP03.right.right = new TreeNode(9);
        rootP03.left.left.left = new TreeNode(6);
        rootP03.right.right.left = new TreeNode(7);
//        TreeVisualizer.printTree(rootP03);
        System.out.println("Input: [1,3,2,5,null,null,9,6,null,7] => Output: " + makeItBold(widthOfBinaryTree(rootP03)+"") + " ,Expected: 7");

// Test Case 3: Single Node
        System.out.println("\nTest Case 3: Single Node");
        rootP03 = new TreeNode(1);
//        TreeVisualizer.printTree(rootP03);
        System.out.println("Input: [1] => Output: " + makeItBold(widthOfBinaryTree(rootP03)+"") + " ,Expected: 1");

// Test Case 4: Balanced Tree
        System.out.println("\nTest Case 4: Balanced Tree");
        rootP03 = new TreeNode(1);
        rootP03.left = new TreeNode(2);
        rootP03.right = new TreeNode(3);
        rootP03.left.left = new TreeNode(4);
        rootP03.left.right = new TreeNode(5);
        rootP03.right.left = new TreeNode(6);
        rootP03.right.right = new TreeNode(7);
//        TreeVisualizer.printTree(rootP03);
        System.out.println("Input: [1,2,3,4,5,6,7] => Output: " + makeItBold(widthOfBinaryTree(rootP03)+"") + " ,Expected: 4");

        System.out.println("============================================================");
        System.out.println("P04. Maximum Level Sum of a Binary Tree");
        System.out.println("============================================================");

        // Test Case 1: Basic Binary Tree
        TreeNode rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(7);
        rootP04.right = new TreeNode(0);
        rootP04.left.left = new TreeNode(7);
        rootP04.left.right = new TreeNode(-8);
//        TreeVisualizer.printTree(rootP04);
        System.out.println("Input: [1,7,0,7,-8,null,null] => Output: " + makeItBold(maxLevelWithHighestSum(rootP04) + "") + " ,Expected: 2");

        // Test Case 2: Complex Tree with Negatives
        rootP04 = new TreeNode(989);
        rootP04.right = new TreeNode(10250);
        rootP04.right.left = new TreeNode(98693);
        rootP04.right.right = new TreeNode(-89388);
        rootP04.right.left.right = new TreeNode(-32127);
//        TreeVisualizer.printTree(rootP04);
        System.out.println("Input: [989,null,10250,98693,-89388,null,null,null,-32127] => Output: " + makeItBold(maxLevelWithHighestSum(rootP04) + "") + " ,Expected: 2");

        // Test Case 3: Single Node
        rootP04 = new TreeNode(1);
//        TreeVisualizer.printTree(rootP04);
        System.out.println("Input: [1] => Output: " + makeItBold(maxLevelWithHighestSum(rootP04) + "") + " ,Expected: 1");

        // Test Case 4: Balanced Tree
        rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(2);
        rootP04.right = new TreeNode(3);
        rootP04.left.left = new TreeNode(4);
        rootP04.left.right = new TreeNode(5);
        rootP04.right.left = new TreeNode(6);
        rootP04.right.right = new TreeNode(7);
//        TreeVisualizer.printTree(rootP04);
        System.out.println("Input: [1,2,3,4,5,6,7] => Output: " + makeItBold(maxLevelWithHighestSum(rootP04) + "") + " ,Expected: 3");
        System.out.println("============================================================");
        System.out.println("P06. Even Odd Tree");
        System.out.println("============================================================");

        // Test Case 1: Valid Even Odd Tree
        System.out.println("Test Case 1: Valid Even Odd Tree");
        TreeNode rootP06 = new TreeNode(1);


        rootP06.left = new TreeNode(10);
        rootP06.right = new TreeNode(4);

        rootP06.left.left = new TreeNode(3);
        rootP06.left.right = new TreeNode(5);
        rootP06.right.left = new TreeNode(7);
        rootP06.right.right = new TreeNode(9);
        //TreeVisualizer.printTree(rootP06);
        System.out.println("Input: [1,10,4,3,null,7,6,2,null,6,2,null] => Output: " + makeItBold(isEvenOddTree(rootP06) + "") + " ,Expected: true");

        // Test Case 2: Invalid - Not Strictly Increasing
        System.out.println("\nTest Case 2: Invalid - Not Strictly Increasing");
        rootP06 = new TreeNode(5);
        rootP06.left = new TreeNode(4);
        rootP06.right = new TreeNode(2);
        rootP06.left.left = new TreeNode(3);
        rootP06.left.right = new TreeNode(3);
        rootP06.right.left = new TreeNode(7);
        //TreeVisualizer.printTree(rootP06);
        System.out.println("Input: [5,4,2,3,3,7] => Output: " + makeItBold(isEvenOddTree(rootP06) + "") + " ,Expected: false");

        // Test Case 3: Invalid - Even Value at Even Level
        System.out.println("\nTest Case 3: Invalid - Even Value at Even Level");
        rootP06 = new TreeNode(2);
        rootP06.left = new TreeNode(1);
        rootP06.right = new TreeNode(2);
        rootP06.left.left = new TreeNode(10);
        rootP06.left.right = new TreeNode(12);
        rootP06.right.left = new TreeNode(6);
        rootP06.right.right = new TreeNode(12);
        rootP06.left.left.left = new TreeNode(1);
        rootP06.left.left.right = new TreeNode(1);
        rootP06.left.right.left = new TreeNode(1);
        rootP06.left.right.right = new TreeNode(1);
        ////TreeVisualizer.printTree(rootP06);
        System.out.println("Input: [2,1,2,10,12,6,12,1,1,1,1] => Output: " + makeItBold(isEvenOddTree(rootP06) + "") + " ,Expected: false");

        // Test Case 4: Single Node Valid
        System.out.println("\nTest Case 4: Single Node Valid");
        rootP06 = new TreeNode(1);
        ////TreeVisualizer.printTree(rootP06);
        System.out.println("Input: [1] => Output: " + makeItBold(isEvenOddTree(rootP06) + "") + " ,Expected: true");




    }
}

