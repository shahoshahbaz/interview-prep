# System Design Notes

Markdown only. Suggested layout: one file per topic.

- `template.md`  : full write-up — problem statement, requirements, BOE estimates, HLD, API, data model, deep dives, trade-offs
- `template-QuickRef.md`  : condensed mock-interview drill sheet — pitch, memorized numbers, HLD sketch, rapid-fire Q&A, failure modes, curveballs
- add `url-shortener.md`, `rate-limiter.md`, ... as you go (pair each with a `-QuickRef.md` for review)

## Interview Question Index

All questions live under [`03-System Design Interview Question/`](<03-System Design Interview Question>), organized into 5 tiers. ✅ = fully written up, 📝 = placeholder outline still needs a full write-up.

### Tier 1 — Must Know

| # | Topic | Why | Status | File |
|---|-------|-----|--------|------|
| 1 | URL Shortener | Asked everywhere, teaches hashing, redirects, DB design | ✅ | [01-Design shortening URL.md](<03-System Design Interview Question/Tier 1 — Must Know/01-Design shortening  URL.md>) |
| 2 | Rate Limiter | Universal, teaches Redis, sliding window, distributed state | ✅ | [00-Design a Distributed Rate Limiter.md](<03-System Design Interview Question/Tier 1 — Must Know/00-Design a Distributed Rate Limiter.md>) |
| 3 | Twitter / Newsfeed | Classic feed design, teaches fan-out, caching, ranking | ✅ | [03-Designing Twitter (Newsfeed).md](<03-System Design Interview Question/Tier 1 — Must Know/03-Designing Twitter (Newsfeed).md>) |
| 4 | Notification Service | Asked at almost every company, teaches async, queues, multi-channel | 📝 | [06-Design Notification Service.md](<03-System Design Interview Question/Tier 1 — Must Know/06-Design Notification Service.md>) |
| 5 | Typeahead Suggestion | Very common, teaches trie, search, low latency reads | ✅ | [04-Designing Typeahead Suggestion.md](<03-System Design Interview Question/Tier 1 — Must Know/04-Designing Typeahead Suggestion.md>) |
| 6 | Dropbox / File Storage | Teaches chunking, CDN, blob storage, sync | ✅ | [02-Design Dropbox.md](<03-System Design Interview Question/Tier 1 — Must Know/02-Design Dropbox.md>) |
| 7 | Unique ID Generator | Simple but frequently asked, teaches distributed coordination | ✅ | [05-Unique ID Generator.md](<03-System Design Interview Question/Tier 1 — Must Know/05-Unique ID Generator.md>) |

### Tier 2 — Common at Senior Level

| # | Topic | Why | Status | File |
|---|-------|-----|--------|------|
| 8 | Web Crawler | Teaches BFS, distributed workers, deduplication | 📝 | [04-Designing a Web Crawler.md](<03-System Design Interview Question/Tier 2 — Senior Level/04-Designing a Web Crawler.md>) |
| 9 | Twitter Search | Teaches inverted index, Elasticsearch, ranking | 📝 | [05-Designing Twitter Search.md](<03-System Design Interview Question/Tier 2 — Senior Level/05-Designing Twitter Search.md>) |
| 10 | YouTube / Netflix | Teaches video processing, CDN, streaming, recommendation | 📝 | [06-Designing YouTube-Netflix (Video Streaming).md](<03-System Design Interview Question/Tier 2 — Senior Level/06-Designing YouTube-Netflix (Video Streaming).md>) |
| 11 | Instagram | Teaches CDN, blob storage, feed, photo processing pipeline | ✅ | [02-Designing Instagram.md](<03-System Design Interview Question/Tier 2 — Senior Level/02-Designing Instagram.md>) |
| 12 | Messenger (WhatsApp) | Teaches WebSocket, presence, message ordering, delivery guarantees | ✅ | [03-Designing Facebook Messenger (WhatsApp).md](<03-System Design Interview Question/Tier 2 — Senior Level/03-Designing Facebook Messenger (WhatsApp).md>) |
| 13 | Ticketmaster | Teaches concurrency, seat locking, high-demand bursts | 📝 | [07-Designing Ticketmaster (Ticket Booking).md](<03-System Design Interview Question/Tier 2 — Senior Level/07-Designing Ticketmaster (Ticket Booking).md>) |
| 14 | Uber Backend | Teaches geospatial, matching, real-time location tracking | 📝 | [08-Designing Uber Backend.md](<03-System Design Interview Question/Tier 2 — Senior Level/08-Designing Uber Backend.md>) |
| 15 | Pastebin | Simple CRUD + expiry + storage, good warm-up topic | ✅ | [01-Designing Pastebin.md](<03-System Design Interview Question/Tier 2 — Senior Level/01-Designing Pastebin.md>) |
| 16 | Yelp / Nearby Friends | Teaches geospatial indexing, quadtree, proximity search | 📝 | [09-Designing Yelp (Nearby Friends-Proximity Search).md](<03-System Design Interview Question/Tier 2 — Senior Level/09-Designing Yelp (Nearby Friends-Proximity Search).md>) |
| 17 | Recommendation System | Increasingly asked at Netflix, Amazon, LinkedIn | 📝 | [10-Designing a Recommendation System.md](<03-System Design Interview Question/Tier 2 — Senior Level/10-Designing a Recommendation System.md>) |

### Tier 3 — Fintech Specific ✅ Your Strength

| # | Topic | Why | Status | File |
|---|-------|-----|--------|------|
| 18 | Payment Processing | Core fintech, state machine, idempotency | ✅ | [01-Design Payment Processing System.md](<03-System Design Interview Question/Tier 3 — Fintech Specific/01-Design Payment Processing System.md>) |
| 19 | Digital Wallet | Core fintech, balance management, ledger | ✅ | [03-Digital Wallet design.md](<03-System Design Interview Question/Tier 3 — Fintech Specific/03-Digital Wallet design.md>) |
| 20 | Transaction Ledger | Double-entry, immutability, concurrency | ✅ | [02-Transaction Ledger -Double-Entry Ledger.md](<03-System Design Interview Question/Tier 3 — Fintech Specific/02-Transaction Ledger -Double-Entry Ledger.md>) |
| 21 | Credit Card Authorization | Sub-100ms, card networks, fraud check | ✅ | [04-Designing Credit Card Authorization.md](<03-System Design Interview Question/Tier 3 — Fintech Specific/04-Designing Credit Card Authorization.md>) |
| 22 | Fraud Detection | Rules engine, ML scoring, real-time decisions | ✅ | [05-Designing Fraud Detection System.md](<03-System Design Interview Question/Tier 3 — Fintech Specific/05-Designing Fraud Detection System.md>) |
| 23 | Chargeback / Dispute System | Asked at Nubank, extends payment knowledge | ✅ | [08-Designing_Chargeback_System.md](<03-System Design Interview Question/Tier 3 — Fintech Specific/08-Designing_Chargeback_System.md>) |
| 24 | Idempotent API | Critical for payments, prevents double charge | ✅ | [06- Idempotent API System.md](<03-System Design Interview Question/Tier 3 — Fintech Specific/06- Idempotent API System.md>) |

Bonus: [07-Design P2P Payment Engine (e-Transfer).md](<03-System Design Interview Question/Tier 3 — Fintech Specific/07-Design P2P Payment Engine (e-Transfer).md>) — not on the core list, kept as extra fintech coverage.

### Tier 4 — Distributed Systems Deep Dives

| # | Topic | Why | Status | File |
|---|-------|-----|--------|------|
| 25 | Kafka / Messaging System | You know this from work ✅, but know it formally | 📝 | [02-Design a Messaging System (Kafka).md](<03-System Design Interview Question/Tier 4 — Distributed Systems/02-Design a Messaging System (Kafka).md>) |
| 26 | Key-Value Store (Dynamo) | Teaches consistent hashing, replication, CAP | ✅ | [01-Key-Value Store (Dynamo).md](<03-System Design Interview Question/Tier 4 — Distributed Systems/01-Key-Value Store (Dynamo).md>) |
| 27 | Distributed Locking (Chubby) | Staff level, impressive if you can discuss it | 📝 | [03-Design Distributed Locking (Chubby).md](<03-System Design Interview Question/Tier 4 — Distributed Systems/03-Design Distributed Locking (Chubby).md>) |

### Tier 5 — Design Patterns (Concepts, Not Full Designs)

| # | Pattern | Why | Status | File |
|---|---------|-----|--------|------|
| 28 | Saga Pattern | Critical for distributed transactions / payments | 📝 | [02-Saga Pattern.md](<03-System Design Interview Question/Tier 5 — Design Patterns/02-Saga Pattern.md>) |
| 29 | Transactional Outbox | You likely use this at Moneris already | 📝 | [03-Transactional Outbox Pattern.md](<03-System Design Interview Question/Tier 5 — Design Patterns/03-Transactional Outbox Pattern.md>) |
| 30 | Event Sourcing | Audit logs, fintech, event-driven systems | 📝 | [04-Event Sourcing.md](<03-System Design Interview Question/Tier 5 — Design Patterns/04-Event Sourcing.md>) |
| 31 | CQRS | Read/write separation, common in high-scale systems | 📝 | [05-CQRS.md](<03-System Design Interview Question/Tier 5 — Design Patterns/05-CQRS.md>) |
| 32 | Circuit Breaker | Resilience pattern, asked in senior interviews | 📝 | [06-Circuit Breaker Pattern.md](<03-System Design Interview Question/Tier 5 — Design Patterns/06-Circuit Breaker Pattern.md>) |
| 33 | API Gateway Pattern | Routing, auth, rate limiting — very common | ✅ | [01-API Gateway Pattern.md](<03-System Design Interview Question/Tier 5 — Design Patterns/01-API Gateway Pattern.md>) |
| 34 | Service Discovery | Kubernetes-relevant, you know this from work | 📝 | [07-Service Discovery.md](<03-System Design Interview Question/Tier 5 — Design Patterns/07-Service Discovery.md>) |
| 35 | Retry Pattern | Simple but important, especially in payments | 📝 | [08-Retry Pattern.md](<03-System Design Interview Question/Tier 5 — Design Patterns/08-Retry Pattern.md>) |

### Misc — Extra Topics

Not on the core list, kept for reference: [Partner integration platform.md](<03-System Design Interview Question/Misc — Extra Topics/01-Partner integration platform.md>)
