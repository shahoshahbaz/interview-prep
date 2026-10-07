package com.grokingcodeinterview.pattern37realInterviewProblems.apiRateLimiter2;

import java.util.HashMap;
import java.util.Map;

public class FixedWindowRateLimiter implements RateLimiterStrategy{
    // Max number of requests allowed per client in one window
    private final int limit;
    //Duration of the time window
    private final int windowSizeSeconds;
    // Stores state per client
    Map<String, ClientWindow> clientDataMap = new HashMap<>();

    public static class ClientWindow{
        long windowStart;
        int requestCount;

        public ClientWindow(long windowStart, int requestCount) {
            this.windowStart = windowStart;
            this.requestCount = requestCount;
        }
    }

    public FixedWindowRateLimiter(int limit, int windowSizeSeconds){
        this.limit = limit;
        this.windowSizeSeconds = windowSizeSeconds;
    }

    @Override
    public boolean allowRequest(String clientId) {
        long currentTime = System.currentTimeMillis();

        // check the clientId;
         ClientWindow  clientWindow = clientDataMap.get(clientId);
         if(clientWindow == null){
             clientDataMap.put(clientId, new ClientWindow(currentTime, 1));
             return true;
         }
         ///  if elapse time is bigger than window size then start new window
        if(currentTime - clientWindow.windowStart >= this.windowSizeSeconds){
            clientWindow.windowStart = currentTime;
            clientWindow.requestCount = 1;
            return true;
        }

        if(clientWindow.requestCount < limit){
            clientWindow.requestCount ++;
            return true;
        }

        return false;
    }

    @Override
    public int getRemainingRequest(String clientId) {
        long currentTime = System.currentTimeMillis();
        ClientWindow clientWindow = clientDataMap.get(clientId);
        if(clientWindow == null)
            return this.limit;
        if(currentTime - clientWindow.windowStart>= this.windowSizeSeconds){
            return this.limit;
        }
        return limit - clientWindow.requestCount;

    }

    @Override
    public void resetClientId(String clientId) {
        clientDataMap.remove(clientId);

    }
}
