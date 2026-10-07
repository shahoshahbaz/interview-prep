# Design Payment Processing System

## Table of Contents
1. [Problem Statement](#problem-statement)
2. [Clarifications](#first-thing-to-clarify)
3. [Requirements & Goals](#requirements--goals)
   - [Functional Requirements](#functional-requirements)
   - [Non-Functional Requirements](#non-functional-requirements)
4. [Capacity Estimation / BOE](#capacity-estimation--boe)
   - [Traffic Estimation](#traffic-estimation)
   - [Storage Estimation](#storage-estimation)
5. [High-Level Design](#high-level-design)
   - [Basic Workflow](#basic-workflow)
6. [System APIs / Interface](#system-apis--interface)
7. [Database Schema / Data Model](#database-schema--data-model)
8. [Component Design - Payment Flow](#component-design-payment-flow)
9. [Retry and Failure Handling + State Machine](#retry-and-failure-handling--state-machine)
   - [State Machine](#state-machine)
   - [Main Failure Cases](#main-failure-cases)
   - [Provider Timeout Rule](#provider-timeout-rule)
   - [Retry Strategy](#retry-strategy)
10. [Idempotency Deep Dive](#idempotency-deep-dive-tie-with-failures)
11. [Reconciliation](#reconciliation-fixing-pending--unknown)
    - [Important Rules](#important-rules)
12. [Consistency & Correctness Guarantees](#consistency--correctness-guarantees)
    - [Strong vs Eventual Consistency](#-strong-vs-eventual-super-simple)
13. [Exactly-Once vs At-Least-Once](#exactly-once-vs-at-least-once)
14. [Queue / Async Processing Design](#queue--async-processing-design)
15. [Sharding/Scaling DB](#shardingscalling-db)
16. [Security (PCI Compliance, Tokenization, Encryption)](#securitypci-compliance-tokenization-encryption)
17. [Interview Summary Cheat Sheet](#wrap-up-cheat-sheetsinterview-summary)

---

## Problem Statement
Your interview framing

You can say:

> “We need to design a reliable and scalable payment processing system that can :
>
> 1. accept payment requests,
> 
> 2. prevent duplicate charges,
> 
> 3. communicate with external payment providers, 
> 4. track transaction state, 
> 5. and make sure every money movement is auditable and recoverable.”

## First Thing to Clarify

Ask the interviewer:

>“Are we designing this for card payments, bank transfers, wallet payments, or a generic payment platform?”

 **good default assumption:**

>“I’ll assume card/payment transaction processing with authorization, capture, idempotency, ledger recording, and reconciliation.”


## Requirements & Goals
## Functional Requirements

You can say:

> “Functionally, the system should allow 
> 1. a client or merchant to submit a payment request, 
> 2. validate it, authorize the payment with an external processor or bank network, 
> 3. capture the funds, store the transaction state, and return the final or pending result. 
> 4. It should also support idempotency to prevent duplicate charges, refunds, transaction status lookup, webhooks or notifications, and reconciliation with external settlement reports.”

## Non-Functional Requirements

>“For non-functional requirements, 
> 1. the system must be highly reliable because money movement cannot be lost or duplicated. 
> 2. It should provide low-latency authorization, 
> 3. strong consistency around transaction state and ledger updates, 
> 4. high availability, secure handling of sensitive payment data, auditability, and 
> 5. fault tolerance when external providers are slow or unavailable.”

### Main goals to emphasize
| Goal                        | Why it matters                      |
| --------------------------- | ----------------------------------- |
| **No double charge**        | retries must be safe                |
| **No lost payment**         | every request must be traceable     |
| **Clear transaction state** | pending/succeeded/failed/refunded   |
| **Auditability**            | required for finance/reconciliation |
| **Security**                | sensitive payment/card/user data    |


### Your interview paragraph

> “The key goals are correctness, reliability, and auditability. In payment systems, latency matters, but correctness matters more. We need to make sure we never double-charge the customer, never lose a transaction, and can always reconstruct what happened through durable transaction records, ledger entries, and reconciliation.”

## Capacity Estimation / BOE
**Assumptions**

“I’ll assume this is a large fintech/payment platform with 10M daily active users. On average, each active user makes 2 payment-related actions per day, including purchases, transfers, captures, refunds, or status checks.”
- DAU = 10M
- Payment actions per user per day = 2
- Total payment requests per day = DAU × actions/user/day = 10M × 2 = 20M

### Traffic Estimation
Metric	Estimate

- Avg QPS	20M / 100,000 ≈ 200 QPS // we consider 100,000 seconds in a day to account for traffic patterns and peak times
- Peak QPS	5x average ≈ 1,000 to 1,200 QPS
### Storage Estimation

 **Assumptions**
 - each transaction record is around 2 KB.

so:
- Data/day	20M × 2 KB ≈ 40 GB/day
- Data/year	40 GB × 365 ≈ 14.6 TB/year
- 5-year storage	≈ 73 TB
### Important Interview Point

> 1. “The raw QPS is not crazy compared to social systems, but 
> 2. the correctness requirements are much stricter.
> 3. Payments are not mainly hard because of QPS; they are hard because of idempotency, consistency, retries, external failures, reconciliation, and auditability.”
     
## High-Level Design
| Component                     | Responsibility                                  |
| ----------------------------- | ----------------------------------------------- |
| **Client / Merchant**         | Sends payment request                           |
| **API Gateway**               | Auth, rate limit, routing                       |
| **Payment Service**           | Main orchestrator                               |
| **Idempotency Store**         | Prevents duplicate charges                      |
| **Transaction DB**            | Stores payment state                            |
| **Ledger Service**            | Records money movement                          |
| **External Payment Provider** | Bank/card network/processor                     |
| **Message Queue**             | Async events: notifications, reconciliation     |
| **Notification Service**      | Sends transaction alerts                        |
| **Reconciliation Service**    | Compares internal records with provider reports |
## Basic Workflow
```
        Client / Merchant
            ↓
        API Gateway
            ↓
        Payment Service
            ↓
        Idempotency Check
            ↓
        Transaction DB
            ↓
        External Payment Provider
            ↓
        Ledger Service
            ↓
        Queue
            ↓
        Notification / Reconciliation

```
 ### Important Interview Point
> 1. “The main flow is straightforward, but the devil is in the details around idempotency, retries, consistency, and reconciliation.
> 2. We need to make sure that if the external provider is slow or fails, we can retry safely without double-charging, and that we can always reconstruct the transaction history for audit and reconciliation purposes.”
> 3. “Also, we need to consider how to handle edge cases like partial failures, network issues, and eventual consistency in a way that maintains correctness and reliability.”

Key Points
Payment system design is mostly about safe state transition:
````
INITIATED → AUTHORIZED → CAPTURED → SETTLED
                 ↓
              FAILED
                 ↓
              REFUNDED
````

## System APIs / Interface
```
Payments API
├── Create Payment
├── Capture Payment
├── Refund Payment
└── Get Payment Status

```
 ### 1. Create Payment
   ``` http
   POST /api/v1/payments
   ```

**Request**
``` json
{
"merchant_id": "m123",
"customer_id": "c456",
"amount": 2500,
"currency": "CAD",
"payment_method_id": "pm_789",
"description": "Order #123"
}
```

**Headers**
``` http
Idempotency-Key: abc-123
Authorization: Bearer <token>
```
**Response**
``` json
{
"payment_id": "pay_001",
"status": "AUTHORIZED",
"amount": 2500,
"currency": "CAD"
}
```

### 2. Capture Payment
```http
   POST /api/v1/payments/{payment_id}/capture
```
Used when authorization and capture are separate.

### 3. Refund Payment
   
``` https
POST /api/v1/payments/{payment_id}/refund
```
``` json
   {
   "amount": 2500,
   "reason": "customer_request"
   }
```
### 4. Get Payment Status
   ```GET /api/v1/payments/{payment_id}```

Returns:
``` json
{
"payment_id": "pay_001",
"status": "CAPTURED",
"provider_reference": "provider_txn_999",
"created_at": "...",
"updated_at": "..."
}
```
### Interview Paragraph

>“For APIs, I would expose a create payment endpoint with an Idempotency-Key header, because clients may retry due to timeout or network failure. I’d also expose capture, refund, and payment status endpoints. Internally, every API should map to a durable transaction state transition, and each money movement should eventually create immutable ledger entries.”


## Database Schema / Data Model
1.**Payments Table**:

| Field                   | Notes                                             |
| ----------------------- |---------------------------------------------------|
| `payment_id`            | unique payment ID                                 |
| `merchant_id`           | merchant/business                                 |
| `customer_id`           | payer                                             |
| `amount`                | store in cents, not decimal                       |
| `currency`              | CAD, USD, etc.                                    |
| `status`                | INITIATED, AUTHORIZED, CAPTURED, FAILED, REFUNDED |
| `payment_method_id`     | tokenized card/bank method                        |
| `provider_reference_id` | external processor ID                             |
| `idempotency_key`       | prevents duplicate creation  (linked to idempotency keys table) |
| `created_at`            | created time                                      |
| `updated_at`            | last update                                       |

2. **Payment Events Table**

| Field        | Notes                                           |
| ------------ | ----------------------------------------------- |
| `event_id`   | unique event                                    |
| `payment_id` | linked payment                                  |
| `event_type` | CREATED, AUTHORIZED, CAPTURED, FAILED, REFUNDED |
| `old_status` | previous state                                  |
| `new_status` | new state                                       |
| `metadata`   | provider response / error info                  |
| `created_at` | event time                                      |

3. **Ledger Entries Table**

|Field             | Notes                                    |
| ----------------- | ---------------------------------------- |
| `ledger_entry_id` | unique entry                             |
| `transaction_id`  | payment/refund transaction               |
| `account_id`      | customer, merchant, platform-fee account |
| `direction`       | DEBIT or CREDIT                          |
| `amount`          | cents                                    |
| `currency`        | CAD                                      |
| `created_at`      | immutable timestamp                      |
4. **Idempotency Keys Table**

| Field             | Notes                                |
| ----------------- | ------------------------------------ |
| `idempotency_key` | client-provided key                  |
| `merchant_id`     | scope key per merchant               |
| `request_hash`    | detects same key with different body |
| `payment_id`      | created payment                      |
| `response_body`   | return same response on retry        |
| `status`          | IN_PROGRESS, COMPLETED, FAILED       |
| `expires_at`      | TTL cleanup                          |

how each table is used:
- Payments Table: main source of truth for payment state and details.
- Payment Events Table: immutable log of all state transitions and provider responses for audit and debugging.
- Ledger Entries Table: records all money movements for reconciliation and reporting.
- Idempotency Keys Table: ensures safe retries by tracking keys, request hashes, and associated payments.

### an example of how these tables work together:
**Scenario**

- Customer pays $100 CAD to merchant. Amount stored as 10000 cents.

**Step 1: Create Payment:** 
- an API request is made to create a payment with idempotency key "key_abc".
- here a record is created in idempotency keys table with status of IN_PROGRESS, and an expires_at time for cleanup after 24 hours.

```  SQL
  | idempotency_key | merchant_id | request_hash | payment_id | response_body | status      | expires_at |
  | --------------- | ----------- | ------------ | ---------- | ------------- | ----------- | ---------- |
  | key_abc         | m1          | hash_req_1   | pay_1      | null          | IN_PROGRESS | 24h later  |
```

- a record is created in payments table with status of INITIATED, and an idempotency key is 
```  SQL


| payment_id | merchant_id | customer_id | amount | currency | status    | payment_method_id | provider_reference_id | idempotency_key | created_at | updated_at |
| ---------- | ----------- | ----------- | -----: | -------- | --------- | ----------------- | --------------------- | --------------- | ---------- | ---------- |
| pay_1      | m1          | c1          |  10000 | CAD      | INITIATED | pm_123            | null                  | key_abc         | 10:00      | 10:00      |

```

- a record is created in payment events table for the CREATED event.

``` SQL
| event_id | payment_id | event_type | old_status | new_status | metadata | created_at |
| -------- | ---------- | ---------- | ---------- | ---------- | -------- | ---------- |
| evt_1    | pay_1      | CREATED    | null       | INITIATED | null     | 10:00      |
```
- at this stage ledeger entries are not created yet, because money movement has not happened. We will create ledger entries after authorization or capture.

**Step 2: Authorize Payment:**
Bank Says: Approved/Hold placed
- the Payment table is updated to AUTHORIZED, and provider_reference_id is set to the external transaction ID from the bank.
``` sql
| payment_id | merchant_id | customer_id | amount | currency | status     | payment_method_id | provider_reference_id | idempotency_key | created_at | updated_at |
| ---------- | ----------- | ----------- | -----: | -------- | ---------- | ----------------- | --------------------- | --------------- | ---------- | ---------- |
| pay_1      | m1          | c1          |  10000 | CAD      | AUTHORIZED | pm_123            | provider_txn_999    | key_abc         | 10:00      | 10:01      |
```

- a record is created in payment events table for the AUTHORIZED event.

``` SQL
| event_id | payment_id | event_type  | old_status | new_status  | metadata                     | created_at |
| -------- | ---------- | ----------- | ---------- | ----------- | ---------------------------- | ---------- |
| evt_1    | pay_1      | cREATED     | null       | INITIATED   | null                         | 10:00      |
| evt_2    | pay_1      | AUTHORIZED  | INITIATED | AUTHORIZED  | {"bank_response": "approved"} | 10:01      |

```

- also idempotency key status is updated to COMPLETED, and response body is stored for future retries.

``` SQL
| idempotency_key | merchant_id | request_hash | payment_id | response_body                               | status      | expires_at |
| --------------- | ----------- | ------------ | ---------- | ------------------------------------------- | ----------- | ---------- |
| key_abc         | m1          | hash_req_1   | pay_1      | {"payment_id": "pay_1", "status": "AUTHORIZED", "amount": 10000, "currency": "CAD"} | COMPLETED   |
```
- no legeder entries are created yet, because money has not been captured. We will create ledger entries after capture.

**Step 3: Capture Payment:**
Now money movement is finalized.

Payment table is updated to CAPTURED.

``` SQL
| payment_id | merchant_id | customer_id | amount | currency | status     | payment_method_id | provider_reference_id | idempotency_key | created_at | updated_at |
| ---------- | ----------- | ----------- | -----: | -------- | ---------- | ----------------- | --------------------- || --------------- | ---------- | ---------- |
| pay_1      | m1          | c1          |  10000 | CAD      | CAPTURED   | pm_123            | provider_txn_999    | key_abc         | 10:00      | 10:05      |
``` 
- a record is created in payment events table for the CAPTURED event.

``` SQL
| event_id | payment_id | event_type  | old_status | new_status  | metadata                      | created_at |
| -------- | ---------- | ----------- | ---------- | ----------- | ----------------------------  | ---------- |
| evt_1    | pay_1      | CREATED     | null       | INITIATED   | null                          | 10:00      |
| evt_2    | pay_1      | AUTHORIZED  | INITIATED  | AUTHORIZED  | {"bank_response": "approved"} | 10:01      |
| evt_3    | pay_1      | CAPTURED    | AUTHORIZED | CAPTURED    | {"bank_response": "captured"} | 10:05      |
```
- ledger entries are created for the capture:

``` SQL
| ledger_entry_id | transaction_id | account_id | direction | amount | currency | created_at |
| --------------- | -------------- | ---------- | --------- | -----: | -------- | ---------- |
| le_1            | pay_1          | c1         | DEBIT     | 10000 | CAD      | 10:05      |
| le_2            | pay_1          | m1         | CREDIT    | 9700  | CAD      |
| le_3            | pay_1          | platform_fee_account | CREDIT    | 300   | CAD      | 10:05      |
```
- the customer account is debited 10000 cents, the merchant account is credited 9700 cents, and the platform fee account is credited 300 cents.

Key Points: 
- The Payments table is the main source of truth for the current state of each payment.
Payment table is updated. Payment_event and ledger entries are appended-only.

#### what to say in interview:
> 1. “The Payments table is the main source of truth for the current state of each payment. It gets updated with each state transition.
> 2. The Payment Events table is an immutable log of all state transitions and provider responses, which is crucial for auditability and debugging.
> 3. The Ledger Entries table records all money movements in an immutable way, which is essential for reconciliation and financial reporting.
> 4. The Idempotency Keys table ensures that if a client retries a request with the same idempotency
> key, we can return the same response without creating duplicate payments, and we can also detect if the same key is used with different request data, which would indicate a client error.”

## Component Design - Payment Flow:

**Main Flow:**

```
        Client  
        ↓  
        API Gateway  
        ↓  
        Payment Service  
        ↓  
        Idempotency Store  
        ↓  
        Transaction DB  
        ↓  
        External Payment Provider  
        ↓  
        Transaction DB update  
        ↓  
        Ledger Service  
        ↓  
        Queue  
        ↓  
        Notification / Reconciliation
```

Step 1: client send a payment request to API Gateway.
```
post /api/v1/payments
Headers:
Idempotency-Key: key_abc
```
request body:
```
{
"merchant_id": "m1",
"customer_id": "c1",
"amount": 2500,
"currency": "CAD",
"payment_method_id": "pm_123",
"description": "Order #123"
}
```
what happend here: 
"Please charge custormer c1 100 CAD for merchant m1"

Note: 
- the idempotency key is generated by the client and is used to prevent duplicate charges if the client retries due to network issues or timeouts. it usually has a TTL of 24 hours, after which it can be cleaned up.
- usually the idempotency key is generated per unique payment intent, so if the client retries with the same key, we know it's the same payment request and can return the same response without creating a new payment.


Ste2: API Gate way:

the api gateway is responsible for:
- authenticating the request (e.g. checking the Authorization header)
- rate limiting (e.g. to prevent abuse or overload)
- routing the request to the appropriate service (in this case, the Payment Service)
- validating the request format and required fields
- logging the request for monitoring and debugging
- returning appropriate error responses for invalid requests (e.g. 400 Bad Request, 401 Unauthorized, 429 Too Many Requests)
- passing the idempotency key and request body to the Payment Service for further processing.
- the API Gateway should be stateless and scalable, and can be implemented using a load balancer and multiple instances of the gateway service.

Step 3: Payment Service
- the Payment Service is the main orchestrator of the payment flow. It is responsible for:
- receiving the payment request from the API Gateway
- checking the Idempotency Store to see if the idempotency key has already been used or not

Step 4: Idempotency Store
- the Idempotency Store is a key-value store that tracks idempotency keys, their associated payment IDs, request hashes, response bodies, and statuses. It is used to ensure that if
- the store can be Implement using a fast in-memory database like Redis, with a TTL for automatic cleanup of old keys.
- when a payment request comes in, the Payment Service first checks the Idempotency Store: based on merchant_id and idempotency_key, the store returns one of the following:
    - Possible scenarios:
  
      | Case                           | Meaning                       | Action                        |
      | ------------------------------ | ----------------------------- | ----------------------------- |
      | Not found                      | First request                 | create idempotency record     |
      | Found + completed              | Retry                         | return saved response         |
      | Found + in progress            | Same payment still processing | return pending / wait briefly |
      | Found + different request body | Bad reuse of key              | return `409 Conflict`         |

- for first request, a new record is created in the Idempotency store with Status = IN_PROGRESS, and the payment processing continues.
- Once the payment is processed, the record is updated with the payment_id, response_body, and status = COMPLETED.

Step 5: Transaction DB
- create payment row:
``` SQL
payment_id = pay_1
status = INITIATED
amount = 10000
currency = CAD 
```

-also append a payment event:
``` SQL
event_id = evt_1
payment_id = pay_1
event_type = CREATED
old_status = null
new_status = INITIATED
metadata = null
``` 
- now the system has durable records of the payment state before calling the external provider, which is crucial for auditability and recovery in case of failures.
Step 6: External Payment Provider
- the Payment Service calls the external payment provider (e.g. a card network or bank API) to authorize the payment. 
provider can return
- | Response | Meaning               |
  | -------- | --------------------- |
  | Approved | payment can continue  |
  | Declined | payment failed        |
  | Timeout  | unknown result        |
  | Error    | retry or mark pending |

Step 7: Transaction DB update
- based on the provider response, the Payment Service updates the payment status in the Transaction DB 
- appends a new payment event with the provider response metadata.
- if provider response is approved, the status is updated to AUTHORIZED, and we can proceed to capture (which may be a separate step or combined with authorization depending on the business model).
- if provider response is declined, the status is updated to FAILED, and the flow ends there.
- if provider response is timeout or error, the status can be updated to PENDING, and the system can implement retry logic with exponential backoff, while ensuring that retries are safe and do not cause duplicate charges.
- the idempotency key record is also updated to COMPLETED with the response body, so that if the client retries with the same key, we can return the same result without reprocessing the payment.
Step 8: Ledger Service
- if the payment is authorized or captured, the Ledger Service is called to create immutable ledger entries
- | account_id       | direction | amount |
  | ---------------- | --------- | -----: |
  | customer_account | DEBIT     |  10000 |
  | merchant_account | CREDIT    |  10000 |
- the ledger entries are crucial for financial reporting, reconciliation, and auditability, as they provide a clear record of all money movements associated with the payment.

Setp 9: Queue
- after the payment is processed and ledger entries are created, an event can be published to a message queue (e.g. Kafka, RabbitMQ) for asynchronous processing by other services, such as
- Notification Service: to send transaction alerts to customers or merchants via email, SMS, or push notifications.
- Reconciliation Service: to compare internal transaction records and ledger entries with external settlement reports from payment providers, to ensure that all transactions are accounted for and to identify any discrepancies.

Step 10: Notification / Reconciliation
- the Notification Service can consume events from the queue to send real-time alerts to customers or merchants
- the Reconciliation Service can consume events to perform daily or periodic reconciliation of transactions, which is essential for financial integrity and to detect any issues with payment processing or settlement.
- both services should be designed to be idempotent and resilient to failures, as they may process the same event multiple times or encounter transient issues with downstream systems.
## Retry and Failure Handling + state Machine
 ### state machine:
 - State machine for payment status can be represented as:
   - **INITIATED:** when payment is created locally but not yet authorized.
   - **AUTHORIZED:** when payment is approved by the external provider but not yet captured.
   - **CAPTURED:** when payment is finalized and money movement is initiated.
   - **SETTLED:** when payment is fully settled with the provider and funds are transferred.
   - **FAILED:** when payment authorization or capture fails.
   - **REFUNDED:** when a captured payment is refunded back to the customer.
   - **UNKNOWN/PENDING:** when the outcome of the payment is not yet known due to timeouts or errors, and the system is waiting for a retry or manual intervention.

Below is where each state transition can fail
```
        Client / Merchant
        ↓
        API Gateway
        ↓
        Payment Service
        ↓
        Idempotency Check
        ↓
        Transaction DB
        - FAILED: validation failed / invalid state transition
        ↓
        External Payment Provider
        - **FAILED**: auth/capture/refund declined
        - PENDING / UNKNOWN: provider timeout or unclear response
        - REFUNDED: refund request accepted/succeeded
        ↓
        Ledger Service
        - FAILED: ledger write fails before commit
        - REFUNDED: reverse ledger entries created
        ↓
        Queue
        - PENDING / UNKNOWN: event publish delayed/retry needed
        ↓
        Notification / Reconciliation
        - SETTLED: provider settlement report confirms final settlement
        - FAILED: reconciliation confirms provider failed payment
        - PENDING / UNKNOWN: reconciliation cannot confirm yet
        - REFUNDED: reconciliation confirms refund
```
### Main Failure Cases:

| Failure                       | What we do                               |
| ----------------------------- | ---------------------------------------- |
| Client retries                | Same idempotency key returns same result |
| Provider declined             | Mark `FAILED`                            |
| Provider timeout              | Mark `PENDING/UNKNOWN`                   |
| DB fails before provider call | Safe to retry                            |
| DB fails after provider call  | Dangerous → reconciliation needed        |
| Queue fails                   | Retry event publishing                   |


### Provider Timeout Rule

**Most important line:**

>“If the provider times out, I never assume failure. I mark the payment as PENDING and resolve it later using provider status lookup or reconciliation.”

>Why?

>BecaUse provider may have already charged the customer.

### Retry Strategy
| Retry Type           | Strategy                                            |
| -------------------- | --------------------------------------------------- |
| Client retry         | Idempotency store                                   |
| Provider retry       | Exponential backoff                                 |
| Queue retry          | At-least-once delivery + idempotent consumers       |
| Reconciliation retry | Scheduled job compares provider vs internal records |


### Interview Paragraph

>“For failure handling, I model payments as a state machine. A payment starts as INITIATED, then moves to AUTHORIZED, CAPTURED, and eventually SETTLED. If the external provider times out, I do not mark it as failed because the charge may have succeeded externally. Instead, I mark it as PENDING or UNKNOWN and resolve it later through provider status polling or reconciliation. Client retries are handled with idempotency keys, and async events are retried safely with idempotent consumers.”

**Killer sentence**

>“In payment systems, timeout does not mean failure; it means unknown.”

### Idempotency deep dive (tie with failures)
- **Goal**
  >Same payment request retried many times should create only one payment / one charge.

- **Where it happens**:
    ```
        Client
          ↓
        API Gateway
          ↓
        Payment Service
          ↓
        Idempotency Store    <-- **check here before provider call**
          ↓
        Transaction DB
          ↓
        External Provider
    ```
- **Key used for lookup**
  **``` merchant_id + idempotency_key```**

- **Why include merchant_id?** ```Two merchants could accidentally generate the same UUID. Scope it per merchant.```
- **different scenarios for idempotency key lookup and their meaning:**
 

| Idempotency Store Result(status column) | response_body column                                                         | Meaning                              | Action                          |
|-----------------------------------------|------------------------------------------------------------------------------|--------------------------------------| ------------------------------- |
| **IN_PROGRESS**                         | null                                                                         | first request                        | create record, continue processing |
| **COMPLETED**                           | <code> {<br>&nbsp;&nbsp; payment_id, **status: CAPTURED/AUTHORIZED**, amount, currency } </code> | store full final response (success ) | return saved response             |
| **COMPLETED**                           | <code> { payment_id, **status: FAILED**, error } </code>                         | store full final response (failed)   | return saved response             
| **PENDING / UNKNOWN**                   | <code>{ payment_id, **status: PENDING** }  </code>                              |  provider timeout, still processing     | return pending / wait briefly       |
|**EXPIRED**                             | null                                                                         | record expired (e.g. after 24h TTL) | return null or key not found       |

## Reconciliation (fixing PENDING / UNKNOWN)
- **Goal**
    >Resolve payments where our system is not sure what happened (usually after provider timeout).

- **When do we need it?**
    - Typical trigger:
        >- Call provider → timeout
       >- status = PENDING / UNKNOWN

  - 👉 We **cannot** trust our system alone
  - 👉 We must **ask the provider later**

- **How it works (high-level)**
    ```
        Our DB (payments)        External Provider
            ↓                         ↓
        Reconciliation Service (scheduled / event-driven)
            ↓
        Compare + Fix mismatches
    ```
    - **Step-by-step flow**:
       1. **Pick unresolved payments**
       2. **Call provider for status.** Provider responds:
      
          | Provider status | Meaning           |
          | --------------- | ----------------- |
          | SUCCESS         | money was charged |
          | FAILED          | payment failed    |
          | NOT_FOUND       | never processed   |
          | PENDING         | still processing  |

      3. **Compare & decide**:
      
         | Our status | Provider  | Action                       |
         | ---------- | --------- | ---------------------------- |
         | PENDING    | SUCCESS   | mark CAPTURED + write ledger |
         | PENDING    | FAILED    | mark FAILED                  |
         | PENDING    | NOT_FOUND | safe to retry                |
         | PENDING    | PENDING   | retry later                  |

      4. Update system: 
      - append payment event with provider response metadata
      - if provider says SUCCESS, update payment status to CAPTURED and write ledger entries if not already done.
      - if provider says FAILED, update payment status to FAILED.
      - if provider says NOT_FOUND, we can safely retry later without risking duplicate charges, since the provider has no record of the transaction.
**Question**: does system update the idempotency key status during reconciliation?
      - No, because reconciliation is not triggered by a client request, but rather by a scheduled job or event. The idempotency key is only relevant for client-initiated requests to prevent duplicate processing. Reconciliation is an internal process to resolve unknown states and does not involve client retries, so it does not interact with the idempotency keys.


### Important rules
- **Rule 1**

> Never create ledger entries twice

👉 Use: there shuoud be unique constraint on ledger entries to prevent duplicates, such as a composite unique(transaction_id + account_id)

- **Rule 2:**

>Reconciliation must be idempotent

You might run it multiple times. Make sure it does not cause duplicate charges or ledger entries.

- **Rule 3**

> Reconciliation is eventually consistent. It may take: seconds, minutes, or even hours (bank settlement)

### **Example (very important)**

- Step 1: capture request sent
- Step 2: provider charged customer
- Step 3: network timeout
- Step 4: system marks PENDING
- Step 5: reconciliation runs
- Step 6: provider says SUCCESS
- Step 7: system updates to CAPTURED

### Interview paragraph

> “For payments in PENDING or UNKNOWN state, I introduce a reconciliation service that periodically queries the payment provider or consumes settlement reports to determine the final outcome. If the provider confirms success, we update the payment state and write ledger entries if needed. If it failed, we mark it as FAILED. This ensures eventual consistency without risking duplicate charges.”

🔥 Killer line

“Reconciliation is the safety net that guarantees correctness when synchronous processing fails.”

Where candidates fail

They say:

“I’ll just retry provider call”

❌ Wrong

You must say:

“I verify with provider instead of blindly retrying.”


## Consistency & Correctness Guarantees

- **what does it mean:**
>Don’t lose money. Don’t double charge. Always know what happened.

- 🔥 3 Simple Rules (this is all you need):
  - ✅ Rule 1: Never double charge
    - How?Idempotency key
    - Unique constraints
    - 👉 Example:``` Same request retried → return same payment```
  - ✅ Rule 2: Money must always balance
  - Example:
       ```
      customer -100
      merchant +100
      --------------
      total = 0 ✅
      ```

  - 👉 This is why we use ledger

- ✅ Rule 3: Never guess on failure
  - ```Timeout ≠ failure```
  - 👉 Always: ```status = PENDING```
  - Then fix later (reconciliation)

### 🧠 Strong vs Eventual (super simple)
- **Strong consistency = MUST be correct NOW**

  | Thing         | Why              |
  | ------------- | ---------------- |
  | Ledger        | money must match |
  | Payment state | no wrong status  |

- **Eventual consistency = can fix later**
    
    | Thing          | Why           |
    | -------------- | ------------- |
    | Notifications  | not critical  |
    | Reconciliation | happens later |


### 🎯 What interviewer wants to hear

Just say this:


>“In payment systems, correctness is more important than latency. I ensure no duplicate charges using idempotency, maintain balance using double-entry ledger, and treat timeouts as unknown instead of failure, resolving them later via reconciliation.”

## Exactly-once vs At-least-once 
- what does it mean: They describe how many times a system may process a request or event—either exactly once or at least once.

Imagine this:
```
You click “Pay”
Internet is slow
You click “Pay” again
```
Now system receives:
```
Same request twice
```
### 🎯 Two possible system behaviors
- ❌ Exactly-once (ideal but unrealistic):
  - Process request exactly one time
  - Never duplicate
  - Never retry

- Problem:

> In real systems → retries happen, network fails → you can’t guarantee this

- ✅ At-least-once (real world):
  - System may process request multiple times
  - BUT it will not miss it

   - 👉 This is what we actually build

- **💥 The Problem** 
    > At-least-once → duplicates can happen

  - Example:```Payment processed twice → double charge 💥```
    - 🧠 The Solution (this is the key!)

      - Make operations idempotent
        - 🔥 What is idempotent?Running it multiple times = same result
          - Example:
            - Bad ❌
                 ```   
                 Charge $100
                 Charge $100 again
            ```
           → double charge

           - Good ✅
              ```        
              Charge $100 (with idempotency key)
              Retry same request
              → return same result, no new charge
              ```
🧠 So what are we doing?
System: at-least-once (may repeat)
We: make it idempotent (safe repeat)
### 🎯 Final meaning (this is what you say)

> “We don’t rely on exactly-once guarantees. Instead, we use at-least-once processing and make operations idempotent so repeated requests don’t create duplicate side effects.”


## Queue / Async Processing Design
- **Goal**
    >Decouple critical path from non-critical work. Handle spikes. Ensure reliability.
  > Example: after payment is captured, we want to send a notification email. We don’t want to delay the payment response while waiting for the email service.
  > Instead, we publish an event to a queue, and the email service consumes that event to send the notification asynchronously.
  > This way, the payment processing is fast and reliable, and the email sending can be retried if it fails without affecting the payment flow.
- **Where do we use it?**
    - After payment capture to trigger notifications
    - For reconciliation results to update payment status
    - For any non-critical work that can be done asynchronously

- **What kind of queue?**
    - Kafka, RabbitMQ, AWS SQS, etc.
    - Should support at-least-once delivery
    - Should be scalable and reliable
- **How to ensure reliability?**
    - Idempotent consumers: make sure that if the same event is processed multiple times,
    - it does not cause duplicate side effects (e.g. sending multiple emails or creating multiple ledger entries).
    - Dead-letter queue: for events that fail processing after multiple retries, so they can be investigated and handled manually if needed.
    - Monitoring and alerting: to detect issues with the queue or consumers, such as processing failures or backlogs.
### Interview paragraph
>“I use a message queue to decouple the payment processing from non-critical work like notifications and reconciliation. This allows the payment flow to be fast and reliable, while still ensuring that important follow-up actions happen asynchronously. I also make sure that consumers of the queue are idempotent, so that if an event is processed multiple times, it does not cause duplicate side effects.”


## Sharding/Scaling DB

## Security (PCI Compliance, Tokenization, Encryption)

## Wrap Up Cheat Sheet/Interview Summary



