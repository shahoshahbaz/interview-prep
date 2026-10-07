# Networking Fundamentals - Complete Summary

## Quick Reference Guide for System Design Interviews

This document consolidates all key networking concepts from the 05-Networking folder, essential for system design interviews.

---

## 📋 Table of Contents

1. [HTTP vs HTTPS](#1-http-vs-https)
2. [TCP vs UDP](#2-tcp-vs-udp)
3. [HTTP Versions Evolution](#3-http-versions-evolution)
4. [URL vs URI vs URN](#4-url-vs-uri-vs-urn)
5. [Interview Cheat Sheet](#5-interview-cheat-sheet)
6. [Quick Decision Guide](#6-quick-decision-guide)
7. [Key Takeaways](#7-key-takeaways)

---

## 1. HTTP vs HTTPS

### HTTP (Hypertext Transfer Protocol)
**Definition**: Foundational protocol for transmitting data on the World Wide Web.

**Key Features:**
- ❌ **No Encryption** - Data sent in plain text
- 🔓 **Port 80** - Default communication port
- ⚡ **Slightly Faster** - No encryption overhead
- 📝 **Stateless Protocol** - Each request is independent
- ⚠️ **Security Risk** - Data can be intercepted

**Use Cases:**
- Public information sites (rarely used today)
- Non-sensitive data transmission
- Internal testing environments

### HTTPS (Hypertext Transfer Protocol Secure)
**Definition**: HTTP with SSL/TLS encryption for secure data transmission.

**Key Features:**
- ✅ **Encryption** - SSL/TLS protocols protect data
- 🔒 **Port 443** - Secure communication port
- 🛡️ **Authentication** - Verifies website legitimacy
- ✔️ **Data Integrity** - Prevents tampering
- 🏆 **SEO Benefits** - Higher search engine ranking

**How HTTPS Works:**
1. **Client Hello** → Client initiates connection
2. **Server Hello** → Server sends SSL certificate
3. **Certificate Verification** → Client validates certificate
4. **Key Exchange** → Establish encrypted connection
5. **Secure Communication** → Data transmitted securely

**Why HTTPS Matters:**
- 🔐 **Security** - Protects against data breaches
- 👥 **Trust** - Users trust secure websites
- 📈 **SEO** - Search engines prioritize HTTPS
- 📜 **Compliance** - Required by GDPR, PCI-DSS, HIPAA
- 🌐 **Industry Standard** - Expected by default today

### HTTP vs HTTPS Comparison

| Feature | HTTP | HTTPS |
|---------|------|-------|
| **Security** | No encryption (plain text) | Encrypted (SSL/TLS) |
| **Port** | 80 | 443 |
| **Performance** | Slightly faster | Minimal overhead |
| **SEO Ranking** | Lower | Higher |
| **URL Prefix** | http:// | https:// |
| **Browser Indicator** | "Not Secure" warning | Padlock icon ✓ |
| **Data Protection** | None | Full encryption |
| **Use Cases** | Legacy/testing only | All production systems |

**Interview Tip:** Always mention that HTTPS is mandatory for production systems. It's not optional anymore.

---

## 2. TCP vs UDP

### TCP (Transmission Control Protocol)
**Definition**: Connection-oriented protocol ensuring reliable, ordered data delivery.

**Key Characteristics:**
- ✅ **Reliable** - Guarantees delivery
- 🔄 **Connection-Oriented** - 3-way handshake
- 📊 **Ordered Delivery** - Maintains packet sequence
- ✔️ **Error Checking** - Validates data integrity
- 🎛️ **Flow Control** - Prevents receiver overload
- 🚦 **Congestion Control** - Adjusts to network traffic
- 📬 **Acknowledgments** - Confirms packet receipt
- 🔁 **Retransmission** - Resends lost packets

**TCP 3-Way Handshake:**
```
Client                    Server
   |                         |
   |-------- SYN ----------->|
   |<----- SYN-ACK ----------|
   |-------- ACK ----------->|
   |    Connection Open!     |
```

**Use Cases:**
- 🌐 Web browsing (HTTP/HTTPS)
- 📧 Email (SMTP, IMAP, POP3)
- 📁 File transfers (FTP, SFTP)
- 💻 Remote access (SSH)
- 🗄️ Database connections
- 💳 Financial transactions
- 📄 Any scenario requiring accuracy

### UDP (User Datagram Protocol)
**Definition**: Connectionless protocol prioritizing speed over reliability.

**Key Characteristics:**
- ⚡ **Fast** - Minimal overhead (8-byte header)
- 🔓 **Connectionless** - No handshake required
- ❌ **Unreliable** - No delivery guarantee
- 🔀 **No Ordering** - Packets arrive in any order
- 🚫 **No Acknowledgments** - Fire and forget
- 📡 **Broadcast Support** - Multiple recipients
- 🏃 **Low Latency** - Perfect for real-time apps
- 💨 **No Congestion Control** - Full speed ahead

**Use Cases:**
- 🎮 Online gaming
- 📹 Video/audio streaming
- 📞 VoIP (Voice over IP)
- 🔍 DNS lookups
- 📺 Live broadcasts
- 🎯 Real-time data feeds
- 🏐 Scenarios where some loss is acceptable

### TCP vs UDP Comparison

| Feature | TCP | UDP |
|---------|-----|-----|
| **Connection** | Connection-oriented | Connectionless |
| **Reliability** | Guaranteed delivery ✓ | Best-effort ⚠️ |
| **Ordering** | Maintains order ✓ | No ordering ❌ |
| **Speed** | Slower | Faster ⚡ |
| **Header Size** | 20-60 bytes | 8 bytes |
| **Error Checking** | Comprehensive | Basic/optional |
| **Flow Control** | Yes | No |
| **Retransmission** | Yes | No |
| **Use When** | Accuracy critical | Speed critical |

### Decision Tree: TCP vs UDP

```
Is data accuracy critical?
├─ YES → TCP
│   ├─ Web/API → HTTP/HTTPS over TCP
│   ├─ Email → SMTP/IMAP over TCP
│   ├─ File Transfer → FTP/SFTP over TCP
│   └─ Database → TCP connections
│
└─ NO → UDP
    ├─ Gaming → UDP for game state
    ├─ Streaming → RTP over UDP
    ├─ VoIP → SIP/RTP over UDP
    └─ DNS → UDP (with TCP fallback)
```

**Interview Tip:** "TCP guarantees delivery at the cost of speed. UDP prioritizes speed over reliability. Choose based on whether you can tolerate data loss."

---

## 3. HTTP Versions Evolution

### HTTP/1.0 (1996) - Legacy

**Key Features:**
- 📝 One request per connection
- 🔌 New TCP connection each time
- 📋 Basic headers
- ⚠️ Very inefficient

**Limitations:**
- High connection overhead
- No Host header (can't have multiple domains per IP)
- Limited caching

**Performance:** Baseline (100%) - Slowest

**Status:** ❌ Obsolete - Don't use

---

### HTTP/1.1 (1997) - Still Common

**Key Features:**
- 🔄 **Persistent Connections** - Keep-Alive
- 📨 **Pipelining** - Multiple requests (limited adoption)
- 🏠 **Host Header** - Virtual hosting enabled
- 🔧 **More Methods** - PUT, DELETE, OPTIONS, TRACE
- 💾 **Better Caching** - Cache-Control headers
- 📦 **Chunked Transfer** - Stream data

**Improvements Over 1.0:**
- Reuses TCP connections
- Supports multiple domains per IP
- Better caching mechanisms

**Performance:** 70-80% of HTTP/1.0 time

**Status:** ✅ Still widely used, but consider upgrading

**Limitations:**
- Head-of-line blocking (one slow request blocks others)
- Multiple connections needed (6-8 per domain)
- Inefficient headers (repeated on each request)

---

### HTTP/2.0 (2015) - Modern Standard

**Key Features:**
- 🔢 **Binary Protocol** - Efficient parsing
- 🔀 **Multiplexing** - Multiple requests on one connection
- 🗜️ **Header Compression (HPACK)** - Reduces overhead
- 📤 **Server Push** - Proactive resource sending
- 📊 **Stream Prioritization** - Optimize load order
- 🎛️ **Flow Control** - Better data management

**Major Improvements:**
- ✨ Single connection for all resources
- 🚀 Eliminates head-of-line blocking at HTTP layer
- 💾 Compressed headers (HPACK)
- ⚡ Significantly faster page loads

**Performance:** 40-60% of HTTP/1.0 time (2-3x faster!)

**Status:** ✅ Recommended for most applications

**Use Cases:**
- Modern web applications
- High-traffic websites
- Mobile-optimized sites
- SPAs (Single Page Applications)

---

### HTTP/3.0 (2020) - Latest & Greatest

**Key Features:**
- 🌐 **QUIC Protocol** - Based on UDP (not TCP!)
- 🔐 **Built-in TLS 1.3** - Encryption mandatory
- 📱 **Connection Migration** - Seamless network changes
- ⚡ **0-RTT** - Instant connection setup
- 🔀 **Independent Streams** - True parallel processing
- 📶 **Better Loss Recovery** - Works well on poor networks

**Revolutionary Changes:**
- Uses UDP instead of TCP
- No TCP head-of-line blocking at all
- Perfect for mobile (WiFi ↔ 4G/5G transitions)
- Faster connection establishment

**Performance:** 30-50% of HTTP/1.0 time (Best on unreliable networks)

**Status:** 🌟 Cutting edge - Use for mobile/global apps

**Use Cases:**
- Mobile-first applications
- Streaming services (Netflix, YouTube)
- Real-time applications
- Global audiences with varying network quality
- IoT devices

---

### HTTP Versions Comparison Table

| Feature | HTTP/1.0 | HTTP/1.1 | HTTP/2.0 | HTTP/3.0 |
|---------|----------|----------|----------|----------|
| **Year** | 1996 | 1997 | 2015 | 2020 |
| **Transport** | TCP (text) | TCP (text) | TCP (binary) | UDP (QUIC) |
| **Connections** | One per request | Persistent | Multiplexed | Multiplexed |
| **Header Compression** | None | None | HPACK | QPACK |
| **Encryption** | Optional | Optional | Recommended | Mandatory |
| **Server Push** | ❌ | ❌ | ✅ | ✅ |
| **Multiplexing** | ❌ | Limited | ✅ | ✅ |
| **Connection Migration** | ❌ | ❌ | ❌ | ✅ |
| **Performance** | Baseline | 70-80% | 40-60% | 30-50% |
| **Status** | Obsolete | Legacy | Modern | Latest |

### When to Use Each Version

| Scenario | Recommended Version | Why |
|----------|-------------------|-----|
| **New Web App** | HTTP/2 or HTTP/3 | Performance & features |
| **Mobile App** | HTTP/3 | Connection migration, 0-RTT |
| **Streaming Service** | HTTP/3 | Better loss recovery |
| **Legacy System** | HTTP/1.1 | Compatibility |
| **API Service** | HTTP/2 | Multiplexing benefits |
| **Global Users** | HTTP/3 | Works well on poor networks |

**Interview Tip:** "HTTP/2 brought multiplexing and binary protocol. HTTP/3 uses QUIC over UDP for even better performance, especially on mobile networks."

---

## 4. URL vs URI vs URN

### Understanding the Hierarchy

```
        URI (Uniform Resource Identifier)
       /                                \
      /                                  \
    URL                                  URN
 (Locator)                             (Name)
 "Where & How"                        "What"
```

### URI (Uniform Resource Identifier)
**Definition**: Generic term for identifying any resource.

**Key Points:**
- 🌐 **Broadest Concept** - Encompasses URL and URN
- 📝 **Format:** `scheme:[//authority]path[?query][#fragment]`
- 🎯 **Purpose:** Identify resources universally

**Examples:**
- `https://www.example.com/index.html` (also a URL)
- `mailto:user@example.com`
- `urn:isbn:0451450523` (also a URN)
- `file:///C:/Users/file.txt`

---

### URL (Uniform Resource Locator)
**Definition**: Specifies WHERE a resource is and HOW to access it.

**Key Points:**
- 📍 **Location-Based** - Tells you how to find it
- 🔧 **Access Mechanism** - Includes protocol
- 📝 **Format:** `protocol://host[:port]/path[?query][#fragment]`

**Components Breakdown:**
```
https://www.example.com:443/api/users?id=123&sort=asc#profile
│      │                  │   │        │                 │
│      │                  │   │        │                 └─ Fragment
│      │                  │   │        └─ Query Parameters
│      │                  │   └─ Path
│      │                  └─ Port (optional)
│      └─ Host (domain or IP)
└─ Protocol (http, https, ftp, etc.)
```

**Examples:**
- `https://api.example.com/v1/users`
- `ftp://ftp.example.com/files/doc.pdf`
- `http://localhost:8080/api`

**Use Cases:**
- ✅ Web page addresses
- ✅ API endpoints
- ✅ File downloads
- ✅ Resource locations
- ✅ Any access-based identification

---

### URN (Uniform Resource Name)
**Definition**: Identifies WHAT a resource is (by name), not where it is.

**Key Points:**
- 🏷️ **Name-Based** - Persistent identifier
- 🔒 **Location-Independent** - Doesn't change if moved
- 📝 **Format:** `urn:<namespace>:<identifier>`
- ♾️ **Permanent** - Designed to never change

**Examples:**
- `urn:isbn:0451450523` (Book ISBN)
- `urn:uuid:6e8bc430-9c3a-11d9-9669-0800200c9a66` (UUID)
- `urn:ietf:rfc:2648` (RFC document)
- `urn:issn:0167-6423` (Journal ISSN)

**Use Cases:**
- ✅ Library catalogs
- ✅ Document identifiers
- ✅ Digital object identifiers
- ✅ Standards and specifications
- ✅ Persistent naming

---

### Key Differences

| Feature | URL | URI | URN |
|---------|-----|-----|-----|
| **Purpose** | Locate & access | Identify | Name persistently |
| **Protocol** | Required (http, ftp) | Maybe | Never |
| **Location** | Specifies location | Maybe | No |
| **Access Info** | Yes (how to get) | Maybe | No |
| **Persistence** | Changes if moved | Varies | Always permanent |
| **Focus** | "Where" & "How" | "What" | "Who/What" |
| **Example** | https://example.com | Any identifier | urn:isbn:123 |

### Relationships

- ✅ **All URLs are URIs** (URL ⊂ URI)
- ✅ **All URNs are URIs** (URN ⊂ URI)
- ❌ **URLs and URNs are distinct** (URL ≠ URN)
- 📝 **URI is the umbrella term** (URI = URL ∪ URN)

**Interview Tip:** "A URL tells you where something is and how to get it. A URN tells you what something is called. Both are types of URIs."

---

## 5. Interview Cheat Sheet

### Most Common Interview Questions

#### Q1: "What's the difference between TCP and UDP?"

**Answer Template:**
> "TCP is connection-oriented and guarantees reliable, ordered delivery through 3-way handshake, acknowledgments, and retransmissions. It's perfect for web browsing, email, and file transfers where accuracy is critical.
> 
> UDP is connectionless and prioritizes speed over reliability. There's no handshake, no acknowledgments, and no guaranteed delivery. It's ideal for streaming, gaming, and VoIP where low latency matters more than perfect accuracy."

**Key Points to Mention:**
- TCP: Reliable, ordered, connection-oriented (3-way handshake)
- UDP: Fast, connectionless, best-effort delivery
- Trade-off: Reliability vs Speed
- Use cases for each

---

#### Q2: "Why is HTTPS important?"

**Answer Template:**
> "HTTPS is mandatory for production systems because it provides:
> 1. **Encryption** - Protects data from interception
> 2. **Authentication** - Verifies server identity
> 3. **Integrity** - Prevents data tampering
> 4. **Compliance** - Required by GDPR, PCI-DSS
> 5. **SEO** - Search engines prioritize HTTPS
> 6. **Trust** - Users expect the padlock icon
> 
> The TLS handshake adds minimal latency (~40-100ms) but is essential for security."

---

#### Q3: "Explain HTTP/2 improvements over HTTP/1.1"

**Answer Template:**
> "HTTP/2 introduced several major improvements:
> 1. **Binary Protocol** - More efficient than text-based HTTP/1.1
> 2. **Multiplexing** - Multiple requests on single connection
> 3. **Header Compression (HPACK)** - Reduces overhead significantly
> 4. **Server Push** - Server can send resources proactively
> 5. **Stream Prioritization** - Optimize resource loading
> 
> This eliminates the need for multiple connections and removes head-of-line blocking at the HTTP layer, resulting in 2-3x faster page loads."

---

#### Q4: "What's the difference between HTTP/2 and HTTP/3?"

**Answer Template:**
> "HTTP/3 uses QUIC protocol over UDP instead of TCP. Key advantages:
> 1. **No TCP head-of-line blocking** - True independent streams
> 2. **0-RTT connection** - Faster connection establishment
> 3. **Connection migration** - Seamless network switching (WiFi to 4G)
> 4. **Built-in TLS 1.3** - Security by default
> 5. **Better packet loss recovery** - Improved performance on unreliable networks
> 
> This makes HTTP/3 ideal for mobile applications and global users with varying network quality."

---

#### Q5: "When would you use TCP vs UDP in system design?"

**Answer Template:**
> "I'd use **TCP** when:
> - Data accuracy is critical (financial transactions, file transfers)
> - Order matters (chat messages, email)
> - Need delivery confirmation
> - Examples: REST APIs, databases, web traffic
> 
> I'd use **UDP** when:
> - Speed is more important than accuracy
> - Real-time delivery is critical
> - Some data loss is acceptable
> - Examples: Video streaming, online gaming, VoIP, DNS
> 
> Sometimes we use both - like gaming using UDP for position updates but TCP for inventory/transactions."

---

#### Q6: "How would you reduce network latency?"

**Answer Template:**
> "I'd approach this with multiple strategies:
> 
> **Geographic Distribution:**
> - Use CDN for static content
> - Deploy in multiple regions
> - DNS-based routing to nearest datacenter
> 
> **Protocol Optimization:**
> - Use HTTP/2 or HTTP/3
> - Connection pooling and Keep-Alive
> - Implement 0-RTT where possible
> 
> **Caching:**
> - Browser caching with proper headers
> - CDN edge caching
> - Application-level caching (Redis, Memcached)
> 
> **Data Optimization:**
> - Compression (gzip, Brotli)
> - Image optimization
> - Minimize payload size
> 
> **Network Level:**
> - TCP optimization (buffer sizes)
> - Reduce DNS lookup time
> - Preconnect/prefetch hints"

---

### Quick Reference: Protocols Summary

| Protocol | Type | Speed | Reliability | Use Case |
|----------|------|-------|-------------|----------|
| TCP | Transport | Slower | ✅ Guaranteed | Web, Email, Files |
| UDP | Transport | Faster | ⚠️ Best-effort | Streaming, Gaming |
| HTTP/1.1 | Application | Moderate | ✅ Yes | Legacy web |
| HTTP/2 | Application | Fast | ✅ Yes | Modern web |
| HTTP/3 | Application | Fastest | ✅ Yes | Mobile, Streaming |
| WebSocket | Application | Fast | ✅ Yes | Real-time bidirectional |
| gRPC | Application | Fastest | ✅ Yes | Internal microservices |

---

## 6. Quick Decision Guide

### Choose Your Protocol

#### For Web Applications:
```
Legacy/Simple App → HTTP/1.1 over TCP
Modern Web App   → HTTP/2 over TCP
Mobile-First App → HTTP/3 (QUIC)
Real-time App    → WebSocket over TCP
```

#### For Data Transmission:
```
Must be accurate → TCP
Speed critical   → UDP
File transfer    → TCP (SFTP)
Live streaming   → UDP (RTP)
```

#### For APIs:
```
External REST API     → HTTPS + HTTP/2
Internal Microservices → gRPC (HTTP/2)
Real-time Updates     → WebSocket
Public API            → HTTPS + HTTP/2 or HTTP/3
```

### Performance Optimization Priority

**Level 1 - Most Impact:**
1. ✅ Use HTTP/2 or HTTP/3
2. ✅ Enable HTTPS with TLS 1.3
3. ✅ Implement CDN for static assets
4. ✅ Connection pooling/Keep-Alive

**Level 2 - Medium Impact:**
1. ✅ Compression (gzip, Brotli)
2. ✅ Browser caching headers
3. ✅ Image optimization
4. ✅ Minification (CSS, JS)

**Level 3 - Fine-tuning:**
1. ✅ DNS optimization
2. ✅ TCP buffer tuning
3. ✅ Resource hints (preconnect, prefetch)
4. ✅ HTTP/2 Server Push

---

## 7. Key Takeaways

### Essential Facts for Interviews

#### 🔐 Security
- ✅ **HTTPS is mandatory** for all production systems
- ✅ TLS 1.3 is the current standard
- ✅ Encryption adds ~40-100ms latency (acceptable trade-off)

#### 🌐 Protocols
- ✅ **TCP** = Reliable, ordered, connection-oriented
- ✅ **UDP** = Fast, connectionless, best-effort
- ✅ **HTTP/2** = Multiplexing, binary, compressed headers
- ✅ **HTTP/3** = QUIC (UDP-based), 0-RTT, connection migration

#### 📍 Resource Identification
- ✅ **URL** = Location + Access method (where & how)
- ✅ **URN** = Persistent name (what)
- ✅ **URI** = Generic term (URL or URN)

#### ⚡ Performance
- ✅ Network latency is critical in distributed systems
- ✅ Geographic distribution reduces latency
- ✅ Connection pooling reduces overhead
- ✅ Caching at multiple levels (browser, CDN, app)

#### 🎯 Decision Criteria

**Use TCP when:**
- ✅ Data accuracy is critical
- ✅ Order matters
- ✅ Need delivery confirmation

**Use UDP when:**
- ✅ Speed > accuracy
- ✅ Real-time is critical
- ✅ Some loss acceptable

**Use HTTP/2 when:**
- ✅ Building modern web apps
- ✅ Need better performance than HTTP/1.1
- ✅ Wide browser support needed

**Use HTTP/3 when:**
- ✅ Mobile-first application
- ✅ Global users with varying network quality
- ✅ Real-time streaming services

---

### Memory Aids

**TCP vs UDP:**
> "TCP = **T**rust **C**omplete **P**ackages (reliable)
> UDP = **U**ltra **D**eliver **P**ost (fast but no guarantee)"

**HTTP Evolution:**
> "1.0 = One at a time
> 1.1 = Keep connection alive
> 2.0 = Multiplex everything
> 3.0 = QUIC & UDP revolution"

**URL vs URN:**
> "URL = Where (location)
> URN = What (name)
> URI = Either (identifier)"

---

### Common Mistakes to Avoid

❌ **Don't:** Use HTTP in production
✅ **Do:** Always use HTTPS

❌ **Don't:** Open new TCP connection per request
✅ **Do:** Use connection pooling

❌ **Don't:** Ignore network latency in design
✅ **Do:** Consider geographic distribution

❌ **Don't:** Use UDP when reliability is critical
✅ **Do:** Choose TCP for important data

❌ **Don't:** Stick with HTTP/1.1 for new projects
✅ **Do:** Use HTTP/2 or HTTP/3

---

### System Design Checklist

Before finalizing your design, verify:

**Protocol Layer:**
- [ ] TCP or UDP choice justified
- [ ] HTTPS enabled everywhere
- [ ] TLS 1.3 specified
- [ ] Connection pooling planned

**HTTP Layer:**
- [ ] HTTP/2 or HTTP/3 selected
- [ ] Multiplexing considered
- [ ] Header compression noted
- [ ] Caching strategy defined

**Performance:**
- [ ] Network latency calculated
- [ ] Geographic distribution planned
- [ ] CDN usage considered
- [ ] Compression enabled

**Reliability:**
- [ ] Timeout values specified
- [ ] Retry logic with backoff
- [ ] Circuit breaker pattern
- [ ] Failover strategy defined

---

## Summary

This document consolidates networking fundamentals from four key files:
- **01-HTTPvsHTTPS.md** - Security protocols
- **02-TCPvsUDP.md** - Transport layer protocols  
- **03-HTTP10vs11vs20vs30.md** - HTTP evolution
- **04-URLvsURIvsURN.md** - Resource identification

### Final Interview Tips

1. **Start with fundamentals**: "Network performance is critical in distributed systems"
2. **Justify choices**: Always explain WHY you chose TCP vs UDP, HTTP/2 vs HTTP/3
3. **Mention security**: HTTPS is non-negotiable
4. **Consider scale**: Geographic distribution, CDN, caching
5. **Know trade-offs**: Speed vs reliability, performance vs complexity
6. **Be specific**: Use real numbers (latency, throughput, connections)
7. **Think holistically**: Network is part of the bigger system

**Remember:** Network performance is foundational. Every system design involves networking decisions. Know your protocols, understand trade-offs, and always consider latency, reliability, and security.

---

**Last Updated:** January 7, 2025
**Source Files:** 01-HTTPvsHTTPS.md, 02-TCPvsUDP.md, 03-HTTP10vs11vs20vs30.md, 04-URLvsURIvsURN.md

Good luck with your system design interviews! 🚀

