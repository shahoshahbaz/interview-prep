package com.grokingcodeinterview.pattern37realInterviewProblems;

/*
/*
Problem: Log Analyzer

Build a Log Analyzer system that processes server log entries and provides insights.

Input

You are given log entries in the following format:

[2024-01-15 14:30:25] INFO /api/users GET 200 45ms user123
[2024-01-15 14:30:26] ERROR /api/orders POST 500 120ms user456
[2024-01-15 14:30:27] INFO /api/users GET 200 30ms user789

Format: [timestamp] level endpoint method status_code response_time user_id

Implement following methods
addLogEntry(String logLine): parse and store a log entry
getTopSlowestEndpoints(int n): return top N slowest endpoints by average response time // getTopSlowestEndPoints(int n)
getErrorRate(String endpoint): return error rate (4xx/5xx responses) for an endpoint as percentage // getErrorRate(String endpoint)
getUsersWithMostErrors(int n): return top N users with most failed requests getUsersWithMostError(int n)


 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogAnalyzerR1 {

    Map<String, EndpointStats> endPointStatsMap;

    Map<String , Integer> userErrorMap;


    public LogAnalyzerR1(){
        this.endPointStatsMap = new HashMap<>();
        this.userErrorMap = new HashMap<>();
    }

    public static class Parser{
        String timestamp;
        String level;
        String endpoint;
        String method;
        int statusCode;
        long responseTime;
        String uerId;

        public Parser(String timestamp, String level, String endpoint, String method, int statusCode, long responseTime, String uerId) {
            this.timestamp = timestamp;
            this.level = level;
            this.endpoint = endpoint;
            this.method = method;
            this.statusCode = statusCode;
            this.responseTime = responseTime;
            this.uerId = uerId;
        }

    }

    public Parser parseLog(String log){

        // [timestamp] level endpoint method status_code response_time user_id
        // [2024-01-15 14:30:26] ERROR /api/orders POST 500 120ms user456

        log = log.trim();
        int indexOfClosedBracket = log.indexOf("]");
        String timeStamp = log.substring(1, indexOfClosedBracket);
        String remaining = log.substring(indexOfClosedBracket +1);
        String[] parts = remaining.split("\\s+");

        String level = parts[0];
        String endPoint = parts[1];
        String method = parts[2];
        int statusCode = Integer.parseInt(parts[3]);
        long responseTime = Long.parseLong(parts[4].replace("ms","").trim());
        String userId = parts[5];

        return new Parser(timeStamp, level, endPoint, method, statusCode, responseTime, userId);


    }

    public static class EndpointStats{
        int totalRequest;
        int failedRequest;
        int totalResponseTime;

        public EndpointStats(){}

        public EndpointStats(int totalRequest, int failedRequest, int totalResponseTime) {
            this.totalRequest = totalRequest;
            this.failedRequest = failedRequest;
            this.totalResponseTime = totalResponseTime;
        }
    }

    public static class ErrorUserCount{
        String userId;
        int errorCount;

        public ErrorUserCount(String userId, int errorCount) {
            this.userId = userId;
            this.errorCount = errorCount;
        }
    }



    public void addLogEntry(String log){

        Parser parser = parseLog(log);
        String endpoint = parser.endpoint;
        String userId = parser.endpoint;

        EndpointStats stats = endPointStatsMap.getOrDefault(endpoint, new EndpointStats());

        stats.totalRequest ++;
        stats.totalResponseTime += parser.responseTime;
        int statusCode = parser.statusCode;
        if (statusCode >= 400 && statusCode< 600){
            stats.failedRequest ++;
            userErrorMap.put(userId, userErrorMap.getOrDefault(userId, 0) +1 );
        }

        endPointStatsMap.put(endpoint, stats);



    }
    // top N slowest endpoints by average response time
    public List<AverageResponseEndpoint> getTopSlowestEndPont(int n){
         List<AverageResponseEndpoint> result = new ArrayList<>();
        for (Map.Entry<String, EndpointStats> entry: endPointStatsMap.entrySet()){
            String endpoint = entry.getKey();
            EndpointStats stats = entry.getValue();
             double avg =(double) stats.totalRequest/ stats.totalResponseTime;
             result.add(new AverageResponseEndpoint(endpoint, avg ));


        }

        result.sort((a, b) ->Double.compare(b.avgResponseTime, a.avgResponseTime));

        return result.subList(0, Math.min(n, result.size())    );

    }

    // return error rate (4xx/5xx responses) for an endpoint as percentage
    public double getErrorRate(String endPoint){

        EndpointStats stats = endPointStatsMap.get(endPoint);
        if(stats == null  || stats.totalRequest == 0 )
            return 0.0;
        return (double) stats.failedRequest/ stats.totalRequest * 100 ;
    }
    // return top N users with most failed requests
    public List<ErrorUserCount> getUerWithMostErrors(int n){

        List<ErrorUserCount> result = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: userErrorMap.entrySet()){{
            String userId = entry.getKey();
            int count = entry.getValue();

            result.add(new ErrorUserCount(userId, count));

        }}

        result.sort((a, b) -> Integer.compare(b.errorCount , a.errorCount));

        return result.subList(0, Math.min(n, result.size() ));


    }


    public static class AverageResponseEndpoint {
        String endpoint;
        double avgResponseTime;

        public AverageResponseEndpoint(String endpoint, double avgResponseTime) {
            this.endpoint = endpoint;
            this.avgResponseTime = avgResponseTime;
        }
    }

    public static void main(String[] args) {

    }
}
