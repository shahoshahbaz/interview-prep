# Design Uber Backend

> Status: Placeholder — needs full write-up.

## 1. Requirements
- Match riders with nearby available drivers in real time.
- Track live location of drivers/riders.
- Trip lifecycle: request → match → en route → trip → payment.
- ETA and pricing (surge) calculation.

## 2. Capacity Estimation
- TBD: concurrent drivers, location update frequency, matching QPS.

## 3. High-Level Design
- Location service (geospatial index, e.g., geohash/quadtree) ingesting driver GPS pings.
- Matching service finds nearby drivers for a ride request.
- Trip service manages state machine for trip lifecycle.
- Pricing/surge service, Payment service.

## 4. Deep Dives
- Geospatial indexing (geohash, quadtree, S2 cells) for proximity search.
- Real-time location updates at scale (high write throughput).
- Matching algorithm (nearest driver, batching, surge pricing zones).
- Consistency of trip state across services.

## 5. Trade-offs
- Precision of geo-index vs update cost.
- Centralized matching vs regional sharding.
