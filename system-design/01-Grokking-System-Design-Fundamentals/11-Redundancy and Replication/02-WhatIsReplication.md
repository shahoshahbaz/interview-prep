# What is Replication?

Database replication is the process of copying and synchronizing data from one database to one or more additional databases. This is commonly used in distributed systems where multiple copies of the same data are required to ensure data availability, fault tolerance, and scalability.

Replication is widely used in many database management systems (DBMS), usually with a primary-replica relationship between the original and the copies. The primary server gets all the updates, which then ripple through to the replica servers. Each replica outputs a message stating that it has received the update successfully, thus allowing the sending of subsequent updates.

## Redundancy vs. Replication: Key Differences

### Active vs. Passive:
- **Redundancy** is often passive – the backup components are there in case of failure but are not actively used in normal operations.
- **Replication** is active – all copies of the data are usually utilized in some way, either for load balancing or data recovery.

### Focus:
- **Redundancy** focuses on the reliability and availability of the overall system.
- **Replication** focuses on the availability and integrity of the data.

### Implementation:
- **Redundancy** might involve identical backup systems or components.
- **Replication** involves distributing and synchronizing data across different systems.

In essence, while both redundancy and replication are about ensuring high availability and system reliability, redundancy is more about having backup resources at the ready, and replication is about keeping multiple active copies of data. In distributed systems, using both strategies can significantly enhance performance and reliability.

## Replication Strategies

Here are the top three database replication strategies:

### 1. Synchronous Replication
Synchronous replication is a type of database replication where changes made to the primary database are immediately replicated to the replica databases before the write operation is considered complete. In other words, the primary database waits for the replica databases to confirm that they have received and processed the changes before the write operation is acknowledged.

In synchronous replication, there is a strong consistency between the primary and replica databases, as all changes made to the primary database are immediately reflected in the replica databases. This ensures that the data is consistent across all databases and reduces the risk of data loss or inconsistency.

![Synchronous Replication](SynchronousReplication.png)

### 2. Asynchronous Replication
Asynchronous replication is a type of database replication where changes made to the primary database are not immediately replicated to the replica databases. Instead, the changes are queued and replicated to the replicas at a later time.

In asynchronous replication, there is a delay between the write operation on the primary database and the update on the replica databases. This delay can result in temporary inconsistencies between the primary and replica databases, as the data on the replica databases may not immediately reflect the changes made to the primary database.

However, asynchronous replication can also have performance benefits, as write operations can be completed quickly without waiting for confirmation from the replica databases. In addition, if one or more replica databases are unavailable, the write operation can still be completed on the primary database, ensuring that the system remains available.

![Asynchronous Replication](AsynchronousReplication.png)

### 3. Semi-Synchronous Replication
Semi-synchronous replication is a type of database replication that combines elements of both synchronous and asynchronous replication. In semi-synchronous replication, changes made to the primary database are immediately replicated to at least one replica database, while other replicas may be updated asynchronously.

In semi-synchronous replication, the write operation on the primary is not considered complete until at least one replica database has confirmed that it has received and processed the changes. This ensures that there is some level of strong consistency between the primary and replica databases, while also providing improved performance compared to fully synchronous replication.

![Semi-synchronous Replication](Semi-synchronousReplication.png)
