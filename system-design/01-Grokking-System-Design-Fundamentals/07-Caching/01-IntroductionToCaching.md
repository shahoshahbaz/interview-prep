# Introduction to Caching

Caching is a technique used to store data temporarily in a high-speed storage layer, enabling faster access and reducing latency. It acts as an intermediary between the application and the original data source, such as databases, file systems, or remote services.

## Key Benefits
- **Performance Improvement**: Reduces the time required to fetch data.
- **Reduced Latency**: Speeds up data retrieval.
- **Efficient Resource Utilization**: Minimizes load on the original data source.

## Types of Caching
1. **In-Memory Caching**: Stores data in RAM for quick access.
2. **Disk Caching**: Saves data on hard disks, slower than RAM but faster than remote sources.
3. **Database Caching**: Keeps frequently accessed data within the database.
4. **CDN Caching**: Distributes cached data across servers to reduce latency.

## Key Concepts
- **Cache**: Temporary storage for fast data retrieval.
- **Cache Hit**: Data found in the cache.
- **Cache Miss**: Data not found in the cache, requiring retrieval from the original source.
- **Cache Eviction**: Removal of old data to make space for new entries.
- **Cache Staleness**: Outdated data in the cache compared to the original source.
