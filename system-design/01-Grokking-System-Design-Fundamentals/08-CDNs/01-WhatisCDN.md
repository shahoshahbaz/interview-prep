# Content Delivery Network (CDN)

## What is a CDN?

A Content Delivery Network (CDN) is a distributed network of servers strategically located across various geographical locations to deliver web content efficiently to users. CDNs primarily:
- Reduce latency
- Improve web application performance
- Enhance reliability and availability
- Provide security benefits

## How CDNs Work

1. A user requests content from a web application
2. The request is routed to the nearest CDN edge server
3. The edge server checks if the content is cached:
   - If cached → serves directly from cache
   - If not cached → fetches from origin server, caches it, then serves to user
4. Subsequent requests are served from the cache, reducing latency and origin server load

## Key Terminology and Concepts

1. **Point of Presence (PoP)**: Physical locations where CDN servers are deployed in data centers distributed globally to minimize latency.

2. **Edge Server**: CDN server located at a PoP that caches and delivers content to end-users.

3. **Origin Server**: The primary server where original content is stored before being cached by edge servers.

4. **Cache Warming**: Preloading content into edge server caches before users request it.

5. **Time to Live (TTL)**: Value determining how long content remains in cache before needing refresh from origin.

6. **Anycast**: Network routing technique directing user requests to the nearest available edge server.

7. **Content Invalidation**: Process of updating cached content when origin content changes.

8. **Cache Purging**: Forcibly removing content from edge server cache when triggered manually or automatically.

## Benefits of Using a CDN

1. **Reduced Latency**: Content delivered from geographically closer servers results in faster page loads.

2. **Improved Performance**: Offloads traffic from origin servers, freeing resources for dynamic content generation.

3. **Enhanced Reliability**: Multiple edge servers provide redundancy and fault tolerance for continuous content delivery.

4. **Scalability**: Handles traffic spikes and large volumes of concurrent requests without infrastructure expansion.

5. **Security**: Provides DDoS protection, Web Application Firewalls (WAF), and SSL/TLS termination at the edge.
