package com.grokingcodeinterview.pattern37realInterviewProblems;

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

constraints:
the log entries can be malformed, your code should handle such cases gracefully without crashing. You can choose to ignore malformed entries or log an error message.



 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1. top N slowest endPoint by average response time(
 *   map: key: endpoint, average response time =(tatotalRequstbased Kye/totaolresponseTimeKey)
 *  2. top error rate: map(endpont, failedRequest/ totalRequst per endpont)
 *  userId, numberofFAiled request
    so I need a EndPointStats class
  endPointStats{
    int totalRequest;
    int failedRequest;
    int totalResponseTime;
  }
 user ErrorStats{
    String userId;
    int errorCount;
  }

 userErrorStats{
    String userId;
    int errorCount;
  }

 */

public class LogAnalyzer {

    private Map<String, EndpointStats> endpointStatsMap;
    private Map<String, Integer> userErrorCount;

    public LogAnalyzer(){
        endpointStatsMap = new HashMap<>();
        userErrorCount = new HashMap<>();
    }


    public static class ParsedLogEntry{
        //[timestamp] level endpoint method status_code response_time user_id
        String timestamp;
        String level;
        String endpoint;
        String method;
        int statusCode;
        long responseTime;
        String userId;



        public ParsedLogEntry (String timestamp, String level, String endpoint, String method, int statusCode, long responseTime, String userId){
            this.timestamp = timestamp;
            this.level = level;
            this.endpoint = endpoint;
            this.method = method;
            this.statusCode = statusCode;
            this.responseTime = responseTime;
            this.userId = userId;


        }

    }
    public static class EndpointStats{
        int totalRequest;
        int failedRequest;
        long totalResponseTime;
    }


    public static class EndpointAverage {
        String endPoint;
        double averageResponseTime;

        public EndpointAverage(String endPoint, double averageResponseTime) {
            this.endPoint = endPoint;
            this.averageResponseTime = averageResponseTime;
        }
    }
    public static class UserErrorStats {
        String userId;
        int errorCount;

        public UserErrorStats(String userId, int errorCount) {
            this.userId = userId;
            this.errorCount = errorCount;
        }
    }

    public void addLogEntry(String log){

        // parse the log entry
        ParsedLogEntry parse = parseLog(log);

        String endpoint = parse.endpoint;
        int statusCode = parse.statusCode;
        // update endpoint stats,if the endpoint is not exist, create new one
        EndpointStats stats = endpointStatsMap.getOrDefault(endpoint, new EndpointStats());
        stats.totalRequest ++;
        stats.totalResponseTime += parse.responseTime;
        // update the user error count if the status code is 4xx or 5xx
        if( statusCode>= 400  && statusCode<600 ){
            stats.failedRequest ++;
            userErrorCount.put(parse.userId, userErrorCount.getOrDefault(parse.userId, 0) +1);

        }

        endpointStatsMap. put(endpoint, stats);

    }

    public ParsedLogEntry parseLog(String log){
        log = log.trim();

        int closeBracketIndex = log.indexOf("]");
        String timeStamp = log.substring(1, closeBracketIndex);
        String restOfLog = log.substring(closeBracketIndex +1);
        String[] parts = restOfLog.trim().split(" ");
//
//        [2024-01-15 14:30:27] INFO /api/users GET 200 30ms user789
//        Format: [timestamp] level endpoint method status_code response_time user_id
        String level = parts[0];
        String endpoint = parts[1];
        String method = parts[2];
        int stausCode = Integer.parseInt(parts[3]);
        long responseTime = Long.parseLong(parts[4].replace("ms", ""));
        String userId = parts[5];
        return new ParsedLogEntry(timeStamp, level, endpoint, method, stausCode, responseTime, userId);
    }

    public List<EndpointAverage> getTopSlowestEndPoints(int n){

        List<EndpointAverage> result = new ArrayList<>();
        for(Map.Entry<String, EndpointStats> entry: endpointStatsMap.entrySet()){
            String endPoint = entry.getKey();
            EndpointStats stats = entry.getValue();

            double avg = (double) stats.totalResponseTime / stats. totalRequest;

            result.add(new EndpointAverage(endPoint , avg));
        }

        result.sort((a, b) -> Double.compare(b.averageResponseTime, a.averageResponseTime));

        return result.subList(0, Math.min(n,result.size()));

    }

    public double getErrorRate(String endpoint){
         EndpointStats stats = endpointStatsMap.get(endpoint);
         if (stats == null  || stats.totalRequest ==0)
             return 0.0;
         return (double) stats.failedRequest/ stats.totalRequest;

    }

    public List<UserErrorStats> getUserWithMostErrors(int n){
        List<UserErrorStats> result = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: userErrorCount.entrySet()){
            String userId = entry.getKey();
            int errorCount = entry.getValue();
            result.add(new UserErrorStats(userId, errorCount));



        }

        result.sort((a, b) -> b.errorCount - a.errorCount);

        return result.subList(0, Math.min(n, result.size()));

    }

}
