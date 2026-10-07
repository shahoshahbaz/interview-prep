# Java Common Methods Cheatsheet

## Character Methods (`Character` class)

| Method | Definition | Example |
|---|---|---|
| `isDigit` | Returns `true` if the char is a digit (0-9) | `Character.isDigit('5') // true` |
| `isLetter` | Returns `true` if the char is a letter | `Character.isLetter('a') // true` |
| `toLowerCase` | Converts a char to lowercase | `Character.toLowerCase('A') // 'a'` |
| `toUpperCase` | Converts a char to uppercase | `Character.toUpperCase('a') // 'A'` |

---

## String Methods (`String` class)

| Method | Definition | Example |
|---|---|---|
| `charAt` | Returns the char at a given index | `"hello".charAt(1) // 'e'` |
| `contains` | Returns `true` if the string contains a given sequence | `"hello".contains("ell") // true` |
| `endsWith` | Returns `true` if the string ends with a given suffix | `"hello".endsWith("lo") // true` |
| `equals` | Compares string content (not reference) for equality | `"abc".equals("abc") // true` |
| `format` | Builds a formatted string using placeholders | `String.format("Name: %s, Age: %d", "Sam", 30)` |
| `indexOf` | Returns the index of the first occurrence, or -1 | `"hello".indexOf('l') // 2` |
| `isEmpty` | Returns `true` if length is 0 | `"".isEmpty() // true` |
| `join` | Joins multiple strings with a delimiter | `String.join("-", "a", "b", "c") // "a-b-c"` |
| `length` | Returns the number of characters | `"hello".length() // 5` |
| `replace` | Replaces all occurrences of a char/sequence | `"hello".replace('l', 'p') // "heppo"` |
| `split` | Splits a string into an array using a regex delimiter | `"a,b,c".split(",") // ["a","b","c"]` |
| `startsWith` | Returns `true` if the string starts with a given prefix | `"hello".startsWith("he") // true` |
| `substring` | Extracts a portion of the string | `"hello".substring(1, 3) // "el"` |
| `toCharArray` | Converts the string into a char array | `"abc".toCharArray() // ['a','b','c']` |
| `toLowerCase` | Converts the string to lowercase | `"HELLO".toLowerCase() // "hello"` |
| `toUpperCase` | Converts the string to uppercase | `"hello".toUpperCase() // "HELLO"` |
| `trim` | Removes leading/trailing whitespace | `"  hi  ".trim() // "hi"` |
| `valueOf` | Converts a given value (int, char, etc.) to a String | `String.valueOf(42) // "42"` |

---

## Collections Methods (`java.util.Collections`)

> Static methods that operate on `List`/`Collection` objects.

| Method | Definition | Example |
|---|---|---|
| `binarySearch` | Searches a **sorted** list for a value, returns index or negative insertion point | `Collections.binarySearch(list, 5)` |
| `max` | Returns the largest element in a collection | `Collections.max(list) // e.g. 9` |
| `min` | Returns the smallest element in a collection | `Collections.min(list) // e.g. 1` |
| `reverse` | Reverses the order of elements in a list (in place) | `Collections.reverse(list)` |
| `singletonList` | Returns an immutable list containing only one element | `Collections.singletonList("x") // ["x"]` |
| `sort` | Sorts a list in natural (or custom comparator) order | `Collections.sort(list)` |

```java
List<Integer> list = new ArrayList<>(List.of(5, 3, 9, 1));
Collections.sort(list);                    // [1, 3, 5, 9]
int idx = Collections.binarySearch(list, 5); // 2
Collections.reverse(list);                 // [9, 5, 3, 1]
int max = Collections.max(list);           // 9
int min = Collections.min(list);           // 1
List<String> single = Collections.singletonList("only");
```

---

## Map Methods (`Map` interface, e.g. `HashMap`)

| Method | Definition | Example |
|---|---|---|
| `computeIfAbsent` | Computes and inserts a value if key is absent | `map.computeIfAbsent("a", k -> new ArrayList<>())` |
| `containsKey` | Returns `true` if the map has the given key | `map.containsKey("a") // true` |
| `entrySet` | Returns a `Set` view of all key-value pairs | `for (var e : map.entrySet()) {...}` |
| `get` | Returns the value for a key, or `null` if absent | `map.get("a")` |
| `getOrDefault` | Returns the value for a key, or a default if absent | `map.getOrDefault("a", 0)` |
| `keySet` | Returns a `Set` view of all keys | `map.keySet()` |
| `put` | Inserts or updates a key-value pair | `map.put("a", 1)` |
| `putIfAbsent` | Inserts a value only if the key is not already present | `map.putIfAbsent("a", 1)` |
| `remove` | Removes the mapping for a given key | `map.remove("a")` |
| `size` | Returns the number of key-value pairs | `map.size() // 3` |
| `values` | Returns a `Collection` view of all values | `map.values()` |

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.putIfAbsent("a", 99);      // no effect, "a" already exists
int v = map.getOrDefault("b", 0); // 0
map.computeIfAbsent("c", k -> 0);
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}
```

---

## Arrays Methods (`java.util.Arrays`)

> Static methods that operate on arrays.

| Method | Definition | Example |
|---|---|---|
| `asList` | Converts an array into a fixed-size `List` | `Arrays.asList(1, 2, 3)` |
| `binarySearch` | Searches a **sorted** array for a value | `Arrays.binarySearch(arr, 5)` |
| `copyOf` | Copies an array, truncating or padding to a new length | `Arrays.copyOf(arr, 5)` |
| `copyOfRange` | Copies a specified range of an array | `Arrays.copyOfRange(arr, 1, 3)` |
| `equals` | Compares two arrays for equal length and content | `Arrays.equals(arr1, arr2)` |
| `fill` | Fills every element of an array with a given value | `Arrays.fill(arr, 0)` |
| `sort` | Sorts an array in place (ascending order) | `Arrays.sort(arr)` |
| `stream` | Creates a `Stream` from an array | `Arrays.stream(arr).sum()` |
| `toString` | Returns a readable string representation of an array | `Arrays.toString(arr) // "[1, 2, 3]"` |

```java
int[] arr = {5, 3, 9, 1};
Arrays.sort(arr);                          // [1, 3, 5, 9]
int idx = Arrays.binarySearch(arr, 5);     // 2
int[] copy = Arrays.copyOf(arr, 6);        // [1, 3, 5, 9, 0, 0]
int[] range = Arrays.copyOfRange(arr, 1, 3); // [3, 5]
Arrays.fill(arr, 0);                       // [0, 0, 0, 0]
System.out.println(Arrays.toString(arr));  // "[0, 0, 0, 0]"
List<Integer> list = Arrays.asList(1, 2, 3);
int sum = Arrays.stream(new int[]{1,2,3}).sum(); // 6
```