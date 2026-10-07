package com.examples.queue;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackUsingDeque {

    public Deque<Integer> deque = new ArrayDeque<>();

    public void push (int element){
        deque.addFirst(element);
    }

    public  int pop(){
      return  deque.removeFirst();
    }

    public int peek() {
        return deque.peekFirst();
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public static void main(String[] args) {
        StackUsingDeque stack  = new StackUsingDeque();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.deque);
        System.out.println("Poping an elements");
        stack.pop();

        System.out.println(stack.deque);


    }


}
