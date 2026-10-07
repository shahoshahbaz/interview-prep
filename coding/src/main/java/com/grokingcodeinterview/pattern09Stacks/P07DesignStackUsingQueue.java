package com.grokingcodeinterview.pattern09Stacks;

import java.util.ArrayDeque;
import java.util.Deque;

public class P07DesignStackUsingQueue {

    public static class StackPushExpensive{
        private Deque<Integer> queue;

        public  StackPushExpensive(){
            this.queue = new ArrayDeque<>();
        }

        public void push(int n){
            queue.offer(n);
            int size = queue.size();
            for(int i =0; i<size-1; i++){
                queue.offer(queue.poll());
            }

        }
        public Integer pop(){
            if(isEmpty()){
                return null;
            }
            return queue.poll();

        }

        public Integer peek(){
            if(isEmpty()) return null;
            return queue.peek();
        }
        public boolean isEmpty(){
            return queue.isEmpty();
        }
    }

    public static class StackPullExpensive{

        Deque<Integer> queue ;
        public  StackPullExpensive(){
            this.queue = new ArrayDeque<>();


        }



        public void push(int n){
            queue.offer(n);
        }
        public Integer pop(){
            if(isEmpty()) return null;
            int size = queue.size();
            for(int i =0; i< size -1; i++){
                queue.offer(queue.poll());
            }

            return queue.poll();
        }
        public Integer peek(){
            if(isEmpty()) return null;
            int size = queue.size();
            for(int i =0; i< size -1; i++){
                queue.offer(queue.poll());
            }
            return queue.peek();


        }
        public boolean isEmpty(){
            return queue.isEmpty();
        }
    }

    public static void main(String[] args) {

    }
}

