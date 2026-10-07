package com.grokingcodeinterview.pattern37realInterviewProblems;

/*
Develop a class that implements a Set data structure.
 A Set is a collection containing no duplicate elements and is primarily used to test whether a member is contained within,
  rather than retrieving a particular member.
   Please implement the
    add(element), remove(element), contains(element),
    and  _len_ methods using arrays -- use of dict is not allowed
 */
public class Set {

    private int[] arr;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public Set(){
        arr = new int[INITIAL_CAPACITY];
        size = 0;
    }

    public boolean  contains(int value){
        for (int num: arr){
            if(num == value){
                return true;
            }
        }
        return false;
    }

    private void resize(){
        int[] newArr = new int[arr.length *2];
        for(int i =0; i<arr.length; i++){
            newArr[i] = arr[i];
        }
        arr = newArr;
    }
    public boolean add(int value){
        if(contains(value)) return false;
        if(size == arr.length){
            resize();
        }
        arr[size] = value;
        size++;
        return true;
    }
    public int len(){
        return size;
    }

    public boolean remove(int value){
        for(int i =0; i<size; i++){
            if(arr[i] == value){
                // shift left
                for(int j =i; j<size -1; j++){
                    arr[j] = arr[j+1];
                }
                size --;
                return true;
            }

        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("Set Implementation :");
        System.out.println("=================================================================");
        Set set = new Set();
        System.out.println("Adding 1, 2, 3 to the set:");
        set.add(1);
        set.add(2);
        set.add(3);
        System.out.println("Set contains 2? " + set.contains(2)); // true
        System.out.println("Set size: " + set.len()); // 3
        System.out.println("Removing 2 from the set: " + set.remove(2)); // true
        System.out.println("Set contains 2? " + set.contains(2)); // false
        System.out.println("Set size: " + set.len()); // 2

    }
}
