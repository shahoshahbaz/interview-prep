# Designing Typeahead Suggestion System

## Table of Contents
<div style="font-size: 20px; line-height: 1.8em;">

1. 📌[problem statement](#problem-statement)
2. [Basic System Design and Algorithm](#basic-system-design-and-algorithm)
3. [Permanent Storage of the Trie](#permanent-storage-of-the-trie)
4. [Scale Estimation](#scale-estimation)
5. [Data Partition](#data-partition)
6. [Cache](#cache)
7. [Replication and Load Balancer](#replication-and-load-balancer)
8. [Fault Tolerance](#fault-tolerance)
9. [Typeahead Client](#typeahead-client)
10. [Personalization](#personalization)
</div>

##  📌problem statement
Design a Typeahead Suggestion System similar to what you see in:

- Google search bar
- Amazon product search
- YouTube search suggestions
### 🎯 Objective

When a user starts typing a query, the system should:

- Return relevant suggestions in real-time as each character is typed.

##### Example:
```
User types: "ap"

System returns:
- apple
- app store
- application form
- apex legends
```
## Requirements and Goals of the System
### Functional Requirements
These are what the system must do.

 - **Core functional requirements:**
   - User types a prefix
   - System returns top N suggestions
   - Suggestions update after each keystroke
   - Suggestions are ordered by some ranking logic
   - Matching should be based on the typed prefix
   - Possible extended functional requirements

### Non-Functional Requirements

These are how the system performs its functions.
- **Latency:**
  - Suggestions must be returned very fast
  - Usually target:< 100 ms
  - ideally 50 ms-ish end-to-end
- **Availability:**
  - Search box is user-facing and frequent
  - System should have high availability

- **Scalability:**
  - Must handle very large QPS
  - Many users type simultaneously
  - One search session can generate many requests because each keystroke may trigger one
- **Relevance:**
  - Results should not just match prefix
  - They should be useful and ranked well
- **Freshness:**
  - Popular/trending queries may change
  - Suggestion ranking should be updated reasonably fast
- **Consistency:**
  - Strong consistency is usually not required
  - Eventual consistency is often acceptable for rankings / popularity updates

### 🗣️ what to say in interview
>For functional requirements, the system should provide real-time suggestions as the user types in the search box. Given a prefix, it should return the top N relevant suggestions, and update results on every keystroke. The suggestions should be ranked based on factors like popularity, recency, or personalization. Optionally, we could support features like typo tolerance, multi-language input, and filtering of inappropriate content.

> For non-functional requirements, the most critical goal is very low latency—ideally under 100 milliseconds—since this is a user-facing, interactive feature. The system should be highly available and scalable to handle a large number of concurrent users, making it a read-heavy system. We can relax consistency requirements and allow eventual consistency for updating rankings or trends. Overall, the focus is on fast, relevant, and scalable suggestion retrieval.
## 📊 BOE (Back-of-the-envelope)
#### what to say in interview
> “Before jumping into the design, I want to do a quick back-of-the-envelope estimation so I can understand the expected read volume, peak traffic, and approximate data size. Since typeahead is triggered on each keystroke, even a moderate number of users can generate very high QPS, so these numbers will directly influence caching, storage choice, and partitioning.”
Assumptions:
- 1 billion users
### Traffic Estimation

## Basic System Design and Algorithm
## Permanent Storage of the Trie
## Scale Estimation
## Data Partition
## Cache
## Replication and Load Balancer
## Fault Tolerance
## Typeahead Client
## Personalization