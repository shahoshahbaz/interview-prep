# Design a Notification Service

> Status: Placeholder — needs full write-up.

## 1. Requirements

### Functional
- Send notifications via multiple channels: push (iOS/Android/Web), email, SMS.
- Support templated messages and localization.
- Support immediate and scheduled notifications.
- Support user preferences (opt-in/opt-out per channel/topic).
- Idempotent delivery (avoid duplicate sends).

### Non-Functional
- High availability and at-least-once delivery.
- Low latency for time-sensitive notifications (e.g., OTP).
- Scalable to millions of notifications/day.
- Retry with backoff + dead-letter handling for failed sends.

## 2. Capacity Estimation
- TBD: estimate notifications/sec, peak fan-out for broadcast notifications, storage for notification history/audit log.

## 3. High-Level Design
- Producers (services) → Notification API → Queue (Kafka/SQS) → Channel-specific workers (Push/Email/SMS) → Third-party providers (APNs/FCM, SES/SendGrid, Twilio).
- Preference Service to check user opt-in before sending.
- Template Service for rendering content.
- Delivery status tracking + retry/DLQ.

## 4. Deep Dives
- Fan-out strategy for broadcast notifications.
- Rate limiting per user/provider.
- Exactly-once vs at-least-once delivery semantics, dedup keys.
- Priority queues for urgent notifications (OTP) vs bulk marketing.

## 5. Trade-offs
- Push vs pull delivery models.
- Centralized vs per-channel worker scaling.
