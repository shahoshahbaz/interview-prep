# 🧩 The Subset Pattern — Thumb Rule

## 💡 When to Use the Subset Pattern
If a problem asks you to find **all combinations** of items (numbers, characters, or decisions) — where each item can either be included or excluded, or can be chosen in multiple ways, use the Subsets Pattern.

---

## 🧠 What It Really Means
You’re usually dealing with problems where:
- You’re building combinations, permutations, or power sets
- Each element is processed one at a time
- At each step, you make a choice that leads to new results:
  - ✅ include it
  - 🚫 exclude it
  - (or sometimes choose one of multiple options)

So every step expands your existing results into more — and that’s your BFS-like level expansion.

---

## 📊 Typical Structure
```java
List<List<Integer>> subsets = new ArrayList<>();
subsets.add(new ArrayList<>()); // start with empty set
for (int num : nums) {
    int n = subsets.size();
    for (int i = 0; i < n; i++) {
        List<Integer> newSubset = new ArrayList<>(subsets.get(i));
        newSubset.add(num);
        subsets.add(newSubset);
    }
}
return subsets;
```

**Pattern logic:**
For each element → expand all existing subsets → make new ones by adding the element.
That’s your core Subset pattern loop — you’ll see this everywhere (combinations, letter case permutations, balanced parentheses, etc).

---

## 🧭 Quick Recognition Cues (the “Pattern Smell”)
If you read a problem and notice any of these phrases — your “subset sense” should tingle 👀:

| Problem Phrase | Why it’s a hint |
|----------------|-----------------|
| “Find all possible combinations” | You’re building from smaller sets upward |
| “Generate all permutations/subsets” | Each item’s inclusion/exclusion matters |
| “Return all ways to…” | You need to explore all possibilities |
| “Each letter/number can be toggled, flipped, or chosen” | Decision per character — subset pattern |
| “All subsets of given items” | Literal power set generation |
| “Expand partial results level by level” | BFS flavor of Subset pattern |

---

## 🧠 Variations of the Subset Pattern
| Type | Example | Core Difference |
|------|---------|-----------------|
| Basic Subsets | [1,3,5] → [[], [1], [3], [5], [1,3], [1,5], [3,5], [1,3,5]] | Simple include/exclude per element |
| Subsets with Duplicates | [1,3,3] | Need to skip duplicates (by tracking start index ranges) |
| Letter Case Permutation | "a1b" → "a1b", "a1B", "A1b", "A1B" | Each char can branch into 2 cases |
| Balanced Parentheses | "()", "()()", "(())" | Not literally subsets, but same decision branching |
| Combinations / K-combinations | Choose k numbers out of n | Controlled subset generation |

---

## ⚙️ Mental Framework
Subset pattern = build results incrementally and iteratively, where each level adds a new element or decision to all existing results.
That’s why we often say:
> 🧩 Subset pattern problems are BFS on the decision space.

---

## ⚡ Common Mistakes
- Modifying the list you’re iterating over (forgetting to store `n = subsets.size()` first)
- Not handling duplicates (especially for arrays with repeated elements)
- Confusing with sliding window / backtracking — subset is about all possibilities, not optimal ones.

---

## 🧭 Summary (keep this mental checklist)
When you read a problem:
- ✅ “Do I need to explore all possible combinations?”
- ✅ “Can I make a decision for each element?”
- ✅ “Does every decision split into multiple paths?”
➡️ If yes — it’s the Subset pattern.