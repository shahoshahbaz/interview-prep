# Push CDN vs. Pull CDN

Content Delivery Networks (CDNs) can be categorized into two primary types based on how content is distributed to the edge servers: Push CDNs and Pull CDNs. Both types have distinct characteristics, advantages, and use cases.

## Pull CDN

In a Pull CDN, content is not stored on the CDN's servers by default. Instead, content is "pulled" from the origin server when requested for the first time.

### How Pull CDNs Work

1. User requests content not yet in the CDN cache
2. CDN edge server requests the content from origin server
3. Origin server delivers content to the CDN
4. CDN caches the content and serves it to the user
5. Subsequent requests are served directly from the CDN cache until TTL expires

### Advantages of Pull CDN

- **Easy Setup**: Minimal changes to existing infrastructure required
- **Efficient Resource Usage**: Origin server only accessed when content is not in cache
- **Automatic Cache Management**: CDN handles content expiration and refreshing
- **Lower Storage Costs**: Only caches what users actually request
- **Ideal for Dynamic Content**: Works well with frequently changing content

### Disadvantages of Pull CDN

- **Initial Latency**: First user may experience slower load times
- **Origin Dependency**: Origin server must be accessible for cache misses
- **Cache Miss Performance Impact**: Performance degrades during cache misses
- **Limited Control**: Less granular control over content distribution

### Examples of Pull CDNs

- Cloudflare
- Amazon CloudFront
- Fastly
- Google Cloud CDN

## Push CDN

In a Push CDN, content is proactively "pushed" to the CDN's servers by the content provider before any user requests it.

### How Push CDNs Work

1. Content provider uploads or syncs content to the CDN
2. CDN distributes content to all or selected edge servers
3. When users request content, it's already available on edge servers
4. No origin server communication needed at request time

### Advantages of Push CDN

- **Predictable Performance**: Content is pre-loaded, ensuring consistent load times
- **Reduced Origin Load**: Origin server isn't involved in content delivery
- **Greater Control**: More precise management of what content is distributed
- **No Cold Cache Issues**: Eliminates "first user" latency problem
- **Ideal for Large Files**: Better for video, software downloads, and other large assets

### Disadvantages of Push CDN

- **Complex Setup**: More difficult to configure and maintain
- **Higher Storage Costs**: Content stored on both origin and CDN servers
- **Manual Management**: Content provider responsible for updates and purging
- **Resource Intensive**: Requires processes to synchronize content
- **Less Suitable for Dynamic Content**: Not ideal for frequently changing content

### Examples of Push CDNs

- Rackspace Cloud Files
- Akamai NetStorage
- Microsoft Azure CDN (with push capabilities)

## Comparison Table

| Feature | Pull CDN | Push CDN |
|---------|---------|----------|
| **Content Loading** | On-demand (lazy loading) | Preloaded (eager loading) |
| **Setup Complexity** | Lower | Higher |
| **Storage Costs** | Lower | Higher |
| **Initial Request** | Slower | Faster |
| **Control Level** | Limited | Extensive |
| **Origin Dependency** | High | Low |
| **Best For** | Frequently accessed, dynamic content | Large files, predictable content |
| **Cache Management** | Automatic | Manual |

## When to Choose Each

### Choose Pull CDN when:
- You have a mix of frequently and infrequently accessed content
- Your content changes often
- You want simpler setup and management
- Storage cost optimization is important

### Choose Push CDN when:
- You have predictable, large content (videos, downloads)
- You need consistent performance regardless of access patterns
- You want maximum control over content distribution
- Origin server availability or performance is a concern

