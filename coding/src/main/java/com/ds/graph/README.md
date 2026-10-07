# Graph Data Structure Implementation

This package contains implementations of graph data structures using different approaches.

## Graph Implementations

We have implemented two primary graph representations:

1. **MapGraph**: A flexible implementation that uses a `Map<T, List<T>>` to store adjacency lists
2. **ArrayListGraph**: An implementation using arrays of lists, optimized for integer vertices


### Graph Representations
Graphs can be represented in multiple ways depending on the use case. The two most common methods are:

#### 1. Adjacency Matrix
- A 2D array (or matrix) used to represent the presence or absence of edges between vertices.
- The matrix is of size N × N, where N is the number of vertices.
- Each cell (i, j) indicates whether there's an edge from vertex i to vertex j.

#### 2. Adjacency List
- Stores each vertex alongside a list of its neighbors.
- Space-efficient for sparse graphs (graphs with fewer edges).
- Each node maps to a list of connected nodes.
- Commonly implemented using arrays or hash maps with linked lists or dynamic arrays.

## Implementation Choices

### 1️⃣ LinkedList<Integer>[] adjacencyList

#### Most common in:
- Competitive programming
- Algorithm textbooks

#### Cases where:
- The graph's vertex set is fixed as integers 0..n-1
- You know the vertex count beforehand

#### Why:
- It's simple and memory-efficient (an array of lists is contiguous and direct-indexed)
- Index access is O(1) without hashing
- Slightly faster because no map lookups

#### Trade-offs:
- Only works easily when vertex IDs are small integers
- Changing vertex labels (e.g., using String, UUID) requires an extra mapping layer

### 2️⃣ Map<T, List<T>> adjacentList

#### Most common in:
- Production code
- Graph libraries / frameworks

#### cases where:
- Node IDs are not fixed or not integers
- Graph is dynamic (adding/removing vertices often)

#### Why:
- Works with any vertex type (String, custom class, etc.)
- Can handle sparse, irregular graphs naturally
- Easier to extend with additional metadata

#### Trade-offs:
- Slightly more overhead (hashing keys)
- More verbose than array-based version

## 💡 Rule of thumb:
- If your graph is small, fixed, integer-indexed, and speed matters → use List<Integer>[].
- If you need flexibility, generic vertex types, or dynamic graphs → use Map<T, List<T>>.

