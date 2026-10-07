package com;

import com.ds.singleLinkedList.Node;

public class Utility {
    public static boolean LOGGING_FLAG = false;

    public static final String ANSI_BOLD = "\033[1m";
    public static final String ANSI_RESET = "\033[0m";
    public static void log(String message){
        if (LOGGING_FLAG){
            System.out.println( "\t"+message);
        }
    }


    public static String makeItBold(String message){
        return ANSI_BOLD + message + ANSI_RESET;
    }
    public static void swap(int[] nums, int i, int j ){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    public static String printArrayList(Node[] lists) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Node head: lists){
            Node temp = head;
            sb.append(" [  ");
            sb.append("Head ->");
            if (temp == null){
                sb.append("Null");
            } else {
                while (temp != null){
                    sb.append(temp.data);
                    if (temp.next != null){
                        sb.append("->");
                    } else {
                        sb.append("-> Null");
                    }
                    temp = temp.next;
                }
            }
            sb.append("]");  // Moved outside the if-else
            sb.append(",");
        }
        sb.append(" ]");
        return sb.toString();
    }

}
