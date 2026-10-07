package com.ds.singleLinkedList;

public class LinkedList {
    private Node head;
    public LinkedList (){
        this.head = null;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public  void printList(){
        Node temp = head;
        StringBuilder sb = new StringBuilder();
        sb.append("Head ->");
        if (temp == null){
            sb.append("Null");
            System.out.println(sb.toString());
            return;
        }
        while (temp != null){
            sb.append(temp.data );
            if (temp.next != null){
                sb.append( "->");
            }else{
                sb.append( "-> Null");
            }
            temp = temp.next;
        }
        System.out.println(sb.toString());
    }
    @Override
    public String toString(){
        Node curr = head;
        StringBuilder sb = new StringBuilder();
        while (curr != null){
            sb.append(curr.data).append(" -> ");
            curr = curr.next;
        }
        sb.append("null");
        return sb.toString();
    }

    /**
     append method that appends a new node to the end of the linked list.
     **/

    public void append(int data){
        Node newNode = new Node(data);
        if (this.head == null){
            head = newNode;
            return;
        }
        Node temp = this.head;
        while (temp.next != null){

            temp = temp.next;
        }
        temp.next = newNode;
    }
    public Node createLinkedList(int[] lst) {
        for (int i = lst.length - 1; i >= 0; i--) {

            perpend(lst[i]);
        }
        return head;
    }



    /**
     *       removeLast  method  removes the last node from the linked list.
     */
    public void removeList() {

        Node temp = head;
        Node beforNode = head;
        if (temp == null ){
            System.out.println("The List is empty.");
            return;
        }
        if (temp.next == null){
            head = null;
            return;
        }
        while (temp.next != null){
            beforNode =temp;
            temp = temp.next;
        }
        beforNode.next = null;
    }

    /**
     *
     * prepend methods adds a new node at the beginning of the linked list.
      */

    public void perpend(int data){
        Node temp =head;
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    /**
     * removeFirst method removes the first node from the linked list and returns it.
     */
    public  void removeFirst(){
        if (head == null){
            System.out.println("list is empty.");
            return;
        }
        Node temp = head;
        Node pre= temp;
        temp =temp.next;
        head = temp;
        pre.next = null;
    }
    public void clear(){
        head =null;

    }

    /**
     *  insert method inserts a new node at a specified index in the linked list.

     */
    public boolean insert (int index, int data){

        if (index == 1){
//           perpend();
            return true;

        }

        Node temp = head;
        Node pre ;
        int counter =1;
        Node newNode = new Node(data);


        while (temp != null){
            pre = temp;
            temp = temp.next;
            if (counter == index){

                //  insert index;
             pre.next = newNode;
             newNode.next = temp;
            }
            counter ++;
        }
        return true;

    }
    /**
    * Remove method removes a node at specified index in the linkedList
     */

    public Node remove(int index){
        if (index <0) return null;
        Node temp = head;
        int counter =1;
        if (index ==1){
            head = head.next;
            temp.next = null;
            return temp;
        }
        Node pre = head;
        for (int i =1; i< index; i++){
            pre = temp;
            temp = temp.next;
        }
        pre.next = temp.next;
        temp.next = null;
        return temp;
    }

    public Node reverse(){
      Node temp = head;
      Node before = null;
      Node after = head ;

      while(temp != null){
          after = temp.next;
          temp.next = before;
          before = temp;
          temp = after;

      }
      head = before;
      return head;
    }


    public static void main(String[] args) {

        LinkedList list = new LinkedList();
        list.printList();
        list.append(1);
        list.printList();
        list.append(2);
        list.printList();
        list.append(3);
        list.printList();
        System.out.println("Remove last Node");
        list.removeList();
        list.printList();
        System.out.println("Remove last Node");
        list.removeList();
        list.printList();
        System.out.println("Remove last Node");
        list.removeList();
        list.printList();
        System.out.println("Remove last Node");
        list.removeList();
        list.printList();
        System.out.println("prepend list");
        list.clear();
        list.perpend(1);
        list.printList();
        list.perpend(2);
        list.printList();
        list.perpend(3);
        list.printList();
        System.out.println("Remove First Node..");
        list.removeFirst();
        list.printList();
        list.removeFirst();
        list.printList();
        list.removeFirst();
        list.printList();
        list.removeFirst();
        list.printList();
        System.out.println("insert into list at positon 2");

        list.clear();
        list.perpend(1);
        list.perpend(2);
        list.perpend(3);
        list.printList();
        list.insert(2, 4);
        list.printList();
        list.clear();
        System.out.println("Remove list at position 2");
        list.append(1);
         list.append(2);
        list.append(3);
        list.append(4);
        list.printList();
        list.remove(2);
        list.printList();
        System.out.println("Reverse the list....");
        list.reverse();
        list.printList();








    }



}
