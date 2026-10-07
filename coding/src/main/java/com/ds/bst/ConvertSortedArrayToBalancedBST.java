package com.ds.bst;

public class ConvertSortedArrayToBalancedBST  extends BinarySearchTree{

    public Node sortedArrayToBST(int[] nums, int left, int right){
        if (left> right) return null;

        int mid = (right + left)/2;

        Node node = new Node(nums[mid]);
        node.left = sortedArrayToBST(nums,left, mid-1);
        node.right = sortedArrayToBST(nums, mid+1 , right);
        return node;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        ConvertSortedArrayToBalancedBST bst = new ConvertSortedArrayToBalancedBST();
        bst.root = bst.sortedArrayToBST(nums, 0, nums.length-1);
        bst.printTree();
    }


}
