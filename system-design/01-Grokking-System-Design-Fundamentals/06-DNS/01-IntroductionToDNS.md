# Domain Name System (DNS)

## 🔍 Definition
> DNS (Domain Name System) is a system that translates human-readable domain names (e.g., www.example.com) into IP addresses (e.g., 93.184.216.34) that computers use to identify and communicate with each other on the internet. It functions like a phonebook for the internet.

## 🧠 Key Ideas
- Translates user-friendly domain names to numeric IP addresses for computer communication
- Hierarchical and distributed system with multiple server types for scalability and reliability
- Components include domain names, TLDs (Top-Level Domains), subdomains, and DNS servers
- DNS server types: Root servers, TLD servers, and Authoritative Name servers
- DNS resolver types: Stub, Recursive, Caching-only, Forwarder, and Iterative (Non-recursive)
- Uses caching to improve performance and reduce lookup times
- Enables load balancing by distributing traffic across multiple servers

### DNS Components Explained
1. **Domain Names**: Human-readable addresses like example.com that identify websites or services on the internet.
   - Structure: Read from right to left with dots separating levels in the hierarchy.
   - Example: In "www.example.com", "com" is the TLD, "example" is the second-level domain, and "www" is a subdomain.

2. **TLDs (Top-Level Domains)**: The highest level in the DNS hierarchy, appearing as the rightmost part of a domain name.
   - Types:
     - Generic TLDs (gTLDs): .com, .org, .net, .edu
     - Country-code TLDs (ccTLDs): .us, .uk, .ca, .jp
     - Special-purpose TLDs: .gov, .mil, .int
   - Management: Each TLD is managed by a designated organization under ICANN oversight.

3. **Subdomains**: Subdivisions of a domain that can represent different sections of a website or services.
   - Examples: blog.example.com, shop.example.com, api.example.com
   - Purpose: Allow organizations to logically partition their domain namespace.

### DNS Server Types
1. **Root Servers**: The foundation of the DNS hierarchy.
   - Function: Direct queries to appropriate TLD servers.
   - Deployment: 13 root server clusters worldwide (labeled A-M), each with multiple instances via anycast.
   - Management: Operated by 12 different organizations.

2. **TLD Servers**: Maintain information about domains within a specific TLD.
   - Function: Direct queries to the authoritative name servers for a domain.
   - Example: .com TLD servers know which authoritative servers handle example.com.

3. **Authoritative Name Servers**: Hold the actual DNS records for specific domains.
   - Function: Provide definitive answers about a domain's DNS records.
   - Types of records maintained:
     - A records: Map domain names to IPv4 addresses
     - AAAA records: Map domain names to IPv6 addresses
     - CNAME records: Alias one domain name to another
     - MX records: Specify mail servers for a domain
     - TXT records: Store text information (often used for verification)
     - NS records: Indicate which servers are authoritative for a domain

### DNS Resolver Types
1. **Stub Resolver**: The minimal DNS client on end-user devices.
   - Function: Initiates DNS lookups by forwarding requests to a configured DNS server.
   - Behavior: Does not perform recursive lookups itself.
   - Example: The DNS client built into your operating system that forwards queries to your ISP's DNS or a public resolver.

2. **Recursive Resolver**: Performs complete DNS lookups on behalf of clients.
   - Function: Queries the entire DNS hierarchy until it finds the answer.
   - Examples: Google's 8.8.8.8, Cloudflare's 1.1.1.1, or your ISP's DNS servers.
   - Behavior: Caches responses to speed up future lookups.

3. **Caching-Only Resolver**: Specializes in caching DNS responses but does not host any authoritative records.
   - Function: Stores recent DNS query results to reduce lookup latency.
   - Behavior: Honors TTL (Time-To-Live) values to determine how long to cache entries.
   - Example: Many home routers cache DNS responses from upstream servers.

4. **Forwarder**: Forwards queries to another DNS server instead of performing lookups itself.
   - Function: Acts as an intermediary between stub resolvers and recursive resolvers.
   - Use case: Used in corporate networks for centralized DNS management and filtering.
   - Behavior: May maintain a cache of recent lookups.

5. **Iterative (Non-Recursive) Resolver**: Provides referrals rather than complete answers.
   - Function: Returns the best information it has, often directing the client to another server.
   - Behavior: Says "I don't know the answer, but try asking this server" rather than doing the work itself.
   - Example: Root and TLD servers typically operate iteratively, providing referrals to the next level in the hierarchy.

## 📊 Real-world Use Case
When you type www.example.com in your browser, your device's stub resolver sends a DNS query to a recursive resolver (like Google's 8.8.8.8). If not cached, the resolver queries through the DNS hierarchy: root servers → TLD servers (.com) → authoritative servers for example.com. Once found, the IP address is returned to your device, allowing your browser to connect to the website.

## 📈 Diagram
```
User's Device (Stub Resolver)
     |
     v
Recursive Resolver (ISP/Public)
     |
     v
  Root Server
     |
     v
 TLD Server (.com, .net, etc.)
     |
     v
 Authoritative Server (example.com)
     |
     v
   IP Address
```

## ⚔️ Pros & Cons
### ✅ Pros:
- User-friendly navigation with memorable domain names instead of IP addresses
- Distributed architecture provides redundancy and fault tolerance
- Caching improves performance and reduces network traffic
- Enables load balancing and high availability
- Flexibility allows websites to change IPs without affecting users

### ❌ Cons:
- DNS propagation delays when records are updated
- Vulnerable to attacks like DNS spoofing, cache poisoning, and DDoS
- DNS misconfigurations can cause widespread outages
- Single point of failure if not properly configured with redundancy

## 💬 Interview Context
- Foundational to system design discussions about web architecture
- Common follow-up questions:
  - How does DNS caching work and what is TTL (Time To Live)?
  - Explain the DNS resolution process step-by-step
  - How can DNS be used for load balancing and failover?
  - What security measures protect DNS from attacks?
  - How do CDNs use DNS for geographic routing?

## 🔁 Revisit Frequency
[ ] Once a week  
[x] Once a month  
[x] Before interviews

---
