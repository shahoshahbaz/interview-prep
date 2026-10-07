package com.grokingcodeinterview.pattern37realInterviewProblems.apiRateLimiter2;

public class APIRateLimiter {
    RateLimiterStrategy strategy;

    public APIRateLimiter(String strategyType, int limit, int windowSizeSeconds){
        switch (strategyType.toUpperCase()){
            case "FIXED_WINDOW":
                this.strategy = null;
                break;

            case "SLIDING_WINDOW":
                this.strategy = null;
                break;
            case "TOKEN_BUCKET":
                this.strategy = null;
                break;
            default:
                throw new IllegalArgumentException("Invalid startegy: " + strategyType);
        }
    }

    public boolean allowRequest(String clientId){
        return false;
    }
    public int getRemainingRequest(String clientId){
        return 0;
    }
    public void resetClientId(String clientId){

    }
}
