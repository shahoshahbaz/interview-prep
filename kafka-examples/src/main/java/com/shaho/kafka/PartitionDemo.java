package com.shaho.kafka;

import java.nio.charset.StandardCharsets;
import org.apache.kafka.common.utils.Utils;

/**
 * Offline demo (no broker needed): how the default partitioner maps a key to a partition.
 * partition = toPositive(murmur2(keyBytes)) % numPartitions
 * Same key -> same partition -> per-key ordering.
 */
public class PartitionDemo {
    static int partitionFor(String key, int numPartitions) {
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        return Utils.toPositive(Utils.murmur2(bytes)) % numPartitions;
    }

    public static void main(String[] args) {
        int partitions = 6;
        for (String key : new String[]{"order-1", "order-2", "order-1", "customer-42", "order-2"}) {
            System.out.println("key=" + key + " -> partition " + partitionFor(key, partitions));
        }
    }
}
