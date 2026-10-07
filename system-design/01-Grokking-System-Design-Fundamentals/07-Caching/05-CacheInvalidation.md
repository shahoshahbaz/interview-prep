# Cache Invalidation

## Why Cache Invalidation Matters

Cache invalidation ensures data consistency between the cache and the source of truth. It's critical for:

- **Data Freshness**: Prevents serving stale data when underlying information changes
- **System Consistency**: Maintains coherence across multiple caching layers
- **Performance Balance**: Optimizes between cache speed and data accuracy
- **Error Prevention**: Reduces incorrect information being presented to users

## Cache Writing Schemes

### 1. Write-Through Cache
Data is written simultaneously to both cache and database, ensuring consistency but with higher write latency.

### 2. Write-Around Cache
Data bypasses the cache and writes directly to storage, preventing cache flooding but causing cache misses for recently written data.

### 3. Write-Back Cache
Data is written only to cache initially and later persisted to storage when needed, providing low latency but risking data loss during failures.

### 4. Write-Behind Cache
Similar to write-back, but data is written to permanent storage at specific intervals rather than when cache space is needed. The main difference between write-back cache and write-behind cache is when the data is written to the permanent storage. In write-back caching, data is only written to the permanent storage when it is necessary for the cache to free up space, while in write-behind caching, data is written to the permanent storage at specified intervals.

## Cache Invalidation Methods

### Purge
- Immediately removes specific cached content
- Forces fresh content fetch from origin on next request
- Works with specific URLs or objects
- Acts like a "hard delete" of cache entries

### Refresh
- Updates cache with fresh content from origin server
- Preserves existing cache entries but with updated content
- Doesn't remove content, just updates it

### Ban
- Invalidates content based on specific criteria (URL patterns, headers)
- Adds invalidation rules to a ban list rather than immediate removal
- Stale content is refreshed only when requested
- Allows flexible group-based invalidation
- Content remains in memory but is marked invalid

### Time-to-Live (TTL) Expiration
- Sets an expiration time for cached content
- Automatically considers content stale after TTL period
- Refreshes from origin when expired content is requested

### Stale-While-Revalidate
- Serves stale content immediately while refreshing in background
- Ensures fast response times even during cache updates
- Asynchronously fetches fresh content from origin
- Updates cached version once fresh content is available
