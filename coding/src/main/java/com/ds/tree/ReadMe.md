## Breadth-First Search (BFS) in a Binary Search Tree (BST)

### How BFS Works in a BST:

- **Start at the root**: You begin at the root node of the BST.
- **Use a queue**: Insert the root node into a queue.
- **Explore level by level**:
    - While the queue is not empty:
        1. Remove the front node from the queue.
        2. Visit that node (process it).
        3. Add its left child to the queue (if it exists).
        4. Add its right child to the queue (if it exists).
- **Repeat**: Continue removing nodes from the front of the queue and adding their children until you've visited all the nodes.

## Depth-First Search (DFS) in a Binary Search Tree (BST)

### How DFS Works in a BST:

DFS explores as far down a branch as possible before backtracking. There are three common types of DFS traversals in a BST:

1. **In-order (Left, Root, Right)**:
    - Traverse the left subtree.
    - Visit the root node.
    - Traverse the right subtree.
2. **Pre-order (Root, Left, Right)**:
    - Visit the root node.
    - Traverse the left subtree.
    - Traverse the right subtree.
3. **Post-order (Left, Right, Root)**:
    - Traverse the left subtree.
    - Traverse the right subtree.
    - Visit the root node.


### Example:

For the following BST:

```
      5
     / \
    3   7
   / \   \
  2   4   8
 ```

* BFS visits nodes in this order: 5, 3, 7, 2, 4, 8.
* In-order traversal visits nodes in this order: 2, 3, 4, 5, 7, 8.
* Pre-order traversal visits nodes in this order: 5, 3, 2, 4, 7, 8.
*   Post-order traversal visits nodes in this order: 2, 4, 3, 8, 7, 5.

