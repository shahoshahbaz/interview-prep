# Cache Replacement Policies

## 🔍 Definition
> Cache replacement policies determine which items should be evicted from a cache when it becomes full, optimizing performance based on different access patterns and priorities.

## 🧠 Key Ideas
- Cache replacement policies are essential when cache memory is limited
- Different policies make different assumptions about future data access patterns
- Trade-offs exist between implementation complexity and cache hit ratio
- The right choice depends on workload characteristics and performance requirements

## 📊 Real-world Use Case
Web browsers use LRU for page caching, database systems implement various policies for buffer management, and content delivery networks (CDNs) use sophisticated replacement policies to optimize content delivery across global networks.



### Least Recently Used (LRU)
LRU evicts the item that hasn't been accessed for the longest time. It operates on the principle that items that have been accessed recently are more likely to be accessed again in the near future (temporal locality). 

Implementation typically uses a combination of a hash map and a doubly linked list:
1. The hash map provides O(1) lookups to quickly find items in the cache
2. The doubly linked list maintains the access order of items
3. When an item is accessed, it's moved to the front of the list
4. When eviction is needed, the item at the back of the list (least recently used) is removed

LRU is one of the most widely used cache replacement policies due to its balance of implementation simplicity and effectiveness for common workloads.

#### ✅ Pros:
- Works well for temporal locality patterns
- Relatively simple to implement using a linked list and hash map
- Good general-purpose solution for many workloads

#### ❌ Cons:
- Doesn't account for access frequency
- Requires tracking access timestamps
- Can perform poorly with cyclic access patterns

### Least Frequently Used (LFU)
LFU evicts the item that has been accessed the least number of times. It keeps track of how many times each item has been requested and removes the item with the lowest access frequency when the cache is full.

Implementation typically uses:
1. A hash map for O(1) lookups
2. A counter for each item to track access frequency
3. A frequency-based data structure (often a min-heap or multiple linked lists) to efficiently identify the least frequently used item

LFU is particularly effective for workloads where certain items are consistently accessed more frequently than others, regardless of recency.

#### ✅ Pros:
- Works well when certain items are accessed very frequently
- Better hit ratios for skewed access distributions
- More resilient to one-time scans

#### ❌ Cons:
- Complex implementation with higher overhead
- Historical frequency may not predict future access
- Doesn't account for recency

### First In, First Out (FIFO)
FIFO evicts items in the order they were added to the cache, regardless of how often or how recently they were accessed. It works like a queue, where the oldest item (first one added) is the first to be removed when space is needed.

Implementation is straightforward:
1. A simple queue data structure tracks the order items were added
2. New items are added to the back of the queue
3. When eviction is needed, the item at the front of the queue (oldest) is removed

FIFO is the simplest cache replacement policy but is generally less effective than LRU or LFU because it doesn't consider usage patterns at all.

#### ✅ Pros:
- Extremely simple to implement using a queue
- Low computational overhead
- Predictable behavior

#### ❌ Cons:
- Doesn't consider access patterns at all
- Generally lower hit rates than LRU/LFU
- Poor performance for many real-world workloads

### Random Replacement
Random replacement, as the name suggests, randomly selects an item to evict when the cache is full. It makes no attempt to be strategic about which item to remove.

Implementation is extremely simple:
1. When eviction is needed, select a random item from the cache
2. Remove the randomly selected item

Despite its simplicity, random replacement can perform surprisingly well in some scenarios and avoids the overhead of tracking access patterns. It's sometimes used as a baseline for comparing other policies or in systems where minimal overhead is critical.

#### ✅ Pros:
- Extremely simple implementation
- Constant time complexity for all operations
- No additional tracking metadata required

#### ❌ Cons:
- No optimization for access patterns
- Unpredictable performance
- Generally lower hit rates than more sophisticated policies

## 💬 Interview Context
- Often discussed when designing caching layers in distributed systems
- Expected to know tradeoffs and appropriate use cases for each policy
- May be asked to implement a simple LRU cache (common coding interview question)
- Follow-up questions might include cache invalidation, consistency, and sizing strategies

## 🔁 Revisit Frequency
[ ] Once a week  
[x] Once a month  
[ ] Before interviews

---

