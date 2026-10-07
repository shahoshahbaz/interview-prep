package com.grokingcodeinterview.pattern37realInterviewProblems;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {


    public enum StrategyType {
        FIXED_WINDOW,
        SLIDING_WINDOW,
        TOKEN_BUCKET
    }

    private  final StrategyType strategy;
    private final int limit;
    private final long windowMills;

    // private final double refillRatePerMs; // TOKEN_BUCKET only

    // private final ConcurrentHashMap<String, ClientState> clients = new ConcurrentHashMap<>(); // FIXED_WINDOW / TOKEN_BUCKET only

    private final ConcurrentHashMap<String, List<Long>> requestLog;

    public RateLimiter(StrategyType strategy, int limit, int windowSeconds){
        this.strategy = strategy;
        this.limit = limit;
        this.windowMills = windowSeconds * 1000;
        this.requestLog = new ConcurrentHashMap<>();
    }
    // ---------- FIXED WINDOW ----------
    // private boolean allowFixedWindow(String clientId) {
    //   // not attempting this one today
    //   return false;
    // }

    // ---------- SLIDING WINDOW LOG ----------
    private boolean allowSlidingWindow(String clientId) {


        // TODO:
        // 1. get current time
        Long now = System.currentTimeMillis();

        // 2. get or create this client's list of timestamps
        List<Long> timestamps = requestLog.computeIfAbsent(clientId, k-> new ArrayList<>());
        synchronized (timestamps){ // 3. lock on the list
        // 4. evict timestamps older than (now - windowMillis)
        timestamps.removeIf(time -> time< now - this.windowMills);
        // 5. if remaining size < limit -> add now, return true
        if(timestamps.size()< this.limit){
            timestamps.add(now);
            return true;
         }
        return false;
    }

    // ---------- TOKEN BUCKET ----------
    // private boolean allowTokenBucket(String clientId) {
    //   // not attempting this one today
    //   return false;
    // }
}

public static void main(String[] args) throws InterruptedException {
    RateLimiter limiter = new RateLimiter(StrategyType.SLIDING_WINDOW, 3, 10);

    for (int i = 0; i < 5; i++) {
        System.out.println(limiter.allowSlidingWindow("client_1"));
    }
}



}

