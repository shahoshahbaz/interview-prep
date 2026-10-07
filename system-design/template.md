# <System name>

## Table of Contents
1. [Problem Statement](#1-problem-statement)
2. [Requirements](#2-requirements)
3. [Back-of-the-Envelope Estimates](#3-back-of-the-envelope-estimates)
4. [High-Level Architecture](#4-high-level-architecture)
5. [API Design](#5-api-design)
6. [Data Model](#6-data-model)
7. [Deep Dives](#7-deep-dives)
8. [Trade-offs & Failure Modes](#8-trade-offs--failure-modes)

---

## 1. Problem Statement
- One or two sentences: what are we building, and for whom?
- Scope boundary: what's explicitly out of scope for this interview?

## 2. Requirements

### Functional
-

### Non-functional
- Scale (users/DAU, QPS):
- Latency target:
- Availability (SLA, e.g. 99.9%):
- Consistency model (strong / eventual):
- Durability / retention:

## 3. Back-of-the-Envelope Estimates
- Traffic: QPS (avg/peak, read:write ratio)
- Storage: total objects × size × retention, with replication overhead
- Bandwidth: ingress/egress
- Cache sizing (if applicable)

## 4. High-Level Architecture
- Diagram (ASCII) of major components and data flow:
```
Client → LB/Gateway → Service(s) → Cache / DB / Queue
```
- Call out the 2-3 components most likely to be probed in deep dives.

## 5. API Design
- Endpoints (method, path, request/response shape)
- Idempotency / auth / pagination notes

## 6. Data Model
- Entities & key fields
- Storage choice (SQL vs NoSQL) + why
- Partitioning / indexing strategy

## 7. Deep Dives
- Pick 2-4 components and go deep: bottlenecks, scaling strategy, consistency/replication, specific algorithms.

## 8. Trade-offs & Failure Modes
- Key design decisions and what was given up (CAP-style trade-offs)
- What happens when a component fails? (node crash, network partition, cache miss storm, etc.)
