# Introduction to Databases

A database is an organized collection of structured data that is stored and managed electronically. Databases are essential tools for managing, storing, and retrieving data efficiently, playing a vital role in modern applications.

## Database Management Systems (DBMS)
A Database Management System (DBMS) is software that interacts with users, applications, and the database itself to capture, store, and manage data. It provides an interface for performing operations such as:
- Inserting
- Updating
- Deleting
- Retrieving data

### Types of DBMS
1. **Relational Database Management Systems (RDBMS)**: Store data in tables with predefined relationships. Common query language: SQL.
2. **Non-Relational Database Management Systems (NoSQL)**: Store data in various formats (key-value, document, column-family, graph). Known for scalability and handling unstructured data.

## Overview of SQL and NoSQL Databases
### SQL Databases
- Based on the relational model.
- Store data in tables with predefined schema and relationships.
- Examples: MySQL, PostgreSQL, Microsoft SQL Server, Oracle.
- Known for consistency, reliability, and powerful query capabilities.

### NoSQL Databases
- Diverse group of non-relational databases.
- Prioritize flexibility, scalability, and performance.
- Categories: Document databases, key-value stores, column-family stores, graph databases.
- Examples: MongoDB, Redis, Apache Cassandra, Neo4j.

## High-Level Differences Between SQL and NoSQL
1. **Storage**:
   - SQL: Data stored in tables (rows and columns).
   - NoSQL: Data stored in key-value, document, graph, or columnar models.
2. **Schema**:
   - SQL: Fixed schema; predefined columns.
   - NoSQL: Dynamic schema; flexible structure.
3. **Querying**:
   - SQL: Uses SQL for data manipulation.
   - NoSQL: Queries focus on collections of documents (UnQL).
4. **Scalability**:
   - SQL: Vertically scalable (hardware upgrades).
   - NoSQL: Horizontally scalable (adding servers).
5. **Reliability (ACID Compliance)**:
   - SQL: ACID compliant, ensuring data reliability.
   - NoSQL: Often sacrifices ACID compliance for performance and scalability.
