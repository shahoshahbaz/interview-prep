### 1.Short-Circuit Evaluation
Used with || and && to prevent unnecessary execution or exceptions.

🔹 **2. Early Return Pattern**
Handle edge cases at the top and exit early instead of deeply nested if-else blocks.

```java
public void process(String input) {
    if (input == null || input.isEmpty()) return; // Early exit
    // Continue with main logic
}
```
✅ Cleaner and flatter code, avoids “arrow code” (deep nesting).

**🔹 3. Null Safety Pattern**
Avoid NullPointerException using checks, Optional, or safe defaults.

```java
if (obj != null && obj.getValue() > 10) { ... }
```
Or Java 8+ style:

```java
Optional.ofNullable(obj)
    .map(MyClass::getValue)
    .filter(val -> val > 10)
    .ifPresent(val -> doSomething(val));
```

**🔹 4. Guard Clause Pattern**
Defensive programming. Validate inputs early and exit if they are invalid.

```java
public void transferMoney(Account from, Account to, double amount) {
    if (from == null || to == null) throw new IllegalArgumentException("Accounts can't be null");
    if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
    // Actual transfer logic
}
```

**🔹 5. Fail Fast Pattern**
Detect problems immediately when they occur instead of waiting for the system to break later.

Useful in:

- Input validation
- Constructor checks
- API boundary validation

**🔹 6. Lazy Initialization**
Delay object creation until it’s actually needed.

```java
if (instance == null) {
    instance = new SomeHeavyObject();
}
```

**🔹 7. Recursive Pattern (DFS/Backtracking)**
For tree or mapGraph traversal:

```java
public void dfs(TreeNode node) {
    if (node == null) return;
    dfs(node.left);
    dfs(node.right);
}
```
You can create examples under:
com.shaho.codeconcepts.recursion

**🔹 8. Loop Invariant Pattern**
Keep some condition true before and after each iteration — often used in sorting, searching, sliding window.

Example: Maintaining a max sum in a sliding window:

```java
for (int end = 0; end < arr.length; end++) {
    windowSum += arr[end];
    if (end >= k - 1) {
        maxSum = Math.max(maxSum, windowSum);
        windowSum -= arr[start++];
    }
}
```

🔹 **9. Try-With-Resources Pattern**
Clean and safe resource management — introduced in Java 7.

```java
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    // use br
} // br is automatically closed
```

🔹 **10. Immutable Object Pattern**
Create objects that cannot be changed once constructed. Improves thread safety and predictability.

```java
public final class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // no setters, only getters
}
```

---

🔹**11. Pattern Matching with instanceof (Java 14+)**
Cleaner type checks.

```java
if (obj instanceof String s) {
    System.out.println(s.toLowerCase());
}
```

---

🔹 **12. Snapshotting a Mutable State (Defensive Copy Pattern)**
When you're working with mutable objects like List, Map, or Set, it's crucial to copy them before saving or passing if they’re going to be modified later. This pattern is called:

- Snapshotting a mutable state
- Making a defensive copy

It’s especially useful in recursive algorithms, backtracking, path finding, and API design.

**🧠 Why Use It?**
- Prevents shared reference bugs caused by future mutations
- Ensures that the state captured now stays frozen
- Crucial in recursion and backtracking where the same list is reused

**✅ Use Cases**
- Backtracking problems (e.g., all root-to-leaf paths)
- Returning internal data from an API safely
- Recursive DFS traversal when collecting paths
- Avoiding mutation of shared data across calls

**💥 Problem (What can go wrong?)**

```java
List<List<Integer>> result = new ArrayList<>();
List<Integer> currentPath = new ArrayList<>();

currentPath.add(1);
result.add(currentPath); // Adds reference

currentPath.add(2);
result.add(currentPath); // Adds same reference again

System.out.println(result); // Both entries become [1, 2]
```

**✅ Fix: Defensive Copy**

```java
List<List<Integer>> result = new ArrayList<>();
List<Integer> currentPath = new ArrayList<>();

currentPath.add(1);
result.add(new ArrayList<>(currentPath)); // 👈 Defensive copy

currentPath.add(2);
result.add(new ArrayList<>(currentPath)); // 👈 Copy again

System.out.println(result); // [ [1], [1, 2] ]
```
