 # Designing Pastebin
## Table of Contents
- [Overview](#overview)
- [1. System Requirements](#1-system-requirements)
- [2. Capacity Estimation and Constraints](#2-capacity-estimation-and-constraints)
- [3. System API](#3-system-interface-definition)
- [4. Database Design](#4-database-design)
- [5.High Level Design](#6-basic-system-design-and-algorithm)
- [6. component Design]()
- [7.Purging or DB Cleanup]()
- [8.Data Partitioning and Replication]()
- [9.Cache and Load Balancer]()
 -[10.Security and Permissions]()

# Overview
Pastebin is a web application that allows users to store and share plain text or code snippets.
Users can create "pastes" by submitting text, which is then assigned a unique URL for easy sharing. Pastebin is commonly used for sharing code snippets, configuration files, error logs, and other text-based content.
# 1. System Requirements
## Functional Requirements
- Users can create a new paste by submitting text.
- Each paste is assigned a unique URL for sharing. 
- Users can set an expiration time for their pastes (e.g., 10 minutes, 1 hour, 1 day, never).
- Data and will expire after the specific timespan automatically.
- Users can view a paste by accessing its unique URL.
- user can optionally be able to pick a custom alias for their paste
## Non-Functional Requirements
- The system should be highly reliable , any data should not be lost. (consistency)
- The system should be highly available (availability)
- The system should be able to access thier pase in real time with minimum latency (low latency)
- Paste link should not be guessable (security)
## Extended Requirements:
- Analytics: Track the number of views for each paste.
- Our Service should be accessible through REST APIs by other services.
# 2. Capacity Estimation and Constraints
- ## Assumptions:
  - we can assume a 5:1 read to write ratio. 
  - System get 1 million paste creation requests per day.
  - Each paste is on average 10 KB in size.
- ## Traffic Estimation:
    - QPS (Queries Per Second):
      - Write QPS: 1,000,000 pastes/day / 86,400 seconds/day ≈ 12 writes/second
      - Read QPS: 5 * Write QPS = 60 reads/second
      - Total QPS = Write QPS + Read QPS = 72 requests/second 
  - ## bandwidth Estimation:
    ```
    Write bandwidth:(write QPS * average paste size) =  12 writes/second * 10 KB/write = 120 KB/second
    Read bandwidth: (read QPS * average paste size) = 60 reads/second * 10 KB/read = 600 KB/second
   
    ```
- ## storage estimation:
  - Daily Storage Requirement: 1,000,000 pastes/day * 10 KB/paste = 10 GB/day
  - Monthly Storage Requirement: 10 GB/day * 30 days = 300 GB/month
  - Yearly Storage Requirement: 300 GB/month * 12 months = 3.6 TB/year
  - 10 years = 36 TB
 - # unique URL generation estimation:
    - We need to determine the minimum length of the unique URL to ensure that we can generate
      - enough unique URLs to accommodate all pastes over a 10-year period. 
  - with 1M pastes per day , in 10 years we will have 3.65 pastes.How?
  
  ```
     1M * 365 * 10 = 3.65B
  ```
    - To generate unique URLs, we can use a combination of alphanumeric characters [A-Z, a-z, 0-9, , -]. This gives us 64 possible characters.how?
    
    ```
    26 (A-Z) + 26 (a-z) + 10 (0-9) + 2 (-, _) = 64 characters
    ```
    - The number of unique combinations for a URL of length L can be calculated as:
           ```Total Combinations = (Number of Characters)^(Length of URL)```
        - Total Combinations = 64^L
    - We need at least 3.6 billion unique combinations to accommodate our expected number of
    - pastes over 10 years.
   ```
     To find the minimum length L required to achieve this, we can set up the equation:
      64^L >= 3.6 billion
     Taking the logarithm of both sides:
     
      L * log(64) >= log(3.6 billion)
    
      L >= log(3.6 billion) / log(64)
      ```
    Calculating the values:
    
      log(3.6 billion) ≈ 9.556
      log(64) ≈ 1.806
      L >= 9.556 / 1.806 ≈ 5.29
     Since L must be an integer, we round up to the next whole number:
      L = 6
   ```
 Therefore, a URL length of 6 characters is sufficient to provide over 3.6 billion unique combinations, which meets our requirement for 10 years of paste storage.
- # memory estimation:
  - Each paste is 10 KB in size.
  - Assuming we want to cache the most frequently accessed pastes, Following  the 80/20 rule, we can estimate that 20% of the pastes will account for 80% of the access.
  - Daily Cache Requirement: 5,000,000 pastes/day * 20% * 10 KB/paste = 10 GB
# 3. System Interface Definition
  - Create Paste: 
    - ```addPaste(api_dev_key, paste_data, custom_url=None user_name=None, paste_name=None, expire_date=None)```
    - Endpoint: POST /pastes
    - body: { "api_dev_key": "string", "paste_data": "string", "custom_url": "string (optional)", "user_name": "string (optional)", "paste_name": "string (optional)", "expire_date": "timestamp (optional)" }
    - Response: { "paste_url": "string" }
    - Get Paste:
    - ```getPaste(paste_url, api_dev_key)```
    - Endpoint: GET /pastes/{paste_url}
    - Response: { "paste_data": "string", "paste_name": "string",
    - "user_name": "string", "creation_date": "timestamp", "expire_date": "timestamp" }
    - Delete Paste:
    - ```deletePaste(paste_url, api_dev_key)```
    - Endpoint: DELETE /pastes/{paste_url}
    - Response: { "message": "Paste deleted successfully" }
    
    
    
# 4. Database Design
some observations:
- we need to store billions of record
- the system should be highly available and consistent(non-functional  requirements)
- our service is read heavy(non-functional  requirements)
- so we wil use a relational database like PostgreSQL or MySQL
- we will use object storage like AWS S3 or Google Cloud Storage to store the actual paste
  - because pastes can be large and storing them directly in the database can lead to performance issues. 
  - storing pastes in object storage allows for better scalability and cost efficiency.
  - we will store a reference to the paste in the database.
  - this approach helps in handling large text data efficiently.
  
- we will have two tables: User and Paste
- 
## User Table
| Field Name    | Data Type     | Description                          |
|---------------|---------------|--------------------------------------|
| user_id       | UUID          | Unique identifier for each user      |
| user_name     | VARCHAR(100)  | Unique username for the user         |
| api_dev_key   | VARCHAR(255)  | API key for authenticating requests  |
| creation_date | TIMESTAMP     | Timestamp when the user was created   |
| password_hash | VARCHAR(255)  | Hashed password for user authentication | 

## Paste Table
| Field Name    | Data Type    | Description                                             |
|---------------|--------------|---------------------------------------------------------|
| paste_id      | VARChar(16)  | Unique identifier for each paste                        |
| contentKey    | VARCHAR(255) | Key to retrieve paste content from object storage       |
| user_id       | UUID         | Foreign key referencing the user who created the paste  |
| creation_date | TIMESTAMP    | Timestamp when the paste was created                    |
| expiry_date  | TIMESTAMP    | Timestamp when the paste will expire (NULL if never)    |
 

- `paste_id` is the unique identifier for each paste and will be used to generate the unique URL for accessing the paste.
-  `contentKey` is a reference to the actual content of the paste stored in an object storage service like AWS S3 or Google Cloud Storage. This approach helps in handling large text data efficiently.




## Indexes
user and paste tables should have indexes to optimize query performance.
 # user table indexes
| Index Name      | Columns       | Description                                      |
|-----------------|---------------|--------------------------------------------------|
| idx_user_name   | user_name     | Index for quick lookup of users by username
| idx_api_dev_key | api_dev_key   | Index for quick lookup of users by API key       |
# paste table indexes
| Index Name        | Columns       | Description                                      |
|-------------------|---------------|--------------------------------------------------|
| idx_paste_id      | paste_id      | Index for quick lookup of pastes by
| idx_user_id       | user_id       | Index for quick lookup of pastes by user         |
| idx_creation_date | creation_date | Index for sorting and querying pastes by creation date |
| idx_expiry_date   | expiry_date   | Index for efficient cleanup of expired pastes    |



# 5.High Level Design
 At a high level, the system can be divided into several key components:
- API Gateway: Handles incoming requests and routes them to the appropriate services.
- Paste Service: Manages paste creation, retrieval, and deletion.
- Database: Stores user and paste metadata.
- Object Storage: Stores the actual paste content.
- Cache: Caches frequently accessed pastes to reduce latency and database load.
- Load Balancer: Distributes incoming traffic across multiple servers to ensure high availability and reliability.
- Monitoring and Logging: Tracks system performance and logs important events for debugging and analysis.
- Purging Service: Periodically removes expired pastes from the database and object storage.
- Analytics Service: Tracks and reports on paste views and other usage metrics.
- Security Service: Ensures that pastes are secure and not easily guessable.
# 6. Component Design
- ## API Gateway
- The API Gateway is the entry point for all client requests. It handles routing, rate limiting and authentication.
- It forwards requests to the appropriate service based on the endpoint.
- It also handles response formatting and error handling.
- ## Paste Service
- The Paste Service is responsible for managing pastes. It provides methods for creating, retrieving,and deleting pastes.
- When a new paste is created, it generates a unique paste_id, stores the paste content in object storage, and saves the metadata in the database.
- When a paste is retrieved, it first checks the cache. If the paste is not in
- the cache, it fetches the metadata from the database and the content from object storage.
- ## Database
- The database stores user and paste metadata. It is designed to handle high read and write throughput.
- It uses indexing to optimize query performance.
- ## Object Storage
- Object storage is used to store the actual paste content. It is designed for scalability and cost efficiency.
- It provides high availability and durability for stored pastes.
- ## Cache
- The cache stores frequently accessed pastes to reduce latency and database load.
- It uses an LRU (Least Recently Used) eviction policy to manage cache size.
- ## Load Balancer
- The load balancer distributes incoming traffic across multiple servers to ensure high availability and reliability.
- It monitors server health and routes traffic away from unhealthy servers.
- ## Monitoring and Logging
- The monitoring and logging component tracks system performance and logs important events for debugging and analysis.
- It provides alerts for critical issues and generates reports on system usage.
- ## Purging Service
- The purging service periodically checks for expired pastes and removes them from the database and object
- storage.
- It runs as a background job to ensure that expired pastes do not consume unnecessary storage.
- ## Analytics Service
- The analytics service tracks and reports on paste views and other usage metrics.
- It provides insights into user behavior and system usage patterns.
- ## Security Service
- The security service ensures that pastes are secure and not easily guessable.
- It implements measures such as rate limiting, IP blocking, and paste URL obfuscation to
- protect against abuse and unauthorized access.
- # 7.Purging or DB Cleanup
- A background service that periodically checks for expired pastes and removes them from the database and object
- storage.
- It runs as a scheduled job to ensure that expired pastes do not consume unnecessary storage.
- The purging service can be implemented using a cron job or a dedicated worker process.
- It queries the database for pastes with an expiry_date that has passed and deletes them.
- It also deletes the corresponding content from object storage using the contentKey.
- The frequency of the purging job can be adjusted based on the expected volume of pastes
- and the desired storage efficiency.
- # 8.Data Partitioning and Replication
- To handle the expected volume of pastes and ensure high availability, the database can be partitioned and replicated.
- Partitioning can be done based on the paste_id or user_id to distribute the load across multiple database instances.
- For data partitioning:
  - Hash-based Partitioning: Distribute pastes across multiple database instances based on a hash of the paste_id. This ensures an even distribution of data and load.
  - Range-based Partitioning: Partition pastes based on creation_date or expiry_date. This can be useful for purging expired pastes efficiently.
  - consistent hashing can be used to minimize data movement when adding or removing database instances.
  - For replication:
  - - Replication can be implemented using a master-slave architecture,
  - where the master handles write operations and slaves handle read operations.
    - This helps to distribute the read load and improve performance.
    - Asynchronous replication can be used to ensure that slaves are eventually consistent with the master.
    - Multi-region replication can be implemented to ensure high availability and low latency for users in different
    - geographic locations.
    - Regular backups and monitoring of replication lag can help ensure data integrity and availability.
    - # 9.Cache and Load Balancer
      - ## Cache
      - A caching layer can be implemented using an in-memory data store like Redis or Memcached
      - to store frequently accessed pastes. This helps to reduce latency and database load.
      - The cache can use an LRU (Least Recently Used) eviction policy to manage cache size.
      - Cache keys can be based on the paste_id to ensure quick lookups.
      - Cache invalidation can be handled by setting an expiration time for cached pastes or by
      - invalidating the cache when a paste is deleted.
      - ## Load Balancer
      - A load balancer can be used to distribute incoming traffic across multiple servers to ensure high
        - availability and reliability. 
        - It can monitor server health and route traffic away from unhealthy servers.
        - Load balancing algorithms like round-robin, least connections, or IP hash can be used based on the expected traffic patterns.
        - SSL termination can be handled at the load balancer to offload encryption/decryption from
        - the application servers.
        - Auto-scaling can be implemented to dynamically adjust the number of servers based on traffic load
        - to ensure optimal performance and cost efficiency.
        - # 10.Security and Permissions
        - To ensure the security of pastes and prevent unauthorized access, several measures can be implemented
        - ## Authentication and Authorization
        - Users can be required to authenticate using an API key (api_dev_key) when creating or accessing pastes.
        - User roles and permissions can be defined to restrict access to certain operations (e.g., only the creator can delete their paste).
        - ## Paste URL Obfuscation
        - Unique paste URLs can be generated using a combination of alphanumeric characters to make them hard
        - to guess.
        - The length of the paste_id can be adjusted to ensure a sufficient number of unique combinations
        - to accommodate the expected volume of pastes.
        - ## Rate Limiting and IP Blocking
        - Rate limiting can be implemented to prevent abuse and excessive requests from a single user or IP
        - address.
        - IP blocking can be used to block known malicious IP addresses or ranges.
        - ## Data Encryption
        - Sensitive data (e.g., user credentials) can be encrypted both in transit (using HTTPS) and at rest (using database encryption features).
        - ## Monitoring and Alerts
        - Monitoring can be implemented to track suspicious activity (e.g., multiple failed authentication attempts).
        - Alerts can be set up to notify administrators of potential security breaches or unusual activity.
        - ## Regular Security Audits
        - Regular security audits and vulnerability assessments can be conducted to identify and address potential security risks.
        - Keeping software and dependencies up to date with security patches is essential to mitigate known vulnerabilities.
      


        
   