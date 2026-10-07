package com.ds.heap;

import java.util.ArrayList;

public class Heap {

    ArrayList<Integer> heap = new ArrayList<>();
    /**
     *  NOTE: The root node is at index 0.
     *  **/

    /**
     *Parent-child relationships in a binary heap:
     * If a node is at index pos, and if root node at index 0, then its left child is at 2 * pos +1
     *
     */
    public int getLeftChildIndex(int pos){
        return 2 * pos +1;
    }

    /**
     *Parent-child relationships in a binary heap:
     * If a node is at index pos,  and if root node at index 0, its right child is at 2 * pos + 2.
     *
     */
    public int getRightChildIndex(int pos){
        return 2 * pos + 2;
    }


    /**If you know the position of a node, you can find its parent at (pos-1) / 2. **/
    public int getParentIndex(int pos){
        return (pos-1)/2;
    }

    private void swap(int index1, int index2){

        int temp = heap.get(index1);// get element at index1
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);

    }
    public void insert(int value){
        this.heap.add(value);//The new element is added at the end of the heap (represented by an ArrayList).
        int current = heap.size() -1; // index of last element that we just added

        /**
         * Bubbling Up Mechanism:
         * Compare the newly inserted element with its parent:
         *      The parent of the node at index current is at parent(current).
         * Swap the current node with its parent if the current node is greater:
         *      If the new element is larger than its parent, they are swapped.
         *      This is done using the swap(current, parent(current)) method.
         * Move upwards in the heap:
         *      After swapping, the index of the current node is updated to be the index of its parent (current = parent(current)), and the process continues.
         *      This repeats until the new element is no longer greater than its parent, or it reaches the root of the heap (index 1).
         */
        while(current > 0 && heap.get(current)> heap.get(getParentIndex(current))){
            swap(current, getParentIndex(current));
            current = getParentIndex(current);
        }

    }


    /**
     * Explanation:
     * Position of Leaf Nodes:
     *
     * In a binary heap, the first half of the elements represent non-leaf nodes (nodes with children), and the second half represents the leaf nodes (nodes without children).
     * If the heap has n elements, the leaf nodes start from position n/2 to n-1.
     * The condition pos >= (heap.size() / 2) checks:
     *      This ensures that the node is located in the second half of the array. Nodes in the second half are leaf nodes because they do not have any children.
     *      Example: If the heap has 10 elements, the leaf nodes start from position 5 (i.e., n/2 = 10/2 = 5). So, any node at or beyond this position is a leaf node.
     * The condition pos < heap.size() checks:
     *      This ensures that the node is within the valid range of the heap (not beyond the last element). The method is ensuring that the position is less than the current size of the heap.

     */
    public boolean isLeaf(int pos){
        return pos>= (heap.size()/2) && pos< heap.size();
    }

    public Integer remove (int index){
        if (heap.size() == 0){
            return  null;
        }
        if (heap.size() ==1){
            return heap.remove(0);
        }

        int maxValue = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        sinkDown(0);

        return maxValue;


    }
    public void sinkDown(int index){
        int maxIndex = index;


        while(true){

            int leftIndex = getLeftChildIndex(index);
            int rightIndex = getRightChildIndex(index);

            if(leftIndex< heap.size() && heap.get(leftIndex)>heap.get(maxIndex)){
                maxIndex = leftIndex;
            }
            if (rightIndex < heap.size() && heap.get(rightIndex)> heap.get(maxIndex)){
                maxIndex = leftIndex;

            }

            if (maxIndex != index){
                swap(index, maxIndex);
                index = maxIndex;
            }else{
                return;
            }

        }



    }

    // Print the heap
    public void printHeap() {
        for (int i = 0; i < heap.size(); i++) {
            System.out.print(heap.get(i) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Heap myHeap = new Heap();

        myHeap.insert(99);
        myHeap.insert(72);
        myHeap.insert(61);
        myHeap.insert(58);

        myHeap.printHeap();
        System.out.println("Adding another number: 100....");
        myHeap.insert(100);

        myHeap.printHeap();


    }



}
