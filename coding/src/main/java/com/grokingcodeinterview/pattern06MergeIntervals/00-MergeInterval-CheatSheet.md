# Merge Intervals — Cheat Sheet

---

## Trigger

> "If intervals overlap, they must touch in sorted order."

Keywords: *merge, overlapping, calendar, meeting rooms, ranges, time windows*

---

## Core Idea

**Sort → Sweep → Merge**

Sort by start time. Keep a running interval. If next overlaps → extend end. If not → store and move on.

---

## Overlap Condition

| Intervals touch? | Condition |
|---|---|
| Yes `[1,3]`+`[3,5]` | `currStart <= end` |
| No | `currStart < end` |

---

## Template — Basic Merge

```java
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

List<int[]> result = new ArrayList<>();
int start = intervals[0][0], end = intervals[0][1];

for (int i = 1; i < intervals.length; i++) {
    if (intervals[i][0] <= end) {
        end = Math.max(end, intervals[i][1]);      // merge
    } else {
        result.add(new int[]{start, end});          // store
        start = intervals[i][0];
        end = intervals[i][1];
    }
}
result.add(new int[]{start, end});                  // ← never forget this
```

---

## Template — Heap Variant (CPU Load / Meeting Rooms II)

Use when you need to track **active overlapping intervals** at any moment.

```java
Arrays.sort(jobs, (a, b) -> a.start - b.start);

PriorityQueue<Job> heap = new PriorityQueue<>((a, b) -> a.end - b.end); // min-heap by end time
int maxLoad = 0;

for (Job job : jobs) {
    // remove all jobs that ended before current starts
    while (!heap.isEmpty() && heap.peek().end <= job.start)
        heap.poll();

    heap.offer(job);

    // sum current active load
    int currentLoad = 0;
    for (Job j : heap) currentLoad += j.cpuLoad;
    maxLoad = Math.max(maxLoad, currentLoad);
}
```

---

## Traps

- ❌ Forgot to sort → everything breaks
- ❌ Updating `start` during merge → only update `end`
- ❌ Missing final `result.add(...)` after loop
- ❌ `<` vs `<=` on overlap condition
- ❌ Heap: removing by `end < start` instead of `end <= start`

---

## Complexity

| | Time | Space |
|---|---|---|
| Basic merge | O(n log n) | O(n) |
| Heap variant | O(n log n) | O(n) |

---

## Canonical Problems

| Problem | Key Insight |
|---|---|
| Merge Intervals (LC 56) | Sort + greedy merge |
| Insert Interval (LC 57) | Merge only where overlap exists |
| Meeting Rooms II | Count max overlapping → min-heap by end |
| Maximum CPU Load | Sum active loads → min-heap by end |
| Interval Intersection | Two pointers, advance whoever ends first |

---

## Checklist

- [ ] Sorted by start time?
- [ ] Overlap condition correct (`<` vs `<=`)?
- [ ] Only updating `end` during merge?
- [ ] Added last interval after loop?
- [ ] Heap: ordered by **end time**, not start?