package com.grokingcodeinterview.pattern06MergeIntervals;

public class Interval {
    public int start;
    public int end;

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return     "[" + start +", " + end +']';
    }
}
