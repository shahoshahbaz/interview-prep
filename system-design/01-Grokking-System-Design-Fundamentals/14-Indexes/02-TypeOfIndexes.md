# Types of Indexes

Database indexes are designed to improve the speed and efficiency of data retrieval operations. They function by maintaining a separate structure that points to the rows in a table, allowing the database to look up data more quickly without scanning the entire table.
There are various types of database indexes, each with its unique characteristics and use cases. Understanding these different index types is crucial for optimizing the performance of database systems and ensuring efficient data retrieval.
In this section, we will explore several common types of database indexes.

## Primary Index
A Primary Index is the index on a table’s primary key. The primary key is a column (or set of columns) that uniquely identifies each row. Most relational databases automatically create an index on the primary key to enforce uniqueness and allow fast lookup by the primary key. There can only be one primary index per table because a table has only one primary key. This index usually organizes the data by the primary key values, especially in systems where the primary key index is also the clustered index (e.g. InnoDB in MySQL or MS SQL Server by default).

### Performance considerations
A primary index provides very efficient access to records by the primary key. Lookups and joins based on the primary key are fast because the index is typically a B-tree optimized for finding the exact key. Since the primary index is unique by definition, the database can stop searching as soon as it finds the one matching row. Maintaining a primary index has minimal overhead beyond any other index – inserts and updates to primary-key values (which are rare, since primary keys seldom change) will update the index. The primary index also helps enforce referential integrity (foreign keys often reference a primary key index for fast checks). In summary, the primary index improves read performance for identifying specific rows, with very little downside since every table usually needs a primary key.

### Use cases
Use a primary index on the main identifier of the table. This is usually done automatically by declaring a PRIMARY KEY. For example, an Employees table might use an EmployeeID as the primary key; the database will create a primary index on EmployeeID so that queries like SELECT * FROM Employees WHERE EmployeeID = 123 are very fast. Essentially every relational table that has a primary key will have a primary index – it’s the foundational index for uniquely identifying rows.

### Example
Creating a table with a primary key will automatically create a primary index on that key. For instance, in SQL:

```sql
CREATE TABLE Customers (
  CustomerID INT PRIMARY KEY,
  Name       VARCHAR(100),
  Address    VARCHAR(200)
);
```

Here, CustomerID is the primary key, and the database builds a primary index on it so that any lookup by a specific CustomerID is efficient.

---

## Unique Index
A Unique Index ensures that all values in the indexed column (or combination of columns) are distinct. In other words, it enforces a uniqueness constraint – no two rows can have the same key value. Database systems often automatically create a unique index when you define a UNIQUE constraint or a primary key (primary keys are by nature unique). You can also create additional unique indexes on other columns that must be unique (for example, a username or email address in a users table). A unique index functions like a regular index for lookup performance, but with the added rule that duplicate values are not allowed.

### Performance considerations
Unique indexes speed up read queries just as non-unique indexes do, and in addition they guarantee data integrity by preventing duplicates. Reads (SELECTs) using a unique index are very efficient – and the database also knows that at most one row will match, which can slightly optimize certain plans. The trade-off is on writes: whenever a new row is inserted or an indexed column is updated, the database must check the unique index to ensure no duplicate value exists, which adds a small overhead. This means inserts/updates can be a bit slower due to the uniqueness check. However, this cost is usually worth it to maintain data correctness. Storage overhead for a unique index is similar to a normal index.

### Use cases
Use unique indexes for any column that requires unique values. Besides primary keys, this includes candidate keys or business rules – e.g. an email address in a users table (to ensure no two users share the same email), or a social security number field that must be unique per person. Unique indexes are optimal when you frequently query by that column as well, because they both enforce integrity and accelerate lookups. Keep in mind that you can have multiple unique indexes per table (unlike primary index which is one).

### Example
Creating a unique index (or constraint) on a column. Suppose we have a Users table and want Email to be unique:

```sql
-- Create a unique index on the Email column to prevent duplicates
CREATE UNIQUE INDEX idx_users_email ON Users(Email);
```

After this, any SELECT or join by Email can use the index, and any attempt to insert a duplicate email will be blocked by the index. Thus, queries like SELECT * FROM Users WHERE Email = 'alice@example.com' are fast, and the index ensures there’s at most one 'alice@example.com' in the table.

---

## Clustered Index
A Clustered Index determines the physical order of data in the table. In a clustered index, the table’s rows are stored on disk in the same order as the index key (essentially the index is the table). This means that there is no separate structure holding pointers to data – the index’s leaf nodes contain the actual data rows. Because of this, a table can have only one clustered index (the data can only be sorted one way on disk). Often, the primary key serves as the clustered index by default (e.g. in SQL Server or InnoDB), but it can be a different column if chosen. If a table has a clustered index, other indexes on that table are called non-clustered (or secondary) indexes.

### Performance considerations
Clustered indexes are very efficient for range queries and sorting on the indexed key, because the data is already ordered on disk. For example, if you query a range of values (say, all records between two dates on a date-column clustered index), the database can retrieve that continuous block of rows directly from disk with minimal seeking. Similarly, retrieving data in sorted order on the cluster key is fast (no extra sorting step) because the rows are stored sorted. Clustered indexes thus shine for range scans, ordering, and grouping by the indexed column. They also tend to improve locality of reference – adjacent rows (by index order) are stored near each other, which can benefit I/O when reading many sequential rows.

However, clustered indexes have some trade-offs. Writing data can be slower if new rows need to be inserted in sorted order; inserting a row in the middle of existing data may require shifting rows or splitting data pages to maintain the order. Updates to the cluster key (if they occur) are expensive for the same reason (the row might move). Also, since there can be only one clustered index, you must choose it wisely based on query patterns. If you frequently need data sorted or ranged by a particular column, that’s a good candidate. If not, many systems default to clustering on the primary key. Additionally, non-clustered indexes on a clustered table use the clustered key as a pointer to locate data (instead of a direct physical pointer), which adds a bit of size to those indexes.

### Use cases
Use a clustered index for columns on which you often retrieve ordered data or ranges of data. A classic use case is a date/timestamp column for a log or history table – clustering on the date makes it efficient to fetch a time range or the latest records. Similarly, if you always query a customer table sorted by LastName, a clustered index on LastName makes sense (as long as insert patterns are manageable). Many OLTP databases cluster on an auto-incrementing ID (primary key) because new inserts then append to the end (minimizing reorganization), and joins by ID are fast. In data warehouses, clustering on a date or other sequential key can benefit range queries. If a table is small, clustering is less critical, but on large tables the choice of clustered index can significantly impact performance for certain queries.

### Example
In SQL Server (or other systems that support explicit clustered indexes), you can create one like so:

```sql
-- Cluster the Customer table by last name, then first name (alphabetical order)
CREATE CLUSTERED INDEX idx_cust_name ON Customers(LastName ASC, FirstName ASC);
```

This physically sorts the Customers table by last name and first name. Queries such as SELECT * FROM Customers ORDER BY LastName, FirstName or range queries like “LastName BETWEEN 'A' and 'C'” will be very efficient because the data is stored in sorted order. Keep in mind that only one clustered index (here on LastName+FirstName) can exist – if the primary key is different, that primary key would be supported by a non-clustered unique index instead.

---

## Non-Clustered Index
A Non-Clustered Index (also called a secondary index) is a standard index separate from the actual data storage. In a non-clustered index, the index structure (typically a B-tree) stores the indexed column values and pointers (references) to the actual table rows that contain the rest of the data. The table’s data is not sorted by this index; instead, the index is like a lookup table that can quickly find the locations of rows matching a given key. Non-clustered indexes are analogous to an index in a book – the book’s content is in its original order, and the separate index lists keywords with pointers (page numbers) to where those topics are found. A table can have multiple non-clustered indexes on different columns, since they don’t dictate physical order.

### Performance considerations
A non-clustered index greatly speeds up queries that filter or join on the indexed column, by allowing the database to jump directly to matching records instead of scanning the whole table. For example, an index on LastName in an Employees table lets a query WHERE LastName = 'Smith' quickly find all “Smith” entries. Non-clustered indexes are very flexible – you can create many of them to support different queries – but each additional index adds storage overhead and slows down writes (each insert/update/delete must update all relevant indexes). When a query uses a non-clustered index, the database will traverse the index to find matching key values, then follow the pointers to retrieve the full rows. If the index covers the query (contains all needed columns), it may not need to fetch the table rows (see Covering Index below); otherwise, there is an extra step (called a bookmark lookup or index-to-table lookup) to get the remaining data. This lookup is generally fast for a few records, but if a non-clustered index returns many matches, the benefit may diminish because each row retrieval incurs additional I/O.

Compared to clustered indexes, non-clustered indexes do not guarantee physical locality of data – the rows may be scattered – so range scans will fetch each matching row via pointers. If many rows qualify, a table scan might even be cheaper, which the optimizer will consider. Thus, non-clustered indexes are best for selective queries (that return a small percentage of the table). They are not helpful for queries that return a very large fraction of the table (in such cases, a full table scan is usually more efficient than hopping through the index).

### Use cases
Virtually any column that is frequently used in WHERE clauses, join conditions (ON clauses), or used for sorting (ORDER BY) is a candidate for a non-clustered index if it isn’t already the clustered (or primary) index. For example, if you often query an Orders table by CustomerID, and the table is not clustered by customer, you would create a non-clustered index on CustomerID to speed up those lookups. Similarly, an index on LastName in an Employees table helps searches by last name, and an index on ProductCategory in a Products table accelerates filtering by category. You can have multiple non-clustered indexes per table, so you tailor them to your query patterns. Just be cautious with creating too many; each index will need maintenance on writes and consumes disk/RAM.

### Example
Creating a non-clustered index on a single column is straightforward:

```sql
-- Create a non-clustered index on the LastName column of Employees
CREATE INDEX idx_emp_lastname ON Employees(LastName);
```

This index allows fast searches by last name. For instance, SELECT * FROM Employees WHERE LastName = 'Doe' will use the index to find pointers to all "Doe" records and then retrieve them. The Employees data itself remains in its original order (perhaps clustered by another key or as inserted), but this index serves as a quick lookup structure for the LastName field.

---

## Composite Index
A composite index combines multiple columns into a single index, enabling efficient queries that filter by multiple criteria. For example:

```sql
SELECT * FROM Orders WHERE CustomerID = 42 AND OrderDate >= '2024-01-01' AND OrderDate < '2024-02-01';
```

### Key Features
- **Leftmost Prefix**: Queries filtering by the first column (e.g., `CustomerID`) can use the index efficiently.
- **Multi-Column Filtering**: Optimized for queries specifying multiple columns, such as `CustomerID` and `OrderDate`.
- **Limitations**: Cannot efficiently handle queries filtering only by the second column (e.g., `OrderDate`).

### Use Case
Ideal for scenarios where queries often filter by a combination of columns.

---

## Full-Text Index
A full-text index is specialized for text search, breaking text data into tokens for rapid substring and keyword searches. It is typically implemented as an inverted index, mapping each word to rows containing that word.

### Key Features
- **Efficient Text Search**: Handles queries like `LIKE '%keyword%'` efficiently.
- **Advanced Features**: Supports phrase searches, boolean text searches, relevancy ranking, stemming, and noise word filtering.
- **Performance Considerations**: Building and maintaining the index is resource-intensive, and updates may be batch-processed or asynchronous.

### Use Case
Best for columns containing large unstructured text, such as articles, product descriptions, or comments. Example:

```sql
CREATE FULLTEXT INDEX idx_articles_content ON Articles(Content);
SELECT * FROM Articles WHERE MATCH(Content) AGAINST('database');
```

---

## Hash Index
A hash index uses a hash table for indexing, providing O(1) lookup time for equality queries.

### Key Features
- **Exact Match Optimization**: Ideal for queries using `=` or `IN` comparisons.
- **No Ordering**: Cannot be used for range scans or sorting.
- **Collision Handling**: Uses linked lists or overflow areas for hash collisions.

### Use Case
Suitable for key-value lookups or cryptographic hash indexing. Example:

```sql
CREATE INDEX idx_customer_code_hash ON Customers USING HASH(CustomerCode);
SELECT * FROM Customers WHERE CustomerCode = 'ABC123';
```

### Limitations
Not supported by all databases and unsuitable for range queries or prefix matches.
