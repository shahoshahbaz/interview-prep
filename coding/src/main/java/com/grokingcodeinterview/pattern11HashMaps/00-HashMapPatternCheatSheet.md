# HashMap Pattern Cheat Sheet - Interview Preparation

## Table of Contents
1. [Overview](#overview)
2. [Core Concepts](#core-concepts)
3. [Key Techniques](#key-techniques)
4. [Problems & Solutions](#problems--solutions)
5. [Common Patterns](#common-patterns)
6. [Time & Space Complexity](#time--space-complexity)
7. [Tips & Tricks](#tips--tricks)
8. [Common Mistakes](#common-mistakes)

---

## Overview

The **HashMap pattern** is one of the most frequently used patterns in coding interviews. It leverages the HashMap/HashTable data structure to:
- Store and retrieve data in **O(1)** average time
- Count frequencies of elements
- Find unique elements
- Track seen elements for quick lookups

### When to Use HashMap:
- Need fast lookups/insertions
- Counting occurrences of elements
- Finding duplicates or unique elements
- Need to track relationships between elements
- Converting between different representations

---

## Core Concepts

### 1. **Frequency Counting**
The most common use case - count how many times each element appears.

```java
Map<Character, Integer> freqMap = new HashMap<>();
for (char ch : str.toCharArray()) {
    freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
}
```

**Key Method:** `getOrDefault(key, defaultValue)`
- Returns the value for key, or defaultValue if key doesn't exist
- Saves an `if` statement check

### 2. **Containment Check**
Verify if a key exists before accessing.

```java
if (freqMap.containsKey(key)) {
    // Do something
}
```

### 3. **Entry Iteration**
Iterate over all key-value pairs.

```java
for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
    char key = entry.getKey();
    int value = entry.getValue();
}
```

### 4. **Value Extraction**
Iterate only over values when keys aren't needed.

```java
for (int freq : freqMap.values()) {
    // Process frequency
}
```

### 5. **Key Iteration**
Iterate only over keys.

```java
for (char ch : freqMap.keySet()) {
    int freq = freqMap.get(ch);
}
```

---

## Key Techniques

### Technique 1: Two-Pass Frequency Count
**When:** You need to find something unique after counting

**Pattern:**
```
Pass 1: Build a frequency map
Pass 2: Query the frequency map with original order/value
```

**Example:** Find first non-repeating character
- Pass 1: Count frequencies of all characters
- Pass 2: Iterate string and return first character with frequency 1

### Technique 2: Two-HashMap Comparison
**When:** You need to compare frequencies of two groups

**Pattern:**
```
HashMap 1: Build frequency map of requirement
HashMap 2: Build frequency map of available items
Compare: Check if available >= required for each key
```

**Example:** Ransom note problem
- HashMap 1: Character frequencies in ransom note
- HashMap 2: Character frequencies in magazine
- Check if magazine has all needed characters

### Technique 3: Character/Element Subtraction
**When:** You need to verify if elements can be used

**Pattern:**
```
1. Build frequency map
2. For each element to use, decrement frequency
3. Check if frequency becomes negative (invalid)
```

**Example:** Ransom note solution approach

### Technique 4: Division for Maximum Usage
**When:** Finding maximum repetitions of a pattern

**Pattern:**
```
For each required character/element:
  maxCount = min(maxCount, available[element] / required[element])
```

**Example:** Maximum balloons - how many times can "balloon" be formed?

### Technique 5: Parity Checking
**When:** Working with palindromes or paired elements

**Pattern:**
```java
boolean oddFound = false;
for (int freq : freqMap.values()) {
    if (freq % 2 == 0) {
        length += freq;
    } else {
        length += (freq - 1);
        oddFound = true;
    }
}
if (oddFound) length++;
```

**Logic:** 
- Even frequencies contribute fully to palindrome
- Odd frequencies contribute (freq - 1) + 1 (at center)

---

## Problems & Solutions

### Problem 1: First Non-Repeating Character

**Problem Statement:**
Given a string, find the index of the first character that appears only once.
Return -1 if no such character exists.

**Examples:**
- Input: "apple" → Output: 0 (character 'a')
- Input: "abcab" → Output: 2 (character 'c')
- Input: "abab" → Output: -1 (no unique character)

**Solution Approach:**

**Step 1:** Count frequencies of all characters
```java
Map<Character, Integer> freqMap = new HashMap<>();
for (char ch : str.toCharArray()) {
    freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
}
```

**Step 2:** Find first character with frequency 1
```java
for (int i = 0; i < str.length(); i++) {
    if (freqMap.get(str.charAt(i)) == 1) {
        return i;
    }
}
return -1;
```

**Complete Code:**
```java
public static int firstUniqueChar(String str) {
    Map<Character, Integer> freqMap = new HashMap<>();
    
    for (char ch : str.toCharArray()) {
        freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
    }
    
    for (int i = 0; i < str.length(); i++) {
        if (freqMap.get(str.charAt(i)) == 1) {
            return i;
        }
    }
    
    return -1;
}
```

**Complexity:**
- Time: O(n) - Two passes through the string
- Space: O(1) - HashMap contains at most 26 characters (lowercase letters)

**Key Insight:** Two-pass approach maintains original order

---

### Problem 2: Largest Unique Number

**Problem Statement:**
Find the highest value number that appears exactly once in an array.
Return -1 if no such number exists.

**Examples:**
- Input: [5, 7, 3, 7, 5, 8] → Output: 8
- Input: [1, 2, 3, 2, 1, 4, 4] → Output: 3
- Input: [9, 9, 8, 8, 7, 7] → Output: -1

**Solution Approach:**

**Step 1:** Count frequencies
```java
Map<Integer, Integer> freqMap = new HashMap<>();
for (int num : nums) {
    freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
}
```

**Step 2:** Find maximum value with frequency = 1
```java
int max = Integer.MIN_VALUE;
for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
    if (entry.getValue() == 1) {
        max = Math.max(max, entry.getKey());
    }
}
return max == Integer.MIN_VALUE ? -1 : max;
```

**Complete Code:**
```java
public static int largeUniqueNumber(int[] nums) {
    Map<Integer, Integer> freqMap = new HashMap<>();
    
    for (int num : nums) {
        freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
    }
    
    int max = Integer.MIN_VALUE;
    for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
        if (entry.getValue() == 1) {
            max = Math.max(max, entry.getKey());
        }
    }
    
    return max == Integer.MIN_VALUE ? -1 : max;
}
```

**Complexity:**
- Time: O(n) - Single iteration
- Space: O(n) - HashMap of unique numbers

**Key Insight:** Use `Integer.MIN_VALUE` as sentinel to detect when no unique number exists

---

### Problem 3: Maximum Number of Balloons

**Problem Statement:**
Determine how many times the word "balloon" can be formed using characters from a given string.
Each character can be used only once.

**Examples:**
- Input: "balloonballoon" → Output: 2
- Input: "bbaall" → Output: 0 (missing 'o')
- Input: "balloonballoooon" → Output: 2 (extra 'o' doesn't help)

**Analysis:**
- "balloon" requires: b=1, a=1, l=2, o=2, n=1
- For each character type, divide available count by required count
- Take the minimum of all divisions

**Solution Approach:**

**Step 1:** Count frequencies in "balloon"
```java
Map<Character, Integer> balloonFreq = new HashMap<>();
for (char ch : "balloon".toCharArray()) {
    balloonFreq.put(ch, balloonFreq.getOrDefault(ch, 0) + 1);
}
```

**Step 2:** Count frequencies in input string
```java
Map<Character, Integer> strFreq = new HashMap<>();
for (char ch : str.toCharArray()) {
    strFreq.put(ch, strFreq.getOrDefault(ch, 0) + 1);
}
```

**Step 3:** For each character in "balloon", calculate max possible count
```java
int maxBalloons = Integer.MAX_VALUE;
for (char ch : balloonFreq.keySet()) {
    if (!strFreq.containsKey(ch)) {
        return 0;  // Missing required character
    }
    int possible = strFreq.get(ch) / balloonFreq.get(ch);
    maxBalloons = Math.min(maxBalloons, possible);
}
return maxBalloons;
```

**Complete Code:**
```java
public static int findMaximumNumberOfBalloons(String str) {
    Map<Character, Integer> balloonFreq = new HashMap<>();
    for (char ch : "balloon".toCharArray()) {
        balloonFreq.put(ch, balloonFreq.getOrDefault(ch, 0) + 1);
    }
    
    Map<Character, Integer> strFreq = new HashMap<>();
    for (char ch : str.toCharArray()) {
        strFreq.put(ch, strFreq.getOrDefault(ch, 0) + 1);
    }
    
    int maxBalloons = Integer.MAX_VALUE;
    for (char ch : balloonFreq.keySet()) {
        if (!strFreq.containsKey(ch)) {
            return 0;
        }
        int possible = strFreq.get(ch) / balloonFreq.get(ch);
        maxBalloons = Math.min(maxBalloons, possible);
    }
    
    return maxBalloons;
}
```

**Complexity:**
- Time: O(n + m) - n for counting balloon, m for counting string, m > n usually
- Space: O(1) - HashMap limited to alphabet size

**Key Insight:** Division approach finds bottleneck character (minimum division result)

---

### Problem 4: Longest Palindrome Length

**Problem Statement:**
Determine the maximum length of a palindrome that can be constructed from string characters.

**Examples:**
- Input: "applepie" → Output: 5 (e.g., "pepep")
- Input: "aabbcc" → Output: 6 (e.g., "abccba")
- Input: "bananas" → Output: 5 (e.g., "anana")

**Key Concept:**
- Palindromes use characters in pairs (even frequency)
- One character can be in the middle (odd frequency)
- From odd frequency f, we can use f-1 characters + 1 in middle

**Algorithm Logic:**
```
1. Count character frequencies
2. For each character:
   - If frequency is even: add all characters
   - If frequency is odd: add (frequency - 1) + mark odd found
3. If any odd found: add 1 (for middle character)
```

**Solution Approach:**

**Step 1:** Count frequencies
```java
Map<Character, Integer> freqMap = new HashMap<>();
for (char ch : str.toCharArray()) {
    freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
}
```

**Step 2:** Calculate palindrome length using parity
```java
int length = 0;
boolean oddFound = false;

for (int freq : freqMap.values()) {
    if (freq % 2 == 0) {
        length += freq;  // Add all characters
    } else {
        length += (freq - 1);  // Add pairs only
        oddFound = true;  // Mark that we have odd
    }
}

if (oddFound) length++;  // Add 1 for center character
return length;
```

**Complete Code:**
```java
public static int longestPalindrome(String str) {
    Map<Character, Integer> freqMap = new HashMap<>();
    
    for (char ch : str.toCharArray()) {
        freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
    }
    
    int length = 0;
    boolean oddFound = false;
    
    for (int freq : freqMap.values()) {
        if (freq % 2 == 0) {
            length += freq;
        } else {
            length += (freq - 1);
            oddFound = true;
        }
    }
    
    if (oddFound) length++;
    return length;
}
```

**Complexity:**
- Time: O(n) - Count characters once
- Space: O(1) - HashMap limited to 26 characters

**Example Walkthrough:**
```
Input: "applepie"
Frequencies: {a:1, p:2, l:1, e:2, i:1}

Processing:
- a: frequency 1 (odd) → add 0, oddFound = true
- p: frequency 2 (even) → add 2
- l: frequency 1 (odd) → add 0, oddFound = true
- e: frequency 2 (even) → add 2
- i: frequency 1 (odd) → add 0, oddFound = true

Result: 0 + 2 + 0 + 2 + 0 = 4, oddFound = true → 4 + 1 = 5
```

**Key Insight:** Parity matters more than actual frequencies

---

### Problem 5: Ransom Note

**Problem Statement:**
Determine if a ransom note can be constructed using characters from a magazine.
Each character in the magazine can be used only once.

**Examples:**
- ransomNote = "hello", magazine = "hellworld" → true
- ransomNote = "notes", magazine = "stoned" → true
- ransomNote = "apple", magazine = "pale" → false (need 2 'p' but only have 1)

**Solution Approach 1: Frequency Comparison**

**Step 1:** Count frequencies in magazine
```java
Map<Character, Integer> freqMap = new HashMap<>();
for (char ch : magazine.toCharArray()) {
    freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
}
```

**Step 2:** For each character in ransom note, check if available
```java
for (char ch : ransomNote.toCharArray()) {
    if (!freqMap.containsKey(ch)) {
        return false;
    }
    freqMap.put(ch, freqMap.get(ch) - 1);
    if (freqMap.get(ch) == 0) {
        freqMap.remove(ch);  // Clean up
    }
}
return true;
```

**Complete Code:**
```java
public static boolean canConstruct(String ransomNote, String magazine) {
    Map<Character, Integer> freqMap = new HashMap<>();
    
    for (char ch : magazine.toCharArray()) {
        freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
    }
    
    for (char ch : ransomNote.toCharArray()) {
        if (!freqMap.containsKey(ch)) {
            return false;
        }
        freqMap.put(ch, freqMap.get(ch) - 1);
        if (freqMap.get(ch) == 0) {
            freqMap.remove(ch);
        }
    }
    
    return true;
}
```

**Complexity:**
- Time: O(n + m) - n for magazine, m for ransom note
- Space: O(1) - HashMap limited to 26 characters

**Alternative Approach: Array for ASCII Characters**
```java
public static boolean canConstruct(String ransomNote, String magazine) {
    int[] charCount = new int[26];
    
    for (char ch : magazine.toCharArray()) {
        charCount[ch - 'a']++;
    }
    
    for (char ch : ransomNote.toCharArray()) {
        if (charCount[ch - 'a'] == 0) {
            return false;
        }
        charCount[ch - 'a']--;
    }
    
    return true;
}
```

**When to use Array vs HashMap:**
- Array: Faster, fixed alphabet (26 for lowercase)
- HashMap: Flexible for Unicode or dynamic requirements

**Key Insight:** Decrement frequencies as you use them - fail fast if insufficient

---

## Common Patterns

### Pattern 1: Find Unique Elements
**Technique:** Count frequencies, filter for count = 1

```java
Map<E, Integer> freq = new HashMap<>();
for (E element : input) {
    freq.put(element, freq.getOrDefault(element, 0) + 1);
}

List<E> unique = new ArrayList<>();
for (E element : input) {
    if (freq.get(element) == 1) {
        unique.add(element);
    }
}
```

### Pattern 2: Check Anagrams
**Technique:** Compare frequency maps

```java
Map<Character, Integer> freq1 = buildFreqMap(str1);
Map<Character, Integer> freq2 = buildFreqMap(str2);
return freq1.equals(freq2);
```

### Pattern 3: Character Replacement
**Technique:** Track what each character maps to

```java
Map<Character, Character> mapping = new HashMap<>();
for (char c1 : s1, char c2 : s2) {
    if (mapping.containsKey(c1)) {
        if (mapping.get(c1) != c2) return false;
    } else {
        mapping.put(c1, c2);
    }
}
```

### Pattern 4: Top K Frequent Elements
**Technique:** Count frequencies, use min-heap of size K

```java
Map<Integer, Integer> freqMap = buildFreqMap(nums);
PriorityQueue<Integer> minHeap = new PriorityQueue<>(
    (a, b) -> freqMap.get(a) - freqMap.get(b)
);

for (int num : freqMap.keySet()) {
    minHeap.offer(num);
    if (minHeap.size() > k) {
        minHeap.poll();
    }
}
```

### Pattern 5: Number of Pairs
**Technique:** Count frequencies, calculate pairs

```java
Map<Integer, Integer> freqMap = buildFreqMap(input);
int pairs = 0;
for (int freq : freqMap.values()) {
    pairs += freq / 2;
}
return pairs;
```

---

## Time & Space Complexity

### Time Complexity Summary

| Operation | Complexity | Note |
|-----------|-----------|------|
| Put/Get | O(1) average | Varies with hash collisions |
| ContainsKey | O(1) average | Same as Get |
| Remove | O(1) average | Same as Put |
| Full iteration | O(n) | Must visit each entry |
| Building frequency map | O(n) | Two passes is O(2n) = O(n) |

### Space Complexity Summary

| Scenario | Space | Note |
|----------|-------|------|
| Fixed alphabet (26 letters) | O(1) | Constant space |
| Arbitrary elements | O(n) | Worst case, all unique |
| k-sized cache | O(k) | When using limited threshold |

---

## Tips & Tricks

### 1. Use `getOrDefault()` for Cleaner Code
```java
// Instead of:
if (map.containsKey(key)) {
    map.put(key, map.get(key) + 1);
} else {
    map.put(key, 1);
}

// Use:
map.put(key, map.getOrDefault(key, 0) + 1);
```

### 2. Initialize Sentinel Values Correctly
```java
// For maximum:
int max = Integer.MIN_VALUE;  // Not 0!

// For minimum:
int min = Integer.MAX_VALUE;  // Not Integer.MAX_VALUE
```

### 3. Remember Two-Pass Pattern
- **Pass 1:** Build frequency map
- **Pass 2:** Query or iterate with original structure

This maintains order and is efficient.

### 4. Use Frequency Map Comparison for Validation
```java
// Check if all required elements exist
for (char required : requiredFreq.keySet()) {
    if (!availableFreq.containsKey(required) ||
        availableFreq.get(required) < requiredFreq.get(required)) {
        return false;
    }
}
```

### 5. Consider Array Instead of HashMap for ASCII
```java
// For lowercase English letters:
int[] freq = new int[26];
for (char ch : str.toCharArray()) {
    freq[ch - 'a']++;
}

// Faster and simpler than HashMap for small, fixed alphabet!
```

### 6. Early Exit Optimization
```java
// Instead of checking after building full map:
for (char ch : ransomNote.toCharArray()) {
    if (!freqMap.containsKey(ch)) {
        return false;  // Fail fast!
    }
    freqMap.put(ch, freqMap.get(ch) - 1);
}
```

### 7. Entry Set for Key-Value Pairs
```java
// More efficient than:
for (K key : map.keySet()) {
    V value = map.get(key);
}

// Use:
for (Map.Entry<K, V> entry : map.entrySet()) {
    K key = entry.getKey();
    V value = entry.getValue();
}
```

---

## Common Mistakes

### ❌ Mistake 1: Not Handling Edge Cases
```java
// WRONG: Doesn't check for null or empty string
for (char ch : str.toCharArray()) {
    // ...
}

// CORRECT:
if (str == null || str.length() == 0) return -1;
for (char ch : str.toCharArray()) {
    // ...
}
```

### ❌ Mistake 2: Using Wrong Sentinel Value
```java
// WRONG:
int max = 0;  // What if all numbers are negative?

// CORRECT:
int max = Integer.MIN_VALUE;
```

### ❌ Mistake 3: Forgetting to Check containsKey
```java
// WRONG: NullPointerException if key doesn't exist
int value = map.get(key);
value++;

// CORRECT:
if (map.containsKey(key)) {
    map.put(key, map.get(key) + 1);
} else {
    map.put(key, 1);
}
// Or simply:
map.put(key, map.getOrDefault(key, 0) + 1);
```

### ❌ Mistake 4: Modifying HashMap While Iterating
```java
// WRONG: Can cause ConcurrentModificationException
for (String key : map.keySet()) {
    if (shouldRemove(key)) {
        map.remove(key);  // BAD!
    }
}

// CORRECT:
Iterator<String> iterator = map.keySet().iterator();
while (iterator.hasNext()) {
    String key = iterator.next();
    if (shouldRemove(key)) {
        iterator.remove();  // Good!
    }
}

// Or collect keys first:
List<String> toRemove = new ArrayList<>();
for (String key : map.keySet()) {
    if (shouldRemove(key)) {
        toRemove.add(key);
    }
}
for (String key : toRemove) {
    map.remove(key);
}
```

### ❌ Mistake 5: Inefficient Frequency Checking
```java
// WRONG: O(n²) complexity
for (char ch : str1.toCharArray()) {
    int count = 0;
    for (char c : str2.toCharArray()) {
        if (c == ch) count++;
    }
}

// CORRECT: O(n) complexity
Map<Character, Integer> freq = buildFreqMap(str2);
for (char ch : str1.toCharArray()) {
    if (freq.getOrDefault(ch, 0) > 0) {
        freq.put(ch, freq.get(ch) - 1);
    }
}
```

### ❌ Mistake 6: Not Considering Integer Overflow
```java
// WRONG: Can overflow in counting
int count = freqMap.values().stream().mapToInt(Integer::intValue).sum();

// CONSIDER using long for large counts:
long totalCount = freqMap.values().stream().mapToLong(Long::valueOf).sum();
```

### ❌ Mistake 7: Case Sensitivity Issues
```java
// WRONG: Treats 'A' and 'a' as different
Map<Character, Integer> freq = buildFreqMap(str);

// CORRECT: Normalize to lowercase
Map<Character, Integer> freq = buildFreqMap(str.toLowerCase());
```

---

## Quick Reference Template

### Template for Frequency-Based Problem
```java
public static SomeType solveProblem(InputType input) {
    // Step 1: Count frequencies
    Map<ElementType, Integer> freqMap = new HashMap<>();
    for (ElementType element : input) {
        freqMap.put(element, freqMap.getOrDefault(element, 0) + 1);
    }
    
    // Step 2: Process based on frequencies
    for (Map.Entry<ElementType, Integer> entry : freqMap.entrySet()) {
        if (entry.getValue() == someCondition) {
            // Do something
        }
    }
    
    // Step 3: Return result
    return result;
}
```

### Template for Two-HashMap Comparison
```java
public static boolean compareRequirement(String required, String available) {
    Map<Character, Integer> requiredFreq = buildFreqMap(required);
    Map<Character, Integer> availableFreq = buildFreqMap(available);
    
    for (char ch : requiredFreq.keySet()) {
        if (!availableFreq.containsKey(ch) || 
            availableFreq.get(ch) < requiredFreq.get(ch)) {
            return false;
        }
    }
    
    return true;
}

private static Map<Character, Integer> buildFreqMap(String str) {
    Map<Character, Integer> map = new HashMap<>();
    for (char ch : str.toCharArray()) {
        map.put(ch, map.getOrDefault(ch, 0) + 1);
    }
    return map;
}
```

---

## Interview Tips

### Before You Code:
1. ✅ Ask clarifying questions:
   - Are characters/elements case-sensitive?
   - What's the range of input size?
   - Can input be null or empty?
   - Are there special characters to consider?

2. ✅ Discuss approach:
   - "I'll use a frequency map to count occurrences"
   - "Then I'll iterate to find what we're looking for"
   
3. ✅ Mention complexity:
   - "This is O(n) time complexity"
   - "Space complexity is O(1) since we have fixed alphabet"

### During Coding:
1. ✅ Use meaningful variable names
2. ✅ Add comments for non-obvious logic
3. ✅ Handle edge cases explicitly
4. ✅ Test with examples

### After Coding:
1. ✅ Test with provided examples
2. ✅ Test with edge cases (empty, single element, all same)
3. ✅ Discuss optimizations
4. ✅ Talk through the logic one more time

---

## Related Patterns to Study

- **Sliding Window**: Similar frequency counting but with dynamic window
- **Two Pointers**: Complement to HashMap for certain problems
- **Heap**: Often combined with HashMap for top-k problems
- **Trie**: Alternative for string frequency problems
- **Bit Manipulation**: Sometimes alternative to HashMap for specific cases

---

**Last Updated:** Interview Preparation
**Difficulty Level:** Medium (for individual problems, Easy to Medium collectively)

