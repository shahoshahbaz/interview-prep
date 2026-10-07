package com.ds.matrix;

public class MatrixGridNeighbors {

    // Abbreviations for directions
    private static final String[] NAMES = {
            "U",  "D",  "L",  "R",  "LT", "RT", "LB", "RB"
    };

    // Direction vectors: U, D, L, R, LT, RT, LB, RB
    private static final int[][] DIRS = {
            {-1,  0},  // U
            { 1,  0},  // D
            { 0, -1},  // L
            { 0,  1},  // R
            {-1, -1},  // LT
            {-1,  1},  // RT
            { 1, -1},  // LB
            { 1,  1}   // RB
    };

    public static void main(String[] args) {
        int n = 5, m = 6;           // matrix size (rows=n, cols=m)
        int i = 2, j = 3;           // target cell

        // Demo: print neighbor list with T/F
        printNeighborTruths(n, m, i, j);

        // Optional: also show a 3x3 ASCII view around (i, j)
        System.out.println();
        show3x3AsciiView(n, m, i, j);
    }

    /**
     * Prints a list of neighbors around (i, j) with abbreviations and T/F
     * indicating whether each neighbor is within bounds.
     */
    public static void printNeighborTruths(int n, int m, int i, int j) {
        System.out.printf("Matrix size: %dx%d, cell: (i=%d, j=%d)%n", n, m, i, j);
        for (int k = 0; k < DIRS.length; k++) {
            int ni = i + DIRS[k][0];
            int nj = j + DIRS[k][1];
            boolean inBounds = inBounds(n, m, ni, nj);
            System.out.printf("(i%+d, j%+d) %-2s : %s   -> (%d, %d)%n",
                    DIRS[k][0], DIRS[k][1], NAMES[k],
                    inBounds ? "T" : "F", ni, nj);
        }
        // Also include the center itself if you want
        System.out.printf("(i+0, j+0) C  : %s   -> (%d, %d)%n",
                inBounds(n, m, i, j) ? "T" : "F", i, j);
    }

    /**
     * Renders a compact 3x3 ASCII view around (i, j) with abbreviations.
     * 'C' is the center. Displays 'T' if inside bounds, 'X' if out of bounds.
     * Layout:
     *   LT  U  RT
     *    L  C   R
     *   LB  D  RB
     */
    public static void show3x3AsciiView(int n, int m, int i, int j) {
        String LT = mark(n, m, i - 1, j - 1);
        String  U = mark(n, m, i - 1, j);
        String RT = mark(n, m, i - 1, j + 1);
        String  L = mark(n, m, i, j - 1);
        String  C = mark(n, m, i, j);
        String  R = mark(n, m, i, j + 1);
        String LB = mark(n, m, i + 1, j - 1);
        String  D = mark(n, m, i + 1, j);
        String RB = mark(n, m, i + 1, j + 1);

        System.out.println("3x3 view around (i,j): T=in bounds, X=out of bounds");
        System.out.printf("   LT:%s   U:%s   RT:%s%n", LT, U, RT);
        System.out.printf("    L:%s   C:%s    R:%s%n", L, C, R);
        System.out.printf("   LB:%s   D:%s   RB:%s%n", LB, D, RB);
    }

    // Helper: within-bounds check
    private static boolean inBounds(int n, int m, int i, int j) {
        return i >= 0 && i < n && j >= 0 && j < m;
    }

    // Helper: returns "T" if (i,j) in bounds else "X"
    private static String mark(int n, int m, int i, int j) {
        return inBounds(n, m, i, j) ? "T" : "X";
    }

}
