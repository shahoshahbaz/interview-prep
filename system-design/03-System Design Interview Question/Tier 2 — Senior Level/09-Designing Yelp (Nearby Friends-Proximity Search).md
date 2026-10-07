# Design Yelp / Nearby Friends (Proximity Search)

> Status: Placeholder — needs full write-up.

## 1. Requirements
- Search businesses/friends near a given location within a radius.
- Support filters (category, rating, open now).
- Nearby Friends: continuously updated proximity of friends to the user.

## 2. Capacity Estimation
- TBD: number of businesses/users, search QPS, location update frequency.

## 3. High-Level Design
- Geospatial index (geohash/quadtree/S2) over businesses or friend locations.
- Search API queries index for radius/bounding-box search, then ranks results.
- For Nearby Friends: periodic location updates + pub-sub to notify friends within proximity.

## 4. Deep Dives
- Geohashing and grid-based indexing; handling grid-boundary edge cases.
- Balancing index update frequency vs query freshness.
- Privacy considerations for location sharing.

## 5. Trade-offs
- Precomputed proximity vs on-demand distance calculation.
- Push-based updates vs pull/poll for friend proximity.
