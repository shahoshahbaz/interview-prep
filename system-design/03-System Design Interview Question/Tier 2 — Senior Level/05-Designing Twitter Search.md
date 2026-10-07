# Design Twitter Search

> Status: Placeholder — needs full write-up.

## 1. Requirements
- Full-text search over tweets, ranked by relevance and recency.
- Support filters (user, hashtag, date range).
- Near real-time indexing of new tweets.

## 2. Capacity Estimation
- TBD: tweets/sec ingestion rate, index size, QPS for search.

## 3. High-Level Design
- Tweet ingestion → Tokenizer/Analyzer → Inverted Index (sharded, e.g., Elasticsearch/Lucene-based) → Search API → Ranking service (recency + relevance + engagement signals).

## 4. Deep Dives
- Inverted index structure and sharding strategy.
- Real-time vs near-real-time indexing pipeline.
- Ranking: BM25 + social signals, personalization.
- Handling hot terms/trending topics.

## 5. Trade-offs
- Precision/recall vs latency.
- Index freshness vs write amplification.
