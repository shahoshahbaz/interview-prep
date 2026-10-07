package com.grokingcodeinterview.pattern09Stacks;

import java.util.ArrayDeque;
import java.util.Deque;

public class P08DesignQueueUsingStacks {


    public static class QueueWithExpensiveDequeue {
        Deque<Integer> inStack;
        Deque<Integer> outStack;

        public QueueWithExpensiveDequeue(){
            this.inStack = new ArrayDeque<>();
            this.outStack = new ArrayDeque<>();
        }
        public void enqueue(int n){
            inStack.push(n);
        }
        public Integer dequeue(){
            if(inStack.isEmpty() && outStack.isEmpty()) return null;
            transferIfNeeded();;

            return outStack.pop();
        }

        public Integer peek(){
            if(inStack.isEmpty() && outStack.isEmpty()) return null;
            transferIfNeeded();
            return outStack.peek();
        }

        public void transferIfNeeded(){
            if(outStack.isEmpty()){
                while(!inStack.isEmpty()){
                    outStack.push(inStack.pop());
                }
            }

        }


    }

    public static class QueueWithExpensiveEnqueue {
        Deque<Integer> stack1;
        Deque<Integer> stack2;

        public QueueWithExpensiveEnqueue(){
            this.stack1 = new ArrayDeque<>();
            this.stack2 = new ArrayDeque<>();
        }

        public void enqueue(int n){
            while(!stack2.isEmpty()){
                stack1.push(stack2.pop());
            }

            stack1.push(n);
            while(!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }

        public Integer dequeue(){
            if(stack2.isEmpty()) return null;
            return stack2.pop();
        }

        public Integer peek(){
            if(stack2.isEmpty()) return null;
            return stack2.peek();
        }

        public boolean isEmpty(){
            return stack2.isEmpty();
        }

    }

    public static void main(String[] args) {

    }


}

