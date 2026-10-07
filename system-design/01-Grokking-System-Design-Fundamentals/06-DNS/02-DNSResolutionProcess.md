# DNS Resolution Process

## 🔍 Definition
> DNS Resolution is the process of converting a human-readable domain name (e.g., example.com) into its corresponding IP address through a series of queries across the distributed DNS server hierarchy.

## 🧠 Key Ideas
- Uses both recursive and iterative queries to resolve domain names to IP addresses
- Employs caching at multiple levels to improve performance and reduce network traffic
- Functions as a distributed, hierarchical system for reliability and scalability
- Implements Time To Live (TTL) values to control how long DNS records remain in cache
- Utilizes negative caching to prevent repeated queries for non-existent records

### Types of DNS Queries
1. **Recursive Queries**: 
   - The resolver requests the complete answer from a DNS server
   - The server takes full responsibility to find the answer by querying other servers
   - The server returns a complete answer or an error
   - Example: Your computer asking your ISP's DNS server to resolve example.com

2. **Iterative Queries**: 
   - The resolver asks for the best answer the server currently has
   - If the server doesn't have the complete answer, it returns a referral to another server
   - The resolver must follow these referrals until it finds the answer
   - Example: A recursive resolver querying root servers, then TLD servers, then authoritative servers

### DNS Caching
- **Purpose**: Speeds up resolution and reduces network traffic
- **Implementation**: DNS records are stored temporarily at various levels:
  - Browser cache
  - Operating system cache
  - Recursive resolver cache
  - ISP's DNS server cache
- **TTL (Time To Live)**: Controls how long records remain in cache (measured in seconds)
  - Short TTL: Better for frequently changing records but increases DNS traffic
  - Long TTL: Reduces DNS traffic but can lead to outdated information

### Negative Caching
- Stores information about non-existent domains or records
- Prevents repeated queries for resources that don't exist
- Typically has shorter TTL than positive caching
- Improves performance by reducing unnecessary DNS traffic

## 📊 Real-world Use Case
When you visit Netflix.com, your browser initiates a DNS lookup. Your device's stub resolver sends a recursive query to your configured DNS resolver (like 8.8.8.8). If the resolver has the IP cached, it returns it immediately. Otherwise, it starts with iterative queries to root servers, then .com TLD servers, then Netflix's authoritative servers until it finds the correct IP. This IP is then cached at multiple levels for future requests, making subsequent visits faster.

## 📈 Diagram
```
1. User's browser requests example.com
   |
   v
2. OS stub resolver checks local cache (miss)
   |
   v
3. Recursive query to ISP's DNS server
   |
   v
4. ISP's resolver checks its cache (miss)
   |
   v
5. Iterative query to root server
   |
   v
6. Root server returns referral to .com TLD server
   |
   v
7. Iterative query to .com TLD server
   |
   v
8. TLD server returns referral to example.com authoritative server
   |
   v
9. Iterative query to example.com authoritative server
   |
   v
10. Authoritative server returns IP address
    |
    v
11. Result is cached and returned to user
```

## ⚔️ Pros & Cons
### ✅ Pros:
- Distributed architecture ensures reliability and fault tolerance
- Caching at multiple levels improves resolution speed and reduces network traffic
- Hierarchical structure enables scalability to billions of domain names
- TTL mechanism allows flexibility in managing how quickly record changes propagate

### ❌ Cons:
- Multiple lookups can introduce latency for uncached domains
- Caching can lead to stale data if DNS records change before TTL expires
- Complex resolution chain creates multiple potential points of failure
- Vulnerable to various attacks (cache poisoning, DDoS) if not properly secured

## 💬 Interview Context
- Essential knowledge for system design interviews involving web architecture
- Common follow-up questions:
  - How does DNS resolution affect website performance?
  - What strategies can reduce DNS lookup latency?
  - How does DNS-based global load balancing work?
  - How do DNS security mechanisms like DNSSEC work?
  - What happens during a DNS failover scenario?

## 🔁 Revisit Frequency
[ ] Once a week  
[x] Once a month  
[x] Before interviews

---

