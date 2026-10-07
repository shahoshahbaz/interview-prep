# Matrix Traversal Patterns - Quick Action Guide

## 🚀 Quick Actions

### ⚡ I Want to...

#### See the patterns in action
```bash
mvn clean compile exec:java -Dexec.mainClass="com.ds.matrix.MatrixTraversalDemo"
```
Expected: Visual matrices showing all 6 patterns ✅

---

#### Use the patterns in my code
```java
import com.ds.matrix.Matrix;
List<int[]> order = Matrix.upperTriangleByIntervalLength(5);
Matrix.printTraversalOrder(order, 5);

// Use in loop
for (int[] cell : order) {
    dp[cell[0]][cell[1]] = compute();
}
```

---

#### Choose the right pattern for my problem
👉 Go to: **matrixCheatSheet.md** → **Use Cases & Problem Examples**

Quick guide:
- **Interval DP** → `upperTriangleByIntervalLength()` ⭐
- **Simple problem** → `upperTriangleTopToBottom()`
- **Need i+1 first** → `upperTriangleBottomToTop()`
- **Lower triangle** → See VISUAL_REFERENCE.md

---

#### Understand a specific pattern
1. Open **matrixCheatSheet.md**
2. Find your pattern section
3. See code example and when to use it
4. View **VISUAL_REFERENCE.md** for visual

---

#### Debug my traversal order
```java
Matrix.printTraversalOrder(myList, n);
```
Shows numbered matrix of how you're traversing ✅

---

#### Copy code to my project
1. Open **Matrix.java**
2. Copy entire class
3. Paste into your project in your package
4. Import and use the static methods

---

## 📋 Common Patterns Quick Reference

### Pattern 1: For Interval DP (USE THIS! ⭐)
```java
for (int len = 2; len <= n; len++) {
    for (int i = 0; i + len - 1 < n; i++) {
        int j = i + len - 1;
        // SOLVE dp[i][j]
        // All dependencies GUARANTEED ready!
    }
}
```
**Examples**: Matrix Chain, Burst Balloons, Palindrome Partition

---

### Pattern 2: Simple Top-to-Bottom
```java
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
        // SOLVE dp[i][j] (upper)
        // or dp[j][i] for lower
    }
}
```
**Examples**: Basic interval problems, simple DP

---

### Pattern 3: Bottom-to-Top (for i+1 dependency)
```java
for (int i = n - 1; i >= 0; i--) {
    for (int j = i + 1; j < n; j++) {
        // SOLVE dp[i][j]
        // Guarantees dp[i+1][j] ready
    }
}
```

---

## 🎯 Decision Tree (Fast!)

```
Q: Interval DP?
   YES → Use: upperTriangleByIntervalLength() ✅
   
Q: Need i+1 ready first?
   YES → Use: upperTriangleBottomToTop()
   NO → Use: upperTriangleTopToBottom()

Q: Lower triangle?
   LTR? → Use: lowerTriangleTopToBottomLeftToRight()
   RTL? → Use: lowerTriangleTopToBottomRightToLeft()
```

---

## 📚 File Guide

### Reading Order (Quick)
1. This file (2 min) ← You are here
2. README.md (5 min) - Overview
3. matrixCheatSheet.md (15 min) - Details
4. VISUAL_REFERENCE.md (10 min) - Pictures

### Total Time: ~30 minutes to mastery

---

## 🔧 Copy-Paste Ready Code

### Use Interval Length Pattern
```java
import com.ds.matrix.Matrix;
import java.util.List;

public class Solution {
    public int solve(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        
        // GET PATTERN
        List<int[]> order = Matrix.upperTriangleByIntervalLength(n);
        
        // ITERATE IN CORRECT ORDER
        for (int[] cell : order) {
            int i = cell[0], j = cell[1];
            
            // SOLVE - all dependencies ready!
            if (i + 1 == j) {
                dp[i][j] = baseCase(i, j);
            } else {
                int best = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    best = Math.min(best, dp[i][k] + dp[k+1][j]);
                }
                dp[i][j] = best;
            }
        }
        
        return dp[0][n-1];
    }
    
    private int baseCase(int i, int j) {
        // Your base case logic
        return 0;
    }
}
```

---

## ✅ Common Task Checklist

- [ ] Run demo: `mvn exec:java -Dexec.mainClass="com.ds.matrix.MatrixTraversalDemo"`
- [ ] Read matrixCheatSheet.md for your problem type
- [ ] View VISUAL_REFERENCE.md for visual confirmation
- [ ] Copy Matrix.java to your project
- [ ] Choose correct pattern using decision tree
- [ ] Implement using code-paste template
- [ ] Test with printTraversalOrder() if unsure

---

## 🆘 Troubleshooting

### Q: "Which pattern should I use?"
**A**: For ~90% of interval DP → `upperTriangleByIntervalLength()`

### Q: "How do I know if my order is right?"
**A**: Call `Matrix.printTraversalOrder(order, n)` - see numbered matrix

### Q: "Where do I copy the code from?"
**A**: 
1. Matrix.java for reusable methods
2. matrixCheatSheet.md for code patterns
3. MatrixTraversalDemo.java for examples

### Q: "What if my deps aren't ready?"
**A**: Switch to `upperTriangleByIntervalLength()` - it guarantees all deps

### Q: "Compilation error?"
**A**: Make sure you're importing: `import com.ds.matrix.Matrix;`

---

## 📊 Pattern at a Glance

| Name | Code | Deps | Use |
|------|------|------|-----|
| By Length | `for len=2; i=0..n-len; j=i+len-1` | ✅ ALL | ⭐ Interval |
| Top-Down | `for i=0; j=i+1..n` | ⚠️ Some | Simple |
| Bottom-Top | `for i=n-1; j=i+1..n` | ✅ i+1 | Specific |

---

## 🎓 Key Points to Remember

✅ **Use "By Length" when unsure** - it always works  
✅ **Verify order with printTraversalOrder()** - see visually  
✅ **Check if dependencies are ready** - before accessing  
✅ **Copy code templates** - from matrixCheatSheet.md  

❌ **Don't mix patterns** - in same DP table  
❌ **Don't ignore order** - it matters!  
❌ **Don't skip verification** - always check  

---

## 🚀 Ready to Go!

You now have:
- ✅ 3 traversal patterns (upper & lower)
- ✅ Reusable Matrix.java class
- ✅ Comprehensive cheatsheet
- ✅ Visual examples
- ✅ Running demo
- ✅ Code templates

**Next**: Pick your problem → Choose pattern → Implement!

---

## 📞 Quick Links

- **Pattern code** → Matrix.java
- **How to use** → matrixCheatSheet.md → Code Examples
- **See visuals** → VISUAL_REFERENCE.md
- **Quick start** → README.md
- **Navigate everything** → INDEX.md

---

**Last Updated**: March 2024  
**Status**: ✅ Ready to Use  
**Questions**: Check INDEX.md for navigation

