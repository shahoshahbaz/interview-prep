package com.grokingcodeinterview.pattern13TreeDepthFirstSearch;

import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode;
import com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeVisualizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.Utility.makeItBold;
import static com.grokingcodeinterview.pattern12TreeLevelOrderTraversal.TreeNode.buildTree;

public class pattern14TreeDepthFirstSearchR1 {
    /*
        Problem Statement
        Given a binary tree, find the length of its diameter.
        The diameter of a tree is the number of nodes on the longest path between any two leaf nodes.
        The diameter of a tree may or may not pass through the root.

        example 1:
        Input: root = [1, 2, 3, 4, 5] output: 4
        Explanation: The longest path between any two leaf nodes is 4, which is the path [4, 2, 1, 3] or [5, 2, 1, 3].
        example 2:
        Input: root = [1, 2, null, 3, null, 4, null] output: 4
        Explanation: The longest path between any two leaf nodes is 4, which is the path [3, 2, 1, 4].
        example 3: Input: root = [1, 2, 3, 4, 5, 6, 7] output: 5
        Explanation: The longest path between any two leaf nodes is 5, which is the path [4, 2, 1, 3, 7] or [5, 2, 1, 3, 6].
        Note: You can always assume that there are at least two leaf nodes in the given tree.
        Constraints:

        n == edges.length + 1
        1 <= n <= 104
        0 <= ai, bi < n
        ai != bi
 */

    /**
     * [1, 2, 3, 4, 5] output: 4
     *
     *          [1,
     *         2, 3,
     *       4, 5]
     *  [1, 2, null, 3, null, 4, null]
     *          [1,
     *        2, null,
     *      3, null,
     *     4, null]
     */

    public static int lengthOfDiameter(TreeNode root){
        int[] diameter = new int[1];
        height(root, diameter);
        return diameter[0];
    }

    public static int height(TreeNode curr, int[] diameter){
        if(curr == null) return 0;

        int heightLeft = height(curr.left, diameter);
        int heightRight = height(curr.right, diameter);
        diameter[0] = Math.max(diameter[0],  heightLeft+ heightRight +1) ;
        return Math.max(heightLeft, heightRight) +1;

    }

    /*
     * Given a binary tree, return all root-to-leaf paths.
     * Example: [1, 2, 3, null, 5] output [[1,2,5], [1, 3]]
     * Example: [10, 5, 12, 4, 7, null, 15] output [  [10, 5, 4],[10, 5, 7],[10, 12, 15]]
     */
    public  static List<List<Integer>> findAllRootToPath(TreeNode root){
        ArrayList<List<Integer>> paths = new ArrayList<>();
        findAllRootToPath(root, new ArrayList<>(),paths);
        return paths;
    }
    public static  void findAllRootToPath(TreeNode root, ArrayList<Integer> currPath,  ArrayList<List<Integer>> paths){
        if (root == null) {

            return;
        }
        currPath.add(root.val);
        if (root.left == null && root.right == null){

            paths.add(new ArrayList<>(currPath));
        }

        findAllRootToPath(root.left, currPath,  paths);
        findAllRootToPath(root.right, currPath,paths);
        currPath.remove(currPath.size() -1);
    }
     /*
    Count Paths for a Sum (medium)
    Given a binary tree and a number â€˜Sâ€™,
     find all paths in the tree such that the sum of all the node values of each path equals â€˜Sâ€™.
     Please note that the paths can start or end at any node but all paths must follow direction from parent to child (top to bottom).
     Example: input =[1, 7, 9,6, 5, 2,3] S = 12, output= 3, Explantion: There are 3 path with sum '12': 7->5, 1-> 9->2, 9->3
     Example 2: input = [5, 3, 8, 2, 4, 6, 10], S = 11, output = 2  Explanation: Paths are 5->3->3, 3->8
     Example 3: input = [10, 5, -3, 3, 2, 11, 3, -2, 1], S = 8, output = 2  Explanation: Paths are 10->-3->1, 5->3, 3->5
     **/

    /*
                    5,
                  3,    8,
                2, 4,   6, 10] sum :11
     */

    public static int countPaths(TreeNode root, int sum){

        return countPath(root, sum, new ArrayList<>());

    }

    private static int countPath(TreeNode curr, int sum , List<Integer> currPath){

        if (curr == null) return 0;

        currPath.add(curr.val);

        int counter =0;
        int totalSum =0;
        for (int i=currPath.size() -1; i>=0 ; i--){
            totalSum += currPath.get(i);
            if(totalSum == sum)
                counter++;
        }
        int leftCounter = countPath(curr.left, sum, currPath);
        int rightCounter = countPath(curr.right, sum, currPath);
        currPath.remove(currPath.size() -1);
        return counter + leftCounter + rightCounter;
    }

    /*
    Given a binary tree and a number sequence,
    find if the sequence is present as a root-to-leaf path in the given tree.
     Example: Input: root = [1, 7, 9, null, null, 2, 9], sequence = [1, 9, 9]
     Output: true
     Explanation: The path 1 -> 9 -> 9 exists in the tree and matches the sequence.
     Constraints:
     1 <= arr.length <= 5000
     0 <= arr[i] <= 9
    Each node's value is between [0 - 9].
 */

    /**
     * [1, 7, 9, null, null, 2, 9], sequence = [1, 9, 9]
     *     1
     *   /  \
     *   7    9
     *       / \
     *      2   9
     */

    public static boolean findPath(TreeNode root, int[] nums){
        if (root == null) return false;

        int level =0;
        return findPath(root, nums, 0);
    }
    private static boolean findPath(TreeNode root, int[] nums, int level){

        if (root == null || level>= nums.length ||
                nums[level] !=  root.val
        )
            return false;

        if (root.left == null && root.right == null
                && level == nums.length -1) return true;
        return findPath(root.left, nums, level+1) ||
                findPath(root.right, nums, level +1);
         }
    /*
 Given a binary tree where each node can only have a digit (0-9) value, each root-to-leaf path will represent a number. Find the total sum of all the numbers represented by all paths.
 *Example 2: Input: [4, 9, 0, 5, 1]Paths: 495, 491, 40O output: 1026 (495 + 491 + 40)
  Example 3 Input: [1, null, 5]Paths: 15 Output: 15
  Example 4 Input: [0, 1, 3, 5, null, 6, 9]Paths: 015, 036, 039 Output: 90 (15 + 36 + 39)
  Example 5 Input: [9, 8, 7, 6, 5]Paths: 986, 985, 97 Output: 2068 (986 + 985 + 97)
 * */
    public static int sumOfPathNumbers(TreeNode root){
        if (root == null) return 0;
        int sumOfPath =0;
        return sumOfPathNumbers(root, sumOfPath);
    }

    private static int sumOfPathNumbers(TreeNode root, int sumOfPath){
        if (root == null) return 0;

        sumOfPath = sumOfPath *10 + root.val;
        if (root.left == null && root.right == null)
            return sumOfPath ;

        return   sumOfPathNumbers(root.left, sumOfPath ) +
                sumOfPathNumbers(root.right, sumOfPath );

    }
   /*
 problem statement:
 Given a root of the binary tree and an integer â€˜Sâ€™, return true if the tree has a path from root-to-leaf such that the sum of all the node values of that path equals â€˜Sâ€™. Otherwise, return false.
 Examples
 Example 1:
 Input: root = [1, 2, 3, 4, 5, 6, 7], S = 10
 Expected Output: true
 Justification: The tree has 1 -> 3 -> 6 root-to-leaf path having sum equal to 10.
 Example 2:
 Input: root = [12, 7, 1, 9, null, 10, 5], S = 23
 Expected Output: true
 Justification: The tree has 12 -> 1 -> 10 root-to-leaf path having sum equal to 23.
 */
public static boolean hasPath(TreeNode root, int sum){
    if (root == null  ) return false;
    if(sum < 0) return false;

    if(root.left == null && root.right == null && root.val == sum){
        return true;
    }
    return hasPath(root.left, sum - root.val )|| hasPath(root.right, sum- root.val);


}
    /*
 Problem Statement
 Given the root of a binary tree, explore all possible root-to-leaf paths
 , compute the sum of values along each path, and return the minimum sum.
 *
 A leaf node is a node with no children.

 Example 1:
 Input: root = [10, 5, 15, null, null, 7, 20] Expected Output: 15
 Justification: The path with the minimum sum is 10 -> 5. The sum is 10 + 5 = 15.
 */

    /**
     * root =1
     */
    public static int minRootToLeafSum(TreeNode root){
        if (root == null) return Integer.MAX_VALUE;

        if (root.left == null && root.right == null)
            return root.val;

        int left = minRootToLeafSum(root.left);
        int right = minRootToLeafSum(root.right);

        return root.val + Math.min(left, right );
    }

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("P01. P01MinimumSumFromRootToLeaf ");
        System.out.println("======================================");

        // Example 1: root = [10, 5, 15, null, null, 7, 20]
        TreeNode rootP01 = new TreeNode(10);
        rootP01.left = new TreeNode(5);
        rootP01.right = new TreeNode(15);
        rootP01.right.left = new TreeNode(7);
        rootP01.right.right = new TreeNode(20);
        TreeVisualizer.printTree(rootP01);
        System.out.println("Input: " + rootP01.toLevelOrderString() + " ,Output: " + makeItBold(minRootToLeafSum(rootP01) +"") + " Expected: 15");
        rootP01 = new TreeNode(7);
        System.out.println("Input: " + rootP01.toLevelOrderString() + " ,Output: " + makeItBold(minRootToLeafSum(rootP01) +"") + " Expected: 7");

        rootP01 = null;
        System.out.println("Input: " + rootP01 + " ,Output: " + makeItBold(minRootToLeafSum(rootP01) +"") + " Expected: " + Integer.MAX_VALUE);

        // Example 3: Left-skewed tree [5, 4, null, 3, null, 2, null, 1]
        rootP01 = new TreeNode(5);
        rootP01.left = new TreeNode(4);
        rootP01.left.left = new TreeNode(3);
        rootP01.left.left.left = new TreeNode(2);
        rootP01.left.left.left.left = new TreeNode(1);
        System.out.println("Input: " + rootP01.toLevelOrderString() + " ,Output: " + makeItBold(minRootToLeafSum(rootP01) +"") + " Expected: 15");
        // Example 4: Right-skewed tree [1, null, 2, null, 3, null, 4]
        rootP01 = new TreeNode(1);
        rootP01.right = new TreeNode(2);
        rootP01.right.right = new TreeNode(3);
        rootP01.right.right.right = new TreeNode(4);

        System.out.println("Input: " + rootP01.toLevelOrderString() + " ,Output: " + makeItBold(minRootToLeafSum(rootP01) +"") + " Expected: 10");

        // Example 5: Tree with negative values [2, -1, 3, null, null, -4, 5]
        rootP01 = new TreeNode(2);
        rootP01.left = new TreeNode(-1);
        rootP01.right = new TreeNode(3);
        rootP01.right.left = new TreeNode(-4);
        rootP01.right.right = new TreeNode(5);
        System.out.println("Input: " + rootP01.toLevelOrderString() + " ,Output: " + makeItBold(minRootToLeafSum(rootP01) +"") + " Expected: 1");

        System.out.println("=====================================");
        System.out.println("P02. Binary Tree Path Sum");
        System.out.println("=====================================");
        TreeNode rootP02 = new TreeNode(1);
        rootP02.left = new TreeNode(2);
        rootP02.right = new TreeNode(3);
        rootP02.left.left = new TreeNode(4);
        rootP02.left.right = new TreeNode(5);
        rootP02.right.left = new TreeNode(6);
        rootP02.right.right = new TreeNode(7);
        int sumP02 = 10;
        System.out.println("Input: " + rootP02.toLevelOrderString() + " ,S = " + sumP02 + " ,Output: " + makeItBold(hasPath(rootP02, sumP02) +"") + " Expected: true");

        // Example 2: root = [12, 7, 1, 9, null, 10, 5], S = 23
        rootP02 = new TreeNode(12);
        rootP02.left = new TreeNode(7);
        rootP02.right = new TreeNode(1);
        rootP02.left.left = new TreeNode(9);
        rootP02.right.left = new TreeNode(10);
        rootP02.right.right = new TreeNode(5);
        sumP02 = 23;
        System.out.println("Input: " + rootP02.toLevelOrderString() + " ,S = " + sumP02 + " ,Output: " + makeItBold(hasPath(rootP02, sumP02) +"") + " Expected: true");

        // Example 3: root = [5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1], S = 22
        rootP02 = new TreeNode(5);
        rootP02.left = new TreeNode(4);
        rootP02.right = new TreeNode(8);
        rootP02.left.left = new TreeNode(11);
        rootP02.left.left.left = new TreeNode(7);
        rootP02.left.left.right = new TreeNode(2);
        rootP02.right.left = new TreeNode(13);
        rootP02.right.right = new TreeNode(4);
        rootP02.right.right.right = new TreeNode(1);
        sumP02 = 22;
        System.out.println("Input: " + rootP02.toLevelOrderString() + " ,S = " + sumP02 + " ,Output: " + makeItBold(hasPath(rootP02, sumP02) +"") + " Expected: true");
        // give me an example where the output is false
        // Example 4: root = [1, 2, 3], S = 5
        rootP02 = new TreeNode(1);
        rootP02.left = new TreeNode(2);
        rootP02.right = new TreeNode(3);
        sumP02 = 5;
        System.out.println("Input: " + rootP02.toLevelOrderString() + " ,S = " + sumP02 + " ,Output: " + makeItBold(hasPath(rootP02, sumP02) +"") + " Expected: false");

        System.out.println("==================================================");
        System.out.println("P03. Sum of Path Numbers");
        System.out.println("==================================================");
        Integer[] nums03 = {1, 2, 3};
        TreeNode root03 = buildTree(nums03);
        int result03 = sumOfPathNumbers(root03);
        System.out.println("Input: " + Arrays.toString(nums03) +", Output: " + makeItBold(result03 +"") +", Expected Output: 25 (12 + 13)");
//        TreeNode root1 = buildTree(new Integer[]{1, 2, 3});

        nums03 = new Integer[]{4, 9, 0, 5, 1};
        root03 = buildTree(nums03);
        result03 = sumOfPathNumbers(root03);
        System.out.println("Input: " + Arrays.toString(nums03) +", Output: " + makeItBold(result03 +"") +", Expected Output: 1026 (495 + 491 + 40)");
//
        nums03 = new Integer[]{1, null, 5};
        root03 = buildTree(nums03);
        result03 = sumOfPathNumbers(root03);
        System.out.println("Input: " + Arrays.toString(nums03) +", Output: " + makeItBold(result03 +"") +", Expected Output: 15");
//
        nums03 = new Integer[]{0, 1, 3, 5, null, 6, 9};
        root03 = buildTree(nums03);
        result03 = sumOfPathNumbers(root03);
        System.out.println("Input: " + Arrays.toString(nums03) +", Output: " + makeItBold(result03 +"") +", Expected Output: 90 (15 + 36 + 39)");
//

        nums03 = new Integer[]{9, 8, 7, 6, 5};
        root03 = buildTree(nums03);
        result03 = sumOfPathNumbers(root03);
        System.out.println("Input: " + Arrays.toString(nums03) +", Output: " + makeItBold(result03 +"") +", Expected Output: 2068 (986 + 985 + 97)");

        System.out.println("==================================================");
        System.out.println("P07. Path with Given Sequence");
        System.out.println("==================================================");
        Integer[] numsP07= new Integer[]{1, 7, 9, null, null, 2, 9};
        int[] pathP07 = new int[]{1 , 9 , 9};
        TreeNode rootP07 = TreeNode.buildTree(numsP07);
//        TreeVisualizer.printTree(rootP07);
        System.out.println("Input: " + rootP07.toLevelOrderString() + " ,Sequence: " + Arrays.toString(pathP07) + " ,Output: " + makeItBold(findPath(rootP07, pathP07) +"") + " ,Expected: true");

        numsP07= new Integer[]{1, 7, 9, null, null, 2, 9};
        pathP07 = new int[]{1 , 0 , 9};
        rootP07 = TreeNode.buildTree(numsP07);
        System.out.println("Input: " + rootP07.toLevelOrderString() + " ,Sequence: " + Arrays.toString(pathP07) + " ,Output: " + makeItBold(findPath(rootP07, pathP07) +"") + " ,Expected: false");

        numsP07= new Integer[]{1, 0, 1, 1, null, 6, 5};
        pathP07 = new int[]{1 , 0 , 7};
        rootP07 = TreeNode.buildTree(numsP07);
        System.out.println("Input: " + rootP07.toLevelOrderString() + " ,Sequence: " + Arrays.toString(pathP07) + " ,Output: " + makeItBold(findPath(rootP07, pathP07) +"") + " ,Expected: false");

        numsP07= new Integer[]{1, 7, 9, null, null, 2, 9};;
        pathP07 = new int[]{1 , 7 , 9};
        rootP07 = TreeNode.buildTree(numsP07);
        System.out.println("Input: " + rootP07.toLevelOrderString() + " ,Sequence: " + Arrays.toString(pathP07) + " ,Output: " + makeItBold(findPath(rootP07, pathP07) +"") + " ,Expected: true");

        System.out.println("==========================");
        System.out.println("P05. Count Paths for a Sum");
        System.out.println("==========================");
        // Example 1
        Integer[] numsP05 = new Integer[]{1, 7, 9, 6, 5, 2, 3};
        int sumP05 = 12;
        TreeNode rootP05 = TreeNode.buildTree(numsP05);

//        TreeVisualizer.printTree(rootP05);
        int resultP05 = countPaths(rootP05, sumP05);
        System.out.println("Inputs: " + Arrays.toString(numsP05) +" sum :"+ sumP05 +  " ,output: "+
                makeItBold(resultP05+"") + ", expected outPut: 3");

        numsP05 = new Integer[]{5, 3, 8, 2, 4, 6, 10};
        sumP05 = 11;
        rootP05 = TreeNode.buildTree(numsP05);
        resultP05 = countPaths(rootP05, sumP05);
        System.out.println("Inputs: " + Arrays.toString(numsP05) +" sum :"+ sumP05 +  " ,output: "+
                makeItBold(resultP05+"") + ", expected outPut: 0");

        // Example 3
        numsP05 = new Integer[]{10, 5, -3, 3, 2, 11, 3, -2, 1};
        sumP05 = 8;
        rootP05 = TreeNode.buildTree(numsP05);
        resultP05 = countPaths(rootP05, sumP05);
        System.out.println("Inputs: " + Arrays.toString(numsP05) +" sum :"+ sumP05 +  " ,output: "+
                makeItBold(resultP05+"") + ", expected outPut: 2");



        System.out.println("==================================================");
        System.out.println("P04. All Root to Leaf Paths");
        System.out.println("==================================================");

        // Example 1: [1, 2, 3, null, 5]
        TreeNode rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(2);
        rootP04.right = new TreeNode(3);
        rootP04.left.right = new TreeNode(5);
        List<List<Integer>> listP04 = findAllRootToPath(rootP04);
        System.out.println("Input:  " + rootP04.toLevelOrderString() +"output: " +makeItBold(listP04.toString()) + ", Expected Output: [[1, 2, 5], [1, 3]]");

        // Example 2: [10, 5, 12, 4, 7, null, 15]
        rootP04 = new TreeNode(10);
        rootP04.left = new TreeNode(5);
        rootP04.right = new TreeNode(12);
        rootP04.left.left = new TreeNode(4);
        rootP04.left.right = new TreeNode(7);
        rootP04.right.right = new TreeNode(15);
        listP04 = findAllRootToPath(rootP04);
        System.out.println("Input:  " + rootP04.toLevelOrderString() +"output: " + makeItBold(listP04.toString()) + ", Expected Output: [[10, 5, 4], [10, 5, 7], [10, 12, 15]]");

        // Example 3: [1, 2, null, 3]
        rootP04 = new TreeNode(1);
        rootP04.left = new TreeNode(2);
        rootP04.left.left = new TreeNode(3);

        listP04 = findAllRootToPath(rootP04);
        System.out.println("Input:  " + rootP04.toLevelOrderString() +"output: " + makeItBold(listP04.toString()) + ", Expected Output: [[1, 2, 3]]");
        // Example 4: [1]
        rootP04 = new TreeNode(1);
        listP04 = findAllRootToPath(rootP04);
        System.out.println("Input:  " + rootP04.toLevelOrderString() +"output: " + makeItBold(listP04.toString()) + ", Expected Output: [[1]]");

        // Example 5: []
        TreeNode root5 = null;
        listP04 = findAllRootToPath(root5);
        System.out.println("Input:  " + (root5 == null ? "[]" : root5.toLevelOrderString()) +"output: " + makeItBold(listP04.toString()) + ", Expected Output: []");

        System.out.println("===========================");
        System.out.println("P09. Tree Diameter");
        System.out.println("===========================");

        TreeNode rootP09 =TreeNode.buildTree(new Integer[]{1, 2, 3, 4, 5, null, null});
//        TreeVisualizer.printTree(rootP09);
        System.out.println("Input: " + rootP09.toLevelOrderString() +", output: "+ makeItBold(lengthOfDiameter(rootP09) +"") + " Expected Output: 4");

        rootP09 = TreeNode.buildTree(new Integer[]{1, 2, null, 3, null, 4, null});
//        TreeVisualizer.printTree(rootP09);
        System.out.println("Input: " + rootP09.toLevelOrderString() +", output: "+ makeItBold(lengthOfDiameter(rootP09) +"") + " Expected Output: 4");

        rootP09 = TreeNode.buildTree(new Integer[]{1, 2, 3, 4, 5, 6, 7});
//        TreeVisualizer.printTree(rootP09);
        System.out.println("Input: " + rootP09.toLevelOrderString() +", output: "+ makeItBold(lengthOfDiameter(rootP09) +"") + " Expected Output: 5");
        rootP09 = TreeNode.buildTree(new Integer[]{1});
//        TreeVisualizer.printTree(rootP09);
        System.out.println("Input: " + rootP09.toLevelOrderString() +", output: "+ makeItBold(lengthOfDiameter(rootP09) +"") + " Expected Output: 1");
        rootP09 = TreeNode.buildTree(new Integer[]{});
//        TreeVisualizer.printTree(rootP09);
        System.out.println("Input:  []" + ", output: "+makeItBold(lengthOfDiameter(rootP09) +"") + " Expected Output: 0");




    }
}

