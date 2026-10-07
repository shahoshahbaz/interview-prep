# Java Map Interface

In Java, the `Map` interface represents a collection of key-value pairs, allowing for efficient retrieval of values based on their corresponding keys. This document summarizes the common methods available in the `Map` interface.

## Common Methods in the Map Interface

### 1. `put(K key, V value)`
Inserts or updates the key-value pair.

### 2. `get(Object key)`
Retrieves the value associated with the specified key.

### 3. `remove(Object key)`
Removes the key-value pair for the specified key.

### 4. `containsKey(Object key)`
Checks if the map contains a mapping for the specified key.

### 5. `containsValue(Object value)`
Checks if the map contains one or more keys mapped to the specified value.

### 6. `size()`
Returns the number of key-value pairs in the map.

### 7. `isEmpty()`
Checks if the map is empty.

### 8. `keySet()`
Returns a `Set` view of the keys.

### 9. `values()`
Returns a `Collection` view of the values.

### 10. `entrySet()`
Returns a `Set` view of the mappings.

### 11. `getOrDefault(K key, V defaultValue)`
Retrieves the value associated with the specified key or returns the default value if the key is not present.

### 12. `computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)`
Computes the value for the key if it's absent.

### 13. `computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)`
Computes the value for the key if it's present.

### 14. `merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction)`
Merges the specified value with the existing value for the key.

## Example Usage

