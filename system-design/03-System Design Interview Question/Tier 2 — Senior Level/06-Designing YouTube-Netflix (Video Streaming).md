# Design YouTube / Netflix (Video Streaming)

> Status: Placeholder — needs full write-up.

## 1. Requirements
- Upload, transcode, store, and stream video at scale.
- Adaptive bitrate streaming for varying network conditions.
- Recommendations, search, comments, view counts.

## 2. Capacity Estimation
- TBD: uploads/day, storage per video (multiple resolutions), CDN bandwidth, concurrent streams.

## 3. High-Level Design
- Upload service → Transcoding pipeline (multiple resolutions/codecs) → Blob storage (e.g., S3) → CDN for distribution → Metadata service → Client adaptive streaming (HLS/DASH).

## 4. Deep Dives
- Transcoding pipeline (parallel chunk-based transcoding).
- CDN strategy and cache invalidation.
- Adaptive bitrate streaming (manifest files, chunking).
- View count aggregation at scale.

## 5. Trade-offs
- Storage cost vs number of resolutions/codecs supported.
- Push to CDN eagerly vs lazy/on-demand caching.
