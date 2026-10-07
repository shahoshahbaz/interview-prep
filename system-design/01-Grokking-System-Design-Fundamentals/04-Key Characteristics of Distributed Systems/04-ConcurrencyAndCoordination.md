# Concurrency and Coordination

## Definition
Managing multiple processes working together to ensure correct operation and data consistency.

## Key Concepts
- **Concurrency Control:** Mechanisms to manage simultaneous access to shared resources.
- **Synchronization:** Techniques to coordinate timing and order of operations.
- **Coordination Services:** Tools for distributed configuration, leader election, and locking.
- **Consistency Models:** Rules for data visibility and correctness across nodes.

### Concurrency Control Mechanisms
- **Locks:** Restrict access to resources, ensuring only one process/thread can use them at a time (e.g., mutexes, read-write locks).
- **Optimistic Concurrency Control:** Assumes conflicts are rare; allows concurrent access and checks for conflicts before committing, rolling back if necessary.
- **Transactional Memory:** Groups operations into atomic transactions, retrying or rolling back on conflicts to maintain consistency.

### Synchronization Mechanisms
- **Barriers:** Ensure multiple processes/threads reach a certain point before proceeding, useful for phased computations.
- **Semaphores:** Counting signals that control access to resources, allowing limited concurrent access.
- **Condition Variables:** Enable threads to wait for specific conditions, often used with locks.

### Concurrency Control vs. Synchronization
- **Concurrency Control:** Focuses on managing access to shared resources to prevent conflicts and ensure data integrity.
- **Synchronization:** Coordinates the timing and order of operations among processes/threads.
 #### In another words: 
- Concurrency Control in distributed systems is about managing access to shared resources to prevent conflicts and ensure data integrity. 
 It deals with how multiple processes or nodes can safely read and write data, often using mechanisms like locks, transactions, or optimistic concurrency.
- Synchronization is about coordinating the timing and order of operations among processes or nodes. 
 It ensures that certain actions happen in a specific sequence or that processes wait for each other at certain points (using barriers, semaphores, etc.).


### Consistency Models
Consistency models define how distributed systems maintain data consistency across nodes, balancing trade-offs between consistency, availability, and performance:

- **Strong Consistency:** Immediate visibility of writes. Example: MySQL, PostgreSQL.
- **Eventual Consistency:** Updates propagate over time; all accesses eventually return the last updated value. Example: DynamoDB.
- **Causal Consistency:** Ensures causally related operations are seen in the same order by all nodes. Example: Social media posts and comments.
- **Read-Your-Writes Consistency:** Guarantees a client sees their updates in subsequent reads. Example: User profile updates.
- **Session Consistency:** Ensures read-your-writes within a session. Example: Shopping carts in e-commerce.
- **Sequential Consistency:** All operations are seen in the same order by all nodes, though not in real time. Example: Distributed logging systems.
- **Monotonic Read Consistency:** Prevents older values from being read after a newer value. Example: Flight status tracking apps.
- **Linearizability:** The strongest model, ensuring atomic operations instantly visible to all nodes. Example: Distributed key-value stores.

The choice of consistency model depends on application requirements and the nature of the data being managed.

### Summary:
Concurrency Control: Prevents data conflicts and maintains consistency when multiple entities access shared resources.
Synchronization: Coordinates when and in what order operations happen across processes or nodes.

