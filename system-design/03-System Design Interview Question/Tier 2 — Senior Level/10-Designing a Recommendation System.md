# Design a Recommendation System

> Status: Placeholder — needs full write-up.

## 1. Requirements
- Recommend items (products/videos/posts) personalized per user.
- Support both batch (offline) and real-time (online) recommendations.
- Handle cold-start for new users/items.

## 2. Capacity Estimation
- TBD: number of users/items, recommendation request QPS, model refresh cadence.

## 3. High-Level Design
- Data collection (clicks, views, purchases) → Feature store → Offline training pipeline (collaborative filtering/embeddings) → Model store → Online serving layer (candidate generation + ranking) → Recommendation API.

## 4. Deep Dives
- Candidate generation (collaborative filtering, content-based, embeddings/ANN search).
- Ranking model (learning-to-rank, feature cross).
- Cold-start strategies (content-based fallback, popularity).
- Online feature freshness vs offline batch training.

## 5. Trade-offs
- Precompute recommendations vs real-time scoring.
- Model complexity vs serving latency.
