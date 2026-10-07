# Service Discovery

> Status: Placeholder — concept-level notes.

## What it is
Mechanism for services to find network locations (IP:port) of other service instances in a dynamic environment where instances scale up/down and change addresses.

## Patterns
- **Client-side discovery**: client queries a service registry directly and load-balances itself (e.g., Netflix Eureka).
- **Server-side discovery**: client calls a load balancer/router, which queries the registry and routes the request (e.g., Kubernetes Service, AWS ELB).

## Key components
- Service Registry (stores instance locations, health status) — e.g., Consul, etcd, Zookeeper, Eureka.
- Health checks / heartbeats to keep registry accurate.
- DNS-based discovery as a simpler alternative (e.g., Kubernetes DNS).

## When to use
- Any dynamic microservices environment with autoscaling/elastic instances.

## Related
- API Gateway Pattern (often integrates with service discovery), Load Balancing.
