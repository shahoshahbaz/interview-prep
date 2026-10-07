package com.examples.collections.map;

import java.util.HashMap;
import java.util.Map;

public class MapExample {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();

        // 1. put
        map.put("Apple", 1);
        map.put("Banana", 2);
        map.put("Cherry", 3);

        // 2. get
        System.out.println("Value for key 'Apple': " + map.get("Apple")); // Output: 1

        // 3. remove
        map.remove("Banana");
        System.out.println("After removing 'Banana': " + map); // Output: {Apple=1, Cherry=3}

        // 4. containsKey
        System.out.println("Contains key 'Cherry': " + map.containsKey("Cherry")); // Output: true

        // 5. containsValue
        System.out.println("Contains value 2: " + map.containsValue(2)); // Output: false

        // 6. size
        System.out.println("Size of map: " + map.size()); // Output: 2

        // 7. isEmpty
        System.out.println("Is map empty? " + map.isEmpty()); // Output: false

        // 8. keySet
        System.out.println("Keys in the map: " + map.keySet()); // Output: [Apple, Cherry]

        // 9. values
        System.out.println("Values in the map: " + map.values()); // Output: [1, 3]

        // 10. entrySet
        System.out.println("Entries in the map: " + map.entrySet()); // Output: [Apple=1, Cherry=3]

        // 11. getOrDefault
        int cherryCount = map.getOrDefault("Cherry", 0);
        int dateCount = map.getOrDefault("Date", 0);
        System.out.println("Count of Cherries: " + cherryCount); // Output: 3
        System.out.println("Count of Dates: " + dateCount); // Output: 0

        // 12. computeIfAbsent
        map.computeIfAbsent("Date", k -> 4);

        System.out.println("After computeIfAbsent: " + map); // Output: {Apple=1, Cherry=3, Date=4}

        System.out.println("map.computeIfAbsent(\"Apple\", k ->5): " +map.computeIfAbsent("Apple", k ->5));
        // 13. computeIfPresent
        map.computeIfPresent("Apple", (k, v) -> v + 1);
        System.out.println("After computeIfPresent: " + map); // Output: {Apple=2, Cherry=3, Date=4}

        // 14. merge
        map.merge("Cherry", 1, Integer::sum);
        System.out.println("After merge: " + map); // Output: {Apple=2, Cherry=4, Date=4}

        // Merging with a non-existing key
        map.merge("Elderberry", 5, Integer::sum);
        System.out.println("After merge with non-existing key: " + map); // Output: {Apple=2, Cherry=4, Date=4, Elderberry=5}
    }
}

