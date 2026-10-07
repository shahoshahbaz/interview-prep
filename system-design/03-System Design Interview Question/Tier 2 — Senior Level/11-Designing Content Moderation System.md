Table of Contents
Problem Statement
Functional vs Non-functional requirements
BOE (Back-of-the-Envelope) Scale Estimation
High-Level Architecture
Component Deep-Dive
API Gateway
Sync Pre-Publish Filter (Tier 1)
Message Queue
ML Scoring Pipeline (Tier 2)
Decision Engine
Human Review Queue (Tier 3)
Appeals Service
Audit Log
Data Model
Key Design Tradeoffs
Scalability
Failure Modes & Mitigations
How the System Evolves Over Time
1. Problem Statement
   Design a content moderation service for a text-based social media platform (like Twitter) that automatically detects and prevents toxic, spammy, and harmful posts from being published or surfaced to other users.

2. Functional vs Non-functional requirements
   Functional Requirements:
   Users submit text posts; the system evaluates each post for harmful content Automated actions: allow, shadowban, or remove based on content signals
   Human moderators can review borderline cases and override automated decisions
   Users can appeal a moderation decision
   All moderation actions are logged for audit and compliance
   Non-functional Requirements:
   Post submission latency under 100ms — moderation must not block the user
   High throughput — millions of posts per day
   High availability — moderation failure must not take down publishing
   Decisions must be explainable and traceable for regulatory compliance
   System improves over time via moderator feedback
3. BOE (Back-of-the-Envelope) Scale Estimation
   Assumptions:
   Traffic

10M posts/day Peak = 4× average 24/7 global platform

Content

Avg post size: ~500 bytes 5% flagged for human review 1% auto-removed

ML pipeline

~200ms per inference Results within 5 seconds of publish

Human review

200 posts/hour per moderator 3 shifts/day (24/7 coverage)

Storage

Post + metadata: ~700 bytes/post Audit log: ~300 bytes/post Retention: 3 years

calculations:
Throughput:

Average: 10M (posts/day) ÷ 86,400 (sec/day) = ~115 posts/sec Peak: 120 × 4 = ~500 posts/sec

ML workers needed at peak 500 posts/sec (peak) × 0.2s/post (ML inference) = 100 worker-seconds/sec

Human moderators needed:

10M × 5% flagged = 500K posts/day to review 500K ÷ 200 posts/hr = 2,500 moderator-hours/day 2,500 ÷ 8hr shift = ~315 moderators across 3 shifts

Storage per day Posts: 10M × 700 bytes(post + metadata) = ~7 GB/day Audit log: 10M × 300 bytes = ~3 GB/day Total: = ~10 GB/day Storage over 3 years 10 GB × 365 × 3 = ~11 TB
4. High-Level Architecture
   flowchart TD
   A["**Client** *(mobile/web)*\nPOST /tweets"]
   B["**API Gateway**\nAuth · rate limiting · routing"]
   C["**Synchronous Pre-publish Filter**\nRegex blocklist · spam signals · rate abuse"]
   D["**Reject**\n4xx response"]
   E["**Message Queue**\nKafka / SQS — async fan-out"]
   F["**ML Scoring Workers**\nToxicity · spam · PII · NSFW\nReturns confidence score 0–1"]
   G["**Human Review Queue**\nScore 0.4–0.7 *(uncertain)*\nRouted to moderators"]
   H["**Decision Engine**\nscore > 0.9 → remove · 0.7–0.9 → hide · < 0.4 → publish"]
   I["**Content Store** *(DB)*\nPost · status · user_id"]
   J["**Audit Log**\nImmutable · reason · actor"]
   K["**Appeals Service**\nUser-initiated review"]

A --> B
B --> C
C -- reject --> D
C -- pass --> E
E --> F
E --> G
F --> H
H --> I
G --> J
K --> J
Component Summary
Component	Role
Client	Mobile/web app that submits content via POST /tweets
API Gateway	Handles authentication, rate limiting, and routing
Sync Pre-publish Filter	Fast, synchronous checks: regex blocklist, spam signals, rate abuse
Reject	Returns a 4xx error immediately for blocked content
Message Queue	Kafka or SQS — decouples publishing from async ML processing
ML Scoring Workers	Runs toxicity, spam, PII, and NSFW models; outputs a 0–1 confidence score
Human Review Queue	Catches uncertain scores (0.4–0.7) and routes them to human moderators
Decision Engine	Applies thresholds: score > 0.9 → remove · 0.7–0.9 → hide · < 0.4 → publish
Content Store	Persistent DB storing post content, moderation status, and user ID
Audit Log	Immutable record of every moderation action with reason and actor
Appeals Service	Allows users to request a human review of removal decisions
Score Thresholds
Score Range	Action
> 0.9	Remove — high-confidence violation
0.7 – 0.9	Hide — likely violation, pending review
0.4 – 0.7	Human review — uncertain, routed to moderators
< 0.4	Publish — low risk, passes through
5. Component Deep-Dive
   API Gateway
   API Gateway responsible for:

Authentication: Verifies user identity and permissions
Rate Limiting: Prevents abuse by limiting requests per user/IP
Routing: Forwards valid requests to the Sync Pre-publish Filter
Metrics & Logging: Tracks request volumes, latencies, and errors for monitoring
Storage: Redis for block list and rate limiting counters.
Sync Pre-Publish Filter (Tier 1)
What it is?

The last checkpoint before a post is saved. Still on the synchronous path — user is still waiting.

What it does:

Blocklist check — exact-match lookup against a set of banned terms/phrases stored in Redis. O(1) lookup, sub-millisecond
Regex patterns — catches known toxic patterns (slurs, threat templates) that a blocklist alone misses
Spam signals — duplicate post detection, too many URLs, suspicious character patterns
Key point for the interview: This layer only catches the obvious violations. It is intentionally simple — no ML, no heavy compute. The goal is to keep it under 10ms so the user's 100ms budget isn't blown here.

What it returns:
Violation found → 400 or 422 with a reason, post is never saved Clean → post is saved to the content store and handed off to the message queue

Storage: Redis for block list
Message Queue
What it is: The handoff point between the synchronous write path and the async moderation pipeline.

What it does:

Receives a message for every post that passes the sync filter
Fans the message out to downstream consumers (ML workers, logging)
Decouples publishing from scoring — the user gets their response immediately, scoring happens in the background
Key point for the interview:

This is the architectural decision that lets you meet the 100ms latency requirement. Without the queue, you'd have to wait for ML inference before responding to the user. With it, you publish first and moderate asynchronously.

Why Kafka over SQS:

Kafka → better for high throughput, replay, and multiple independent consumers
SQS → simpler, fully managed, good enough for lower scale
At 500 posts/sec peak, either works — Kafka gives you more control. -Tradeoff to mention:
Async moderation means a brief window where a harmful post is technically live. You accept that tradeoff in exchange for low latency.
The sync filter reduces that window by catching the worst content upfront.
Storage: Kafka topic or SQS queue with appropriate retention and scaling settings.

ML Scoring Pipeline (Tier 2)
What it is: The brain of the moderation system. Consumes posts from the queue and scores them.

What it does:

Pulls posts from the message queue
Runs one or more classifiers against the post text:
Toxicity — hate speech, threats, harassment
Spam — repetitive content, phishing links, bot patterns
NSFW — sexually explicit language
PII — accidental exposure of sensitive personal data
Returns a confidence score between 0 and 1 for each signal
Combines signals into a single score (e.g. take the max, or a weighted ensemble)
Key point for the interview: Workers are stateless — they just take a post in, return a score out. This makes them trivially horizontally scalable. At peak (500 posts/sec, 200ms inference) you need ~100 concurrent workers, and you auto-scale around queue depth.

Tradeoff to mention: Running multiple classifiers per post increases accuracy but also increases latency and cost. You tune this based on your SLA — if results must land within 5 seconds of publish, you have budget for 2–3 models in parallel.

Storage: none - workers are stateless.

Decision Engine
Human Review Queue (Tier 3)
What it is:
Where the gray zone lands. Posts the ML wasn't confident enough to auto-decide.

What it does:
Receives posts scored between 0.4–0.7 from the decision engine
Presents each post to a moderator with full context:
The post text
The ML confidence score
The reason category (toxicity, spam, etc.)
The user's moderation history
Moderator makes a final decision: approve, remove, or escalate
Key point for the interview:
Human review is expensive — recall from the BOE we estimated ~315 moderators needed. So you want to minimize what lands here. A well-tuned ML model should keep the 0.4–0.7 band as narrow as possible.

Tradeoff to mention:
Moderator decisions are your most valuable asset — they are labeled ground truth. Every override a moderator makes should be captured and fed back into ML retraining. Without this feedback loop the model drifts over time.
Operational concern to mention: Moderator wellbeing. Reviewing toxic content at scale is psychologically damaging. Real systems rotate moderators, limit daily exposure hours, and provide mental health support. Worth a sentence in an interview — shows maturity.
Appeals Service
What it is: The mechanism that gives users recourse when they believe a moderation decision was wrong.

What it does:

User receives a removal notification with a reason and an appeal link
User submits an appeal with their explanation
System automatically attaches:
The original post
The ML score and reason
The moderation action taken
Appeal gets routed back into the human review queue with elevated priority
Moderator reviews and either reinstates or upholds the removal
User is notified of the outcome
Key point for the interview: Appeals are not just a UX nicety — in many jurisdictions (EU's DSA for example) platforms are legally required to offer an appeals mechanism. At RBC, compliance would flag this as mandatory.

Tradeoff to mention: You need to prevent appeal abuse — a bad actor who just re-appeals every removal. Common mitigations are a cooldown period between appeals, or a limit on appeals per account per month.

Audit Log
What it is: The immutable record of every moderation action taken in the system. What it does:

Records every decision made — by the ML pipeline, decision engine, or a human moderator
Each entry captures:
The post ID
The action taken (publish, shadowban, remove)
The reason/category
The confidence score
The actor (ML model version or moderator ID)
The timestamp
Key point for the interview: The audit log must be append-only — no updates, no deletes. Once written, a record cannot be changed. This is what makes it trustworthy for compliance and legal purposes. Why it matters at RBC specifically: Financial institutions operate under strict regulatory frameworks. The same mindset applies to a platform moderation system — you must be able to prove what decision was made, when, why, and by whom. The audit log is how you do that. Tradeoff to mention: You could write audit entries to your main database, but a dedicated append-only store (like a Kafka topic with long retention, or a write-once object store like S3) is more appropriate — it's cheaper to store at scale and harder to accidentally mutate.

6. Data Model
   Posts : post_id (PK), user_id (FK), content, status, created_at
   Moderation decisions: decision_id (PK), post_id (FK), score, action, reason, actor, created_at
   Appeals: appeal_id (PK), post_id (FK), user_id (FK), explanation, status, resolved_a
7. Key Design Tradeoffs
   Sync vs async moderation

Doing everything inline is simple but too slow (ML inference blocks the user)
Two-phase approach: sync gate for obvious violations, async ML for nuance
Tradeoff: Brief window where harmful content is live; sync gate minimizes this
Shadowban vs hard remove

Hard remove is confrontational and irreversible
Shadowban is softer, reversible, and better for uncertain signals
Tradeoff: Shadowban can feel deceptive to users if overused
Threshold calibration

Where you set 0.4 / 0.7 / 0.9 is a product + compliance decision, not just technical
Lower thresholds mean fewer harmful posts but more false positives (wrongly silenced users)
At RBC, compliance and legal own this decision
Database choices

PostgreSQL for posts, decisions, appeals: relational and strongly consistent
Redis for blocklist and rate limiting: in-memory and sub-millisecond
Kafka for queue and audit log: high throughput, append-only, replayable
Tradeoff: More moving parts, but each store is optimized for its access pattern
Human review as a feedback loop

Moderator overrides are labeled ground truth
Without feeding them back into ML retraining, the model drifts over time
Tradeoff: Adds operational complexity but is essential for long-term accuracy
8. Scalability
   API gateway

Stateless; scales horizontally behind a load balancer
Rate-limit counters live in Redis and are shared across all gateway instances
Sync filter

Stateless workers; scale horizontally
Blocklist stored in Redis with replication to avoid a single point of failure
Message queue (Kafka)

Partition posts.raw by user_id to preserve per-user ordering
Add partitions as throughput grows (no downtime)
Consumer groups enable independent scaling of ML workers
ML scoring workers

Stateless; scale horizontally based on queue depth
At peak (500 posts/sec, 200ms inference): about 100 workers
Auto-scale up on queue lag thresholds; scale down during off-peak
Decision engine

Stateless and horizontally scalable
Uses PostgreSQL connection pooling (PgBouncer) to reduce DB bottlenecks
PostgreSQL

Read replicas serve moderation dashboard traffic (read-heavy workload)
Partition moderation_decisions by created_at for better query performance
Archive old records to S3 after the retention window
Audit log

Kafka with long retention handles high write throughput
Offload to S3 after 30 days for cheap, durable cold storage (query via Athena)
9. Failure Modes & Mitigations
   ML scoring service goes down

Risk: Posts queue up, and no moderation decisions are made
Mitigation: Use a circuit breaker; if ML workers are unavailable, route posts to the human review queue as fallback
Note: Posts are not lost because Kafka retains them until workers recover
Kafka goes down

Risk: Entire async pipeline stops
Mitigation: Run Kafka with replication factor 3 so a single broker failure does not bring the cluster down
Fallback: In catastrophic failure, rely on the sync filter as the temporary gate
Decision engine goes down

Risk: Scored posts sit without actions
Mitigation: Keep the service stateless for fast restart; Kafka retention prevents message loss during recovery
PostgreSQL goes down

Risk: Cannot write decisions or update post status
Mitigation: Use primary-replica with automatic failover (for example, AWS RDS Multi-AZ)
Redis goes down

Risk: Blocklist unavailable and rate limiting disabled
Mitigation: Use Redis Sentinel or Redis Cluster for high availability; if fully unavailable, fail open to keep publishing online
Human review queue backs up

Risk: Borderline content stays live too long
Mitigation: Set an SLA on queue depth; alert when backlog exceeds 4 hours and page on-call moderators
10. How the System Evolves Over Time
    ML model improves

Every human moderator override becomes labeled training data
Retrain periodically (weekly or monthly) using accumulated overrides
Over time, the 0.4-0.7 gray zone shrinks, so fewer posts need human review
Track model drift with offline evaluation before deploying new versions
Blocklist grows

New slurs, toxic patterns, and spam templates emerge constantly
Maintain a pipeline for moderators to flag new terms directly into the blocklist
Automate blocklist updates without redeployment; Redis supports live updates
Thresholds get tuned

As data accumulates, false positive and false negative rates can be measured precisely
Adjust thresholds based on real-world outcomes and regulatory feedback
In RBC context, compliance reviews may trigger threshold changes after incidents
New signal types added

Start with toxicity and spam
Add signals over time (coordinated inauthentic behavior, bot detection, URL reputation)
Each new signal plugs into the decision engine without changing the architecture
Moderator tooling matures

Early: simple queue with approve/reject
Later: moderator dashboard with user history, appeal context, and similar past cases
Eventually: ML-assisted review where the model surfaces relevant context to speed decisions
Regulatory requirements evolve

EU DSA, Canadian CRTC, and similar frameworks add obligations over time
Audit log and appeals service already exist, so compliance additions remain additive, not architectural