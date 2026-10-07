# Introduction to Bloom Filters

Bloom filters are probabilistic data structures designed for efficient membership testing. They are widely used in scenarios where space and speed are critical, and occasional false positives are acceptable.

## Why Use Multiple Hash Functions?
Each hash function provides independent "labels" or positions in the bit array, spreading the influence of each item across the array. This redundancy ensures:
- **No False Negatives**: An item cannot lose all its labels unless explicitly removed.
- **False Positives**: Overlapping positions may cause false positives when unrelated items fill all required bits.

## How Bloom Filters Work
### Step-by-Step Process
1. **Initialize**: Start with a bit array of length N, all bits set to 0. Decide on k independent hash functions.
2. **Adding an Item**: Hash the item using k functions to get k indices. Set the bits at these positions to 1.
3. **Querying an Item**:
   - If any bit is 0, the item is definitely not in the set.
   - If all bits are 1, the item is probably in the set (with a chance of false positives).

### False Positives
False positives occur when unrelated items fill all required bits for a query item. To mitigate this:
- Increase the bit array size.
- Optimize the number of hash functions (k).

### How Bloom Filters Work (Step by Step)
Below diagram shows an example Bloom filter for a set containing three items (P, Q, R). Each item is hashed by three hash functions (indicated by different colored arrows) to specific positions in the bit array, which are then set to 1. For instance, item “P” sets three bits (the red arrows). To query a new item, you hash it and check the corresponding bit positions; if any required bit is 0, the item is definitely not in the set, but if all are 1, the item is probably in the set. For example, the item "X" is not present as it points to a "0" bit.

![Bloom Filter Using 3 Hash Functions](bloomFilters.png)

### Removing Items
Standard Bloom filters do not support removals. Variants like counting Bloom filters allow deletions but require more memory.

## Advantages
- **Space Efficiency**: Uses minimal memory compared to other data structures.
- **Speed**: Operations are O(k), independent of the number of stored items.

## Applications
Bloom filters are ideal for scenarios where:
- False positives are tolerable.
- Quick membership testing is required.

![Bloom Filters](bloomFilters.png)
