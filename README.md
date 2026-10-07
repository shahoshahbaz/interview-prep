# interview-prep

Multi-module Maven project.

| Module | Contents |
|---|---|
| `coding/` | LeetCode patterns in plain Java (`Solution` per problem, own package per pattern) |
| `spring-boot-examples/` | @Transactional rollback rules, @Primary vs @Qualifier, more to add |
| `kafka-examples/` | key->partition mapping, idempotent/transactional producer (docker-compose included) |
| `system-design/` | Markdown notes only (not a Maven module) |

## Commands (from repo root)
```
mvn clean install                                   # build + test everything
mvn -pl coding compile                              # one module
mvn -pl spring-boot-examples test                   # Spring tests
mvn -pl spring-boot-examples spring-boot:run        # start the Spring app
java -cp coding/target/classes com.shaho.coding.arrays.Solution
java -cp kafka-examples/target/classes:$(mvn -q -pl kafka-examples dependency:build-classpath -Dmdep.outputFile=/dev/stdout) com.shaho.kafka.PartitionDemo
```

## Adding a new coding problem
Create `coding/src/main/java/com/shaho/coding/<pattern>/Solution.java` (package-private `Solution`
with `main`, using the test-case template). One package per pattern avoids class-name clashes.

## Requirements
JDK 21+ (change `java.version` in the parent pom if needed), Maven 3.9+, Docker for Kafka examples.
