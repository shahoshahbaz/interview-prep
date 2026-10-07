package com.grokingcodeinterview.pattern37realInterviewProblems.apiRateLimiter2;

public interface RateLimiterStrategy {
    boolean allowRequest(String clientId);
    int getRemainingRequest(String clientId);
    void resetClientId(String clientId);
}
