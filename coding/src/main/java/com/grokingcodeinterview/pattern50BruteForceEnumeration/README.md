# Brute Force / Exhaustive Enumeration Pattern

Problems in this package try every valid candidate from a small, well-bounded
search space and score each one directly — there's no recursive decision
tree with choose/explore/un-choose (that's Backtracking), and no evolving
state stepped forward round-by-round until a stop condition (that's
Simulation). Just enumerate all candidates and keep the best.

Recognize it when:
- The number of candidates is small and provably bounded by the constraints
  (e.g., `expression.length() <= 10`).
- Each candidate can be scored independently, with no dependency on
  previously tried candidates.
- No smarter (e.g., DP, greedy, two-pointer) insight reduces the complexity
  class — trying everything IS the intended solution.

## Problems
- P01. Minimize Result by Adding Parentheses to Expression
