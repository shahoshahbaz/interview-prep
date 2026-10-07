# Designing file sharing and synchronization system like Dropbox

## Table of Contents
<div style="font-size: 20px; line-height: 1.8em;">

1. 📌 [Problem Statement](#problem-statement)
2. 📋 [Requirements and Goals of the System](#requirements-and-goals-of-the-system)
3. 📊 [Capacity Estimation and Constraints](#capacity-estimation-and-constraints)
4. 🏗️ [High-Level Design](#high-level-design)
5. ⚙️ [Component Design](#component-design)
6. 🔄 [File Processing Workflow](#file-processing-workflow)
7. ♻️ [Data Deduplication](#deduplication)
8. 🪓 [Metadata Partitioning](#metadata-partitioning)
9. 🧊 [Caching](#caching)
10. 🔀 [Load Balancer(LB)](#load-balancer)
11. 🔐[Security, Permission and File Sharing](#security-permission-and-file-sharing)
12. 💬 [Question & Answer Style](#qa)
13. 🎬 [Complete Interview Narrative](#complete-interview-narrative)
</div>

## Problem Statement

Design a scalable cloud storage system similar to Dropbox where users can upload and download files from multiple devices, automatically synchronize updates across devices, support file sharing, and maintain file version history, while ensuring high availability, consistency, and efficient storage usage.

## 📋Requirements and Goals of the System

### Functional Requirements (What users can do)
- Upload and download files from any device
- Share files and folders with other users
- Automatic synchronization across devices
- Support offline edits and sync when back online
- Handle large files (up to GBs)
- File versioning / snapshots (restore previous versions)
### Non-Functional Requirements (How the system behaves)
- Consistency & Reliability: 
  - Strong consistency (ACID) for file operations
- Scalability:  
  - Handle massive read and write traffic
  - System scales horizontally with users and data
- Performance:
  -  Chunking files (e.g., 4MB) → efficient uploads/retries
  - Delta sync → only send changed parts
  - Local metadata caching → reduce latency
- Efficiency (Cost & Bandwidth):
  - Deduplication → avoid storing duplicate data
- Availability:
  - Multi-device sync with eventual consistency
  - System remains available under heavy load

### 🗣️ What to say in interview

> I separate user-facing features like upload, share, sync, and versioning as functional requirements, and ensure scalability, 
consistency, and performance using chunking, deduplication, and delta sync as non-functional guarantees."
---

## 📊Capacity Estimation and Constraints

### 🤔Assumptions


**Key Assumptions:**
- Total Users: 500 million
- DAU: 100 million
- Average devices per user: 3
- Average files per user: 200 files/photos
- Average file size: 100 KB
- Active connections per minute: 1 million

**Total Files Calculation:**
$$\text{Total Files} = \text{Total Users} \times \text{Average Files per User}$$
$$\text{Total Files} = 500\text{M} \times 200 = 100\text{ Billion files}$$



### 📈 Traffic Estimation

- **Step 1:** Identify system events
  - Writes → file uploads / updates
  - Reads → file downloads / sync

- **Step 2:** Apply change-rate assumption
  - Total files = 100B
  - 🤔Assume 1% updated per day

$$\text{Events/day} = \text{TotalObjects} \times \text{ChangeRate}$$
$$\text{Writes/day} = 100B \times 1\% = 1B$$

- **Step 3:** Convert to QPS
$$QPS = \frac{\text{Events/day}}{86400}$$
$$QPS = \frac{1B}{86400} \approx 11.6K$$

- **Step 4:** Read vs Write split
  - 🤔Assume: Read = 2× writes

$$ReadQPS = 2 \times WriteQPS$$
$$ReadQPS = 2 \times 11.6K = 23.2K$$

- **📈 Final Traffic:**
  - Write QPS  ≈ 11.6K 
  -  Read QPS  ≈ 23K
  - Total QPS ≈ 35K


### 📡 Bandwidth Estimation

- **Inbound (Writes):**
$$B_{\text{in}} = \text{WriteQPS} \times \text{AvgFileSize}$$
$$B_{\text{in}} = 11.6K \times 100KB \approx 1.16 \text{ GB/s}$$

- **Outbound (Reads):**
$$B_{\text{out}} = \text{ReadQPS} \times \text{AvgFileSize}$$
$$B_{\text{out}} = 23K \times 100KB \approx 2.3 \text{ GB/s}$$

- **📡 Final Bandwidth:**

  - Inbound ≈ 1.16 GB/s 
  - Outbound  ≈ 2.3 GB/s 
  - Total ≈ 3.5 GB/s



### 💽 Storage Estimation

- **Raw Storage:**
$$\text{Storage} = \text{TotalFiles} \times \text{AvgFileSize}$$
$$\text{Storage} = 100B \times 100KB = 10PB$$

- **Provisioned Storage (With Replication & Overhead):**

  - 🤔Assumptions:
    - Replication = 3
    - Overhead = 1.5

$$\text{TotalStorage} = \text{Raw} \times \text{Replication} \times \text{Overhead}$$
$$\text{TotalStorage} = 10PB \times 3 \times 1.5 = 45PB$$

**✅ Final Storage:**

- Raw Storage : 10 PB 
- Provisioned Storage:30–45 PB 




### ⚡📉 Cache Impact Estimation
- Read QPS = 23K
- 🤔Assumptions:
  - Cache hit rate = 20%

- **Database Load (Without Cache):**
$$\text{DB QPS} = \text{ReadQPS} \times (1 - \text{HitRate})$$
$$\text{DB QPS} = 23K \times (1 - 0.2) = 23K \times 0.8 = 18.4K$$

- **Cache Load (With Cache):**
$$\text{Cache QPS} = \text{ReadQPS} \times \text{HitRate}$$
$$\text{Cache QPS} = 23K \times 0.2 = 4.6K$$

**✅ Final Cache Impact:**



- Cache Serves : 4.6K 
- Database Handles: 18.4K 

### 🗣️ What to say in interview

> "For Dropbox, I estimate traffic based on file change rates. Assuming 1% of files update daily, I get around 11.6K write QPS and about 23K read QPS. This results in roughly 3.5 GB/s total bandwidth. Storage is about 10PB raw, increasing to around 30–45PB with replication and overhead. With caching, I can reduce database read load by about 20%."
---

## 🏗️High-Level Design

### Main Components

1. **Client Applications**
   - Web client
   - Mobile client
   - Desktop sync client
   
   These are the entry points for upload, download, sync, and sharing.

2. **Load Balancer / API Gateway**
   - Receives client requests
   - Routes traffic to backend services
   - Handles auth forwarding, throttling, and request routing

3. **Authentication Service**
   - Verifies user identity
   - Issues / validates tokens
   - Protects file access

4. **Metadata Service**
   - Stores and manages:
     - file/folder names
     - directory structure
     - ownership
     - permissions
     - file versions
     - mapping from file to chunks
   - This is the control plane of the system.

5. **Blob / Object Storage**
   - Stores the actual file content
   - This is the data plane:
     - file chunks
     - large binary data
     - durable storage

6. **Sync Service**
   - Coordinates updates across devices
   - Responsible for:
     - detecting changes
     - informing other devices
     - helping devices stay in sync

7. **Sharing / Permission Service**
   - Handles:
     - file sharing
     - folder sharing
     - access control
     - link-based sharing

8. **Cache**
   - Used to reduce backend load for:
     - hot metadata
     - frequent permission checks
     - popular files/chunks

9. **Message Queue / Async Workers**
   - Used for background tasks:
     - sync notifications
     - indexing
     - virus scanning
     - thumbnail generation
     - audit logging

## ⚙️Component Design

### Overview

This section explains the internal architecture of each component and how they interact. The focus is on understanding:

- How each component works
- Why certain design choices are made
- Interview-level explanations for key features

### 1. Client (Most Important Component)

#### Core Responsibility

The client detects changes and syncs them efficiently with the server.

#### Client Internal Components

##### a) Internal Metadata DB
- **Stores:**
  - file list
  - chunk mapping
  - file versions
- **Enables:**
  - offline mode (works without server)
  - fewer server round trips
  
> **Interview Line:** "Client keeps a local metadata store to support offline operations and reduce round trips."

##### b) Chunker
- Splits files into chunks (e.g., 4MB chunks)
- Rebuilds files from chunks on download
- Sends only modified chunks to server

**Why Important:**
- Saves bandwidth (delta sync)
- Enables deduplication

> **Interview Line:** "Files are chunked so we only transfer modified parts rather than entire files."

##### c) Watcher
- Monitors local file system directory
- **Detects changes:**
  - File creation
  - File updates
  - File deletion

> **Interview Line:** "A watcher monitors file system changes and triggers sync operations."

##### d) Indexer
- Processes events from watcher
- Updates local metadata DB
- Communicates with sync service

> **Interview Line:** "Indexer converts file changes into metadata updates and sync operations."

#### Client Sync Mechanisms

##### Sync Trigger
- **❌ Bad approach:** Polling (wasteful, delayed)
- **✅ Good approach:** Long polling or push-based updates

> **Interview Line:** "We use long polling or push-based updates to notify clients instead of frequent polling."

##### Retry Strategy
- Uses exponential backoff for failed requests
- Handles slow or overloaded servers gracefully

> **Interview Line:** "Client uses exponential backoff to handle slow or overloaded servers."

##### Platform Considerations
- **Desktop clients:** Real-time sync enabled
- **Mobile clients:** On-demand sync to save bandwidth and battery

> **Interview Line:** "Mobile clients sync on-demand to save bandwidth and battery."

---

### 2. Metadata Database

#### Core Responsibility

Centralized store for all metadata about files, chunks, users, and devices.

#### Stores
- Files and folders
- Chunk information and mappings
- User accounts and permissions
- Device information
- Workspace structure

#### SQL vs NoSQL Decision

| Aspect | SQL | NoSQL |
|--------|-----|-------|
| **Consistency** | Built-in ACID ✅ | Requires custom logic |
| **Transactions** | Native support ✅ | Limited support |
| **Use Case** | Metadata ✅ | High-volume data |

> **Interview Line:** "Metadata requires strong consistency for correctness, so relational databases are often preferred over NoSQL."

---

### 3. Synchronization Service (The Heart)

#### Core Responsibility

Orchestrates updates across all client devices and maintains consistency.

#### Responsibilities
- Receives updates from clients
- Validates and applies changes to metadata
- Notifies other devices about updates
- Handles conflict resolution

#### Sync Flow

1. Client sends file update
2. Sync service queries metadata DB
3. Validates permissions and conflicts
4. Applies update to metadata DB
5. Notifies other clients of change

#### Optimization Techniques

**Delta Sync:**
- Only transfer changed data
- Reduces bandwidth significantly

**Chunk + Hash:**
- Compare chunks using cryptographic hashes
- Identify exact changes without transferring full files

> **Interview Line:** "We use chunking and hashing to identify and send only changed data, minimizing bandwidth."

#### Scaling Communication

- Use message queues between clients and sync service
- Decouples clients from sync service

> **Interview Line:** "We use messaging middleware to scale synchronization across many clients without overwhelming the sync service."

---

### 4. Message Queue

#### Core Responsibility

Decouple system components and handle asynchronous communication at scale.

#### Why Needed
- **Decouple:** Clients don't directly call sync service
- **Handle Spikes:** Queue absorbs traffic bursts
- **Async Processing:** Enable parallel processing of updates

#### Queue Types

1. **Request Queue:** Client → Sync Service
   - Clients post file updates to queue
   - Sync service processes asynchronously

2. **Response Queue:** Sync Service → Client
   - Per-client notification queue
   - Notifies client of updates from other devices

> **Interview Line:** "We use request queues for updates and per-client response queues for notifications, enabling efficient async communication."

---

### 5. Blob / Object Storage

#### Core Responsibility

Store actual file content chunks (the data plane).

#### What It Stores
- File chunks (broken-down file data)
- Chunk metadata (size, hash)
- Large binary data

#### Architecture Decision

**Separation of Concerns:**
- Metadata DB: Tracks structure and relationships
- Blob Storage: Stores actual content

This separation enables:
- Independent scaling of metadata and storage
- Cost-effective storage tier
- Easy replication and backups

> **Interview Line:** "Blob storage stores chunks, while the metadata DB tracks structure and references, enabling independent scaling."

---

### Key Design Principles

#### Core Ideas (What to Remember)

| Concept | Purpose |
|---------|---------|
| **Chunking** | Send only changed data |
| **Local Metadata** | Enable offline mode + reduce server calls |
| **Sync Service** | Central coordination point |
| **Message Queue** | Decouple and scale system |
| **Metadata ≠ Storage** | Scale independently |

#### Interview Tips

**❌ DON'T:** Start with "Watcher, Indexer, Chunker…" (too detailed too early)

**✅ DO SAY:** "Client detects changes, chunks files, updates local metadata, and syncs via the sync service."

#### One-Line Summary

> "The client detects local changes, splits files into chunks, maintains local metadata for offline support, and syncs updates through a central synchronization service using queues and efficient delta transfer to minimize bandwidth."

---

## 🔄File Processing Workflow

### Overview

The File Processing Workflow illustrates the complete sequence of operations when a client updates a file that is shared with other clients.

### 🛠️ Workflow Steps

#### ✅ Step 1: Client A Uploads Chunks

**What Happens:**
- File is split into chunks (e.g., 4MB chunks)
- Only modified chunks are uploaded to cloud storage

> **Key Idea:** Delta sync - Only changed data is transmitted

#### ✅ Step 2: Update Metadata (CRITICAL STEP)

**What Happens:**
- Client A sends metadata update to metadata database
- Update includes: file version, chunk references, modification timestamp
- Metadata DB update is atomic (all-or-nothing)

> **Key Idea:** Metadata is the source of truth for consistency

#### ✅ Step 3: Sync Service Triggers Notifications

**What Happens:**
- Sync service detects metadata change
- Sends notifications to other clients via:
  - Message queues (asynchronous)
  - Long polling / push mechanisms (real-time)

> **Key Idea:** Other clients are informed of changes

#### ✅ Step 4: Clients B & C Receive and Sync

**What Happens:**
- Clients B and C receive metadata update
- Compare with their local metadata
- Download only missing chunks
- Reconstruct file with updated chunks

#### ✅ Step 5: Offline Client Handling

**If B/C are offline:**
- Update notifications stored in response queues
- When client reconnects, receives all pending notifications
- Downloads all modified chunks
- File system syncs to current state

---

### Workflow Flow Diagram

```
Upload Chunks
     ↓
Metadata Commit (SOURCE OF TRUTH)
     ↓
Sync Service Detects Change
     ↓
Send Notifications (Queue/Push)
     ↓
Clients Fetch & Download
     ↓
Reconstruct File
```

---

### Core Concepts Table

| Concept | Explanation |
|---------|------------|
| **Chunking** | Only changed chunks uploaded (delta sync) |
| **Metadata Commit** | Source of truth for all changes |
| **Async Notifications** | Queue/push-based client notifications |
| **Offline Resilience** | Queued notifications delivered on reconnect |

---

### Interview Answers

**🎯 Quick Answer (30 seconds):**

> "When a file is updated, the client uploads modified chunks and updates metadata (the source of truth). The sync service notifies other clients, who download the missing chunks and reconstruct the file."

**🚀 Full Story (1-2 minutes):**

> "When a client updates a file, it splits it into chunks and uploads only the modified ones to cloud storage. Then it updates the metadata database with the new version and chunk references, which acts as the source of truth. The sync service detects this change and notifies other clients through message queues or push notifications. When other clients receive the update, they fetch the latest metadata, compare with their local state, and download only missing chunks to reconstruct the file. If a client is offline, notifications are stored in queues and delivered when the client reconnects."

**🔥 Pro Tip:**

> "The critical insight is that metadata commit is atomic and acts as the coordination point. This ensures strong consistency before notifying clients."

---

## ♻️deduplication

### 🧠 What is Data Deduplication?

“Avoid storing or sending the same data twice.”

### Core Idea
- Files → split into chunks
- Each chunk → compute hash (e.g., SHA-256)
- If hash already exists:
  - ❌ don’t store again
  - ✅ just reference existing chunk
### 🎯 Why it matters
- Saves storage (huge impact)
- Saves bandwidth (critical for Dropbox)
- Enables faster sync
### ⚙️ Two Approaches
 #### 1️⃣ Post-Process Deduplication
 **How it works**
 1. Store all chunks first
 2. Later background job:
    - scans chunks
    - removes duplicates
- **✅ Pros**
  - Faster uploads (no blocking)
  - Simpler write path
 - **❌ Cons**:
   - Temporary duplicate storage
   - Wasted bandwidth
   - Extra cleanup process
#### 2️⃣ In-Line Deduplication (IMPORTANT):
  **How it works**

   1. Client uploads chunk (or hash first)
   2. System checks hash immediately:
   3. If exists: 
      - only store reference
   4. Else:
       - store new chunk
   - **✅ Pros**:
     - No duplicate storage
     - No duplicate transfer
     - Optimal efficiency
   - **❌ Cons:**
     - Slightly slower writes (hash + lookup)
     - Needs fast hash index
###  🔥 Which one to choose?
👉 Answer in interview:

```
"In-line deduplication is preferred because it avoids duplicate 
storage and reduces bandwidth, which is critical for large-scale systems like Dropbox."
```

**🧠 Key Design Detail (VERY IMPORTANT)**

You need:

**Chunk Hash Index**
- Key: hash
- Value: chunk_id / storage_location

Used to:

- detect duplicates
- retrieve chunks
#### 🔗 How it connects to previous topics
 - Chunking → enables dedup
 - Metadata DB → stores references
 - Storage → stores unique chunks only
 - Workflow → uses dedup before upload

🎯 Short Answer (Interview)

> We use chunk-level deduplication by hashing each chunk and checking if it already exists. If it does, we store only a reference instead of duplicating the data, which significantly reduces storage and bandwidth.


**🚀 Full Interview Story (Strong Answer)**

“To optimize storage and bandwidth, we use chunk-level deduplication. Each file is split into chunks, and a hash is computed for each chunk. When a new chunk arrives, we check if its hash already exists in the system. If it does, instead of storing the chunk again, we simply store a reference to the existing chunk in the metadata. This avoids duplicate storage and reduces network transfer. We typically prefer in-line deduplication so that duplicates are eliminated immediately, although it introduces a small overhead for hash computation and lookup
## 🪓Metadata Partitioning

### What is Metadata Partitioning?

The Metadata DB stores:
- Files
- Chunks
- Users
- Devices
- Workspace information

As scale grows, one database is not enough. We partition/shard metadata across multiple database servers.

---

### Partitioning Strategies

#### 1️⃣ Vertical Partitioning

**Idea:** Split by feature/table type

**Example:**
- User tables → DB 1
- File/chunk tables → DB 2

**✅ Pros:**
- Simple starting point
- Clear ownership by domain

**❌ Cons:**
- Not enough at very large scale
- File/chunk tables may still become too large
- Cross-DB joins become harder and slower

**Interview Line:**
> "Vertical partitioning is a good first step by domain, but it does not fully solve scale for very large metadata tables."

---

#### 2️⃣ Range-Based Partitioning

**Idea:** Split data by some ordered key range

**Example:**
- File paths A–C → shard 1
- File paths D–F → shard 2

**✅ Pros:**
- Easy to understand
- Predictable lookup

**❌ Cons:**
- Uneven distribution
- Hot partitions
- Painful rebalancing later

**Interview Line:**
> "Range partitioning is easy to reason about, but it often creates skew and hotspot problems."

---

#### 3️⃣ Hash-Based Partitioning

**Idea:** Hash a key, then map it to a shard

**Example:**
```
shard = hash(fileId) % N
```

**✅ Pros:**
- Better distribution
- Reduces hotspots compared to range
- Scales better

**❌ Cons:**
- Harder range queries
- Shard movement is painful if using simple modulo hashing

**Interview Line:**
> "Hash partitioning is usually the better default for metadata because it distributes load more evenly."

---

#### 4️⃣ Consistent Hashing

**Why needed:**
- With plain hash % N, adding/removing shards moves a lot of data
- Consistent hashing reduces movement when cluster size changes

**Interview Line:**
> "If shard membership changes frequently, consistent hashing helps rebalance with less data movement."

---

### What Key Should We Shard On?

**This is the important part.**

Common choices for Dropbox metadata:
- userId
- workspaceId
- fileId

**Best practical answer:**
> "Shard metadata by userId or workspaceId, because most file operations are scoped to a user or shared workspace."

**Why this is strong:**
- Many queries are user/workspace centered
- Reduces cross-shard access
- Aligns with access pattern

---

### Recommendation

**Start with:**
1. Vertical partitioning by domain
2. For large metadata tables, use hash-based sharding
3. Prefer shard key like userId/workspaceId
4. Use consistent hashing or shard map for rebalance

**Short answer for interview:**
> "To scale metadata, I would not rely only on vertical partitioning because file and chunk tables can still become massive. Range partitioning is predictable but can create skewed partitions. A better default is hash-based partitioning using a key like userId, workspaceId, or fileId, because it distributes load more evenly. If shards need to be added dynamically, consistent hashing helps reduce rebalancing cost."

**One-paragraph interview story:**
> "To scale the metadata database, I would partition it across multiple servers. Vertical partitioning can separate domains like users and files, but by itself it is not enough because file and chunk metadata can still grow too large. Range-based partitioning is simple but tends to create unbalanced shards and hotspots. My preferred approach is hash-based partitioning, typically using a key such as userId or workspaceId, since most metadata operations are scoped around a user or shared folder. This gives more even distribution and better scalability, and if shard membership changes over time, consistent hashing or a shard map can reduce rebalancing cost."

**One-line summary:**
> "For Dropbox metadata, hash-based sharding by access pattern is usually the safest default."

---
## 🧊Caching
### What caching means in Dropbox

Caching is used to avoid hitting slower backends every time.

In this system, we mainly cache two things:

- **Hot file chunks before block/blob storage**
- **Hot metadata before metadata DB**
### 1. Chunk Cache
   **What is cached**
   - actual file chunks
   - key = chunkId or chunkHash
   - value = chunk data

 **Why**
     Some chunks are requested again and again:
  
  - popular shared files
  - same file synced to multiple devices
  - repeated downloads
   
 Instead of reading from block storage every time, block servers first check cache.
    
  **Read flow**
    - request comes for chunk
      - block server checks cache
      - if hit → return chunk fast
      - if miss → fetch from block storage and put in cache
### 2. Metadata Cache
   **What is cached**
   - file metadata
   - folder metadata
   - version info
   - chunk mapping
   - permissions

 **Why**

  Almost every sync operation reads metadata first, so metadata cache reduces DB load and improves latency.

### Why chunk cache matters

Given your chunk size is 4MB, if one cache server has 144GB RAM:
$$144\text{GB} \div 4\text{MB} \approx 36\text{K chunks}$$

So one such server can cache about 36,000 chunks.

That number is just a capacity estimate.

### Cache replacement policy
**Best default: LRU**

When cache is full:
- evict the chunk that has not been used for the longest time
Why LRU fits here

Because recently accessed chunks are more likely to be accessed again.

This is a practical default for:
- hot chunks
- hot metadata
### 🗣️ What to say in interview


> “I would use two caches: a chunk cache in front of block storage for hot file chunks, and a metadata cache in front of the metadata database. Chunk cache reduces repeated reads from storage, while metadata cache speeds up sync and lookup operations. LRU is a reasonable eviction policy because recently accessed data is more likely to be reused.”

### 📝 One-Paragraph Interview Story

> “To improve performance, I would add two caching layers. First, a chunk cache in front of block storage to keep hot file chunks in memory, using chunk IDs or hashes as cache keys. This reduces read latency and avoids repeatedly hitting durable storage for frequently downloaded or synced data. Second, I would add a metadata cache in front of the metadata database, since sync operations frequently read file metadata, chunk mappings, versions, and permissions. For eviction, LRU is a practical choice because recently accessed chunks and metadata are most likely to be requested again.”

### 🔥What you should remember
    
- **Main idea**
  - metadata cache speeds up control path
  - chunk cache speeds up file reads
- **Eviction**: LRU
- **Cache key**:
    - chunk cache → `chunkId/hash`
    - metadata cache → `fileId/path/userId` depending on item

### 🧷 One-line Memory Hook

> “Cache hot metadata to protect the DB, and cache hot chunks to protect storage.”
---
## 🔀load-balancer
###  Where do we put LB?

Two main places:
- Clients → Block servers
- Clients → Metadata servers

That means LB helps distribute:
- file/chunk traffic
- metadata/sync traffic

### 🥉**Basic option: Round Robin**

**How it works**
- send requests evenly across servers

**✅ Pros:**
- simple
- easy to implement
- low overhead
- if a server is down, remove it from rotation

**❌ Cons:**
- ignores actual server load
- may still send traffic to a slow or overloaded server

### 🏆**Better option: Load-aware balancing**
**How it works**

LB checks:
- server health
- CPU/load
- latency
- active connections

Then routes more traffic to healthier/less busy servers.

**Why better**

Because Dropbox traffic is not uniform:
- some block servers may get hot chunks
- some metadata servers may be busier than others

### 🗣️ What to say in interview
>“I would start with round robin for simplicity, but at scale I’d prefer load-aware balancing because request cost is not uniform and some servers may become overloaded.”

### 📝 One-Paragraph Interview Story

>“I would place load balancers in front of both metadata servers and block servers. A simple round robin policy is a good starting point because it is easy to implement and can route around failed servers. However, since request cost is not uniform, especially for large file transfers and hot metadata traffic, I would evolve to a load-aware strategy that considers server health, latency, and current load before routing requests.”

## 🔐Security, Permission and File Sharing
**Main concern**

Users want:
- privacy
- controlled sharing
- secure storage/access
 ### Where do we store permissions?
In the **metadata DB**

Examples:
- owner
- read access
- write access
- shared with specific users
- public/private flag

**Why metadata DB?**

Because permissions are part of file metadata and must be checked before:
- reading
- updating
- sharing
- deleting

**What should happen on access?**

Before serving metadata or file chunks:
- authenticate user
- authorize request using metadata permissions

### Types of sharing
- private file
- shared with selected users
- shared folder/workspace
- public link

### 🗣️ What to say in interview

>“Permissions should be stored in the metadata database and checked on every access request. This allows us to support private files, user-to-user sharing, shared folders, and public links while keeping access control centralized.”

### 📝 One-Paragraph Interview Story

>“Security and sharing are primarily enforced through metadata. For each file or folder, the metadata database stores ownership and permission information such as who can read, write, or share the object. When a user requests access, the system first authenticates the user and then checks authorization against this metadata before allowing access to either metadata or file chunks. This design supports private content, shared folders, and public sharing links while keeping access control centralized and consistent.”


##  💬Q&A:

#### ❓ How does Dropbox client work?

> "The client monitors the local file system for changes, splits files into chunks, updates local metadata, and communicates with the sync service to upload or download only the required data."

#### ❓ How do you sync files?
  
> "A synchronization service coordinates updates by receiving changes from one client, updating metadata, and notifying other clients using push mechanisms like long polling or messaging queues."

#### ❓ How do you reduce bandwidth?

> "We use chunking and delta sync—files are split into chunks and only modified chunks are transferred. We also use hashing to avoid re-uploading existing data."

#### ❓ How do you handle offline updates?

> "The client maintains a local metadata store, allowing users to make changes offline. Once back online, it syncs changes with the server and resolves conflicts if needed."



## Complete Interview Narrative

