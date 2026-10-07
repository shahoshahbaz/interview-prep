# Load Balancer - Complete Summary

## Quick Reference Guide for System Design Interviews

This document summarizes all key concepts about load balancing for system design interviews.

---

## 1. What is Load Balancing?

**Definition**: A technique to distribute incoming requests and traffic evenly across multiple servers to ensure high availability, reliability, and performance.

**Key Purpose**:
- Reduce load on individual servers
- Prevent single point of failure
- Improve application availability and responsiveness
- Enable horizontal scaling

**Placement in Architecture**:
Load balancers can be placed at three critical layers:
1. Between users and web servers
2. Between web servers and application/platform layer
3. Between application layer and database

---

## 2. Load Balancing Algorithms

### Simple Algorithms
1. **Round Robin**: Sequential distribution to each server in a cycle
   - Best for: Similar server specifications
   - Simple but doesn't consider server capacity

2. **Weighted Round Robin**: Distributes based on server capacity weights
   - Best for: Heterogeneous servers with varying capacities

3. **Random**: Randomly selects a server for each request
   - Best for: Simple implementations with similar servers

### Dynamic Algorithms
4. **Least Connection**: Routes to server with fewest active connections
   - Best for: Varying request processing times

5. **Weighted Least Connection**: Considers both capacity and current connections
   - Best for: Heterogeneous servers with varying request times

6. **Least Response Time**: Routes to server with lowest response time
   - Best for: Applications where speed is critical

7. **Least Bandwidth**: Routes to server serving least traffic (Mbps)
   - Best for: Media-heavy applications

8. **Resource-Based (Adaptive)**: Distributes based on real-time resource usage (CPU, memory)
   - Best for: Complex applications with varying resource requirements

### Specialized Algorithms
9. **IP Hash**: Uses client IP to consistently route to same server
   - Best for: Session persistence requirements

10. **URL-Based**: Routes based on requested URL
    - Best for: Content-based specialization

---

## 3. Common Uses of Load Balancing

1. **Website Performance**: Faster response times by distributing traffic
2. **High Availability**: Prevents downtime by redirecting from failed servers
3. **Scalability**: Easy addition of servers without infrastructure changes
4. **Redundancy**: Maintains multiple copies to prevent data loss
5. **Network Optimization**: Distributes traffic across multiple paths
6. **Geographic Distribution**: Routes users to nearest data center
7. **Application Performance**: Dedicates resources per application
8. **Security**: Mitigates DDoS attacks by distributing malicious traffic
9. **Cost Savings**: Optimizes resource usage and reduces energy consumption
10. **Content Caching**: Serves static content directly from load balancer

---

## 4. Types of Load Balancers

### By Implementation
1. **Hardware Load Balancers**
   - Pros: High performance, built-in features
   - Cons: Expensive, limited scalability
   - Example: E-commerce with high traffic volumes

2. **Software Load Balancers**
   - Pros: Affordable, flexible, easily scalable
   - Cons: Lower performance under heavy loads
   - Example: Startups with growing traffic

3. **Cloud-based Load Balancers**
   - Pros: Highly scalable, simplified management, pay-as-you-go
   - Cons: Vendor dependence, less control
   - Example: Mobile apps with variable traffic

### By Mechanism
4. **DNS Load Balancing**
   - Resolves domain to multiple IPs
   - Simple but limited by DNS caching

5. **Global Server Load Balancing (GSLB)**
   - Distributes across geographic locations
   - Combines DNS with health checks

### By Network Layer
6. **Layer 4 (Transport Layer)**
   - Routes based on IP addresses and ports
   - Fast but lacks application awareness
   - Example: Online gaming platforms

7. **Layer 7 (Application Layer)**
   - Routes based on HTTP headers, cookies, URL paths
   - Intelligent but more resource-intensive
   - Example: Microservices with API routing

8. **Hybrid Load Balancing**
   - Combines multiple techniques
   - Highly flexible but complex

---

## 5. Stateless vs. Stateful Load Balancing

### Stateless Load Balancing
- **Behavior**: No session information maintained
- **Routing**: Based only on current request data
- **Best for**: Independent request processing
- **Example**: Location-based product search

### Stateful Load Balancing
- **Behavior**: Maintains session information
- **Routing**: Same client → same server
- **Best for**: Applications requiring session data

**Types of Stateful Balancing**:
1. **Source IP Affinity**: Based on client IP address
2. **Session Affinity**: Based on session identifier (cookie/URL parameter)

---

## 6. High Availability and Fault Tolerance

### Redundancy Strategies
1. **Active-Passive Configuration**
   - One active, one standby
   - Simple but underutilizes passive instance

2. **Active-Active Configuration**
   - Multiple load balancers handling traffic simultaneously
   - Better resource utilization and fault tolerance

### Health Checks and Monitoring
- Regular health checks ensure only healthy servers receive traffic
- Monitors response times, error rates, resource usage
- Prevents cascading failures
- Requires proper alerting and incident response

### Synchronization
- **Centralized Configuration**: Using etcd, Consul, or ZooKeeper
- **State Sharing**: Via database replication or distributed caching (Redis, Memcached)

---

## 7. Scalability and Performance

### Scaling Approaches
1. **Horizontal Scaling** (Preferred)
   - Add more load balancer instances
   - Effective for active-active configurations
   - Uses DNS or additional load balancer layer

2. **Vertical Scaling**
   - Increase resources of existing instances
   - Limited by single instance capacity

### Connection Management
- **Rate Limiting**: Prevents overload and DoS attacks
- Can be based on IP, domain, or URL patterns

### Performance Optimization
1. **Caching**: Static content (images, CSS, JS)
2. **Content Optimization**: Compression and minification
3. **Geographic Distribution**: Reduced latency
4. **Connection Reuse**: Keep-alive connections
5. **Protocol Optimizations**: HTTP/2 or QUIC

---

## 8. Key Challenges and Solutions

| Challenge | Issue | Solution |
|-----------|-------|----------|
| **Single Point of Failure** | Load balancer itself fails | Implement redundant instances with failover |
| **Configuration Complexity** | Misconfigurations cause outages | Use automation, regular reviews, expert consultation |
| **Scalability Limitations** | Load balancer becomes bottleneck | Plan for horizontal/vertical scaling |
| **Increased Latency** | Additional network hop | Optimize algorithms, geographic placement |
| **Sticky Sessions** | Uneven load distribution | Advanced techniques or stateless design |
| **Cost** | High infrastructure costs | Open-source or cloud pay-as-you-go |
| **Health Check Issues** | Traffic to failed servers | Comprehensive checks and real-time monitoring |

---

## 9. Interview Tips

### Key Terminology to Know
- **Backend Servers**: Server pool/farm receiving distributed traffic
- **Load Balancing Algorithm**: Method to determine traffic distribution
- **Health Checks**: Periodic tests for server availability
- **Session Persistence**: Directing same client to same server
- **SSL/TLS Termination**: Decrypting at load balancer level

### When to Use Load Balancers in Design
- High traffic applications (e.g., Instagram, Netflix)
- Systems requiring high availability (e.g., banking apps)
- Microservices architectures
- Geographic distribution needs
- Security against DDoS attacks

### Common Interview Questions to Prepare
1. Explain different load balancing algorithms and when to use each
2. How would you design a load balancer for [specific system]?
3. Stateless vs stateful load balancing trade-offs?
4. How to ensure load balancer doesn't become single point of failure?
5. Layer 4 vs Layer 7 load balancing differences?
6. How to handle sticky sessions at scale?

### Design Considerations Checklist
- [ ] Identify where to place load balancers (user→web, web→app, app→db)
- [ ] Choose appropriate algorithm based on requirements
- [ ] Plan for high availability (active-active/active-passive)
- [ ] Consider health checks and monitoring
- [ ] Address scalability (horizontal vs vertical)
- [ ] Evaluate stateless vs stateful needs
- [ ] Plan for geographic distribution if needed
- [ ] Consider security implications (DDoS, SSL termination)
- [ ] Budget and cost considerations (hardware/software/cloud)

---

## 10. Real-World Example: Instagram Design

**Load Balancer Usage**:
- Between users and web servers: Distribute millions of photo requests
- Between web servers and application servers: Separate upload/view operations
- Between application layer and database: Distribute queries across shards

**Key Decisions**:
- Use Layer 7 for content-based routing (uploads vs views vs API)
- Implement caching at load balancer for popular photos
- Geographic distribution with GSLB for global users
- Active-active configuration for high availability
- Health checks to route around failed servers
- Rate limiting to prevent abuse and DoS

---

## Summary

Load balancers are critical for:
✅ **Performance**: Fast response times through traffic distribution
✅ **Availability**: No single point of failure with redundancy
✅ **Scalability**: Easy horizontal scaling by adding servers
✅ **Reliability**: Health checks ensure traffic goes to healthy servers
✅ **Security**: DDoS mitigation and SSL termination
✅ **Cost Efficiency**: Optimized resource utilization

**Remember**: The choice of load balancing strategy depends on specific system requirements, traffic patterns, and constraints. Always consider trade-offs between complexity, cost, performance, and availability.

