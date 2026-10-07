# Design a Web Crawler

> Status: Placeholder — needs full write-up.

## 1. Requirements
- Crawl billions of web pages, extract links, store content for indexing.
- Respect robots.txt, politeness (rate limit per domain), avoid duplicate crawling.
- Extensible to new content types (HTML, PDF, etc.).

## 2. Capacity Estimation
- TBD: pages/sec, average page size, total storage for crawled content.

## 3. High-Level Design
- Seed URLs → URL Frontier (priority + politeness queues) → Fetcher workers → HTML parser/Link extractor → Dedup (Bloom filter/URL seen store) → Content store + new URLs back to frontier.
- DNS resolver cache, robots.txt cache.

## 4. Deep Dives
- URL frontier design (priority queues per host for politeness).
- Dedup at URL and content level (checksum/simhash for near-duplicates).
- Distributed crawling and consistent hashing for domain partitioning.
- Handling crawler traps and dynamic content.

## 5. Trade-offs
- Breadth-first vs priority-based crawling.
- Centralized frontier vs sharded per-domain queues.
