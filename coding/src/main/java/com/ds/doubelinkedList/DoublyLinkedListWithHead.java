package com.ds.doubelinkedList;

/**
 * A class representing a doubly linked list.
 * Each node in the list contains a reference to the next node and the previous node.
 */
public class DoublyLinkedListWithHead  implements  DoublyLinkedList{

    public Node head;

    /** append method appends a new Node at the end of doubly linkedList**/

    public void append(int data){
        Node newNode = new Node(data);
        // edge case
        if (head == null){ //
            head= newNode;
        }
        Node temp = head;


        while (temp.next != null){
            temp = temp.next;
        }
        temp.next = newNode;
        newNode.prev = temp;
        newNode.next = null;
    }

    @Override
    public void printDoubllyList(){
        if(head == null) {
            System.out.println("Head -> null");
            return;
        }
            Node temp = head;
            StringBuilder sb = new StringBuilder();
            sb.append("DD: ");
            while (temp != null){
                sb.append(temp.data);
                if (temp.next != null){
                    sb.append("<=>");
                }else{
                    sb.append("->null");
                }

                temp = temp.next;


            }
            System.out.println(sb.toString() + "\nHead: " + head.data );
        }

    @Override
    public void printDoubllyList(Node head) {

        if(head == null) {
            System.out.println("Head -> null");
            return;
        }
        Node temp = head;
        StringBuilder sb = new StringBuilder();
        sb.append("DD: ");
        while (temp != null){
            sb.append(temp.data);
            if (temp.next != null){
                sb.append("<=>");
            }else{
                sb.append("->null");
            }

            temp = temp.next;


        }
        System.out.println(sb.toString() + "\nHead: " + head.data );
    }


    public void removeDoubleLinkedList(){
    head = null;
}




    /** RemoveLst metod remove the last node from doubly linked list **/
    public void removeLast(){
        // edge cases
        if (head == null ){
            System.out.println("The list is empty.");
            return;
        }

        if (head.next == null){
            head = null;
            return;
        }

        Node temp = head;
        while(temp.next != null){
            temp = temp.next;
        }
        temp.prev.next = null;


    }


    /** prepend method adds a new node to the beginning of the doubly linked list. **/
    public void prepend(int data){
        // edge cases
        Node newNode = new Node(data);
        if (head == null){
            head = newNode;
        }
        Node temp = head;

        newNode.next = temp;
        temp.prev = newNode;
        head = newNode;
    }
    /** RemoveFirst method removes the firstNode from dll **/
    public void removeFirst(){
        if (head == null){
            System.out.println("List is empty.");
            return;
        }
        Node temp = head;
        temp = temp.next;
        temp.prev = null;
        head.next = null;
        head = temp;
    }



    /** insert method inserts a new node with a given value at a specified index in the dll **/
    public void insert(int data, int index){
        //edge case


        Node newNode = new Node (data);
        Node temp = head;
        // get index -1
        for (int i =1; i<index-1; i++){
            temp = temp.next;
        }
        Node before = temp;
        Node after = temp.next;
        before.next = newNode;
        newNode.prev = before;
        newNode.next = after;
        after.prev = newNode;

    }

    /** remove method removes a node at a specific index for dll **/
    public void remove(int index){

        //edge cases

        Node temp = head;
        for (int i = 1; i< index -1; i++){
            temp = temp.next;
        }
        System.out.println("value of temp: "+temp.data);

        Node after = temp.next;
        after.next.prev = temp;
        temp.next = after.next;
        after.next = null;
        after.prev = null;


    }



    public static void main(String[] args) {
        DoublyLinkedListWithHead dList = new DoublyLinkedListWithHead();
        dList.append(1);
        dList.append(2);
        dList.append(3);
        dList.append(4);
        dList.printDoubllyList();
        System.out.println("Rmove last node...");
        dList.removeLast();
        dList.printDoubllyList();
        dList.removeLast();
        dList.printDoubllyList();
        dList.removeLast();
        dList.printDoubllyList();
        dList.removeLast();
        dList.printDoubllyList();
        dList.removeLast();
        dList.printDoubllyList();
        System.out.println("prepend to the list ...");
        dList.append(1);
        dList.append(2);
        dList.append(3);
        dList.append(4);
        dList.printDoubllyList();
        dList.prepend(5);
        dList.printDoubllyList();
        System.out.println("remove first node....");
        dList.removeFirst();
        dList.printDoubllyList();
        System.out.println("insert node at  position 3...");
        dList.insert(8, 3);
        dList.printDoubllyList();
        System.out.println("Remove at position 5....");
        dList.append(1);
        dList.append(2);
        dList.append(3);
        dList.append(4);
        dList.append(8);
        dList.append(10);
        dList.append(13);
        dList.append(14);
        dList.printDoubllyList();
        dList.remove(5);
        dList.printDoubllyList();





    }
}
