# Load Balancer Algorithms

Load balancer algorithms are methods used to determine how incoming requests are distributed across multiple servers or resources. Different algorithms serve different needs based on factors such as server capacity, current load, response time, and the specific requirements of the application.

## Common Load Balancing Algorithms

### 1. Round Robin
The simplest load balancing algorithm that distributes requests sequentially to each server in the pool.

**Characteristics:**
- Every server receives requests in a cyclical order
- Equal distribution of requests, regardless of server capacity or current load
- Simple to implement and understand

**Best for:**
- Environments with servers of similar specifications
- Situations where requests generally require similar processing power

**Example:**
If there are three servers A, B, and C, the first request goes to A, second to B, third to C, fourth to A again, and so on.

### 2. Weighted Round Robin
An extension of the Round Robin algorithm that assigns different weights to servers based on their capacity.

**Characteristics:**
- Servers with higher capacity receive more requests
- Weights are predetermined based on server specifications
- Still follows a cyclical pattern, but adjusted based on weights

**Best for:**
- Environments with heterogeneous servers of varying capacities
- When you want to control the proportion of traffic to each server

**Example:**
If server A has weight 3, B has weight 2, and C has weight 1, then A will receive three requests, B will receive two requests, and C will receive one request in each cycle.

### 3. Least Connection
Directs traffic to the server with the fewest active connections.

**Characteristics:**
- Considers the current load on each server
- More requests go to less busy servers
- Dynamic adjustment based on real-time server load

**Best for:**
- Situations with varying request processing times
- When connection times vary significantly between requests

**Example:**
If server A has 10 active connections, B has 15, and C has 5, the next request will be directed to C.

### 4. Weighted Least Connection
Combines the Least Connection method with weighted server capacities.

**Characteristics:**
- Considers both server capacity and current connection count
- Higher capacity servers can handle more connections
- Balances load based on the ratio of current connections to weight

**Best for:**
- Environments with heterogeneous servers and varying request processing times

**Example:**
If server A has weight 3 and 9 connections, B has weight 2 and 4 connections, and C has weight 1 and 3 connections, then the connection-to-weight ratios are A: 3, B: 2, C: 3. The next request would go to server B.

### 5. IP Hash
Generates a hash based on the client's IP address to determine which server receives the request.

**Characteristics:**
- The same client (IP) is always directed to the same server
- Provides session persistence without requiring cookies or other tracking methods
- Distribution may become uneven if many clients come from the same subnet

**Best for:**
- Applications that require session persistence
- When client sessions must be maintained on the same server

**Example:**
A user with IP 192.168.1.1 always gets directed to server B based on the hash calculation, ensuring all requests from that user go to the same server.

### 6. Least Response Time
Directs traffic to the server with the lowest response time and fewest active connections.

**Characteristics:**
- Considers both the response time and the number of connections
- More sensitive to performance variations
- Requires active monitoring of server response times

**Best for:**
- Applications where speed is critical
- Environments with performance variations between servers

**Example:**
Server A has a 20ms response time and 10 connections, B has 15ms and 15 connections, and C has 10ms and 12 connections. The next request would go to C due to its faster response time.

### 7. Random
Randomly selects a server from the pool for each incoming request.

**Characteristics:**
- Simple implementation
- No need to maintain state information
- Over time, should distribute evenly but may have short-term imbalances

**Best for:**
- Simple implementations with similar server capacities
- When statistical load balancing is sufficient

**Example:**
Each new request has an equal probability of being assigned to any server in the pool.

### 8. URL-Based
Directs requests to specific servers based on the requested URL.

**Characteristics:**
- Routes requests based on the content being requested
- Can optimize for specific content types or applications
- Allows for content-based specialization of servers

**Best for:**
- Applications where different content types have different resource requirements
- When specific servers are optimized for particular content

**Example:**
All image requests (/images/*) go to server A, all API calls (/api/*) go to server B, and all HTML pages go to server C.

### 9. Least Bandwidth
Directs traffic to the server currently serving the least amount of traffic (measured in Mbps).

**Characteristics:**
- Based on actual bandwidth consumption
- Useful for media-heavy applications
- Requires continuous monitoring of traffic volume

**Best for:**
- Applications with varying bandwidth requirements
- When network capacity is a primary concern

**Example:**
Server A is currently handling 50 Mbps, B is handling 30 Mbps, and C is handling 20 Mbps. The next request would go to server C.

### 10. Resource-Based (Adaptive)
Distributes requests based on the real-time resource usage of servers (CPU, memory, etc.).

**Characteristics:**
- Monitors various system resources
- Makes decisions based on comprehensive server health
- Requires agent software on servers to report metrics

**Best for:**
- Complex applications with varying resource requirements
- When detailed monitoring and adaptive load balancing are needed

**Example:**
Server A has 80% CPU usage, B has 40%, and C has 60%. The next CPU-intensive request would go to server B due to its lower CPU utilization.
