package com.grokingcodeinterview.pattern20TopKElements;

import java.util.PriorityQueue;

/*
Problem Statement

You are building a layout engine (like Pinterest's masonry grid) with numColumns columns,
 each starting with a load of 0. Given a stream of items, where each item has a size, assign each item to the column with the least current load. After placing an item, add its size to that column's load. If multiple columns are tied for the least load, assign to the column with the lowest column index.

Return the final load of each column after all items have been placed.

Example 1:

Input: numColumns = 3, items = [2, 3, 4, 1, 5]
Output: [3, 8, 4]

Explanation:
Initial loads: [0, 0, 0]
Item 2 â†’ column 0 (tie among all 0s, lowest index wins) â†’ loads: [2, 0, 0]
Item 3 â†’ column 1 (tie between col1=0, col2=0, lowest index wins) â†’ loads: [2, 3, 0]
Item 4 â†’ column 2 (col2=0 is lowest) â†’ loads: [2, 3, 4]
Item 1 â†’ column 0 (col0=2 is lowest) â†’ loads: [3, 3, 4]
Item 5 â†’ column 1 (tie between col0=3, col1=3, lowest index wins) â†’ loads: [3, 8, 4]

Example 2:

Input: numColumns = 2, items = [5, 5, 5]
Output: [10, 5]

Explanation:
Item 5 â†’ column 0 (tie, lowest index) â†’ loads: [5, 0]
Item 5 â†’ column 1 (col1=0 is lowest) â†’ loads: [5, 5]
Item 5 â†’ column 0 (tie, lowest index) â†’ loads: [10, 5]

Constraints

1 <= numColumns <= 10^4
1 <= items.length <= 10^5
1 <= items[i] <= 10^4
 */
public class P15ColumnAllocator {

    public static class Column{
        int index;
        int currLoad;

        public Column(int index, int currLoad){
            this.currLoad = currLoad;
            this. index = index;

        }

    }
    public static int[] allocateColumns(int numColumns, int[] items) {

        PriorityQueue<Column> minHeap = new PriorityQueue<>(
                (a,b) -> a.currLoad != b.currLoad ?a.currLoad - b.currLoad: a.index - b.index);

        // Intialize all Coulmns with load 0
        for(int i =0; i< numColumns; i++){
            minHeap.offer(new Column (i, 0));
        }

        for(int size: items){
            Column col = minHeap.poll();
            col.currLoad+= size;
            minHeap.offer(col);
        }

        int[] result = new int[numColumns];
        while (!minHeap.isEmpty()){
            Column col = minHeap.poll();
            result[col.index] = col.currLoad;

        }
        return result;

    }
}

