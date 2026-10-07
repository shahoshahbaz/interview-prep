# DNS (Domain Name System) - Complete Summary

## Quick Reference Guide for System Design Interviews

This document consolidates all key DNS concepts from the 06-DNS folder, essential for system design interviews.

---

## 📋 Table of Contents

1. [Introduction to DNS](#1-introduction-to-dns)
2. [DNS Resolution Process](#2-dns-resolution-process)
3. [DNS Load Balancing and High Availability](#3-dns-load-balancing-and-high-availability)
4. [Interview Cheat Sheet](#4-interview-cheat-sheet)
5. [Key Takeaways](#5-key-takeaways)

---

## 1. Introduction to DNS

### What is DNS?
**Definition**: DNS (Domain Name System) is a hierarchical, distributed system that translates human-readable domain names (e.g., www.example.com) into IP addresses (e.g., 93.184.216.34) that computers use to communicate on the internet. It functions as the internet's phonebook.

### DNS Components

#### 1. Domain Names
- **Structure**: Read from right to left with dots separating hierarchy levels
- **Example**: `www.example.com`
  - `.com` = Top-Level Domain (TLD)
  - `example` = Second-level domain
  - `www` = Subdomain

#### 2. Top-Level Domains (TLDs)
**Types:**
- **Generic TLDs (gTLDs)**: .com, .org, .net, .edu
- **Country-code TLDs (ccTLDs)**: .us, .uk, .ca, .jp
- **Special-purpose TLDs**: .gov, .mil, .int

**Management**: Each TLD is managed by designated organizations under ICANN oversight.

#### 3. Subdomains
- **Purpose**: Logically partition domain namespace
- **Examples**: 
  - `blog.example.com`
  - `shop.example.com`
  - `api.example.com`

### DNS Server Types

#### 1. Root Servers
- **Function**: Foundation of DNS hierarchy; direct queries to TLD servers
- **Deployment**: 13 root server clusters worldwide (labeled A-M)
- **Distribution**: Each cluster has multiple instances via anycast
- **Management**: Operated by 12 different organizations

#### 2. TLD Servers
- **Function**: Maintain information about domains within a specific TLD
- **Role**: Direct queries to authoritative name servers for domains
- **Example**: .com TLD servers know which authoritative servers handle example.com

#### 3. Authoritative Name Servers
- **Function**: Hold actual DNS records for specific domains
- **Role**: Provide definitive answers about domain DNS records

**Common DNS Record Types:**
- **A records**: Domain → IPv4 address
- **AAAA records**: Domain → IPv6 address
- **CNAME records**: Alias one domain to another
- **MX records**: Specify mail servers
- **TXT records**: Store text information (verification, SPF, DKIM)
- **NS records**: Indicate authoritative servers for domain

### DNS Resolver Types

#### 1. Stub Resolver
- **Location**: End-user devices (OS-level)
- **Function**: Initiates DNS lookups by forwarding to configured DNS server
- **Behavior**: Does NOT perform recursive lookups itself
- **Example**: DNS client built into your operating system

#### 2. Recursive Resolver
- **Function**: Performs complete DNS lookups on behalf of clients
- **Examples**: Google (8.8.8.8), Cloudflare (1.1.1.1), ISP DNS servers
- **Behavior**: Queries entire DNS hierarchy until answer is found
- **Caching**: Stores responses to speed up future lookups

#### 3. Caching-Only Resolver
- **Function**: Specializes in caching DNS responses
- **Characteristic**: Does NOT host authoritative records
- **Purpose**: Reduce lookup latency
- **TTL Compliance**: Honors Time-To-Live values
- **Example**: Home routers caching DNS responses

#### 4. Forwarder
- **Function**: Forwards queries to another DNS server
- **Role**: Intermediary between stub and recursive resolvers
- **Use Case**: Corporate networks for centralized DNS management/filtering
- **Behavior**: May maintain cache of recent lookups

#### 5. Iterative (Non-Recursive) Resolver
- **Function**: Provides referrals rather than complete answers
- **Response**: "I don't know, but try asking this server"
- **Example**: Root and TLD servers operate iteratively

### DNS Hierarchy Diagram

```
User's Device (Stub Resolver)
     |
     v
Recursive Resolver (ISP/Public DNS)
     |
     v
Root Server (.)
     |
     v
TLD Server (.com, .net, etc.)
     |
     v
Authoritative Server (example.com)
     |
     v
IP Address Returned
```

### Pros & Cons

#### ✅ Pros:
- **User-Friendly**: Memorable domain names instead of IP addresses
- **Distributed**: Provides redundancy and fault tolerance
- **Performance**: Caching improves speed and reduces traffic
- **Load Balancing**: Enables traffic distribution
- **Flexibility**: Can change IPs without affecting users

#### ❌ Cons:
- **Propagation Delays**: Updates take time to spread
- **Security Vulnerabilities**: DNS spoofing, cache poisoning, DDoS
- **Misconfigurations**: Can cause widespread outages
- **Single Point of Failure**: If not properly configured with redundancy

---

## 2. DNS Resolution Process

### What is DNS Resolution?
**Definition**: The process of converting a human-readable domain name into its corresponding IP address through a series of queries across the distributed DNS hierarchy.

### Types of DNS Queries

#### 1. Recursive Queries
- **Requester**: Client to resolver
- **Responsibility**: Server takes full responsibility to find the answer
- **Response**: Complete answer or error
- **Example**: Your computer asking ISP's DNS server to resolve example.com

**Characteristics:**
- Client expects a definitive answer
- Server does all the work
- Most common for end-user queries

#### 2. Iterative Queries
- **Requester**: Resolver to DNS servers
- **Responsibility**: Server returns best information it has
- **Response**: Answer OR referral to another server
- **Example**: Recursive resolver querying root → TLD → authoritative servers

**Characteristics:**
- "Best effort" responses
- May return referrals
- Used between DNS infrastructure servers

### DNS Resolution Step-by-Step

```
1. User types example.com in browser
   ↓
2. Browser checks its cache (miss)
   ↓
3. OS stub resolver checks local cache (miss)
   ↓
4. Recursive query sent to configured DNS resolver (e.g., 8.8.8.8)
   ↓
5. Recursive resolver checks its cache (miss)
   ↓
6. Iterative query to Root Server
   ↓
7. Root returns referral: "Ask .com TLD server"
   ↓
8. Iterative query to .com TLD Server
   ↓
9. TLD returns referral: "Ask example.com authoritative server"
   ↓
10. Iterative query to example.com Authoritative Server
    ↓
11. Authoritative server returns IP address (93.184.216.34)
    ↓
12. Result cached at multiple levels
    ↓
13. IP address returned to browser
    ↓
14. Browser connects to IP address
```

### DNS Caching

**Purpose**: 
- Speed up resolution
- Reduce network traffic
- Improve user experience

**Caching Levels:**
1. **Browser Cache**: Stores recent DNS lookups
2. **Operating System Cache**: OS-level DNS cache
3. **Recursive Resolver Cache**: ISP/public DNS cache
4. **ISP DNS Server Cache**: Provider-level caching

### TTL (Time To Live)

**Definition**: Controls how long DNS records remain in cache (measured in seconds)

**Trade-offs:**

| TTL Setting | Benefits | Drawbacks |
|-------------|----------|-----------|
| **Short TTL** (60-300s) | Quick updates, flexibility | More DNS traffic, higher load |
| **Long TTL** (3600s+) | Reduced traffic, better performance | Slower updates, stale data risk |

**Common Values:**
- **A/AAAA records**: 300-3600 seconds (5 min - 1 hour)
- **CNAME records**: 300-1800 seconds
- **MX records**: 3600-86400 seconds (1-24 hours)

### Negative Caching

**Purpose**: Store information about non-existent domains/records

**Benefits:**
- Prevents repeated queries for resources that don't exist
- Improves performance
- Reduces unnecessary DNS traffic

**Characteristics:**
- Typically shorter TTL than positive caching
- Prevents DNS abuse
- Returns NXDOMAIN (Non-Existent Domain) errors

### Real-World Example

**Scenario**: Visiting Netflix.com

```
1. Browser initiates DNS lookup for netflix.com
2. Stub resolver sends recursive query to 8.8.8.8
3. If cached: Return IP immediately (fastest path)
4. If not cached:
   - Query root servers
   - Get referral to .com TLD servers
   - Query .com TLD servers
   - Get referral to Netflix authoritative servers
   - Query Netflix authoritative servers
   - Receive IP address(es)
5. IP cached at multiple levels for future requests
6. Browser connects to Netflix servers
```

### Pros & Cons

#### ✅ Pros:
- **Distributed**: Ensures reliability and fault tolerance
- **Multi-Level Caching**: Improves speed, reduces traffic
- **Hierarchical**: Scalable to billions of domains
- **TTL Flexibility**: Control update propagation speed

#### ❌ Cons:
- **Latency**: Multiple lookups for uncached domains
- **Stale Data**: Caching can serve outdated information
- **Complexity**: Multiple points of failure
- **Security Risks**: Cache poisoning, DDoS attacks

---

## 3. DNS Load Balancing and High Availability

### Overview

DNS load balancing and high availability techniques ensure:
- ✅ Distributed query load across multiple servers
- ✅ Reduced latency for end-users
- ✅ Uninterrupted service despite failures
- ✅ Improved overall DNS infrastructure resilience

### Key Techniques

#### 1. Round-Robin DNS

**How it Works**: Multiple IP addresses associated with a single domain name. DNS server rotates through these IPs when responding to queries.

**Example:**
```
example.com → 192.168.1.1
example.com → 192.168.1.2
example.com → 192.168.1.3

Query 1: Returns 192.168.1.1
Query 2: Returns 192.168.1.2
Query 3: Returns 192.168.1.3
Query 4: Returns 192.168.1.1 (cycles back)
```

**✅ Benefits:**
- Simple to implement
- Distributes load among multiple servers
- Improves service availability
- No additional infrastructure needed

**❌ Limitations:**
- Doesn't consider actual server load
- Ignores client geographic location
- Can lead to uneven distribution
- No health checks (returns dead servers)
- Client-side caching affects distribution

**Use Cases:**
- Basic load distribution
- Small to medium deployments
- Static content delivery

---

#### 2. Geographically Distributed DNS Servers

**How it Works**: Deploy DNS servers across multiple geographic locations to serve users from nearest location.

**Architecture:**
```
North America DNS Server → Serves US/Canada users
Europe DNS Server       → Serves European users
Asia DNS Server         → Serves Asian users
```

**✅ Benefits:**
- **Faster Resolution**: Users query nearest server
- **Increased Redundancy**: Multiple server locations
- **Fault Tolerance**: Localized failures don't affect global service
- **Better Reliability**: Geographic distribution prevents single points of failure

**Implementation:**
- Deploy DNS servers in multiple regions
- Use GeoDNS to route users to nearest server
- Implement health monitoring
- Configure automatic failover

**Use Cases:**
- Global applications
- CDN infrastructure
- Multi-region deployments
- Enterprise DNS

---

#### 3. Anycast Routing

**How it Works**: Multiple servers share the same IP address. Network routing automatically directs queries to the nearest/best server based on network topology.

**Architecture:**
```
Global IP: 1.1.1.1

Server in New York    → Announces 1.1.1.1
Server in London      → Announces 1.1.1.1
Server in Singapore   → Announces 1.1.1.1

User in US    → Routes to New York
User in Europe → Routes to London
User in Asia   → Routes to Singapore
```

**✅ Benefits:**
- **Automatic Load Balancing**: Network handles distribution
- **Reduced Latency**: Routes to nearest available server
- **High Availability**: Auto-redirects if server fails
- **DDoS Mitigation**: Spreads attack across multiple servers
- **Improved Resilience**: No single point of failure

**Examples in Production:**
- Root DNS servers (all use anycast)
- Cloudflare (1.1.1.1)
- Google Public DNS (8.8.8.8)
- Major CDN providers

**Requirements:**
- BGP (Border Gateway Protocol) support
- Multiple geographic locations
- Network infrastructure coordination
- Health monitoring and failover

---

#### 4. Content Delivery Networks (CDNs) and DNS

**How it Works**: CDNs use DNS to direct users to the optimal edge server for content delivery based on location, load, and availability.

**DNS-CDN Integration:**
```
1. User requests www.example.com
2. DNS query goes to CDN's DNS server
3. CDN DNS analyzes:
   - User's geographic location
   - Server availability
   - Current server load
   - Network conditions
4. CDN DNS returns IP of optimal edge server
5. User connects to nearby edge server
6. Content delivered with minimal latency
```

**✅ Benefits:**
- **Faster Content Delivery**: Users served from nearby edge servers
- **Reduced Latency**: Geographic proximity improves speed
- **Load Distribution**: Spreads traffic across edge servers
- **Improved User Experience**: Better performance globally
- **Origin Protection**: Shields origin servers from direct traffic

**Popular CDN Providers:**
- Cloudflare
- Akamai
- AWS CloudFront
- Fastly
- Azure CDN

**DNS Techniques Used by CDNs:**
- GeoDNS (geographic routing)
- Health-based routing
- Latency-based routing
- Weighted routing
- Failover routing

---

### Comparison of Load Balancing Techniques

| Technique | Complexity | Performance | Geographic Awareness | Cost | Best For |
|-----------|------------|-------------|---------------------|------|----------|
| **Round-Robin** | Low | Basic | No | Free | Small sites, basic distribution |
| **Geographic DNS** | Medium | Good | Yes | Medium | Global applications |
| **Anycast** | High | Excellent | Yes | High | DNS infrastructure, CDNs |
| **CDN Integration** | Medium-High | Excellent | Yes | Variable | Content delivery, web apps |

---

### High Availability Strategies

#### 1. Multiple Authoritative Servers
- Configure at least 2 authoritative DNS servers
- Place in different geographic locations
- Different network providers for redundancy

#### 2. Secondary DNS Providers
- Use secondary DNS provider as backup
- Zone transfers keep records synchronized
- Automatic failover if primary fails

#### 3. Health Monitoring
- Continuous health checks on DNS servers
- Automatic removal of unhealthy servers
- Alert systems for failures

#### 4. DDoS Protection
- Anycast distribution
- Rate limiting
- Query filtering
- Dedicated DDoS mitigation services

---

## 4. Interview Cheat Sheet

### Common DNS Interview Questions

#### Q1: "What is DNS and how does it work?"

**Answer Template:**
> "DNS is the Domain Name System that translates human-readable domain names like example.com into IP addresses like 93.184.216.34. It works through a hierarchical system:
> 
> 1. When you type a domain, your device queries a recursive resolver
> 2. The resolver queries root servers, which point to TLD servers
> 3. TLD servers point to authoritative name servers
> 4. Authoritative servers return the IP address
> 5. The result is cached at multiple levels to improve performance
> 
> This distributed approach ensures scalability and reliability."

---

#### Q2: "Explain the DNS resolution process step by step."

**Answer Template:**
> "The DNS resolution process involves both recursive and iterative queries:
> 
> 1. **Browser cache check** - First checks local cache
> 2. **OS resolver** - Sends recursive query to configured DNS (like 8.8.8.8)
> 3. **Recursive resolver cache** - Checks its cache
> 4. **Root server query** - If not cached, queries root servers
> 5. **TLD server query** - Root refers to TLD servers (.com)
> 6. **Authoritative query** - TLD refers to authoritative servers
> 7. **IP returned** - Authoritative server provides the IP
> 8. **Multi-level caching** - Result cached for TTL duration
> 
> This takes about 20-120ms for uncached queries, but subsequent queries are instant due to caching."

---

#### Q3: "What is TTL and why does it matter?"

**Answer Template:**
> "TTL (Time To Live) controls how long DNS records stay in cache before needing refresh. It's measured in seconds.
> 
> **Short TTL (60-300s):**
> - Pros: Quick updates, flexibility for changes
> - Cons: More DNS traffic, higher load
> - Use when: Planning infrastructure changes
> 
> **Long TTL (3600s+):**
> - Pros: Better performance, reduced DNS load
> - Cons: Slower propagation of changes
> - Use when: Stable infrastructure
> 
> In system design, I'd use short TTL before planned migrations and longer TTL for stable production systems to optimize performance."

---

#### Q4: "How does DNS enable load balancing?"

**Answer Template:**
> "DNS provides several load balancing mechanisms:
> 
> 1. **Round-Robin DNS**: Returns multiple IPs in rotation
>    - Simple but doesn't check server health
> 
> 2. **Geographic DNS**: Routes users to nearest data center
>    - Reduces latency, improves user experience
> 
> 3. **Weighted Routing**: Distributes traffic by percentage
>    - Useful for gradual rollouts or A/B testing
> 
> 4. **Health-Based Routing**: Only returns healthy servers
>    - Monitors server health, provides automatic failover
> 
> Major CDNs like Cloudflare combine these with anycast routing for optimal performance and reliability."

---

#### Q5: "What are the security concerns with DNS?"

**Answer Template:**
> "DNS faces several security challenges:
> 
> 1. **DNS Spoofing/Cache Poisoning**: Attacker injects false DNS records
>    - Solution: DNSSEC for cryptographic validation
> 
> 2. **DDoS Attacks**: Overwhelming DNS servers with traffic
>    - Solution: Anycast distribution, rate limiting
> 
> 3. **DNS Tunneling**: Using DNS for data exfiltration
>    - Solution: DNS query monitoring and filtering
> 
> 4. **Man-in-the-Middle**: Intercepting DNS queries
>    - Solution: DNS over HTTPS (DoH) or DNS over TLS (DoT)
> 
> In system design, I'd implement DNSSEC, use reputable DNS providers with DDoS protection, and consider DoH/DoT for sensitive applications."

---

#### Q6: "How would you design DNS for a global application?"

**Answer Template:**
> "For a global application, I'd design DNS with these components:
> 
> **Infrastructure:**
> - Use managed DNS service (Route53, Cloudflare) for reliability
> - Deploy authoritative servers in multiple regions
> - Implement anycast for automatic geographic routing
> 
> **Routing Strategy:**
> - GeoDNS to route users to nearest region
> - Health checks with automatic failover
> - Weighted routing for gradual rollouts
> - Low TTL (300s) during migrations, higher (3600s) when stable
> 
> **Reliability:**
> - Multiple DNS providers (primary + secondary)
> - Monitor DNS query latency and errors
> - DDoS protection via anycast and rate limiting
> - DNSSEC for security
> 
> **Performance:**
> - CDN integration for static assets
> - Edge DNS resolution
> - Multi-level caching strategy
> 
> This approach ensures low latency globally while maintaining high availability and security."

---

### Quick Reference: DNS Components

| Component | Function | Example |
|-----------|----------|---------|
| **Root Server** | Top of hierarchy | Directs to .com |
| **TLD Server** | Domain extension | .com, .org, .net |
| **Authoritative** | Final answer | example.com → IP |
| **Recursive Resolver** | Does lookup work | 8.8.8.8, 1.1.1.1 |
| **Stub Resolver** | Client initiator | OS DNS client |

---

### Quick Reference: DNS Record Types

| Record Type | Purpose | Example |
|-------------|---------|---------|
| **A** | Domain → IPv4 | example.com → 93.184.216.34 |
| **AAAA** | Domain → IPv6 | example.com → 2606:2800:220:1:... |
| **CNAME** | Alias | www → example.com |
| **MX** | Mail server | mail.example.com priority 10 |
| **TXT** | Text data | SPF, DKIM, verification |
| **NS** | Name server | ns1.example.com |
| **SOA** | Zone authority | Primary NS, admin email, serial |

---

### Quick Reference: DNS Performance Metrics

| Metric | Good | Acceptable | Poor | Impact |
|--------|------|------------|------|--------|
| **Lookup Time** | <20ms | 20-50ms | >50ms | User experience |
| **TTL** | 300-3600s | 60-300s | <60s or >86400s | Performance vs flexibility |
| **Cache Hit Rate** | >90% | 70-90% | <70% | DNS load |
| **Availability** | 99.99% | 99.9% | <99.9% | Service reliability |

---

## 5. Key Takeaways

### Essential Facts for Interviews

#### 🌐 DNS Basics
- ✅ **DNS = Internet's phonebook**: Translates domains to IPs
- ✅ **Hierarchical system**: Root → TLD → Authoritative
- ✅ **Distributed architecture**: Reliability through redundancy
- ✅ **Caching at all levels**: Browser, OS, resolver, servers

#### 🔄 Resolution Process
- ✅ **Recursive queries**: Client → Resolver (complete answer)
- ✅ **Iterative queries**: Resolver → DNS servers (referrals)
- ✅ **Multi-step process**: Root → TLD → Authoritative
- ✅ **Typical latency**: 20-120ms uncached, <1ms cached

#### ⚡ Performance
- ✅ **TTL controls caching**: Balance updates vs performance
- ✅ **Short TTL**: Quick updates, more traffic
- ✅ **Long TTL**: Better performance, slower updates
- ✅ **Caching is critical**: Reduces load, improves speed

#### 🔧 Load Balancing
- ✅ **Round-Robin**: Simple, but no health checks
- ✅ **GeoDNS**: Routes to nearest location
- ✅ **Anycast**: Network-level routing to nearest server
- ✅ **CDN integration**: Optimal edge server selection

#### 🛡️ High Availability
- ✅ **Multiple authoritative servers**: Different locations/networks
- ✅ **Secondary DNS providers**: Failover redundancy
- ✅ **Health monitoring**: Automatic failure detection
- ✅ **DDoS protection**: Anycast distribution, rate limiting

---

### Memory Aids

**DNS Hierarchy:**
> "**R**oot servers **T**ell **A**ll
> Root → TLD → Authoritative"

**Query Types:**
> "**R**ecursive = **R**esponsibility (server does all work)
> **I**terative = **I**nformation (best info available)"

**TTL Decision:**
> "**S**hort TTL = **S**peed of changes
> **L**ong TTL = **L**ow DNS load"

---

### Common Mistakes to Avoid

❌ **Don't:** Use single DNS server (single point of failure)
✅ **Do:** Use multiple authoritative servers in different locations

❌ **Don't:** Set extremely short TTL (<60s) in production
✅ **Do:** Use 300-3600s TTL for stable systems

❌ **Don't:** Rely only on Round-Robin for load balancing
✅ **Do:** Implement health checks and geographic routing

❌ **Don't:** Ignore DNS security (spoofing, DDoS)
✅ **Do:** Use DNSSEC, DDoS protection, monitoring

❌ **Don't:** Forget DNS propagation time in deployments
✅ **Do:** Lower TTL before changes, plan for propagation delay

❌ **Don't:** Use DNS for real-time load balancing
✅ **Do:** Use Layer 7 load balancers for dynamic routing

---

### System Design Checklist

When designing DNS for your system, verify:

**Infrastructure:**
- [ ] Multiple authoritative DNS servers configured
- [ ] Servers in different geographic locations
- [ ] Different network providers for redundancy
- [ ] Managed DNS service selected (Route53, Cloudflare, etc.)

**Performance:**
- [ ] Appropriate TTL values set (300-3600s for stable systems)
- [ ] Caching strategy defined at all levels
- [ ] DNS query monitoring implemented
- [ ] GeoDNS configured for global users

**Load Balancing:**
- [ ] Load balancing method chosen (Round-Robin, GeoDNS, etc.)
- [ ] Health checks configured
- [ ] Failover strategy defined
- [ ] CDN integration planned if needed

**Security:**
- [ ] DNSSEC considered for sensitive domains
- [ ] DDoS protection enabled
- [ ] DNS query logging and monitoring
- [ ] Rate limiting configured

**High Availability:**
- [ ] Secondary DNS provider configured
- [ ] Automatic failover tested
- [ ] Monitoring and alerting set up
- [ ] Disaster recovery plan documented

---

### DNS in System Design Interviews

**When DNS is Critical:**
- 🌐 **Global applications** - GeoDNS for regional routing
- 📈 **High traffic systems** - Load balancing and caching
- 🎯 **Multi-region deployments** - Geographic distribution
- 🔄 **Microservices** - Service discovery (though usually internal DNS)
- 🛡️ **High availability requirements** - Redundancy and failover
- ⚡ **Low latency needs** - Caching and geographic proximity

**What to Mention:**
1. **Start**: "DNS is critical for translating domains to IPs"
2. **Resolution**: Explain the lookup process with caching
3. **Performance**: Mention TTL and multi-level caching
4. **Load Balancing**: Describe GeoDNS or Round-Robin
5. **Reliability**: Multiple servers, health checks, failover
6. **Global Scale**: Anycast, CDN integration, geographic routing

---

## Summary

This document consolidates DNS fundamentals from three key files:
- **01-IntroductionToDNS.md** - DNS components, server types, resolver types
- **02-DNSResolutionProcess.md** - Resolution process, caching, TTL
- **03-DNSLoadBalancingAndHighAvailability.md** - Load balancing techniques, high availability

### Final Interview Tips

1. **Understand the hierarchy**: Root → TLD → Authoritative
2. **Know query types**: Recursive (client-facing) vs Iterative (server-to-server)
3. **Explain caching**: Multi-level caching is key to DNS performance
4. **TTL trade-offs**: Short = flexible, Long = performant
5. **Load balancing options**: Round-Robin, GeoDNS, Anycast, CDN
6. **High availability**: Multiple servers, different locations, health checks
7. **Security concerns**: Spoofing, DDoS, cache poisoning
8. **Global considerations**: GeoDNS, anycast, CDN integration

**Remember:** DNS is often overlooked in system design but is critical for global applications. Always consider DNS latency, caching strategy, and redundancy in your designs. A typical DNS lookup adds 20-120ms for uncached queries, which matters at scale!

---

**Last Updated:** January 7, 2025
**Source Files:** 01-IntroductionToDNS.md, 02-DNSResolutionProcess.md, 03-DNSLoadBalancingAndHighAvailability.md

Good luck with your system design interviews! 🚀

