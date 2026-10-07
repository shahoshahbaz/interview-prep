package com.grokingcodeinterview.pattern37realInterviewProblems;

import java.util.*;

import static com.Utility.makeItBold;

public class QuestionsR1 {

    /*
  Given an integer array nums, find the subarray with the largest sum and return that sum.
  example:  Input:  [-2, 1, -3, 4, -1, 2, 1, -5, 4] Output: 6
  example:  Input:  [1] Output: 1
  example:  Input:  [5,4,-1,7,8] Output: 23
  Constraints:
    1 <= nums.length <= 10^5
    -10^4 <= nums[i] <= 10^4

 */

    /**
     * Input:  [-3, 2, 4, -1, 5, -8, 3]
     * Index:    0  1  2   3  4   5  6
     * currSum = -3, maxSum = -3
     * i =1 , nums[1] = 2,   currSum = 2, maxSum = 2
     * i = 2, nums[2] = 4,   currSum = 6, maxSum= 6
     * i = 3, nums[3] = -1,  currSum = 5, maxSum = 6
     * i = 4, nums[4] = 5,   currSum = 10, maxSum = 10
     * i = 5, nums[5] = -8,  currSum = 2, maxSum = 10
     * i = 6, nums[6] = 3,   currSum = 5, maxSum= 10
     */

    public static int maxSubArray(int[] nums){

        int currSum = nums[0];
        int maxSum = nums[0];

        for (int i =1; i< nums.length; i++){
            currSum = Math.max(nums[i] , nums[i] + currSum);
            maxSum = Math.max(currSum, maxSum);
        }
        return maxSum;
    }

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
 getTopSlowestEndpoints(int n): return top N slowest endpoints by average response time
 getErrorRate(String endpoint): return error rate (4xx/5xx responses) for an endpoint as percentage
 getUsersWithMostErrors(int n): return top N users with most failed requests

  */
    public static  class LogAnalyzer {
        private Map<String, EndPointStats> endPointStats  ;

        private Map<String,  Integer> userErrorCountMap;

        public LogAnalyzer(){
            // intiaite some class variables
            endPointStats = new HashMap<>();
            userErrorCountMap = new HashMap<>();

        }
        // : parse and store a log entry
        public void addLogEntry(String logLine) {

            LogParser logParser = parsedLog(logLine);

            EndPointStats stats = endPointStats.getOrDefault(logParser.endPoint, new EndPointStats());
            stats.totalRequest++;
            stats.totalResponseTime += logParser.responseTime;

            int responseCode = logParser.responseCode;
            if (responseCode % 4 == 0 || responseCode % 5 == 0) {
                stats.failedRequest++;
                userErrorCountMap.put(logParser.user, userErrorCountMap.getOrDefault(logParser.user, 0) + 1);
            }

            endPointStats.put(logParser.endPoint, stats);


                }

        private LogParser parsedLog(String logLine){
            int closeBracketIndex = logLine.indexOf("]");
            String remainingLog = logLine.substring(closeBracketIndex+ 1);
            String[] parts= remainingLog.split(" ");

            // Format: [timestamp] level endpoint method status_code response_time user_id
            return new LogParser (parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);

        }

        public List<AverageResponseTime> getTopSlowestEndpoints(int n){
            //return top N slowest endpoints by average response time
            List<AverageResponseTime> result = new ArrayList<>();
            for(Map.Entry<String, EndPointStats> entry: endPointStats.entrySet()){
                String endPoint = entry.getKey();
                EndPointStats stats = entry.getValue();

                double aveResponseTime = (double) stats.totalResponseTime / stats.totalRequest;
                result.add(new AverageResponseTime(endPoint, aveResponseTime));
            }

            result.sort((a, b) -> Double.compare(b.averageResponseTime , a.averageResponseTime));

            return result.subList(0, Math.min(n+1, result.size()));

        }
            //  return top N users with most failed requests
        public List<UserErrorStats> getUsersWithMostErrors(int n){

            List<UserErrorStats> result = new ArrayList<>();
            for(Map.Entry<String , Integer> entry: userErrorCountMap.entrySet() ){

                result.add(new UserErrorStats(entry.getKey(), entry.getValue()) );

            }
            result.sort((a, b) -> Integer.compare(b.errorCount, a.errorCount));
            return result.subList( 0, Math.min(n +1, result.size()));
        }



        private  static class AverageResponseTime{
            String endPoint;
            double averageResponseTime;



            public AverageResponseTime(String endPoint, double averageResponseTime){
                this.endPoint = endPoint;
                this.averageResponseTime = averageResponseTime;
            }
        }

        public static class LogParser {
            // [2024-01-15 14:30:27] INFO /api/users GET 200 30ms user789

            String level;
            String endPoint;
            String method;
            int responseTime;
            int responseCode;
            String user;

            public LogParser(String level, String endPoint, String method,  String responseCode, String responseTime, String user) {

                // Format: [timestamp] level endpoint method status_code response_time user_id
                this.level = level;
                this.endPoint = endPoint;
                this.method = method;
                this.responseCode = Integer.parseInt(responseCode);
                this.responseTime = Integer.parseInt(responseTime.replace("ms", ""));
                 this.user = user;
            }



        }

        public static class EndPointStats{
            int totalRequest;
            int failedRequest;
            int totalResponseTime;
        }

        public static class UserErrorStats{
            String userIde;
            int errorCount;

            public UserErrorStats(String userIde, int errorCount) {
                this.userIde = userIde;
                this.errorCount = errorCount;
            }
        }


    }
    /*
problem statement
LRU Question:
Implement a Cache data structure using a Least Recently Used algorithm.
 A Cache stores as many elements as possible, and when full, discards the least recently used items first.
  Please implement a constructor that takes the desired capacity as well as the put(K k, V v), and get(K k) methods.
 */
    public static class LRUCache <K, V>{


        HashMap<K,Node> cache ;
        int  capacity;
        private class Node {
            K key;
            V value;
            Node next;
            Node prev;

            public Node(K key, V value){
                this.key = key;
                this.value = value;
            }
        }

        Node head;
        Node tail;




        public LRUCache( int capacity){
            this.capacity = capacity;
            cache = new HashMap<>();
            this.head = new Node(null, null);
            this. tail = new Node(null, null);
            head.next = tail;
            tail.prev = head;

        }

        public V get(K key){
            Node node = cache.get(key);
            if (node == null)
                return null;

            // move it to front
            moveToFront(node);
            return node.value;
        }

        public void put(K key, V value){
            // first check if they key exsit
            // if it is we update the value and move To front
            // if not , then we check the capacity
            // if cache size is bigger than hashMap then we have to evict the lRU

            // if not we do insert at the add the fist of list.


            Node node = cache.get(key);
            // if the node exist we update the cache and move itFront
            if(node != null){
                node.value = value;

                moveToFront(node);
                return;

            }

            if (this.capacity == cache.size()){
                Node lru = tail.prev;
                remove(lru);
                cache.remove(lru.key);
            }

            Node newNode = new Node (key, value);
            cache.put(key, newNode);
            addToFront(newNode);

        }

        // we need some operation
        // when the get is called, the element should go to the fron of list
        // when we put is called and if cache is full it should do evict LRU node : rmove

        // when we add the new node, we addTofront if exist and we update the value:addToFront
        public void addToFront(Node newNode){
            // how
//            head <-> node
            //<- newNode ->

            newNode.next = head.next;
            newNode.prev = head;
            head.next.prev = newNode;
            head.next = newNode;
        }

        public void moveToFront(Node node){
            remove(node);
            addToFront(node);

        }

        public void remove( Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;

        }

        public void printCacheOrder(){
         Node curr = head.next;
         StringBuilder sb = new StringBuilder();
         while(curr!= tail){
             sb.append("[").append(curr.key).append("=").
                     append(curr.value).append("] ");
             curr = curr.next;

         }
         System.out.print(makeItBold(sb.toString() +"\t"));
     }

 }
    /*
    Develop a class that implements a Set data structure.
    A Set is a collection containing no duplicate elements and is primarily used to test whether a member is contained within,
    rather than retrieving a particular member.
    Please implement the
    add(element), remove(element), contains(element),
    and  _len_ methods using arrays -- use of dict is not allowed
 */
    public class Set{
        int[] arr;
        int  size;
        private static final int  INITIAL_CAPACITY=10;

        public Set(){
            this.arr = new int[INITIAL_CAPACITY];
            this.size =0;
        }

        public boolean add(int key){
            if(contains(key)){
                return false;
            }
            if (size == arr.length){
                int[] newArray = new int[ 2 * INITIAL_CAPACITY];

                for (int i =0; i<size; i++){
                    newArray[i] = arr[i];
                    arr = newArray;
                }

            }
            size++;
            arr[size] = key;
            return true;
        }

        public boolean remove(int key){

            for (int i =0; i<size; i++){
                if(this.arr[i] == key){
                    for (int j =i; j< size -1; j++){
                        arr[j] = arr[i+ 1];
                    }
                    size--;
                    return true;
                }
            }
            return false;
        }



        public boolean contains(int key){

            for(int k: arr){
                if(key == k)
                    return true;
            }
            return false;
        }

    }

    public static void main(String[] args) {


        System.out.println("=================================================================");
        System.out.println("LRU Cache :");
        System.out.println("=================================================================");
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        System.out.print("Adding 3 items: (capacity is 3): ");

        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.printCacheOrder(); // [3=C] [2=B] [1=A]
        System.out.print("Expected result: [3=C] [2=B] [1=A] (most recently used is 3, least recently used is 1)" +"\n");


        System.out.print("Accessing key 1 (value A), so it becomes most recently used: ");
        cache.get(1); // access 1, so it becomes most recently used
        cache.printCacheOrder(); // [1=A] [3=C] [2=B]
        System.out.print("Expected result: [1=A] [3=C] [2=B] (most recently used is 1, least recently used is 2)"+"\n");

        System.out.print("Adding key 4 with value D, which should evict least recently used key 2: ");
        cache.put(4, "D"); // evicts key 2
        cache.printCacheOrder(); // [4=D] [1=A] [3=C]
        System.out.print("Expected result: [4=D] [1=A] [3=C] (most recently used is 4, least recently used is 3)"+"\n");

        System.out.print("Accessing key 3 (value C), so it becomes most recently used: ");
        cache.get(3);
        cache.printCacheOrder(); // [3=C] [4=D] [1=A]
        System.out.println("Expected result: [3=C] [4=D] [1=A] (most recently used is 3, least recently used is 1)");


        System.out.println("=================================================================");
        System.out.println("Set Implementation :");
        System.out.println("=================================================================");
        com.grokingcodeinterview.pattern37realInterviewProblems.Set set = new com.grokingcodeinterview.pattern37realInterviewProblems.Set();
        System.out.println("Adding 1, 2, 3 to the set:");
        set.add(1);
        set.add(2);
        set.add(3);
        System.out.println("Set contains 2? " + set.contains(2)); // true
        System.out.println("Set size: " + set.len()); // 3
        System.out.println("Removing 2 from the set: " + set.remove(2)); // true
        System.out.println("Set contains 2? " + set.contains(2)); // false
        System.out.println("Set size: " + set.len()); // 2

        System.out.println("=====================================");
        System.out.println("P02. Max Subarray");
        System.out.println("=====================================");

        int[] numsP02 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Input: " + Arrays.toString(numsP02) + " output: " + maxSubArray(numsP02) +" Expected Output: 6"); // Output: 6

        numsP02 =  new int[]{1};
        System.out.println("Input: " + Arrays.toString(numsP02) + " output: " + maxSubArray(numsP02) +" Expected Output: 1"); // Output: 1

        numsP02 = new int[]{5, 4, -1, 7, 8};
        System.out.println("Input: " + Arrays.toString(numsP02) + " output: " + maxSubArray(numsP02) +" Expected Output: 23"); // Output: 23
        numsP02 = new int[] {-2, 3, -1, 4, -6, 2, 3};
        System.out.println("Input: " + Arrays.toString(numsP02) + " output: " + maxSubArray(numsP02) +" Expected Output: 6"); // Output: 6


    }
}
