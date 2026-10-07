package com;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ConsoleTestHarness {

    public interface Solver<I, O> {
        O solve(I input);
    }

    // Generic test case with a message
    public static final class TestCase<I, O> {
        public final String name;    // e.g., "Knapsack classic"
        public final I input;
        public final O expected;

        public TestCase(String name, I input, O expected) {
            this.name = name;
            this.input = input;
            this.expected = expected;
        }
    }

    public static final class TestRunner {

        public static <I, O> void runSuite(
                String suiteName,
                List<TestCase<I, O>> cases,
                Solver<I, O> solver,
                Function<I, String> inputPretty,
                Function<O, String> outputPretty) {

            System.out.println("\n=== " + suiteName + " ===");
            int passed = 0;

            for (int i = 0; i < cases.size(); i++) {
                TestCase<I, O> tc = cases.get(i);
                O actual = solver.solve(tc.input);
                boolean ok = Objects.equals(actual, tc.expected);
//                public static final String ANSI_BOLD = "\033[1m";
//                public static final String ANSI_RESET = "\033[0m";
                System.out.printf(
                        "[%02d] %-24s | input: %s | output: \033[1m%s\033[0m | expected: %s %s%n",
                        i + 1,
                        tc.name,
                        inputPretty.apply(tc.input),
                        outputPretty.apply(actual),
                        outputPretty.apply(tc.expected),
                        ok ? "✓" : "✗"
                );
                if (ok) passed++;
            }

            System.out.printf("Summary: %d/%d passed%n", passed, cases.size());
        }
    }

}
