package com.ds.matrix;

import java.util.List;

/**
 * Demonstration of all Matrix traversal patterns
 * Shows how each pattern orders the elements in upper and lower triangles
 */
public class MatrixTraversalDemo {

    public static void main(String[] args) {
        int n = 5;
        
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("MATRIX TRAVERSAL PATTERNS - DEMONSTRATION");
        System.out.println("════════════════════════════════════════════════════════════\n");
        
        // ======================== UPPER TRIANGLE ========================
        System.out.println("📊 UPPER TRIANGLE (i < j)");
        System.out.println("════════════════════════════════════════════════════════════\n");
        
        System.out.println("1️⃣  Top-to-Bottom, Left-to-Right (MOST COMMON)");
        System.out.println("────────────────────────────────────────────────");
        List<int[]> upper1 = Matrix.upperTriangleTopToBottom(n);
        Matrix.printTraversalOrder(upper1, n);
        
        System.out.println("\n2️⃣  Bottom-to-Top, Left-to-Right");
        System.out.println("────────────────────────────────────────────────");
        List<int[]> upper2 = Matrix.upperTriangleBottomToTop(n);
        Matrix.printTraversalOrder(upper2, n);
        
        System.out.println("\n3️⃣  By Interval Length (BEST FOR INTERVAL DP) ⭐");
        System.out.println("────────────────────────────────────────────────");
        List<int[]> upper3 = Matrix.upperTriangleByIntervalLength(n);
        Matrix.printTraversalOrder(upper3, n);
        
        // ======================== LOWER TRIANGLE ========================
        System.out.println("\n\n📊 LOWER TRIANGLE (i > j)");
        System.out.println("════════════════════════════════════════════════════════════\n");
        
        System.out.println("1️⃣  Top-to-Bottom, Left-to-Right");
        System.out.println("────────────────────────────────────────────────");
        List<int[]> lower1 = Matrix.lowerTriangleTopToBottomLeftToRight(n);
        Matrix.printTraversalOrder(lower1, n);
        
        System.out.println("\n2️⃣  Top-to-Bottom, Right-to-Left");
        System.out.println("────────────────────────────────────────────────");
        List<int[]> lower2 = Matrix.lowerTriangleTopToBottomRightToLeft(n);
        Matrix.printTraversalOrder(lower2, n);
        
        System.out.println("\n3️⃣  Bottom-to-Top");
        System.out.println("────────────────────────────────────────────────");
        List<int[]> lower3 = Matrix.lowerTriangleBottomToTop(n);
        Matrix.printTraversalOrder(lower3, n);

        System.out.println("\n4️⃣   8‑direction neighbors (including diagonals)");
        System.out.println("────────────────────────────────────────────────");
        List<int[]> lower4 = Matrix.get8DirectionNeighbors(n);
        Matrix.printTraversalOrder(lower4, n);
        
        // ======================== SUMMARY ========================
        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("SUMMARY");
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("\n✅ Upper Triangle (i < j): " + upper1.size() + " cells");
        System.out.println("✅ Lower Triangle (i > j): " + lower1.size() + " cells");
        System.out.println("\n⭐ RECOMMENDED for Interval DP:");
        System.out.println("   - Use: upperTriangleByIntervalLength()");
        System.out.println("   - Guarantees dependencies ready before use");
        System.out.println("   - Best for: Matrix Chain Multiplication, Burst Balloons, etc.");
    }
}

