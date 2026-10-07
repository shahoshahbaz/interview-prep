package com.shaho.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * Exactly-once building blocks, producer side.
 *  - enable.idempotence=true : broker dedups retries per (producerId, partition, sequence)
 *  - acks=all                : required for idempotence
 *  - transactional.id        : enables atomic multi-partition writes (+ consumer read_committed)
 * Needs a broker: docker compose up -d (see docker-compose.yml).
 */
public class IdempotentProducerExample {
    public static void main(String[] args) {
        Properties p = new Properties();
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        p.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        p.put(ProducerConfig.ACKS_CONFIG, "all");
        p.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "demo-tx-1");

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(p)) {
            producer.initTransactions();
            producer.beginTransaction();
            try {
                producer.send(new ProducerRecord<>("demo-topic", "order-1", "created"));
                producer.send(new ProducerRecord<>("demo-topic", "order-1", "paid"));
                producer.commitTransaction();
            } catch (RuntimeException e) {
                producer.abortTransaction();
                throw e;
            }
        }
    }
}
