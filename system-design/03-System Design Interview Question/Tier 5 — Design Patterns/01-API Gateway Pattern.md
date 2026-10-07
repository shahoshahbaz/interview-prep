# API Gateway — Cheat Sheet
*Design without a managed service (no AWS API Gateway / Azure APIM)*

---

## What is an API Gateway?
The **single entry point** for all client traffic into your system.
Sits between clients and your upstream microservices.
Enforces rules at the edge so your services don't have to.

> "A smart gatekeeper — not just a forwarder."

---

## 1. Requirements

### Functional
1. SSL/TLS Termination — decrypt HTTPS at the edge
2. Authentication & Authorization — validate every request
3. Rate Limiting — enforce request quotas per client/IP/endpoint
4. Routing — map request path to correct upstream service
5. Observability — logs, metrics, traces for every request

### Non-Functional
| Requirement | Target |
|---|---|
| Availability | 99.99% (~4.3 min downtime/month) |
| Latency added by gateway | < 5ms p99 |
| Throughput | 58K req/sec peak |
| Rate limit accuracy | < 1–2% overage |
| Consistency | Rate limit state shared across all instances |

---

## 2. Capacity Estimation

### Assumptions
- 10M active users/day
- 100 requests/user/day
- Request size: 2 KB | Response size: 10 KB
- 10 gateway instances | Peak multiplier: 5×

### QPS
```
Daily:   10M × 100 = 1B requests/day
Average: 1B ÷ 86,400 ≈ 11,600 req/sec
Peak:    11,600 × 5  ≈ 58,000 req/sec
Per instance: 58,000 ÷ 10 ≈ 5,800 req/sec
```

### Bandwidth
```
Inbound:  58,000 × 2 KB  = 116 MB/sec peak
Outbound: 58,000 × 10 KB = 580 MB/sec peak
```

### Redis (Rate Limiting)
```
58,000 ops/sec at peak
Single Redis node handles ~100K ops/sec
→ 1 node sufficient for capacity
→ Run 3-node cluster for HA
```

### Rate Limit State Memory
```
10M clients × 100 bytes = ~1 GB in Redis
100 bytes per client = client_id + counter + timestamp + TTL + Redis overhead
```

---

## 3. High-Level Architecture

```
Clients (mobile, web, third-party)
              |
              | HTTPS
              ↓
     Load Balancer (Nginx / HAProxy / ALB)
     SSL termination here
              |
              | HTTP
              ↓
┌─────────────────────────────────────────┐
│           API Gateway Cluster           │
│   Instance 1 | Instance 2 | Instance 3  │  ← stateless, active-active
└─────────────────────────────────────────┘
       |              |              |
       ↓              ↓              ↓
     Redis          Auth         Config Store
   Cluster        Service        (etcd/Consul)
  (rate limits)  (JWT/OAuth)    (routing rules)
                                      |
                                      ↓
                            Service Discovery
                            (Consul / K8s DNS)
                                      |
                                      ↓
                           Upstream Microservices
                      (Payment | Wallet | Account | ...)
```

### Component Responsibilities
| Component | What it does |
|---|---|
| **Load Balancer** | Distributes traffic, health checks, SSL termination |
| **API Gateway Instances** | Stateless workers — run full request pipeline |
| **Redis** | Shared rate limit counters across all instances |
| **Auth Service** | Validates tokens — JWT locally or OAuth via network call |
| **Config Store** | Stores routing rules (etcd/Consul) |
| **Service Discovery** | Resolves upstream service location at request time |
| **Upstream Services** | Actual business services (Payment, Wallet, Account) |

---

## 4. Request Pipeline (in order)

```
Incoming request
        ↓
1. SSL/TLS Termination      (at LB)
        ↓
2. Auth (JWT / OAuth)       → 401 if invalid, stop here
        ↓
3. Rate Limit (Redis)       → 429 if exceeded, stop here
        ↓
4. Routing                  → resolve upstream via Service Discovery
        ↓
5. Forward to upstream      → 504 if timeout
        ↓
6. Observability            → log, metric, trace
        ↓
Return response to client
```

### Why This Order Matters
- Auth before rate limit — don't consume quota on invalid tokens
- Rate limit before routing — no point resolving upstream if request will be rejected

### Auth: JWT vs OAuth Introspection
| | JWT | OAuth Introspection |
|---|---|---|
| How | Validate locally using public key | Call Auth Service via network |
| Speed | Fast — no network call | Slower |
| Revocation | Not real-time | Real-time |
| Use when | High throughput | Need instant revocation |

---

## 5. Rate Limiting — Deep Dive

### Why Redis is Required
```
Without Redis (in-memory per instance):
Client limit = 100 req/min, 3 instances
→ Client hits 100 on Instance 1, 100 on Instance 2, 100 on Instance 3
→ 300 requests pass — limit completely bypassed ❌

With Redis (shared):
All instances check same counter → limit enforced correctly ✅
```

### The 4 Algorithms

**1. Fixed Window Counter**
- Count requests per client per fixed time bucket
- Problem: boundary burst attack (200 requests in 2 seconds across two windows)
- Use when: simple internal APIs

**2. Sliding Window Log**
- Store timestamp of every request in sorted set
- Remove old timestamps, count remaining
- Problem: memory heavy at scale
- Use when: low traffic, strict accuracy required

**3. Sliding Window Counter ✅ Best for production**
- Blend two adjacent fixed windows weighted by elapsed time
- Formula: `current = prev_window × (1 - elapsed%) + current_window`
- Low memory, ~99% accuracy
- Use when: high-scale production

**4. Token Bucket ✅ Best for burst traffic**
- Bucket refills tokens at fixed rate, max capacity
- Each request consumes 1 token, empty = reject
- Naturally handles burst
- Use when: fintech APIs with retry storms

### Algorithm Comparison
| Algorithm | Burst | Memory | Accuracy | Use When |
|---|---|---|---|---|
| Fixed Window | Bad | Low | Low | Simple internal |
| Sliding Window Log | No | High | Perfect | Low traffic strict |
| Sliding Window Counter | Good | Low | ~99% | High-scale production |
| Token Bucket | Excellent | Low | Good | Burst / fintech |

---

## 6. Redis Atomicity — Lua Script

Check + increment must be **one atomic operation** — otherwise race conditions allow limit bypass.

```lua
local key = KEYS[1]
local limit = tonumber(ARGV[1])
local window = tonumber(ARGV[2])

local current = redis.call('INCR', key)
if current == 1 then
  redis.call('EXPIRE', key, window)
end
if current > limit then
  return 0  -- rejected
else
  return 1  -- allowed
end
```

Redis key structure:
```
rate_limit:{client_id}:{endpoint}  →  integer counter
Example: rate_limit:client_abc:/v1/payments  →  47
```

---

## 7. What to Return on 429

```
HTTP/1.1 429 Too Many Requests
Retry-After: 30
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 0
X-RateLimit-Reset: 1718000060

{
  "error": "rate_limit_exceeded",
  "message": "Too many requests. Retry after 30 seconds.",
  "retry_after_seconds": 30
}
```

| Header | Purpose |
|---|---|
| `Retry-After` | Tells client when to retry — critical for payment retries |
| `X-RateLimit-Limit` | Client knows their quota |
| `X-RateLimit-Remaining` | Client can self-throttle before hitting 429 |
| `X-RateLimit-Reset` | Epoch timestamp when window resets |

---

## 8. Failure Modes

| Failure | Response |
|---|---|
| Gateway instance crashes | LB health check detects, routes around it |
| Redis goes down | **Fail closed** for payments — local fallback counter per instance |
| Auth service goes down | Return `503`, never let unvalidated traffic through |
| Upstream service slow | Gateway enforces timeout → `504` |
| Config store unreachable | Serve routing from local cache |

### Redis Failure — Local Fallback Pattern
```
Redis unreachable > 2 seconds
→ Switch to local in-memory counter
→ Local limit = global limit ÷ number of instances
→ Example: limit 100, 10 instances → each enforces 10
```

> Fail closed for payments — never fail open.

---

## 9. Observability — The 3 Pillars

### Logs (structured JSON)
```json
{
  "timestamp": "2026-06-18T10:00:00Z",
  "trace_id": "abc-123",
  "client_id": "client_xyz",
  "method": "POST",
  "path": "/v1/payments",
  "status_code": 200,
  "latency_ms": 3,
  "upstream": "payment-service",
  "rate_limit_remaining": 47
}
```

### Metrics (Prometheus → Grafana)
| Metric | Signal |
|---|---|
| Request count per endpoint | Traffic patterns |
| Error rate (4xx, 5xx) | Health |
| p50 / p95 / p99 latency | Performance |
| Rate limit hit rate | Abuse detection |
| Auth failure rate | Security |

### Traces (OpenTelemetry / Jaeger)
- Attach trace ID at gateway, propagate to all upstream services
- Follow single request across all services in one view
```
Client → Gateway (3ms) → Payment Service (120ms) → DB (5ms)
         trace_id: abc-123 propagated across all hops
```

### Alerts
| Alert | Trigger |
|---|---|
| High latency | p99 > 10ms at gateway |
| Error spike | 5xx rate > 1% |
| Rate limit storm | 429 rate spikes suddenly |
| Auth failures | 401 rate spikes — possible attack |

---

## 10. Scalability & High Availability

### Scaling Strategy
| Layer | How |
|---|---|
| Gateway instances | Horizontal — add instances behind LB, stateless |
| Redis | 3-node cluster for HA; shard by client_id if > 1M QPS |
| Auth Service | Multiple instances behind its own LB |
| Config Store | etcd/Consul replicated across 3 nodes |

### Multi-Region
```
Region: US-East                Region: EU-West
─────────────────              ─────────────────
LB → Gateway Cluster           LB → Gateway Cluster
     Redis Cluster                  Redis Cluster

          ↓                              ↓
     Global DNS (Route53 / Azure Traffic Manager)
     → routes client to nearest region
```

Rate limiting across regions: each region enforces independently — accept ~5% overage. True correctness enforced downstream via idempotency keys.

---

## Gateway vs Reverse Proxy vs Load Balancer

| Component | Job | Business logic? | Example |
|---|---|---|---|
| Load Balancer | Distribute traffic, health checks | No | HAProxy, ALB |
| Reverse Proxy | Forward requests, hide servers, SSL | No | Nginx |
| API Gateway | Auth, rate limiting, routing, observability | Yes | What you just designed |

> Reverse proxy = dumb forwarder. API Gateway = smart gatekeeper.

---

## API Versioning

- **Path-based** (most common): `/v1/payments` → old, `/v2/payments` → new
- **Header-based**: `API-Version: 2` in request header
- **Subdomain-based**: `v2.api.company.com`

Allows running old and new versions simultaneously — zero downtime migrations.

---

## AWS → Azure Equivalents

| AWS | Azure |
|---|---|
| API Gateway | API Management (APIM) |
| ALB | Application Gateway |
| EKS | AKS |
| Lambda | Azure Functions |
| CloudWatch | Azure Monitor |
| Route 53 | Azure Traffic Manager |

---

## Key Interview Phrases

- *"The gateway is stateless — that's intentional. All shared state lives in Redis and Config Store."*
- *"SSL terminates at the Load Balancer — certificates managed in one place, internal traffic is plain HTTP."*
- *"Auth before rate limit — don't consume quota on invalid tokens."*
- *"Rate limiting must use Redis — in-memory per instance lets clients bypass limits by hitting different instances."*
- *"The Lua script makes check + increment atomic — Redis executes it as one uninterruptible operation."*
- *"For payments I fail closed — if Redis is down I'd rather reject requests than allow unlimited traffic to hit payment services."*
- *"The gateway is the perfect place for observability — every request passes through one component."*
- *"For multi-region I accept approximate rate limiting at the gateway and rely on idempotency downstream for true correctness."*
- *"Token Bucket is my pick for fintech — it handles retry storms naturally by accumulating tokens during quiet periods."*