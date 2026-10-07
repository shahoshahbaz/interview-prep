# Indexes

## What are Indexes?
Indexes are essential for optimizing database performance, especially when search queries become slow. They are data structures that store column values and pointers to rows, enabling faster data retrieval and efficient access to ordered records.

### Example: Library Catalog
A library catalog is analogous to a database index. It organizes books by title or writer, allowing quick searches based on specific criteria. Similarly, database indexes provide sorted lists of data for rapid lookups.

### Larger Datasets
Indexes are crucial for large datasets, often spanning terabytes and distributed across multiple devices. They help locate small payloads efficiently without iterating over vast amounts of data.

## Purpose of Database Indexes
- **Faster Data Retrieval**: Speeds up query execution by reducing disk I/O and CPU usage.
- **Sorting and Ordering**: Enables quick sorting and ordering of data for reporting or display.

## How Indexes Improve Query Performance
- **Reduced Table Scans**: Avoids full table scans by directly accessing indexed columns.
- **Efficient Data Access**: Organizes data for quick location of rows meeting query criteria.
- **Index Selectivity**: High selectivity filters out unnecessary rows, improving query efficiency.

## How Indexes Decrease Write Performance
While indexes enhance read performance, they can slow down write operations (INSERT, UPDATE, DELETE) due to the need for index updates. This trade-off requires careful consideration:
- Avoid unnecessary indexes.
- Remove unused indexes.

### Summary
Indexes improve search query performance but may degrade write performance. For write-intensive databases, minimizing indexes is advisable to prioritize write efficiency.
