# 🌲 Trie (Prefix Tree) Cheat Sheet

---

## 📚 Table of Contents

1. [Definition](#1-definition)
2. [When to Use This Pattern (Recognition Signals)](#2-when-to-use-this-pattern-recognition-signals)
3. [Core Idea (Mental Model)](#3-core-idea-mental-model)
4. [Standard Code Template (Skeleton)](#4-standard-code-template-skeleton)
5. [Key Decisions to Make (Interview Gold)](#5-key-decisions-to-make-interview-gold)
6. [Common Traps & Mistakes](#6-common-traps--mistakes)
7. [Time & Space Complexity](#7-time--space-complexity)
8. [Canonical Problems (Must-Know)](#8-canonical-problems-must-know)
9. [My Personal Notes (Critical Section)](#9-my-personal-notes-critical-section)
10. [Quick Checklist Before Coding](#-quick-checklist-before-coding)
11. [Quick Reference - Problem Types](#-quick-reference---problem-types)
12. [Pinterest Senior SWE Priority Tracker](#12-pinterest-senior-swe-priority-tracker)

---

## 1. Definition

**Trie (Prefix Tree)** — A tree where each **node represents one token** (a character, or a word — see [Section 5](#5-key-decisions-to-make-interview-gold)), and paths from the root spell out strings/phrases. Nodes shared by a common prefix are **reused**, not duplicated.

---

## 2. When to Use This Pattern (Recognition Signals)

- ✅ Need **fast prefix lookups** (does any word start with "ca"?)
- ✅ Need **autocomplete / search suggestions**
- ✅ Problem involves a **dictionary of words** searched repeatedly
- ✅ Problems with **"prefix", "autocomplete", "word dictionary", "starts with" keywords**
- ✅ Many strings share **common prefixes** — a plain HashSet wastes that structure

### Complexity Hint:
Insert/search/startsWith are all **O(L)**, independent of how many words are stored — see [Section 7](#7-time--space-complexity).

### One-Line Trigger Thought:
> "Do I need to repeatedly check prefixes across many strings that share structure?"

---

## 3. Core Idea (Mental Model)

**Trie = Tree traversal where reusing a node = sharing a prefix**

Think of it like a branching hallway:
1. **Walk** — for each token in the string, step to the child node for that token
2. **Build if missing** — if no child exists for that token yet, create it
3. **Mark the end** — flag the final node as `isEndOfWord` (a complete entry ends here, not just a prefix passing through)
4. **Reuse, don't duplicate** — if a path already exists (shared prefix), walk it instead of recreating it

The key insight: **`isEndOfWord` is the only thing that distinguishes "a word ends here" from "just passing through on the way to a longer word."** The tree shape alone doesn't tell you that.

---

## 4. Standard Code Template (Skeleton)

**Two shapes. Pick by tokenization unit, not by problem name.**

- **Base — character-level** → Each node = one character, `children` keyed by `Character`. Standard interview trie. *(Implement Trie, Word Search II, Add and Search Word)*
- **[4a — Word-level](#4a-word-level-variant-tokenize-by-word-not-character)** → Each node = one word/token, `children` keyed by `String`, string split by delimiter (usually space) instead of `toCharArray()`. *(Query autocomplete, phrase suggestion systems — e.g. Pinterest search suggestions)*

### Base template — character-level

```java
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord;
}

class Trie {
    TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            curr.children.putIfAbsent(c, new TrieNode());  // BUILD IF MISSING
            curr = curr.children.get(c);                    // WALK / REUSE
        }
        curr.isEndOfWord = true;                             // MARK THE END
    }

    public boolean search(String word) {
        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;             // must be a COMPLETE word
    }

    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;                     // path existing is enough
    }

    private TrieNode findNode(String s) {
        TrieNode curr = root;
        for (char c : s.toCharArray()) {
            if (!curr.children.containsKey(c)) return null;
            curr = curr.children.get(c);
        }
        return curr;
    }
}
```

**The three lines that matter** — memorize this block, it's the whole `insert` pattern:

```java
curr.children.putIfAbsent(c, new TrieNode());  // build if missing
curr = curr.children.get(c);                    // walk
// ... after loop:
curr.isEndOfWord = true;                         // mark end
```

⚠️ **Trap:** `search` and `startsWith` share `findNode` — the *only* difference is whether you check `isEndOfWord` afterward. Don't duplicate the traversal logic.

### Autocomplete extension (prefix + DFS collect)

```java
public List<String> autocomplete(String prefix) {
    List<String> results = new ArrayList<>();
    TrieNode prefixNode = findNode(prefix);
    if (prefixNode == null) return results;       // no words with this prefix

    dfs(prefixNode, prefix, results);
    return results;
}

private void dfs(TrieNode node, String currentWord, List<String> results) {
    if (node.isEndOfWord) {
        results.add(currentWord);
    }
    for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
        dfs(entry.getValue(), currentWord + entry.getKey(), results);
    }
}
```

**Two-phase idea:** (1) `findNode(prefix)` walks down to where the prefix ends — reuses the same helper as `search`/`startsWith`. (2) `dfs(...)` explores every branch below that node, collecting each complete word found along the way.

⚠️ **Trap:** string concatenation (`currentWord + entry.getKey()`) rebuilds a new string at every recursive call — fine for interviews, but a `StringBuilder` with `append`/`deleteCharAt` avoids the hidden cost if asked to optimize (see [Section 7](#7-time--space-complexity)).

### 4a. Word-level variant (tokenize by word, not character)

Same structure, different token unit — split by word instead of by letter. No new logic, just a different key type and split strategy.

```java
class TrieNode {
    Map<String, TrieNode> children = new HashMap<>();   // keyed by WORD, not Character
    boolean isEndOfPhrase;
}

class Trie {
    TrieNode root = new TrieNode();

    public void insert(String phrase) {
        TrieNode curr = root;
        for (String word : phrase.split(" ")) {          // split by word, not toCharArray()
            curr.children.putIfAbsent(word, new TrieNode());
            curr = curr.children.get(word);
        }
        curr.isEndOfPhrase = true;
    }
}
```

⚠️ **Why HashMap, not array:** char-level tries can use `array[26]` since the alphabet is bounded. Word-level vocab is unbounded — `HashMap<String, TrieNode>` is the only realistic choice.

### 4b. Delete operation (recursive prune, safe removal)

Unlike insert (always create) or search (read-only), delete must remove nodes **only if safe** — a node might be shared by another word's path.

```java
public void delete(String word) {
    delete(root, word, 0);
}

private boolean delete(TrieNode node, String word, int index) {
    // BASE CASE: consumed the whole word, standing on its last node
    if (index == word.length()) {
        if (!node.isEndOfWord) return false;      // word was never inserted
        node.isEndOfWord = false;                  // unmark it
        return node.children.isEmpty();             // no children -> safe to delete this node
    }

    char c = word.charAt(index);
    TrieNode child = node.children.get(c);
    if (child == null) return false;                // word not in trie

    boolean shouldDeleteChild = delete(child, word, index + 1);   // recurse deeper first

    if (shouldDeleteChild) {
        node.children.remove(c);                     // prune the child
        return node.children.isEmpty() && !node.isEndOfWord;   // can WE also be pruned?
    }
    return false;
}
```

**Dry run — delete("car") from a trie holding "cat", "car":**

| Call | node | index | action | returns |
|---|---|---|---|---|
| delete(root,"car",0) | root | 0 | recurse into 'c' | waits |
| delete(c,"car",1) | c | 1 | recurse into 'a' | waits |
| delete(a,"car",2) | a | 2 | recurse into 'r' | waits |
| delete(r,"car",3) | r | 3 (base case) | unmark isEndOfWord; no children | `true` |
| back at a | a | 2 | remove 'r' from a.children; a still has 't' → not empty | `false` |
| back at c | c | 1 | nothing removed | `false` |

Result: "car" gone, "cat" untouched — `r` pruned, `a`/`c` survive because they're still on "cat"'s path.

### 4c. Count-prefix operation (counter per node, paid at insert time)

Adds an `int` counter to every node, incremented on **every step of the walk**, not just the end — trades a small insert-time cost for O(1) prefix-count queries instead of a full DFS.

```java
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord;
    int prefixCount = 0;          // how many inserted words pass through this node
}

public void insert(String word) {
    TrieNode curr = root;
    for (char c : word.toCharArray()) {
        curr.children.putIfAbsent(c, new TrieNode());
        curr = curr.children.get(c);
        curr.prefixCount++;        // bump on EVERY step, not just the end
    }
    curr.isEndOfWord = true;
}

public int countWordsWithPrefix(String prefix) {
    TrieNode node = findNode(prefix);
    return node == null ? 0 : node.prefixCount;
}
```

**Why it works:** a node's counter = "how many words were ever built by walking through here." Since "cart" contains "car" as a prefix, inserting "cart" necessarily re-walks the same nodes "car" used, bumping their counters too — no need to distinguish "ends here" vs "passes through" for this query.

### 4d. Ranked autocomplete (frequency-weighted, word-level)

**Part 1 — Autocomplete (baseline):** given a prefix, return matching phrases (covered in the base autocomplete extension above, word-level variant).

**Part 2 — Ranked by Frequency:** each log entry also has a frequency (how many times searched). Given a prefix, return matching phrases ranked by frequency descending, tie-broken lexicographically.

```
rice, 1000
rice fried, 500
rice fried chicken, 200
rice fried chicken with beans, 50
```
`autocomplete("rice")` with top-3 → `["rice", "rice fried", "rice fried chicken"]`

Two implementation options, increasing in complexity — pick based on read/write ratio:

**Option A — naive: frequency stored only at `isEndOfPhrase`, sort at query time**

```java
class WordTrieNode {
    Map<String, WordTrieNode> children = new HashMap<>();
    boolean isEndOfPhrase;
    int frequency;              // only meaningful if isEndOfPhrase == true
}

public void insert(String phrase, int freq) {
    WordTrieNode curr = root;
    for (String word : phrase.split(" ")) {
        curr.children.putIfAbsent(word, new WordTrieNode());
        curr = curr.children.get(word);
    }
    curr.isEndOfPhrase = true;
    curr.frequency = freq;
}

public List<String> topKByFrequency(String prefix, int k) {
    WordTrieNode prefixNode = findNode(prefix);
    if (prefixNode == null) return new ArrayList<>();

    List<Map.Entry<String, Integer>> matches = new ArrayList<>();
    dfs(prefixNode, prefix, matches);

    matches.sort((a, b) -> {
        if (!a.getValue().equals(b.getValue())) return b.getValue() - a.getValue();  // freq desc
        return a.getKey().compareTo(b.getKey());                                       // tie-break
    });

    List<String> result = new ArrayList<>();
    for (int i = 0; i < Math.min(k, matches.size()); i++) result.add(matches.get(i).getKey());
    return result;
}
```
Complexity: O(P + K + K log K) — DFS collect, then sort. Fine unless K (match count) is huge.

**Option B — optimized: top-K cached at every node (min-heap)**

Trades insert-time cost for O(1)-ish query time by maintaining a small min-heap (size ≤ K) of best phrases **at every node on every phrase's path**, not just the end.

```java
class WordTrieNode {
    Map<String, WordTrieNode> children = new HashMap<>();
    boolean isEndOfPhrase;
    int frequency;
    PriorityQueue<Map.Entry<String, Integer>> topK =
        new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());  // MIN-heap by frequency
}

static final int K = 3;

public void insert(String phrase, int freq) {
    WordTrieNode curr = root;
    updateTopK(curr, phrase, freq);                 // root tracks overall top phrases too
    for (String word : phrase.split(" ")) {
        curr.children.putIfAbsent(word, new WordTrieNode());
        curr = curr.children.get(word);
        updateTopK(curr, phrase, freq);                // update EVERY node on the path
    }
    curr.isEndOfPhrase = true;
    curr.frequency = freq;
}

private void updateTopK(WordTrieNode node, String phrase, int freq) {
    node.topK.removeIf(e -> e.getKey().equals(phrase));  // handle re-insert / frequency update
    node.topK.offer(new AbstractMap.SimpleEntry<>(phrase, freq));
    if (node.topK.size() > K) node.topK.poll();            // evict SMALLEST frequency
}

public List<String> topKByFrequency(String prefix) {
    WordTrieNode node = findNode(prefix);
    if (node == null) return new ArrayList<>();

    List<Map.Entry<String, Integer>> entries = new ArrayList<>(node.topK);
    entries.sort((a, b) -> {
        if (!a.getValue().equals(b.getValue())) return b.getValue() - a.getValue();
        return a.getKey().compareTo(b.getKey());
    });

    List<String> result = new ArrayList<>();
    for (Map.Entry<String, Integer> e : entries) result.add(e.getKey());
    return result;
}
```

**Why min-heap (not max-heap):** you need fast access to the *smallest* of your currently-kept top-K, since that's what you compare against and evict when a bigger frequency shows up.

**The tradeoff:**

| | List (sorted) | Min-heap (size K) |
|---|---|---|
| Insert/update | O(K log K) always (full re-sort) | O(log K) typically |
| Read top-K in order | O(1) — already sorted | O(K log K) — must drain + sort to get ranked output |

⚠️ **Trap: `removeIf` before `offer`** — without removing the stale entry first, re-inserting the same phrase with a new (e.g. higher) frequency creates a duplicate stale entry in the heap instead of updating it — this is the mechanism needed for the "live updates" follow-up (a phrase gets searched again).

**Interview framing:** start with Option A (correct, simple). When asked "how do you make this scale to millions of queries," pivot to Option B and say explicitly: *"I'd trade insert-time cost for query-time speed by caching the top-K at every node, since autocomplete is read far more often than phrases are inserted/updated."* That read-vs-write tradeoff framing is what signals senior-level thinking.

---

## 5. Key Decisions to Make (Interview Gold)

✅ **If you can answer these, you can code confidently.**

1. **What is the token unit?** Character or word?
   - Character → classic trie (Implement Trie, Word Search II)
   - Word → query/phrase autocomplete (search-suggestion systems)

2. **What structure for `children`?**
   - `array[26]` — fastest, but only for fixed lowercase alphabet
   - `HashMap<Character, TrieNode>` — flexible charset, standard for interviews
   - `HashMap<String, TrieNode>` — required for word-level (unbounded vocabulary)

3. **Do I need `search` (exact) or `startsWith` (prefix) or both?**
   - `search` → check `isEndOfWord` after traversal
   - `startsWith` → path existing is enough, skip the `isEndOfWord` check

4. **Do I need to *collect* matches (autocomplete) or just check existence?**
   - Existence only → `findNode` + boolean check
   - Collect all → `findNode` + `dfs` to gather every `isEndOfWord` below that point

5. **Does the problem need deletion or counting?**
   - Delete → recursive removal, only prune nodes with no children and not `isEndOfWord`
   - Count words with prefix → add an `int` counter per node, incremented on every `insert` pass-through (not just at the end)

---

## 6. Common Traps & Mistakes

❌ **Trap 1: Confusing `search` with `startsWith`**
```java
search("ca")       // false if "ca" was never inserted as a full word
startsWith("ca")   // true if the path c→a exists, regardless of isEndOfWord
```
Both share `findNode` — the only difference is the `isEndOfWord` check afterward.

❌ **Trap 2: Using `array[26]` for unbounded/mixed charsets**
- Breaks immediately if input has uppercase, digits, or unicode — default to `HashMap<Character, TrieNode>` unless the problem guarantees lowercase-only.

❌ **Trap 3: Forgetting to mark `isEndOfWord` after the loop**
```java
for (char c : word.toCharArray()) { ... }
// ❌ missing: curr.isEndOfWord = true;
```
Without this, `search` can never return true — the tree has the path but no record that a word *ends* there.

❌ **Trap 4: `main` method structural mistakes in scratch files**
- If nesting `Trie`/`TrieNode` inside another class, keep `main` as a **direct sibling**, not nested further inside `Trie` — nesting it inside a non-static inner class breaks `public static void main` discovery.

❌ **Trap 5: Autocomplete — forgetting to call `dfs()`**
```java
TrieNode prefixNode = findNode(prefix);
if (prefixNode == null) return result;
// ❌ missing: dfs(prefixNode, prefix, result);
return result;
```
`findNode` alone only confirms the prefix exists — it does **not** collect the words. This is a real bug that's easy to leave as a stub comment and forget.

❌ **Trap 6: String concatenation in DFS at scale**
- `currentWord + entry.getKey()` is O(word length) per call — fine by default, but use `StringBuilder` if asked to optimize space/time for large result sets.

---

## 7. Time & Space Complexity

| Operation | Time | Notes |
|---|---|---|
| Insert | O(L) | L = length of word/phrase; one pass, O(1) per step |
| Search | O(L) | same traversal as insert, plus final `isEndOfWord` check |
| startsWith | O(L) | same as search, skips the final check |
| Autocomplete | O(P + K) | P = prefix length (walk down), K = total chars across all matched words (DFS collect) |

**Space:**
- Per insert: O(L) worst case (no shared prefix → L new nodes), O(1) best case (entire path already exists)
- Overall trie: O(N·L) worst case (N words, avg length L, no shared prefixes) — shared prefixes reduce this in practice

**Explain why in one line:**
> None of the core operations depend on N (total words stored) — only on L (the length of the string being processed), because each step is a direct child lookup, not a scan.

---

## 8. Canonical Problems (Must-Know)

These are your **"anchor problems."** Master these first.

**Problem 1: Implement Trie (Prefix Tree)**
- Name: LeetCode 208 - Implement Trie
- Key insight: The foundational build — insert, search, startsWith, sharing `findNode` as a helper.
- Pattern: Base character-level template

**Problem 2: Design Add and Search Words Data Structure**
- Name: LeetCode 211 - Add and Search Word
- Key insight: `search` must support `.` wildcard matching any character — requires DFS branching at wildcard positions instead of a single-path walk.
- Pattern: Base template + backtracking-style branch on wildcard

**Problem 3: Word Search II**
- Name: LeetCode 212 - Word Search II
- Key insight: Build a trie from the word list first, then DFS the grid — trie lets you prune grid search early when no word matches the current path prefix.
- Pattern: Base template + grid DFS (backtracking) combined

**Problem 4: Longest Word in Dictionary**
- Name: LeetCode 720 - Longest Word in Dictionary
- Key insight: A word only "counts" if every prefix of it was also inserted as a complete word — check `isEndOfWord` at every step of the walk, not just the last.
- Pattern: Base template + full-path validity check

**Problem 5: Search Suggestions System / Autocomplete**
- Name: Common Pinterest-style query — "Product Suggestions", "Search Autocomplete"
- Key insight: Insert full vocabulary, then for each prefix `findNode` + DFS-collect (or maintain top-k at each node to avoid full DFS at query time).
- Pattern: Base template + autocomplete extension

**Problem 6: Ranked Autocomplete by Frequency (Part 2 extension)**
- Name: Pinterest-style — "Search logs ranked by frequency" (word-level trie + top-K)
- Key insight: Part 1 is plain word-level autocomplete. Part 2 adds frequency: naive approach (Option A) DFS-collects then sorts at query time; optimized approach (Option B) caches a min-heap of top-K phrases at every node, paid at insert time instead of query time. Follow-up: live updates require removing the stale heap entry before re-offering an updated frequency.
- Pattern: 4a (word-level) + 4d (ranked autocomplete, Option A/B)

---

## 9. My Personal Notes (Critical Section)

> **This is what separates seniors from juniors.**

### Mental Shortcut:
> "Trie = Tree where reusing a node = sharing a prefix. isEndOfWord is the only thing marking a real entry vs. just passing through."

### Interview Phrasing:
> "I'll use a trie to store the dictionary so prefix and exact-match lookups are O(L) instead of O(N·L) with a list. Each node holds children keyed by the next token, plus a flag marking whether a complete entry ends there."

### My Favorite Variation:
> **Word-level trie**: same exact structure and logic as char-level, just swap `Character` keys for `String` keys and `toCharArray()` for `split(" ")`. No new algorithm — just a different token granularity. This is what came up in the Pinterest context.

### What I Always Forget:
> - **Mark `isEndOfWord = true` after the loop**, not during
> - **`search` vs `startsWith`** — same traversal, different final check
> - **Call `dfs()` inside `autocomplete`** — `findNode` alone doesn't collect results
> - **HashMap for word-level / unbounded charsets**, array only for fixed lowercase alphabet
> - **`putIfAbsent`**, not `getOrDefault`, when the map itself needs mutating (not just read)
> - **`main` must be a direct sibling of `Trie`**, not nested inside it, in scratch/practice files

### Interview Tips:
> - Draw the tree for 2-3 words that share a prefix before coding — makes the "reuse vs create" logic obvious
> - State clearly: "I'll use a HashMap for children to keep this general, not assume lowercase-only"
> - If asked about autocomplete/suggestions, mention the O(P+K) breakdown proactively — shows you understand why tries beat brute-force scanning
> - For Word Search II style problems, mention trie-based pruning explicitly — it's the "why trie" answer interviewers want

---

## 📋 Quick Checklist Before Coding

```
☐ Identified the problem as trie (prefix lookups, autocomplete, dictionary)
☐ Decided: character-level or word-level tokenization?
☐ Decided: HashMap or array[26] for children?
☐ Wrote TrieNode with children map + isEndOfWord flag
☐ insert(): build-if-missing, walk, mark end after loop
☐ search(): findNode + isEndOfWord check
☐ startsWith(): findNode only, no isEndOfWord check
☐ If autocomplete needed: findNode + dfs() call (not forgotten!)
☐ ✅ Traced through with 2-3 words sharing a prefix
☐ ✅ Tested edge cases (empty string, prefix with no matches, word inserted twice)
```

---

## 🎯 Quick Reference - Problem Types

| Problem Type | Token Unit | Children Type | Key Check | Time |
|---|---|---|---|---|
| **Implement Trie** | char | Map/array | `isEndOfWord` | O(L) |
| **Add and Search (wildcard)** | char | Map | branch on `.` | O(L) avg, O(26^L) worst |
| **Word Search II** | char | Map | trie + grid DFS combo | O(NM×4ᴸ) with pruning |
| **Longest Word in Dictionary** | char | Map | `isEndOfWord` at every step | O(N·L) |
| **Autocomplete / Suggestions** | char or word | Map | `isEndOfWord` + DFS collect | O(P+K) |
| **Word-level phrase trie** | word (`split(" ")`) | `Map<String,·>` | `isEndOfPhrase` | O(W) where W = word count |
| **Delete** | char or word | Map | recursive prune, safe-to-remove check | O(L) |
| **Count-prefix** | char or word | Map | `prefixCount` bumped every step | O(L) insert, O(L) query |
| **Ranked Autocomplete (naive)** | word | Map | `frequency` at end + DFS + sort | O(P+K+K log K) |
| **Ranked Autocomplete (optimized)** | word | Map | min-heap top-K per node | O(L log K) insert, O(K log K) query |

---

## 12. Pinterest Senior SWE Priority Tracker

Based on real candidate-report data pulled from a third-party interview-question aggregator (paraphrased titles, not an official Pinterest source — treat confidence column accordingly).

| Problem | Template | Evidence at Pinterest | Confidence | Priority | Status |
|---|---|---|---|---|---|
| **Word-level Autocomplete (Search Suggestions)** | 4a | Directly referenced in Pinterest prep context | High | **Tier 1 — top priority** | ✅ Solved |
| **Ranked Autocomplete by Frequency (Part 2)** | 4d | Directly referenced in Pinterest prep context (Part 1 + Part 2 log-frequency question) | High | **Tier 1 — top priority** | ✅ Solved (Option A + B) |
| **Implement Trie (Prefix Tree)** | Base | Foundational — required to attempt any trie variant | High | Tier 1 | ✅ Solved |
| **Delete / Count-prefix operations** | 4b / 4c | Common trie extension, not confirmed Pinterest-specific | Unconfirmed | Tier 2 | ✅ Solved |
| **Word Search II** | Base + grid DFS | No direct evidence found yet | Unconfirmed | Tier 3 | Not started |
| **Add and Search Word (wildcard)** | Base + wildcard | No direct evidence found yet | Unconfirmed | Tier 3 | Not started |
| **Longest Word in Dictionary** | Base | No direct evidence found yet | Unconfirmed | Tier 4 | Not started |

> **Note:** confidence levels here are placeholders — update this table once you cross-reference against CodeJeet's verified Pinterest list or recruiter guides, the way your other pattern trackers are sourced.