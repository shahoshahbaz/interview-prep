# Bitwise Operations Cheat Sheet (Java)

## Most Common Bitwise Operations

### 1️⃣ AND – `&`
- **Purpose:** Check if a bit is set (1), mask bits, extract bits.
- **Example:**
  ```java
  int x = 5;      // 0101
  int bit = 1;    // 0001
  boolean isSet = (x & bit) != 0;   // true
  ```

### 2️⃣ OR – `|`
- **Purpose:** Force a bit ON.
- **Example:**
  ```java
  int x = 4;      // 0100
  int y = x | 1;  // 0101
  ```

### 3️⃣ XOR – `^`
- **Purpose:** Toggle bits, remove duplicates, detect single numbers.
- **Example:**
  ```java
  int x = 5 ^ 5;   // 0
  int y = 4 ^ 6;   // 2
  ```
- **Used heavily in:**
  - "Single Number"
  - "Find two unique numbers"
  - Bit manipulation tricks

### 4️⃣ NOT (bitwise complement) – `~`
- **Purpose:** Flip every bit.
- **Example:**
  ```java
  int x = ~5;
  ```
- Not used often unless dealing with masks or two's complement.

### 5️⃣ Left shift – `<<`
- **Purpose:** Multiply by 2 each shift.
- **Example:**
  ```java
  int x = 3 << 1;  // 6
  int mask = 1 << 3; // 1000
  ```

### 6️⃣ Right shift (signed) – `>>`
- **Purpose:** Divide by 2 for positive numbers (keeps sign bit).
- **Example:**
  ```java
  int x = 8 >> 1; // 4
  ```

### 7️⃣ Unsigned right shift – `>>>`
- **Purpose:** Shift right but fill with 0 (even for negatives).
- **Example:**
  ```java
  int x = -8 >>> 1;
  ```
- Useful in low-level operations, but rare in interview problems.

### 8 Right most bit vs Rightmost set bit
Right most bit always the bit on the far right.It's just the least-significant bit (LSB). it used to check if a number is even or odd.
but
Rightmost set bit is the first 1 when you scan the number from the right. it is used to find where two numbers differ 
(like the XOR problem). Used to build masks and partition groups. and they are NOT the same.

- **Example:**
  ```java
    12 = 1100
  ```
    - Right most bit: 0 (the farthest right bit)
    - Rightmost set bit: 4 (the first 1 from the right, which is in the 2^2 position)

---

## 🧠 Common Patterns
In java , to get rightmost set bit and right most bit we can use the following code snippets:

```java
int rightMostSetBit = x & -x;

int rightMostBit = x & 1;
```
example:
```java
int x = 12; // 1100
int rightMostSetBit = x & -x; // 4 (0100)

int rightMostBit = x & 1; // 0
```
## 🔥 Super-Useful Bit Tricks

- **Find rightmost set bit:**
  ```java
  int right = x & -x;
  ```
- **Check if number is even:**
  ```java
  (x & 1) == 0
  ```
- **Set a bit:**
  ```java
  x |= (1 << k);
  ```
- **Clear a bit:**
  ```java
  x &= ~(1 << k);
  ```
- **Toggle a bit:**
  ```java
  x ^= (1 << k);
  ```
- **Check k-th bit:**
  ```java
  ((x >> k) & 1) == 1
  ```
