# Design Ticketmaster (Ticket Booking System)

> Status: Placeholder — needs full write-up.

## 1. Requirements
- Browse events, view seat maps, book/reserve seats, pay for tickets.
- Prevent double-booking under high concurrency (flash sales).
- Temporary seat holds with expiration.

## 2. Capacity Estimation
- TBD: concurrent users during a popular on-sale event, seats/event, booking QPS.

## 3. High-Level Design
- Event/Venue service → Seat inventory service (strongly consistent) → Booking/Reservation service (distributed lock/hold with TTL) → Payment service → Confirmation.

## 4. Deep Dives
- Preventing double-booking: pessimistic locking vs optimistic concurrency vs distributed locks (Redis/Zookeeper).
- Seat hold expiration (TTL + background reaper).
- Handling flash-sale traffic spikes (queueing/waiting room pattern).
- Idempotent booking/payment flow.

## 5. Trade-offs
- Strong consistency for inventory vs throughput.
- Virtual waiting queue vs direct access during high demand.
