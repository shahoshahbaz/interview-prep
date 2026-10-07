# <System name> — Mock Interview Quick Ref
*Use this to self-quiz or run a mock interview. Cover the right-hand/answer content, say it out loud, then check. Target: talk through the whole sheet in ~15 min.*

---

## Table of Contents
1. [60-Second Pitch](#1-60-second-pitch)
2. [Requirements (memorize these)](#2-requirements-memorize-these)
3. [Capacity Numbers (memorize these)](#3-capacity-numbers-memorize-these)
4. [HLD — Draw This](#4-hld--draw-this)
5. [API + Idempotency](#5-api--idempotency)
6. [Data Model / DB Choice](#6-data-model--db-choice)
7. [Deep-Dive Rapid Fire](#7-deep-dive-rapid-fire)
8. [Failure Scenarios](#8-failure-scenarios)
9. [Trade-off Questions](#9-trade-off-questions)
10. [Curveballs](#10-curveballs)

---

## 1. 60-Second Pitch
**Prompt:** *"Design \_\_\_."*
One or two sentences you can say immediately, cold, before asking any clarifying questions.

## 2. Requirements (memorize these)
**Func:**
**NFR:** scale | availability | latency | consistency | retention

## 3. Capacity Numbers (memorize these)
- Write QPS: **_/s** avg → **_/s** peak (3x)
- Read QPS: **_/s** avg → **_/s** peak (3x)
- Total objects / storage: **_**
- Cache size: **_**
- Any magic constant specific to this system (e.g. short-code length, shard count):

## 4. HLD — Draw This
```
Client → ... → ...
```
**Trigger line:** one sentence that states the single most important design decision (e.g. "keep X off the hot path").

## 5. API + Idempotency
- `METHOD /path` → status code, idempotency mechanism

## 6. Data Model / DB Choice
**Choice:** SQL vs NoSQL — one-line reason
**Key tables/entities:**
**Partitioning key:**

## 7. Deep-Dive Rapid Fire
Pick the 3-4 components most likely to be probed. One Q + one crisp answer each.

**Q:**
**A:**

**Q:**
**A:**

**Q:**
**A:**

## 8. Failure Scenarios
| Interviewer asks | Your answer |
|---|---|
| "What if a node crashes?" | |
| "What if the cache/queue goes down?" | |
| "What if two writes race?" | |
| "What if traffic spikes 10x?" | |

## 9. Trade-off Questions
**Q: Why X over Y?**
**A:**

**Q: What do you give up with this design?**
**A:**

## 10. Curveballs
Questions that test understanding vs. memorization — things the interviewer asks to see if you can extend the design live.
-
-

---
### If stuck, ask yourself:
1. Read or write heavy path? → which tier needs scaling
2. What's the one thing that makes this system hard? → lead with that
3. What did I just say that I can't defend? → preempt it in trade-offs
