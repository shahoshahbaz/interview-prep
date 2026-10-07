package com.grokingcodeinterview.pattern37realInterviewProblems;

public class ResilientApiClient {
    private  final int maxRetries;
    private final long baseDelayMillis;


    private enum CircuitBreakerState{
        CLOSED,
        OPEN,
        HALF_OPEN
    }

    private CircuitBreakerState circuitState = CircuitBreakerState.CLOSED;
    private int consecutiveFailures = 0;
    private final int failureThreshold;
    private final long cooldwonMillis;
    private long circuitOpendAt =0;
    public ResilientApiClient(int maxRetries, long baseDelayMillis,
                              int failureThreshold, long cooldownMillis){
        this.maxRetries = maxRetries;
        this.baseDelayMillis = baseDelayMillis;
        this.failureThreshold = failureThreshold;
        this.cooldwonMillis = cooldownMillis;
    }
    // Simulates the real HTTP call — you control its behavior for testing
    private String callRawApi(String endpoint) throws ApiException {
        // stub - we'll make this fail on demand to test
        return "success";
    }

    public String fetchData(String endpoint) throws ApiException {
        ApiException lastException = null;
            for (int i =0; i< maxRetries; i++){
                try{
                    return callRawApi(endpoint);
                }catch (ApiException e){
                    lastException = e;

                    System.out.println("Attempt " + (i + 1) + " failed: " + e.getMessage());
                    if(e.statusCode == 400){
                        throw  e;
                    }

                    if (i< maxRetries -1){
                    //
                        long delay ;
                        if(e.statusCode == 429){
                            delay =  (long) (baseDelayMillis  * Math.pow(2, i)) *5;
                        }else {
                            delay  = (long) (baseDelayMillis  * Math.pow(2, i));
                        }

                        System.out.println("Backing off for " + delay + "ms before retrying...");
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException interruptedEx) {
                            Thread.currentThread().interrupt();;
                            throw new ApiException(500, "Retry interrupted");

                        }
                    }
                }

            }


        throw   lastException;
    }

    class ApiException extends  Exception{
        final int statusCode;
        public ApiException(int statusCode, String messgae){
            super(messgae);
            this.statusCode = statusCode;
        }
    }
}
