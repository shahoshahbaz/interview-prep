package com.grokingcodeinterview.pattern12TreeLevelOrderTraversal;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.Utility.makeItBold;

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

public class P03MaximumWidthOfBinaryTree {
    private static final boolean DEBUG = true;
    static class Pair{
        TreeNode node;
        long index; // use long to avoid overflow
        public Pair(TreeNode node, long index){
            this.node = node;
            this.index = index;
        }
    }

    /**
     Tree Structure:
          1(0)
        /    \
     3(1)   2(2)
     /  \      \
   5(3) 3(4)   9(6)
     dry-run example
     maxWidth = 0, queue = [(1,0)]
     LEVEL 0: queue: [(1, 0)] levelSize = 1, minIndex = queue.peek().index = 0
        i=0: curr=(1,0), idx= curr.index - minIndex = 0-0=0, first=0, last=0 => Add children: (3, 1), (2, 2)=> queue: [(3,1), (2,2)] =>
     out of the for loop
     maxWidth = max(0, 0-0+1) = 1
     LEVEL 1:  queue: [(3,1), (2,2)]   levelSize = 2, minIndex = queue.peek().index =1
       i = 0: curr=(3,1), idx=curr.index - minIndex = 1-1=0, first=0, last=0 => Add children: (5, 3), (3, 4) queue: [(2,2), (5,3), (3,4)]
       i = 1: curr=(2,2), idx=curr.index - minIndex = 2-1=1, last=1  => add children: no left, (9,6) => queue: [(5,3), (3,4), (9,6)]
        out of the for loop
     maxWidth = max(1, 1-0+1) = 2
     LEVEL 2: queue: [(5,3), (3,4), (9,6)] levelSize = 3, minIndex = queue.peek().index =3
        i = 0: curr=(5,3), idx=curr.index - minIndex = 3-3=0, first=0, last=0 => No children
        i = 1: curr=(3,4), idx=curr.index - minIndex = 4-3=1 => No children
        i = 2: curr=(9,6), idx=curr.index - minIndex = 6-3=3, last=3 => No children
        out of the for loop
     maxWidth = max(2, 3-0+1) = 4
        queue: [] then exit while loop and return maxWidth = 4


     *
     */
    public static int widthOfBinaryTree(TreeNode root) {

        if (root == null) return 0;
        Deque<Pair> queue = new ArrayDeque<>();
        queue.offer(new Pair(root, 0));

        int maxWidth = 0;
        while (!queue.isEmpty()){
            int levelSize = queue.size();
            long minIndex = queue.peek().index; // the index of the leftmost node at the current level, minIndex anchors the level, and normalization keeps indices small while preserving correct width.
            long first=0;
            long last = 0;

            for (int i =0; i< levelSize; i++){
                Pair curr = queue.poll();
                long idx = curr.index - minIndex; // why currIndex - minIndex? to prevent overflow and keep indices small and
                // why not just curr.index? because indices can grow exponentially with tree depth,
                // leading to potential overflow issues. By normalizing indices at each level, we ensure they remain manageable
                // while still accurately representing their relative positions within that level. could we use 0 instead of minIndex? no,
                // because we need to maintain the relative positions of nodes within the level to calculate width correctly.
                //but we now the levelSize,can we use levelSize instead of minIndex? no, because levelSize is just the count of nodes at that level,
                // it doesn't represent the position of the leftmost node, which is crucial for width calculation. and some nodes could be missing in between. becuase it migth be null nodes in between.
                if (i == 0) first = idx;
                if (i == levelSize -1) last = idx;

                if (curr.node.left != null)
                    queue.offer(new Pair (curr.node.left, 2 * idx + 1));
                if (curr.node.right != null)
                    queue.offer(new Pair(curr.node.right, 2* idx +2));
            }

            maxWidth = Math.max(maxWidth, (int)(last - first +1));


        }

        return maxWidth;

    }

    public static void main(String[] args) {
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
        TreeVisualizer.printTree(rootP03);
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
        TreeVisualizer.printTree(rootP03);
        System.out.println("Input: [1,3,2,5,null,null,9,6,null,7] => Output: " + makeItBold(widthOfBinaryTree(rootP03)+"") + " ,Expected: 7");

// Test Case 3: Single Node
        System.out.println("\nTest Case 3: Single Node");
        rootP03 = new TreeNode(1);
        TreeVisualizer.printTree(rootP03);
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
        TreeVisualizer.printTree(rootP03);
        System.out.println("Input: [1,2,3,4,5,6,7] => Output: " + makeItBold(widthOfBinaryTree(rootP03)+"") + " ,Expected: 4");

    }
}

